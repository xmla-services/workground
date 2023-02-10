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

@XmlType(name = "Origin")
@XmlEnum
public enum OriginEnum {

    @XmlEnumValue("0x1")
    MSOLAP(0x1),
    @XmlEnumValue("0x2")
    UDF(0x2),
    @XmlEnumValue("0x3")
    RELATIONAL(0x3),
    @XmlEnumValue("0x4")
    SCALAR(0x4);

    private final int value;

    OriginEnum(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }

    public static OriginEnum fromValue(String v) {
        if (v == null) {
            return null;
        }
        int vi = Integer.valueOf(v);
        for (OriginEnum c : OriginEnum.values()) {
            if (c.value == vi) {
                return c;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("OriginEnum Illegal argument ")
            .append(v).toString());
    }
}
