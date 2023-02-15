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
package org.eclipse.daanse.xmla.api.discover.mdschema.sets;

import org.eclipse.daanse.xmla.api.common.enums.CubeSourceEnum;
import org.eclipse.daanse.xmla.api.common.enums.ScopeEnum;

import java.util.Optional;

public interface MdSchemaSetsRestrictions {

    String RESTRICTIONS_CATALOG_NAME = "CATALOG_NAME";
    String RESTRICTIONS_SCHEMA_NAME = "SCHEMA_NAME";
    String RESTRICTIONS_CUBE_NAME = "CUBE_NAME";
    String RESTRICTIONS_SET_NAME = "SET_NAME";
    String RESTRICTIONS_SCOPE = "SCOPE";
    String RESTRICTIONS_CUBE_SOURCE = "CUBE_SOURCE";
    String RESTRICTIONS_HIERARCHY_UNIQUE_NAME = "HIERARCHY_UNIQUE_NAME";

    /**
     * @return The name of the database.
     */
    Optional<String> catalogName();


    /**
     * @return The name of the schema.
     */
    Optional<String> schemaName();

    /**
     * @return The name of the cube.
     */
    Optional<String> cubeName();

    /**
     * The name of the set, as specified in the CREATE SET
     * statement.
     */
    Optional<String> setName();

    /**
     * The scope of the set. The set can be a session-defined
     * set or a global-defined set.
     * This column can have one of the following values:
     * 1 - Global
     * 2 – Session
     */
    Optional<ScopeEnum> scope();

    /**
     * A bitmask with one of the following valid values:
     * 0x01 - Cube
     * 0x02 - Dimension<233>
     * The default restriction is a value of 1.
     */
    Optional<CubeSourceEnum> cubeSource();

    /**
     * The unique name of the hierarchy that contains the set.
     */
    Optional<String> hierarchyUniqueName();
}
