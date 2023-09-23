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

import org.eclipse.daanse.function.FunDef;
import org.eclipse.daanse.function.FunctionResolver;
import org.eclipse.daanse.function.ResolverBase;
import org.eclipse.daanse.function.Validator;
import org.eclipse.daanse.olap.api.Syntax;
import org.eclipse.daanse.olap.api.query.component.Expression;
import org.eclipse.daanse.olap.api.type.Type;

import java.util.List;

public class CacheFunResolver  extends ResolverBase {
    public static final String NAME = "Cache";
    private static final String SIGNATURE_VALUE = "Cache(<<Exp>>)";
    private static final String DESCRIPTION =
        "Evaluates and returns its sole argument, applying statement-level caching";
    private static final Syntax SYNTAX = Syntax.Function;

    CacheFunResolver() {
        super(NAME, SIGNATURE_VALUE, DESCRIPTION, SYNTAX);
    }

    @Override
    public FunDef resolve(
        Expression[] args,
        Validator validator,
        List<Conversion> conversions)
    {
        if (args.length != 1) {
            return null;
        }
        final Expression exp = args[0];
        final int category = exp.getCategory();
        final Type type = exp.getType();
        return new CacheFunDef(
            NAME, SIGNATURE_VALUE, DESCRIPTION, SYNTAX,
            category, type);
    }

    @Override
    public boolean requiresExpression(int k) {
        return false;
    }

    @Override
    public int compareTo(FunctionResolver resolver) {
        return 0;
    }
}
