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
package org.eclipse.daanse.xmla.api.discover.mdschemafunctions;

import java.util.Optional;

public interface DiscoverMdSchemaFunctionsRestrictions {

    String RESTRICTIONS_ORIGIN = "ORIGIN";
    String RESTRICTIONS_INTERFACE_NAME = "INTERFACE_NAME";
    String RESTRICTIONS_LIBRARY_NAME = "LIBRARY_NAME";

    /**
     * @return The possible values are as follows:
     * (0x1) MSOLAP
     * (0x2) UDF
     * (0x3) RELATIONAL
     * (0x4) SCALAR
     */
    Optional<Integer> origin();

    /**
     * @return A logical classification of the type of function. For
     * example:
     * DATETIME
     * LOGICAL
     * FILTER
     */
    Optional<String> interfaceName();

    /**
     * @return The library that implements the function.
     */
    Optional<String> libraryName();
}
