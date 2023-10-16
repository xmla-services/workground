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
 *   SmartCity Jena - initial
 *   Stefan Bischof (bipolis.org) - initial
 */
package mondrian.rolap;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingHierarchy;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingInlineTable;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingLevel;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingPrivateDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingRole;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingTable;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingVirtualCube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.AccessEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.DimensionTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.HideMemberIfEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.LevelTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.MeasureDataTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.MemberGrantAccessEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.TypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.JoinR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.TableR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.CalculatedMemberPropertyRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.CalculatedMemberRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.ClosureRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.ColumnDefRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.CubeGrantRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.CubeRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.CubeUsageRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.DimensionUsageRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.FormulaRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.HierarchyGrantRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.HierarchyRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.InlineTableRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.LevelRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.MeasureRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.MemberGrantRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.PrivateDimensionRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.PropertyRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.RoleRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.RowRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.SchemaGrantRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.SchemaRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.ValueRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.VirtualCubeDimensionRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.VirtualCubeMeasureRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.VirtualCubeRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.modifier.record.RDbMappingSchemaModifier;

import java.util.ArrayList;
import java.util.List;

public class SchemaModifiers {

    /*
            + "<Role name=\"No_WA_State\">\n"
                + "  <SchemaGrant access=\"none\">\n"
                + "    <CubeGrant cube=\"Sales\" access=\"all\">\n"
                + "      <HierarchyGrant hierarchy=\"[Customers]\" access=\"custom\" rollupPolicy=\"partial\">\n"
                + "        <MemberGrant member=\"[Customers].[USA].[WA]\" access=\"none\"/>\n"
                + "        <MemberGrant member=\"[Customers].[USA].[OR]\" access=\"all\"/>\n"
                + "        <MemberGrant member=\"[Customers].[USA].[CA]\" access=\"all\"/>\n"
                + "        <MemberGrant member=\"[Customers].[Canada]\" access=\"all\"/>\n"
                + "        <MemberGrant member=\"[Customers].[Mexico]\" access=\"all\"/>\n"
                + "      </HierarchyGrant>\n"
                + "    </CubeGrant>\n"
                + "  </SchemaGrant>\n"
                + "</Role>\n";
    */
    public static class RoleRestrictionWorksWaRoleDef extends RDbMappingSchemaModifier {

        public RoleRestrictionWorksWaRoleDef(MappingSchema mappingSchema) {
            super(mappingSchema);
        }

        @Override
        protected List<MappingRole> schemaRoles(MappingSchema mappingSchemaOriginal) {
            List<MappingRole> result = new ArrayList<>();
            result.addAll(super.schemaRoles(mappingSchemaOriginal));
            result.add(RoleRBuilder.builder()
                .name("No_WA_State")
                .schemaGrants(List.of(
                    SchemaGrantRBuilder.builder()
                        .access(AccessEnum.NONE)
                        .cubeGrants(List.of(
                            CubeGrantRBuilder.builder()
                                .cube("Sales")
                                .access("all")
                                .hierarchyGrants(List.of(
                                    HierarchyGrantRBuilder.builder()
                                        .hierarchy("[Customers]")
                                        .access(AccessEnum.CUSTOM)
                                        .rollupPolicy("partial")
                                        .memberGrants(List.of(
                                            MemberGrantRBuilder.builder()
                                                .member("[Customers].[USA].[WA]")
                                                .access(MemberGrantAccessEnum.NONE)
                                                .build(),
                                            MemberGrantRBuilder.builder()
                                                .member("[Customers].[USA].[OR]")
                                                .access(MemberGrantAccessEnum.ALL)
                                                .build(),
                                            MemberGrantRBuilder.builder()
                                                .member("[Customers].[USA].[CA]")
                                                .access(MemberGrantAccessEnum.ALL)
                                                .build(),
                                            MemberGrantRBuilder.builder()
                                                .member("[Customers].[Canada]")
                                                .access(MemberGrantAccessEnum.ALL)
                                                .build(),
                                            MemberGrantRBuilder.builder()
                                                .member("[Customers].[Mexico]")
                                                .access(MemberGrantAccessEnum.ALL)
                                                .build()
                                        ))
                                        .build()
                                ))
                                .build()
                        ))
                        .build()
                ))
                .build());
            return result;
        }
    }

    /*
            + "<Role name=\"Only_DF_State\">\n"
                + "  <SchemaGrant access=\"none\">\n"
                + "    <CubeGrant cube=\"Sales\" access=\"all\">\n"
                + "      <HierarchyGrant hierarchy=\"[Customers]\" access=\"custom\" rollupPolicy=\"partial\">\n"
                + "        <MemberGrant member=\"[Customers].[USA].[WA]\" access=\"all\"/>\n"
                + "        <MemberGrant member=\"[Customers].[USA].[OR]\" access=\"all\"/>\n"
                + "        <MemberGrant member=\"[Customers].[USA].[CA]\" access=\"all\"/>\n"
                + "        <MemberGrant member=\"[Customers].[Canada]\" access=\"all\"/>\n"
                + "        <MemberGrant member=\"[Customers].[Mexico].[DF]\" access=\"all\"/>\n"
                + "      </HierarchyGrant>\n"
                + "    </CubeGrant>\n"
                + "  </SchemaGrant>\n"
                + "</Role>\n";
    */
    public static class RoleRestrictionWorksDfRoleDef extends RDbMappingSchemaModifier {

        public RoleRestrictionWorksDfRoleDef(MappingSchema mappingSchema) {
            super(mappingSchema);
        }

        @Override
        protected List<MappingRole> schemaRoles(MappingSchema mappingSchemaOriginal) {
            List<MappingRole> result = new ArrayList<>();
            result.addAll(super.schemaRoles(mappingSchemaOriginal));
            result.add(RoleRBuilder.builder()
                .name("Only_DF_State")
                .schemaGrants(List.of(
                    SchemaGrantRBuilder.builder()
                        .access(AccessEnum.NONE)
                        .cubeGrants(List.of(
                            CubeGrantRBuilder.builder()
                                .cube("Sales")
                                .access("all")
                                .hierarchyGrants(List.of(
                                    HierarchyGrantRBuilder.builder()
                                        .hierarchy("[Customers]")
                                        .access(AccessEnum.CUSTOM)
                                        .rollupPolicy("partial")
                                        .memberGrants(List.of(
                                            MemberGrantRBuilder.builder()
                                                .member("[Customers].[USA].[WA]")
                                                .access(MemberGrantAccessEnum.ALL)
                                                .build(),
                                            MemberGrantRBuilder.builder()
                                                .member("[Customers].[USA].[OR]")
                                                .access(MemberGrantAccessEnum.ALL)
                                                .build(),
                                            MemberGrantRBuilder.builder()
                                                .member("[Customers].[USA].[CA]")
                                                .access(MemberGrantAccessEnum.ALL)
                                                .build(),
                                            MemberGrantRBuilder.builder()
                                                .member("[Customers].[Canada]")
                                                .access(MemberGrantAccessEnum.ALL)
                                                .build(),
                                            MemberGrantRBuilder.builder()
                                                .member("[Customers].[Mexico].[DF]")
                                                .access(MemberGrantAccessEnum.ALL)
                                                .build()
                                        ))
                                        .build()
                                ))
                                .build()
                        ))
                        .build()
                ))
                .build());
            return result;
        }
    }

    public static class CustomCountMeasureCubeName extends RDbMappingSchemaModifier {

        public CustomCountMeasureCubeName(MappingSchema mappingSchema) {
            super(mappingSchema);
        }

        @Override
        protected List<MappingCube> cubes(List<MappingCube> cubes) {
            List<MappingCube> result = new ArrayList<>();
            result.addAll(super.cubes(cubes));
            result.add(CubeRBuilder.builder()
                .name("StoreWithCountM")
                .fact(new TableR("store"))
                .dimensionUsageOrDimensions(List.of(
                    PrivateDimensionRBuilder.builder()
                        .visible(true)
                        .highCardinality(false)
                        .name("Store Type")
                        .hierarchies(List.of(
                            HierarchyRBuilder.builder()
                                .visible(true)
                                .hasAll(true)
                                .levels(List.of(
                                    LevelRBuilder.builder()
                                        .name("Store Type")
                                        .visible(true)
                                        .column("store_type")
                                        .type(TypeEnum.STRING)
                                        .uniqueMembers(true)
                                        .levelType(LevelTypeEnum.REGULAR)
                                        .hideMemberIf(HideMemberIfEnum.NEVER)
                                        .build()
                                ))
                                .build()
                        ))
                        .build(),
                    DimensionUsageRBuilder.builder()
                        .source("Store")
                        .name("Store")
                        .visible(true)
                        .highCardinality(false)
                        .build(),
                    PrivateDimensionRBuilder.builder()
                        .visible(true)
                        .highCardinality(false)
                        .name("Has coffee bar")
                        .hierarchies(List.of(
                            HierarchyRBuilder.builder()
                                .visible(true)
                                .hasAll(true)
                                .levels(List.of(
                                    LevelRBuilder.builder()
                                        .name("Has coffee bar")
                                        .visible(true)
                                        .column("coffee_bar")
                                        .type(TypeEnum.BOOLEAN)
                                        .uniqueMembers(true)
                                        .levelType(LevelTypeEnum.REGULAR)
                                        .hideMemberIf(HideMemberIfEnum.NEVER)
                                        .build()
                                ))
                                .build()
                        ))
                        .build()
                ))
                .measures(List.of(
                    MeasureRBuilder.builder()
                        .name("Store Sqft")
                        .column("store_sqft")
                        .formatString("#,###")
                        .aggregator("sum")
                        .build(),
                    MeasureRBuilder.builder()
                        .name("Grocery Sqft")
                        .column("grocery_sqft")
                        .formatString("#,###")
                        .aggregator("sum")
                        .build(),
                    MeasureRBuilder.builder()
                        .name("CountM")
                        .column("store_id")
                        .formatString("Standard")
                        .aggregator("count")
                        .visible(true)
                        .build()
                ))
                .build());
            return result;
        }
    }

    public static class SharedDimensionTestModifier extends RDbMappingSchemaModifier {

        public SharedDimensionTestModifier(MappingSchema mappingSchema) {
            super(mappingSchema);
        }

        @Override
        protected List<MappingCube> schemaCubes(MappingSchema schema) {
            List<MappingCube> result = new ArrayList<>();
            result.addAll(super.schemaCubes(schema));
            result.add(CubeRBuilder.builder()
                .name("Employee Store Analysis A")
                .fact(new TableR(null,"inventory_fact_1997", "inventory", List.of()))
                .dimensionUsageOrDimensions(List.of(
                    DimensionUsageRBuilder.builder()
                        .name("Employee")
                        .source("Employee")
                        .foreignKey("product_id")
                        .build(),
                    DimensionUsageRBuilder.builder()
                        .name("Store Type")
                        .source("Store Type")
                        .foreignKey("warehouse_id")
                        .build()
                ))
                .measures(List.of(
                    MeasureRBuilder.builder()
                        .name("Employee Store Sales")
                        .aggregator("sum")
                        .formatString("$#,##0")
                        .column("warehouse_sales")
                        .build(),
                    MeasureRBuilder.builder()
                        .name("Employee Store Cost")
                        .aggregator("sum")
                        .formatString("$#,##0")
                        .column("warehouse_cost")
                        .build()
                ))
                .build());
            result.add(CubeRBuilder.builder()
                .name("Employee Store Analysis B")
                .fact(new TableR(null,"inventory_fact_1997", "inventory", List.of()))
                .dimensionUsageOrDimensions(List.of(
                    DimensionUsageRBuilder.builder()
                        .name("Employee")
                        .source("Employee")
                        .foreignKey("time_id")
                        .build(),
                    DimensionUsageRBuilder.builder()
                        .name("Store Type")
                        .source("Store Type")
                        .foreignKey("store_id")
                        .build()
                ))
                .measures(List.of(
                    MeasureRBuilder.builder()
                        .name("Employee Store Sales")
                        .aggregator("sum")
                        .formatString("$#,##0")
                        .column("warehouse_sales")
                        .build(),
                    MeasureRBuilder.builder()
                        .name("Employee Store Cost")
                        .aggregator("sum")
                        .formatString("$#,##0")
                        .column("warehouse_cost")
                        .build()
                ))
                .build());
            return result;
        }


