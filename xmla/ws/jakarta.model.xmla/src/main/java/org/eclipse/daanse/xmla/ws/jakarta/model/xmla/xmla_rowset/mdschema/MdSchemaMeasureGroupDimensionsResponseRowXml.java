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
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.enums.DimensionCardinalityEnum;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.MeasureGroupDimension;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_rowset.Row;

import java.io.Serializable;
import java.util.List;

/**
 * This schema rowset enumerates the dimensions of measure groups.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MdSchemaMeasureGroupDimensionsResponseRowXml")
public class MdSchemaMeasureGroupDimensionsResponseRowXml extends Row implements Serializable {

    @XmlTransient
    private final static long serialVersionUID = 5622885644407965879L;

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
     * The name of the measure group.
     */
    @XmlElement(name = "MEASUREGROUP_NAME", required = false)
    private String measureGroupName;

    /**
     * The number of instances a measure in the
     * measure group can have for a single
     * dimension member.
     * Possible values include:
     */
    @XmlElement(name = "MEASUREGROUP_CARDINALITY", required = false)
    private String measureGroupCardinality;

    /**
     * The unique name for the dimension.
     */
    @XmlElement(name = "DIMENSION_UNIQUE_NAME", required = false)
    private String dimensionUniqueName;

    /**
     * The number of instances a dimension
     * member can have for a single instance of a
     * measure group measure.
     * Possible values include:
     * ONE
     * MANY
     */
    @XmlElement(name = "DIMENSION_CARDINALITY", required = false)
    private DimensionCardinalityEnum dimensionCardinality;

    /**
     * When true, indicates that hierarchies in the
     * dimension are visible; otherwise false.
     */
    @XmlElement(name = "DIMENSION_IS_VISIBLE", required = false)
    private Boolean dimensionIsVisible;

    /**
     * When true, indicates that the dimension is a
     * fact dimension; otherwise false.
     */
    @XmlElement(name = "DIMENSION_IS_FACT_DIMENSION", required = false)
    private Boolean dimensionIsFactDimension;

    /**
     * A list of dimensions for the reference
     * dimension. The column name of the nested
     * row is "MeasureGroupDimension". For
     * information on nested rowsets, see section
     * 2.2.4.1.3.1.1.
     */
    @XmlElement(name = "DIMENSION_PATH", required = false)
    private List<MeasureGroupDimension> dimensionPath;

    /**
     * The unique name of the attribute hierarchy
     * that represents the granularity of the
     * dimension.
     */
    @XmlElement(name = "DIMENSION_GRANULARITY", required = false)
    private String dimensionGranularity;

}
