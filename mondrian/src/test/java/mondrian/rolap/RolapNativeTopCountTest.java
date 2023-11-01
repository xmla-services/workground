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
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.opencube.junit5.ContextSource;
import org.opencube.junit5.context.TestContext;
import org.opencube.junit5.context.TestContextWrapper;
import org.opencube.junit5.dataloader.FastFoodmardDataLoader;
import org.opencube.junit5.propupdator.AppandFoodMartCatalogAsFile;

import java.util.List;

import static mondrian.rolap.RolapNativeTopCountTestCases.CUSTOM_COUNT_MEASURE_QUERY;
import static mondrian.rolap.RolapNativeTopCountTestCases.CUSTOM_COUNT_MEASURE_RESULT;
import static mondrian.rolap.RolapNativeTopCountTestCases.EMPTY_CELLS_ARE_HIDDEN_WHEN_NON_EMPTY_QUERY;
import static mondrian.rolap.RolapNativeTopCountTestCases.EMPTY_CELLS_ARE_HIDDEN_WHEN_NON_EMPTY_RESULT;
import static mondrian.rolap.RolapNativeTopCountTestCases.EMPTY_CELLS_ARE_SHOWN_COUNTRIES_QUERY;
import static mondrian.rolap.RolapNativeTopCountTestCases.EMPTY_CELLS_ARE_SHOWN_COUNTRIES_RESULT;
import static mondrian.rolap.RolapNativeTopCountTestCases.EMPTY_CELLS_ARE_SHOWN_NOT_MORE_THAN_EXIST_QUERY;
import static mondrian.rolap.RolapNativeTopCountTestCases.EMPTY_CELLS_ARE_SHOWN_NOT_MORE_THAN_EXIST_RESULT;
import static mondrian.rolap.RolapNativeTopCountTestCases.EMPTY_CELLS_ARE_SHOWN_STATES_QUERY;
import static mondrian.rolap.RolapNativeTopCountTestCases.EMPTY_CELLS_ARE_SHOWN_STATES_RESULT;
import static mondrian.rolap.RolapNativeTopCountTestCases.IMPLICIT_COUNT_MEASURE_QUERY;
import static mondrian.rolap.RolapNativeTopCountTestCases.IMPLICIT_COUNT_MEASURE_RESULT;
import static mondrian.rolap.RolapNativeTopCountTestCases.NON_EMPTY_IS_NOT_IGNORED_WHEN_TWO_PARAMS_QUERY;
import static mondrian.rolap.RolapNativeTopCountTestCases.NON_EMPTY_IS_NOT_IGNORED_WHEN_TWO_PARAMS_RESULT;
import static mondrian.rolap.RolapNativeTopCountTestCases.RESULTS_ARE_SHOWN_NOT_MORE_THAN_EXIST_2_PARAMS_QUERY;
import static mondrian.rolap.RolapNativeTopCountTestCases.RESULTS_ARE_SHOWN_NOT_MORE_THAN_EXIST_2_PARAMS_RESULT;
import static mondrian.rolap.RolapNativeTopCountTestCases.ROLE_RESTRICTION_WORKS_DF_QUERY;
import static mondrian.rolap.RolapNativeTopCountTestCases.ROLE_RESTRICTION_WORKS_DF_RESULT;
import static mondrian.rolap.RolapNativeTopCountTestCases.ROLE_RESTRICTION_WORKS_DF_ROLE_NAME;
import static mondrian.rolap.RolapNativeTopCountTestCases.ROLE_RESTRICTION_WORKS_WA_QUERY;
import static mondrian.rolap.RolapNativeTopCountTestCases.ROLE_RESTRICTION_WORKS_WA_RESULT;
import static mondrian.rolap.RolapNativeTopCountTestCases.ROLE_RESTRICTION_WORKS_WA_ROLE_NAME;
import static mondrian.rolap.RolapNativeTopCountTestCases.SUM_MEASURE_QUERY;
import static mondrian.rolap.RolapNativeTopCountTestCases.SUM_MEASURE_RESULT;
import static mondrian.rolap.RolapNativeTopCountTestCases.TOPCOUNT_MIMICS_HEAD_WHEN_TWO_PARAMS_CITIES_QUERY;
import static mondrian.rolap.RolapNativeTopCountTestCases.TOPCOUNT_MIMICS_HEAD_WHEN_TWO_PARAMS_CITIES_RESULT;
import static mondrian.rolap.RolapNativeTopCountTestCases.TOPCOUNT_MIMICS_HEAD_WHEN_TWO_PARAMS_STATES_QUERY;
import static mondrian.rolap.RolapNativeTopCountTestCases.TOPCOUNT_MIMICS_HEAD_WHEN_TWO_PARAMS_STATES_RESULT;
import static org.opencube.junit5.TestUtil.assertQueryReturns;
import static org.opencube.junit5.TestUtil.withRole;
import static org.opencube.junit5.TestUtil.withSchema;

/**
 * @author Andrey Khayrutdinov
 * @see RolapNativeTopCountTestCases
 */
