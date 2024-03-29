/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package mondrian.mdx;

import org.eclipse.daanse.olap.api.model.Level;

import mondrian.calc.Calc;
import mondrian.calc.ExpCompiler;
import mondrian.calc.impl.ConstantCalc;
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
public class LevelExpr extends ExpBase implements Exp {
    private final Level level;

    /**
     * Creates a level expression.
     *
     * @param level Level
     * @pre level != null
     */
    public LevelExpr(Level level) {
        Util.assertPrecondition(level != null, "level != null");
        this.level = level;
    }

    /**
     * Returns the level.
     *
     * @post return != null
     */
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
	public LevelExpr cloneExp() {
        return new LevelExpr(level);
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
        return ConstantCalc.constantLevel(level);
    }

    @Override
	public Object accept(MdxVisitor visitor) {
        return visitor.visit(this);
    }

}
