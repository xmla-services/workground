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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggLevel;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggLevelProperty;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AggLevel", propOrder = { "properties" })
public class AggLevelImpl implements MappingAggLevel {

    @XmlAttribute(name = "column", required = true)
    protected String column;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "ordinalColumn")
    protected String ordinalColumn;
    @XmlAttribute(name = "captionColumn")
    protected String captionColumn;
    @XmlAttribute(name = "nameColumn")
    protected String nameColumn;
    @XmlAttribute(name = "collapsed")
    protected Boolean collapsed = true;
    @XmlElement(name = "AggLevelProperty", type = AggLevelPropertyImpl.class)
    List<MappingAggLevelProperty> properties;

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
    public List<MappingAggLevelProperty> properties() {
        if (properties == null) {
            properties = new ArrayList<>();
        }
        return properties;
    }

    public void setName(String value) {
        this.name = value;
    }

}
