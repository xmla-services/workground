/*********************************************************************
* Copyright (c) 2022 Contributors to the Eclipse Foundation.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*   SmartCity Jena - initial
*   Stefan Bischof (bipolis.org) - initial
**********************************************************************/
package org.eclipse.daanse.db.dialect.db.mysql;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AllDialectsVersionCheck {

    DataSource dataSource = mock(DataSource.class);
    Connection connection = mock(Connection.class);
    Statement statement = mock(Statement.class);
    DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
    ResultSet resultSet = mock(ResultSet.class);

    MySqlDialect1 d1 = new MySqlDialect1();

    MySqlDialect2 d2 = new MySqlDialect2();

    MySqlDialect3 d3 = new MySqlDialect3();

    @BeforeEach
    public void beforeEach() throws SQLException {

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.getMetaData()).thenReturn(databaseMetaData);
        when(databaseMetaData.getDatabaseProductName()).thenReturn("MYSQL");

        when(databaseMetaData.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(any())).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(false);// no infobright

    }

    @Test
    public void testVersion() throws SQLException {

        when(databaseMetaData.getDatabaseProductVersion()).thenReturn("3.9");
        Assertions.assertThat(d1.isCompatible(dataSource)).isTrue();
        Assertions.assertThat(d2.isCompatible(dataSource)).isFalse();
        Assertions.assertThat(d3.isCompatible(dataSource)).isFalse();

    }

    @Test
    public void testVersion2() throws SQLException {

        when(databaseMetaData.getDatabaseProductVersion()).thenReturn("4.0");
        Assertions.assertThat(d1.isCompatible(dataSource)).isFalse();
        Assertions.assertThat(d2.isCompatible(dataSource)).isTrue();
        Assertions.assertThat(d3.isCompatible(dataSource)).isFalse();

    }

    @Test
    public void testVersion3() throws SQLException {

        when(databaseMetaData.getDatabaseProductVersion()).thenReturn("5.7");
        Assertions.assertThat(d1.isCompatible(dataSource)).isFalse();
        Assertions.assertThat(d2.isCompatible(dataSource)).isFalse();
        Assertions.assertThat(d3.isCompatible(dataSource)).isTrue();

    }
}
