package org.osgi.impl.service.jdbc.clickhouse;

import java.sql.Driver;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.osgi.service.jdbc.DataSourceFactory;

import com.clickhouse.jdbc.ClickHouseDataSource;
import com.clickhouse.jdbc.ClickHouseDriver;

public class ClickhouseDataSourceFactory implements DataSourceFactory {

	// Driver is not configurable.
	public static final ClickHouseDriver DRIVER = new ClickHouseDriver();

	@Override
	public DataSource createDataSource(Properties props) throws SQLException {
		String url=props.getProperty("url");
		if(url==null) {
			
			url=props.getProperty(JDBC_URL);
		}else {
			url=null;
		}

		ClickHouseDataSource dataSource = new ClickHouseDataSource(url,props);
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
