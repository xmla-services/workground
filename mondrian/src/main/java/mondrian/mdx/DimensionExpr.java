/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package mondrian.mdx;

import org.eclipse.daanse.calc.api.DimensionCalc;
import org.eclipse.daanse.calc.base.constant.ConstantProfilingDimensionCalc;
import org.eclipse.daanse.olap.api.model.Dimension;

import mondrian.calc.ExpCompiler;
import mondrian.olap.Category;
import mondrian.olap.Exp;
import mondrian.olap.ExpBase;
import mondrian.olap.Util;
import mondrian.olap.Validator;
import mondrian.olap.type.DimensionType;
import mondrian.olap.type.Type;

/**
 * Usage of a {@link org.eclipse.daanse.olap.api.model.Dimension} as an MDX expression.
 *
 * @author jhyde
 * @since Sep 26, 2005
 */
public class DimensionExpr extends ExpBase implements Exp {
    private final Dimension dimension;

    /**
     * Creates a dimension expression.
     *
     * @param dimension Dimension
     * @pre dimension != null
     */
    public DimensionExpr(Dimension dimension) {
        Util.assertPrecondition(dimension != null, "dimension != null");
        this.dimension = dimension;
    }

    /**
     * Returns the dimension.
     *
     * @post return != null
     */
    public Dimension getDimension() {
        return dimension;
    }

    @Override
	public String toString() {
        return dimension.getUniqueName();
    }

    @Override
	public Type getType() {
        return DimensionType.forDimension(dimension);
    }

    @Override
	public DimensionExpr cloneExp() {
        return new DimensionExpr(dimension);
    }

    @Override
	public int getCategory() {
        return Category.DIMENSION;
    }

    @Override
	public Exp accept(Validator validator) {
        return this;
    }

    @Override
	public DimensionCalc accept(ExpCompiler compiler) {
        return ConstantProfilingDimensionCalc.of(dimension);
    }

    @Override
	public Object accept(MdxVisitor visitor) {
        return visitor.visit(this);
    }

}
