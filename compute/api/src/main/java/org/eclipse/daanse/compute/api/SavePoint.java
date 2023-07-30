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
 * The Interface SavePoint.
 * 
 * References a state of a {@link ComputationContext} when
 * {@link ComputationContext#save()} is called.
 */
public interface SavePoint {

	/**
	 * Checks if is valid.
	 * 
	 * If {@link ComputationContext#rollback(SavePoint)} is called with a earlier
	 * {@link SavePoint} than this could be invalid.
	 *
	 * @return true, if is valid
	 */
	boolean isValid();
}
