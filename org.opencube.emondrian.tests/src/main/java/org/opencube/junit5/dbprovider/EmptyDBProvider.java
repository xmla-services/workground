package org.opencube.junit5.dbprovider;

import java.io.IOException;
import java.util.Map.Entry;

import javax.sql.DataSource;

public class EmptyDBProvider implements DatabaseProvider {

	@Override
	public void close() throws IOException {

	}

	@Override
	public String getJdbcUrl() {
		return "";
	}

	@Override
	public Entry<String, DataSource> activate() {
		return null;
	}

}
