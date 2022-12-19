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
package org.eclipse.daanse.db.datasource.mysql;

import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.annotations.Component;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.jdbc.MysqlXADataSource;

@Component(service = ManagedServiceFactory.class, property = {Constants.SERVICE_PID+"=org.eclipse.daanse.db.xadatasource.mysql"})
public class XaDataSourceService extends MysqlMetaTypeProviderService implements ManagedServiceFactory {

	private Map<String, MysqlDataSourceRegistration<?>> registrations = new ConcurrentHashMap<>();
	private BundleContext bundleContext;

	public XaDataSourceService(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	@Override
	public String getName() {
		return "MySQL DataSourceService";
	}

	@Override
	public void updated(String pid, Dictionary<String, ?> properties) throws ConfigurationException {
		MysqlDataSourceRegistration<?> registration = registrations.computeIfAbsent(pid,
				nil -> new MysqlDataSourceRegistration<MysqlXADataSource>(new MysqlXADataSource(), XADataSource.class));
		registration.update(properties, bundleContext);
	}

	@Override
	public void deleted(String pid) {
		MysqlDataSourceRegistration<?> registration = registrations.remove(pid);
		if (registration != null) {
			registration.dispose();
		}
	}
}
