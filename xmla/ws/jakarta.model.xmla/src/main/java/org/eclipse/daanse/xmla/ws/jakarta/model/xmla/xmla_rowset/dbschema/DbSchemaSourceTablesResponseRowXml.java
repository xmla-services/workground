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
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.enums.TableTypeEnum;

import java.util.Optional;

/**
 * This schema rowset identifies the (base) data types supported by the server.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DbSchemaSourceTablesResponseRowXml")
public class DbSchemaSourceTablesResponseRowXml {

    @XmlTransient
    private final static long serialVersionUID = 4980356365829213910L;

    /**
     * @return Catalog name. NULL if the provider does not support
     * catalogs.
     */
    @XmlElement(name = "TABLE_CATALOG", required = false)
    private Optional<String> catalogName;

    /**
     * @return Unqualified schema name. NULL if the provider does not
     * support schemas.
     */
    @XmlElement(name = "TABLE_SCHEMA", required = false)
    private Optional<String> schemaName;

    /**
     * @return Table name
     */
    @XmlElement(name = "TABLE_NAME", required = false)
    private String tableName;

    /**
     * @return Table type. One of the following or a provider-specific
     * value: ALIAS, TABLE, SYNONYM, SYSTEM TABLE, VIEW, GLOBAL "
     * TEMPORARY, LOCAL TEMPORARY, EXTERNAL TABLE, SYSTEM VIEW
     */
    @XmlElement(name = "TABLE_TYPE", required = false)
    private TableTypeEnum tableType;

    public Optional<String> getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(Optional<String> catalogName) {
        this.catalogName = catalogName;
    }

    public Optional<String> getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(Optional<String> schemaName) {
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public TableTypeEnum getTableType() {
        return tableType;
    }

    public void setTableType(TableTypeEnum tableType) {
        this.tableType = tableType;
    }
}
