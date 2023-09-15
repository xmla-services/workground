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
package org.eclipse.daanse.engine.impl;

import java.sql.Connection;
import java.util.Dictionary;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.api.DialectFactory;
import org.eclipse.daanse.db.statistics.api.StatisticsProvider;
import org.eclipse.daanse.engine.api.Context;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.namespace.unresolvable.UnresolvableNamespace;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.util.converter.Converter;
import org.osgi.util.converter.Converters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Designate(ocd = BasicContextConfig.class, factory = true)
@Component(service = Context.class, scope = ServiceScope.SINGLETON)
public class BasicContext implements Context {

    public static final String PID = "org.eclipse.daanse.engine.impl.BasicContext";
    public static final String REF_NAME_DIALECT = "dialect";
    public static final String REF_NAME_STATISTICS_PROVIDER = "statisticsProvider";
    public static final String REF_NAME_DATA_SOURCE = "dataSource";
    public static final String REF_NAME_QUERY_PROVIDER = "queryProvier";
    public static final String REF_NAME_DB_MAPPING_SCHEMA_PROVIDER = "dataBaseMappingSchemaProvider";
	private static final String ERR_MSG_DIALECT_INIT = "Could not activate context. Error on initialisation of Dialect";
	private static Logger LOGGER = LoggerFactory.getLogger(BasicContext.class);

	private static final Converter CONVERTER = Converters.standardConverter();

	@Reference(name = REF_NAME_DATA_SOURCE, target = UnresolvableNamespace.UNRESOLVABLE_FILTER)
    private DataSource dataSource = null;

    @Reference(name = REF_NAME_DIALECT, target = UnresolvableNamespace.UNRESOLVABLE_FILTER)
    private DialectFactory dialectFactory = null;

    private Dialect dialect = null;

    @Reference(name = REF_NAME_STATISTICS_PROVIDER)
    private StatisticsProvider statisticsProvider = null;

    @Reference
    private ConfigurationAdmin configAdmin;


    @Reference
    ManagedService  managedService;


//    @Reference(name = REF_NAME_QUERY_PROVIDER, target = UnresolvableNamespace.UNRESOLVABLE_FILTER)
//    private QueryProvider queryProvider;
//
//
//    @Reference(name = REF_NAME_DB_MAPPING_SCHEMA_PROVIDER, target = UnresolvableNamespace.UNRESOLVABLE_FILTER)
//    private DataBaseMappingSchemaProvider dataBaseMappingSchemaProvider;


    private BasicContextConfig config;

    @Activate
    public void activate(Map<String, Object> coniguration, BundleContext context) throws Exception {
        Dictionary  dictionary = FrameworkUtil.asDictionary(coniguration);
        Configuration configuration = configAdmin.getConfiguration("coniguration");
        configuration.update(dictionary);
        configuration.update();
        String p = System.getProperty("test.name"); //"theName"
        Dictionary  dictionary1 = configuration.getProperties();
        managedService.updated(dictionary1);
        Dictionary  dictionary2 = configuration.getProcessedProperties(context.getServiceReference(ManagedService.class));
        System.out.println(dictionary1.get("name")); //"$[env:test.name]"
        System.out.println(dictionary2.get("name")); //"$[env:test.name]" but should "theName"
        this.config = CONVERTER.convert(coniguration)
                .to(BasicContextConfig.class);
        try (Connection connection = dataSource.getConnection()) {
            Optional<Dialect> optionalDialect =  dialectFactory.tryCreateDialect(connection);
            dialect = optionalDialect.orElseThrow(() -> new Exception(ERR_MSG_DIALECT_INIT));
        }
        statisticsProvider.initialize(dataSource, getDialect());
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public Dialect getDialect() {
        return dialect;
    }

    @Override
    public StatisticsProvider getStatisticsProvider() {
        return statisticsProvider;
    }

    @Override
    public String getName() {
        return config.name();
    }

    @Override
    public Optional<String> getDescription() {
        return Optional.ofNullable(config.description());
    }


//	@Override
//	public DataBaseMappingSchemaProvider getDataBaseMappingSchemaProvider() {
//		return dataBaseMappingSchemaProvider;
//	}
//
//	@Override
//	public QueryProvider getQueryProvider() {
//		return queryProvider;
//	}

}
