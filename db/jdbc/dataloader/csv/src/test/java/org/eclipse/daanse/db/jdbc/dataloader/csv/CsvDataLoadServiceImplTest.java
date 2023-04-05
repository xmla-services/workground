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
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.api.DialectResolver;
import org.eclipse.daanse.db.jdbc.dataloader.api.DataLoadService;
import org.eclipse.daanse.db.jdbc.util.impl.Column;
import org.eclipse.daanse.db.jdbc.util.impl.Table;
import org.eclipse.daanse.db.jdbc.util.impl.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.osgi.framework.BundleContext;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@WithFactoryConfiguration(factoryPid = CsvDataLoadServiceImplTest.COMPONENT_NAME, name = "1", location = "?", properties = {
    @Property(key = "lineSeparatorDetectionEnabled", value = "true", scalar = Property.Scalar.Boolean),
    @Property(key = "nullValue", value = "NULL"),
    @Property(key = "quoteEscape", value = "\\", scalar = Property.Scalar.Character),
    @Property(key = "quote", value = "\"", scalar = Property.Scalar.Character),
    @Property(key = "delimiter", value = ","),
    @Property(key = "csvFolderPath", value = CsvDataLoadServiceImplTest.PATH),
    @Property(key = "csvFileSuffix", value = ".csv"),
    @Property(key = "csvFilePrefix", value = ""),
    @Property(key = "encoding", value = "UTF-8"),
    @Property(key = "quoteDetectionEnabled", value = "true", scalar = Property.Scalar.Boolean),
    @Property(key = "clearTableBeforeLoad", value = "true", scalar = Property.Scalar.Boolean)
})
class CsvDataLoadServiceImplTest {

    public static final String COMPONENT_NAME = "org.eclipse.daanse.db.jdbc.dataloader.csv.CsvDataLoadServiceImpl";
    public static final String PATH = "../../../../../src/test/resources/";
    @InjectBundleContext
    BundleContext bc;
    DialectResolver dialectResolver = mock(DialectResolver.class);
    Dialect dialect = mock(Dialect.class);
    Connection connection = mock(Connection.class);
    Statement statement = mock(Statement.class);
    PreparedStatement preparedStatement = mock(PreparedStatement.class);


    DataSource dataSource = mock(DataSource.class);

    @BeforeEach
    void beforeEach() throws SQLException {
        bc.registerService(DialectResolver.class, dialectResolver, dictionaryOf("ds", "1"));
        when(dialectResolver.resolve(any())).thenReturn(Optional.of(dialect));
        when(dialect.getDialectName()).thenReturn("MYSQL");
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(any())).thenReturn(preparedStatement);
        when(preparedStatement.getConnection()).thenReturn(connection);
    }
    @Test
    void testBach(@InjectService(filter = "(component.name=" + COMPONENT_NAME + ")") DataLoadService csvDataLoadService) throws IOException, URISyntaxException, SQLException {
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Long> longCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Boolean> booleanCaptor = ArgumentCaptor.forClass(Boolean.class);
        ArgumentCaptor<Date> dateCaptor = ArgumentCaptor.forClass(Date.class);
        ArgumentCaptor<Integer> integerCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Double> doubleCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Short> shortCaptor = ArgumentCaptor.forClass(Short.class);
        ArgumentCaptor<Timestamp> timestampCaptor = ArgumentCaptor.forClass(Timestamp.class);
        when(dialect.supportBatchOperations()).thenReturn(true);

        List<Column> list = List.of(
            new Column("id", Type.INTEGER),
            new Column("testLong", Type.LONG),
            new Column("testBoolean", Type.BOOLEAN),
            new Column("testDate", Type.DATE),
            new Column("testInteger", Type.INTEGER),
            new Column("testNumeric", Type.NUMERIC),
            new Column("testSmallInt", Type.SMALLINT),
            new Column("testTimeStamp", Type.TIMESTAMP),
            new Column("testString", Type.STRING)
        );
        Table t = new Table("test", "test",  List.of(), list);
        List<Table> tables = List.of(t);
        //
        csvDataLoadService.loadData(dataSource, tables);
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

    @Test
    void testWithoutBach(@InjectService(filter = "(component.name=" + COMPONENT_NAME + ")") DataLoadService csvDataLoadService) throws IOException, URISyntaxException, SQLException {
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Long> longCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Boolean> booleanCaptor = ArgumentCaptor.forClass(Boolean.class);
        ArgumentCaptor<Date> dateCaptor = ArgumentCaptor.forClass(Date.class);
        ArgumentCaptor<Integer> integerCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Double> doubleCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Short> shortCaptor = ArgumentCaptor.forClass(Short.class);
        ArgumentCaptor<Timestamp> timestampCaptor = ArgumentCaptor.forClass(Timestamp.class);
        when(dialect.supportBatchOperations()).thenReturn(false);

        List<Column> list = List.of(
            new Column("id", Type.INTEGER),
            new Column("testLong", Type.LONG),
            new Column("testBoolean", Type.BOOLEAN),
            new Column("testDate", Type.DATE),
            new Column("testInteger", Type.INTEGER),
            new Column("testNumeric", Type.NUMERIC),
            new Column("testSmallInt", Type.SMALLINT),
            new Column("testTimeStamp", Type.TIMESTAMP),
            new Column("testString", Type.STRING)
        );
        Table t = new Table("test", "test",  List.of(), list);
        List<Table> tables = List.of(t);
        //
        csvDataLoadService.loadData(dataSource, tables);
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
