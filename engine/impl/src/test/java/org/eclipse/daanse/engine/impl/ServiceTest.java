/*
uuid * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * History:
 *  This files came from the mondrian project. Some of the Flies
 *  (mostly the Tests) did not have License Header.
 *  But the Project is EPL Header. 2002-2022 Hitachi Vantara.
 *
 * Contributors:
 *   Hitachi Vantara.
 *   SmartCity Jena - initial  Java 8, Junit5
 */
package org.eclipse.daanse.engine.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.osgi.test.common.dictionary.Dictionaries.dictionaryOf;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.sql.DataSource;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.statistics.api.StatisticsProvider;
import org.eclipse.daanse.engine.api.Context;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.Configuration;
import org.osgi.test.assertj.servicereference.ServiceReferenceAssert;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.config.InjectConfiguration;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.common.service.ServiceAware;

public class ServiceTest {

    @InjectBundleContext
    BundleContext bc;
    @Mock
    Dialect dialect;

    @Mock
    DataSource dataSource;

    @Mock
    StatisticsProvider statisticsProvider;

    @Test
    public void serviceExists(
            @InjectConfiguration(withFactoryConfig = @WithFactoryConfiguration(factoryPid = BasicContext.PID, name = "name1")) Configuration c,
            @InjectService ServiceAware<Context> saContext) throws Exception {
        assertThat(saContext).isNotNull()
                .extracting(ServiceAware::size)
                .isEqualTo(0);

        ServiceReferenceAssert.assertThat(saContext.getServiceReference())
                .isNotNull();

        bc.registerService(DataSource.class, dataSource, dictionaryOf("ds", "1"));
        bc.registerService(Dialect.class, dialect, dictionaryOf("d", "2"));
        bc.registerService(StatisticsProvider.class, statisticsProvider, dictionaryOf("sp", "3"));

        Dictionary<String, Object> props = new Hashtable<>();

        props.put("target." + BasicContext.REF_NAME_DATA_SOURCE, "(ds=1)");
        props.put("target." + BasicContext.REF_NAME_DIALECT, "(d=2)");
        props.put("target." + BasicContext.REF_NAME_STATISTICS_PROVIDER, "(sp=3)");

        String theName = "theName";
        String theDescription = "theDescription";
        props.put("name", theName);
        props.put("description", theDescription);
        c.update(props);
        Context ctx = saContext.waitForService(100);

        assertThat(ctx).satisfies(x -> {
            assertThat(x.getName()).isEqualTo(theName);
            assertThat(x.getDescription()).isEqualTo(theDescription);
            assertThat(x.getDataSource()).isEqualTo(dataSource);
            assertThat(x.getDialect()).isEqualTo(dialect);
            assertThat(x.getStatisticsProvider()).isEqualTo(statisticsProvider);
        });

    }
}
