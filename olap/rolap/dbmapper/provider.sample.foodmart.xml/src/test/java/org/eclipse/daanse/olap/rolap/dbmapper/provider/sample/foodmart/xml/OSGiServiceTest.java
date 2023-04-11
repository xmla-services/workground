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
                .isEqualTo("FoodMart");
    }
    
    @org.junit.jupiter.api.Test
    void testDbMappingSchemaProviderWithProps(@InjectService(timeout = 1000,filter = "(&(sample.type=xml)(sample.name=FoodMart))") DbMappingSchemaProvider provider) throws Exception {
        Schema schema = provider.get();

        assertThat(schema.name()).isNotNull()
                .isEqualTo("FoodMart");
    }
}
