package org.eclipse.daanse.engine.impl;

import java.util.Map;

import javax.sql.DataSource;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.api.DialectResolver;
import org.eclipse.daanse.db.statistics.api.StatisticsProvider;
import org.eclipse.daanse.engine.api.Context;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ServiceScope;

@Component(service = Context.class, scope = ServiceScope.SINGLETON)
public class ContextImpl implements Context {

    private DataSource dataSource = null;

    private DialectResolver dialectResolver = null;

    private StatisticsProvider statisticsProvider;

    @Reference(policy = ReferencePolicy.DYNAMIC, cardinality = ReferenceCardinality.MANDATORY)
    public void bindDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void unbindDataSource(DataSource dataSource) {
        this.dataSource = null;
    }

    @Reference(policy = ReferencePolicy.DYNAMIC, cardinality = ReferenceCardinality.MANDATORY)
    public void bindDialectResolver(DialectResolver dialectResolver) {
        this.dialectResolver = dialectResolver;
    }

    public void unbindDialectResolver(DialectResolver dialectResolver) {
        this.dialectResolver = null;
    }

    @Reference(policy = ReferencePolicy.DYNAMIC, cardinality = ReferenceCardinality.MANDATORY)
    public void bindStatisticsProvider(StatisticsProvider statisticsProvider) {
        this.statisticsProvider = statisticsProvider;

    }

    public void unbindStatisticsProvider(StatisticsProvider statisticsProvider) {
        this.statisticsProvider = null;
    }

    @Activate
    public void activate(Map<String, Object> coniguration) {
        statisticsProvider.init(dataSource, getDialect());
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public Dialect getDialect() {
        return dialectResolver.resolve(getDataSource());
    }

    @Override
    public StatisticsProvider getStatisticsProvider() {
        return statisticsProvider;
    }

}
