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
*/package org.eclipse.daanse.calc.impl;

import org.eclipse.daanse.calc.api.DimensionCalc;
import org.eclipse.daanse.olap.api.model.Dimension;

import mondrian.olap.type.DimensionType;

public class ConstantDimensionProfilingCalc extends AbstractProfilingConstantCalc<Dimension> implements DimensionCalc {

	public ConstantDimensionProfilingCalc(DimensionType type, Dimension value) {
		super(value, type, "ConstantDimensionProfilingCalc");
	}

	public static ConstantDimensionProfilingCalc of(Dimension dimension) {
		return new ConstantDimensionProfilingCalc(DimensionType.forDimension(dimension), dimension);
	}

}
