/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2003-2005 Julian Hyde
// Copyright (C) 2005-2017 Hitachi Vantara
// All Rights Reserved.
*/

package mondrian.olap;

import org.eclipse.daanse.olap.api.element.Cube;
import org.eclipse.daanse.olap.api.element.Dimension;
import org.eclipse.daanse.olap.api.element.Hierarchy;
import org.eclipse.daanse.olap.api.element.Level;
import org.eclipse.daanse.olap.api.element.Member;

/**
 * <code>Category</code> enumerates the possible expression types.
 *
 * <p>Values of this enumeration are returned by {@link Expression#getCategory()},
 * {@link FunctionDefinition#getParameterCategories()}, and
 * {@link FunctionDefinition#getReturnCategory()}.
 *
 * <p>For modern code, the more descriptive type system
 * ({@link mondrian.olap.type.Type}) is preferred.
 *
 * @author jhyde
 * @since Feb 21, 2003
 */
public class Category extends EnumeratedValues {
    /**
     * The singleton instance of <code>Category</code>.
     */
    public static final Category instance = new Category();

    private Category() {
        super(
            new String[] {
                "unknown", "array", "dimension", "hierarchy", "level",
                "logical", "member", "numeric", "set",
                "string", "tuple", "symbol", "cube", "value", "integer",
                "null", "empty", "datetime",
            },
            new int[] {
                UNKNOWN, ARRAY, DIMENSION, HIERARCHY, LEVEL,
                LOGICAL, MEMBER, NUMERIC, SET,
                STRING, TUPLE, SYMBOL, CUBE, VALUE, INTEGER,
                NULL, EMPTY, DATE_TIME,
            },
            new String[] {
                "Unknown", "Array", "Dimension", "Hierarchy", "Level",
                "Logical Expression", "Member", "Numeric Expression", "Set",
                "String", "Tuple", "Symbol", "Cube", "Value", "Integer",
                "Null", "Empty", "DateTime",
            }
        );
    }

    /**
     * Returns the singleton instance of <code>Category</code>.
     *
     * @return the singleton instance
     */
    public static Category instance() {
        return instance;
    }

    /**
     * <code>Unknown</code> is an expression whose type is as yet unknown.
     */
    public static final int UNKNOWN = 0;

    /**
     * <code>Array</code> is an expression of array type.
     */
    public static final int ARRAY = 1;

    /**
     * <code>Dimension</code> is a dimension expression.
     * @see Dimension
     */
    public static final int DIMENSION = 2;

    /**
     * <code>Hierarchy</code> is a hierarchy expression.
     * @see Hierarchy
     */
    public static final int HIERARCHY = 3;

    /**
     * <code>Level</code> is a level expression.
     * @see Level
     */
    public static final int LEVEL = 4;

    /**
     * <code>Logical</code> is a boolean expression.
     */
    public static final int LOGICAL = 5;

    /**
     * <code>Member</code> is a member expression.
     * @see Member
     */
    public static final int MEMBER = 6;

    /**
     * <code>Numeric</code> is a numeric expression.
     */
    public static final int NUMERIC = 7;

    /**
     * <code>Set</code> is a set of members or tuples.
     */
    public static final int SET = 8;

    /**
     * <code>String</code> is a string expression.
     */
    public static final int STRING = 9;

    /**
     * <code>Tuple</code> is a tuple expression.
     */
    public static final int TUPLE = 10;

    /**
     * <code>Symbol</code> is a symbol, for example the <code>BASC</code>
     * keyword to the <code>Order()</code> function.
     */
    public static final int SYMBOL = 11;

    /**
     * <code>Cube</code> is a cube expression.
     * @see Cube
     */
    public static final int CUBE = 12;

    /**
     * <code>Value</code> is any expression yielding a string or numeric value.
     */
    public static final int VALUE = 13;

    /**
     * <code>Integer</code> is an integer expression. This is a subtype of
     * {@link #NUMERIC}.
     */
    public static final int INTEGER = 15;

    /**
     * Represents a <code>Null</code> value
     */
    public static final int NULL = 16;

    /**
     * Represents an empty expression.
     */
    public static final int EMPTY = 17;

    /**
     * Represents a DataTime expression.
     */
    public static final int DATE_TIME = 18;

    /**
     * <code>Expression</code> is a flag which, when bitwise-OR-ed with a
     * category value, indicates an expression (as opposed to a constant).
     */
    public static final int EXPRESSION = 0;
    /** <code>Constant</code> is a flag which, when bitwise-OR-ed with a
     * category value, indicates a constant (as opposed to an expression). */
    public static final int CONSTANT = 64;
    /** <code>Mask</code> is a mask to remove flags. */
    public static final int MASK = 31;

    /**
     * Returns whether a category represents a scalar type.
     *
     * @param category Category
     * @return Whether is scalar
     */
    public static boolean isScalar(int category) {
        switch (category & MASK) {
        case VALUE:
        case LOGICAL:
        case NUMERIC:
        case INTEGER:
        case STRING:
        case DATE_TIME:
            return true;
        default:
            return false;
        }
    }
}
