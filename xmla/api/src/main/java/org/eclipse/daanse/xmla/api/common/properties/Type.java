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
package org.eclipse.daanse.xmla.api.common.properties;

enum Type {
    STRING("string", Types.XSD_STRING),
    STRING_ARRAY("StringArray", Types.XSD_STRING),
    ARRAY("Array", Types.XSD_STRING),
    ENUMERATION("Enumeration", Types.XSD_STRING),
    ENUMERATION_ARRAY("EnumerationArray", Types.XSD_STRING),
    ENUM_STRING("EnumString", Types.XSD_STRING),
    BOOLEAN("Boolean", "xsd:boolean"),
    STRING_SOMETIMES_ARRAY("StringSometimesArray", Types.XSD_STRING),
    INTEGER("Integer", "xsd:int"),
    UNSIGNED_INTEGER("UnsignedInteger", "xsd:unsignedInt"),
    DOUBLE("Double", "xsd:double"),
    DATE_TIME("DateTime", "xsd:dateTime"),
    ROW_SET("Rowset", null),
    SHORT("Short", "xsd:short"),
    UUID("UUID", "uuid"),
    UNSIGNED_SHORT("UnsignedShort", "xsd:unsignedShort"),
    LONG("Long", "xsd:long"),
    UNSIGNED_LONG("UnsignedLong", "xsd:unsignedLong");

    public final String columnType;
    public final String nameValue;

    Type(String nameValue, String columnType) {
        this.nameValue = nameValue;
        this.columnType = columnType;
    }

    boolean isEnum() {
        return this == ENUMERATION || this == ENUMERATION_ARRAY || this == ENUM_STRING;
    }

    String getNameValue() {
        return nameValue;
    }

    private static class Types {

        public static final String XSD_STRING = "xsd:string";
    }
}
