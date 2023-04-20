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
*   SmartCity Jena - initial
*   Stefan Bischof (bipolis.org) - initial
*/
package org.eclipse.daanse.db.datasource.mysql;

import com.mysql.cj.jdbc.MysqlDataSource;

public class ConfigUtil {

    private ConfigUtil() {
        // constructor
    }

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
