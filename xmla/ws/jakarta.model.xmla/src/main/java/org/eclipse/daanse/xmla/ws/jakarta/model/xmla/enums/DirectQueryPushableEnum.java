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

@XmlType(name = "DirectQueryPushable")
@XmlEnum
public enum DirectQueryPushableEnum {

    /**
     * This function can be used in measure expressions.
     */
    @XmlEnumValue("0x1")
    MEASURE(0x1),

    /**
     * This function can be used in calculated column expressions.
     */
    @XmlEnumValue("0x2")
    CALCCOL(0x2);

    private final int value;

    DirectQueryPushableEnum(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }

    public static DirectQueryPushableEnum fromValue(int v) {
        for (DirectQueryPushableEnum c : DirectQueryPushableEnum.values()) {
            if (c.value == v) {
                return c;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("DirectQueryPushableEnum Illegal argument ")
            .append(v).toString());
    }
}
