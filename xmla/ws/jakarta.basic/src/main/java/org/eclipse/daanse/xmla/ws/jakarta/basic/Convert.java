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

import org.eclipse.daanse.xmla.api.common.properties.Content;
import org.eclipse.daanse.xmla.api.common.properties.Format;
import org.eclipse.daanse.xmla.api.discover.dbschema.catalogs.DbSchemaCatalogsRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.catalogs.DbSchemaCatalogsResponse;
import org.eclipse.daanse.xmla.api.discover.dbschema.tables.DbSchemaTablesRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.tables.DbSchemaTablesResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.tables.DbSchemaTablesRestrictions;
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
import org.eclipse.daanse.xmla.model.record.discover.PropertiesR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.catalogs.DbSchemaCatalogsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.tables.DbSchemaTablesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.tables.DbSchemaTablesRestrictionsR;
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
import org.eclipse.daanse.xmla.model.record.discover.mdschema.actions.MdSchemaActionsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.actions.MdSchemaActionsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.cubes.MdSchemaCubesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.cubes.MdSchemaCubesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.demensions.MdSchemaDimensionsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.demensions.MdSchemaDimensionsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.functions.MdSchemaFunctionsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.functions.MdSchemaFunctionsRestrictionsR;
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
        PropertiesR propertiesMap = new PropertiesR();
        propertiesMap.setLocaleIdentifier(localeIdentifier(requestWs));
        propertiesMap.setContent(content(requestWs));
        propertiesMap.setFormat(format(requestWs));
        propertiesMap.setDataSourceInfo(dataSourceInfo(requestWs));
        propertiesMap.setCatalog(catalog(requestWs));
        propertiesMap.setLocaleIdentifier(localeIdentifier(requestWs));

        return propertiesMap;
    }

    private static DiscoverPropertiesRestrictionsR discoverPropertiesMapestrictions(Discover requestWs) {
        Map<String, String> map = restrictionsMap(requestWs);

        String propertyName = map.get(DiscoverPropertiesRestrictions.RESTRICTIONS_PROPERTY_NAME);

        return new DiscoverPropertiesRestrictionsR(Optional.ofNullable(propertyName));
    }

    public static DiscoverPropertiesRequest fromDiscoverProperties(Discover requestWs) {

        System.out.println(requestWs);
        PropertiesR properties = discoverProperties(requestWs);
        DiscoverPropertiesRestrictionsR restrictions = discoverPropertiesMapestrictions(requestWs);

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
                cubeName, Optional.ofNullable(actionName), Optional.ofNullable(Integer.decode(actionType)),
                Optional.ofNullable(coordinate), Integer.valueOf(coordinateType), Integer.valueOf(invocation));
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
                Optional.ofNullable(baseCubeName), Optional.ofNullable(Integer.decode(cubeSource)));
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

        return new MdSchemaFunctionsRestrictionsR(Optional.ofNullable(Integer.valueOf(origin)),
                Optional.ofNullable(interfaceName), Optional.ofNullable(libraryName));
    }

    public static DiscoverResponse toDiscoverMdSchemaFunctions(List<MdSchemaFunctionsResponseRow> responseApi) {
        // TODO
        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }
}
