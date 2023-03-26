/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.olap.rolap.dbmapper.model.record;

import java.io.PrintWriter;
import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;

import io.soabase.recordbuilder.core.RecordBuilder;

public record SchemaR(String name,
                      String description,
                      String measuresCaption,
                      String defaultRole,
                      List<AnnotationR> annotations,
                      List<ParameterR> parameter,
                      List<PrivateDimensionR> dimension,
                      List<CubeR> cube,
                      List<VirtualCubeR> virtualCube,
                      List<NamedSetR> namedSet,
                      List<RoleR> roles,
                      List<UserDefinedFunctionR> userDefinedFunctions)
        implements Schema {

    @Override
    public void display(PrintWriter pw, int i) {

    }
}
