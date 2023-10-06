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

import org.opencube.junit5.context.SQLLiteContext;
import org.opencube.junit5.context.TestContext;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

//@ServiceProvider(value = DatabaseProvider.class)
public class SQLiteDatabaseProvider implements DatabaseProvider {

	private static String getTempFile() {
		try {
			Path temp = Path.of("sqlite.db");
			if (Files.exists(temp)) {
				Files.delete(temp);
			}
			return temp.toFile().getAbsolutePath().toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	private static final String JDBC_SQLITE_MEMORY = "jdbc:sqlite:" + getTempFile();

	@Override
	public void close() throws IOException {

	}

	@Override
	public TestContext activate() {
		SQLiteConfig cfg = new SQLiteConfig();
		SQLiteDataSource ds = new SQLiteDataSource(cfg);
		ds.setUrl(JDBC_SQLITE_MEMORY);
		TestContext context = new SQLLiteContext(ds);
		return context;
	}



}
