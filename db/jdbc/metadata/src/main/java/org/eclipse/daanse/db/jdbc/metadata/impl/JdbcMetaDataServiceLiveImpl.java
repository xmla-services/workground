package org.eclipse.daanse.db.jdbc.metadata.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.eclipse.daanse.db.jdbc.metadata.api.JdbcMetaDataService;

public class JdbcMetaDataServiceLiveImpl implements JdbcMetaDataService {

    private DatabaseMetaData metadata;
    private String catalogName;

    public JdbcMetaDataServiceLiveImpl(Connection connection) throws SQLException {
        metadata = connection.getMetaData();
        catalogName = connection.getCatalog();
    }

    @Override
    public Optional<Integer> getColumnDataType(String schemaName, String tableName, String columnName)
            throws SQLException {
        try (ResultSet rs = metadata.getColumns(catalogName, schemaName, tableName, columnName);) {
            while (rs.next()) {
                return Optional.ofNullable(rs.getInt("DATA_TYPE"));
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean doesColumnExist(String schemaName, String tableName, String columnName) throws SQLException {
        try (ResultSet rs = metadata.getColumns(catalogName, schemaName, tableName, columnName);) {
            return rs.next();
        }
    }

    @Override
    public boolean doesTableExist(String schemaName, String tableName) throws SQLException {
        try (ResultSet rs = metadata.getTables(catalogName, schemaName, tableName, null);) {
            return rs.next();
        }
    }

    @Override
    public boolean doesSchemaExist(String schemaName) throws SQLException {
        try (ResultSet rs = metadata.getSchemas(catalogName, schemaName);) {
            return rs.next();
        }
    }

}
