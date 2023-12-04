/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.db.jdbc.dataloader.ods;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.api.DialectResolver;
import org.eclipse.daanse.db.jdbc.metadata.api.JdbcMetaDataService;
import org.eclipse.daanse.db.jdbc.metadata.api.JdbcMetaDataServiceFactory;
import org.eclipse.daanse.db.jdbc.util.impl.Type;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.util.converter.Converter;
import org.osgi.util.converter.Converters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.miachm.sods.Range;
import com.github.miachm.sods.Sheet;
import com.github.miachm.sods.SpreadSheet;

@Designate(ocd = OdsDataLoadServiceConfig.class, factory = true)
@Component( scope = ServiceScope.SINGLETON)
public class OdsDataLoadServiceImpl  {
    private static final Logger LOGGER = LoggerFactory.getLogger(OdsDataLoadServiceImpl.class);
    public static final Converter CONVERTER = Converters.standardConverter();
    @Reference
    private DialectResolver dialectResolver;
    @Reference
    private DataSource dataSource;

    @Reference
    private JdbcMetaDataServiceFactory jmdsf;


    private OdsDataLoadServiceConfig config;

    @Activate
    public void activate(Map<String, Object> configMap) {
        this.config = CONVERTER.convert(configMap)
            .to(OdsDataLoadServiceConfig.class);
    }

    @Deactivate
    public void deactivate() {
        config = null;
    }

    public void loadData() {
        try {
            Path p = Paths.get(config.odsFolderPath()).resolve(new StringBuilder().append(config.odsFilePrefix())
                .append(config.odsFileName()).append(config.odsFileSuffix()).toString());
            if (!p.toFile().exists()) {
                String fileName = config.odsFileName();
                LOGGER.warn("File does not exist - {}.", fileName);
                return;
            }
            Optional<Dialect> dialectOptional = dialectResolver.resolve(dataSource);

            if (dialectOptional.isPresent()) {
                Dialect dialect = dialectOptional.get();
                try (Connection connection = dataSource.getConnection()) {
                    JdbcMetaDataService jms = jmdsf.create(connection);
                    SpreadSheet spread = new SpreadSheet(p.toFile());
                    int numSheets = spread.getNumSheets();
                    LOGGER.debug("Number of sheets: {}", numSheets);
                    List<Sheet> sheets = spread.getSheets();
                    String schemaName = connection.getSchema();
                    if (dialect.supportParallelLoading()) {
                        sheets.parallelStream().forEach(sheet -> loadSheet(connection, jms, dialect, schemaName, sheet));
                    } else {
                        sheets.stream().forEach(sheet -> loadSheet(connection, jms, dialect, schemaName, sheet));
                    }
                } catch (SQLException e) {
                    throw new OdsDataLoadException("Database connection error", e);
                }
            }

        } catch (IOException e){
            throw new OdsDataLoadException("OdsDataLoaderService loadData error", e);
        }
    }

    private void loadSheet(Connection connection, JdbcMetaDataService jms, Dialect dialect, String schemaName, Sheet sheet) {
        String tableName = sheet.getName();
        Map<String, Type> headersMap = getHeaders( schemaName, tableName, sheet, jms);
        StringBuilder b = new StringBuilder();
        Set<String> headers = headersMap.keySet();
        b.append("INSERT INTO ");
        b.append(dialect.quoteIdentifier(tableName, schemaName));
        b.append(" ( ");
        b.append(headers.stream().map(dialect::quoteIdentifier).collect(Collectors.joining(",")));
        b.append(" ) VALUES ");
        b.append(" ( ");
        b.append(headers.stream().map(i -> "?").collect(Collectors.joining(",")));
        b.append(" ) ");

        try (PreparedStatement ps = connection.prepareStatement(b.toString())) {
            if (dialect.supportBatchOperations()) {
                batchExecute(ps, headersMap, sheet);
            } else {
                execute(ps, headersMap, sheet);
            }
        } catch (SQLException e) {
            throw new OdsDataLoadException("Load data error", e);
        }

    }

    private void batchExecute(PreparedStatement ps, Map<String, Type> headersMap, Sheet sheet) throws SQLException {
        ps.getConnection().setAutoCommit(false);
        long start = System.currentTimeMillis();
        int count = 0;
        int columns = sheet.getMaxColumns();
        int rows = sheet.getMaxRows();
        if (rows > 1) {


            for (int i = 1; i < rows; i++) {
                count++;
                if (i > 1) {
                    ps.clearParameters();
                }
                for (int j = 0; j < columns; j++) {
                    processingTypeValues( ps, headersMap, i, j, sheet );
                }
                ps.addBatch();

            }

            ps.addBatch();
            if (count % config.batchSize() == 0) {
                ps.executeBatch();
                LOGGER.debug("execute batch time {}", (System.currentTimeMillis() - start));
                ps.getConnection().commit();
                LOGGER.debug("execute commit time {}", (System.currentTimeMillis() - start));
                start = System.currentTimeMillis();
            }
        }

        LOGGER.debug("---");
        ps.executeBatch();
        LOGGER.debug("execute batch time {}", (System.currentTimeMillis() - start));

        ps.getConnection().commit();
        LOGGER.debug("execute commit time {}", (System.currentTimeMillis() - start));
        ps.getConnection().setAutoCommit(true);

    }

