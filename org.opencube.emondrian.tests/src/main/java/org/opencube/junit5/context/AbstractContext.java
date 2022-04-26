package org.opencube.junit5.context;

import java.net.URL;
import java.sql.SQLException;
import java.util.Map.Entry;
import java.util.Optional;

import javax.sql.DataSource;

import org.olap4j.OlapConnection;
import org.olap4j.OlapWrapper;

import mondrian.olap.Connection;
import mondrian.olap.DriverManager;
import mondrian.olap.Util;
import mondrian.olap4j.MondrianOlap4jDriver;
import mondrian.rolap.RolapConnectionProperties;

public abstract class AbstractContext implements Context {

	private DataSource dataSource;
	private Util.PropertyList connectProperties = new Util.PropertyList();
	private String connectionString;
	private String olapConnectString;

	@Override
	public void init(Entry<String, DataSource> dataSource) {
		this.dataSource = dataSource.getValue();
		this.connectionString = dataSource.getKey();
		init();

	}

	private void init() {
		connectProperties = new Util.PropertyList();

		connectProperties.put(RolapConnectionProperties.Provider.name(), provider());
		jdbcURL().ifPresent(v -> connectProperties.put(RolapConnectionProperties.Jdbc.name(), v));
		jdbcUser().ifPresent(v -> connectProperties.put(RolapConnectionProperties.JdbcUser.name(), v));
		jdbcPassword().ifPresent(v -> connectProperties.put(RolapConnectionProperties.JdbcPassword.name(), v));
		connectProperties.put(RolapConnectionProperties.Catalog.name(), catalog().toString());

		olapConnectString = getJDBCConnectString();
		if (olapConnectString.startsWith("Provider=mondrian; ")) {
			olapConnectString = olapConnectString.substring("Provider=mondrian; ".length());
		}
		olapConnectString = "jdbc:mondrian:" + olapConnectString;
	}

	@Override
	public Connection createConnection() {

		if (dataSource == null) {
			return DriverManager.getConnection(connectionString, null);
		}
		return DriverManager.getConnection(connectProperties, null, dataSource);

	}

	abstract URL catalog();

	protected String provider() {
		return "mondrian";
	}

	protected Optional<String> jdbcPassword() {
		return Optional.empty();
	}

	protected Optional<String> jdbcUser() {
		return Optional.empty();
	}

	protected Optional<String> jdbcURL() {
		return Optional.empty();
	}

	/*
	 * must be done before getConection;
	 */
	public void setProperty(String key, boolean value) {

		connectProperties.put(key, value ? "true" : "false");

	}

	@Override
	public OlapConnection createOlap4jConnection() throws SQLException {

		MondrianOlap4jDriver d = new MondrianOlap4jDriver();

		final java.sql.Connection connection = java.sql.DriverManager.getConnection(getOlapConnectString());
		return ((OlapWrapper) connection).unwrap(OlapConnection.class);
	}

	public String getJDBCConnectString() {

		// may exist better ways
		return connectionString;
	}

	public String getOlapConnectString() {

		return olapConnectString;
	}

	@Override
	public String toString() {

		return connectionString;

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
