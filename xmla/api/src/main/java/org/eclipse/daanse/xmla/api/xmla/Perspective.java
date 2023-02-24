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

import java.time.Instant;
import java.util.List;

public interface Perspective {

    String name();

    String id();

    Instant createdTimestamp();

    Instant lastSchemaUpdate();

    String description();

    Perspective.Annotations annotations();

    Perspective.Translations translations();

    String defaultMeasure();

    Perspective.Dimensions dimensions();

    Perspective.MeasureGroups measureGroups();

    Perspective.Calculations calculations();

    Perspective.Kpis kpis();

    Perspective.Actions actions();

    public interface Actions {

        List<PerspectiveAction> action();

    }

    public interface Annotations {

        List<Annotation> annotation();
    }

    public interface Calculations {

        List<PerspectiveCalculation> calculation();

    }

    public interface Dimensions {

        List<PerspectiveDimension> dimension();
    }

    public interface Kpis {

        List<PerspectiveKpi> kpi();
    }

    interface MeasureGroups {

        List<PerspectiveMeasureGroup> measureGroup();

    }

    interface Translations {

        List<Translation> translation();

    }

}
