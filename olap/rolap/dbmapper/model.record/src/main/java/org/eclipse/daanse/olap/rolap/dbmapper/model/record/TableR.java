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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggExclude;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggTable;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingHint;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.SQL;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Table;

public class TableR implements Table {

    private SQL sql;
    private String alias;
    private List<MappingAggExclude> aggExcludes;
    private String name;
    private String schema;
    private List<MappingHint> hints;
    private List<MappingAggTable> aggTables;

    public TableR(Table table) {
        this(table.schema(), table.name(), table.alias(), table.hints());
    }

    public TableR(String name, List<MappingAggExclude> aggExcludes, List<MappingAggTable> aggTables) {
        this.name = name;
        this.aggExcludes = aggExcludes;
        this.aggTables = aggTables;
    }
    public TableR(String schema, String name, String alias, List<MappingHint> hints) {
        this.name = name;
        this.schema = schema;
        this.alias = alias;
        this.hints = hints;
    }

    public TableR(Table tbl, String possibleName) {
        this(tbl.schema(), tbl.name(), possibleName, tbl.hints());

        // Remake the filter with the new alias
        if (tbl.sql() != null) {
            String aliasOrName = tbl.alias() == null ? tbl.name() : tbl.alias();
            this.sql = new SQLR(tbl.sql().content() != null ? tbl.sql().content().replace(aliasOrName, possibleName) : null,
                tbl.sql().dialect());
        }
    }

    public TableR(String name) {
    	this.name = name;
    	this.schema = null;
    	this.alias = null;
    	this.hints = List.of();
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
    public List<MappingAggExclude> aggExcludes() {
        return aggExcludes;
    }

    @Override
    public List<MappingAggTable> aggTables() {
        return aggTables;
    }

    @Override
    public List<MappingHint> hints() {
        return hints;
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
