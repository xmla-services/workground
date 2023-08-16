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

package org.eclipse.daanse.calc.base.type.integer;

import org.eclipse.daanse.calc.api.DoubleCalc;
import org.eclipse.daanse.calc.base.nested.AbstractProfilingNestedIntegerCalc;

import mondrian.olap.Evaluator;
import mondrian.olap.type.Type;

public class DoubleToIntegerCalc extends AbstractProfilingNestedIntegerCalc<DoubleCalc> {

	public DoubleToIntegerCalc(Type type, DoubleCalc doubleCalc) {
		super(type, new DoubleCalc[] { doubleCalc });
	}

	@Override
	public Integer evaluate(Evaluator evaluator) {
		Double d = getFirstChildCalc().evaluate(evaluator);
		if (d == null) {
			return null;
		}
		return d.intValue();
	}
}