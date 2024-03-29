/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2015-2017 Hitachi Vantara and others
// All Rights Reserved.
*/
package mondrian.rolap;

import static mondrian.rolap.RolapNativeTopCountTestCases.CUSTOM_COUNT_MEASURE_CUBE;
import static mondrian.rolap.RolapNativeTopCountTestCases.CUSTOM_COUNT_MEASURE_QUERY;
import static mondrian.rolap.RolapNativeTopCountTestCases.EMPTY_CELLS_ARE_HIDDEN_WHEN_NON_EMPTY_QUERY;
import static mondrian.rolap.RolapNativeTopCountTestCases.EMPTY_CELLS_ARE_SHOWN_COUNTRIES_QUERY;
import static mondrian.rolap.RolapNativeTopCountTestCases.EMPTY_CELLS_ARE_SHOWN_NOT_MORE_THAN_EXIST_QUERY;
import static mondrian.rolap.RolapNativeTopCountTestCases.EMPTY_CELLS_ARE_SHOWN_STATES_QUERY;
import static mondrian.rolap.RolapNativeTopCountTestCases.IMPLICIT_COUNT_MEASURE_QUERY;
import static mondrian.rolap.RolapNativeTopCountTestCases.NON_EMPTY_IS_NOT_IGNORED_WHEN_TWO_PARAMS_QUERY;
import static mondrian.rolap.RolapNativeTopCountTestCases.RESULTS_ARE_SHOWN_NOT_MORE_THAN_EXIST_2_PARAMS_QUERY;
import static mondrian.rolap.RolapNativeTopCountTestCases.ROLE_RESTRICTION_WORKS_DF_QUERY;
import static mondrian.rolap.RolapNativeTopCountTestCases.ROLE_RESTRICTION_WORKS_DF_ROLE_DEF;
import static mondrian.rolap.RolapNativeTopCountTestCases.ROLE_RESTRICTION_WORKS_DF_ROLE_NAME;
import static mondrian.rolap.RolapNativeTopCountTestCases.ROLE_RESTRICTION_WORKS_WA_QUERY;
import static mondrian.rolap.RolapNativeTopCountTestCases.ROLE_RESTRICTION_WORKS_WA_ROLE_DEF;
import static mondrian.rolap.RolapNativeTopCountTestCases.ROLE_RESTRICTION_WORKS_WA_ROLE_NAME;
import static mondrian.rolap.RolapNativeTopCountTestCases.SUM_MEASURE_QUERY;
import static mondrian.rolap.RolapNativeTopCountTestCases.TOPCOUNT_MIMICS_HEAD_WHEN_TWO_PARAMS_CITIES_QUERY;
import static mondrian.rolap.RolapNativeTopCountTestCases.TOPCOUNT_MIMICS_HEAD_WHEN_TWO_PARAMS_STATES_QUERY;
import static org.opencube.junit5.TestUtil.verifySameNativeAndNot;
import static org.opencube.junit5.TestUtil.withRole;
import static org.opencube.junit5.TestUtil.withSchema;

import org.eclipse.daanse.olap.api.Connection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.opencube.junit5.ContextSource;
import org.opencube.junit5.SchemaUtil;
import org.opencube.junit5.TestUtil;
import org.opencube.junit5.context.TestingContext;
import org.opencube.junit5.dataloader.FastFoodmardDataLoader;
import org.opencube.junit5.propupdator.AppandFoodMartCatalogAsFile;

import mondrian.test.PropertySaver5;

/**
 * @author Andrey Khayrutdinov
 */
class RolapNativeTopCountVersusNonNativeTest extends BatchTestCase {

    private PropertySaver5 propSaver;

    @BeforeEach
    public void beforeEach() {
        propSaver = new PropertySaver5();
    }

    @AfterEach
    public void afterEach() {
        propSaver.reset();
    }

