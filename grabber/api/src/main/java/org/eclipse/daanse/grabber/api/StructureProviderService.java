package org.eclipse.daanse.grabber.api;

import org.eclipse.daanse.db.jdbc.util.impl.DBStructure;

import java.util.List;

public interface StructureProviderService {
    DBStructure grabStructure(String targetSchemaName, List<String> endPoints);
}
