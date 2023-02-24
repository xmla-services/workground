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

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

public interface MeasureGroup {

    String name();

    String id();

    Instant createdTimestamp();

    Instant lastSchemaUpdate();

    String description();

    MeasureGroup.Annotations annotations();

    Instant lastProcessed();

    MeasureGroup.Translations translations();

    String type();

    String state();

    MeasureGroup.Measures measures();

    String dataAggregation();

    MeasureGroupBinding source();

    MeasureGroup.StorageMode storageMode();

    String storageLocation();

    Boolean ignoreUnrelatedDimensions();

    ProactiveCaching proactiveCaching();

    Long estimatedRows();

    ErrorConfiguration errorConfiguration();

    Long estimatedSize();

    String processingMode();

    MeasureGroup.Dimensions dimensions();

    MeasureGroup.Partitions partitions();

    String aggregationPrefix();

    BigInteger processingPriority();

    MeasureGroup.AggregationDesigns aggregationDesigns();

    public interface AggregationDesigns {

        List<AggregationDesign> aggregationDesign();
    }

    public interface Annotations {

        List<Annotation> annotation();
    }

    public interface Dimensions {

        List<MeasureGroupDimension> dimension();

    }

    public interface Measures {

        List<Measure> measure();

    }

    public interface Partitions {

        List<Partition> partition();

    }

    public interface StorageMode {

        MeasureGroupStorageModeEnumType value();

        String valuens();

    }

    interface Translations {

        List<Translation> translation();

    }

}
