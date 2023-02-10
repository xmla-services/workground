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

@XmlType(name = "PreferredQueryPatterns")
@XmlEnum
public enum PreferredQueryPatternsEnum {

    @XmlEnumValue("0x00")
    CROSS_JOIN(0x00),

    @XmlEnumValue("0x01")
    DRILL_DOWN_MEMBER(0x01);

    private final int value;

    PreferredQueryPatternsEnum(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }

    public static PreferredQueryPatternsEnum fromValue(int v) {
        for (PreferredQueryPatternsEnum c : PreferredQueryPatternsEnum.values()) {
            if (c.value == v) {
                return c;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("PreferredQueryPatternsEnum Illegal argument ")
            .append(v).toString());
    }
}
