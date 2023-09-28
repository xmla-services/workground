/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2002-2005 Julian Hyde
// Copyright (C) 2005-2017 Hitachi Vantara and others
// All Rights Reserved.
*/

package mondrian.olap.fun;

import java.util.List;
import java.util.Optional;

import org.eclipse.daanse.olap.api.DataType;
import org.eclipse.daanse.olap.api.Syntax;
import org.eclipse.daanse.olap.api.Validator;
import org.eclipse.daanse.olap.api.function.FunctionDefinition;
import org.eclipse.daanse.olap.api.function.FunctionResolver;
import org.eclipse.daanse.olap.api.query.component.Expression;

/**
 * A <code>SimpleResolver</code> resolves a single, non-overloaded function.
 *
 * @author jhyde
 * @since 3 March, 2002
 */
class SimpleResolver implements FunctionResolver {
    private  final FunctionDefinition funDef;

    SimpleResolver(FunctionDefinition funDef) {
        this.funDef = funDef;
    }

    @Override
	public Optional<FunctionDefinition> getRepresentativeFunDef() {
        return  Optional.ofNullable(funDef);
    }

    @Override
	public String getName() {
        return funDef.getFunctionMetaData().name();
    }

    @Override
	public String getDescription() {
        return funDef.getFunctionMetaData().description();
    }

    @Override
	public String getSignature() {
        return funDef.getSignature();
    }

    @Override
	public Syntax getSyntax() {
        return funDef.getFunctionMetaData().syntax();
    }


    @Override
	public FunctionDefinition resolve(
        Expression[] args,
        Validator validator,
        List<Conversion> conversions)
    {
    	DataType[] parameterTypes = funDef.getFunctionMetaData().parameterCategories();
        if (parameterTypes.length != args.length) {
            return null;
        }
        for (int i = 0; i < args.length; i++) {
            if (!validator.canConvert(
                    i, args[i], parameterTypes[i], conversions))
            {
                return null;
            }
        }
        return funDef;
    }

    @Override
	public boolean requiresExpression(int k) {
    	DataType[] parameterTypes = funDef.getFunctionMetaData().parameterCategories();
        return (k >= parameterTypes.length)
            || (parameterTypes[k] != DataType.SET);
    }
}
