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

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.daanse.xmla.api.common.properties.Content;
import org.eclipse.daanse.xmla.api.common.properties.Format;
import org.eclipse.daanse.xmla.api.discover.dbschemacatalogs.DiscoverDbSchemaCatalogsRequest;
import org.eclipse.daanse.xmla.api.discover.dbschemacatalogs.DiscoverDbSchemaCatalogsResponse;
import org.eclipse.daanse.xmla.api.discover.dbschematables.DiscoverDbSchemaTablesRequest;
import org.eclipse.daanse.xmla.api.discover.dbschematables.DiscoverDbSchemaTablesResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschematables.DiscoverDbSchemaTablesRestrictions;
import org.eclipse.daanse.xmla.api.discover.discoverproperties.DiscoverPropertiesRequest;
import org.eclipse.daanse.xmla.api.discover.discoverproperties.DiscoverPropertiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.discoverproperties.DiscoverPropertiesRestrictions;
import org.eclipse.daanse.xmla.api.discover.enumerators.DiscoverEnumeratorsRequest;
import org.eclipse.daanse.xmla.api.discover.enumerators.DiscoverEnumeratorsResponseRow;
import org.eclipse.daanse.xmla.api.discover.enumerators.DiscoverEnumeratorsRestrictions;
import org.eclipse.daanse.xmla.api.discover.keywords.DiscoverKeywordsRequest;
import org.eclipse.daanse.xmla.api.discover.keywords.DiscoverKeywordsResponseRow;
import org.eclipse.daanse.xmla.api.discover.keywords.DiscoverKeywordsRestrictions;
import org.eclipse.daanse.xmla.api.discover.literals.DiscoverLiteralsRequest;
import org.eclipse.daanse.xmla.api.discover.literals.DiscoverLiteralsResponseRow;
import org.eclipse.daanse.xmla.api.discover.literals.DiscoverLiteralsRestrictions;
import org.eclipse.daanse.xmla.api.discover.mdschemaactions.DiscoverMdSchemaActionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschemaactions.DiscoverMdSchemaActionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschemaactions.DiscoverMdSchemaActionsRestrictions;
import org.eclipse.daanse.xmla.api.discover.mdschemacubes.DiscoverMdSchemaCubesRequest;
import org.eclipse.daanse.xmla.api.discover.mdschemacubes.DiscoverMdSchemaCubesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschemacubes.DiscoverMdSchemaCubesRestrictions;
import org.eclipse.daanse.xmla.api.discover.mdschemademensions.DiscoverMdSchemaDimensionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschemademensions.DiscoverMdSchemaDimensionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschemademensions.DiscoverMdSchemaDimensionsRestrictions;
import org.eclipse.daanse.xmla.api.discover.mdschemafunctions.DiscoverMdSchemaFunctionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschemafunctions.DiscoverMdSchemaFunctionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschemafunctions.DiscoverMdSchemaFunctionsRestrictions;
import org.eclipse.daanse.xmla.api.discover.schemarowsets.DiscoverSchemaRowsetsRequest;
import org.eclipse.daanse.xmla.api.discover.schemarowsets.DiscoverSchemaRowsetsResponseRow;
import org.eclipse.daanse.xmla.api.discover.schemarowsets.DiscoverSchemaRowsetsRestrictions;
import org.eclipse.daanse.xmla.model.record.discover.PropertiesR;
import org.eclipse.daanse.xmla.model.record.discover.dbschemacatalogs.DbSchemaCatalogsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.dbschematables.DiscoverDbSchemaTablesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.dbschematables.DiscoverDbSchemaTablesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.discoverproperties.DiscoverPropertiesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.discoverproperties.DiscoverPropertiesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.discoverschemarowsets.DiscoverSchemaRowsetsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.discoverschemarowsets.DiscoverSchemaRowsetsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.enumerators.DiscoverEnumeratorsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.enumerators.DiscoverEnumeratorsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.keywords.DiscoverKeywordsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.keywords.DiscoverKeywordsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.literals.DiscoverLiteralsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.literals.DiscoverLiteralsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschemaactions.DiscoverMdSchemaActionsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschemaactions.DiscoverMdSchemaActionsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschemacubes.DiscoverMdSchemaCubesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschemacubes.DiscoverMdSchemaCubesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschemademensions.DiscoverMdSchemaDimensionsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschemademensions.DiscoverMdSchemaDimensionsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschemafunctions.DiscoverMdSchemaFunctionsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschemafunctions.DiscoverMdSchemaFunctionsRestrictionsR;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.Discover;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.DiscoverResponse;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.PropertyList;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.Return;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla_rowset.DiscoverPropertiesResponseRowXml;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla_rowset.Row;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla_rowset.Rowset;

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
        Optional<Integer> localeIdentifier = localeIdentifier(requestWs);
        Optional<Content> content = content(requestWs);
        Optional<Format> format = format(requestWs);
        Optional<String> dataSourceInfo = dataSourceInfo(requestWs);
        Optional<String> catalog = catalog(requestWs);

        return new PropertiesR(localeIdentifier, dataSourceInfo, content, format, catalog);
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

    public static DiscoverResponse toDiscoverProperties(List<DiscoverPropertiesResponseRow> responseApi) {

        List<Row> rows = responseApi.stream()
                .map(Convert::convertDiscoverPropertiesResponseRow)
                .toList();

        DiscoverResponse responseWs = new DiscoverResponse();

        Return r = new Return();
        Rowset rs = new Rowset();
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

        return new Row();
    }

    public static DiscoverDbSchemaCatalogsRequest fromDiscoverDbSchemaCatalogs(Discover requestWs) {
        return new DbSchemaCatalogsRequestR();
    }

    public static DiscoverResponse toDiscoverDbSchemaCatalogs(DiscoverDbSchemaCatalogsResponse responseApi) {
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
        //TODO
        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }

    public static DiscoverDbSchemaTablesRequest fromDiscoverDbSchemaTables(Discover requestWs) {
        PropertiesR properties = discoverProperties(requestWs);
        DiscoverDbSchemaTablesRestrictionsR restrictions = discoverDbSchemaTablesRestrictions(requestWs);
        return new DiscoverDbSchemaTablesRequestR(properties, restrictions);
    }

    private static DiscoverDbSchemaTablesRestrictionsR discoverDbSchemaTablesRestrictions(Discover requestWs) {
        Map<String, String> map = restrictionsMap(requestWs);

        String tableCatalog = map.get(DiscoverDbSchemaTablesRestrictions.RESTRICTIONS_TABLE_CATALOG);
        String tableSchema = map.get(DiscoverDbSchemaTablesRestrictions.RESTRICTIONS_TABLE_SCHEMA);
        String tableName = map.get(DiscoverDbSchemaTablesRestrictions.RESTRICTIONS_TABLE_NAME);
        String tableType = map.get(DiscoverDbSchemaTablesRestrictions.RESTRICTIONS_TABLE_TYPE);

        return new DiscoverDbSchemaTablesRestrictionsR(Optional.ofNullable(tableCatalog),
            Optional.ofNullable(tableSchema), Optional.ofNullable(tableName), Optional.ofNullable(tableType));
    }

    public static DiscoverResponse toDiscoverDbSchemaTables(List<DiscoverDbSchemaTablesResponseRow> responseApi) {
        //TODO
        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }

    public static DiscoverMdSchemaActionsRequest fromDiscoverMdSchemaActions(Discover requestWs) {
        PropertiesR properties = discoverProperties(requestWs);
        DiscoverMdSchemaActionsRestrictionsR restrictions = discoverMdSchemaActionsRestrictions(requestWs);
        return new DiscoverMdSchemaActionsRequestR(properties, restrictions);
    }

    private static DiscoverMdSchemaActionsRestrictionsR discoverMdSchemaActionsRestrictions(Discover requestWs) {
        Map<String, String> map = restrictionsMap(requestWs);

        String catalogName = map.get(DiscoverMdSchemaActionsRestrictions.RESTRICTIONS_CATALOG_NAME);
        String schemaName = map.get(DiscoverMdSchemaActionsRestrictions.RESTRICTIONS_SCHEMA_NAME);
        String cubeName = map.get(DiscoverMdSchemaActionsRestrictions.RESTRICTIONS_CUBE_NAME);
        String actionName = map.get(DiscoverMdSchemaActionsRestrictions.RESTRICTIONS_ACTION_NAME);
        String actionType = map.get(DiscoverMdSchemaActionsRestrictions.RESTRICTIONS_ACTION_TYPE);
        String coordinate = map.get(DiscoverMdSchemaActionsRestrictions.RESTRICTIONS_COORDINATE);
        String coordinateType = map.get(DiscoverMdSchemaActionsRestrictions.RESTRICTIONS_COORDINATE_TYPE);
        String invocation = map.get(DiscoverMdSchemaActionsRestrictions.RESTRICTIONS_INVOCATION);

        return new DiscoverMdSchemaActionsRestrictionsR(Optional.ofNullable(catalogName),
            Optional.ofNullable(schemaName), cubeName, Optional.ofNullable(actionName),
            Optional.ofNullable(Integer.decode(actionType)), Optional.ofNullable(coordinate),
            Integer.valueOf(coordinateType),
            Integer.valueOf(invocation));
    }

    public static DiscoverResponse toDiscoverMdSchemaActions(List<DiscoverMdSchemaActionsResponseRow> responseApi) {
        //TODO
        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }

    public static DiscoverMdSchemaCubesRequest fromDiscoverMdSchemaCubes(Discover requestWs) {
        PropertiesR properties = discoverProperties(requestWs);
        DiscoverMdSchemaCubesRestrictionsR restrictions = discoverMdSchemaCubesRestrictions(requestWs);
        return new DiscoverMdSchemaCubesRequestR(properties, restrictions);
    }

    private static DiscoverMdSchemaCubesRestrictionsR discoverMdSchemaCubesRestrictions(Discover requestWs) {
        Map<String, String> map = restrictionsMap(requestWs);

        String schemaName = map.get(DiscoverMdSchemaCubesRestrictions.RESTRICTIONS_SCHEMA_NAME);
        String cubeName = map.get(DiscoverMdSchemaCubesRestrictions.RESTRICTIONS_CUBE_NAME);
        String baseCubeName = map.get(DiscoverMdSchemaCubesRestrictions.RESTRICTIONS_BASE_CUBE_NAME);
        String cubeSource = map.get(DiscoverMdSchemaCubesRestrictions.RESTRICTIONS_CUBE_SOURCE);

        return new DiscoverMdSchemaCubesRestrictionsR(Optional.ofNullable(schemaName),
            Optional.ofNullable(cubeName), Optional.ofNullable(baseCubeName),
            Optional.ofNullable(Integer.decode(cubeSource)));
    }

    public static DiscoverResponse toDiscoverMdSchemaCubes(List<DiscoverMdSchemaCubesResponseRow> responseApi) {
        //TODO
        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }

    public static DiscoverMdSchemaDimensionsRequest fromDiscoverMdSchemaDimensions(Discover requestWs) {
        PropertiesR properties = discoverProperties(requestWs);
        DiscoverMdSchemaDimensionsRestrictionsR restrictions = discoverMdSchemaDimensionsRestrictions(requestWs);
        return new DiscoverMdSchemaDimensionsRequestR(properties, restrictions);
    }

    private static DiscoverMdSchemaDimensionsRestrictionsR discoverMdSchemaDimensionsRestrictions(Discover requestWs) {
        Map<String, String> map = restrictionsMap(requestWs);

        String catalogName = map.get(DiscoverMdSchemaDimensionsRestrictions.RESTRICTIONS_CATALOG_NAME);
        String schemaName = map.get(DiscoverMdSchemaDimensionsRestrictions.RESTRICTIONS_SCHEMA_NAME);
        String cubeName = map.get(DiscoverMdSchemaDimensionsRestrictions.RESTRICTIONS_CUBE_NAME);
        String dimensionName = map.get(DiscoverMdSchemaDimensionsRestrictions.RESTRICTIONS_DIMENSION_NAME);
        String dimensionUniqueName = map.get(DiscoverMdSchemaDimensionsRestrictions.RESTRICTIONS_DIMENSION_UNIQUE_NAME);

        return new DiscoverMdSchemaDimensionsRestrictionsR(Optional.ofNullable(catalogName),
            Optional.ofNullable(schemaName),
            Optional.ofNullable(cubeName), Optional.ofNullable(dimensionName),
            Optional.ofNullable(dimensionUniqueName));
    }

    public static DiscoverResponse toDiscoverMdSchemaDimensions(List<DiscoverMdSchemaDimensionsResponseRow> responseApi) {
        //TODO
        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }

    public static DiscoverMdSchemaFunctionsRequest fromDiscoverMdSchemaFunctions(Discover requestWs) {
        PropertiesR properties = discoverProperties(requestWs);
        DiscoverMdSchemaFunctionsRestrictionsR restrictions = discoverMdSchemaFunctionsRestrictions(requestWs);
        return new DiscoverMdSchemaFunctionsRequestR(properties, restrictions);
    }

    private static DiscoverMdSchemaFunctionsRestrictionsR discoverMdSchemaFunctionsRestrictions(Discover requestWs) {
        Map<String, String> map = restrictionsMap(requestWs);

        String origin = map.get(DiscoverMdSchemaFunctionsRestrictions.RESTRICTIONS_ORIGIN);
        String interfaceName = map.get(DiscoverMdSchemaFunctionsRestrictions.RESTRICTIONS_INTERFACE_NAME);
        String libraryName = map.get(DiscoverMdSchemaFunctionsRestrictions.RESTRICTIONS_LIBRARY_NAME);

        return new DiscoverMdSchemaFunctionsRestrictionsR(Optional.ofNullable(Integer.valueOf(origin)),
            Optional.ofNullable(interfaceName),
            Optional.ofNullable(libraryName));
    }

    public static DiscoverResponse toDiscoverMdSchemaFunctions(List<DiscoverMdSchemaFunctionsResponseRow> responseApi) {
        //TODO
        DiscoverResponse responseWs = new DiscoverResponse();
        return responseWs;
    }
}
