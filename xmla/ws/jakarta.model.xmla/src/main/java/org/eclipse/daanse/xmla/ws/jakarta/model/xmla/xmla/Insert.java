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
@XmlType(name = "Insert", propOrder = {

})
public class Insert {

    @XmlElement(name = "Object", required = true)
    protected Object object;
    @XmlElement(name = "Attributes")
    protected Insert.Attributes attributes;

    public Object getObject() {
        return object;
    }

    public void setObject(Object value) {
        this.object = value;
    }

    public Insert.Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Insert.Attributes value) {
        this.attributes = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"attribute"})
    public static class Attributes {

        @XmlElement(name = "Attribute")
        protected List<AttributeInsertUpdate> attribute;

        public List<AttributeInsertUpdate> getAttribute() {
            return this.attribute;
        }

        public void setAttribute(List<AttributeInsertUpdate> attribute) {
            this.attribute = attribute;
        }
    }

}
