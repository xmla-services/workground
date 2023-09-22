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
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.SchemaGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.AccessEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.adapter.AccessAdaptor;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "cubeGrants" })
public class SchemaGrantImpl implements SchemaGrant {

    @XmlElement(name = "CubeGrant", required = true, type = CubeGrantImpl.class)
    protected List<MappingCubeGrant> cubeGrants;
    @XmlAttribute(name = "access", required = true)
    @XmlJavaTypeAdapter(AccessAdaptor.class)
    protected AccessEnum access;

    @Override
    public List<MappingCubeGrant> cubeGrants() {
        if (cubeGrants == null) {
            cubeGrants = new ArrayList<>();
        }
        return this.cubeGrants;
    }

    @Override
    public AccessEnum access() {
        return access;
    }

    public void setAccess(AccessEnum value) {
        this.access = value;
    }

    public void setCubeGrants(List<MappingCubeGrant> cubeGrants) {
        this.cubeGrants = cubeGrants;
    }
}
