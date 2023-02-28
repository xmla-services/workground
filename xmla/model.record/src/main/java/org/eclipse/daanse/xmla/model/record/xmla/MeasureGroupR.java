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

import org.eclipse.daanse.xmla.api.xmla.AggregationDesign;
import org.eclipse.daanse.xmla.api.xmla.Annotation;
import org.eclipse.daanse.xmla.api.xmla.ErrorConfiguration;
import org.eclipse.daanse.xmla.api.xmla.Measure;
import org.eclipse.daanse.xmla.api.xmla.MeasureGroup;
import org.eclipse.daanse.xmla.api.xmla.MeasureGroupBinding;
import org.eclipse.daanse.xmla.api.xmla.MeasureGroupDimension;
import org.eclipse.daanse.xmla.api.xmla.MeasureGroupStorageModeEnumType;
import org.eclipse.daanse.xmla.api.xmla.Partition;
import org.eclipse.daanse.xmla.api.xmla.ProactiveCaching;
import org.eclipse.daanse.xmla.api.xmla.Translation;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

public record MeasureGroupR(String name,
                            String id,
                            Instant createdTimestamp,
                            Instant lastSchemaUpdate,
                            String description,
                            MeasureGroup.Annotations annotations,
                            Instant lastProcessed,
                            MeasureGroup.Translations translations,
                            String type,
                            String state,
                            MeasureGroup.Measures measures,
                            String dataAggregation,
                            MeasureGroupBinding source,
                            MeasureGroup.StorageMode storageMode,
                            String storageLocation,
                            Boolean ignoreUnrelatedDimensions,
                            ProactiveCaching proactiveCaching,
                            Long estimatedRows,
                            ErrorConfiguration errorConfiguration,
                            Long estimatedSize,
                            String processingMode,
                            MeasureGroup.Dimensions dimensions,
                            MeasureGroup.Partitions partitions,
                            String aggregationPrefix,
                            BigInteger processingPriority,
                            MeasureGroup.AggregationDesigns aggregationDesigns) implements MeasureGroup {

    public record AggregationDesigns(
        List<AggregationDesign> aggregationDesign) implements MeasureGroup.AggregationDesigns {

    }

    public record Annotations(List<Annotation> annotation) implements MeasureGroup.Annotations {

    }

    public record Dimensions(List<MeasureGroupDimension> dimension) implements MeasureGroup.Dimensions {

    }

    public record Measures(List<Measure> measure) implements MeasureGroup.Measures {

    }

    public record Partitions(List<Partition> partition) implements MeasureGroup.Partitions {

    }

    public record StorageMode(MeasureGroupStorageModeEnumType value,
                              String valuens) implements MeasureGroup.StorageMode {

    }

    public record Translations(List<Translation> translation) implements MeasureGroup.Translations {

    }

}
