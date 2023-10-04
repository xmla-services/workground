package org.eclipse.daanse.olap.rolap.dbmapper.provider.modifier.jaxb;

import org.eclipse.daanse.olap.rolap.dbmapper.provider.sample.foodmart.record.FoodMartRecordDbMappingSchemaProvider;

public class Test {

	
	@org.junit.jupiter.api.Test
	void testName() throws Exception {
		FoodMartRecordDbMappingSchemaProvider provider=new FoodMartRecordDbMappingSchemaProvider();
		SerializerModifier sm=new SerializerModifier(provider.get());
		System.out.println(sm.getXML());
	}
}
