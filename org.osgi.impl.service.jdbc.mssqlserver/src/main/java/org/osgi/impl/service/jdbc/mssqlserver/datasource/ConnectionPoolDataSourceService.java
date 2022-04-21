package org.osgi.impl.service.jdbc.mssqlserver.datasource;

import java.sql.SQLException;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;

import org.osgi.impl.service.jdbc.common.datasource.AbstractDelegateConnectionPoolDataSource;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;

import com.microsoft.sqlserver.jdbc.SQLServerConnectionPoolDataSource;


@Designate(ocd = MsSqlConfig.class, factory = true)
@Component(service = ConnectionPoolDataSource.class, scope = ServiceScope.PROTOTYPE)
public class ConnectionPoolDataSourceService
		extends AbstractDelegateConnectionPoolDataSource<SQLServerConnectionPoolDataSource> {

	private MsSqlConfig config;
	private SQLServerConnectionPoolDataSource ds;

	@Activate
	public ConnectionPoolDataSourceService(MsSqlConfig config) throws SQLException {
		this.ds = new SQLServerConnectionPoolDataSource();
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
	protected SQLServerConnectionPoolDataSource delegate() {
		return ds;
	}

}
