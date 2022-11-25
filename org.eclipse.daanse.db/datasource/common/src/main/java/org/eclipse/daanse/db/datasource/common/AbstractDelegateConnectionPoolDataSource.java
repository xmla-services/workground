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
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.ShardingKeyBuilder;
import java.util.logging.Logger;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;
import javax.sql.PooledConnectionBuilder;

public abstract class AbstractDelegateConnectionPoolDataSource<D extends ConnectionPoolDataSource>
        implements ConnectionPoolDataSource {

    protected abstract D delegate();

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
