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
import java.util.Optional;

import org.eclipse.daanse.xmla.api.common.properties.Content;
import org.eclipse.daanse.xmla.api.common.properties.Format;
import org.eclipse.daanse.xmla.api.discover.discover.properties.DiscoverPropertiesResponseRow;
import org.eclipse.daanse.xmla.model.record.discover.PropertiesR;
import org.eclipse.daanse.xmla.model.record.discover.discover.properties.DiscoverPropertiesRestrictionsR;

import jakarta.xml.soap.Node;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPElement;

public class Convert {

    public static DiscoverPropertiesRestrictionsR discoverPropertiesRestrictions(SOAPElement restriction) {
        // TODO Auto-generated method stub
        return null;
    }

    public static PropertiesR toProperties(SOAPElement properties) {
        Integer localeIdentifier;
        String dataSourceInfo;
        Content content;
        Format format;
        String catalog;
        Iterator<Node> nodeIterator = properties.getChildElements();
        while (nodeIterator.hasNext()) {
            Node node = nodeIterator.next();
            if (node instanceof SOAPElement) {
                SOAPElement element = (SOAPElement) node;

            }
        }
        return new PropertiesR(Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null),
                Optional.ofNullable(null), Optional.ofNullable(null));
    }

    public static SOAPBody toDiscoverProperties(List<DiscoverPropertiesResponseRow> rows) {
        // TODO Auto-generated method stub
        return null;
    }

}
