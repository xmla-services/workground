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
package org.eclipse.daanse.xmla.api.discover.mdschema.members;

import java.util.Optional;

import org.eclipse.daanse.xmla.api.annotations.Restriction;
import org.eclipse.daanse.xmla.api.common.enums.CubeSourceEnum;
import org.eclipse.daanse.xmla.api.common.enums.MemberTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.TreeOpEnum;

public interface MdSchemaMembersRestrictions {

    String RESTRICTIONS_CATALOG_NAME = "CATALOG_NAME";
    String RESTRICTIONS_SCHEMA_NAME = "SCHEMA_NAME";
    String RESTRICTIONS_CUBE_NAME = "CUBE_NAME";
    String RESTRICTIONS_DIMENSION_UNIQUE_NAME = "DIMENSION_UNIQUE_NAME";
    String RESTRICTIONS_HIERARCHY_UNIQUE_NAME = "HIERARCHY_UNIQUE_NAME";
    String RESTRICTIONS_LEVEL_UNIQUE_NAME = "LEVEL_UNIQUE_NAME";
    String RESTRICTIONS_LEVEL_NUMBER = "LEVEL_NUMBER";
    String RESTRICTIONS_MEMBER_NAME = "MEMBER_NAME";
    String RESTRICTIONS_MEMBER_UNIQUE_NAME = "MEMBER_UNIQUE_NAME";
    String RESTRICTIONS_MEMBER_TYPE = "MEMBER_TYPE";
    String RESTRICTIONS_MEMBER_CAPTION = "MEMBER_CAPTION";
    String RESTRICTIONS_CUBE_SOURCE = "CUBE_SOURCE";
    String RESTRICTIONS_TREE_OP = "TREE_OP";

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
     * The unique name of the dimension.
     */
    @Restriction(name = RESTRICTIONS_DIMENSION_UNIQUE_NAME, type = "xsd:string")
    Optional<String> dimensionUniqueName();

    /**
     * The unique name of the hierarchy.
     */
    @Restriction(name = RESTRICTIONS_HIERARCHY_UNIQUE_NAME, type = "xsd:string")
    Optional<String> hierarchyUniqueName();

    /**
     * The unique name of the level.
     */
    @Restriction(name = RESTRICTIONS_LEVEL_UNIQUE_NAME, type = "xsd:string")
    Optional<String> levelUniqueName();

    /**
     * The distance of the member from the root of the
     * hierarchy. The root level is zero (0).
     */
    @Restriction(name = RESTRICTIONS_LEVEL_NUMBER, type = "xsd:string")
    Optional<Integer> levelNumber();

    /**
     * @return The name of the member.
     */
    @Restriction(name = RESTRICTIONS_MEMBER_NAME, type = "xsd:string")
    Optional<String> memberName();

    /**
     * @return The unique name of the member.
     */
    @Restriction(name = RESTRICTIONS_MEMBER_UNIQUE_NAME, type = "xsd:string")
    Optional<String> memberUniqueName();

    /**
     * @return The type of the member.<228>
     * 1 - Is a regular member.
     * 2 - Is the All member.
     * 3 - Is a measure.
     * 4 - Is a formula.
     * 0 - Is of unknown type.
     */
    @Restriction(name = RESTRICTIONS_MEMBER_TYPE, type = "xsd:string")
    Optional<MemberTypeEnum> memberType();

    /**
     * @return The caption of the member.
     */
    @Restriction(name = RESTRICTIONS_MEMBER_CAPTION, type = "xsd:string")
    Optional<String> memberCaption();

    /**
     * @return bitmask with one of these valid values:
     * 0x01 - Cube
     * 0x02 - Dimension
     * The default restriction is a value of 1.
     */
    @Restriction(name = RESTRICTIONS_CUBE_SOURCE, type = "xsd:int")
    Optional<CubeSourceEnum> cubeSource();

    /**
     * Applies only to a single member:
     * 0x20 - Returns all of the ancestors.
     * 0x01 - Returns only the immediate children.
     * 0x02 - Returns members on the same level.
     * 0x04 - Returns only the immediate parent.
     * 0x08 – Returns only itself.
     * 0x10 - Returns all of the descendants.
     */
    @Restriction(name = RESTRICTIONS_TREE_OP, type = "xsd:int")
    Optional<TreeOpEnum> treeOp();
}
