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

@XmlType(name = "InterfaceName")
@XmlEnum
public enum InterfaceNameEnum {

    @XmlEnumValue("DATETIME")
    DATETIME,

    @XmlEnumValue("LOGICAL")
    LOGICAL,

    @XmlEnumValue("FILTER")
    FILTER;

    public static InterfaceNameEnum  fromValue(String v) {
        if (v == null) {
            return null;
        }
        for (InterfaceNameEnum c : InterfaceNameEnum.values()) {
            if (c.name().equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("InterfaceNameEnum Illegal argument ")
            .append(v).toString());
    }
}
