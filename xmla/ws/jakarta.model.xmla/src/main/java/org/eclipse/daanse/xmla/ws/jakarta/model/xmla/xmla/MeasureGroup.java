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
@XmlType(name = "MeasureGroup", propOrder = {

})
public class MeasureGroup extends AbstractItem {

    @XmlElement(name = "LastProcessed")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastProcessed;
    @XmlElement(name = "Translations")
    protected MeasureGroup.Translations translations;
    @XmlElement(name = "Type")
    protected String type;
    @XmlElement(name = "State")
    protected String state;
    @XmlElement(name = "Measures", required = true)
    protected MeasureGroup.Measures measures;
    @XmlElement(name = "DataAggregation")
    protected String dataAggregation;
    @XmlElement(name = "Source")
    protected MeasureGroupBinding source;
    @XmlElement(name = "StorageMode")
    protected MeasureGroup.StorageMode storageMode;
    @XmlElement(name = "StorageLocation")
    protected String storageLocation;
    @XmlElement(name = "IgnoreUnrelatedDimensions")
    protected Boolean ignoreUnrelatedDimensions;
    @XmlElement(name = "ProactiveCaching")
    protected ProactiveCaching proactiveCaching;
    @XmlElement(name = "EstimatedRows")
    protected Long estimatedRows;
    @XmlElement(name = "ErrorConfiguration")
    protected ErrorConfiguration errorConfiguration;
    @XmlElement(name = "EstimatedSize")
    protected Long estimatedSize;
    @XmlElement(name = "ProcessingMode")
    protected String processingMode;
    @XmlElement(name = "Dimensions", required = true)
    protected MeasureGroup.Dimensions dimensions;
    @XmlElement(name = "Partitions")
    protected MeasureGroup.Partitions partitions;
    @XmlElement(name = "AggregationPrefix")
    protected String aggregationPrefix;
    @XmlElement(name = "ProcessingPriority")
    protected BigInteger processingPriority;
    @XmlElement(name = "AggregationDesigns")
    protected MeasureGroup.AggregationDesigns aggregationDesigns;

    public XMLGregorianCalendar getLastProcessed() {
        return lastProcessed;
    }

    public void setLastProcessed(XMLGregorianCalendar value) {
        this.lastProcessed = value;
    }

    public MeasureGroup.Translations getTranslations() {
        return translations;
    }

    public void setTranslations(MeasureGroup.Translations value) {
        this.translations = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public String getState() {
        return state;
    }

    public void setState(String value) {
        this.state = value;
    }

    public MeasureGroup.Measures getMeasures() {
        return measures;
    }

    public void setMeasures(MeasureGroup.Measures value) {
        this.measures = value;
    }

    public String getDataAggregation() {
        return dataAggregation;
    }

    public void setDataAggregation(String value) {
        this.dataAggregation = value;
    }

    public MeasureGroupBinding getSource() {
        return source;
    }

    public void setSource(MeasureGroupBinding value) {
        this.source = value;
    }

    public MeasureGroup.StorageMode getStorageMode() {
        return storageMode;
    }

    public void setStorageMode(MeasureGroup.StorageMode value) {
        this.storageMode = value;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String value) {
        this.storageLocation = value;
    }

    public Boolean isIgnoreUnrelatedDimensions() {
        return ignoreUnrelatedDimensions;
    }

    public void setIgnoreUnrelatedDimensions(Boolean value) {
        this.ignoreUnrelatedDimensions = value;
    }

    public ProactiveCaching getProactiveCaching() {
        return proactiveCaching;
    }

    public void setProactiveCaching(ProactiveCaching value) {
        this.proactiveCaching = value;
    }

    public Long getEstimatedRows() {
        return estimatedRows;
    }

    public void setEstimatedRows(Long value) {
        this.estimatedRows = value;
    }

    public ErrorConfiguration getErrorConfiguration() {
        return errorConfiguration;
    }

    public void setErrorConfiguration(ErrorConfiguration value) {
        this.errorConfiguration = value;
    }

    public Long getEstimatedSize() {
        return estimatedSize;
    }

    public void setEstimatedSize(Long value) {
        this.estimatedSize = value;
    }

    public String getProcessingMode() {
        return processingMode;
    }

    public void setProcessingMode(String value) {
        this.processingMode = value;
    }

    public MeasureGroup.Dimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(MeasureGroup.Dimensions value) {
        this.dimensions = value;
    }

    public MeasureGroup.Partitions getPartitions() {
        return partitions;
    }

    public void setPartitions(MeasureGroup.Partitions value) {
        this.partitions = value;
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

    public MeasureGroup.AggregationDesigns getAggregationDesigns() {
        return aggregationDesigns;
    }

    public void setAggregationDesigns(MeasureGroup.AggregationDesigns value) {
        this.aggregationDesigns = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"aggregationDesign"})
    public static class AggregationDesigns {

        @XmlElement(name = "AggregationDesign")
        protected List<AggregationDesign> aggregationDesign;

        public List<AggregationDesign> getAggregationDesign() {
            return this.aggregationDesign;
        }

        public void setAggregationDesign(List<AggregationDesign> aggregationDesign) {
            this.aggregationDesign = aggregationDesign;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"dimension"})
    public static class Dimensions {

        @XmlElement(name = "Dimension", required = true)
        protected List<MeasureGroupDimension> dimension;

        public List<MeasureGroupDimension> getDimension() {
            return this.dimension;
        }

        public void setDimension(List<MeasureGroupDimension> dimension) {
            this.dimension = dimension;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"measure"})
    public static class Measures {

        @XmlElement(name = "Measure", required = true)
        protected List<Measure> measure;

        public List<Measure> getMeasure() {
            return this.measure;
        }

        public void setMeasure(List<Measure> measure) {
            this.measure = measure;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"partition"})
    public static class Partitions {

        @XmlElement(name = "Partition")
        protected List<Partition> partition;

        public List<Partition> getPartition() {
            return this.partition;
        }

        public void setPartition(List<Partition> partition) {
            this.partition = partition;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"value"})
    public static class StorageMode {

        @XmlValue
        protected MeasureGroupStorageModeEnumType value;
        @XmlAttribute(name = "valuens")
        protected String valuens;

        public MeasureGroupStorageModeEnumType getValue() {
            return value;
        }

        public void setValue(MeasureGroupStorageModeEnumType value) {
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
