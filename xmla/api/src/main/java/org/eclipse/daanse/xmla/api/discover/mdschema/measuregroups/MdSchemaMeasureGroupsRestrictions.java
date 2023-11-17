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
package org.eclipse.daanse.xmla.api.discover.mdschema.measuregroups;

import org.eclipse.daanse.xmla.api.annotations.Restriction;

import java.util.Optional;

public interface MdSchemaMeasureGroupsRestrictions {

    String RESTRICTIONS_CATALOG_NAME = "CATALOG_NAME";
    String RESTRICTIONS_SCHEMA_NAME = "SCHEMA_NAME";
    String RESTRICTIONS_CUBE_NAME = "CUBE_NAME";
    String RESTRICTIONS_MEASUREGROUP_NAME = "MEASUREGROUP_NAME";

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
     * The name of the measure group.
     */
    @Restriction(name = RESTRICTIONS_MEASUREGROUP_NAME, type = "xsd:string")
    Optional<String> measureGroupName();
}
