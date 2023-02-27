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

import org.eclipse.daanse.xmla.api.engine300_300.Relationships;
import org.eclipse.daanse.xmla.api.xmla.Annotation;
import org.eclipse.daanse.xmla.api.xmla.Dimension;
import org.eclipse.daanse.xmla.api.xmla.DimensionAttribute;
import org.eclipse.daanse.xmla.api.xmla.DimensionCurrentStorageModeEnumType;
import org.eclipse.daanse.xmla.api.xmla.DimensionPermission;
import org.eclipse.daanse.xmla.api.xmla.Hierarchy;
import org.eclipse.daanse.xmla.api.xmla.Translation;
import org.eclipse.daanse.xmla.api.xmla.UnknownMemberEnumType;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

public record DimensionR(String name,
                         String id,
                         Instant createdTimestamp,
                         Instant lastSchemaUpdate,
                         String description,
                         DimensionR.Annotations annotations,
                         BindingR source,
                         String miningModelID,
                         String type,
                         DimensionR.UnknownMember unknownMember,
                         String mdxMissingMemberMode,
                         ErrorConfigurationR errorConfiguration,
                         String storageMode,
                         Boolean writeEnabled,
                         BigInteger processingPriority,
                         Instant lastProcessed,
                         DimensionR.DimensionPermissions dimensionPermissions,
                         String dependsOnDimensionID,
                         BigInteger language,
                         String collation,
                         String unknownMemberName,
                         DimensionR.UnknownMemberTranslations unknownMemberTranslations,
                         String state,
                         ProactiveCachingR proactiveCaching,
                         String processingMode,
                         String processingGroup,
                         DimensionR.CurrentStorageMode currentStorageMode,
                         DimensionR.Translations translations,
                         DimensionR.Attributes attributes,
                         String attributeAllMemberName,
                         DimensionR.AttributeAllMemberTranslations attributeAllMemberTranslations,
                         DimensionR.Hierarchies hierarchies,
                         String processingRecommendation,
                         Relationships relationships,
                         Integer stringStoresCompatibilityLevel,
                         Integer currentStringStoresCompatibilityLevel) implements Dimension {

    record Annotations(List<Annotation> annotation) implements Dimension.Annotations {

    }

    record AttributeAllMemberTranslations(
        List<Translation> memberAllMemberTranslation) implements Dimension.AttributeAllMemberTranslations {

    }

    record Attributes(List<DimensionAttribute> attribute) implements Dimension.Attributes {

    }

    record CurrentStorageMode(DimensionCurrentStorageModeEnumType value,

                              String valuens) implements Dimension.CurrentStorageMode {

    }

    record DimensionPermissions(
        List<DimensionPermission> dimensionPermission) implements Dimension.DimensionPermissions {

    }

    record Hierarchies(List<Hierarchy> hierarchy) implements Dimension.Hierarchies {

    }

    record Translations(List<Translation> translation) implements Dimension.Translations {

    }

    record UnknownMember(UnknownMemberEnumType value,
                         String valuens) implements Dimension.UnknownMember {

    }

    record UnknownMemberTranslations(
        List<Translation> unknownMemberTranslation) implements Dimension.UnknownMemberTranslations {

    }

}
