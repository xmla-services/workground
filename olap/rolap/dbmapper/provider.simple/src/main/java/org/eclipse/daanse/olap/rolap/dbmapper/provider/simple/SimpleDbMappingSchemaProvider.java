package org.eclipse.daanse.olap.rolap.dbmapper.provider.simple;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchema;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.api.DatabaseMappingSchemaProvider;

public class SimpleDbMappingSchemaProvider implements DatabaseMappingSchemaProvider {

	private MappingSchema schema;

	private SimpleDbMappingSchemaProvider() {
	}

	public SimpleDbMappingSchemaProvider(MappingSchema schema) {
		this();
		this.schema = schema;
	}

	@Override
	public MappingSchema get() {
		return schema;
	}
}