class RolapNativeTopCountTest extends BatchTestCase {

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
    void testTopCount_ImplicitCountMeasure(TestContextWrapper context) throws Exception {
        assertQueryReturns(context.createConnection(),
            IMPLICIT_COUNT_MEASURE_QUERY, IMPLICIT_COUNT_MEASURE_RESULT);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testTopCount_CountMeasure(TestContext context) throws Exception {
        /*
        String baseSchema = TestUtil.getRawSchema(context);
        String schema = SchemaUtil.getSchema(baseSchema,
                null, CUSTOM_COUNT_MEASURE_CUBE,
                    null, null, null, null);

        withSchema(context, schema);
        //withCube(CUSTOM_COUNT_MEASURE_CUBE_NAME);
         */
        withSchema(context, SchemaModifiers.CustomCountMeasureCubeName::new);
        assertQueryReturns(context.getConnection(),
            CUSTOM_COUNT_MEASURE_QUERY, CUSTOM_COUNT_MEASURE_RESULT);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testTopCount_SumMeasure(TestContextWrapper context) throws Exception {
        assertQueryReturns(context.createConnection(), SUM_MEASURE_QUERY, SUM_MEASURE_RESULT);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testEmptyCellsAreShown_Countries(TestContextWrapper context) {
        assertQueryReturns(context.createConnection(),
            EMPTY_CELLS_ARE_SHOWN_COUNTRIES_QUERY,
            EMPTY_CELLS_ARE_SHOWN_COUNTRIES_RESULT);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testEmptyCellsAreShown_States(TestContextWrapper context) {
        assertQueryReturns(context.createConnection(),
            EMPTY_CELLS_ARE_SHOWN_STATES_QUERY,
            EMPTY_CELLS_ARE_SHOWN_STATES_RESULT);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testEmptyCellsAreShown_ButNoMoreThanReallyExist(TestContextWrapper context) {
        assertQueryReturns(context.createConnection(),
            EMPTY_CELLS_ARE_SHOWN_NOT_MORE_THAN_EXIST_QUERY,
            EMPTY_CELLS_ARE_SHOWN_NOT_MORE_THAN_EXIST_RESULT);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testEmptyCellsAreHidden_WhenNonEmptyIsDeclaredExplicitly(TestContextWrapper context) {
        assertQueryReturns(context.createConnection(),
            EMPTY_CELLS_ARE_HIDDEN_WHEN_NON_EMPTY_QUERY,
            EMPTY_CELLS_ARE_HIDDEN_WHEN_NON_EMPTY_RESULT);
    }


    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testRoleRestrictionWorks_ForRowWithData(TestContextWrapper context) throws Exception {
        RolapSchemaPool.instance().clear();
        /*
        String baseSchema = TestUtil.getRawSchema(context);
        String schema = SchemaUtil.getSchema(baseSchema,
                null, null, null, null, null,
                ROLE_RESTRICTION_WORKS_WA_ROLE_DEF);
        withSchema(context, schema);
         */
        withRole(context, ROLE_RESTRICTION_WORKS_WA_ROLE_NAME);
        MappingSchema schema = context.getContext().getDatabaseMappingSchemaProviders().get(0).get();
        context.getContext().setDatabaseMappingSchemaProviders(
            List.of(new SchemaModifiers.RoleRestrictionWorksWaRoleDef(schema)));
        assertQueryReturns(context.createConnection(),
            ROLE_RESTRICTION_WORKS_WA_QUERY,
            ROLE_RESTRICTION_WORKS_WA_RESULT);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testRoleRestrictionWorks_ForRowWithOutData(TestContextWrapper context) throws Exception {
        RolapSchemaPool.instance().clear();
        /*
        String baseSchema = TestUtil.getRawSchema(context);
        String schema = SchemaUtil.getSchema(baseSchema,
                null, null, null, null, null,
                ROLE_RESTRICTION_WORKS_DF_ROLE_DEF);
        withSchema(context, schema);
         */
        withRole(context, ROLE_RESTRICTION_WORKS_DF_ROLE_NAME);
        MappingSchema schema = context.getContext().getDatabaseMappingSchemaProviders().get(0).get();
        context.getContext().setDatabaseMappingSchemaProviders(
            List.of(new SchemaModifiers.RoleRestrictionWorksDfRoleDef(schema)));
        assertQueryReturns(context.createConnection(),
            ROLE_RESTRICTION_WORKS_DF_QUERY,
            ROLE_RESTRICTION_WORKS_DF_RESULT);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testMimicsHeadWhenTwoParams_States(TestContextWrapper context) {
        assertQueryReturns(context.createConnection(),
            TOPCOUNT_MIMICS_HEAD_WHEN_TWO_PARAMS_STATES_QUERY,
            TOPCOUNT_MIMICS_HEAD_WHEN_TWO_PARAMS_STATES_RESULT);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testMimicsHeadWhenTwoParams_Cities(TestContextWrapper context) {
        assertQueryReturns(context.createConnection(),
            TOPCOUNT_MIMICS_HEAD_WHEN_TWO_PARAMS_CITIES_QUERY,
            TOPCOUNT_MIMICS_HEAD_WHEN_TWO_PARAMS_CITIES_RESULT);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testMimicsHeadWhenTwoParams_ShowsNotMoreThanExist(TestContextWrapper context) {
        assertQueryReturns(context.createConnection(),
            RESULTS_ARE_SHOWN_NOT_MORE_THAN_EXIST_2_PARAMS_QUERY,
            RESULTS_ARE_SHOWN_NOT_MORE_THAN_EXIST_2_PARAMS_RESULT);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testMimicsHeadWhenTwoParams_DoesNotIgnoreNonEmpty(TestContextWrapper context) {
        assertQueryReturns(context.createConnection(),
            NON_EMPTY_IS_NOT_IGNORED_WHEN_TWO_PARAMS_QUERY,
            NON_EMPTY_IS_NOT_IGNORED_WHEN_TWO_PARAMS_RESULT);
    }
}
