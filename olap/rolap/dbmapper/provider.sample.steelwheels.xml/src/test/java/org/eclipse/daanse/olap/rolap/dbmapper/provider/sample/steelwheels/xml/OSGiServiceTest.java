package org.eclipse.daanse.olap.rolap.dbmapper.provider.sample.steelwheels.xml;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.api.DbMappingSchemaProvider;
import org.osgi.service.component.annotations.RequireServiceComponentRuntime;
import org.osgi.test.common.annotation.InjectService;
@RequireServiceComponentRuntime
class OSGiServiceTest {

    @org.junit.jupiter.api.Test
    void testDbMappingSchemaProvider(@InjectService DbMappingSchemaProvider provider) throws Exception {
        Schema schema = provider.get();

        assertThat(schema.name()).isNotNull()
                .isEqualTo("SteelWheels");
    }
    
    @org.junit.jupiter.api.Test
    void testDbMappingSchemaProviderWithProps(@InjectService(filter = "(&(sample.type=xml)(sample.name=SteelWheels))") DbMappingSchemaProvider provider) throws Exception {
        Schema schema = provider.get();

        assertThat(schema.name()).isNotNull()
                .isEqualTo("SteelWheels");
    }
}
