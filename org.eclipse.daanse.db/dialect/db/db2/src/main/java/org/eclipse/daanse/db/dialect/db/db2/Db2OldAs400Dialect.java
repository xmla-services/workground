/*********************************************************************
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * History:
 *  This files came from the mondrian project. Some of the Flies
 *  (mostly the Tests) did not have License Header.
 *  But the Project is EPL Header. 2002-2022 Hitachi Vantara.
 *
 * Contributors:
 *   Hitachi Vantara.
 *   SmartCity Jena - initial  Java 8, Junit5
 **********************************************************************/
package org.eclipse.daanse.db.dialect.db.db2;

import aQute.bnd.annotation.spi.ServiceProvider;
import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.factory.JdbcDialectFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Implementation of {@link Dialect} for old versions of the IBM
 * DB2/AS400 database. Modern versions of DB2/AS400 use
 * {@link Db2Dialect}.
 *
 * @see Db2Dialect
 *
 * @author jhyde
 * @since Nov 23, 2008
 */
@ServiceProvider(value = Dialect.class, attribute = { "database.dialect.type:String='DB2_OLD_AS400'",
    "database.product:String='DB2_OLD_AS400'" })
@Component(service = Dialect.class, scope = ServiceScope.SINGLETON)
public class Db2OldAs400Dialect extends Db2Dialect {
    public static final JdbcDialectFactory FACTORY =
        new JdbcDialectFactory(
            Db2OldAs400Dialect.class);

    public Db2OldAs400Dialect() {
    }
    /**
     * Creates a Db2OldAs400Dialect.
     *
     * @param connection Connection
     */
    public Db2OldAs400Dialect(Connection connection) throws SQLException {
        super(connection);
    }

    public boolean allowsFromQuery() {
        // Older versions of AS400 do not allow FROM
        // subqueries in the FROM clause.
        return false;
    }
}

// End Db2OldAs400Dialect.java
