package org.opencube.junit5.context;

import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.Map.Entry;
import java.util.Optional;

import javax.sql.DataSource;

import org.olap4j.OlapConnection;
import org.olap4j.OlapWrapper;

import mondrian.olap.Connection;
import mondrian.olap.DriverManager;
import mondrian.olap.Util;
import mondrian.olap.Util.PropertyList;
import mondrian.olap4j.MondrianOlap4jDriver;
import mondrian.rolap.RolapConnectionProperties;
import mondrian.util.Pair;

public class BaseTestContext implements Context {

	private DataSource dataSource;
	private Util.PropertyList connectProperties = new Util.PropertyList();
	private String olapConnectString;

	public BaseTestContext clone() {

		BaseTestContext context = new BaseTestContext();

		Util.PropertyList newConnectProperties = new Util.PropertyList();

		for (Pair<String, String> pair : connectProperties) {
			newConnectProperties.put(pair.left, pair.right);
		}

		context.init(new AbstractMap.SimpleEntry<Util.PropertyList, DataSource>(newConnectProperties, dataSource));

		return context;

	}

	@Override
	public void init(Entry<PropertyList, DataSource> dataSource) {
		this.dataSource = dataSource.getValue();
		this.connectProperties = dataSource.getKey();
		init();

	}

	private void init() {

		connectProperties.remove(RolapConnectionProperties.Catalog.name());
		//TODO maybe ead file to content before remove
		
		connectProperties.put(RolapConnectionProperties.Provider.name(), provider());

		// Find the catalog. Use the URL specified in the connect string, if
		// it is specified and is valid. Otherwise, reference FoodMart.xml
		// assuming we are at the root of the source tree.
		String catalogContent = null;
		String catalog = connectProperties.get(RolapConnectionProperties.CatalogContent.name());
		if (catalog == null || catalog.isEmpty()) {
			catalogContent = getCatalogContent();
		} else {
			catalogContent = catalog;
		}

		connectProperties.put(RolapConnectionProperties.CatalogContent.name(), catalogContent);

//		connectProperties.put(RolapConnectionProperties.Catalog.name(), catalogURL.toString());
		olapConnectString = connectProperties.toString();
		if (olapConnectString.startsWith("Provider=mondrian; ")) {
			olapConnectString = olapConnectString.substring("Provider=mondrian; ".length());
		}
		olapConnectString = "jdbc:mondrian:" + olapConnectString;
	}

	@Override
	public Connection createConnection() {

		if (dataSource == null) {
			return DriverManager.getConnection(getJDBCConnectString(), null);
		}
		return DriverManager.getConnection(connectProperties, null, dataSource);

	}

	protected String getCatalogContent() {
		return "";
	}

	protected String provider() {
		return "mondrian";
	}

	protected Optional<String> jdbcPassword() {
		return Optional.empty();
	}

	protected Optional<String> jdbcUser() {
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
		return connectProperties.get(RolapConnectionProperties.Jdbc.name());

	}

	@Override
	public String getOlapConnectString() {

		return connectProperties.toString();
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
