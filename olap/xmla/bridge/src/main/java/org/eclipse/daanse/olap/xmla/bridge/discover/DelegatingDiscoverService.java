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

import org.eclipse.daanse.olap.xmla.bridge.ContextGroupXmlaServiceConfig;
import org.eclipse.daanse.olap.xmla.bridge.ContextListSupplyer;
import org.eclipse.daanse.xmla.api.RequestMetaData;
import org.eclipse.daanse.xmla.api.discover.DiscoverService;
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
import org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesRequest;
import org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.enumerators.DiscoverEnumeratorsRequest;
import org.eclipse.daanse.xmla.api.discover.discover.enumerators.DiscoverEnumeratorsResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.keywords.DiscoverKeywordsRequest;
import org.eclipse.daanse.xmla.api.discover.discover.keywords.DiscoverKeywordsResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.literals.DiscoverLiteralsRequest;
import org.eclipse.daanse.xmla.api.discover.discover.literals.DiscoverLiteralsResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.properties.DiscoverPropertiesRequest;
import org.eclipse.daanse.xmla.api.discover.discover.properties.DiscoverPropertiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.schemarowsets.DiscoverSchemaRowsetsRequest;
import org.eclipse.daanse.xmla.api.discover.discover.schemarowsets.DiscoverSchemaRowsetsResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.xmlmetadata.DiscoverXmlMetaDataRequest;
import org.eclipse.daanse.xmla.api.discover.discover.xmlmetadata.DiscoverXmlMetaDataResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.actions.MdSchemaActionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.actions.MdSchemaActionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.cubes.MdSchemaCubesRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.cubes.MdSchemaCubesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.functions.MdSchemaFunctionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.functions.MdSchemaFunctionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.hierarchies.MdSchemaHierarchiesRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.hierarchies.MdSchemaHierarchiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.kpis.MdSchemaKpisRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.kpis.MdSchemaKpisResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.levels.MdSchemaLevelsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.levels.MdSchemaLevelsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.measuregroupdimensions.MdSchemaMeasureGroupDimensionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.measuregroupdimensions.MdSchemaMeasureGroupDimensionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.measuregroups.MdSchemaMeasureGroupsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.measuregroups.MdSchemaMeasureGroupsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.measures.MdSchemaMeasuresRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.measures.MdSchemaMeasuresResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.members.MdSchemaMembersRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.members.MdSchemaMembersResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.properties.MdSchemaPropertiesRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.properties.MdSchemaPropertiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.sets.MdSchemaSetsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.sets.MdSchemaSetsResponseRow;

/*
 * Delegates to a other class that share same kind of information.
 * Encapsulates the Logic.
 */
public class DelegatingDiscoverService implements DiscoverService {

	private DBSchemaDiscoverService dbSchemaService;
	private MDSchemaDiscoverService mdSchemaService;
	private OtherDiscoverService otherSchemaService;

	public DelegatingDiscoverService(ContextListSupplyer contextsListSupplyer, ContextGroupXmlaServiceConfig config) {
		this.dbSchemaService = new DBSchemaDiscoverService(contextsListSupplyer);
		this.mdSchemaService = new MDSchemaDiscoverService(contextsListSupplyer);
		this.otherSchemaService = new OtherDiscoverService(contextsListSupplyer, config);
	}

	@Override
	public List<DiscoverDataSourcesResponseRow> dataSources(DiscoverDataSourcesRequest request) {
		return otherSchemaService.dataSources(request);
	}

	@Override
	public List<DbSchemaCatalogsResponseRow> dbSchemaCatalogs(DbSchemaCatalogsRequest request,RequestMetaData metaData) {

		return dbSchemaService.dbSchemaCatalogs(request);
	}

	@Override
	public List<DbSchemaColumnsResponseRow> dbSchemaColumns(DbSchemaColumnsRequest request) {

		return dbSchemaService.dbSchemaColumns(request);
	}

	@Override
	public List<DbSchemaProviderTypesResponseRow> dbSchemaProviderTypes(DbSchemaProviderTypesRequest request) {

		return dbSchemaService.dbSchemaProviderTypes(request);
	}

	@Override
	public List<DbSchemaSchemataResponseRow> dbSchemaSchemata(DbSchemaSchemataRequest request) {

		return dbSchemaService.dbSchemaSchemata(request);
	}

