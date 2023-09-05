/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2004-2005 Julian Hyde
// Copyright (C) 2005-2017 Hitachi Vantara and others
// All Rights Reserved.
*/

package mondrian.olap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.opencube.junit5.TestUtil.assertEqualsVerbose;
import static org.opencube.junit5.TestUtil.assertQueryReturns;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.List;

import mondrian.olap.api.CalculatedFormula;
import mondrian.olap.api.DmvQuery;
import mondrian.olap.api.DrillThrough;
import mondrian.olap.api.Explain;
import mondrian.olap.api.Formula;
import mondrian.olap.api.Id;
import mondrian.olap.api.Query;
import mondrian.olap.api.QueryPart;
import mondrian.olap.api.Refresh;
import mondrian.olap.api.Subcube;
import mondrian.olap.api.TransactionCommand;
import mondrian.olap.api.Update;
import org.eclipse.daanse.olap.api.Connection;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import mondrian.olap.api.QueryAxis;
import org.opencube.junit5.ContextSource;
import org.opencube.junit5.context.TestingContext;
import org.opencube.junit5.dataloader.FastFoodmardDataLoader;
import org.opencube.junit5.propupdator.AppandFoodMartCatalogAsFile;

import mondrian.mdx.QueryPrintWriter;
import mondrian.mdx.UnresolvedFunCallImpl;
import mondrian.olap.fun.BuiltinFunTable;
import mondrian.parser.JavaccParserValidatorImpl;
import mondrian.parser.MdxParserValidator;
import mondrian.server.Statement;
import mondrian.util.Bug;

/**
 * Tests the MDX parser.
 *
 * @author gjohnson
 */
class ParserTest {


    static final BuiltinFunTable funTable = BuiltinFunTable.instance();

    protected TestParser createParser() {
        return new TestParser();
    }

    @Test
    void testAxisParsing() throws Exception {
        checkAxisAllWays(0, "COLUMNS");
        checkAxisAllWays(1, "ROWS");
        checkAxisAllWays(2, "PAGES");
        checkAxisAllWays(3, "CHAPTERS");
        checkAxisAllWays(4, "SECTIONS");
    }

    private void checkAxisAllWays(int axisOrdinal, String axisName) {
        checkAxis(axisOrdinal + "", axisName);
        checkAxis("AXIS(" + axisOrdinal + ")", axisName);
        checkAxis(axisName, axisName);
    }

    private void checkAxis(
        String s,
        String expectedName)
    {
        TestParser p = createParser();
        String q = "select [member] on " + s + " from [cube]";
        QueryPart query = p.parseInternal(null, q, false, funTable, false);
        assertNull(query, "Test parser should return null query");

        QueryAxis[] axes = p.getAxes();

        assertEquals(1, axes.length, "Number of axes must be 1");
        assertEquals(
            expectedName,
            axes[0].getAxisName(), "Axis index name must be correct");
    }

    @Test
    void testNegativeCases() throws Exception {
        assertParseQueryFails(
            "select [member] on axis(1.7) from sales",
            "Invalid axis specification. "
            + "The axis number must be a non-negative integer, but it was 1.7.");
        assertParseQueryFails(
            "select [member] on axis(-1) from sales",
            "Syntax error at line");
        // used to be an error, no longer
        assertParseQuery(
            "select [member] on axis(5) from sales",
            "select [member] ON AXIS(5)\n"
            + "from [sales]\n");
        assertParseQueryFails(
            "select [member] on axes(0) from sales", "Syntax error at line");
        assertParseQueryFails(
            "select [member] on 0.5 from sales",
            "Invalid axis specification. "
            + "The axis number must be a non-negative integer, but it was 0.5.");
        assertParseQuery(
            "select [member] on 555 from sales",
            "select [member] ON AXIS(555)\n" + "from [sales]\n");
    }

