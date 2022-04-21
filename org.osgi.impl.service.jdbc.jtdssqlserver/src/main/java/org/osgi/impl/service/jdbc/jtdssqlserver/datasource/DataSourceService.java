package org.osgi.impl.service.jdbc.jtdssqlserver.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.osgi.impl.service.jdbc.common.datasource.AbstractDelegateDataSource;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;

import net.sourceforge.jtds.jdbcx.JtdsDataSource;

@Designate(ocd = JtdsConfig.class, factory = true)
@Component(service = DataSource.class, scope = ServiceScope.PROTOTYPE)
public class DataSourceService extends AbstractDelegateDataSource<JtdsDataSource> {

	private JtdsConfig config;
	private JtdsDataSource ds;

	@Activate
	public DataSourceService(JtdsConfig config) throws SQLException {
		this.ds = new JtdsDataSource();
//		ds. setConnectionProperties(Util.transformConfig(config));
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
	protected JtdsDataSource delegate() {
		return ds;
	}

}
