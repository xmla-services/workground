/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2002-2005 Julian Hyde and others
// Copyright (C) 2005-2017 Hitachi Vantara and others
// All Rights Reserved.
*/

package mondrian.olap.fun;

import java.util.List;

import org.eclipse.daanse.olap.api.DataType;
import org.eclipse.daanse.olap.api.Syntax;
import org.eclipse.daanse.olap.api.Validator;
import org.eclipse.daanse.olap.api.function.FunctionDefinition;
import org.eclipse.daanse.olap.api.function.FunctionMetaData;
import org.eclipse.daanse.olap.api.function.FunctionResolver;
import org.eclipse.daanse.olap.api.query.component.Expression;
import org.eclipse.daanse.olap.function.FunctionMetaDataR;
import org.eclipse.daanse.olap.query.base.Expressions;

import mondrian.olap.Util;

/**
 * A <code>MultiResolver</code> considers several overloadings of the same
 * function. If one of these overloadings matches the actual arguments, it
 * calls the factory method {@link #createFunDef}.
 *
 * @author jhyde
 * @since Feb 12, 2003
 */
public abstract class MultiResolver implements FunctionResolver {
    private final String name;
    private final String signature;
    private final String description;
    private final String[] signatures;
    private final Syntax syntax;

    /**
     * Creates a <code>MultiResolver</code>.
     *
     * @param name Name of function or operator
     * @param signature Signature of function or operator
     * @param description Description of function or operator
     * @param signatures Array of possible signatures, each of which is an
     *     encoding of the syntactic type, return type, and parameter
     *     types of this operator. The "Members" operator has a syntactic
     *     type "pxd" which means "an operator with
     *     {@link Syntax#Property property} syntax (p) which returns a set
     *     (x) and takes a dimension (d) as its argument".
     *     See {@link FunUtil#decodeSyntacticType(String)},
     *     {@link FunUtil#decodeReturnCategory(String)},
     *     {@link FunUtil#decodeParameterCategories(String)}.
     */
    protected MultiResolver(
        String name,
        String signature,
        String description,
        String[] signatures)
    {
        this.name = name;
        this.signature = signature;
        this.description = description;
        this.signatures = signatures;
        Util.assertTrue(signatures.length > 0);
        this.syntax = FunUtil.decodeSyntacticType(signatures[0]);
        for (int i = 1; i < signatures.length; i++) {
            Util.assertTrue(FunUtil.decodeSyntacticType(signatures[i]) == syntax);
        }
    }

    @Override
	public String getName() {
        return name;
    }

    @Override
	public String getDescription() {
        return description;
    }

    @Override
	public String getSignature() {
        return signature;
    }

    @Override
	public Syntax getSyntax() {
        return syntax;
    }


    public String[] getSignatures() {
        return signatures;
    }


    @Override
	public FunctionDefinition resolve(
        Expression[] args,
        Validator validator,
        List<Conversion> conversions)
    {
outer:
        for (String signature : signatures) {
        	DataType[] parameterTypes = FunUtil.decodeParameterCategories(signature);
            if (parameterTypes.length != args.length) {
                continue;
            }
            conversions.clear();
            for (int i = 0; i < args.length; i++) {
                if (!validator.canConvert(
                        i, args[i], parameterTypes[i], conversions))
                {
                    continue outer;
                }
            }
            DataType returnType = FunUtil.decodeReturnCategory(signature);
            
        	FunctionMetaData functionMetaData = new FunctionMetaDataR(getName(), getDescription(), getSignature(),
					getSyntax(), returnType, Expressions.categoriesOf(args));
            return createFunDef(args, functionMetaData);
        }
        return null;
    }

    @Override
	public boolean requiresExpression(int k) {
        for (String signature : signatures) {
        	DataType[] parameterTypes = FunUtil.decodeParameterCategories(signature);
            if ((k < parameterTypes.length)
                && parameterTypes[k] == DataType.SET)
            {
                return false;
            }
        }
        return true;
    }

    protected abstract FunctionDefinition createFunDef(Expression[] args, FunctionMetaData functionMetaData);
}
