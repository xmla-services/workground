
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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAnnotation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCube;
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
@XmlType(name = "", propOrder = {"annotations", "parameters", "dimensions", "cubes", "virtualCubes", "namedSets", "roles",
    "userDefinedFunctions"})
@XmlRootElement(name = "Schema")
public class SchemaImpl implements Schema {

    /**
     * A bcd
     */
    @XmlElement(name = "Annotation", type = AnnotationImpl.class)
    @XmlElementWrapper(name = "Annotations")
    protected List<MappingAnnotation> annotations;
    @XmlElement(name = "Parameter", type = ParameterImpl.class)
    protected List<Parameter> parameters;
    @XmlElement(name = "Dimension", type = PrivateDimensionImpl.class)
    protected List<PrivateDimension> dimensions;
    @XmlElement(name = "Cube", required = true, type = CubeImpl.class)
    protected List<MappingCube> cubes;
    @XmlElement(name = "VirtualCube", type = VirtualCubeImpl.class)
    protected List<VirtualCube> virtualCubes;
    @XmlElement(name = "NamedSet", type = NamedSetImpl.class)
    protected List<NamedSet> namedSets;
    @XmlElement(name = "Role", type = RoleImpl.class)
    protected List<Role> roles;
    /**
     * @deprecated
     */
    @Deprecated(since = "new version")
    @XmlElement(name = "UserDefinedFunction", type = UserDefinedFunctionImpl.class)
    protected List<UserDefinedFunction> userDefinedFunctions;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "description")
    protected String description;
    @XmlAttribute(name = "measuresCaption")
    protected String measuresCaption;
    @XmlAttribute(name = "defaultRole")
    protected String defaultRole;

    @Override
    public List<MappingAnnotation> annotations() {
        return annotations;
    }

    public void setAnnotations(List<MappingAnnotation> value) {
        this.annotations = value;
    }

    @Override
    public List<Parameter> parameters() {
        if (parameters == null) {
            parameters = new ArrayList<>();
        }
        return this.parameters;
    }

    @Override
    public List<PrivateDimension> dimensions() {
        if (dimensions == null) {
            dimensions = new ArrayList<>();
        }
        return this.dimensions;
    }

    @Override
    public List<MappingCube> cubes() {
        if (cubes == null) {
            cubes = new ArrayList<>();
        }
        return this.cubes;
    }

    @Override
    public List<VirtualCube> virtualCubes() {
        if (virtualCubes == null) {
            virtualCubes = new ArrayList<>();
        }
        return this.virtualCubes;
    }

    @Override
    public List<NamedSet> namedSets() {
        if (namedSets == null) {
            namedSets = new ArrayList<>();
        }
        return this.namedSets;
    }

    @Override
    public List<Role> roles() {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        return this.roles;
    }

    /**
     * @return @deprecated
     */
    @Deprecated(since="new version")
    @Override
    public List<UserDefinedFunction> userDefinedFunctions() {
        if (userDefinedFunctions == null) {
            userDefinedFunctions = new ArrayList<>();
        }
        return this.userDefinedFunctions;
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

    public void setNamedSets(List<NamedSet> namedSets) {
        this.namedSets = namedSets;

    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;

    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;

    }


    /**
     * @return @deprecated
     */
    @Deprecated(since="new version")
    public void setUserDefinedFunctions(List<UserDefinedFunction> userDefinedFunctions) {
        this.userDefinedFunctions = userDefinedFunctions;
    }

    public void setVirtualCubes(List<VirtualCube> virtualCubs) {
        this.virtualCubes = virtualCubs;
    }

    public void setDimensions(List<PrivateDimension> dimensions) {
        this.dimensions = dimensions;
    }

    public void setCubes(List<MappingCube> cubes) {
        this.cubes = cubes;
    }

}
