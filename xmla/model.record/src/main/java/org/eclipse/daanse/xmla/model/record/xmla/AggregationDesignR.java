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

import org.eclipse.daanse.xmla.api.xmla.Aggregation;
import org.eclipse.daanse.xmla.api.xmla.AggregationDesign;
import org.eclipse.daanse.xmla.api.xmla.AggregationDesignDimension;
import org.eclipse.daanse.xmla.api.xmla.Annotation;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

public record AggregationDesignR(String name,
                                 String id,
                                 Instant createdTimestamp,
                                 Instant lastSchemaUpdate,
                                 String description,
                                 List<Annotation> annotations,
                                 Long estimatedRows,
                                 List<AggregationDesignDimension> dimensions,
                                 List<Aggregation> aggregations,
                                 BigInteger estimatedPerformanceGain
                                 ) implements AggregationDesign{

}