        @Override
        protected List<MappingVirtualCube> schemaVirtualCubes(MappingSchema schema) {
            List<MappingVirtualCube> result = new ArrayList<>();
            result.addAll(super.schemaVirtualCubes(schema));
            result.add(VirtualCubeRBuilder.builder()
                .name("Employee Store Analysis")
                .virtualCubeDimensions(List.of(
                    VirtualCubeDimensionRBuilder.builder()
                        .name("Employee")
                        .build(),
                    VirtualCubeDimensionRBuilder.builder()
                        .name("Store Type")
                        .build()
                ))
                .virtualCubeMeasures(List.of(
                    VirtualCubeMeasureRBuilder.builder()
                        .cubeName("Employee Store Analysis A")
                        .name("[Measures].[Employee Store Sales]")
                        .build(),
                    VirtualCubeMeasureRBuilder.builder()
                        .cubeName("Employee Store Analysis B")
                        .name("[Measures].[Employee Store Cost]")
                        .build()
                ))
                .build());
            return result;
        }
    }

    public static class SharedDimensionTestModifier1 extends RDbMappingSchemaModifier {

        public SharedDimensionTestModifier1(MappingSchema mappingSchema) {
            super(mappingSchema);
        }
        @Override
        protected List<MappingCube> schemaCubes(MappingSchema schema) {
            List<MappingCube> result = new ArrayList<>();
            result.addAll(super.schemaCubes(schema));
            result.add(CubeRBuilder.builder()
                .name("Alternate Sales")
                .fact(new TableR("inventory_fact_1997"))
                .dimensionUsageOrDimensions(List.of(
                    DimensionUsageRBuilder.builder()
                        .name("Store Type")
                        .source("Store Type")
                        .foreignKey("store_id")
                        .build(),
                    DimensionUsageRBuilder.builder()
                        .name("Store")
                        .source("Store")
                        .foreignKey("store_id")
                        .build(),
                    DimensionUsageRBuilder.builder()
                        .name("Buyer")
                        .source("Store")
                        .visible(true)
                        .foreignKey("product_id")
                        .highCardinality(false)
                        .build(),
                    DimensionUsageRBuilder.builder()
                        .name("BuyerTwo")
                        .source("BuyerTwo")
                        .visible(true)
                        .foreignKey("product_id")
                        .highCardinality(false)
                        .build(),
                    DimensionUsageRBuilder.builder()
                        .name("Store Size in SQFT")
                        .source("Store Size in SQFT")
                        .foreignKey("store_id")
                        .build(),
                    DimensionUsageRBuilder.builder()
                        .name("Store Type")
                        .source("Store Type")
                        .foreignKey("store_id")
                        .build(),
                    DimensionUsageRBuilder.builder()
                        .name("Time")
                        .source("Time")
                        .foreignKey("time_id")
                        .build()

                ))
                .measures(List.of(
                    MeasureRBuilder.builder()
                        .name("Unit Sales")
                        .column("unit_sales")
                        .aggregator("sum")
                        .formatString("Standard")
                        .build()
                ))
                .build());
            return result;
        }


    }

    public static class AggregationOnDistinctCountMeasuresTestModifier extends RDbMappingSchemaModifier {

        public AggregationOnDistinctCountMeasuresTestModifier(MappingSchema mappingSchema) {
            super(mappingSchema);
        }

        @Override
        protected List<MappingVirtualCube> schemaVirtualCubes(MappingSchema schema) {
            List<MappingVirtualCube> result = new ArrayList<>();
            result.addAll(super.schemaVirtualCubes(schema));
            result.add(VirtualCubeRBuilder.builder()
                .name("Warehouse and Sales2")
                .defaultMeasure("Store Sales")
                .virtualCubeDimensions(List.of(
                    VirtualCubeDimensionRBuilder.builder()
                        .cubeName("Sales")
                        .name("Gender")
                        .build(),
                    VirtualCubeDimensionRBuilder.builder()
                        .name("Store")
                        .build(),
                    VirtualCubeDimensionRBuilder.builder()
                        .name("Product")
                        .build(),
                    VirtualCubeDimensionRBuilder.builder()
                        .cubeName("Warehouse")
                        .name("Warehouse")
                        .build()
                ))
                .virtualCubeMeasures(List.of(
                    VirtualCubeMeasureRBuilder.builder()
                        .cubeName("Sales")
                        .name("[Measures].[Store Sales]")
                        .build(),
                    VirtualCubeMeasureRBuilder.builder()
                        .cubeName("Sales")
                        .name("[Measures].[Customer Count]")
                        .build()
                ))
                .build());

            result.add(VirtualCubeRBuilder.builder()
                .name("Warehouse and Sales3")
                .defaultMeasure("Store Invoice")
                .cubeUsages(List.of(
                    CubeUsageRBuilder.builder()
                        .cubeName("Sales")
                        .ignoreUnrelatedDimensions(true)
                        .build()
                ))
                .virtualCubeDimensions(List.of(
                    VirtualCubeDimensionRBuilder.builder()
                        .cubeName("Sales")
                        .name("Gender")
                        .build(),
                    VirtualCubeDimensionRBuilder.builder()
                        .name("Store")
                        .build(),
                    VirtualCubeDimensionRBuilder.builder()
                        .name("Product")
                        .build(),
                    VirtualCubeDimensionRBuilder.builder()
                        .cubeName("Warehouse")
                        .name("Warehouse")
                        .build()
                ))
                .virtualCubeMeasures(List.of(
                    VirtualCubeMeasureRBuilder.builder()
                        .cubeName("Sales")
                        .name("[Measures].[Customer Count]")
                        .build()
                ))
                .build());
            return result;
        }
    }

    //storeDimensionLevelDependent,
    //cubeA,
    public static class SelectNotInGroupByTestModifier1 extends RDbMappingSchemaModifier {

        public SelectNotInGroupByTestModifier1(MappingSchema mappingSchema) {
            super(mappingSchema);
        }

        @Override
        protected List<MappingPrivateDimension> schemaDimensions(MappingSchema schema) {
            List<MappingPrivateDimension> result = new ArrayList<>();
            result.addAll(super.schemaDimensions(schema));
            result.add(PrivateDimensionRBuilder.builder()
                .name("CustomStore")
                .hierarchies(List.of(
                    HierarchyRBuilder.builder()
                        .hasAll(true)
                        .primaryKey("store_id")
                        .relation(new TableR("store"))
                        .levels(List.of(
                            LevelRBuilder.builder()
                                .name("Store Country")
                                .column("store_country")
                                .uniqueMembers(true)
                                .build(),
                            LevelRBuilder.builder()
                                .name("Store City")
                                .column("store_city")
                                .uniqueMembers(false)
                                .properties(List.of(
                                    PropertyRBuilder.builder()
                                        .name("Store State")
                                        .column("store_state")
                                        .dependsOnLevelValue(true)
                                        .build()
                                ))
                                .build(),
                            LevelRBuilder.builder()
                                .name("Store Name")
                                .column("store_name")
                                .uniqueMembers(true)
                                .build()
                        ))
                        .build()))
                .build());
            return result;
        }

        @Override
        protected List<MappingCube> schemaCubes(MappingSchema schema) {
            List<MappingCube> result = new ArrayList<>();
            result.addAll(super.schemaCubes(schema));
            result.add(CubeRBuilder.builder()
                .name("CustomSales")
                .fact(new TableR("sales_fact_1997"))
                .dimensionUsageOrDimensions(List.of(
                    DimensionUsageRBuilder.builder()
                        .name("CustomStore")
                        .source("CustomStore")
                        .foreignKey("store_id")
                        .build()
                ))
                .measures(List.of(
                    MeasureRBuilder.builder()
                        .name("Custom Store Sales")
                        .column("store_sales")
                        .aggregator("sum")
                        .formatString("#,###.00")
                        .build(),
                    MeasureRBuilder.builder()
                        .name("Sales Count")
                        .column("product_id")
                        .aggregator("count")
                        .build()
                ))
                .build());
            return result;
        }
    }



    //storeDimensionLevelIndependent,
    //cubeA,
    public static class SelectNotInGroupByTestModifier2 extends RDbMappingSchemaModifier {

        public SelectNotInGroupByTestModifier2(MappingSchema mappingSchema) {
            super(mappingSchema);
        }

        @Override
        protected List<MappingPrivateDimension> schemaDimensions(MappingSchema schema) {
            List<MappingPrivateDimension> result = new ArrayList<>();
            result.addAll(super.schemaDimensions(schema));
            result.add(PrivateDimensionRBuilder.builder()
                .name("CustomStore")
                .hierarchies(List.of(
                    HierarchyRBuilder.builder()
                        .hasAll(true)
                        .primaryKey("store_id")
                        .relation(new TableR("store"))
                        .levels(List.of(
                            LevelRBuilder.builder()
                                .name("Store Country")
                                .column("store_country")
                                .uniqueMembers(true)
                                .build(),
                            LevelRBuilder.builder()
                                .name("Store City")
                                .column("store_city")
                                .uniqueMembers(false)
                                .properties(List.of(
                                    PropertyRBuilder.builder()
                                        .name("Store State")
                                        .column("store_state")
                                        .build()
                                ))
                                .build(),
                            LevelRBuilder.builder()
                                .name("Store Name")
                                .column("store_name")
                                .uniqueMembers(true)
                                .build()
                        ))
                        .build()))
                .build());
            return result;
        }

        @Override
        protected List<MappingCube> schemaCubes(MappingSchema schema) {
            List<MappingCube> result = new ArrayList<>();
            result.addAll(super.schemaCubes(schema));
            result.add(CubeRBuilder.builder()
                .name("CustomSales")
                .fact(new TableR("sales_fact_1997"))
                .dimensionUsageOrDimensions(List.of(
                    DimensionUsageRBuilder.builder()
                        .name("CustomStore")
                        .source("CustomStore")
                        .foreignKey("store_id")
                        .build()
                ))
                .measures(List.of(
                    MeasureRBuilder.builder()
                        .name("Custom Store Sales")
                        .column("store_sales")
                        .aggregator("sum")
                        .formatString("#,###.00")
                        .build(),
                    MeasureRBuilder.builder()
                        .name("Sales Count")
                        .column("product_id")
                        .aggregator("count")
                        .build()
                ))
                .build());
            return result;
        }
    }

    //storeDimensionUniqueLevelDependentProp,
    //cubeA
    public static class SelectNotInGroupByTestModifier3 extends RDbMappingSchemaModifier {

