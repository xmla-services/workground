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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.CubeGrant;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.DimensionGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.HierarchyGrant;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "dimensionGrants", "hierarchyGrants" })
public class CubeGrantImpl implements CubeGrant {

    @XmlElement(name = "DimensionGrant", type = DimensionGrantImpl.class)
    protected List<DimensionGrant> dimensionGrants;
    @XmlElement(name = "HierarchyGrant", type = HierarchyGrantImpl.class)
    protected List<HierarchyGrant> hierarchyGrants;
    @XmlAttribute(name = "cube", required = true)
    protected String cube;
    @XmlAttribute(name = "access", required = true)
    protected String access;

    @Override
    public List<DimensionGrant> dimensionGrants() {
        if (dimensionGrants == null) {
            dimensionGrants = new ArrayList<>();
        }
        return this.dimensionGrants;
    }

    @Override
    public List<HierarchyGrant> hierarchyGrants() {
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

    public void setDimensionGrant(List<DimensionGrant> dimensionGrants) {
        this.dimensionGrants = dimensionGrants;
    }

    public void setHierarchyGrant(List<HierarchyGrant> hierarchyGrants) {
        this.hierarchyGrants = hierarchyGrants;
    }
}
