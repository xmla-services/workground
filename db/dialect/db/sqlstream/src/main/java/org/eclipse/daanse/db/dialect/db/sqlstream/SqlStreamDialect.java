/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.eclipse.daanse.db.dialect.db.sqlstream;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.luciddb.LucidDbDialect;

import java.sql.Connection;

/**
 * Implementation of {@link Dialect} for the SQLstream streaming SQL system.
 *
 * @author jhyde
 * @since Mar 23, 2009
 */
public class SqlStreamDialect extends LucidDbDialect {

    private static final String SUPPORTED_PRODUCT_NAME = "SQLSTREAM";

    public SqlStreamDialect(Connection connection) {
        super(connection);
    }

    @Override
    public String getDialectName() {
        return SUPPORTED_PRODUCT_NAME.toLowerCase();
    }
}
