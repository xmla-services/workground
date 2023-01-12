/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2011-2017 Hitachi Vantara and others
// All Rights Reserved.
*/
package org.eclipse.daanse.db.dialect.db.hive;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.daanse.db.dialect.api.DatabaseProduct;
import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.JdbcDialectImpl;
import org.eclipse.daanse.db.dialect.db.common.Util;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import aQute.bnd.annotation.spi.ServiceProvider;

/**
 * Implementation of {@link Dialect} for the Hive database.
 *
 * @author Hongwei Fu
 * @since Jan 10, 2011
 */
@ServiceProvider(value = Dialect.class, attribute = { "database.dialect.type:String='HIVE'",
        "database.product:String='HIVE'" })
@Component(service = Dialect.class, scope = ServiceScope.PROTOTYPE)
public class HiveDialect extends JdbcDialectImpl {
    private static final int MAX_COLUMN_NAME_LENGTH = 128;

    private static final String SUPPORTED_PRODUCT_NAME = "HIVE";

    @Override
    protected boolean isSupportedProduct(String productName, String productVersion) {
        return SUPPORTED_PRODUCT_NAME.equalsIgnoreCase(productVersion);
    }

    @Override
    public boolean initialize(Connection connection) {
        return super.initialize(connection) && !isDatabase(DatabaseProduct.IMPALA, connection);
    }

    protected String deduceIdentifierQuoteString(DatabaseMetaData databaseMetaData) {
        return null;
    }

    protected Set<List<Integer>> deduceSupportedResultSetStyles(DatabaseMetaData databaseMetaData) {
        // Hive don't support this, so just return an empty set.
        return Collections.emptySet();
    }

    protected boolean deduceReadOnly(DatabaseMetaData databaseMetaData) {
        try {
            return databaseMetaData.isReadOnly();
        } catch (SQLException e) {
            // Hive is read only (as of release 0.7)
            return true;
        }
    }

    protected int deduceMaxColumnNameLength(DatabaseMetaData databaseMetaData) {
        try {
            return databaseMetaData.getMaxColumnNameLength();
        } catch (SQLException e) {
            return MAX_COLUMN_NAME_LENGTH;
        }
    }

    public boolean allowsCompoundCountDistinct() {
        return true;
    }

    public boolean requiresAliasForFromQuery() {
        return true;
    }

    @Override
    public boolean requiresOrderByAlias() {
        return true;
    }

    @Override
    public boolean allowsOrderByAlias() {
        return true;
    }

    @Override
    public boolean requiresGroupByAlias() {
        return false;
    }

    public boolean requiresUnionOrderByExprToBeInSelectClause() {
        return false;
    }

    public boolean requiresUnionOrderByOrdinal() {
        return false;
    }

    public String generateInline(List<String> columnNames, List<String> columnTypes, List<String[]> valueList) {
        return new StringBuilder("select * from (")
            .append(generateInlineGeneric(columnNames, columnTypes, valueList, " from dual", false))
            .append(") x limit ").append(valueList.size()).toString();
    }

    protected void quoteDateLiteral(StringBuilder buf, String value, Date date) {
        // Hive doesn't support Date type; treat date as a string '2008-01-23'
        Util.singleQuoteString(value, buf);
    }

    @Override
    protected String generateOrderByNulls(String expr, boolean ascending, boolean collateNullsLast) {
        // In Hive, Null values are worth negative infinity.
        if (collateNullsLast) {
            if (ascending) {
                return new StringBuilder("ISNULL(").append(expr)
                    .append(") ASC, ").append(expr).append(" ASC").toString();
            } else {
                return new StringBuilder(expr).append(" DESC").toString();
            }
        } else {
            if (ascending) {
                return new StringBuilder(expr).append(" ASC").toString();
            } else {
                return new StringBuilder("ISNULL(").append(expr)
                    .append(") DESC, ").append(expr).append(" DESC").toString();
            }
        }
    }

    public boolean allowsAs() {
        return false;
    }

    public boolean allowsJoinOn() {
        return false;
    }

    public void quoteTimestampLiteral(StringBuilder buf, String value) {
        try {
            Timestamp.valueOf(value);
        } catch (IllegalArgumentException ex) {
            throw new NumberFormatException("Illegal TIMESTAMP literal:  " + value);
        }
        buf.append("cast( ");
        Util.singleQuoteString(value, buf);
        buf.append(" as timestamp )");
    }
}
