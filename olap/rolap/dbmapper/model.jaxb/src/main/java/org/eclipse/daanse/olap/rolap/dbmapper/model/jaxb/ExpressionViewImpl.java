
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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Expression;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.ExpressionView;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExpressionView", propOrder = { "sql" })
public class ExpressionViewImpl implements ExpressionView {

    @XmlElement(name = "SQL", required = true)
    protected List<SQLImpl> sql;
    @XmlAttribute(name = "table")
    protected String table;
    @XmlAttribute(name = "name", required = true)
    protected String name;

    @Override
    public List<SQLImpl> sql() {
        if (sql == null) {
            sql = new ArrayList<SQLImpl>();
        }
        return this.sql;
    }

    @Override
    public String table() {
        return table;
    }

    @Override
    public String name() {
        return name;
    }

    public int hashCode(Expression expression) {
            int h = 17;
            for (int i = 0; i < ((ExpressionView) expression).sql().size(); i++) {
                h = 37 * h + ((ExpressionView) expression).sql().get(i).dialect().hashCode();
            }
            return h;
    }

    @Override
	public boolean equals(Object obj) {
        if (!(obj instanceof ExpressionView)) {
            return false;
        }
        ExpressionView that = (ExpressionView) obj;
        if (sql().size() != that.sql().size()) {
            return false;
        }
        for (int i = 0; i < sql().size(); i++) {
            if (! sql().get(i).equals(that.sql().get(i))) {
                return false;
            }
        }
        return true;
    }
}
