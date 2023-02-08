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
 * The type of the level
 */
public enum LevelTypeEnum {

    Account(0x1014),
    All(0x0001),
    Bill_of_Material_Resource(0x1012),
    Calculated(0x0002),
    Channel(0x1061),
    Company(0x1042),
    Currency_Destination(0x1052),
    Currency_Source(0x1051),
    Customer(0x1021),
    Customer_Group(0x1022),
    Customer_Household(0x1023),
    Geography_City(0x2006),
    Geography_Continent(0x2001),
    Geography_Country(0x2003),
    Geography_County(0x2005),
    Geography_Point(0x2008),
    Postal_Code(0x2007),
    Geography_Region(0x2002),
    Geography_StateOrProvince(0x2004),
    Organization_Unit(0x1011),
    Person(0x1041),
    Product(0x1031),
    Product_Group(0x1032),
    Promotion(0x1071),
    Quantitative(0x1013),
    Regular(0x0000),
    Representative(0x1062),
    Reserved1(0x0008),
    Scenario(0x1015),
    Time(0x0004),
    Time_Days(0x0204),
    Time_Half_Years(0x0024),
    Time_Quarters(0x0044),
    Time_Seconds(0x0804),
    Time_Undefined(0x1004),
    Time_Weeks(0x0104),
    Time_Years(0x0014),
    Utility(0x1016);

    private final int value;

    LevelTypeEnum(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }

    public static LevelTypeEnum fromValue(String v) {
        if (v == null) {
            return null;
        }
        int vi = Integer.decode(v);
        for (LevelTypeEnum c : LevelTypeEnum.values()) {
            if (c.value == vi) {
                return c;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("LevelTypeEnum Illegal argument ")
            .append(v).toString());
    }
}
