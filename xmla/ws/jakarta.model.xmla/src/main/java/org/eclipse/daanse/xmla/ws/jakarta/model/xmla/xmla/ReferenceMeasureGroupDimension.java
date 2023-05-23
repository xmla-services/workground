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
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReferenceMeasureGroupDimension", propOrder = {"cubeDimensionID", "annotations", "source",
    "intermediateCubeDimensionID", "intermediateGranularityAttributeID", "materialization", "processingState"})
public class ReferenceMeasureGroupDimension extends MeasureGroupDimension {

    @XmlElement(name = "CubeDimensionID", required = true)
    protected String cubeDimensionID;
    @XmlElementWrapper(name = "Annotations")
    @XmlElement(name = "Annotation", type = Annotation.class)
    protected List<Annotation> annotations;
    @XmlElement(name = "Source")
    protected MeasureGroupDimensionBinding source;
    @XmlElement(name = "IntermediateCubeDimensionID", required = true)
    protected String intermediateCubeDimensionID;
    @XmlElement(name = "IntermediateGranularityAttributeID", required = true)
    protected String intermediateGranularityAttributeID;
    @XmlElement(name = "Materialization")
    protected String materialization;
    @XmlElement(name = "ProcessingState", namespace = "http://schemas.microsoft" +
        ".com/analysisservices/2010/engine/200/200")
    protected String processingState;

    public String getCubeDimensionID() {
        return cubeDimensionID;
    }

    public void setCubeDimensionID(String value) {
        this.cubeDimensionID = value;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<Annotation> value) {
        this.annotations = value;
    }

    public MeasureGroupDimensionBinding getSource() {
        return source;
    }

    public void setSource(MeasureGroupDimensionBinding value) {
        this.source = value;
    }

    public String getIntermediateCubeDimensionID() {
        return intermediateCubeDimensionID;
    }

    public void setIntermediateCubeDimensionID(String value) {
        this.intermediateCubeDimensionID = value;
    }

    public String getIntermediateGranularityAttributeID() {
        return intermediateGranularityAttributeID;
    }

    public void setIntermediateGranularityAttributeID(String value) {
        this.intermediateGranularityAttributeID = value;
    }

    public String getMaterialization() {
        return materialization;
    }

    public void setMaterialization(String value) {
        this.materialization = value;
    }

    public String getProcessingState() {
        return processingState;
    }

    public void setProcessingState(String value) {
        this.processingState = value;
    }

}
