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
 * The scope of the member. The member can be a
 * session-calculated member or a global-calculated
 * member. The column returns NULL for non-
 * calculated members.
 * This column can have one of the following
 */
@XmlType(name = "Scope")
@XmlEnum
public enum ScopeEnum {

    @XmlEnumValue("1")
    Global(1),

    @XmlEnumValue("2")
    Session(2);

    private final int value;

    ScopeEnum(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }

    public static ScopeEnum fromValue(String v) {
        int vi = Integer.valueOf(v);
        for (ScopeEnum c : ScopeEnum.values()) {
            if (c.value == vi) {
                return c;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("ScopeEnum Illegal argument ")
            .append(v).toString());
    }
}
