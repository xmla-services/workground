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

import java.sql.*;
import java.sql.Date;
import java.util.*;

import aQute.bnd.annotation.spi.ServiceProvider;
import org.eclipse.daanse.db.dialect.api.DatabaseProduct;
import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.JdbcDialectImpl;
import org.eclipse.daanse.db.dialect.db.common.Util;
import org.eclipse.daanse.db.dialect.db.common.factory.JdbcDialectFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * Implementation of {@link Dialect} for the Hive database.
 *
 * @author Hongwei Fu
 * @since Jan 10, 2011
 */
@ServiceProvider(value = Dialect.class, attribute = { "database.dialect.type:String='HIVE'",
		"database.product:String='HIVE'" })
@Component(service = Dialect.class, scope = ServiceScope.SINGLETON)
public class HiveDialect extends JdbcDialectImpl {
    private static final int MAX_COLUMN_NAME_LENGTH = 128;

    public HiveDialect() {
    }
    public static final JdbcDialectFactory FACTORY =
        new JdbcDialectFactory(
            HiveDialect.class)
        {
            protected boolean acceptsConnection(Connection connection) {
                return super.acceptsConnection(connection)
                    && !isDatabase(DatabaseProduct.IMPALA, connection);
            }
        };

    /**
     * Creates a HiveDialect.
     *
     * @param connection Connection
     *
     * @throws SQLException on error
     */
    public HiveDialect(Connection connection) throws SQLException {
        super(connection);
    }

    protected String deduceIdentifierQuoteString(
        DatabaseMetaData databaseMetaData)
    {
        return null;
    }

    protected Set<List<Integer>> deduceSupportedResultSetStyles(
        DatabaseMetaData databaseMetaData)
    {
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

    public String generateInline(
        List<String> columnNames,
        List<String> columnTypes,
        List<String[]> valueList)
    {
        return "select * from ("
            + generateInlineGeneric(
                columnNames, columnTypes, valueList, " from dual", false)
            + ") x limit " + valueList.size();
    }

    protected void quoteDateLiteral(
        StringBuilder buf,
        String value,
        Date date)
    {
        // Hive doesn't support Date type; treat date as a string '2008-01-23'
        Util.singleQuoteString(value, buf);
    }

    @Override
    protected String generateOrderByNulls(
        String expr,
        boolean ascending,
        boolean collateNullsLast)
    {
        // In Hive, Null values are worth negative infinity.
        if (collateNullsLast) {
            if (ascending) {
                return "ISNULL(" + expr + ") ASC, " + expr + " ASC";
            } else {
                return expr + " DESC";
            }
        } else {
            if (ascending) {
                return expr + " ASC";
            } else {
                return "ISNULL(" + expr + ") DESC, " + expr + " DESC";
            }
        }
    }

    public boolean allowsAs() {
        return false;
    }

    public boolean allowsJoinOn() {
        return false;
    }

    public void quoteTimestampLiteral(
        StringBuilder buf,
        String value)
    {
        try {
            Timestamp.valueOf(value);
        } catch (IllegalArgumentException ex) {
            throw new NumberFormatException(
                "Illegal TIMESTAMP literal:  " + value);
        }
        buf.append("cast( ");
        Util.singleQuoteString(value, buf);
        buf.append(" as timestamp )");
    }
}

// End HiveDialect.java