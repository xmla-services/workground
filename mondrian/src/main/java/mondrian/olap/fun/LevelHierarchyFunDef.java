/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package mondrian.olap.fun;

import org.eclipse.daanse.calc.api.LevelCalc;
import org.eclipse.daanse.calc.base.nested.AbstractProfilingNestedHierarchyCalc;
import org.eclipse.daanse.olap.api.model.Hierarchy;
import org.eclipse.daanse.olap.api.model.Level;

import mondrian.calc.Calc;
import mondrian.calc.ExpCompiler;
import mondrian.mdx.ResolvedFunCall;
import mondrian.olap.Evaluator;
import mondrian.olap.type.Type;

/**
 * Definition of the <code>&lt;Level&gt;.Hierarchy</code> MDX builtin function.
 *
 * @author jhyde
 * @since Mar 23, 2006
 */
public class LevelHierarchyFunDef extends FunDefBase {
    static final LevelHierarchyFunDef instance = new LevelHierarchyFunDef();

    private LevelHierarchyFunDef() {
        super("Hierarchy", "Returns a level's hierarchy.", "phl");
    }

    @Override
	public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        final LevelCalc levelCalc =
                compiler.compileLevel(call.getArg(0));
        return new CalcImpl(call.getType(), levelCalc);
    }

    public static class CalcImpl extends AbstractProfilingNestedHierarchyCalc {
        private final LevelCalc levelCalc;

        public CalcImpl(Type type, LevelCalc levelCalc) {
            super("LevelHirarchy",type, new Calc[] {levelCalc});
            this.levelCalc = levelCalc;
        }

        @Override
		public Hierarchy evaluate(Evaluator evaluator) {
            Level level = levelCalc.evaluate(evaluator);
            return level.getHierarchy();
        }
    }
}
