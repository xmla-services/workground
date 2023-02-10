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

@XmlType(name = "LevelUniqueSettings")
@XmlEnum
public enum LevelUniqueSettingsEnum {

    /**
    * Member
    * key columns establish
    * uniqueness.
    */
    @XmlEnumValue("0x00000001")
    KEY_COLUMNS(0x00000001),

    /**
     * Member
     * name columns establish
     * uniqueness.
     */
    @XmlEnumValue("0x00000002")
    NAME_COLUMNS(0x00000002);

    private final int value;

    LevelUniqueSettingsEnum(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }

    public static LevelUniqueSettingsEnum fromValue(String v) {
        if (v == null) {
            return null;
        }
        int vi = Integer.decode(v);
        for (LevelUniqueSettingsEnum c : LevelUniqueSettingsEnum.values()) {
            if (c.value == vi) {
                return c;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("LevelUniqueSettingsEnum Illegal argument ")
            .append(v).toString());
    }
}
