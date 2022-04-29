package org.opencube.junit5.dbprovider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import mondrian.olap.Util.PropertyList;
import mondrian.rolap.RolapConnectionProperties;

//@ServiceProvider(value = DatabaseProvider.class)
public class SQLiteDatabaseProvider implements DatabaseProvider {

	private static String getTempFile() {
		try {
			Path temp = Path.of("foodsqlite.db");
			if (Files.exists(temp)) {
				Files.delete(temp);
			}
			return temp.toFile().getAbsolutePath().toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	private static final String JDBC_SQLITE_MEMORY = "jdbc:sqlite:" + getTempFile();

	@Override
	public void close() throws IOException {

	}

	@Override
	public Entry<PropertyList, DataSource> activate() {

		SQLiteConfig cfg = new SQLiteConfig();
		SQLiteDataSource ds = new SQLiteDataSource(cfg);
		ds.setUrl(JDBC_SQLITE_MEMORY);

		PropertyList connectProperties = new PropertyList();
		connectProperties.put(RolapConnectionProperties.Jdbc.name(), JDBC_SQLITE_MEMORY);
//		connectProperties.put(RolapConnectionProperties.JdbcUser.name(),MYSQL_USER);
//		connectProperties.put(RolapConnectionProperties.JdbcPassword.name(), MYSQL_PASSWORD);
		return new AbstractMap.SimpleEntry<PropertyList, DataSource>(connectProperties, ds);
	}

	@Override
	public String getJdbcUrl() {
		return JDBC_SQLITE_MEMORY;
	}

}
