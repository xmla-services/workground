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
package org.eclipse.daanse.olap.core;

import java.sql.Connection;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.api.DialectFactory;
import org.eclipse.daanse.db.statistics.api.StatisticsProvider;
import org.eclipse.daanse.olap.api.Context;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.api.DatabaseMappingSchemaProvider;
import org.osgi.namespace.unresolvable.UnresolvableNamespace;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.util.converter.Converter;
import org.osgi.util.converter.Converters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aQute.bnd.metatype.annotations.Designate;

@Designate(ocd = BasicContextConfig.class, factory = true)
@Component(service = Context.class, scope = ServiceScope.SINGLETON)
public class BasicContext implements Context {

    public static final String PID = "org.eclipse.daanse.engine.impl.BasicContext";
    public static final String REF_NAME_DIALECT = "dialect";
    public static final String REF_NAME_STATISTICS_PROVIDER = "statisticsProvider";
    public static final String REF_NAME_DATA_SOURCE = "dataSource";
    public static final String REF_NAME_QUERY_PROVIDER = "queryProvier";
    public static final String REF_NAME_DB_MAPPING_SCHEMA_PROVIDER = "databaseMappingSchemaProvider";
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

//    @Reference(name = REF_NAME_QUERY_PROVIDER, target = UnresolvableNamespace.UNRESOLVABLE_FILTER)
//    private QueryProvider queryProvider;
//
//
    @Reference(name = REF_NAME_DB_MAPPING_SCHEMA_PROVIDER, target = UnresolvableNamespace.UNRESOLVABLE_FILTER)
    private DatabaseMappingSchemaProvider databaseMappingSchemaProvider;
    

    private BasicContextConfig config;

    @Activate
    public void activate(Map<String, Object> coniguration) throws Exception {

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

	@Override
	public DatabaseMappingSchemaProvider getDatabaseMappingSchemaProvider() {
		return databaseMappingSchemaProvider;
	}
//
//	@Override
//	public QueryProvider getQueryProvider() {
//		return queryProvider;
//	}

}
