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

import java.util.Iterator;
import java.util.List;

import org.eclipse.daanse.xmla.api.XmlaService;
import org.eclipse.daanse.xmla.api.discover.discover.properties.DiscoverPropertiesRequest;
import org.eclipse.daanse.xmla.api.discover.discover.properties.DiscoverPropertiesResponseRow;
import org.eclipse.daanse.xmla.model.record.discover.PropertiesR;
import org.eclipse.daanse.xmla.model.record.discover.discover.properties.DiscoverPropertiesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.discover.properties.DiscoverPropertiesRestrictionsR;

import jakarta.xml.soap.Node;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPEnvelope;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;

public class XmlaApiAdapter {

    public XmlaService xmlaService;

    public XmlaApiAdapter(XmlaService xmlaService) {
        this.xmlaService = xmlaService;
    }

    private static final String DISCOVER_PROPERTIES = "DISCOVER_PROPERTIES";

    public SOAPMessage handleRequest(SOAPMessage message) {
        try {
            SOAPEnvelope envelope = message.getSOAPPart()
                    .getEnvelope();
            handleBody(message.getSOAPBody());
        } catch (SOAPException e) {
            e.printStackTrace();
        }

        return null;

    }

    private SOAPBody handleBody(SOAPBody body) {
        SOAPElement node = null;

        Iterator<Node> nodeIterator = body.getChildElements();
        while (nodeIterator.hasNext()) {
            Node nodeN = nodeIterator.next();
            if (nodeN instanceof SOAPElement) {
                node = (SOAPElement) nodeN;
                break;
            }
        }
        printNode(node);

        if (Constants.QNAME_MSXMLA_DISCOVER.equals(node.getElementQName())) {

            return discover(node);

        }
        if (Constants.QNAME_MSXMLA_EXECUTE.equals(node.getElementQName())) {

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
                    continue;
                }
            }
        }

        return discover(requestType, properties, restictions);
    }

    private void printNode(SOAPElement node) {
        System.out.println(node.getNamespaceURI());
        System.out.println(node.getBaseURI());
        System.out.println(node.getPrefix());
        System.out.println(node.getNodeName());
        System.out.println(node.getLocalName());
        System.out.println(node.getNodeValue());
        System.out.println(node.getTextContent());
        System.out.println(node.getValue());

        System.out.println(node.getElementQName());
    }

    private PropertiesR properties(SOAPElement propertiesElement) {
        PropertiesR properties = new PropertiesR();
        return properties;
    }

    private SOAPBody discover(String requestType, PropertiesR properties, SOAPElement restrictionElement) {

        SOAPBody discoverResponse = null;

        switch (requestType) {
        case DISCOVER_PROPERTIES -> discoverResponse = handleDiscoverProperties(properties, restrictionElement);
        default -> throw new IllegalArgumentException("Unexpected value: " + requestType);

        }
        return discoverResponse;
    }

    private SOAPBody handleDiscoverProperties(PropertiesR propertiesR, SOAPElement restrictionElement) {

        DiscoverPropertiesRestrictionsR restrictionsR = Convert.discoverPropertiesRestrictions(restrictionElement);
        DiscoverPropertiesRequest request = new DiscoverPropertiesRequestR(propertiesR, restrictionsR);
        List<DiscoverPropertiesResponseRow> rows = xmlaService.discover()
                .discoverProperties(request);
        SOAPBody responseWs = Convert.toDiscoverProperties(rows);

        return responseWs;
    }

}
