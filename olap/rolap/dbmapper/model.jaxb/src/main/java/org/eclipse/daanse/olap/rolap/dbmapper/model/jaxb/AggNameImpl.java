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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.AggColumnName;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.AggForeignKey;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.AggLevel;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.AggMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.AggMeasureFactCount;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.AggName;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "aggFactCount", "aggIgnoreColumns", "aggForeignKeys", "aggMeasures", "aggLevels", "measuresFactCounts" })
public class AggNameImpl implements AggName {

    @XmlElement(name = "AggFactCount", required = true)
    protected AggColumnNameImpl aggFactCount;
    @XmlElement(name = "AggIgnoreColumn", type = AggColumnNameImpl.class)
    protected List<AggColumnName> aggIgnoreColumns;
    @XmlElement(name = "AggForeignKey", type = AggForeignKeyImpl.class)
    protected List<AggForeignKey> aggForeignKeys;
    @XmlElement(name = "AggMeasure", required = true, type = AggMeasureImpl.class)
    protected List<AggMeasure> aggMeasures;
    @XmlElement(name = "AggLevel", type = AggLevelImpl.class)
    protected List<AggLevel> aggLevels;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "ignorecase")
    protected Boolean ignorecase;
    @XmlAttribute(name = "approxRowCount")
    protected String approxRowCount;
    @XmlElement(name = "AggMeasureFactCount", type = AggMeasureFactCountImpl.class)
    private List<AggMeasureFactCount> measuresFactCounts;

    @Override
    public AggColumnNameImpl aggFactCount() {
        return aggFactCount;
    }

    public void setAggFactCount(AggColumnNameImpl value) {
        this.aggFactCount = value;
    }

    @Override
    public List<AggColumnName> aggIgnoreColumns() {
        if (aggIgnoreColumns == null) {
            aggIgnoreColumns = new ArrayList<>();
        }
        return this.aggIgnoreColumns;
    }

    @Override
    public List<AggForeignKey> aggForeignKeys() {
        if (aggForeignKeys == null) {
            aggForeignKeys = new ArrayList<>();
        }
        return this.aggForeignKeys;
    }

    @Override
    public List<AggMeasure> aggMeasures() {
        if (aggMeasures == null) {
            aggMeasures = new ArrayList<>();
        }
        return this.aggMeasures;
    }

    @Override
    public List<AggLevel> aggLevels() {
        if (aggLevels == null) {
            aggLevels = new ArrayList<>();
        }
        return this.aggLevels;
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
    public List<AggMeasureFactCount> measuresFactCounts() {
        if (measuresFactCounts == null) {
            measuresFactCounts = new ArrayList<>();
        }
        return measuresFactCounts;
    }

    public void setIgnorecase(Boolean value) {
        this.ignorecase = value;
    }

}
