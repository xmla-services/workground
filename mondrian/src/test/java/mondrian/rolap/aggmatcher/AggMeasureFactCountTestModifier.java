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

import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggExclude;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggTable;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.DimensionTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.LevelTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.PropertyTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.TypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.TableR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.CubeRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.DimensionUsageRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.HierarchyRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.LevelRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.MeasureRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.PrivateDimensionRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.PropertyRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.SchemaRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.modifier.record.RDbMappingSchemaModifier;

public class AggMeasureFactCountTestModifier extends RDbMappingSchemaModifier {

    public AggMeasureFactCountTestModifier(MappingSchema mappingSchema) {
        super(mappingSchema);
    }
/*
            + "<Schema name=\"FoodMart\">\n"
            + "<Dimension name=\"Time\" type=\"TimeDimension\">\n"
            + "    <Hierarchy hasAll=\"false\" primaryKey=\"time_id\">\n"
            + "      <Table name=\"time_csv\"/>\n"
            + "      <Level name=\"Year\" column=\"the_year\" type=\"Numeric\" uniqueMembers=\"true\"\n"
            + "          levelType=\"TimeYears\"/>\n"
            + "      <Level name=\"Quarter\" column=\"quarter\" uniqueMembers=\"false\"\n"
            + "          levelType=\"TimeQuarters\"/>\n"
            + "      <Level name=\"Month\" column=\"month_of_year\" uniqueMembers=\"false\" type=\"Numeric\"\n"
            + "          levelType=\"TimeMonths\"/>\n"
            + "    </Hierarchy>\n"
            + "    <Hierarchy hasAll=\"true\" name=\"Weekly\" primaryKey=\"time_id\">\n"
            + "      <Table name=\"time_csv\"/>\n"
            + "      <Level name=\"Year\" column=\"the_year\" type=\"Numeric\" uniqueMembers=\"true\"\n"
            + "          levelType=\"TimeYears\"/>\n"
            + "      <Level name=\"Week\" column=\"week_of_year\" type=\"Numeric\" uniqueMembers=\"false\"\n"
            + "          levelType=\"TimeWeeks\"/>\n"
            + "      <Level name=\"Day\" column=\"day_of_month\" uniqueMembers=\"false\" type=\"Numeric\"\n"
            + "          levelType=\"TimeDays\"/>\n"
            + "    </Hierarchy>\n"
            + "  </Dimension>\n"
            + "<Dimension name=\"Store\">\n"
            + "    <Hierarchy hasAll=\"true\" primaryKey=\"store_id\">\n"
            + "      <Table name=\"store\"/>\n"
            + "      <Level name=\"Store Country\" column=\"store_country\" uniqueMembers=\"true\"/>\n"
            + "      <Level name=\"Store State\" column=\"store_state\" uniqueMembers=\"true\"/>\n"
            + "      <Level name=\"Store City\" column=\"store_city\" uniqueMembers=\"false\"/>\n"
            + "      <Level name=\"Store Name\" column=\"store_name\" uniqueMembers=\"true\">\n"
            + "        <Property name=\"Store Type\" column=\"store_type\"/>\n"
            + "        <Property name=\"Store Manager\" column=\"store_manager\"/>\n"
            + "        <Property name=\"Store Sqft\" column=\"store_sqft\" type=\"Numeric\"/>\n"
            + "        <Property name=\"Grocery Sqft\" column=\"grocery_sqft\" type=\"Numeric\"/>\n"
            + "        <Property name=\"Frozen Sqft\" column=\"frozen_sqft\" type=\"Numeric\"/>\n"
            + "        <Property name=\"Meat Sqft\" column=\"meat_sqft\" type=\"Numeric\"/>\n"
            + "        <Property name=\"Has coffee bar\" column=\"coffee_bar\" type=\"Boolean\"/>\n"
            + "        <Property name=\"Street address\" column=\"store_street_address\" type=\"String\"/>\n"
            + "      </Level>\n"
            + "    </Hierarchy>\n"
            + "  </Dimension>"
            + "<Cube name=\"Sales\" defaultMeasure=\"Unit Sales\"> \n"
            + "<Table name=\"fact_csv_2016\"> \n"

            // add aggregation table here
            + "%AGG_DESCRIPTION_HERE%"

            + "</Table> \n"
            + "<DimensionUsage name=\"Time\" source=\"Time\" foreignKey=\"time_id\"/> \n"
            + "<DimensionUsage name=\"Store\" source=\"Store\" foreignKey=\"store_id\"/>"
            + "<Measure name=\"Unit Sales\" column=\"unit_sales\" aggregator=\"avg\"\n"
            + "   formatString=\"Standard\"/>\n"
            + "<Measure name=\"Store Cost\" column=\"store_cost\" aggregator=\"avg\"\n"
            + "   formatString=\"#,###.00\"/>\n"
            + "<Measure name=\"Store Sales\" column=\"store_sales\" aggregator=\"avg\"\n"
            + "   formatString=\"#,###.00\"/>\n"
            + "</Cube>\n"
            + "</Schema>";

 */
    protected MappingSchema modifyMappingSchema(MappingSchema mappingSchemaOriginal) {
        return SchemaRBuilder.builder()
            .name("FoodMart")
            .dimensions(List.of(
                PrivateDimensionRBuilder.builder()
                    .name("Time")
                    .type(DimensionTypeEnum.TIME_DIMENSION)
                    .hierarchies(List.of(
                        HierarchyRBuilder.builder()
                            .hasAll(false)
                            .primaryKey("time_id")
                            .relation(new TableR("time_csv"))
                            .levels(List.of(
                                LevelRBuilder.builder()
                                    .name("Year")
                                    .column("the_year")
                                    .type(TypeEnum.NUMERIC)
                                    .uniqueMembers(true)
                                    .levelType(LevelTypeEnum.TIME_YEARS)
                                    .build(),
                                LevelRBuilder.builder()
                                    .name("Quarter")
                                    .column("quarter")
                                    .uniqueMembers(false)
                                    .levelType(LevelTypeEnum.TIME_QUARTERS)
                                    .build(),
                                LevelRBuilder.builder()
                                    .name("Month")
                                    .column("month_of_year")
                                    .uniqueMembers(false)
                                    .type(TypeEnum.NUMERIC)
                                    .levelType(LevelTypeEnum.TIME_MONTHS)
                                    .build()
                            ))
                            .build(),
                        HierarchyRBuilder.builder()
                            .hasAll(true)
                            .name("Weekly")
                            .primaryKey("time_id")
                            .relation(new TableR("time_csv"))
                            .levels(List.of(
                                LevelRBuilder.builder()
                                    .name("Year")
                                    .column("the_year")
                                    .type(TypeEnum.NUMERIC)
                                    .uniqueMembers(true)
                                    .levelType(LevelTypeEnum.TIME_YEARS)
                                    .build(),
                                LevelRBuilder.builder()
                                    .name("Week")
                                    .column("week_of_year")
                                    .type(TypeEnum.NUMERIC)
                                    .uniqueMembers(false)
                                    .levelType(LevelTypeEnum.TIME_WEEKS)
                                    .build(),
                                LevelRBuilder.builder()
                                    .name("Day")
                                    .column("day_of_month")
                                    .type(TypeEnum.NUMERIC)
                                    .uniqueMembers(false)
                                    .levelType(LevelTypeEnum.TIME_DAYS)
                                    .build()
                            ))
                            .build()
                    ))
                    .build(),
                PrivateDimensionRBuilder.builder()
                    .name("Store")
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
                                    .name("Store State")
                                    .column("store_state")
                                    .uniqueMembers(true)
                                    .build(),
                                LevelRBuilder.builder()
                                    .name("Store City")
                                    .column("store_city")
                                    .uniqueMembers(false)
                                    .build(),
                                LevelRBuilder.builder()
                                    .name("Store Name")
                                    .column("store_name")
                                    .uniqueMembers(true)
                                    .properties(List.of(
                                        PropertyRBuilder.builder().name("Store Type").column("store_type").build(),
                                        PropertyRBuilder.builder().name("Store Manager").column("store_manager").build(),
                                        PropertyRBuilder.builder().name("Store Sqft").column("store_sqft")
                                            .type(PropertyTypeEnum.NUMERIC).build(),
                                        PropertyRBuilder.builder().name("Grocery Sqft").column("grocery_sqft")
                                            .type(PropertyTypeEnum.NUMERIC).build(),
                                        PropertyRBuilder.builder().name("Frozen Sqft").column("frozen_sqft")
                                            .type(PropertyTypeEnum.NUMERIC).build(),
                                        PropertyRBuilder.builder().name("Meat Sqft").column("meat_sqft")
                                            .type(PropertyTypeEnum.NUMERIC).build(),
                                        PropertyRBuilder.builder().name("Has coffee bar").column("coffee_bar")
                                            .type(PropertyTypeEnum.BOOLEAN).build(),
                                        PropertyRBuilder.builder().name("Street address").column("store_street_address")
                                            .type(PropertyTypeEnum.STRING).build()
                                        ))
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
                    .fact(new TableR("fact_csv_2016", getAggExcludes(), getAggTables()))
                    .dimensionUsageOrDimensions(List.of(
                        DimensionUsageRBuilder.builder()
                            .name("Time")
                            .source("Time")
                            .foreignKey("time_id")
                            .build(),
                        DimensionUsageRBuilder.builder()
                            .name("Store")
                            .source("Store")
                            .foreignKey("store_id")
                            .build()

                    ))
                    .measures(List.of(
                        MeasureRBuilder.builder()
                            .name("Unit Sales")
                            .column("unit_sales")
                            .aggregator("avg")
                            .formatString("Standard")
                            .build(),
                        MeasureRBuilder.builder()
                            .name("Store Cost")
                            .column("store_cost")
                            .aggregator("avg")
                            .formatString("#,###.00")
                            .build(),
                        MeasureRBuilder.builder()
                            .name("Store Sales")
                            .column("store_sales")
                            .aggregator("avg")
                            .formatString("#,###.00")
                            .build()
                    ))
                    .build()
            ))
            .build();

    }

    protected List<MappingAggTable> getAggTables() {
        return List.of();
    }

    protected List<MappingAggExclude> getAggExcludes() {
        return List.of();
    }
}
