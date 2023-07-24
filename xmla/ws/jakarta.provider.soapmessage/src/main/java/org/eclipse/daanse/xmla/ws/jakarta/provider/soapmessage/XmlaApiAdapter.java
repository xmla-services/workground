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
package org.eclipse.daanse.xmla.ws.jakarta.provider.soapmessage;

import jakarta.xml.soap.Node;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import org.eclipse.daanse.xmla.api.XmlaService;
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
import org.eclipse.daanse.xmla.api.execute.ExecuteParameter;
import org.eclipse.daanse.xmla.api.execute.statement.StatementRequest;
import org.eclipse.daanse.xmla.api.execute.statement.StatementResponse;
import org.eclipse.daanse.xmla.api.xmla.Command;
import org.eclipse.daanse.xmla.model.record.discover.PropertiesR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.catalogs.DbSchemaCatalogsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.catalogs.DbSchemaCatalogsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.columns.DbSchemaColumnsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.columns.DbSchemaColumnsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.providertypes.DbSchemaProviderTypesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.providertypes.DbSchemaProviderTypesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.schemata.DbSchemaSchemataRequestR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.schemata.DbSchemaSchemataRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.sourcetables.DbSchemaSourceTablesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.sourcetables.DbSchemaSourceTablesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.tables.DbSchemaTablesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.tables.DbSchemaTablesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.tablesinfo.DbSchemaTablesInfoRequestR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.tablesinfo.DbSchemaTablesInfoRestrictionsR;
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
import org.eclipse.daanse.xmla.model.record.discover.mdschema.kpis.MdSchemaKpisRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.kpis.MdSchemaKpisRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.levels.MdSchemaLevelsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.levels.MdSchemaLevelsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.measuregroupdimensions.MdSchemaMeasureGroupDimensionsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.measuregroupdimensions.MdSchemaMeasureGroupDimensionsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.measuregroups.MdSchemaMeasureGroupsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.measuregroups.MdSchemaMeasureGroupsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.measures.MdSchemaMeasuresRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.measures.MdSchemaMeasuresRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.members.MdSchemaMembersRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.members.MdSchemaMembersRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.properties.MdSchemaPropertiesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.properties.MdSchemaPropertiesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.sets.MdSchemaSetsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.sets.MdSchemaSetsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.execute.statement.StatementRequestR;
import org.eclipse.daanse.xmla.model.record.xmla.StatementR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

import static org.eclipse.daanse.xmla.ws.jakarta.provider.soapmessage.SoapUtil.toStatementResponse;

