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
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CubeDimension", propOrder = {

})
public class CubeDimension {

    @XmlElement(name = "ID")
    protected String id;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Translations")
    protected CubeDimension.Translations translations;
    @XmlElement(name = "DimensionID", required = true)
    protected String dimensionID;
    @XmlElement(name = "Visible")
    protected Boolean visible;
    @XmlElement(name = "AllMemberAggregationUsage")
    protected String allMemberAggregationUsage;
    @XmlElement(name = "HierarchyUniqueNameStyle")
    protected String hierarchyUniqueNameStyle;
    @XmlElement(name = "MemberUniqueNameStyle")
    protected String memberUniqueNameStyle;
    @XmlElement(name = "Attributes")
    protected CubeDimension.Attributes attributes;
    @XmlElement(name = "Hierarchies")
    protected CubeDimension.Hierarchies hierarchies;
    @XmlElement(name = "Annotations")
    protected CubeDimension.Annotations annotations;

    public String getID() {
        return id;
    }

    public void setID(String value) {
        this.id = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public CubeDimension.Translations getTranslations() {
        return translations;
    }

    public void setTranslations(CubeDimension.Translations value) {
        this.translations = value;
    }

    public String getDimensionID() {
        return dimensionID;
    }

    public void setDimensionID(String value) {
        this.dimensionID = value;
    }

    public Boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean value) {
        this.visible = value;
    }

    public String getAllMemberAggregationUsage() {
        return allMemberAggregationUsage;
    }

    public void setAllMemberAggregationUsage(String value) {
        this.allMemberAggregationUsage = value;
    }

    public String getHierarchyUniqueNameStyle() {
        return hierarchyUniqueNameStyle;
    }

    public void setHierarchyUniqueNameStyle(String value) {
        this.hierarchyUniqueNameStyle = value;
    }

    public String getMemberUniqueNameStyle() {
        return memberUniqueNameStyle;
    }

    public void setMemberUniqueNameStyle(String value) {
        this.memberUniqueNameStyle = value;
    }

    public CubeDimension.Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(CubeDimension.Attributes value) {
        this.attributes = value;
    }

    public CubeDimension.Hierarchies getHierarchies() {
        return hierarchies;
    }

    public void setHierarchies(CubeDimension.Hierarchies value) {
        this.hierarchies = value;
    }

    public CubeDimension.Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(CubeDimension.Annotations value) {
        this.annotations = value;
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
    @XmlType(name = "", propOrder = {"attribute"})
    public static class Attributes {

        @XmlElement(name = "Attribute")
        protected List<CubeAttribute> attribute;

        public List<CubeAttribute> getAttribute() {
            return this.attribute;
        }

        public void setAttribute(List<CubeAttribute> attribute) {
            this.attribute = attribute;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"hierarchy"})
    public static class Hierarchies {

        @XmlElement(name = "Hierarchy")
        protected List<CubeHierarchy> hierarchy;

        public List<CubeHierarchy> getHierarchy() {
            return this.hierarchy;
        }

        public void setHierarchy(List<CubeHierarchy> hierarchy) {
            this.hierarchy = hierarchy;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"translation"})
    public static class Translations {

        @XmlElement(name = "Translation")
        protected List<Translation> translation;

        public List<Translation> getTranslation() {
            return this.translation;
        }

        public void setTranslation(List<Translation> translation) {
            this.translation = translation;
        }
    }

}
