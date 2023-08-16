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
package org.eclipse.daanse.calc.base.type.doublex;

import java.util.Objects;

import org.eclipse.daanse.calc.base.nested.AbstractProfilingNestedDoubleCalc;

import mondrian.calc.Calc;
import mondrian.olap.Evaluator;
import mondrian.olap.fun.FunUtil;
import mondrian.olap.type.Type;

public class UnknownToDoubleCalc extends AbstractProfilingNestedDoubleCalc<Calc<?>> {

	public UnknownToDoubleCalc(Type type, Calc<?> calc) {
		super(type, new Calc[] { calc });
	}

	@Override
	public Double evaluate(Evaluator evaluator) {

		Object o = getFirstChildCalc().evaluate(evaluator);
		if (o == null) {
			return FunUtil.DOUBLE_NULL;
			// null;
			// TODO: !!! JUST REFACTORING 0 must be null
		} else if (Objects.equals(o, FunUtil.DOUBLE_NULL)) {
			return FunUtil.DOUBLE_NULL;
		} else if (o instanceof Double d) {
			return d;
		} else if (o instanceof Number n) {
			return n.doubleValue();
		}
		throw evaluator.newEvalException(null, "wrtong typed, was: " + o);
	}
}