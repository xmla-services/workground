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
package org.eclipse.daanse.olap.rolap.dbmapper.provider.sample.foodmart.xml;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.api.DbMappingSchemaProvider;
import org.osgi.service.cm.annotations.RequireConfigurationAdmin;
import org.osgi.service.component.annotations.RequireServiceComponentRuntime;
import org.osgi.test.common.annotation.InjectService;
@RequireConfigurationAdmin
@RequireServiceComponentRuntime
class OSGiServiceTest {

    @org.junit.jupiter.api.Test
    void testDbMappingSchemaProvider(@InjectService(timeout = 1000) DbMappingSchemaProvider provider) throws Exception {
        Schema schema = provider.get();

        assertThat(schema.name()).isNotNull()
                .isEqualTo("Population");
    }

    @org.junit.jupiter.api.Test
    void testDbMappingSchemaProviderWithProps(@InjectService(timeout = 1000,filter = "(&(sample.type=xml)(sample.name=Population))") DbMappingSchemaProvider provider) throws Exception {
        Schema schema = provider.get();

        assertThat(schema.name()).isNotNull()
                .isEqualTo("Population");
    }
}