        public SelectNotInGroupByTestModifier3(MappingSchema mappingSchema) {
            super(mappingSchema);
        }
        @Override
        protected List<MappingPrivateDimension> schemaDimensions(MappingSchema schema) {
            List<MappingPrivateDimension> result = new ArrayList<>();
            result.addAll(super.schemaDimensions(schema));
            result.add(PrivateDimensionRBuilder.builder()
                .name("CustomStore")
                .hierarchies(List.of(
                    HierarchyRBuilder.builder()
                        .hasAll(true)
                        .primaryKey("store_id")
                        .uniqueKeyLevelName("Store Name")
                        .relation(new TableR("store"))
                        .levels(List.of(
                            LevelRBuilder.builder()
                                .name("Store Country")
                                .column("store_country")
                                .uniqueMembers(true)
                                .build(),
                            LevelRBuilder.builder()
                                .name("Store City")
                                .column("store_city")
                                .uniqueMembers(false)
                                .properties(List.of(
                                    PropertyRBuilder.builder()
                                        .name("Store State")
                                        .column("store_state")
                                        .dependsOnLevelValue(true)
                                        .build()
                                ))
                                .build(),
                            LevelRBuilder.builder()
                                .name("Store Name")
                                .column("store_name")
                                .uniqueMembers(true)
                                .build()
                        ))
                        .build()))
                .build());
            return result;
        }

        @Override
        protected List<MappingCube> schemaCubes(MappingSchema schema) {
            List<MappingCube> result = new ArrayList<>();
            result.addAll(super.schemaCubes(schema));
            result.add(CubeRBuilder.builder()
                .name("CustomSales")
                .fact(new TableR("sales_fact_1997"))
                .dimensionUsageOrDimensions(List.of(
                    DimensionUsageRBuilder.builder()
                        .name("CustomStore")
                        .source("CustomStore")
                        .foreignKey("store_id")
                        .build()
                ))
                .measures(List.of(
                    MeasureRBuilder.builder()
                        .name("Custom Store Sales")
                        .column("store_sales")
                        .aggregator("sum")
                        .formatString("#,###.00")
                        .build(),
                    MeasureRBuilder.builder()
                        .name("Sales Count")
                        .column("product_id")
                        .aggregator("count")
                        .build()
                ))
                .build());
            return result;
        }
    }

    //storeDimensionUniqueLevelIndependentProp,
    //cubeA
    public static class SelectNotInGroupByTestModifier4 extends RDbMappingSchemaModifier {

        public SelectNotInGroupByTestModifier4(MappingSchema mappingSchema) {
            super(mappingSchema);
        }
        @Override
        protected List<MappingPrivateDimension> schemaDimensions(MappingSchema schema) {
            List<MappingPrivateDimension> result = new ArrayList<>();
            result.addAll(super.schemaDimensions(schema));
            result.add(PrivateDimensionRBuilder.builder()
                .name("CustomStore")
                .hierarchies(List.of(
                    HierarchyRBuilder.builder()
                        .hasAll(true)
                        .primaryKey("store_id")
                        .uniqueKeyLevelName("Store Name")
                        .uniqueKeyLevelName("Store Name")
                        .relation(new TableR("store"))
                        .levels(List.of(
                            LevelRBuilder.builder()
                                .name("Store Country")
                                .column("store_country")
                                .uniqueMembers(true)
                                .build(),
                            LevelRBuilder.builder()
                                .name("Store City")
                                .column("store_city")
                                .uniqueMembers(false)
                                .properties(List.of(
                                    PropertyRBuilder.builder()
                                        .name("Store State")
                                        .column("store_state")
                                        .dependsOnLevelValue(false)
                                        .build()
                                ))
                                .build(),
                            LevelRBuilder.builder()
                                .name("Store Name")
                                .column("store_name")
                                .uniqueMembers(true)
                                .build()
                        ))
                        .build()))
                .build());
            return result;
        }

        @Override
        protected List<MappingCube> schemaCubes(MappingSchema schema) {
            List<MappingCube> result = new ArrayList<>();
            result.addAll(super.schemaCubes(schema));
            result.add(CubeRBuilder.builder()
                .name("CustomSales")
                .fact(new TableR("sales_fact_1997"))
                .dimensionUsageOrDimensions(List.of(
                    DimensionUsageRBuilder.builder()
                        .name("CustomStore")
                        .source("CustomStore")
                        .foreignKey("store_id")
                        .build()
                ))
                .measures(List.of(
                    MeasureRBuilder.builder()
                        .name("Custom Store Sales")
                        .column("store_sales")
                        .aggregator("sum")
                        .formatString("#,###.00")
                        .build(),
                    MeasureRBuilder.builder()
                        .name("Sales Count")
                        .column("product_id")
                        .aggregator("count")
                        .build()
                ))
                .build());
            return result;
        }
    }


    public static class IgnoreUnrelatedDimensionsTestModifier extends RDbMappingSchemaModifier {

        public IgnoreUnrelatedDimensionsTestModifier(MappingSchema mappingSchema) {
            super(mappingSchema);
        }

        @Override
        protected List<MappingVirtualCube> schemaVirtualCubes(MappingSchema schema) {
            List<MappingVirtualCube> result = new ArrayList<>();
            result.addAll(super.schemaVirtualCubes(schema));
            result.add(VirtualCubeRBuilder.builder()
                .name("Warehouse and Sales2")
                .defaultMeasure("Store Sales")
                .cubeUsages(List.of(
                    CubeUsageRBuilder.builder()
                        .cubeName("Sales")
                        .ignoreUnrelatedDimensions(true)
                        .build(),
                    CubeUsageRBuilder.builder()
                        .cubeName("Warehouse")
                        .ignoreUnrelatedDimensions(true)
                        .build()
                ))

                .virtualCubeDimensions(List.of(
                    VirtualCubeDimensionRBuilder.builder()
                        .cubeName("Sales")
                        .name("Customers")
                        .build(),
                    VirtualCubeDimensionRBuilder.builder()
                        .cubeName("Sales")
                        .name("Education Level")
                        .build(),
                    VirtualCubeDimensionRBuilder.builder()
                        .cubeName("Sales")
                        .name("Gender")
                        .build(),
                    VirtualCubeDimensionRBuilder.builder()
                        .cubeName("Sales")
                        .name("Marital Status")
                        .build(),
                    VirtualCubeDimensionRBuilder.builder()
                        .name("Product")
                        .build(),
                    VirtualCubeDimensionRBuilder.builder()
                        .cubeName("Sales")
                        .name("Product")
                        .build(),
                    VirtualCubeDimensionRBuilder.builder()
                        .cubeName("Sales")
                        .name("Promotions")
                        .build(),
                    VirtualCubeDimensionRBuilder.builder()
                        .name("Store")
                        .build(),
                    VirtualCubeDimensionRBuilder.builder()
                        .name("Time")
                        .build(),
                    VirtualCubeDimensionRBuilder.builder()
                        .cubeName("Sales")
                        .name("Yearly Income")
                        .build(),
                    VirtualCubeDimensionRBuilder.builder()
                        .cubeName("Warehouse")
                        .name("Warehouse")
                        .build()
                ))
                .virtualCubeMeasures(List.of(
                    VirtualCubeMeasureRBuilder.builder()
                        .cubeName("Sales")
                        .name("[Measures].[Sales Count]")
                        .build(),
                    VirtualCubeMeasureRBuilder.builder()
                        .cubeName("Sales")
                        .name("[Measures].[Store Cost]")
                        .build(),
                    VirtualCubeMeasureRBuilder.builder()
                        .cubeName("Sales")
                        .name("[Measures].[Store Sales]")
                        .build(),
                    VirtualCubeMeasureRBuilder.builder()
                        .cubeName("Sales")
                        .name("Unit Sales")
                        .build(),
                    VirtualCubeMeasureRBuilder.builder()
                        .cubeName("Sales")
                        .name("[Measures].[Profit]")
                        .build(),
                    VirtualCubeMeasureRBuilder.builder()
                        .cubeName("Sales")
                        .name("[Measures].[Profit Growth]")
                        .build(),
                    VirtualCubeMeasureRBuilder.builder()
                        .cubeName("Warehouse")
                        .name("[Measures].[Store Invoice]")
                        .build(),
                    VirtualCubeMeasureRBuilder.builder()
                        .cubeName("Warehouse")
                        .name("[Measures].[Supply Time]")
                        .build(),
                    VirtualCubeMeasureRBuilder.builder()
                        .cubeName("Warehouse")
                        .name("[Measures].[Units Ordered]")
                        .build(),
                    VirtualCubeMeasureRBuilder.builder()
                        .cubeName("Warehouse")
                        .name("[Measures].[Units Shipped]")
                        .build(),
                    VirtualCubeMeasureRBuilder.builder()
                        .cubeName("Warehouse")
                        .name("[Measures].[Warehouse Cost]")
                        .build(),
                    VirtualCubeMeasureRBuilder.builder()
                        .cubeName("Warehouse")
                        .name("[Measures].[Warehouse Profit]")
                        .build(),
                    VirtualCubeMeasureRBuilder.builder()
                        .cubeName("Warehouse")
                        .name("[Measures].[Warehouse Sales]")
                        .build(),
                    VirtualCubeMeasureRBuilder.builder()
                        .cubeName("Warehouse")
                        .name("[Measures].[Average Warehouse Sale]")
                        .build()
                ))
                .calculatedMembers(List.of(
                    CalculatedMemberRBuilder.builder()
                        .name("Profit Per Unit Shipped")
                        .dimension("Measures")
                        .formulaElement(FormulaRBuilder.builder()
                            .cdata("[Measures].[Profit] / [Measures].[Units Shipped]").build())
                        .build()
                ))
                .build());

            result.add(VirtualCubeRBuilder.builder()
                .name("Warehouse and Sales3")
                .defaultMeasure("Store Invoice")
                .cubeUsages(List.of(
                    CubeUsageRBuilder.builder()
                        .cubeName("Sales")
                        .ignoreUnrelatedDimensions(true)
                        .build()
                ))
                .virtualCubeDimensions(List.of(
                    VirtualCubeDimensionRBuilder.builder()
                        .cubeName("Sales")
                        .name("Gender")
                        .build(),
                    VirtualCubeDimensionRBuilder.builder()
                        .name("Store")
                        .build(),
                    VirtualCubeDimensionRBuilder.builder()
                        .name("Product")
                        .build(),
                    VirtualCubeDimensionRBuilder.builder()
                        .cubeName("Warehouse")
                        .name("Warehouse")
                        .build()
                ))
                .virtualCubeMeasures(List.of(
                    VirtualCubeMeasureRBuilder.builder()
                        .cubeName("Sales")
                        .name("[Measures].[Customer Count]")
                        .build()
                ))
                .build());
            return result;
        }
    }

    //cubeSales3
    public static class IgnoreUnrelatedDimensionsTestModifier1 extends RDbMappingSchemaModifier {

        public IgnoreUnrelatedDimensionsTestModifier1(MappingSchema mappingSchema) {
            super(mappingSchema);
        }

