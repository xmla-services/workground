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
package org.eclipse.daanse.compute.api;

import java.util.List;
import java.util.Optional;

/**
 * The Interface Computation.
 * 
 * The physical language of compiled expressions
 * 
 * @param <T> the type that will be returned when the
 *            {@link Computation#compute(ComputationContext)} is called.
 */
public interface Computation<T> {

	/**
	 * Computes this Computation.
	 *
	 * @param context the context
	 * @return the result
	 */
	T compute(ComputationContext context);

	/**
	 * Sub computation.
	 *
	 * @return the list
	 */
	List<Computation<Object>> subComputation();

	/**
	 * Indicated that the {@link Computation#compute(ComputationContext)} has been
	 * called.
	 *
	 * @return <code>true</code> if the started
	 */
	boolean isComputeStarted();

	/**
	 * Optional Current result.
	 * 
	 * {@link Optional#isEmpty()} if {@link #compute(ComputationContext)} had not
	 * been called and Result not been computed.
	 *
	 * @return optional of the the currentResult
	 */
	Optional<T> currentResult();

	/**
	 * Indicated that the {@link Computation} always returns the same value.
	 *
	 * @return true, if is constant
	 */
	boolean isConstant();
}
