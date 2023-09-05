/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package mondrian.olap.fun;

import mondrian.olap.interfaces.DimensionExpr;
import org.eclipse.daanse.olap.api.model.Dimension;
import org.eclipse.daanse.olap.calc.api.Calc;
import org.eclipse.daanse.olap.calc.base.constant.ConstantDimensionCalc;

import mondrian.calc.ExpCompiler;
import mondrian.mdx.ResolvedFunCallImpl;

/**
 * Definition of the <code>&lt;Dimension&gt;.Dimension</code>
 * MDX builtin function.
 *
 * @author jhyde
 * @since Jul 20, 2009
 */
class DimensionDimensionFunDef extends FunDefBase {
    public static final FunDefBase INSTANCE = new DimensionDimensionFunDef();

    private DimensionDimensionFunDef() {
        super(
            "Dimension",
            "Returns the dimension that contains a specified hierarchy.",
            "pdd");
    }

    @Override
	public Calc compileCall(ResolvedFunCallImpl call, ExpCompiler compiler)
    {
        Dimension dimension =
            ((DimensionExpr) call.getArg(0)).getDimension();
        return ConstantDimensionCalc.of(dimension);
    }
}
