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

package org.eclipse.daanse.calc.base.nested.conv;

import org.eclipse.daanse.calc.base.nested.AbstractProfilingNestedDimensionCalc;
import org.eclipse.daanse.olap.api.model.Dimension;

import mondrian.calc.Calc;
import mondrian.olap.Evaluator;
import mondrian.olap.type.Type;

public class ConvertUnknownToDimensionCalc extends AbstractProfilingNestedDimensionCalc<Calc<?>> {

	public ConvertUnknownToDimensionCalc(Type type, Calc<?> calc) {
		super(type, new Calc<?>[] { calc });
	}

	@Override
	public Dimension evaluate(Evaluator evaluator) {
		Object o = getFirstChildCalc().evaluate(evaluator);
		if (o instanceof Dimension d) {
			return d;
		}
		throw evaluator.newEvalException(null, "expected Dimension, was: " + o);
	}
}