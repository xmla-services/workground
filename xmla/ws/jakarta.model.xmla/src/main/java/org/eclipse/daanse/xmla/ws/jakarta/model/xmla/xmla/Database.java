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

import java.math.BigInteger;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine.ImpersonationInfo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Database", propOrder = {

})
public class Database extends AbstractItem {

    @XmlElement(name = "LastUpdate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastUpdate;
    @XmlElement(name = "State")
    protected String state;
    @XmlElement(name = "ReadWriteMode", namespace = "http://schemas.microsoft.com/analysisservices/2008/engine/100")
    protected String readWriteMode;
    @XmlElement(name = "DbStorageLocation", namespace = "http://schemas.microsoft" +
        ".com/analysisservices/2008/engine/100/100")
    protected String dbStorageLocation;
    @XmlElement(name = "AggregationPrefix")
    protected String aggregationPrefix;
    @XmlElement(name = "ProcessingPriority")
    protected BigInteger processingPriority;
    @XmlElement(name = "EstimatedSize")
    protected Long estimatedSize;
    @XmlElement(name = "LastProcessed")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastProcessed;
    @XmlElement(name = "Language")
    protected BigInteger language;
    @XmlElement(name = "Collation")
    protected String collation;
    @XmlElement(name = "Visible")
    protected Boolean visible;
    @XmlElement(name = "MasterDataSourceID")
    protected String masterDataSourceID;
    @XmlElement(name = "DataSourceImpersonationInfo")
    protected ImpersonationInfo dataSourceImpersonationInfo;
    @XmlElement(name = "Accounts")
    protected Database.Accounts accounts;
    @XmlElement(name = "DataSources")
    protected Database.DataSources dataSources;
    @XmlElement(name = "DataSourceViews")
    protected Database.DataSourceViews dataSourceViews;
    @XmlElement(name = "Dimensions")
    protected Database.Dimensions dimensions;
    @XmlElement(name = "Cubes")
    protected Database.Cubes cubes;
    @XmlElement(name = "MiningStructures")
    protected Database.MiningStructures miningStructures;
    @XmlElement(name = "Roles")
    protected Database.Roles roles;
    @XmlElement(name = "Assemblies")
    protected Database.Assemblies assemblies;
    @XmlElement(name = "DatabasePermissions")
    protected Database.DatabasePermissions databasePermissions;
    @XmlElement(name = "Translations")
    protected Database.Translations translations;
    @XmlElement(name = "StorageEngineUsed", namespace = "http://schemas.microsoft" +
        ".com/analysisservices/2010/engine/200/200")
    protected String storageEngineUsed;
    @XmlElement(name = "ImagePath", namespace = "http://schemas.microsoft.com/analysisservices/2010/engine/200/200")
    protected String imagePath;
    @XmlElement(name = "ImageUrl", namespace = "http://schemas.microsoft.com/analysisservices/2010/engine/200/200")
    protected String imageUrl;
    @XmlElement(name = "ImageUniqueID", namespace = "http://schemas.microsoft.com/analysisservices/2010/engine/200/200")
    protected String imageUniqueID;
    @XmlElement(name = "ImageVersion", namespace = "http://schemas.microsoft.com/analysisservices/2010/engine/200/200")
    protected String imageVersion;
    @XmlElement(name = "Token", namespace = "http://schemas.microsoft.com/analysisservices/2010/engine/200/200")
    protected String token;
    @XmlElement(name = "CompatibilityLevel", namespace = "http://schemas.microsoft" +
        ".com/analysisservices/2010/engine/200")
    protected BigInteger compatibilityLevel;
    @XmlElement(name = "DirectQueryMode", namespace = "http://schemas.microsoft" +
        ".com/analysisservices/2011/engine/300/300")
    protected String directQueryMode;

    public XMLGregorianCalendar getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(XMLGregorianCalendar value) {
        this.lastUpdate = value;
    }

    public String getState() {
        return state;
    }

    public void setState(String value) {
        this.state = value;
    }

    public String getReadWriteMode() {
        return readWriteMode;
    }

    public void setReadWriteMode(String value) {
        this.readWriteMode = value;
    }

    public String getDbStorageLocation() {
        return dbStorageLocation;
    }

    public void setDbStorageLocation(String value) {
        this.dbStorageLocation = value;
    }

    public String getAggregationPrefix() {
        return aggregationPrefix;
    }

    public void setAggregationPrefix(String value) {
        this.aggregationPrefix = value;
    }

    public BigInteger getProcessingPriority() {
        return processingPriority;
    }

    public void setProcessingPriority(BigInteger value) {
        this.processingPriority = value;
    }

    public Long getEstimatedSize() {
        return estimatedSize;
    }

    public void setEstimatedSize(Long value) {
        this.estimatedSize = value;
    }

    public XMLGregorianCalendar getLastProcessed() {
        return lastProcessed;
    }

    public void setLastProcessed(XMLGregorianCalendar value) {
        this.lastProcessed = value;
    }

    public BigInteger getLanguage() {
        return language;
    }

    public void setLanguage(BigInteger value) {
        this.language = value;
    }

    public String getCollation() {
        return collation;
    }

    public void setCollation(String value) {
        this.collation = value;
    }

    public Boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean value) {
        this.visible = value;
    }

    public String getMasterDataSourceID() {
        return masterDataSourceID;
    }

