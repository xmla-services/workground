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
import java.sql.SQLException;

import aQute.bnd.annotation.spi.ServiceProvider;
import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.JdbcDialectImpl;
import org.eclipse.daanse.db.dialect.db.common.Util;
import org.eclipse.daanse.db.dialect.db.common.factory.JdbcDialectFactory;

/**
 * Implementation of {@link Dialect} for the Sybase database.
 *
 * @author jhyde
 * @since Nov 23, 2008
 */
@ServiceProvider(value = Dialect.class, attribute = { "database.dialect.type:String='SYBASE'",
		"database.product:String='SYBASE'" })
public class SybaseDialect extends JdbcDialectImpl {

    public static final JdbcDialectFactory FACTORY =
        new JdbcDialectFactory(
            SybaseDialect.class);

    /**
     * Creates a SybaseDialect.
     *
     * @param connection Connection
     */
    public SybaseDialect(Connection connection) throws SQLException {
        super(connection);
    }

    public boolean allowsAs() {
        return false;
    }

    public boolean allowsFromQuery() {
        return false;
    }

    public boolean requiresAliasForFromQuery() {
        return true;
    }

    @Override
    protected void quoteDateLiteral(StringBuilder buf, String value, Date date)
    {
        Util.singleQuoteString(value, buf);
    }
}

// End SybaseDialect.java
