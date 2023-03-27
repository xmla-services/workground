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

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.api.DialectResolver;
import org.eclipse.daanse.db.jdbc.dataloader.api.CsvDataLoadService;
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
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.osgi.test.common.dictionary.Dictionaries.dictionaryOf;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
class CsvDataLoadServiceImplTest {

    public static final String COMPONENT_NAME = "org.eclipse.daanse.db.jdbc.dataloader.csv.CsvDataLoadServiceImpl";
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
    void testPrivateDimension(@InjectService(filter = "(component.name=" + COMPONENT_NAME + ")") CsvDataLoadService csvDataLoadService) throws IOException, URISyntaxException, SQLException {
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Long> longCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Boolean> booleanCaptor = ArgumentCaptor.forClass(Boolean.class);
        ArgumentCaptor<Date> dateCaptor = ArgumentCaptor.forClass(Date.class);
        ArgumentCaptor<Integer> integerCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Double> doubleCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Short> shortCaptor = ArgumentCaptor.forClass(Short.class);
        ArgumentCaptor<Timestamp> timestampCaptor = ArgumentCaptor.forClass(Timestamp.class);

        Column column  = new Column("id", Type.INTEGER);
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
        Path csvDir = Paths.get(System.getProperty("user.dir") + "/../../../../src/test/resources/");
        csvDataLoadService.loadData(dataSource, tables, csvDir);
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
}