    /**
     * Test case for bug <a href="http://jira.pentaho.com/browse/MONDRIAN-831">
     * MONDRIAN-831, "Failure parsing queries with member identifiers beginning
     * with '_' and not expressed between brackets"</a>.
     *
     * <p>According to the spec
     * <a href="http://msdn.microsoft.com/en-us/library/ms145572.aspx">
     * Identifiers (MDX)</a>, the first character of a regular identifier
     * must be a letter (per the unicode standard 2.0) or underscore. Subsequent
     * characters must be a letter, and underscore, or a digit.
     */
    @Test
    void testScannerPunc() {
        assertParseQuery(
            "with member [Measures].__Foo as 1 + 2\n"
            + "select __Foo on 0\n"
            + "from _Bar_Baz",
            "with member [Measures].__Foo as '(1 + 2)'\n"
            + "select __Foo ON COLUMNS\n"
            + "from [_Bar_Baz]\n");

        // # is not allowed
        assertParseQueryFails(
            "with member [Measures].#_Foo as 1 + 2\n"
            + "select __Foo on 0\n"
            + "from _Bar#Baz",
            "Unexpected character '#'");
        assertParseQueryFails(
            "with member [Measures].Foo as 1 + 2\n"
            + "select Foo on 0\n"
            + "from Bar#Baz", "Unexpected character '#'");

        // The spec doesn't allow $ but SSAS allows it so we allow it too.
        assertParseQuery(
            "with member [Measures].$Foo as 1 + 2\n"
            + "select $Foo on 0\n"
            + "from Bar$Baz",
            "with member [Measures].$Foo as '(1 + 2)'\n"
            + "select $Foo ON COLUMNS\n"
            + "from [Bar$Baz]\n");
        // '$' is OK inside brackets too
        assertParseQuery(
            "select [measures].[$foo] on columns from sales",
            "select [measures].[$foo] ON COLUMNS\n"
            + "from [sales]\n");

        // ']' unexpected
        assertParseQueryFails(
            "select { Customers].Children } on columns from [Sales]",
            "Unexpected character ']'");
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testUnparse(TestingContext context) {
        checkUnparse(context.createConnection(),
            "with member [Measures].[Foo] as ' 123 '\n"
            + "select {[Measures].members} on columns,\n"
            + " CrossJoin([Product].members, {[Gender].Children}) on rows\n"
            + "from [Sales]\n"
            + "where [Marital Status].[S]",
            "with member [Measures].[Foo] as '123'\n"
            + "select {[Measures].Members} ON COLUMNS,\n"
            + "  Crossjoin([Product].Members, {[Gender].Children}) ON ROWS\n"
            + "from [Sales]\n"
            + "where [Marital Status].[S]\n");
    }

    private void checkUnparse(Connection connection, String queryString, final String expected) {
        final QueryImpl query = connection.parseQuery(queryString);
        String unparsedQueryString = Util.unparse(query);
        assertEqualsVerbose(expected, unparsedQueryString);
    }

    private void assertParseQueryFails(String query, String expected) {
        checkFails(createParser(), query, expected);
    }

    private void assertParseExprFails(String expr, String expected) {
        checkFails(createParser(), wrapExpr(expr), expected);
    }

    private void checkFails(TestParser p, String query, String expected) {
        try {
            p.parseInternal(null, query, false, funTable, false);

            fail("Must return an error");
        } catch (Exception e) {
            Exception nested = (Exception) e.getCause();
            String message = nested.getMessage();
            if (message.indexOf(expected) < 0) {
                fail(
                    "Actual result [" + message
                    + "] did not contain [" + expected + "]");
            }
        }
    }

    @Test
    void testMultipleAxes() throws Exception {
        TestParser p = createParser();
        String query = "select {[axis0mbr]} on axis(0), "
                + "{[axis1mbr]} on axis(1) from cube";

        assertNull(
                p.parseInternal(null, query, false, funTable, false),
                "Test parser should return null query");

        QueryAxis[] axes = p.getAxes();

        assertEquals(2, axes.length, "Number of axes");
        assertEquals(
            AxisOrdinal.StandardAxisOrdinal.forLogicalOrdinal(0).name(),
            axes[0].getAxisName(), "Axis index name must be correct");
        assertEquals(
            AxisOrdinal.StandardAxisOrdinal.forLogicalOrdinal(1).name(),
            axes[1].getAxisName(), "Axis index name must be correct");

        query = "select {[axis1mbr]} on aXiS(1), "
                + "{[axis0mbr]} on AxIs(0) from cube";

        assertNull(
            p.parseInternal(null, query, false, funTable, false), "Test parser should return null query");

        assertEquals(2, axes.length, "Number of axes");
        assertEquals(
            AxisOrdinal.StandardAxisOrdinal.forLogicalOrdinal(0).name(),
            axes[0].getAxisName(),"Axis index name must be correct");
        assertEquals(
            AxisOrdinal.StandardAxisOrdinal.forLogicalOrdinal(1).name(),
            axes[1].getAxisName(), "Axis index name must be correct");

        Exp colsSetExpr = axes[0].getSet();
        assertNotNull(colsSetExpr, "Column tuples");

        UnresolvedFunCallImpl fun = (UnresolvedFunCallImpl)colsSetExpr;
        Id arg0 = (Id) (fun.getArgs()[0]);
        IdImpl.NameSegment id = (IdImpl.NameSegment) arg0.getElement(0);
        assertEquals("axis0mbr", id.name, "Correct member on axis");

        Exp rowsSetExpr = axes[1].getSet();
        assertNotNull(rowsSetExpr, "Row tuples");

        fun = (UnresolvedFunCallImpl) rowsSetExpr;
        arg0 = (Id) (fun.getArgs()[0]);
        id = (IdImpl.NameSegment) arg0.getElement(0);
        assertEquals("axis1mbr", id.name, "Correct member on axis");
    }

    /**
     * If an axis expression is a member, implicitly convert it to a set.
     */
    @Test
    void testMemberOnAxis() {
        assertParseQuery(
            "select [Measures].[Sales Count] on 0, non empty [Store].[Store State].members on 1 from [Sales]",
            "select [Measures].[Sales Count] ON COLUMNS,\n"
            + "  NON EMPTY [Store].[Store State].members ON ROWS\n"
            + "from [Sales]\n");
    }

    @Test
    void testCaseTest() {
        assertParseQuery(
            "with member [Measures].[Foo] as "
            + " ' case when x = y then \"eq\" when x < y then \"lt\" else \"gt\" end '"
            + "select {[foo]} on axis(0) from [cube]",
            "with member [Measures].[Foo] as 'CASE WHEN (x = y) THEN \"eq\" WHEN (x < y) THEN \"lt\" ELSE \"gt\" END'\n"
            + "select {[foo]} ON COLUMNS\n"
            + "from [cube]\n");
    }

    @Test
    void testCaseSwitch() {
        assertParseQuery(
            "with member [Measures].[Foo] as "
            + " ' case x when 1 then 2 when 3 then 4 else 5 end '"
            + "select {[foo]} on axis(0) from [cube]",
            "with member [Measures].[Foo] as 'CASE x WHEN 1 THEN 2 WHEN 3 THEN 4 ELSE 5 END'\n"
            + "select {[foo]} ON COLUMNS\n"
            + "from [cube]\n");
    }

    /**
     * Test case for bug <a href="http://jira.pentaho.com/browse/MONDRIAN-306">
     * MONDRIAN-306, "Parser should not require braces around range op in WITH
     * SET"</a>.
     */
    @Test
    void testSetExpr() {
        assertParseQuery(
            "with set [Set1] as '[Product].[Drink]:[Product].[Food]' \n"
            + "select [Set1] on columns, {[Measures].defaultMember} on rows \n"
            + "from Sales",
            "with set [Set1] as '([Product].[Drink] : [Product].[Food])'\n"
            + "select [Set1] ON COLUMNS,\n"
            + "  {[Measures].defaultMember} ON ROWS\n"
            + "from [Sales]\n");

        // set expr in axes
        assertParseQuery(
            "select [Product].[Drink]:[Product].[Food] on columns,\n"
            + " {[Measures].defaultMember} on rows \n"
            + "from Sales",
            "select ([Product].[Drink] : [Product].[Food]) ON COLUMNS,\n"
            + "  {[Measures].defaultMember} ON ROWS\n"
            + "from [Sales]\n");
    }

    @Test
    void testDimensionProperties() {
        assertParseQuery(
            "select {[foo]} properties p1,   p2 on columns from [cube]",
            "select {[foo]} DIMENSION PROPERTIES p1, p2 ON COLUMNS\n"
            + "from [cube]\n");
    }

    @Test
    void testCellProperties() {
        assertParseQuery(
            "select {[foo]} on columns "
            + "from [cube] CELL PROPERTIES FORMATTED_VALUE",
            "select {[foo]} ON COLUMNS\n"
            + "from [cube]\n"
            + "[FORMATTED_VALUE]");
    }

    @Test
    void testIsEmpty() {
        assertParseExpr(
            "[Measures].[Unit Sales] IS EMPTY",
            "([Measures].[Unit Sales] IS EMPTY)");

        assertParseExpr(
            "[Measures].[Unit Sales] IS EMPTY AND 1 IS NULL",
            "(([Measures].[Unit Sales] IS EMPTY) AND (1 IS NULL))");

        // FIXME: "NULL" should associate as "IS NULL" rather than "NULL + 56"
        // FIXME: Gives error at token '+' with new parser.
        assertParseExpr(
            "- x * 5 is empty is empty is null + 56",
            "(((((- x) * 5) IS EMPTY) IS EMPTY) IS (NULL + 56))",
            true);
    }

    @Test
    void testIs() {
        assertParseExpr(
            "[Measures].[Unit Sales] IS [Measures].[Unit Sales] "
            + "AND [Measures].[Unit Sales] IS NULL",
            "(([Measures].[Unit Sales] IS [Measures].[Unit Sales]) "
            + "AND ([Measures].[Unit Sales] IS NULL))");
    }

    @Test
    void testIsNull() {
        assertParseExpr(
            "[Measures].[Unit Sales] IS NULL",
            "([Measures].[Unit Sales] IS NULL)");

        assertParseExpr(
            "[Measures].[Unit Sales] IS NULL AND 1 <> 2",
            "(([Measures].[Unit Sales] IS NULL) AND (1 <> 2))");

        assertParseExpr(
            "x is null or y is null and z = 5",
            "((x IS NULL) OR ((y IS NULL) AND (z = 5)))");

        assertParseExpr(
            "(x is null) + 56 > 6",
            "((((x IS NULL)) + 56) > 6)");

        // FIXME: Should be:
        //  "(((((x IS NULL) AND (a = b)) OR ((c = (d + 5))) IS NULL) + 5)"
        // FIXME: Gives error at token '+' with new parser.
        assertParseExpr(
            "x is null and a = b or c = d + 5 is null + 5",
            "(((x IS NULL) AND (a = b)) OR ((c = (d + 5)) IS (NULL + 5)))",
            true);
    }

    @Test
    void testNull() {
        assertParseExpr(
            "Filter({[Measures].[Foo]}, Iif(1 = 2, NULL, 'X'))",
            "Filter({[Measures].[Foo]}, Iif((1 = 2), NULL, \"X\"))");
    }

    @Test
    void testCast() {
        assertParseExpr(
            "Cast([Measures].[Unit Sales] AS Numeric)",
            "CAST([Measures].[Unit Sales] AS Numeric)");

        assertParseExpr(
            "Cast(1 + 2 AS String)",
            "CAST((1 + 2) AS String)");
    }

    /**
     * Verifies that calculated measures made of several '*' operators
     * can resolve them correctly.
     */
    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testMultiplication(TestingContext context) {
        Parser p = new Parser();
        final String mdx =
            wrapExpr(
                "([Measures].[Unit Sales]"
                + " * [Measures].[Store Cost]"
                + " * [Measures].[Store Sales])");

        final Statement statement =
            ((ConnectionBase) context.createConnection()).getInternalStatement();
        try {
            final QueryPart query =
                p.parseInternal(
                    new Parser.FactoryImpl(),
                    statement, mdx, false,
                    funTable, false);
            assertTrue(query instanceof QueryImpl);
            ((QueryImpl) query).resolve();
        } finally {
            statement.close();
        }
    }

    @Test
    void testBangFunction() {
        // Parser accepts '<id> [! <id>] *' as a function name, but ignores
        // all but last name.
        assertParseExpr("foo!bar!Exp(2.0)", "Exp(2.0)");
        assertParseExpr("1 + VBA!Exp(2.0 + 3)", "(1 + Exp((2.0 + 3)))");
    }

    @Test
    void testId() {
        assertParseExpr("foo", "foo");
        assertParseExpr("fOo", "fOo");
        assertParseExpr("[Foo].[Bar Baz]", "[Foo].[Bar Baz]");
        assertParseExpr("[Foo].&[Bar]", "[Foo].&[Bar]", false);
    }

    @Test
    void testIdWithKey() {
        // two segments each with a compound key
        final String mdx = "[Foo].&Key1&Key2.&[Key3]&Key4&[5]";
        assertParseExpr(mdx, mdx, false);

        TestParser p = createParser();
        final String mdxQuery = wrapExpr(mdx);
        new JavaccParserValidatorImpl(p).parseInternal(
            null, mdxQuery, false, funTable, false);
        assertEquals(1, p.getFormulas().length);
        Formula withMember = p.getFormulas()[0];
        final Exp expr = withMember.getExpression();
        Id id = (Id) expr;
        assertEquals(3, id.getSegments().size());

        final IdImpl.NameSegment seg0 = (IdImpl.NameSegment) id.getSegments().get(0);
        assertEquals("Foo", seg0.getName());
        assertEquals(IdImpl.Quoting.QUOTED, seg0.getQuoting());

        final IdImpl.KeySegment seg1 = (IdImpl.KeySegment) id.getSegments().get(1);
        assertEquals(IdImpl.Quoting.KEY, seg1.getQuoting());
        List<IdImpl.NameSegment> keyParts = seg1.getKeyParts();
        assertNotNull(keyParts);
        assertEquals(2, keyParts.size());
        assertEquals("Key1", keyParts.get(0).getName());
        assertEquals(
            IdImpl.Quoting.UNQUOTED, keyParts.get(0).getQuoting());
        assertEquals("Key2", keyParts.get(1).getName());
        assertEquals(
            IdImpl.Quoting.UNQUOTED, keyParts.get(1).getQuoting());

        final IdImpl.Segment seg2 = id.getSegments().get(2);
        assertEquals(IdImpl.Quoting.KEY, seg2.getQuoting());
        List<IdImpl.NameSegment> keyParts2 = seg2.getKeyParts();
        assertNotNull(keyParts2);
        assertEquals(3, keyParts2.size());
        assertEquals(
            IdImpl.Quoting.QUOTED, keyParts2.get(0).getQuoting());
        assertEquals(
            IdImpl.Quoting.UNQUOTED, keyParts2.get(1).getQuoting());
        assertEquals(
            IdImpl.Quoting.QUOTED, keyParts2.get(2).getQuoting());
        assertEquals("5", keyParts2.get(2).getName());

        final String actual = expr.toString();
        assertEqualsVerbose(mdx, actual);
    }

    @Test
    void testIdComplex() {
        // simple key
        assertParseExpr(
            "[Foo].&[Key1]&[Key2].[Bar]",
            "[Foo].&[Key1]&[Key2].[Bar]",
            false);
        // compound key
        assertParseExpr(
            "[Foo].&[1]&[Key 2]&[3].[Bar]",
            "[Foo].&[1]&[Key 2]&[3].[Bar]",
            false);
        // compound key sans brackets
        assertParseExpr(
            "[Foo].&Key1&Key2 + 4",
            "([Foo].&Key1&Key2 + 4)",
            false);
        // brackets are required for numbers
        if (false)
        assertParseExprFails(
            "[Foo].&[1]&[Key2]&^3.[Bar]",
            "Lexical error at line 1, column 51\\.  Encountered: \"3\" \\(51\\), after : \"&\"");
        // space between ampersand and key is unacceptable
        if (false)
        assertParseExprFails(
            "[Foo].&^ [Key2].[Bar]",
            "Lexical error at line 1, column 40\\.  Encountered: \" \" \\(32\\), after : \"&\"");
        // underscore after ampersand is unacceptable
        if (false)
        assertParseExprFails(
            "[Foo].&^_Key2.[Bar]",
            "Lexical error at line 1, column 40\\.  Encountered: \"_\" \\(95\\), after : \"&\"");
        // but underscore is OK within brackets
        assertParseExpr(
            "[Foo].&[_Key2].[Bar]",
            "[Foo].&[_Key2].[Bar]",
            false);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testCloneQuery(TestingContext context) {
        Connection connection = context.createConnection();
        QueryImpl query = connection.parseQuery(
            "select {[Measures].Members} on columns,\n"
            + " {[Store].Members} on rows\n"
            + "from [Sales]\n"
            + "where ([Gender].[M])");

        Object queryClone = new QueryImpl(query);
        assertTrue(queryClone instanceof QueryImpl);
        assertEquals(query.toString(), queryClone.toString());
    }

    /**
     * Tests parsing of numbers.
     */
    @Test
    void testNumbers() {
        // Number: [+-] <digits> [ . <digits> ] [e [+-] <digits> ]
        assertParseExpr("2", "2");

        // leading '-' is treated as an operator -- that's ok
        assertParseExpr("-3", "(- 3)");

        // leading '+' is ignored -- that's ok
        assertParseExpr("+45", "45");

        // space bad
        assertParseExprFails(
            "4 5",
            "Syntax error at line 1, column 35, token '5'");

        assertParseExpr("3.14", "3.14");
        assertParseExpr(".12345", "0.12345");

        // lots of digits left and right of point
        assertParseExpr("31415926535.89793", "31415926535.89793");
        assertParseExpr(
            "31415926535897.9314159265358979",
            "31415926535897.9314159265358979");
        assertParseExpr("3.141592653589793", "3.141592653589793");
        assertParseExpr(
            "-3141592653589793.14159265358979",
            "(- 3141592653589793.14159265358979)");

        // exponents akimbo
        assertParseExpr("1e2", "100", true);
        assertParseExpr("1e2", "1E+2", false);

        assertParseExprFails(
            "1e2e3",
            "Syntax error at line 1, column 37, token 'e3'");

        assertParseExpr("1.2e3", "1200", true);
        assertParseExpr("1.2e3", "1.2E+3", false);

        assertParseExpr("-1.2345e3", "(- 1234.5)");
        assertParseExprFails(
            "1.2e3.4",
            "Syntax error at line 1, column 39, token '0.4'");
        assertParseExpr(".00234e0003", "2.34");
        assertParseExpr(
            ".00234e-0067",
            "2.34E-70");
    }

    /**
     * Testcase for bug <a href="http://jira.pentaho.com/browse/MONDRIAN-272">
     * MONDRIAN-272, "High precision number in MDX causes overflow"</a>.
     * The problem was that "5000001234" exceeded the precision of the int being
     * used to gather the mantissa.
     */
    @Test
    void testLargePrecision() {
        // Now, a query with several numeric literals. This is the original
        // testcase for the bug.
        assertParseQuery(
            "with member [Measures].[Small Number] as '[Measures].[Store Sales] / 9000'\n"
            + "select\n"
            + "{[Measures].[Small Number]} on columns,\n"
            + "{Filter([Product].[Product Department].members, [Measures].[Small Number] >= 0.3\n"
            + "and [Measures].[Small Number] <= 0.5000001234)} on rows\n"
            + "from Sales\n"
            + "where ([Time].[1997].[Q2].[4])",
            "with member [Measures].[Small Number] as '([Measures].[Store Sales] / 9000)'\n"
            + "select {[Measures].[Small Number]} ON COLUMNS,\n"
            + "  {Filter([Product].[Product Department].members, (([Measures].[Small Number] >= 0.3) AND ([Measures].[Small Number] <= 0.5000001234)))} ON ROWS\n"
            + "from [Sales]\n"
            + "where ([Time].[1997].[Q2].[4])\n");
    }

    @Test
    void testIdentifier() {
        // must have at least one segment
        IdImpl id;
        List<IdImpl.Segment> list  = Collections.<IdImpl.Segment>emptyList();
        try {
            id = new IdImpl(list);
            fail("expected exception, got " + id);
        } catch (IllegalArgumentException e) {
            assertInstanceOf(IllegalArgumentException.class, e);
        }

        id = new IdImpl(new IdImpl.NameSegment("foo"));
        assertEquals("[foo]", id.toString());

        // append does not mutate
        Id id2 = id.append(
            new IdImpl.KeySegment(
                new IdImpl.NameSegment(
                    "bar", IdImpl.Quoting.QUOTED)));
        assertNotSame(id, id2);
        assertEquals("[foo]", id.toString());
        assertEquals("[foo].&[bar]", id2.toString());

        // cannot mutate segment list
        final List<IdImpl.Segment> segments = id.getSegments();
        try {
            segments.remove(0);
            fail("expected exception");
        } catch (UnsupportedOperationException e) {
            // ok
        }
        try {
            segments.clear();
            fail("expected exception");
        } catch (UnsupportedOperationException e) {
            // ok
        }
        IdImpl.NameSegment nameSegment = new IdImpl.NameSegment("baz");
        try {
            segments.add(
                nameSegment);
            fail("expected exception");
        } catch (UnsupportedOperationException e) {
            // ok
        }
    }

    /**
     * Test case for empty expressions. Test case for <a href=
  "http://sf.net/tracker/?func=detail&aid=3030772&group_id=168953&atid=848534"
     * > bug 3030772, "DrilldownLevelTop parser error"</a>.
     */
    @Test
    void testEmptyExpr() {
        assertParseQuery(
            "select NON EMPTY HIERARCHIZE(\n"
            + "  {DrillDownLevelTop(\n"
            + "     {[Product].[All Products]},3,,[Measures].[Unit Sales])}"
            + "  ) ON COLUMNS\n"
            + "from [Sales]\n",
            "select NON EMPTY HIERARCHIZE({DrillDownLevelTop({[Product].[All Products]}, 3, , [Measures].[Unit Sales])}) ON COLUMNS\n"
            + "from [Sales]\n");

        // more advanced; the actual test case in the bug
        assertParseQuery(
            "SELECT {[Measures].[NetSales]}"
            + " DIMENSION PROPERTIES PARENT_UNIQUE_NAME ON COLUMNS ,"
            + " NON EMPTY HIERARCHIZE(AddCalculatedMembers("
            + "{DrillDownLevelTop({[ProductDim].[Name].[All]}, 10, ,"
            + " [Measures].[NetSales])}))"
            + " DIMENSION PROPERTIES PARENT_UNIQUE_NAME ON ROWS "
            + "FROM [cube]",
            "select {[Measures].[NetSales]} DIMENSION PROPERTIES PARENT_UNIQUE_NAME ON COLUMNS,\n"
            + "  NON EMPTY HIERARCHIZE(AddCalculatedMembers({DrillDownLevelTop({[ProductDim].[Name].[All]}, 10, , [Measures].[NetSales])})) DIMENSION PROPERTIES PARENT_UNIQUE_NAME ON ROWS\n"
            + "from [cube]\n");
    }

    /**
     * Test case for SELECT in the FROM clause.
     */
    @Disabled
    @Test
    void _testInnerSelect() {
        assertParseQuery(
            "SELECT FROM "
            + "(SELECT ({[ProductDim].[Product Group].&[Mobile Phones]}) "
            + "ON COLUMNS FROM [cube]) CELL PROPERTIES VALUE",
            "SELECT\n"
            + "FROM (\n"
            + "    SELECT\n"
            + "    {[ProductDim].[Product Group].&[Mobile Phones]} ON COLUMNS\n"
            + "    FROM [cube])\n"
            + "CELL PROPERTIES VALUE");
    }

    @Disabled
    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void _testRealInnerSelect(TestingContext context) {
        Connection connection = context.createConnection();
        assertQueryReturns(connection,
        "select\n"
        + "from\n"
        + "(\n"
        + "select ({([Product].[Food].[Baked Goods].[Bread])}) on 0 from [Sales]\n"
        + ")\n"
        + "CELL PROPERTIES VALUE",
        "xxx"); //need check result
    }

    /**
     * Test case for bug <a href="http://jira.pentaho.com/browse/MONDRIAN-648">
     * MONDRIAN-648, "AS operator has lower precedence than required by MDX
     * specification"</a>.
     *
     * <p>Currently that bug is not fixed. We give the AS operator low
     * precedence, so CAST works as it should but 'expr AS namedSet' does not.
     */
    @Test
    void testAsPrecedence() {
        // low precedence operator (AND) in CAST.
        assertParseQuery(
            "select cast(a and b as string) on 0 from [cube]",
            "select CAST((a AND b) AS string) ON COLUMNS\n"
            + "from [cube]\n");

        // medium precedence operator (:) in CAST
        assertParseQuery(
            "select cast(a : b as string) on 0 from [cube]",
            "select CAST((a : b) AS string) ON COLUMNS\n" + "from [cube]\n");

        // high precedence operator (IS) in CAST
        assertParseQuery(
            "select cast(a is b as string) on 0 from [cube]",
            "select CAST((a IS b) AS string) ON COLUMNS\n"
            + "from [cube]\n");

        // low precedence operator in axis expression. According to spec, 'AS'
        // has higher precedence than '*' but we give it lower. Bug.
        assertParseQuery(
            "select a * b as c on 0 from [cube]",
            Bug.BugMondrian648Fixed
                ? "select (a * (b AS c) ON COLUMNS\n"
                  + "from [cube]\n"
                : "select ((a * b) AS c) ON COLUMNS\n"
                  + "from [cube]\n");

        if (Bug.BugMondrian648Fixed) {
            // Note that 'AS' has higher precedence than '*'.
            assertParseQuery(
                "select a * b as c * d on 0 from [cube]",
                "select (((a * b) AS c) * d) ON COLUMNS\n"
                + "from [cube]\n");

        } else {
            // Bug. Even with MONDRIAN-648, Mondrian should parse this query.
            assertParseQueryFails(
                "select a * b as c * d on 0 from [cube]",
                "Syntax error at line 1, column 19, token '*'");
        }

        // Spec says that ':' has a higher precedence than '*'.
        // Mondrian currently does it wrong.
        assertParseQuery(
            "select a : b * c : d on 0 from [cube]",
            Bug.BugMondrian648Fixed
                ? "select ((a : b) * (c : d)) ON COLUMNS\n"
                  + "from [cube]\n"
                : "select ((a : (b * c)) : d) ON COLUMNS\n"
                  + "from [cube]\n");

        if (Bug.BugMondrian648Fixed) {
            // Note that 'AS' has higher precedence than ':', has higher
            // precedence than '*'.
            assertParseQuery(
                "select a : b as n * c : d as n2 as n3 on 0 from [cube]",
                "select (((a : b) as n) * ((c : d) AS n2) as n3) ON COLUMNS\n"
                + "from [cube]\n");
        } else {
            // Bug. Even with MONDRIAN-648, Mondrian should parse this query.
            assertParseQueryFails(
                "select a : b as n * c : d as n2 as n3 on 0 from [cube]",
                "Syntax error at line 1, column 19, token '*'");
        }
    }

    @Test
    void testDrillThrough() {
        assertParseQuery(
            "DRILLTHROUGH SELECT [Foo] on 0, [Bar] on 1 FROM [Cube]",
            "drillthrough\n"
            + "select [Foo] ON COLUMNS,\n"
            + "  [Bar] ON ROWS\n"
            + "from [Cube]\n");
    }

    @Test
    void testDrillThroughExtended1() {
        assertParseQuery(
            "DRILLTHROUGH MAXROWS 5 FIRSTROWSET 7\n"
            + "SELECT [Foo] on 0, [Bar] on 1 FROM [Cube]\n"
            + "RETURN [Xxx].[AAa]",
            "drillthrough maxrows 5 firstrowset 7\n"
            + "select [Foo] ON COLUMNS,\n"
            + "  [Bar] ON ROWS\n"
            + "from [Cube]\n"
            + " return  return [Xxx].[AAa]");
    }

    @Test
    void testDrillThroughExtended() {
        assertParseQuery(
            "DRILLTHROUGH MAXROWS 5 FIRSTROWSET 7\n"
            + "SELECT [Foo] on 0, [Bar] on 1 FROM [Cube]\n"
            + "RETURN [Xxx].[AAa], [YYY]",
            "drillthrough maxrows 5 firstrowset 7\n"
            + "select [Foo] ON COLUMNS,\n"
            + "  [Bar] ON ROWS\n"
            + "from [Cube]\n"
            + " return  return [Xxx].[AAa], [YYY]");
    }

    @Test
    void testDrillThroughExtended3() {
        assertParseQuery(
            "DRILLTHROUGH MAXROWS 5 FIRSTROWSET 7\n"
            + "SELECT [Foo] on 0, [Bar] on 1 FROM [Cube]\n"
            + "RETURN [Xxx].[AAa], [YYY], [zzz]",
            "drillthrough maxrows 5 firstrowset 7\n"
            + "select [Foo] ON COLUMNS,\n"
            + "  [Bar] ON ROWS\n"
            + "from [Cube]\n"
            + " return  return [Xxx].[AAa], [YYY], [zzz]");
    }

    @Test
    void testExplain() {
        assertParseQuery(
            "explain plan for\n"
            + "with member [Mesaures].[Foo] as 1 + 3\n"
            + "select [Measures].[Unit Sales] on 0,\n"
            + " [Product].Children on 1\n"
            + "from [Sales]",
            "explain plan for\n"
            + "with member [Mesaures].[Foo] as '(1 + 3)'\n"
            + "select [Measures].[Unit Sales] ON COLUMNS,\n"
            + "  [Product].Children ON ROWS\n"
            + "from [Sales]\n");
        assertParseQuery(
            "explain plan for\n"
            + "drillthrough maxrows 5\n"
            + "with member [Mesaures].[Foo] as 1 + 3\n"
            + "select [Measures].[Unit Sales] on 0,\n"
            + " [Product].Children on 1\n"
            + "from [Sales]",
            "explain plan for\n"
            + "drillthrough maxrows 5\n"
            + "with member [Mesaures].[Foo] as '(1 + 3)'\n"
            + "select [Measures].[Unit Sales] ON COLUMNS,\n"
            + "  [Product].Children ON ROWS\n"
            + "from [Sales]\n");
    }

    /**
     * Test case for bug <a href="http://jira.pentaho.com/browse/MONDRIAN-924">
     * MONDRIAN-924, "Parsing fails with multiple spaces between words"</a>.
     */
    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testMultipleSpaces(TestingContext context) {
        assertParseQuery(
            "select [Store].[With   multiple  spaces] on 0\n"
            + "from [Sales]",
            "select [Store].[With   multiple  spaces] ON COLUMNS\n"
            + "from [Sales]\n");

        // Only enable the following if you have manually renamed 'Store 23' to
        // 'Store   23' (note: 3 spaces) in your database.
        Connection connection = context.createConnection();
        if (false) {
        assertQueryReturns(connection,
            "select [Store].[USA].[WA].[Yakima].[Store   23] on 0\n"
            + "from [Sales]",
            "Axis #0:\n"
            + "{}\n"
            + "Axis #1:\n"
            + "{[Store].[USA].[WA].[Yakima].[Store   23]}\n"
            + "Row #0: 11,491\n");
        assertQueryReturns(connection,
            "With \n"
            + "Set [*NATIVE_CJ_SET] as '[*BASE_MEMBERS_Store]' \n"
            + "Set [*SORTED_ROW_AXIS] as 'Order([*CJ_ROW_AXIS],[Store].CurrentMember.OrderKey,BASC,"
            + "Ancestor([Store].CurrentMember,[Store].[Store City]).OrderKey,BASC)' \n"
            + "Set [*BASE_MEMBERS_Measures] as '{[Measures].[*ZERO]}' \n"
            + "Set [*CJ_ROW_AXIS] as 'Generate([*NATIVE_CJ_SET], {([Store].currentMember)})' \n"
            + "Set [*BASE_MEMBERS_Store] as '{[Store].[USA].[WA].[Yakima].[Store   23]}' \n"
            + "Set [*CJ_COL_AXIS] as '[*NATIVE_CJ_SET]' \n"
            + "Member [Measures].[*ZERO] as '0', SOLVE_ORDER=0 \n"
            + "Select \n"
            + "[*BASE_MEMBERS_Measures] on columns, \n"
            + "[*SORTED_ROW_AXIS] on rows \n"
            + "From [Sales] ",
            "Axis #0:\n"
            + "{}\n"
            + "Axis #1:\n"
            + "{[Measures].[*ZERO]}\n"
            + "Axis #2:\n"
            + "{[Store].[USA].[WA].[Yakima].[Store   23]}\n"
            + "Row #0: 0\n");
        }
    }

    /**
     * Test case for olap4j bug
     * <a href="http://sourceforge.net/tracker/?func=detail&aid=3515404&group_id=168953&atid=848534">3515404</a>,
     * "Inconsistent parsing behavior('.CHILDREN' and '.Children')".
     */
    @Test
    void testChildren() {
        TestParser p = createParser();

        checkChildren0(p, "CHILDREN");
        checkChildren0(p, "Children");
        checkChildren0(p, "children");
    }

    private void checkChildren0(TestParser p, String name) {
        Exp node =
            p.parseExpression(
                null, null, "[Store].[USA]." + name, false, funTable);
        checkChildren(node, name);
    }

    private void checkChildren(Exp node, String name) {
        assertTrue(node instanceof FunCall);
        FunCall call = (FunCall) node;
        assertEquals(name, call.getFunName());
        assertTrue(call.getArgs()[0] instanceof Id);
        assertEquals("[Store].[USA]", call.getArgs()[0].toString());
        assertEquals(1, call.getArgCount());
    }

    /**
     * Parses an MDX query and asserts that the result is as expected when
     * unparsed.
     *
     * @param mdx MDX query
     * @param expected Expected result of unparsing
     */
    private void assertParseQuery(String mdx, final String expected) {
        assertParseQuery(mdx, expected, true);
        assertParseQuery(mdx, expected, false);
    }

    private void assertParseQuery(
        String mdx, final String expected, boolean old)
    {
        TestParser p = createParser();
        final QueryPart query;
        if (old) {
            query = p.parseInternal(null, mdx, false, funTable, false);
        } else {
            MdxParserValidator parser =
                new JavaccParserValidatorImpl(p);
            query =
                parser.parseInternal(
                    null, mdx, false, funTable, false);
        }
        if (!(query instanceof DrillThrough
            || query instanceof Explain))
        {
            assertNull(query, "Test parser should return null query");
        }
        final String actual = p.toMdxString();
        assertEqualsVerbose(expected, actual);
    }

    /**
     * Parses an MDX expression and asserts that the result is as expected when
     * unparsed.
     *
     * @param expr MDX query
     * @param expected Expected result of unparsing
     */
    private void assertParseExpr(String expr, final String expected) {
        assertParseExpr(expr, expected, true);
        assertParseExpr(expr, expected, false);
    }

    private void assertParseExpr(
        String expr, final String expected, boolean old)
    {
        TestParser p = createParser();
        final String mdx = wrapExpr(expr);
        final QueryPart query;
        if (old) {
            query = p.parseInternal(null, mdx, false, funTable, false);
        } else {
            MdxParserValidator parser =
                new JavaccParserValidatorImpl(p);
            query =
                parser.parseInternal(
                    null, mdx, false, funTable, false);
        }
        assertNull(query, "Test parser should return null query");
        final String actual = Util.unparse(p.formulas[0].getExpression());
        assertEqualsVerbose(expected, actual);
    }

    private String wrapExpr(String expr) {
        return
            "with member [Measures].[Foo] as "
            + expr
            + "\n select from [Sales]";
    }

    public static class TestParser
        extends Parser
        implements MdxParserValidator.QueryPartFactory
    {
        private Formula[] formulas;
        private QueryAxis[] axes;
        private String cube;
        private Exp slicer;
        private QueryPart[] cellProps;
        private boolean drillThrough;
        private int maxRowCount;
        private int firstRowOrdinal;
        private List<Exp> returnList;
        private boolean explain;

        public TestParser() {
            super();
        }

        public QueryPart parseInternal(
            Statement statement,
            String queryString,
            boolean debug,
            FunTable funTable,
            boolean strictValidation)
        {
            return super.parseInternal(
                this,
                statement,
                queryString,
                debug,
                funTable,
                strictValidation);
        }

        @Override
		public Query makeQuery(
            Statement statement,
            Formula[] formulae,
            QueryAxis[] axes,
            Subcube subcube,
            Exp slicer,
            QueryPart[] cellProps,
            boolean strictValidation)
        {
            setFormulas(formulae);
            setAxes(axes);
            setCube(subcube.getCubeName());
            setSlicer(slicer);
            setCellProps(cellProps);
            return null;
        }

        @Override
		public DrillThrough makeDrillThrough(
            Query query,
            int maxRowCount,
            int firstRowOrdinal,
            List<Exp> returnList)
        {
            this.drillThrough = true;
            this.maxRowCount = maxRowCount;
            this.firstRowOrdinal = firstRowOrdinal;
            this.returnList = returnList;
            return null;
        }

        @Override
		public CalculatedFormula makeCalculatedFormula(
                String cubeName,
                Formula e)
        {
            return null;
        }

        @Override
		public Explain makeExplain(QueryPart query) {
            this.explain = true;
            return null;
        }

        @Override
		public Refresh makeRefresh(
                String cubeName)
        {
            return null;
        }

        @Override
		public Update makeUpdate(
                String cubeName,
                List<UpdateImpl.UpdateClause> list)
        {
            return null;
        }

        @Override
		public DmvQuery makeDmvQuery(
                String tableName,
                List<String> columns,
                Exp whereExpression)
        {
            return null;
        }

        @Override
		public TransactionCommand makeTransactionCommand(
                TransactionCommandImpl.Command c)
        {
            return null;
        }

        public QueryAxis[] getAxes() {
            return axes;
        }

        public void setAxes(QueryAxis[] axes) {
            this.axes = axes;
        }

        public QueryPart[] getCellProps() {
            return cellProps;
        }

        public void setCellProps(QueryPart[] cellProps) {
            this.cellProps = cellProps;
        }

        public String getCube() {
            return cube;
        }

        public void setCube(String cube) {
            this.cube = cube;
        }

        public Formula[] getFormulas() {
            return formulas;
        }

        public void setFormulas(Formula[] formulas) {
            this.formulas = formulas;
        }

        public Exp getSlicer() {
            return slicer;
        }

        public void setSlicer(Exp slicer) {
            this.slicer = slicer;
        }

        /**
         * Converts this query to a string.
         *
         * @return This query converted to a string
         */
        public String toMdxString() {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new QueryPrintWriter(sw);
            unparse(pw);
            return sw.toString();
        }

        private void unparse(PrintWriter pw) {
            if (explain) {
                pw.println("explain plan for");
            }
            if (drillThrough) {
                pw.print("drillthrough");
                if (maxRowCount > 0) {
                    pw.print(" maxrows " + maxRowCount);
                }
                if (firstRowOrdinal > 0) {
                    pw.print(" firstrowset " + firstRowOrdinal);
                }
                pw.println();
            }
            if (formulas != null) {
                for (int i = 0; i < formulas.length; i++) {
                    if (i == 0) {
                        pw.print("with ");
                    } else {
                        pw.print("  ");
                    }
                    formulas[i].unparse(pw);
                    pw.println();
                }
            }
            pw.print("select ");
            if (axes != null) {
                for (int i = 0; i < axes.length; i++) {
                    axes[i].unparse(pw);
                    if (i < axes.length - 1) {
                        pw.println(",");
                        pw.print("  ");
                    } else {
                        pw.println();
                    }
                }
            }
            if (cube != null) {
                pw.println("from [" + cube + "]");
            }
            if (slicer != null) {
                pw.print("where ");
                slicer.unparse(pw);
                pw.println();
            }
            if (cellProps != null) {
                for (QueryPart cellProp : cellProps) {
                    cellProp.unparse(pw);
                }
            }
            if (drillThrough && returnList != null) {
                pw.print(" return ");
                ExpBase.unparseList(
                    pw, returnList.toArray(new Exp[returnList.size()]),
                    " return ", ", ", "");
            }
        }
    }
}
