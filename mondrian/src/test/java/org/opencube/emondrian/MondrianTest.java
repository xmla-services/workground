package org.opencube.emondrian;


import java.util.Properties;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.opencube.junit5.Constants;
import org.sqlite.SQLiteDataSource;

import mondrian.olap.Connection;
import mondrian.olap.DriverManager;
import mondrian.olap.Query;
import mondrian.olap.Result;
import mondrian.olap.Util.PropertyList;
import mondrian.olap4j.MondrianOlap4jDriver;
import mondrian.rolap.RolapConnectionProperties;
import mondrian.server.Execution;
import mondrian.server.Statement;
import mondrian.spi.CatalogLocator;

//@ExtendWith(MondrianRuntimeExtension.class)

public class MondrianTest {


	public static final String query = "SELECT  \n"//
			+ "    { [Measures].[Anzahl],   \n"//
			+ "        [Measures].[Einkommen] } ON COLUMNS,  \n"//
			+ "    { [Geographie].[Jena]} on rows  \n"//
			+ "FROM [Menschen]  \n"//
			+ "   where {[geschlecht].[m]}";

	@Test
	void testdriver() throws Exception {

		org.sqlite.JDBC j = new org.sqlite.JDBC();
		PropertyList propertyList = new PropertyList();
		propertyList.put(RolapConnectionProperties.Provider.name(), "mondrian");
		propertyList.put(RolapConnectionProperties.Catalog.name(),
				Constants.TESTFILES_DIR + "Mensch.xml");

		CatalogLocator catalogLocator = new CatalogLocator() {

			@Override
			public String locate(String arg0) {
				return arg0;
			}
		};

		SQLiteDataSource ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:" + Constants.TESTFILES_DIR + "sqlite.db");

		Connection c = DriverManager.getConnection(propertyList, catalogLocator, ds);
		System.out.println(c);
		System.out.println(c.getCatalogName());

		Query q = c.parseQuery(query);
		Statement s = q.getStatement();
		Result r = s.getMondrianConnection().execute(new Execution(s, 1000l));
		java.io.StringWriter sw = new java.io.StringWriter();
		java.io.PrintWriter pw = new java.io.PrintWriter(sw);

		r.print(pw);
		System.out.println(sw.getBuffer().toString());

	}

	@Test
	void testWithUrl() throws Exception {

		org.sqlite.JDBC j = new org.sqlite.JDBC();
		PropertyList propertyList = new PropertyList();
		propertyList.put(RolapConnectionProperties.Provider.name(), "mondrian");
		propertyList.put("Jdbc", "jdbc:sqlite:" + Constants.TESTFILES_DIR + "sqlite.db");
		propertyList.put(RolapConnectionProperties.Catalog.name(),
				Constants.TESTFILES_DIR + "Mensch.xml");
		propertyList.put("JdbcDrivers", "org.sqlite.JDBC");

		CatalogLocator catalogLocator = new CatalogLocator() {

			@Override
			public String locate(String arg0) {
				return arg0;
			}
		};

		Connection c = DriverManager.getConnection(propertyList, catalogLocator);
		System.out.println(c);
		System.out.println(c.getCatalogName());

		Query q = c.parseQuery(query);
		Statement s = q.getStatement();
		Result r = s.getMondrianConnection().execute(new Execution(s, 1000l));
		java.io.StringWriter sw = new java.io.StringWriter();
		java.io.PrintWriter pw = new java.io.PrintWriter(sw);

		r.print(pw);
		System.out.println(sw.getBuffer().toString());
//		System.out.println(r.getAxes());
//		RolapConnection r = new RolapConnection(null, propertyList, null);
	}

	@Test
	@Disabled
	void testOlap() throws Exception {
		mondrian.olap4j.MondrianOlap4jDriver d = new MondrianOlap4jDriver();
		java.sql.Connection c = d.connect("url", new Properties());

	}

}
