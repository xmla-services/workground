/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2008-2009 Jaspersoft
// Copyright (C) 2009-2017 Hitachi Vantara
// All Rights Reserved.
*/

package org.eclipse.daanse.db.dialect.db.netezza;

import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

import org.eclipse.daanse.db.dialect.api.BestFitColumnType;
import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.postgresql.PostgreSqlDialect;

/**
 * Implementation of {@link Dialect} for the Netezza database.
 *
 * @author swood
 * @since April 17, 2009
 */
public class NetezzaDialect extends PostgreSqlDialect {

    private static final String SUPPORTED_PRODUCT_NAME = "NETEZZA";

    public NetezzaDialect(Connection connection) {
        super(connection);
    }

    @Override
    public boolean allowsRegularExpressionInWhereClause() {
        return false;
    }

    @Override
    public StringBuilder generateRegularExpression(String source, String javaRegex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public BestFitColumnType getType(ResultSetMetaData metaData, int columnIndex) throws SQLException {
        final int precision = metaData.getPrecision(columnIndex + 1);
        final int scale = metaData.getScale(columnIndex + 1);
        final int columnType = metaData.getColumnType(columnIndex + 1);

        if (columnType == Types.NUMERIC || columnType == Types.DECIMAL && (scale == 0 && precision == 38)) {
            // Netezza marks longs as scale 0 and precision 38.
            // An int would overflow.
            logTypeInfo(metaData, columnIndex, BestFitColumnType.DOUBLE);
            return BestFitColumnType.DOUBLE;
        }
        return super.getType(metaData, columnIndex);
    }

    @Override
    public String getDialectName() {
        return SUPPORTED_PRODUCT_NAME.toLowerCase();
    }
}
