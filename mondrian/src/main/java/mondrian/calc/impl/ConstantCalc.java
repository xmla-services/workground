/*
 * This software is subject to the terms of the Eclipse Public License v1.0
 * Agreement, available at the following URL:
 * http://www.eclipse.org/legal/epl-v10.html.
 * You must accept the terms of that agreement to use this software.
 *
 * Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
 */

package mondrian.calc.impl;

import org.eclipse.daanse.olap.api.model.Hierarchy;
import org.eclipse.daanse.olap.api.model.Level;
import org.eclipse.daanse.olap.api.model.Member;

import mondrian.calc.Calc;
import mondrian.calc.ResultStyle;
import mondrian.olap.Evaluator;
import mondrian.olap.type.HierarchyType;
import mondrian.olap.type.LevelType;
import mondrian.olap.type.MemberType;
import mondrian.olap.type.Type;

/**
 * Calculator which always returns the same value.
 *
 * @author jhyde
 * @since Sep 27, 2005
 */

//TODO: remove  and use just the new interface org.eclipse.daanse.calc.api.ConstantCalc
public class ConstantCalc extends GenericCalc  implements org.eclipse.daanse.calc.api.ConstantCalc<Object>{
    private final Object o;

    public ConstantCalc( Type type, Object o) {
        super("ConstantCalc",type);
        this.o = o;
  
    }

    @Override
    public String getName() {
        return "Literal";
    }

    @Override
    public ResultStyle getResultStyle() {
        return o == null
                ? ResultStyle.VALUE
                        : ResultStyle.VALUE_NOT_NULL;
    }

    

    @Override
    public Object evaluate(Evaluator evaluator) {
        return o;
    }




    @Override
    public boolean dependsOn(Hierarchy hierarchy) {
        // A constant -- including a catalog element -- will evaluate to the
        // same result regardless of the evaluation context. For example, the
        // member [Gender].[M] does not 'depend on' the [Gender] dimension.
        return false;
    }

    @Override
    public Calc[] getChildCalcs() {
        return new Calc[0];
    }

    /**
     * Creates an expression which evaluates to null.
     *
     * @param type Type
     * @return Constant null expression
     */
    public static ConstantCalc constantNull(Type type) {
        return new ConstantCalc(type, null);
    }

    /**
     * Creates an expression which evaluates to a given member.
     *
     * @param member Member
     * @return Constant member expression
     */
    public static Calc constantMember(Member member) {
        return new ConstantCalc(
                MemberType.forMember(member),
                member);
    }

    /**
     * Creates an expression which evaluates to a given level.
     *
     * @param level Level
     * @return Constant level expression
     */
    public static Calc constantLevel(Level level) {
        return new ConstantCalc(
                LevelType.forLevel(level),
                level);
    }

}
