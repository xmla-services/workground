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

@XmlType(name = "LevelOrigin")
@XmlEnum
public enum LevelOriginEnum {

    /**
     * Identifies levels
     * in a user defined
     * hierarchy.
     */
    @XmlEnumValue("0x0001")
    USER_DEFINED(0x0001),

    /**
     * Identifies levels
     * in an attribute hierarchy.
     */
    @XmlEnumValue("0x0002")
    ATTRIBUTE(0x0002),

    /**
     * Identifies levels
     * in a key attribute
     * hierarchy.
     */
    @XmlEnumValue("0x0004")
    KEY(0x0004),

    /**
     * Identifies levels
     * in attribute hierarchies
     * that are not enabled
     */
    @XmlEnumValue("0x0008")
    NOT_ENABLED(0x0008);

    private final int value;

    LevelOriginEnum(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }

    public static LevelOriginEnum fromValue(String v) {
        if (v == null) {
            return null;
        }
        int vi = Integer.decode(v);
        for (LevelOriginEnum c : LevelOriginEnum.values()) {
            if (c.value == vi) {
                return c;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("LevelOriginEnum Illegal argument ")
            .append(v).toString());
    }
}