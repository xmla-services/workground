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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.AggMeasureFactCount;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.AggName;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "aggFactCount", "aggIgnoreColumn", "aggForeignKey", "aggMeasure", "aggLevel", "measuresFactCount" })
public class AggNameImpl implements AggName {

    @XmlElement(name = "AggFactCount", required = true)
    protected AggColumnNameImpl aggFactCount;
    @XmlElement(name = "AggIgnoreColumn")
    protected List<AggColumnNameImpl> aggIgnoreColumn;
    @XmlElement(name = "AggForeignKey")
    protected List<AggForeignKeyImpl> aggForeignKey;
    @XmlElement(name = "AggMeasure", required = true)
    protected List<AggMeasureImpl> aggMeasure;
    @XmlElement(name = "AggLevel")
    protected List<AggLevelImpl> aggLevel;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "ignorecase")
    protected Boolean ignorecase;
    @XmlAttribute(name = "approxRowCount")
    protected String approxRowCount;
    @XmlElement(name = "AggMeasureFactCount")
    private List<AggMeasureFactCountImpl> measuresFactCount;

    @Override
    public AggColumnNameImpl aggFactCount() {
        return aggFactCount;
    }

    public void setAggFactCount(AggColumnNameImpl value) {
        this.aggFactCount = value;
    }

    @Override
    public List<AggColumnNameImpl> aggIgnoreColumn() {
        if (aggIgnoreColumn == null) {
            aggIgnoreColumn = new ArrayList<>();
        }
        return this.aggIgnoreColumn;
    }

    @Override
    public List<AggForeignKeyImpl> aggForeignKey() {
        if (aggForeignKey == null) {
            aggForeignKey = new ArrayList<>();
        }
        return this.aggForeignKey;
    }

    @Override
    public List<AggMeasureImpl> aggMeasure() {
        if (aggMeasure == null) {
            aggMeasure = new ArrayList<>();
        }
        return this.aggMeasure;
    }

    @Override
    public List<AggLevelImpl> aggLevel() {
        if (aggLevel == null) {
            aggLevel = new ArrayList<>();
        }
        return this.aggLevel;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String approxRowCount() {
        return approxRowCount;
    }

    public void setName(String value) {
        this.name = value;
    }

    @Override
    public boolean ignorecase() {
        if (ignorecase == null) {
            return true;
        } else {
            return ignorecase;
        }
    }

    @Override
    public List<? extends AggMeasureFactCount> measuresFactCount() {
        if (measuresFactCount == null) {
            measuresFactCount = new ArrayList<>();
        }
        return measuresFactCount;
    }

    public void setIgnorecase(Boolean value) {
        this.ignorecase = value;
    }

}
