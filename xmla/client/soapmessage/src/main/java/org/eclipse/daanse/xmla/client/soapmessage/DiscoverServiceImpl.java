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
import org.eclipse.daanse.xmla.api.discover.dbschema.tablesinfo.DbSchemaTablesInfoRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.tablesinfo.DbSchemaTablesInfoResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesRequest;
import org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesRestrictions;
import org.eclipse.daanse.xmla.api.discover.discover.enumerators.DiscoverEnumeratorsRequest;
import org.eclipse.daanse.xmla.api.discover.discover.enumerators.DiscoverEnumeratorsResponseRow;
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
import org.eclipse.daanse.xmla.api.discover.mdschema.cubes.MdSchemaCubesRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.cubes.MdSchemaCubesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.functions.MdSchemaFunctionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.functions.MdSchemaFunctionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.hierarchies.MdSchemaHierarchiesRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.hierarchies.MdSchemaHierarchiesResponseRow;
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
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.DISCOVER;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.DISCOVER_DATASOURCES;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.PROPERTIES;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.PROPERTY_LIST;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.REQUEST_TYPE;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTIONS;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.RESTRICTION_LIST;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.SOAP_ACTION_DISCOVER;

public class DiscoverServiceImpl implements DiscoverService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscoverServiceImpl.class);
    private SoapClient soapClient;

    public DiscoverServiceImpl(SoapClient soapClient) {
        this.soapClient = soapClient;
    }

    @Override
    public List<DbSchemaCatalogsResponseRow> dbSchemaCatalogs(DbSchemaCatalogsRequest dbSchemaCatalogsRequest) {

        try {
            soapClient.callSoapWebService(null, null);
        } catch (SOAPException e) {
            LOGGER.error("DiscoverService dbSchemaCatalogs error", e);
        }
        return null;
    }

    @Override
    public List<DbSchemaTablesResponseRow> dbSchemaTables(DbSchemaTablesRequest dbSchemaTablesRequest) {
        return null;
    }

    @Override
    public List<DiscoverEnumeratorsResponseRow> discoverEnumerators(
            DiscoverEnumeratorsRequest discoverEnumeratorsRequest) {
        return null;
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
            DiscoverSchemaRowsetsRequest discoverSchemaRowsetsRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MdSchemaActionsResponseRow> mdSchemaActions(MdSchemaActionsRequest mdSchemaActionsRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MdSchemaCubesResponseRow> mdSchemaCubes(MdSchemaCubesRequest mdSchemaCubesRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MdSchemaDimensionsResponseRow> mdSchemaDimensions(MdSchemaDimensionsRequest mdSchemaDimensionsRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MdSchemaFunctionsResponseRow> mdSchemaFunctions(MdSchemaFunctionsRequest mdSchemaFunctionsRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MdSchemaHierarchiesResponseRow> mdSchemaHierarchies(MdSchemaHierarchiesRequest requestApi) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DiscoverDataSourcesResponseRow> dataSources(DiscoverDataSourcesRequest requestApi) {
        try {

            Consumer<SOAPMessage> msg = new Consumer<>() {

                @Override
                public void accept(SOAPMessage message) {
                    try {
                        DiscoverDataSourcesRestrictions dr = requestApi.restrictions();
                        Properties properties = requestApi.properties();
                        SOAPElement discover =  message.getSOAPBody()
                                .addChildElement(DISCOVER);
                        discover.addChildElement(REQUEST_TYPE).setTextContent(DISCOVER_DATASOURCES);
                        SOAPElement restrictionList = discover.addChildElement(RESTRICTIONS)
                            .addChildElement(RESTRICTION_LIST);
                        restrictionList.addChildElement(RESTRICTIONS_DATA_SOURCE_NAME).setTextContent(dr.dataSourceName());
                        if (dr.dataSourceDescription().isPresent()) {
                            restrictionList.addChildElement(RESTRICTIONS_DATA_SOURCE_DESCRIPTION)
                                .setTextContent(dr.dataSourceDescription().get());
                        }
                        if (dr.dataSourceDescription().isPresent()) {
                            restrictionList.addChildElement(RESTRICTIONS_DATA_SOURCE_DESCRIPTION)
                                .setTextContent(dr.dataSourceDescription().get());
                        }
                        if (dr.url().isPresent()) {
                            restrictionList.addChildElement(RESTRICTIONS_URL)
                                .setTextContent(dr.url().get());
                        }
                        if (dr.dataSourceInfo().isPresent()) {
                            restrictionList.addChildElement(RESTRICTIONS_DATA_SOURCE_INFO)
                                .setTextContent(dr.dataSourceInfo().get());
                        }
                        if (dr.providerName() != null) {
                            restrictionList.addChildElement(RESTRICTIONS_PROVIDER_NAME)
                                .setTextContent(dr.providerName());
                        }
                        if (dr.providerType().isPresent()) {
                            restrictionList.addChildElement(RESTRICTIONS_PROVIDER_TYPE)
                                .setTextContent(dr.providerType().get().name());
                        }
                        if (dr.authenticationMode().isPresent()) {
                            restrictionList.addChildElement(RESTRICTIONS_AUTHENTICATION_MODE)
                                .setTextContent(dr.authenticationMode().get().name());
                        }

                        SOAPElement propertyList = discover.addChildElement(PROPERTIES)
                            .addChildElement(PROPERTY_LIST);
                        setPropertyList(propertyList, properties);

                    } catch (SOAPException e) {
                        LOGGER.error("DiscoverService accept error", e);
                    }
                }
            };
            SOAPMessage message = soapClient.callSoapWebService(Optional.of(SOAP_ACTION_DISCOVER), msg);
            //TODO SOAPMessage to response

        } catch (SOAPException e) {
            LOGGER.error("DiscoverService dataSources error", e);
        }
        return null;
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

    private void setPropertyList(SOAPElement propertyList, Properties properties) throws SOAPException {
        if (properties.localeIdentifier().isPresent()) {
            propertyList.addChildElement("LocaleIdentifier").setTextContent(properties.localeIdentifier().get().toString());
        }

        if (properties.dataSourceInfo().isPresent()) {
            propertyList.addChildElement("DataSourceInfo").setTextContent(properties.axisFormat().get().getValue());
        }

        if (properties.content().isPresent()) {
            propertyList.addChildElement("Content").setTextContent(properties.content().get().getValue());
        }

        if (properties.format().isPresent()) {
            propertyList.addChildElement("Format").setTextContent(properties.format().get().getValue());
        }

        if (properties.catalog().isPresent()) {
            propertyList.addChildElement("Catalog").setTextContent(properties.catalog().get());
        }

        if (properties.axisFormat().isPresent()) {
            propertyList.addChildElement("AxisFormat").setTextContent(properties.axisFormat().get().getValue());
        }
    }
}
