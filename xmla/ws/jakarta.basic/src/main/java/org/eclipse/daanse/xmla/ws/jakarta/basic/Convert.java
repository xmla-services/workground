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
package org.eclipse.daanse.xmla.ws.jakarta.basic;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.daanse.xmla.api.common.enums.*;
import org.eclipse.daanse.xmla.api.common.properties.Content;
import org.eclipse.daanse.xmla.api.common.properties.Format;
import org.eclipse.daanse.xmla.api.discover.dbschema.catalogs.DbSchemaCatalogsRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.catalogs.DbSchemaCatalogsResponse;
import org.eclipse.daanse.xmla.api.discover.dbschema.columns.DbSchemaColumnsRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.columns.DbSchemaColumnsResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.columns.DbSchemaColumnsRestrictions;
import org.eclipse.daanse.xmla.api.discover.dbschema.providertypes.DbSchemaProviderTypesRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.providertypes.DbSchemaProviderTypesResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.providertypes.DbSchemaProviderTypesRestrictions;
import org.eclipse.daanse.xmla.api.discover.dbschema.schemata.DbSchemaSchemataRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.schemata.DbSchemaSchemataResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.schemata.DbSchemaSchemataRestrictions;
import org.eclipse.daanse.xmla.api.discover.dbschema.tables.DbSchemaTablesRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.tables.DbSchemaTablesResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.tables.DbSchemaTablesRestrictions;
import org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesRequest;
import org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesRestrictions;
import org.eclipse.daanse.xmla.api.discover.discover.enumerators.DiscoverEnumeratorsRequest;
import org.eclipse.daanse.xmla.api.discover.discover.enumerators.DiscoverEnumeratorsResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.enumerators.DiscoverEnumeratorsRestrictions;
import org.eclipse.daanse.xmla.api.discover.discover.keywords.DiscoverKeywordsRequest;
import org.eclipse.daanse.xmla.api.discover.discover.keywords.DiscoverKeywordsResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.keywords.DiscoverKeywordsRestrictions;
import org.eclipse.daanse.xmla.api.discover.discover.literals.DiscoverLiteralsRequest;
import org.eclipse.daanse.xmla.api.discover.discover.literals.DiscoverLiteralsResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.literals.DiscoverLiteralsRestrictions;
import org.eclipse.daanse.xmla.api.discover.discover.properties.DiscoverPropertiesRequest;
import org.eclipse.daanse.xmla.api.discover.discover.properties.DiscoverPropertiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.properties.DiscoverPropertiesRestrictions;
import org.eclipse.daanse.xmla.api.discover.discover.schemarowsets.DiscoverSchemaRowsetsRequest;
import org.eclipse.daanse.xmla.api.discover.discover.schemarowsets.DiscoverSchemaRowsetsResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.schemarowsets.DiscoverSchemaRowsetsRestrictions;
import org.eclipse.daanse.xmla.api.discover.discover.xmlmetadata.DiscoverXmlMetaDataRequest;
import org.eclipse.daanse.xmla.api.discover.discover.xmlmetadata.DiscoverXmlMetaDataResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.xmlmetadata.DiscoverXmlMetaDataRestrictions;
import org.eclipse.daanse.xmla.api.discover.mdschema.actions.MdSchemaActionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.actions.MdSchemaActionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.actions.MdSchemaActionsRestrictions;
import org.eclipse.daanse.xmla.api.discover.mdschema.cubes.MdSchemaCubesRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.cubes.MdSchemaCubesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.cubes.MdSchemaCubesRestrictions;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsRestrictions;
import org.eclipse.daanse.xmla.api.discover.mdschema.functions.MdSchemaFunctionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.functions.MdSchemaFunctionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.functions.MdSchemaFunctionsRestrictions;
import org.eclipse.daanse.xmla.api.discover.mdschema.hierarchies.MdSchemaHierarchiesRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.hierarchies.MdSchemaHierarchiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.hierarchies.MdSchemaHierarchiesRestrictions;
import org.eclipse.daanse.xmla.api.discover.mdschema.levels.MdSchemaLevelsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.levels.MdSchemaLevelsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.levels.MdSchemaLevelsRestrictions;
import org.eclipse.daanse.xmla.api.discover.mdschema.measuregroupdimensions.MdSchemaMeasureGroupDimensionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.measuregroupdimensions.MdSchemaMeasureGroupDimensionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.measuregroupdimensions.MdSchemaMeasureGroupDimensionsRestrictions;
import org.eclipse.daanse.xmla.api.discover.mdschema.measures.MdSchemaMeasuresRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.measures.MdSchemaMeasuresResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.measures.MdSchemaMeasuresRestrictions;
import org.eclipse.daanse.xmla.api.discover.mdschema.members.MdSchemaMembersRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.members.MdSchemaMembersResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.members.MdSchemaMembersRestrictions;
import org.eclipse.daanse.xmla.api.discover.mdschema.properties.MdSchemaPropertiesRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.properties.MdSchemaPropertiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.properties.MdSchemaPropertiesRestrictions;
import org.eclipse.daanse.xmla.model.record.discover.PropertiesR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.catalogs.DbSchemaCatalogsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.columns.DbSchemaColumnsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.columns.DbSchemaColumnsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.providertypes.DbSchemaProviderTypesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.providertypes.DbSchemaProviderTypesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.schemata.DbSchemaSchemataRequestR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.schemata.DbSchemaSchemataRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.tables.DbSchemaTablesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.tables.DbSchemaTablesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.discover.datasources.DiscoverDataSourcesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.discover.datasources.DiscoverDataSourcesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.discover.enumerators.DiscoverEnumeratorsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.discover.enumerators.DiscoverEnumeratorsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.discover.keywords.DiscoverKeywordsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.discover.keywords.DiscoverKeywordsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.discover.literals.DiscoverLiteralsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.discover.literals.DiscoverLiteralsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.discover.properties.DiscoverPropertiesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.discover.properties.DiscoverPropertiesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.discover.schemarowsets.DiscoverSchemaRowsetsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.discover.schemarowsets.DiscoverSchemaRowsetsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.discover.xmlmetadata.DiscoverXmlMetaDataRequestR;
import org.eclipse.daanse.xmla.model.record.discover.discover.xmlmetadata.DiscoverXmlMetaDataRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.actions.MdSchemaActionsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.actions.MdSchemaActionsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.cubes.MdSchemaCubesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.cubes.MdSchemaCubesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.demensions.MdSchemaDimensionsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.demensions.MdSchemaDimensionsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.functions.MdSchemaFunctionsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.functions.MdSchemaFunctionsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.hierarchies.MdSchemaHierarchiesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.hierarchies.MdSchemaHierarchiesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.levels.MdSchemaLevelsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.levels.MdSchemaLevelsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.measuregroupdimensions.MdSchemaMeasureGroupDimensionsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.measuregroupdimensions.MdSchemaMeasureGroupDimensionsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.measures.MdSchemaMeasuresRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.measures.MdSchemaMeasuresRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.members.MdSchemaMembersRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.members.MdSchemaMembersRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.properties.MdSchemaPropertiesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.properties.MdSchemaPropertiesRestrictionsR;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Discover;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.DiscoverResponse;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.PropertyList;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Return;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_rowset.DiscoverPropertiesResponseRowXml;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_rowset.Row;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_rowset.Rowset;
import org.eclipse.daanse.xmla.ws.jakarta.model.xsd.Schema;