    public void setMasterDataSourceID(String value) {
        this.masterDataSourceID = value;
    }

    public ImpersonationInfo getDataSourceImpersonationInfo() {
        return dataSourceImpersonationInfo;
    }

    public void setDataSourceImpersonationInfo(ImpersonationInfo value) {
        this.dataSourceImpersonationInfo = value;
    }

    public Database.Accounts getAccounts() {
        return accounts;
    }

    public void setAccounts(Database.Accounts value) {
        this.accounts = value;
    }

    public Database.DataSources getDataSources() {
        return dataSources;
    }

    public void setDataSources(Database.DataSources value) {
        this.dataSources = value;
    }

    public Database.DataSourceViews getDataSourceViews() {
        return dataSourceViews;
    }

    public void setDataSourceViews(Database.DataSourceViews value) {
        this.dataSourceViews = value;
    }

    public Database.Dimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(Database.Dimensions value) {
        this.dimensions = value;
    }

    public Database.Cubes getCubes() {
        return cubes;
    }

    public void setCubes(Database.Cubes value) {
        this.cubes = value;
    }

    public Database.MiningStructures getMiningStructures() {
        return miningStructures;
    }

    public void setMiningStructures(Database.MiningStructures value) {
        this.miningStructures = value;
    }

    public Database.Roles getRoles() {
        return roles;
    }

    public void setRoles(Database.Roles value) {
        this.roles = value;
    }

    public Database.Assemblies getAssemblies() {
        return assemblies;
    }

    public void setAssemblies(Database.Assemblies value) {
        this.assemblies = value;
    }

    public Database.DatabasePermissions getDatabasePermissions() {
        return databasePermissions;
    }

    public void setDatabasePermissions(Database.DatabasePermissions value) {
        this.databasePermissions = value;
    }

    public Database.Translations getTranslations() {
        return translations;
    }

    public void setTranslations(Database.Translations value) {
        this.translations = value;
    }

    public String getStorageEngineUsed() {
        return storageEngineUsed;
    }

    public void setStorageEngineUsed(String value) {
        this.storageEngineUsed = value;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String value) {
        this.imagePath = value;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String value) {
        this.imageUrl = value;
    }

    public String getImageUniqueID() {
        return imageUniqueID;
    }

    public void setImageUniqueID(String value) {
        this.imageUniqueID = value;
    }

    public String getImageVersion() {
        return imageVersion;
    }

    public void setImageVersion(String value) {
        this.imageVersion = value;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String value) {
        this.token = value;
    }

    public BigInteger getCompatibilityLevel() {
        return compatibilityLevel;
    }

    public void setCompatibilityLevel(BigInteger value) {
        this.compatibilityLevel = value;
    }

    public String getDirectQueryMode() {
        return directQueryMode;
    }

    public void setDirectQueryMode(String value) {
        this.directQueryMode = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"account"})
    public static class Accounts {

        @XmlElement(name = "Account")
        protected List<Account> account;

        public List<Account> getAccount() {
            return this.account;
        }

        public void setAccount(List<Account> account) {
            this.account = account;
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
    @XmlType(name = "", propOrder = {"cube"})
    public static class Cubes {

        @XmlElement(name = "Cube")
        protected List<Cube> cube;

        public List<Cube> getCube() {
            return this.cube;
        }

        public void setCube(List<Cube> cube) {
            this.cube = cube;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"databasePermission"})
    public static class DatabasePermissions {

        @XmlElement(name = "DatabasePermission")
        protected List<DatabasePermission> databasePermission;

        public List<DatabasePermission> getDatabasePermission() {
            return this.databasePermission;
        }

        public void setDatabasePermission(List<DatabasePermission> databasePermission) {
            this.databasePermission = databasePermission;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"dataSource"})
    public static class DataSources {

        @XmlElement(name = "DataSource")
        protected List<DataSource> dataSource;

        public List<DataSource> getDataSource() {
            return this.dataSource;
        }

        public void setDataSource(List<DataSource> dataSource) {
            this.dataSource = dataSource;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"dataSourceView"})
    public static class DataSourceViews {

        @XmlElement(name = "DataSourceView")
        protected List<DataSourceView> dataSourceView;

        public List<DataSourceView> getDataSourceView() {
            return this.dataSourceView;
        }

        public void setDataSourceView(List<DataSourceView> dataSourceView) {
            this.dataSourceView = dataSourceView;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"dimension"})
    public static class Dimensions {

        @XmlElement(name = "Dimension")
        protected List<Dimension> dimension;

        public List<Dimension> getDimension() {
            return this.dimension;
        }

        public void setDimension(List<Dimension> dimension) {
            this.dimension = dimension;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"miningStructure"})
    public static class MiningStructures {

        @XmlElement(name = "MiningStructure")
        protected List<MiningStructure> miningStructure;

        public List<MiningStructure> getMiningStructure() {
            return this.miningStructure;
        }

        public void setMiningStructure(List<MiningStructure> miningStructure) {
            this.miningStructure = miningStructure;
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
    @XmlType(name = "", propOrder = {"translation"})
    public static class Translations {

        @XmlElement(name = "Translation")
        protected List<Translation> translation;

        public List<Translation> getTranslation() {
            return this.translation;
        }

        public void setTranslation(List<Translation> translation) {
            this.translation = translation;
        }
    }

}
