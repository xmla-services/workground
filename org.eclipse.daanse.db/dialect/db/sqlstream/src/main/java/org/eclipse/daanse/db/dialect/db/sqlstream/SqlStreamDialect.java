/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.eclipse.daanse.db.dialect.db.sqlstream;

import java.sql.Connection;
import java.sql.SQLException;

import aQute.bnd.annotation.spi.ServiceProvider;
import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.factory.JdbcDialectFactory;
import org.eclipse.daanse.db.dialect.db.luciddb.LucidDbDialect;

/**
 * Implementation of {@link Dialect} for the SQLstream streaming
 * SQL system.
 *
 * @author jhyde
 * @since Mar 23, 2009
 */
@ServiceProvider(value = Dialect.class, attribute = { "database.dialect.type:String='SQLSTREAM'",
		"database.product:String='SQLSTREAM'" })
public class SqlStreamDialect extends LucidDbDialect {

    public static final JdbcDialectFactory FACTORY =
        new JdbcDialectFactory(
            SqlStreamDialect.class);

    /**
     * Creates a SqlStreamDialect.
     *
     * @param connection Connection
     */
    public SqlStreamDialect(Connection connection) throws SQLException {
        super(connection);
    }
}

// End SqlStreamDialect.java
