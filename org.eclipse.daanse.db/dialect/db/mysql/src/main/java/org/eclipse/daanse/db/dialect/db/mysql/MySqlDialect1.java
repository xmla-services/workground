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
import org.eclipse.daanse.db.dialect.db.common.factory.JdbcDialectFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aQute.bnd.annotation.spi.ServiceProvider;

@ServiceProvider(value = Dialect.class)
@Component(service = Dialect.class, scope = ServiceScope.SINGLETON)
public class MySqlDialect1 extends AbstractMySqlDialect {

    private Logger LOGGER = LoggerFactory.getLogger(MySqlDialect1.class);
    public static final JdbcDialectFactory FACTORY =
        new JdbcDialectFactory(
            MySqlDialect1.class)
        {
            @Override
            protected boolean acceptsConnection(Connection connection) {
                try {
                    // Infobright looks a lot like MySQL. If this is an
                    // Infobright connection, yield to the Infobright dialect.
                    return super.acceptsConnection(connection)
                        && !isInfobright(connection.getMetaData());
                } catch (SQLException e) {
                    throw new RuntimeException(
                        "Error while instantiating dialect", e);
                }
            }
        };

    public MySqlDialect1() {
    }

    public MySqlDialect1(Connection connection) throws SQLException {
        super(connection);
    }

    @Override
    public boolean allowsFromQuery() {

        return false;
    }

    @Override
    public boolean requiresOrderByAlias() {
        return false;
    }

    @Override
    public boolean isCompatible(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData meta = connection.getMetaData();

            if (compatibleProduct(meta)) {
                String productVersion = deduceProductVersion(meta);
                if (productVersion.compareTo("4.") < 0) {
                    return true;
                }
            }

        } catch (SQLException e) {
            LOGGER.warn("", e);
        }
        return false;
    }
}
