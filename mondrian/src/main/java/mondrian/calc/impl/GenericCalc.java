/*
 * This software is subject to the terms of the Eclipse Public License v1.0
 * Agreement, available at the following URL:
 * http://www.eclipse.org/legal/epl-v10.html.
 * You must accept the terms of that agreement to use this software.
 *
 * Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
 */

package mondrian.calc.impl;

import java.util.Date;

import org.eclipse.daanse.calc.api.HierarchyCalc;
import org.eclipse.daanse.calc.api.LevelCalc;
import org.eclipse.daanse.calc.impl.AbstractProfilingNestedCalc;
import org.eclipse.daanse.olap.api.model.Dimension;
import org.eclipse.daanse.olap.api.model.Hierarchy;
import org.eclipse.daanse.olap.api.model.Level;
import org.eclipse.daanse.olap.api.model.Member;

import mondrian.calc.Calc;
import mondrian.calc.MemberCalc;
import mondrian.calc.TupleCalc;
import mondrian.olap.Evaluator;
import mondrian.olap.fun.FunUtil;
import mondrian.olap.type.Type;

/**
 * Adapter which computes a scalar or tuple expression and converts it to any
 * required type.
 *
 * @see mondrian.calc.impl.GenericIterCalc
 *
 * @author jhyde
 * @since Sep 26, 2005
 */
public abstract class GenericCalc
extends AbstractProfilingNestedCalc<Object>
implements TupleCalc,
 MemberCalc
{
    /**
     * Creates a GenericCalc without specifying child calculated expressions.
     *
     * <p>Subclass should override {@link #getChildCalcs()}.
     *
     * @param exp Source expression
     */
    protected GenericCalc(String name, Type type) {
        super(name,type, null);
    }

    /**
     * Creates an GenericCalc.
     *
     * @param exp Source expression
     * @param calcs Child compiled expressions
     */
    protected GenericCalc(String name, Type type, Calc[] calcs) {
        super(name,type, calcs);
    }



	@Override
    public Member[] evaluateTuple(Evaluator evaluator) {
        return (Member[]) evaluate(evaluator);
    }

    private String msg(TypeEnum expectedType, Object o) {
        final TypeEnum actualType = GenericCalc.actualType(o);
        return new StringBuilder("Expected value of type ").append(expectedType).append("; got value '").append(o)
                .append("' (").append((actualType == null ? o.getClass() : actualType)).append(")").toString();
    }

    private static TypeEnum actualType(Object o) {
        if (o == null) {
            return TypeEnum.NULL;
        }
        if (o instanceof String) {
            return TypeEnum.STRING;
        } else if (o instanceof Boolean) {
            return TypeEnum.BOOLEAN;
        } else if (o instanceof Number) {
            return TypeEnum.NUMERIC;
        } else if (o instanceof Date) {
            return TypeEnum.DATETIME;
        } else if (o instanceof Member) {
            return TypeEnum.MEMBER;
        } else if (o instanceof Level) {
            return TypeEnum.LEVEL;
        } else if (o instanceof Hierarchy) {
            return TypeEnum.HIERARCHY;
        } else if (o instanceof Dimension) {
            return TypeEnum.DIMENSION;
        } else {
            return null;
        }
    }



    public static double numberToDouble(Number number) {
        return number == null
                ? FunUtil.DOUBLE_NULL
                        : number.doubleValue();
    }



    @Override
    public Member evaluateMember(Evaluator evaluator) {
        final Object o = evaluate(evaluator);
        try {
            return (Member) o;
        } catch (final ClassCastException e) {
            throw evaluator.newEvalException(null, msg(TypeEnum.MEMBER, o));
        }
    }

    private enum TypeEnum {
        NULL,
        BOOLEAN, STRING, NUMERIC, DATETIME,
        MEMBER, LEVEL, HIERARCHY, DIMENSION
    }
}