import jakarta.xml.bind.JAXBException;

public class Convert {
    private static Optional<Integer> localeIdentifier(Discover requestWs) {
        Optional<Integer> oLocaleIdentifier = Optional.ofNullable(propertyList(requestWs).getLocaleIdentifier());
        return oLocaleIdentifier;
    }

    private static Optional<Content> content(Discover requestWs) {
        String content = propertyList(requestWs).getContent();
        if (content != null) {
            return Optional.ofNullable(Content.valueOf(content));
        }
        return Optional.empty();
    }

    private static Optional<String> catalog(Discover requestWs) {
        Optional<String> catalog = Optional.ofNullable(propertyList(requestWs).getCatalog());
        return catalog;
    }

    private static Optional<String> dataSourceInfo(Discover requestWs) {
        Optional<String> dataSourceInfo = Optional.ofNullable(propertyList(requestWs).getDataSourceInfo());
        return dataSourceInfo;
    }

    private static Optional<Format> format(Discover requestWs) {
        String format = propertyList(requestWs).getFormat();
        if (format != null) {
            return Optional.ofNullable(Format.valueOf(format));
        }
        return Optional.empty();
    }

    private static PropertyList propertyList(Discover requestWs) {
        return requestWs.getProperties()
                .getPropertyList();
    }

    private static Map<String, String> restrictionsMap(Discover requestWs) {
        return requestWs.getRestrictions()
                .getRestrictionMap();
    }

    private static PropertiesR discoverProperties(Discover requestWs) {
        PropertiesR properties = new PropertiesR();
        properties.setLocaleIdentifier(localeIdentifier(requestWs));
        properties.setContent(content(requestWs));
        properties.setFormat(format(requestWs));
        properties.setDataSourceInfo(dataSourceInfo(requestWs));
        properties.setCatalog(catalog(requestWs));
        properties.setLocaleIdentifier(localeIdentifier(requestWs));

        return properties;
    }

    private static DiscoverPropertiesRestrictionsR discoverPropertiesRestrictions(Discover requestWs) {
        Map<String, String> map = restrictionsMap(requestWs);

        String propertyName = map.get(DiscoverPropertiesRestrictions.RESTRICTIONS_PROPERTY_NAME);

        return new DiscoverPropertiesRestrictionsR(Optional.ofNullable(propertyName));
    }

    public static DiscoverPropertiesRequest fromDiscoverProperties(Discover requestWs) {

        System.out.println(requestWs);
        PropertiesR properties = discoverProperties(requestWs);
        DiscoverPropertiesRestrictionsR restrictions = discoverPropertiesRestrictions(requestWs);

        return new DiscoverPropertiesRequestR(properties, restrictions);

    }

    public static DiscoverResponse toDiscoverProperties(List<DiscoverPropertiesResponseRow> responseApi)
            throws JAXBException, IOException {

        List<Row> rows = responseApi.stream()
                .map(Convert::convertDiscoverPropertiesResponseRow)
                .toList();

        DiscoverResponse responseWs = new DiscoverResponse();

        Schema schema = SchemaUtil.generateSchema("urn:schemas-microsoft-com:xml-analysis:rowset",
                DiscoverPropertiesResponseRowXml.class);

        Return r = new Return();

        Rowset rs = new Rowset();
        rs.setSchema(schema);
        rs.setRow(rows);
        r.setValue(rs);
        responseWs.setReturn(r);

        return responseWs;
    }

    private static Row convertDiscoverPropertiesResponseRow(DiscoverPropertiesResponseRow apiRow) {
        DiscoverPropertiesResponseRowXml row = new DiscoverPropertiesResponseRowXml();

        // Mandatory
        row.setPropertyName(apiRow.propertyName());
        row.setPropertyAccessType(apiRow.propertyAccessType());

        // Optional
        apiRow.propertyDescription()
                .ifPresent(row::setPropertyDescription);
        apiRow.propertyType()
                .ifPresent(row::setPropertyType);
        apiRow.required()
                .ifPresent(row::setRequired);
        apiRow.value()
                .ifPresent(row::setValue);

        return (Row) row;
    }

    public static DbSchemaCatalogsRequest fromDiscoverDbSchemaCatalogs(Discover requestWs) {
        return new DbSchemaCatalogsRequestR();
    }

    public static DiscoverResponse toDiscoverDbSchemaCatalogs(DbSchemaCatalogsResponse responseApi) {
        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }

    public static DiscoverResponse toDiscoverSchemaRowsets(List<DiscoverSchemaRowsetsResponseRow> responseApi) {

        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }

    private static DiscoverSchemaRowsetsRestrictionsR discoverSchemaRowsetsRestrictions(Discover requestWs) {
        Map<String, String> map = restrictionsMap(requestWs);

        String schemaName = map.get(DiscoverSchemaRowsetsRestrictions.RESTRICTIONS_SCHEMA_NAME);

        return new DiscoverSchemaRowsetsRestrictionsR(Optional.ofNullable(schemaName));
    }

    public static DiscoverSchemaRowsetsRequest fromDiscoverSchemaRowsets(Discover requestWs) {

        PropertiesR properties = discoverProperties(requestWs);
        DiscoverSchemaRowsetsRestrictionsR restrictions = discoverSchemaRowsetsRestrictions(requestWs);

        return new DiscoverSchemaRowsetsRequestR(properties, restrictions);

    }

