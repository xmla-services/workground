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

public interface Partition {

    String name();

    String id();

    Instant createdTimestamp();

    Instant lastSchemaUpdate();

    String description();

    List<Annotation> annotations();

    TabularBinding source();

    BigInteger processingPriority();

    String aggregationPrefix();

    Partition.StorageMode storageMode();

    String processingMode();

    ErrorConfiguration errorConfiguration();

    String storageLocation();

    String remoteDatasourceID();

    String slice();

    ProactiveCaching proactiveCaching();

    String type();

    Long estimatedSize();

    Long estimatedRows();

    Partition.CurrentStorageMode currentStorageMode();

    String aggregationDesignID();

    Partition.AggregationInstances aggregationInstances();

    DataSourceViewBinding aggregationInstanceSource();

    Instant lastProcessed();

    String state();

    Integer stringStoresCompatibilityLevel();

    Integer currentStringStoresCompatibilityLevel();

    String directQueryUsage();

    public interface AggregationInstances {

        List<AggregationInstance> aggregationInstance();

    }

    public interface CurrentStorageMode {

        PartitionCurrentStorageModeEnumType value();

        String valuens();

    }

    public interface StorageMode {

        PartitionStorageModeEnumType value();

        String valuens();
    }

}
