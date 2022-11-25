/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
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
