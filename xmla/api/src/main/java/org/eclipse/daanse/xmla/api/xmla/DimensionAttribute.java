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

import org.eclipse.daanse.xmla.api.engine300.AttributeHierarchyProcessingState;
import org.eclipse.daanse.xmla.api.engine300.DimensionAttributeVisualizationProperties;

import java.math.BigInteger;
import java.util.List;

public interface DimensionAttribute {


    String name();

    String id();

    String description();

    DimensionAttribute.Type type();

    String usage();

    Binding source();

    Long estimatedCount();

    DimensionAttribute.KeyColumns keyColumns();

    DataItem nameColumn();

    DataItem valueColumn();

    DimensionAttribute.Translations translations();

    DimensionAttribute.AttributeRelationships attributeRelationships();

    String discretizationMethod();

    BigInteger discretizationBucketCount();

    String rootMemberIf();

    String orderBy();

    String defaultMember();

    String orderByAttributeID();

    DataItem skippedLevelsColumn();

    String namingTemplate();

    String membersWithData();

    String membersWithDataCaption();

    DimensionAttribute.NamingTemplateTranslations namingTemplateTranslations();

    DataItem customRollupColumn();

    DataItem customRollupPropertiesColumn();

    DataItem unaryOperatorColumn();

    Boolean attributeHierarchyOrdered();

    Boolean memberNamesUnique();

    Boolean isAggregatable();

    Boolean attributeHierarchyEnabled();

    String attributeHierarchyOptimizedState();

    Boolean attributeHierarchyVisible();

    String attributeHierarchyDisplayFolder();

    Boolean keyUniquenessGuarantee();

    String groupingBehavior();

    String instanceSelection();

    DimensionAttribute.Annotations annotations();

    String processingState();


    AttributeHierarchyProcessingState attributeHierarchyProcessingState();

    DimensionAttributeVisualizationProperties visualizationProperties();

    String extendedType();


    public interface Annotations {


        List<Annotation> annotation();


    }

    public interface AttributeRelationships {


        List<AttributeRelationship> attributeRelationship();

    }

    public interface KeyColumns {


        List<DataItem> keyColumn();

    }

    public interface NamingTemplateTranslations {


        List<Translation> namingTemplateTranslation();

    }

    public interface Translations {


        List<AttributeTranslation> translation();

    }

    public interface Type {

        DimensionAttributeTypeEnumType value();

        String valuens();

    }

}
