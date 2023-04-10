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

import java.util.List;

import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine300.HierarchyVisualizationProperties;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Hierarchy", propOrder = {

})
public class Hierarchy {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "ID")
    protected String id;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "ProcessingState", namespace = "http://schemas.microsoft.com/analysisservices/2011/engine/300")
    protected String processingState;
    @XmlElement(name = "StructureType", namespace = "http://schemas.microsoft.com/analysisservices/2011/engine/300")
    protected String structureType;
    @XmlElement(name = "DisplayFolder")
    protected String displayFolder;
    @XmlElement(name = "Translations")
    protected Hierarchy.Translations translations;
    @XmlElement(name = "AllMemberName")
    protected String allMemberName;
    @XmlElement(name = "AllMemberTranslations")
    protected Hierarchy.AllMemberTranslations allMemberTranslations;
    @XmlElement(name = "MemberNamesUnique")
    protected Boolean memberNamesUnique;
    @XmlElement(name = "MemberKeysUnique", namespace = "http://schemas.microsoft.com/analysisservices/2003/engine/2")
    protected String memberKeysUnique;
    @XmlElement(name = "AllowDuplicateNames")
    protected Boolean allowDuplicateNames;
    @XmlElement(name = "Levels", required = true)
    protected Hierarchy.Levels levels;
    @XmlElement(name = "Annotations")
    protected Hierarchy.Annotations annotations;
    @XmlElement(name = "VisualizationProperties")
    protected HierarchyVisualizationProperties visualizationProperties;

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

    public String getProcessingState() {
        return processingState;
    }

    public void setProcessingState(String value) {
        this.processingState = value;
    }

    public String getStructureType() {
        return structureType;
    }

    public void setStructureType(String value) {
        this.structureType = value;
    }

    public String getDisplayFolder() {
        return displayFolder;
    }

    public void setDisplayFolder(String value) {
        this.displayFolder = value;
    }

    public Hierarchy.Translations getTranslations() {
        return translations;
    }

    public void setTranslations(Hierarchy.Translations value) {
        this.translations = value;
    }

    public String getAllMemberName() {
        return allMemberName;
    }

    public void setAllMemberName(String value) {
        this.allMemberName = value;
    }

    public Hierarchy.AllMemberTranslations getAllMemberTranslations() {
        return allMemberTranslations;
    }

    public void setAllMemberTranslations(Hierarchy.AllMemberTranslations value) {
        this.allMemberTranslations = value;
    }

    public Boolean isMemberNamesUnique() {
        return memberNamesUnique;
    }

    public void setMemberNamesUnique(Boolean value) {
        this.memberNamesUnique = value;
    }

    public String getMemberKeysUnique() {
        return memberKeysUnique;
    }

    public void setMemberKeysUnique(String value) {
        this.memberKeysUnique = value;
    }

    public Boolean isAllowDuplicateNames() {
        return allowDuplicateNames;
    }

    public void setAllowDuplicateNames(Boolean value) {
        this.allowDuplicateNames = value;
    }

    public Hierarchy.Levels getLevels() {
        return levels;
    }

    public void setLevels(Hierarchy.Levels value) {
        this.levels = value;
    }

    public Hierarchy.Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Hierarchy.Annotations value) {
        this.annotations = value;
    }

    public HierarchyVisualizationProperties getVisualizationProperties() {
        return visualizationProperties;
    }

    public void setVisualizationProperties(HierarchyVisualizationProperties value) {
        this.visualizationProperties = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"allMemberTranslation"})
    public static class AllMemberTranslations {

        @XmlElement(name = "AllMemberTranslation")
        protected List<Translation> allMemberTranslation;

        public List<Translation> getAllMemberTranslation() {
            return this.allMemberTranslation;
        }

        public void setAllMemberTranslation(List<Translation> allMemberTranslation) {
            this.allMemberTranslation = allMemberTranslation;
        }
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
    @XmlType(name = "", propOrder = {"level"})
    public static class Levels {

        @XmlElement(name = "Level", required = true)
        protected List<Level> level;

        public List<Level> getLevel() {
            return this.level;
        }

        public void setLevel(List<Level> level) {
            this.level = level;
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
