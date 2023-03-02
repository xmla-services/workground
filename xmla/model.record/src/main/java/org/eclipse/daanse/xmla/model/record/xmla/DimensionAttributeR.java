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

import org.eclipse.daanse.xmla.api.engine300.AttributeHierarchyProcessingState;
import org.eclipse.daanse.xmla.api.engine300.DimensionAttributeVisualizationProperties;
import org.eclipse.daanse.xmla.api.xmla.Annotation;
import org.eclipse.daanse.xmla.api.xmla.AttributeRelationship;
import org.eclipse.daanse.xmla.api.xmla.AttributeTranslation;
import org.eclipse.daanse.xmla.api.xmla.DataItem;
import org.eclipse.daanse.xmla.api.xmla.DimensionAttribute;
import org.eclipse.daanse.xmla.api.xmla.DimensionAttributeTypeEnumType;
import org.eclipse.daanse.xmla.api.xmla.Translation;

import java.math.BigInteger;
import java.util.List;

public record DimensionAttributeR(String name,
                                  String id,
                                  String description,
                                  DimensionAttributeR.Type type,
                                  String usage,
                                  BindingR source,
                                  Long estimatedCount,
                                  DimensionAttributeR.KeyColumns keyColumns,
                                  DataItemR nameColumn,
                                  DataItemR valueColumn,
                                  DimensionAttributeR.Translations translations,
                                  DimensionAttributeR.AttributeRelationships attributeRelationships,
                                  String discretizationMethod,
                                  BigInteger discretizationBucketCount,
                                  String rootMemberIf,
                                  String orderBy,
                                  String defaultMember,
                                  String orderByAttributeID,
                                  DataItemR skippedLevelsColumn,
                                  String namingTemplate,
                                  String membersWithData,
                                  String membersWithDataCaption,
                                  DimensionAttributeR.NamingTemplateTranslations namingTemplateTranslations,
                                  DataItemR customRollupColumn,
                                  DataItemR customRollupPropertiesColumn,
                                  DataItemR unaryOperatorColumn,
                                  Boolean attributeHierarchyOrdered,
                                  Boolean memberNamesUnique,
                                  Boolean isAggregatable,
                                  Boolean attributeHierarchyEnabled,
                                  String attributeHierarchyOptimizedState,
                                  Boolean attributeHierarchyVisible,
                                  String attributeHierarchyDisplayFolder,
                                  Boolean keyUniquenessGuarantee,
                                  String groupingBehavior,
                                  String instanceSelection,
                                  List<Annotation> annotations,
                                  String processingState,
                                  AttributeHierarchyProcessingState attributeHierarchyProcessingState,
                                  DimensionAttributeVisualizationProperties visualizationProperties,
                                  String extendedType) implements DimensionAttribute {

    public record AttributeRelationships(
        List<AttributeRelationship> attributeRelationship) implements DimensionAttribute.AttributeRelationships {

    }

    public record KeyColumns(List<DataItem> keyColumn) implements DimensionAttribute.KeyColumns {

    }

    public record NamingTemplateTranslations(
        List<Translation> namingTemplateTranslation) implements DimensionAttribute.NamingTemplateTranslations {

    }

    public record Translations(List<AttributeTranslation> translation) implements DimensionAttribute.Translations {

    }

    public record Type(DimensionAttributeTypeEnumType value,
                       String valuens) implements DimensionAttribute.Type {

    }

}
