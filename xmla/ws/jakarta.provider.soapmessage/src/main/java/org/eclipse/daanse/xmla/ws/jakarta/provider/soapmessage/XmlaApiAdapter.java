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
import org.eclipse.daanse.xmla.api.discover.discover.properties.DiscoverPropertiesRequest;
import org.eclipse.daanse.xmla.api.discover.discover.properties.DiscoverPropertiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.cubes.MdSchemaCubesRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.cubes.MdSchemaCubesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.functions.MdSchemaFunctionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.functions.MdSchemaFunctionsResponseRow;
import org.eclipse.daanse.xmla.model.record.discover.PropertiesR;
import org.eclipse.daanse.xmla.model.record.discover.discover.properties.DiscoverPropertiesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.discover.properties.DiscoverPropertiesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.cubes.MdSchemaCubesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.cubes.MdSchemaCubesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.demensions.MdSchemaDimensionsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.demensions.MdSchemaDimensionsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.functions.MdSchemaFunctionsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.functions.MdSchemaFunctionsRestrictionsR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

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

    private PropertiesR properties(SOAPElement propertiesElement) {
        return new PropertiesR();
    }

    private SOAPBody discover(String requestType, PropertiesR properties, SOAPElement restrictionElement) {

        SOAPBody discoverResponse = null;

        switch (requestType) {
            case MDSCHEMA_FUNCTIONS -> discoverResponse = handleMdSchemaFunctions(properties, restrictionElement);
            case MDSCHEMA_DIMENSIONS -> discoverResponse = handleMdSchemaDimensions(properties, restrictionElement);
            case MDSCHEMA_CUBES -> discoverResponse = handleMdSchemaCubes(properties, restrictionElement);
            case DISCOVER_PROPERTIES -> discoverResponse = handleDiscoverProperties(properties, restrictionElement);
        default -> throw new IllegalArgumentException("Unexpected value: " + requestType);

        }
        return discoverResponse;
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

}
