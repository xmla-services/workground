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

import java.util.Objects;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Column;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class ColumnImpl implements Column {

    @XmlAttribute(name = "table")
    protected String table;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    private String genericExpression;

    public ColumnImpl() {
    }

    public ColumnImpl(String table, String name) {
        this.table = table;
        this.name = name;
        this.genericExpression = table == null ? name : new StringBuilder(table).append(".").append(name).toString();
    }

    @Override
    public String table() {
        return table;
    }

    @Override
	public  void setTable(String table) {
        this.table = table;
    }

    @Override
    public String name() {
        return name;
    }

    public  void setName(String name) {
        this.name = name;
    }

    @Override
	public int hashCode() {
        return name().hashCode() ^ (table()==null ? 0 : table().hashCode());
    }

    @Override
	public boolean equals(Object obj) {
            if (!(obj instanceof Column)) {
                return false;
            }
            Column that = (Column) obj;
            return name().equals(that.name()) &&
                Objects.equals(table(), that.table());
    }

    @Override
    public String genericExpression() {
        return genericExpression;
    }
}
