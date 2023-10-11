package org.eclipse.daanse.db.jdbc.ecoregen;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "DatabaseSchemaObserver", description = "")
public interface CreatorConfig {

	@AttributeDefinition(name = "databaseSchema", description = "name of the schema in the database", required = true)
	default String database_schema() {
		return null;
	}

	@AttributeDefinition(name = "package name", description = "name of the package in the ecore model", required = true)
	default String ecore_packageName() {
		return null;
	}

	@AttributeDefinition(name = "namespace uri", description = "namespace uri of the package in the schema", required = true)
	default String ecore_nsUri() {
		return null;

	}

	@AttributeDefinition(name = "namespace prefix", description = "namespace prefix of the package in the schema", required = true)
	default String ecore_nsPrefix() {
		return null;
	}

}
