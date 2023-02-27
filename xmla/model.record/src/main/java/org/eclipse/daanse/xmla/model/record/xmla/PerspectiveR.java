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
import org.eclipse.daanse.xmla.api.xmla.Perspective;
import org.eclipse.daanse.xmla.api.xmla.PerspectiveAction;
import org.eclipse.daanse.xmla.api.xmla.PerspectiveCalculation;
import org.eclipse.daanse.xmla.api.xmla.PerspectiveDimension;
import org.eclipse.daanse.xmla.api.xmla.PerspectiveKpi;
import org.eclipse.daanse.xmla.api.xmla.PerspectiveMeasureGroup;
import org.eclipse.daanse.xmla.api.xmla.Translation;

import java.time.Instant;
import java.util.List;

public record PerspectiveR(String name,
                           String id,
                           Instant createdTimestamp,
                           Instant lastSchemaUpdate,
                           String description,
                           PerspectiveR.Annotations annotations,
                           PerspectiveR.Translations translations,
                           String defaultMeasure,
                           PerspectiveR.Dimensions dimensions,
                           PerspectiveR.MeasureGroups measureGroups,
                           PerspectiveR.Calculations calculations,
                           PerspectiveR.Kpis kpis,
                           PerspectiveR.Actions actions) implements Perspective {

    public record Actions(List<PerspectiveAction> action) implements Perspective.Actions {

    }

    public record Annotations(List<Annotation> annotation) implements Perspective.Annotations {

    }

    public record Calculations(List<PerspectiveCalculation> calculation) implements Perspective.Calculations {

    }

    public record Dimensions(List<PerspectiveDimension> dimension) implements Perspective.Dimensions {

    }

    public record Kpis(List<PerspectiveKpi> kpi) implements Perspective.Kpis {

    }

    record MeasureGroups(List<PerspectiveMeasureGroup> measureGroup) implements Perspective.MeasureGroups {

    }

    record Translations(List<Translation> translation) implements Perspective.Translations {

    }

}
