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
package org.eclipse.daanse.xmla.api.discover.dbschema.tables;

import java.util.Optional;

public interface DbSchemaTablesRestrictions {
    String RESTRICTIONS_TABLE_CATALOG = "TABLE_CATALOG";
    String RESTRICTIONS_TABLE_SCHEMA = "TABLE_SCHEMA";
    String RESTRICTIONS_TABLE_NAME = "TABLE_NAME";
    String RESTRICTIONS_TABLE_TYPE = "TABLE_TYPE";

    /**
     * @return The name of the database.
     */
    Optional<String> tableCatalog();

    /**
     * @return The name of the schema.
     */
    Optional<String> tableSchema();

    /**
     * @return The name of the table.
     */
    Optional<String> tableName();

    /**
     * @return The type of table:
     * TABLE for measure group.
     * SYSTEM TABLE for dimension.
     * SCHEMA for schema rowset table.
     */
    Optional<String> tableType();

}
