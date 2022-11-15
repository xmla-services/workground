/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2008-2009 Millersoft
// Copyright (C) 2011-2017 Hitachi Vantara and others
// All Rights Reserved.
*/

package org.eclipse.daanse.db.dialect.db.greenplum;

import java.sql.Connection;
import java.sql.SQLException;

import aQute.bnd.annotation.spi.ServiceProvider;
import org.eclipse.daanse.db.dialect.api.DatabaseProduct;
import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.factory.JdbcDialectFactory;
import org.eclipse.daanse.db.dialect.db.postgresql.PostgreSqlDialect;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * Implementation of {@link Dialect} for the GreenplumSQL database.
 *
 * @author Millersoft
 * @since Dec 23, 2009
 */
@ServiceProvider(value = Dialect.class, attribute = { "database.dialect.type:String='POSTGRESQL'",
		"database.product:String='GREENPLUM'" })
@Component(service = Dialect.class, scope = ServiceScope.SINGLETON)
public class GreenplumDialect extends PostgreSqlDialect {

    public GreenplumDialect() {
    }
    /**
     * Creates a GreenplumDialect.
     *
     * @param connection Connection
     */
    public GreenplumDialect(Connection connection) throws SQLException {
        super(connection);
    }


    public static final JdbcDialectFactory FACTORY =
        new JdbcDialectFactory(
            GreenplumDialect.class)
        {
            protected boolean acceptsConnection(Connection connection) {
                return super.acceptsConnection(connection)
                   && isDatabase(DatabaseProduct.GREENPLUM, connection);
            }
        };

    public boolean supportsGroupingSets() {
        return true;
    }

    public boolean requiresGroupByAlias() {
        return true;
    }

    public boolean requiresAliasForFromQuery() {
        return true;
    }

    public boolean allowsCountDistinct() {
        return true;
    }

    public DatabaseProduct getDatabaseProduct() {
        return DatabaseProduct.GREENPLUM;
    }

    public String generateCountExpression(String exp) {
        return caseWhenElse(exp + " ISNULL", "'0'", "TEXT(" + exp + ")");
    }

    public boolean allowsRegularExpressionInWhereClause() {
        // Support for regexp was added in GP 3.2+
        if (productVersion.compareTo("3.2") >= 0) {
            return true;
        } else {
            return false;
        }
    }
}

// End GreenplumDialect.java
