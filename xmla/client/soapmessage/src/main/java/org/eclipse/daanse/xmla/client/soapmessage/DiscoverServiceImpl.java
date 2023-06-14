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
package org.eclipse.daanse.xmla.client.soapmessage;

import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import org.eclipse.daanse.xmla.api.discover.DiscoverService;
import org.eclipse.daanse.xmla.api.discover.Properties;
import org.eclipse.daanse.xmla.api.discover.dbschema.catalogs.DbSchemaCatalogsRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.catalogs.DbSchemaCatalogsResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.catalogs.DbSchemaCatalogsRestrictions;
import org.eclipse.daanse.xmla.api.discover.dbschema.columns.DbSchemaColumnsRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.columns.DbSchemaColumnsResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.providertypes.DbSchemaProviderTypesRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.providertypes.DbSchemaProviderTypesResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.schemata.DbSchemaSchemataRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.schemata.DbSchemaSchemataResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.sourcetables.DbSchemaSourceTablesRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.sourcetables.DbSchemaSourceTablesResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.tables.DbSchemaTablesRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.tables.DbSchemaTablesResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.tables.DbSchemaTablesRestrictions;
import org.eclipse.daanse.xmla.api.discover.dbschema.tablesinfo.DbSchemaTablesInfoRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.tablesinfo.DbSchemaTablesInfoResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesRequest;
import org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesRestrictions;
import org.eclipse.daanse.xmla.api.discover.discover.enumerators.DiscoverEnumeratorsRequest;
import org.eclipse.daanse.xmla.api.discover.discover.enumerators.DiscoverEnumeratorsResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.enumerators.DiscoverEnumeratorsRestrictions;
import org.eclipse.daanse.xmla.api.discover.discover.keywords.DiscoverKeywordsRequest;
import org.eclipse.daanse.xmla.api.discover.discover.keywords.DiscoverKeywordsResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.literals.DiscoverLiteralsRequest;
import org.eclipse.daanse.xmla.api.discover.discover.literals.DiscoverLiteralsResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.properties.DiscoverPropertiesRequest;
import org.eclipse.daanse.xmla.api.discover.discover.properties.DiscoverPropertiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.schemarowsets.DiscoverSchemaRowsetsRequest;
import org.eclipse.daanse.xmla.api.discover.discover.schemarowsets.DiscoverSchemaRowsetsResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.xmlmetadata.DiscoverXmlMetaDataRequest;
import org.eclipse.daanse.xmla.api.discover.discover.xmlmetadata.DiscoverXmlMetaDataResponseRow;
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
import org.eclipse.daanse.xmla.api.discover.mdschema.kpis.MdSchemaKpisRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.kpis.MdSchemaKpisResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.levels.MdSchemaLevelsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.levels.MdSchemaLevelsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.measuregroupdimensions.MdSchemaMeasureGroupDimensionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.measuregroupdimensions.MdSchemaMeasureGroupDimensionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.measuregroups.MdSchemaMeasureGroupsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.measuregroups.MdSchemaMeasureGroupsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.measures.MdSchemaMeasuresRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.measures.MdSchemaMeasuresResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.members.MdSchemaMembersRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.members.MdSchemaMembersResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.properties.MdSchemaPropertiesRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.properties.MdSchemaPropertiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.sets.MdSchemaSetsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.sets.MdSchemaSetsResponseRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesRestrictions.RESTRICTIONS_AUTHENTICATION_MODE;
import static org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesRestrictions.RESTRICTIONS_DATA_SOURCE_DESCRIPTION;
import static org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesRestrictions.RESTRICTIONS_DATA_SOURCE_INFO;
import static org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesRestrictions.RESTRICTIONS_DATA_SOURCE_NAME;
import static org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesRestrictions.RESTRICTIONS_PROVIDER_NAME;
import static org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesRestrictions.RESTRICTIONS_PROVIDER_TYPE;
import static org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesRestrictions.RESTRICTIONS_URL;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.AXIS_FORMAT;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.CATALOG;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.CONTENT;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.DATA_SOURCE_INFO;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.DBSCHEMA_CATALOGS;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.DBSCHEMA_TABLES;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.DISCOVER;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.DISCOVER_DATASOURCES;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.DISCOVER_ENUMERATORS;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.FORMAT;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.LOCALE_IDENTIFIER;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.MDSCHEMA_ACTIONS;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.MDSCHEMA_CUBES;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.MDSCHEMA_DIMENSIONS;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.MDSCHEMA_FUNCTIONS;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.MDSCHEMA_HIERARCHIES;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.PROPERTIES;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.PROPERTY_LIST;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.REQUEST_TYPE;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTIONS;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTIONS_ACTION_NAME;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTIONS_ACTION_TYPE;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTIONS_BASE_CUBE_NAME;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTIONS_CATALOG_NAME;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTIONS_COORDINATE;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTIONS_COORDINATE_TYPE;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTIONS_CUBE_NAME;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTIONS_CUBE_SOURCE;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTIONS_DIMENSION_NAME;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTIONS_DIMENSION_UNIQUE_NAME;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTIONS_DIMENSION_VISIBILITY;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTIONS_ENUM_NAME;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTIONS_HIERARCHY_NAME;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTIONS_HIERARCHY_ORIGIN;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTIONS_HIERARCHY_UNIQUE_NAME;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTIONS_HIERARCHY_VISIBILITY;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTIONS_INTERFACE_NAME;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTIONS_INVOCATION;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTIONS_LIBRARY_NAME;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTIONS_ORIGIN;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTIONS_SCHEMA_NAME;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTIONS_TABLE_CATALOG;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTIONS_TABLE_NAME;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTIONS_TABLE_SCHEMA;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTIONS_TABLE_TYPE;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTION_LIST;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.SOAP_ACTION_DISCOVER;
import static org.eclipse.daanse.xmla.client.soapmessage.Convertor.convertToDbSchemaCatalogsResponseRow;
import static org.eclipse.daanse.xmla.client.soapmessage.Convertor.convertToDbSchemaTablesResponseRow;
import static org.eclipse.daanse.xmla.client.soapmessage.Convertor.convertToDiscoverDataSourcesResponseRow;
import static org.eclipse.daanse.xmla.client.soapmessage.Convertor.convertToDiscoverEnumeratorsResponseRow;
import static org.eclipse.daanse.xmla.client.soapmessage.Convertor.convertToMdSchemaActionsResponseRow;
import static org.eclipse.daanse.xmla.client.soapmessage.Convertor.convertToMdSchemaCubesResponseRow;
import static org.eclipse.daanse.xmla.client.soapmessage.Convertor.convertToMdSchemaDimensionsResponseRow;
import static org.eclipse.daanse.xmla.client.soapmessage.Convertor.convertToMdSchemaFunctionsResponseRow;
import static org.eclipse.daanse.xmla.client.soapmessage.Convertor.convertToMdSchemaHierarchiesResponseRow;

