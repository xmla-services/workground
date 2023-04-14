
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
import java.util.Objects;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Expression;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.ExpressionView;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.SQL;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExpressionView", propOrder = { "sqls" })
public class ExpressionViewImpl implements ExpressionView {

    @XmlElement(name = "SQL", required = true, type = SQLImpl.class)
    protected List<SQL> sqls;
    @XmlAttribute(name = "table")
    protected String table;
    @XmlAttribute(name = "name", required = true)
    protected String name;

    @Override
    public List<SQL> sqls() {
        if (sqls == null) {
            sqls = new ArrayList<>();
        }
        return this.sqls;
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
            for (int i = 0; i < ((ExpressionView) expression).sqls().size(); i++) {
                h = 37 * h + ((ExpressionView) expression).sqls().get(i).dialect().hashCode();
            }
            return h;
    }

    @Override
	public boolean equals(Object obj) {
        if (!(obj instanceof ExpressionView that)) {
            return false;
        }
        if (sqls().size() != that.sqls().size()) {
            return false;
        }
        for (int i = 0; i < sqls().size(); i++) {
            if (! sqls().get(i).equals(that.sqls().get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sqls, table, name);
    }
}
