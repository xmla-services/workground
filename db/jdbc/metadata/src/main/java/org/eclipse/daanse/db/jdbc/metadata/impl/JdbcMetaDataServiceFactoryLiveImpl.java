package org.eclipse.daanse.db.jdbc.metadata.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.eclipse.daanse.db.jdbc.metadata.api.JdbcMetaDataService;
import org.eclipse.daanse.db.jdbc.metadata.api.JdbcMetaDataServiceFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

@Component(service = JdbcMetaDataServiceFactory.class, scope = ServiceScope.SINGLETON)

public class JdbcMetaDataServiceFactoryLiveImpl implements JdbcMetaDataServiceFactory {

    @Override
    public JdbcMetaDataService create(Connection connection) throws SQLException {
        return new JdbcMetaDataServiceLiveImpl(connection);
    }

}
