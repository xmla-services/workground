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
package org.eclipse.daanse.xmla.ws.xmlwsprovider;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.daanse.xmla.api.XmlaService;
import org.eclipse.daanse.xmla.api.discover.discoverproperties.DiscoverPropertiesRequest;
import org.eclipse.daanse.xmla.api.discover.discoverproperties.DiscoverPropertiesResponseRow;
import org.eclipse.daanse.xmla.model.record.discover.PropertiesR;
import org.eclipse.daanse.xmla.model.record.discover.discoverproperties.DiscoverPropertiesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.discoverproperties.DiscoverPropertiesRestrictionsR;

import jakarta.xml.soap.Node;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPBodyElement;
import jakarta.xml.soap.SOAPEnvelope;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;

public class XmlaApiAdapter {

    XmlaService xmlaService;

    public XmlaApiAdapter(XmlaService xmlaService) {
        this.xmlaService = xmlaService;
    }

    private static final String DISCOVER_PROPERTIES = "DISCOVER_PROPERTIES";

    public SOAPMessage handleRequest(SOAPMessage message) throws SOAPException {

        handleDiscoverRequest(message.getSOAPBody());

        return null;

    }

    private SOAPBody handleDiscoverRequest(SOAPBody body) {
        String requestType = null;
        PropertiesR properties = null;
        Node restriction = null;

        Iterator<Node> nodeIterator = body.getChildElements();
        while (nodeIterator.hasNext()) {

            Node node = nodeIterator.next();

            String name = node.getNodeName();
        }

        return discover(requestType, properties, restriction);

    }

    private PropertiesR properties(Node restriction) {
        PropertiesR properties = new PropertiesR(null, null, null, null, null);
        return properties;
    }

    private SOAPBody discover(String requestType, PropertiesR properties, Node restriction) {

        SOAPBody discoverResponse = null;

        switch (requestType) {
        case DISCOVER_PROPERTIES -> discoverResponse = handleDiscoverProperties(properties, restriction);
        default -> throw new IllegalArgumentException("Unexpected value: " + requestType);

        }
        return discoverResponse;
    }

    private SOAPBody handleDiscoverProperties(PropertiesR propertiesR, Node restrictionNode) {

        DiscoverPropertiesRestrictionsR restrictionsR = Convert.discoverPropertiesRestrictions(restrictionNode);
        DiscoverPropertiesRequest request = new DiscoverPropertiesRequestR(propertiesR, restrictionsR);
        List<DiscoverPropertiesResponseRow> rows = xmlaService.discoverProperties(request);
        SOAPBody responseWs = Convert.toDiscoverProperties(rows);

        return responseWs;
    }

    private static final String HTTP_SCHEMAS_XMLSOAP_ORG_SOAP_ENVELOPE = "http://schemas.xmlsoap.org/soap/envelope";

    
    public static HashMap<String, String> documentFromSoapBody(SOAPMessage soapMessage)
            throws ParserConfigurationException, SOAPException {
        SOAPEnvelope env = soapMessage.getSOAPPart()
                .getEnvelope();
        SOAPBodyElement body = (SOAPBodyElement) soapMessage.getSOAPBody()
                .getChildNodes()
                .item(0);
        return getNamespaceDeclarations(env, body);
    }

    private static HashMap<String, String> getNamespaceDeclarations(SOAPEnvelope env, SOAPBodyElement body) {
        HashMap<String, String> nsMap = new HashMap<String, String>();
        @SuppressWarnings("rawtypes")
        Iterator nsPrefixIterator = env.getNamespacePrefixes();
        // all ns declarations from <SOAP-ENV>
        while (nsPrefixIterator.hasNext()) {
            String prefix = (String) nsPrefixIterator.next();
            String nsUri = env.getNamespaceURI(prefix);
            // filter SOAP-ENV ns
            if (!nsUri.startsWith(HTTP_SCHEMAS_XMLSOAP_ORG_SOAP_ENVELOPE)) {
                nsMap.put(prefix, nsUri);
            }
        }
        // ns declarations from <SOAP-BODY>
        nsPrefixIterator = body.getNamespacePrefixes();
        while (nsPrefixIterator.hasNext()) {
            String prefix = (String) nsPrefixIterator.next();
            String nsUri = env.getNamespaceURI(prefix);
            nsMap.put(prefix, nsUri);
        }
        return nsMap;
    }
}
