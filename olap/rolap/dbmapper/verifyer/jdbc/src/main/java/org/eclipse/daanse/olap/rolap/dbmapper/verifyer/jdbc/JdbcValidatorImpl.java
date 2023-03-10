/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.olap.rolap.dbmapper.verifyer.jdbc;

import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.JdbcValidator;

import java.sql.Connection;
import java.util.List;

public class JdbcValidatorImpl implements JdbcValidator {

    private JdbcMetaData jdbcMetadata;

    public JdbcValidatorImpl(Connection connection) {
        this.jdbcMetadata = new JdbcMetaData(connection);
    }

    @Override
    public int getColumnDataType(String schemaName, String tableName, String colName) {
        return jdbcMetadata.getColumnDataType(schemaName, tableName, colName);
    }

    @Override
    public boolean isColExists(String schemaName, String tableName, String colName) {
        return jdbcMetadata.isColExists(schemaName, tableName, colName);
    }

    @Override
    public boolean isTableExists(String schemaName, String tableName) {
        return jdbcMetadata.isTableExists(schemaName, tableName);
    }

    @Override
    public boolean isInitialized() {
        return jdbcMetadata.getErrMsg() == null;
    }

    @Override
    public boolean isSchemaExists(String schemaName) {
        List<String> theSchemas = jdbcMetadata.getAllSchemas();
        return theSchemas != null && theSchemas.contains(schemaName);
    }
}
