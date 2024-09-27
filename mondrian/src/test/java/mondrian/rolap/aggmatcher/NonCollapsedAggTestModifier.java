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
import org.eclipse.daanse.rolap.mapping.api.model.PhysicalCubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SchemaMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.MeasureAggregatorType;
import org.eclipse.daanse.rolap.mapping.modifier.pojo.PojoMappingModifier;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationColumnNameMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationLevelMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationMeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationNameMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DimensionConnectorMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.HierarchyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.JoinQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.JoinedQueryElementMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.LevelMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureGroupMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalCubeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.StandardDimensionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TableQueryMappingImpl;

public class NonCollapsedAggTestModifier extends PojoMappingModifier {

    public NonCollapsedAggTestModifier(CatalogMapping catalog) {
        super(catalog);
    }

    /*
            "<Cube name=\"foo\">\n"
        + "    <Table name=\"foo_fact\">\n"
        + "        <AggName name=\"agg_tenant\">\n"
        + "            <AggFactCount column=\"fact_count\"/>\n"
        + "            <AggMeasure name=\"[Measures].[Unit Sales]\" column=\"unit_sales\"/>\n"
        + "            <AggLevel name=\"[dimension.tenant].[tenant]\"\n"
        + "                column=\"tenant_id\" collapsed=\"false\"/>\n"
        + "        </AggName>\n"
        + "        <AggName name=\"agg_line_class\">\n"
        + "            <AggFactCount column=\"fact_count\"/>\n"
        + "            <AggMeasure name=\"[Measures].[Unit Sales]\" column=\"unit_sales\"/>\n"
        + "            <AggLevel name=\"[dimension.distributor].[line class]\"\n"
        + "                column=\"line_class_id\" collapsed=\"false\"/>\n"
        + "        </AggName>\n"
        + "        <AggName name=\"agg_line_class\">\n"
        + "            <AggFactCount column=\"fact_count\"/>\n"
        + "            <AggMeasure name=\"[Measures].[Unit Sales]\" column=\"unit_sales\"/>\n"
        + "            <AggLevel name=\"[dimension.network].[line class]\"\n"
        + "                column=\"line_class_id\" collapsed=\"false\"/>\n"
        + "        </AggName>\n"
        + "    </Table>\n"
        + "    <Dimension name=\"dimension\" foreignKey=\"line_id\">\n"
        + "        <Hierarchy name=\"tenant\" hasAll=\"true\" allMemberName=\"All tenants\"\n"
        + "            primaryKey=\"line_id\" primaryKeyTable=\"line\">\n"
        + "            <Join leftKey=\"line_id\" rightKey=\"line_id\"\n"
        + "                rightAlias=\"line_tenant\">\n"
        + "                <Table name=\"line\"/>\n"
        + "                <Join leftKey=\"tenant_id\" rightKey=\"tenant_id\">\n"
        + "                    <Table name=\"line_tenant\"/>\n"
        + "                    <Table name=\"tenant\"/>\n"
        + "                </Join>\n"
        + "            </Join>\n"
        + "            <Level name=\"tenant\" table=\"tenant\" column=\"tenant_id\" nameColumn=\"tenant_name\" uniqueMembers=\"true\"/>\n"
        + "            <Level name=\"line\" table=\"line\" column=\"line_id\" nameColumn=\"line_name\"/>\n"
        + "        </Hierarchy>\n"
        + "        <Hierarchy name=\"distributor\" hasAll=\"true\" allMemberName=\"All distributors\"\n"
        + "            primaryKey=\"line_id\" primaryKeyTable=\"line\">\n"
        + "            <Join leftKey=\"line_id\" rightKey=\"line_id\" rightAlias=\"line_line_class\">\n"
        + "                <Table name=\"line\"/>\n"
        + "                <Join leftKey=\"line_class_id\" rightKey=\"line_class_id\" rightAlias=\"line_class\">\n"
        + "                    <Table name=\"line_line_class\"/>\n"
        + "                    <Join leftKey=\"line_class_id\" rightKey=\"line_class_id\" rightAlias=\"line_class_distributor\">\n"
        + "                        <Table name=\"line_class\"/>\n"
        + "                        <Join leftKey=\"distributor_id\" rightKey=\"distributor_id\">\n"
        + "                            <Table name=\"line_class_distributor\"/>\n"
        + "                            <Table name=\"distributor\"/>\n"
        + "                        </Join>\n"
        + "                    </Join>\n"
        + "                </Join>\n"
        + "            </Join>\n"
        + "            <Level name=\"distributor\" table=\"distributor\" column=\"distributor_id\" nameColumn=\"distributor_name\"/>\n"
        + "            <Level name=\"line class\" table=\"line_class\" column=\"line_class_id\" nameColumn=\"line_class_name\" uniqueMembers=\"true\"/>\n"
        + "            <Level name=\"line\" table=\"line\" column=\"line_id\" nameColumn=\"line_name\"/>\n"
        + "        </Hierarchy>\n"
        + "        <Hierarchy name=\"network\" hasAll=\"true\" allMemberName=\"All networks\"\n"
        + "            primaryKey=\"line_id\" primaryKeyTable=\"line\">\n"
        + "            <Join leftKey=\"line_id\" rightKey=\"line_id\" rightAlias=\"line_line_class\">\n"
        + "                <Table name=\"line\"/>\n"
        + "                <Join leftKey=\"line_class_id\" rightKey=\"line_class_id\" rightAlias=\"line_class\">\n"
        + "                    <Table name=\"line_line_class\"/>\n"
        + "                    <Join leftKey=\"line_class_id\" rightKey=\"line_class_id\" rightAlias=\"line_class_network\">\n"
        + "                        <Table name=\"line_class\"/>\n"
        + "                        <Join leftKey=\"network_id\" rightKey=\"network_id\">\n"
        + "                            <Table name=\"line_class_network\"/>\n"
        + "                            <Table name=\"network\"/>\n"
        + "                        </Join>\n"
        + "                    </Join>\n"
        + "                </Join>\n"
        + "            </Join>\n"
        + "            <Level name=\"network\" table=\"network\" column=\"network_id\" nameColumn=\"network_name\"/>\n"
        + "            <Level name=\"line class\" table=\"line_class\" column=\"line_class_id\" nameColumn=\"line_class_name\" uniqueMembers=\"true\"/>\n"
        + "            <Level name=\"line\" table=\"line\" column=\"line_id\" nameColumn=\"line_name\"/>\n"
        + "        </Hierarchy>\n"
        + " </Dimension>\n"
        + "   <Measure name=\"Unit Sales\" column=\"unit_sales\" aggregator=\"sum\" formatString=\"Standard\" />\n"
        + "</Cube>\n"
        + "<Cube name=\"foo2\">\n"
        + "    <Table name=\"foo_fact\">\n"
        + "    </Table>\n"
        + "    <Dimension name=\"dimension\" foreignKey=\"line_id\">\n"
        + "        <Hierarchy name=\"tenant\" hasAll=\"true\" allMemberName=\"All tenants\"\n"
        + "            primaryKey=\"line_id\" primaryKeyTable=\"line\">\n"
        + "            <Join leftKey=\"line_id\" rightKey=\"line_id\"\n"
        + "                rightAlias=\"line_tenant\">\n"
        + "                <Table name=\"line\"/>\n"
        + "                <Join leftKey=\"tenant_id\" rightKey=\"tenant_id\">\n"
        + "                    <Table name=\"line_tenant\"/>\n"
        + "                    <Table name=\"tenant\"/>\n"
        + "                </Join>\n"
        + "            </Join>\n"
        + "            <Level name=\"tenant\" table=\"tenant\" column=\"tenant_id\" nameColumn=\"tenant_name\" uniqueMembers=\"true\"/>\n"
        + "            <Level name=\"line\" table=\"line\" column=\"line_id\" nameColumn=\"line_name\"/>\n"
        + "        </Hierarchy>\n"
        + "        <Hierarchy name=\"distributor\" hasAll=\"true\" allMemberName=\"All distributors\"\n"
        + "            primaryKey=\"line_id\" primaryKeyTable=\"line\">\n"
        + "            <Join leftKey=\"line_id\" rightKey=\"line_id\" rightAlias=\"line_line_class\">\n"
        + "                <Table name=\"line\"/>\n"
        + "                <Join leftKey=\"line_class_id\" rightKey=\"line_class_id\" rightAlias=\"line_class\">\n"
        + "                    <Table name=\"line_line_class\"/>\n"
        + "                    <Join leftKey=\"line_class_id\" rightKey=\"line_class_id\" rightAlias=\"line_class_distributor\">\n"
        + "                        <Table name=\"line_class\"/>\n"
        + "                        <Join leftKey=\"distributor_id\" rightKey=\"distributor_id\">\n"
        + "                            <Table name=\"line_class_distributor\"/>\n"
        + "                            <Table name=\"distributor\"/>\n"
        + "                        </Join>\n"
        + "                    </Join>\n"
        + "                </Join>\n"
        + "            </Join>\n"
        + "            <Level name=\"distributor\" table=\"distributor\" column=\"distributor_id\" nameColumn=\"distributor_name\"/>\n"
        + "            <Level name=\"line class\" table=\"line_class\" column=\"line_class_id\" nameColumn=\"line_class_name\" uniqueMembers=\"true\"/>\n"
        + "            <Level name=\"line\" table=\"line\" column=\"line_id\" nameColumn=\"line_name\"/>\n"
        + "        </Hierarchy>\n"
        + "        <Hierarchy name=\"network\" hasAll=\"true\" allMemberName=\"All networks\"\n"
        + "            primaryKey=\"line_id\" primaryKeyTable=\"line\">\n"
        + "            <Join leftKey=\"line_id\" rightKey=\"line_id\" rightAlias=\"line_line_class\">\n"
        + "                <Table name=\"line\"/>\n"
        + "                <Join leftKey=\"line_class_id\" rightKey=\"line_class_id\" rightAlias=\"line_class\">\n"
        + "                    <Table name=\"line_line_class\"/>\n"
        + "                    <Join leftKey=\"line_class_id\" rightKey=\"line_class_id\" rightAlias=\"line_class_network\">\n"
        + "                        <Table name=\"line_class\"/>\n"
        + "                        <Join leftKey=\"network_id\" rightKey=\"network_id\">\n"
        + "                            <Table name=\"line_class_network\"/>\n"
        + "                            <Table name=\"network\"/>\n"
        + "                        </Join>\n"
        + "                    </Join>\n"
        + "                </Join>\n"
        + "            </Join>\n"
        + "            <Level name=\"network\" table=\"network\" column=\"network_id\" nameColumn=\"network_name\"/>\n"
        + "            <Level name=\"line class\" table=\"line_class\" column=\"line_class_id\" nameColumn=\"line_class_name\" uniqueMembers=\"true\"/>\n"
        + "            <Level name=\"line\" table=\"line\" column=\"line_id\" nameColumn=\"line_name\"/>\n"
        + "        </Hierarchy>\n"
        + " </Dimension>\n"
        + "   <Measure name=\"Unit Sales\" column=\"unit_sales\" aggregator=\"sum\" formatString=\"Standard\" />\n"
        + "</Cube>\n";

     */

