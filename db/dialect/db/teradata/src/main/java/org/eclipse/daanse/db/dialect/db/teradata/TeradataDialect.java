/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.eclipse.daanse.db.dialect.db.teradata;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.JdbcDialectImpl;

import java.sql.Connection;
import java.util.List;

/**
 * Implementation of {@link Dialect} for the Teradata database.
 *
 * @author jhyde
 * @since Nov 23, 2008
 */
public class TeradataDialect extends JdbcDialectImpl {

    private static final String SUPPORTED_PRODUCT_NAME = "TERADATA";

    public TeradataDialect(Connection connection) {
        super(connection);
    }

    @Override
    public boolean requiresAliasForFromQuery() {
        return true;
    }

    @Override
    public StringBuilder generateInline(List<String> columnNames, List<String> columnTypes, List<String[]> valueList) {
        String fromClause = null;
        if (valueList.size() > 1) {
            // In Teradata, "SELECT 1,2" is valid but "SELECT 1,2 UNION
            // SELECT 3,4" gives "3888: SELECT for a UNION,INTERSECT or
            // MINUS must reference a table."
            fromClause = " FROM (SELECT 1 a) z ";
        }
        return generateInlineGeneric(columnNames, columnTypes, valueList, fromClause, true);
    }

    @Override
    public boolean supportsGroupingSets() {
        return true;
    }

    @Override
    public boolean requiresUnionOrderByOrdinal() {
        return true;
    }

    @Override
    public String getDialectName() {
        return SUPPORTED_PRODUCT_NAME.toLowerCase();
    }
}
