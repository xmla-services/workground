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
@XmlType(name = "DimensionPermission", propOrder = {"attributePermissions", "write", "allowedRowsExpression"})
public class DimensionPermission extends Permission {

    @XmlElement(name = "AttributePermissions")
    protected DimensionPermission.AttributePermissions attributePermissions;
    @XmlElement(name = "Write")
    protected String write;
    @XmlElement(name = "AllowedRowsExpression", namespace = "http://schemas.microsoft" +
        ".com/analysisservices/2011/engine/300/300")
    protected String allowedRowsExpression;

    public DimensionPermission.AttributePermissions getAttributePermissions() {
        return attributePermissions;
    }

    public void setAttributePermissions(DimensionPermission.AttributePermissions value) {
        this.attributePermissions = value;
    }

    public String getWrite() {
        return write;
    }

    public void setWrite(String value) {
        this.write = value;
    }

    public String getAllowedRowsExpression() {
        return allowedRowsExpression;
    }

    public void setAllowedRowsExpression(String value) {
        this.allowedRowsExpression = value;
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