    public static DiscoverEnumeratorsRequest fromDiscoverEnumerators(Discover requestWs) {
        PropertiesR properties = discoverProperties(requestWs);
        DiscoverEnumeratorsRestrictionsR restrictions = discoverEnumeratorsRestrictions(requestWs);
        return new DiscoverEnumeratorsRequestR(properties, restrictions);

    }

    private static DiscoverEnumeratorsRestrictionsR discoverEnumeratorsRestrictions(Discover requestWs) {
        Map<String, String> map = restrictionsMap(requestWs);

        String enumName = map.get(DiscoverEnumeratorsRestrictions.RESTRICTIONS_ENUM_NAME);

        return new DiscoverEnumeratorsRestrictionsR(Optional.ofNullable(enumName));
    }

    public static DiscoverResponse toDiscoverEnumerators(List<DiscoverEnumeratorsResponseRow> responseApi) {
        // TODO
        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }

    public static DiscoverKeywordsRequest fromDiscoverKeywords(Discover requestWs) {
        PropertiesR properties = discoverProperties(requestWs);
        DiscoverKeywordsRestrictionsR restrictions = discoverKeywordsRestrictions(requestWs);
        return new DiscoverKeywordsRequestR(properties, restrictions);
    }

    private static DiscoverKeywordsRestrictionsR discoverKeywordsRestrictions(Discover requestWs) {
        Map<String, String> map = restrictionsMap(requestWs);

        String keyword = map.get(DiscoverKeywordsRestrictions.RESTRICTIONS_KEYWORD);

        return new DiscoverKeywordsRestrictionsR(Optional.ofNullable(keyword));
    }

    public static DiscoverResponse toDiscoverKeywords(List<DiscoverKeywordsResponseRow> responseApi) {
        // TODO
        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }

    public static DiscoverLiteralsRequest fromDiscoverLiterals(Discover requestWs) {
        PropertiesR properties = discoverProperties(requestWs);
        DiscoverLiteralsRestrictionsR restrictions = discoverLiteralsRestrictions(requestWs);
        return new DiscoverLiteralsRequestR(properties, restrictions);
    }

    private static DiscoverLiteralsRestrictionsR discoverLiteralsRestrictions(Discover requestWs) {
        Map<String, String> map = restrictionsMap(requestWs);

        String keyword = map.get(DiscoverLiteralsRestrictions.RESTRICTIONS_LITERAL_NAME);

        return new DiscoverLiteralsRestrictionsR(Optional.ofNullable(keyword));
    }

    public static DiscoverResponse toDiscoverLiterals(List<DiscoverLiteralsResponseRow> responseApi) {
        // TODO
        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }

    public static DbSchemaTablesRequest fromDiscoverDbSchemaTables(Discover requestWs) {
        PropertiesR properties = discoverProperties(requestWs);
        DbSchemaTablesRestrictionsR restrictions = discoverDbSchemaTablesRestrictions(requestWs);
        return new DbSchemaTablesRequestR(properties, restrictions);
    }

    private static DbSchemaTablesRestrictionsR discoverDbSchemaTablesRestrictions(Discover requestWs) {
        Map<String, String> map = restrictionsMap(requestWs);

        String tableCatalog = map.get(DbSchemaTablesRestrictions.RESTRICTIONS_TABLE_CATALOG);
        String tableSchema = map.get(DbSchemaTablesRestrictions.RESTRICTIONS_TABLE_SCHEMA);
        String tableName = map.get(DbSchemaTablesRestrictions.RESTRICTIONS_TABLE_NAME);
        String tableType = map.get(DbSchemaTablesRestrictions.RESTRICTIONS_TABLE_TYPE);

        return new DbSchemaTablesRestrictionsR(Optional.ofNullable(tableCatalog), Optional.ofNullable(tableSchema),
                Optional.ofNullable(tableName), Optional.ofNullable(tableType));
    }

    public static DiscoverResponse toDiscoverDbSchemaTables(List<DbSchemaTablesResponseRow> responseApi) {
        // TODO
        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }

    public static MdSchemaActionsRequest fromDiscoverMdSchemaActions(Discover requestWs) {
        PropertiesR properties = discoverProperties(requestWs);
        MdSchemaActionsRestrictionsR restrictions = discoverMdSchemaActionsRestrictions(requestWs);
        return new MdSchemaActionsRequestR(properties, restrictions);
    }

    private static MdSchemaActionsRestrictionsR discoverMdSchemaActionsRestrictions(Discover requestWs) {
        Map<String, String> map = restrictionsMap(requestWs);

        String catalogName = map.get(MdSchemaActionsRestrictions.RESTRICTIONS_CATALOG_NAME);
        String schemaName = map.get(MdSchemaActionsRestrictions.RESTRICTIONS_SCHEMA_NAME);
        String cubeName = map.get(MdSchemaActionsRestrictions.RESTRICTIONS_CUBE_NAME);
        String actionName = map.get(MdSchemaActionsRestrictions.RESTRICTIONS_ACTION_NAME);
        String actionType = map.get(MdSchemaActionsRestrictions.RESTRICTIONS_ACTION_TYPE);
        String coordinate = map.get(MdSchemaActionsRestrictions.RESTRICTIONS_COORDINATE);
        String coordinateType = map.get(MdSchemaActionsRestrictions.RESTRICTIONS_COORDINATE_TYPE);
        String invocation = map.get(MdSchemaActionsRestrictions.RESTRICTIONS_INVOCATION);

        return new MdSchemaActionsRestrictionsR(Optional.ofNullable(catalogName), Optional.ofNullable(schemaName),
            cubeName, Optional.ofNullable(actionName), Optional.ofNullable(ActionTypeEnum.fromValue(actionType)),
            Optional.ofNullable(coordinate), CoordinateTypeEnum.fromValue(coordinateType),
            InvocationEnum.fromValue(invocation));
    }

    public static DiscoverResponse toDiscoverMdSchemaActions(List<MdSchemaActionsResponseRow> responseApi) {
        // TODO
        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }

