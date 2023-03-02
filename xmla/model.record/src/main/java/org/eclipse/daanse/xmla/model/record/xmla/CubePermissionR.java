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
package org.eclipse.daanse.xmla.model.record.xmla;

import org.eclipse.daanse.xmla.api.xmla.Annotation;
import org.eclipse.daanse.xmla.api.xmla.CellPermission;
import org.eclipse.daanse.xmla.api.xmla.CubeDimensionPermission;
import org.eclipse.daanse.xmla.api.xmla.CubePermission;

import java.time.Instant;
import java.util.List;

public record CubePermissionR(String readSourceData,
                              CubePermission.DimensionPermissions dimensionPermissions,
                              CubePermission.CellPermissions cellPermissions,
                              String write,
                              String name,
                              String id,
                              Instant createdTimestamp,
                              Instant lastSchemaUpdate,
                              String description,
                              List<Annotation> annotations,
                              String roleID,
                              Boolean process,
                              String readDefinition,
                              String read) implements CubePermission {

    public record CellPermissionsR(List<CellPermission> cellPermission) implements CubePermission.CellPermissions {

    }

    public record DimensionPermissionsR(
        List<CubeDimensionPermission> dimensionPermission) implements CubePermission.DimensionPermissions {

    }

}
