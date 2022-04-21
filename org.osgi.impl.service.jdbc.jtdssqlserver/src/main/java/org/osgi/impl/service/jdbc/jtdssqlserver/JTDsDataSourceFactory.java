package org.osgi.impl.service.jdbc.jtdssqlserver;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.XADataSource;

import org.osgi.service.jdbc.DataSourceFactory;

import net.sourceforge.jtds.jdbc.Driver;
import net.sourceforge.jtds.jdbcx.JtdsDataSource;

public class JTDsDataSourceFactory implements DataSourceFactory {

	// Driver is not configurable.
	public static final Driver DRIVER = new Driver();

	@Override
	public JtdsDataSource createDataSource(Properties props) throws SQLException {
		JtdsDataSource dataSource = new JtdsDataSource();

		return dataSource;
	}

	@Override
	public ConnectionPoolDataSource createConnectionPoolDataSource(Properties props) throws SQLException {
		throw new SQLFeatureNotSupportedException("ConnectionPoolDataSource not implemented");

	}

	@Override
	public XADataSource createXADataSource(Properties props) throws SQLException {

		throw new SQLFeatureNotSupportedException("XADataSource not implemented");
	}

	@Override
	public Driver createDriver(Properties props) throws SQLException {
		return DRIVER;

	}

	String getVersion() {
		return DRIVER.getMajorVersion() + "." + DRIVER.getMinorVersion();
	}

}