            /*
            "<Cube name=\"Sales 3\">\n"
                + "   <Table name=\"sales_fact_1997\"/>\n"
                + "   <DimensionUsage name=\"Time\" source=\"Time\" foreignKey=\"time_id\"/>\n"
                + "   <Dimension name=\"Education Level\" foreignKey=\"customer_id\">\n"
                + "    <Hierarchy hasAll=\"true\" primaryKey=\"customer_id\">\n"
                + "      <Table name=\"customer\"/>\n"
                + "      <Level name=\"Education Level\" column=\"education\" uniqueMembers=\"true\"/>\n"
                + "    </Hierarchy>\n"
                + "  </Dimension>\n"
                + "   <DimensionUsage name=\"Product\" source=\"Product\" foreignKey=\"product_id\"/>\n"
                + "   <Dimension name=\"Gender\" foreignKey=\"customer_id\">\n"
                + "     <Hierarchy hasAll=\"true\" defaultMember=\"[Gender].[F]\" "
                + "allMemberName=\"All Gender\" primaryKey=\"customer_id\">\n"
                + "       <Table name=\"customer\"/>\n"
                + "       <Level name=\"Gender\" column=\"gender\" uniqueMembers=\"true\"/>\n"
                + "     </Hierarchy>\n"
                + "   </Dimension>\n"
                + "   <Measure name=\"Unit Sales\" column=\"unit_sales\" aggregator=\"sum\"    formatString=\"Standard\">\n"
                + "     <CalculatedMemberProperty name=\"MEMBER_ORDINAL\" value=\"2\"/>\n"
                + "   </Measure>\n"
                + "</Cube>";
            */
        @Override
        protected List<MappingCube> schemaCubes(MappingSchema schema) {
            List<MappingCube> result = new ArrayList<>();
            result.addAll(super.schemaCubes(schema));
            result.add(CubeRBuilder.builder()
                .name("Sales 3")
                .fact(new TableR("sales_fact_1997"))
                .dimensionUsageOrDimensions(List.of(
                    DimensionUsageRBuilder.builder()
                        .name("Time")
                        .source("Time")
                        .foreignKey("time_id")
                        .build(),
                    PrivateDimensionRBuilder.builder()
                        .name("Education Level")
                        .foreignKey("customer_id")
                        .hierarchies(List.of(
                            HierarchyRBuilder.builder()
                                .hasAll(true)
                                .primaryKey("customer_id")
                                .relation(new TableR("customer"))
                                .levels(List.of(
                                    LevelRBuilder.builder()
                                        .name("Education Level")
                                        .column("education")
                                        .uniqueMembers(true)
                                        .build()
                                ))
                                .build()
                        ))
                        .build(),
                    DimensionUsageRBuilder.builder()
                        .name("Product")
                        .source("Product")
                        .foreignKey("product_id")
                        .build(),
                    PrivateDimensionRBuilder.builder()
                        .name("Gender")
                        .foreignKey("customer_id")
                        .hierarchies(List.of(
                            HierarchyRBuilder.builder()
                                .hasAll(true)
                                .defaultMember("[Gender].[F]")
                                .allMemberName("All Gender")
                                .primaryKey("customer_id")
                                .relation(new TableR("customer"))
                                .levels(List.of(
                                    LevelRBuilder.builder()
                                        .name("Gender")
                                        .column("gender")
                                        .uniqueMembers(true)
                                        .build()
                                ))
                                .build()
                        ))
                        .build()
                ))
                .measures(List.of(
                    MeasureRBuilder.builder()
                        .name("Unit Sales")
                        .column("unit_sales")
                        .aggregator("sum")
                        .formatString("Standard")
                        .calculatedMemberProperties(List.of(
                            CalculatedMemberPropertyRBuilder.builder()
                                .name("MEMBER_ORDINAL")
                                .value("2")
                                .build()
                        ))
                        .build()
                ))
                .build());
            return result;
        }

        @Override
        protected List<MappingVirtualCube> schemaVirtualCubes(MappingSchema schema) {
            List<MappingVirtualCube> result = new ArrayList<>();
            result.addAll(super.schemaVirtualCubes(schema));
            result.add(VirtualCubeRBuilder.builder()
                .name("Warehouse and Sales 3")
                .defaultMeasure("Store Invoice")
                .cubeUsages(List.of(
                    CubeUsageRBuilder.builder()
                        .cubeName("Sales 3")
                        .ignoreUnrelatedDimensions(false)
                        .build(),
                    CubeUsageRBuilder.builder()
                        .cubeName("Warehouse")
                        .ignoreUnrelatedDimensions(true)
                        .build()
                ))
                .virtualCubeDimensions(List.of(
                    VirtualCubeDimensionRBuilder.builder()
                        .cubeName("Sales 3")
                        .name("Gender")
                        .build(),
                    VirtualCubeDimensionRBuilder.builder()
                        .cubeName("Sales 3")
                        .name("Education Level")
                        .build(),
                    VirtualCubeDimensionRBuilder.builder()
                        .name("Product")
                        .build(),
                    VirtualCubeDimensionRBuilder.builder()
                        .name("Time")
                        .build(),
                    VirtualCubeDimensionRBuilder.builder()
                        .cubeName("Warehouse")
                        .name("Warehouse")
                        .build()
                ))
                .virtualCubeMeasures(List.of(
                    VirtualCubeMeasureRBuilder.builder()
                        .cubeName("Sales 3")
                        .name("[Measures].[Unit Sales]")
                        .build(),
                    VirtualCubeMeasureRBuilder.builder()
                        .cubeName("Warehouse")
                        .name("[Measures].[Store Invoice]")
                        .build(),
                    VirtualCubeMeasureRBuilder.builder()
                        .cubeName("Warehouse")
                        .name("[Measures].[Warehouse Sales]")
                        .build()
                ))
                .build());

            return result;
        }
    }

    public static class ParentChildHierarchyTestModifier1 extends RDbMappingSchemaModifier {

        public ParentChildHierarchyTestModifier1(MappingSchema mappingSchema) {
            super(mappingSchema);
        }
        /*
            "  <Dimension name=\"EmployeesClosure\" foreignKey=\"employee_id\">\n"
            + "      <Hierarchy hasAll=\"true\" allMemberName=\"All Employees\"\n"
            + "          primaryKey=\"employee_id\" primaryKeyTable=\"employee_closure\">\n"
            + "        <Join leftKey=\"supervisor_id\" rightKey=\"employee_id\">\n"
            + "          <Table name=\"employee_closure\"/>\n"
            + "          <Table name=\"employee\" alias=\"employee2\" />\n"
            + "        </Join>\n"
            + "        <Level name=\"Closure\"  type=\"Numeric\" uniqueMembers=\"false\"\n"
            + "            table=\"employee_closure\" column=\"supervisor_id\"/>\n"
            + "        <Level name=\"Employee\" type=\"Numeric\" uniqueMembers=\"true\"\n"
            + "            table=\"employee_closure\" column=\"employee_id\"/>\n"
            + "      </Hierarchy>\n"
            + "  </Dimension>"));
         */
        @Override
        protected List<MappingCubeDimension> cubeDimensionUsageOrDimensions(MappingCube cube) {
            List<MappingCubeDimension> result = new ArrayList<>();
            result.addAll(super.cubeDimensionUsageOrDimensions(cube));
            if ("HR".equals(cube.name())) {
                MappingLevel level1 = LevelRBuilder
                    .builder()
                    .name("Closure")
                    .type(TypeEnum.NUMERIC)
                    .uniqueMembers(false)
                    .build();
                MappingLevel level2 = LevelRBuilder
                    .builder()
                    .name("Employee")
                    .type(TypeEnum.NUMERIC)
                    .uniqueMembers(true)
                    .table("employee_closure")
                    .column("employee_id")
                    .build();
                MappingHierarchy hierarchy = HierarchyRBuilder
                    .builder()
                    .hasAll(true)
                    .allMemberName("All Employees")
                    .primaryKey("employee_id")
                    .primaryKeyTable("employee_closure")
                    .relation(new JoinR(List.of(
                        new TableR("employee_closure"),
                        new TableR(null, "employee", "employee2", List.of())),
                        null, "supervisor_id", null, "employee_id"))
                    .levels(List.of(level1, level2))
                    .build();
                MappingCubeDimension dimension = PrivateDimensionRBuilder
                    .builder()
                    .name("EmployeesClosure")
                    .foreignKey("employee_id")
                    .hierarchies(List.of(hierarchy))
                    .build();
                result.add(dimension);
            }
            return result;
        }
    }

    public static class ParentChildHierarchyTestModifier2 extends RDbMappingSchemaModifier {

        public ParentChildHierarchyTestModifier2(MappingSchema mappingSchema) {
            super(mappingSchema);
        }
        /*
            "<Dimension name=\"EmployeeSnowFlake\" foreignKey=\"employee_id\">"
            + "<Hierarchy hasAll=\"true\" allMemberName=\"All Employees\""
            + "    primaryKey=\"employee_id\" primaryKeyTable=\"employee\">"
            + "  <Join leftKey=\"store_id\""
            + "    rightAlias=\"store\" rightKey=\"store_id\">"
            + "    <Table name=\"employee\"/>"
            + "    <Table name=\"store\"/>"
            + "  </Join>"
            + "  <Level name=\"Employee Stores\" table=\"store\""
            + "      column=\"store_id\" uniqueMembers=\"true\"/>"
            + "  <Level name=\"Employee Id\" type=\"Numeric\" table=\"employee\" uniqueMembers=\"true\""
            + "      column=\"employee_id\" parentColumn=\"supervisor_id\""
            + "      nameColumn=\"full_name\" nullParentValue=\"0\">"
            + "    <Closure parentColumn=\"supervisor_id\" childColumn=\"employee_id\">"
            + "      <Table name=\"employee_closure\"/>"
            + "    </Closure>"
            + "    <Property name=\"Marital Status\" column=\"marital_status\"/>"
            + "    <Property name=\"Position Title\" column=\"position_title\"/>"
            + "    <Property name=\"Gender\" column=\"gender\"/>"
            + "    <Property name=\"Salary\" column=\"salary\"/>"
            + "    <Property name=\"Education Level\" column=\"education_level\"/>"
            + "    <Property name=\"Management Role\" column=\"management_role\"/>"
            + "  </Level>"
            + "</Hierarchy>"
            + "</Dimension>"));
         */
        @Override
        protected List<MappingCubeDimension> cubeDimensionUsageOrDimensions(MappingCube cube) {
            List<MappingCubeDimension> result = new ArrayList<>();
            result.addAll(super.cubeDimensionUsageOrDimensions(cube));
            if ("HR".equals(cube.name())) {
                MappingLevel level1 = LevelRBuilder
                    .builder()
                    .name("Employee Stores")
                    .table("store")
                    .column("store_id")
                    .uniqueMembers(true)
                    .build();
                MappingLevel level2 = LevelRBuilder
                    .builder()
                    .name("Employee Id")
                    .type(TypeEnum.NUMERIC)
                    .table("employee")
                    .uniqueMembers(true)
                    .table("employee_closure")
                    .column("employee_id")
                    .parentColumn("supervisor_id")
                    .nameColumn("full_name")
                    .nullParentValue("0")
                    .closure(ClosureRBuilder.builder()
                        .parentColumn("supervisor_id")
                        .childColumn("employee_id")
                        .table(new TableR("employee_closure"))
                        .build())
                    .properties(List.of(
                        PropertyRBuilder.builder()
                            .name("Marital Status")
                            .column("marital_status")
                            .build(),
                        PropertyRBuilder.builder()
                            .name("Position Title")
                            .column("position_title")
                            .build(),
                        PropertyRBuilder.builder()
                            .name("Gender")
                            .column("gender")
                            .build(),
                        PropertyRBuilder.builder()
                            .name("Salary")
                            .column("salary")
                            .build(),
                        PropertyRBuilder.builder()
                            .name("Education Level")
                            .column("education_level")
                            .build(),
                        PropertyRBuilder.builder()
                            .name("Management Role")
                            .column("management_role")
                            .build()
                    ))
                    .build();

                MappingHierarchy hierarchy = HierarchyRBuilder
                    .builder()
                    .hasAll(true)
                    .allMemberName("All Employees")
                    .primaryKey("employee_id")
                    .primaryKeyTable("employee")
                    .relation(new JoinR(List.of(
                        new TableR("employee"),
                        new TableR("store")),
                        null, "store_id", "store", "store_id"))
                    .levels(List.of(level1, level2))
                    .build();

                MappingCubeDimension dimension = PrivateDimensionRBuilder
                    .builder()
                    .name("EmployeeSnowFlake")
                    .foreignKey("employee_id")
                    .hierarchies(List.of(hierarchy))
                    .build();
                result.add(dimension);
            }
            return result;
        }
    }

