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

import org.eclipse.daanse.olap.rolap.dbmapper.api.CellFormatter;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Script;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CellFormatter", propOrder = { "script" })
public class CellFormatterImpl implements CellFormatter {

    @XmlAttribute(name = "className")
    protected  String className;
    @XmlElement(name = "Script")
    protected  ScriptImpl script;

    @Override
    public String className() {
        return className;
    }

    @Override
    public Script script() {
        return script;
    }
}
