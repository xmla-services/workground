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
package org.eclipse.daanse.db.datasource.h2;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;

import org.eclipse.daanse.db.datasource.common.AbstractDelegateConnectionPoolDataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.util.converter.Converter;
import org.osgi.util.converter.Converters;

@Designate(ocd = H2Config.class, factory = true)
@Component(service = { DataSource.class, ConnectionPoolDataSource.class }, scope = ServiceScope.SINGLETON,name = H2DataSource.PID)
public class H2DataSource extends AbstractDelegateConnectionPoolDataSource<ConnectionPoolDataSource> implements ConnectionPoolDataSource,DataSource {
	private static final Converter CONVERTER = Converters.standardConverter();

	public static final String PID = "org.eclipse.daanse.db.datasource.h2.H2DataSource";

	private H2Config config;
	private JdbcDataSource ds;

	@Activate
	public H2DataSource(Map<String, Object> properties) throws SQLException {
		config = CONVERTER.convert(properties).to(H2Config.class);

		this.ds = new JdbcDataSource();
		ds.setURL(config.url());
	}
	// no @Modified to force consumed Services get new configured connections.

	@Deactivate
	public void deactivate() {

		config = null;
	}

	@Override
	public Connection getConnection() throws SQLException {

		return delegate().getConnection(config.username(), config._password());
	}
	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return delegate().getConnection(username, password);
	}

	@Override
	protected JdbcDataSource delegate() {
		return ds;
	}
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {

		return delegate().unwrap(iface);
	}
	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return delegate().isWrapperFor(iface);
	}

}
