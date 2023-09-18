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

import java.util.List;
import java.util.Optional;

import org.eclipse.daanse.olap.api.Context;
import org.eclipse.daanse.olap.xmla.bridge.ContextListSupplyer;
import org.eclipse.daanse.xmla.api.discover.dbschema.catalogs.DbSchemaCatalogsRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.catalogs.DbSchemaCatalogsResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.columns.DbSchemaColumnsRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.columns.DbSchemaColumnsResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.providertypes.DbSchemaProviderTypesRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.providertypes.DbSchemaProviderTypesResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.schemata.DbSchemaSchemataRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.schemata.DbSchemaSchemataResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.sourcetables.DbSchemaSourceTablesRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.sourcetables.DbSchemaSourceTablesResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.tables.DbSchemaTablesRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.tables.DbSchemaTablesResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.tablesinfo.DbSchemaTablesInfoRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.tablesinfo.DbSchemaTablesInfoResponseRow;

public class DBSchemaDiscoverService {

	private ContextListSupplyer contextsListSupplyer;

	public DBSchemaDiscoverService(ContextListSupplyer contextsListSupplyer) {
		this.contextsListSupplyer = contextsListSupplyer;
	}

	public List<DbSchemaCatalogsResponseRow> dbSchemaCatalogs(DbSchemaCatalogsRequest request) {

		Optional<String> oName = request.restrictions().catalogName();
		Optional<Context> oContext = oName.flatMap(name -> contextsListSupplyer.tryGetFirstByName(name));
		;

//				oContext.map(c->c.ge)
		return null;
	}

	public List<DbSchemaColumnsResponseRow> dbSchemaColumns(DbSchemaColumnsRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DbSchemaProviderTypesResponseRow> dbSchemaProviderTypes(DbSchemaProviderTypesRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DbSchemaSchemataResponseRow> dbSchemaSchemata(DbSchemaSchemataRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DbSchemaSourceTablesResponseRow> dbSchemaSourceTables(DbSchemaSourceTablesRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DbSchemaTablesResponseRow> dbSchemaTables(DbSchemaTablesRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DbSchemaTablesInfoResponseRow> dbSchemaTablesInfo(DbSchemaTablesInfoRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
