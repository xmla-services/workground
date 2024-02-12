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
package mondrian.rolap.agg;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingRole;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.AccessEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.DimensionTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.HideMemberIfEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.LevelTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.MemberGrantAccessEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.TypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.TableR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.AggColumnNameRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.AggLevelRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.AggMeasureRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.AggNameRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.CubeGrantRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.CubeRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.HierarchyGrantRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.HierarchyRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.LevelRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.MeasureRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.MemberGrantRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.PrivateDimensionRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.RoleRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.SchemaGrantRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.modifier.record.RDbMappingSchemaModifier;

public class AggregationOnInvalidRoleTestModifier extends RDbMappingSchemaModifier {

    public AggregationOnInvalidRoleTestModifier(MappingSchema mappingSchema) {
        super(mappingSchema);
    }

    /*
      + "<Cube name=\"mondrian2225\" visible=\"true\" cache=\"true\" enabled=\"true\">"
        + "  <Table name=\"mondrian2225_fact\">"
        + "    <AggName name=\"mondrian2225_agg\" ignorecase=\"true\">"
        + "      <AggFactCount column=\"fact_count\"/>"
        + "      <AggMeasure column=\"fact_Measure\" name=\"[Measures].[Measure]\"/>"
        + "      <AggLevel column=\"dim_code\" name=\"[Product Code].[Code]\" collapsed=\"true\"/>"
        + "    </AggName>"
        + "  </Table>"
        + "  <Dimension type=\"StandardDimension\" visible=\"true\" foreignKey=\"customer_id\" highCardinality=\"false\" name=\"Customer\">"
        + "    <Hierarchy name=\"Customer\" visible=\"true\" hasAll=\"true\" primaryKey=\"customer_id\">"
        + "      <Table name=\"mondrian2225_customer\"/>"
        + "        <Level name=\"First Name\" visible=\"true\" column=\"customer_name\" type=\"String\" uniqueMembers=\"false\" levelType=\"Regular\" hideMemberIf=\"Never\"/>"
        + "    </Hierarchy>"
        + "  </Dimension>"
        + "  <Dimension type=\"StandardDimension\" visible=\"true\" foreignKey=\"product_ID\" highCardinality=\"false\" name=\"Product Code\">"
        + "    <Hierarchy name=\"Product Code\" visible=\"true\" hasAll=\"true\" primaryKey=\"product_id\">"
        + "      <Table name=\"mondrian2225_dim\"/>"
        + "      <Level name=\"Code\" visible=\"true\" column=\"product_code\" type=\"String\" uniqueMembers=\"false\" levelType=\"Regular\" hideMemberIf=\"Never\"/>"
        + "    </Hierarchy>"
        + "  </Dimension>"
        + "  <Measure name=\"Measure\" column=\"fact\" aggregator=\"sum\" visible=\"true\"/>"
        + "</Cube>";
     */
    @Override
    protected List<MappingCube> schemaCubes(MappingSchema mappingSchemaOriginal) {
        List<MappingCube> result = new ArrayList<>();
        result.addAll(super.schemaCubes(mappingSchemaOriginal));
        result.add(CubeRBuilder.builder()
            .name("mondrian2225")
            .visible(true)
            .cache(true)
            .enabled(true)
            .fact(new TableR("mondrian2225_fact", List.of(), List.of(
                AggNameRBuilder.builder()
                    .name("mondrian2225_agg")
                    .ignorecase(true)
                    .aggFactCount(AggColumnNameRBuilder
                        .builder()
                        .column("fact_count")
                        .build())
                    .aggMeasures(List.of(
                        AggMeasureRBuilder.builder()
                            .column("fact_Measure")
                            .name("[Measures].[Measure]")
                            .build()
                    ))
                    .aggLevels(List.of(
                        AggLevelRBuilder.builder()
                            .column("dim_code")
                            .name("[Product Code].[Code]")
                            .collapsed(true)
                            .build()
                    ))
                    .build()
            )))
            .dimensionUsageOrDimensions(List.of(
                PrivateDimensionRBuilder.builder()
                    .type(DimensionTypeEnum.STANDARD_DIMENSION)
                    .visible(true)
                    .foreignKey("customer_id")
                    .name("Customer")
                    .hierarchies(List.of(
                        HierarchyRBuilder.builder()
                            .name("Customer")
                            .visible(true)
                            .hasAll(true)
                            .primaryKey("customer_id")
                            .relation(new TableR("mondrian2225_customer"))
                            .levels(List.of(
                                LevelRBuilder.builder()
                                    .name("First Name")
                                    .visible(true)
                                    .column("customer_name").type(TypeEnum.STRING)
                                    .uniqueMembers(false)
                                    .levelType(LevelTypeEnum.REGULAR)
                                    .hideMemberIf(HideMemberIfEnum.NEVER)
                                    .build()
                            ))
                            .build()
                    ))
                    .build(),
                PrivateDimensionRBuilder.builder()
                    .type(DimensionTypeEnum.STANDARD_DIMENSION)
                    .visible(true)
                    .foreignKey("product_ID")
                    .name("Product Code")
                    .hierarchies(List.of(
                        HierarchyRBuilder.builder()
                            .name("Product Code")
                            .visible(true)
                            .hasAll(true)
                            .primaryKey("product_id")
                            .relation(new TableR("mondrian2225_dim"))
                            .levels(List.of(
                                LevelRBuilder.builder()
                                    .name("Code")
                                    .visible(true)
                                    .column("product_code")
                                    .type(TypeEnum.STRING)
                                    .uniqueMembers(false)
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
                    .name("Measure")
                    .column("fact")
                    .aggregator("sum")
                    .visible(true)
                    .build()
            ))
            .build());

        return result;
    }

    /*
            + "<Role name=\"Test\">"
        + "  <SchemaGrant access=\"none\">"
        + "    <CubeGrant cube=\"mondrian2225\" access=\"all\">"
        + "      <HierarchyGrant hierarchy=\"[Customer.Customer]\" topLevel=\"[Customer.Customer].[First Name]\" access=\"custom\">"
        + "        <MemberGrant member=\"[Customer.Customer].[NonExistingName]\" access=\"all\"/>"
        + "      </HierarchyGrant>"
        + "    </CubeGrant>"
        + "  </SchemaGrant>"
        + "</Role>";

     */
    @Override
    protected List<MappingRole> schemaRoles(MappingSchema mappingSchemaOriginal) {
        List<MappingRole> result = new ArrayList<>();
        result.addAll(super.schemaRoles(mappingSchemaOriginal));
        result.add(RoleRBuilder.builder()
            .name("Test")
            .schemaGrants(List.of(
                SchemaGrantRBuilder.builder()
                    .access(AccessEnum.NONE)
                    .cubeGrants(List.of(
                        CubeGrantRBuilder.builder()
                            .cube("mondrian2225")
                            .access("all")
                            .hierarchyGrants(List.of(
                                HierarchyGrantRBuilder.builder()
                                    .hierarchy("[Customer.Customer]")
                                    .topLevel("[Customer.Customer].[First Name]")
                                    .access(AccessEnum.CUSTOM)
                                    .memberGrants(List.of(
                                        MemberGrantRBuilder.builder()
                                            .member("[Customer.Customer].[NonExistingName]")
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
