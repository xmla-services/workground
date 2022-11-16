package org.eclipse.daanse.db.dialect.db.clickhouse;

import aQute.bnd.annotation.spi.ServiceProvider;
import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.JdbcDialectImpl;
import org.eclipse.daanse.db.dialect.db.common.Util;
import org.eclipse.daanse.db.dialect.db.common.factory.JdbcDialectFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Implementation of {@link Dialect} for ClickHouse
 */
@ServiceProvider(value = Dialect.class, attribute = { "database.dialect.type:String='CLICKHOUSE'",
    "database.product:String='CLICKHOUSE'" })
@Component(service = Dialect.class, scope = ServiceScope.SINGLETON)
public class ClickHouseDialect extends JdbcDialectImpl {


    public static final JdbcDialectFactory FACTORY =
        new JdbcDialectFactory(
            ClickHouseDialect.class);

    public ClickHouseDialect() {
    }
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

        String s0 = s.replace("\\", "\\\\");
        s0 = s0.replace("'", "\\'");
        buf.append(s0);

        buf.append('\'');
    }
}
