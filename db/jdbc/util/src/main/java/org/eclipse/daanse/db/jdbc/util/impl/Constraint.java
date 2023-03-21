/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.db.jdbc.util.impl;

public class Constraint {

    private final String name;
    private final String[] columnNames;
    private final boolean unique;

    public Constraint(String name, boolean unique, String... columnNames) {
        this.name = name;
        this.unique = unique;
        this.columnNames = columnNames;
    }

    public String getName() {
        return name;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public boolean isUnique() {
        return unique;
    }
}
