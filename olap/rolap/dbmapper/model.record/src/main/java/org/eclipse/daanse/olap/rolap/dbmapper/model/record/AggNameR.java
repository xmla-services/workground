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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggColumnName;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggForeignKey;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggLevel;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggMeasureFactCount;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggName;

public record AggNameR(String name,
                       MappingAggColumnName aggFactCount,
                       List<MappingAggMeasure> aggMeasures,
                       List<MappingAggColumnName> aggIgnoreColumns,
                       List<MappingAggForeignKey> aggForeignKeys,
                       List<MappingAggLevel> aggLevels,
                       boolean ignorecase,
                       List<MappingAggMeasureFactCount> measuresFactCounts,
                       String approxRowCount)
        implements MappingAggName {

}
