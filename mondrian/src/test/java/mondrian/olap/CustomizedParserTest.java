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
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.daanse.olap.api.DataType;
import org.eclipse.daanse.olap.api.function.FunctionDefinition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.opencube.junit5.ContextSource;
import org.opencube.junit5.context.TestingContext;
import org.opencube.junit5.dataloader.FastFoodmardDataLoader;
import org.opencube.junit5.propupdator.AppandFoodMartCatalogAsFile;

import mondrian.olap.fun.CustomizedFunctionTable;
import mondrian.olap.fun.ParenthesesFunDef;
import mondrian.server.Statement;
import mondrian.test.PropertySaver5;

/**
 * Tests a customized MDX Parser.
 *
 * @author Rushan Chen
 * @author Stefan Bischof
 */
class CustomizedParserTest {

	private PropertySaver5 propSaver;

	@BeforeEach
	public void beforeEach() {
		propSaver = new PropertySaver5();
	}

	@AfterEach
	public void afterEach() {
		propSaver.reset();
	}

    CustomizedFunctionTable getCustomizedFunctionTable(Set<String> funNameSet) {
        Set<FunctionDefinition> specialFunctions = new HashSet<>();
        specialFunctions.add(new ParenthesesFunDef(DataType.NUMERIC));

        CustomizedFunctionTable cftab =
            new CustomizedFunctionTable(funNameSet, specialFunctions);
        cftab.init();
        return cftab;
    }

    private String wrapExpr(String expr) {
        return
            "with member [Measures].[Foo] as "
            + expr
            + "\n select from [Sales]";
    }

    private void checkErrorMsg(Throwable e, String expectedErrorMsg) {
        while (e.getCause() != null && !e.getCause().equals(e)) {
            e = e.getCause();
        }
        String actualMsg = e.getMessage();
        assertEquals(expectedErrorMsg, actualMsg);
    }

    private QueryImpl getParsedQueryForExpr(TestingContext foodMartContext,
        CustomizedFunctionTable cftab,
        String expr,
        boolean strictValidation)
    {
        String mdx = wrapExpr(expr);
        final ConnectionBase connectionBase =(ConnectionBase) foodMartContext.createConnection();
        final Statement statement =
            connectionBase.getInternalStatement();
        try {
            return (QueryImpl) connectionBase.parseStatement(
                statement, mdx, cftab, strictValidation);
        } finally {
        	connectionBase.close();
            statement.close();
        }
    }

