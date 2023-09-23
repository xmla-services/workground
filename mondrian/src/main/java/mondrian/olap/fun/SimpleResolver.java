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

import org.eclipse.daanse.olap.api.Category;
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
	public FunctionDefinition getRepresentativeFunDef() {
        return funDef;
    }

    @Override
	public String getName() {
        return funDef.getName();
    }

    @Override
	public String getDescription() {
        return funDef.getDescription();
    }

    @Override
	public String getSignature() {
        return funDef.getSignature();
    }

    @Override
	public Syntax getSyntax() {
        return funDef.getSyntax();
    }

    @Override
	public String[] getReservedWords() {
        return FunUtil.emptyStringArray;
    }

    @Override
	public FunctionDefinition resolve(
        Expression[] args,
        Validator validator,
        List<Conversion> conversions)
    {
        int[] parameterTypes = funDef.getParameterCategories();
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
        int[] parameterTypes = funDef.getParameterCategories();
        return (k >= parameterTypes.length)
            || (parameterTypes[k] != Category.SET);
    }
}
