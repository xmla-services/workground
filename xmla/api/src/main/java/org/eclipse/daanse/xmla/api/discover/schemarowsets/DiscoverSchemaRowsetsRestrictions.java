package org.eclipse.daanse.xmla.api.discover.schemarowsets;

import java.util.Optional;

public interface DiscoverSchemaRowsetsRestrictions {
    public static final String RESTRICTIONS_SCHEMA_NAME = "SchemaName";

    Optional<String> schemaName();

}
