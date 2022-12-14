/*
 * This software is subject to the terms of the Eclipse Public License v1.0
 * Agreement, available at the following URL:
 * http://www.eclipse.org/legal/epl-v10.html.
 * You must accept the terms of that agreement to use this software.
 *
 * Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
 */

package mondrian.calc.impl;

import mondrian.calc.Calc;
import mondrian.olap.Evaluator;
import mondrian.olap.Exp;
import mondrian.olap.ExpCacheDescriptor;

/**
 * Calculation which retrieves the value of an underlying calculation
 * from cache.
 *
 * @author jhyde
 * @since Oct 10, 2005
 */
public class CacheCalc extends GenericCalc {
    private final ExpCacheDescriptor key;

    public CacheCalc(Exp exp, ExpCacheDescriptor key) {
        super(exp);
        this.key = key;
    }

    @Override
    public Object evaluate(Evaluator evaluator) {
        return evaluator.getCachedResult(key);
    }

    @Override
    public Calc[] getCalcs() {
        return new Calc[] {key.getCalc()};
    }
}
