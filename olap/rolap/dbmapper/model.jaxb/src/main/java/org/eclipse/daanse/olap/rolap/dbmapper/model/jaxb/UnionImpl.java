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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingRoleUsage;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingUnion;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "roleUsages" })
public class UnionImpl implements MappingUnion {

    @XmlElement(name = "RoleUsage", required = true, type = RoleUsageImpl.class)
    protected List<MappingRoleUsage> roleUsages;

    @Override
    public List<MappingRoleUsage> roleUsages() {
        if (roleUsages == null) {
            roleUsages = new ArrayList<>();
        }
        return this.roleUsages;
    }

    public void setRoleUsages(List<MappingRoleUsage> roleUsages) {
        this.roleUsages = roleUsages;
    }

}
