package org.eclipse.daanse.db.jdbc.metadata.api;

import java.sql.Connection;

public interface JdbcMetaDataServiceFactory {

    JdbcMetaDataService create(Connection connection);
}
