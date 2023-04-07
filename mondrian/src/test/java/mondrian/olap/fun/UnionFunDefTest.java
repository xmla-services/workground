/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (c) 2002-2017 Hitachi Vantara.  All rights reserved.
*/
package mondrian.olap.fun;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.opencube.junit5.TestUtil.assertAxisReturns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.daanse.olap.api.Connection;
import org.eclipse.daanse.olap.api.model.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Mockito;
import org.opencube.junit5.ContextSource;
import org.opencube.junit5.context.TestingContext;
import org.opencube.junit5.dataloader.FastFoodmardDataLoader;
import org.opencube.junit5.propupdator.AppandFoodMartCatalogAsFile;

import mondrian.calc.Calc;
import mondrian.calc.TupleList;
import mondrian.calc.impl.ArrayTupleList;
import mondrian.calc.impl.UnaryTupleList;
import mondrian.mdx.ResolvedFunCall;
import mondrian.olap.Exp;
import mondrian.olap.FunDef;
import mondrian.olap.type.SetType;
import mondrian.rolap.RolapMemberBase;

/**
 * Tests for UnionFunDef
 *
 * @author Yury Bakhmutski
 */
public class UnionFunDefTest {

  /**
   * Test for MONDRIAN-2250 issue.
   * Tests that the result is independent on the hashCode.
   * For this purpose MemberForTest with rewritten hashCode is used.
   *
   * Tuples are gotten from customer attachments.
   */
  @Test
  void testMondrian2250() {
    Member[] dates = new Member[4];
    for (int i = 25; i < 29; i++) {
      dates[i - 25] =
          new MemberForTest("[Consumption Date.Calendar].[2014-07-" + i + "]");
    }
    List<Member> list = Arrays.asList(dates);
    UnaryTupleList unaryTupleList = new UnaryTupleList(list);

    Member consumptionMethod =
        new MemberForTest("[Consumption Method].[PVR]");
    Member measuresAverageTimeshift =
        new MemberForTest("[Measures].[Average Timeshift]");
    String[] hours = { "00", "14", "15", "16", "23" };
    Member[] times = new Member[5];
    for (int i = 0; i < hours.length; i++) {
      times[i] =
          new MemberForTest("[Consumption Time.Time].[" + hours[i] + ":00]");
    }

    int arity = 3;
    ArrayTupleList arrayTupleList = new ArrayTupleList(arity);
    for (Member time : times) {
      List<Member> currentList = new ArrayList(3);
      currentList.add(consumptionMethod);
      currentList.add(measuresAverageTimeshift);
      currentList.add(time);
      arrayTupleList.add(currentList);
    }

    CrossJoinFunDef crossJoinFunDef =
        new CrossJoinFunDef(new CrossJoinTest.NullFunDef());
    Exp[] expMock = new Exp[1];
    expMock[0] = mock(Exp.class);
    ResolvedFunCall resolvedFunCall =
        new ResolvedFunCall(mock(FunDef.class), expMock, mock(SetType.class));
    Calc[] calcs = new Calc[1];
    calcs[0] = Mockito.mock(Calc.class);
    CrossJoinFunDef.ImmutableListCalc immutableListCalc =
        crossJoinFunDef.new ImmutableListCalc(
            resolvedFunCall, calcs);

    TupleList listForUnion1 =
        immutableListCalc.makeList(unaryTupleList, arrayTupleList);

    List<Member> list2 = Arrays.asList(dates);
    UnaryTupleList unaryTupleList2 = new UnaryTupleList(list2);

    Member measuresTotalViewingTime =
        new MemberForTest("[Measures].[Total Viewing Time]");
    ArrayTupleList arrayTupleList2 = new ArrayTupleList(arity);
    for (Member time : times) {
      List<Member> currentList = new ArrayList(3);
      currentList.add(consumptionMethod);
      currentList.add(measuresTotalViewingTime);
      currentList.add(time);
      arrayTupleList2.add(currentList);
    }

    TupleList listForUnion2 =
        immutableListCalc.makeList(unaryTupleList2, arrayTupleList2);

    UnionFunDef unionFunDefMock = mock(UnionFunDef.class);
    doCallRealMethod().when(unionFunDefMock).union(
    		any(), any(), anyBoolean());

    TupleList tupleList =
        unionFunDefMock.union(listForUnion1, listForUnion2, false);
    System.out.println(tupleList);
    assertEquals(40, tupleList.size());
  }

