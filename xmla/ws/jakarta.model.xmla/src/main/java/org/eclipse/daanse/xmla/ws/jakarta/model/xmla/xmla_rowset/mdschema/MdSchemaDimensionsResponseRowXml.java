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
package org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_rowset.mdschema;

import jakarta.xml.bind.annotation.*;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.enums.DimensionTypeEnum;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.enums.DimensionUniqueSettingEnum;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_rowset.Row;

import java.io.Serializable;

/**
 * This schema rowset describes the dimensions within a database.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MdSchemaDimensionsResponseRowXml")
public class MdSchemaDimensionsResponseRowXml extends Row implements Serializable {

    @XmlTransient
    private final static long serialVersionUID = 474404709256189241L;

    /**
     * The name of the database.
     */
    @XmlElement(name = "CATALOG_NAME", required = false)
    private String catalogName;

    /**
     * The name of the schema.
     */
    @XmlElement(name = "SCHEMA_NAME", required = false)
    private String schemaName;

    /**
     * The name of the cube.
     */
    @XmlElement(name = "CUBE_NAME", required = false)
    private String cubeName;

    /**
     * The name of the dimension.
     */
    @XmlElement(name = "DIMENSION_NAME", required = false)
    private String dimensionName;

    /**
     * The unique name of the dimension.
     */
    @XmlElement(name = "DIMENSION_UNIQUE_NAME", required = false)
    private String dimensionUniqueName;

    /**
     * The GUID of the dimension.
     */
    @XmlElement(name = "DIMENSION_GUID", required = false)
    private Integer dimensionGuid;

    /**
     * The caption of the dimension.
     */
    @XmlElement(name = "DIMENSION_CAPTION", required = false)
    private String dimensionCaption;

    /**
     * The position of the dimension within the cube.
     */
    @XmlElement(name = "DIMENSION_ORDINAL", required = false)
    private Integer dimensionOptional;

    /**
     * The type of the dimension. Valid values are:
     * 0 - UNKNOWN
     * 1 - TIME
     * 2 - MEASURE
     * 3 - OTHER
     * 5 - QUANTITATIVE
     * 6- ACCOUNTS
     * 7 - CUSTOMERS
     * 8- PRODUCTS
     * 9 - SCENARIO
     * 10- UTILITY
     * 11 - CURRENCY
     * 12 - RATES
     * 13 - CHANNEL
     * 14 - PROMOTION
     * 15 - ORGANIZATION
     * 16 - BILL OF MATERIALS
     * 17 – GEOGRAPHY
     */
    @XmlElement(name = "DIMENSION_TYPE", required = false)
    private DimensionTypeEnum dimensionType;

    /**
     * The number of members in the key
     * attribute.
     */
    @XmlElement(name = "DIMENSION_CARDINALITY", required = false)
    private Integer dimensionCardinality;

    /**
     * The default hierarchy of the dimension.
     */
    @XmlElement(name = "DEFAULT_HIERARCHY", required = false)
    private String defaultHierarchy;

    /**
     * A description of the dimension.
     */
    @XmlElement(name = "DESCRIPTION", required = false)
    private String description;

    /**
     * When true, indicates that the dimension is
     * virtual; otherwise false.
     */
    @XmlElement(name = "IS_VIRTUAL", required = false)
    private Boolean isVirtual;

    /**
     * When true, indicates that the dimension is
     * write-enabled; otherwise false
     */
    @XmlElement(name = "IS_READWRITE", required = false)
    private Boolean isReadWrite;

    /**
     * A bitmask that specifies which columns
     * contain unique values:
     * 0x00000001 - Member key columns
     * establish uniqueness.
     * 0x00000002 - Member name columns
     * establish uniqueness.
     */
    @XmlElement(name = "DIMENSION_UNIQUE_SETTINGS", required = false)
    private DimensionUniqueSettingEnum dimensionUniqueSetting;

    /**
     * The name of the master dimension.
     */
    @XmlElement(name = "DIMENSION_MASTER_NAME", required = false)
    private String dimensionMasterName;

    /**
     * When true, indicates that the dimension is
     * visible in a client application; otherwise
     * false.
     */
    @XmlElement(name = "DIMENSION_IS_VISIBLE", required = false)
    private Boolean dimensionIsVisible;

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getCubeName() {
        return cubeName;
    }

    public void setCubeName(String cubeName) {
        this.cubeName = cubeName;
    }

    public String getDimensionName() {
        return dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public String getDimensionUniqueName() {
        return dimensionUniqueName;
    }

    public void setDimensionUniqueName(String dimensionUniqueName) {
        this.dimensionUniqueName = dimensionUniqueName;
    }

    public Integer getDimensionGuid() {
        return dimensionGuid;
    }

    public void setDimensionGuid(Integer dimensionGuid) {
        this.dimensionGuid = dimensionGuid;
    }

    public String getDimensionCaption() {
        return dimensionCaption;
    }

    public void setDimensionCaption(String dimensionCaption) {
        this.dimensionCaption = dimensionCaption;
    }

    public Integer getDimensionOptional() {
        return dimensionOptional;
    }

    public void setDimensionOptional(Integer dimensionOptional) {
        this.dimensionOptional = dimensionOptional;
    }

    public DimensionTypeEnum getDimensionType() {
        return dimensionType;
    }

    public void setDimensionType(DimensionTypeEnum dimensionType) {
        this.dimensionType = dimensionType;
    }

    public Integer getDimensionCardinality() {
        return dimensionCardinality;
    }

    public void setDimensionCardinality(Integer dimensionCardinality) {
        this.dimensionCardinality = dimensionCardinality;
    }

    public String getDefaultHierarchy() {
        return defaultHierarchy;
    }

    public void setDefaultHierarchy(String defaultHierarchy) {
        this.defaultHierarchy = defaultHierarchy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getVirtual() {
        return isVirtual;
    }

    public void setVirtual(Boolean virtual) {
        isVirtual = virtual;
    }

    public Boolean getReadWrite() {
        return isReadWrite;
    }

    public void setReadWrite(Boolean readWrite) {
        isReadWrite = readWrite;
    }

    public DimensionUniqueSettingEnum getDimensionUniqueSetting() {
        return dimensionUniqueSetting;
    }

    public void setDimensionUniqueSetting(DimensionUniqueSettingEnum dimensionUniqueSetting) {
        this.dimensionUniqueSetting = dimensionUniqueSetting;
    }

    public String getDimensionMasterName() {
        return dimensionMasterName;
    }

    public void setDimensionMasterName(String dimensionMasterName) {
        this.dimensionMasterName = dimensionMasterName;
    }

    public Boolean getDimensionIsVisible() {
        return dimensionIsVisible;
    }

    public void setDimensionIsVisible(Boolean dimensionIsVisible) {
        this.dimensionIsVisible = dimensionIsVisible;
    }
}
