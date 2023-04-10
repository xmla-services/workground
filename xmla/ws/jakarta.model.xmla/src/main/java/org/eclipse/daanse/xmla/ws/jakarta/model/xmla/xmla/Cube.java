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

import java.math.BigInteger;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Cube", propOrder = {

})
public class Cube {

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
    protected Cube.Annotations annotations;
    @XmlElement(name = "Language")
    protected BigInteger language;
    @XmlElement(name = "Collation")
    protected String collation;
    @XmlElement(name = "Translations")
    protected Cube.Translations translations;
    @XmlElement(name = "Dimensions", required = true)
    protected Cube.Dimensions dimensions;
    @XmlElement(name = "CubePermissions")
    protected Cube.CubePermissions cubePermissions;
    @XmlElement(name = "MdxScripts")
    protected Cube.MdxScripts mdxScripts;
    @XmlElement(name = "Perspectives")
    protected Cube.Perspectives perspectives;
    @XmlElement(name = "State")
    protected String state;
    @XmlElement(name = "DefaultMeasure")
    protected String defaultMeasure;
    @XmlElement(name = "Visible")
    protected Boolean visible;
    @XmlElement(name = "MeasureGroups", required = true)
    protected Cube.MeasureGroups measureGroups;
    @XmlElement(name = "Source")
    protected DataSourceViewBinding source;
    @XmlElement(name = "AggregationPrefix")
    protected String aggregationPrefix;
    @XmlElement(name = "ProcessingPriority")
    protected BigInteger processingPriority;
    @XmlElement(name = "StorageMode")
    protected Cube.StorageMode storageMode;
    @XmlElement(name = "ProcessingMode")
    protected String processingMode;
    @XmlElement(name = "ScriptCacheProcessingMode")
    protected String scriptCacheProcessingMode;
    @XmlElement(name = "ScriptErrorHandlingMode")
    protected String scriptErrorHandlingMode;
    @XmlElement(name = "DaxOptimizationMode", namespace = "http://schemas.microsoft" +
        ".com/analysisservices/2013/engine/800")
    protected String daxOptimizationMode;
    @XmlElement(name = "ProactiveCaching")
    protected ProactiveCaching proactiveCaching;
    @XmlElement(name = "Kpis")
    protected Cube.Kpis kpis;
    @XmlElement(name = "ErrorConfiguration")
    protected ErrorConfiguration errorConfiguration;
    @XmlElement(name = "Actions")
    protected Cube.Actions actions;
    @XmlElement(name = "StorageLocation")
    protected String storageLocation;
    @XmlElement(name = "EstimatedRows")
    protected Long estimatedRows;
    @XmlElement(name = "LastProcessed")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastProcessed;

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

