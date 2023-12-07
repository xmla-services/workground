/*
* Copyright (c) 2022 Contributors to the Eclipse Foundation.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*   SmartCity Jena - initial
*   Stefan Bischof (bipolis.org) - initial
*/
package org.eclipse.daanse.db.dialect.db.h2;

import java.sql.Connection;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.JdbcDialectImpl;

/**
 * Implementation of {@link Dialect} for the H2 database.
 */
public class H2Dialect extends JdbcDialectImpl {

    private static final String SUPPORTED_PRODUCT_NAME = "H2";

    public H2Dialect(Connection connection) {
        super(connection);
    }


    @Override
    public String getDialectName() {
        return SUPPORTED_PRODUCT_NAME.toLowerCase();
    }
}