  @ParameterizedTest
  @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
  void testArity4TupleUnion(TestingContext context) {
    String tupleSet =
        "CrossJoin( [Customers].[USA].Children,"
        + " CrossJoin( Time.[1997].children, { (Gender.F, [Marital Status].M ) }) ) ";
    String expected =
        "{[Customers].[USA].[CA], [Time].[1997].[Q1], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[USA].[CA], [Time].[1997].[Q2], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[USA].[CA], [Time].[1997].[Q3], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[USA].[CA], [Time].[1997].[Q4], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[USA].[OR], [Time].[1997].[Q1], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[USA].[OR], [Time].[1997].[Q2], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[USA].[OR], [Time].[1997].[Q3], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[USA].[OR], [Time].[1997].[Q4], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[USA].[WA], [Time].[1997].[Q1], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[USA].[WA], [Time].[1997].[Q2], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[USA].[WA], [Time].[1997].[Q3], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[USA].[WA], [Time].[1997].[Q4], [Gender].[F], [Marital Status].[M]}";

    assertAxisReturns(context.createConnection(), "Union( " + tupleSet + ", " + tupleSet + ")", expected);
  }

  @ParameterizedTest
  @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
  void testArity5TupleUnion(TestingContext context) {
    String tupleSet = "CrossJoin( [Customers].[Canada].Children, "
        + "CrossJoin( [Time].[1997].lastChild, "
        + "CrossJoin ([Education Level].children,{ (Gender.F, [Marital Status].M ) })) )";
    String expected =
        "{[Customers].[Canada].[BC], [Time].[1997].[Q4], [Education Level].[Bachelors Degree], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[Canada].[BC], [Time].[1997].[Q4], [Education Level].[Graduate Degree], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[Canada].[BC], [Time].[1997].[Q4], [Education Level].[High School Degree], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[Canada].[BC], [Time].[1997].[Q4], [Education Level].[Partial College], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[Canada].[BC], [Time].[1997].[Q4], [Education Level].[Partial High School], [Gender].[F], [Marital Status].[M]}";
    Connection connection = context.createConnection();
    assertAxisReturns(connection, tupleSet, expected);

    assertAxisReturns(connection, "Union( " + tupleSet + ", " + tupleSet + ")", expected);
  }

  void testArity5TupleUnionAll(TestingContext context) {
    String tupleSet = "CrossJoin( [Customers].[Canada].Children, "
        + "CrossJoin( [Time].[1998].firstChild, "
        + "CrossJoin ([Education Level].members,{ (Gender.F, [Marital Status].M ) })) )";
    String expected =
        "{[Customers].[Canada].[BC], [Time].[1998].[Q1], [Education Level].[All Education Levels], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[Canada].[BC], [Time].[1998].[Q1], [Education Level].[Bachelors Degree], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[Canada].[BC], [Time].[1998].[Q1], [Education Level].[Graduate Degree], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[Canada].[BC], [Time].[1998].[Q1], [Education Level].[High School Degree], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[Canada].[BC], [Time].[1998].[Q1], [Education Level].[Partial College], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[Canada].[BC], [Time].[1998].[Q1], [Education Level].[Partial High School], [Gender].[F], [Marital Status].[M]}";

    Connection connection = context.createConnection();
    assertAxisReturns(connection, tupleSet, expected);

    assertAxisReturns(connection,
        "Union( " + tupleSet + ", " + tupleSet + ", " + "ALL" + ")",
        expected + "\n" + expected);
  }