    public static class ParentChildHierarchyTestModifier3 extends RDbMappingSchemaModifier {

        public ParentChildHierarchyTestModifier3(MappingSchema mappingSchema) {
            super(mappingSchema);
        }

        /*
              "<Cube name=\"EmployeeSharedClosureCube\">\n"
            + "  <Table name=\"salary\" alias=\"salary_closure\" />\n"
            + "  <DimensionUsage name=\"SharedEmployee\" source=\"SharedEmployee\" foreignKey=\"employee_id\" />\n"
            + "  <Dimension name=\"Department\" foreignKey=\"department_id\">"
            + "    <Hierarchy hasAll=\"true\" primaryKey=\"department_id\">"
            + "      <Table name=\"department\"/>"
            + "        <Level name=\"Department Description\" uniqueMembers=\"true\""
            + "          column=\"department_id\"/>"
            + "    </Hierarchy>"
            + "  </Dimension>"
            + "  <DimensionUsage name=\"Store Type\" source=\"Store Type\" foreignKey=\"warehouse_id\" />\n"
            + "  <Measure name=\"Org Salary\" column=\"salary_paid\" aggregator=\"sum\""
            + "      formatString=\"Currency\"/>"
            + "   <Measure name=\"Count\" column=\"employee_id\" aggregator=\"count\""
            + "    formatString=\"#,#\"/>"
            + "</Cube>";
         */
        @Override
        protected List<MappingCube> schemaCubes(MappingSchema schema) {
            List<MappingCube> result = new ArrayList<>();
            result.addAll(super.schemaCubes(schema));
            result.add(CubeRBuilder.builder()
                .name("EmployeeSharedClosureCube")
                .fact(new TableR(null, "salary", "sales_fact_1997", List.of()))
                .dimensionUsageOrDimensions(List.of(
                    DimensionUsageRBuilder.builder()
                        .name("SharedEmployee")
                        .source("SharedEmployee")
                        .foreignKey("employee_id")
                        .build(),
                    PrivateDimensionRBuilder.builder()
                        .name("Department")
                        .foreignKey("department_id")
                        .hierarchies(List.of(
                            HierarchyRBuilder.builder()
                                .hasAll(true)
                                .primaryKey("department_id")
                                .relation(new TableR("department"))
                                .levels(List.of(
                                    LevelRBuilder.builder()
                                        .name("Department Description")
                                        .column("department_id")
                                        .uniqueMembers(true)
                                        .build()
                                ))
                                .build()
                        ))
                        .build(),
                    DimensionUsageRBuilder.builder()
                        .name("Store Type")
                        .source("Store Type")
                        .foreignKey("warehouse_id")
                        .build()
                ))
                .measures(List.of(
                    MeasureRBuilder.builder()
                        .name("Org Salary")
                        .column("salary_paid")
                        .aggregator("sum")
                        .formatString("Currency")
                        .build(),
                    MeasureRBuilder.builder()
                        .name("Count")
                        .column("employee_id")
                        .aggregator("count")
                        .formatString("#,#")
                        .build()
                ))
                .build()
            );
            return result;
        }

        /*
              "<Dimension name=\"SharedEmployee\">"
            + "<Hierarchy hasAll=\"true\""
            + "    primaryKey=\"employee_id\" primaryKeyTable=\"employee\">"
            + "  <Join leftKey=\"store_id\""
            + "    rightAlias=\"store\" rightKey=\"store_id\">"
            + "    <Table name=\"employee\"/>"
            + "    <Table name=\"store\"/>"
            + "  </Join>"
            + "  <Level name=\"Employee Id\" type=\"Numeric\" table=\"employee\" uniqueMembers=\"true\""
            + "      column=\"employee_id\" parentColumn=\"supervisor_id\""
            + "      nameColumn=\"full_name\" nullParentValue=\"0\">"
            + "    <Closure parentColumn=\"supervisor_id\" childColumn=\"employee_id\">"
            + "      <Table name=\"employee_closure\"/>"
            + "    </Closure>"
            + "    <Property name=\"Marital Status\" column=\"marital_status\"/>"
            + "    <Property name=\"Position Title\" column=\"position_title\"/>"
            + "    <Property name=\"Gender\" column=\"gender\"/>"
            + "    <Property name=\"Salary\" column=\"salary\"/>"
            + "    <Property name=\"Education Level\" column=\"education_level\"/>"
            + "    <Property name=\"Management Role\" column=\"management_role\"/>"
            + "  </Level>"
            + "</Hierarchy>"
            + "</Dimension>";
         */
        @Override
        protected List<MappingPrivateDimension> schemaDimensions(MappingSchema schema) {
            List<MappingPrivateDimension> result = new ArrayList<>();
            result.addAll(super.schemaDimensions(schema));
            result.add(PrivateDimensionRBuilder.builder()
                .name("SharedEmployee")
                .hierarchies(List.of(
                    HierarchyRBuilder.builder()
                        .hasAll(true)
                        .primaryKey("employee_id")
                        .primaryKeyTable("employee")
                        .relation(new JoinR(
                            List.of(new TableR("employee"), new TableR("store")),
                            null, "store_id", "store", "store_id"))
                        .levels(List.of(
                            LevelRBuilder.builder()
                                .name("Employee Id")
                                .type(TypeEnum.NUMERIC)
                                .table("employee")
                                .uniqueMembers(true)
                                .column("employee_id")
                                .parentColumn("supervisor_id")
                                .nameColumn("full_name")
                                .nullParentValue("0")
                                .closure(ClosureRBuilder.builder()
                                    .parentColumn("supervisor_id")
                                    .childColumn("employee_id")
                                    .table(new TableR("employee_closure"))
                                    .build())
                                .properties(List.of(
                                    PropertyRBuilder.builder()
                                        .name("Marital Status")
                                        .column("marital_status")
                                        .build(),
                                    PropertyRBuilder.builder()
                                        .name("Position Title")
                                        .column("position_title")
                                        .build(),
                                    PropertyRBuilder.builder()
                                        .name("Gender")
                                        .column("gender")
                                        .build(),
                                    PropertyRBuilder.builder()
                                        .name("Salary")
                                        .column("salary")
                                        .build(),
                                    PropertyRBuilder.builder()
                                        .name("Education Level")
                                        .column("education_level")
                                        .build(),
                                    PropertyRBuilder.builder()
                                        .name("Management Role")
                                        .column("management_role")
                                        .build()
                                ))
                                .build()
                        ))
                        .build()
                ))
                .build());
            return result;
        }
    }

    public static class ParentChildHierarchyTestModifier4 extends RDbMappingSchemaModifier {

        public ParentChildHierarchyTestModifier4(MappingSchema mappingSchema) {
            super(mappingSchema);
        }
        /*
            "<Dimension name=\"EmployeesNonClosure\" foreignKey=\"employee_id\">"
            + "<Hierarchy hasAll=\"true\" allMemberName=\"All Employees\""
            + "    primaryKey=\"employee_id\">"
            + "  <Table name=\"employee\"/>"
            + "  <Level name=\"Employee Id\" type=\"Numeric\" uniqueMembers=\"true\""
            + "      column=\"employee_id\" parentColumn=\"supervisor_id\""
            + "      nameColumn=\"full_name\" nullParentValue=\"0\">"
            + "    <Property name=\"Marital Status\" column=\"marital_status\"/>"
            + "    <Property name=\"Position Title\" column=\"position_title\"/>"
            + "    <Property name=\"Gender\" column=\"gender\"/>"
            + "    <Property name=\"Salary\" column=\"salary\"/>"
            + "    <Property name=\"Education Level\" column=\"education_level\"/>"
            + "    <Property name=\"Management Role\" column=\"management_role\"/>"
            + "  </Level>"
            + "</Hierarchy>"
            + "</Dimension>",
         */
        @Override
        protected List<MappingCubeDimension> cubeDimensionUsageOrDimensions(MappingCube cube) {
            List<MappingCubeDimension> result = new ArrayList<>();
            result.addAll(super.cubeDimensionUsageOrDimensions(cube));
            if ("HR".equals(cube.name())) {


                MappingLevel level = LevelRBuilder
                    .builder()
                    .name("Employee Id")
                    .type(TypeEnum.NUMERIC)
                    .uniqueMembers(true)
                    .column("employee_id")
                    .parentColumn("supervisor_id")
                    .nameColumn("full_name")
                    .nullParentValue("0")
                    .closure(ClosureRBuilder.builder()
                        .parentColumn("supervisor_id")
                        .childColumn("employee_id")
                        .table(new TableR("employee_closure"))
                        .build())
                    .properties(List.of(
                        PropertyRBuilder.builder()
                            .name("Marital Status")
                            .column("marital_status")
                            .build(),
                        PropertyRBuilder.builder()
                            .name("Position Title")
                            .column("position_title")
                            .build(),
                        PropertyRBuilder.builder()
                            .name("Gender")
                            .column("gender")
                            .build(),
                        PropertyRBuilder.builder()
                            .name("Salary")
                            .column("salary")
                            .build(),
                        PropertyRBuilder.builder()
                            .name("Education Level")
                            .column("education_level")
                            .build(),
                        PropertyRBuilder.builder()
                            .name("Management Role")
                            .column("management_role")
                            .build()
                    ))
                    .build();

                MappingHierarchy hierarchy = HierarchyRBuilder
                    .builder()
                    .hasAll(true)
                    .allMemberName("All Employees")
                    .primaryKey("employee_id")
                    .relation(new TableR("employee"))
                    .levels(List.of(level))
                    .build();

                MappingCubeDimension dimension = PrivateDimensionRBuilder
                    .builder()
                    .name("EmployeesNonClosure")
                    .foreignKey("employee_id")
                    .hierarchies(List.of(hierarchy))
                    .build();
                result.add(dimension);
            }
            return result;
        }
    }

    public static class ParentChildHierarchyTestModifier5 extends RDbMappingSchemaModifier {

