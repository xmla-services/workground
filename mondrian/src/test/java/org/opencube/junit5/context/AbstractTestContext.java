package org.opencube.junit5.context;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.statistics.api.StatisticsProvider;
import org.eclipse.daanse.olap.api.Context;
import org.eclipse.daanse.olap.calc.api.compiler.ExpressionCompilerFactory;
import org.eclipse.daanse.olap.calc.base.compiler.BetterExpressionCompilerFactory;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.api.DatabaseMappingSchemaProvider;

public abstract class AbstractTestContext implements Context {

	private Dialect dialect;
	private StatisticsProvider statisticsProvider = new StatisticsProvider() {
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
	};
	private DataSource dataSource;

	private ExpressionCompilerFactory expressionCompilerFactory = new BetterExpressionCompilerFactory();

	public AbstractTestContext(DataSource dataSource) {
		this.dataSource = dataSource;
		try (Connection connection = dataSource.getConnection()) {
			dialect = createDialect(connection);
		} catch (SQLException e) {
			new RuntimeException(e);
		}

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
		return Optional.empty();
	}

	@Override
	public List<DatabaseMappingSchemaProvider> getDatabaseMappingSchemaProviders() {
		return null;
	}

	@Override
	public ExpressionCompilerFactory getExpressionCompilerFactory() {
		return expressionCompilerFactory;
	}

	abstract Dialect createDialect(Connection connection);

}
