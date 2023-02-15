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
package org.eclipse.daanse.xmla.api.discover.dbschema.columns;

import org.eclipse.daanse.xmla.api.common.enums.ColumnOlapTypeEnum;

import java.util.Optional;

public interface DbSchemaColumnsRestrictions {
    String RESTRICTIONS_TABLE_CATALOG = "TABLE_CATALOG";
    String RESTRICTIONS_TABLE_SCHEMA = "TABLE_SCHEMA";
    String RESTRICTIONS_TABLE_NAME = "TABLE_NAME";
    String RESTRICTIONS_COLUMN_NAME = "COLUMN_NAME";
    String RESTRICTIONS_COLUMN_OLAP_TYPE = "COLUMN_OLAP_TYPE";

    /**
     *@return The name of the database.
     */
    Optional<String> tableCatalog();

    /**
     *@return The name of the schema.
     */
    Optional<String> tableSchema();

    /**
     *@return The name of the table.
     */
    Optional<String> tableName();

    /**
     *The name of the attribute hierarchy or
     *measure.
     */
    Optional<String> columnName();

    /**
     *@return The OLAP type of the object:
     *MEASURE indicates that the object is a
     *measure.
     *ATTRIBUTE indicates that the object is a
     *dimension attribute.
     *SCHEMA indicates that the object is a
     *column in a schema rowset table.
     */
    Optional<ColumnOlapTypeEnum> columnOlapType();
}
