/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.eclipse.daanse.db.dialect.db.firebird;

import java.sql.Connection;
import java.sql.SQLException;

import aQute.bnd.annotation.spi.ServiceProvider;
import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.JdbcDialectImpl;
import org.eclipse.daanse.db.dialect.db.common.factory.JdbcDialectFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * Implementation of {@link Dialect} for the Firebird database.
 *
 * @author jhyde
 * @since Nov 23, 2008
 */
@ServiceProvider(value = Dialect.class, attribute = { "database.dialect.type:String='FIREBIRD'",
		"database.product:String='FIREBIRD'" })
@Component(service = Dialect.class, scope = ServiceScope.SINGLETON)
public class FirebirdDialect extends JdbcDialectImpl {

    public static final JdbcDialectFactory FACTORY =
        new JdbcDialectFactory(
            FirebirdDialect.class);

    public FirebirdDialect() {
    }
    /**
     * Creates a FirebirdDialect.
     *
     * @param connection Connection
     */
    public FirebirdDialect(Connection connection) throws SQLException {
        super(connection);
    }

    public boolean allowsAs() {
        return false;
    }

    @Override
    public String generateOrderByNulls(
        String expr,
        boolean ascending,
        boolean collateNullsLast)
    {
        return generateOrderByNullsAnsi(expr, ascending, collateNullsLast);
    }
}

// End FirebirdDialect.java
