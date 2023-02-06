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

public enum DimensionTypeEnum {

    UNKNOWN(0),
    TIME(1),
    MEASURE(2),
    OTHER(3),
    QUANTITATIVE(5),
    ACCOUNTS(6),
    CUSTOMERS(7),
    PRODUCTS(8),
    SCENARIO(9),
    UTILITY(10),
    CURRENCY(11),
    RATES(12),
    CHANNEL(13),
    PROMOTION(14),
    ORGANIZATION(15),
    BILL_OF_MATERIALS(16),
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
