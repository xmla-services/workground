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

import jakarta.xml.bind.annotation.*;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Script;
import org.eclipse.daanse.olap.rolap.dbmapper.api.UserDefinedFunction;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@Deprecated
public class UserDefinedFunctionImpl implements UserDefinedFunction {

    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "className", required = true)
    protected String className;
    @XmlElement(name = "Script")
    protected ScriptImpl script;

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
    public Script script() {
        return script;
    }

    public void setClassName(String value) {
        this.className = value;
    }

}
