/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.eclipse.daanse.core.api.calc;

import org.eclipse.daanse.core.api.olap.*;
import org.eclipse.daanse.core.api.olap.type.Type;

import java.util.List;

/**
 * Mediates the compilation of an expression ({@link mondrian.olap.Exp})
 * into a compiled expression ({@link Calc}).
 *
 * @author jhyde
 * @since Sep 28, 2005
 */
public interface ExpCompiler {

    /**
     * Returns the evaluator to be used for evaluating expressions during the
     * compilation process.
     */
    Evaluator getEvaluator();

    /**
     * Returns the validator which was used to validate this expression.
     *
     * @return validator
     */
    Validator getValidator();

    /**
     * Compiles an expression.
     *
     * @param exp Expression
     * @return Compiled expression
     */
    Calc compile(Exp exp);

    /**
     * Compiles an expression to a given result type.
     *
     * <p>If <code>resultType</code> is not null, casts the expression to that
     * type. Throws an exception if that conversion is not allowed by the
     * type system.
     *
     * <p>The <code>preferredResultStyles</code> parameter specifies a list
     * of desired result styles. It must not be null, but may be empty.
     *
     * @param exp Expression
     *
     * @param resultType Desired result type, or null to use expression's
     *                   current type
     *
     * @param preferredResultStyles List of result types, in descending order
     *                   of preference. Never null.
     *
     * @return Compiled expression, or null if none can satisfy
     */
    Calc compileAs(
        Exp exp,
        Type resultType,
        List<ResultStyle> preferredResultStyles);

    /**
     * Compiles an expression which yields a {@link Member} result.
     */
    MemberCalc compileMember(Exp exp);

    /**
     * Compiles an expression which yields a {@link Level} result.
     */
    LevelCalc compileLevel(Exp exp);

    /**
     * Compiles an expression which yields a {@link Dimension} result.
     */
    DimensionCalc compileDimension(Exp exp);

    /**
     * Compiles an expression which yields a {@link Hierarchy} result.
     */
    HierarchyCalc compileHierarchy(Exp exp);

    /**
     * Compiles an expression which yields an <code>int</code> result.
     * The expression is implicitly converted into a scalar.
     */
    IntegerCalc compileInteger(Exp exp);

    /**
     * Compiles an expression which yields a {@link String} result.
     * The expression is implicitly converted into a scalar.
     */
    StringCalc compileString(Exp exp);

    /**
     * Compiles an expression which yields a {@link java.util.Date} result.
     * The expression is implicitly converted into a scalar.
     */
    DateTimeCalc compileDateTime(Exp exp);

    /**
     * Compiles an expression which yields an immutable {@link TupleList}
     * result.
     *
     * <p>Always equivalent to <code>{@link #compileList}(exp, false)</code>.
     */
    ListCalc compileList(Exp exp);

    /**
     * Compiles an expression which yields {@link TupleList} result.
     *
     * <p>Such an expression is generally a list of {@link Member} objects or a
     * list of tuples (each represented by a {@link Member} array).
     *
     * <p>See {@link #compileList(mondrian.olap.Exp)}.
     *
     * @param exp Expression
     * @param mutable Whether resulting list is mutable
     */
    ListCalc compileList(Exp exp, boolean mutable);

    /**
     * Compiles an expression which yields an immutable {@link Iterable} result.
     *
     * @param exp Expression
     * @return Calculator which yields an Iterable
     */
    IterCalc compileIter(Exp exp);

    /**
     * Compiles an expression which yields a <code>boolean</code> result.
     *
     * @param exp Expression
     * @return Calculator which yields a boolean
     */
    BooleanCalc compileBoolean(Exp exp);

    /**
     * Compiles an expression which yields a <code>double</code> result.
     *
     * @param exp Expression
     * @return Calculator which yields a double
     */
    DoubleCalc compileDouble(Exp exp);

    /**
     * Compiles an expression which yields a tuple result.
     *
     * @param exp Expression
     * @return Calculator which yields a tuple
     */
    TupleCalc compileTuple(Exp exp);

    /**
     * Compiles an expression to yield a scalar result.
     *
     * <p>If the expression yields a member or tuple, the calculator will
     * automatically apply that member or tuple to the current dimensional
     * context and return the value of the current measure.
     *
     * @param exp Expression
     * @param specific Whether to try to use the specific compile method for
     *   scalar types. For example, if <code>specific</code> is true and
     *   <code>exp</code> is a string expression, calls
     *   {@link #compileString(mondrian.olap.Exp)}
     * @return Calculation which returns the scalar value of the expression
     */
    Calc compileScalar(Exp exp, boolean specific);

    /**
     * Implements a parameter, returning a unique slot which will hold the
     * parameter's value.
     *
     * @param parameter Parameter
     * @return Slot
     */
    ParameterSlot registerParameter(Parameter parameter);

    /**
     * Returns a list of the {@link ResultStyle}s
     * acceptable to the caller.
     */
    List<ResultStyle> getAcceptableResultStyles();

}

// End ExpCompiler.java
