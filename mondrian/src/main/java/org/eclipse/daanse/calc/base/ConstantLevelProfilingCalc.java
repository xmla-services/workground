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

import org.eclipse.daanse.calc.api.LevelCalc;
import org.eclipse.daanse.olap.api.model.Level;

import mondrian.olap.type.LevelType;

public class ConstantLevelProfilingCalc extends AbstractProfilingConstantCalc<Level> implements LevelCalc {

	public ConstantLevelProfilingCalc(LevelType type, Level value) {
		super(value, type, "ConstantLevelProfilingCalc");
	}

	public static ConstantLevelProfilingCalc of(Level level) {
		return new ConstantLevelProfilingCalc(LevelType.forLevel(level), level);
	}

}