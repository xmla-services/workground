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
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.AggMeasureFactCount;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.AggPattern;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "aggFactCount", "aggIgnoreColumn", "aggForeignKey", "aggMeasure", "aggLevel",
        "aggExclude", "measuresFactCount" })
public class AggPatternImpl implements AggPattern {

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
    @XmlElement(name = "AggExclude")
    protected List<AggExcludeImpl> aggExclude;
    @XmlAttribute(name = "pattern", required = true)
    protected String pattern;
    @XmlAttribute(name = "ignorecase")
    protected Boolean ignorecase;
    @XmlElement(name = "AggMeasureFactCount")
    protected List<AggMeasureFactCountImpl> measuresFactCount;

    @Override
    public AggColumnName aggFactCount() {
        return aggFactCount;
    }

    public void setAggFactCount(AggColumnNameImpl value) {
        this.aggFactCount = value;
    }

    @Override
    public List<AggColumnNameImpl> aggIgnoreColumn() {
        if (aggIgnoreColumn == null) {
            aggIgnoreColumn = new ArrayList<AggColumnNameImpl>();
        }
        return this.aggIgnoreColumn;
    }

    @Override
    public List<AggForeignKeyImpl> aggForeignKey() {
        if (aggForeignKey == null) {
            aggForeignKey = new ArrayList<AggForeignKeyImpl>();
        }
        return this.aggForeignKey;
    }

    @Override
    public List<AggMeasureImpl> aggMeasure() {
        if (aggMeasure == null) {
            aggMeasure = new ArrayList<AggMeasureImpl>();
        }
        return this.aggMeasure;
    }

    @Override
    public List<AggLevelImpl> aggLevel() {
        if (aggLevel == null) {
            aggLevel = new ArrayList<AggLevelImpl>();
        }
        return this.aggLevel;
    }

    @Override
    public List<AggExcludeImpl> aggExclude() {
        if (aggExclude == null) {
            aggExclude = new ArrayList<AggExcludeImpl>();
        }
        return this.aggExclude;
    }

    @Override
    public String pattern() {
        return pattern;
    }

    public void setPattern(String value) {
        this.pattern = value;
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
        return measuresFactCount;
    }

    public void setIgnorecase(Boolean value) {
        this.ignorecase = value;
    }

}
