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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCubeGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingDimensionGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingHierarchyGrant;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "dimensionGrants", "hierarchyGrants" })
public class CubeGrantImpl implements MappingCubeGrant {

    @XmlElement(name = "DimensionGrant", type = DimensionGrantImpl.class)
    protected List<MappingDimensionGrant> dimensionGrants;
    @XmlElement(name = "HierarchyGrant", type = HierarchyGrantImpl.class)
    protected List<MappingHierarchyGrant> hierarchyGrants;
    @XmlAttribute(name = "cube", required = true)
    protected String cube;
    @XmlAttribute(name = "access", required = true)
    protected String access;

    @Override
    public List<MappingDimensionGrant> dimensionGrants() {
        if (dimensionGrants == null) {
            dimensionGrants = new ArrayList<>();
        }
        return this.dimensionGrants;
    }

    @Override
    public List<MappingHierarchyGrant> hierarchyGrants() {
        if (hierarchyGrants == null) {
            hierarchyGrants = new ArrayList<>();
        }
        return this.hierarchyGrants;
    }

    @Override
    public String cube() {
        return cube;
    }

    public void setCube(String value) {
        this.cube = value;
    }

    @Override
    public String access() {
        return access;
    }

    public void setAccess(String value) {
        this.access = value;
    }

    public void setDimensionGrant(List<MappingDimensionGrant> dimensionGrants) {
        this.dimensionGrants = dimensionGrants;
    }

    public void setHierarchyGrant(List<MappingHierarchyGrant> hierarchyGrants) {
        this.hierarchyGrants = hierarchyGrants;
    }
}
