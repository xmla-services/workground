package org.eclipse.daanse.olap.rolap.dbmapper.provider.sample.foodmart.xml;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.api.DbMappingSchemaProvider;
import org.osgi.test.common.annotation.InjectService;

class Test {

    @org.junit.jupiter.api.Test
    void testName(@InjectService DbMappingSchemaProvider provider) throws Exception {
        Schema schema = provider.get();

        assertThat(schema.name()).isNotNull()
                .isEqualTo("FoodMart");
    }
}
