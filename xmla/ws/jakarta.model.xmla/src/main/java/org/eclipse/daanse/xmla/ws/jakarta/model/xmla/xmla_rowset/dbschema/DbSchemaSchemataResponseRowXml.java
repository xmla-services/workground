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
package org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_rowset.dbschema;

import jakarta.xml.bind.annotation.*;

/**
 * This schema rowset identifies the (base) data types supported by the server.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DbSchemaSchemataResponseRowXml")
public class DbSchemaSchemataResponseRowXml {

    @XmlTransient
    private final static long serialVersionUID = 5088358216257720851L;

    /**
     * @return Catalog name
     */
    @XmlElement(name = "CATALOG_NAME", required = false)
    private String catalogName;

    /**
     * Schema name
     */
    @XmlElement(name = "SCHEMA_NAME", required = false)
    private String schemaName;

    /**
     * @return Schema owner
     */
    @XmlElement(name = "SCHEMA_OWNER", required = false)
    private String schemaOwner;

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getSchemaOwner() {
        return schemaOwner;
    }

    public void setSchemaOwner(String schemaOwner) {
        this.schemaOwner = schemaOwner;
    }
}
