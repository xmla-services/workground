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
package org.eclipse.daanse.xmla.api.discover.mdschema.properties;

import java.util.Optional;

import org.eclipse.daanse.xmla.api.common.enums.CubeSourceEnum;
import org.eclipse.daanse.xmla.api.common.enums.PropertyOriginEnum;
import org.eclipse.daanse.xmla.api.common.enums.PropertyTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.VisibilityEnum;

public interface MdSchemaPropertiesRestrictions {

    String RESTRICTIONS_CATALOG_NAME = "CATALOG_NAME";
    String RESTRICTIONS_SCHEMA_NAME = "SCHEMA_NAME";
    String RESTRICTIONS_CUBE_NAME = "CUBE_NAME";
    String RESTRICTIONS_DIMENSION_UNIQUE_NAME = "DIMENSION_UNIQUE_NAME";
    String RESTRICTIONS_HIERARCHY_UNIQUE_NAME = "HIERARCHY_UNIQUE_NAME";
    String RESTRICTIONS_LEVEL_UNIQUE_NAME = "LEVEL_UNIQUE_NAME";
    String RESTRICTIONS_MEMBER_UNIQUE_NAME = "MEMBER_UNIQUE_NAME";
    String RESTRICTIONS_PROPERTY_TYPE = "PROPERTY_TYPE";
    String RESTRICTIONS_PROPERTY_NAME = "PROPERTY_NAME";
    String RESTRICTIONS_PROPERTY_ORIGIN = "PROPERTY_ORIGIN";
    String RESTRICTIONS_CUBE_SOURCE = "CUBE_SOURCE";
    String RESTRICTIONS_PROPERTY_VISIBILITY = "PROPERTY_VISIBILITY";

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
     * The unique name of the dimension.
     */
    Optional<String> dimensionUniqueName();

    /**
     * The unique name of the hierarchy.
     */
    Optional<String> hierarchyUniqueName();

    /**
     * The unique name of the level.
     */
    Optional<String> levelUniqueName();

    /**
     * @return The unique name of the member.
     */
    Optional<String> memberUniqueName();

    /**
     * @return A bitmask that specifies the
     * type of the property, as
     * follows:
     * 1 - Identifies a property of
     * a member.
     * 2 - Identifies a property of
     * a cell.
     * 4 - Identifies an internal
     * property.
     * 8 - Identifies a property
     * which contains a binary
     * large object (BLOB).
     */
    Optional<PropertyTypeEnum> propertyType();

    /**
     * @return The name of the property.
     */
    Optional<String> propertyName();

    /**
     * @return A bitmask that specifies the
     * type of hierarchy to which the
     * property applies, as follows:
     * 1 - Indicates the property
     * is on a user defined
     * hierarchy.
     * 2 - Indicates the property
     * is on an attribute
     * hierarchy.
     * 4 - Indicates the property
     * is on a key attribute
     * hierarchy.
     * 8 - Indicates the property
     * is on an attribute hierarchy
     * that is not enabled.
     */
    Optional<PropertyOriginEnum> propertyOrigin();

    /**
     * @return bitmask with one of these valid values:
     * 0x01 - Cube
     * 0x02 - Dimension
     * The default restriction is a value of 1.
     */
    Optional<CubeSourceEnum> cubeSource();

    /**
     *A bitmask with one of these valid values:
     * 0x01 - Visible
     * 0x02 - Not Visible
     * The default restriction is a value of 1.
     */
    Optional<VisibilityEnum> propertyVisibility();
}
