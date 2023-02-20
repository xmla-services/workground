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
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MiningModel", propOrder = {

})
public class MiningModel {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "ID")
    protected String id;
    @XmlElement(name = "CreatedTimestamp")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdTimestamp;
    @XmlElement(name = "LastSchemaUpdate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastSchemaUpdate;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Annotations")
    protected MiningModel.Annotations annotations;
    @XmlElement(name = "Algorithm", required = true)
    protected String algorithm;
    @XmlElement(name = "LastProcessed")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastProcessed;
    @XmlElement(name = "AlgorithmParameters")
    protected MiningModel.AlgorithmParameters algorithmParameters;
    @XmlElement(name = "AllowDrillThrough")
    protected Boolean allowDrillThrough;
    @XmlElement(name = "Translations")
    protected MiningModel.Translations translations;
    @XmlElement(name = "Columns", required = true)
    protected MiningModel.Columns columns;
    @XmlElement(name = "State")
    protected String state;
    @XmlElement(name = "FoldingParameters")
    protected FoldingParameters foldingParameters;
    @XmlElement(name = "Filter")
    protected String filter;
    @XmlElement(name = "MiningModelPermissions")
    protected MiningModel.MiningModelPermissions miningModelPermissions;
    @XmlElement(name = "Language")
    protected String language;
    @XmlElement(name = "Collation")
    protected String collation;

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

    public XMLGregorianCalendar getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(XMLGregorianCalendar value) {
        this.createdTimestamp = value;
    }

    public XMLGregorianCalendar getLastSchemaUpdate() {
        return lastSchemaUpdate;
    }

    public void setLastSchemaUpdate(XMLGregorianCalendar value) {
        this.lastSchemaUpdate = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public MiningModel.Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(MiningModel.Annotations value) {
        this.annotations = value;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String value) {
        this.algorithm = value;
    }

    public XMLGregorianCalendar getLastProcessed() {
        return lastProcessed;
    }

    public void setLastProcessed(XMLGregorianCalendar value) {
        this.lastProcessed = value;
    }

    public MiningModel.AlgorithmParameters getAlgorithmParameters() {
        return algorithmParameters;
    }

    public void setAlgorithmParameters(MiningModel.AlgorithmParameters value) {
        this.algorithmParameters = value;
    }

    public Boolean isAllowDrillThrough() {
        return allowDrillThrough;
    }

    public void setAllowDrillThrough(Boolean value) {
        this.allowDrillThrough = value;
    }

    public MiningModel.Translations getTranslations() {
        return translations;
    }

    public void setTranslations(MiningModel.Translations value) {
        this.translations = value;
    }

    public MiningModel.Columns getColumns() {
        return columns;
    }

    public void setColumns(MiningModel.Columns value) {
        this.columns = value;
    }

    public String getState() {
        return state;
    }

    public void setState(String value) {
        this.state = value;
    }

    public FoldingParameters getFoldingParameters() {
        return foldingParameters;
    }

    public void setFoldingParameters(FoldingParameters value) {
        this.foldingParameters = value;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String value) {
        this.filter = value;
    }

    public MiningModel.MiningModelPermissions getMiningModelPermissions() {
        return miningModelPermissions;
    }

    public void setMiningModelPermissions(MiningModel.MiningModelPermissions value) {
        this.miningModelPermissions = value;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String value) {
        this.language = value;
    }

    public String getCollation() {
        return collation;
    }

    public void setCollation(String value) {
        this.collation = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"algorithmParameter"})
    public static class AlgorithmParameters {

        @XmlElement(name = "AlgorithmParameter")
        protected List<AlgorithmParameter> algorithmParameter;

        public List<AlgorithmParameter> getAlgorithmParameter() {
            return this.algorithmParameter;
        }

        public void setAlgorithmParameter(List<AlgorithmParameter> algorithmParameter) {
            this.algorithmParameter = algorithmParameter;
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
    @XmlType(name = "", propOrder = {"column"})
    public static class Columns {

        @XmlElement(name = "Column")
        protected List<MiningModelColumn> column;

        public List<MiningModelColumn> getColumn() {
            return this.column;
        }

        public void setColumn(List<MiningModelColumn> column) {
            this.column = column;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"miningModelPermission"})
    public static class MiningModelPermissions {

        @XmlElement(name = "MiningModelPermission")
        protected List<MiningModelPermission> miningModelPermission;

        public List<MiningModelPermission> getMiningModelPermission() {
            return this.miningModelPermission;
        }

        public void setMiningModelPermission(List<MiningModelPermission> miningModelPermission) {
            this.miningModelPermission = miningModelPermission;
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

}
