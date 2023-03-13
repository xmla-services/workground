package org.eclipse.daanse.db.jdbc.metadata.api;

public interface JdbcMetaDataService {

    public int getColumnDataType(String schemaName, String tableName, String columnName);

    public boolean doesColumnExist(String schemaName, String tableName, String columnName);

    public boolean doesTableExist(String schemaName, String tableName);

    public boolean doesSchemaExist(String schemaName);
}