    private void execute(PreparedStatement ps, Map<String, Type> headersMap, Sheet sheet) throws SQLException {
        long start = System.currentTimeMillis();
        int columns = sheet.getMaxColumns();
        int rows = sheet.getMaxRows();
        if (rows > 1) {
            for (int i = 1; i < rows; i++) {
                if (i > 1) {
                    ps.clearParameters();
                }
                for (int j = 0; j < columns; j++) {
                    processingTypeValues( ps, headersMap, i, j, sheet );
                }
                ps.executeUpdate();
            }
        }
        LOGGER.debug("---");
        LOGGER.debug("execute time {}", (System.currentTimeMillis() - start));

    }

    private void processingTypeValues(PreparedStatement ps, Map<String, Type> headersMap, int row, int column, Sheet sheet) throws SQLException {
        Range range = sheet.getRange(0, column);
        if ( hasContent(range) ) {
            String columnName = range.getValue().toString();
            Type type = headersMap.get(columnName);
            Object value = sheet.getRange(row, column).getValue();
            if (value == null || value.toString().equals("NULL")) {
                ps.setObject(row - 1, null);
            } else if (type.equals(Type.LONG)) {
                ps.setLong(row - 1, Double.valueOf(sheet.getRange(row, column).getValue().toString()).longValue());

            } else if (type.equals(Type.BOOLEAN)) {
                ps.setBoolean(row - 1, Boolean.valueOf(sheet.getRange(row, column).getValue().toString()));

            } else if (type.equals(Type.DATE)) {
                ps.setDate(row - 1, Date.valueOf(sheet.getRange(row, column).getValue().toString()));

            } else if (type.equals(Type.INTEGER)) {
                ps.setInt(row - 1, Double.valueOf(sheet.getRange(row, column).getValue().toString()).intValue());

            } else if (type.equals(Type.NUMERIC)) {
                ps.setDouble(row - 1, Double.valueOf(sheet.getRange(row, column).getValue().toString()));

            } else if (type.equals(Type.SMALLINT)) {
                ps.setShort(row - 1, Short.valueOf(sheet.getRange(row, column).getValue().toString()));

            } else if (type.equals(Type.TIMESTAMP)) {
                ps.setTimestamp(row - 1, Timestamp.valueOf(sheet.getRange(row, column).getValue().toString()));

            } else if (type.equals(Type.TIME)) {
                ps.setTime(row - 1, convertTime(sheet.getRange(row, column).getValue()));

            } else if (type.equals(Type.STRING)) {
                ps.setString(row - 1, sheet.getRange(row, column).getValue().toString());
            }
        }
    }

    private Time convertTime(Object value) {
        if ( value instanceof Duration duration) {
           return new Time(duration.toMillis());
        }
        return Time.valueOf(value.toString());
    }

    private Map<String, Type> getHeaders(String schemaName, String tableName, Sheet sheet, JdbcMetaDataService jms) {
        Map<String, Type> result = new HashMap<>();
        int columns = sheet.getMaxColumns();
        for (int i = 0; i < columns; i++) {
            Range range = sheet.getRange(0, i);
            if ( hasContent(range) ) {
                String columnName = range.getValue().toString();
                try {
                    result.put(columnName, getColumnType(jms.getColumnDataType(schemaName, tableName, columnName)));
                } catch (SQLException e) {
                    throw new OdsDataLoadException("Get getColumnDataType error", e);
                }
            }
        }
        return result;
    }

    private Type getColumnType(Optional<Integer> optionalType) {
        Integer type = optionalType.orElseThrow(
            () -> new OdsDataLoadException("getColumnType: type is absent")
        );
        switch (type) {
            case Types.TINYINT, Types.SMALLINT:
                return Type.SMALLINT;
            case Types.INTEGER:
                return Type.INTEGER;
            case Types.FLOAT, Types.REAL, Types.DOUBLE, Types.NUMERIC, Types.DECIMAL:
                return Type.NUMERIC;
            case Types.BIGINT:
                return Type.LONG;
            case Types.BOOLEAN:
                return Type.BOOLEAN;
            case Types.DATE:
                return Type.DATE;
            case Types.TIME:
                return Type.TIME;
            case Types.TIMESTAMP:
                return Type.TIMESTAMP;
            case Types.CHAR, Types.VARCHAR:
            default:
                return Type.STRING;
        }

    }

    private boolean hasContent(Range range) {
        Object value = range.getValue();
        return  value != null && !value.toString().isEmpty();
    }


}
