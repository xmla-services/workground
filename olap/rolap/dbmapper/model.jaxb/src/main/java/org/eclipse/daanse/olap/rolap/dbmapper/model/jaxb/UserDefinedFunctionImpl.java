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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingScript;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingUserDefinedFunction;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * @deprecated
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@Deprecated(since = "new version")
public class UserDefinedFunctionImpl implements MappingUserDefinedFunction {

    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "className", required = true)
    protected String className;
    @XmlElement(name = "Script", type = ScriptImpl.class)
    protected MappingScript script;

    @Override
    public String name() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @Override
    public String className() {
        return className;
    }

    @Override
    public MappingScript script() {
        return script;
    }

    public void setClassName(String value) {
        this.className = value;
    }

    public void setScript(MappingScript script) {
        this.script = script;
    }
}
