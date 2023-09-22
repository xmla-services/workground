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

import org.eclipse.daanse.olap.api.Evaluator;
import org.eclipse.daanse.olap.calc.api.IntegerCalc;
import org.eclipse.daanse.olap.calc.base.nested.AbstractProfilingNestedBooleanCalc;

import mondrian.olap.fun.FunUtil;
import mondrian.olap.type.Type;

public class IntgegerToBooleanCalc extends AbstractProfilingNestedBooleanCalc<IntegerCalc> {

	public IntgegerToBooleanCalc(Type type, IntegerCalc integerCalc) {
		super(type, new IntegerCalc[] { integerCalc });
	}

	@Override
	public Boolean evaluate(Evaluator evaluator) {
		Integer v0 = getFirstChildCalc().evaluate(evaluator);
		if (v0 == null) {
			return FunUtil.BOOLEAN_NULL;
		}
		return v0 != 0;
	}
}
