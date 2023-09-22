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
import mondrian.olap.Category;
import mondrian.olap.Expression;
import mondrian.olap.QueryImpl;
import mondrian.olap.Syntax;

import org.eclipse.daanse.function.FunDef;
import org.eclipse.daanse.function.FunctionResolver;
import org.eclipse.daanse.function.ResolverBase;
import org.eclipse.daanse.function.Validator;
import org.eclipse.daanse.olap.api.query.component.NamedSetExpression;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import java.util.List;

@ServiceProvider(value = FunctionResolver.class, attribute = { "function.definition:String='AsFun'" })
@Component(service = FunctionResolver.class, scope = ServiceScope.SINGLETON)
public class AsFunDefResolver extends ResolverBase {
    public AsFunDefResolver() {
        super("AS", null, null, Syntax.Infix);
    }

    @Override
    public FunDef resolve(
        Expression[] args,
        Validator validator,
        List<Conversion> conversions)
    {
        if (!validator.canConvert(
            0, args[0], Category.SET, conversions))
        {
            return null;
        }

        // By the time resolve is called, the id argument has already been
        // resolved... to a named set, namely itself. That's not pretty.
        // We'd rather it stayed as an id, and we'd rather that a named set
        // was not visible in the scope that defines it. But we can work
        // with this.

        final QueryImpl.ScopedNamedSet scopedNamedSet =
            (QueryImpl.ScopedNamedSet) ((NamedSetExpression) args[1]).getNamedSet();
        return (FunDef) new AsFunDef(scopedNamedSet);
    }

    @Override
    public int compareTo(FunctionResolver resolver) {
        return 0;
        //TODO
    }
}
