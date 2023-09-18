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
package org.eclipse.daanse.olap.xmla.bridge.discover;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.eclipse.daanse.olap.api.Context;
import org.eclipse.daanse.olap.xmla.bridge.ContextsSupplyerImpl;
import org.eclipse.daanse.xmla.api.discover.dbschema.catalogs.DbSchemaCatalogsRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.catalogs.DbSchemaCatalogsRestrictions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DbSchemaDiscoverServiceTest {

	@Mock
	private Context context1;

	@Mock
	private Context context2;

	private DBSchemaDiscoverService service;

	private ContextsSupplyerImpl cls;

	@BeforeEach
	void setup() {
		/*
		 * empty list, but override with:
		 * when(cls.get()).thenReturn(List.of(context1,context2));`
		 */

		cls = Mockito.spy(new ContextsSupplyerImpl(List.of()));
		service = new DBSchemaDiscoverService(cls);
	}

	@Test
	void dbSchemaCubes_1() {
		when(cls.get()).thenReturn(List.of(context1,context2));
		
		DbSchemaCatalogsRequest request = Mockito.mock(DbSchemaCatalogsRequest.class);
		DbSchemaCatalogsRestrictions restrictions = Mockito.mock(DbSchemaCatalogsRestrictions.class);
		
		when(request.restrictions()).thenReturn(restrictions);
		when(restrictions.catalogName()).thenReturn(Optional.of("foo"));

		when(context1.getName()).thenReturn("bar");
		when(context2.getName()).thenReturn("foo");

		
		service.dbSchemaCatalogs(request);
		verify(context1,times(1)).getName();
		verify(context2,times(1)).getName();
		
		//TODO: Mock a Context and see if the response correlates to the request
		
//		context1.getDatabaseMappingSchemaProvider().get().cubes();

	}

}
