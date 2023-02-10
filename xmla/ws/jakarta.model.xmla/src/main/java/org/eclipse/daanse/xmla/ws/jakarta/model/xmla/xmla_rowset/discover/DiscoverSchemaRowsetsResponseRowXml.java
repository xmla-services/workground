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
package org.eclipse.daanse.xmla.api.discover.discover.schemarowsets;

import jakarta.xml.bind.annotation.*;

import java.util.Optional;

/**
 * This schema rowset returns the names, restrictions, description, and other information for all
 * Discover requests.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DiscoverSchemaRowsetsResponseRowXml")
public class DiscoverSchemaRowsetsResponseRowXml {

    @XmlTransient
    private final static long serialVersionUID = 8000363696884603878L;

    @XmlElement(name = "SchemaName", required = true)
    private String schemaName;

    @XmlElement(name = "SchemaGuid")
    private String schemaGuid;

    @XmlElement(name = "Restrictions")
    private String restrictions;

    @XmlElement(name = "Description")
    private String description;

    @XmlElement(name = "RestrictionsMask")
    private Long restrictionsMask;
}
