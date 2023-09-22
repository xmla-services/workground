/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.eclipse.daanse.db.dialect.db.informix;

import java.sql.Connection;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.JdbcDialectImpl;

/**
 * Implementation of {@link Dialect} for the Informix database.
 *
 * @author jhyde
 * @since Nov 23, 2008
 */
public class InformixDialect extends JdbcDialectImpl {

    private static final String SUPPORTED_PRODUCT_NAME = "INFORMIX";

    public InformixDialect(Connection connection) {
        super(connection);
    }

    @Override
    public boolean allowsFromQuery() {
        return false;
    }

    @Override
    public StringBuilder generateOrderByNulls(
        CharSequence expr,
        boolean ascending,
        boolean collateNullsLast)
    {
        return generateOrderByNullsAnsi(expr, ascending, collateNullsLast);
    }

    @Override
    public boolean supportsGroupByExpressions() {
        return false;
    }

    @Override
    public String getDialectName() {
        return SUPPORTED_PRODUCT_NAME.toLowerCase();
    }
}
