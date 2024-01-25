package org.opencube.junit5.context;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import mondrian.rolap.RolapConnectionProps;
import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.statistics.api.StatisticsProvider;
import org.eclipse.daanse.olap.api.result.Scenario;
import org.eclipse.daanse.olap.calc.api.compiler.ExpressionCompilerFactory;
import org.eclipse.daanse.olap.calc.base.compiler.BaseExpressionCompilerFactory;
import org.eclipse.daanse.olap.core.AbstractBasicContext;
import org.eclipse.daanse.olap.core.BasicContextConfig;
import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.SchemaImpl;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.api.DatabaseMappingSchemaProvider;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import mondrian.rolap.RolapConnection;
import mondrian.rolap.RolapConnectionPropsR;
import mondrian.rolap.RolapResultShepherd;
import mondrian.rolap.agg.AggregationManager;

public class TestContextImpl extends AbstractBasicContext implements TestContext {

	private Dialect dialect;
	private StatisticsProvider statisticsProvider = new NullStatisticsProvider();
	private DataSource dataSource;

	private ExpressionCompilerFactory expressionCompilerFactory = new BaseExpressionCompilerFactory();
	private List<DatabaseMappingSchemaProvider> databaseMappingSchemaProviders;
	private String name;
	private Optional<String> description = Optional.empty();
    private TestConfig testConfig;

	public TestContextImpl() {
        testConfig = new TestConfig();
	    shepherd = new RolapResultShepherd(testConfig.rolapConnectionShepherdThreadPollingInterval(),
            testConfig.rolapConnectionShepherdNbThreads());
	    aggMgr = new AggregationManager(this);
	}

	@Override
	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

	@Override
	public void setDatabaseMappingSchemaProviders(List<DatabaseMappingSchemaProvider> databaseMappingSchemaProviders) {
		this.databaseMappingSchemaProviders = databaseMappingSchemaProviders;
	}

	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;

	}

	@Override
	public DataSource getDataSource() {
		return dataSource;
	}

	@Override
	public Dialect getDialect() {
		return dialect;
	}

	@Override
	public StatisticsProvider getStatisticsProvider() {
		return statisticsProvider;
	}

	@Override
	public Optional<String> getDescription() {
		return description;
	}

	@Override
	public List<DatabaseMappingSchemaProvider> getDatabaseMappingSchemaProviders() {
		return databaseMappingSchemaProviders;
	}

	public SchemaImpl read(InputStream inputStream) throws JAXBException {

		JAXBContext jaxbContext = JAXBContext.newInstance(SchemaImpl.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (SchemaImpl) jaxbUnmarshaller.unmarshal(inputStream);

	}

	@Override
	public ExpressionCompilerFactory getExpressionCompilerFactory() {
		return expressionCompilerFactory;
	}

	@Override
	public org.eclipse.daanse.olap.api.Connection getConnection() {
		return getConnection(new RolapConnectionPropsR());
	}

    @Override
    public org.eclipse.daanse.olap.api.Connection getConnection(RolapConnectionProps props) {
        return new RolapConnection(this, props);
    }

    @Override
    public org.eclipse.daanse.olap.api.Connection getConnection(List<String> roles) {
        return getConnection(new RolapConnectionPropsR(roles,
                true, Locale.getDefault(),
                -1, TimeUnit.SECONDS, Optional.empty(), Optional.empty()));
    }

    @Override
    public Scenario createScenario() {
        return null;
    }

    @Override
    public BasicContextConfig getConfig() {
        return testConfig;
    }

    @Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setDescription(Optional<String> description) {
		this.description = description;
	}

	@Override
	public void setExpressionCompilerFactory(ExpressionCompilerFactory expressionCompilerFactory) {
		this.expressionCompilerFactory = expressionCompilerFactory;
	}

	@Override
	public void setStatisticsProvider(StatisticsProvider statisticsProvider) {
		this.statisticsProvider = statisticsProvider;
	}

	private final class NullStatisticsProvider implements StatisticsProvider {
		@Override
		public void initialize(DataSource dataSource, Dialect dialect) {

		}

		@Override
		public long getTableCardinality(String catalog, String schema, String table) {
			return 0;
		}

		@Override
		public long getQueryCardinality(String sql) {
			return 0;
		}

		@Override
		public long getColumnCardinality(String catalog, String schema, String table, String column) {
			return 0;
		}
	}

}
