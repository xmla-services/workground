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
package org.eclipse.daanse.xmla.api.discover.mdschemacubes;

import java.time.LocalDateTime;

/**
 * This schema rowset describes the structure of cubes within a database. Perspectives are also
 * returned in this schema.
 */
public interface DiscoverMdSchemaCubesResponseRow {

    /**
     * @return The name of the schema.
     */
    String schemaName();

    /**
     * @return The name of the cube.
     */
    String cubeName();

    /**
     * @return The type of the cube.
     * Valid values are:
     * CUBE
     * DIMENSION
     */
    String cubeType();

    /**
     * @return The GUID of the cube.
     */
    Integer cubeGuid();

    /**
     * @return TimeThe date and time the cube was
     * created.
     */
    LocalDateTime createdOn();

    /**
     * @return The date and time that the cube schema
     * was last updated.
     */
    LocalDateTime lastSchemaUpdate();

    /**
     * @return The name of the user who last updated the
     * cube’s schema.
     */
    String schemaUpdatedBy();

    /**
     * @return TimeThe date and time that the cube was last
     * processed.
     */
    LocalDateTime lastDataUpdate();

    /**
     * @return The name of the user who last updated the
     * data of the cube.
     */
    String dataUpdateDBy();

    /**
     * @return A description of the cube.
     */
    String description();

    /**
     * @return When true, indicates that the cube has
     * drillthrough enabled; otherwise,
     * false.
     */
    Boolean isDrillThroughEnabled();

    /**
     * @return When true, indicates that the cube can be
     * used in a linked cube; otherwise false.
     */
    Boolean isLinkable();

    /**
     * @return When true, indicates that the cube is
     * write-enabled; otherwise false.
     */
    Boolean isWriteEnabled();

    /**
     * @return When true, indicates that SQL can be used
     * on the cube; otherwise false.
     */
    Boolean isSqlEnabled();

    /**
     * @return The caption of the cube.
     * BASE_CUBE_NAMExsd:stringYesThe name of the source cube if this cube is
     * a perspective cube.
     */
    Boolean cubeCaption();

    /**
     * @return A bitmask with one of these valid values:
     * 0x01-Cube
     * 0x02-Dimension
     */
    Integer cubeSource();

    /**
     * @return A bitmask that describes query
     * pattern client applications can utilize for
     * higher performance. Valid values are:
     *
     * 0x00 – Use CrossJoin function to
     * create symmetric sets on an axis. This
     * is the default value for the 0th bit
     * when Analysis Services is running in
     * Traditional mode.
     *
     * 0x01 – Use DrillDownMember to
     * create a more restrictive, asymmetric
     * axis. This is the default value for the
     * 0th bit when a server that is running
     * Analysis Services is running in
     * VertiPaq mode.
     */
    Integer preferredQueryPatterns();

}
