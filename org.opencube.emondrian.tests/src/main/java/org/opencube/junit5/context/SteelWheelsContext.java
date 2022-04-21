package org.opencube.junit5.context;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.opencube.junit5.Constants;

public class SteelWheelsContext extends AbstractContext {

	@Override
	URL catalog() {
		try {
			return new File(Constants.PROJECT_DIR+"SteelWheels.xml").toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}



}
