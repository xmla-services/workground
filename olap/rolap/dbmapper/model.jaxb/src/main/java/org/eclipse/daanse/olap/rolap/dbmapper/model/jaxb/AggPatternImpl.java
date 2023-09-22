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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggColumnName;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggExclude;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggForeignKey;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggLevel;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggMeasureFactCount;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggPattern;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "aggFactCount", "aggIgnoreColumns", "aggForeignKeys", "aggMeasures", "aggLevels",
        "aggExcludes", "measureFactCounts" })
public class AggPatternImpl implements MappingAggPattern {

    @XmlElement(name = "AggFactCount", required = true, type = AggColumnNameImpl.class)
    protected MappingAggColumnName aggFactCount;
    @XmlElement(name = "AggIgnoreColumn", type = AggColumnNameImpl.class)
    protected List<MappingAggColumnName> aggIgnoreColumns;
    @XmlElement(name = "AggForeignKey", type = AggForeignKeyImpl.class)
    protected List<MappingAggForeignKey> aggForeignKeys;
    @XmlElement(name = "AggMeasure", required = true, type = AggMeasureImpl.class)
    protected List<MappingAggMeasure> aggMeasures;
    @XmlElement(name = "AggLevel", type = AggLevelImpl.class)
    protected List<MappingAggLevel> aggLevels;
    @XmlElement(name = "AggExclude", type = AggExcludeImpl.class)
    protected List<MappingAggExclude> aggExcludes;
    @XmlAttribute(name = "pattern", required = true)
    protected String pattern;
    @XmlAttribute(name = "ignorecase")
    protected Boolean ignorecase;
    @XmlElement(name = "AggMeasureFactCount", type = AggMeasureFactCountImpl.class)
    protected List<MappingAggMeasureFactCount> measureFactCounts;

    @Override
    public MappingAggColumnName aggFactCount() {
        return aggFactCount;
    }

    public void setAggFactCount(AggColumnNameImpl value) {
        this.aggFactCount = value;
    }

    @Override
    public List<MappingAggColumnName> aggIgnoreColumns() {
        if (aggIgnoreColumns == null) {
            aggIgnoreColumns = new ArrayList<>();
        }
        return this.aggIgnoreColumns;
    }

    @Override
    public List<MappingAggForeignKey> aggForeignKeys() {
        if (aggForeignKeys == null) {
            aggForeignKeys = new ArrayList<>();
        }
        return this.aggForeignKeys;
    }

    @Override
    public List<MappingAggMeasure> aggMeasures() {
        if (aggMeasures == null) {
            aggMeasures = new ArrayList<>();
        }
        return this.aggMeasures;
    }

    @Override
    public List<MappingAggLevel> aggLevels() {
        if (aggLevels == null) {
            aggLevels = new ArrayList<>();
        }
        return this.aggLevels;
    }

    @Override
    public List<MappingAggExclude> aggExcludes() {
        if (aggExcludes == null) {
            aggExcludes = new ArrayList<>();
        }
        return this.aggExcludes;
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
    public List<MappingAggMeasureFactCount> measuresFactCounts() {
        return measureFactCounts;
    }

    public void setIgnorecase(Boolean value) {
        this.ignorecase = value;
    }

}
