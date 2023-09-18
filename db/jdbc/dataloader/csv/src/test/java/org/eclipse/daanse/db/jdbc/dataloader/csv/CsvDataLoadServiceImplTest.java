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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.osgi.test.common.dictionary.Dictionaries.dictionaryOf;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.api.DialectResolver;
import org.eclipse.daanse.db.jdbc.dataloader.api.DataLoadService;
import org.eclipse.daanse.db.jdbc.util.impl.Column;
import org.eclipse.daanse.db.jdbc.util.impl.Table;
import org.eclipse.daanse.db.jdbc.util.impl.Type;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.annotations.RequireConfigurationAdmin;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.service.ServiceAware;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@RequireConfigurationAdmin
class CsvDataLoadServiceImplTest {

	@TempDir
	Path path;
	public static final String COMPONENT_NAME = "org.eclipse.daanse.db.jdbc.dataloader.csv.CsvDataLoadServiceImpl";
	@InjectBundleContext
	BundleContext bc;
	DialectResolver dialectResolver = mock(DialectResolver.class);
	Dialect dialect = mock(Dialect.class);
	Connection connection = mock(Connection.class);
	Statement statement = mock(Statement.class);
	PreparedStatement preparedStatement = mock(PreparedStatement.class);

	DataSource dataSource = mock(DataSource.class);
	@InjectService
	ConfigurationAdmin ca;

	Configuration conf;

	@BeforeEach
	void beforeEach() throws SQLException, IOException {
		copy("test.csv");

		bc.registerService(DialectResolver.class, dialectResolver, dictionaryOf("ds", "1"));
		when(dialectResolver.resolve(any(DataSource.class))).thenReturn(Optional.of(dialect));
		when(dialect.getDialectName()).thenReturn("MYSQL");
		when(dataSource.getConnection()).thenReturn(connection);
		when(connection.prepareStatement(any())).thenReturn(preparedStatement);
		when(preparedStatement.getConnection()).thenReturn(connection);
	}

	private void copy(String... files) throws IOException {

		for (String file : files) {
			InputStream is = bc.getBundle().getResource(file).openConnection().getInputStream();
			Files.copy(is, path.resolve(file));
		}
	}

	@AfterEach
	void afterEach() throws SQLException, IOException {
		if (conf != null) {
			conf.delete();
		}
	}

	@Test
	void testBatch(
			@InjectService(cardinality = 0, filter = "(component.name=" + COMPONENT_NAME
					+ ")") ServiceAware<DataLoadService> csvDataLoadServiceAware)
			throws IOException, URISyntaxException, SQLException, InterruptedException {

		setupCsvDataLoadServiceImpl(true, "NULL", '\\', '\"', ",", path, ".csv", "", "UTF-8", true, true);

		ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Long> longCaptor = ArgumentCaptor.forClass(Long.class);
		ArgumentCaptor<Boolean> booleanCaptor = ArgumentCaptor.forClass(Boolean.class);
		ArgumentCaptor<Date> dateCaptor = ArgumentCaptor.forClass(Date.class);
		ArgumentCaptor<Integer> integerCaptor = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Double> doubleCaptor = ArgumentCaptor.forClass(Double.class);
		ArgumentCaptor<Short> shortCaptor = ArgumentCaptor.forClass(Short.class);
		ArgumentCaptor<Timestamp> timestampCaptor = ArgumentCaptor.forClass(Timestamp.class);
		when(dialect.supportBatchOperations()).thenReturn(true);

		List<Column> list = List.of(new Column("id", Type.INTEGER), new Column("testLong", Type.LONG),
				new Column("testBoolean", Type.BOOLEAN), new Column("testDate", Type.DATE),
				new Column("testInteger", Type.INTEGER), new Column("testNumeric", Type.NUMERIC),
				new Column("testSmallInt", Type.SMALLINT), new Column("testTimeStamp", Type.TIMESTAMP),
				new Column("testString", Type.STRING));
		Table t = new Table("test", "test", List.of(), list);
		List<Table> tables = List.of(t);
		//
		csvDataLoadServiceAware.waitForService(1000).loadData(dataSource, tables);
		verify(connection, (times(1))).prepareStatement(stringCaptor.capture());

		verify(preparedStatement, (times(4))).setInt(integerCaptor.capture(), integerCaptor.capture());
		verify(preparedStatement, (times(2))).setLong(integerCaptor.capture(), longCaptor.capture());
		verify(preparedStatement, (times(2))).setBoolean(integerCaptor.capture(), booleanCaptor.capture());
		verify(preparedStatement, (times(2))).setDate(integerCaptor.capture(), dateCaptor.capture());
		verify(preparedStatement, (times(2))).setDouble(integerCaptor.capture(), doubleCaptor.capture());
		verify(preparedStatement, (times(2))).setShort(integerCaptor.capture(), shortCaptor.capture());
		verify(preparedStatement, (times(2))).setTimestamp(integerCaptor.capture(), timestampCaptor.capture());
		verify(preparedStatement, (times(2))).setString(integerCaptor.capture(), stringCaptor.capture());

		verify(preparedStatement, (times(2))).addBatch();
		verify(preparedStatement, (times(1))).executeBatch();
	}

