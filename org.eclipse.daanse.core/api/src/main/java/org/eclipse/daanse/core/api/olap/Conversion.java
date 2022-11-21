/*********************************************************************
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
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
 **********************************************************************/
package org.eclipse.daanse.core.api.olap;

import java.util.List;

/**
 * Description of an implicit conversion that occurred while resolving an
 * operator call.
 */
public interface Conversion {
    /**
     * Returns the cost of the conversion. If there are several matching
     * overloads, the one with the lowest overall cost will be preferred.
     *
     * @return Cost of conversion
     */
    int getCost();

    /**
     * Checks the viability of implicit conversions. Converting from a
     * dimension to a hierarchy is valid if is only one hierarchy.
     */
    void checkValid();

    /**
     * Applies this conversion to its argument, modifying the argument list
     * in place.
     *
     * @param validator Validator
     * @param args Argument list
     */
    void apply(Validator validator, List<Exp> args);
}
