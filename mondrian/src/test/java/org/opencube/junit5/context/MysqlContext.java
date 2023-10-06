/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   SmartCity Jena - initial
 *   Stefan Bischof (bipolis.org) - initial
 */
package org.opencube.junit5.context;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.eclipse.daanse.db.dialect.db.mysql.MySqlDialect;

public class MysqlContext extends AbstractTestContext {

	public MysqlContext(DataSource dataSource) {
		
		setDataSource(dataSource);
		try (Connection connection = dataSource.getConnection()) {
			setDialect(new MySqlDialect(connection));
		} catch (SQLException e) {
			new RuntimeException(e);
		}
	}

	@Override
	public String getName() {
		return "mysqlBaseContext";
	}


	

}
