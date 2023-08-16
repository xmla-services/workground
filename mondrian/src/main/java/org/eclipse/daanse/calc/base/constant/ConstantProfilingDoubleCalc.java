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
package org.eclipse.daanse.calc.base.constant;

import org.eclipse.daanse.calc.api.DoubleCalc;
import org.eclipse.daanse.calc.base.AbstractProfilingConstantCalc;

import mondrian.olap.type.NumericType;

public class ConstantProfilingDoubleCalc extends AbstractProfilingConstantCalc<Double> implements DoubleCalc {

	public ConstantProfilingDoubleCalc(NumericType type, Double value) {
		super(value, type);
	}

}
