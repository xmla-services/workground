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
import org.eclipse.daanse.xmla.api.xmla.CubeAttribute;
import org.eclipse.daanse.xmla.api.xmla.CubeDimension;
import org.eclipse.daanse.xmla.api.xmla.CubeHierarchy;
import org.eclipse.daanse.xmla.api.xmla.Translation;

import java.util.List;

public record CubeDimensionR(String id,
                             String name,
                             String description,
                             CubeDimensionR.Translations translations,
                             String dimensionID,
                             Boolean visible,
                             String allMemberAggregationUsage,
                             String hierarchyUniqueNameStyle,
                             String memberUniqueNameStyle,
                             CubeDimensionR.Attributes attributes,
                             CubeDimensionR.Hierarchies hierarchies,
                             CubeDimensionR.Annotations annotations) implements CubeDimension {

    public record Annotations(List<Annotation> annotation) implements CubeDimension.Annotations {

    }

    public record Attributes(List<CubeAttribute> attribute) implements CubeDimension.Attributes {

    }

    public record Hierarchies(List<CubeHierarchy> hierarchy) implements CubeDimension.Hierarchies {

    }

    public record Translations(List<Translation> translation) implements CubeDimension.Translations {

    }

}
