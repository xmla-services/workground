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

import org.eclipse.daanse.calc.api.LevelCalc;
import org.eclipse.daanse.calc.base.AbstractProfilingConstantCalc;
import org.eclipse.daanse.olap.api.model.Level;

import mondrian.olap.type.LevelType;

public class ConstantProfilingLevelCalc extends AbstractProfilingConstantCalc<Level> implements LevelCalc {

	public ConstantProfilingLevelCalc(LevelType type, Level value) {
		super(value, type);
	}

	public static ConstantProfilingLevelCalc of(Level level) {
		return new ConstantProfilingLevelCalc(LevelType.forLevel(level), level);
	}

}
