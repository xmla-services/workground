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
package org.eclipse.daanse.olap.calc.base.type.booleanx;

import org.eclipse.daanse.olap.calc.api.Calc;
import org.eclipse.daanse.olap.calc.base.nested.AbstractProfilingNestedBooleanCalc;

import mondrian.olap.Evaluator;
import mondrian.olap.fun.FunUtil;
import mondrian.olap.type.Type;

public class UnknownToBooleanCalc extends AbstractProfilingNestedBooleanCalc<Calc<?>> {

	public UnknownToBooleanCalc(Type type, Calc<?> calc) {
		super(type, new Calc<?>[] { calc });
	}

	@Override
	public Boolean evaluate(Evaluator evaluator) {
		Object v0 = getFirstChildCalc().evaluate(evaluator);
		if (v0 == null) {
			return FunUtil.BOOLEAN_NULL;
		}
		if (v0 instanceof Boolean b) {
			return b;
		}
		if (v0 instanceof Number n) {
			return n.intValue() != 0;
		}
		throw evaluator.newEvalException(null, "wrong type, was:" + v0);

	}
}