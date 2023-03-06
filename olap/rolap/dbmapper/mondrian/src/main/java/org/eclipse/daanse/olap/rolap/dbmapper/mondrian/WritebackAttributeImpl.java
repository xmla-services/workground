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

import org.eclipse.daanse.olap.rolap.dbmapper.api.WritebackAttribute;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class WritebackAttributeImpl implements WritebackAttribute {

    @XmlAttribute(name = "dimension")
    private String dimension;
    @XmlAttribute(name = "column")
    private String column;

    @Override
    public String dimension() {
        return dimension;
    }

    @Override
    public String column() {
        return column;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public void setColumn(String column) {
        this.column = column;
    }
}
