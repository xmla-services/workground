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

import mondrian.calc.ExpCompiler;
import mondrian.olap.api.Literal;
import mondrian.olap.type.NullType;
import mondrian.olap.type.NumericType;
import mondrian.olap.type.StringType;
import mondrian.olap.type.SymbolType;
import mondrian.olap.type.Type;
import org.eclipse.daanse.olap.calc.api.Calc;
import org.eclipse.daanse.olap.calc.base.constant.ConstantDoubleCalc;
import org.eclipse.daanse.olap.calc.base.constant.ConstantStringCalc;

import java.io.PrintWriter;

/**
 * Represents a constant value, such as a string or number, in a parse tree.
 *
 * <p>Symbols, such as the <code>ASC</code> keyword in
 * <code>Order([Store].Members, [Measures].[Unit Sales], ASC)</code>, are
 * also represented as Literals.
 *
 * @author jhyde, 21 January, 1999
 */
public abstract class AbstractLiteralImpl<R> extends ExpBase implements Literal{

    // Data members.

    public final int category;
    private final R o;

    /**
     * Private constructor.
     *
     * <p>Use the creation methods create(R) etc.
     */
    protected AbstractLiteralImpl(int type, R o) {
        this.category = type;
        this.o = o;
    }



    @Override
	public AbstractLiteralImpl cloneExp() {
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


    public Object getValue() {
        return o;
    }


    public int getIntValue() {
        if (o instanceof Number number) {
            return number.intValue();
        } else {
            throw Util.newInternal(new StringBuilder("cannot convert ").append(o).append(" to int").toString());
        }
    }

}
