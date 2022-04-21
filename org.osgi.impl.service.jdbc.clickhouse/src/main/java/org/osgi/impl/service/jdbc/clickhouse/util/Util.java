package org.osgi.impl.service.jdbc.clickhouse.util;

import java.sql.SQLException;
import java.util.Properties;

import org.osgi.impl.service.jdbc.clickhouse.datasource.ClickHouseConfig;

public class Util {

	public static Properties transformConfig(ClickHouseConfig config) throws SQLException {
		Properties properties = new Properties();

		if (config.username() != null) {
			properties.put("usernmame", config.username());

		}
		return properties;
	}
}
