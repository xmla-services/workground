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
package org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine300.AttributeHierarchyProcessingState;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine300.DimensionAttributeVisualizationProperties;

import java.math.BigInteger;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DimensionAttribute", propOrder = {

})
public class DimensionAttribute {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "ID")
    protected String id;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Type")
    protected DimensionAttribute.Type type;
    @XmlElement(name = "Usage")
    protected String usage;
    @XmlElement(name = "Source")
    protected Binding source;
    @XmlElement(name = "EstimatedCount")
    protected Long estimatedCount;
    @XmlElement(name = "KeyColumns", required = true)
    protected DimensionAttribute.KeyColumns keyColumns;
    @XmlElement(name = "NameColumn")
    protected DataItem nameColumn;
    @XmlElement(name = "ValueColumn")
    protected DataItem valueColumn;
    @XmlElement(name = "Translations")
    protected DimensionAttribute.Translations translations;
    @XmlElement(name = "AttributeRelationships")
    protected DimensionAttribute.AttributeRelationships attributeRelationships;
    @XmlElement(name = "DiscretizationMethod")
    protected String discretizationMethod;
    @XmlElement(name = "DiscretizationBucketCount")
    protected BigInteger discretizationBucketCount;
    @XmlElement(name = "RootMemberIf")
    protected String rootMemberIf;
    @XmlElement(name = "OrderBy")
    protected String orderBy;
    @XmlElement(name = "DefaultMember")
    protected String defaultMember;
    @XmlElement(name = "OrderByAttributeID")
    protected String orderByAttributeID;
    @XmlElement(name = "SkippedLevelsColumn")
    protected DataItem skippedLevelsColumn;
    @XmlElement(name = "NamingTemplate")
    protected String namingTemplate;
    @XmlElement(name = "MembersWithData")
    protected String membersWithData;
    @XmlElement(name = "MembersWithDataCaption")
    protected String membersWithDataCaption;
    @XmlElement(name = "NamingTemplateTranslations")
    protected DimensionAttribute.NamingTemplateTranslations namingTemplateTranslations;
    @XmlElement(name = "CustomRollupColumn")
    protected DataItem customRollupColumn;
    @XmlElement(name = "CustomRollupPropertiesColumn")
    protected DataItem customRollupPropertiesColumn;
    @XmlElement(name = "UnaryOperatorColumn")
    protected DataItem unaryOperatorColumn;
    @XmlElement(name = "AttributeHierarchyOrdered")
    protected Boolean attributeHierarchyOrdered;
    @XmlElement(name = "MemberNamesUnique")
    protected Boolean memberNamesUnique;
    @XmlElement(name = "IsAggregatable")
    protected Boolean isAggregatable;
    @XmlElement(name = "AttributeHierarchyEnabled")
    protected Boolean attributeHierarchyEnabled;
    @XmlElement(name = "AttributeHierarchyOptimizedState")
    protected String attributeHierarchyOptimizedState;
    @XmlElement(name = "AttributeHierarchyVisible")
    protected Boolean attributeHierarchyVisible;
    @XmlElement(name = "AttributeHierarchyDisplayFolder")
    protected String attributeHierarchyDisplayFolder;
    @XmlElement(name = "KeyUniquenessGuarantee")
    protected Boolean keyUniquenessGuarantee;
    @XmlElement(name = "GroupingBehavior")
    protected String groupingBehavior;
    @XmlElement(name = "InstanceSelection")
    protected String instanceSelection;
    @XmlElement(name = "Annotations")
    protected DimensionAttribute.Annotations annotations;
    @XmlElement(name = "ProcessingState")
    protected String processingState;
    @XmlElement(name = "AttributeHierarchyProcessingState")
    @XmlSchemaType(name = "string")
    protected AttributeHierarchyProcessingState attributeHierarchyProcessingState;
    @XmlElement(name = "VisualizationProperties")
    protected DimensionAttributeVisualizationProperties visualizationProperties;
    @XmlElement(name = "ExtendedType")
    protected String extendedType;

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getID() {
        return id;
    }

    public void setID(String value) {
        this.id = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public DimensionAttribute.Type getType() {
        return type;
    }

    public void setType(DimensionAttribute.Type value) {
        this.type = value;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String value) {
        this.usage = value;
    }

    public Binding getSource() {
        return source;
    }

    public void setSource(Binding value) {
        this.source = value;
    }

    public Long getEstimatedCount() {
        return estimatedCount;
    }

    public void setEstimatedCount(Long value) {
        this.estimatedCount = value;
    }

    public DimensionAttribute.KeyColumns getKeyColumns() {
        return keyColumns;
    }

    public void setKeyColumns(DimensionAttribute.KeyColumns value) {
        this.keyColumns = value;
    }

    public DataItem getNameColumn() {
        return nameColumn;
    }

    public void setNameColumn(DataItem value) {
        this.nameColumn = value;
    }

    public DataItem getValueColumn() {
        return valueColumn;
    }

    public void setValueColumn(DataItem value) {
        this.valueColumn = value;
    }

    public DimensionAttribute.Translations getTranslations() {
        return translations;
    }

    public void setTranslations(DimensionAttribute.Translations value) {
        this.translations = value;
    }

    public DimensionAttribute.AttributeRelationships getAttributeRelationships() {
        return attributeRelationships;
    }

    public void setAttributeRelationships(DimensionAttribute.AttributeRelationships value) {
        this.attributeRelationships = value;
    }

    public String getDiscretizationMethod() {
        return discretizationMethod;
    }

    public void setDiscretizationMethod(String value) {
        this.discretizationMethod = value;
    }

    public BigInteger getDiscretizationBucketCount() {
        return discretizationBucketCount;
    }

    public void setDiscretizationBucketCount(BigInteger value) {
        this.discretizationBucketCount = value;
    }

    public String getRootMemberIf() {
        return rootMemberIf;
    }

    public void setRootMemberIf(String value) {
        this.rootMemberIf = value;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String value) {
        this.orderBy = value;
    }

    public String getDefaultMember() {
        return defaultMember;
    }

    public void setDefaultMember(String value) {
        this.defaultMember = value;
    }

    public String getOrderByAttributeID() {
        return orderByAttributeID;
    }

    public void setOrderByAttributeID(String value) {
        this.orderByAttributeID = value;
    }

    public DataItem getSkippedLevelsColumn() {
        return skippedLevelsColumn;
    }

    public void setSkippedLevelsColumn(DataItem value) {
        this.skippedLevelsColumn = value;
    }

    public String getNamingTemplate() {
        return namingTemplate;
    }

    public void setNamingTemplate(String value) {
        this.namingTemplate = value;
    }

    public String getMembersWithData() {
        return membersWithData;
    }

    public void setMembersWithData(String value) {
        this.membersWithData = value;
    }

    public String getMembersWithDataCaption() {
        return membersWithDataCaption;
    }

    public void setMembersWithDataCaption(String value) {
        this.membersWithDataCaption = value;
    }

    public DimensionAttribute.NamingTemplateTranslations getNamingTemplateTranslations() {
        return namingTemplateTranslations;
    }

    public void setNamingTemplateTranslations(DimensionAttribute.NamingTemplateTranslations value) {
        this.namingTemplateTranslations = value;
    }

    public DataItem getCustomRollupColumn() {
        return customRollupColumn;
    }

    public void setCustomRollupColumn(DataItem value) {
        this.customRollupColumn = value;
    }

    public DataItem getCustomRollupPropertiesColumn() {
        return customRollupPropertiesColumn;
    }

    public void setCustomRollupPropertiesColumn(DataItem value) {
        this.customRollupPropertiesColumn = value;
    }

    public DataItem getUnaryOperatorColumn() {
        return unaryOperatorColumn;
    }

    public void setUnaryOperatorColumn(DataItem value) {
        this.unaryOperatorColumn = value;
    }

    public Boolean isAttributeHierarchyOrdered() {
        return attributeHierarchyOrdered;
    }

    public void setAttributeHierarchyOrdered(Boolean value) {
        this.attributeHierarchyOrdered = value;
    }

    public Boolean isMemberNamesUnique() {
        return memberNamesUnique;
    }

    public void setMemberNamesUnique(Boolean value) {
        this.memberNamesUnique = value;
    }

    public Boolean isIsAggregatable() {
        return isAggregatable;
    }

    public void setIsAggregatable(Boolean value) {
        this.isAggregatable = value;
    }

    public Boolean isAttributeHierarchyEnabled() {
        return attributeHierarchyEnabled;
    }

    public void setAttributeHierarchyEnabled(Boolean value) {
        this.attributeHierarchyEnabled = value;
    }

    public String getAttributeHierarchyOptimizedState() {
        return attributeHierarchyOptimizedState;
    }

    public void setAttributeHierarchyOptimizedState(String value) {
        this.attributeHierarchyOptimizedState = value;
    }

    public Boolean isAttributeHierarchyVisible() {
        return attributeHierarchyVisible;
    }

    public void setAttributeHierarchyVisible(Boolean value) {
        this.attributeHierarchyVisible = value;
    }

    public String getAttributeHierarchyDisplayFolder() {
        return attributeHierarchyDisplayFolder;
    }

    public void setAttributeHierarchyDisplayFolder(String value) {
        this.attributeHierarchyDisplayFolder = value;
    }

    public Boolean isKeyUniquenessGuarantee() {
        return keyUniquenessGuarantee;
    }

    public void setKeyUniquenessGuarantee(Boolean value) {
        this.keyUniquenessGuarantee = value;
    }

    public String getGroupingBehavior() {
        return groupingBehavior;
    }

    public void setGroupingBehavior(String value) {
        this.groupingBehavior = value;
    }

    public String getInstanceSelection() {
        return instanceSelection;
    }

    public void setInstanceSelection(String value) {
        this.instanceSelection = value;
    }

    public DimensionAttribute.Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(DimensionAttribute.Annotations value) {
        this.annotations = value;
    }

    public String getProcessingState() {
        return processingState;
    }

    public void setProcessingState(String value) {
        this.processingState = value;
    }

    public AttributeHierarchyProcessingState getAttributeHierarchyProcessingState() {
        return attributeHierarchyProcessingState;
    }

    public void setAttributeHierarchyProcessingState(AttributeHierarchyProcessingState value) {
        this.attributeHierarchyProcessingState = value;
    }

    public DimensionAttributeVisualizationProperties getVisualizationProperties() {
        return visualizationProperties;
    }

    public void setVisualizationProperties(DimensionAttributeVisualizationProperties value) {
        this.visualizationProperties = value;
    }

    public String getExtendedType() {
        return extendedType;
    }

    public void setExtendedType(String value) {
        this.extendedType = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"annotation"})
    public static class Annotations {

        @XmlElement(name = "Annotation")
        protected List<Annotation> annotation;

        public List<Annotation> getAnnotation() {
            return this.annotation;
        }

        public void setAnnotation(List<Annotation> annotation) {
            this.annotation = annotation;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"attributeRelationship"})
    public static class AttributeRelationships {

        @XmlElement(name = "AttributeRelationship")
        protected List<AttributeRelationship> attributeRelationship;

        public List<AttributeRelationship> getAttributeRelationship() {
            return this.attributeRelationship;
        }

        public void setAttributeRelationship(List<AttributeRelationship> attributeRelationship) {
            this.attributeRelationship = attributeRelationship;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"keyColumn"})
    public static class KeyColumns {

        @XmlElement(name = "KeyColumn")
        protected List<DataItem> keyColumn;

        public List<DataItem> getKeyColumn() {
            return this.keyColumn;
        }

        public void setKeyColumn(List<DataItem> keyColumn) {
            this.keyColumn = keyColumn;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"namingTemplateTranslation"})
    public static class NamingTemplateTranslations {

        @XmlElement(name = "NamingTemplateTranslation")
        protected List<Translation> namingTemplateTranslation;

        public List<Translation> getNamingTemplateTranslation() {
            return this.namingTemplateTranslation;
        }

        public void setNamingTemplateTranslation(List<Translation> namingTemplateTranslation) {
            this.namingTemplateTranslation = namingTemplateTranslation;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"translation"})
    public static class Translations {

        @XmlElement(name = "Translation")
        protected List<AttributeTranslation> translation;

        public List<AttributeTranslation> getTranslation() {
            return this.translation;
        }

        public void setTranslation(List<AttributeTranslation> translation) {
            this.translation = translation;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"value"})
    public static class Type {

        @XmlValue
        protected DimensionAttributeTypeEnumType value;
        @XmlAttribute(name = "valuens")
        protected String valuens;

        public DimensionAttributeTypeEnumType getValue() {
            return value;
        }

        public void setValue(DimensionAttributeTypeEnumType value) {
            this.value = value;
        }

        public String getValuens() {
            return valuens;
        }

        public void setValuens(String value) {
            this.valuens = value;
        }
    }

}
