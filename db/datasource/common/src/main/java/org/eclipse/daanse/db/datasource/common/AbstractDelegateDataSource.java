/*
* Copyright (c) 2022 Contributors to the Eclipse Foundation.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*   SmartCity Jena - initial
*   Stefan Bischof (bipolis.org) - initial
*/
package org.eclipse.daanse.db.datasource.common;

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

    @Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
        return delegate().unwrap(iface);
    }

    @Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return delegate().isWrapperFor(iface);
    }

    @Override
	public Connection getConnection() throws SQLException {
        return delegate().getConnection();
    }

    @Override
	public Connection getConnection(String username, String password) throws SQLException {
        return delegate().getConnection(username, password);
    }

    @Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return delegate().getParentLogger();
    }

    @Override
	public PrintWriter getLogWriter() throws SQLException {
        return delegate().getLogWriter();
    }

    @Override
	public void setLogWriter(PrintWriter out) throws SQLException {
        delegate().setLogWriter(out);
    }

    @Override
	public void setLoginTimeout(int seconds) throws SQLException {
        delegate().setLoginTimeout(seconds);
    }

    @Override
	public int getLoginTimeout() throws SQLException {
        return delegate().getLoginTimeout();
    }

    @Override
	public ConnectionBuilder createConnectionBuilder() throws SQLException {
        return delegate().createConnectionBuilder();
    }

    @Override
	public ShardingKeyBuilder createShardingKeyBuilder() throws SQLException {
        return delegate().createShardingKeyBuilder();
    }
}
