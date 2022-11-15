package org.eclipse.daanse.engine.api;

import java.sql.Connection;

import javax.sql.DataSource;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.statistics.api.StatisticsProvider;

/**
 * The Context gives access to all resources and configurations that are needed
 * to calculate and Data Cubes
 * 
 * @author stbischof
 *
 */
public interface Context {

    /**
     * Gives access to the {@link DataSource} that holds the {@link Connection}s to
     * the Database.
     * 
     * @return DataSource
     */
    public DataSource getDataSource();

    /**
     * Gives access to the {@link Dialect} that must be used to generate SQL querys
     * against the {@link DataSource}.
     * 
     * @return DataSource
     */
    public Dialect getDialect();

    /**
     * Gives access to a {@link StatisticsProvider} that is initialised with the
     * {@link DataSource} of this {@link Context}.
     * 
     * @return StatisticsProvider
     */
    StatisticsProvider getStatisticsProvider();

}
