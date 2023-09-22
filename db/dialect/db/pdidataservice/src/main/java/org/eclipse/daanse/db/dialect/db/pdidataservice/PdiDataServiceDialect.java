/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/
package org.eclipse.daanse.db.dialect.db.pdidataservice;

import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

import org.eclipse.daanse.db.dialect.api.BestFitColumnType;
import org.eclipse.daanse.db.dialect.db.common.JdbcDialectImpl;

public class PdiDataServiceDialect extends JdbcDialectImpl {

    private static final String SUPPORTED_PRODUCT_NAME = "PDI";

    public PdiDataServiceDialect(Connection connection) {
        super(connection);
    }

    @Override
    public BestFitColumnType getType(ResultSetMetaData metaData, int columnIndex) throws SQLException {
        int type = metaData.getColumnType(columnIndex + 1);
        if (type == Types.DECIMAL) {
            return BestFitColumnType.OBJECT;
        } else {
            return super.getType(metaData, columnIndex);
        }
    }

    @Override
    public String getDialectName() {
        return SUPPORTED_PRODUCT_NAME.toLowerCase();
    }
}
