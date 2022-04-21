package org.osgi.impl.service.jdbc.common.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ConnectionBuilder;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.ShardingKeyBuilder;
import java.util.logging.Logger;

import javax.sql.DataSource;

public abstract class AbstractDelegateDataSource<D extends DataSource> implements DataSource {

	protected abstract D delegate();

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return delegate().unwrap(iface);
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return delegate().isWrapperFor(iface);
	}

	public Connection getConnection() throws SQLException {
		return delegate().getConnection();
	}

	public Connection getConnection(String username, String password) throws SQLException {
		return delegate().getConnection(username, password);
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return delegate().getParentLogger();
	}

	public PrintWriter getLogWriter() throws SQLException {
		return delegate().getLogWriter();
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		delegate().setLogWriter(out);
	}

	public void setLoginTimeout(int seconds) throws SQLException {
		delegate().setLoginTimeout(seconds);
	}

	public int getLoginTimeout() throws SQLException {
		return delegate().getLoginTimeout();
	}

	public ConnectionBuilder createConnectionBuilder() throws SQLException {
		return delegate().createConnectionBuilder();
	}

	public ShardingKeyBuilder createShardingKeyBuilder() throws SQLException {
		return delegate().createShardingKeyBuilder();
	}
}
