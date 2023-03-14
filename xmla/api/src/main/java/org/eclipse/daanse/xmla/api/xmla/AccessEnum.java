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
package org.eclipse.daanse.xmla.api.xmla;

/**
 * An enumeration value that indicates the type of access being granted.
 * The enumeration values are as follows:
 * Read – Read access to the cell is permitted.
 * ReadContingent – ReadContingent access to the cell is permitted.
 * ReadWrite – ReadWrite access to the cell is permitted.
 */
public enum AccessEnum {

    Read,

    ReadContingent,

    ReadWrite;

    public static AccessEnum fromValue(String v) {
        if (v == null) {
            return Read;
        }
        for (AccessEnum e : AccessEnum.values()) {
            if (e.name().equals(v)) {
                return e;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("AccessEnum Illegal argument ")
            .append(v).toString());
    }

}
