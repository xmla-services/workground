/*
 * Copyright (c) 2023 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   SmartCity Jena - initial
 *   Stefan Bischof (bipolis.org) - initial
 */
package mondrian.rolap;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SchemaMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.DataType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.MeasureAggregatorType;
import org.eclipse.daanse.rolap.mapping.modifier.pojo.PojoMappingModifier;
import org.eclipse.daanse.rolap.mapping.pojo.DimensionConnectorMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.HierarchyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.LevelMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureGroupMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalCubeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.StandardDimensionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TableQueryMappingImpl;

public class RolapResultTestModifier extends PojoMappingModifier {

    public RolapResultTestModifier(CatalogMapping catalog) {
        super(catalog);
    }

    /*
                "<Cube name='FTAll'>\n"
            + "<Table name='FT1' />\n"
            + "<Dimension name='D1' foreignKey='d1_id' >\n"
            + " <Hierarchy hasAll='true' primaryKey='d1_id'>\n"
            + " <Table name='D1'/>\n"
            + " <Level name='Name' column='name' type='String' uniqueMembers='true'/>\n"
            + " </Hierarchy>\n"
            + "</Dimension>\n"
            + "<Dimension name='D2' foreignKey='d2_id' >\n"
            + " <Hierarchy hasAll='true' primaryKey='d2_id'>\n"
            + " <Table name='D2'/>\n"
            + " <Level name='Name' column='name' type='String' uniqueMembers='true'/>\n"
            + " </Hierarchy>\n"
            + "</Dimension>\n"

            + "<Measure name='Value' \n"
            + "    column='value' aggregator='sum'\n"
            + "   formatString='#,###'/>\n"
            + "</Cube> \n"

            + "<Cube name='FT1'>\n"
            + "<Table name='FT1' />\n"
            + "<Dimension name='D1' foreignKey='d1_id' >\n"
            + " <Hierarchy hasAll='false' defaultMember='[D1].[d]' primaryKey='d1_id'>\n"
            + " <Table name='D1'/>\n"
            + " <Level name='Name' column='name' type='String' uniqueMembers='true'/>\n"
            + " </Hierarchy>\n"
            + "</Dimension>\n"
            + "<Dimension name='D2' foreignKey='d2_id' >\n"
            + " <Hierarchy hasAll='false' defaultMember='[D2].[w]' primaryKey='d2_id'>\n"
            + " <Table name='D2'/>\n"
            + " <Level name='Name' column='name' type='String' uniqueMembers='true'/>\n"
            + " </Hierarchy>\n"
            + "</Dimension>\n"

            + "<Measure name='Value' \n"
            + "    column='value' aggregator='sum'\n"
            + "   formatString='#,###'/>\n"
            + "</Cube> \n"

            + "<Cube name='FT2'>\n"
            + "<Table name='FT2'/>\n"
            + "<Dimension name='D1' foreignKey='d1_id' >\n"
            + " <Hierarchy hasAll='false' defaultMember='[D1].[d]' primaryKey='d1_id'>\n"
            + " <Table name='D1'/>\n"
            + " <Level name='Name' column='name' type='String' uniqueMembers='true'/>\n"
            + " </Hierarchy>\n"
            + "</Dimension>\n"
            + "<Dimension name='D2' foreignKey='d2_id' >\n"
            + " <Hierarchy hasAll='false' defaultMember='[D2].[w]' primaryKey='d2_id'>\n"
            + " <Table name='D2'/>\n"
            + " <Level name='Name' column='name' type='String' uniqueMembers='true'/>\n"
            + " </Hierarchy>\n"
            + "</Dimension>\n"

            + "<Measure name='Value' \n"
            + "    column='value' aggregator='sum'\n"
            + "   formatString='#,###'/>\n"
            + "</Cube>\n"

            + "<Cube name='FT2Extra'>\n"
            + "<Table name='FT2'/>\n"
            + "<Dimension name='D1' foreignKey='d1_id' >\n"
            + " <Hierarchy hasAll='false' defaultMember='[D1].[d]' primaryKey='d1_id'>\n"
            + " <Table name='D1'/>\n"
            + " <Level name='Name' column='name' type='String' uniqueMembers='true'/>\n"
            + " </Hierarchy>\n"
            + "</Dimension>\n"
            + "<Dimension name='D2' foreignKey='d2_id' >\n"
            + " <Hierarchy hasAll='false' defaultMember='[D2].[w]' primaryKey='d2_id'>\n"
            + " <Table name='D2'/>\n"
            + " <Level name='Name' column='name' type='String' uniqueMembers='true'/>\n"
            + " </Hierarchy>\n"
            + "</Dimension>\n"
            + "<Measure name='VExtra' \n"
            + "    column='vextra' aggregator='sum'\n"
            + "   formatString='#,###'/>\n"
            + "<Measure name='Value' \n"
            + "    column='value' aggregator='sum'\n"
            + "   formatString='#,###'/>\n"
            + "</Cube>";

     */
    
