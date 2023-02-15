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

@XmlType(name = "GroupingBehavior")
@XmlEnum
public enum GroupingBehaviorEnum {

    /**
     *Client applications are encouraged to group by each member of the hierarchy.
     */
    @XmlEnumValue("1")
    ENCOURAGED(1),

    /**
     *Client applications are discouraged from grouping by each member of the hierarchy.
     */
    @XmlEnumValue("2")
    DISCOURAGED(2);

    private final int value;

    GroupingBehaviorEnum(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }

    public static GroupingBehaviorEnum fromValue(int v) {
        for (GroupingBehaviorEnum c : GroupingBehaviorEnum.values()) {
            if (c.value == v) {
                return c;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("GroupingBehaviorEnum Illegal argument ")
            .append(v).toString());
    }
}
