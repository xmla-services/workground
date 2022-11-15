package org.eclipse.daanse.db.dialect.db.db2;

import aQute.bnd.annotation.spi.ServiceProvider;
import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.factory.JdbcDialectFactory;

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
public class Db2OldAs400Dialect extends Db2Dialect {

    public static final JdbcDialectFactory FACTORY =
        new JdbcDialectFactory(
            Db2OldAs400Dialect.class);

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
