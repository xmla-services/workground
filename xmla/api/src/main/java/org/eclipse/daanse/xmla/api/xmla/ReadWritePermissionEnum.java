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
 * A string that specifies whether the role has permission to read
 * metadata or data from the object or any of its contained
 * objects.
 * "None" implies no read access to object metadata or
 * data.<99>
 * "Allowed" implies full read access to object metadata or data.
 */
public enum ReadWritePermissionEnum {

    /**
     * implies no read access to object metadata or
     * data.<99>
     */
    None,

    /**
     * implies full read access to object metadata or data.
     */
    Allowed;

    public static ReadWritePermissionEnum fromValue(String v) {
        if (v == null) {
            return None;
        }
        for (ReadWritePermissionEnum e : ReadWritePermissionEnum.values()) {
            if (e.name().equals(v)) {
                return e;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("ReadWritePermissionEnum Illegal argument ")
            .append(v).toString());
    }

}
