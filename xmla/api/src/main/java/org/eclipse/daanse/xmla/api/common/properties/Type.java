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
    String("xsd:string"), StringArray("xsd:string"), Array("xsd:string"), Enumeration("xsd:string"),
    EnumerationArray("xsd:string"), EnumString("xsd:string"), Boolean("xsd:boolean"),
    StringSometimesArray("xsd:string"), Integer("xsd:int"), UnsignedInteger("xsd:unsignedInt"), Double("xsd:double"),
    DateTime("xsd:dateTime"), Rowset(null), Short("xsd:short"), UUID("uuid"), UnsignedShort("xsd:unsignedShort"),
    Long("xsd:long"), UnsignedLong("xsd:unsignedLong");

    public final String columnType;

    Type(String columnType) {
        this.columnType = columnType;
    }

    boolean isEnum() {
        return this == Enumeration || this == EnumerationArray || this == EnumString;
    }

    String getName() {
        return this == String ? "string" : name();
    }
}
