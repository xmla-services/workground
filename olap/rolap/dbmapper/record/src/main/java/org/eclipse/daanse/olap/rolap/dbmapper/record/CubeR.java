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
package org.eclipse.daanse.olap.rolap.dbmapper.record;

import org.eclipse.daanse.olap.rolap.dbmapper.api.Cube;
import org.eclipse.daanse.olap.rolap.dbmapper.api.CubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Relation;

import java.util.List;

public record CubeR(String name,
                    String caption,
                    String description,
                    String defaultMeasure,
                    List<AnnotationR> annotations,
                    List<CubeDimension> dimensionUsageOrDimension,
                    List<MeasureR> measure,
                    List<CalculatedMemberR> calculatedMember,
                    List<NamedSetR> namedSet,
                    List<DrillThroughActionR> drillThroughAction,
                    List<WritebackTableR> writebackTable,
                    boolean enabled,
                    boolean cache,
                    boolean visible,
                    Relation fact,
                    List<ActionR> action
                    )
        implements Cube {

}
