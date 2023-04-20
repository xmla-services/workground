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

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.postgresql.PostgreSqlDialect;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import aQute.bnd.annotation.spi.ServiceProvider;

/**
 * Implementation of {@link Dialect} for the GreenplumSQL database.
 *
 * @author Millersoft
 * @since Dec 23, 2009
 */
@ServiceProvider(value = Dialect.class, attribute = { "database.dialect.type:String='POSTGRESQL'",
        "database.product:String='GREENPLUM'" })
@Component(service = Dialect.class, scope = ServiceScope.PROTOTYPE)
public class GreenplumDialect extends PostgreSqlDialect {

    private static final String SUPPORTED_PRODUCT_NAME = "GREENPLUM";

    @Override
    public boolean initialize(Connection connection) {

        return super.initialize(connection) && isDatabase(SUPPORTED_PRODUCT_NAME, connection);
    }

    @Override
    protected boolean isSupportedProduct(String productName, String productVersion) {
        return SUPPORTED_PRODUCT_NAME.equalsIgnoreCase(productVersion);
    }

    @Override
    public boolean supportsGroupingSets() {
        return true;
    }

    @Override
    public boolean requiresGroupByAlias() {
        return true;
    }

    @Override
    public boolean requiresAliasForFromQuery() {
        return true;
    }

    @Override
    public boolean allowsCountDistinct() {
        return true;
    }

    @Override
    public StringBuilder generateCountExpression(CharSequence exp) {
        return caseWhenElse(
            new StringBuilder(exp).append(" ISNULL"),
            "'0'",
            new StringBuilder("TEXT(").append(exp).append(")"));
    }

    @Override
    public boolean allowsRegularExpressionInWhereClause() {
        // Support for regexp was added in GP 3.2+
        return productVersion.compareTo("3.2") >= 0;
    }

    @Override
    public boolean allowsInnerDistinct() {
        return false;
    }

    @Override
    public String getDialectName() {
        return SUPPORTED_PRODUCT_NAME.toLowerCase();
    }

}
