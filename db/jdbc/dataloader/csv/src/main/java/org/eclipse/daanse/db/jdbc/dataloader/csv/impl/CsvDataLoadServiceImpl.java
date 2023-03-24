package org.eclipse.daanse.db.jdbc.dataloader.csv.impl;

import com.univocity.parsers.csv.Csv;
import com.univocity.parsers.csv.CsvParserSettings;
import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.api.DialectResolver;
import org.eclipse.daanse.db.jdbc.dataloader.csv.api.CsvDataLoadService;
import org.eclipse.daanse.db.jdbc.util.impl.Column;
import org.eclipse.daanse.db.jdbc.util.impl.Table;
import org.eclipse.daanse.db.jdbc.util.impl.Type;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Component(service = CsvDataLoadService.class, scope = ServiceScope.SINGLETON)
public class CsvDataLoadServiceImpl implements CsvDataLoadService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvDataLoadServiceImpl.class);
    @Reference
    private DialectResolver dialectResolver;


    @Override
    public void loadData(DataSource dataSource, List<Table> tables, Path csvDir) {
        Optional<Dialect> dialectOptional = dialectResolver.resolve(dataSource);
        if (dialectOptional.isPresent()) {
            Dialect dialect = dialectOptional.get();
            CsvParserSettings settings = Csv.parseRfc4180();
            settings.setLineSeparatorDetectionEnabled(true);
            settings.setNullValue("NULL");
            settings.getFormat().setQuoteEscape('\\');
            settings.getFormat().setQuote('"');

            settings.setQuoteDetectionEnabled(true);

            tables.parallelStream().forEach(table -> {
                try (Connection connection = dataSource.getConnection();) {
                    System.out.println("+" + table.tableName());

                    Path p = csvDir.resolve(table.tableName() + ".csv");

                    if (!p.toFile().exists()) {
                        LOGGER.warn("file does not exist-" + table.tableName());

                    }

                    com.univocity.parsers.csv.CsvParser parser = new com.univocity.parsers.csv.CsvParser(settings);
                    parser.beginParsing(p.toFile(), StandardCharsets.UTF_8);
                    parser.parseNext();
                    String[] headers = parser.getRecordMetadata().headers();

                    StringBuilder b = new StringBuilder();
                    b.append("INSERT INTO ");
                    b.append(dialect.quoteIdentifier(table.schemaName(), table.schemaName()));
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
                    ps.getConnection().setAutoCommit(false);
                    com.univocity.parsers.common.record.Record r;
                    while ((r = parser.parseNextRecord()) != null) {

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
                        ps.addBatch();
                    }

                    long start = System.currentTimeMillis();
                    LOGGER.debug("---");
                    ps.executeBatch();
                    LOGGER.debug("" + (System.currentTimeMillis() - start));

                    connection.commit();
                    LOGGER.debug("" + (System.currentTimeMillis() - start));
                    connection.setAutoCommit(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        } else {
            throw new RuntimeException("Database dialect did not determinate");
        }
    }
}
