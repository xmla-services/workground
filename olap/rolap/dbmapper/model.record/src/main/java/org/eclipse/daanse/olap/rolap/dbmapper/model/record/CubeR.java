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

import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAction;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAnnotation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCalculatedMember;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingDrillThroughAction;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingNamedSet;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingRelation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingWritebackTable;

public record CubeR(String name,
                    String description,
                    List<MappingAnnotation> annotations,
                    String caption,
                    boolean visible,
                    String defaultMeasure,
                    List<MappingCubeDimension> dimensionUsageOrDimensions,
                    List<MappingMeasure> measures,
                    List<MappingCalculatedMember> calculatedMembers,
                    List<MappingNamedSet> namedSets,
                    List<MappingDrillThroughAction> drillThroughActions,
                    List<MappingWritebackTable> writebackTables,
                    boolean enabled,
                    boolean cache,
                    MappingRelation fact,
                    List<MappingAction> actions
                    )
        implements MappingCube {

}
