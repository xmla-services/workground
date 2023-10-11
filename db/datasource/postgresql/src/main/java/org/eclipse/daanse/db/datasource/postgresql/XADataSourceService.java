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
package org.eclipse.daanse.db.datasource.postgresql;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.XAConnectionBuilder;
import javax.sql.XADataSource;

import org.eclipse.daanse.db.datasource.common.AbstractDelegateXADataSource;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.util.converter.Converter;
import org.osgi.util.converter.Converters;
import org.postgresql.xa.PGXADataSource;

@Designate(ocd = PostgresConfig.class, factory = true)
@Component(service = XADataSource.class, scope = ServiceScope.SINGLETON)
public class XADataSourceService extends AbstractDelegateXADataSource<PGXADataSource> {
	private static final Converter CONVERTER = Converters.standardConverter();

	private PostgresConfig config;
	private PGXADataSource ds;

	@Activate
	public XADataSourceService(Map<String, Object> config) throws SQLException {
		this(CONVERTER.convert(config).to(PostgresConfig.class));
	}

	public XADataSourceService(PostgresConfig config) throws SQLException {
		this.ds = new PGXADataSource();
		Util.doConfig(config, ds);
		this.config = config;
	}

	// no @Modified to force consumed Services get new configured connections.
	@Deactivate
	public void deactivate() {
		config = null;
	}

	@Override
	public XAConnectionBuilder createXAConnectionBuilder() throws SQLException {
		return super.createXAConnectionBuilder().user(config.user()).password(config._password());
	}

	@Override
	protected PGXADataSource delegate() {
		return ds;
	}

}
