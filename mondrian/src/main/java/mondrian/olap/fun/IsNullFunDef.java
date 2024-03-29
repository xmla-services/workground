/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (c) 2002-2018 Hitachi Vantara..  All rights reserved.
*/
package mondrian.olap.fun;

import org.eclipse.daanse.olap.api.model.Member;

import mondrian.calc.Calc;
import mondrian.calc.ExpCompiler;
import mondrian.calc.MemberCalc;
import mondrian.calc.impl.AbstractBooleanCalc;
import mondrian.mdx.ResolvedFunCall;
import mondrian.olap.Evaluator;
import mondrian.olap.FunDef;
import mondrian.rolap.RolapMember;
import mondrian.rolap.RolapUtil;

/**
 * Definition of the <code>IS NULL</code> MDX function.
 *
 * @author medstat
 * @since Aug 21, 2006
 */
class IsNullFunDef extends FunDefBase {
    /**
     * Resolves calls to the <code>IS NULL</code> postfix operator.
     */
    static final ReflectiveMultiResolver Resolver =
        new ReflectiveMultiResolver(
            "IS NULL",
            "<Expression> IS NULL",
            "Returns whether an object is null",
            new String[]{"Qbm", "Qbl", "Qbh", "Qbd"},
            IsNullFunDef.class);

    public IsNullFunDef(FunDef dummyFunDef) {
        super(dummyFunDef);
    }

    @Override
	public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {

        if (call.getArgCount() != 1) {
            throw new IllegalArgumentException("ArgCount should be 1 ");
        }
        final MemberCalc memberCalc = compiler.compileMember(call.getArg(0));
        return new AbstractBooleanCalc(call.getFunName(),call.getType(), new Calc[]{memberCalc}) {
            @Override
			public boolean evaluateBoolean(Evaluator evaluator) {
                Member member = memberCalc.evaluateMember(evaluator);
                return member.isNull()
                   || nonAllWithNullKey((RolapMember) member);
      }
    };
  }

  /**
   * Dimension members with a null value are treated as the null member.
   */
  private boolean nonAllWithNullKey(RolapMember member) {
    return !member.isAll() && member.getKey() == RolapUtil.sqlNullValue;
  }
}
