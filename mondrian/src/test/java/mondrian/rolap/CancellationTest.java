/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/
package mondrian.rolap;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.opencube.junit5.TestUtil.cubeByName;
import static org.opencube.junit5.TestUtil.executeQuery;
import static org.opencube.junit5.TestUtil.productMembersPotScrubbersPotsAndPans;

import org.eclipse.daanse.olap.api.Connection;
import org.eclipse.daanse.olap.api.result.Position;
import org.eclipse.daanse.olap.api.result.Result;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.opencube.junit5.ContextSource;
import org.opencube.junit5.context.TestingContext;
import org.opencube.junit5.dataloader.FastFoodmardDataLoader;
import org.opencube.junit5.propupdator.AppandFoodMartCatalogAsFile;

import mondrian.calc.TupleList;
import mondrian.calc.impl.UnaryTupleList;
import mondrian.mdx.ResolvedFunCall;
import mondrian.olap.Evaluator;
import mondrian.olap.FunDef;
import mondrian.olap.MondrianException;
import mondrian.olap.SchemaReader;
import mondrian.olap.fun.CrossJoinFunDef;
import mondrian.olap.fun.CrossJoinTest;
import mondrian.server.Execution;
import mondrian.server.Locus;
import mondrian.test.PropertySaver5;

public class CancellationTest {

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
     * Creates a cell region, runs a query, then flushes the cache.
     */
    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testNonEmptyListCancellation(TestingContext context) throws MondrianException {
        // tests that cancellation/timeout is checked in
        // CrossJoinFunDef.nonEmptyList
        propSaver.set(propSaver.properties.CheckCancelOrTimeoutInterval, 1);
        CrossJoinFunDefTester crossJoinFunDef =
                new CrossJoinFunDefTester(new CrossJoinTest.NullFunDef());
        Result result =
            executeQuery(context.createConnection(), "select store.[store name].members on 0 from sales");
        Evaluator eval = ((RolapResult) result).getEvaluator(new int[]{0});
        TupleList list = new UnaryTupleList();
        for (Position pos : result.getAxes()[0].getPositions()) {
            list.add(pos);
        }
        Execution exec = spy(new Execution(eval.getQuery().getStatement(), 0));
        eval.getQuery().getStatement().start(exec);
        crossJoinFunDef.nonEmptyList(eval, list, null);
        // checkCancelOrTimeout should be called once
        // for each tuple since phase interval is 1
        verify(exec, times(list.size())).checkCancelOrTimeout();
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testMutableCrossJoinCancellation(TestingContext context) throws MondrianException {
        // tests that cancellation/timeout is checked in
        // CrossJoinFunDef.mutableCrossJoin
        propSaver.set(propSaver.properties.CheckCancelOrTimeoutInterval, 1);
        Connection connection = context.createConnection();
        RolapCube salesCube = (RolapCube) cubeByName(
             connection,
            "Sales");
        SchemaReader salesCubeSchemaReader =
            salesCube.getSchemaReader(
                    connection.getRole()).withLocus();

        TupleList productMembers =
            productMembersPotScrubbersPotsAndPans(salesCubeSchemaReader);

        String selectGenders = "select Gender.members on 0 from sales";
        Result genders = executeQuery(connection, selectGenders);

        Evaluator gendersEval =
            ((RolapResult) genders).getEvaluator(new int[]{0});
        TupleList genderMembers = new UnaryTupleList();
        for (Position pos : genders.getAxes()[0].getPositions()) {
            genderMembers.add(pos);
        }

        Execution execution =
            spy(new Execution(genders.getQuery().getStatement(), 0));
        TupleList mutableCrossJoinResult =
            mutableCrossJoin(productMembers, genderMembers, execution);

        gendersEval.getQuery().getStatement().start(execution);

        // checkCancelOrTimeout should be called once
        // for each tuple from mutableCrossJoin since phase interval is 1
        // plus once for each productMembers item
        // since it gets through SqlStatement.execute
        int expectedCallsQuantity =
            mutableCrossJoinResult.size() + productMembers.size();
        verify(execution, times(expectedCallsQuantity)).checkCancelOrTimeout();
    }

    private TupleList mutableCrossJoin(
        final TupleList list1, final TupleList list2, final Execution execution)
        {
            return Locus.execute(
                execution, "CancellationTest",
                new Locus.Action<TupleList>() {
                    public TupleList execute() {
                        return CrossJoinFunDef.mutableCrossJoin(list1, list2);
                    }
                });
        }

    public class CrossJoinFunDefTester extends CrossJoinFunDef {
        public CrossJoinFunDefTester(FunDef dummyFunDef) {
            super(dummyFunDef);
        }

        public TupleList nonEmptyList(
            Evaluator evaluator,
            TupleList list,
            ResolvedFunCall call)
        {
            return super.nonEmptyList(evaluator, list, call);
        }
    }
}
