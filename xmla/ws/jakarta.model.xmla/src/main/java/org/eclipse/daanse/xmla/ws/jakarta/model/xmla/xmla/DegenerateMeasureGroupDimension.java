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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DegenerateMeasureGroupDimension", propOrder = {"cubeDimensionID", "annotations", "source",
    "shareDimensionStorage"})
public class DegenerateMeasureGroupDimension extends MeasureGroupDimension {

    @XmlElement(name = "CubeDimensionID", required = true)
    protected String cubeDimensionID;
    @XmlElement(name = "Annotations")
    protected Annotations annotations;
    @XmlElement(name = "Source")
    protected MeasureGroupDimensionBinding source;
    @XmlElement(name = "ShareDimensionStorage", namespace = "http://schemas.microsoft" +
        ".com/analysisservices/2010/engine/200/200")
    protected String shareDimensionStorage;

    public String getCubeDimensionID() {
        return cubeDimensionID;
    }

    public void setCubeDimensionID(String value) {
        this.cubeDimensionID = value;
    }

    public Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Annotations value) {
        this.annotations = value;
    }

    public MeasureGroupDimensionBinding getSource() {
        return source;
    }

    public void setSource(MeasureGroupDimensionBinding value) {
        this.source = value;
    }

    public String getShareDimensionStorage() {
        return shareDimensionStorage;
    }

    public void setShareDimensionStorage(String value) {
        this.shareDimensionStorage = value;
    }

}
