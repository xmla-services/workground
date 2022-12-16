
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
package org.eclipse.daanse.olap.rolap.dbmapper.mondrian;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.*;
import org.eclipse.daanse.olap.rolap.dbmapper.api.ExpressionView;

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

    public String genericExpression() {
        for (int i = 0; i < sql.size(); i++) {
            if (sql.get(i).dialect().equals("generic")) {
                return sql.get(i).content();
            }
        }
        return sql.get(0).content();
    }

    @Override
    public String table() {
        return table;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String tableAlias() {
        return null;
    }
}
