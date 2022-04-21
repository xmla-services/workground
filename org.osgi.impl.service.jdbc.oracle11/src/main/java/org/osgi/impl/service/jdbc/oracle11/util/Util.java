package org.osgi.impl.service.jdbc.oracle11.util;

import java.sql.SQLException;
import java.util.Properties;

import org.osgi.impl.service.jdbc.oracle11.datasource.Oracle11Config;

public class Util {

	public static Properties transformConfig(Oracle11Config config) throws SQLException {
		Properties properties = new Properties();

		if (config.username() != null) {
			properties.put("usernmame", config.username());

		}
		return properties;
	}
}
