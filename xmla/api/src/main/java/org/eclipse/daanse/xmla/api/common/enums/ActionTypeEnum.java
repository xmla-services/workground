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

public enum ActionTypeEnum {

    URL(0x01), //Action type is URL.
    HTML(0x02), // Action type is HTML.
    STATEMENT(0x04), // Action type is Statement.
    DATASET(0x08), // Action type is Dataset.
    ROW_SET(0x10), // Action type is Rowset.
    COMMANDLINE(0x20), // Action type is Commandline.
    PROPRIETARY(0x40), // Action type is Proprietary.
    REPORT(0x80), // Action type is Report.
    DRILL_THROUGH(0x100); // Action type is DrillThrough.

    private final int value;

    ActionTypeEnum(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }

    public static ActionTypeEnum fromValue(String v) {
        if (v == null){
            return null;
        }
        int vi = Integer.decode(v);
        for (ActionTypeEnum c : ActionTypeEnum.values()) {
            if (c.value == vi) {
                return c;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("ActionTypeEnum Illegal argument ")
            .append(v).toString());
    }
}
