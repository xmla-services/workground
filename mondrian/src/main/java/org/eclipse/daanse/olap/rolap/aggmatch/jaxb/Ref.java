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
 *   SmartCity Jena - initial
 *   Stefan Bischof (bipolis.org) - initial
 */
package org.eclipse.daanse.olap.rolap.aggmatch.jaxb;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "Ref")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({LevelMapRef.class, MeasureMapRef.class, IgnoreMapRef.class, FactCountMatchRef.class,
    ForeignKeyMatchRef.class, TableMatchRef.class})
public abstract class Ref extends Base {

    @XmlAttribute(name = "refId", required = true)
    String refId;

    protected String getTag() {
        return getRefId();
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    @Override
    protected String getName() {
        return "Ref";
    }
}
