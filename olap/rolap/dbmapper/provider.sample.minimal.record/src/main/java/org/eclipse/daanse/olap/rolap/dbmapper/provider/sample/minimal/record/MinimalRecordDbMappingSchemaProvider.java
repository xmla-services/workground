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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingDimensionUsage;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.MeasureDataTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.HierarchyR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.LevelR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.MeasureR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.PrivateDimensionR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.TableR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.CubeRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.DimensionUsageRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.HierarchyRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.LevelRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.MeasureRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.PrivateDimensionRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.SchemaRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.api.DatabaseMappingSchemaProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

@Component(service = DatabaseMappingSchemaProvider.class, scope = ServiceScope.SINGLETON, property = {"sample.name=Minimal",
    "sample.type=record"})
public class MinimalRecordDbMappingSchemaProvider implements DatabaseMappingSchemaProvider {
	private static final String SCHEMA_NAME = "Minimal";
	private static final String CUBE_NAME = "OnlyCube";
	private static final TableR CUBE_TABLE_FACT = new TableR("Cube1Fact");
    private static final TableR ONLY_CUBE_TABLE_FACT = new TableR("OnlyCubeFact");
    public static final String LEVEL_NAME = "OnlyLevel";
    public static final String KEY_NAME = "KEY_NAME";
    public static final String KEY_ID = "KEY";
    public static final String KEY_ORDER = "KEY_ORDER";
    public static final String VALUE_NAME = "VALUE";
    public static final String VALUE_NAME1 = "VALUE_COUNT";



    private static final LevelR LEVEL = LevelRBuilder
        .builder()
        .name(LEVEL_NAME)
        .column(KEY_ID)
        .nameColumn(KEY_NAME)
        .ordinalColumn(KEY_ORDER)
        .build();


    private static final HierarchyR HIERARCHY = HierarchyRBuilder
        .builder()
        .hasAll(true)
        .name("OnlyHierarchy")
        .primaryKey(KEY_NAME)
        .relation(ONLY_CUBE_TABLE_FACT)
        .levels(List.of(LEVEL))
        .build();

    public static final String ONLY_DIMENSION = "OnlyDimension";
    private static final PrivateDimensionR DIMENSION_SCHEMA = PrivateDimensionRBuilder
        .builder()
        .name(ONLY_DIMENSION)
        .foreignKey(LEVEL_NAME)
        .hierarchies(List.of(HIERARCHY))
        .build();

    private static final MappingDimensionUsage DIMENSION_USAGE = DimensionUsageRBuilder
        .builder()
        .name(ONLY_DIMENSION)
        .source(ONLY_DIMENSION)
        .foreignKey("D1")
        .build();

    private static final MeasureR MEASURE1 = MeasureRBuilder
        .builder()
        .name("Measure")
        .column(VALUE_NAME)
        .aggregator("sum")
        .formatString("#,###.00")
        .datatype(MeasureDataTypeEnum.INTEGER)
        .build();

    private static final MeasureR MEASURE2 = MeasureRBuilder
        .builder()
        .name("Measure1")
        .column(VALUE_NAME1)
        .aggregator("count")
        .formatString("Standard")
        .build();


    private static final MappingCube CUBE = CubeRBuilder
        .builder()
        .name(CUBE_NAME)
        .fact(CUBE_TABLE_FACT)
        .dimensionUsageOrDimensions(List.of(
            DIMENSION_USAGE))
        .measures(List.of(MEASURE1, MEASURE2))
        .build();

    private static final MappingSchema
        SCHEMA = SchemaRBuilder.builder()
        .name(SCHEMA_NAME)
        .dimensions(List.of(DIMENSION_SCHEMA))
        .cubes(List.of(CUBE))
        .build();

    @Override
    public MappingSchema get() {
        return SCHEMA;
    }

}
