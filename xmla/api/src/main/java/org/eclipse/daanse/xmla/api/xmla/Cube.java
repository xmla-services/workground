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

public interface Cube {

    String name();

    String id();

    Instant createdTimestamp();

    Instant lastSchemaUpdate();

    String description();

    List<Annotation> annotations();

    BigInteger language();

    String collation();

    Cube.Translations translations();

    Cube.Dimensions dimensions();

    Cube.CubePermissions cubePermissions();

    Cube.MdxScripts mdxScripts();

    Cube.Perspectives perspectives();

    String state();

    String defaultMeasure();

    Boolean visible();

    Cube.MeasureGroups measureGroups();

    DataSourceViewBinding source();

    String aggregationPrefix();

    BigInteger processingPriority();

    Cube.StorageMode storageMode();

    String processingMode();

    String scriptCacheProcessingMode();

    String scriptErrorHandlingMode();

    String daxOptimizationMode();

    ProactiveCaching proactiveCaching();

    Cube.Kpis kpis();

    ErrorConfiguration errorConfiguration();

    Cube.Actions actions();

    String storageLocation();

    Long estimatedRows();

    Instant lastProcessed();

    interface Actions {

        List<Action> action();

    }

    interface CubePermissions {

        List<CubePermission> cubePermission();

    }

    interface Dimensions {

        List<CubeDimension> dimension();


    }

    interface Kpis {

        List<Kpi> kpi();

    }

    interface MdxScripts {

        List<MdxScript> mdxScript();


    }

    interface MeasureGroups {

        List<MeasureGroup> measureGroup();

    }

    interface Perspectives {

        List<Perspective> perspective();


    }

    interface StorageMode {

        CubeStorageModeEnumType value();

        String valuens();

    }

    interface Translations {

        List<Translation> translation();

    }

}
