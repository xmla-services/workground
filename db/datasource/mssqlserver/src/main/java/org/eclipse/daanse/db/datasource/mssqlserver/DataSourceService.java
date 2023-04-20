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

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.eclipse.daanse.db.datasource.common.AbstractDelegateDataSource;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

@Designate(ocd = MsSqlConfig.class, factory = true)
@Component(service = DataSource.class, scope = ServiceScope.SINGLETON)
public class DataSourceService extends AbstractDelegateDataSource<SQLServerDataSource> {

    private MsSqlConfig config;
    private SQLServerDataSource ds;

    @Activate
    public DataSourceService(MsSqlConfig config) {
        this.ds = new SQLServerDataSource();
        this.config = config;
    }
    // no @Modified to force consumed Services get new configured connections.

    @Deactivate
    public void deactivate() {

        config = null;
    }

    @Override
    public Connection getConnection() throws SQLException {

        return super.getConnection(config.username(), config._password());
    }

    @Override
    protected SQLServerDataSource delegate() {
        return ds;
    }

}