public class DiscoverServiceImpl implements DiscoverService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscoverServiceImpl.class);
    private SoapClient soapClient;

    public DiscoverServiceImpl(SoapClient soapClient) {
        this.soapClient = soapClient;
    }

    @Override
    public List<DbSchemaCatalogsResponseRow> dbSchemaCatalogs(DbSchemaCatalogsRequest dbSchemaCatalogsRequest) {

        try {
            Consumer<SOAPMessage> msg = getConsumer(dbSchemaCatalogsRequest);
            SOAPMessage message = soapClient.callSoapWebService(Optional.of(SOAP_ACTION_DISCOVER), msg);
            return convertToDbSchemaCatalogsResponseRow(message.getSOAPBody());
        } catch (SOAPException e) {
            LOGGER.error("DiscoverService dbSchemaCatalogs error", e);
        }
        return List.of();
    }

    @Override
    public List<DbSchemaTablesResponseRow> dbSchemaTables(DbSchemaTablesRequest dbSchemaTablesRequest) {
        try {
            Consumer<SOAPMessage> msg = getConsumer(dbSchemaTablesRequest);
            SOAPMessage message = soapClient.callSoapWebService(Optional.of(SOAP_ACTION_DISCOVER), msg);
            return convertToDbSchemaTablesResponseRow(message.getSOAPBody());
        } catch (SOAPException e) {
            LOGGER.error("DiscoverService dbSchemaTables error", e);
        }
        return List.of();
    }

    @Override
    public List<DiscoverEnumeratorsResponseRow> discoverEnumerators(
        DiscoverEnumeratorsRequest discoverEnumeratorsRequest
    ) {
        try {
            Consumer<SOAPMessage> msg = getConsumer(discoverEnumeratorsRequest);
            SOAPMessage message = soapClient.callSoapWebService(Optional.of(SOAP_ACTION_DISCOVER), msg);
            return convertToDiscoverEnumeratorsResponseRow(message.getSOAPBody());
        } catch (SOAPException e) {
            LOGGER.error("DiscoverService discoverEnumerators error", e);
        }
        return List.of();
    }

    @Override
    public List<DiscoverKeywordsResponseRow> discoverKeywords(DiscoverKeywordsRequest discoverKeywordsRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DiscoverLiteralsResponseRow> discoverLiterals(DiscoverLiteralsRequest discoverLiteralsRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DiscoverPropertiesResponseRow> discoverProperties(DiscoverPropertiesRequest discoverPropertiesRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DiscoverSchemaRowsetsResponseRow> discoverSchemaRowsets(
        DiscoverSchemaRowsetsRequest discoverSchemaRowsetsRequest
    ) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MdSchemaActionsResponseRow> mdSchemaActions(MdSchemaActionsRequest mdSchemaActionsRequest) {
        try {
            Consumer<SOAPMessage> msg = getConsumer(mdSchemaActionsRequest);
            SOAPMessage message = soapClient.callSoapWebService(Optional.of(SOAP_ACTION_DISCOVER), msg);
            return convertToMdSchemaActionsResponseRow(message.getSOAPBody());
        } catch (SOAPException e) {
            LOGGER.error("DiscoverService mdSchemaCubes error", e);
        }
        return List.of();
    }

    @Override
    public List<MdSchemaCubesResponseRow> mdSchemaCubes(MdSchemaCubesRequest mdSchemaCubesRequest) {
        try {
            Consumer<SOAPMessage> msg = getConsumer(mdSchemaCubesRequest);
            SOAPMessage message = soapClient.callSoapWebService(Optional.of(SOAP_ACTION_DISCOVER), msg);
            return convertToMdSchemaCubesResponseRow(message.getSOAPBody());
        } catch (SOAPException e) {
            LOGGER.error("DiscoverService mdSchemaCubes error", e);
        }
        return List.of();
    }

    @Override
    public List<MdSchemaDimensionsResponseRow> mdSchemaDimensions(MdSchemaDimensionsRequest mdSchemaDimensionsRequest) {
        try {
            Consumer<SOAPMessage> msg = getConsumer(mdSchemaDimensionsRequest);
            SOAPMessage message = soapClient.callSoapWebService(Optional.of(SOAP_ACTION_DISCOVER), msg);
            return convertToMdSchemaDimensionsResponseRow(message.getSOAPBody());
        } catch (SOAPException e) {
            LOGGER.error("DiscoverService mdSchemaFunctions error", e);
        }
        return List.of();
    }

    @Override
    public List<MdSchemaFunctionsResponseRow> mdSchemaFunctions(MdSchemaFunctionsRequest mdSchemaFunctionsRequest) {
        try {
            Consumer<SOAPMessage> msg = getConsumer(mdSchemaFunctionsRequest);
            SOAPMessage message = soapClient.callSoapWebService(Optional.of(SOAP_ACTION_DISCOVER), msg);
            return convertToMdSchemaFunctionsResponseRow(message.getSOAPBody());
        } catch (SOAPException e) {
            LOGGER.error("DiscoverService mdSchemaFunctions error", e);
        }
        return List.of();
    }

    @Override
    public List<MdSchemaHierarchiesResponseRow> mdSchemaHierarchies(MdSchemaHierarchiesRequest requestApi) {
        try {
            Consumer<SOAPMessage> msg = getConsumer(requestApi);
            SOAPMessage message = soapClient.callSoapWebService(Optional.of(SOAP_ACTION_DISCOVER), msg);
            return convertToMdSchemaHierarchiesResponseRow(message.getSOAPBody());
        } catch (SOAPException e) {
            LOGGER.error("DiscoverService dataSources error", e);
        }
        return List.of();
    }

    @Override
    public List<DiscoverDataSourcesResponseRow> dataSources(DiscoverDataSourcesRequest requestApi) {
        try {
            Consumer<SOAPMessage> msg = getConsumer(requestApi);
            SOAPMessage message = soapClient.callSoapWebService(Optional.of(SOAP_ACTION_DISCOVER), msg);
            return convertToDiscoverDataSourcesResponseRow(message.getSOAPBody());
        } catch (SOAPException e) {
            LOGGER.error("DiscoverService dataSources error", e);
        }
        return List.of();
    }

    private Consumer<SOAPMessage> getConsumer(DiscoverEnumeratorsRequest requestApi) {
        return new Consumer<>() {
            @Override
            public void accept(SOAPMessage message) {
                try {
                    DiscoverEnumeratorsRestrictions dr = requestApi.restrictions();
                    Properties properties = requestApi.properties();
                    SOAPElement discover = message.getSOAPBody()
                        .addChildElement(DISCOVER);
                    discover.addChildElement(REQUEST_TYPE).setTextContent(DISCOVER_ENUMERATORS);
                    SOAPElement restrictionList = discover.addChildElement(RESTRICTIONS)
                        .addChildElement(RESTRICTION_LIST);

                    dr.enumName().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_ENUM_NAME, v));

                    SOAPElement propertyList = discover.addChildElement(PROPERTIES)
                        .addChildElement(PROPERTY_LIST);
                    setPropertyList(propertyList, properties);

                } catch (SOAPException e) {
                    LOGGER.error("DiscoverService MdSchemaActionsRequest accept error", e);
                }
            }
        };
    }

    private Consumer<SOAPMessage> getConsumer(DbSchemaTablesRequest requestApi) {
        return new Consumer<>() {
            @Override
            public void accept(SOAPMessage message) {
                try {
                    DbSchemaTablesRestrictions dr = requestApi.restrictions();
                    Properties properties = requestApi.properties();
                    SOAPElement discover = message.getSOAPBody()
                        .addChildElement(DISCOVER);
                    discover.addChildElement(REQUEST_TYPE).setTextContent(DBSCHEMA_TABLES);
                    SOAPElement restrictionList = discover.addChildElement(RESTRICTIONS)
                        .addChildElement(RESTRICTION_LIST);

                    dr.tableCatalog().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_TABLE_CATALOG, v));
                    dr.tableSchema().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_TABLE_SCHEMA, v));
                    dr.tableName().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_TABLE_NAME, v));
                    dr.tableType().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_TABLE_TYPE, v));

                    SOAPElement propertyList = discover.addChildElement(PROPERTIES)
                        .addChildElement(PROPERTY_LIST);
                    setPropertyList(propertyList, properties);

                } catch (SOAPException e) {
                    LOGGER.error("DiscoverService DbSchemaTablesRequest accept error", e);
                }
            }
        };
    }

    private Consumer<SOAPMessage> getConsumer(DbSchemaCatalogsRequest requestApi) {
        return new Consumer<>() {
            @Override
            public void accept(SOAPMessage message) {
                try {
                    DbSchemaCatalogsRestrictions dr = requestApi.restrictions();
                    Properties properties = requestApi.properties();
                    SOAPElement discover = message.getSOAPBody()
                        .addChildElement(DISCOVER);
                    discover.addChildElement(REQUEST_TYPE).setTextContent(DBSCHEMA_CATALOGS);
                    SOAPElement restrictionList = discover.addChildElement(RESTRICTIONS)
                        .addChildElement(RESTRICTION_LIST);

                    dr.catalogName().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_CATALOG_NAME, v));

                    SOAPElement propertyList = discover.addChildElement(PROPERTIES)
                        .addChildElement(PROPERTY_LIST);
                    setPropertyList(propertyList, properties);

                } catch (SOAPException e) {
                    LOGGER.error("DiscoverService DbSchemaCatalogsRequest accept error", e);
                }
            }
        };
    }

    private Consumer<SOAPMessage> getConsumer(MdSchemaActionsRequest requestApi) {
        return new Consumer<>() {
            @Override
            public void accept(SOAPMessage message) {
                try {
                    MdSchemaActionsRestrictions dr = requestApi.restrictions();
                    Properties properties = requestApi.properties();
                    SOAPElement discover = message.getSOAPBody()
                        .addChildElement(DISCOVER);
                    discover.addChildElement(REQUEST_TYPE).setTextContent(MDSCHEMA_ACTIONS);
                    SOAPElement restrictionList = discover.addChildElement(RESTRICTIONS)
                        .addChildElement(RESTRICTION_LIST);

                    dr.catalogName().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_CATALOG_NAME, v));
                    dr.schemaName().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_SCHEMA_NAME, v));
                    if (dr.cubeName() != null) {
                        addChildElement(restrictionList, RESTRICTIONS_CUBE_NAME, dr.cubeName());
                    }
                    dr.actionName().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_ACTION_NAME, v));
                    dr.actionType().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_ACTION_TYPE, String.valueOf(v.getValue())));
                    dr.coordinate().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_COORDINATE, v));
                    if (dr.coordinateType() != null) {
                        addChildElement(restrictionList, RESTRICTIONS_COORDINATE_TYPE, String.valueOf(dr.coordinateType().getValue()));
                    }
                    if (dr.invocation() != null) {
                        addChildElement(restrictionList, RESTRICTIONS_INVOCATION, String.valueOf(dr.invocation().getValue()));
                    }
                    dr.cubeSource().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_CUBE_SOURCE,
                        String.valueOf(v.getValue())));

                    SOAPElement propertyList = discover.addChildElement(PROPERTIES)
                        .addChildElement(PROPERTY_LIST);
                    setPropertyList(propertyList, properties);

                } catch (SOAPException e) {
                    LOGGER.error("DiscoverService MdSchemaActionsRequest accept error", e);
                }
            }
        };
    }

    private Consumer<SOAPMessage> getConsumer(MdSchemaCubesRequest requestApi) {
        return new Consumer<>() {
            @Override
            public void accept(SOAPMessage message) {
                try {
                    MdSchemaCubesRestrictions dr = requestApi.restrictions();
                    Properties properties = requestApi.properties();
                    SOAPElement discover = message.getSOAPBody()
                        .addChildElement(DISCOVER);
                    discover.addChildElement(REQUEST_TYPE).setTextContent(MDSCHEMA_CUBES);
                    SOAPElement restrictionList = discover.addChildElement(RESTRICTIONS)
                        .addChildElement(RESTRICTION_LIST);

                    if (dr.catalogName() != null) {
                        addChildElement(restrictionList, RESTRICTIONS_CATALOG_NAME, dr.catalogName());
                    }
                    dr.schemaName().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_SCHEMA_NAME, v));
                    dr.cubeName().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_CUBE_NAME, v));
                    dr.baseCubeName().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_BASE_CUBE_NAME, v));
                    dr.cubeSource().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_CUBE_SOURCE,
                        String.valueOf(v.getValue())));

                    SOAPElement propertyList = discover.addChildElement(PROPERTIES)
                        .addChildElement(PROPERTY_LIST);
                    setPropertyList(propertyList, properties);

                } catch (SOAPException e) {
                    LOGGER.error("DiscoverService MdSchemaCubesRequest accept error", e);
                }
            }
        };
    }

    private Consumer<SOAPMessage> getConsumer(MdSchemaDimensionsRequest requestApi) {
        return new Consumer<>() {
            @Override
            public void accept(SOAPMessage message) {
                try {
                    MdSchemaDimensionsRestrictions dr = requestApi.restrictions();
                    Properties properties = requestApi.properties();
                    SOAPElement discover = message.getSOAPBody()
                        .addChildElement(DISCOVER);
                    discover.addChildElement(REQUEST_TYPE).setTextContent(MDSCHEMA_DIMENSIONS);
                    SOAPElement restrictionList = discover.addChildElement(RESTRICTIONS)
                        .addChildElement(RESTRICTION_LIST);
                    dr.catalogName().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_CATALOG_NAME, v));
                    dr.schemaName().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_SCHEMA_NAME, v));
                    dr.cubeName().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_CUBE_NAME, v));
                    dr.dimensionName().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_DIMENSION_NAME, v));
                    dr.dimensionUniqueName().ifPresent(v -> addChildElement(restrictionList,
                        RESTRICTIONS_DIMENSION_UNIQUE_NAME, v));
                    dr.cubeSource().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_CUBE_SOURCE,
                        String.valueOf(v.getValue())));
                    dr.dimensionVisibility().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_DIMENSION_VISIBILITY
                        , String.valueOf(v.getValue())));

                    SOAPElement propertyList = discover.addChildElement(PROPERTIES)
                        .addChildElement(PROPERTY_LIST);
                    setPropertyList(propertyList, properties);

                } catch (SOAPException e) {
                    LOGGER.error("DiscoverService MdSchemaDimensionsRequest accept error", e);
                }
            }
        };
    }

    private Consumer<SOAPMessage> getConsumer(MdSchemaFunctionsRequest requestApi) {
        return new Consumer<>() {
            @Override
            public void accept(SOAPMessage message) {
                try {
                    MdSchemaFunctionsRestrictions dr = requestApi.restrictions();
                    Properties properties = requestApi.properties();
                    SOAPElement discover = message.getSOAPBody()
                        .addChildElement(DISCOVER);
                    discover.addChildElement(REQUEST_TYPE).setTextContent(MDSCHEMA_FUNCTIONS);
                    SOAPElement restrictionList = discover.addChildElement(RESTRICTIONS)
                        .addChildElement(RESTRICTION_LIST);

                    dr.origin().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_ORIGIN,
                        String.valueOf(v.getValue())));
                    dr.interfaceName().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_INTERFACE_NAME,
                        v.name()));
                    dr.libraryName().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_LIBRARY_NAME, v));

                    SOAPElement propertyList = discover.addChildElement(PROPERTIES)
                        .addChildElement(PROPERTY_LIST);
                    setPropertyList(propertyList, properties);

                } catch (SOAPException e) {
                    LOGGER.error("DiscoverService MdSchemaFunctionsRequest accept error", e);
                }
            }
        };
    }

    private Consumer<SOAPMessage> getConsumer(MdSchemaHierarchiesRequest requestApi) {
        return new Consumer<>() {
            @Override
            public void accept(SOAPMessage message) {
                try {
                    MdSchemaHierarchiesRestrictions dr = requestApi.restrictions();
                    Properties properties = requestApi.properties();
                    SOAPElement discover = message.getSOAPBody()
                        .addChildElement(DISCOVER);
                    discover.addChildElement(REQUEST_TYPE).setTextContent(MDSCHEMA_HIERARCHIES);
                    SOAPElement restrictionList = discover.addChildElement(RESTRICTIONS)
                        .addChildElement(RESTRICTION_LIST);

                    dr.catalogName().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_CATALOG_NAME, v));
                    dr.schemaName().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_SCHEMA_NAME, v));
                    dr.cubeName().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_CUBE_NAME, v));
                    dr.dimensionUniqueName().ifPresent(v -> addChildElement(restrictionList,
                        RESTRICTIONS_DIMENSION_UNIQUE_NAME, v));
                    dr.hierarchyName().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_HIERARCHY_NAME, v));
                    dr.hierarchyUniqueName().ifPresent(v -> addChildElement(restrictionList,
                        RESTRICTIONS_HIERARCHY_UNIQUE_NAME, v));
                    dr.hierarchyOrigin().ifPresent(v -> addChildElement(restrictionList,
                        RESTRICTIONS_HIERARCHY_ORIGIN, String.valueOf(v)));
                    dr.cubeSource().ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_CUBE_SOURCE,
                        String.valueOf(v.getValue())));
                    dr.hierarchyVisibility().ifPresent(v -> addChildElement(restrictionList,
                        RESTRICTIONS_HIERARCHY_VISIBILITY, String.valueOf(v.getValue())));

                    SOAPElement propertyList = discover.addChildElement(PROPERTIES)
                        .addChildElement(PROPERTY_LIST);
                    setPropertyList(propertyList, properties);

                } catch (SOAPException e) {
                    LOGGER.error("DiscoverService MdSchemaHierarchiesRequest accept error", e);
                }
            }
        };
    }

    private Consumer<SOAPMessage> getConsumer(DiscoverDataSourcesRequest requestApi) {
        return new Consumer<>() {
            @Override
            public void accept(SOAPMessage message) {
                try {
                    DiscoverDataSourcesRestrictions dr = requestApi.restrictions();
                    Properties properties = requestApi.properties();
                    SOAPElement discover = message.getSOAPBody()
                        .addChildElement(DISCOVER);
                    discover.addChildElement(REQUEST_TYPE).setTextContent(DISCOVER_DATASOURCES);
                    SOAPElement restrictionList = discover.addChildElement(RESTRICTIONS)
                        .addChildElement(RESTRICTION_LIST);
                    addChildElement(restrictionList, RESTRICTIONS_DATA_SOURCE_NAME, dr.dataSourceName());
                    dr.dataSourceDescription()
                        .ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_DATA_SOURCE_DESCRIPTION, v));
                    dr.dataSourceDescription()
                        .ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_DATA_SOURCE_DESCRIPTION, v));
                    dr.url()
                        .ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_URL, v));
                    dr.dataSourceInfo()
                        .ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_DATA_SOURCE_INFO, v));
                    if (dr.providerName() != null) {
                        addChildElement(restrictionList, RESTRICTIONS_PROVIDER_NAME, dr.providerName());
                    }
                    dr.providerType()
                        .ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_PROVIDER_TYPE, v.name()));
                    dr.authenticationMode()
                        .ifPresent(v -> addChildElement(restrictionList, RESTRICTIONS_AUTHENTICATION_MODE, v.name()));

                    SOAPElement propertyList = discover.addChildElement(PROPERTIES)
                        .addChildElement(PROPERTY_LIST);
                    setPropertyList(propertyList, properties);

                } catch (SOAPException e) {
                    LOGGER.error("DiscoverService DiscoverDataSourcesRequest accept error", e);
                }
            }
        };
    }

    @Override
    public List<DiscoverXmlMetaDataResponseRow> xmlMetaData(DiscoverXmlMetaDataRequest requestApi) {
        return null;
    }

    @Override
    public List<DbSchemaColumnsResponseRow> dbSchemaColumns(DbSchemaColumnsRequest requestApi) {
        return null;
    }

    @Override
    public List<DbSchemaProviderTypesResponseRow> dbSchemaProviderTypes(DbSchemaProviderTypesRequest requestApi) {
        return null;
    }

    @Override
    public List<DbSchemaSchemataResponseRow> dbSchemaSchemata(DbSchemaSchemataRequest requestApi) {
        return null;
    }

    @Override
    public List<MdSchemaLevelsResponseRow> mdSchemaLevels(MdSchemaLevelsRequest requestApi) {
        return null;
    }

    @Override
    public List<MdSchemaMeasureGroupDimensionsResponseRow> mdSchemaMeasureGroupDimensions(
        MdSchemaMeasureGroupDimensionsRequest requestApi
    ) {
        return null;
    }

    @Override
    public List<MdSchemaMeasuresResponseRow> mdSchemaMeasures(MdSchemaMeasuresRequest requestApi) {
        return null;
    }

    @Override
    public List<MdSchemaMembersResponseRow> mdSchemaMembers(MdSchemaMembersRequest requestApi) {
        return null;
    }

    @Override
    public List<MdSchemaPropertiesResponseRow> mdSchemaProperties(
        MdSchemaPropertiesRequest requestApi
    ) {
        return null;
    }

    @Override
    public List<MdSchemaSetsResponseRow> mdSchemaSets(MdSchemaSetsRequest requestApi) {
        return null;
    }

    @Override
    public List<MdSchemaKpisResponseRow> mdSchemaKpis(MdSchemaKpisRequest requestApi) {
        return null;
    }

    @Override
    public List<MdSchemaMeasureGroupsResponseRow> mdSchemaMeasureGroups(
        MdSchemaMeasureGroupsRequest requestApi
    ) {
        return null;
    }

    @Override
    public List<DbSchemaSourceTablesResponseRow> dbSchemaSourceTables(
        DbSchemaSourceTablesRequest requestApi
    ) {
        return null;
    }

    @Override
    public List<DbSchemaTablesInfoResponseRow> dbSchemaTablesInfo(
        DbSchemaTablesInfoRequest requestApi
    ) {
        return null;
    }

    private void setPropertyList(SOAPElement propertyList, Properties properties) {
        properties.localeIdentifier().ifPresent(v -> addChildElement(propertyList, LOCALE_IDENTIFIER, v.toString()));
        properties.dataSourceInfo().ifPresent(v -> addChildElement(propertyList, DATA_SOURCE_INFO, v));
        properties.content().ifPresent(v -> addChildElement(propertyList, CONTENT, v.getValue()));
        properties.format().ifPresent(v -> addChildElement(propertyList, FORMAT, v.getValue()));
        properties.catalog().ifPresent(v -> addChildElement(propertyList, CATALOG, v));
        properties.axisFormat().ifPresent(v -> addChildElement(propertyList, AXIS_FORMAT, v.getValue()));
    }

    private void addChildElement(SOAPElement element, String childElementName, String value) {
        try {
            element.addChildElement(childElementName).setTextContent(value);
        } catch (SOAPException e) {
            LOGGER.error("addChildElement {} error", childElementName);
            throw new SoapClientException("addChildElement error", e);
        }
    }
}
