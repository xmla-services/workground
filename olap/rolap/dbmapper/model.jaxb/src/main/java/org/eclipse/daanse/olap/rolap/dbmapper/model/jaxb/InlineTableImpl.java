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
package org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb;

import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.InlineTable;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InlineTable", propOrder = { "columnDefs", "rows" })
public class InlineTableImpl implements InlineTable {

    @XmlElementWrapper(name = "ColumnDefs")
    @XmlElement(name = "ColumnDef", required = true)
    protected List<ColumnDefImpl> columnDefs;
    @XmlElementWrapper(name = "Rows", required = true)
    @XmlElement(name = "Row", required = true)
    protected List<RowImpl> rows;
    @XmlAttribute(name = "alias")
    private String alias;

    @Override
    public List<ColumnDefImpl> columnDefs() {
        return columnDefs;
    }

    public void setColumnDefs(List<ColumnDefImpl> value) {
        this.columnDefs = value;
    }

    @Override
    public List<RowImpl> rows() {
        return rows;
    }

    public void setRows(List<RowImpl> value) {
        this.rows = value;
    }

    @Override
    public String alias() {
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }

    public boolean equals(Object o) {
        if (o instanceof InlineTable) {
            InlineTable that = (InlineTable) o;
            return alias().equals(that.alias());
        } else {
            return false;
        }
    }

    public String toString() {
        return "<inline data>";
    }

    public int hashCode() {
        return toString().hashCode();
    }
}
