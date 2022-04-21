package org.osgi.impl.service.jdbc.oracle11.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.osgi.impl.service.jdbc.common.datasource.AbstractDelegateDataSource;
import org.osgi.impl.service.jdbc.oracle11.util.Util;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;

import oracle.jdbc.datasource.impl.OracleDataSource;

@Designate(ocd = Oracle11Config.class, factory = true)
@Component(service = DataSource.class, scope = ServiceScope.PROTOTYPE)
public class DataSourceService extends AbstractDelegateDataSource<OracleDataSource> {

	private Oracle11Config config;
	private OracleDataSource ds;

	@Activate
	public DataSourceService(Oracle11Config config) throws SQLException {
		this.ds = new OracleDataSource();
		ds.setConnectionProperties(Util.transformConfig(config));
		this.config = config;
	}
	// no @Modified to force consumed Services get new configured connections.

	@Deactivate
	public void deactivate() {

		config = null;
	}

	@Override
	public Connection getConnection() throws SQLException {

		return super.getConnection(config.username(), config._password());
	}

	@Override
	protected OracleDataSource delegate() {
		return ds;
	}

}
