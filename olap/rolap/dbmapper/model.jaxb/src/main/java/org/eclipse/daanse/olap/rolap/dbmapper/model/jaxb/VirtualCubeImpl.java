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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Annotation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.CalculatedMember;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.CubeUsage;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.NamedSet;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.VirtualCube;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.VirtualCubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.VirtualCubeMeasure;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "annotations", "cubeUsages", "virtualCubeDimension", "virtualCubeMeasure",
        "calculatedMember", "namedSet" })
public class VirtualCubeImpl implements VirtualCube {

    @XmlElement(name = "Annotation", type = AnnotationImpl.class)
    @XmlElementWrapper(name = "Annotations")
    protected List<Annotation> annotations;
    @XmlElementWrapper(name = "CubeUsages", required = true)
    @XmlElement(name = "CubeUsage", required = true, type = CubeUsageImpl.class)
    protected List<CubeUsage> cubeUsages;
    @XmlElement(name = "VirtualCubeDimension", required = true, type = VirtualCubeDimensionImpl.class)
    protected List<VirtualCubeDimension> virtualCubeDimension;
    @XmlElement(name = "VirtualCubeMeasure", required = true, type = VirtualCubeMeasureImpl.class)
    protected List<VirtualCubeMeasure> virtualCubeMeasure;
    @XmlElement(name = "CalculatedMember", type = CalculatedMemberImpl.class)
    protected List<CalculatedMember> calculatedMember;
    @XmlElement(name = "NamedSet", type = NamedSetImpl.class)
    protected List<NamedSet> namedSet;
    @XmlAttribute(name = "enabled")
    protected Boolean enabled;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "defaultMeasure")
    protected String defaultMeasure;
    @XmlAttribute(name = "caption")
    protected String caption;
    @XmlAttribute(name = "description")
    protected String description;
    @XmlAttribute(name = "visible")
    protected Boolean visible = true;

    @Override
    public List<Annotation> annotations() {
        return annotations;
    }

    public void setAnnotations(List<Annotation> value) {
        this.annotations = value;
    }

    @Override
    public List<CubeUsage> cubeUsages() {
        return cubeUsages;
    }

    public void setCubeUsages(List<CubeUsage> value) {
        this.cubeUsages = value;
    }

    @Override
    public List<VirtualCubeDimension> virtualCubeDimension() {
        if (virtualCubeDimension == null) {
            virtualCubeDimension = new ArrayList<>();
        }
        return this.virtualCubeDimension;
    }

    @Override
    public List<VirtualCubeMeasure> virtualCubeMeasure() {
        if (virtualCubeMeasure == null) {
            virtualCubeMeasure = new ArrayList<>();
        }
        return this.virtualCubeMeasure;
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
    public String name() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @Override
    public String defaultMeasure() {
        return defaultMeasure;
    }

    public void setDefaultMeasure(String value) {
        this.defaultMeasure = value;
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

    @Override
    public boolean visible() {
        return visible;
    }

    public void setDescription(String value) {
        this.description = value;
    }

}
