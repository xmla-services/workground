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

import javax.sql.XAConnection;
import javax.sql.XAConnectionBuilder;
import javax.sql.XADataSource;

public abstract class AbstractDelegateXADataSource<D extends XADataSource> implements XADataSource {

    protected abstract D delegate();

    @Override
	public XAConnection getXAConnection() throws SQLException {
        return delegate().getXAConnection();
    }

    @Override
	public XAConnection getXAConnection(String user, String password) throws SQLException {
        return delegate().getXAConnection(user, password);
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
	public XAConnectionBuilder createXAConnectionBuilder() throws SQLException {
        return delegate().createXAConnectionBuilder();
    }

    @Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return delegate().getParentLogger();
    }

    @Override
	public ShardingKeyBuilder createShardingKeyBuilder() throws SQLException {
        return delegate().createShardingKeyBuilder();
    }

}
