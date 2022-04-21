package org.osgi.impl.service.jdbc.mssqlserver.util;

import java.sql.SQLException;
import java.util.Properties;

import org.osgi.impl.service.jdbc.mssqlserver.datasource.MsSqlConfig;

public class Util {

	public static Properties transformConfig(MsSqlConfig config) throws SQLException {
		Properties properties = new Properties();

		if (config.username() != null) {
			properties.put("usernmame", config.username());

		}
		return properties;
	}
}