    private void assertResultsAreEqual(
        Connection connection,
        String testCase,
        String query)
    {
        String message = String.format(
            "[%s]: native and non-native results of the query differ. The query:\n\t\t%s",
            testCase,
            query);
        verifySameNativeAndNot(connection, query, message, propSaver);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testTopCount_ImplicitCountMeasure(TestingContext context) throws Exception {
        assertResultsAreEqual(context.createConnection(),
            "Implicit Count Measure", IMPLICIT_COUNT_MEASURE_QUERY);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testTopCount_SumMeasure(TestingContext context) throws Exception {
        assertResultsAreEqual(context.createConnection(),
            "Sum Measure", SUM_MEASURE_QUERY);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testTopCount_CountMeasure(TestingContext context) throws Exception {
        String baseSchema = TestUtil.getRawSchema(context);
        String schema = SchemaUtil.getSchema(baseSchema,null, CUSTOM_COUNT_MEASURE_CUBE, null, null, null, null);

       withSchema(context, schema);
       //withCube(CUSTOM_COUNT_MEASURE_CUBE_NAME);

        assertResultsAreEqual(context.createConnection(),
            "Custom Count Measure", CUSTOM_COUNT_MEASURE_QUERY);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testEmptyCellsAreShown_Countries(TestingContext context) throws Exception {
        assertResultsAreEqual(context.createConnection(),
            "Empty Cells Are Shown - Countries",
            EMPTY_CELLS_ARE_SHOWN_COUNTRIES_QUERY);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testEmptyCellsAreShown_States(TestingContext context) throws Exception {
        assertResultsAreEqual(context.createConnection(),
            "Empty Cells Are Shown - States",
            EMPTY_CELLS_ARE_SHOWN_STATES_QUERY);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testEmptyCellsAreShown_ButNoMoreThanReallyExist(TestingContext context) {
        assertResultsAreEqual(context.createConnection(),
            "Empty Cells Are Shown - But no more than really exist",
            EMPTY_CELLS_ARE_SHOWN_NOT_MORE_THAN_EXIST_QUERY);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testEmptyCellsAreHidden_WhenNonEmptyIsDeclaredExplicitly(TestingContext context) {
        assertResultsAreEqual(context.createConnection(),
            "Empty Cells Are Hidden - When NON EMPTY is declared explicitly",
            EMPTY_CELLS_ARE_HIDDEN_WHEN_NON_EMPTY_QUERY);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testRoleRestrictionWorks_ForRowWithData(TestingContext context) {
        String baseSchema = TestUtil.getRawSchema(context);
        String schema = SchemaUtil.getSchema(baseSchema,
                null, null, null, null, null,
                ROLE_RESTRICTION_WORKS_WA_ROLE_DEF);
        withSchema(context, schema);
        withRole(context, ROLE_RESTRICTION_WORKS_WA_ROLE_NAME);

        assertResultsAreEqual(context.createConnection(),
            "Role restriction works - For WA state",
            ROLE_RESTRICTION_WORKS_WA_QUERY);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testRoleRestrictionWorks_ForRowWithOutData(TestingContext context) {
        String baseSchema = TestUtil.getRawSchema(context);
        String schema = SchemaUtil.getSchema(baseSchema,
                null, null, null, null, null,
                ROLE_RESTRICTION_WORKS_DF_ROLE_DEF);
        withSchema(context, schema);
        withRole(context, ROLE_RESTRICTION_WORKS_DF_ROLE_NAME);

        assertResultsAreEqual(context.createConnection(),
            "Role restriction works - For DF state",
            ROLE_RESTRICTION_WORKS_DF_QUERY);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testMimicsHeadWhenTwoParams_States(TestingContext context) {
        assertResultsAreEqual(context.createConnection(),
            "Two Parameters - States",
            TOPCOUNT_MIMICS_HEAD_WHEN_TWO_PARAMS_STATES_QUERY);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testMimicsHeadWhenTwoParams_Cities(TestingContext context) {
        assertResultsAreEqual(context.createConnection(),
            "Two Parameters - Cities",
            TOPCOUNT_MIMICS_HEAD_WHEN_TWO_PARAMS_CITIES_QUERY);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testMimicsHeadWhenTwoParams_ShowsNotMoreThanExist(TestingContext context) {
        assertResultsAreEqual(context.createConnection(),
            "Two Parameters - Shows not more than really exist",
            RESULTS_ARE_SHOWN_NOT_MORE_THAN_EXIST_2_PARAMS_QUERY);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testMimicsHeadWhenTwoParams_DoesNotIgnoreNonEmpty(TestingContext context) {
        assertResultsAreEqual(context.createConnection(),
            "Two Parameters - Does not ignore NON EMPTY",
            NON_EMPTY_IS_NOT_IGNORED_WHEN_TWO_PARAMS_QUERY);
    }
}