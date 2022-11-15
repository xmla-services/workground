package org.eclipse.daanse.db.dialect.db.clickhouse;

import aQute.bnd.annotation.spi.ServiceProvider;
import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.JdbcDialectImpl;
import org.eclipse.daanse.db.dialect.db.common.Util;
import org.eclipse.daanse.db.dialect.db.common.factory.JdbcDialectFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Implementation of {@link Dialect} for ClickHouse
 */
@ServiceProvider(value = Dialect.class, attribute = { "database.dialect.type:String='CLICKHOUSE'",
    "database.product:String='CLICKHOUSE'" })
public class ClickHouseDialect extends JdbcDialectImpl {

    public static final JdbcDialectFactory FACTORY =
        new JdbcDialectFactory(
            ClickHouseDialect.class);

    /**
     * Creates a Db2OldAs400Dialect.
     *
     * @param connection Connection
     */
    public ClickHouseDialect(Connection connection) throws SQLException {
        super(connection);
    }

    public boolean requiresDrillthroughMaxRowsInLimit() {
        return true;
    }

    public void quoteStringLiteral(
        StringBuilder buf,
        String s)
    {
        buf.append('\'');

        String s0 = Util.replace(s, "\\", "\\\\");
        s0 = Util.replace(s0, "'", "\\'");
        buf.append(s0);

        buf.append('\'');
    }
}
