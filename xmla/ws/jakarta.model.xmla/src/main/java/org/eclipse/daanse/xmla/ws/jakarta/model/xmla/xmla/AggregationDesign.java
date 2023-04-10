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

import javax.xml.datatype.XMLGregorianCalendar;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AggregationDesign", propOrder = {

})
public class AggregationDesign {

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
    protected AggregationDesign.Annotations annotations;
    @XmlElement(name = "EstimatedRows")
    protected Long estimatedRows;
    @XmlElement(name = "Dimensions")
    protected AggregationDesign.Dimensions dimensions;
    @XmlElement(name = "Aggregations")
    protected AggregationDesign.Aggregations aggregations;
    @XmlElement(name = "EstimatedPerformanceGain")
    protected Integer estimatedPerformanceGain;

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

    public AggregationDesign.Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(AggregationDesign.Annotations value) {
        this.annotations = value;
    }

    public Long getEstimatedRows() {
        return estimatedRows;
    }

    public void setEstimatedRows(Long value) {
        this.estimatedRows = value;
    }

    public AggregationDesign.Dimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(AggregationDesign.Dimensions value) {
        this.dimensions = value;
    }

    public AggregationDesign.Aggregations getAggregations() {
        return aggregations;
    }

    public void setAggregations(AggregationDesign.Aggregations value) {
        this.aggregations = value;
    }

    public Integer getEstimatedPerformanceGain() {
        return estimatedPerformanceGain;
    }

    public void setEstimatedPerformanceGain(Integer value) {
        this.estimatedPerformanceGain = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"aggregation"})
    public static class Aggregations {

        @XmlElement(name = "Aggregation")
        protected List<Aggregation> aggregation;

        public List<Aggregation> getAggregation() {
            return this.aggregation;
        }

        public void setAggregation(List<Aggregation> aggregation) {
            this.aggregation = aggregation;
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
    @XmlType(name = "", propOrder = {"dimension"})
    public static class Dimensions {

        @XmlElement(name = "Dimension")
        protected List<AggregationDesignDimension> dimension;

        public List<AggregationDesignDimension> getDimension() {
            return this.dimension;
        }

        public void setDimension(List<AggregationDesignDimension> dimension) {
            this.dimension = dimension;
        }
    }

}