    @Override
    protected List<? extends CubeMapping> schemaCubes(SchemaMapping schemaMappingOriginal) {
        List<CubeMapping> result = new ArrayList<>();
        result.add(PhysicalCubeMappingImpl.builder()
        	.withName("foo")
            .withQuery(TableQueryMappingImpl.builder().withName("foo_fact")
            	.withAggregationTables(List.of(
                	AggregationNameMappingImpl.builder()
                        .withName("agg_tenant")
                        .withAggregationFactCount(AggregationColumnNameMappingImpl.builder()
                            .withColumn("fact_count")
                            .build())
                        .withAggregationMeasures(List.of(
                        		AggregationMeasureMappingImpl.builder()
                                .withName("[Measures].[Unit Sales]").withColumn("unit_sales")
                                .build()
                        ))
                        .withAggregationLevels(List.of(
                        		AggregationLevelMappingImpl.builder()
                                .withName("[dimension.tenant].[tenant]")
                                .withColumn("tenant_id").withCollapsed(false)
                                .build()
                        ))
                        .build(),
                    AggregationNameMappingImpl.builder()
                        .withName("agg_line_class")
                        .withAggregationFactCount(AggregationColumnNameMappingImpl.builder()
                            .withColumn("fact_count")
                            .build())
                        .withAggregationMeasures(List.of(
                            AggregationMeasureMappingImpl.builder()
                                .withName("[Measures].[Unit Sales]").withColumn("unit_sales")
                                .build()
                        ))
                        .withAggregationLevels(List.of(
                            AggregationLevelMappingImpl.builder()
                                .withName("[dimension.distributor].[line class]")
                                .withColumn("line_class_id").withCollapsed(false)
                                .build()
                        ))
                        .build(),
                    AggregationNameMappingImpl.builder()
                        .withName("agg_line_class")
                        .withAggregationFactCount(AggregationColumnNameMappingImpl.builder()
                            .withColumn("fact_count")
                            .build())
                        .withAggregationMeasures(List.of(
                            AggregationMeasureMappingImpl.builder()
                                .withName("[Measures].[Unit Sales]").withColumn("unit_sales")
                                .build()
                        ))
                        .withAggregationLevels(List.of(
                            AggregationLevelMappingImpl.builder()
                                .withName("[dimension.network].[line class]")
                                .withColumn("line_class_id").withCollapsed(false)
                                .build()
                        ))
                        .build()
             )).build())
            .withDimensionConnectors(List.of(
            	DimensionConnectorMappingImpl.builder()
            		.withOverrideDimensionName("dimension")
                    .withForeignKey("line_id")
                    .withDimension(StandardDimensionMappingImpl.builder()
                    	.withName("dimension")
                    	.withHierarchies(List.of(
                        HierarchyMappingImpl.builder()
                            .withName("tenant")
                            .withHasAll(true)
                            .withAllMemberName("All tenants")
                            .withPrimaryKey("line_id")
                            .withPrimaryKeyTable("line")
                            .withQuery(JoinQueryMappingImpl.builder()
                            		.withLeft(JoinedQueryElementMappingImpl.builder()
                            				.withKey("line_id")
                            				.withQuery(TableQueryMappingImpl.builder().withName("line").build())
                            				.build())
                            		.withRight(JoinedQueryElementMappingImpl.builder()
                            				.withAlias("line_tenant")
                            				.withKey("line_id")
                                            .withQuery(JoinQueryMappingImpl.builder()
                                            		.withLeft(JoinedQueryElementMappingImpl.builder()
                                            				.withKey("tenant_id")
                                            				.withQuery(TableQueryMappingImpl.builder().withName("line_tenant").build())
                                            				.build())
                                            		.withRight(JoinedQueryElementMappingImpl.builder()
                                            				.withKey("tenant_id")
                                            				.withQuery(TableQueryMappingImpl.builder().withName("tenant").build())
                                            				.build())
                                            		.build())
                            				.build())
                            		.build())
                            .withLevels(List.of(
                                LevelMappingImpl.builder()
                                    .withName("tenant")
                                    .withTable("tenant")
                                    .withColumn("tenant_id")
                                    .withNameColumn("tenant_name")
                                    .withUniqueMembers(true)
                                    .build(),
                                LevelMappingImpl.builder()
                                    .withName("line")
                                    .withTable("line")
                                    .withColumn("line_id")
                                    .withNameColumn("line_name")
                                    .build()

                            ))
                            .build(),
                        HierarchyMappingImpl.builder()
                            .withName("distributor")
                            .withHasAll(true)
                            .withAllMemberName("All distributors")
                            .withPrimaryKey("line_id")
                            .withPrimaryKeyTable("line")
                            .withQuery(JoinQueryMappingImpl.builder()
                            		.withLeft(JoinedQueryElementMappingImpl.builder()
                            				.withKey("line_id")
                            				.withQuery(TableQueryMappingImpl.builder().withName("line").build())
                            				.build())
                            		.withRight(JoinedQueryElementMappingImpl.builder()
                            				.withAlias("line_line_class")
                            				.withKey("line_id")
                                            .withQuery(JoinQueryMappingImpl.builder()
                                            		.withLeft(JoinedQueryElementMappingImpl.builder()
                                            				.withKey("line_class_id")
                                            				.withQuery(TableQueryMappingImpl.builder().withName("line_line_class").build())
                                            				.build())
                                            		.withRight(JoinedQueryElementMappingImpl.builder()
                                            				.withAlias("line_class")
                                            				.withKey("line_class_id")
                                                            .withQuery(JoinQueryMappingImpl.builder()
                                                            		.withLeft(JoinedQueryElementMappingImpl.builder()
                                                            				.withKey("line_class_id")
                                                            				.withQuery(TableQueryMappingImpl.builder().withName("line_class").build())
                                                            				.build())
                                                            		.withRight(JoinedQueryElementMappingImpl.builder()
                                                            				.withAlias("line_class_distributor")
                                                            				.withKey("line_class_id")
                                                                            .withQuery(JoinQueryMappingImpl.builder()
                                                                            		.withLeft(JoinedQueryElementMappingImpl.builder()
                                                                            				.withKey("distributor_id")
                                                                            				.withQuery(TableQueryMappingImpl.builder().withName("line_class_distributor").build())
                                                                            				.build())
                                                                            		.withRight(JoinedQueryElementMappingImpl.builder()
                                                                            				.withKey("distributor_id")
                                                                            				.withQuery(TableQueryMappingImpl.builder().withName("distributor").build())
                                                                            				.build())
                                                                            		.build())
                                                            				.build())
                                                            		.build())

                                            				.build())
                                            		.build())
                            				.build())
                            		.build())
                            .withLevels(List.of(
                                LevelMappingImpl.builder()
                                    .withName("distributor")
                                    .withTable("distributor")
                                    .withColumn("distributor_id")
                                    .withNameColumn("distributor_name")
                                    .build(),
                                LevelMappingImpl.builder()
                                    .withName("line class")
                                    .withTable("line_class")
                                    .withColumn("line_class_id")
                                    .withNameColumn("line_class_name")
                                    .withUniqueMembers(true)
                                    .build(),
                                LevelMappingImpl.builder()
                                    .withName("line")
                                    .withTable("line")
                                    .withColumn("line_id")
                                    .withNameColumn("line_name")
                                    .build()
                            ))
                            .build(),
                        HierarchyMappingImpl.builder()
                            .withName("network")
                            .withHasAll(true)
                            .withAllMemberName("All networks")
                            .withPrimaryKey("line_id")
                            .withPrimaryKeyTable("line")
                            .withQuery(JoinQueryMappingImpl.builder()
                            		.withLeft(JoinedQueryElementMappingImpl.builder()
                            				.withKey("line_id")
                            				.withQuery(TableQueryMappingImpl.builder().withName("line").build())
                            				.build())
                            		.withRight(JoinedQueryElementMappingImpl.builder()
                            				.withAlias("line_line_class")
                            				.withKey("line_id")
                                            .withQuery(JoinQueryMappingImpl.builder()
                                            		.withLeft(JoinedQueryElementMappingImpl.builder()
                                            				.withKey("line_class_id")
                                            				.withQuery(TableQueryMappingImpl.builder().withName("line_line_class").build())
                                            				.build())
                                            		.withRight(JoinedQueryElementMappingImpl.builder()
                                            				.withAlias("line_class")
                                            				.withKey("line_class_id")
                                                            .withQuery(JoinQueryMappingImpl.builder()
                                                            		.withLeft(JoinedQueryElementMappingImpl.builder()
                                                            				.withKey("line_class_id")
                                                            				.withQuery(TableQueryMappingImpl.builder().withName("line_class").build())
                                                            				.build())
                                                            		.withRight(JoinedQueryElementMappingImpl.builder()
                                                            				.withAlias("line_class_network")
                                                            				.withKey("line_class_id")
                                                                            .withQuery(JoinQueryMappingImpl.builder()
                                                                            		.withLeft(JoinedQueryElementMappingImpl.builder()
                                                                            				.withKey("network_id")
                                                                            				.withQuery(TableQueryMappingImpl.builder().withName("line_class_network").build())
                                                                            				.build())
                                                                            		.withRight(JoinedQueryElementMappingImpl.builder()
                                                                            				.withKey("network_id")
                                                                            				.withQuery(TableQueryMappingImpl.builder().withName("network").build())
                                                                            				.build())
                                                                            		.build())
                                                            				.build())
                                                            		.build())

                                            				.build())
                                            		.build())
                            				.build())
                            		.build())
                            .withLevels(List.of(
                                LevelMappingImpl.builder()
                                    .withName("network")
                                    .withTable("network")
                                    .withColumn("network_id")
                                    .withNameColumn("network_name")
                                    .build(),
                                LevelMappingImpl.builder()
                                    .withName("line class")
                                    .withTable("line_class")
                                    .withColumn("line_class_id")
                                    .withNameColumn("line_class_name")
                                    .withUniqueMembers(true)
                                    .build(),
                                LevelMappingImpl.builder()
                                    .withName("line")
                                    .withTable("line")
                                    .withColumn("line_id")
                                    .withNameColumn("line_name")
                                    .build()
                            ))
                            .build()
                    )).build())
                    .build()
            ))
            .withMeasureGroups(List.of(MeasureGroupMappingImpl.builder().withMeasures(List.of(
                MeasureMappingImpl.builder()
                    .withName("Unit Sales")
                    .withColumn("unit_sales")
                    .withAggregatorType(MeasureAggregatorType.SUM)
                    .withFormatString("Standard")
                    .build()

            )).build()))
            .build());

        result.add(PhysicalCubeMappingImpl.builder()
            .withName("foo2")
            .withQuery(TableQueryMappingImpl.builder().withName("foo_fact").build())
            .withDimensionConnectors(List.of(
                DimensionConnectorMappingImpl.builder()
                	.withOverrideDimensionName("dimension")
                    .withForeignKey("line_id")
                    .withDimension(StandardDimensionMappingImpl.builder()
                    	.withName("dimension")
                    	.withHierarchies(List.of(
                        HierarchyMappingImpl.builder()
                            .withName("tenant")
                            .withHasAll(true)
                            .withAllMemberName("All tenants")
                            .withPrimaryKey("line_id")
                            .withPrimaryKeyTable("line")
                            .withQuery(JoinQueryMappingImpl.builder()
                            		.withLeft(JoinedQueryElementMappingImpl.builder()
                            				.withKey("line_id")
                            				.withQuery(TableQueryMappingImpl.builder().withName("line").build())
                            				.build())
                            		.withRight(JoinedQueryElementMappingImpl.builder()
                            				.withAlias("line_tenant")
                            				.withKey("line_id")
                                            .withQuery(JoinQueryMappingImpl.builder()
                                            		.withLeft(JoinedQueryElementMappingImpl.builder()
                                            				.withKey("tenant_id")
                                            				.withQuery(TableQueryMappingImpl.builder().withName("line_tenant").build())
                                            				.build())
                                            		.withRight(JoinedQueryElementMappingImpl.builder()
                                            				.withKey("tenant_id")
                                            				.withQuery(TableQueryMappingImpl.builder().withName("tenant").build())
                                            				.build())
                                            		.build())
                            				.build())
                            		.build())
                            .withLevels(List.of(
                                LevelMappingImpl.builder()
                                    .withName("tenant")
                                    .withTable("tenant")
                                    .withColumn("tenant_id")
                                    .withNameColumn("tenant_name")
                                    .withUniqueMembers(true)
                                    .build(),
                                LevelMappingImpl.builder()
                                    .withName("line")
                                    .withTable("line")
                                    .withColumn("line_id")
                                    .withNameColumn("line_name")
                                    .build()
                            ))
                            .build(),
                        HierarchyMappingImpl.builder()
                            .withName("distributor")
                            .withHasAll(true)
                            .withAllMemberName("All distributors")
                            .withPrimaryKey("line_id")
                            .withPrimaryKeyTable("line")
                            .withQuery(JoinQueryMappingImpl.builder()
                            		.withLeft(JoinedQueryElementMappingImpl.builder()
                            				.withKey("line_id")
                            				.withQuery(TableQueryMappingImpl.builder().withName("line").build())
                            				.build())
                            		.withRight(JoinedQueryElementMappingImpl.builder()
                            				.withAlias("line_line_class")
                            				.withKey("line_id")
                                            .withQuery(JoinQueryMappingImpl.builder()
                                            		.withLeft(JoinedQueryElementMappingImpl.builder()
                                            				.withKey("line_class_id")
                                            				.withQuery(TableQueryMappingImpl.builder().withName("line_line_class").build())
                                            				.build())
                                            		.withRight(JoinedQueryElementMappingImpl.builder()
                                            				.withAlias("line_class")
                                            				.withKey("line_class_id")
                                                            .withQuery(JoinQueryMappingImpl.builder()
                                                            		.withLeft(JoinedQueryElementMappingImpl.builder()
                                                            				.withKey("line_class_id")
                                                            				.withQuery(TableQueryMappingImpl.builder().withName("line_class").build())
                                                            				.build())
                                                            		.withRight(JoinedQueryElementMappingImpl.builder()
                                                            				.withAlias("line_class")
                                                            				.withKey("line_class_id")
                                                                            .withQuery(JoinQueryMappingImpl.builder()
                                                                            		.withLeft(JoinedQueryElementMappingImpl.builder()
                                                                            				.withKey("line_class_id")
                                                                            				.withQuery(TableQueryMappingImpl.builder().withName("line_class").build())
                                                                            				.build())
                                                                            		.withRight(JoinedQueryElementMappingImpl.builder()
                                                                            				.withAlias("line_class_distributor")
                                                                            				.withKey("line_class_id")
                                                                            				.withQuery(JoinQueryMappingImpl.builder()
                                                                                            		.withLeft(JoinedQueryElementMappingImpl.builder()
                                                                                            				.withKey("distributor_id")
                                                                                            				.withQuery(TableQueryMappingImpl.builder().withName("line_class_distributor").build())
                                                                                            				.build())
                                                                                            		.withRight(JoinedQueryElementMappingImpl.builder()
                                                                                            				.withKey("distributor_id")
                                                                                            				.withQuery(TableQueryMappingImpl.builder().withName("distributor").build())
                                                                                            				.build())
                                                                                            		.build())
                                                                            				.build())
                                                                            		.build())
                                                            				.build())
                                                            		.build())

                                            				.build())
                                            		.build())
                            				.build())
                            		.build())
                            .withLevels(List.of(
                                LevelMappingImpl.builder()
                                    .withName("distributor")
                                    .withTable("distributor")
                                    .withColumn("distributor_id")
                                    .withNameColumn("distributor_name")
                                    .build(),
                                LevelMappingImpl.builder()
                                    .withName("line class")
                                    .withTable("line_class")
                                    .withColumn("line_class_id")
                                    .withNameColumn("line_class_name")
                                    .withUniqueMembers(true)
                                    .build(),
                                LevelMappingImpl.builder()
                                    .withName("line")
                                    .withTable("line")
                                    .withColumn("line_id")
                                    .withNameColumn("line_name")
                                    .build()
                            ))
                            .build(),
                        HierarchyMappingImpl.builder()
                            .withName("network")
                            .withHasAll(true)
                            .withAllMemberName("All networks")
                            .withPrimaryKey("line_id")
                            .withPrimaryKeyTable("line")


                            .withQuery(JoinQueryMappingImpl.builder()
                            		.withLeft(JoinedQueryElementMappingImpl.builder()
                            				.withKey("line_id")
                            				.withQuery(TableQueryMappingImpl.builder().withName("line").build())
                            				.build())
                            		.withRight(JoinedQueryElementMappingImpl.builder()
                            				.withAlias("line_line_class").withKey("line_id")
                                            .withQuery(JoinQueryMappingImpl.builder()
                                            		.withLeft(JoinedQueryElementMappingImpl.builder()
                                            				.withKey("line_class_id")
                                            				.withQuery(TableQueryMappingImpl.builder().withName("line_line_class").build())
                                            				.build())
                                            		.withRight(JoinedQueryElementMappingImpl.builder()
                                            				.withAlias("line_class").withKey("line_class_id")//-
                                                            .withQuery(JoinQueryMappingImpl.builder()
                                                            		.withLeft(JoinedQueryElementMappingImpl.builder()
                                                            				.withKey("line_class_id")
                                                            				.withQuery(TableQueryMappingImpl.builder().withName("line_class").build()) //----
                                                            				.build())
                                                            		.withRight(JoinedQueryElementMappingImpl.builder()
                                                            				.withAlias("line_class_network").withKey("line_class_id") //++
                                                                            .withQuery(JoinQueryMappingImpl.builder()
                                                                            		.withLeft(JoinedQueryElementMappingImpl.builder()
                                                                            				.withKey("network_id")
                                                                            				.withQuery(TableQueryMappingImpl.builder().withName("line_class_network").build())
                                                                            				.build())
                                                                            		.withRight(JoinedQueryElementMappingImpl.builder()
                                                                            				.withKey("network_id")
                                                                            				.withQuery(TableQueryMappingImpl.builder().withName("network").build())
                                                                            				.build())
                                                                            		.build())
                                                            				.build())
                                                            		.build())

                                            				.build())
                                            		.build())
                            				.build())
                            .build())
                            .withLevels(List.of(
                                LevelMappingImpl.builder()
                                    .withName("network")
                                    .withTable("network")
                                    .withColumn("network_id")
                                    .withNameColumn("network_name")
                                    .build(),
                                LevelMappingImpl.builder()
                                    .withName("line class")
                                    .withTable("line_class")
                                    .withColumn("line_class_id")
                                    .withNameColumn("line_class_name")
                                    .withUniqueMembers(true)
                                    .build(),
                                LevelMappingImpl.builder()
                                    .withName("line")
                                    .withTable("line")
                                    .withColumn("line_id")
                                    .withNameColumn("line_name")
                                    .build()
                            ))
                            .build()
                    )).build())
                    .build()
            ))
            .withMeasureGroups(List.of(MeasureGroupMappingImpl.builder().withMeasures(List.of(
                MeasureMappingImpl.builder()
                    .withName("Unit Sales")
                    .withColumn("unit_sales")
                    .withAggregatorType(MeasureAggregatorType.SUM)
                    .withFormatString("Standard")
                    .build()
            )).build()))
            .build());
        result.addAll(super.schemaCubes(schemaMappingOriginal));
        return result;
    }
}
