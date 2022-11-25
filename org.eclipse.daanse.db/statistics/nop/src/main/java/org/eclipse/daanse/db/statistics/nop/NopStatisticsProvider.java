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
package org.eclipse.daanse.db.statistics.nop;

import javax.sql.DataSource;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.statistics.api.StatisticsProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * Implementation of {@link StatisticsProvider} that always return -1
 * ({@link StatisticsProvider.CARDINALITY_UNKNOWN}).
 */
@Component(service = StatisticsProvider.class, scope = ServiceScope.SINGLETON)
public class NopStatisticsProvider implements StatisticsProvider {

    @Override
    public void initialize(DataSource dataSource, Dialect dialect) {

    }

    @Override
    public long getTableCardinality(String catalog, String schema, String table) {

        return CARDINALITY_UNKNOWN;
    }

    @Override
    public long getQueryCardinality(String sql) {
        return CARDINALITY_UNKNOWN;
    }

    @Override
    public long getColumnCardinality(String catalog, String schema, String table, String column) {
        return CARDINALITY_UNKNOWN;
    }

}
