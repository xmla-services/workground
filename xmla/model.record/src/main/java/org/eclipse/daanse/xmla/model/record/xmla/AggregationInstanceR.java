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

import org.eclipse.daanse.xmla.api.xmla.AggregationInstance;
import org.eclipse.daanse.xmla.api.xmla.AggregationInstanceDimension;
import org.eclipse.daanse.xmla.api.xmla.AggregationInstanceMeasure;
import org.eclipse.daanse.xmla.api.xmla.Annotations;
import org.eclipse.daanse.xmla.api.xmla.TabularBinding;

import java.util.List;

public record AggregationInstanceR(String id,
                                   String name,
                                   String aggregationType,
                                   TabularBinding source,
                                   AggregationInstance.Dimensions dimensions,
                                   AggregationInstance.Measures measures,
                                   Annotations annotations,
                                   String description
) implements AggregationInstance {


    public record DimensionsR(List<AggregationInstanceDimension> dimension) implements AggregationInstance.Dimensions {

    }

    public record MeasuresR(List<AggregationInstanceMeasure> measure) implements AggregationInstance.Measures {

    }

}
