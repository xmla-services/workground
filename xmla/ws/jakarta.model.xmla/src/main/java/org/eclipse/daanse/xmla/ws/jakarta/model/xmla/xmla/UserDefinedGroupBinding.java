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
@XmlType(name = "UserDefinedGroupBinding", propOrder = {"attributeID", "groups"})
public class UserDefinedGroupBinding extends Binding {

    @XmlElement(name = "AttributeID", required = true)
    protected String attributeID;
    @XmlElement(name = "Groups")
    protected UserDefinedGroupBinding.Groups groups;

    public String getAttributeID() {
        return attributeID;
    }

    public void setAttributeID(String value) {
        this.attributeID = value;
    }

    public UserDefinedGroupBinding.Groups getGroups() {
        return groups;
    }

    public void setGroups(UserDefinedGroupBinding.Groups value) {
        this.groups = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"group"})
    public static class Groups {

        @XmlElement(name = "Group")
        protected List<Group> group;

        public List<Group> getGroup() {
            return this.group;
        }

        public void setGroup(List<Group> group) {
            this.group = group;
        }
    }

}
