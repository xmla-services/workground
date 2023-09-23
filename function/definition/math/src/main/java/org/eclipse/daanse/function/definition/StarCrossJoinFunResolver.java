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

import org.eclipse.daanse.function.FunDef;
import org.eclipse.daanse.function.FunctionResolver;
import org.eclipse.daanse.function.MultiResolver;
import org.eclipse.daanse.function.Validator;
import org.eclipse.daanse.olap.api.query.component.Expression;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import java.util.List;

@ServiceProvider(value = FunctionResolver.class, attribute = { "function.definition:String='StarCrossJoinFun'" })
@Component(service = FunctionResolver.class, scope = ServiceScope.SINGLETON)
public class StarCrossJoinFunResolver extends MultiResolver {
    public StarCrossJoinFunResolver() {
        super( "*", "<Set1> * <Set2>", "Returns the cross product of two sets.", new String[] { "ixxx", "ixmx", "ixxm",
            "ixmm" } );
    }

    @Override
    public FunDef resolve(Expression[] args, Validator validator, List<Conversion> conversions ) {
        // This function only applies in contexts which require a set.
        // Elsewhere, "*" is the multiplication operator.
        // This means that [Measures].[Unit Sales] * [Gender].[M] is
        // well-defined.
        if ( validator.requiresExpression() ) {
            return null;
        }
        return super.resolve( args, validator, conversions );
    }

    @Override
    public int compareTo(FunctionResolver resolver) {
        return 0;
        //TODO
    }

    @Override
    protected FunDef createFunDef( Expression[] args, FunDef dummyFunDef ) {
        return new CrossJoinFunDef( dummyFunDef );
    }
}
