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

/**
 * The Interface {@link ComputationContext}.
 * 
 * An {@link ComputationContext} holds the necessary information a
 * {@link Computation} needs to {@link Computation#compute(ComputationContext)}.
 * 
 * 
 */
public interface ComputationContext {

	/**
	 * Saves the current state of the {@link ComputationContext}.
	 * 
	 * The returned value of {@link #save()} could be used to rollback to a state by
	 * calling {@link #rollback(int)}.
	 *
	 * <p>
	 * Call this method before {@link Computation#compute(ComputationContext)} an
	 * {@link Computation} that is known to corrupt the {@link ComputationContext}.
	 *
	 * <p>
	 * The Implementation instance of {@link ComputationContext} must handle
	 * multiple active savepoints at the same time.
	 * 
	 * It is allowable to roll back to the save {@link SavePoint} multiple times.
	 * 
	 * After {@link #rollback(SavePoint))} to a {@link SavePoint} you are not
	 * allowed to {@link #rollback(SavePoint)} to a later {@link SavePoint}.
	 *
	 * @return the {@link SavePoint}
	 */
//	SavePoint save();

	/**
	 * Rolls back to the state of the given {@link SavePoint} returned by
	 * {@link #save()}.
	 *
	 */
//	void rollback(SavePoint savePoint);

}
