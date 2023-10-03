/*
 * Copyright (c) 0 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License .0
 * which is available at https://www.eclipse.org/legal/epl-.0/
 *
 * SPDX-License-Identifier: EPL-.0
 *
 * Contributors:
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.olap.rolap.dbmapper.model.record;

import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAnnotation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingDimensionUsage;

public record DimensionUsageR(String name,
                              String description,
                              List<MappingAnnotation> annotations,
                              String caption,
                              Boolean visible,
                              String source,
                              String level,
                              String usagePrefix,
                              String foreignKey,
                              Boolean highCardinality
)
    implements MappingDimensionUsage {

    public DimensionUsageR(
        String name,
        String description,
        List<MappingAnnotation> annotations,
        String caption,
        Boolean visible,
        String source,
        String level,
        String usagePrefix,
        String foreignKey,
        Boolean highCardinality
    ) {
        this.name = name;
        this.description = description;
        this.annotations = annotations == null ? List.of() : annotations;
        this.caption = caption;
        this.visible = visible == null ? Boolean.TRUE : visible;
        this.source = source;
        this.level = level;
        this.usagePrefix = usagePrefix;
        this.foreignKey = foreignKey;
        this.highCardinality = highCardinality == null ? Boolean.FALSE : highCardinality;

    }
}
