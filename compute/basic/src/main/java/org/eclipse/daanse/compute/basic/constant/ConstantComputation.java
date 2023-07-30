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
package org.eclipse.daanse.compute.basic.constant;

import java.util.List;

import org.eclipse.daanse.compute.api.Computation;
import org.eclipse.daanse.compute.api.ComputationContext;
import org.eclipse.daanse.compute.basic.base.AbstractComputation;

public class ConstantComputation<T> extends AbstractComputation<T> {

	private T value;

	public ConstantComputation(T value) {
		this.value = value;
	}

	@Override
	public List<Computation<Object>> subComputation() {
		return List.of();
	}

	@Override
	public boolean isConstant() {
		return true;
	}

	@Override
	protected T plainCompute(ComputationContext context) {
		return value;
	}

}
