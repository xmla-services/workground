/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (C) 2012-2017 Hitachi Vantara and others
* All Rights Reserved.
*
* Contributors:
*   SmartCity Jena, Stefan Bischof - make OSGi Component
*/
package org.eclipse.daanse.db.dialect.db.access;

import java.sql.Connection;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.JdbcDialectImpl;

/**
 * Implementation of {@link Dialect} for the Microsoft Access
 * database (also called the JET Engine).
 *
 * @author jhyde
 * @since Nov 23, 2008
 */
public class AccessDialect extends JdbcDialectImpl {

    private static final String SUPPORTED_PRODUCT_NAME = "ACCESS";

    public AccessDialect(Connection connection) {
        super(connection);
    }

    @Override
    public StringBuilder toUpper(CharSequence expr) {
        return new StringBuilder("UCASE(").append(expr).append(")");
    }

    @Override
    public StringBuilder caseWhenElse(CharSequence cond, CharSequence thenExpr, CharSequence elseExpr) {
        return new StringBuilder("IIF(").append(cond)
            .append(",").append(thenExpr).append(",").append(elseExpr).append(")");
    }

    @Override
    protected void quoteDateLiteral(StringBuilder buf, Date date) {
        // Access accepts #01/23/2008# but not SQL:2003 format.
        buf.append("#");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        buf.append(calendar.get(Calendar.MONTH) + 1);
        buf.append("/");
        buf.append(calendar.get(Calendar.DAY_OF_MONTH));
        buf.append("/");
        buf.append(calendar.get(Calendar.YEAR));
        buf.append("#");
    }

    @Override
    protected StringBuilder generateOrderByNulls(CharSequence expr, boolean ascending, boolean collateNullsLast) {
        if (collateNullsLast) {
            if (ascending) {
                return new StringBuilder("Iif(").append(expr)
                    .append(" IS NULL, 1, 0), ").append(expr).append(" ASC");
            } else {
                return new StringBuilder("Iif(").append(expr)
                    .append(" IS NULL, 1, 0), ").append(expr).append(" DESC");
            }
        } else {
            if (ascending) {
                return new StringBuilder("Iif(").append(expr)
                    .append(" IS NULL, 0, 1), ").append(expr).append(" ASC");
            } else {
                return new StringBuilder("Iif(").append(expr)
                    .append(" IS NULL, 0, 1), ").append(expr).append(" DESC");
            }
        }
    }

    @Override
    public boolean requiresUnionOrderByExprToBeInSelectClause() {
        return true;
    }

    @Override
    public boolean allowsCountDistinct() {
        return false;
    }

    @Override
    public StringBuilder generateInline(List<String> columnNames, List<String> columnTypes, List<String[]> valueList) {
        // Fall back to using the FoodMart 'days' table, because
        // Access SQL has no way to generate values not from a table.
        return generateInlineGeneric(columnNames, columnTypes, valueList, " from `days` where `day` = 1", false);
    }

    @Override
    public String getDialectName() {
        return SUPPORTED_PRODUCT_NAME.toLowerCase();
    }

}
