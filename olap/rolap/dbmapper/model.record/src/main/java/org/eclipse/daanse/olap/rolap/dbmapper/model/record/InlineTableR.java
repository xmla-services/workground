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
package org.eclipse.daanse.olap.rolap.dbmapper.model.record;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.ColumnDef;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.InlineTable;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Row;

public record InlineTableR(List<ColumnDef> columnDefs,
                           List<Row> rows, String alias)
        implements InlineTable {

    public InlineTableR(InlineTable inlineTable) {
        this(new ArrayList<>(inlineTable.columnDefs()), new ArrayList<>(inlineTable.rows()), inlineTable.alias());
    }

    public InlineTableR(InlineTable inlineTable, String alias) {
        this(new ArrayList<>(inlineTable.columnDefs()), new ArrayList<>(inlineTable.rows()), alias);
    }

    @Override
	public boolean equals(Object o) {
        if (o instanceof InlineTable) {
            InlineTable that = (InlineTable) o;
            return alias().equals(that.alias());
        } else {
            return false;
        }
    }

    @Override
	public String toString() {
        return "<inline data>";
    }

    @Override
	public int hashCode() {
        return toString().hashCode();
    }
}
