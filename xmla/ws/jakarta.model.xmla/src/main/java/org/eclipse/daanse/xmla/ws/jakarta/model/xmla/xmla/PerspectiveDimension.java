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
@XmlType(name = "PerspectiveDimension", propOrder = {

})
public class PerspectiveDimension {

    @XmlElement(name = "CubeDimensionID", required = true)
    protected String cubeDimensionID;
    @XmlElement(name = "Attributes")
    protected PerspectiveDimension.Attributes attributes;
    @XmlElement(name = "Hierarchies")
    protected PerspectiveDimension.Hierarchies hierarchies;
    @XmlElement(name = "Annotations")
    protected Annotations annotations;

    public String getCubeDimensionID() {
        return cubeDimensionID;
    }

    public void setCubeDimensionID(String value) {
        this.cubeDimensionID = value;
    }

    public PerspectiveDimension.Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(PerspectiveDimension.Attributes value) {
        this.attributes = value;
    }

    public PerspectiveDimension.Hierarchies getHierarchies() {
        return hierarchies;
    }

    public void setHierarchies(PerspectiveDimension.Hierarchies value) {
        this.hierarchies = value;
    }

    public Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Annotations value) {
        this.annotations = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"attribute"})
    public static class Attributes {

        @XmlElement(name = "Attribute")
        protected List<PerspectiveAttribute> attribute;

        public List<PerspectiveAttribute> getAttribute() {
            return this.attribute;
        }

        public void setAttribute(List<PerspectiveAttribute> attribute) {
            this.attribute = attribute;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"hierarchy"})
    public static class Hierarchies {

        @XmlElement(name = "Hierarchy")
        protected List<PerspectiveHierarchy> hierarchy;

        public List<PerspectiveHierarchy> getHierarchy() {
            return this.hierarchy;
        }

        public void setHierarchy(List<PerspectiveHierarchy> hierarchy) {
            this.hierarchy = hierarchy;
        }
    }

}
