package org.osgi.impl.service.jdbc.common.datasource;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.ShardingKeyBuilder;
import java.util.logging.Logger;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;
import javax.sql.PooledConnectionBuilder;

public abstract class AbstractDelegateConnectionPoolDataSource<D extends ConnectionPoolDataSource>
		implements ConnectionPoolDataSource {


	protected abstract D delegate() ;


	public PooledConnection getPooledConnection() throws SQLException {
		return delegate().getPooledConnection();
	}

	public PooledConnection getPooledConnection(String user, String password) throws SQLException {
		return delegate().getPooledConnection(user, password);
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

	public PooledConnectionBuilder createPooledConnectionBuilder() throws SQLException {
		return delegate().createPooledConnectionBuilder();
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return delegate().getParentLogger();
	}

	public ShardingKeyBuilder createShardingKeyBuilder() throws SQLException {
		return delegate().createShardingKeyBuilder();
	}

}
