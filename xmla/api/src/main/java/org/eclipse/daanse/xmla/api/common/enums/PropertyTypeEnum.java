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
 * A bitmask that specifies the
 * type of the property, as
 * follows:
 */
public enum PropertyTypeEnum {

    /**
     * Identifies a property of
     * a member.
     */
    property_member(1),

    /**
     * Identifies a property of
     * a cell.
     */
    property_cell(2),

    /**
     *  - Identifies an internal
     *      * property.
     */
    internal_property(4),

    /**
     * Identifies a property
     * which contains a binary
     * large object (BLOB).
     */
    BLOB_property(8);

    private final int value;

    PropertyTypeEnum(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }

    public static PropertyTypeEnum fromValue(String v) {
        int vi = Integer.valueOf(v);
        for (PropertyTypeEnum c : PropertyTypeEnum.values()) {
            if (c.value == vi) {
                return c;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("PropertyTypeEnum Illegal argument ")
            .append(v).toString());
    }
}
