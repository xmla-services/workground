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
package mondrian.rolap.aggmatcher;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SchemaMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.MeasureAggregatorType;
import org.eclipse.daanse.rolap.mapping.modifier.pojo.PojoMappingModifier;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationColumnNameMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationForeignKeyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationMeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationNameMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DimensionConnectorMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.HierarchyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.LevelMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureGroupMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalCubeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.StandardDimensionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TableQueryMappingImpl;

public class BUG_1541077Modifier extends PojoMappingModifier {

    public BUG_1541077Modifier(CatalogMapping c) {
        super(c);
    }

    /*
        return "<Cube name='Cheques'>\n"
               + "<Table name='cheques'>\n"
               + "<AggName name='agg_lp_xxx_cheques'>\n"
               + "<AggFactCount column='FACT_COUNT'/>\n"
               + "<AggForeignKey factColumn='store_id' aggColumn='store_id' />\n"
               + "<AggMeasure name='[Measures].[Avg Amount]'\n"
               + "   column='amount_AVG' />\n"
               + "</AggName>\n"
                   + "</Table>\n"
                   + "<Dimension name='StoreX' foreignKey='store_id'>\n"
                   + " <Hierarchy hasAll='true' primaryKey='store_id'>\n"
                   + " <Table name='store_x'/>\n"
                   + " <Level name='Store Value' column='value' uniqueMembers='true'/>\n"
                   + " </Hierarchy>\n"
                   + "</Dimension>\n"
                   + "<Dimension name='ProductX' foreignKey='prod_id'>\n"
                   + " <Hierarchy hasAll='true' primaryKey='prod_id'>\n"
                   + " <Table name='product_x'/>\n"
                   + " <Level name='Store Name' column='name' uniqueMembers='true'/>\n"
                   + " </Hierarchy>\n"
                   + "</Dimension>\n"

                   + "<Measure name='Sales Count' \n"
                   + "    column='prod_id' aggregator='count'\n"
                   + "   formatString='#,###'/>\n"
                   + "<Measure name='Store Count' \n"
                   + "    column='store_id' aggregator='distinct-count'\n"
                   + "   formatString='#,###'/>\n"
                   + "<Measure name='Total Amount' \n"
                   + "    column='amount' aggregator='sum'\n"
                   + "   formatString='#,###'/>\n"
                   + "<Measure name='Avg Amount' \n"
                   + "    column='amount' aggregator='avg'\n"
                   + "   formatString='00.0'/>\n"
                   + "</Cube>";

     */

    protected List<? extends CubeMapping> schemaCubes(SchemaMapping schema) {
        List<CubeMapping> result = new ArrayList<>();
        result.addAll(super.schemaCubes(schema));
        result.add(PhysicalCubeMappingImpl.builder()
            .withName("Cheques")
            .withQuery(TableQueryMappingImpl.builder().withName("cheques")
            		.withAggregationTables(List.of(
                            AggregationNameMappingImpl.builder()
                            .withName("agg_lp_xxx_cheques")
                            .withAggregationFactCount(AggregationColumnNameMappingImpl.builder()
                                .withColumn("FACT_COUNT")
                                .build())
                            .withAggregationForeignKeys(List.of(
                            	AggregationForeignKeyMappingImpl.builder()
                                    .withFactColumn("store_id")
                                    .withAggregationColumn("store_id")
                                    .build()
                            ))
                            .withAggregationMeasures(List.of(
                                AggregationMeasureMappingImpl.builder()
                                    .withName("[Measures].[Avg Amount]")
                                    .withColumn("amount_AVG")
                                    .build()
                            ))
                            .build()
            		))
            		.build())
            .withDimensionConnectors(List.of(
            	DimensionConnectorMappingImpl.builder()
            		.withOverrideDimensionName("StoreX")
                    .withForeignKey("store_id")
                    .withDimension(StandardDimensionMappingImpl.builder()
                    	.withName("StoreX")
                    	.withHierarchies(List.of(
                        HierarchyMappingImpl.builder()
                            .withHasAll(true)
                            .withPrimaryKey("store_id")
                            .withQuery(TableQueryMappingImpl.builder().withName("store_x").build())
                            .withLevels(List.of(
                                LevelMappingImpl.builder()
                                    .withName("Store Value")
                                    .withColumn("value")
                                    .withUniqueMembers(true)
                                    .build()
                            ))
                            .build()
                    )).build())
                    .build(),
                DimensionConnectorMappingImpl.builder()
                    .withOverrideDimensionName("ProductX")
                    .withForeignKey("prod_id")
                    .withDimension(StandardDimensionMappingImpl.builder()
                        .withName("StoreX")
                        .withHierarchies(List.of(
                        HierarchyMappingImpl.builder()
                            .withHasAll(true)
                            .withPrimaryKey("prod_id")
                            .withQuery(TableQueryMappingImpl.builder().withName("product_x").build())
                            .withLevels(List.of(
                                LevelMappingImpl.builder()
                                    .withName("Store Name")
                                    .withColumn("name")
                                    .withUniqueMembers(true)
                                    .build()
                            ))
                            .build()
                    )).build())
                    .build()
            ))
            .withMeasureGroups(List.of(MeasureGroupMappingImpl.builder().withMeasures(List.of(
                MeasureMappingImpl.builder()
                    .withName("Sales Count")
                    .withColumn("prod_id")
                    .withAggregatorType(MeasureAggregatorType.COUNT)
                    .withFormatString("#,###")
                    .build(),
                MeasureMappingImpl.builder()
                    .withName("Store Count")
                    .withColumn("store_id")
                    .withAggregatorType(MeasureAggregatorType.DICTINCT_COUNT)
                    .withFormatString("#,###")
                    .build(),
                MeasureMappingImpl.builder()
                    .withName("Total Amount")
                    .withColumn("amount")
                    .withAggregatorType(MeasureAggregatorType.SUM)
                    .withFormatString("#,###")
                    .build(),
                MeasureMappingImpl.builder()
                    .withName("Avg Amount")
                    .withColumn("amount")
                    .withAggregatorType(MeasureAggregatorType.AVG)
                    .withFormatString("00.0")
                    .build()
            )).build()))
            .build());
        return result;

    }
}