    public static MdSchemaCubesRequest fromDiscoverMdSchemaCubes(Discover requestWs) {
        PropertiesR properties = discoverProperties(requestWs);
        MdSchemaCubesRestrictionsR restrictions = discoverMdSchemaCubesRestrictions(requestWs);
        return new MdSchemaCubesRequestR(properties, restrictions);
    }

    private static MdSchemaCubesRestrictionsR discoverMdSchemaCubesRestrictions(Discover requestWs) {
        Map<String, String> map = restrictionsMap(requestWs);

        String schemaName = map.get(MdSchemaCubesRestrictions.RESTRICTIONS_SCHEMA_NAME);
        String cubeName = map.get(MdSchemaCubesRestrictions.RESTRICTIONS_CUBE_NAME);
        String baseCubeName = map.get(MdSchemaCubesRestrictions.RESTRICTIONS_BASE_CUBE_NAME);
        String cubeSource = map.get(MdSchemaCubesRestrictions.RESTRICTIONS_CUBE_SOURCE);

        return new MdSchemaCubesRestrictionsR(Optional.ofNullable(schemaName), Optional.ofNullable(cubeName),
                Optional.ofNullable(baseCubeName), Optional.ofNullable(CubeSourceEnum.fromValue(cubeSource)));
    }

    public static DiscoverResponse toDiscoverMdSchemaCubes(List<MdSchemaCubesResponseRow> responseApi) {
        // TODO
        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }

    public static MdSchemaDimensionsRequest fromDiscoverMdSchemaDimensions(Discover requestWs) {
        PropertiesR properties = discoverProperties(requestWs);
        MdSchemaDimensionsRestrictionsR restrictions = discoverMdSchemaDimensionsRestrictions(requestWs);
        return new MdSchemaDimensionsRequestR(properties, restrictions);
    }

    private static MdSchemaDimensionsRestrictionsR discoverMdSchemaDimensionsRestrictions(Discover requestWs) {
        Map<String, String> map = restrictionsMap(requestWs);

        String catalogName = map.get(MdSchemaDimensionsRestrictions.RESTRICTIONS_CATALOG_NAME);
        String schemaName = map.get(MdSchemaDimensionsRestrictions.RESTRICTIONS_SCHEMA_NAME);
        String cubeName = map.get(MdSchemaDimensionsRestrictions.RESTRICTIONS_CUBE_NAME);
        String dimensionName = map.get(MdSchemaDimensionsRestrictions.RESTRICTIONS_DIMENSION_NAME);
        String dimensionUniqueName = map.get(MdSchemaDimensionsRestrictions.RESTRICTIONS_DIMENSION_UNIQUE_NAME);

        return new MdSchemaDimensionsRestrictionsR(Optional.ofNullable(catalogName), Optional.ofNullable(schemaName),
                Optional.ofNullable(cubeName), Optional.ofNullable(dimensionName),
                Optional.ofNullable(dimensionUniqueName));
    }

    public static DiscoverResponse toDiscoverMdSchemaDimensions(List<MdSchemaDimensionsResponseRow> responseApi) {
        // TODO
        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }

    public static MdSchemaFunctionsRequest fromDiscoverMdSchemaFunctions(Discover requestWs) {
        PropertiesR properties = discoverProperties(requestWs);
        MdSchemaFunctionsRestrictionsR restrictions = discoverMdSchemaFunctionsRestrictions(requestWs);
        return new MdSchemaFunctionsRequestR(properties, restrictions);
    }

    private static MdSchemaFunctionsRestrictionsR discoverMdSchemaFunctionsRestrictions(Discover requestWs) {
        Map<String, String> map = restrictionsMap(requestWs);

        String origin = map.get(MdSchemaFunctionsRestrictions.RESTRICTIONS_ORIGIN);
        String interfaceName = map.get(MdSchemaFunctionsRestrictions.RESTRICTIONS_INTERFACE_NAME);
        String libraryName = map.get(MdSchemaFunctionsRestrictions.RESTRICTIONS_LIBRARY_NAME);

        return new MdSchemaFunctionsRestrictionsR(Optional.ofNullable(OriginEnum.fromValue(origin)),
                Optional.ofNullable(InterfaceNameEnum.fromValue(interfaceName)), Optional.ofNullable(libraryName));
    }

    public static DiscoverResponse toDiscoverMdSchemaFunctions(List<MdSchemaFunctionsResponseRow> responseApi) {
        // TODO
        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }

    public static MdSchemaHierarchiesRequest fromDiscoverMdSchemaHierarchies(Discover requestWs) {
        PropertiesR properties = discoverProperties(requestWs);
        MdSchemaHierarchiesRestrictionsR restrictions = discoverMdSchemaHierarchiesRestrictions(requestWs);
        return new MdSchemaHierarchiesRequestR(properties, restrictions);

    }

    private static MdSchemaHierarchiesRestrictionsR discoverMdSchemaHierarchiesRestrictions(Discover requestWs) {
        Map<String, String> map = restrictionsMap(requestWs);

        String catalogName = map.get(MdSchemaHierarchiesRestrictions.RESTRICTIONS_CATALOG_NAME);
        String schemaName = map.get(MdSchemaHierarchiesRestrictions.RESTRICTIONS_SCHEMA_NAME);
        String cubeName = map.get(MdSchemaHierarchiesRestrictions.RESTRICTIONS_CUBE_NAME);
        String dimensionUniqueName = map.get(MdSchemaHierarchiesRestrictions.RESTRICTIONS_DIMENSION_UNIQUE_NAME);
        String hierarchyName = map.get(MdSchemaHierarchiesRestrictions.RESTRICTIONS_HIERARCHY_NAME);
        String hierarchyUniqueName = map.get(MdSchemaHierarchiesRestrictions.RESTRICTIONS_HIERARCHY_UNIQUE_NAME);
        String hierarchyOrigin = map.get(MdSchemaHierarchiesRestrictions.RESTRICTIONS_HIERARCHY_ORIGIN);
        String cubeSource = map.get(MdSchemaHierarchiesRestrictions.RESTRICTIONS_CUBE_SOURCE);
        String hierarchyVisibility = map.get(MdSchemaHierarchiesRestrictions.RESTRICTIONS_HIERARCHY_VISIBILITY);

        return new MdSchemaHierarchiesRestrictionsR(Optional.ofNullable(catalogName),
            Optional.ofNullable(schemaName),
            Optional.ofNullable(cubeName),
            Optional.ofNullable(dimensionUniqueName),
            Optional.ofNullable(hierarchyName),
            Optional.ofNullable(hierarchyUniqueName),
            Optional.ofNullable(hierarchyOrigin == null ? null : Integer.valueOf(hierarchyOrigin)),
            Optional.ofNullable(CubeSourceEnum.fromValue(cubeSource)),
            Optional.ofNullable(VisibilityEnum.fromValue(hierarchyVisibility)));
    }

