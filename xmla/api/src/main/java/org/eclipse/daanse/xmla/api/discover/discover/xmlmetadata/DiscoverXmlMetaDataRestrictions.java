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
package org.eclipse.daanse.xmla.api.discover.discover.xmlmetadata;

import java.util.Optional;

import org.eclipse.daanse.xmla.api.common.enums.ObjectExpansionEnum;

public interface DiscoverXmlMetaDataRestrictions {
    public static final String RESTRICTIONS_DATABASE_ID = "DatabaseID";
    public static final String RESTRICTIONS_DIMENSION_ID = "DimensionID";
    public static final String RESTRICTIONS_CUBE_ID = "CubeID";
    public static final String RESTRICTIONS_MEASURE_GROUP_ID = "MeasureGroupID";
    public static final String RESTRICTIONS_PARTITION_ID = "PartitionID";
    public static final String RESTRICTIONS_PERSPECTIVE_ID = "PerspectiveID";
    public static final String RESTRICTIONS_PERMISSION_ID = "DimensionPermissionID";
    public static final String RESTRICTIONS_ROLE_ID = "RoleID";
    public static final String RESTRICTIONS_DATABASE_PERMISSION_ID = "DatabasePermissionID";
    public static final String RESTRICTIONS_MINING_MODEL_ID = "MiningModelID";
    public static final String RESTRICTIONS_MINING_MODEL_PERMISSION_ID = "MiningModelPermissionID";
    public static final String RESTRICTIONS_DATA_SOURCE_ID = "DataSourceID";
    public static final String RESTRICTIONS_MINING_STRUCTURE_ID = "MiningStructureID";
    public static final String RESTRICTIONS_AGGREGATION_DESIGN_ID = "AggregationDesignID";
    public static final String RESTRICTIONS_TRACE_ID = "TraceID";
    public static final String RESTRICTIONS_MINING_STRUCTURE_PERMISSION_ID = "MiningStructurePermissionID";
    public static final String RESTRICTIONS_CUBE_PERMISSION_ID = "CubePermissionID";
    public static final String RESTRICTIONS_ASSEMBLY_ID = "AssemblyID";
    public static final String RESTRICTIONS_MDX_SCRIPT_ID = "MdxScriptID";
    public static final String RESTRICTIONS_DATA_SOURCE_VIEW_ID = "DataSourceViewID";
    public static final String RESTRICTIONS_DATA_SOURCE_PERMISSION_ID = "DataSourcePermissionID";
    public static final String RESTRICTIONS_OBJECT_EXPANSION = "ObjectExpansion";

    /**
     * @return The database ID.
     */
    Optional<String> databaseId();

    /**
     * @return The dimension ID.
     */
    Optional<String> dimensionId();

    /**
     * @return The cube ID.
     */
    Optional<String> cubeId();

    /**
     * @return The measure group ID.
     */
    Optional<String> measureGroupId();

    /**
     * @return The partition ID.
     */
    Optional<String> partitionId();

    /**
     * @return The perspective ID.
     */
    Optional<String> perspectiveId();

    /**
     * @return The dimension permission ID.
     */
    Optional<String> dimensionPermissionId();

    /**
     * @return The role ID.
     */
    Optional<String> roleId();

    /**
     * @return The database permission ID.
     */
    Optional<String> databasePermissionId();

    /**
     * @return The mining model ID.
     */
    Optional<String> miningModelId();

    /**
     * @return The mining model permission ID.
     */
    Optional<String> miningModelPermissionId();

    /**
     * @return The data source ID.
     */
    Optional<String> dataSourceId();

    /**
     * @return The mining structure ID.
     */
    Optional<String> miningStructureId();

    /**
     * @return The aggregation design ID.
     */
    Optional<String> aggregationDesignId();

    /**
     * @return The trace ID.
     */
    Optional<String> traceId();

    /**
     * @return The mining structure permission ID.
     */
    Optional<String> miningStructurePermissionId();

    /**
     * @return The cube permission ID.
     */
    Optional<String> cubePermissionId();

    /**
     * @return The assembly ID.
     */
    Optional<String> assemblyId();

    /**
     * @return The MDX script ID.
     */
    Optional<String> mdxScriptId();

    /**
     * @return The data source view ID.
     */
    Optional<String> dataSourceViewId();

    /**
     * @return The data source permission ID.
     */
    Optional<String> dataSourcePermissionId();

    /**
     * @return The degree of expansion that is wanted in the return result. The
     * available values are:
     * ReferenceOnly - Returns only the name/ID/timestamp/state
     * requested for the requested objects and all descendant major
     * objects recursively.
     * ObjectProperties - Expands the requested object with no
     * references to contained objects (includes expanded minor
     * contained objects).
     * ExpandObject - Same as ObjectProperties, but also returns the
     * name, ID, and timestamp for contained major objects.
     * ExpandFull - Fully expands the requested object recursively to
     * the bottom of every contained object.
     */
    Optional<ObjectExpansionEnum> objectExpansion();
}
