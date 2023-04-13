
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

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Annotation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Cube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.NamedSet;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Parameter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.PrivateDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Role;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.UserDefinedFunction;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.VirtualCube;

/**
 * A schema is a collection of cubes and virtual cubes. It can also contain
 * shared dimensions (for use by those cubes), named sets, roles, and
 * declarations of user-defined functions.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"annotations", "parameter", "dimension", "cube", "virtualCube", "namedSet", "role",
    "userDefinedFunction"})
@XmlRootElement(name = "Schema")
public class SchemaImpl implements Schema {

    /**
     * A bcd
     */
    @XmlElement(name = "Annotation", type = AnnotationImpl.class)
    @XmlElementWrapper(name = "Annotations")
    protected List<Annotation> annotations;
    @XmlElement(name = "Parameter", type = ParameterImpl.class)
    protected List<Parameter> parameter;
    @XmlElement(name = "Dimension", type = PrivateDimensionImpl.class)
    protected List<PrivateDimension> dimension;
    @XmlElement(name = "Cube", required = true, type = CubeImpl.class)
    protected List<Cube> cube;
    @XmlElement(name = "VirtualCube", type = VirtualCubeImpl.class)
    protected List<VirtualCube> virtualCube;
    @XmlElement(name = "NamedSet", type = NamedSetImpl.class)
    protected List<NamedSet> namedSet;
    @XmlElement(name = "Role", type = RoleImpl.class)
    protected List<Role> role;
    @Deprecated
    @XmlElement(name = "UserDefinedFunction", type = UserDefinedFunctionImpl.class)
    protected List<UserDefinedFunction> userDefinedFunction;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "description")
    protected String description;
    @XmlAttribute(name = "measuresCaption")
    protected String measuresCaption;
    @XmlAttribute(name = "defaultRole")
    protected String defaultRole;

    @Override
    public List<Annotation> annotations() {
        return annotations;
    }

    public void setAnnotations(List<Annotation> value) {
        this.annotations = value;
    }

    @Override
    public List<Parameter> parameter() {
        if (parameter == null) {
            parameter = new ArrayList<>();
        }
        return this.parameter;
    }

    @Override
    public List<PrivateDimension> dimension() {
        if (dimension == null) {
            dimension = new ArrayList<>();
        }
        return this.dimension;
    }

    @Override
    public List<Cube> cube() {
        if (cube == null) {
            cube = new ArrayList<>();
        }
        return this.cube;
    }

    @Override
    public List<VirtualCube> virtualCube() {
        if (virtualCube == null) {
            virtualCube = new ArrayList<>();
        }
        return this.virtualCube;
    }

    @Override
    public List<NamedSet> namedSet() {
        if (namedSet == null) {
            namedSet = new ArrayList<>();
        }
        return this.namedSet;
    }

    @Override
    public List<Role> roles() {
        if (role == null) {
            role = new ArrayList<>();
        }
        return this.role;
    }

    @Override
    @Deprecated
    public List<UserDefinedFunction> userDefinedFunctions() {
        if (userDefinedFunction == null) {
            userDefinedFunction = new ArrayList<>();
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

    public void setNamedSet(List<NamedSet> namedSet) {
        this.namedSet = namedSet;

    }

    public void setParameter(List<Parameter> parameter) {
        this.parameter = parameter;

    }

    public void setRole(List<Role> role) {
        this.role = role;

    }

    public void setUserDefinedFunction(List<UserDefinedFunction> userDefinedFunction) {
        this.userDefinedFunction = userDefinedFunction;
    }

    public void setVirtualCube(List<VirtualCube> virtualCub) {
        this.virtualCube = virtualCub;
    }

}
