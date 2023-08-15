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
import org.eclipse.daanse.calc.impl.AbstractProfilingNestedStringCalc;
import org.eclipse.daanse.olap.api.model.Member;

import mondrian.calc.Calc;
import mondrian.calc.ExpCompiler;
import mondrian.calc.TupleCalc;
import mondrian.mdx.ResolvedFunCall;
import mondrian.olap.Evaluator;
import mondrian.olap.type.TypeUtil;

/**
 * Definition of the <code>TupleToStr</code> MDX function.
 *
 * <p>Syntax:
 * <blockquote><code>
 * TupleToStr(&lt;Tuple&gt;)
 * </code></blockquote>
 *
 * @author jhyde
 * @since Aug 3, 2006
 */
class TupleToStrFunDef extends FunDefBase {
    static final TupleToStrFunDef instance = new TupleToStrFunDef();

    private TupleToStrFunDef() {
        super("TupleToStr", "Constructs a string from a tuple.", "fSt");
    }

    @Override
	public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        if (TypeUtil.couldBeMember(call.getArg(0).getType())) {
            final MemberCalc memberCalc =
                    compiler.compileMember(call.getArg(0));
            return new AbstractProfilingNestedStringCalc(call.getFunName(),call.getType(), new Calc[] {memberCalc}) {
                @Override
				public String evaluate(Evaluator evaluator) {
                    final Member member =
                            memberCalc.evaluate(evaluator);
                    if (member.isNull()) {
                        return "";
                    }
                    StringBuilder buf = new StringBuilder();
                    buf.append(member.getUniqueName());
                    return buf.toString();
                }
            };
        } else {
            final TupleCalc tupleCalc =
                    compiler.compileTuple(call.getArg(0));
            return new AbstractProfilingNestedStringCalc(call.getFunName(),call.getType(), new Calc[] {tupleCalc}) {
                @Override
				public String evaluate(Evaluator evaluator) {
                    final Member[] members =
                            tupleCalc.evaluateTuple(evaluator);
                    if (members == null) {
                        return "";
                    }
                    StringBuilder buf = new StringBuilder();
                    FunUtil.appendTuple(buf, members);
                    return buf.toString();
                }
            };
        }
    }

}
