/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2003-2005 Julian Hyde
// Copyright (C) 2005-2017 Hitachi Vantara
// All Rights Reserved.
*/

package mondrian.rolap.aggmatcher;

import mondrian.olap.MondrianException;
import mondrian.olap.Result;
import org.eclipse.daanse.sql.dialect.api.DatabaseProduct;
import mondrian.test.PropertySaver5;
import mondrian.test.SqlPattern;
import mondrian.test.loader.CsvDBTestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.opencube.junit5.ContextSource;
import org.opencube.junit5.TestUtil;
import org.opencube.junit5.context.Context;
import org.opencube.junit5.dataloader.FastFoodmardDataLoader;
import org.opencube.junit5.propupdator.AppandFoodMartCatalogAsFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.opencube.junit5.TestUtil.assertQueryReturns;
import static org.opencube.junit5.TestUtil.withSchema;

public class AggMeasureFactCountTest extends CsvDBTestCase {

    private static final String SCHEMA = ""
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

    private final String QUERY = ""
            + "select [Time].[Quarter].Members on columns, \n"
            + "{[Measures].[Store Sales], [Measures].[Store Cost], [Measures].[Unit Sales]} on rows "
            + "from [Sales]";

    @Override
    protected String getFileName() {
        return "agg_measure_fact_count_test.csv";
    }

    private PropertySaver5 propSaver;

    @BeforeEach
    public void beforeEach() {
        propSaver = new PropertySaver5();
        propSaver.set(propSaver.properties.GenerateFormattedSql, true);
        propSaver.set(propSaver.properties.DisableCaching, true);
        enableAggregates();
    }

    @AfterEach
    public void afterEach() {
        propSaver.reset();
    }

    @Override
    protected String getCubeDescription() {
        return "";
    }

