/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.eclipse.daanse.db.dialect.db.neoview;

import java.sql.Connection;
import java.util.List;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.JdbcDialectImpl;

/**
 * Implementation of {@link Dialect} for the Neoview database.
 *
 * @author jhyde
 * @since Dec 4, 2009
 */
public class NeoviewDialect extends JdbcDialectImpl {
    private static final String SUPPORTED_PRODUCT_NAME = "NEOVIEW";

    public NeoviewDialect(Connection connection) {
        super(connection);
    }

    public boolean supportsOrderByNullsLast() {
        return true;
    }

    @Override
    public boolean requiresOrderByAlias() {
        return true;
    }

    @Override
    public boolean requiresAliasForFromQuery() {
        return true;
    }

    @Override
    public boolean allowsDdl() {
        // We get the following error in the test environment. It might be a bit
        // pessimistic to say DDL is never allowed.
        //
        // ERROR[1116] The current partitioning scheme requires a user-specified
        // clustering key on object NEO.PENTAHO."foo"
        return false;
    }

    @Override
    public boolean supportsGroupByExpressions() {
        return false;
    }

    @Override
    public StringBuilder generateInline(List<String> columnNames, List<String> columnTypes, List<String[]> valueList) {
        return generateInlineForAnsi("t", columnNames, columnTypes, valueList, true);
    }

    @Override
    public String getDialectName() {
        return SUPPORTED_PRODUCT_NAME.toLowerCase();
    }
}
