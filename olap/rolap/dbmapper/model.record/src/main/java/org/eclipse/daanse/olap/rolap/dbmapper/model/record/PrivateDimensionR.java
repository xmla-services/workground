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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAnnotation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingHierarchy;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.PrivateDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.DimensionTypeEnum;

public record PrivateDimensionR(String name,
                                DimensionTypeEnum type,
                                String caption,
                                String description,
                                String foreignKey,
                                boolean highCardinality,
                                List<MappingAnnotation> annotations,
                                List<MappingHierarchy> hierarchies,
                                boolean visible,
                                List<MappingAnnotation> annotation,
                                String usagePrefix
                                )
        implements PrivateDimension {
}
