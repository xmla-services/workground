package org.opencube.junit5.context;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.statistics.api.StatisticsProvider;
import org.eclipse.daanse.olap.calc.api.compiler.ExpressionCompilerFactory;
import org.eclipse.daanse.olap.calc.base.compiler.BaseExpressionCompilerFactory;
import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.SchemaImpl;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.api.DatabaseMappingSchemaProvider;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.sample.foodmart.record.FoodMartRecordDbMappingSchemaProvider;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public abstract class AbstractTestContext implements  TestContext {

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

	private ExpressionCompilerFactory expressionCompilerFactory = new BaseExpressionCompilerFactory();
	private List<DatabaseMappingSchemaProvider> databaseMappingSchemaProviders;



	@Override
	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}
	
	@Override
	public void setDatabaseMappingSchemaProviders(List<DatabaseMappingSchemaProvider> databaseMappingSchemaProviders){
		this.databaseMappingSchemaProviders=databaseMappingSchemaProviders;
	}
	
	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource=dataSource;
		
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
		databaseMappingSchemaProviders = List.of(new FoodMartRecordDbMappingSchemaProvider());
		
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
		// TODO Auto-generated method stub
		return null;
	}


}
