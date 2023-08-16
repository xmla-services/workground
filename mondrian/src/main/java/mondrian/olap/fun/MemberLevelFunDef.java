/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package mondrian.olap.fun;

import org.eclipse.daanse.calc.api.MemberCalc;
import org.eclipse.daanse.calc.base.AbstractProfilingNestedLevelCalc;
import org.eclipse.daanse.olap.api.model.Level;
import org.eclipse.daanse.olap.api.model.Member;

import mondrian.calc.Calc;
import mondrian.calc.ExpCompiler;
import mondrian.mdx.ResolvedFunCall;
import mondrian.olap.Evaluator;
import mondrian.olap.Exp;
import mondrian.olap.Validator;
import mondrian.olap.type.LevelType;
import mondrian.olap.type.Type;

/**
 * Definition of the <code>&lt;Member&gt;.Level</code> MDX builtin function.
 *
 * @author jhyde
 * @since Mar 23, 2006
 */
public class MemberLevelFunDef extends FunDefBase {
    static final MemberLevelFunDef instance = new MemberLevelFunDef();

    private MemberLevelFunDef() {
        super("Level", "Returns a member's level.", "plm");
    }

    @Override
	public Type getResultType(Validator validator, Exp[] args) {
        final Type argType = args[0].getType();
        return LevelType.forType(argType);
    }

    @Override
	public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        final MemberCalc memberCalc =
                compiler.compileMember(call.getArg(0));
        return new CalcImpl(call.getType(), memberCalc);
    }

    public static class CalcImpl extends AbstractProfilingNestedLevelCalc {
        private final MemberCalc memberCalc;

        public CalcImpl(Type type, MemberCalc memberCalc) {
            super("MemberLevel",type, new Calc[] {memberCalc});
            this.memberCalc = memberCalc;
        }



		@Override
		public Level evaluate(Evaluator evaluator) {
            Member member = memberCalc.evaluate(evaluator);
            return member.getLevel();
        }
    }
}
