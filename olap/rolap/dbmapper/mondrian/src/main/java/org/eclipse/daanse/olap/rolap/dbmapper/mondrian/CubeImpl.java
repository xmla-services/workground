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

import org.eclipse.daanse.olap.rolap.dbmapper.api.Action;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Cube;
import org.eclipse.daanse.olap.rolap.dbmapper.api.CubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Relation;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Cube", propOrder = { "annotations", "dimensionUsageOrDimension", "measure",
        "calculatedMember", "namedSet", "drillThroughAction", "writebackTable", "fact", "action"})
@XmlRootElement(name = "Cube")
public class CubeImpl implements Cube {

    @XmlElementWrapper(name = "Annotations")
    @XmlElement(name = "Annotation")
    protected List<AnnotationImpl> annotations;
    @XmlElements({ @XmlElement(name = "DimensionUsage", type = DimensionUsageImpl.class),
            @XmlElement(name = "Dimension", type = PrivateDimensionImpl.class) })
    protected List<CubeDimension> dimensionUsageOrDimension;
    @XmlElement(name = "Measure", required = true)
    protected List<MeasureImpl> measure;
    @XmlElement(name = "CalculatedMember")
    protected List<CalculatedMemberImpl> calculatedMember;
    @XmlElement(name = "NamedSet")
    protected List<NamedSetImpl> namedSet;
    @XmlElement(name = "DrillThroughAction")
    protected List<DrillThroughActionImpl> drillThroughAction;
    @XmlElement(name = "WritebackTable")
    protected List<WritebackTableImpl> writebackTable;
    @XmlAttribute(name = "name", required = true)
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
    @XmlAttribute(name = "visible")
    protected boolean visible = true;
    @XmlElements({ @XmlElement(name = "InlineTable", type = InlineTableImpl.class),
        @XmlElement(name = "Table", type = TableImpl.class), @XmlElement(name = "View", type = ViewImpl.class)})
    protected Relation fact;
    @XmlElement(name = "Action")
    protected List<ActionImpl> action;

    @Override
    public List<AnnotationImpl> annotations() {
        return annotations;
    }

    public void setAnnotations(List<AnnotationImpl> value) {
        this.annotations = value;
    }

    @Override
    public List<CubeDimension> dimensionUsageOrDimension() {
        if (dimensionUsageOrDimension == null) {
            dimensionUsageOrDimension = new ArrayList<CubeDimension>();
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

    @Override
    public boolean visible() {
        return visible;
    }

    @Override
    public Relation fact() {
        return fact;
    }

    public void setFact(Relation fact) {
        this.fact = fact;
    }

    @Override
    public List<? extends Action> action() {
        if (action == null) {
            action = new ArrayList<ActionImpl>();
        }
        return action;
    }
}
