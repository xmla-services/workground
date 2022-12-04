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

import org.eclipse.daanse.olap.rolap.dbmapper.api.Cube;
import org.eclipse.daanse.olap.rolap.dbmapper.api.View;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "annotations", "table", "view", "dimensionUsageOrDimension", "measure",
        "calculatedMember", "namedSet", "drillThroughAction" })
public class CubeImpl implements Cube {

    @XmlElementWrapper(name = "Annotations")
    @XmlElement(name = "Annotation")
    protected List<AnnotationImpl> annotations;
    @XmlElement(name = "Table")
    protected TableImpl table;
    @XmlElement(name = "View")
    protected ViewImpl view;
    @XmlElements({ @XmlElement(name = "DimensionUsage", type = DimensionUsageImpl.class),
            @XmlElement(name = "Dimension", type = PrivateDimensionImpl.class) })
    protected List<Object> dimensionUsageOrDimension;
    @XmlElement(name = "Measure", required = true)
    protected List<MeasureImpl> measure;
    @XmlElement(name = "CalculatedMember")
    protected List<CalculatedMemberImpl> calculatedMember;
    @XmlElement(name = "NamedSet")
    protected List<NamedSetImpl> namedSet;
    @XmlElement(name = "DrillThroughAction")
    protected List<DrillThroughActionImpl> drillThroughAction;
    @XmlAttribute(name = "name", required = true)
    protected List<WritebackTableImpl> writebackTable;
    @XmlAttribute(name = "writebackTable", required = true)
    protected String name;
    @XmlAttribute(name = "caption")
    protected String caption;
    @XmlAttribute(name = "description")
    protected String description;
    @XmlAttribute(name = "defaultMeasure")
    protected String defaultMeasure;
    @XmlAttribute(name = "cache")
    protected Boolean cache;
    @XmlAttribute(name = "enabled")
    protected Boolean enabled;

    @Override
    public List<AnnotationImpl> annotations() {
        return annotations;
    }

    public void setAnnotations(List<AnnotationImpl> value) {
        this.annotations = value;
    }

    @Override
    public TableImpl table() {
        return table;
    }

    public void setTable(TableImpl value) {
        this.table = value;
    }

    @Override
    public View view() {
        return view;
    }

    public void setView(ViewImpl value) {
        this.view = value;
    }

    @Override
    public List<Object> dimensionUsageOrDimension() {
        if (dimensionUsageOrDimension == null) {
            dimensionUsageOrDimension = new ArrayList<Object>();
        }
        return this.dimensionUsageOrDimension;
    }

    @Override
    public List<MeasureImpl> measure() {
        if (measure == null) {
            measure = new ArrayList<MeasureImpl>();
        }
        return this.measure;
    }

    @Override
    public List<CalculatedMemberImpl> calculatedMember() {
        if (calculatedMember == null) {
            calculatedMember = new ArrayList<CalculatedMemberImpl>();
        }
        return this.calculatedMember;
    }

    @Override
    public List<NamedSetImpl> namedSet() {
        if (namedSet == null) {
            namedSet = new ArrayList<NamedSetImpl>();
        }
        return this.namedSet;
    }

    @Override
    public List<DrillThroughActionImpl> drillThroughAction() {
        if (drillThroughAction == null) {
            drillThroughAction = new ArrayList<DrillThroughActionImpl>();
        }
        return this.drillThroughAction;
    }

    @Override
    public String name() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @Override
    public String caption() {
        return caption;
    }

    public void setCaption(String value) {
        this.caption = value;
    }

    @Override
    public String description() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    @Override
    public String defaultMeasure() {
        return defaultMeasure;
    }

    public void setDefaultMeasure(String value) {
        this.defaultMeasure = value;
    }

    @Override
    public boolean cache() {
        if (cache == null) {
            return true;
        } else {
            return cache;
        }
    }

    public void setCache(Boolean value) {
        this.cache = value;
    }

    @Override
    public boolean enabled() {
        if (enabled == null) {
            return true;
        } else {
            return enabled;
        }
    }

    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

    @Override
    public List<WritebackTableImpl> writebackTable() {
        if (writebackTable == null) {
            writebackTable = new ArrayList<WritebackTableImpl>();
        }
        return this.writebackTable;
    }

}
