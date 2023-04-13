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
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.AggExclude;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.AggForeignKey;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.AggLevel;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.AggMeasure;
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

    @XmlElement(name = "AggFactCount", required = true, type = AggColumnNameImpl.class)
    protected AggColumnName aggFactCount;
    @XmlElement(name = "AggIgnoreColumn", type = AggColumnNameImpl.class)
    protected List<AggColumnName> aggIgnoreColumn;
    @XmlElement(name = "AggForeignKey", type = AggForeignKeyImpl.class)
    protected List<AggForeignKey> aggForeignKey;
    @XmlElement(name = "AggMeasure", required = true, type = AggMeasureImpl.class)
    protected List<AggMeasure> aggMeasure;
    @XmlElement(name = "AggLevel", type = AggLevelImpl.class)
    protected List<AggLevel> aggLevel;
    @XmlElement(name = "AggExclude", type = AggExcludeImpl.class)
    protected List<AggExclude> aggExclude;
    @XmlAttribute(name = "pattern", required = true)
    protected String pattern;
    @XmlAttribute(name = "ignorecase")
    protected Boolean ignorecase;
    @XmlElement(name = "AggMeasureFactCount", type = AggMeasureFactCountImpl.class)
    protected List<AggMeasureFactCount> measuresFactCount;

    @Override
    public AggColumnName aggFactCount() {
        return aggFactCount;
    }

    public void setAggFactCount(AggColumnNameImpl value) {
        this.aggFactCount = value;
    }

    @Override
    public List<AggColumnName> aggIgnoreColumn() {
        if (aggIgnoreColumn == null) {
            aggIgnoreColumn = new ArrayList<>();
        }
        return this.aggIgnoreColumn;
    }

    @Override
    public List<AggForeignKey> aggForeignKey() {
        if (aggForeignKey == null) {
            aggForeignKey = new ArrayList<>();
        }
        return this.aggForeignKey;
    }

    @Override
    public List<AggMeasure> aggMeasure() {
        if (aggMeasure == null) {
            aggMeasure = new ArrayList<>();
        }
        return this.aggMeasure;
    }

    @Override
    public List<AggLevel> aggLevel() {
        if (aggLevel == null) {
            aggLevel = new ArrayList<>();
        }
        return this.aggLevel;
    }

    @Override
    public List<AggExclude> aggExclude() {
        if (aggExclude == null) {
            aggExclude = new ArrayList<>();
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
    public List<AggMeasureFactCount> measuresFactCount() {
        return measuresFactCount;
    }

    public void setIgnorecase(Boolean value) {
        this.ignorecase = value;
    }

}
