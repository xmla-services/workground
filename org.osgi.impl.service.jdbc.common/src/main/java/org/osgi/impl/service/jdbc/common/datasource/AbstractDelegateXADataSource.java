package org.osgi.impl.service.jdbc.common.datasource;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.ShardingKeyBuilder;
import java.util.logging.Logger;

import javax.sql.XAConnection;
import javax.sql.XAConnectionBuilder;
import javax.sql.XADataSource;

public abstract class AbstractDelegateXADataSource<D extends XADataSource>  implements XADataSource {

	protected abstract D delegate();

	public XAConnection getXAConnection() throws SQLException {
		return delegate().getXAConnection();
	}

	public XAConnection getXAConnection(String user, String password) throws SQLException {
		return delegate().getXAConnection(user, password);
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

	public XAConnectionBuilder createXAConnectionBuilder() throws SQLException {
		return delegate().createXAConnectionBuilder();
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return delegate().getParentLogger();
	}

	public ShardingKeyBuilder createShardingKeyBuilder() throws SQLException {
		return delegate().createShardingKeyBuilder();
	}

}