	@Override
	public List<DbSchemaSourceTablesResponseRow> dbSchemaSourceTables(DbSchemaSourceTablesRequest request) {

		return dbSchemaService.dbSchemaSourceTables(request);
	}

	@Override
	public List<DbSchemaTablesResponseRow> dbSchemaTables(DbSchemaTablesRequest request) {

		return dbSchemaService.dbSchemaTables(request);
	}

	@Override
	public List<DbSchemaTablesInfoResponseRow> dbSchemaTablesInfo(DbSchemaTablesInfoRequest request) {

		return dbSchemaService.dbSchemaTablesInfo(request);
	}

	@Override
	public List<DiscoverEnumeratorsResponseRow> discoverEnumerators(DiscoverEnumeratorsRequest request) {

		return otherSchemaService.discoverEnumerators(request);
	}

	@Override
	public List<DiscoverKeywordsResponseRow> discoverKeywords(DiscoverKeywordsRequest request) {

		return otherSchemaService.discoverKeywords(request);
	}

	@Override
	public List<DiscoverLiteralsResponseRow> discoverLiterals(DiscoverLiteralsRequest request) {

		return otherSchemaService.discoverLiterals(request);
	}

	@Override
	public List<DiscoverPropertiesResponseRow> discoverProperties(DiscoverPropertiesRequest request) {

		return otherSchemaService.discoverProperties(request);
	}

	@Override
	public List<DiscoverSchemaRowsetsResponseRow> discoverSchemaRowsets(DiscoverSchemaRowsetsRequest request) {

		return otherSchemaService.discoverSchemaRowsets(request);
	}

	@Override
	public List<MdSchemaActionsResponseRow> mdSchemaActions(MdSchemaActionsRequest request) {

		return mdSchemaService.mdSchemaActions(request);
	}

	@Override
	public List<MdSchemaCubesResponseRow> mdSchemaCubes(MdSchemaCubesRequest request) {

		return mdSchemaService.mdSchemaCubes(request);
	}

	@Override
	public List<MdSchemaDimensionsResponseRow> mdSchemaDimensions(MdSchemaDimensionsRequest request) {

		return mdSchemaService.mdSchemaDimensions(request);
	}

	@Override
	public List<MdSchemaFunctionsResponseRow> mdSchemaFunctions(MdSchemaFunctionsRequest request) {

		return mdSchemaService.mdSchemaFunctions(request);
	}

	@Override
	public List<MdSchemaHierarchiesResponseRow> mdSchemaHierarchies(MdSchemaHierarchiesRequest request) {

		return mdSchemaService.mdSchemaHierarchies(request);
	}

	@Override
	public List<MdSchemaKpisResponseRow> mdSchemaKpis(MdSchemaKpisRequest request) {

		return mdSchemaService.mdSchemaKpis(request);
	}

	@Override
	public List<MdSchemaLevelsResponseRow> mdSchemaLevels(MdSchemaLevelsRequest request) {

		return mdSchemaService.mdSchemaLevels(request);
	}

	@Override
	public List<MdSchemaMeasureGroupDimensionsResponseRow> mdSchemaMeasureGroupDimensions(
			MdSchemaMeasureGroupDimensionsRequest request) {

		return mdSchemaService.mdSchemaMeasureGroupDimensions(request);
	}

	@Override
	public List<MdSchemaMeasureGroupsResponseRow> mdSchemaMeasureGroups(MdSchemaMeasureGroupsRequest request) {

		return mdSchemaService.mdSchemaMeasureGroups(request);
	}

	@Override
	public List<MdSchemaMeasuresResponseRow> mdSchemaMeasures(MdSchemaMeasuresRequest request) {

		return mdSchemaService.mdSchemaMeasures(request);
	}

	@Override
	public List<MdSchemaMembersResponseRow> mdSchemaMembers(MdSchemaMembersRequest request) {

		return mdSchemaService.mdSchemaMembers(request);
	}

	@Override
	public List<MdSchemaPropertiesResponseRow> mdSchemaProperties(MdSchemaPropertiesRequest request) {

		return mdSchemaService.mdSchemaProperties(request);
	}

	@Override
	public List<MdSchemaSetsResponseRow> mdSchemaSets(MdSchemaSetsRequest request) {

		return mdSchemaService.mdSchemaSets(request);
	}

	@Override
	public List<DiscoverXmlMetaDataResponseRow> xmlMetaData(DiscoverXmlMetaDataRequest request) {

		return otherSchemaService.xmlMetaData(request);
	}

}
