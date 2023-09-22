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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAnnotation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Role;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.SchemaGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Union;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "annotations", "schemaGrants", "union" })
public class RoleImpl implements Role {

    @XmlElement(name = "Annotation", type = AnnotationImpl.class)
    @XmlElementWrapper(name = "Annotations")
    protected List<MappingAnnotation> annotations;
    @XmlElement(name = "SchemaGrant", type = SchemaGrantImpl.class)
    protected List<SchemaGrant> schemaGrants;
    @XmlElement(name = "Union", required = true, type = UnionImpl.class)
    protected Union union;
    @XmlAttribute(name = "name", required = true)
    protected String name;

    @Override
    public List<MappingAnnotation> annotations() {
        return annotations;
    }

    public void setAnnotations(List<MappingAnnotation> value) {
        this.annotations = value;
    }

    @Override
    public List<SchemaGrant> schemaGrants() {
        if (schemaGrants == null) {
            schemaGrants = new ArrayList<>();
        }
        return this.schemaGrants;
    }

    @Override
    public Union union() {
        return union;
    }

    public void setUnion(UnionImpl value) {
        this.union = value;
    }

    @Override
    public String name() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public void setSchemaGrants(List<SchemaGrant> schemaGrants) {
        this.schemaGrants = schemaGrants;
    }
}
