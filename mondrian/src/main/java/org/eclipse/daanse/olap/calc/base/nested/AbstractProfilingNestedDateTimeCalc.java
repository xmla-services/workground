/*
 * This software is subject to the terms of the Eclipse Public License v1.0
 * Agreement, available at the following URL:
 * http://www.eclipse.org/legal/epl-v10.html.
 * You must accept the terms of that agreement to use this software.
 *
 * Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
 */

package org.eclipse.daanse.olap.calc.base.nested;

import java.util.Date;

import org.eclipse.daanse.olap.calc.api.DateTimeCalc;
import org.eclipse.daanse.olap.calc.base.AbstractProfilingNestedCalc;

import mondrian.calc.Calc;
import mondrian.olap.type.Type;


public abstract class AbstractProfilingNestedDateTimeCalc<C   extends Calc<?>>
extends AbstractProfilingNestedCalc<Date,C>
implements DateTimeCalc
{

    protected AbstractProfilingNestedDateTimeCalc(Type type, C[] calcs) {
        super(type, calcs);
    }

 
}
