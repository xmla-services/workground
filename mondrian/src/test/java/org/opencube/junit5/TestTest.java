package org.opencube.junit5;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.junit.jupiter.params.ParameterizedTest;
import org.opencube.junit5.context.Context;
import org.opencube.junit5.dataloader.DataLoader;
import org.opencube.junit5.dbprovider.DatabaseProvider;
import org.opencube.junit5.propupdator.AppandFoodMartCatalogAsFile;
import org.sqlite.SQLiteDataSource;

import mondrian.olap.Util.PropertyList;

public class TestTest {

	public static class ExampleDatabaseProvider implements DatabaseProvider {

		@Override
		public void close() throws IOException {

		}

		@Override
		public Entry<PropertyList, DataSource> activate() {
			PropertyList props = new PropertyList();
			props.put("x", "y");
			return new AbstractMap.SimpleEntry<PropertyList, DataSource>(props, new SQLiteDataSource());
		}

		@Override
		public String getJdbcUrl() {
			return "jdbc:example";
		}

	}

	public static class ExampleDataLoader implements DataLoader {

		public ExampleDataLoader() {
		}
		@Override
		public boolean loadData(DataSource dataSource) throws Exception {
			return true;
		}

	}

	@ParameterizedTest
	@ContextSource(database = ExampleDatabaseProvider.class, dataloader = ExampleDataLoader.class,propertyUpdater = AppandFoodMartCatalogAsFile.class)
	protected void runTest(Context context) {
		System.out.println(context);
	}
}
