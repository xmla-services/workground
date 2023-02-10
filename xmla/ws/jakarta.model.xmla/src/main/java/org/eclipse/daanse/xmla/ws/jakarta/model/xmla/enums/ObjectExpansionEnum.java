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
package org.eclipse.daanse.xmla.ws.jakarta.model.xmla.enums;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "ObjectExpansion")
@XmlEnum
public enum ObjectExpansionEnum {

    /**
     * Returns only the name/ID/timestamp/state requested for the requested objects and all descendant major objects recursively.
     */
    @XmlEnumValue("ReferenceOnly")
    ReferenceOnly,

    /**
     *  Expands the requested object with no references to contained objects (includes expanded minor contained objects).
     */
    @XmlEnumValue("ObjectProperties")
    ObjectProperties,

    /**
     * Same as ObjectProperties, but also returns the name, ID, and timestamp for contained major objects.
     */
    @XmlEnumValue("ExpandObject")
    ExpandObject,

    /**
     * Fully expands the requested object recursively to the bottom of every contained object.
     */
    @XmlEnumValue("ExpandFull")
    ExpandFull;

    public static ObjectExpansionEnum fromValue(String v) {
        if (v == null) {
            return null;
        }
        for (ObjectExpansionEnum e : ObjectExpansionEnum.values()) {
            if (e.name().equals(v)) {
                return e;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("ObjectExpansion Illegal argument ")
            .append(v).toString());
    }
}
