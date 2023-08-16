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

import java.util.List;

import org.eclipse.daanse.calc.api.ConstantCalc;
import org.eclipse.daanse.calc.api.profile.CalculationProfile;
import org.eclipse.daanse.olap.api.model.Hierarchy;

import mondrian.calc.ResultStyle;
import mondrian.olap.Evaluator;
import mondrian.olap.type.Type;

public abstract class AbstractProfilingConstantCalc<T> extends AbstractProfilingCalc<T> implements ConstantCalc<T> {

	private T value;

	public AbstractProfilingConstantCalc(T value, Type type, String name) {
		super(type, name);
		this.value = value;
	}

	@Override
	public T evaluate(Evaluator evaluator) {
		return value;
	}

	@Override
	public boolean dependsOn(Hierarchy hierarchy) {
		return false;
	}

	@Override
	public ResultStyle getResultStyle() {
		return value == null ? ResultStyle.VALUE : ResultStyle.VALUE_NOT_NULL;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) {
		return false;
	}

	@Override
	public <X> X unwrap(Class<X> iface) {
		return null;
	}
	@Override
	List<CalculationProfile> getChildProfiles() {
		return List.of();
	}

}
