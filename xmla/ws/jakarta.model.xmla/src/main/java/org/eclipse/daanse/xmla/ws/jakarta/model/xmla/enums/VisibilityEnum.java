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

import java.util.EnumSet;

@XmlType(name = "Visibility")
@XmlEnum
public enum VisibilityEnum {

    @XmlEnumValue("0x01")
    VISIBLE(0x01),

    @XmlEnumValue("0x02")
    NOT_VISIBLE(0x02);


    private final int value;

    VisibilityEnum(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }

    public static VisibilityEnum fromValue(String v) {
        if (v == null) {
            return VISIBLE;
        }
        int vi = Integer.decode(v);
        return EnumSet.allOf(VisibilityEnum.class).stream().filter(e -> (e.value == vi)).findFirst()
            .orElseThrow(() -> new IllegalArgumentException(
                new StringBuilder("VisibilityEnum Illegal argument ").append(v)
                    .toString())
            );
    }
}
