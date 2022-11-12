package org.opencube.junit5.dbprovider;

import java.io.IOException;
import java.util.Map.Entry;

import javax.sql.DataSource;

import mondrian.olap.Util.PropertyList;

public class EmptyDBProvider implements DatabaseProvider {

	@Override
	public void close() throws IOException {

	}

	@Override
	public String getJdbcUrl() {
		return "";
	}

	@Override
	public Entry<PropertyList, DataSource> activate() {
		return null;
	}

}
