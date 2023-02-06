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
package org.eclipse.daanse.xmla.api.discover.mdschema.cubes;

import org.eclipse.daanse.xmla.api.common.enums.CubeSourceEnum;

import java.util.Optional;

public interface MdSchemaCubesRestrictions {

    String RESTRICTIONS_SCHEMA_NAME = "SCHEMA_NAME";
    String RESTRICTIONS_CUBE_NAME = "CUBE_NAME";
    String RESTRICTIONS_BASE_CUBE_NAME = "BASE_CUBE_NAME";
    String RESTRICTIONS_CUBE_SOURCE = "CUBE_SOURCE";


    /**
     * @return The name of the schema.
     */
    Optional<String> schemaName();

    /**
     * @return The name of the cube.
     */
    Optional<String> cubeName();

    /**
     * @return The name of the source cube if this cube is
     * a perspective cube.
     */
    Optional<String> baseCubeName();

    /**
     * A bitmask with one of these valid values:
     * 0x01-Cube
     * 0x02-Dimension
     */
    Optional<CubeSourceEnum> cubeSource();
}
