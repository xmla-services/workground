package org.opencube.junit5.context;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.opencube.junit5.Constants;
import org.opencube.junit5.dbprovider.MySqlDatabaseProvider;

public class FoodMartCachesExistsTestsAlternateSalesContext extends AbstractContext {


	@Override
	URL catalog() {
		try {
			return new File(Constants.TESTFILES_DIR +"/catalogs/FoodMart_CachedExistsTest_Alternate_Sales.xml").toURI().toURL();
		} catch (MalformedURLException e) {
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
