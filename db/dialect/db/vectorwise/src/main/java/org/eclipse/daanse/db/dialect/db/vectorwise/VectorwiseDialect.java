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

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.ingres.IngresDialect;

/**
 * Implementation of {@link Dialect} for the Vertica database.
 *
 * @author LBoudreau
 * @since Sept 11, 2009
 */
public class VectorwiseDialect extends IngresDialect {

    private static final String SUPPORTED_PRODUCT_NAME = "VECTORWISE";

    public VectorwiseDialect(Connection connection) {
        super(connection);
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

    @Override
    public String getDialectName() {
        return SUPPORTED_PRODUCT_NAME.toLowerCase();
    }
}
