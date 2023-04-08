/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package mondrian.rolap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.opencube.junit5.TestUtil.assertQueryReturns;

import org.eclipse.daanse.olap.api.result.Result;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.opencube.junit5.ContextSource;
import org.opencube.junit5.TestUtil;
import org.opencube.junit5.context.BaseTestContext;
import org.opencube.junit5.context.TestingContext;
import org.opencube.junit5.dataloader.FastFoodmardDataLoader;
import org.opencube.junit5.propupdator.AppandFoodMartCatalogAsFile;
import org.opencube.junit5.propupdator.SchemaUpdater;

import mondrian.rolap.aggmatcher.AggTableTestCase;

/**
 * Testcase for
 *
 *
 * @author <a>Richard M. Emberson</a>
 * @since Feb 21 2007
 */
public class RolapResultTest extends AggTableTestCase {

    private static final String RolapResultTest = "RolapResultTest.csv";

    private static final String RESULTS_ALL =
        "Axis #0:\n"
        + "{}\n"
        + "Axis #1:\n"
        + "{[D1].[a]}\n"
        + "{[D1].[b]}\n"
        + "{[D1].[c]}\n"
        + "Axis #2:\n"
        + "{[D2].[x]}\n"
        + "{[D2].[y]}\n"
        + "{[D2].[z]}\n"
        + "Row #0: 5\n"
        + "Row #0: \n"
        + "Row #0: \n"
        + "Row #1: \n"
        + "Row #1: 10\n"
        + "Row #1: \n"
        + "Row #2: \n"
        + "Row #2: \n"
        + "Row #2: 15\n";

