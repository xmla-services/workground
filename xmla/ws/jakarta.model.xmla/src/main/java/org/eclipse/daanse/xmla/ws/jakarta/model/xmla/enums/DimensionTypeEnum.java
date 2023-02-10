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

@XmlType(name = "DimensionType")
@XmlEnum
public enum DimensionTypeEnum {

    @XmlEnumValue("0")
    UNKNOWN(0),
    @XmlEnumValue("1")
    TIME(1),
    @XmlEnumValue("2")
    MEASURE(2),
    @XmlEnumValue("3")
    OTHER(3),
    @XmlEnumValue("5")
    QUANTITATIVE(5),
    @XmlEnumValue("6")
    ACCOUNTS(6),
    @XmlEnumValue("7")
    CUSTOMERS(7),
    @XmlEnumValue("8")
    PRODUCTS(8),
    @XmlEnumValue("9")
    SCENARIO(9),
    @XmlEnumValue("10")
    UTILITY(10),
    @XmlEnumValue("11")
    CURRENCY(11),
    @XmlEnumValue("12")
    RATES(12),
    @XmlEnumValue("13")
    CHANNEL(13),
    @XmlEnumValue("14")
    PROMOTION(14),
    @XmlEnumValue("15")
    ORGANIZATION(15),
    @XmlEnumValue("16")
    BILL_OF_MATERIALS(16),
    @XmlEnumValue("17")
    GEOGRAPHY(17);

    private final int value;

    DimensionTypeEnum(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }

    public static DimensionTypeEnum fromValue(int v) {
        for (DimensionTypeEnum c : DimensionTypeEnum.values()) {
            if (c.value == v) {
                return c;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("CoordinateTypeEnum Illegal argument ")
            .append(v).toString());
    }
}
