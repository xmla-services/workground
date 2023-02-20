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
@XmlType(name = "Where_Attribute", propOrder = {

})
public class WhereAttribute {

    @XmlElement(name = "AttributeName", required = true)
    protected String attributeName;
    @XmlElement(name = "Keys")
    protected WhereAttribute.Keys keys;

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String value) {
        this.attributeName = value;
    }

    public WhereAttribute.Keys getKeys() {
        return keys;
    }

    public void setKeys(WhereAttribute.Keys value) {
        this.keys = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"key"})
    public static class Keys {

        @XmlElement(name = "Key")
        protected List<java.lang.Object> key;

        public List<java.lang.Object> getKey() {
            return this.key;
        }

        public void setKey(List<java.lang.Object> key) {
            this.key = key;
        }
    }

}
