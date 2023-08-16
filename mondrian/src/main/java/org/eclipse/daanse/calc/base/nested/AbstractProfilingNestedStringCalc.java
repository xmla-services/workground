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

package org.eclipse.daanse.calc.base.nested;

import org.eclipse.daanse.calc.api.StringCalc;
import org.eclipse.daanse.calc.base.AbstractProfilingNestedCalc;

import mondrian.calc.Calc;
import mondrian.olap.type.Type;

/**
 * Abstract implementation of the {@link org.eclipse.daanse.calc.api.StringCalc}
 * interface.
 * 
 * Handles nested child and profiling
 *
 */
public abstract class AbstractProfilingNestedStringCalc<C  extends Calc<?>> extends AbstractProfilingNestedCalc<String,C>
		implements StringCalc {
	/**
	 * {@inheritDoc}
	 *
	 */
	protected AbstractProfilingNestedStringCalc(Type type, C[] calcs) {
		super( type, calcs);
	}

}