    private static final String RESULTS =
        "Axis #0:\n"
        + "{}\n"
        + "Axis #1:\n"
        + "{[D1].[a]}\n"
        + "{[D1].[b]}\n"
        + "{[D1].[c]}\n"
        + "Axis #2:\n"
        + "{[D2].[x]}\n"
        + "{[D2].[y]}\n"
        + "{[D2].[z]}\n"
        + "Row #0: 5\n"
        + "Row #0: \n"
        + "Row #0: \n"
        + "Row #1: \n"
        + "Row #1: 10\n"
        + "Row #1: \n"
        + "Row #2: \n"
        + "Row #2: \n"
        + "Row #2: 15\n";



    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testAll(TestingContext context) throws Exception {
        prepareContext(context);
        if (!isApplicable(context.createConnection())) {
            return;
        }

        String mdx =
            "select "
            + " filter({[D1].[a],[D1].[b],[D1].[c]}, "
            + "    [Measures].[Value] > 0) "
            + " ON COLUMNS, "
            + " {[D2].[x],[D2].[y],[D2].[z]} "
            + " ON ROWS "
            + "from FTAll";

        assertQueryReturns(context.createConnection(), mdx, RESULTS_ALL);
/*
        Result result = getCubeTestContext().executeQuery(mdx);
        String resultString = TestContext.toString(result);
//System.out.println(resultString);

        assertTrue(resultString.equals(RESULTS_ALL));
*/
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testD1(TestingContext context) throws Exception {
        prepareContext(context);
        if (!isApplicable(context.createConnection())) {
            return;
        }
        String mdx =
            "select "
            + " filter({[D1].[a],[D1].[b],[D1].[c]}, "
            + "    [Measures].[Value] > 0) "
            + " ON COLUMNS, "
            + " {[D2].[x],[D2].[y],[D2].[z]} "
            + " ON ROWS "
            + "from FT1";

        //getCubeTestContext().assertQueryReturns(mdx, RESULTS);
        Result result = executeQuery(mdx, context.createConnection());
        String resultString = TestUtil.toString(result);
//System.out.println(resultString);
/*
 This is what is produced
Axis #0:
{}
Axis #1:
Axis #2:
{[D2].[x]}
{[D2].[y]}
{[D2].[z]}
*/
        assertEquals(resultString, RESULTS);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testD2(TestingContext context) throws Exception {
        prepareContext(context);
        if (!isApplicable(context.createConnection())) {
            return;
        }
        String mdx =
            "select "
            + " NON EMPTY filter({[D1].[a],[D1].[b],[D1].[c]}, "
            + "    [Measures].[Value] > 0) "
            + " ON COLUMNS, "
            + " {[D2].[x],[D2].[y],[D2].[z]} "
            + " ON ROWS "
            + "from FT2";

        assertQueryReturns(context.createConnection(), mdx, RESULTS);
/*
        Result result = getCubeTestContext().executeQuery(mdx);
        String resultString = TestContext.toString(result);
//System.out.println(resultString);
        assertTrue(resultString.equals(RESULTS));
*/
    }

    /**
     * This ought to give the same result as the above testD2() method.
     * In this case, the FT2Extra cube has a default measure with no
     * data (null) for all members. This default measure is used
     * in the evaluation even though there is an implicit use of the
     * measure [Measures].[Value].
     *
     * @throws Exception
     */
    @Disabled //disabled for CI build
    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    public void _testNullDefaultMeasure(TestingContext context) throws Exception {
        prepareContext(context);
        if (!isApplicable(context.createConnection())) {
            return;
        }

        String mdx =
            "select "
            + " NON EMPTY filter({[D1].[a],[D1].[b],[D1].[c]}, "
            + "    [Measures].[Value] > 0) "
            + " ON COLUMNS, "
            + " {[D2].[x],[D2].[y],[D2].[z]} "
            + " ON ROWS "
            + "from FT2Extra";

        //getCubeTestContext().assertQueryReturns(mdx, RESULTS);
        Result result = executeQuery(mdx, context.createConnection());
        String resultString = TestUtil.toString(result);
        assertTrue(resultString.equals(RESULTS));
    }




    @Override
	protected String getFileName() {
        return RolapResultTest;
    }

    @Override
	protected String getCubeDescription() {
        return
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
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testNonAllPromotionMembers(TestingContext context) {
        prepareContext(context);
        ((BaseTestContext)context).update(SchemaUpdater.createSubstitutingCube(
            "Sales",
            "<Dimension name=\"Promotions2\" foreignKey=\"promotion_id\">\n"
            + "  <Hierarchy hasAll=\"false\" primaryKey=\"promotion_id\">\n"
            + "    <Table name=\"promotion\"/>\n"
            + "    <Level name=\"Promotion2 Name\" column=\"promotion_name\" uniqueMembers=\"true\"/>\n"
            + "  </Hierarchy>\n"
            + "</Dimension>"));

        assertQueryReturns(context.createConnection(),
            "select {[Promotion2 Name].[Price Winners], [Promotion2 Name].[Sale Winners]} * {Tail([Time].[Year].Members,3)} ON COLUMNS, "
            + "NON EMPTY Crossjoin({[Store].CurrentMember.Children},  {[Store Type].[All Store Types].Children}) ON ROWS "
            + "from [Sales]",
            "Axis #0:\n"
            + "{}\n"
            + "Axis #1:\n"
            + "{[Promotions2].[Price Winners], [Time].[1997]}\n"
            + "{[Promotions2].[Price Winners], [Time].[1998]}\n"
            + "{[Promotions2].[Sale Winners], [Time].[1997]}\n"
            + "{[Promotions2].[Sale Winners], [Time].[1998]}\n"
            + "Axis #2:\n"
            + "{[Store].[USA], [Store Type].[Mid-Size Grocery]}\n"
            + "{[Store].[USA], [Store Type].[Small Grocery]}\n"
            + "{[Store].[USA], [Store Type].[Supermarket]}\n"
            + "Row #0: \n"
            + "Row #0: \n"
            + "Row #0: 444\n"
            + "Row #0: \n"
            + "Row #1: 23\n"
            + "Row #1: \n"
            + "Row #1: \n"
            + "Row #1: \n"
            + "Row #2: 1,271\n"
            + "Row #2: \n"
            + "Row #2: \n"
            + "Row #2: \n");
    }
}
