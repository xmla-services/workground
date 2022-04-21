package org.osgi.impl.service.jdbc.sqllite.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.osgi.impl.service.jdbc.common.datasource.AbstractDelegateDataSource;
import org.osgi.impl.service.jdbc.sqllite.util.Util;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;
import org.sqlite.SQLiteDataSource;

@Designate(ocd = SqliteConfig.class, factory = true)
@Component(service = DataSource.class, scope = ServiceScope.PROTOTYPE)
public class DataSourceService extends AbstractDelegateDataSource<SQLiteDataSource> {

	private SqliteConfig config;
	private SQLiteDataSource ds;

	@Activate
	public DataSourceService(SqliteConfig config) throws SQLException {

		this.ds = new SQLiteDataSource(Util.transformConfig(config));
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
	protected SQLiteDataSource delegate() {
		return ds;
	}

}
