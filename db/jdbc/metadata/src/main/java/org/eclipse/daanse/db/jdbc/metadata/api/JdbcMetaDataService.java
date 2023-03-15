package org.eclipse.daanse.db.jdbc.metadata.api;

import java.sql.SQLException;
import java.util.Optional;

public interface JdbcMetaDataService {

    public Optional<Integer> getColumnDataType(String schemaName, String tableName, String columnName)
            throws SQLException;

    public boolean doesColumnExist(String schemaName, String tableName, String columnName) throws SQLException;

    public boolean doesTableExist(String schemaName, String tableName) throws SQLException;

    public boolean doesSchemaExist(String schemaName) throws SQLException;
}
