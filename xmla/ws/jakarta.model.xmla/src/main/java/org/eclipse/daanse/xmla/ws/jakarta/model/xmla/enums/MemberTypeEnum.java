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

/**
 * The type of the member
 */
@XmlType(name = "MemberType")
@XmlEnum
public enum MemberTypeEnum {

    /**
     *  Is a regular member.
     */
    @XmlEnumValue("1")
    Regular_member(1),

    /**
     * Is the All member.
     */
    @XmlEnumValue("2")
    All_member(2),

    /**
     *  Is a measure.
     */
    @XmlEnumValue("3")
    Measure(3),

    /**
     * Is a formula.
     */
    @XmlEnumValue("4")
    Formula(4),

    /**
     *  Is of unknown type.
     */
    @XmlEnumValue("0")
    Unknown(0);

    private final int value;

    MemberTypeEnum(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }

    public static MemberTypeEnum fromValue(int v) {
        for (MemberTypeEnum c : MemberTypeEnum.values()) {
            if (c.value == v) {
                return c;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("MemberTypeEnum Illegal argument ")
            .append(v).toString());
    }
}
