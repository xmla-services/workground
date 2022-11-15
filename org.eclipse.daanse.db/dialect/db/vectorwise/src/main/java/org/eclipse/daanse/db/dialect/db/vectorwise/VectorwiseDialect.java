/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/
package org.eclipse.daanse.db.dialect.db.vectorwise;

import java.sql.Connection;
import java.sql.SQLException;

import aQute.bnd.annotation.spi.ServiceProvider;
import org.eclipse.daanse.db.dialect.api.DatabaseProduct;
import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.factory.JdbcDialectFactory;
import org.eclipse.daanse.db.dialect.db.ingres.IngresDialect;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * Implementation of {@link Dialect} for the Vertica database.
 *
 * @author LBoudreau
 * @since Sept 11, 2009
 */
@ServiceProvider(value = Dialect.class, attribute = { "database.dialect.type:String='VECTORWISE'",
		"database.product:String='VECTORWISE'" })
@Component(service = Dialect.class, scope = ServiceScope.SINGLETON)
public class VectorwiseDialect extends IngresDialect {

    public static final JdbcDialectFactory FACTORY =
        new JdbcDialectFactory(
            VectorwiseDialect.class);

    public VectorwiseDialect() {
    }
    /**
     * Creates a VectorwiseDialect.
     *
     * @param connection Connection
     */
    public VectorwiseDialect(Connection connection) throws SQLException {
        super(connection);
    }

    @Override
    public DatabaseProduct getDatabaseProduct() {
        return DatabaseProduct.VECTORWISE;
    }

    @Override
    public boolean supportsResultSetConcurrency(int type, int concurrency) {
        return false;
    }

    @Override
    public boolean requiresHavingAlias() {
        return true;
    }

    @Override
    public boolean requiresAliasForFromQuery() {
        return true;
    }

    @Override
    public boolean requiresUnionOrderByOrdinal() {
        return false;
    }
}

// End VectorwiseDialect.java
