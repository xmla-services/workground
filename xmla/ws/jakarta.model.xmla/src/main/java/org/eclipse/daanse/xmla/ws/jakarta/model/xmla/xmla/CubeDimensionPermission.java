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
@XmlType(name = "CubeDimensionPermission", propOrder = {

})
public class CubeDimensionPermission {

    @XmlElement(name = "CubeDimensionID", required = true)
    protected String cubeDimensionID;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Read")
    protected String read;
    @XmlElement(name = "Write")
    protected String write;
    @XmlElement(name = "AttributePermissions")
    protected CubeDimensionPermission.AttributePermissions attributePermissions;
    @XmlElement(name = "Annotations")
    protected Annotations annotations;

    public String getCubeDimensionID() {
        return cubeDimensionID;
    }

    public void setCubeDimensionID(String value) {
        this.cubeDimensionID = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String value) {
        this.read = value;
    }

    public String getWrite() {
        return write;
    }

    public void setWrite(String value) {
        this.write = value;
    }

    public CubeDimensionPermission.AttributePermissions getAttributePermissions() {
        return attributePermissions;
    }

    public void setAttributePermissions(CubeDimensionPermission.AttributePermissions value) {
        this.attributePermissions = value;
    }

    public Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Annotations value) {
        this.annotations = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"attributePermission"})
    public static class AttributePermissions {

        @XmlElement(name = "AttributePermission")
        protected List<AttributePermission> attributePermission;

        public List<AttributePermission> getAttributePermission() {
            return this.attributePermission;
        }

        public void setAttributePermission(List<AttributePermission> attributePermission) {
            this.attributePermission = attributePermission;
        }
    }

}
