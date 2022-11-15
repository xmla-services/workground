package org.eclipse.daanse.db.dialect.db.db2;

import aQute.bnd.annotation.spi.ServiceProvider;
import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.JdbcDialectImpl;
import org.eclipse.daanse.db.dialect.db.common.factory.JdbcDialectFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Implementation of {@link Dialect} for the IBM DB2 database.
 *
 * @see Db2OldAs400Dialect
 *
 * @author jhyde
 * @since Nov 23, 2008
 */
@ServiceProvider(value = Dialect.class, attribute = { "database.dialect.type:String='DB2'",
    "database.product:String='DB2'" })
@Component(service = Dialect.class, scope = ServiceScope.SINGLETON)
public class Db2Dialect extends JdbcDialectImpl {

    public Db2Dialect() {
    }
    public static final JdbcDialectFactory FACTORY =
        new JdbcDialectFactory(
            Db2Dialect.class);

    /**
     * Creates a Db2Dialect.
     *
     * @param connection Connection
     */
    public Db2Dialect(Connection connection) throws SQLException {
        super(connection);
    }

    public String toUpper(String expr) {
        return "UCASE(" + expr + ")";
    }

    public boolean supportsGroupingSets() {
        return true;
    }
}

// End Db2Dialect.java

