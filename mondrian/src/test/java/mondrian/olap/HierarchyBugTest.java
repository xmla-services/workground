/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2003-2005 Julian Hyde
// Copyright (C) 2005-2017 Hitachi Vantara
// All Rights Reserved.
//
// remberson, Jan 31, 2006
*/
package mondrian.olap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;
import java.util.List;

import org.eclipse.daanse.olap.api.Connection;
import org.eclipse.daanse.olap.api.model.Hierarchy;
import org.eclipse.daanse.olap.api.model.Level;
import org.eclipse.daanse.olap.api.model.Member;
import org.eclipse.daanse.olap.api.model.OlapElement;
import org.eclipse.daanse.olap.api.result.Axis;
import org.eclipse.daanse.olap.api.result.Result;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.olap4j.CellSet;
import org.olap4j.OlapConnection;
import org.opencube.junit5.ContextSource;
import org.opencube.junit5.TestUtil;
import org.opencube.junit5.context.BaseTestContext;
import org.opencube.junit5.context.TestingContext;
import org.opencube.junit5.dataloader.FastFoodmardDataLoader;
import org.opencube.junit5.propupdator.AppandFoodMartCatalogAsFile;
import org.opencube.junit5.propupdator.SchemaUpdater;

import mondrian.rolap.RolapConnectionProperties;
import mondrian.test.PropertySaver5;
public class HierarchyBugTest {
	private PropertySaver5 propSaver;

	@BeforeEach
	public void beforeEach() {
		propSaver = new PropertySaver5();
	}

	@AfterEach
	public void afterEach() {
		propSaver.reset();
	}

    /**
     * This is code that demonstrates a bug that appears when using
     * JPivot with the current version of Mondrian. With the previous
     * version of Mondrian (and JPivot), pre compilation Mondrian,
     * this was not a bug (or at least Mondrian did not have a null
     * hierarchy).
     * Here the Time dimension is not returned in axis == 0, rather
     * null is returned. This causes a NullPointer exception in JPivot
     * when it tries to access the (null) hierarchy's name.
     * If the Time hierarchy is miss named in the query string, then
     * the parse ought to pick it up.
     **/
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testNoHierarchy(TestingContext foodMartContext) {
        String queryString =
            "select NON EMPTY "
            + "Crossjoin(Hierarchize(Union({[Time].[Time].LastSibling}, "
            + "[Time].[Time].LastSibling.Children)), "
            + "{[Measures].[Unit Sales],      "
            + "[Measures].[Store Cost]}) ON columns, "
            + "NON EMPTY Hierarchize(Union({[Store].[All Stores]}, "
            + "[Store].[All Stores].Children)) ON rows "
            + "from [Sales]";

        Connection conn = foodMartContext.createConnection();
        Query query = conn.parseQuery(queryString);

        String failStr = null;
        int len = query.getAxes().length;
        for (int i = 0; i < len; i++) {
            Hierarchy[] hs =
                query.getMdxHierarchiesOnAxis(
                    AxisOrdinal.StandardAxisOrdinal.forLogicalOrdinal(i));
            if (hs == null) {
            } else {
                for (Hierarchy h : hs) {
                    // This should NEVER be null, but it is.
                    if (h == null) {
                        failStr =
                            "Got a null Hierarchy, "
                            + "Should be Time Hierarchy";
                    }
                }
            }
        }
        if (failStr != null) {
            fail(failStr);
        }
    }