  @ParameterizedTest
  @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
  void testArity6TupleUnion(TestingContext context) {
    String tupleSet1 = "CrossJoin( [Customers].[Canada].Children, "
        + "CrossJoin( [Time].[1997].firstChild, "
        + "CrossJoin ([Education Level].lastChild,"
        + "CrossJoin ([Yearly Income].lastChild,"
        + "{ (Gender.F, [Marital Status].M ) })) ) )";
    String tupleSet2 = "CrossJoin( [Customers].[Canada].Children, "
        + "CrossJoin( [Time].[1997].firstChild, "
        + "CrossJoin ([Education Level].lastChild,"
        + "CrossJoin ([Yearly Income].children,"
        + "{ (Gender.F, [Marital Status].M ) })) ) )";

    String tupleSet1Expected =
        "{[Customers].[Canada].[BC], [Time].[1997].[Q1], [Education Level].[Partial High School], [Yearly Income].[$90K - $110K], [Gender].[F], [Marital Status].[M]}";
    Connection connection = context.createConnection();
    assertAxisReturns(connection, tupleSet1, tupleSet1Expected);

    String tupleSet2Expected =
        "{[Customers].[Canada].[BC], [Time].[1997].[Q1], [Education Level].[Partial High School], [Yearly Income].[$10K - $30K], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[Canada].[BC], [Time].[1997].[Q1], [Education Level].[Partial High School], [Yearly Income].[$110K - $130K], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[Canada].[BC], [Time].[1997].[Q1], [Education Level].[Partial High School], [Yearly Income].[$130K - $150K], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[Canada].[BC], [Time].[1997].[Q1], [Education Level].[Partial High School], [Yearly Income].[$150K +], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[Canada].[BC], [Time].[1997].[Q1], [Education Level].[Partial High School], [Yearly Income].[$30K - $50K], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[Canada].[BC], [Time].[1997].[Q1], [Education Level].[Partial High School], [Yearly Income].[$50K - $70K], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[Canada].[BC], [Time].[1997].[Q1], [Education Level].[Partial High School], [Yearly Income].[$70K - $90K], [Gender].[F], [Marital Status].[M]}\n"
        + tupleSet1Expected;

    assertAxisReturns(connection, tupleSet2, tupleSet2Expected);

    assertAxisReturns(connection,
        "Union( " + tupleSet2 + ", " + tupleSet1 + ")",
        tupleSet2Expected);
  }

  @ParameterizedTest
  @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
  void testArity6TupleUnionAll(TestingContext context) {
    String tupleSet1 = "CrossJoin( [Customers].[Canada].Children, "
        + "CrossJoin( [Time].[1997].firstChild, "
        + "CrossJoin ([Education Level].lastChild,"
        + "CrossJoin ([Yearly Income].lastChild,"
        + "{ (Gender.F, [Marital Status].M ) })) ) )";
    String tupleSet2 = "CrossJoin( [Customers].[Canada].Children, "
        + "CrossJoin( [Time].[1997].firstChild, "
        + "CrossJoin ([Education Level].lastChild,"
        + "CrossJoin ([Yearly Income].children,"
        + "{ (Gender.F, [Marital Status].M ) })) ) )";

    String tupleSet1Expected =
        "{[Customers].[Canada].[BC], [Time].[1997].[Q1], [Education Level].[Partial High School], [Yearly Income].[$90K - $110K], [Gender].[F], [Marital Status].[M]}";
    Connection connection = context.createConnection();
    assertAxisReturns(connection, tupleSet1, tupleSet1Expected);

    String tupleSet2Expected =
        "{[Customers].[Canada].[BC], [Time].[1997].[Q1], [Education Level].[Partial High School], [Yearly Income].[$10K - $30K], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[Canada].[BC], [Time].[1997].[Q1], [Education Level].[Partial High School], [Yearly Income].[$110K - $130K], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[Canada].[BC], [Time].[1997].[Q1], [Education Level].[Partial High School], [Yearly Income].[$130K - $150K], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[Canada].[BC], [Time].[1997].[Q1], [Education Level].[Partial High School], [Yearly Income].[$150K +], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[Canada].[BC], [Time].[1997].[Q1], [Education Level].[Partial High School], [Yearly Income].[$30K - $50K], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[Canada].[BC], [Time].[1997].[Q1], [Education Level].[Partial High School], [Yearly Income].[$50K - $70K], [Gender].[F], [Marital Status].[M]}\n"
        + "{[Customers].[Canada].[BC], [Time].[1997].[Q1], [Education Level].[Partial High School], [Yearly Income].[$70K - $90K], [Gender].[F], [Marital Status].[M]}\n"
        + tupleSet1Expected;
    assertAxisReturns(connection, tupleSet2, tupleSet2Expected);

    assertAxisReturns(connection,
            "Union( " + tupleSet1 + ", " + tupleSet2 + ", " + "ALL" + ")",
        tupleSet1Expected + "\n" + tupleSet2Expected);
  }

  private class MemberForTest extends RolapMemberBase {
    private String identifer;

    public MemberForTest(String identifer) {
      this.identifer = identifer;
    }

    @Override
    public String getUniqueName() {
      return identifer;
    }

    @Override
    public int hashCode() {
      return 31;
    }
  }
}
