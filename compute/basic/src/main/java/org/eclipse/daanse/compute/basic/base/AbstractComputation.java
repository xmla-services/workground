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
package org.eclipse.daanse.compute.basic.base;

import java.util.Optional;

import org.eclipse.daanse.compute.api.Computation;
import org.eclipse.daanse.compute.api.ComputationContext;

public abstract class AbstractComputation<T> implements Computation<T> {

	private T current = null;
	private boolean started = false;

	@Override
	public T compute(ComputationContext context) {
		started = true;
		current = plainCompute(context);
		return current;
	}

	/**
	 * @param context as in {@link Computation#compute(ComputationContext)}
	 * @return
	 */
	protected abstract T plainCompute(ComputationContext context);

	@Override
	public boolean isComputeStarted() {
		return started;
	}

	@Override
	public Optional<T> currentResult() {
		return Optional.ofNullable(current);
	}

}
