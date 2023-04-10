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
@XmlType(name = "DataMiningMeasureGroupDimension", propOrder = {"cubeDimensionID", "annotations", "source",
    "caseCubeDimensionID"})
public class DataMiningMeasureGroupDimension extends MeasureGroupDimension {

    @XmlElement(name = "CubeDimensionID", required = true)
    protected String cubeDimensionID;
    @XmlElement(name = "Annotations")
    protected DataMiningMeasureGroupDimension.Annotations annotations;
    @XmlElement(name = "Source")
    protected MeasureGroupDimensionBinding source;
    @XmlElement(name = "CaseCubeDimensionID", required = true)
    protected String caseCubeDimensionID;

    public String getCubeDimensionID() {
        return cubeDimensionID;
    }

    public void setCubeDimensionID(String value) {
        this.cubeDimensionID = value;
    }

    public DataMiningMeasureGroupDimension.Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(DataMiningMeasureGroupDimension.Annotations value) {
        this.annotations = value;
    }

    public MeasureGroupDimensionBinding getSource() {
        return source;
    }

    public void setSource(MeasureGroupDimensionBinding value) {
        this.source = value;
    }

    public String getCaseCubeDimensionID() {
        return caseCubeDimensionID;
    }

    public void setCaseCubeDimensionID(String value) {
        this.caseCubeDimensionID = value;
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

}
