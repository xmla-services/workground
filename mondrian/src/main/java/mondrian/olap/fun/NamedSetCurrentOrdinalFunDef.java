/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package mondrian.olap.fun;

import mondrian.olap.interfaces.NamedSetExpr;
import org.eclipse.daanse.olap.calc.api.Calc;
import org.eclipse.daanse.olap.calc.base.nested.AbstractProfilingNestedIntegerCalc;

import mondrian.calc.ExpCompiler;
import mondrian.mdx.ResolvedFunCallImpl;
import mondrian.olap.Evaluator;
import mondrian.olap.Exp;
import mondrian.olap.Validator;
import mondrian.resource.MondrianResource;

/**
 * Definition of the <code>&lt;Named Set&gt;.CurrentOrdinal</code> MDX builtin
 * function.
 *
 * @author jhyde
 * @since Oct 19, 2008
 */
public class NamedSetCurrentOrdinalFunDef extends FunDefBase {
    static final NamedSetCurrentOrdinalFunDef instance =
        new NamedSetCurrentOrdinalFunDef();

    private NamedSetCurrentOrdinalFunDef() {
        super(
            "CurrentOrdinal",
            "Returns the ordinal of the current iteration through a named set.",
            "pix");
    }

    @Override
	public Exp createCall(Validator validator, Exp[] args) {
        assert args.length == 1;
        final Exp arg0 = args[0];
        if (!(arg0 instanceof NamedSetExpr)) {
            throw MondrianResource.instance().NotANamedSet.ex();
        }
        return super.createCall(validator, args);
    }

    @Override
	public Calc compileCall(ResolvedFunCallImpl call, ExpCompiler compiler) {
        final Exp arg0 = call.getArg(0);
        assert arg0 instanceof NamedSetExpr : "checked this in createCall";
        final NamedSetExpr namedSetExpr = (NamedSetExpr) arg0;
        return new AbstractProfilingNestedIntegerCalc(call.getType(), new Calc[0]) {
            @Override
			public Integer evaluate(Evaluator evaluator) {
                return namedSetExpr.getEval(evaluator).currentOrdinal();
            }
        };
    }
}