        public ParentChildHierarchyTestModifier5(MappingSchema mappingSchema) {
            super(mappingSchema);
        }
        /*
                "<Dimension name=\"EmployeesNoClosure\" foreignKey=\"employee_id\">\n"
                + "<Hierarchy hasAll=\"true\" allMemberName=\"All Employees\" primaryKey=\"employee_id\">\n"
                + "<Table name=\"employee\"/>\n"
                + "<Level name=\"Employee Id\" uniqueMembers=\"true\" type=\"Numeric\" column=\"employee_id\" nameColumn=\"full_name\" parentColumn=\"supervisor_id\" nullParentValue=\"0\">\n"
                + "<Property name=\"Marital Status\" column=\"marital_status\"/>\n"
                + "<Property name=\"Position Title\" column=\"position_title\"/>\n"
                + "<Property name=\"Gender\" column=\"gender\"/>\n"
                + "<Property name=\"Salary\" column=\"salary\"/>\n"
                + "<Property name=\"Education Level\" column=\"education_level\"/>\n"
                + "<Property name=\"Management Role\" column=\"management_role\"/>\n"
                + "</Level>\n"
                + "</Hierarchy>\n"
                + "</Dimension>\n"));
         */
        @Override
        protected List<MappingCubeDimension> cubeDimensionUsageOrDimensions(MappingCube cube) {
            List<MappingCubeDimension> result = new ArrayList<>();
            result.addAll(super.cubeDimensionUsageOrDimensions(cube));
            if ("HR".equals(cube.name())) {
                MappingLevel level = LevelRBuilder
                    .builder()
                    .name("Employee Id")
                    .uniqueMembers(true)
                    .type(TypeEnum.NUMERIC)
                    .column("employee_id")
                    .parentColumn("supervisor_id")
                    .nameColumn("full_name")
                    .nullParentValue("0")
                    .closure(ClosureRBuilder.builder()
                        .parentColumn("supervisor_id")
                        .childColumn("employee_id")
                        .table(new TableR("employee_closure"))
                        .build())
                    .properties(List.of(
                        PropertyRBuilder.builder()
                            .name("Marital Status")
                            .column("marital_status")
                            .build(),
                        PropertyRBuilder.builder()
                            .name("Position Title")
                            .column("position_title")
                            .build(),
                        PropertyRBuilder.builder()
                            .name("Gender")
                            .column("gender")
                            .build(),
                        PropertyRBuilder.builder()
                            .name("Salary")
                            .column("salary")
                            .build(),
                        PropertyRBuilder.builder()
                            .name("Education Level")
                            .column("education_level")
                            .build(),
                        PropertyRBuilder.builder()
                            .name("Management Role")
                            .column("management_role")
                            .build()
                    ))
                    .build();

                MappingHierarchy hierarchy = HierarchyRBuilder
                    .builder()
                    .hasAll(true)
                    .allMemberName("All Employees")
                    .primaryKey("employee_id")
                    .relation(new TableR("employee"))
                    .levels(List.of(level))
                    .build();

                MappingCubeDimension dimension = PrivateDimensionRBuilder
                    .builder()
                    .name("EmployeesNonClosure")
                    .foreignKey("employee_id")
                    .hierarchies(List.of(hierarchy))
                    .build();
                result.add(dimension);
            }
            return result;
        }
    }

    public static class ParentChildHierarchyTestModifier6 extends RDbMappingSchemaModifier {

        public ParentChildHierarchyTestModifier6(MappingSchema mappingSchema) {
            super(mappingSchema);
        }

        /*
            "<Cube name='HR-fewer-dims'>\n"
            + "    <Table name='salary'/>\n"
            + "    <Dimension name='Department' foreignKey='department_id'>\n"
            + "        <Hierarchy hasAll='true' primaryKey='department_id'>\n"
            + "            <Table name='department'/>\n"
            + "            <Level name='Department Description' uniqueMembers='true' column='department_id'/>\n"
            + "        </Hierarchy>\n"
            + "    </Dimension>\n"
            + "    <Dimension name='Employees' foreignKey='employee_id'>\n"
            + "        <Hierarchy hasAll='true' allMemberName='All Employees' primaryKey='employee_id'>\n"
            + "            <Table name='employee'/>\n"
            + "            <Level name='Employee Id' type='Numeric' uniqueMembers='true' column='employee_id' parentColumn='supervisor_id' nameColumn='full_name' nullParentValue='0'>\n"
            + "                <Property name='Marital Status' column='marital_status'/>\n"
            + "                <Property name='Position Title' column='position_title'/>\n"
            + "                <Property name='Gender' column='gender'/>\n"
            + "                <Property name='Salary' column='salary'/>\n"
            + "                <Property name='Education Level' column='education_level'/>\n"
            + "                <Property name='Management Role' column='management_role'/>\n"
            + "            </Level>\n"
            + "        </Hierarchy>\n"
            + "    </Dimension>\n"
            + "    <Measure name='Org Salary' column='salary_paid' aggregator='sum' formatString='Currency' />\n"
            + "    <Measure name='Count' column='employee_id' aggregator='count' formatString='#,#'/>\n"
            + "</Cube>",
         */
        @Override
        protected List<MappingCube> schemaCubes(MappingSchema schema) {
            List<MappingCube> result = new ArrayList<>();
            result.addAll(super.schemaCubes(schema));
            result.add(CubeRBuilder.builder()
                .name("HR-fewer-dims")
                .fact(new TableR("salary"))
                .dimensionUsageOrDimensions(List.of(
                    PrivateDimensionRBuilder.builder()
                        .name("Department")
                        .foreignKey("department_id")
                        .hierarchies(List.of(
                            HierarchyRBuilder.builder()
                                .hasAll(true)
                                .primaryKey("department_id")
                                .relation(new TableR("department"))
                                .levels(List.of(
                                    LevelRBuilder.builder()
                                        .name("Department Description")
                                        .column("department_id")
                                        .uniqueMembers(true)
                                        .build()
                                ))
                                .build()
                        ))
                        .build(),
                    PrivateDimensionRBuilder.builder()
                        .name("Employees")
                        .foreignKey("employee_id")
                        .hierarchies(List.of(
                            HierarchyRBuilder.builder()
                                .hasAll(true)
                                .allMemberName("All Employees")
                                .primaryKey("employee_id")
                                .relation(new TableR("employee"))
                                .levels(List.of(
                                    LevelRBuilder.builder()
                                        .name("Employee Id")
                                        .type(TypeEnum.NUMERIC)
                                        .uniqueMembers(true)
                                        .column("employee_id")
                                        .parentColumn("supervisor_id")
                                        .nameColumn("full_name")
                                        .nullParentValue("0")
                                        .properties(List.of(
                                            PropertyRBuilder.builder()
                                                .name("Marital Status").column("marital_status").build(),
                                            PropertyRBuilder.builder()
                                                .name("Position Title").column("position_title").build(),
                                            PropertyRBuilder.builder()
                                                .name("Gender").column("gender").build(),
                                            PropertyRBuilder.builder()
                                                .name("Salary").column("salary").build(),
                                            PropertyRBuilder.builder()
                                                .name("Education Level").column("education_level").build(),
                                            PropertyRBuilder.builder()
                                                .name("Management Role").column("management_role").build()
                                        ))
                                        .build()
                                ))

                                .build()
                        ))
                        .build()
                ))
                .measures(List.of(
                    MeasureRBuilder.builder()
                        .name("Org Salary")
                        .column("salary_paid")
                        .aggregator("sum")
                        .formatString("Currency")
                        .build(),
                    MeasureRBuilder.builder()
                        .name("Count")
                        .column("employee_id")
                        .aggregator("count")
                        .formatString("#,#")
                        .build()
                ))
                .build()
            );
            return result;
        }
    }

    public static class ParentChildHierarchyTestModifier7 extends RDbMappingSchemaModifier {

        public ParentChildHierarchyTestModifier7(MappingSchema mappingSchema) {
            super(mappingSchema);
        }

        /*
            "<Cube name=\"HR-ordered\">\n"
            + "  <Table name=\"salary\"/>\n"
            + "  <Dimension name=\"Employees\" foreignKey=\"employee_id\">\n"
            + "    <Hierarchy hasAll=\"true\" allMemberName=\"All Employees\"\n"
            + "        primaryKey=\"employee_id\">\n"
            + "      <Table name=\"employee\"/>\n"
            + "      <Level name=\"Employee Id\" type=\"Numeric\" uniqueMembers=\"true\"\n"
            + "          column=\"employee_id\" parentColumn=\"supervisor_id\"\n"
            + "          nameColumn=\"full_name\" nullParentValue=\"0\""
            // Original "HR" cube has no ordinalColumn.
            + "          ordinalColumn=\"last_name\" >\n"
            + "        <Closure parentColumn=\"supervisor_id\" childColumn=\"employee_id\">\n"
            + "          <Table name=\"employee_closure\"/>\n"
            + "        </Closure>\n"
            + "        <Property name=\"First Name\" column=\"first_name\"/>\n"
            + "      </Level>\n"
            + "    </Hierarchy>\n"
            + "  </Dimension>\n"
            + "\n"
            + "  <Measure name=\"Org Salary\" column=\"salary_paid\" aggregator=\"sum\"\n"
            + "      formatString=\"Currency\"/>\n"
            + "  <Measure name=\"Count\" column=\"employee_id\" aggregator=\"count\"\n"
            + "      formatString=\"#,#\"/>\n"
            + "</Cube>",
         */
        @Override
        protected List<MappingCube> schemaCubes(MappingSchema schema) {
            List<MappingCube> result = new ArrayList<>();
            result.addAll(super.schemaCubes(schema));
            result.add(CubeRBuilder.builder()
                .name("HR-ordered")
                .fact(new TableR("salary"))
                .dimensionUsageOrDimensions(List.of(
                    PrivateDimensionRBuilder.builder()
                        .name("Employees")
                        .foreignKey("employee_id")
                        .hierarchies(List.of(
                            HierarchyRBuilder.builder()
                                .hasAll(true)
                                .allMemberName("All Employees")
                                .primaryKey("employee_id")
                                .relation(new TableR("employee"))
                                .levels(List.of(
                                    LevelRBuilder.builder()
                                        .name("Employee Id")
                                        .type(TypeEnum.NUMERIC)
                                        .uniqueMembers(true)
                                        .column("employee_id")
                                        .parentColumn("supervisor_id")
                                        .nameColumn("full_name")
                                        .nullParentValue("0")
                                        .ordinalColumn("last_name")
                                        .closure(ClosureRBuilder.builder()
                                            .table(new TableR("employee_closure"))
                                            .build())
                                        .properties(List.of(
                                            PropertyRBuilder.builder()
                                                .name("First Name")
                                                .column("first_name")
                                                .build()
                                        ))
                                        .build()
                                ))
                                .build()
                        ))
                        .build()
                ))
                .measures(List.of(
                    MeasureRBuilder.builder()
                        .name("Org Salary")
                        .column("salary_paid")
                        .aggregator("sum")
                        .formatString("Currency")
                        .build(),
                    MeasureRBuilder.builder()
                        .name("Count")
                        .column("employee_id")
                        .aggregator("count")
                        .formatString("#,#")
                        .build()
                ))
                .build()
            );
            return result;
        }
    }

    public static class ParentChildHierarchyTestModifier8 extends RDbMappingSchemaModifier {

        public ParentChildHierarchyTestModifier8(MappingSchema mappingSchema) {
            super(mappingSchema);
        }

        /*
            "<Cube name=\"CustomSales\">"
            + "   <Table name=\"sales_fact_1997\"/>"
            + "   <DimensionUsage name=\"Employees\" source=\"Employees\" foreignKey=\"time_id\"/>"
            + "   <Measure name=\"Store Sales\" column=\"store_sales\" aggregator=\"sum\"/>"
            + "</Cube>"
            + "<Cube name=\"CustomHR\">"
            + "   <Table name=\"salary\"/>"
            + "   <DimensionUsage name=\"Employees\" source=\"Employees\" foreignKey=\"employee_id\"/>"
            + "   <Measure name=\"Org Salary\" column=\"salary_paid\" aggregator=\"sum\"/>"
            + "</Cube>"
         */
        @Override
        protected List<MappingCube> schemaCubes(MappingSchema schema) {
            List<MappingCube> result = new ArrayList<>();
            result.addAll(super.schemaCubes(schema));
            result.add(CubeRBuilder.builder()
                .name("CustomSales")
                .fact(new TableR("sales_fact_1997"))
                .dimensionUsageOrDimensions(List.of(
                    DimensionUsageRBuilder.builder()
                        .name("Employees")
                        .source("Employees")
                        .foreignKey("time_id")
                        .build()
                ))
                .measures(List.of(
                    MeasureRBuilder.builder()
                        .name("Store Sales")
                        .column("store_sales")
                        .aggregator("sum")
                        .build()
                ))
                .build()
            );
            result.add(CubeRBuilder.builder()
                .name("CustomHR")
                .fact(new TableR("salary"))
                .dimensionUsageOrDimensions(List.of(
                    DimensionUsageRBuilder.builder()
                        .name("Employees")
                        .source("Employees")
                        .foreignKey("employee_id")
                        .build()
                ))
                .measures(List.of(
                    MeasureRBuilder.builder()
                        .name("Org Salary")
                        .column("salary_paid")
                        .aggregator("sum")
                        .build()
                ))
                .build()
            );

            return result;
        }

