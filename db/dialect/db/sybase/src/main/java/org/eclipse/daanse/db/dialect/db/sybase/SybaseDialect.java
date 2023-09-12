/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (c) 2002-2017 Hitachi Vantara.
// All rights reserved.
*/
package org.eclipse.daanse.db.dialect.db.sybase;

import java.sql.Connection;
import java.sql.Date;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.JdbcDialectImpl;
import org.eclipse.daanse.db.dialect.db.common.Util;

/**
 * Implementation of {@link Dialect} for the Sybase database.
 *
 * @author jhyde
 * @since Nov 23, 2008
 */
public class SybaseDialect extends JdbcDialectImpl {

    private static final String SUPPORTED_PRODUCT_NAME = "SQLSTREAM";

    public SybaseDialect(Connection connection) {
        super(connection);
    }

    @Override
    public boolean allowsAs() {
        return false;
    }

    @Override
    public boolean allowsFromQuery() {
        return false;
    }

    @Override
    public boolean requiresAliasForFromQuery() {
        return true;
    }

    @Override
    protected void quoteDateLiteral(StringBuilder buf, Date date)
    {
        Util.singleQuoteString(date.toString(), buf);
    }

    @Override
    public String getDialectName() {
        return SUPPORTED_PRODUCT_NAME.toLowerCase();
    }
}
