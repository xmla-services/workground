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

import java.math.BigInteger;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScalarMiningStructureColumn", propOrder = {

})
public class ScalarMiningStructureColumn {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "ID")
    protected String id;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Type", required = true)
    protected String type;
    @XmlElement(name = "Annotations")
    protected ScalarMiningStructureColumn.Annotations annotations;
    @XmlElement(name = "IsKey")
    protected Boolean isKey;
    @XmlElement(name = "Source")
    protected Binding source;
    @XmlElement(name = "Distribution")
    protected String distribution;
    @XmlElement(name = "ModelingFlags")
    protected ScalarMiningStructureColumn.ModelingFlags modelingFlags;
    @XmlElement(name = "Content", required = true)
    protected String content;
    @XmlElement(name = "ClassifiedColumns")
    protected ScalarMiningStructureColumn.ClassifiedColumns classifiedColumns;
    @XmlElement(name = "DiscretizationMethod")
    protected String discretizationMethod;
    @XmlElement(name = "DiscretizationBucketCount")
    protected BigInteger discretizationBucketCount;
    @XmlElement(name = "KeyColumns")
    protected ScalarMiningStructureColumn.KeyColumns keyColumns;
    @XmlElement(name = "NameColumn")
    protected DataItem nameColumn;
    @XmlElement(name = "Translations")
    protected ScalarMiningStructureColumn.Translations translations;

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

    public String getType() {
        return type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public ScalarMiningStructureColumn.Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(ScalarMiningStructureColumn.Annotations value) {
        this.annotations = value;
    }

    public Boolean isIsKey() {
        return isKey;
    }

    public void setIsKey(Boolean value) {
        this.isKey = value;
    }

    public Binding getSource() {
        return source;
    }

    public void setSource(Binding value) {
        this.source = value;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String value) {
        this.distribution = value;
    }

    public ScalarMiningStructureColumn.ModelingFlags getModelingFlags() {
        return modelingFlags;
    }

    public void setModelingFlags(ScalarMiningStructureColumn.ModelingFlags value) {
        this.modelingFlags = value;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String value) {
        this.content = value;
    }

    public ScalarMiningStructureColumn.ClassifiedColumns getClassifiedColumns() {
        return classifiedColumns;
    }

    public void setClassifiedColumns(ScalarMiningStructureColumn.ClassifiedColumns value) {
        this.classifiedColumns = value;
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

    public ScalarMiningStructureColumn.KeyColumns getKeyColumns() {
        return keyColumns;
    }

    public void setKeyColumns(ScalarMiningStructureColumn.KeyColumns value) {
        this.keyColumns = value;
    }

    public DataItem getNameColumn() {
        return nameColumn;
    }

    public void setNameColumn(DataItem value) {
        this.nameColumn = value;
    }

    public ScalarMiningStructureColumn.Translations getTranslations() {
        return translations;
    }

    public void setTranslations(ScalarMiningStructureColumn.Translations value) {
        this.translations = value;
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
    @XmlType(name = "", propOrder = {"classifiedColumnID"})
    public static class ClassifiedColumns {

        @XmlElement(name = "ClassifiedColumnID")
        protected List<String> classifiedColumnID;

        public List<String> getClassifiedColumnID() {
            return this.classifiedColumnID;
        }

        public void setClassifiedColumnID(List<String> classifiedColumnID) {
            this.classifiedColumnID = classifiedColumnID;
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
    @XmlType(name = "", propOrder = {"modelingFlag"})
    public static class ModelingFlags {

        @XmlElement(name = "ModelingFlag")
        protected List<MiningModelingFlag> modelingFlag;

        public List<MiningModelingFlag> getModelingFlag() {
            return this.modelingFlag;
        }

        public void setModelingFlag(List<MiningModelingFlag> modelingFlag) {
            this.modelingFlag = modelingFlag;
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