    public static DiscoverResponse toDiscoverMdSchemaHierarchies(List<MdSchemaHierarchiesResponseRow> responseApi) {
        // TODO
        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }

    public static DiscoverDataSourcesRequest fromDiscoverDataSources(Discover requestWs) {
        PropertiesR properties = discoverProperties(requestWs);
        DiscoverDataSourcesRestrictionsR restrictions = discoverDataSourcesRestrictions(requestWs);
        return new DiscoverDataSourcesRequestR(properties, restrictions);
    }

    private static DiscoverDataSourcesRestrictionsR discoverDataSourcesRestrictions(Discover requestWs) {
        Map<String, String> map = restrictionsMap(requestWs);
        String dataSourcesName = map.get(DiscoverDataSourcesRestrictions.RESTRICTIONS_DATA_SOURCE_NAME);
        String dataSourcesDescription = map.get(DiscoverDataSourcesRestrictions.RESTRICTIONS_DATA_SOURCE_DESCRIPTION);
        String url = map.get(DiscoverDataSourcesRestrictions.RESTRICTIONS_URL);
        String dataSourcesInfo = map.get(DiscoverDataSourcesRestrictions.RESTRICTIONS_DATA_SOURCE_INFO);
        String providerName = map.get(DiscoverDataSourcesRestrictions.RESTRICTIONS_PROVIDER_NAME);
        String providerType = map.get(DiscoverDataSourcesRestrictions.RESTRICTIONS_PROVIDER_TYPE);
        String authenticationMode = map.get(DiscoverDataSourcesRestrictions.RESTRICTIONS_AUTHENTICATION_MODE);


        return new DiscoverDataSourcesRestrictionsR(
            dataSourcesName,
            Optional.ofNullable(dataSourcesDescription),
            Optional.ofNullable(url),
            Optional.ofNullable(dataSourcesInfo),
            providerName,
            Optional.ofNullable(ProviderTypeEnum.fromValue(providerType)),
            Optional.ofNullable(AuthenticationModeEnum.fromValue(authenticationMode)));
    }

    public static DiscoverResponse toDiscoverDataSources(List<DiscoverDataSourcesResponseRow> responseApi) {
        // TODO
        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }

    public static DiscoverResponse toDiscoverXmlMetaData(List<DiscoverXmlMetaDataResponseRow> responseApi) {
        // TODO
        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }

    public static DiscoverXmlMetaDataRequest fromDiscoverXmlMetaData(Discover requestWs) {
        PropertiesR properties = discoverProperties(requestWs);
        DiscoverXmlMetaDataRestrictionsR restrictions = discoverXmlMetaDataRestrictions(requestWs);
        return new DiscoverXmlMetaDataRequestR(properties, restrictions);
    }

    private static DiscoverXmlMetaDataRestrictionsR discoverXmlMetaDataRestrictions(Discover requestWs) {
        Map<String, String> map = restrictionsMap(requestWs);

        String databaseId = map.get(DiscoverXmlMetaDataRestrictions.RESTRICTIONS_DATABASE_ID);
        String dimensionId = map.get(DiscoverXmlMetaDataRestrictions.RESTRICTIONS_DIMENSION_ID);
        String cubeId = map.get(DiscoverXmlMetaDataRestrictions.RESTRICTIONS_CUBE_ID);
        String measureGroupId = map.get(DiscoverXmlMetaDataRestrictions.RESTRICTIONS_MEASURE_GROUP_ID);
        String partitionId = map.get(DiscoverXmlMetaDataRestrictions.RESTRICTIONS_PARTITION_ID);
        String perspectiveId = map.get(DiscoverXmlMetaDataRestrictions.RESTRICTIONS_PERSPECTIVE_ID);
        String dimensionPermissionId = map.get(DiscoverXmlMetaDataRestrictions.RESTRICTIONS_PERMISSION_ID);
        String roleId = map.get(DiscoverXmlMetaDataRestrictions.RESTRICTIONS_ROLE_ID);
        String databasePermissionId = map.get(DiscoverXmlMetaDataRestrictions.RESTRICTIONS_DATABASE_PERMISSION_ID);
        String miningModelId = map.get(DiscoverXmlMetaDataRestrictions.RESTRICTIONS_MINING_MODEL_ID);
        String miningModelPermissionId = map.get(DiscoverXmlMetaDataRestrictions.RESTRICTIONS_MINING_MODEL_PERMISSION_ID);
        String dataSourceId = map.get(DiscoverXmlMetaDataRestrictions.RESTRICTIONS_DATA_SOURCE_ID);
        String miningStructureId = map.get(DiscoverXmlMetaDataRestrictions.RESTRICTIONS_MINING_STRUCTURE_ID);
        String aggregationDesignId = map.get(DiscoverXmlMetaDataRestrictions.RESTRICTIONS_AGGREGATION_DESIGN_ID);
        String traceId = map.get(DiscoverXmlMetaDataRestrictions.RESTRICTIONS_TRACE_ID);
        String miningStructurePermissionId = map.get(DiscoverXmlMetaDataRestrictions.RESTRICTIONS_MINING_STRUCTURE_PERMISSION_ID);
        String cubePermissionId = map.get(DiscoverXmlMetaDataRestrictions.RESTRICTIONS_CUBE_PERMISSION_ID);
        String assemblyId = map.get(DiscoverXmlMetaDataRestrictions.RESTRICTIONS_ASSEMBLY_ID);
        String mdxScriptId = map.get(DiscoverXmlMetaDataRestrictions.RESTRICTIONS_MDX_SCRIPT_ID);
        String dataSourceViewId = map.get(DiscoverXmlMetaDataRestrictions.RESTRICTIONS_DATA_SOURCE_VIEW_ID);
        String dataSourcePermissionId = map.get(DiscoverXmlMetaDataRestrictions.RESTRICTIONS_DATA_SOURCE_PERMISSION_ID);
        String objectExpansion = map.get(DiscoverXmlMetaDataRestrictions.RESTRICTIONS_OBJECT_EXPANSION);


        return new DiscoverXmlMetaDataRestrictionsR(
            Optional.ofNullable(databaseId),
            Optional.ofNullable(dimensionId),
            Optional.ofNullable(cubeId),
            Optional.ofNullable(measureGroupId),
            Optional.ofNullable(partitionId),
            Optional.ofNullable(perspectiveId),
            Optional.ofNullable(dimensionPermissionId),
            Optional.ofNullable(roleId),
            Optional.ofNullable(databasePermissionId),
            Optional.ofNullable(miningModelId),
            Optional.ofNullable(miningModelPermissionId),
            Optional.ofNullable(dataSourceId),
            Optional.ofNullable(miningStructureId),
            Optional.ofNullable(aggregationDesignId),
            Optional.ofNullable(traceId),
            Optional.ofNullable(miningStructurePermissionId),
            Optional.ofNullable(cubePermissionId),
            Optional.ofNullable(assemblyId),
            Optional.ofNullable(mdxScriptId),
            Optional.ofNullable(dataSourceViewId),
            Optional.ofNullable(dataSourcePermissionId),
            Optional.ofNullable(ObjectExpansionEnum.fromValue(objectExpansion)));
    }

