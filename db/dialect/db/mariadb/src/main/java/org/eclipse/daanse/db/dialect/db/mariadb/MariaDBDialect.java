/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2016 - 2017 Hitachi Vantara
// All Rights Reserved.
*/

package org.eclipse.daanse.db.dialect.db.mariadb;

import org.eclipse.daanse.db.dialect.db.mysql.MySqlDialect;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

public class MariaDBDialect extends MySqlDialect {

    private static final String SUPPORTED_PRODUCT_NAME = "MARIADB";

    public MariaDBDialect(Connection connection) {
        super(connection);
    }

    @Override
    protected String deduceProductName(DatabaseMetaData databaseMetaData) {
        // It is possible for someone to use the MariaDB JDBC driver with Infobright . .
        // .
        final String productName = super.deduceProductName(databaseMetaData);
        if (isInfobright(databaseMetaData)) {
            return "MySQL (Infobright)";
        }
        return productName;
    }

    @Override
    public String getDialectName() {
        return SUPPORTED_PRODUCT_NAME.toLowerCase();
    }
}
