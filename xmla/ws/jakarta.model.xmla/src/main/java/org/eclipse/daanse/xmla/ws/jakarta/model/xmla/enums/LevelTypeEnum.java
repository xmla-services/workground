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

/**
 * The type of the level
 */
@XmlType(name = "LevelType")
@XmlEnum
public enum LevelTypeEnum {

    @XmlEnumValue("0x1014")
    Account(0x1014),

    @XmlEnumValue("0x0001")
    All(0x0001),

    @XmlEnumValue("0x1012")
    Bill_of_Material_Resource(0x1012),

    @XmlEnumValue("0x0002")
    Calculated(0x0002),

    @XmlEnumValue("0x1061")
    Channel(0x1061),

    @XmlEnumValue("0x1042")
    Company(0x1042),

    @XmlEnumValue("0x1052")
    Currency_Destination(0x1052),

    @XmlEnumValue("0x1051")
    Currency_Source(0x1051),

    @XmlEnumValue("0x1021")
    Customer(0x1021),

    @XmlEnumValue("0x1022")
    Customer_Group(0x1022),

    @XmlEnumValue("0x1023")
    Customer_Household(0x1023),

    @XmlEnumValue("0x2006")
    Geography_City(0x2006),

    @XmlEnumValue("0x2001")
    Geography_Continent(0x2001),

    @XmlEnumValue("0x2003")
    Geography_Country(0x2003),

    @XmlEnumValue("0x2005")
    Geography_County(0x2005),

    @XmlEnumValue("0x2008")
    Geography_Point(0x2008),

    @XmlEnumValue("0x2007")
    Postal_Code(0x2007),

    @XmlEnumValue("0x2002")
    Geography_Region(0x2002),

    @XmlEnumValue("0x2004")
    Geography_StateOrProvince(0x2004),

    @XmlEnumValue("0x1011")
    Organization_Unit(0x1011),

    @XmlEnumValue("0x1041")
    Person(0x1041),

    @XmlEnumValue("0x1031")
    Product(0x1031),

    @XmlEnumValue("0x1032")
    Product_Group(0x1032),

    @XmlEnumValue("0x1071")
    Promotion(0x1071),

    @XmlEnumValue("0x1013")
    Quantitative(0x1013),

    @XmlEnumValue("0x0000")
    Regular(0x0000),

    @XmlEnumValue("0x1062")
    Representative(0x1062),

    @XmlEnumValue("0x0008")
    Reserved1(0x0008),

    @XmlEnumValue("0x1015")
    Scenario(0x1015),

    @XmlEnumValue("0x0004")
    Time(0x0004),

    @XmlEnumValue("0x0204")
    Time_Days(0x0204),

    @XmlEnumValue("0x0024")
    Time_Half_Years(0x0024),

    @XmlEnumValue("0x0044")
    Time_Quarters(0x0044),

    @XmlEnumValue("0x0804")
    Time_Seconds(0x0804),

    @XmlEnumValue("0x1004")
    Time_Undefined(0x1004),

    @XmlEnumValue("0x0104")
    Time_Weeks(0x0104),

    @XmlEnumValue("0x0014")
    Time_Years(0x0014),

    @XmlEnumValue("0x1016")
    Utility(0x1016);

    private final int value;

    LevelTypeEnum(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }

    public static LevelTypeEnum fromValue(int v) {
        for (LevelTypeEnum c : LevelTypeEnum.values()) {
            if (c.value == v) {
                return c;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("LevelTypeEnum Illegal argument ")
            .append(v).toString());
    }
}
