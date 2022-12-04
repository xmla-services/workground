
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

import org.eclipse.daanse.olap.rolap.dbmapper.api.View;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

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
            sql = new ArrayList<SQLImpl>();
        }
        return this.sql;
    }

    @Override
    public String alias() {
        return alias;
    }

    public void setAlias(String value) {
        this.alias = value;
    }

}
