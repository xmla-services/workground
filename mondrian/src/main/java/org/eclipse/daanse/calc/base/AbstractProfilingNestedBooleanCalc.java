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
package org.eclipse.daanse.calc.base;

import org.eclipse.daanse.calc.api.BooleanCalc;

import mondrian.calc.Calc;
import mondrian.olap.type.Type;

/**
 * Abstract implementation of the {@link org.eclipse.daanse.calc.api.BooleanCalc} interface.
 * 
 * Handles nested child and profiling
 *
 */
public abstract class AbstractProfilingNestedBooleanCalc
extends AbstractProfilingNestedCalc<Boolean>
implements BooleanCalc
{
    /**
     * {@inheritDoc} 
     *
     */
    protected AbstractProfilingNestedBooleanCalc(String name, Type type,Calc[] calcs) {
        super(name,type, calcs);
    }


}
