/*
 * Copyright (c) 2023 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Permission", propOrder = {"name", "id", "createdTimestamp", "lastSchemaUpdate", "description",
    "annotations", "roleID", "process", "readDefinition", "read", "write"})
@XmlSeeAlso({DatabasePermission.class, DataSourcePermission.class, DimensionPermission.class,
    MiningStructurePermission.class, MiningModelPermission.class, CubePermission.class})
public class Permission {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "ID")
    protected String id;
    @XmlElement(name = "CreatedTimestamp")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdTimestamp;
    @XmlElement(name = "LastSchemaUpdate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastSchemaUpdate;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Annotations")
    protected Permission.Annotations annotations;
    @XmlElement(name = "RoleID", required = true)
    protected String roleID;
    @XmlElement(name = "Process")
    protected Boolean process;
    @XmlElement(name = "ReadDefinition")
    protected String readDefinition;
    @XmlElement(name = "Read")
    protected String read;
    @XmlElement(name = "Write")
    protected String write;

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getID() {
        return id;
    }

    public void setID(String value) {
        this.id = value;
    }

    public XMLGregorianCalendar getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(XMLGregorianCalendar value) {
        this.createdTimestamp = value;
    }

    public XMLGregorianCalendar getLastSchemaUpdate() {
        return lastSchemaUpdate;
    }

    public void setLastSchemaUpdate(XMLGregorianCalendar value) {
        this.lastSchemaUpdate = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public Permission.Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Permission.Annotations value) {
        this.annotations = value;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String value) {
        this.roleID = value;
    }

    public Boolean isProcess() {
        return process;
    }

    public void setProcess(Boolean value) {
        this.process = value;
    }

    public String getReadDefinition() {
        return readDefinition;
    }

    public void setReadDefinition(String value) {
        this.readDefinition = value;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String value) {
        this.read = value;
    }

    public String getWrite() {
        return write;
    }

    public void setWrite(String write) {
        this.write = write;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"annotation"})
    public static class Annotations {

        @XmlElement(name = "Annotation")
        protected List<Annotation> annotation;

        public List<Annotation> getAnnotation() {
            return this.annotation;
        }

        public void setAnnotation(List<Annotation> annotation) {
            this.annotation = annotation;
        }
    }

}
