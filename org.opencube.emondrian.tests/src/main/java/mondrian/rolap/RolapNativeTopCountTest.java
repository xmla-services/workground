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

import mondrian.test.PropertySaver5;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.opencube.junit5.ContextSource;
import org.opencube.junit5.SchemaUtil;
import org.opencube.junit5.TestUtil;
import org.opencube.junit5.context.Context;
import org.opencube.junit5.dataloader.FastFoodmardDataLoader;
import org.opencube.junit5.propupdator.AppandFoodMartCatalogAsFile;

import static mondrian.rolap.RolapNativeTopCountTestCases.*;
import static org.opencube.junit5.TestUtil.assertQueryReturns;
import static org.opencube.junit5.TestUtil.withRole;
import static org.opencube.junit5.TestUtil.withSchema;

/**
 * @author Andrey Khayrutdinov
 * @see RolapNativeTopCountTestCases
 */
public class RolapNativeTopCountTest extends BatchTestCase {

    private PropertySaver5 propSaver;
    @BeforeEach
    public void beforeEach() {
        propSaver = new PropertySaver5();
        propSaver.set(propSaver.properties.EnableNativeTopCount, true);
    }

    @AfterEach
    public void afterEach() {
        propSaver.reset();
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    public void testTopCount_ImplicitCountMeasure(Context context) throws Exception {
        assertQueryReturns(context.createConnection(),
            IMPLICIT_COUNT_MEASURE_QUERY, IMPLICIT_COUNT_MEASURE_RESULT);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    public void testTopCount_CountMeasure(Context context) throws Exception {

        String baseSchema = TestUtil.getRawSchema(context);
        String schema = SchemaUtil.getSchema(baseSchema,
                null, CUSTOM_COUNT_MEASURE_CUBE,
                    null, null, null, null);


        withSchema(context, schema);
        //withCube(CUSTOM_COUNT_MEASURE_CUBE_NAME);

        assertQueryReturns(context.createConnection(),
            CUSTOM_COUNT_MEASURE_QUERY, CUSTOM_COUNT_MEASURE_RESULT);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    public void testTopCount_SumMeasure(Context context) throws Exception {
        assertQueryReturns(context.createConnection(), SUM_MEASURE_QUERY, SUM_MEASURE_RESULT);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    public void testEmptyCellsAreShown_Countries(Context context) {
        assertQueryReturns(context.createConnection(),
            EMPTY_CELLS_ARE_SHOWN_COUNTRIES_QUERY,
            EMPTY_CELLS_ARE_SHOWN_COUNTRIES_RESULT);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    public void testEmptyCellsAreShown_States(Context context) {
        assertQueryReturns(context.createConnection(),
            EMPTY_CELLS_ARE_SHOWN_STATES_QUERY,
            EMPTY_CELLS_ARE_SHOWN_STATES_RESULT);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    public void testEmptyCellsAreShown_ButNoMoreThanReallyExist(Context context) {
        assertQueryReturns(context.createConnection(),
            EMPTY_CELLS_ARE_SHOWN_NOT_MORE_THAN_EXIST_QUERY,
            EMPTY_CELLS_ARE_SHOWN_NOT_MORE_THAN_EXIST_RESULT);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    public void testEmptyCellsAreHidden_WhenNonEmptyIsDeclaredExplicitly(Context context) {
        assertQueryReturns(context.createConnection(),
            EMPTY_CELLS_ARE_HIDDEN_WHEN_NON_EMPTY_QUERY,
            EMPTY_CELLS_ARE_HIDDEN_WHEN_NON_EMPTY_RESULT);
    }


    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    public void testRoleRestrictionWorks_ForRowWithData(Context context) throws Exception {
        String baseSchema = TestUtil.getRawSchema(context);
        String schema = SchemaUtil.getSchema(baseSchema,
                null, null, null, null, null,
                ROLE_RESTRICTION_WORKS_WA_ROLE_DEF);
        withSchema(context, schema);
        withRole(context, ROLE_RESTRICTION_WORKS_WA_ROLE_NAME);

        assertQueryReturns(context.createConnection(),
            ROLE_RESTRICTION_WORKS_WA_QUERY,
            ROLE_RESTRICTION_WORKS_WA_RESULT);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    public void testRoleRestrictionWorks_ForRowWithOutData(Context context) throws Exception {
        String baseSchema = TestUtil.getRawSchema(context);
        String schema = SchemaUtil.getSchema(baseSchema,
                null, null, null, null, null,
                ROLE_RESTRICTION_WORKS_DF_ROLE_DEF);
        withSchema(context, schema);
        withRole(context, ROLE_RESTRICTION_WORKS_DF_ROLE_NAME);

        assertQueryReturns(context.createConnection(),
            ROLE_RESTRICTION_WORKS_DF_QUERY,
            ROLE_RESTRICTION_WORKS_DF_RESULT);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    public void testMimicsHeadWhenTwoParams_States(Context context) {
        assertQueryReturns(context.createConnection(),
            TOPCOUNT_MIMICS_HEAD_WHEN_TWO_PARAMS_STATES_QUERY,
            TOPCOUNT_MIMICS_HEAD_WHEN_TWO_PARAMS_STATES_RESULT);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    public void testMimicsHeadWhenTwoParams_Cities(Context context) {
        assertQueryReturns(context.createConnection(),
            TOPCOUNT_MIMICS_HEAD_WHEN_TWO_PARAMS_CITIES_QUERY,
            TOPCOUNT_MIMICS_HEAD_WHEN_TWO_PARAMS_CITIES_RESULT);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    public void testMimicsHeadWhenTwoParams_ShowsNotMoreThanExist(Context context) {
        assertQueryReturns(context.createConnection(),
            RESULTS_ARE_SHOWN_NOT_MORE_THAN_EXIST_2_PARAMS_QUERY,
            RESULTS_ARE_SHOWN_NOT_MORE_THAN_EXIST_2_PARAMS_RESULT);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    public void testMimicsHeadWhenTwoParams_DoesNotIgnoreNonEmpty(Context context) {
        assertQueryReturns(context.createConnection(),
            NON_EMPTY_IS_NOT_IGNORED_WHEN_TWO_PARAMS_QUERY,
            NON_EMPTY_IS_NOT_IGNORED_WHEN_TWO_PARAMS_RESULT);
    }
}

// End RolapNativeTopCountTest.java
