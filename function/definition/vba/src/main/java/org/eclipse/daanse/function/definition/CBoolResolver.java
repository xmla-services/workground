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
import mondrian.olap.Expression;
import mondrian.olap.Syntax;
import org.eclipse.daanse.function.FunDef;
import org.eclipse.daanse.function.FunctionResolver;
import org.eclipse.daanse.function.ResolverBase;
import org.eclipse.daanse.function.Validator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import java.util.List;

@ServiceProvider(value = FunctionResolver.class, attribute = { "function.definition:String='CBoolFun'" })
@Component(service = FunctionResolver.class, scope = ServiceScope.SINGLETON)
public class CBoolResolver extends ResolverBase {
    private FunDef cBool = new CBoolFunDef();
    public CBoolResolver() {
        super(CBoolFunDef.NAME, CBoolFunDef.SIGNATURE, CBoolFunDef.DESCRIPTION, Syntax.Function);
    }

    @Override
    public FunDef resolve(
        Expression[] args,
        Validator validator,
        List<Conversion> conversions
    ) {
        //TODO
        return cBool;
    }

    @Override
    public int compareTo(FunctionResolver resolver) {
        return 0;
        //TODO
    }
}
