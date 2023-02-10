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
 * Applies only to a single member:
 */
public enum TreeOpEnum {

    /**
     * Returns all of the ancestors.
     */
    all(0x20),

    /**
     *  Returns only the immediate children.
     */
    children(0x01),

    /**
     * Returns members on the same level.
     */
    level(0x02),

    /**
     * Returns only the immediate parent.
     */
    parent(0x04),

    /**
     * Returns only itself.
     */
    itself(0x08),

    /**
     * Returns all of the descendants.
     */
    all_descendants(0x10);

    private final int value;

    TreeOpEnum(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }

    public static TreeOpEnum fromValue(String v) {
        if (v == null) {
            return null;
        }
        int vi = Integer.decode(v);
        for (TreeOpEnum c : TreeOpEnum.values()) {
            if (c.value == vi) {
                return c;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("TreeOpEnum Illegal argument ")
            .append(v).toString());
    }
}
