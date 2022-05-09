package org.opencube.junit5.context;

import java.io.File;

import org.opencube.junit5.Constants;

public class SteelWheelsContext extends BaseTestContext {

	@Override
	protected String getCatalogContent() {
		try {
			return new String(new File(Constants.TESTFILES_DIR + "/catalogs/SteelWheels.xml").toURI().toURL()
					.openConnection().getInputStream().readAllBytes());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
