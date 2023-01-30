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
package org.eclipse.daanse.mdx.model;

public record NameObjectIdentifier(String name,
                                   Quoting quoting)
        implements ObjectIdentifier {

    public NameObjectIdentifier {
        if (name == null) {
            throw new IllegalArgumentException();
        }
        if (!(quoting == Quoting.QUOTED || quoting == Quoting.UNQUOTED)) {
            throw new IllegalArgumentException();
        }
    }
}
