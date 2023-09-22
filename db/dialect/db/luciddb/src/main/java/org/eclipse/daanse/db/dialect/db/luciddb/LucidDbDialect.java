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

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.JdbcDialectImpl;

/**
 * Implementation of {@link Dialect} for the LucidDB database.
 *
 * @author jhyde
 * @since Nov 23, 2008
 */
public class LucidDbDialect extends JdbcDialectImpl {

    private static final String SUPPORTED_PRODUCT_NAME = "LUCIDDB";

    public LucidDbDialect(Connection connection) {
        super(connection);
    }

    @Override
    public boolean allowsMultipleDistinctSqlMeasures() {
        return false;
    }

    @Override
    public boolean needsExponent(Object value, String valueString) {
        return value instanceof Double && !valueString.contains("E");
    }

    @Override
    public boolean supportsUnlimitedValueList() {
        return true;
    }

    @Override
    public boolean supportsMultiValueInExpr() {
        return true;
    }

    @Override
    public String getDialectName() {
        return SUPPORTED_PRODUCT_NAME.toLowerCase();
    }
}