    private QueryImpl getParsedQueryForExpr(TestingContext foodMartContext,
        CustomizedFunctionTable cftab,
        String expr)
    {
        return getParsedQueryForExpr(foodMartContext, cftab, expr, false);
    }
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testAddition(TestingContext foodMartContext) {
        Set<String> functionNameSet = new HashSet<>();
        functionNameSet.add("+");
        CustomizedFunctionTable cftab =
            getCustomizedFunctionTable(functionNameSet);

        try {
            QueryImpl q =
                getParsedQueryForExpr(foodMartContext,
                    cftab,
                    "([Measures].[Store Cost] + [Measures].[Unit Sales])");
            q.resolve(q.createValidator(cftab, true));
        } catch (Throwable e) {
            fail(e.getMessage());
        }
    }
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testSubtraction(TestingContext foodMartContext) {
        Set<String> functionNameSet = new HashSet<>();
        functionNameSet.add("-");
        CustomizedFunctionTable cftab =
            getCustomizedFunctionTable(functionNameSet);

        try {
            QueryImpl q =
                getParsedQueryForExpr(foodMartContext,
                    cftab,
                    "([Measures].[Store Cost] - [Measures].[Unit Sales])");
            q.resolve(q.createValidator(cftab, true));
        } catch (Throwable e) {
            fail(e.getMessage());
        }
    }
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testSingleMultiplication(TestingContext foodMartContext) {
        Set<String> functionNameSet = new HashSet<>();
        functionNameSet.add("*");
        CustomizedFunctionTable cftab =
            getCustomizedFunctionTable(functionNameSet);

        try {
            QueryImpl q =
                getParsedQueryForExpr(foodMartContext,
                    cftab,
                    "[Measures].[Store Cost] * [Measures].[Unit Sales]");
            q.resolve(q.createValidator(cftab, true));
        } catch (Throwable e) {
            fail(e.getMessage());
        }
    }
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testMultipleMultiplication(TestingContext foodMartContext) {
        Set<String> functionNameSet = new HashSet<>();
        functionNameSet.add("*");
        CustomizedFunctionTable cftab =
            getCustomizedFunctionTable(functionNameSet);

        try {
            QueryImpl q =
                getParsedQueryForExpr(foodMartContext,
                    cftab,
                    "([Measures].[Store Cost] * [Measures].[Unit Sales] * [Measures].[Store Sales])");
            q.resolve(q.createValidator(cftab, true));
        } catch (Throwable e) {
            fail(e.getMessage());
        }
    }
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testLiterals(TestingContext foodMartContext) {
        Set<String> functionNameSet = new HashSet<>();
        functionNameSet.add("+");
        CustomizedFunctionTable cftab =
            getCustomizedFunctionTable(functionNameSet);

        try {
            QueryImpl q =
                getParsedQueryForExpr(foodMartContext,
                    cftab,
                    "([Measures].[Store Cost] + 10)");
            q.resolve(q.createValidator(cftab, true));
        } catch (Throwable e) {
            fail(e.getMessage());
        }
    }
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testMissingObjectFail(TestingContext foodMartContext) {
        Set<String> functionNameSet = new HashSet<>();
        functionNameSet.add("+");
        CustomizedFunctionTable cftab =
            getCustomizedFunctionTable(functionNameSet);

        try {
            QueryImpl q =
                getParsedQueryForExpr(foodMartContext,
                    cftab,
                    "'[Measures].[Store Cost] + [Measures].[Unit Salese]'");
            q.resolve(q.createValidator(cftab, true));
            // Shouldn't reach here
            fail("Expected error did not occur.");
        } catch (Throwable e) {
            checkErrorMsg(
                e,
                "Mondrian Error:MDX object '[Measures].[Unit Salese]' not found in cube 'Sales'");
        }
    }
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testMissingObjectFailWithStrict(TestingContext foodMartContext) {
        testMissingObject(foodMartContext,true);
    }
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testMissingObjectSucceedWithoutStrict(TestingContext foodMartContext) {
        testMissingObject(foodMartContext,false);
    }

