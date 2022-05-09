package org.opencube.junit5.context;

import java.io.File;
import java.util.Optional;

import org.opencube.junit5.Constants;
import org.opencube.junit5.dbprovider.MySqlDatabaseProvider;

public class FoodMartCachesExistsTestsAlternateSalesContext extends BaseTestContext {


	@Override
	protected
	String getCatalogContent() {
		try {
			return new String( new File(Constants.TESTFILES_DIR +"/catalogs/FoodMart_CachedExistsTest_Alternate_Sales.xml").toURI().toURL().openConnection().getInputStream().readAllBytes());
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
	protected	Optional<String> jdbcUser() {
		return Optional.of(MySqlDatabaseProvider.MYSQL_USER);
	}

	


}
