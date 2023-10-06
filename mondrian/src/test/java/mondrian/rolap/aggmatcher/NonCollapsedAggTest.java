/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/
package mondrian.rolap.aggmatcher;

import static org.opencube.junit5.TestUtil.assertQueryReturns;
import static org.opencube.junit5.TestUtil.withSchema;

import org.junit.jupiter.params.ParameterizedTest;
import org.opencube.junit5.ContextSource;
import org.opencube.junit5.SchemaUtil;
import org.opencube.junit5.TestUtil;
import org.opencube.junit5.context.BaseTestContext;
import org.opencube.junit5.context.TestContextWrapper;
import org.opencube.junit5.dataloader.FastFoodmardDataLoader;
import org.opencube.junit5.propupdator.AppandFoodMartCatalogAsFile;
import org.opencube.junit5.propupdator.SchemaUpdater;

import mondrian.olap.MondrianProperties;

/**
 * Testcase for non-collapsed levels in agg tables.
 *
 * @author Luc Boudreau
 */
class NonCollapsedAggTest extends AggTableTestCase {

    private static final String CUBE_1 =
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
    private static final String SSAS_COMPAT_CUBE = "<Cube name=\"testSsas\">\n"
            + "    <Table name=\"foo_fact\">\n"
            + "        <AggName name=\"agg_tenant\">\n"
            + "            <AggFactCount column=\"fact_count\"/>\n"
            + "            <AggMeasure name=\"[Measures].[Unit Sales]\" column=\"unit_sales\"/>\n"
            + "            <AggLevel name=\"[dimension].[tenant].[tenant]\"\n"
            + "                column=\"tenant_id\" collapsed=\"false\"/>\n"
            + "        </AggName>\n"
            + "        <AggName name=\"agg_line_class\">\n"
            + "            <AggFactCount column=\"fact_count\"/>\n"
            + "            <AggMeasure name=\"[Measures].[Unit Sales]\" column=\"unit_sales\"/>\n"
            + "            <AggLevel name=\"[dimension].[distributor].[line class]\"\n"
            + "                column=\"line_class_id\" collapsed=\"false\"/>\n"
            + "        </AggName>\n"
            + "        <AggName name=\"agg_line_class\">\n"
            + "            <AggFactCount column=\"fact_count\"/>\n"
            + "            <AggMeasure name=\"[Measures].[Unit Sales]\" column=\"unit_sales\"/>\n"
            + "            <AggLevel name=\"[dimension].[network].[line class]\"\n"
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
            + "</Cube>\n";

    @Override
	protected String getFileName() {
        return "non_collapsed_agg_test.csv";
    }

    @Override
    protected String getCubeDescription() {
        return CUBE_1;
    }


