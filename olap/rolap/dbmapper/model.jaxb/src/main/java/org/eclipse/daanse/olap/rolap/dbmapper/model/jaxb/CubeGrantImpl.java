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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "dimensionGrant", "hierarchyGrant" })
public class CubeGrantImpl implements CubeGrant {

    @XmlElement(name = "DimensionGrant")
    protected List<DimensionGrantImpl> dimensionGrant;
    @XmlElement(name = "HierarchyGrant")
    protected List<HierarchyGrantImpl> hierarchyGrant;
    @XmlAttribute(name = "cube", required = true)
    protected String cube;
    @XmlAttribute(name = "access", required = true)
    protected String access;

    @Override
    public List<DimensionGrantImpl> dimensionGrant() {
        if (dimensionGrant == null) {
            dimensionGrant = new ArrayList<>();
        }
        return this.dimensionGrant;
    }

    @Override
    public List<HierarchyGrantImpl> hierarchyGrant() {
        if (hierarchyGrant == null) {
            hierarchyGrant = new ArrayList<>();
        }
        return this.hierarchyGrant;
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

    public void setDimensionGrant(List<DimensionGrantImpl> dimensionGrant) {
        this.dimensionGrant = dimensionGrant;
    }

    public void setHierarchyGrant(List<HierarchyGrantImpl> hierarchyGrant) {
        this.hierarchyGrant = hierarchyGrant;
    }
}
