package org.osgi.impl.service.jdbc.mssqlserver.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.osgi.impl.service.jdbc.common.datasource.AbstractDelegateDataSource;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

@Designate(ocd = MsSqlConfig.class, factory = true)
@Component(service = DataSource.class, scope = ServiceScope.PROTOTYPE)
public class DataSourceService extends AbstractDelegateDataSource<SQLServerDataSource> {

	private MsSqlConfig config;
	private SQLServerDataSource ds;

	@Activate
	public DataSourceService(MsSqlConfig config) throws SQLException {
		this.ds = new SQLServerDataSource();
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
	protected SQLServerDataSource delegate() {
		return ds;
	}

}