    @Override
    protected List<? extends CubeMapping> schemaCubes(SchemaMapping schema) {
        List<CubeMapping> result = new ArrayList<>();
        result.addAll(super.schemaCubes(schema));
        result.add(PhysicalCubeMappingImpl.builder()
            .withName("FTAll")
            .withQuery(TableQueryMappingImpl.builder().withName("FT1").build())
            .withDimensionConnectors(List.of(
                DimensionConnectorMappingImpl.builder()
                	.withOverrideDimensionName("D1")
                    .withForeignKey("d1_id")
                    .withDimension(StandardDimensionMappingImpl.builder()
                    .withHierarchies(List.of(
                        HierarchyMappingImpl.builder()
                            .withHasAll(true)
                            .withPrimaryKey("d1_id")
                            .withQuery(TableQueryMappingImpl.builder().withName("D1").build())
                            .withLevels(List.of(
                                LevelMappingImpl.builder()
                                    .withName("Name")
                                    .withColumn("name")
                                    .withType(DataType.STRING)
                                    .withUniqueMembers(true)
                                    .build()
                            ))
                            .build()
                    )).build())
                    .build(),
                DimensionConnectorMappingImpl.builder()
                    .withOverrideDimensionName("D2")
                    .withForeignKey("d2_id")
                    .withDimension(StandardDimensionMappingImpl.builder()
                    .withHierarchies(List.of(
                        HierarchyMappingImpl.builder()
                            .withHasAll(true)
                            .withPrimaryKey("d2_id")
                            .withQuery(TableQueryMappingImpl.builder().withName("D2").build())
                            .withLevels(List.of(
                                LevelMappingImpl.builder()
                                    .withName("Name")
                                    .withColumn("name")
                                    .withType(DataType.STRING)
                                    .withUniqueMembers(true)
                                    .build()
                            ))
                            .build()
                    )).build())
                    .build()
            ))
            .withMeasureGroups(List.of(MeasureGroupMappingImpl.builder().withMeasures(List.of(
                MeasureMappingImpl.builder()
                    .withName("Value")
                    .withColumn("value")
                    .withAggregatorType(MeasureAggregatorType.SUM)
                    .withFormatString("#,###")
                    .build()
            )).build()))
            .build());

        result.add(PhysicalCubeMappingImpl.builder()
            .withName("FT1")
            .withQuery(TableQueryMappingImpl.builder().withName("FT1").build())
            .withDimensionConnectors(List.of(
                DimensionConnectorMappingImpl.builder()
                    .withOverrideDimensionName("D1")
                    .withForeignKey("d1_id")
                    .withDimension(StandardDimensionMappingImpl.builder()
                    	.withName("D1")
                    	.withHierarchies(List.of(
                        HierarchyMappingImpl.builder()
                            .withHasAll(false)
                            .withDefaultMember("[D1].[d]")
                            .withPrimaryKey("d1_id")
                            .withQuery(TableQueryMappingImpl.builder().withName("D1").build())
                            .withLevels(List.of(
                                LevelMappingImpl.builder()
                                    .withName("Name")
                                    .withColumn("name")
                                    .withType(DataType.STRING)
                                    .withUniqueMembers(true)
                                    .build()
                            ))
                            .build()
                    )).build())
                    .build(),
                DimensionConnectorMappingImpl.builder()
                    .withOverrideDimensionName("D2")
                    .withForeignKey("d2_id")
                    .withDimension(StandardDimensionMappingImpl.builder()
                    	.withName("D2")
                    	.withHierarchies(List.of(
                        HierarchyMappingImpl.builder()
                            .withHasAll(false)
                            .withDefaultMember("[D2].[w]")
                            .withPrimaryKey("d2_id")
                            .withQuery(TableQueryMappingImpl.builder().withName("D2").build())
                            .withLevels(List.of(
                                LevelMappingImpl.builder()
                                    .withName("Name")
                                    .withColumn("name")
                                    .withType(DataType.STRING)
                                    .withUniqueMembers(true)
                                    .build()
                            ))
                            .build()
                    )).build())
                    .build()
            ))
            .withMeasureGroups(List.of(MeasureGroupMappingImpl.builder().withMeasures(List.of(
                 MeasureMappingImpl.builder()
                    .withName("Value")
                    .withColumn("value")
                    .withAggregatorType(MeasureAggregatorType.SUM)
                    .withFormatString("#,###")
                    .build()
            )).build()))
            .build());

        result.add(PhysicalCubeMappingImpl.builder()
            .withName("FT2")
            .withQuery(TableQueryMappingImpl.builder().withName("FT2").build())
            .withDimensionConnectors(List.of(
                DimensionConnectorMappingImpl.builder()
                    .withOverrideDimensionName("D1")
                    .withForeignKey("d1_id")
                    .withDimension(StandardDimensionMappingImpl.builder()
                    	.withName("D1")
                    	.withHierarchies(List.of(
                        HierarchyMappingImpl.builder()
                            .withHasAll(true)
                            .withDefaultMember("[D1].[d]")
                            .withPrimaryKey("d1_id")
                            .withQuery(TableQueryMappingImpl.builder().withName("D1").build())
                            .withLevels(List.of(
                                LevelMappingImpl.builder()
                                    .withName("Name")
                                    .withColumn("name")
                                    .withType(DataType.STRING)
                                    .withUniqueMembers(true)
                                    .build()
                            ))
                            .build()
                    )).build())
                    .build(),
                DimensionConnectorMappingImpl.builder()
                    .withOverrideDimensionName("D2")
                    .withForeignKey("d2_id")
                    .withDimension(StandardDimensionMappingImpl.builder()
                    	.withName("D2")
                    	.withHierarchies(List.of(
                        HierarchyMappingImpl.builder()
                            .withHasAll(true)
                            .withDefaultMember("[D2].[w]")
                            .withPrimaryKey("d2_id")
                            .withQuery(TableQueryMappingImpl.builder().withName("D2").build())
                            .withLevels(List.of(
                                LevelMappingImpl.builder()
                                    .withName("Name")
                                    .withColumn("name")
                                    .withType(DataType.STRING)
                                    .withUniqueMembers(true)
                                    .build()
                            ))
                            .build()
                    )).build())
                    .build()
            ))
            .withMeasureGroups(List.of(MeasureGroupMappingImpl.builder().withMeasures(List.of(
                MeasureMappingImpl.builder()
                    .withName("Value")
                    .withColumn("value")
                    .withAggregatorType(MeasureAggregatorType.SUM)
                    .withFormatString("#,###")
                    .build()
            )).build()))
            .build());

        result.add(PhysicalCubeMappingImpl.builder()
            .withName("FT2Extra")
            .withQuery(TableQueryMappingImpl.builder().withName("FT2").build())
            .withDimensionConnectors(List.of(
                DimensionConnectorMappingImpl.builder()
                    .withOverrideDimensionName("D1")
                    .withForeignKey("d1_id")
                    .withDimension(StandardDimensionMappingImpl.builder()
                        .withName("D1")
                        .withHierarchies(List.of(
                        HierarchyMappingImpl.builder()
                            .withHasAll(true)
                            .withDefaultMember("[D1].[d]")
                            .withPrimaryKey("d1_id")
                            .withQuery(TableQueryMappingImpl.builder().withName("D1").build())
                            .withLevels(List.of(
                                LevelMappingImpl.builder()
                                    .withName("Name")
                                    .withColumn("name")
                                    .withType(DataType.STRING)
                                    .withUniqueMembers(true)
                                    .build()
                            ))
                            .build()
                    )).build())
                    .build(),
                DimensionConnectorMappingImpl.builder()
                    .withOverrideDimensionName("D2")
                    .withForeignKey("d2_id")
                    	.withDimension(StandardDimensionMappingImpl.builder()
                    	.withName("D2")                    
                    	.withHierarchies(List.of(
                        HierarchyMappingImpl.builder()
                            .withHasAll(false)
                            .withDefaultMember("[D2].[w]")
                            .withPrimaryKey("d2_id")
                            .withQuery(TableQueryMappingImpl.builder().withName("D2").build())
                            .withLevels(List.of(
                                LevelMappingImpl.builder()
                                    .withName("Name")
                                    .withColumn("name")
                                    .withType(DataType.STRING)
                                    .withUniqueMembers(true)
                                    .build()
                            ))
                            .build()
                    )).build())
                    .build()
            ))
            .withMeasureGroups(List.of(MeasureGroupMappingImpl.builder().withMeasures(List.of(
                MeasureMappingImpl.builder()
                    .withName("VExtra")
                    .withColumn("vextra")
                    .withAggregatorType(MeasureAggregatorType.SUM)
                    .withFormatString("#,###")
                    .build(),
                MeasureMappingImpl.builder()
                    .withName("Value")
                    .withColumn("value")
                    .withAggregatorType(MeasureAggregatorType.SUM)
                    .withFormatString("#,###")
                    .build()
            )).build()))
            .build());
        return result;

    }
}
