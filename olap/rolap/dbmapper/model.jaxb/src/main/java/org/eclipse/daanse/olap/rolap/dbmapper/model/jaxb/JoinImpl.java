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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Join;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.RelationOrJoin;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "relation" })
public class JoinImpl implements Join {

    @XmlElements({ @XmlElement(name = "Table", type = TableImpl.class),
            @XmlElement(name = "View", type = ViewImpl.class), @XmlElement(name = "Join", type = JoinImpl.class),
            @XmlElement(name = "InlineTable", type = InlineTableImpl.class) })
    protected List<RelationOrJoin> relation;
    @XmlAttribute(name = "leftAlias")
    protected String leftAlias;
    @XmlAttribute(name = "leftKey")
    protected String leftKey;
    @XmlAttribute(name = "rightAlias")
    protected String rightAlias;
    @XmlAttribute(name = "rightKey")
    protected String rightKey;

    @Override
    public List<RelationOrJoin> relation() {
        if (relation == null) {
            relation = new ArrayList<>();
        }
        return this.relation;
    }

    @Override
    public String leftAlias() {
        return leftAlias;
    }

    public void setLeftAlias(String value) {
        this.leftAlias = value;
    }

    @Override
    public String leftKey() {
        return leftKey;
    }

    public void setLeftKey(String value) {
        this.leftKey = value;
    }

    @Override
    public String rightAlias() {
        return rightAlias;
    }

    public void setRightAlias(String value) {
        this.rightAlias = value;
    }

    @Override
    public String rightKey() {
        return rightKey;
    }

    public void setRightKey(String value) {
        this.rightKey = value;
    }

}
