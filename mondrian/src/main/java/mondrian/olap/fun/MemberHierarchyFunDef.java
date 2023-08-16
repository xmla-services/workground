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
import org.eclipse.daanse.calc.base.nested.AbstractProfilingNestedHierarchyCalc;
import org.eclipse.daanse.olap.api.model.Hierarchy;
import org.eclipse.daanse.olap.api.model.Member;

import mondrian.calc.Calc;
import mondrian.calc.ExpCompiler;
import mondrian.mdx.ResolvedFunCall;
import mondrian.olap.Evaluator;
import mondrian.olap.type.Type;

/**
 * Definition of the <code>&lt;Member&gt;.Hierarchy</code> MDX builtin function.
 *
 * @author jhyde
 * @since Mar 23, 2006
 */
public class MemberHierarchyFunDef extends FunDefBase {
    static final MemberHierarchyFunDef instance = new MemberHierarchyFunDef();

    private MemberHierarchyFunDef() {
        super("Hierarchy", "Returns a member's hierarchy.", "phm");
    }

    @Override
	public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        final MemberCalc memberCalc =
                compiler.compileMember(call.getArg(0));
        return new MemberHirarchyCalcImpl(call.getType(), memberCalc);
    }

    public static class MemberHirarchyCalcImpl extends AbstractProfilingNestedHierarchyCalc {
        private final MemberCalc memberCalc;

        public MemberHirarchyCalcImpl(Type type, MemberCalc memberCalc) {
            super(type, new Calc[] {memberCalc});
            this.memberCalc = memberCalc;
        }

        @Override
		public Hierarchy evaluate(Evaluator evaluator) {
            Member member = memberCalc.evaluate(evaluator);
            return member.getHierarchy();
        }
    }
}