    @Override
	protected void prepareContext(TestContextWrapper context) {
        try {
            super.prepareContext(context);
            String baseSchema = TestUtil.getRawSchema(context);
            String schema = SchemaUtil.getSchema(baseSchema,
                    null, getCubeDescription(), null, null, null, null);
            TestUtil.withSchema(context, schema);
        }
        catch (Exception e) {
            throw  new RuntimeException("Prepare context for csv tests failed");
        }
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testSingleJoin(TestContextWrapper context) throws Exception {
    	super.prepareContext(context);
        if (!isApplicable(context.createConnection())) {
            return;
        }

        final String mdx =
            "select {[Measures].[Unit Sales]} on columns, {[dimension.tenant].[tenant].Members} on rows from [foo]";



        // We expect the correct cell value + 1 if the agg table is used.
        assertQueryReturns(context.createConnection(),
            mdx,
            "Axis #0:\n"
            + "{}\n"
            + "Axis #1:\n"
            + "{[Measures].[Unit Sales]}\n"
            + "Axis #2:\n"
            + "{[dimension.tenant].[tenant one]}\n"
            + "{[dimension.tenant].[tenant two]}\n"
            + "Row #0: 31\n"
            + "Row #1: 121\n");
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testComplexJoin(TestContextWrapper context) throws Exception {
        prepareContext(context);
        if (!isApplicable(context.createConnection())) {
            return;
        }

        final String mdx =
            "select {[Measures].[Unit Sales]} on columns, {[dimension.distributor].[line class].Members} on rows from [foo]";



        // We expect the correct cell value + 1 if the agg table is used.
        assertQueryReturns(context.createConnection(),
            mdx,
            "Axis #0:\n"
            + "{}\n"
            + "Axis #1:\n"
            + "{[Measures].[Unit Sales]}\n"
            + "Axis #2:\n"
            + "{[dimension.distributor].[distributor one].[line class one]}\n"
            + "{[dimension.distributor].[distributor two].[line class two]}\n"
            + "Row #0: 31\n"
            + "Row #1: 121\n");

        final String mdx2 =
            "select {[Measures].[Unit Sales]} on columns, {[dimension.network].[line class].Members} on rows from [foo]";
        // We expect the correct cell value + 1 if the agg table is used.
        assertQueryReturns(context.createConnection(),
            mdx2,
            "Axis #0:\n"
            + "{}\n"
            + "Axis #1:\n"
            + "{[Measures].[Unit Sales]}\n"
            + "Axis #2:\n"
            + "{[dimension.network].[network one].[line class one]}\n"
            + "{[dimension.network].[network two].[line class two]}\n"
            + "Row #0: 31\n"
            + "Row #1: 121\n");
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testComplexJoinDefaultRecognizer(TestContextWrapper context) throws Exception {
        prepareContext(context);
        if (!isApplicable(context.createConnection())) {
            return;
        }

        // We expect the correct cell value + 2 if the agg table is used.
        final String mdx =
            "select {[Measures].[Unit Sales]} on columns, {[dimension.distributor].[line class].Members} on rows from [foo2]";
        assertQueryReturns(context.createConnection(),
            mdx,
            "Axis #0:\n"
            + "{}\n"
            + "Axis #1:\n"
            + "{[Measures].[Unit Sales]}\n"
            + "Axis #2:\n"
            + "{[dimension.distributor].[distributor one].[line class one]}\n"
            + "{[dimension.distributor].[distributor two].[line class two]}\n"
            + "Row #0: 32\n"
            + "Row #1: 122\n");

        final String mdx2 =
            "select {[Measures].[Unit Sales]} on columns, {[dimension.network].[line class].Members} on rows from [foo2]";
        // We expect the correct cell value + 2 if the agg table is used.
        assertQueryReturns(context.createConnection(),
            mdx2,
            "Axis #0:\n"
            + "{}\n"
            + "Axis #1:\n"
            + "{[Measures].[Unit Sales]}\n"
            + "Axis #2:\n"
            + "{[dimension.network].[network one].[line class one]}\n"
            + "{[dimension.network].[network two].[line class two]}\n"
            + "Row #0: 32\n"
            + "Row #1: 122\n");
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testSsasCompatNamingInAgg(TestContextWrapper context) throws Exception {
        prepareContext(context);
        // MONDRIAN-1085
        if (!MondrianProperties.instance().SsasCompatibleNaming.get()) {
            return;
        }

        String baseSchema = TestUtil.getRawSchema(context);
        String schema = SchemaUtil.getSchema(baseSchema,
            null, SSAS_COMPAT_CUBE, null, null, null, null);
        withSchema(context, schema);
        final String mdx =
                "select {[Measures].[Unit Sales]} on columns, {[dimension].[tenant].[tenant].Members} on rows from [testSsas]";
        assertQueryReturns(context.createConnection(),
            mdx,
            "Axis #0:\n"
            + "{}\n"
            + "Axis #1:\n"
            + "{[Measures].[Unit Sales]}\n"
            + "Axis #2:\n"
            + "{[dimension].[tenant].[tenant one]}\n"
            + "{[dimension].[tenant].[tenant two]}\n"
            + "Row #0: 31\n"
            + "Row #1: 121\n");
    }

    /**
     * Test case for cast exception on min/max of an integer measure
     */
    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testMondrian1325(TestContextWrapper context) {
        prepareContext(context);
        final String query1 =
            "SELECT\n"
            + "{ Measures.[Bogus Number]} on 0,\n"
            + "non empty Descendants([Time].[Year].Members, Time.Month, SELF) on 1\n"
            + "FROM [Sales]\n";

        final String query2 =
            "SELECT\n"
            + "{ Measures.[Bogus Number]} on 0,\n"
            + "non empty Descendants([Time].[Year].Members, Time.Month, SELF_AND_BEFORE) on 1\n"
            + "FROM [Sales]";

        ((BaseTestContext)context).update(SchemaUpdater.createSubstitutingCube(
                "Sales",
                null,
                "<Measure name=\"Bogus Number\" column=\"promotion_id\" datatype=\"Numeric\" aggregator=\"max\" visible=\"true\"/>",
                null,
                null));

        executeQuery(query1, context.createConnection());
        executeQuery(query2, context.createConnection());
    }

}
