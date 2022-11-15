/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.eclipse.daanse.db.dialect.db.luciddb;

import java.sql.Connection;
import java.sql.SQLException;

import aQute.bnd.annotation.spi.ServiceProvider;
import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.JdbcDialectImpl;
import org.eclipse.daanse.db.dialect.db.common.factory.JdbcDialectFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * Implementation of {@link Dialect} for the LucidDB database.
 *
 * @author jhyde
 * @since Nov 23, 2008
 */
@ServiceProvider(value = Dialect.class, attribute = { "database.dialect.type:String='LUCIDDB'",
		"database.product:String='LUCIDDB'" })
@Component(service = Dialect.class, scope = ServiceScope.SINGLETON)
public class LucidDbDialect extends JdbcDialectImpl {

    public static final JdbcDialectFactory FACTORY =
        new JdbcDialectFactory(
            LucidDbDialect.class);

    public LucidDbDialect() {
    }
    /**
     * Creates a LucidDbDialect.
     *
     * @param connection Connection
     *
     * @throws java.sql.SQLException on error
     */
    public LucidDbDialect(Connection connection) throws SQLException {
        super(connection);
    }

    public boolean allowsMultipleDistinctSqlMeasures() {
        return false;
    }

    public boolean needsExponent(Object value, String valueString) {
        return value instanceof Double && !valueString.contains("E");
    }

    public boolean supportsUnlimitedValueList() {
        return true;
    }

    public boolean supportsMultiValueInExpr() {
        return true;
    }
}

// End LucidDbDialect.java
