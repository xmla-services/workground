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
package org.eclipse.daanse.calc.impl;

import org.eclipse.daanse.calc.api.HierarchyCalc;
import org.eclipse.daanse.olap.api.model.Hierarchy;

import mondrian.olap.type.HierarchyType;

public class ConstantHierarchyProfilingCalc extends AbstractProfilingConstantCalc<Hierarchy> implements HierarchyCalc {

	public ConstantHierarchyProfilingCalc(HierarchyType type, Hierarchy value) {
		super(value, type, "ConstantHierarchyProfilingCalc");
	}

	public static ConstantHierarchyProfilingCalc of(Hierarchy hierarchy) {
		return new ConstantHierarchyProfilingCalc(HierarchyType.forHierarchy(hierarchy), hierarchy);
	}

}
