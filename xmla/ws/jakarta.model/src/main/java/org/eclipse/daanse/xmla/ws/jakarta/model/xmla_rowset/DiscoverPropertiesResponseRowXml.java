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
package org.eclipse.daanse.xmla.ws.jakarta.model.xmla_rowset;

import java.io.Serializable;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "row")
@XmlRootElement(name = "row")

public class DiscoverPropertiesResponseRowXml implements Serializable {

    @XmlTransient
    private final static long serialVersionUID = 1L;

    @XmlElement(name = "PropertyName", required = true)
    String propertyName;

    @XmlElement(name = "PropertyDescription", required = false)
    String propertyDescription;

    @XmlElement(name = "PropertyType", required = false)
    String propertyType;

    @XmlElement(name = "PropertyAccessType", required = true)
    String propertyAccessType;

    @XmlElement(name = "IsRequired", required = false)
    Boolean required;

    @XmlElement(name = "Value", required = false)
    String value;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyDescription() {
        return propertyDescription;
    }

    public void setPropertyDescription(String propertyDescription) {
        this.propertyDescription = propertyDescription;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getPropertyAccessType() {
        return propertyAccessType;
    }

    public void setPropertyAccessType(String propertyAccessType) {
        this.propertyAccessType = propertyAccessType;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
