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
package org.eclipse.daanse.xmla.api.common.enums;

/**
 * The type of the member
 */
public enum MemberTypeEnum {

    /**
     *  Is a regular member.
     */
    Regular_member(1),
    /**
     * Is the All member.
     */
    All_member(2),
    /**
     *  Is a measure.
     */
    Measure(3),
    /**
     * Is a formula.
     */
    Formula(4),
    /**
     *  Is of unknown type.
     */
    Unknown(0);

    private final int value;

    MemberTypeEnum(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }

    public static MemberTypeEnum fromValue(String v) {
        int vi = Integer.valueOf(v);
        for (MemberTypeEnum c : MemberTypeEnum.values()) {
            if (c.value == vi) {
                return c;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("MemberTypeEnum Illegal argument ")
            .append(v).toString());
    }
}
