package org.eclipse.daanse.xmla.api.discover.discover.schemarowsets;

import org.eclipse.daanse.xmla.api.annotation.Restriction;

import java.util.Optional;

import static org.eclipse.daanse.xmla.api.common.properties.XsdType.XSD_STRING;

public interface DiscoverSchemaRowsetsRestrictions {
    String RESTRICTIONS_SCHEMA_NAME = "SchemaName";

    @Restriction(name = RESTRICTIONS_SCHEMA_NAME, type = XSD_STRING)
    Optional<String> schemaName();

}
