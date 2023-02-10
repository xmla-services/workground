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

@XmlType(name = "ColumnOlapType")
@XmlEnum
public enum ColumnOlapTypeEnum {

    /**
     * indicates that the object is a measure.
     */
    @XmlEnumValue("MEASURE")
    MEASURE,
    /**
     *  indicates that the object is a
     *  dimension attribute.
     */
    @XmlEnumValue("ATTRIBUTE")
    ATTRIBUTE,
    /**
     * indicates that the object is a
     * column in a schema rowset table.
     */
    @XmlEnumValue("SCHEMA")
    SCHEMA;

    public static ColumnOlapTypeEnum fromValue(String v) {
        if (v == null) {
            return null;
        }
        for (ColumnOlapTypeEnum e : ColumnOlapTypeEnum.values()) {
            if (e.name().equals(v)) {
                return e;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("ColumnOlapTypeEnum Illegal argument ")
            .append(v).toString());
    }
}
