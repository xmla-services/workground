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

import java.util.List;

public record Table(String schemaName,
                    String tableName,
                    List<Constraint> constraints,
                    List<Column> columns) {


    public String getSchemaName() {
        return schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public List<Constraint> getConstraints() {
        return constraints;
    }

    public List<Column> getColumns() {
        return columns;
    }
}

