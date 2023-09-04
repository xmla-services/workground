/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package mondrian.mdx;

import mondrian.olap.interfaces.LevelExpr;
import org.eclipse.daanse.olap.api.model.Level;
import org.eclipse.daanse.olap.calc.api.Calc;
import org.eclipse.daanse.olap.calc.base.constant.ConstantLevelCalc;

import mondrian.calc.ExpCompiler;
import mondrian.olap.Category;
import mondrian.olap.Exp;
import mondrian.olap.ExpBase;
import mondrian.olap.Util;
import mondrian.olap.Validator;
import mondrian.olap.type.LevelType;
import mondrian.olap.type.Type;

/**
 * Usage of a {@link org.eclipse.daanse.olap.api.model.Level} as an MDX expression.
 *
 * @author jhyde
 * @since Sep 26, 2005
 */
public class LevelExprImpl extends ExpBase implements Exp, LevelExpr {
    private final Level level;

    /**
     * Creates a level expression.
     *
     * @param level Level
     * @pre level != null
     */
    public LevelExprImpl(Level level) {
        Util.assertPrecondition(level != null, "level != null");
        this.level = level;
    }

    /**
     * Returns the level.
     *
     * @post return != null
     */
    @Override
    public Level getLevel() {
        return level;
    }

    @Override
	public String toString() {
        return level.getUniqueName();
    }

    @Override
	public Type getType() {
        return LevelType.forLevel(level);
    }

    @Override
	public LevelExprImpl cloneExp() {
        return new LevelExprImpl(level);
    }

    @Override
	public int getCategory() {
        return Category.LEVEL;
    }

    @Override
	public Exp accept(Validator validator) {
        return this;
    }

    @Override
	public Calc accept(ExpCompiler compiler) {
        return ConstantLevelCalc.of(level);
    }

    @Override
	public Object accept(MdxVisitor visitor) {
        return visitor.visit(this);
    }

}
