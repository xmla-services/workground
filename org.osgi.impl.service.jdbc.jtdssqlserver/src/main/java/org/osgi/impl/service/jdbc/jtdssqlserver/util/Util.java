package org.osgi.impl.service.jdbc.jtdssqlserver.util;

import java.sql.SQLException;
import java.util.Properties;

import org.osgi.impl.service.jdbc.jtdssqlserver.datasource.JtdsConfig;

public class Util {

	public static Properties transformConfig(JtdsConfig config) throws SQLException {
		Properties properties = new Properties();

		if (config.username() != null) {
			properties.put("usernmame", config.username());

		}
		return properties;
	}
}