    public static DbSchemaColumnsRequest fromDiscoverDbSchemaColumns(Discover requestWs) {
        PropertiesR properties = discoverProperties(requestWs);
        DbSchemaColumnsRestrictionsR restrictions = discoverDbSchemaColumnsRestrictions(requestWs);
        return new DbSchemaColumnsRequestR(properties, restrictions);
    }

    private static DbSchemaColumnsRestrictionsR discoverDbSchemaColumnsRestrictions(Discover requestWs) {
        Map<String, String> map = restrictionsMap(requestWs);

        String tableCatalog = map.get(DbSchemaColumnsRestrictions.RESTRICTIONS_TABLE_CATALOG);
        String tableSchema = map.get(DbSchemaColumnsRestrictions.RESTRICTIONS_TABLE_SCHEMA);
        String tableName = map.get(DbSchemaColumnsRestrictions.RESTRICTIONS_TABLE_NAME);
        String columnName = map.get(DbSchemaColumnsRestrictions.RESTRICTIONS_COLUMN_NAME);
        String columnOlapType = map.get(DbSchemaColumnsRestrictions.RESTRICTIONS_COLUMN_OLAP_TYPE);

        return new DbSchemaColumnsRestrictionsR(
            Optional.ofNullable(tableCatalog),
            Optional.ofNullable(tableSchema),
            Optional.ofNullable(tableName),
            Optional.ofNullable(columnName),
            Optional.ofNullable(ColumnOlapTypeEnum.fromValue(columnOlapType)));
    }

    public static DiscoverResponse toDiscoverDbSchemaColumns(List<DbSchemaColumnsResponseRow> responseApi) {
        // TODO
        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }

    public static DbSchemaProviderTypesRequest fromDiscoverDbSchemaProviderTypes(Discover requestWs) {
        PropertiesR properties = discoverProperties(requestWs);
        DbSchemaProviderTypesRestrictionsR restrictions = discoverDbSchemaProviderTypesRestrictions(requestWs);
        return new DbSchemaProviderTypesRequestR(properties, restrictions);
    }

    private static DbSchemaProviderTypesRestrictionsR discoverDbSchemaProviderTypesRestrictions(Discover requestWs) {
        Map<String, String> map = restrictionsMap(requestWs);

        String dataType = map.get(DbSchemaProviderTypesRestrictions.RESTRICTIONS_DATA_TYPE);
        String bestMatch = map.get(DbSchemaProviderTypesRestrictions.RESTRICTIONS_BEST_MATCH);

        return new DbSchemaProviderTypesRestrictionsR(
            Optional.ofNullable(LevelDbTypeEnum.fromValue(dataType)),
            Optional.ofNullable(bestMatch == null ? null : Boolean.valueOf(bestMatch)));
    }

    public static DiscoverResponse toDiscoverDbSchemaProviderTypes(List<DbSchemaProviderTypesResponseRow> responseApi) {
        // TODO
        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }

    public static DbSchemaSchemataRequest fromDiscoverDbSchemaSchemata(Discover requestWs) {
        PropertiesR properties = discoverProperties(requestWs);
        DbSchemaSchemataRestrictionsR restrictions = discoverDbSchemaSchemataRestrictions(requestWs);
        return new DbSchemaSchemataRequestR(properties, restrictions);
    }

    private static DbSchemaSchemataRestrictionsR discoverDbSchemaSchemataRestrictions(Discover requestWs) {
        Map<String, String> map = restrictionsMap(requestWs);

        String catalogName = map.get(DbSchemaSchemataRestrictions.RESTRICTIONS_CATALOG_NAME);
        String schemaName = map.get(DbSchemaSchemataRestrictions.RESTRICTIONS_SCHEMA_NAME);
        String schemaOwner = map.get(DbSchemaSchemataRestrictions.RESTRICTIONS_SCHEMA_OWNER);

        return new DbSchemaSchemataRestrictionsR(
            catalogName,
            schemaName,
            schemaOwner);
    }

    public static DiscoverResponse toDiscoverDbSchemaSchemata(List<DbSchemaSchemataResponseRow> responseApi) {
        // TODO
        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }

    public static MdSchemaLevelsRequest fromDiscoverMdSchemaLevels(Discover requestWs) {
        PropertiesR properties = discoverProperties(requestWs);
        MdSchemaLevelsRestrictionsR restrictions = discoverMdSchemaLevelsRestrictions(requestWs);
        return new MdSchemaLevelsRequestR(properties, restrictions);
    }

