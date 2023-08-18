/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.olap.rolap.dbmapper.provider.sample.minimal.record;

import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Cube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.HierarchyR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.LevelR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.PrivateDimensionR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.TableR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.CubeRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.HierarchyRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.LevelRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.PrivateDimensionRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.SchemaRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.api.DbMappingSchemaProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

@Component(service = DbMappingSchemaProvider.class, scope = ServiceScope.SINGLETON, property = {"sample.name=Minimal",
    "sample.type=record"})
public class MinimalRecordDbMappingSchemaProvider implements DbMappingSchemaProvider {
	private static final String SCHEMA_NAME = "Minimal";
	private static final String CUBE_NAME = "OnlyCube";
	private static final TableR CUBE_TABLE_FACT = new TableR("OnlyCubeFact");
    public static final String LEVEL_NAME = "OnlyLevel";
    public static final String KEY_NAME = "KEY";
    public static final String VALUE_NAME = "VALUE";
  
  
 
    private static final LevelR LEVEL = LevelRBuilder
        .builder()
        .name(LEVEL_NAME)
        .column(KEY_NAME)
        .nameColumn(KEY_NAME)
        .build();


    private static final HierarchyR HIERARCHY = HierarchyRBuilder
        .builder()
        .hasAll(true)
        .name("OnlyHirarchy")
        .primaryKey(KEY_NAME)
        .relation(CUBE_TABLE_FACT)
        .levels(List.of(LEVEL))
        .build();

 
    private static final PrivateDimensionR DIMENSION = PrivateDimensionRBuilder
        .builder()
        .name("OnlyDimension")
        .foreignKey(LEVEL_NAME)
        .hierarchies(List.of(HIERARCHY))
        .build();

  

    private static final Cube CUBE = CubeRBuilder
        .builder()
        .name(CUBE_NAME)
        .fact(CUBE_TABLE_FACT)
        .dimensionUsageOrDimensions(List.of(
            DIMENSION))
        .build();

    private static final Schema
        SCHEMA = SchemaRBuilder.builder()
        .name(SCHEMA_NAME)
        .cubes(List.of(CUBE))
        .build();

    @Override
    public Schema get() {
        return SCHEMA;
    }

}
