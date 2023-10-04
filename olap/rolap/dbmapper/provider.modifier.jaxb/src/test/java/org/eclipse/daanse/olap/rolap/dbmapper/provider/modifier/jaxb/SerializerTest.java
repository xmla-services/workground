package org.eclipse.daanse.olap.rolap.dbmapper.provider.modifier.jaxb;

import org.eclipse.daanse.olap.rolap.dbmapper.provider.sample.foodmart.record.FoodMartRecordDbMappingSchemaProvider;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SerializerTest {


	@Test
	void testName() throws Exception {
		FoodMartRecordDbMappingSchemaProvider provider=new FoodMartRecordDbMappingSchemaProvider();
		SerializerModifier sm=new SerializerModifier(provider.get());
        String xml = sm.getXML();
        assertThat(xml).isNotNull();
		System.out.println(xml);
	}
}
