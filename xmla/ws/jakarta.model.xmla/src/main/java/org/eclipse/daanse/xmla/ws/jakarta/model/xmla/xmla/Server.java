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

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Server", propOrder = {

})
public class Server {

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
    protected Server.Annotations annotations;
    @XmlElement(name = "ProductName")
    protected String productName;
    @XmlElement(name = "Edition")
    protected String edition;
    @XmlElement(name = "EditionID")
    protected Long editionID;
    @XmlElement(name = "Version")
    protected String version;
    @XmlElement(name = "ServerMode", namespace = "http://schemas.microsoft.com/analysisservices/2011/engine/300")
    protected String serverMode;
    @XmlElement(name = "ProductLevel")
    protected String productLevel;
    @XmlElement(name = "DefaultCompatibilityLevel", namespace = "http://schemas.microsoft" +
        ".com/analysisservices/2012/engine/400")
    protected Long defaultCompatibilityLevel;
    @XmlElement(name = "SupportedCompatibilityLevels", namespace = "http://schemas.microsoft" +
        ".com/analysisservices/2013/engine/600")
    protected String supportedCompatibilityLevels;
    @XmlElement(name = "Databases")
    protected Server.Databases databases;
    @XmlElement(name = "Assemblies")
    protected Server.Assemblies assemblies;
    @XmlElement(name = "Traces")
    protected Server.Traces traces;
    @XmlElement(name = "Roles")
    protected Server.Roles roles;
    @XmlElement(name = "ServerProperties")
    protected Server.ServerProperties serverProperties;

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

    public Server.Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Server.Annotations value) {
        this.annotations = value;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String value) {
        this.productName = value;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String value) {
        this.edition = value;
    }

    public Long getEditionID() {
        return editionID;
    }

    public void setEditionID(Long value) {
        this.editionID = value;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String value) {
        this.version = value;
    }

    public String getServerMode() {
        return serverMode;
    }

    public void setServerMode(String value) {
        this.serverMode = value;
    }

    public String getProductLevel() {
        return productLevel;
    }

    public void setProductLevel(String value) {
        this.productLevel = value;
    }

    public Long getDefaultCompatibilityLevel() {
        return defaultCompatibilityLevel;
    }

    public void setDefaultCompatibilityLevel(Long value) {
        this.defaultCompatibilityLevel = value;
    }

    public String getSupportedCompatibilityLevels() {
        return supportedCompatibilityLevels;
    }

    public void setSupportedCompatibilityLevels(String value) {
        this.supportedCompatibilityLevels = value;
    }

    public Server.Databases getDatabases() {
        return databases;
    }

    public void setDatabases(Server.Databases value) {
        this.databases = value;
    }

    public Server.Assemblies getAssemblies() {
        return assemblies;
    }

    public void setAssemblies(Server.Assemblies value) {
        this.assemblies = value;
    }

    public Server.Traces getTraces() {
        return traces;
    }

    public void setTraces(Server.Traces value) {
        this.traces = value;
    }

    public Server.Roles getRoles() {
        return roles;
    }

    public void setRoles(Server.Roles value) {
        this.roles = value;
    }

    public Server.ServerProperties getServerProperties() {
        return serverProperties;
    }

    public void setServerProperties(Server.ServerProperties value) {
        this.serverProperties = value;
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

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"assembly"})
    public static class Assemblies {

        @XmlElement(name = "Assembly")
        protected List<Assembly> assembly;

        public List<Assembly> getAssembly() {
            return this.assembly;
        }

        public void setAssembly(List<Assembly> assembly) {
            this.assembly = assembly;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"database"})
    public static class Databases {

        @XmlElement(name = "Database")
        protected List<Database> database;
        public List<Database> getDatabase() {
            return this.database;
        }

        public void setDatabase(List<Database> database) {
            this.database = database;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"role"})
    public static class Roles {

        @XmlElement(name = "Role")
        protected List<Role> role;

        public List<Role> getRole() {
            return this.role;
        }

        public void setRole(List<Role> role) {
            this.role = role;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"serverProperty"})
    public static class ServerProperties {

        @XmlElement(name = "ServerProperty")
        protected List<ServerProperty> serverProperty;

        public List<ServerProperty> getServerProperty() {
            return this.serverProperty;
        }

        public void setServerProperty(List<ServerProperty> serverProperty) {
            this.serverProperty = serverProperty;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"trace"})
    public static class Traces {

        @XmlElement(name = "Trace")
        protected List<Trace> trace;

        public List<Trace> getTrace() {
            return this.trace;
        }

        public void setTrace(List<Trace> trace) {
            this.trace = trace;
        }
    }

}
