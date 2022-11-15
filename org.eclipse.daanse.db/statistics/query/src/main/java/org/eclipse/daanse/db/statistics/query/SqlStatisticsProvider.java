/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.eclipse.daanse.db.statistics.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.statistics.api.StatisticsProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link StatisticsProvider} that generates SQL queries to
 * count rows and distinct values.
 */
@Component(service = StatisticsProvider.class, scope = ServiceScope.PROTOTYPE)
public class SqlStatisticsProvider implements StatisticsProvider {
    Logger LOGGER = LoggerFactory.getLogger(SqlStatisticsProvider.class);

    private DataSource dataSource;
    private Dialect dialect;

    @Override
    public void init(DataSource dataSource, Dialect dialect) {
        this.dataSource = dataSource;
        this.dialect = dialect;
    }

    public long getTableCardinality(String catalog, String schema, String table) {
        StringBuilder stringBuilder = new StringBuilder("select count(*) from ");
        dialect.quoteIdentifier(stringBuilder, catalog, schema, table);
        final String sql = stringBuilder.toString();
        return query(sql);
    }

    public long getQueryCardinality(String sql) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select count(*) from (").append(sql).append(")");
        if (dialect.requiresAliasForFromQuery()) {
            if (dialect.allowsAs()) {
                stringBuilder.append(" as ");
            } else {
                stringBuilder.append(" ");
            }
            dialect.quoteIdentifier(stringBuilder, "init");
        }
        final String countSql = stringBuilder.toString();
        return query(countSql);
    }

    public long getColumnCardinality(String catalog, String schema, String table, String column) {
        final String sql = generateColumnCardinalitySql(dialect, schema, table, column);
        if (sql == null) {
            return -1;
        }
        return query(sql);
    }

    private long query(final String sql) {
        try (Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String generateColumnCardinalitySql(Dialect dialect, String schema, String table, String column) {
        final StringBuilder buf = new StringBuilder();
        String exprString = dialect.quoteIdentifier(column);
        if (dialect.allowsCountDistinct()) {
            // e.g. "select count(distinct product_id) from product"
            buf.append("select count(distinct ").append(exprString).append(") from ");
            dialect.quoteIdentifier(buf, schema, table);
            return buf.toString();
        } else if (dialect.allowsFromQuery()) {
            // Some databases (e.g. Access) don't like 'count(distinct)',
            // so use, e.g., "select count(*) from (select distinct
            // product_id from product)"
            buf.append("select count(*) from (select distinct ").append(exprString).append(" from ");
            dialect.quoteIdentifier(buf, schema, table);
            buf.append(")");
            if (dialect.requiresAliasForFromQuery()) {
                if (dialect.allowsAs()) {
                    buf.append(" as ");
                } else {
                    buf.append(' ');
                }
                dialect.quoteIdentifier(buf, "init");
            }
            return buf.toString();
        } else {
            // Cannot compute cardinality: this database neither supports COUNT
            // DISTINCT nor SELECT in the FROM clause.
            return null;
        }
    }

}
