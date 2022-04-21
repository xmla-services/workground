package org.opencube.junit5.context;

import java.net.URL;
import java.sql.SQLException;
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

	@Override
	public void init(DataSource dataSource) {

		connectProperties = new Util.PropertyList();

		connectProperties.put(RolapConnectionProperties.Provider.name(), provider());
		jdbcURL().ifPresent(v -> connectProperties.put(RolapConnectionProperties.Jdbc.name(), v));
		jdbcUser().ifPresent(v -> connectProperties.put(RolapConnectionProperties.JdbcUser.name(), v));
		jdbcPassword().ifPresent(v -> connectProperties.put(RolapConnectionProperties.JdbcPassword.name(), v));
		connectProperties.put(RolapConnectionProperties.Catalog.name(), catalog().toString());

		if (dataSource == null) {
			System.out.println(1);
		}
		this.dataSource = dataSource;

	}

	@Override
	public Connection createConnection() {

		if (dataSource == null) {
			System.out.println(1);
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

		String connectString = getConnectString();
		if (connectString.startsWith("Provider=mondrian; ")) {
			connectString = connectString.substring("Provider=mondrian; ".length());
		}
		final java.sql.Connection connection = java.sql.DriverManager.getConnection("jdbc:mondrian:" + connectString);
		return ((OlapWrapper) connection).unwrap(OlapConnection.class);
	}

	private String getConnectString() {

		// may exist better ways
		return createConnection().getConnectString();
	}

	@Override
	public String toString() {

		Connection con = createConnection();
		if (con == null||dataSource==null) {
			return super.toString();
		}
		return dataSource.getClass()+ " - " + con.getConnectString();
	}

}
