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

public enum VisibilityEnum {

    VISIBLE(0x01),
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
        for (VisibilityEnum c : VisibilityEnum.values()) {
            if (c.value == vi) {
                return c;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("VisibilityEnum Illegal argument ")
            .append(v).toString());
    }
}
