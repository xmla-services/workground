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
package org.eclipse.daanse.olap.xmla.bridge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.osgi.test.assertj.servicereference.ServiceReferenceAssert.assertThat;
import static org.osgi.test.common.dictionary.Dictionaries.dictionaryOf;

import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.daanse.olap.api.Context;
import org.eclipse.daanse.xmla.api.XmlaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.Configuration;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.config.InjectConfiguration;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.common.service.ServiceAware;
import org.osgi.test.junit5.cm.ConfigurationExtension;

@ExtendWith(ConfigurationExtension.class)
@ExtendWith(MockitoExtension.class)
class ServiceTest {

	private static final String TARGET_EXT = ".target";

	@InjectBundleContext
	BundleContext bc;

	@Mock
	Context context1;

	@Mock
	Context context2;

	@Mock
	Context context3;

	@BeforeEach
	public void setup() throws SQLException {

	}

	@Test
	 void serviceExists(
			@InjectConfiguration(withFactoryConfig = @WithFactoryConfiguration(factoryPid = MultiContextXmlaService.PID, name = "name1")) Configuration c,
			@InjectService(cardinality = 0) ServiceAware<XmlaService> saXmlaService) throws Exception {

		// Must exist from start
		assertThat(saXmlaService).isNotNull().extracting(ServiceAware::size).isEqualTo(1);
		assertThat(saXmlaService.getServiceReference()).isNotNull();
		XmlaService xmlaService = saXmlaService.waitForService(100);

		assertThat(xmlaService).isNotNull().satisfies(x -> {
			assertThat(x).isInstanceOf(MultiContextXmlaService.class);
			assertThat((MultiContextXmlaService) x).satisfies(bx -> {
				assertThat(bx.getContexts()).isEmpty();
			});
		});

		bc.registerService(Context.class, context1, dictionaryOf("c", "1", "a", "true"));
		bc.registerService(Context.class, context2, dictionaryOf("c", "2", "a", "true"));
		bc.registerService(Context.class, context3, dictionaryOf("c", "3", "a", "false"));

		Dictionary<String, Object> props = new Hashtable<>();
		props.put(MultiContextXmlaService.REF_NAME_CONTEXT + TARGET_EXT, "(a=true)");
		c.update(props);

		Thread.sleep(100);
		xmlaService = saXmlaService.waitForService(100);

		assertThat(xmlaService).satisfies(x -> {
			assertThat(x).isInstanceOf(MultiContextXmlaService.class);
			assertThat((MultiContextXmlaService) x).satisfies(bx -> {
				assertThat(bx.getContexts()).contains(context1, context2).doesNotContain(context3);
			});

		});

	}
}
