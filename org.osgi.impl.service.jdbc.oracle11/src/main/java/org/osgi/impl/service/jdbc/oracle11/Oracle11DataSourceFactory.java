package org.osgi.impl.service.jdbc.oracle11;

import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.osgi.service.jdbc.DataSourceFactory;

import oracle.jdbc.OracleDriver;
import oracle.jdbc.datasource.impl.OracleConnectionPoolDataSource;
import oracle.jdbc.datasource.impl.OracleDataSource;
import oracle.jdbc.xa.client.OracleXADataSource;

public class Oracle11DataSourceFactory implements DataSourceFactory {

	// Driver is not configurable.
	public static final OracleDriver DRIVER = new OracleDriver();

	@Override
	public DataSource createDataSource(Properties props) throws SQLException {
		OracleDataSource dataSource = new OracleDataSource();
		dataSource.setConnectionProperties(props);
		return dataSource;
	}

	@Override
	public ConnectionPoolDataSource createConnectionPoolDataSource(Properties props) throws SQLException {
		OracleConnectionPoolDataSource dataSource = new OracleConnectionPoolDataSource();
		dataSource.setConnectionProperties(props);
		return dataSource;
	}

	@Override
	public XADataSource createXADataSource(Properties props) throws SQLException {
		OracleXADataSource dataSource=new OracleXADataSource();
		dataSource.setConnectionProperties(props);
		return dataSource;
	}

	@Override
	public Driver createDriver(Properties props) throws SQLException {
		return DRIVER;
		
	}

	String getVersion() {
		return DRIVER.getMajorVersion() + "." + DRIVER.getMinorVersion();
	}

}
