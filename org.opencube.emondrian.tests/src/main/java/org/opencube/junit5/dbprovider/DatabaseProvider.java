package org.opencube.junit5.dbprovider;

import java.io.Closeable;
import java.util.Map.Entry;

import javax.sql.DataSource;

public interface DatabaseProvider extends Closeable {

	public Entry<String, DataSource> activate();

	public String getJdbcUrl();

}
