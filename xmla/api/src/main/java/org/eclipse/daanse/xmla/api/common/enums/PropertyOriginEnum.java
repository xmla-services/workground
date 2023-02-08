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
 * type of hierarchy to which the
 * property applies, as follows:
 */
public enum PropertyOriginEnum {

    /**
     *  Indicates the property
     *  is on a user defined
     *  hierarchy.
     */
    user_defined(1),
    /**
     *  Indicates the property
     *  is on an attribute
     *  hierarchy.
     */
    attribute_hierarchy(2),
    /**
     * Indicates the property
     * is on a key attribute
     * hierarchy.
     */
    key_attribute_hierarchy(4),

    /**
     * Indicates the property
     * is on an attribute hierarchy
     * that is not enabled.
     */
    attribute_hierarchy_not_enabled(8);

    private final int value;

    PropertyOriginEnum(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }

    public static PropertyOriginEnum fromValue(String v) {
        int vi = Integer.valueOf(v);
        for (PropertyOriginEnum c : PropertyOriginEnum.values()) {
            if (c.value == vi) {
                return c;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("PropertyOriginEnum Illegal argument ")
            .append(v).toString());
    }
}
