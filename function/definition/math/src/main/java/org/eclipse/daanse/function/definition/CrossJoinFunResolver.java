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
import mondrian.olap.Exp;
import org.eclipse.daanse.function.FunDef;
import org.eclipse.daanse.function.FunUtil;
import org.eclipse.daanse.function.FunctionResolver;
import org.eclipse.daanse.function.ResolverBase;
import org.eclipse.daanse.function.Validator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import java.util.List;

@ServiceProvider(value = FunctionResolver.class, attribute = { "function.definition:String='CrossJoinFun'" })
@Component(service = FunctionResolver.class, scope = ServiceScope.SINGLETON)
public class CrossJoinFunResolver extends ResolverBase {

    public CrossJoinFunResolver() {
        super("Crossjoin", "Crossjoin(<Set1>, <Set2>[, <Set3>...])", "Returns the cross product of two sets.",
            mondrian.olap.Syntax.Function);
    }

    @Override
    public FunDef resolve(
        Exp[] args,
        Validator validator,
        List<Conversion> conversions)
    {
        if (args.length < 2) {
            return null;
        } else {
            for (int i = 0; i < args.length; i++) {
                if (!validator.canConvert(
                    i, args[i], mondrian.olap.Category.SET, conversions)) {
                    return null;
                }
            }

            FunDef dummy = FunUtil.createDummyFunDef(this, mondrian.olap.Category.SET, args);
            return new CrossJoinFunDef(dummy);
        }
    }

    @Override
    public int compareTo(FunctionResolver resolver) {
        return 0;
        //TODO
    }

    protected FunDef createFunDef(Exp[] args, FunDef dummyFunDef) {
        return new CrossJoinFunDef(dummyFunDef);
    }
}
