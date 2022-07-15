package org.opencube.junit5.context;

import java.sql.SQLException;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.olap4j.OlapConnection;
import org.olap4j.OlapWrapper;
import org.opencube.junit5.propupdator.PropertyUpdater;

import mondrian.olap.Connection;
import mondrian.olap.DriverManager;
import mondrian.olap.Util;
import mondrian.olap.Util.PropertyList;
import mondrian.olap4j.MondrianOlap4jDriver;
import mondrian.rolap.RolapConnectionProperties;

public class BaseTestContext implements Context {

	private DataSource dataSource;
	private Util.PropertyList properties = new Util.PropertyList();

	@Override
	public void init(Entry<PropertyList, DataSource> dataSource) {
		this.dataSource = dataSource.getValue();
		this.properties = dataSource.getKey().clone();
		init();

	}

	public void update(PropertyUpdater updater) {
		properties = updater.update(properties);
	}

	private void init() {

		properties.put(RolapConnectionProperties.Provider.name(), provider());

//		olapConnectString = "jdbc:mondrian:" + olapConnectString;
	}

	@Override
	public Connection createConnection() {

		if (dataSource == null) {
			return DriverManager.getConnection(getJDBCConnectString(), null);
		}
		return DriverManager.getConnection(properties, null, dataSource);

	}

	protected String getCatalogContent() {
		return "";
	}

	protected String provider() {
		return "mondrian";
	}

	/*
	 * must be done before getConection;
	 */
	public void setProperty(String key, boolean value) {

		properties.put(key, value ? "true" : "false");

	}

	@Override
	public void setProperty(String key, String value) {
		properties.put(key, value);
	}
	
	@Override
	public OlapConnection createOlap4jConnection() throws SQLException {

		MondrianOlap4jDriver d = new MondrianOlap4jDriver();

		final java.sql.Connection connection = java.sql.DriverManager.getConnection("jdbc:mondrian:" + getOlapConnectString());
		return ((OlapWrapper) connection).unwrap(OlapConnection.class);
	}

	public String getJDBCConnectString() {

		// may exist better ways
		return properties.get(RolapConnectionProperties.Jdbc.name());

	}

	@Override
	public String getOlapConnectString() {
		 
		return properties.toString();
	}

	@Override
	public String toString() {

		return getJDBCConnectString();

//		
//		Connection con = createConnection();
//		if (con == null||dataSource==null) {
//			return super.toString();
//		}
//		try {
//			return jdbcURL().orElse(		dataSource.getConnection().getMetaData().getDriverName());
//		} catch (SQLException e) {
//		return	super.toString();
//		}

	}

}
