package org.osgi.impl.service.jdbc.sqllite.datasource;

import java.sql.SQLException;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;

import org.osgi.impl.service.jdbc.common.datasource.AbstractDelegateConnectionPoolDataSource;
import org.osgi.impl.service.jdbc.sqllite.util.Util;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;
import org.sqlite.javax.SQLiteConnectionPoolDataSource;

@Designate(ocd = SqliteConfig.class, factory = true)
@Component(service = ConnectionPoolDataSource.class, scope = ServiceScope.PROTOTYPE)
public class ConnectionPoolDataSourceService
		extends AbstractDelegateConnectionPoolDataSource<SQLiteConnectionPoolDataSource> {

	private SqliteConfig config;
	private SQLiteConnectionPoolDataSource ds;

	@Activate
	public ConnectionPoolDataSourceService(SqliteConfig config) throws SQLException {

		this.ds = new SQLiteConnectionPoolDataSource(Util.transformConfig(config));
		this.config = config;
	}

	// no @Modified to force consumed Services get new configured connections.
	@Deactivate
	public void deactivate() {
		config = null;
	}

	@Override
	public PooledConnection getPooledConnection() throws SQLException {

		return super.getPooledConnection(config.username(), config._password());
	}

	@Override
	protected SQLiteConnectionPoolDataSource delegate() {
		return ds;
	}

}
