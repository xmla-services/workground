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
import org.eclipse.daanse.olap.rolap.dbmapper.api.WritebackColumn;
import org.eclipse.daanse.olap.rolap.dbmapper.api.WritebackTable;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WritebackTable")
public class WritebackTableImpl implements WritebackTable {

    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "schema")
    protected String schema;
    @XmlElements({ @XmlElement(name = "WritebackAttribute", type = WritebackAttributeImpl.class),
        @XmlElement(name = "WritebackMeasure", type = WritebackMeasureImpl.class) })
    private List<WritebackColumn> columns;

    @Override
    public String name() {
        return name;
    }

    @Override
    public List<WritebackColumn> columns() {
        return columns;
    }

    public void setName(String value) {
        this.name = value;
    }

    @Override
    public String schema() {
        return schema;
    }

    public void setSchema(String value) {
        this.schema = value;
    }

}
