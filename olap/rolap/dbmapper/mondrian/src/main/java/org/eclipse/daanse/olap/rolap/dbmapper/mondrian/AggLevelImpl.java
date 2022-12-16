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
import org.eclipse.daanse.olap.rolap.dbmapper.api.AggLevel;
import org.eclipse.daanse.olap.rolap.dbmapper.api.AggLevelProperty;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AggLevel", propOrder = { "properties" })
public class AggLevelImpl implements AggLevel {

    @XmlAttribute(name = "column", required = true)
    protected String column;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "ordinalColumn", required = true)
    protected String ordinalColumn;
    @XmlAttribute(name = "captionColumn", required = true)
    protected String captionColumn;
    @XmlAttribute(name = "nameColumn", required = true)
    protected String nameColumn;
    @XmlAttribute(name = "nameColumn", required = true)
    protected Boolean collapsed = true;
    @XmlElement(name = "AggLevelProperty", type = AggLevelPropertyImpl.class)
    List<AggLevelProperty> properties;

    @Override
    public String column() {
        return column;
    }

    public void setColumn(String value) {
        this.column = value;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String ordinalColumn() {
        return ordinalColumn;
    }

    @Override
    public String captionColumn() {
        return captionColumn;
    }

    @Override
    public String nameColumn() {
        return nameColumn;
    }

    @Override
    public Boolean collapsed() {
        return collapsed;
    }

    @Override
    public List<AggLevelProperty> properties() {
        return properties;
    }

    public void setName(String value) {
        this.name = value;
    }

}
