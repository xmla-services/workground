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
import java.sql.SQLException;
import java.util.List;

import aQute.bnd.annotation.spi.ServiceProvider;
import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.JdbcDialectImpl;
import org.eclipse.daanse.db.dialect.db.common.factory.JdbcDialectFactory;

/**
 * Implementation of {@link Dialect} for the Neoview database.
 *
 * @author jhyde
 * @since Dec 4, 2009
 */
@ServiceProvider(value = Dialect.class, attribute = { "database.dialect.type:String='NEOVIEW'",
		"database.product:String='NEOVIEW'" })
public class NeoviewDialect extends JdbcDialectImpl {

    public static final JdbcDialectFactory FACTORY =
        new JdbcDialectFactory(
            NeoviewDialect.class);

    /**
     * Creates a NeoviewDialect.
     *
     * @param connection Connection
     */
    public NeoviewDialect(Connection connection) throws SQLException {
        super(connection);
    }

    public boolean _supportsOrderByNullsLast() {
        return true;
    }

    public boolean requiresOrderByAlias() {
        return true;
    }

    public boolean requiresAliasForFromQuery() {
        return true;
    }

    public boolean allowsDdl() {
        // We get the following error in the test environment. It might be a bit
        // pessimistic to say DDL is never allowed.
        //
        // ERROR[1116] The current partitioning scheme requires a user-specified
        // clustering key on object NEO.PENTAHO."foo"
        return false;
    }

    public boolean supportsGroupByExpressions() {
        return false;
    }

    public String generateInline(
        List<String> columnNames,
        List<String> columnTypes,
        List<String[]> valueList)
    {
        return generateInlineForAnsi(
            "t", columnNames, columnTypes, valueList, true);
    }
}

// End NeoviewDialect.java