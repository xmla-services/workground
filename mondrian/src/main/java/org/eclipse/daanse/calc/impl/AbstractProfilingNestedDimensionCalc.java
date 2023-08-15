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

package org.eclipse.daanse.calc.impl;

import org.eclipse.daanse.calc.api.DimensionCalc;
import org.eclipse.daanse.olap.api.model.Dimension;

import mondrian.calc.Calc;
import mondrian.olap.type.DimensionType;
import mondrian.olap.type.Type;


public abstract class AbstractProfilingNestedDimensionCalc
extends AbstractProfilingNestedCalc<Dimension>
implements DimensionCalc
{

    protected AbstractProfilingNestedDimensionCalc(String name, Type type, Calc<?>[] calcs) {
        super(name,type, calcs);
        assert getType() instanceof DimensionType;
    }


}
