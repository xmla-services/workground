package org.osgi.impl.service.jdbc.mssqlserver;

import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.osgi.service.jdbc.DataSourceFactory;

import com.microsoft.sqlserver.jdbc.SQLServerConnectionPoolDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import com.microsoft.sqlserver.jdbc.SQLServerXADataSource;

public class SQLServerDataSourceFactory implements DataSourceFactory {

	// Driver is not configurable.
	public static final SQLServerDriver DRIVER = new SQLServerDriver();

	@Override
	public DataSource createDataSource(Properties props) throws SQLException {
		SQLServerDataSource dataSource = new SQLServerDataSource();

		return dataSource;
	}

	@Override
	public ConnectionPoolDataSource createConnectionPoolDataSource(Properties props) throws SQLException {
		SQLServerConnectionPoolDataSource dataSource = new SQLServerConnectionPoolDataSource();
		return dataSource;
	}

	@Override
	public XADataSource createXADataSource(Properties props) throws SQLException {
		SQLServerXADataSource dataSource=new SQLServerXADataSource();

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
