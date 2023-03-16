package org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.jdbc;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition
public interface DatabaseVerifierConfig {
    @AttributeDefinition(description = "schemaRequired")
    default Boolean isSchemaRequired() {
        return true;
    }
}
