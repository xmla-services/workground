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

import java.util.List;
import java.util.Objects;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.AggExclude;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.AggTable;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Hint;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.SQL;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Table;

public class TableR implements Table {

    private SQL sql;
    private String alias;
    private List<? extends AggExclude> aggExclude;
    private String name;
    private String schema;
    private List<? extends Hint> hint;
    private List<? extends AggTable> aggTable;

    public TableR(Table table) {
        this(table.schema(), table.name(), table.alias(), table.hint());
    }

    public TableR(String name, List<? extends AggExclude> aggExclude, List<? extends AggTable> aggTable) {
        this.name = name;
        this.aggExclude = aggExclude;
        this.aggTable = aggTable;
    }
    public TableR(String schema, String name, String alias, List<? extends Hint> hint) {
        this.name = name;
        this.schema = schema;
        this.alias = alias;
        this.hint = hint;
    }

    public TableR(Table tbl, String possibleName) {
        this(tbl.schema(), tbl.name(), possibleName, tbl.hint());

        // Remake the filter with the new alias
        if (tbl.sql() != null) {
            this.sql = new SQLR(tbl.sql().content() != null ? tbl.sql().content().replace(
                tbl.alias() == null
                    ? tbl.name()
                    : tbl.alias(),
                possibleName) : null, tbl.sql().dialect());
        }
    }

    public TableR(String name) {
    	this.name = name;
    	this.schema = null;
    	this.alias = null;
    	this.hint = List.of();
    }

    @Override
    public String alias() {
        return alias;
    }

    @Override
    public SQL sql() {
        return sql;
    }

    @Override
    public List<? extends AggExclude> aggExclude() {
        return aggExclude;
    }

    @Override
    public List<? extends AggTable> aggTable() {
        return aggTable;
    }

    @Override
    public List<? extends Hint> hint() {
        return hint;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String schema() {
        return schema;
    }

    @Override
	public boolean equals(Object o) {
        if (o instanceof Table that) {
            return this.name.equals(that.name()) &&
                Objects.equals(this.alias, that.alias()) &&
                Objects.equals(this.schema, that.schema());
        } else {
            return false;
        }
    }

    @Override
	public String toString() {
        return (schema == null) ?
            name :
            new StringBuilder(schema).append(".").append(name).toString();
    }

    @Override
	public int hashCode() {
        return toString().hashCode();
    }
}
