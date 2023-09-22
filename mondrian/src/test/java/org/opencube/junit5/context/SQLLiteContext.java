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

import javax.sql.DataSource;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.mysql.MySqlDialect;

public class SQLLiteContext extends AbstractTestContext {
	
	public SQLLiteContext(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public String getName() {
		return "sqliteBaseContext";
	}

	@Override
	Dialect createDialect(Connection connection) {
		return new MySqlDialect(connection);

	}

}
