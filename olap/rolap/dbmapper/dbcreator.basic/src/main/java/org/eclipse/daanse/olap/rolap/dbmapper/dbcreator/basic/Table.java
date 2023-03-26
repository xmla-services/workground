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
package org.eclipse.daanse.olap.rolap.dbmapper.dbcreator.basic;

import org.eclipse.daanse.db.jdbc.util.impl.Column;
import org.eclipse.daanse.db.jdbc.util.impl.Constraint;

import java.util.HashMap;
import java.util.Map;

public class Table {

    private String name;
    private String schema;
    private Map<String, Column> columns = new HashMap<>();
    private Map<String, Constraint> constraints = new HashMap<>();

    public Table(String name, String schema) {
        this.name = name;
        this.schema = schema;
    }

    public String getName() {
        return name;
    }

    public String getSchema() {
        return schema;
    }

    public Map<String, Column> getColumns() {
        return columns;
    }

    public Map<String, Constraint> getConstraint() {
        return constraints;
    }
}