    private void testMissingObject(TestingContext foodMartContext,boolean strictValidation) {
        Set<String> functionNameSet = new HashSet<>();
        functionNameSet.add("+");
        CustomizedFunctionTable cftab =
            getCustomizedFunctionTable(functionNameSet);

        MondrianProperties properties = MondrianProperties.instance();

        propSaver.set(
            properties.IgnoreInvalidMembers,
            true);
        propSaver.set(
            properties.IgnoreInvalidMembersDuringQuery,
            true);

        // TODO: MondranProperties is a singleton. shouldbe contextbased multipe instances
        //then eadyer to fix this and not use reset algorithm
//        foodMartContext.setProperty("mondrian.rolap.ignoreInvalidMembers",true);
//        foodMartContext.setProperty("mondrian.rolap.ignoreInvalidMembersDuringQuery",true);


        try {
            QueryImpl q = getParsedQueryForExpr(foodMartContext,
                cftab,
                "'[Measures].[Store Cost] + [Measures].[Unit Salese]'",
                strictValidation);
            q.resolve(q.createValidator(cftab, true));
            // Shouldn't reach here if strictValidation
            fail(
                "Expected error does not occur when strictValidation is set:"
                + strictValidation);
        } catch (Throwable e) {
            if (strictValidation) {
                checkErrorMsg(
                    e,
                    "Mondrian Error:MDX object '[Measures].[Unit Salese]' not found in cube 'Sales'");
            } else {
                checkErrorMsg(
                    e,
                    "Expected error does not occur when strictValidation is set:"
                    + strictValidation);
            }
        }
    }
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testMultiplicationFail(TestingContext foodMartContext) {
        Set<String> functionNameSet = new HashSet<>();
        functionNameSet.add("+");
        CustomizedFunctionTable cftab =
            getCustomizedFunctionTable(functionNameSet);

        try {
            QueryImpl q =
                getParsedQueryForExpr(foodMartContext,
                    cftab,
                    "([Measures].[Store Cost] * [Measures].[Unit Sales])");
            q.resolve(q.createValidator(cftab, true));
            // Shouldn't reach here
            fail("Expected error did not occur.");
        } catch (Throwable e) {
            checkErrorMsg(
                e,
                "Mondrian Error:No function matches signature '<Member> * <Member>'");
        }
    }
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testMixingAttributesFail(TestingContext foodMartContext) {
        Set<String> functionNameSet = new HashSet<>();
        functionNameSet.add("+");
        CustomizedFunctionTable cftab =
            getCustomizedFunctionTable(functionNameSet);

        try {
            QueryImpl q =
                getParsedQueryForExpr(foodMartContext,
                    cftab,
                    "([Measures].[Store Cost] + [Store].[Store Country])");
            q.resolve(q.createValidator(cftab, true));
            // Shouldn't reach here
            fail("Expected error did not occur.");
        } catch (Throwable e) {
            checkErrorMsg(
                e,
                "Mondrian Error:No function matches signature '<Member> + <Level>'");
        }
    }
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testCrossJoinFail(TestingContext foodMartContext) {
        Set<String> functionNameSet = new HashSet<>();
        functionNameSet.add("+");
        functionNameSet.add("-");
        functionNameSet.add("*");
        functionNameSet.add("/");
        CustomizedFunctionTable cftab =
            getCustomizedFunctionTable(functionNameSet);

        try {
            QueryImpl q =
                getParsedQueryForExpr(foodMartContext,
                    cftab,
                    "CrossJoin([Measures].[Store Cost], [Measures].[Unit Sales])");
            q.resolve(q.createValidator(cftab, true));
            // Shouldn't reach here
            fail("Expected error did not occur.");
        } catch (Throwable e) {
            checkErrorMsg(
                e,
                "Mondrian Error:Tuple contains more than one member of hierarchy '[Measures]'.");
        }
    }
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testMeasureSlicerFail(TestingContext foodMartContext) {
        Set<String> functionNameSet = new HashSet<>();
        functionNameSet.add("+");
        functionNameSet.add("-");
        functionNameSet.add("*");
        functionNameSet.add("/");
        CustomizedFunctionTable cftab =
            getCustomizedFunctionTable(functionNameSet);

        try {
            QueryImpl q =
                getParsedQueryForExpr(foodMartContext,
                    cftab,
                    "([Measures].[Store Cost], [Gender].[F])");
            q.resolve(q.createValidator(cftab, true));
            // Shouldn't reach here
            fail("Expected error did not occur.");
        } catch (Throwable e) {
            checkErrorMsg(
                e,
                "Mondrian Error:No function matches signature '(<Member>, <Member>)'");
        }
    }
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testTupleFail(TestingContext foodMartContext) {
        Set<String> functionNameSet = new HashSet<>();
        functionNameSet.add("+");
        functionNameSet.add("-");
        functionNameSet.add("*");
        functionNameSet.add("/");
        CustomizedFunctionTable cftab =
            getCustomizedFunctionTable(functionNameSet);

        try {
            QueryImpl q =
                getParsedQueryForExpr(foodMartContext,
                    cftab,
                    "([Store].[USA], [Gender].[F])");
            q.resolve(q.createValidator(cftab, true));
            // Shouldn't reach here
            fail("Expected error did not occur.");
        } catch (Throwable e) {
            checkErrorMsg(
                e,
                "Mondrian Error:No function matches signature '(<Member>, <Member>)'");
        }
    }

    /**
     * Mondrian is not strict about referencing a dimension member in calculated
     * measures.
     *
     * <p>The following expression passes parsing and validation.
     * Its computation is strange: the result is as if the measure is defined as
     *  ([Measures].[Store Cost] + [Measures].[Store Cost])
     */
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testMixingMemberLimitation(TestingContext foodMartContext) {
        Set<String> functionNameSet = new HashSet<>();
        functionNameSet.add("+");
        CustomizedFunctionTable cftab =
            getCustomizedFunctionTable(functionNameSet);

        try {
            QueryImpl q =
                getParsedQueryForExpr(foodMartContext,
                    cftab,
                    "([Measures].[Store Cost] + [Store].[USA])");
            q.resolve(q.createValidator(cftab, true));
            // Shouldn't reach here
            fail("Expected error did not occur.");
        } catch (Throwable e) {
            checkErrorMsg(e, "Expected error did not occur.");
        }
    }
}

// End CustomizedParserTest.java

