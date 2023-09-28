/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package mondrian.udf;

import org.eclipse.daanse.olap.api.Evaluator;
import org.eclipse.daanse.olap.api.Syntax;
import org.eclipse.daanse.olap.api.type.Type;

import aQute.bnd.annotation.spi.ServiceProvider;
import mondrian.olap.type.NumericType;
import mondrian.spi.UserDefinedFunction;

/**
 * VB function <code>Val</code>
 *
 * @author Gang Chen
 */
@ServiceProvider(value = UserDefinedFunction.class)
public class ValUdf implements UserDefinedFunction {

    @Override
	public Object execute(Evaluator evaluator, Argument[] arguments) {
        Object arg = arguments[0].evaluateScalar(evaluator);

        if (arg instanceof Number) {
            return new Double(((Number) arg).doubleValue());
        } else {
            return new Double(0.0);
        }
    }

    @Override
	public String getDescription() {
        return "VB function Val";
    }

    @Override
	public String getName() {
        return "Val";
    }

    @Override
	public Type[] getParameterTypes() {
        return new Type[] { NumericType.INSTANCE };
    }

    @Override
	public Type getReturnType(Type[] parameterTypes) {
        return NumericType.INSTANCE;
    }

    @Override
	public Syntax getSyntax() {
        return Syntax.Function;
    }

}