    private static MdSchemaLevelsRestrictionsR discoverMdSchemaLevelsRestrictions(Discover requestWs) {
        Map<String, String> map = restrictionsMap(requestWs);

        String catalogName = map.get(MdSchemaLevelsRestrictions.RESTRICTIONS_CATALOG_NAME);
        String schemaName = map.get(MdSchemaLevelsRestrictions.RESTRICTIONS_SCHEMA_NAME);
        String cubeName = map.get(MdSchemaLevelsRestrictions.RESTRICTIONS_CUBE_NAME);
        String dimensionUniqueName = map.get(MdSchemaLevelsRestrictions.RESTRICTIONS_DIMENSION_UNIQUE_NAME);
        String hierarchyUniqueName = map.get(MdSchemaLevelsRestrictions.RESTRICTIONS_HIERARCHY_UNIQUE_NAME);
        String levelName = map.get(MdSchemaLevelsRestrictions.RESTRICTIONS_LEVEL_NAME);
        String levelUniqueName = map.get(MdSchemaLevelsRestrictions.RESTRICTIONS_LEVEL_UNIQUE_NAME);
        String cubeSource = map.get(MdSchemaLevelsRestrictions.RESTRICTIONS_CUBE_SOURCE);
        String levelVisibility = map.get(MdSchemaLevelsRestrictions.RESTRICTIONS_LEVEL_VISIBILITY);

        return new MdSchemaLevelsRestrictionsR(
            Optional.ofNullable(catalogName),
            Optional.ofNullable(schemaName),
            Optional.ofNullable(cubeName),
            Optional.ofNullable(dimensionUniqueName),
            Optional.ofNullable(hierarchyUniqueName),
            Optional.ofNullable(levelName),
            Optional.ofNullable(levelUniqueName),
            Optional.ofNullable(CubeSourceEnum.fromValue(cubeSource)),
            Optional.ofNullable(VisibilityEnum.fromValue(levelVisibility)));
    }

    public static DiscoverResponse toDiscoverMdSchemaLevels(List<MdSchemaLevelsResponseRow> responseApi) {
        // TODO
        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }

    public static MdSchemaMeasureGroupDimensionsRequest fromDiscoverMdSchemaMeasureGroupDimensions(Discover requestWs) {
        PropertiesR properties = discoverProperties(requestWs);
        MdSchemaMeasureGroupDimensionsRestrictionsR restrictions = discoverMdSchemaMeasureGroupDimensionsRestrictions(requestWs);
        return new MdSchemaMeasureGroupDimensionsRequestR(properties, restrictions);
    }

    private static MdSchemaMeasureGroupDimensionsRestrictionsR discoverMdSchemaMeasureGroupDimensionsRestrictions(
        Discover requestWs) {
        Map<String, String> map = restrictionsMap(requestWs);

        String catalogName = map.get(MdSchemaMeasureGroupDimensionsRestrictions.RESTRICTIONS_CATALOG_NAME);
        String schemaName = map.get(MdSchemaMeasureGroupDimensionsRestrictions.RESTRICTIONS_SCHEMA_NAME);
        String cubeName = map.get(MdSchemaMeasureGroupDimensionsRestrictions.RESTRICTIONS_CUBE_NAME);
        String measureGroupName = map.get(MdSchemaMeasureGroupDimensionsRestrictions.RESTRICTIONS_MEASUREGROUP_NAME);
        String dimensionUniqueName = map.get(MdSchemaMeasureGroupDimensionsRestrictions.RESTRICTIONS_DIMENSION_UNIQUE_NAME);
        String dimensionVisibility = map.get(MdSchemaMeasureGroupDimensionsRestrictions.RESTRICTIONS_DIMENSION_VISIBILITY);

        return new MdSchemaMeasureGroupDimensionsRestrictionsR(
            Optional.ofNullable(catalogName),
            Optional.ofNullable(schemaName),
            Optional.ofNullable(cubeName),
            Optional.ofNullable(dimensionUniqueName),
            Optional.ofNullable(measureGroupName),
            Optional.ofNullable(VisibilityEnum.fromValue(dimensionVisibility)));
    }

    public static DiscoverResponse toDiscoverMdSchemaMeasureGroupDimensions(List<MdSchemaMeasureGroupDimensionsResponseRow> responseApi) {
        // TODO
        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }

    public static MdSchemaMeasuresRequest fromDiscoverMdSchemaMeasures(Discover requestWs) {
        PropertiesR properties = discoverProperties(requestWs);
        MdSchemaMeasuresRestrictionsR restrictions = discoverMdSchemaMeasuresRestrictions(requestWs);
        return new MdSchemaMeasuresRequestR(properties, restrictions);
    }

    private static MdSchemaMeasuresRestrictionsR discoverMdSchemaMeasuresRestrictions(Discover requestWs) {
            Map<String, String> map = restrictionsMap(requestWs);

            String catalogName = map.get(MdSchemaMeasuresRestrictions.RESTRICTIONS_CATALOG_NAME);
            String schemaName = map.get(MdSchemaMeasuresRestrictions.RESTRICTIONS_SCHEMA_NAME);
            String cubeName = map.get(MdSchemaMeasuresRestrictions.RESTRICTIONS_CUBE_NAME);
            String measureName = map.get(MdSchemaMeasuresRestrictions.RESTRICTIONS_MEASURE_NAME);
            String measureUniqueName = map.get(MdSchemaMeasuresRestrictions.RESTRICTIONS_MEASURE_UNIQUE_NAME);
            String measureGroupName = map.get(MdSchemaMeasuresRestrictions.RESTRICTIONS_MEASUREGROUP_NAME);
            String cubeSource = map.get(MdSchemaMeasuresRestrictions.RESTRICTIONS_CUBE_SOURCE);
            String measureVisibility = map.get(MdSchemaMeasuresRestrictions.RESTRICTIONS_MEASURE_VISIBILITY);

        return new MdSchemaMeasuresRestrictionsR(
                Optional.ofNullable(catalogName),
                Optional.ofNullable(schemaName),
                Optional.ofNullable(cubeName),
                Optional.ofNullable(measureName),
                Optional.ofNullable(measureUniqueName),
                Optional.ofNullable(measureGroupName),
                Optional.ofNullable(CubeSourceEnum.fromValue(cubeSource)),
                Optional.ofNullable(VisibilityEnum.fromValue(measureVisibility)));
    }

    public static DiscoverResponse toDiscoverMdSchemaMeasures(List<MdSchemaMeasuresResponseRow> responseApi) {
        // TODO
        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }

