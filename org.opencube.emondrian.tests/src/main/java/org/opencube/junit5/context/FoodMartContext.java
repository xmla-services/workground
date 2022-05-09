package org.opencube.junit5.context;

import java.io.File;
import java.util.Optional;

import org.opencube.junit5.Constants;
import org.opencube.junit5.dataloader.DataLoader;
import org.opencube.junit5.dataloader.FastFoodmardDataLoader;
import org.opencube.junit5.dbprovider.MySqlDatabaseProvider;

public class FoodMartContext extends BaseTestContext implements LoadableContext {

	@Override
	protected
	String getCatalogContent() {
		try {
			return new String( new File(Constants.TESTFILES_DIR +"/catalogs/FoodMart.xml").toURI().toURL().openConnection().getInputStream().readAllBytes());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	@Override
	protected Optional<String> jdbcPassword() {
		return Optional.of(MySqlDatabaseProvider.MYSQL_PASSWORD);
	}

	@Override
	protected Optional<String> jdbcUser() {
		return Optional.of(MySqlDatabaseProvider.MYSQL_USER);
	}

	@Override
	public Class<? extends DataLoader> dataLoader() {
		return FastFoodmardDataLoader.class;
	}

}
