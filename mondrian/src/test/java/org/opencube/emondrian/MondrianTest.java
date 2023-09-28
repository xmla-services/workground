/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * History:
 *  This files came from the mondrian project. Some of the Flies
 *  (mostly the Tests) did not have License Header.
 *  But the Project is EPL Header. 2002-2022 Hitachi Vantara.
 *
 * Contributors:
 *   Hitachi Vantara.
 *   SmartCity Jena - initial  Java 8, Junit5
 */
package org.opencube.emondrian;


import java.util.Properties;

import org.eclipse.daanse.olap.api.Connection;
import org.eclipse.daanse.olap.api.result.Result;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.opencube.junit5.Constants;
import org.opencube.junit5.context.SQLLiteContext;
import org.sqlite.SQLiteDataSource;

import mondrian.olap.DriverManager;
import mondrian.olap.QueryImpl;
import mondrian.olap.Util.PropertyList;
import mondrian.olap4j.MondrianOlap4jDriver;
import mondrian.rolap.RolapConnectionProperties;
import mondrian.server.Execution;
import mondrian.server.Statement;
import mondrian.spi.CatalogLocator;
import mondrian.spi.impl.IdentityCatalogLocator;

//@ExtendWith(MondrianRuntimeExtension.class)

class MondrianTest {


	public static final String query = "SELECT  \n"//
			+ "    { [Measures].[Anzahl],   \n"//
			+ "        [Measures].[Einkommen] } ON COLUMNS,  \n"//
			+ "    { [Geographie].[Jena]} on rows  \n"//
			+ "FROM [Menschen]  \n"//
			+ "   where {[geschlecht].[m]}";

	@Test
	@Disabled //disabled for CI build
	void testdriver() throws Exception {

		org.sqlite.JDBC j = new org.sqlite.JDBC();
		PropertyList propertyList = new PropertyList();
		propertyList.put(RolapConnectionProperties.Catalog.name(),
				Constants.TESTFILES_DIR + "Mensch.xml");

		CatalogLocator catalogLocator = new IdentityCatalogLocator();

		SQLiteDataSource ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:" + Constants.TESTFILES_DIR + "sqlite.db");
        SQLLiteContext context = new SQLLiteContext(ds);
		Connection c = DriverManager.getConnection(propertyList, catalogLocator, context);
		System.out.println(c);
		System.out.println(c.getCatalogName());

		QueryImpl q = c.parseQuery(query);
		Statement s = q.getStatement();
		Result r = s.getMondrianConnection().execute(new Execution(s, 1000l));
		java.io.StringWriter sw = new java.io.StringWriter();
		java.io.PrintWriter pw = new java.io.PrintWriter(sw);

		r.print(pw);
		System.out.println(sw.getBuffer().toString());

	}

	@Disabled //disabled for CI build
	@Test
	void testWithUrl() throws Exception {

		org.sqlite.JDBC j = new org.sqlite.JDBC();
		PropertyList propertyList = new PropertyList();
		propertyList.put("Jdbc", "jdbc:sqlite:" + Constants.TESTFILES_DIR + "sqlite.db");
		propertyList.put(RolapConnectionProperties.Catalog.name(),
				Constants.TESTFILES_DIR + "Mensch.xml");
		propertyList.put("JdbcDrivers", "org.sqlite.JDBC");

		CatalogLocator catalogLocator = new IdentityCatalogLocator();

		Connection c = DriverManager.getConnection(propertyList, catalogLocator);

		QueryImpl q = c.parseQuery(query);
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
