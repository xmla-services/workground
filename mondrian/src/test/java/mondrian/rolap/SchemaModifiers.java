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
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingRole;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.AccessEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.HideMemberIfEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.LevelTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.MemberGrantAccessEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.TypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.TableR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.CubeGrantRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.CubeRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.DimensionUsageRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.HierarchyGrantRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.HierarchyRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.LevelRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.MeasureRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.MemberGrantRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.PrivateDimensionRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.RoleRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.SchemaGrantRBuilder;
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

}
