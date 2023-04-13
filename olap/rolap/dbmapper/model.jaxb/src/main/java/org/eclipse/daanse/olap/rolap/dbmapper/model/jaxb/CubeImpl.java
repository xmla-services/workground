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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Action;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Annotation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.CalculatedMember;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Cube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.CubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.DrillThroughAction;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Measure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.NamedSet;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Relation;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.WritebackTable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Cube", propOrder = { "annotations", "dimensionUsageOrDimension", "measure",
        "calculatedMember", "namedSet", "drillThroughAction", "writebackTable", "fact", "action"})
@XmlRootElement(name = "Cube")
public class CubeImpl implements Cube {

    @XmlElementWrapper(name = "Annotations")
    @XmlElement(name = "Annotation", type = AnnotationImpl.class)
    protected List<Annotation> annotations;
    @XmlElements({ @XmlElement(name = "DimensionUsage", type = DimensionUsageImpl.class),
            @XmlElement(name = "Dimension", type = PrivateDimensionImpl.class) })
    protected List<CubeDimension> dimensionUsageOrDimension;
    @XmlElement(name = "Measure", required = true, type = MeasureImpl.class)
    protected List<Measure> measure;
    @XmlElement(name = "CalculatedMember",  type = CalculatedMemberImpl.class)
    protected List<CalculatedMember> calculatedMember;
    @XmlElement(name = "NamedSet", type = NamedSetImpl.class)
    protected List<NamedSet> namedSet;
    @XmlElement(name = "DrillThroughAction", type = DrillThroughActionImpl.class)
    protected List<DrillThroughAction> drillThroughAction;
    @XmlElement(name = "WritebackTable", type = WritebackTableImpl.class)
    protected List<WritebackTable> writebackTable;
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
    @XmlElement(name = "Action", type = ActionImpl.class)
    protected List<Action> action;

    @Override
    public List<Annotation> annotations() {
        return annotations;
    }

    public void setAnnotations(List<Annotation> value) {
        this.annotations = value;
    }

    @Override
    public List<CubeDimension> dimensionUsageOrDimension() {
        if (dimensionUsageOrDimension == null) {
            dimensionUsageOrDimension = new ArrayList<>();
        }
        return this.dimensionUsageOrDimension;
    }

    @Override
    public List<Measure> measure() {
        if (measure == null) {
            measure = new ArrayList<>();
        }
        return this.measure;
    }

    @Override
    public List<CalculatedMember> calculatedMember() {
        if (calculatedMember == null) {
            calculatedMember = new ArrayList<>();
        }
        return this.calculatedMember;
    }

    @Override
    public List<NamedSet> namedSet() {
        if (namedSet == null) {
            namedSet = new ArrayList<>();
        }
        return this.namedSet;
    }

    @Override
    public List<DrillThroughAction> drillThroughAction() {
        if (drillThroughAction == null) {
            drillThroughAction = new ArrayList<>();
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
    public List<WritebackTable> writebackTable() {
        if (writebackTable == null) {
            writebackTable = new ArrayList<>();
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
    public List<Action> action() {
        if (action == null) {
            action = new ArrayList<>();
        }
        return action;
    }
}
