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
import org.eclipse.daanse.xmla.api.xmla.Annotation;
import org.eclipse.daanse.xmla.api.xmla.Partition;
import org.eclipse.daanse.xmla.api.xmla.PartitionCurrentStorageModeEnumType;
import org.eclipse.daanse.xmla.api.xmla.PartitionStorageModeEnumType;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

public record PartitionR(String name,
                         String id,
                         Instant createdTimestamp,
                         Instant lastSchemaUpdate,
                         String description,
                         PartitionR.Annotations annotations,
                         TabularBindingR source,
                         BigInteger processingPriority,
                         String aggregationPrefix,
                         PartitionR.StorageMode storageMode,
                         String processingMode,
                         ErrorConfigurationR errorConfiguration,
                         String storageLocation,
                         String remoteDatasourceID,
                         String slice,
                         ProactiveCachingR proactiveCaching,
                         String type,
                         Long estimatedSize,
                         Long estimatedRows,
                         PartitionR.CurrentStorageMode currentStorageMode,
                         String aggregationDesignID,
                         PartitionR.AggregationInstances aggregationInstances,
                         DataSourceViewBindingR aggregationInstanceSource,
                         Instant lastProcessed,
                         String state,
                         Integer stringStoresCompatibilityLevel,
                         Integer currentStringStoresCompatibilityLevel,
                         String directQueryUsage) implements Partition {

    public record AggregationInstances(
        List<AggregationInstance> aggregationInstance) implements Partition.AggregationInstances {

    }

    public record Annotations(List<Annotation> annotation) implements Partition.Annotations {

    }

    public record CurrentStorageMode(PartitionCurrentStorageModeEnumType value,

                                     String valuens) implements Partition.CurrentStorageMode {

    }

    public record StorageMode(PartitionStorageModeEnumType value,
                              String valuens) implements Partition.StorageMode {

    }
}
