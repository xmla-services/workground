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
package org.eclipse.daanse.db.jdbc.dataloader.csv;

import com.univocity.parsers.csv.Csv;
import com.univocity.parsers.csv.CsvParserSettings;
import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.api.DialectResolver;
import org.eclipse.daanse.db.jdbc.dataloader.api.DataLoadService;
import org.eclipse.daanse.db.jdbc.util.impl.Column;
import org.eclipse.daanse.db.jdbc.util.impl.Table;
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

import javax.sql.DataSource;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Designate(ocd = CsvDataLoadServiceConfig.class, factory = true)
@Component(service = DataLoadService.class, scope = ServiceScope.SINGLETON)
public class CsvDataLoadServiceImpl implements DataLoadService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvDataLoadServiceImpl.class);
    public static final Converter CONVERTER = Converters.standardConverter();
    @Reference
    private DialectResolver dialectResolver;

    private CsvDataLoadServiceConfig config;

    @Activate
    public void activate(Map<String, Object> configMap) {
        this.config = CONVERTER.convert(configMap)
            .to(CsvDataLoadServiceConfig.class);
    }

    @Deactivate
    public void deactivate() {
        config = null;
    }


    @Override
    public void loadData(DataSource dataSource, List<Table> tables) {
        Optional<Dialect> dialectOptional = dialectResolver.resolve(dataSource);
        if (dialectOptional.isPresent()) {
            Dialect dialect = dialectOptional.get();
            CsvParserSettings settings = Csv.parseRfc4180();
            settings.setLineSeparatorDetectionEnabled(config.lineSeparatorDetectionEnabled());
            settings.setNullValue(config.nullValue());
            settings.getFormat().setQuoteEscape(config.quoteEscape());
            settings.getFormat().setQuote(config.quote());
            settings.getFormat().setDelimiter(config.delimiter());

            settings.setQuoteDetectionEnabled(config.quoteDetectionEnabled());
            try (Connection connection = dataSource.getConnection()) {
                if (dialect.supportParallelLoading()) {
                    tables.parallelStream().forEach(table -> loadTable(connection, dialect, settings, table));
                } else {
                    tables.stream().forEach(table -> loadTable(connection, dialect, settings, table));
                }
            }
            catch (Exception e) {

            }
        } else {
            throw new RuntimeException("Database dialect did not determinate");
        }
    }

    private void loadTable(Connection connection, Dialect dialect, CsvParserSettings settings, Table table) {
        try {
            LOGGER.debug("+" + table.tableName());
            if (config.clearTableBeforeLoad()) {
                dialect.clearTable(connection, table.schemaName(), table.tableName());
            }

            Path p = Paths.get(config.csvFolderPath()).resolve(new StringBuilder().append(config.csvFilePrefix())
                .append(table.tableName()).append(config.csvFileSuffix()).toString());

            if (!p.toFile().exists()) {
                LOGGER.warn("file does not exist-" + table.tableName());
                return;
            }

            com.univocity.parsers.csv.CsvParser parser = new com.univocity.parsers.csv.CsvParser(settings);
            parser.beginParsing(p.toFile(), config.encoding());
            parser.parseNext();
            String[] headers = parser.getRecordMetadata().headers();

            StringBuilder b = new StringBuilder();
            b.append("INSERT INTO ");
            b.append(dialect.quoteIdentifier(table.tableName(), table.schemaName()));
            b.append(" ( ");
            boolean first1 = true;
            for (String header : headers) {
                if (first1) {
                    first1 = false;
                } else {
                    b.append(",");
                }
                b.append(dialect.quoteIdentifier(header));
            }
            b.append(" ) VALUES ");
            b.append(" ( ");

            boolean first2 = true;
            for (String header : headers) {
                if (first2) {
                    first2 = false;
                } else {
                    b.append(",");
                }
                b.append("?");

            }
            b.append(" ) ");
            PreparedStatement ps = connection.prepareStatement(b.toString());

            boolean first = true;
            if (dialect.supportBatchOperations()) {
                ps.getConnection().setAutoCommit(false);
            }
            long start = System.currentTimeMillis();
            com.univocity.parsers.common.record.Record r;
            int count = 0;
            while ((r = parser.parseNextRecord()) != null) {
                count++;
                if (first) {
                    first = false;
                } else {
                    ps.clearParameters();
                }

                int i = 1;
                for (Column col : table.getColumns()) {

                    if (r.getString(col.name()) == null || r.getString(col.name()).equals("NULL")) {
                        ps.setObject(i, null);
                    } else if (col.getType().equals(Type.LONG)) {
                        ps.setLong(i, r.getLong(col.getName()));

                    } else if (col.getType().equals(Type.BOOLEAN)) {
                        ps.setBoolean(i, r.getBoolean(col.name()));

                    } else if (col.getType().equals(Type.DATE)) {
                        ps.setDate(i, Date.valueOf(r.getString(col.name())));

                    } else if (col.getType().equals(Type.INTEGER)) {
                        ps.setInt(i, r.getInt(col.name()));

                    } else if (col.type().equals(Type.NUMERIC)) {
                        ps.setDouble(i, r.getDouble(col.name()));

                    } else if (col.type().equals(Type.SMALLINT)) {
                        ps.setShort(i, r.getShort(col.name()));

                    } else if (col.type().equals(Type.TIMESTAMP)) {
                        ps.setTimestamp(i, Timestamp.valueOf(r.getString(col.name())));

                    } else if (col.type().equals(Type.STRING)) {
                        ps.setString(i, r.getString(col.name()));

                    }

                    i++;
                }
                if (dialect.supportBatchOperations()) {
                    ps.addBatch();
                    if (count % config.batchSize() == 0) {
                        ps.executeBatch();
                        LOGGER.debug("execute batch time " + (System.currentTimeMillis() - start));
                        connection.commit();
                        LOGGER.debug("execute commit time " + (System.currentTimeMillis() - start));
                        start = System.currentTimeMillis();
                    }
                } else {
                    ps.executeUpdate();
                }
            }

            LOGGER.debug("---");
            if (dialect.supportBatchOperations()) {
                ps.executeBatch();
                LOGGER.debug("execute batch time " + (System.currentTimeMillis() - start));

                connection.commit();
                LOGGER.debug("execute commit time " + (System.currentTimeMillis() - start));
                connection.setAutoCommit(true);
            }
            else {
                LOGGER.debug("execute time" + (System.currentTimeMillis() - start));
            }

        } catch (Exception e) {
            throw new RuntimeException("Load data error", e);
        }
    }
}
