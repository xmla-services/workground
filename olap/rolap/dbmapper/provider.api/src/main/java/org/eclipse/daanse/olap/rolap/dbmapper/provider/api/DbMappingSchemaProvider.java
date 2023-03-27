package org.eclipse.daanse.olap.rolap.dbmapper.provider.api;

import java.util.function.Supplier;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;



/*
 * Provides a Schema
 */
public interface DbMappingSchemaProvider extends Supplier<Schema> {

    /*
     * Provides a Schema.
     */
    @Override
    Schema get();
}
