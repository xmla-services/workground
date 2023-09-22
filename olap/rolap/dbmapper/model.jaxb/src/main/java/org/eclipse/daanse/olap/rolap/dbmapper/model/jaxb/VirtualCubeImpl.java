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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAnnotation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCalculatedMember;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCubeUsage;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingNamedSet;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingVirtualCube;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingVirtualCubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingVirtualCubeMeasure;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "annotations", "cubeUsages", "virtualCubeDimensions", "virtualCubeMeasures",
        "calculatedMembers", "namedSets" })
public class VirtualCubeImpl implements MappingVirtualCube {

    @XmlElement(name = "Annotation", type = AnnotationImpl.class)
    @XmlElementWrapper(name = "Annotations")
    protected List<MappingAnnotation> annotations;
    @XmlElementWrapper(name = "CubeUsages", required = true)
    @XmlElement(name = "CubeUsage", required = true, type = CubeUsageImpl.class)
    protected List<MappingCubeUsage> cubeUsages;
    @XmlElement(name = "VirtualCubeDimension", required = true, type = VirtualCubeDimensionImpl.class)
    protected List<MappingVirtualCubeDimension> virtualCubeDimensions;
    @XmlElement(name = "VirtualCubeMeasure", required = true, type = VirtualCubeMeasureImpl.class)
    protected List<MappingVirtualCubeMeasure> virtualCubeMeasures;
    @XmlElement(name = "CalculatedMember", type = CalculatedMemberImpl.class)
    protected List<MappingCalculatedMember> calculatedMembers;
    @XmlElement(name = "NamedSet", type = NamedSetImpl.class)
    protected List<MappingNamedSet> namedSets;
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
    public List<MappingAnnotation> annotations() {
        return annotations;
    }

    public void setAnnotations(List<MappingAnnotation> value) {
        this.annotations = value;
    }

    @Override
    public List<MappingCubeUsage> cubeUsages() {
        return cubeUsages;
    }

    public void setCubeUsages(List<MappingCubeUsage> value) {
        this.cubeUsages = value;
    }

    @Override
    public List<MappingVirtualCubeDimension> virtualCubeDimensions() {
        if (virtualCubeDimensions == null) {
            virtualCubeDimensions = new ArrayList<>();
        }
        return this.virtualCubeDimensions;
    }

    @Override
    public List<MappingVirtualCubeMeasure> virtualCubeMeasures() {
        if (virtualCubeMeasures == null) {
            virtualCubeMeasures = new ArrayList<>();
        }
        return this.virtualCubeMeasures;
    }

    @Override
    public List<MappingCalculatedMember> calculatedMembers() {
        if (calculatedMembers == null) {
            calculatedMembers = new ArrayList<>();
        }
        return this.calculatedMembers;
    }

    @Override
    public List<MappingNamedSet> namedSets() {
        if (namedSets == null) {
            namedSets = new ArrayList<>();
        }
        return this.namedSets;
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
