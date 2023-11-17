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
package org.eclipse.daanse.xmla.api.discover.mdschema.levels;

import java.util.Optional;

import org.eclipse.daanse.xmla.api.annotations.Restriction;
import org.eclipse.daanse.xmla.api.common.enums.CubeSourceEnum;
import org.eclipse.daanse.xmla.api.common.enums.VisibilityEnum;

public interface MdSchemaLevelsRestrictions {

    String RESTRICTIONS_CATALOG_NAME = "CATALOG_NAME";
    String RESTRICTIONS_SCHEMA_NAME = "SCHEMA_NAME";
    String RESTRICTIONS_CUBE_NAME = "CUBE_NAME";
    String RESTRICTIONS_DIMENSION_UNIQUE_NAME = "DIMENSION_UNIQUE_NAME";
    String RESTRICTIONS_HIERARCHY_UNIQUE_NAME = "HIERARCHY_UNIQUE_NAME";
    String RESTRICTIONS_LEVEL_NAME = "LEVEL_NAME";
    String RESTRICTIONS_LEVEL_UNIQUE_NAME = "LEVEL_UNIQUE_NAME";
    String RESTRICTIONS_CUBE_SOURCE = "CUBE_SOURCE";
    String RESTRICTIONS_LEVEL_VISIBILITY = "LEVEL_VISIBILITY";

    /**
     * @return The name of the database.
     */
    @Restriction(name = RESTRICTIONS_CATALOG_NAME, type = "xsd:string")
    Optional<String> catalogName();


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
     * @return The unique name of the
     * dimension.
     */
    @Restriction(name = RESTRICTIONS_DIMENSION_UNIQUE_NAME, type = "xsd:string")
    Optional<String> dimensionUniqueName();

    /**
     * @return The unique name of the
     * hierarchy.
     */
    @Restriction(name = RESTRICTIONS_HIERARCHY_UNIQUE_NAME, type = "xsd:string")
    Optional<String> hierarchyUniqueName();

    /**
     * The name of the level.
     */
    @Restriction(name = RESTRICTIONS_LEVEL_NAME, type = "xsd:string")
    Optional<String> levelName();

    /**
     * The unique name of the level.
     */
    @Restriction(name = RESTRICTIONS_LEVEL_UNIQUE_NAME, type = "xsd:string")
    Optional<String> levelUniqueName();

    /**
     * @return A bitmask with one of these valid values:
     * 0x01 - Cube
     * 0x02 - Dimension<218>
     * The default restriction is a value of 1.
     */
    @Restriction(name = RESTRICTIONS_CUBE_SOURCE, type = "xsd:int")
    Optional<CubeSourceEnum> cubeSource();

    /**
     * @return A bitmask with one of these valid values:
     * 0x01 - Visible
     * 0x02 - Not Visible
     * The default restriction is a value of 1.
     */
    @Restriction(name = RESTRICTIONS_LEVEL_VISIBILITY, type = "xsd:int")
    Optional<VisibilityEnum> levelVisibility();

}
