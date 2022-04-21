package org.opencube.junit5.dbprovider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.sql.DataSource;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import aQute.bnd.annotation.spi.ServiceProvider;

//@ServiceProvider(value = DatabaseProvider.class)
public class SQLiteDatabaseProvider implements DatabaseProvider {

	private static String getTempFile() {
		try {
			Path temp = Files.createTempFile("database", ".sqlite.db");
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
	public DataSource activate() {

		SQLiteConfig cfg = new SQLiteConfig();
		SQLiteDataSource ds = new SQLiteDataSource(cfg);
		ds.setUrl(JDBC_SQLITE_MEMORY);

		return ds;
	}

	@Override
	public String getJdbcUrl() {
		return JDBC_SQLITE_MEMORY;
	}

}
