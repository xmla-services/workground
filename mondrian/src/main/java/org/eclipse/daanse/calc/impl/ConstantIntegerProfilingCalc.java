/*
FunUtil* Copyright (c) 2023 Contributors to the Eclipse Foundation.
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

import org.eclipse.daanse.calc.api.IntegerCalc;

import mondrian.olap.type.DecimalType;

public class ConstantIntegerProfilingCalc extends AbstractProfilingConstantCalc<Integer> implements IntegerCalc{

	public ConstantIntegerProfilingCalc(DecimalType type,Integer value) {
		super(value, type, "ConstantIntegerProfilingCalc");
	}

}
