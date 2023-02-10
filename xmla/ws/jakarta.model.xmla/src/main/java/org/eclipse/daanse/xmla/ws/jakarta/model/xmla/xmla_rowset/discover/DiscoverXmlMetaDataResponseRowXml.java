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
package org.eclipse.daanse.xmla.api.discover.discover.xmlmetadata;

import jakarta.xml.bind.annotation.*;

/**
 * This schema rowset returns a rowset with one row and one column. The single cell in the rowset
 * contains an XML document that contains the requested XML metadata.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DiscoverXmlMetaDataResponseRowXml")
public class DiscoverXmlMetaDataResponseRowXml {

    @XmlTransient
    private final static long serialVersionUID = 5024203686449768474L;

    @XmlElement(name = "MetaData", required = true)
    private String metaData;
}
