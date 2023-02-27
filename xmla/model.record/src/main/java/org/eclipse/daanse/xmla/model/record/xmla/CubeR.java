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

import org.eclipse.daanse.xmla.api.xmla.Action;
import org.eclipse.daanse.xmla.api.xmla.Annotation;
import org.eclipse.daanse.xmla.api.xmla.Cube;
import org.eclipse.daanse.xmla.api.xmla.CubeDimension;
import org.eclipse.daanse.xmla.api.xmla.CubePermission;
import org.eclipse.daanse.xmla.api.xmla.CubeStorageModeEnumType;
import org.eclipse.daanse.xmla.api.xmla.Kpi;
import org.eclipse.daanse.xmla.api.xmla.MdxScript;
import org.eclipse.daanse.xmla.api.xmla.MeasureGroup;
import org.eclipse.daanse.xmla.api.xmla.Perspective;
import org.eclipse.daanse.xmla.api.xmla.Translation;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

public record CubeR(String name,
                    String id,
                    Instant createdTimestamp,
                    Instant lastSchemaUpdate,
                    String description,
                    CubeR.Annotations annotations,
                    BigInteger language,
                    String collation,
                    CubeR.Translations translations,
                    CubeR.Dimensions dimensions,
                    CubeR.CubePermissions cubePermissions,
                    CubeR.MdxScripts mdxScripts,
                    CubeR.Perspectives perspectives,
                    String state,
                    String defaultMeasure,
                    Boolean visible,
                    CubeR.MeasureGroups measureGroups,
                    DataSourceViewBindingR source,
                    String aggregationPrefix,
                    BigInteger processingPriority,
                    CubeR.StorageMode storageMode,
                    String processingMode,
                    String scriptCacheProcessingMode,
                    String scriptErrorHandlingMode,
                    String daxOptimizationMode,
                    ProactiveCachingR proactiveCaching,
                    CubeR.Kpis kpis,
                    ErrorConfigurationR errorConfiguration,
                    CubeR.Actions actions,
                    String storageLocation,
                    Long estimatedRows,
                    Instant lastProcessed) implements Cube {

    record Actions(List<Action> action) implements Cube.Actions {

    }

    record Annotations(List<Annotation> annotation) implements Cube.Annotations {

    }

    record CubePermissions(List<CubePermission> cubePermission) implements Cube.CubePermissions {

    }

    record Dimensions(List<CubeDimension> dimension) implements Cube.Dimensions {

    }

    record Kpis(List<Kpi> kpi) implements Cube.Kpis {

    }

    record MdxScripts(List<MdxScript> mdxScript) implements Cube.MdxScripts {

    }

    record MeasureGroups(List<MeasureGroup> measureGroup) implements Cube.MeasureGroups {

    }

    record Perspectives(List<Perspective> perspective) implements Cube.Perspectives {

    }

    record StorageMode(CubeStorageModeEnumType value,
                       String valuens) implements Cube.StorageMode {

    }

    record Translations(List<Translation> translation) implements Cube.Translations {

    }

}
