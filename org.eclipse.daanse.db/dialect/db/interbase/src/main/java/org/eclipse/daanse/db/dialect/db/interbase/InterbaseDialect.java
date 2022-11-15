/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.eclipse.daanse.db.dialect.db.interbase;

import java.sql.Connection;
import java.sql.SQLException;

import aQute.bnd.annotation.spi.ServiceProvider;
import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.JdbcDialectImpl;
import org.eclipse.daanse.db.dialect.db.common.factory.JdbcDialectFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * Implementation of {@link Dialect} for the Interbase database.
 *
 * @author jhyde
 * @since Nov 23, 2008
 */
@ServiceProvider(value = Dialect.class, attribute = { "database.dialect.type:String='INTERBASE'",
		"database.product:String='INTERBASE'" })
@Component(service = Dialect.class, scope = ServiceScope.SINGLETON)
public class InterbaseDialect extends JdbcDialectImpl {

    public static final JdbcDialectFactory FACTORY =
        new JdbcDialectFactory(
            InterbaseDialect.class);

    public InterbaseDialect() {
    }
    /**
     * Creates an InterbaseDialect.
     *
     * @param connection Connection
     */
    public InterbaseDialect(Connection connection) throws SQLException {
        super(connection);
    }

    public boolean allowsAs() {
        return false;
    }

    public boolean allowsFromQuery() {
        return false;
    }
}

// End InterbaseDialect.java
