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
@XmlType(name = "MeasureGroupAttribute", propOrder = {

})
public class MeasureGroupAttribute {

    @XmlElement(name = "AttributeID", required = true)
    protected String attributeID;
    @XmlElement(name = "KeyColumns")
    protected MeasureGroupAttribute.KeyColumns keyColumns;
    @XmlElement(name = "Type")
    protected String type;
    @XmlElement(name = "Annotations")
    protected MeasureGroupAttribute.Annotations annotations;

    public String getAttributeID() {
        return attributeID;
    }

    public void setAttributeID(String value) {
        this.attributeID = value;
    }

    public MeasureGroupAttribute.KeyColumns getKeyColumns() {
        return keyColumns;
    }

    public void setKeyColumns(MeasureGroupAttribute.KeyColumns value) {
        this.keyColumns = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public MeasureGroupAttribute.Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(MeasureGroupAttribute.Annotations value) {
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
    @XmlType(name = "", propOrder = {"keyColumn"})
    public static class KeyColumns {

        @XmlElement(name = "KeyColumn")
        protected List<DataItem> keyColumn;

        public List<DataItem> getKeyColumn() {
            return this.keyColumn;
        }

        public void setKeyColumn(List<DataItem> keyColumn) {
            this.keyColumn = keyColumn;
        }
    }

}