	private void setupCsvDataLoadServiceImpl(Boolean lineSeparatorDetectionEnabled, String nullValue,
			Character quoteEscape, Character quote, String delimiter, Path csvFolderPath, String csvFileSuffix,
			String csvFilePrefix, String encoding, Boolean quoteDetectionEnabled, Boolean clearTableBeforeLoad)
			throws IOException {
		conf = ca.getFactoryConfiguration(CsvDataLoadServiceImplTest.COMPONENT_NAME, "1", "?");
		Dictionary<String, Object> dict = new Hashtable<>();
		if (lineSeparatorDetectionEnabled != null) {

			dict.put("lineSeparatorDetectionEnabled", lineSeparatorDetectionEnabled);
		}
		if (nullValue != null) {

			dict.put("nullValue", nullValue);
		}
		if (quoteEscape != null) {
			dict.put("quoteEscape", quoteEscape);
		}
		if (quote != null) {
			dict.put("quote", quote);
		}
		if (delimiter != null) {
			dict.put("delimiter", delimiter);
		}
		if (csvFolderPath != null) {
			dict.put("csvFolderPath", csvFolderPath.toAbsolutePath().toString());
		}
		if (csvFileSuffix != null) {
			dict.put("csvFileSuffix", csvFileSuffix);
		}
		if (csvFilePrefix != null) {
			dict.put("csvFilePrefix", csvFilePrefix);
		}
		if (encoding != null) {
			dict.put("encoding", encoding);
		}
		if (quoteDetectionEnabled != null) {
			dict.put("quoteDetectionEnabled", quoteDetectionEnabled);
		}
		if (quoteDetectionEnabled != null) {
			dict.put("clearTableBeforeLoad", quoteDetectionEnabled);
		}
		conf.update(dict);
	}

	@Test
	void testWithoutBach(
			@InjectService(cardinality = 0, filter = "(component.name=" + COMPONENT_NAME
					+ ")") ServiceAware<DataLoadService> csvDataLoadServiceAware)
			throws IOException, URISyntaxException, SQLException, InterruptedException {
		setupCsvDataLoadServiceImpl(true, "NULL", '\\', '\"', ",", path, ".csv", "", "UTF-8", true, true);

		ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Long> longCaptor = ArgumentCaptor.forClass(Long.class);
		ArgumentCaptor<Boolean> booleanCaptor = ArgumentCaptor.forClass(Boolean.class);
		ArgumentCaptor<Date> dateCaptor = ArgumentCaptor.forClass(Date.class);
		ArgumentCaptor<Integer> integerCaptor = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Double> doubleCaptor = ArgumentCaptor.forClass(Double.class);
		ArgumentCaptor<Short> shortCaptor = ArgumentCaptor.forClass(Short.class);
		ArgumentCaptor<Timestamp> timestampCaptor = ArgumentCaptor.forClass(Timestamp.class);
		when(dialect.supportBatchOperations()).thenReturn(false);

		List<Column> list = List.of(new Column("id", Type.INTEGER), new Column("testLong", Type.LONG),
				new Column("testBoolean", Type.BOOLEAN), new Column("testDate", Type.DATE),
				new Column("testInteger", Type.INTEGER), new Column("testNumeric", Type.NUMERIC),
				new Column("testSmallInt", Type.SMALLINT), new Column("testTimeStamp", Type.TIMESTAMP),
				new Column("testString", Type.STRING));
		Table t = new Table("test", "test", List.of(), list);
		List<Table> tables = List.of(t);
		//
		csvDataLoadServiceAware.waitForService(1000).loadData(dataSource, tables);
		verify(connection, (times(1))).prepareStatement(stringCaptor.capture());

		verify(preparedStatement, (times(4))).setInt(integerCaptor.capture(), integerCaptor.capture());
		verify(preparedStatement, (times(2))).setLong(integerCaptor.capture(), longCaptor.capture());
		verify(preparedStatement, (times(2))).setBoolean(integerCaptor.capture(), booleanCaptor.capture());
		verify(preparedStatement, (times(2))).setDate(integerCaptor.capture(), dateCaptor.capture());
		verify(preparedStatement, (times(2))).setDouble(integerCaptor.capture(), doubleCaptor.capture());
		verify(preparedStatement, (times(2))).setShort(integerCaptor.capture(), shortCaptor.capture());
		verify(preparedStatement, (times(2))).setTimestamp(integerCaptor.capture(), timestampCaptor.capture());
		verify(preparedStatement, (times(2))).setString(integerCaptor.capture(), stringCaptor.capture());

		verify(preparedStatement, (times(2))).executeUpdate();
	}

}
