/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.eclipse.daanse.db.dialect.db.ingres;

import java.sql.Connection;
import java.util.List;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.JdbcDialectImpl;

/**
 * Implementation of {@link Dialect} for the Ingres database.
 *
 * @author jhyde
 * @since Nov 23, 2008
 */
public class IngresDialect extends JdbcDialectImpl {

    private static final String SUPPORTED_PRODUCT_NAME = "INGRES";

    public IngresDialect(Connection connection) {
        super(connection);
    }

    @Override
    public StringBuilder generateInline(List<String> columnNames, List<String> columnTypes, List<String[]> valueList) {
        return generateInlineGeneric(columnNames, columnTypes, valueList, null, false);
    }

    @Override
    public boolean requiresOrderByAlias() {
        return true;
    }

    @Override
    public String getDialectName() {
        return SUPPORTED_PRODUCT_NAME.toLowerCase();
    }
}
