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
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.api.DialectResolver;
import org.eclipse.daanse.db.jdbc.util.impl.Type;
import org.eclipse.daanse.util.io.watcher.api.EventKind;
import org.eclipse.daanse.util.io.watcher.api.PathListener;
import org.eclipse.daanse.util.io.watcher.api.PathListenerConfig;
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

@Designate(ocd = OdsDataLoaderConfig.class, factory = true)
@Component(scope = ServiceScope.SINGLETON, service = PathListener.class)
@PathListenerConfig(kinds = EventKind.ENTRY_MODIFY,pattern = ".*.ods")
public class OdsDataLoader  implements PathListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(OdsDataLoader.class);
    public static final Converter CONVERTER = Converters.standardConverter();
    private Queue<Path> initialPaths = new ArrayDeque<>();
    @Reference
    private DialectResolver dialectResolver;
    @Reference
    private DataSource dataSource;

    private OdsDataLoaderConfig config;
    private Path basePath;

    @Activate
    public void activate(Map<String, Object> configMap) {

    	System.err.println(configMap);
        this.config = CONVERTER.convert(configMap)
            .to(OdsDataLoaderConfig.class);
    }

    @Deactivate
    public void deactivate() {
        config = null;
    }

    private void loadData(Path path) {
        Optional<Dialect> dialectOptional = this.dialectResolver.resolve(dataSource);
        if (dialectOptional.isPresent()) {
            Dialect dialect = dialectOptional.get();

            try (Connection connection = dataSource.getConnection()) {
                loadData(connection, dialect, path);
            } catch (SQLException e) {
                LOGGER.error("Database connection error", e);
            }
        } else {
            LOGGER.error("Database dialect did not determinate");
        }

    }

    public void loadData(Connection connection, Dialect dialect, Path p) {
        try {
            SpreadSheet spread = new SpreadSheet(p.toFile());
            int numSheets = spread.getNumSheets();
            LOGGER.debug("Number of sheets: {}", numSheets);
            List<Sheet> sheets = spread.getSheets();
            String schemaName = getDatabaseSchemaNameFromPath(p);
            if (dialect.supportParallelLoading()) {
                sheets.parallelStream().forEach(sheet -> loadSheet(connection, dialect, schemaName, sheet));
            } else {
                sheets.stream().forEach(sheet -> loadSheet(connection, dialect, schemaName, sheet));
            }
        } catch (IOException e){
            throw new OdsDataLoaderException("OdsDataLoaderService loadData error", e);
        }
    }

    private void loadSheet(Connection connection, Dialect dialect, String schemaName, Sheet sheet) {
        String tableName = sheet.getName();
        Map<String, Type> headersMap = getHeaders(sheet);
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
            throw new OdsDataLoaderException("Load data error", e);
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
        if (rows > 2) {
            for (int i = 2; i < rows; i++) {
                if (i > 2) {
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

    private Map<String, Type> getHeaders(Sheet sheet) {
        Map<String, Type> result = new HashMap<>();
        int columns = sheet.getMaxColumns();
        int rows = sheet.getMaxRows();
        if (rows > 1) {
            for (int i = 0; i < columns; i++) {
                Range headerRange = sheet.getRange(0, i);
                Range typeRange = sheet.getRange(1, i);
                if (hasContent(headerRange)) {
                    String columnName = headerRange.getValue().toString();
                    String typeName = typeRange.getValue().toString();
                    result.put(columnName, getColumnType(typeName));
                }
            }
        }
        return result;
    }

    private Type getColumnType(String stringType) {
        switch (stringType) {
            case "SMALLINT":
                return Type.SMALLINT;
            case "INTEGER":
                return Type.INTEGER;
            case "FLOAT", "REAL", "DOUBLE", "NUMERIC", "DECIMAL":
                return Type.NUMERIC;
            case "BIGINT":
                return Type.LONG;
            case "BOOLEAN":
                return Type.BOOLEAN;
            case "DATE":
                return Type.DATE;
            case "TIME":
                return Type.TIME;
            case "TIMESTAMP":
                return Type.TIMESTAMP;
            case "CHAR", "VARCHAR":
            default:
                return Type.STRING;
        }

    }

    private boolean hasContent(Range range) {
        Object value = range.getValue();
        return  value != null && !value.toString().isEmpty();
    }

    @Override
    public void handleBasePath(Path basePath) {
        this.basePath = basePath;
    }

    @Override
    public void handleInitialPaths(List<Path> initialPaths) {
        this.initialPaths.addAll(initialPaths);
        for (Path path : initialPaths) {
            loadData(path);
        }

    }

    @Override
    public void handlePathEvent(Path path, WatchEvent.Kind<Path> kind) {
        if ((kind.name().equals(StandardWatchEventKinds.ENTRY_CREATE.name()))
            || (kind.name().equals(StandardWatchEventKinds.ENTRY_MODIFY.name()))) {
            loadData(path);
        }
        if (kind.name().equals(StandardWatchEventKinds.ENTRY_DELETE.name())) {
            delete(path);
        }
    }
    private String getFileNameWithoutExtension(String fileName) {
        if (fileName.indexOf(".") > 0) {
            return fileName.substring(0, fileName.lastIndexOf("."));
        } else {
            return fileName;
        }
    }

    private void delete(Path path) {
        String tableName = getFileNameWithoutExtension(path.getFileName().toString());
        LOGGER.debug("Drop table {}", tableName);
        Optional<Dialect> dialectOptional = dialectResolver.resolve(dataSource);
        if (dialectOptional.isPresent()) {
            Dialect dialect = dialectOptional.get();
            try (Connection connection = dataSource.getConnection()) {
                dialect.clearTable(getDatabaseSchemaNameFromPath(path), tableName);
            } catch (SQLException e) {
                LOGGER.error("Database connection error", e);
            }
        } else {
            LOGGER.error("Database dialect did not determinate");
        }
    }

    private String getDatabaseSchemaNameFromPath(Path path) {
        Path parent = path.getParent();
        if (basePath.equals(parent)) {
            return null;
        }
        return parent.getFileName().toString();
    }

}
