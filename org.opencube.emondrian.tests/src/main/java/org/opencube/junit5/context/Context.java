package org.opencube.junit5.context;

import java.sql.SQLException;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.olap4j.OlapConnection;

import mondrian.olap.Connection;
import mondrian.olap.Util.PropertyList;

public interface Context {

	public void init(Entry<PropertyList, DataSource> dataSource);

	/**
	 * Returns the olap.Connection.
	 */
	Connection createConnection();

	OlapConnection createOlap4jConnection() throws SQLException;

	public String getJDBCConnectString();

	String getOlapConnectString();
	
	void setProperty(String key, String value);

}