    public static MdSchemaMembersRequest fromDiscoverMdSchemaMembers(Discover requestWs) {
        PropertiesR properties = discoverProperties(requestWs);
        MdSchemaMembersRestrictionsR restrictions = discoverMdSchemaMembersRestrictions(requestWs);
        return new MdSchemaMembersRequestR(properties, restrictions);
    }

    private static MdSchemaMembersRestrictionsR discoverMdSchemaMembersRestrictions(Discover requestWs) {
        Map<String, String> map = restrictionsMap(requestWs);

        String catalogName = map.get(MdSchemaMembersRestrictions.RESTRICTIONS_CATALOG_NAME);
        String schemaName = map.get(MdSchemaMembersRestrictions.RESTRICTIONS_SCHEMA_NAME);
        String cubeName = map.get(MdSchemaMembersRestrictions.RESTRICTIONS_CUBE_NAME);
        String dimensionUniqueName = map.get(MdSchemaMembersRestrictions.RESTRICTIONS_DIMENSION_UNIQUE_NAME);
        String hierarchyUniqueName = map.get(MdSchemaMembersRestrictions.RESTRICTIONS_HIERARCHY_UNIQUE_NAME);
        String levelUniqueName = map.get(MdSchemaMembersRestrictions.RESTRICTIONS_LEVEL_UNIQUE_NAME);
        String levelNumber = map.get(MdSchemaMembersRestrictions.RESTRICTIONS_LEVEL_NUMBER);
        String memberName = map.get(MdSchemaMembersRestrictions.RESTRICTIONS_MEMBER_NAME);
        String memberUniqueName = map.get(MdSchemaMembersRestrictions.RESTRICTIONS_MEMBER_UNIQUE_NAME);
        String memberType = map.get(MdSchemaMembersRestrictions.RESTRICTIONS_MEMBER_TYPE);
        String measureCaption = map.get(MdSchemaMembersRestrictions.RESTRICTIONS_MEMBER_CAPTION);
        String cubeSource = map.get(MdSchemaMembersRestrictions.RESTRICTIONS_CUBE_SOURCE);
        String treeOp = map.get(MdSchemaMembersRestrictions.RESTRICTIONS_TREE_OP);


        return new MdSchemaMembersRestrictionsR(
                Optional.ofNullable(catalogName),
                Optional.ofNullable(schemaName),
                Optional.ofNullable(cubeName),
                Optional.ofNullable(dimensionUniqueName),
                Optional.ofNullable(hierarchyUniqueName),
                Optional.ofNullable(levelUniqueName),
                Optional.ofNullable(levelNumber == null ? null: Integer.valueOf(levelNumber)),
                Optional.ofNullable(memberName),
                Optional.ofNullable(memberUniqueName),
                Optional.ofNullable(MemberTypeEnum.fromValue(memberType)),
                Optional.ofNullable(measureCaption),
                Optional.ofNullable(CubeSourceEnum.fromValue(cubeSource)),
                Optional.ofNullable(TreeOpEnum.fromValue(treeOp)));
    }

    public static DiscoverResponse toDiscoverMdSchemaMembers(List<MdSchemaMembersResponseRow> responseApi) {
        // TODO
        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }

    public static MdSchemaPropertiesRequest fromDiscoverMdSchemaProperties(Discover requestWs) {
        PropertiesR properties = discoverProperties(requestWs);
        MdSchemaPropertiesRestrictionsR restrictions = discoverMdSchemaPropertiesRestrictions(requestWs);
        return new MdSchemaPropertiesRequestR(properties, restrictions);

    }

    private static MdSchemaPropertiesRestrictionsR discoverMdSchemaPropertiesRestrictions(Discover requestWs) {
        Map<String, String> map = restrictionsMap(requestWs);

        String catalogName = map.get(MdSchemaPropertiesRestrictions.RESTRICTIONS_CATALOG_NAME);
        String schemaName = map.get(MdSchemaPropertiesRestrictions.RESTRICTIONS_SCHEMA_NAME);
        String cubeName = map.get(MdSchemaPropertiesRestrictions.RESTRICTIONS_CUBE_NAME);
        String dimensionUniqueName = map.get(MdSchemaPropertiesRestrictions.RESTRICTIONS_DIMENSION_UNIQUE_NAME);
        String hierarchyUniqueName = map.get(MdSchemaPropertiesRestrictions.RESTRICTIONS_HIERARCHY_UNIQUE_NAME);
        String levelUniqueName = map.get(MdSchemaPropertiesRestrictions.RESTRICTIONS_LEVEL_UNIQUE_NAME);

        String memberUniqueName = map.get(MdSchemaPropertiesRestrictions.RESTRICTIONS_MEMBER_UNIQUE_NAME);
        String propertyType = map.get(MdSchemaPropertiesRestrictions.RESTRICTIONS_PROPERTY_TYPE);
        String propertyName = map.get(MdSchemaPropertiesRestrictions.RESTRICTIONS_PROPERTY_NAME);
        String propertyOrigin = map.get(MdSchemaPropertiesRestrictions.RESTRICTIONS_PROPERTY_ORIGIN);
        String cubeSource = map.get(MdSchemaPropertiesRestrictions.RESTRICTIONS_CUBE_SOURCE);
        String propertyVisibility = map.get(MdSchemaPropertiesRestrictions.RESTRICTIONS_PROPERTY_VISIBILITY);

        return new MdSchemaPropertiesRestrictionsR(
            Optional.ofNullable(catalogName),
            Optional.ofNullable(schemaName),
            Optional.ofNullable(cubeName),
            Optional.ofNullable(dimensionUniqueName),
            Optional.ofNullable(hierarchyUniqueName),
            Optional.ofNullable(levelUniqueName),
            Optional.ofNullable(memberUniqueName),
            Optional.ofNullable(PropertyTypeEnum.fromValue(propertyType)),
            Optional.ofNullable(propertyName),
            Optional.ofNullable(PropertyOriginEnum.fromValue(propertyOrigin)),
            Optional.ofNullable(CubeSourceEnum.fromValue(cubeSource)),
            Optional.ofNullable(VisibilityEnum.fromValue(propertyVisibility)));
    }

    public static DiscoverResponse toDiscoverMdSchemaProperties(List<MdSchemaPropertiesResponseRow> responseApi) {
        // TODO
        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }
}
