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

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Aggregation", propOrder = {

})
public class Aggregation {

    @XmlElement(name = "ID")
    protected String id;
    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "Dimensions")
    protected Aggregation.Dimensions dimensions;
    @XmlElement(name = "Annotations")
    protected Aggregation.Annotations annotations;
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

    public Aggregation.Dimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(Aggregation.Dimensions value) {
        this.dimensions = value;
    }

    public Aggregation.Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Aggregation.Annotations value) {
        this.annotations = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        protected List<AggregationDimension> dimension;

        public List<AggregationDimension> getDimension() {
            return this.dimension;
        }

        public void setDimension(List<AggregationDimension> dimension) {
            this.dimension = dimension;
        }
    }

}
