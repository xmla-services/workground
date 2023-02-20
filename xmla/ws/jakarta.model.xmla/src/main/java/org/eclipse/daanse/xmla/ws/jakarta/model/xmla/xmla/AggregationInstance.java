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
@XmlType(name = "AggregationInstance", propOrder = {

})
public class AggregationInstance {

    @XmlElement(name = "ID")
    protected String id;
    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "AggregationType", required = true)
    protected String aggregationType;
    @XmlElement(name = "Source")
    protected TabularBinding source;
    @XmlElement(name = "Dimensions")
    protected AggregationInstance.Dimensions dimensions;
    @XmlElement(name = "Measures")
    protected AggregationInstance.Measures measures;
    @XmlElement(name = "Annotations")
    protected AggregationInstance.Annotations annotations;
    @XmlElement(name = "Description")
    protected String description;

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

    public String getAggregationType() {
        return aggregationType;
    }

    public void setAggregationType(String value) {
        this.aggregationType = value;
    }

    public TabularBinding getSource() {
        return source;
    }

    public void setSource(TabularBinding value) {
        this.source = value;
    }

    public boolean isSetSource() {
        return (this.source != null);
    }

    public AggregationInstance.Dimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(AggregationInstance.Dimensions value) {
        this.dimensions = value;
    }

    public AggregationInstance.Measures getMeasures() {
        return measures;
    }

    public void setMeasures(AggregationInstance.Measures value) {
        this.measures = value;
    }

    public AggregationInstance.Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(AggregationInstance.Annotations value) {
        this.annotations = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
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
        protected List<AggregationInstanceDimension> dimension;

        public List<AggregationInstanceDimension> getDimension() {
            return this.dimension;
        }

        public void setDimension(List<AggregationInstanceDimension> dimension) {
            this.dimension = dimension;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"measure"})
    public static class Measures {

        @XmlElement(name = "Measure")
        protected List<AggregationInstanceMeasure> measure;

        public List<AggregationInstanceMeasure> getMeasure() {
            return this.measure;
        }

        public void setMeasure(List<AggregationInstanceMeasure> measure) {
            this.measure = measure;
        }
    }

}
