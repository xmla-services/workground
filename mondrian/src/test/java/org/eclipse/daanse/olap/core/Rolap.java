/*
Collections.synchronizedList(new ArrayList<>());Collections.synchronizedList(new ArrayList<>());* Copyright (c) 2022 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.olap.core;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.sql.DataSource;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.olap.api.Context;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.sample.foodmart.record.FoodMartRecordDbMappingSchemaProvider;
import org.junit.jupiter.api.Test;

import mondrian.olap.Util;
import mondrian.rolap.SchemaKey;

class Rolap {

	@Test
	void testPidNotChanged() throws Exception {
		SchemaKey schemaKey = mock(SchemaKey.class);
		Util.PropertyList list = new Util.PropertyList();
		DataSource ds = mock(DataSource.class);

		Context ctx = mock(Context.class);
		Dialect dialect = mock(Dialect.class);
		FoodMartRecordDbMappingSchemaProvider fsp = new FoodMartRecordDbMappingSchemaProvider();

		when(ctx.getDatabaseMappingSchemaProviders()).thenReturn(List.of(fsp));
		when(ctx.getDataSource()).thenReturn(ds);
		when(ctx.getDialect()).thenReturn(dialect);

//		new RolapSchema(schemaKey, list, ctx);

	}
}
