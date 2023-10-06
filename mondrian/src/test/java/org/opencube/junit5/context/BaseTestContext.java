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
package org.opencube.junit5.context;

import java.sql.SQLException;
import java.util.Properties;

import org.eclipse.daanse.olap.api.Connection;
import org.eclipse.daanse.olap.api.Context;
import org.olap4j.OlapConnection;
import org.olap4j.OlapWrapper;
import org.opencube.junit5.propupdator.PropertyUpdater;

import mondrian.olap.DriverManager;
import mondrian.olap.Util;
import mondrian.olap4j.MondrianOlap4jDriver;

public class BaseTestContext implements TestingContext {

	private TestContext context;
	private Util.PropertyList properties = new Util.PropertyList();

	@Override
	public void init(TestContext context) {
		this.context = context;


	}

	public void update(PropertyUpdater updater) {
		properties = updater.update(properties);
	}


	@Override
	public Connection createConnection() {

		return DriverManager.getConnection(properties, null, context);

	}

	protected String getCatalogContent() {
		return "";
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
    public TestContext getContext() {
        return context;
    }

    @Override
	public OlapConnection createOlap4jConnection() throws SQLException {

		//final java.sql.Connection connection = java.sql.DriverManager.getConnection("jdbc:mondrian:" + getOlapConnectString());
        MondrianOlap4jDriver driver = new MondrianOlap4jDriver(context);
        final java.sql.Connection connection =  driver.connect("jdbc:mondrian:" + getOlapConnectString(), new Properties());
		return ((OlapWrapper) connection).unwrap(OlapConnection.class);
	}



	@Override
	public String getOlapConnectString() {

		return properties.toString();
	}



}
