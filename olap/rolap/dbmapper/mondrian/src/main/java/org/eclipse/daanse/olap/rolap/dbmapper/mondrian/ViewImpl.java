
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

import jakarta.xml.bind.annotation.*;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Relation;
import org.eclipse.daanse.olap.rolap.dbmapper.api.View;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "View", propOrder = { "sql" })
public class ViewImpl implements View {

    @XmlElement(name = "SQL", required = true)
    protected List<SQLImpl> sql;
    @XmlAttribute(name = "alias", required = true)
    protected String alias;

    @Override
    public List<SQLImpl> sqls() {
        if (sql == null) {
            sql = new ArrayList<>();
        }
        return this.sql;
    }

    @Override
    public void addCode(String dialect, String code) {
        if (sql == null) {
            sql = new ArrayList<>();
        }
        SQLImpl sqlImpl = new SQLImpl();
        sqlImpl.setDialect(dialect);
        sqlImpl.setContent(code);
        sql.add(sqlImpl);
    }

    @Override
    public String alias() {
        return alias;
    }

    public void setAlias(String value) {
        this.alias = value;
    }

}
