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
package org.eclipse.daanse.db.datasource.mssqlserver;

import java.sql.SQLException;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;

import org.eclipse.daanse.db.datasource.common.AbstractDelegateConnectionPoolDataSource;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;

import com.microsoft.sqlserver.jdbc.SQLServerConnectionPoolDataSource;

@Designate(ocd = MsSqlConfig.class, factory = true)
@Component(service = ConnectionPoolDataSource.class, scope = ServiceScope.SINGLETON)
public class ConnectionPoolDataSourceService
        extends AbstractDelegateConnectionPoolDataSource<SQLServerConnectionPoolDataSource> {

    private MsSqlConfig config;
    private SQLServerConnectionPoolDataSource ds;

    @Activate
    public ConnectionPoolDataSourceService(MsSqlConfig config) {
        this.ds = new SQLServerConnectionPoolDataSource();
        this.config = config;
    }

    // no @Modified to force consumed Services get new configured connections.
    @Deactivate
    public void deactivate() {
        config = null;
    }

    @Override
    public PooledConnection getPooledConnection() throws SQLException {
        return super.getPooledConnection(config.username(), config._password());
    }

    @Override
    protected SQLServerConnectionPoolDataSource delegate() {
        return ds;
    }

}
