
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
import org.eclipse.daanse.olap.rolap.dbmapper.api.Schema;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * A schema is a collection of cubes and virtual cubes. It can also contain
 * shared dimensions (for use by those cubes), named sets, roles, and
 * declarations of user-defined functions.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "annotations", "parameter", "dimension", "cube", "virtualCube", "namedSet", "role",
        "userDefinedFunction" })
@XmlRootElement(name = "Schema")
public class SchemaImpl implements Schema {

    /**
     * A bcd
     */
    @XmlElement(name = "Annotation")
    @XmlElementWrapper(name = "Annotations")
    protected List<AnnotationImpl> annotations;
    @XmlElement(name = "Parameter")
    protected List<ParameterImpl> parameter;
    @XmlElement(name = "Dimension")
    protected List<PrivateDimensionImpl> dimension;
    @XmlElement(name = "Cube", required = true)
    protected List<CubeImpl> cube;
    @XmlElement(name = "VirtualCube")
    protected List<VirtualCubeImpl> virtualCube;
    @XmlElement(name = "NamedSet")
    protected List<NamedSetImpl> namedSet;
    @XmlElement(name = "Role")
    protected List<RoleImpl> role;
    @Deprecated
    @XmlElement(name = "UserDefinedFunction")
    protected List<UserDefinedFunctionImpl> userDefinedFunction;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "description")
    protected String description;
    @XmlAttribute(name = "measuresCaption")
    protected String measuresCaption;
    @XmlAttribute(name = "defaultRole")
    protected String defaultRole;

    @Override
    public List<AnnotationImpl> annotations() {
        return annotations;
    }

    public void setAnnotations(List<AnnotationImpl> value) {
        this.annotations = value;
    }

    @Override
    public List<ParameterImpl> parameter() {
        if (parameter == null) {
            parameter = new ArrayList<ParameterImpl>();
        }
        return this.parameter;
    }

    @Override
    public List<PrivateDimensionImpl> dimension() {
        if (dimension == null) {
            dimension = new ArrayList<PrivateDimensionImpl>();
        }
        return this.dimension;
    }

    @Override
    public List<CubeImpl> cube() {
        if (cube == null) {
            cube = new ArrayList<CubeImpl>();
        }
        return this.cube;
    }

    @Override
    public List<VirtualCubeImpl> virtualCube() {
        if (virtualCube == null) {
            virtualCube = new ArrayList<VirtualCubeImpl>();
        }
        return this.virtualCube;
    }

    @Override
    public List<NamedSetImpl> namedSet() {
        if (namedSet == null) {
            namedSet = new ArrayList<NamedSetImpl>();
        }
        return this.namedSet;
    }

    @Override
    public List<RoleImpl> roles() {
        if (role == null) {
            role = new ArrayList<RoleImpl>();
        }
        return this.role;
    }

    @Override
    @Deprecated
    public List<UserDefinedFunctionImpl> userDefinedFunctions() {
        if (userDefinedFunction == null) {
            userDefinedFunction = new ArrayList<UserDefinedFunctionImpl>();
        }
        return this.userDefinedFunction;
    }

    @Override
    public String name() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @Override
    public String description() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    @Override
    public String measuresCaption() {
        return measuresCaption;
    }

    public void setMeasuresCaption(String value) {
        this.measuresCaption = value;
    }

    @Override
    public String defaultRole() {
        return defaultRole;
    }

    @Override
    public void display(PrintWriter pw, int i) {
        //TODO
    }

    public void setDefaultRole(String value) {
        this.defaultRole = value;
    }
}
