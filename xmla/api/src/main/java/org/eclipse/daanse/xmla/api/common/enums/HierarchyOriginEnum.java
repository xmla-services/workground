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

public enum HierarchyOriginEnum {
    USER_DEFINED(0x0001), //Identifies user-defined hierarchies.
    ATTRIBUTE(0x0002), //Identifies attribute hierarchies.
    KEY(0x0004), //Identifies key attribute hierarchies.
    WITH_NO_ATTRIBUTE(0x0004), //Identifies attributes with no attribute hierarchies.
    DEFAULT(0x0003); //The default restriction


    private final int value;

    HierarchyOriginEnum(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }

    public static HierarchyOriginEnum fromValue(String v) {
        if (v == null) {
            return DEFAULT;
        }
        int vi = Integer.decode(v);
        for (HierarchyOriginEnum c : HierarchyOriginEnum.values()) {
            if (c.value == vi) {
                return c;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("HierarchyOriginEnum Illegal argument ")
            .append(v).toString());
    }
}
