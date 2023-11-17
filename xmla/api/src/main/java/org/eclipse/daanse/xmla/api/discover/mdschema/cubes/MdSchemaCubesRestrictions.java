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

import java.util.Optional;

import org.eclipse.daanse.xmla.api.annotations.Restriction;
import org.eclipse.daanse.xmla.api.common.enums.CubeSourceEnum;

public interface MdSchemaCubesRestrictions {

    String RESTRICTIONS_CATALOG_NAME = "CATALOG_NAME";
    String RESTRICTIONS_SCHEMA_NAME = "SCHEMA_NAME";
    String RESTRICTIONS_CUBE_NAME = "CUBE_NAME";
    String RESTRICTIONS_BASE_CUBE_NAME = "BASE_CUBE_NAME";
    String RESTRICTIONS_CUBE_SOURCE = "CUBE_SOURCE";

    /**
     * @return The catalog name.
     */
    @Restriction(name = RESTRICTIONS_CATALOG_NAME, type = "xsd:string")
    String catalogName();

    /**
     * @return The name of the schema.
     */
    @Restriction(name = RESTRICTIONS_SCHEMA_NAME, type = "xsd:string")
    Optional<String> schemaName();

    /**
     * @return The name of the cube.
     */
    @Restriction(name = RESTRICTIONS_CUBE_NAME, type = "xsd:string")
    Optional<String> cubeName();

    /**
     * @return The name of the source cube if this cube is
     * a perspective cube.
     */
    @Restriction(name = RESTRICTIONS_BASE_CUBE_NAME, type = "xsd:string")
    Optional<String> baseCubeName();

    /**
     * A bitmask with one of these valid values:
     * 0x01-Cube
     * 0x02-Dimension
     */
    @Restriction(name = RESTRICTIONS_CUBE_SOURCE, type = "xsd:int")
    Optional<CubeSourceEnum> cubeSource();
}
