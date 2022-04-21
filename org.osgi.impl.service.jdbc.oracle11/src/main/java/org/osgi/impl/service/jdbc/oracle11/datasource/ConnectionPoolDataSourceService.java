package org.osgi.impl.service.jdbc.oracle11.datasource;

import java.sql.SQLException;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;

import org.osgi.impl.service.jdbc.common.datasource.AbstractDelegateConnectionPoolDataSource;
import org.osgi.impl.service.jdbc.oracle11.util.Util;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;

import oracle.jdbc.datasource.impl.OracleConnectionPoolDataSource;

@Designate(ocd = Oracle11Config.class, factory = true)
@Component(service = ConnectionPoolDataSource.class, scope = ServiceScope.PROTOTYPE)
public class ConnectionPoolDataSourceService
		extends AbstractDelegateConnectionPoolDataSource<OracleConnectionPoolDataSource> {

	private Oracle11Config config;
	private OracleConnectionPoolDataSource ds;

	@Activate
	public ConnectionPoolDataSourceService(Oracle11Config config) throws SQLException {
		this.ds = new OracleConnectionPoolDataSource();
		ds.setConnectionProperties(Util.transformConfig(config));
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
	protected OracleConnectionPoolDataSource delegate() {
		return ds;
	}

}
