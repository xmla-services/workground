package org.opencube.junit5.dbprovider;

import java.io.Closeable;
import java.util.Map.Entry;

import javax.sql.DataSource;

import mondrian.olap.Util.PropertyList;

public interface DatabaseProvider extends Closeable {

	public Entry<PropertyList, DataSource> activate();

	public String getJdbcUrl();

}
