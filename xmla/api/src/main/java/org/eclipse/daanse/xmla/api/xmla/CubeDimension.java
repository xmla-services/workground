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
package org.eclipse.daanse.xmla.api.xmla;

import java.util.List;

public interface CubeDimension {

    String id();

    String name();

    String description();

    CubeDimension.Translations translations();

    String dimensionID();

    Boolean visible();

    String allMemberAggregationUsage();

    String hierarchyUniqueNameStyle();

    String memberUniqueNameStyle();

    CubeDimension.Attributes attributes();

    CubeDimension.Hierarchies hierarchies();

    List<Annotation> annotations();

    public interface Attributes {

        List<CubeAttribute> attribute();

    }

    public interface Hierarchies {

        List<CubeHierarchy> hierarchy();

    }

    public interface Translations {

        List<Translation> translation();
    }

}
