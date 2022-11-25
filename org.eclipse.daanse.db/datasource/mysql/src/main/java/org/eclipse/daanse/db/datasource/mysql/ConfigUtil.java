package org.eclipse.daanse.db.datasource.mysql;

import com.mysql.cj.jdbc.MysqlDataSource;

public class ConfigUtil {

    public static void configBase(MysqlDataSource ds, ConfigBase configuration) {
        if (configuration.username() != null) {
            ds.setUser(configuration.username());
        }
        if (configuration._password() != null) {
            ds.setPassword(configuration._password());
        }
        if (configuration.serverName() != null) {
            ds.setServerName(configuration.serverName());
        }
        if (configuration.databaseName() != null) {
            ds.setDatabaseName(configuration.databaseName());
        }
        if (configuration.port() != null) {
            ds.setPort(configuration.port());
        }
    }

}
