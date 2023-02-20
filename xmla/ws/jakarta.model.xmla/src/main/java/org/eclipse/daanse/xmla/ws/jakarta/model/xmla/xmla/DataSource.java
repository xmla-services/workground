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
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine.ImpersonationInfo;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataSource", propOrder = {

})
@XmlSeeAlso({RelationalDataSource.class, OlapDataSource.class})
public abstract class DataSource {

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
    protected DataSource.Annotations annotations;
    @XmlElement(name = "ManagedProvider")
    protected String managedProvider;
    @XmlElement(name = "ConnectionString", required = true)
    protected String connectionString;
    @XmlElement(name = "ConnectionStringSecurity")
    protected String connectionStringSecurity;
    @XmlElement(name = "ImpersonationInfo")
    protected ImpersonationInfo impersonationInfo;
    @XmlElement(name = "Isolation")
    protected String isolation;
    @XmlElement(name = "MaxActiveConnections")
    protected BigInteger maxActiveConnections;
    @XmlElement(name = "Timeout")
    protected Duration timeout;
    @XmlElement(name = "DataSourcePermissions")
    protected DataSource.DataSourcePermissions dataSourcePermissions;
    @XmlElement(name = "QueryImpersonationInfo", namespace = "http://schemas.microsoft" +
        ".com/analysisservices/2011/engine/300")
    protected ImpersonationInfo queryImpersonationInfo;
    @XmlElement(name = "QueryHints", namespace = "http://schemas.microsoft.com/analysisservices/2011/engine/300")
    protected String queryHints;

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

    public DataSource.Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(DataSource.Annotations value) {
        this.annotations = value;
    }

    public String getManagedProvider() {
        return managedProvider;
    }

    public void setManagedProvider(String value) {
        this.managedProvider = value;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String value) {
        this.connectionString = value;
    }

    public String getConnectionStringSecurity() {
        return connectionStringSecurity;
    }

    public void setConnectionStringSecurity(String value) {
        this.connectionStringSecurity = value;
    }

    public ImpersonationInfo getImpersonationInfo() {
        return impersonationInfo;
    }

    public void setImpersonationInfo(ImpersonationInfo value) {
        this.impersonationInfo = value;
    }

    public String getIsolation() {
        return isolation;
    }

    public void setIsolation(String value) {
        this.isolation = value;
    }

    public BigInteger getMaxActiveConnections() {
        return maxActiveConnections;
    }

    public void setMaxActiveConnections(BigInteger value) {
        this.maxActiveConnections = value;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration value) {
        this.timeout = value;
    }

    public DataSource.DataSourcePermissions getDataSourcePermissions() {
        return dataSourcePermissions;
    }

    public void setDataSourcePermissions(DataSource.DataSourcePermissions value) {
        this.dataSourcePermissions = value;
    }

    public ImpersonationInfo getQueryImpersonationInfo() {
        return queryImpersonationInfo;
    }

    public void setQueryImpersonationInfo(ImpersonationInfo value) {
        this.queryImpersonationInfo = value;
    }

    public String getQueryHints() {
        return queryHints;
    }

    public void setQueryHints(String value) {
        this.queryHints = value;
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
    @XmlType(name = "", propOrder = {"dataSourcePermission"})
    public static class DataSourcePermissions {

        @XmlElement(name = "DataSourcePermission")
        protected List<DataSourcePermission> dataSourcePermission;

        public List<DataSourcePermission> getDataSourcePermission() {
            return this.dataSourcePermission;
        }

        public void setDataSourcePermission(List<DataSourcePermission> dataSourcePermission) {
            this.dataSourcePermission = dataSourcePermission;
        }
    }

}
