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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.CubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.api.DatabaseMappingSchemaProvider;
import org.junit.jupiter.api.Test;
import org.osgi.service.cm.annotations.RequireConfigurationAdmin;
import org.osgi.service.component.annotations.RequireServiceComponentRuntime;
import org.osgi.test.common.annotation.InjectService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RequireConfigurationAdmin
@RequireServiceComponentRuntime
class PopulationReadTest {

    @Test
    void test_Population(
        @InjectService(timeout = 100000, filter = "(&(sample.type=xml)(sample.name=Population))") DatabaseMappingSchemaProvider provider
    )  throws Exception {
    	
        Schema schema = provider.get();
        assertThat(schema).isNotNull();
        assertEquals("Population", schema.name());
        assertThat(schema.cubes()).isNotNull().hasSize(1);
        assertThat(schema.cubes().get(0).name()).isEqualTo("Population");
        assertThat(schema.cubes().get(0).dimensionUsageOrDimensions()).isNotNull().hasSize(4);
        assertThat(schema.cubes().get(0).dimensionUsageOrDimensions())
        .extracting(CubeDimension::name)
        .contains("Year")
        .contains("Geographical")
        .contains("Gender")
        .contains("Age");
    }
}
