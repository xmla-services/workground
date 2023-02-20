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
import java.math.BigInteger;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MiningStructure", propOrder = {

})
public class MiningStructure {

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
    protected MiningStructure.Annotations annotations;
    @XmlElement(name = "Source")
    protected Binding source;
    @XmlElement(name = "LastProcessed")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastProcessed;
    @XmlElement(name = "Translations")
    protected MiningStructure.Translations translations;
    @XmlElement(name = "Language")
    protected BigInteger language;
    @XmlElement(name = "Collation")
    protected String collation;
    @XmlElement(name = "ErrorConfiguration")
    protected ErrorConfiguration errorConfiguration;
    @XmlElement(name = "CacheMode")
    protected String cacheMode;
    @XmlElement(name = "HoldoutMaxPercent", namespace = "http://schemas.microsoft" +
        ".com/analysisservices/2008/engine/100/100")
    protected Integer holdoutMaxPercent;
    @XmlElement(name = "HoldoutMaxCases", namespace = "http://schemas.microsoft" +
        ".com/analysisservices/2008/engine/100/100")
    protected Integer holdoutMaxCases;
    @XmlElement(name = "HoldoutSeed", namespace = "http://schemas.microsoft.com/analysisservices/2008/engine/100/100")
    protected Integer holdoutSeed;
    @XmlElement(name = "HoldoutActualSize", namespace = "http://schemas.microsoft" +
        ".com/analysisservices/2008/engine/100/100")
    protected Integer holdoutActualSize;
    @XmlElement(name = "Columns", required = true)
    protected MiningStructure.Columns columns;
    @XmlElement(name = "State")
    protected String state;
    @XmlElement(name = "MiningStructurePermissions")
    protected MiningStructure.MiningStructurePermissions miningStructurePermissions;
    @XmlElement(name = "MiningModels")
    protected MiningStructure.MiningModels miningModels;

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

    public MiningStructure.Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(MiningStructure.Annotations value) {
        this.annotations = value;
    }

    public Binding getSource() {
        return source;
    }

    public void setSource(Binding value) {
        this.source = value;
    }

    public XMLGregorianCalendar getLastProcessed() {
        return lastProcessed;
    }

    public void setLastProcessed(XMLGregorianCalendar value) {
        this.lastProcessed = value;
    }

    public MiningStructure.Translations getTranslations() {
        return translations;
    }

    public void setTranslations(MiningStructure.Translations value) {
        this.translations = value;
    }

    public boolean isSetTranslations() {
        return (this.translations != null);
    }

    public BigInteger getLanguage() {
        return language;
    }

    public void setLanguage(BigInteger value) {
        this.language = value;
    }

    public String getCollation() {
        return collation;
    }

    public void setCollation(String value) {
        this.collation = value;
    }

    public ErrorConfiguration getErrorConfiguration() {
        return errorConfiguration;
    }

    public void setErrorConfiguration(ErrorConfiguration value) {
        this.errorConfiguration = value;
    }

    public String getCacheMode() {
        return cacheMode;
    }

    public void setCacheMode(String value) {
        this.cacheMode = value;
    }

    public Integer getHoldoutMaxPercent() {
        return holdoutMaxPercent;
    }

    public void setHoldoutMaxPercent(Integer value) {
        this.holdoutMaxPercent = value;
    }

    public Integer getHoldoutMaxCases() {
        return holdoutMaxCases;
    }

    public void setHoldoutMaxCases(Integer value) {
        this.holdoutMaxCases = value;
    }

    public Integer getHoldoutSeed() {
        return holdoutSeed;
    }

    public void setHoldoutSeed(Integer value) {
        this.holdoutSeed = value;
    }

    public Integer getHoldoutActualSize() {
        return holdoutActualSize;
    }

    public void setHoldoutActualSize(Integer value) {
        this.holdoutActualSize = value;
    }

    public MiningStructure.Columns getColumns() {
        return columns;
    }

    public void setColumns(MiningStructure.Columns value) {
        this.columns = value;
    }

    public String getState() {
        return state;
    }

    public void setState(String value) {
        this.state = value;
    }

    public MiningStructure.MiningStructurePermissions getMiningStructurePermissions() {
        return miningStructurePermissions;
    }

    public void setMiningStructurePermissions(MiningStructure.MiningStructurePermissions value) {
        this.miningStructurePermissions = value;
    }

    public MiningStructure.MiningModels getMiningModels() {
        return miningModels;
    }

    public void setMiningModels(MiningStructure.MiningModels value) {
        this.miningModels = value;
    }

    public boolean isSetMiningModels() {
        return (this.miningModels != null);
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

        @XmlElement(name = "Column", required = true)
        protected List<MiningStructureColumn> column;

        public List<MiningStructureColumn> getColumn() {
            return this.column;
        }

        public void setColumn(List<MiningStructureColumn> column) {
            this.column = column;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"miningModel"})
    public static class MiningModels {

        @XmlElement(name = "MiningModel")
        protected List<MiningModel> miningModel;

        public List<MiningModel> getMiningModel() {
            return this.miningModel;
        }

        public void setMiningModel(List<MiningModel> miningModel) {
            this.miningModel = miningModel;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"miningStructurePermission"})
    public static class MiningStructurePermissions {

        @XmlElement(name = "MiningStructurePermission")
        protected List<MiningStructurePermission> miningStructurePermission;

        public List<MiningStructurePermission> getMiningStructurePermission() {
            return this.miningStructurePermission;
        }

        public void setMiningStructurePermission(List<MiningStructurePermission> miningStructurePermission) {
            this.miningStructurePermission = miningStructurePermission;
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