        /*
            "<Dimension name=\"Employees\" >"
            + "   <Hierarchy hasAll=\"true\" allMemberName=\"All Employees\""
            + "      primaryKey=\"employee_id\" primaryKeyTable=\"employee\">"
            + "      <Table name=\"employee\"/>"
            + "      <Level name=\"Employee Name\" type=\"Numeric\" uniqueMembers=\"true\""
            + "         column=\"employee_id\" parentColumn=\"supervisor_id\""
            + "         nameColumn=\"full_name\" nullParentValue=\"0\">"
            + "         <Closure parentColumn=\"supervisor_id\" childColumn=\"employee_id\">"
            + "            <Table name=\"employee_closure\"/>"
            + "         </Closure>"
            + "      </Level>"
            + "   </Hierarchy>"
            + "</Dimension>",
         */
        @Override
        protected List<MappingPrivateDimension> schemaDimensions(MappingSchema schema) {
            List<MappingPrivateDimension> result = new ArrayList<>();
            result.addAll(super.schemaDimensions(schema));
            result.add(PrivateDimensionRBuilder.builder()
                .name("Employees")
                .hierarchies(List.of(
                    HierarchyRBuilder.builder()
                        .hasAll(true)
                        .allMemberName("All Employees")
                        .primaryKey("employee_id")
                        .primaryKeyTable("employee")
                        .relation(new TableR("employee"))
                        .levels(List.of(
                            LevelRBuilder.builder()
                                .name("Employee Name")
                                .type(TypeEnum.NUMERIC)
                                .uniqueMembers(true)
                                .column("employee_id")
                                .parentColumn("supervisor_id")
                                .nameColumn("full_name")
                                .nullParentValue("0")
                                .closure(ClosureRBuilder.builder()
                                    .parentColumn("supervisor_id")
                                    .childColumn("employee_id")
                                    .table(new TableR("employee_closure"))
                                    .build())
                                .build()
                        ))
                        .build()
                ))
                .build());
            return result;
        }

        /*
            + "<VirtualCube name=\"CustomSalesAndHR\" >"
            + "<VirtualCubeDimension name=\"Employees\"/>"
            + "<VirtualCubeMeasure cubeName=\"CustomSales\" name=\"[Measures].[Store Sales]\"/>"
            + "<VirtualCubeMeasure cubeName=\"CustomHR\" name=\"[Measures].[Org Salary]\"/>"
            + "<CalculatedMember name=\"HR Cost per Sale\" dimension=\"Measures\">"
            + "<Formula>[Measures].[Store Sales] / [Measures].[Org Salary]</Formula>"
            + "</CalculatedMember>"
            + "</VirtualCube>",
         */
        @Override
        protected List<MappingVirtualCube> schemaVirtualCubes(MappingSchema schema) {
            List<MappingVirtualCube> result = new ArrayList<>();
            result.addAll(super.schemaVirtualCubes(schema));
            result.add(VirtualCubeRBuilder.builder()
                .name("CustomSalesAndHR")
                .virtualCubeDimensions(List.of(
                    VirtualCubeDimensionRBuilder.builder()
                        .name("Employees")
                        .build()
                ))
                .virtualCubeMeasures(List.of(
                    VirtualCubeMeasureRBuilder.builder()
                        .cubeName("CustomSales")
                        .name("[Measures].[Store Sales]")
                        .build(),
                    VirtualCubeMeasureRBuilder.builder()
                        .cubeName("CustomHR")
                        .name("[Measures].[Org Salary]")
                        .build()
                ))
                .calculatedMembers(List.of(
                    CalculatedMemberRBuilder.builder()
                        .name("HR Cost per Sale")
                        .dimension("Measures")
                        .formulaElement(FormulaRBuilder.builder()
                            .cdata("[Measures].[Store Sales] / [Measures].[Org Salary]")
                            .build())
                        .build()
                ))
                .build());
            return result;
        }
    }

    public static class ParentChildHierarchyTestModifier9 extends RDbMappingSchemaModifier {

        public ParentChildHierarchyTestModifier9(MappingSchema mappingSchema) {
            super(mappingSchema);
        }

        /*
                "<Cube name=\"HR4C\">\n"
                        + "  <Table name=\"salary\"/>\n"
                        + "  <Dimension name=\"Employees\" foreignKey=\"employee_id\">\n"
                        + "    <Hierarchy hasAll=\"true\" allMemberName=\"All\"\n"
                        + "        primaryKey=\"employee_id\">\n"
                        + "      <Table name=\"employee\"/>\n"
                        + "      <Level name=\"Employee Id\" type=\"Numeric\" uniqueMembers=\"true\"\n"
                        + "          column=\"employee_id\" parentColumn=\"supervisor_id\"\n"
                        + "          nameColumn=\"full_name\" nullParentValue=\"0\">\n";

                "        <Closure parentColumn=\"supervisor_id\" childColumn=\"employee_id\">\n"
                        + "          <Table name=\"employee_closure\"/>\n"
                        + "        </Closure>\n";

                "      </Level>\n"
                        + "    </Hierarchy>\n"
                        + "  </Dimension>\n"
                        + "\n"
                        + "  <Measure name=\"Count\" column=\"employee_id\" aggregator=\"count\" />\n"
                        + "</Cube>\n";
         */
        @Override
        protected List<MappingCube> schemaCubes(MappingSchema schema) {
            List<MappingCube> result = new ArrayList<>();
            result.addAll(super.schemaCubes(schema));
            result.add(CubeRBuilder.builder()
                .name("HR4C")
                .fact(new TableR("salary"))
                .dimensionUsageOrDimensions(List.of(


                    PrivateDimensionRBuilder.builder()
                        .name("Employees")
                        .foreignKey("employee_id")
                        .hierarchies(List.of(
                            HierarchyRBuilder.builder()
                                .hasAll(true)
                                .allMemberName("All")
                                .primaryKey("employee_id")
                                .relation(new TableR("employee"))
                                .levels(List.of(
                                    LevelRBuilder.builder()
                                        .name("Employee Id")
                                        .type(TypeEnum.NUMERIC)
                                        .uniqueMembers(true)
                                        .column("employee_id")
                                        .parentColumn("supervisor_id")
                                        .nameColumn("full_name")
                                        .nullParentValue("0")
                                        .closure(ClosureRBuilder.builder()
                                            .parentColumn("supervisor_id")
                                            .childColumn("employee_id")
                                            .table(new TableR("employee_closure"))
                                            .build())
                                        .build()
                                ))
                                .build()
                        ))
                        .build()
                ))
                .measures(List.of(
                    MeasureRBuilder.builder()
                        .name("Count")
                        .column("employee_id")
                        .aggregator("count")
                        .build()
                ))
                .build()
            );

            return result;
        }

    }

    public static class ParentChildHierarchyTestModifier10 extends RDbMappingSchemaModifier {

        public ParentChildHierarchyTestModifier10(MappingSchema mappingSchema) {
            super(mappingSchema);
        }

        /*
                "<Cube name=\"HR4C\">\n"
                        + "  <Table name=\"salary\"/>\n"
                        + "  <Dimension name=\"Employees\" foreignKey=\"employee_id\">\n"
                        + "    <Hierarchy hasAll=\"true\" allMemberName=\"All\"\n"
                        + "        primaryKey=\"employee_id\">\n"
                        + "      <Table name=\"employee\"/>\n"
                        + "      <Level name=\"Employee Id\" type=\"Numeric\" uniqueMembers=\"true\"\n"
                        + "          column=\"employee_id\" parentColumn=\"supervisor_id\"\n"
                        + "          nameColumn=\"full_name\" nullParentValue=\"0\">\n";


                "      </Level>\n"
                        + "    </Hierarchy>\n"
                        + "  </Dimension>\n"
                        + "\n"
                        + "  <Measure name=\"Count\" column=\"employee_id\" aggregator=\"count\" />\n"
                        + "</Cube>\n";
         */
        @Override
        protected List<MappingCube> schemaCubes(MappingSchema schema) {
            List<MappingCube> result = new ArrayList<>();
            result.addAll(super.schemaCubes(schema));
            result.add(CubeRBuilder.builder()
                .name("HR4C")
                .fact(new TableR("salary"))
                .dimensionUsageOrDimensions(List.of(
                    PrivateDimensionRBuilder.builder()
                        .name("Employees")
                        .foreignKey("employee_id")
                        .hierarchies(List.of(
                            HierarchyRBuilder.builder()
                                .hasAll(true)
                                .allMemberName("All")
                                .primaryKey("employee_id")
                                .relation(new TableR("employee"))
                                .levels(List.of(
                                    LevelRBuilder.builder()
                                        .name("Employee Id")
                                        .type(TypeEnum.NUMERIC)
                                        .uniqueMembers(true)
                                        .column("employee_id")
                                        .parentColumn("supervisor_id")
                                        .nameColumn("full_name")
                                        .nullParentValue("0")
                                        .build()
                                ))
                                .build()
                        ))
                        .build()
                ))
                .measures(List.of(
                    MeasureRBuilder.builder()
                        .name("Count")
                        .column("employee_id")
                        .aggregator("count")
                        .build()
                ))
                .build()
            );

            return result;
        }

    }

    public static class ParentChildHierarchyTestModifier11 extends RDbMappingSchemaModifier {

        public ParentChildHierarchyTestModifier11(MappingSchema mappingSchema) {
            super(mappingSchema);
        }

