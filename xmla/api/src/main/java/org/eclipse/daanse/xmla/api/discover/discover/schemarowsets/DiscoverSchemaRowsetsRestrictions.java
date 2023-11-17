package org.eclipse.daanse.xmla.api.discover.discover.schemarowsets;

import org.eclipse.daanse.xmla.api.annotations.Restriction;

import java.util.Optional;

public interface DiscoverSchemaRowsetsRestrictions {
    String RESTRICTIONS_SCHEMA_NAME = "SchemaName";

    @Restriction(name = RESTRICTIONS_SCHEMA_NAME, type = "xsd:string")
    Optional<String> schemaName();

}
