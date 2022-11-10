/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.eclipse.daanse.sql.statistics.api;

import javax.sql.DataSource;

/**
 * Provides estimates of the number of rows in a database.
 */
public interface StatisticsProvider {
    /**
     * Returns an estimate of the number of rows in a table.
     *
     * @param dataSource Data source
     * @param catalog Catalog name
     * @param schema Schema name
     * @param table Table name
     *
     * @return Estimated number of rows in table, or -1 if there
     * is no estimate
     */
    long getTableRows(
        DataSource dataSource,
        String catalog,
        String schema,
        String table);

    /**
     * Returns an estimate of the number of rows returned by a query.
     *
     * @param dataSource Data source
     * @param sql Query, e.g. "select * from customers where age < 20"
     *
     * @return Estimated number of rows returned by query, or -1 if there
     * is no estimate
     */
    long getQueryResultRows(
        DataSource dataSource,
        String sql);

    /**
     * Returns the column cardinality.
     *
     * @param dataSource Data source
     * @param catalog Catalog name
     * @param schema Schema name
     * @param table Table name
     * @param column Column name
     *
     * @return column cardinality, or -1 if there
     * is no estimate
     */
    long getColumnCardinality(
        DataSource dataSource,
        String catalog,
        String schema,
        String table,
        String column);
}

