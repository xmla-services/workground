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

import org.eclipse.daanse.olap.rolap.dbmapper.api.HierarchyGrant;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import org.eclipse.daanse.olap.rolap.dbmapper.api.MemberGrant;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "memberGrant" })
public class HierarchyGrantImpl implements HierarchyGrant {

    @XmlElement(name = "MemberGrant", type = MemberGrantImpl.class)
    protected List<MemberGrant> memberGrant;
    @XmlAttribute(name = "hierarchy", required = true)
    protected String hierarchy;
    @XmlAttribute(name = "access", required = true)
    protected String access;
    @XmlAttribute(name = "topLevel")
    protected String topLevel;
    @XmlAttribute(name = "bottomLevel")
    protected String bottomLevel;
    @XmlAttribute(name = "rollupPolicy")
    protected String rollupPolicy;

    @Override
    public List<MemberGrant> memberGrant() {
        if (memberGrant == null) {
            memberGrant = new ArrayList<>();
        }
        return this.memberGrant;
    }

    @Override
    public String hierarchy() {
        return hierarchy;
    }

    public void setHierarchy(String value) {
        this.hierarchy = value;
    }

    @Override
    public String access() {
        return access;
    }

    public void setAccess(String value) {
        this.access = value;
    }

    @Override
    public String topLevel() {
        return topLevel;
    }

    public void setTopLevel(String value) {
        this.topLevel = value;
    }

    @Override
    public String bottomLevel() {
        return bottomLevel;
    }

    public void setBottomLevel(String value) {
        this.bottomLevel = value;
    }

    @Override
    public String rollupPolicy() {
        if (rollupPolicy == null) {
            return "full";
        } else {
            return rollupPolicy;
        }
    }

    public void setRollupPolicy(String value) {
        this.rollupPolicy = value;
    }

    public void setMemberGrant(List<MemberGrant> memberGrant) {
        this.memberGrant = memberGrant;
    }

}