    /**
     * Test cases for <a href="http://jira.pentaho.com/browse/MONDRIAN-1126">
     * MONDRIAN-1126:
     * member getHierarchy vs. level.getHierarchy differences in Time Dimension
     * </a>
     */
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
	void testNamesIdentitySsasCompatibleTimeHierarchy(TestingContext foodMartContext) {
        propSaver.set(
            MondrianProperties.instance().SsasCompatibleNaming, true);
        String mdxTime = "SELECT\n"
            + "   [Measures].[Unit Sales] ON COLUMNS,\n"
            + "   [Time].[Time].[Year].Members ON ROWS\n"
            + "FROM [Sales]";
       Connection conn= foodMartContext.createConnection();
       
        
        Result resultTime = TestUtil.executeQuery(conn, mdxTime);
        verifyMemberLevelNamesIdentityMeasureAxis(
            resultTime.getAxes()[0], "[Measures]");
        verifyMemberLevelNamesIdentityDimAxis(
            resultTime.getAxes()[1], "[Time].[Time]");
        
TestUtil.flushSchemaCache(conn);
    }
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testNamesIdentitySsasCompatibleWeeklyHierarchy(TestingContext foodMartContext) {
        propSaver.set(
            MondrianProperties.instance().SsasCompatibleNaming, true);
        String mdxWeekly = "SELECT\n"
            + "   [Measures].[Unit Sales] ON COLUMNS,\n"
            + "   [Time].[Weekly].[Year].Members ON ROWS\n"
            + "FROM [Sales]";
        
       // Fresh sets this before get new Conn
       //  RolapConnectionProperties.UseSchemaPool.name(), false);
        foodMartContext.setProperty(RolapConnectionProperties.UseSchemaPool.name(), Boolean.toString(false));
        Connection conn=foodMartContext.createConnection();
        Result resultWeekly =TestUtil.executeQuery(conn, mdxWeekly);
        verifyMemberLevelNamesIdentityMeasureAxis(
            resultWeekly.getAxes()[0], "[Measures]");
        verifyMemberLevelNamesIdentityDimAxis(
            resultWeekly.getAxes()[1], "[Time].[Weekly]");
        TestUtil.flushSchemaCache(conn);
    }
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testNamesIdentitySsasInCompatibleTimeHierarchy(TestingContext foodMartContext) {
        // SsasCompatibleNaming defaults to false
        String mdxTime = "SELECT\n"
            + "   [Measures].[Unit Sales] ON COLUMNS,\n"
            + "   [Time].[Year].Members ON ROWS\n"
            + "FROM [Sales]";
        
        Connection conn=foodMartContext.createConnection();
        Result resultTime =TestUtil.executeQuery(conn, mdxTime);
        verifyMemberLevelNamesIdentityMeasureAxis(
            resultTime.getAxes()[0], "[Measures]");
        verifyMemberLevelNamesIdentityDimAxis(
            resultTime.getAxes()[1], "[Time]");
        TestUtil.flushSchemaCache(conn);
    }
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testNamesIdentitySsasInCompatibleWeeklyHierarchy(TestingContext foodMartContext) {
        String mdxWeekly = "SELECT\n"
            + "   [Measures].[Unit Sales] ON COLUMNS,\n"
            + "   [Time.Weekly].[Year].Members ON ROWS\n"
            + "FROM [Sales]";
        
        Connection conn=foodMartContext.createConnection();
        Result resultWeekly =TestUtil.executeQuery(conn, mdxWeekly);

        verifyMemberLevelNamesIdentityMeasureAxis(
            resultWeekly.getAxes()[0], "[Measures]");
        verifyMemberLevelNamesIdentityDimAxis(
            resultWeekly.getAxes()[1], "[Time.Weekly]");
        TestUtil.flushSchemaCache(conn);
    }

    private String verifyMemberLevelNamesIdentityMeasureAxis(
        Axis axis, String expected)
    {
        OlapElement unitSales =
            axis.getPositions().get(0).get(0);
        String unitSalesHierarchyName =
            unitSales.getHierarchy().getUniqueName();
        assertEquals(expected, unitSalesHierarchyName);
        return unitSalesHierarchyName;
    }

