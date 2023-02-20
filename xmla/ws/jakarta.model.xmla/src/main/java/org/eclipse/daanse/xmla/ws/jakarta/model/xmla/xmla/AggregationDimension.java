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
@XmlType(name = "AggregationDimension", propOrder = {

})
public class AggregationDimension {

    @XmlElement(name = "CubeDimensionID", required = true)
    protected String cubeDimensionID;
    @XmlElement(name = "Attributes")
    protected AggregationDimension.Attributes attributes;
    @XmlElement(name = "Annotations")
    protected AggregationDimension.Annotations annotations;

    public String getCubeDimensionID() {
        return cubeDimensionID;
    }

    public void setCubeDimensionID(String value) {
        this.cubeDimensionID = value;
    }

    public AggregationDimension.Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(AggregationDimension.Attributes value) {
        this.attributes = value;
    }

    public AggregationDimension.Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(AggregationDimension.Annotations value) {
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
        protected List<AggregationAttribute> attribute;

        public List<AggregationAttribute> getAttribute() {
            return this.attribute;
        }

        public void setAttribute(List<AggregationAttribute> attribute) {
            this.attribute = attribute;
        }
    }

}
