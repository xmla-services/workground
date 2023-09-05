/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 1998-2005 Julian Hyde
// Copyright (C) 2005-2017 Hitachi Vantara and others
// All Rights Reserved.
*/

package mondrian.olap;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Map;

import mondrian.olap.api.Literal;
import org.eclipse.daanse.olap.calc.api.Calc;
import org.eclipse.daanse.olap.calc.base.constant.ConstantDoubleCalc;
import org.eclipse.daanse.olap.calc.base.constant.ConstantStringCalc;
import org.olap4j.impl.UnmodifiableArrayMap;

import mondrian.calc.ExpCompiler;
import mondrian.mdx.MdxVisitor;
import mondrian.olap.type.NullType;
import mondrian.olap.type.NumericType;
import mondrian.olap.type.StringType;
import mondrian.olap.type.SymbolType;
import mondrian.olap.type.Type;

/**
 * Represents a constant value, such as a string or number, in a parse tree.
 *
 * <p>Symbols, such as the <code>ASC</code> keyword in
 * <code>Order([Store].Members, [Measures].[Unit Sales], ASC)</code>, are
 * also represented as Literals.
 *
 * @author jhyde, 21 January, 1999
 */
public class LiteralImpl extends ExpBase implements Literal {

    // Data members.

    public final int category;
    private final Object o;


    // Constants for commonly used literals.

    public static final LiteralImpl nullValue = new LiteralImpl(Category.NULL, null);

    public static final LiteralImpl emptyString = new LiteralImpl(Category.STRING, "");

    public static final LiteralImpl zero =
        new LiteralImpl(Category.NUMERIC, BigDecimal.ZERO);

    public static final LiteralImpl one =
        new LiteralImpl(Category.NUMERIC, BigDecimal.ONE);

    public static final LiteralImpl negativeOne =
        new LiteralImpl(Category.NUMERIC, BigDecimal.ONE.negate());

    public static final LiteralImpl doubleZero = zero;

    public static final LiteralImpl doubleOne = one;

    public static final LiteralImpl doubleNegativeOne = negativeOne;

    private static final Map<BigDecimal, LiteralImpl> MAP =
        UnmodifiableArrayMap.of(
            BigDecimal.ZERO, zero,
            BigDecimal.ONE, one,
            BigDecimal.ONE.negate(), negativeOne);

    /**
     * Private constructor.
     *
     * <p>Use the creation methods {@link #createString(String)} etc.
     */
    private LiteralImpl(int type, Object o) {
        this.category = type;
        this.o = o;
    }

    /**
     * Creates a string literal.
     * @see #createSymbol
     */
    public static LiteralImpl createString(String s) {
        return (s.equals(""))
            ? emptyString
            : new LiteralImpl(Category.STRING, s);
    }

    /**
     * Creates a symbol.
     *
     * @see #createString
     */
    public static LiteralImpl createSymbol(String s) {
        return new LiteralImpl(Category.SYMBOL, s);
    }

    /**
     * Creates a numeric literal.
     *
     * @deprecated Use {@link #create(java.math.BigDecimal)}
     */
    @Deprecated
	public static LiteralImpl create(Double d) {
        return new LiteralImpl(Category.NUMERIC, BigDecimal.valueOf(d));
    }

    /**
     * Creates an integer literal.
     *
     * @deprecated Use {@link #create(java.math.BigDecimal)}
     */
    @Deprecated
	public static LiteralImpl create(Integer i) {
        return new LiteralImpl(Category.NUMERIC, new BigDecimal(i));
    }

    /**
     * Creates a numeric literal.
     *
     * <p>Using a {@link BigDecimal} allows us to store the precise value that
     * the user typed. We will have to fit the value into a native
     * {@code double} or {@code int} later on, but parse time is not the time to
     * be throwing away information.
     */
    public static LiteralImpl create(BigDecimal d) {
        final LiteralImpl literal = MAP.get(d);
        if (literal != null) {
            return literal;
        }
        return new LiteralImpl(Category.NUMERIC, d);
    }

    @Override
	public LiteralImpl cloneExp() {
        return this;
    }

    @Override
	public void unparse(PrintWriter pw) {
        switch (category) {
        case Category.SYMBOL, Category.NUMERIC:
            pw.print(o);
            break;
        case Category.STRING:
            pw.print(Util.quoteForMdx((String) o));
            break;
        case Category.NULL:
            pw.print("NULL");
            break;
        default:
            throw Util.newInternal("bad literal type " + category);
        }
    }

    @Override
	public int getCategory() {
        return category;
    }

    @Override
	public Type getType() {
        switch (category) {
        case Category.SYMBOL:
            return new SymbolType();
        case Category.NUMERIC:
            return new NumericType();
        case Category.STRING:
            return new StringType();
        case Category.NULL:
            return new NullType();
        default:
            throw Category.instance.badValue(category);
        }
    }

    @Override
	public Exp accept(Validator validator) {
        return this;
    }

	@Override
	public Calc<?> accept(ExpCompiler compiler) {

		switch (category) {
		case Category.NUMERIC:
			if (o instanceof Number n) {
				return new ConstantDoubleCalc(new NumericType(), n.doubleValue());
			}
		default:
			String s = null;

			if (o != null) {
				s = o.toString();
			}
			return new ConstantStringCalc(new StringType(), s);
		}
	}

    @Override
	public Object accept(MdxVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public Object getValue() {
        return o;
    }

    @Override
    public int getIntValue() {
        if (o instanceof Number number) {
            return number.intValue();
        } else {
            throw Util.newInternal(new StringBuilder("cannot convert ").append(o).append(" to int").toString());
        }
    }

}
