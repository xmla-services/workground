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
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.olap.rolap.dbmapper.provider.sample.expressivenames.xml;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.api.DbMappingSchemaProvider;
import org.junit.jupiter.api.Test;
import org.osgi.service.cm.annotations.RequireConfigurationAdmin;
import org.osgi.service.component.annotations.RequireServiceComponentRuntime;
import org.osgi.test.common.annotation.InjectService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RequireConfigurationAdmin
@RequireServiceComponentRuntime
class ExpressiveNamesReadTest {

	@Test
	void test_ExpressiveNames(
			@InjectService(timeout = 100000,filter = "(&(sample.type=xml)(sample.name=ExpressiveNames))") DbMappingSchemaProvider provider)
			{

		Schema schema = provider.get();
		assertThat(schema).isNotNull();
		assertEquals("ExpressiveNames", schema.name());
		assertNotNull(schema.dimensions());
		assertEquals(3, schema.dimensions().size());
	}

}
