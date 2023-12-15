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

import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.Csv;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
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

import javax.sql.DataSource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent.Kind;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.Map.Entry;

@Designate(ocd = CsvDataLoaderConfig.class, factory = true)
@Component(scope = ServiceScope.SINGLETON, service = PathListener.class)
@PathListenerConfig(kinds = EventKind.ENTRY_MODIFY, pattern = ".*.csv", recursive = true)
public class CsvDataLoader implements PathListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(CsvDataLoader.class);
	public static final Converter CONVERTER = Converters.standardConverter();

	private Queue<Path> initialPaths = new ArrayDeque<>();

	@Reference
	private DialectResolver dialectResolver;

	@Reference
	private DataSource dataSource;

	private CsvDataLoaderConfig config;

	private CsvParserSettings settings;
	private Path basePath;

	@Activate
	public void activate(CsvDataLoaderConfig config) {
		this.config=config;
		settings = Csv.parseRfc4180();
		settings.setLineSeparatorDetectionEnabled(config.lineSeparatorDetectionEnabled());
		settings.setNullValue(config.nullValue());
		settings.getFormat().setQuoteEscape(config.quoteEscape());
		settings.getFormat().setQuote(config.quote());
		settings.getFormat().setDelimiter(config.delimiter());
		settings.setQuoteDetectionEnabled(config.quoteDetectionEnabled());
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
				loadTable(connection, dialect, settings, path);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error("Database connection error", e);
			}
		} else {
			LOGGER.error("Database dialect did not determinate");
		}

	}

	private void loadTable(Connection connection, Dialect dialect, CsvParserSettings settings, Path path) {
		String fileName = getFileNameWithoutExtension(path.getFileName().toString());
		LOGGER.debug("Load table {}", fileName);
		String databaseSchemaName = getDatabaseSchemaNameFromPath(path);
		
		
		if (Boolean.TRUE.equals(config.clearTableBeforeLoad())) {
			try {
				String statementText = dialect.dropTable(databaseSchemaName, fileName, true);
				connection.createStatement().execute(statementText);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		if (!path.toFile().exists()) {

			LOGGER.warn("File does not exist - {} {}", fileName, path);
			return;
		}
		try {

			com.univocity.parsers.csv.CsvParser parser = new com.univocity.parsers.csv.CsvParser(settings);
			parser.beginParsing(path.toFile(), config.encoding());
			parser.parseNext();
			String[] headers = parser.getRecordMetadata().headers();
			String[] types = parser.parseNext();
			List<Map.Entry<String, Type>> headersTypeList = getHeadersTypeList(headers, types);
			if (!headersTypeList.isEmpty()) {
				createTable(connection,dialect, headersTypeList,databaseSchemaName, fileName);
				StringBuilder b = new StringBuilder();
				b.append("INSERT INTO ");
				b.append(dialect.quoteIdentifier(databaseSchemaName, fileName));
				b.append(" ( ");
				b.append(headersTypeList.stream().map(e -> dialect.quoteIdentifier(e.getKey()))
						.collect(Collectors.joining(",")));
				b.append(" ) VALUES ");
				b.append(" ( ");
				b.append(headersTypeList.stream().map(i -> "?").collect(Collectors.joining(",")));
				b.append(" ) ");

				System.out.println(b);
				try (PreparedStatement ps = connection.prepareStatement(b.toString())) {
					if (dialect.supportBatchOperations()) {
						batchExecute(connection, ps, parser, headersTypeList);
					} else {
						execute(ps, parser, headersTypeList);
					}
				} catch (SQLException e) {
					throw new CsvDataLoaderException("Load data error", e);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    private String getDatabaseSchemaNameFromPath(Path path) {
        Path parent = path.getParent();
        if (basePath.equals(parent)) {
        	return null;
        }
        return parent.getFileName().toString();
    }

    private void execute(PreparedStatement ps, CsvParser parser, List<Map.Entry<String, Type>> headersTypeList)
			throws SQLException {
		boolean first = true;
		long start = System.currentTimeMillis();
		com.univocity.parsers.common.record.Record r;
		while ((r = parser.parseNextRecord()) != null) {
			if (first) {
				first = false;
			} else {
				ps.clearParameters();
			}

			int i = 1;
			for (Map.Entry<String, Type> entry : headersTypeList) {
				processingTypeValues(ps, entry.getKey(), entry.getValue(), r, i);
				i++;
			}
			ps.executeUpdate();
		}

		LOGGER.debug("---");
		LOGGER.debug("execute time {}", (System.currentTimeMillis() - start));

	}

	private void batchExecute(Connection connection, PreparedStatement ps, com.univocity.parsers.csv.CsvParser parser,
			List<Map.Entry<String, Type>> headersTypeList) throws SQLException {

		boolean first = true;
		connection.setAutoCommit(false);
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
			for (Map.Entry<String, Type> entry : headersTypeList) {
				processingTypeValues(ps, entry.getKey(), entry.getValue(), r, i);
				i++;
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

		connection.commit();
		LOGGER.debug("execute commit time {}", (System.currentTimeMillis() - start));
		connection.setAutoCommit(true);
	}

	private void processingTypeValues(PreparedStatement ps, String columnName, Type type, Record r, int i)
			throws SQLException {
		if (r.getString(columnName) == null || r.getString(columnName).equals("NULL")) {
			ps.setObject(i, null);
		} else if (type.equals(Type.LONG)) {
			ps.setLong(i, r.getLong(columnName));

		} else if (type.equals(Type.BOOLEAN)) {
			ps.setBoolean(i, r.getBoolean(columnName));

		} else if (type.equals(Type.DATE)) {
			ps.setDate(i, Date.valueOf(r.getString(columnName)));

		} else if (type.equals(Type.INTEGER)) {
			ps.setInt(i, r.getInt(columnName));

		} else if (type.equals(Type.NUMERIC)) {
			ps.setDouble(i, r.getDouble(columnName));

		} else if (type.equals(Type.SMALLINT)) {
			ps.setShort(i, r.getShort(columnName));

		} else if (type.equals(Type.TIMESTAMP)) {
			ps.setTimestamp(i, Timestamp.valueOf(r.getString(columnName)));

		} else if (type.equals(Type.STRING)) {
			ps.setString(i, r.getString(columnName));
		}
	}

	private void createTable(Connection connection, Dialect dialect,List<Entry<String, Type>> headersTypeList, String schemaName, String tableName) {
		System.err.println(tableName);
		String tabeleWithSchemaName=dialect.quoteIdentifier(schemaName, tableName);
		try (Statement stmt = connection.createStatement();) {
			StringBuilder sb = new StringBuilder("CREATE TABLE ").append(tabeleWithSchemaName).append(" ( ");
			String cols=	headersTypeList.stream().map(e -> {
				StringBuilder s = new StringBuilder();
				s.append(dialect.quoteIdentifier(e.getKey())).append(" ").append(getStringType(e.getValue()));
				return s.toString();
			}).collect(Collectors.joining(", "));
			
			String sql=sb.append(cols).append(")").toString();
			stmt.execute(sql);
			System.out.println(sql);
			connection.commit();
			LOGGER.debug("Created table in given database...");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String getStringType(Type value) {
		switch (value) {
		case INTEGER:
		case SMALLINT:
			return "INTEGER";
		case NUMERIC:
			return "REAL";
		case BOOLEAN:
			return "BOOLEAN";
		case LONG:
			return "BIGINT";
		case DATE:
			return "DATE";
		case TIMESTAMP:
			return "TIMESTAMP";
		case TIME:
			return "TIME";
		case STRING:
		default:
			return "VARCHAR(255)";
		}
	}

	private List<Entry<String, Type>> getHeadersTypeList(String[] headers, String[] types) {
		List<Entry<String, Type>> result = new ArrayList<>();
		for (int i = 0; i < headers.length; i++) {
			try {
				result.add(new AbstractMap.SimpleEntry<>(headers[i], Type.fromName(types[i])));
			} catch (Exception e) {
				LOGGER.error("Load data error: Create data type for Csv loader error", e);
			}
		}
		return result;
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
				
				String statementText=dialect.dropTable( getDatabaseSchemaNameFromPath(path), tableName,true);
				connection.createStatement().execute(statementText);
			} catch (SQLException e) {
				LOGGER.error("Database connection error", e);
			}
		} else {
			LOGGER.error("Database dialect did not determinate");
		}
	}

	@Override
	public void handleInitialPaths(List<Path> initialPaths) {
		this.initialPaths.addAll(initialPaths);
		for (Path path : initialPaths) {

			if(!Files.isDirectory(path)) {
				loadData(path);
			}
		}

	}

	@Override
	public void handlePathEvent(Path path, Kind<Path> kind) {
		System.err.println(path);
		if(Files.isDirectory(path)) {
			return;
		}
		if ( kind.name().equals(StandardWatchEventKinds.ENTRY_MODIFY.name())) {
			loadData(path);
		}
		if (kind.name().equals(StandardWatchEventKinds.ENTRY_DELETE.name())) {
			delete(path);
		}
	}

	@Override
	public void handleBasePath(Path basePath) {
		this.basePath = basePath;

	}

}