public class XmlaApiAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlaApiAdapter.class);

    private XmlaService xmlaService;

    public XmlaApiAdapter(XmlaService xmlaService) {
        this.xmlaService = xmlaService;
    }

    private static final String MDSCHEMA_FUNCTIONS = "MDSCHEMA_FUNCTIONS";
    private static final String MDSCHEMA_DIMENSIONS = "MDSCHEMA_DIMENSIONS";
    private static final String MDSCHEMA_CUBES = "MDSCHEMA_CUBES";
    private static final String MDSCHEMA_ACTIONS = "MDSCHEMA_ACTIONS";
    private static final String DBSCHEMA_TABLES = "DBSCHEMA_TABLES";
    private static final String DISCOVER_LITERALS = "DISCOVER_LITERALS";
    private static final String DISCOVER_KEYWORDS = "DISCOVER_KEYWORDS";
    private static final String DISCOVER_ENUMERATORS = "DISCOVER_ENUMERATORS";
    private static final String DISCOVER_SCHEMA_ROWSETS = "DISCOVER_SCHEMA_ROWSETS";
    private static final String DISCOVER_PROPERTIES = "DISCOVER_PROPERTIES";
    private static final String DBSCHEMA_CATALOGS = "DBSCHEMA_CATALOGS";
    private static final String DISCOVER_DATASOURCES = "DISCOVER_DATASOURCES";
    private static final String DISCOVER_XML_METADATA = "DISCOVER_XML_METADATA";
    private static final String DBSCHEMA_COLUMNS = "DBSCHEMA_COLUMNS";
    private static final String DBSCHEMA_PROVIDER_TYPES = "DBSCHEMA_PROVIDER_TYPES";
    private static final String DBSCHEMA_SCHEMATA = "DBSCHEMA_SCHEMATA";
    private static final String DBSCHEMA_SOURCE_TABLES = "DBSCHEMA_SOURCE_TABLES";
    private static final String DBSCHEMA_TABLES_INFO = "DBSCHEMA_TABLES_INFO";
    private static final String MDSCHEMA_HIERARCHIES = "MDSCHEMA_HIERARCHIES";
    private static final String MDSCHEMA_LEVELS = "MDSCHEMA_LEVELS";
    private static final String MDSCHEMA_MEASUREGROUP_DIMENSIONS = "MDSCHEMA_MEASUREGROUP_DIMENSIONS";
    private static final String MDSCHEMA_MEASURES = "MDSCHEMA_MEASURES";
    private static final String MDSCHEMA_MEMBERS = "MDSCHEMA_MEMBERS";
    private static final String MDSCHEMA_PROPERTIES = "MDSCHEMA_PROPERTIES";
    private static final String MDSCHEMA_SETS = "MDSCHEMA_SETS";
    private static final String MDSCHEMA_KPIS = "MDSCHEMA_KPIS";
    private static final String MDSCHEMA_MEASUREGROUPS = "MDSCHEMA_MEASUREGROUPS";


    public SOAPMessage handleRequest(SOAPMessage message) {
        try {
            handleBody(message.getSOAPBody());
        } catch (SOAPException e) {
            LOGGER.error("handleRequest error", e);
        }

        return null;

    }

    private SOAPBody handleBody(SOAPBody body) {
        SOAPElement node = null;

        Iterator<Node> nodeIterator = body.getChildElements();
        while (nodeIterator.hasNext()) {
            Node nodeN = nodeIterator.next();
            if (nodeN instanceof SOAPElement soapElement) {
                node = soapElement;
                break;
            }
        }
        if (node != null) {
            printNode(node);
        }

        if (node != null && Constants.QNAME_MSXMLA_DISCOVER.equals(node.getElementQName())) {

            return discover(node);

        }
        if (node != null && Constants.QNAME_MSXMLA_EXECUTE.equals(node.getElementQName())) {
            return execute(node);
        }

        return body;

    }

    private SOAPBody discover(SOAPElement discover) {

        String requestType = null;
        PropertiesR properties = null;
        SOAPElement restictions = null;

        Iterator<Node> nodeIterator = discover.getChildElements();
        while (nodeIterator.hasNext()) {
            Node node = nodeIterator.next();
            if (node instanceof SOAPElement element) {
                if (requestType == null && Constants.QNAME_MSXMLA_REQUESTTYPE.equals(element.getElementQName())) {
                    requestType = element.getTextContent();
                    continue;
                }
                if (restictions == null && Constants.QNAME_MSXMLA_RESTRICTIONS.equals(element.getElementQName())) {
                    restictions = element;
                    continue;
                }
                if (properties == null && Constants.QNAME_MSXMLA_PROPERTIES.equals(element.getElementQName())) {
                    properties = Convert.propertiestoProperties(element);
                }
            }
        }

        return discover(requestType, properties, restictions);
    }

    private SOAPBody execute(SOAPElement discover) {

        Command command = null;
        PropertiesR properties = null;
        List<ExecuteParameter> parameters = null;

        Iterator<Node> nodeIterator = discover.getChildElements();
        while (nodeIterator.hasNext()) {
            Node node = nodeIterator.next();
            if (node instanceof SOAPElement element) {
                if (properties == null && Constants.QNAME_MSXMLA_PROPERTIES.equals(element.getElementQName())) {
                    properties = Convert.propertiestoProperties(element);
                }
                if (command == null && Constants.QNAME_MSXMLA_COMMAND.equals(element.getElementQName())) {
                    command = Convert.commandtoCommand(element);
                }
            }
        }

        return execute(command, properties, parameters);
    }

    private void printNode(SOAPElement node) {
        LOGGER.debug(node.getNamespaceURI());
        LOGGER.debug(node.getBaseURI());
        LOGGER.debug(node.getPrefix());
        LOGGER.debug(node.getNodeName());
        LOGGER.debug(node.getLocalName());
        LOGGER.debug(node.getNodeValue());
        LOGGER.debug(node.getTextContent());
        LOGGER.debug(node.getValue());
        String elementQNameStr = node.getElementQName().toString();
        LOGGER.debug(elementQNameStr);
    }

    private SOAPBody execute(Command command, PropertiesR properties, List<ExecuteParameter> parameters) {

        if (command instanceof StatementR statement) {
            return handleStatement(statement, properties, parameters);
        }
        else {
            throw new IllegalArgumentException("Unexpected value: " + command);
        }
    }

    private SOAPBody discover(String requestType, PropertiesR properties, SOAPElement restrictionElement) {

        SOAPBody discoverResponse = null;

        switch (requestType) {
            case MDSCHEMA_FUNCTIONS -> discoverResponse = handleMdSchemaFunctions(properties, restrictionElement);
            case MDSCHEMA_DIMENSIONS -> discoverResponse = handleMdSchemaDimensions(properties, restrictionElement);
            case MDSCHEMA_CUBES -> discoverResponse = handleMdSchemaCubes(properties, restrictionElement);
            case MDSCHEMA_ACTIONS -> discoverResponse = handleMdSchemaActions(properties, restrictionElement);
            case DBSCHEMA_TABLES -> discoverResponse = handleDbSchemaTables(properties, restrictionElement);
            case DISCOVER_LITERALS -> discoverResponse = handleDiscoverLiterals(properties, restrictionElement);
            case DISCOVER_KEYWORDS -> discoverResponse = handleDiscoverKeywords(properties, restrictionElement);
            case DISCOVER_ENUMERATORS -> discoverResponse = handleDiscoverEnumerators(properties, restrictionElement);
            case DISCOVER_SCHEMA_ROWSETS -> discoverResponse = handleDiscoverSchemaRowsets(properties, restrictionElement);
            case DISCOVER_PROPERTIES -> discoverResponse = handleDiscoverProperties(properties, restrictionElement);
            case DBSCHEMA_CATALOGS -> discoverResponse = handleDbSchemaCatalogs(properties, restrictionElement);
            case DISCOVER_DATASOURCES -> discoverResponse = handleDiscoverDataSources(properties, restrictionElement);
            case DISCOVER_XML_METADATA -> discoverResponse = handleDiscoverXmlMetaData(properties, restrictionElement);
            case DBSCHEMA_COLUMNS -> discoverResponse = handleDbSchemaColumns(properties, restrictionElement);
            case DBSCHEMA_PROVIDER_TYPES -> discoverResponse = handleDbSchemaProviderTypes(properties, restrictionElement);
            case DBSCHEMA_SCHEMATA -> discoverResponse = handleDbSchemaSchemata(properties, restrictionElement);
            case DBSCHEMA_SOURCE_TABLES -> discoverResponse = handleDbSchemaSourceTables(properties, restrictionElement);
            case DBSCHEMA_TABLES_INFO -> discoverResponse = handleDbSchemaTablesInfo(properties, restrictionElement);
            case MDSCHEMA_HIERARCHIES -> discoverResponse = handleMdSchemaHierarchies(properties, restrictionElement);
            case MDSCHEMA_LEVELS -> discoverResponse = handleMdSchemaLevels(properties, restrictionElement);
            case MDSCHEMA_MEASUREGROUP_DIMENSIONS -> discoverResponse = handleMdSchemaMeasureGroupDimensions(properties, restrictionElement);
            case MDSCHEMA_MEASURES -> discoverResponse = handleMdSchemaMeasures(properties, restrictionElement);
            case MDSCHEMA_MEMBERS -> discoverResponse = handleMdSchemaMembers(properties, restrictionElement);
            case MDSCHEMA_PROPERTIES -> discoverResponse = handleMdSchemaProperties(properties, restrictionElement);
            case MDSCHEMA_SETS -> discoverResponse = handleMdSchemaSets(properties, restrictionElement);
            case MDSCHEMA_KPIS -> discoverResponse = handleMdSchemaKpis(properties, restrictionElement);
            case MDSCHEMA_MEASUREGROUPS -> discoverResponse = handleMdSchemaMeasureGroups(properties, restrictionElement);
            default -> throw new IllegalArgumentException("Unexpected value: " + requestType);

        }
        return discoverResponse;
    }

    private SOAPBody handleMdSchemaMeasureGroups(PropertiesR propertiesR, SOAPElement restrictionElement) {
        MdSchemaMeasureGroupsRestrictionsR restrictionsR = Convert.discoverMdSchemaMeasureGroups(restrictionElement);
        MdSchemaMeasureGroupsRequest request = new MdSchemaMeasureGroupsRequestR(propertiesR, restrictionsR);
        List<MdSchemaMeasureGroupsResponseRow> rows = xmlaService.discover()
            .mdSchemaMeasureGroups(request);

        return Convert.toMdSchemaMeasureGroups(rows);
    }

    private SOAPBody handleMdSchemaKpis(PropertiesR propertiesR, SOAPElement restrictionElement) {
        MdSchemaKpisRestrictionsR restrictionsR = Convert.discoverMdSchemaKpisRestrictions(restrictionElement);
        MdSchemaKpisRequest request = new MdSchemaKpisRequestR(propertiesR, restrictionsR);
        List<MdSchemaKpisResponseRow> rows = xmlaService.discover()
            .mdSchemaKpis(request);

        return Convert.toMdSchemaKpis(rows);
    }

    private SOAPBody handleMdSchemaSets(PropertiesR propertiesR, SOAPElement restrictionElement) {
        MdSchemaSetsRestrictionsR restrictionsR = Convert.discoverMdSchemaSetsRestrictions(restrictionElement);
        MdSchemaSetsRequest request = new MdSchemaSetsRequestR(propertiesR, restrictionsR);
        List<MdSchemaSetsResponseRow> rows = xmlaService.discover()
            .mdSchemaSets(request);

        return Convert.toMdSchemaSets(rows);
    }

    private SOAPBody handleMdSchemaProperties(PropertiesR propertiesR, SOAPElement restrictionElement) {
        MdSchemaPropertiesRestrictionsR restrictionsR = Convert.discoverMdSchemaPropertiesRestrictions(restrictionElement);
        MdSchemaPropertiesRequest request = new MdSchemaPropertiesRequestR(propertiesR, restrictionsR);
        List<MdSchemaPropertiesResponseRow> rows = xmlaService.discover()
            .mdSchemaProperties(request);

        return Convert.toMdSchemaProperties(rows);

    }

    private SOAPBody handleMdSchemaMembers(PropertiesR propertiesR, SOAPElement restrictionElement) {
        MdSchemaMembersRestrictionsR restrictionsR = Convert.discoverMdSchemaMembersRestrictions(restrictionElement);
        MdSchemaMembersRequest request = new MdSchemaMembersRequestR(propertiesR, restrictionsR);
        List<MdSchemaMembersResponseRow> rows = xmlaService.discover()
            .mdSchemaMembers(request);

        return Convert.toMdSchemaMembers(rows);

    }

    private SOAPBody handleMdSchemaMeasures(PropertiesR propertiesR, SOAPElement restrictionElement) {
        MdSchemaMeasuresRestrictionsR restrictionsR = Convert.discoverMdSchemaMeasuresRestrictions(restrictionElement);
        MdSchemaMeasuresRequest request = new MdSchemaMeasuresRequestR(propertiesR, restrictionsR);
        List<MdSchemaMeasuresResponseRow> rows = xmlaService.discover()
            .mdSchemaMeasures(request);

        return Convert.toMdSchemaMeasures(rows);

    }

    private SOAPBody handleMdSchemaMeasureGroupDimensions(PropertiesR propertiesR, SOAPElement restrictionElement) {
        MdSchemaMeasureGroupDimensionsRestrictionsR restrictionsR = Convert.discoverMdSchemaMeasureGroupDimensionsRestrictions(restrictionElement);
        MdSchemaMeasureGroupDimensionsRequest request = new MdSchemaMeasureGroupDimensionsRequestR(propertiesR, restrictionsR);
        List<MdSchemaMeasureGroupDimensionsResponseRow> rows = xmlaService.discover()
            .mdSchemaMeasureGroupDimensions(request);

        return Convert.toMdSchemaMeasureGroupDimensions(rows);

    }

    private SOAPBody handleMdSchemaLevels(PropertiesR propertiesR, SOAPElement restrictionElement) {
        MdSchemaLevelsRestrictionsR restrictionsR = Convert.discoverMdSchemaLevelsRestrictions(restrictionElement);
        MdSchemaLevelsRequest request = new MdSchemaLevelsRequestR(propertiesR, restrictionsR);
        List<MdSchemaLevelsResponseRow> rows = xmlaService.discover()
            .mdSchemaLevels(request);

        return Convert.toMdSchemaLevels(rows);
    }

    private SOAPBody handleMdSchemaHierarchies(PropertiesR propertiesR, SOAPElement restrictionElement) {
        MdSchemaHierarchiesRestrictionsR restrictionsR = Convert.discoverMdSchemaHierarchiesRestrictions(restrictionElement);
        MdSchemaHierarchiesRequest request = new MdSchemaHierarchiesRequestR(propertiesR, restrictionsR);
        List<MdSchemaHierarchiesResponseRow> rows = xmlaService.discover()
            .mdSchemaHierarchies(request);

        return Convert.toMdSchemaHierarchies(rows);
    }

    private SOAPBody handleDbSchemaTablesInfo(PropertiesR propertiesR, SOAPElement restrictionElement) {
        DbSchemaTablesInfoRestrictionsR restrictionsR = Convert.discoverDbSchemaTablesInfo(restrictionElement);
        DbSchemaTablesInfoRequest request = new DbSchemaTablesInfoRequestR(propertiesR, restrictionsR);
        List<DbSchemaTablesInfoResponseRow> rows = xmlaService.discover()
            .dbSchemaTablesInfo(request);

        return Convert.toDbSchemaTablesInfo(rows);

    }

    private SOAPBody handleDbSchemaSourceTables(PropertiesR propertiesR, SOAPElement restrictionElement) {
        DbSchemaSourceTablesRestrictionsR restrictionsR = Convert.discoverDbSchemaSourceTablesRestrictions(restrictionElement);
        DbSchemaSourceTablesRequest request = new DbSchemaSourceTablesRequestR(propertiesR, restrictionsR);
        List<DbSchemaSourceTablesResponseRow> rows = xmlaService.discover()
            .dbSchemaSourceTables(request);

        return Convert.toDbSchemaSourceTables(rows);

    }

    private SOAPBody handleDbSchemaSchemata(PropertiesR propertiesR, SOAPElement restrictionElement) {
        DbSchemaSchemataRestrictionsR restrictionsR = Convert.discoverDbSchemaSchemataRestrictions(restrictionElement);
        DbSchemaSchemataRequest request = new DbSchemaSchemataRequestR(propertiesR, restrictionsR);
        List<DbSchemaSchemataResponseRow> rows = xmlaService.discover()
            .dbSchemaSchemata(request);

        return Convert.toDbSchemaSchemata(rows);

    }

    private SOAPBody handleDbSchemaProviderTypes(PropertiesR propertiesR, SOAPElement restrictionElement) {
        DbSchemaProviderTypesRestrictionsR restrictionsR = Convert.discoverDbSchemaProviderTypesRestrictions(restrictionElement);
        DbSchemaProviderTypesRequest request = new DbSchemaProviderTypesRequestR(propertiesR, restrictionsR);
        List<DbSchemaProviderTypesResponseRow> rows = xmlaService.discover()
            .dbSchemaProviderTypes(request);

        return Convert.toDbSchemaProviderTypes(rows);

    }

    private SOAPBody handleDbSchemaColumns(PropertiesR propertiesR, SOAPElement restrictionElement) {
        DbSchemaColumnsRestrictionsR restrictionsR = Convert.discoverDbSchemaColumnsRestrictions(restrictionElement);
        DbSchemaColumnsRequest request = new DbSchemaColumnsRequestR(propertiesR, restrictionsR);
        List<DbSchemaColumnsResponseRow> rows = xmlaService.discover()
            .dbSchemaColumns(request);

        return Convert.toDbSchemaColumns(rows);

    }

    private SOAPBody handleDiscoverXmlMetaData(PropertiesR propertiesR, SOAPElement restrictionElement) {
        DiscoverXmlMetaDataRestrictionsR restrictionsR = Convert.discoverDiscoverXmlMetaDataRestrictions(restrictionElement);
        DiscoverXmlMetaDataRequest request = new DiscoverXmlMetaDataRequestR(propertiesR, restrictionsR);
        List<DiscoverXmlMetaDataResponseRow> rows = xmlaService.discover()
            .xmlMetaData(request);

        return Convert.toDiscoverXmlMetaData(rows);

    }

    private SOAPBody handleDiscoverDataSources(PropertiesR propertiesR, SOAPElement restrictionElement) {
        DiscoverDataSourcesRestrictionsR restrictionsR = Convert.discoverDiscoverDataSourcesRestrictions(restrictionElement);
        DiscoverDataSourcesRequest request = new DiscoverDataSourcesRequestR(propertiesR, restrictionsR);
        List<DiscoverDataSourcesResponseRow> rows = xmlaService.discover()
            .dataSources(request);

        return Convert.toDiscoverDataSources(rows);

    }

    private SOAPBody handleDbSchemaCatalogs(PropertiesR propertiesR, SOAPElement restrictionElement) {
        DbSchemaCatalogsRestrictionsR restrictionsR = Convert.discoverDbSchemaCatalogsRestrictions(restrictionElement);
        DbSchemaCatalogsRequest request = new DbSchemaCatalogsRequestR(propertiesR, restrictionsR);
        List<DbSchemaCatalogsResponseRow> rows = xmlaService.discover()
            .dbSchemaCatalogs(request);

        return Convert.toDbSchemaCatalogs(rows);

    }

    private SOAPBody handleDiscoverSchemaRowsets(PropertiesR propertiesR, SOAPElement restrictionElement) {
        DiscoverSchemaRowsetsRestrictionsR restrictionsR = Convert.discoverSchemaRowsetsRestrictions(restrictionElement);
        DiscoverSchemaRowsetsRequest request = new DiscoverSchemaRowsetsRequestR(propertiesR, restrictionsR);
        List<DiscoverSchemaRowsetsResponseRow> rows = xmlaService.discover()
            .discoverSchemaRowsets(request);

        return Convert.toDiscoverSchemaRowsets(rows);

    }

    private SOAPBody handleDiscoverEnumerators(PropertiesR propertiesR, SOAPElement restrictionElement) {
        DiscoverEnumeratorsRestrictionsR restrictionsR = Convert.discoverDiscoverEnumerators(restrictionElement);
        DiscoverEnumeratorsRequest request = new DiscoverEnumeratorsRequestR(propertiesR, restrictionsR);
        List<DiscoverEnumeratorsResponseRow> rows = xmlaService.discover()
            .discoverEnumerators(request);

        return Convert.toDiscoverEnumerators(rows);

    }

    private SOAPBody handleDiscoverKeywords(PropertiesR propertiesR, SOAPElement restrictionElement) {
        DiscoverKeywordsRestrictionsR restrictionsR = Convert.discoverKeywordsRestrictions(restrictionElement);
        DiscoverKeywordsRequest request = new DiscoverKeywordsRequestR(propertiesR, restrictionsR);
        List<DiscoverKeywordsResponseRow> rows = xmlaService.discover()
            .discoverKeywords(request);

        return Convert.toDiscoverKeywords(rows);

    }

    private SOAPBody handleDiscoverLiterals(PropertiesR propertiesR, SOAPElement restrictionElement) {
        DiscoverLiteralsRestrictionsR restrictionsR = Convert.discoverLiteralsRestrictions(restrictionElement);
        DiscoverLiteralsRequest request = new DiscoverLiteralsRequestR(propertiesR, restrictionsR);
        List<DiscoverLiteralsResponseRow> rows = xmlaService.discover()
            .discoverLiterals(request);

        return Convert.toDiscoverLiterals(rows);

    }

    private SOAPBody handleDbSchemaTables(PropertiesR propertiesR, SOAPElement restrictionElement) {
        DbSchemaTablesRestrictionsR restrictionsR = Convert.discoverDbSchemaTablesRestrictions(restrictionElement);
        DbSchemaTablesRequest request = new DbSchemaTablesRequestR(propertiesR, restrictionsR);
        List<DbSchemaTablesResponseRow> rows = xmlaService.discover()
            .dbSchemaTables(request);

        return Convert.toDbSchemaTables(rows);

    }

    private SOAPBody handleMdSchemaActions(PropertiesR propertiesR, SOAPElement restrictionElement) {
        MdSchemaActionsRestrictionsR restrictionsR = Convert.discoverMdSchemaActionsRestrictions(restrictionElement);
        MdSchemaActionsRequest request = new MdSchemaActionsRequestR(propertiesR, restrictionsR);
        List<MdSchemaActionsResponseRow> rows = xmlaService.discover()
            .mdSchemaActions(request);

        return Convert.toMdSchemaActions(rows);
    }

    private SOAPBody handleMdSchemaCubes(PropertiesR propertiesR, SOAPElement restrictionElement) {
        MdSchemaCubesRestrictionsR restrictionsR = Convert.discoverMdSchemaCubesRestrictions(restrictionElement);
        MdSchemaCubesRequest request = new MdSchemaCubesRequestR(propertiesR, restrictionsR);
        List<MdSchemaCubesResponseRow> rows = xmlaService.discover()
            .mdSchemaCubes(request);

        return Convert.toMdSchemaCubes(rows);
    }

    private SOAPBody handleMdSchemaDimensions(PropertiesR propertiesR, SOAPElement restrictionElement) {
        MdSchemaDimensionsRestrictionsR restrictionsR = Convert.discoverMdSchemaDimensionsRestrictions(restrictionElement);
        MdSchemaDimensionsRequest request = new MdSchemaDimensionsRequestR(propertiesR, restrictionsR);
        List<MdSchemaDimensionsResponseRow> rows = xmlaService.discover()
            .mdSchemaDimensions(request);

        return Convert.toMdSchemaDimensions(rows);
    }

    private SOAPBody handleDiscoverProperties(PropertiesR propertiesR, SOAPElement restrictionElement) {

        DiscoverPropertiesRestrictionsR restrictionsR = Convert.discoverPropertiesRestrictions(restrictionElement);
        DiscoverPropertiesRequest request = new DiscoverPropertiesRequestR(propertiesR, restrictionsR);
        List<DiscoverPropertiesResponseRow> rows = xmlaService.discover()
                .discoverProperties(request);

        return Convert.toDiscoverProperties(rows);
    }

    private SOAPBody handleMdSchemaFunctions(PropertiesR propertiesR, SOAPElement restrictionElement) {

        MdSchemaFunctionsRestrictionsR restrictionsR = Convert.discoverMdSchemaFunctionsRestrictions(restrictionElement);
        MdSchemaFunctionsRequest request = new MdSchemaFunctionsRequestR(propertiesR, restrictionsR);
        List<MdSchemaFunctionsResponseRow> rows = xmlaService.discover()
            .mdSchemaFunctions(request);

        return Convert.toMdSchemaFunctions(rows);
    }

    private SOAPBody handleStatement(StatementR statement, PropertiesR properties, List<ExecuteParameter> parameters) {
        StatementRequest statementRequest = new StatementRequestR(properties,
            parameters,
            statement);
        StatementResponse statementResponse = xmlaService.execute().statement(statementRequest);
        return toStatementResponse(statementResponse);
    }

}