    private void verifyMemberLevelNamesIdentityDimAxis(
        Axis axis, String expected)
    {
        Member year1997 = axis.getPositions().get(0).get(0);
        String year1997HierarchyName = year1997.getHierarchy().getUniqueName();
        assertEquals(expected, year1997HierarchyName);
        Level year = year1997.getLevel();
        String yearHierarchyName = year.getHierarchy().getUniqueName();
        assertEquals(year1997HierarchyName, yearHierarchyName);
    }
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testNamesIdentitySsasCompatibleOlap4j(TestingContext foodMartContext) throws SQLException {
        propSaver.set(
            MondrianProperties.instance().SsasCompatibleNaming, true);
        verifyLevelMemberNamesIdentityOlap4jTimeHierarchy(foodMartContext, "[Time].[Time]");
    }
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testNamesIdentitySsasInCompatibleOlap4j(TestingContext foodMartContext) throws SQLException {
        // SsasCompatibleNaming defaults to false
        verifyLevelMemberNamesIdentityOlap4jTimeHierarchy(foodMartContext, "[Time]");
    }

    private void verifyLevelMemberNamesIdentityOlap4jTimeHierarchy(TestingContext foodMartContext, String expected)
        throws SQLException
    {
        // essential here, in time hierarchy, is hasAll="false"
        // so that we expect "[Time]"
        String mdx = "SELECT\n"
            + "   [Measures].[Unit Sales] ON COLUMNS,\n"
            + "   [Time].[Time].[Year].Members ON ROWS\n"
            + "FROM [Sales]";
        verifyLevelMemberNamesIdentityOlap4j(mdx, foodMartContext, expected);
    }
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testNamesIdentitySsasCompatibleOlap4jWeekly(TestingContext foodMartContext)
        throws SQLException
    {
        propSaver.set(
            MondrianProperties.instance().SsasCompatibleNaming, true);
        String mdx = "SELECT\n"
            + "   [Measures].[Unit Sales] ON COLUMNS,\n"
            + "   [Time].[Weekly].[Year].Members ON ROWS\n"
            + "FROM [Sales]";
        verifyLevelMemberNamesIdentityOlap4j(
            mdx, foodMartContext, "[Time].[Weekly]");
    }
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testNamesIdentitySsasInCompatibleOlap4jWeekly(TestingContext foodMartContext)
        throws SQLException
    {
        // SsasCompatibleNaming defaults to false
        String mdx = "SELECT\n"
            + "   [Measures].[Unit Sales] ON COLUMNS,\n"
            + "   [Time.Weekly].[Year].Members ON ROWS\n"
            + "FROM [Sales]";
        verifyLevelMemberNamesIdentityOlap4j(
            mdx, foodMartContext, "[Time.Weekly]");
    }
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testNamesIdentitySsasCompatibleOlap4jDateDim(TestingContext foodMartContext)
        throws SQLException
    {
        propSaver.set(
            MondrianProperties.instance().SsasCompatibleNaming, true);
        verifyMemberLevelNamesIdentityOlap4jDateDim(foodMartContext, "[Date].[Date]");
    }
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testNamesSsasInCompatibleOlap4jDateDim(TestingContext foodMartContext)
        throws SQLException
    {
        // SsasCompatibleNaming defaults to false
        verifyMemberLevelNamesIdentityOlap4jDateDim(foodMartContext, "[Date]");
    }

