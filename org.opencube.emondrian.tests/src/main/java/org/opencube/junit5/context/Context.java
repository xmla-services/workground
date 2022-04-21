package org.opencube.junit5.context;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.olap4j.OlapConnection;

import mondrian.olap.Connection;

public interface Context {
	
	public void init(DataSource dataSource);
	/**
	 * Returns the olap.Connection.
	 */
	Connection createConnection();
	OlapConnection createOlap4jConnection() throws SQLException;
	
	
//    OlapConnection getOlap4jConnection() throws SQLException;
}