    @Override
    protected void prepareContext(Context context) {
        super.prepareContext(context);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    public void testDefaultRecognition(Context context) {
        prepareContext(context);
        String sqlMysql = ""
                + "select\n"
                + "    `agg_c_6_fact_csv_2016`.`the_year` as `c0`,\n"
                + "    `agg_c_6_fact_csv_2016`.`quarter` as `c1`,\n"
                + "    sum(`agg_c_6_fact_csv_2016`.`unit_sales`) / sum(`agg_c_6_fact_csv_2016`.`unit_sales_fact_count`) as `m0`,\n"
                + "    sum(`agg_c_6_fact_csv_2016`.`store_cost`) / sum(`agg_c_6_fact_csv_2016`.`store_cost_fact_count`) as `m1`,\n"
                + "    sum(`agg_c_6_fact_csv_2016`.`store_sales`) / sum(`agg_c_6_fact_csv_2016`.`store_sales_fact_count`) as `m2`\n"
                + "from\n"
                + "    `agg_c_6_fact_csv_2016` as `agg_c_6_fact_csv_2016`\n"
                + "where\n"
                + "    `agg_c_6_fact_csv_2016`.`the_year` = 1997\n"
                + "group by\n"
                + "    `agg_c_6_fact_csv_2016`.`the_year`,\n"
                + "    `agg_c_6_fact_csv_2016`.`quarter`";

        verifySameAggAndNot(context, QUERY, getAggSchema(null), sqlMysql);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    public void testAggName(Context context) {
    	prepareContext(context);
        String agg = ""
                + "<AggName name=\"agg_c_6_fact_csv_2016\">\n"
                + "    <AggFactCount column=\"fact_count\"/>\n"
                + "    <AggMeasureFactCount column=\"store_sales_fact_count\" factColumn=\"store_sales\" />\n"
                + "    <AggMeasureFactCount column=\"store_cost_fact_count\" factColumn=\"store_cost\" />\n"
                + "    <AggMeasureFactCount column=\"unit_sales_fact_count\" factColumn=\"unit_sales\" />\n"
                + "    <AggMeasure name=\"[Measures].[Unit Sales]\" column=\"UNIT_SALES\" />\n"
                + "    <AggMeasure name=\"[Measures].[Store Cost]\" column=\"STORE_COST\" />\n"
                + "    <AggMeasure name=\"[Measures].[Store Sales]\" column=\"STORE_SALES\" />\n"
                + "    <AggLevel name=\"[Time].[Year]\" column=\"the_year\" />\n"
                + "    <AggLevel name=\"[Time].[Quarter]\" column=\"quarter\" />\n"
                + "    <AggLevel name=\"[Time].[Month]\" column=\"month_of_year\" />\n"
                + "</AggName>\n";

        String aggSql = ""
                + "select\n"
                + "    `agg_c_6_fact_csv_2016`.`the_year` as `c0`,\n"
                + "    `agg_c_6_fact_csv_2016`.`quarter` as `c1`,\n"
                + "    sum(`agg_c_6_fact_csv_2016`.`unit_sales` * `agg_c_6_fact_csv_2016`.`unit_sales_fact_count`) / sum(`agg_c_6_fact_csv_2016`.`unit_sales_fact_count`) as `m0`,\n"
                + "    sum(`agg_c_6_fact_csv_2016`.`store_cost` * `agg_c_6_fact_csv_2016`.`store_cost_fact_count`) / sum(`agg_c_6_fact_csv_2016`.`store_cost_fact_count`) as `m1`,\n"
                + "    sum(`agg_c_6_fact_csv_2016`.`store_sales` * `agg_c_6_fact_csv_2016`.`store_sales_fact_count`) / sum(`agg_c_6_fact_csv_2016`.`store_sales_fact_count`) as `m2`\n"
                + "from\n"
                + "    `agg_c_6_fact_csv_2016` as `agg_c_6_fact_csv_2016`\n"
                + "where\n"
                + "    `agg_c_6_fact_csv_2016`.`the_year` = 1997\n"
                + "group by\n"
                + "    `agg_c_6_fact_csv_2016`.`the_year`,\n"
                + "    `agg_c_6_fact_csv_2016`.`quarter`";

        verifySameAggAndNot(context, QUERY, getAggSchema(agg), aggSql);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    public void testFactColumnNotExists(Context context) {
        prepareContext(context);
        String agg = ""
                + "<AggName name=\"agg_c_6_fact_csv_2016\">\n"
                + "    <AggFactCount column=\"fact_count\"/>\n"
                + "    <AggMeasureFactCount column=\"store_sales_fact_count\" />\n"
                + "    <AggMeasureFactCount column=\"store_cost_fact_count\" />\n"
                + "    <AggMeasureFactCount column=\"unit_sales_fact_count\" />\n"
                + "    <AggMeasure name=\"[Measures].[Unit Sales]\" column=\"UNIT_SALES\" />\n"
                + "    <AggMeasure name=\"[Measures].[Store Cost]\" column=\"STORE_COST\" />\n"
                + "    <AggMeasure name=\"[Measures].[Store Sales]\" column=\"STORE_SALES\" />\n"
                + "    <AggLevel name=\"[Time].[Year]\" column=\"the_year\" />\n"
                + "    <AggLevel name=\"[Time].[Quarter]\" column=\"quarter\" />\n"
                + "    <AggLevel name=\"[Time].[Month]\" column=\"month_of_year\" />\n"
                + "</AggName>\n";

        try {
            verifySameAggAndNot(context, QUERY, getAggSchema(agg));
            fail("Should throw mondrian exception");
        } catch (MondrianException e) {
            assertTrue
                    (e.getMessage().startsWith
                            ("Mondrian Error:Internal"
                                    + " error: while parsing catalog"));
        }
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    public void testMeasureFactColumnUpperCase(Context context) {
        prepareContext(context);
        String agg = ""
                + "<AggName name=\"agg_c_6_fact_csv_2016\">\n"
                + "    <AggFactCount column=\"fact_count\"/>\n"
                + "    <AggMeasureFactCount column=\"store_sales_fact_count\" factColumn=\"STORE_SALES\" />\n"
                + "    <AggMeasureFactCount column=\"store_cost_fact_count\" factColumn=\"StOrE_cosT\" />\n"
                + "    <AggMeasureFactCount column=\"unit_sales_fact_count\" factColumn=\"unit_SALES\" />\n"
                + "    <AggMeasure name=\"[Measures].[Unit Sales]\" column=\"UNIT_SALES\" />\n"
                + "    <AggMeasure name=\"[Measures].[Store Cost]\" column=\"STORE_COST\" />\n"
                + "    <AggMeasure name=\"[Measures].[Store Sales]\" column=\"STORE_SALES\" />\n"
                + "    <AggLevel name=\"[Time].[Year]\" column=\"the_year\" />\n"
                + "    <AggLevel name=\"[Time].[Quarter]\" column=\"quarter\" />\n"
                + "    <AggLevel name=\"[Time].[Month]\" column=\"month_of_year\" />\n"
                + "</AggName>\n";

        // aggregation tables are used, but with general fact count column
        String aggSql = ""
                + "select\n"
                + "    `agg_c_6_fact_csv_2016`.`the_year` as `c0`,\n"
                + "    `agg_c_6_fact_csv_2016`.`quarter` as `c1`,\n"
                + "    sum(`agg_c_6_fact_csv_2016`.`unit_sales` * `agg_c_6_fact_csv_2016`.`fact_count`) / sum(`agg_c_6_fact_csv_2016`.`fact_count`) as `m0`,\n"
                + "    sum(`agg_c_6_fact_csv_2016`.`store_cost` * `agg_c_6_fact_csv_2016`.`fact_count`) / sum(`agg_c_6_fact_csv_2016`.`fact_count`) as `m1`,\n"
                + "    sum(`agg_c_6_fact_csv_2016`.`store_sales` * `agg_c_6_fact_csv_2016`.`fact_count`) / sum(`agg_c_6_fact_csv_2016`.`fact_count`) as `m2`\n"
                + "from\n"
                + "    `agg_c_6_fact_csv_2016` as `agg_c_6_fact_csv_2016`\n"
                + "where\n"
                + "    `agg_c_6_fact_csv_2016`.`the_year` = 1997\n"
                + "group by\n"
                + "    `agg_c_6_fact_csv_2016`.`the_year`,\n"
                + "    `agg_c_6_fact_csv_2016`.`quarter`";

        assertQuerySql(context, QUERY, getAggSchema(agg), aggSql);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    public void testMeasureFactColumnNotExist(Context context) {
        prepareContext(context);
        String agg = ""
                + "<AggName name=\"agg_c_6_fact_csv_2016\">\n"
                + "    <AggFactCount column=\"fact_count\"/>\n"
                + "    <AggMeasureFactCount column=\"store_sales_fact_count\" factColumn=\"not_exist\" />\n"
                + "    <AggMeasureFactCount column=\"store_cost_fact_count\" factColumn=\"not_exist\" />\n"
                + "    <AggMeasureFactCount column=\"unit_sales_fact_count\" factColumn=\"not_exist\" />\n"
                + "    <AggMeasure name=\"[Measures].[Unit Sales]\" column=\"UNIT_SALES\" />\n"
                + "    <AggMeasure name=\"[Measures].[Store Cost]\" column=\"STORE_COST\" />\n"
                + "    <AggMeasure name=\"[Measures].[Store Sales]\" column=\"STORE_SALES\" />\n"
                + "    <AggLevel name=\"[Time].[Year]\" column=\"the_year\" />\n"
                + "    <AggLevel name=\"[Time].[Quarter]\" column=\"quarter\" />\n"
                + "    <AggLevel name=\"[Time].[Month]\" column=\"month_of_year\" />\n"
                + "</AggName>\n";

        // aggregation tables are used, but with general fact count column
        String aggSql = ""
                + "select\n"
                + "    `agg_c_6_fact_csv_2016`.`the_year` as `c0`,\n"
                + "    `agg_c_6_fact_csv_2016`.`quarter` as `c1`,\n"
                + "    sum(`agg_c_6_fact_csv_2016`.`unit_sales` * `agg_c_6_fact_csv_2016`.`fact_count`) / sum(`agg_c_6_fact_csv_2016`.`fact_count`) as `m0`,\n"
                + "    sum(`agg_c_6_fact_csv_2016`.`store_cost` * `agg_c_6_fact_csv_2016`.`fact_count`) / sum(`agg_c_6_fact_csv_2016`.`fact_count`) as `m1`,\n"
                + "    sum(`agg_c_6_fact_csv_2016`.`store_sales` * `agg_c_6_fact_csv_2016`.`fact_count`) / sum(`agg_c_6_fact_csv_2016`.`fact_count`) as `m2`\n"
                + "from\n"
                + "    `agg_c_6_fact_csv_2016` as `agg_c_6_fact_csv_2016`\n"
                + "where\n"
                + "    `agg_c_6_fact_csv_2016`.`the_year` = 1997\n"
                + "group by\n"
                + "    `agg_c_6_fact_csv_2016`.`the_year`,\n"
                + "    `agg_c_6_fact_csv_2016`.`quarter`";

        assertQuerySql(context, QUERY, getAggSchema(agg), aggSql);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    public void testWithoutMeasureFactColumnElement(Context context) {
        prepareContext(context);
        String agg = ""
                + "<AggName name=\"agg_c_6_fact_csv_2016\">\n"
                + "    <AggFactCount column=\"fact_count\"/>\n"
                + "    <AggMeasure name=\"[Measures].[Unit Sales]\" column=\"UNIT_SALES\" />\n"
                + "    <AggMeasure name=\"[Measures].[Store Cost]\" column=\"STORE_COST\" />\n"
                + "    <AggMeasure name=\"[Measures].[Store Sales]\" column=\"STORE_SALES\" />\n"
                + "    <AggLevel name=\"[Time].[Year]\" column=\"the_year\" />\n"
                + "    <AggLevel name=\"[Time].[Quarter]\" column=\"quarter\" />\n"
                + "    <AggLevel name=\"[Time].[Month]\" column=\"month_of_year\" />\n"
                + "</AggName>\n";

        // aggregation tables are used, but with general fact count column
        String aggSql = ""
                + "select\n"
                + "    `agg_c_6_fact_csv_2016`.`the_year` as `c0`,\n"
                + "    `agg_c_6_fact_csv_2016`.`quarter` as `c1`,\n"
                + "    sum(`agg_c_6_fact_csv_2016`.`unit_sales` * `agg_c_6_fact_csv_2016`.`fact_count`) / sum(`agg_c_6_fact_csv_2016`.`fact_count`) as `m0`,\n"
                + "    sum(`agg_c_6_fact_csv_2016`.`store_cost` * `agg_c_6_fact_csv_2016`.`fact_count`) / sum(`agg_c_6_fact_csv_2016`.`fact_count`) as `m1`,\n"
                + "    sum(`agg_c_6_fact_csv_2016`.`store_sales` * `agg_c_6_fact_csv_2016`.`fact_count`) / sum(`agg_c_6_fact_csv_2016`.`fact_count`) as `m2`\n"
                + "from\n"
                + "    `agg_c_6_fact_csv_2016` as `agg_c_6_fact_csv_2016`\n"
                + "where\n"
                + "    `agg_c_6_fact_csv_2016`.`the_year` = 1997\n"
                + "group by\n"
                + "    `agg_c_6_fact_csv_2016`.`the_year`,\n"
                + "    `agg_c_6_fact_csv_2016`.`quarter`";

        assertQuerySql(context, QUERY, getAggSchema(agg), aggSql);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    public void testMeasureFactColumnAndAggFactCountNotExist(Context context) {
        prepareContext(context);
        String agg = ""
                + "<AggName name=\"agg_c_6_fact_csv_2016\">\n"
                + "    <AggFactCount column=\"not_exist\"/>\n"
                + "    <AggMeasureFactCount column=\"store_sales_fact_count\" factColumn=\"not_exist\" />\n"
                + "    <AggMeasureFactCount column=\"store_cost_fact_count\" factColumn=\"not_exist\" />\n"
                + "    <AggMeasureFactCount column=\"unit_sales_fact_count\" factColumn=\"not_exist\" />\n"
                + "    <AggMeasure name=\"[Measures].[Unit Sales]\" column=\"UNIT_SALES\" />\n"
                + "    <AggMeasure name=\"[Measures].[Store Cost]\" column=\"STORE_COST\" />\n"
                + "    <AggMeasure name=\"[Measures].[Store Sales]\" column=\"STORE_SALES\" />\n"
                + "    <AggLevel name=\"[Time].[Year]\" column=\"the_year\" />\n"
                + "    <AggLevel name=\"[Time].[Quarter]\" column=\"quarter\" />\n"
                + "    <AggLevel name=\"[Time].[Month]\" column=\"month_of_year\" />\n"
                + "</AggName>\n";

        try {
            assertQuerySql(context, QUERY, getAggSchema(agg), "");
            fail("Should have thrown mondrian exception");
        } catch (MondrianException e) {
            assertEquals
                    ("Mondrian Error:Too many errors, '1',"
                                    + " while loading/reloading aggregates.",
                            e.getMessage());
        }
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    public void testAggNameDifferentColumnNames(Context context) {
        prepareContext(context);
        String agg = ""
                + "<AggExclude name=\"agg_c_6_fact_csv_2016\" />"
                + "<AggName name=\"agg_csv_different_column_names\">\n"
                + "    <AggFactCount column=\"fact_count\"/>\n"
                + "    <AggMeasureFactCount column=\"ss_fc\" factColumn=\"store_sales\" />\n"
                + "    <AggMeasureFactCount column=\"sc_fc\" factColumn=\"store_cost\" />\n"
                + "    <AggMeasureFactCount column=\"us_fc\" factColumn=\"unit_sales\" />\n"
                + "    <AggMeasure name=\"[Measures].[Unit Sales]\" column=\"UNIT_SALES\" />\n"
                + "    <AggMeasure name=\"[Measures].[Store Cost]\" column=\"STORE_COST\" />\n"
                + "    <AggMeasure name=\"[Measures].[Store Sales]\" column=\"STORE_SALES\" />\n"
                + "    <AggLevel name=\"[Time].[Year]\" column=\"the_year\" />\n"
                + "    <AggLevel name=\"[Time].[Quarter]\" column=\"quarter\" />\n"
                + "    <AggLevel name=\"[Time].[Month]\" column=\"month_of_year\" />\n"
                + "</AggName>\n";

        String aggSql = ""
                + "select\n"
                + "    `agg_csv_different_column_names`.`the_year` as `c0`,\n"
                + "    `agg_csv_different_column_names`.`quarter` as `c1`,\n"
                + "    sum(`agg_csv_different_column_names`.`unit_sales` * `agg_csv_different_column_names`.`us_fc`) / sum(`agg_csv_different_column_names`.`us_fc`) as `m0`,\n"
                + "    sum(`agg_csv_different_column_names`.`store_cost` * `agg_csv_different_column_names`.`sc_fc`) / sum(`agg_csv_different_column_names`.`sc_fc`) as `m1`,\n"
                + "    sum(`agg_csv_different_column_names`.`store_sales` * `agg_csv_different_column_names`.`ss_fc`) / sum(`agg_csv_different_column_names`.`ss_fc`) as `m2`\n"
                + "from\n"
                + "    `agg_csv_different_column_names` as `agg_csv_different_column_names`\n"
                + "where\n"
                + "    `agg_csv_different_column_names`.`the_year` = 1997\n"
                + "group by\n"
                + "    `agg_csv_different_column_names`.`the_year`,\n"
                + "    `agg_csv_different_column_names`.`quarter`";

        verifySameAggAndNot(context, QUERY, getAggSchema(agg), aggSql);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    public void testAggDivideByZero(Context context) {
        prepareContext(context);
        String agg = ""
                + "<AggExclude name=\"agg_c_6_fact_csv_2016\" />"
                + "<AggName name=\"agg_csv_divide_by_zero\">\n"
                + "    <AggFactCount column=\"fact_count\"/>\n"
                + "    <AggMeasureFactCount column=\"store_sales_fact_count\" factColumn=\"store_sales\" />\n"
                + "    <AggMeasureFactCount column=\"store_cost_fact_count\" factColumn=\"store_cost\" />\n"
                + "    <AggMeasureFactCount column=\"unit_sales_fact_count\" factColumn=\"unit_sales\" />\n"
                + "    <AggMeasure name=\"[Measures].[Unit Sales]\" column=\"UNIT_SALES\" />\n"
                + "    <AggMeasure name=\"[Measures].[Store Cost]\" column=\"STORE_COST\" />\n"
                + "    <AggMeasure name=\"[Measures].[Store Sales]\" column=\"STORE_SALES\" />\n"
                + "    <AggLevel name=\"[Time].[Year]\" column=\"the_year\" />\n"
                + "    <AggLevel name=\"[Time].[Quarter]\" column=\"quarter\" />\n"
                + "    <AggLevel name=\"[Time].[Month]\" column=\"month_of_year\" />\n"
                + "</AggName>\n";

        String result = ""
                + "Axis #0:\n"
                + "{}\n"
                + "Axis #1:\n"
                + "{[Time].[1997].[Q1]}\n"
                + "{[Time].[1997].[Q2]}\n"
                + "{[Time].[1997].[Q3]}\n"
                + "{[Time].[1997].[Q4]}\n"
                + "Axis #2:\n"
                + "{[Measures].[Store Sales]}\n"
                + "{[Measures].[Store Cost]}\n"
                + "{[Measures].[Unit Sales]}\n"
                + "Row #0: \n"
                + "Row #0: 1.00\n"
                + "Row #0: 1.00\n"
                + "Row #0: 1.00\n"
                + "Row #1: 2.00\n"
                + "Row #1: 2.00\n"
                + "Row #1: 2.00\n"
                + "Row #1: 2.00\n"
                + "Row #2: 3\n"
                + "Row #2: 3\n"
                + "Row #2: 3\n"
                + "Row #2: 3\n";

        withSchema(context, getAggSchema(agg));
        assertQueryReturns(context.createConnection(), QUERY, result);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    public void testAggPattern(Context context) {
        prepareContext(context);
        String agg = ""
                + "<AggPattern pattern=\"agg_c_6_fact_csv_2016\">\n"
                + "    <AggFactCount column=\"fact_count\"/>\n"
                + "    <AggMeasureFactCount column=\"store_sales_fact_count\" factColumn=\"store_sales\" />\n"
                + "    <AggMeasureFactCount column=\"store_cost_fact_count\" factColumn=\"store_cost\" />\n"
                + "    <AggMeasureFactCount column=\"unit_sales_fact_count\" factColumn=\"unit_sales\" />\n"
                + "    <AggMeasure name=\"[Measures].[Unit Sales]\" column=\"UNIT_SALES\" />\n"
                + "    <AggMeasure name=\"[Measures].[Store Cost]\" column=\"STORE_COST\" />\n"
                + "    <AggMeasure name=\"[Measures].[Store Sales]\" column=\"STORE_SALES\" />\n"
                + "    <AggLevel name=\"[Time].[Year]\" column=\"the_year\" />\n"
                + "    <AggLevel name=\"[Time].[Quarter]\" column=\"quarter\" />\n"
                + "    <AggLevel name=\"[Time].[Month]\" column=\"month_of_year\" />\n"
                + "</AggPattern>\n";

        String aggSql = ""
                + "select\n"
                + "    `agg_c_6_fact_csv_2016`.`the_year` as `c0`,\n"
                + "    `agg_c_6_fact_csv_2016`.`quarter` as `c1`,\n"
                + "    sum(`agg_c_6_fact_csv_2016`.`unit_sales` * `agg_c_6_fact_csv_2016`.`unit_sales_fact_count`) / sum(`agg_c_6_fact_csv_2016`.`unit_sales_fact_count`) as `m0`,\n"
                + "    sum(`agg_c_6_fact_csv_2016`.`store_cost` * `agg_c_6_fact_csv_2016`.`store_cost_fact_count`) / sum(`agg_c_6_fact_csv_2016`.`store_cost_fact_count`) as `m1`,\n"
                + "    sum(`agg_c_6_fact_csv_2016`.`store_sales` * `agg_c_6_fact_csv_2016`.`store_sales_fact_count`) / sum(`agg_c_6_fact_csv_2016`.`store_sales_fact_count`) as `m2`\n"
                + "from\n"
                + "    `agg_c_6_fact_csv_2016` as `agg_c_6_fact_csv_2016`\n"
                + "where\n"
                + "    `agg_c_6_fact_csv_2016`.`the_year` = 1997\n"
                + "group by\n"
                + "    `agg_c_6_fact_csv_2016`.`the_year`,\n"
                + "    `agg_c_6_fact_csv_2016`.`quarter`";

        verifySameAggAndNot(context, QUERY, getAggSchema(agg), aggSql);
    }

    private String getAggSchema(String agg) {
        if (agg == null) {
            agg = "";
        }

        return SCHEMA.replace("%AGG_DESCRIPTION_HERE%", agg);
    }

    private void verifySameAggAndNot(Context context, String query, String schema) {
        withSchema(context, schema);
        Result resultWithAgg =
                executeQuery(query, context.createConnection());
        disableAggregates();
        Result result = executeQuery(query, context.createConnection());

        String resultStr = TestUtil.toString(result);
        String resultWithAggStr = TestUtil.toString(resultWithAgg);
        assertEquals(
        		resultStr,
        		resultWithAggStr,
        		"Results with and without agg table should be equal");
    }

    private void verifySameAggAndNot
            (Context context, String query, String schema, String aggSql) {
        enableAggregates();
        // check that agg tables are used
        assertQuerySql(context, QUERY, schema, aggSql);

        verifySameAggAndNot(context, query, schema);
    }

    private void assertQuerySql
            (Context context, String query, String schema, String sql) {

        withSchema(context, schema);
        //withFreshConnection();
        assertQuerySql
                (context.createConnection(), query, new SqlPattern[]
                        {
                                new SqlPattern
                                        (DatabaseProduct.MYSQL,
                                                sql,
                                                sql.length())
                        });
    }

    private void enableAggregates() {
        propSaver.set(propSaver.properties.UseAggregates, true);
        propSaver.set(propSaver.properties.ReadAggregates, true);
    }

    private void disableAggregates() {
        propSaver.set(propSaver.properties.UseAggregates, false);
        propSaver.set(propSaver.properties.ReadAggregates, false);
    }
}
// End AggMeasureFactCountTest.java