    private void verifyMemberLevelNamesIdentityOlap4jDateDim(TestingContext context, String expected)
        throws SQLException
    {
        String mdx =
            "SELECT\n"
            + "   [Measures].[Unit Sales] ON COLUMNS,\n"
            + "   [Date].[Date].[Year].Members ON ROWS\n"
            + "FROM [Sales]";

        String dateDim  =
            "<Dimension name=\"Date\" type=\"TimeDimension\" foreignKey=\"time_id\">\n"
            + "    <Hierarchy hasAll=\"false\" primaryKey=\"time_id\">\n"
            + "      <Table name=\"time_by_day\"/>\n"
            + "      <Level name=\"Year\" column=\"the_year\" type=\"Numeric\" uniqueMembers=\"true\"\n"
            + "          levelType=\"TimeYears\"/>\n"
            + "      <Level name=\"Quarter\" column=\"quarter\" uniqueMembers=\"false\"\n"
            + "          levelType=\"TimeQuarters\"/>\n"
            + "      <Level name=\"Month\" column=\"month_of_year\" uniqueMembers=\"false\" type=\"Numeric\"\n"
            + "          levelType=\"TimeMonths\"/>\n"
            + "    </Hierarchy>\n"
            + "  </Dimension>";
    

       ((BaseTestContext)context).update(SchemaUpdater.createSubstitutingCube("Sales", dateDim));
        
        verifyLevelMemberNamesIdentityOlap4j(mdx, context, expected);
    }
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testNamesIdentitySsasCompatibleOlap4jDateWeekly(TestingContext context)
        throws SQLException
    {
        propSaver.set(
            MondrianProperties.instance().SsasCompatibleNaming, true);
        String mdx = "SELECT\n"
            + "   [Measures].[Unit Sales] ON COLUMNS,\n"
            + "   [Date].[Weekly].[Year].Members ON ROWS\n"
            + "FROM [Sales]";
        verifyMemberLevelNamesIdentityOlap4jWeekly(context,mdx,"[Date].[Weekly]");
    }
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testNamesIdentitySsasInCompatibleOlap4jDateDim(TestingContext context)
        throws SQLException
    {
        // SsasCompatibleNaming defaults to false
        String mdx = "SELECT\n"
            + "   [Measures].[Unit Sales] ON COLUMNS,\n"
            + "   [Date.Weekly].[Year].Members ON ROWS\n"
            + "FROM [Sales]";
        verifyMemberLevelNamesIdentityOlap4jWeekly(context,mdx, "[Date.Weekly]");
    }

    private void verifyMemberLevelNamesIdentityOlap4jWeekly(TestingContext context,
        String mdx, String expected) throws SQLException
    {
   
        String dateDim =
            "<Dimension name=\"Date\" type=\"TimeDimension\" foreignKey=\"time_id\">\n"
            + "    <Hierarchy hasAll=\"true\" name=\"Weekly\" primaryKey=\"time_id\">\n"
            + "      <Table name=\"time_by_day\"/>\n"
            + "      <Level name=\"Year\" column=\"the_year\" type=\"Numeric\" uniqueMembers=\"true\"\n"
            + "          levelType=\"TimeYears\"/>\n"
            + "      <Level name=\"Week\" column=\"week_of_year\" type=\"Numeric\" uniqueMembers=\"false\"\n"
            + "          levelType=\"TimeWeeks\"/>\n"
            + "      <Level name=\"Day\" column=\"day_of_month\" uniqueMembers=\"false\" type=\"Numeric\"\n"
            + "          levelType=\"TimeDays\"/>\n"
            + "    </Hierarchy>\n"
            + "  </Dimension>";
        

        ((BaseTestContext)context).update(SchemaUpdater.createSubstitutingCube("Sales", dateDim));
        
        verifyLevelMemberNamesIdentityOlap4j(mdx, context, expected);
    }

    private void verifyLevelMemberNamesIdentityOlap4j(
        String mdx, TestingContext context, String expected) throws SQLException
    {
    OlapConnection olapConnection=	context.createOlap4jConnection();
    
        CellSet result = TestUtil.executeOlap4jQuery(olapConnection,mdx);

        List<org.olap4j.Position> positions =
            result.getAxes().get(1).getPositions();
        org.olap4j.metadata.Member year1997 =
            positions.get(0).getMembers().get(0);
        String year1997HierarchyName = year1997.getHierarchy().getUniqueName();
        assertEquals(expected, year1997HierarchyName);

        org.olap4j.metadata.Level year = year1997.getLevel();
        String yearHierarchyName = year.getHierarchy().getUniqueName();
        assertEquals(year1997HierarchyName, yearHierarchyName);
        
        TestUtil.flushSchemaCache(context.createConnection());
    }


}
