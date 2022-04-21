package org.opencube.junit5.dbprovider;

import java.io.IOException;

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
	public DataSource activate()  {
		return null;
	}

}
