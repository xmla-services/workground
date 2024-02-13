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
package org.opencube.junit5.dbprovider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.UUID;

import javax.sql.DataSource;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.h2.H2Dialect;
import org.h2.jdbcx.JdbcConnectionPool;

import aQute.bnd.annotation.spi.ServiceProvider;

@ServiceProvider(value = DatabaseProvider.class)
public class H2DatabaseProvider implements DatabaseProvider {

	private String getTempFile() {
		try {

			Path temp = Files.createTempDirectory("daanse_test").toAbsolutePath();

			if (Files.exists(temp)) {
				Files.delete(temp);
			}
			return temp.toFile().getAbsolutePath().toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	@Override
	public void close() throws IOException {

	}

	@Override
	public Entry<DataSource, Dialect> activate() {
		String JDBC_SQLITE_MEMORY = "jdbc:h2:" + getTempFile() + "/" + UUID.randomUUID() + ".db";

		JdbcConnectionPool cpDataSource = JdbcConnectionPool.create(JDBC_SQLITE_MEMORY, "sa", "sa");

		try {
			Connection connection = cpDataSource.getConnection();
			Dialect dialect = new H2Dialect(connection);

			connection.close();
			return new SimpleEntry<>(cpDataSource, dialect);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
