/*
 * Copyright (c) 2023 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   SmartCity Jena - initial
 *   Stefan Bischof (bipolis.org) - initial
 */
package org.eclipse.daanse.function.definition;

import aQute.bnd.annotation.spi.ServiceProvider;
import mondrian.olap.Syntax;
import mondrian.olap.Util;
import org.eclipse.daanse.function.FunDef;
import org.eclipse.daanse.function.FunUtil;
import org.eclipse.daanse.function.FunctionResolver;
import org.eclipse.daanse.function.ResolverBase;
import org.eclipse.daanse.function.Validator;
import org.eclipse.daanse.olap.api.query.component.Expression;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import java.util.List;

@ServiceProvider(value = FunctionResolver.class, attribute = { "function.definition:String='CaseMatchFun'" })
@Component(service = FunctionResolver.class, scope = ServiceScope.SINGLETON)
public class CaseMatchFunDefResolver extends ResolverBase {
    private CaseMatchFunDefResolver() {
        super(
            "_CaseMatch",
            "Case <Expression> When <Expression> Then <Expression> [...] [Else <Expression>] End",
            "Evaluates various expressions, and returns the corresponding expression for the first which matches a particular value.",
            Syntax.Case);
    }

    @Override
    public FunDef resolve(
        Expression[] args,
        Validator validator,
        List<Conversion> conversions)
    {
        if (args.length < 3) {
            return null;
        }
        int valueType = args[0].getCategory();
        int returnType = args[2].getCategory();
        int j = 0;
        int clauseCount = (args.length - 1) / 2;
        int mismatchingArgs = 0;
        if (!validator.canConvert(j, args[j++], valueType, conversions)) {
            mismatchingArgs++;
        }
        for (int i = 0; i < clauseCount; i++) {
            if (!validator.canConvert(j, args[j++], valueType, conversions))
            {
                mismatchingArgs++;
            }
            if (!validator.canConvert(
                j, args[j++], returnType, conversions))
            {
                mismatchingArgs++;
            }
        }

        if (j < args.length && !validator.canConvert(
            j, args[j++], returnType, conversions)) {
            mismatchingArgs++;
        }

        Util.assertTrue(j == args.length);
        if (mismatchingArgs != 0) {
            return null;
        }

        FunDef dummy = FunUtil.createDummyFunDef(this, returnType, args);
        return new CaseMatchFunDef(dummy);
    }

    @Override
    public boolean requiresExpression(int k) {
        return true;
    }

    @Override
    public int compareTo(FunctionResolver resolver) {
        return 0;
        //TODO
    }
}
