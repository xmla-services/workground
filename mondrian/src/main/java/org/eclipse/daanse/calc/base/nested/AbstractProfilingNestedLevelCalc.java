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

package org.eclipse.daanse.calc.base.nested;

import org.eclipse.daanse.calc.api.LevelCalc;
import org.eclipse.daanse.calc.base.AbstractProfilingNestedCalc;
import org.eclipse.daanse.olap.api.model.Level;

import mondrian.calc.Calc;
import mondrian.olap.type.LevelType;
import mondrian.olap.type.Type;

public abstract class AbstractProfilingNestedLevelCalc extends AbstractProfilingNestedCalc<Level> implements LevelCalc {

	protected AbstractProfilingNestedLevelCalc(Type type, Calc<?>[] calcs) {
		super( type, calcs);
		assert getType() instanceof LevelType;
	}

}
