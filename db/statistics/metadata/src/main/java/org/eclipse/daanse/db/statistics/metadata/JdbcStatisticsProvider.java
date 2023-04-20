/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*
* Contributors:
*   SmartCity Jena - major API, docs, code-quality changes
*   Stefan Bischof (bipolis.org)
*/

package org.eclipse.daanse.db.statistics.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.statistics.api.StatisticsProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link StatisticsProvider} that uses JDBC metadata calls to
 * count rows and distinct values.
 */
@Component(service = StatisticsProvider.class, scope = ServiceScope.PROTOTYPE)
public class JdbcStatisticsProvider implements StatisticsProvider {

    private static final int NONUNIQUE_COLUMN = 4;
    private static final int TYPE_COLUMN = 7;
    private static final int COLUMN_NAME = 9;
    private static final int CARDINALITY_COLUMN = 11;
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcStatisticsProvider.class);
    private DataSource dataSource;

    @Override
    public void initialize(DataSource dataSource, Dialect dialect) {
        this.dataSource = dataSource;
    }

    @Override
    public long getTableCardinality(String catalog, String schema, String table) {
        try (Connection connection = dataSource.getConnection();
                ResultSet resultSet = connection.getMetaData().getIndexInfo(catalog, schema, table, false, true);) {
            long maxNonUnique = CARDINALITY_UNKNOWN;
            while (resultSet.next()) {
                final int type = resultSet.getInt(TYPE_COLUMN);
                final int cardinality = resultSet.getInt(CARDINALITY_COLUMN);
                final boolean unique = !resultSet.getBoolean(NONUNIQUE_COLUMN);
                if (type == DatabaseMetaData.tableIndexStatistic) {
                    return cardinality;
                }
                if (!unique) {
                    maxNonUnique = Math.max(maxNonUnique, cardinality);
                }
            }
            // The cardinality of each non-unique index will be the number of
            // non-NULL values in that index. Unless we're unlucky, one of those
            // columns will cover most of the table.
            return maxNonUnique;
        } catch (SQLException e) {

            LOGGER.debug("JdbcStatisticsProvider failed to get the cardinality of the table " + table, e);

            return CARDINALITY_UNKNOWN;
        }
    }

    @Override
    public long getQueryCardinality(String sql) {
        // JDBC cannot help with this. Defer to another statistics provider.
        return CARDINALITY_UNKNOWN;
    }

    @Override
    public long getColumnCardinality(String catalog, String schema, String table, String column) {

        try (Connection connection = dataSource.getConnection();
                ResultSet resultSet = connection.getMetaData().getIndexInfo(catalog, schema, table, false, true);) {

            while (resultSet.next()) {
                int type = resultSet.getInt(TYPE_COLUMN);
                if (type != DatabaseMetaData.tableIndexStatistic) {
                    String columnName = resultSet.getString(COLUMN_NAME);
                    if (columnName != null && columnName.equals(column)) {
                        return resultSet.getInt(CARDINALITY_COLUMN);
                    }
                }
            }
            return CARDINALITY_UNKNOWN;
        } catch (SQLException e) {

            LOGGER.debug("JdbcStatisticsProvider failed to get the cardinality of the tablColumn " + table, e);

            return CARDINALITY_UNKNOWN;
        }
    }

}
