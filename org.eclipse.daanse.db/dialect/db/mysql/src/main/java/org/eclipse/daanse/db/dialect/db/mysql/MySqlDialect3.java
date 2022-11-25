/*********************************************************************
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
**********************************************************************/
package org.eclipse.daanse.db.dialect.db.mysql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aQute.bnd.annotation.spi.ServiceProvider;

@ServiceProvider(value = Dialect.class)
@Component(service = Dialect.class, scope = ServiceScope.SINGLETON)
public class MySqlDialect3 extends AbstractMySqlDialect {

    private Logger LOGGER = LoggerFactory.getLogger(MySqlDialect3.class);

    public MySqlDialect3() {
    }

    public MySqlDialect3(Connection connection) throws SQLException {
        super(connection);
    }

    @Override
    public boolean allowsFromQuery() {

        return true;
    }

    /**
     * Required for MySQL 5.7+, where SQL_MODE include ONLY_FULL_GROUP_BY by
     * default. This prevent expressions like
     *
     * ISNULL(RTRIM(`promotion_name`)) ASC
     *
     * from being used in ORDER BY section.
     *
     * ISNULL(`c0`) ASC
     *
     * will be used, where `c0` is an alias of the RTRIM(`promotion_name`). And this
     * is important for the cases where we're using SQL expressions in a Level
     * definition.
     *
     * Jira ticket, that describes the issue:
     * http://jira.pentaho.com/browse/MONDRIAN-2451
     *
     * @return true when MySQL version is 5.7 or larger
     */
    @Override
    public boolean requiresOrderByAlias() {
        return true;
    }

    @Override
    public boolean isCompatible(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData meta = connection.getMetaData();
            String productVersion = deduceProductVersion(meta);
            if (compatibleProduct(meta)) {

                if (productVersion.compareTo("5.7") >= 0) {
                    return true;
                }
            }

        } catch (SQLException e) {
            LOGGER.warn("", e);
        }
        return false;
    }
}