        /*
                    "<Schema name='FoodMart'>\n"
            + "  <Dimension type='StandardDimension' highCardinality='false' name='Employee'>\n"
            + "    <Hierarchy name='Employee' hasAll='false' primaryKey='store_id' primaryKeyTable='bri_store_employee'>\n"
            + "      <Join leftKey='employee_id' rightKey='employee_id'>\n"
            + "        <InlineTable alias='bri_store_employee'>\n"
            + "          <ColumnDefs>\n"
            + "            <ColumnDef name='store_id' type='Integer'/>\n"
            + "            <ColumnDef name='employee_id' type='Integer'/>\n"
            + "          </ColumnDefs>\n"
            + "          <Rows>\n"
            + "            <Row>\n"
            + "              <Value column='store_id'>2</Value>\n"
            + "              <Value column='employee_id'>o</Value>\n"
            + "            </Row>\n"
            + "            <Row>\n"
            + "              <Value column='store_id'>2</Value>\n"
            + "              <Value column='employee_id'>1</Value>\n"
            + "            </Row>\n"
            + "            <Row>\n"
            + "              <Value column='store_id'>2</Value>\n"
            + "              <Value column='employee_id'>2</Value>\n"
            + "            </Row>\n"
            + "            <Row>\n"
            + "              <Value column='store_id'>2</Value>\n"
            + "              <Value column='employee_id'>22</Value>\n"
            + "            </Row>\n"
            + "            <Row>\n"
            + "              <Value column='store_id'>2</Value>\n"
            + "              <Value column='employee_id'>22</Value>\n"
            + "            </Row>\n"
            + "            <Row>\n"
            + "              <Value column='store_id'>2</Value>\n"
            + "              <Value column='employee_id'>32</Value>\n"
            + "            </Row>\n"
            + "            <Row>\n"
            + "              <Value column='store_id'>2</Value>\n"
            + "              <Value column='employee_id'>484</Value>\n"
            + "            </Row>\n"
            + "          </Rows>\n"
            + "        </InlineTable>\n"
            + "        <Table name='employee' alias='employee'/>\n"
            + "      </Join>\n"
            + "      <Level name='Employee' table='employee' column='employee_id' nameColumn='full_name' parentColumn='supervisor_id' nullParentValue='0' type='Integer' uniqueMembers='true' levelType='Regular' hideMemberIf='Never'>\n"
            + "        <Closure parentColumn='supervisor_id' childColumn='employee_id'>\n"
            + "          <Table name='employee_closure'/>\n"
            + "        </Closure>\n"
            + "      </Level>\n"
            + "    </Hierarchy>\n"
            + "  </Dimension>\n"
            + "  <Cube name='Sales_Bug_441' cache='true' enabled='true'>\n"
            + "    <Table name='sales_fact_1997'/>\n"
            + "    <DimensionUsage source='Employee' name='Employee' foreignKey='store_id' highCardinality='false'/>\n"
            + "    <Measure name='Store Sales' column='store_sales' datatype='Numeric' formatString='#,###.00' aggregator='sum' visible='true'/>\n"
            + "  </Cube>\n"
            + "</Schema>");

         */
        @Override
        protected MappingSchema modifyMappingSchema(MappingSchema mappingSchemaOriginal) {
            MappingInlineTable it = InlineTableRBuilder.builder()
                .alias("bri_store_employee")
                .columnDefs(List.of(
                    ColumnDefRBuilder.builder()
                        .name("store_id")
                        .type(TypeEnum.INTEGER)
                        .build(),
                    ColumnDefRBuilder.builder()
                        .name("employee_id")
                        .type(TypeEnum.INTEGER)
                        .build()
                ))
                .rows(List.of(
                    RowRBuilder.builder()
                        .values(List.of(
                            ValueRBuilder.builder().column("store_id").content("2").build(),
                            ValueRBuilder.builder().column("employee_id").content("o").build()
                        ))
                        .build(),
                    RowRBuilder.builder()
                        .values(List.of(
                            ValueRBuilder.builder().column("store_id").content("2").build(),
                            ValueRBuilder.builder().column("employee_id").content("1").build()
                        ))
                        .build(),
                    RowRBuilder.builder()
                        .values(List.of(
                            ValueRBuilder.builder().column("store_id").content("2").build(),
                            ValueRBuilder.builder().column("employee_id").content("2").build()
                        ))
                        .build(),
                    RowRBuilder.builder()
                        .values(List.of(
                            ValueRBuilder.builder().column("store_id").content("2").build(),
                            ValueRBuilder.builder().column("employee_id").content("22").build()
                        ))
                        .build(),
                    RowRBuilder.builder()
                        .values(List.of(
                            ValueRBuilder.builder().column("store_id").content("2").build(),
                            ValueRBuilder.builder().column("employee_id").content("22").build()
                        ))
                        .build(),
                    RowRBuilder.builder()
                        .values(List.of(
                            ValueRBuilder.builder().column("store_id").content("2").build(),
                            ValueRBuilder.builder().column("employee_id").content("32").build()
                        ))
                        .build(),
                    RowRBuilder.builder()
                        .values(List.of(
                            ValueRBuilder.builder().column("store_id").content("2").build(),
                            ValueRBuilder.builder().column("employee_id").content("484").build()
                        ))
                        .build()

                ))
                .build();
            MappingTable t = new TableR(null, "employee", "employee", List.of());

            return SchemaRBuilder.builder()
                .name("FoodMart")
                .dimensions(List.of(
                    PrivateDimensionRBuilder.builder()
                        .type(DimensionTypeEnum.STANDARD_DIMENSION)
                        .highCardinality(false)
                        .name("Employee")
                        .hierarchies(List.of(
                            HierarchyRBuilder.builder()
                                .name("Employee")
                                .hasAll(false)
                                .primaryKey("store_id")
                                .primaryKeyTable("bri_store_employee")
                                .relation( new JoinR(
                                    List.of(it, t),
                                    null, "employee_id", null, "employee_id"))
                                .levels(List.of(
                                    LevelRBuilder.builder()
                                        .name("Employee")
                                        .table("employee")
                                        .column("employee_id")
                                        .nameColumn("full_name")
                                        .parentColumn("supervisor_id")
                                        .nullParentValue("nullParentValue")
                                        .type(TypeEnum.INTEGER)
                                        .uniqueMembers(true)
                                        .levelType(LevelTypeEnum.REGULAR)
                                        .hideMemberIf(HideMemberIfEnum.NEVER)
                                        .closure(ClosureRBuilder.builder()
                                            .parentColumn("supervisor_id")
                                            .childColumn("employee_id")
                                            .table(new TableR("employee_closure"))
                                            .build())
                                        .build()
                                ))
                                .build()
                        ))
                        .build()
                ))
                .cubes(List.of(
                    CubeRBuilder.builder()
                        .name("Sales_Bug_441")
                        .cache(true)
                        .enabled(true)
                        .fact(new TableR("sales_fact_1997"))
                        .dimensionUsageOrDimensions(List.of(
                            DimensionUsageRBuilder.builder()
                                .source("Employee")
                                .name("Employee")
                                .foreignKey("store_id")
                                .highCardinality(false)
                                .build()
                        ))
                        .measures(List.of(
                            MeasureRBuilder.builder()
                                .name("Store Sales")
                                .column("store_sales")
                                .datatype(MeasureDataTypeEnum.NUMERIC)
                                .formatString("#,###.00")
                                .aggregator("sum")
                                .visible(true)
                                .build()
                        ))
                        .build()
                ))
                .build();
        }
    }

    public static class ValidMeasureFunDefTestModifier extends RDbMappingSchemaModifier {

        public ValidMeasureFunDefTestModifier(MappingSchema mappingSchema) {
            super(mappingSchema);
        }

        /*
    "<?xml version=\"1.0\"?>\n"
    + "<Schema name=\"FoodMart\">\n"
    + "  <Dimension name=\"Product\">\n"
    + "    <Hierarchy hasAll=\"true\" primaryKey=\"product_id\" primaryKeyTable=\"product\">\n"
    + "      <Join leftKey=\"product_class_id\" rightKey=\"product_class_id\">\n"
    + "        <Table name=\"product\"/>\n"
    + "        <Table name=\"product_class\"/>\n"
    + "      </Join>\n"
    + "      <Level name=\"Product Name\" table=\"product\" column=\"product_name\" uniqueMembers=\"true\"/>\n"
    + "    </Hierarchy>\t\n"
    + "\t<Hierarchy name=\"BrandOnly\" hasAll=\"true\" primaryKey=\"product_id\" primaryKeyTable=\"product\">\n"
    + "      <Join leftKey=\"product_class_id\" rightKey=\"product_class_id\">\n"
    + "        <Table name=\"product\"/>\n"
    + "        <Table name=\"product_class\"/>\n"
    + "      </Join>\n"
    + "      <Level name=\"Product\" table=\"product\" column=\"brand_name\" uniqueMembers=\"false\"/>\n"
    + "    </Hierarchy>\n"
    + "  </Dimension>\n"
    + "  <Cube name=\"Sales\" defaultMeasure=\"Unit Sales\">\n"
    + "    <Table name=\"sales_fact_1997\"/>\n"
    + "    <DimensionUsage name=\"Product\" source=\"Product\" foreignKey=\"product_id\"/>\n"
    + "  </Cube>\n"
    + "  <Cube name=\"Sales 1\" cache=\"true\" enabled=\"true\">\n"
    + "    <Table name=\"sales_fact_1997\"/>\n"
    + "\t<Measure name=\"Unit Sales1\" column=\"unit_sales\" aggregator=\"sum\"\n"
    + "      formatString=\"Standard\"/>\n" + "  </Cube>  \n"
    + " \n"
    + "  <VirtualCube enabled=\"true\" name=\"Virtual Cube\">\n"
    + "\t<VirtualCubeDimension cubeName=\"Sales\" highCardinality=\"false\" name=\"Product\">\n"
    + "    </VirtualCubeDimension>\n"
    + "    <VirtualCubeMeasure cubeName=\"Sales 1\" name=\"[Measures].[Unit Sales1]\" visible=\"true\">\n"
    + "    </VirtualCubeMeasure>\n"
    + "  </VirtualCube>\n" + "</Schema>";
         */
        @Override
        protected MappingSchema modifyMappingSchema(MappingSchema mappingSchemaOriginal) {
            return SchemaRBuilder.builder()
                .name("FoodMart")
                .dimensions(List.of(
                    PrivateDimensionRBuilder.builder()
                        .name("Product")
                        .hierarchies(List.of(
                            HierarchyRBuilder.builder()
                                .hasAll(true)
                                .primaryKey("product_id")
                                .primaryKeyTable("product")
                                .relation( new JoinR(
                                    List.of(new TableR("product"), new TableR("product_class")),
                                    null, "product_class_id", null, "product_class_id"))
                                .levels(List.of(
                                    LevelRBuilder.builder()
                                        .name("Product Name")
                                        .table("product")
                                        .column("product_name")
                                        .uniqueMembers(true)
                                        .build()
                                ))
                                .build(),
                            HierarchyRBuilder.builder()
                                .name("BrandOnly")
                                .hasAll(true)
                                .primaryKey("product_id")
                                .primaryKeyTable("product")
                                .relation(new JoinR(
                                    List.of(new TableR("product"), new TableR("product_class")),
                                    null, "product_class_id",
                                    null, "product_class_id"
                                ))
                                .levels(List.of(
                                    LevelRBuilder.builder()
                                        .name("Product")
                                        .table("product")
                                        .column("brand_name")
                                        .uniqueMembers(false)
                                        .build()
                                ))
                                .build()
                        ))
                        .build()
                ))
                .cubes(List.of(
                    CubeRBuilder.builder()
                        .name("Sales")
                        .defaultMeasure("Unit Sales")
                        .fact(new TableR("sales_fact_1997"))
                        .dimensionUsageOrDimensions(List.of(
                            DimensionUsageRBuilder.builder()
                                .source("Product")
                                .name("Product")
                                .foreignKey("product_id")
                                .build()
                        ))
                        .build(),
                    CubeRBuilder.builder()
                        .name("Sales 1")
                        .cache(true)
                        .enabled(true)
                        .fact(new TableR("sales_fact_1997"))
                        .measures(List.of(
                            MeasureRBuilder.builder()
                                .name("Unit Sales1")
                                .column("unit_sales")
                                .aggregator("sum")
                                .formatString("Standard")
                                .build()
                        ))
                        .build()
                ))
                .virtualCubes(List.of(
                    VirtualCubeRBuilder.builder()
                        .enabled(true)
                        .name("Virtual Cube")
                        .virtualCubeDimensions(List.of(
                            VirtualCubeDimensionRBuilder.builder()
                                .cubeName("Sales")
                                .highCardinality(false)
                                .name("Product")
                                .build()
                        ))
                        .virtualCubeMeasures(List.of(
                            VirtualCubeMeasureRBuilder.builder()
                                .cubeName("Sales 1")
                                .name("[Measures].[Unit Sales1]")
                                .visible(true)
                                .build()
                        ))
                        .build()
                ))
                .build();
        }
    }

}