    public Cube.Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Cube.Annotations value) {
        this.annotations = value;
    }

    public boolean isSetAnnotations() {
        return (this.annotations != null);
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

    public Cube.Translations getTranslations() {
        return translations;
    }

    public void setTranslations(Cube.Translations value) {
        this.translations = value;
    }

    public Cube.Dimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(Cube.Dimensions value) {
        this.dimensions = value;
    }

    public Cube.CubePermissions getCubePermissions() {
        return cubePermissions;
    }

    public void setCubePermissions(Cube.CubePermissions value) {
        this.cubePermissions = value;
    }

    public Cube.MdxScripts getMdxScripts() {
        return mdxScripts;
    }

    public void setMdxScripts(Cube.MdxScripts value) {
        this.mdxScripts = value;
    }

    public Cube.Perspectives getPerspectives() {
        return perspectives;
    }

    public void setPerspectives(Cube.Perspectives value) {
        this.perspectives = value;
    }

    public String getState() {
        return state;
    }

    public void setState(String value) {
        this.state = value;
    }

    public String getDefaultMeasure() {
        return defaultMeasure;
    }

    public void setDefaultMeasure(String value) {
        this.defaultMeasure = value;
    }

    public Boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean value) {
        this.visible = value;
    }

    public Cube.MeasureGroups getMeasureGroups() {
        return measureGroups;
    }

    public void setMeasureGroups(Cube.MeasureGroups value) {
        this.measureGroups = value;
    }

    public DataSourceViewBinding getSource() {
        return source;
    }

    public void setSource(DataSourceViewBinding value) {
        this.source = value;
    }

    public String getAggregationPrefix() {
        return aggregationPrefix;
    }

    public void setAggregationPrefix(String value) {
        this.aggregationPrefix = value;
    }

    public BigInteger getProcessingPriority() {
        return processingPriority;
    }

    public void setProcessingPriority(BigInteger value) {
        this.processingPriority = value;
    }

    public Cube.StorageMode getStorageMode() {
        return storageMode;
    }

    public void setStorageMode(Cube.StorageMode value) {
        this.storageMode = value;
    }

    public String getProcessingMode() {
        return processingMode;
    }

    public void setProcessingMode(String value) {
        this.processingMode = value;
    }

    public String getScriptCacheProcessingMode() {
        return scriptCacheProcessingMode;
    }

    public void setScriptCacheProcessingMode(String value) {
        this.scriptCacheProcessingMode = value;
    }

    public String getScriptErrorHandlingMode() {
        return scriptErrorHandlingMode;
    }

    public void setScriptErrorHandlingMode(String value) {
        this.scriptErrorHandlingMode = value;
    }

    public String getDaxOptimizationMode() {
        return daxOptimizationMode;
    }

    public void setDaxOptimizationMode(String value) {
        this.daxOptimizationMode = value;
    }

    public ProactiveCaching getProactiveCaching() {
        return proactiveCaching;
    }

    public void setProactiveCaching(ProactiveCaching value) {
        this.proactiveCaching = value;
    }

    public Cube.Kpis getKpis() {
        return kpis;
    }

    public void setKpis(Cube.Kpis value) {
        this.kpis = value;
    }

    public ErrorConfiguration getErrorConfiguration() {
        return errorConfiguration;
    }

    public void setErrorConfiguration(ErrorConfiguration value) {
        this.errorConfiguration = value;
    }

    public Cube.Actions getActions() {
        return actions;
    }

    public void setActions(Cube.Actions value) {
        this.actions = value;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String value) {
        this.storageLocation = value;
    }

    public Long getEstimatedRows() {
        return estimatedRows;
    }

    public void setEstimatedRows(Long value) {
        this.estimatedRows = value;
    }

    public XMLGregorianCalendar getLastProcessed() {
        return lastProcessed;
    }

    public void setLastProcessed(XMLGregorianCalendar value) {
        this.lastProcessed = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"action"})
    public static class Actions {

        @XmlElement(name = "Action")
        protected List<Action> action;

        public List<Action> getAction() {
            return this.action;
        }

        public void setAction(List<Action> action) {
            this.action = action;
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
    @XmlType(name = "", propOrder = {"cubePermission"})
    public static class CubePermissions {

        @XmlElement(name = "CubePermission")
        protected List<CubePermission> cubePermission;

        public List<CubePermission> getCubePermission() {
            return this.cubePermission;
        }

        public void setCubePermission(List<CubePermission> cubePermission) {
            this.cubePermission = cubePermission;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"dimension"})
    public static class Dimensions {

        @XmlElement(name = "Dimension", required = true)
        protected List<CubeDimension> dimension;

        public List<CubeDimension> getDimension() {
            return this.dimension;
        }

        public void setDimension(List<CubeDimension> dimension) {
            this.dimension = dimension;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"kpi"})
    public static class Kpis {

        @XmlElement(name = "Kpi")
        protected List<Kpi> kpi;

        public List<Kpi> getKpi() {
            return this.kpi;
        }

        public void setKpi(List<Kpi> kpi) {
            this.kpi = kpi;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"mdxScript"})
    public static class MdxScripts {

        @XmlElement(name = "MdxScript")
        protected List<MdxScript> mdxScript;

        public List<MdxScript> getMdxScript() {
            return this.mdxScript;
        }

        public void setMdxScript(List<MdxScript> mdxScript) {
            this.mdxScript = mdxScript;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"measureGroup"})
    public static class MeasureGroups {

        @XmlElement(name = "MeasureGroup")
        protected List<MeasureGroup> measureGroup;

        public List<MeasureGroup> getMeasureGroup() {
            return this.measureGroup;
        }

        public void setMeasureGroup(List<MeasureGroup> measureGroup) {
            this.measureGroup = measureGroup;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"perspective"})
    public static class Perspectives {

        @XmlElement(name = "Perspective")
        protected List<Perspective> perspective;

        public List<Perspective> getPerspective() {
            return this.perspective;
        }

        public void setPerspective(List<Perspective> perspective) {
            this.perspective = perspective;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"value"})
    public static class StorageMode {

        @XmlValue
        protected CubeStorageModeEnumType value;
        @XmlAttribute(name = "valuens")
        protected String valuens;

        public CubeStorageModeEnumType getValue() {
            return value;
        }

        public void setValue(CubeStorageModeEnumType value) {
            this.value = value;
        }

        public String getValuens() {
            return valuens;
        }

        public void setValuens(String value) {
            this.valuens = value;
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
