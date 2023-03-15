package org.eclipse.daanse.db.jdbc.metadata.api;

import java.sql.Connection;
import java.sql.SQLException;

public interface JdbcMetaDataServiceFactory {

    JdbcMetaDataService create(Connection connection) throws SQLException;
}
