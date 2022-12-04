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

import org.eclipse.daanse.olap.rolap.dbmapper.api.SchemaGrant;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "cubeGrant" })
public class SchemaGrantImpl implements SchemaGrant {

    @XmlElement(name = "CubeGrant", required = true)
    protected List<CubeGrantImpl> cubeGrant;
    @XmlAttribute(name = "access", required = true)
    protected String access;

    @Override
    public List<CubeGrantImpl> cubeGrant() {
        if (cubeGrant == null) {
            cubeGrant = new ArrayList<CubeGrantImpl>();
        }
        return this.cubeGrant;
    }

    @Override
    public String access() {
        return access;
    }

    public void setAccess(String value) {
        this.access = value;
    }

}
