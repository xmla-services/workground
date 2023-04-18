/*
* Copyright (c) 2023 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.xmla.ws.jakarta.basic;

import java.io.IOException;
import java.util.List;

import org.eclipse.daanse.xmla.api.XmlaService;
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
import org.eclipse.daanse.xmla.api.execute.alter.AlterRequest;
import org.eclipse.daanse.xmla.api.execute.alter.AlterResponse;
import org.eclipse.daanse.xmla.api.execute.cancel.CancelRequest;
import org.eclipse.daanse.xmla.api.execute.cancel.CancelResponse;
import org.eclipse.daanse.xmla.api.execute.clearcache.ClearCacheRequest;
import org.eclipse.daanse.xmla.api.execute.clearcache.ClearCacheResponse;
import org.eclipse.daanse.xmla.api.execute.statement.StatementRequest;
import org.eclipse.daanse.xmla.api.execute.statement.StatementResponse;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.ext.Authenticate;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.ext.AuthenticateResponse;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.BeginSession;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Discover;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.DiscoverResponse;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.EndSession;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Execute;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.ExecuteResponse;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Session;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.ws.Holder;

public class ApiXmlaWsAdapter implements WsAdapter {

	XmlaService xmlaService;

	public ApiXmlaWsAdapter(XmlaService xmlaService) {
		this.xmlaService = xmlaService;
	}

	private static final String MDSCHEMA_FUNCTIONS = "MDSCHEMA_FUNCTIONS";
	private static final String MDSCHEMA_DIMENSIONS = "MDSCHEMA_DIMENSIONS";
	private static final String MDSCHEMA_CUBES = "MDSCHEMA_CUBES";
	private static final String MDSCHEMA_ACTIONS = "MDSCHEMA_ACTIONS";
	private static final String DBSCHEMA_TABLES = "DBSCHEMA_TABLES";
	private static final String DISCOVER_LITERALS = "DISCOVER_LITERALS";
	private static final String DISCOVER_KEYWORDS = "DISCOVER_KEYWORDS";
	private static final String DISCOVER_ENUMERATORS = "DISCOVER_ENUMERATORS";
	private static final String DISCOVER_SCHEMA_ROWSETS = "DISCOVER_SCHEMA_ROWSETS";
	private static final String DISCOVER_PROPERTIES = "DISCOVER_PROPERTIES";
	private static final String DBSCHEMA_CATALOGS = "DBSCHEMA_CATALOGS";
	private static final String DISCOVER_DATASOURCES = "DISCOVER_DATASOURCES";
	private static final String DISCOVER_XML_METADATA = "DISCOVER_XML_METADATA";
	private static final String DBSCHEMA_COLUMNS = "DBSCHEMA_COLUMNS";
	private static final String DBSCHEMA_PROVIDER_TYPES = "DBSCHEMA_PROVIDER_TYPES";
	private static final String DBSCHEMA_SCHEMATA = "DBSCHEMA_SCHEMATA";
	private static final String DBSCHEMA_SOURCE_TABLES = "DBSCHEMA_SOURCE_TABLES";
	private static final String DBSCHEMA_TABLES_INFO = "DBSCHEMA_TABLES_INFO";
	private static final String MDSCHEMA_HIERARCHIES = "MDSCHEMA_HIERARCHIES";
	private static final String MDSCHEMA_LEVELS = "MDSCHEMA_LEVELS";
	private static final String MDSCHEMA_MEASUREGROUP_DIMENSIONS = "MDSCHEMA_MEASUREGROUP_DIMENSIONS";
	private static final String MDSCHEMA_MEASURES = "MDSCHEMA_MEASURES";
	private static final String MDSCHEMA_MEMBERS = "MDSCHEMA_MEMBERS";
	private static final String MDSCHEMA_PROPERTIES = "MDSCHEMA_PROPERTIES";
	private static final String MDSCHEMA_SETS = "MDSCHEMA_SETS";
	private static final String MDSCHEMA_KPIS = "MDSCHEMA_KPIS";
	private static final String MDSCHEMA_MEASUREGROUPS = "MDSCHEMA_MEASUREGROUPS";

	@Override
	public AuthenticateResponse authenticate(Authenticate authenticate) {
		return null;
	}

	@Override
	public DiscoverResponse discover(Discover discover, Holder<Session> session, BeginSession beginSession,
			EndSession endSession) {

		if (discover == null) {
			return null;
		}

		try {
			return switch (discover.getRequestType()) {
                case DBSCHEMA_TABLES_INFO -> handleDiscoverDbSchemaTablesInfo(discover);
                case DBSCHEMA_SOURCE_TABLES -> handleDiscoverDbSchemaSourceTables(discover);
                case MDSCHEMA_MEASUREGROUPS -> handleDiscoverMdSchemaMeasureGroups(discover);
                case MDSCHEMA_KPIS -> handleDiscoverMdSchemaKpis(discover);
                case MDSCHEMA_SETS -> handleDiscoverMdSchemaSets(discover);
                case MDSCHEMA_PROPERTIES -> handleDiscoverMdSchemaProperties(discover);
                case MDSCHEMA_MEMBERS -> handleDiscoverMdSchemaMembers(discover);
                case MDSCHEMA_MEASURES -> handleDiscoverMdSchemaMeasures(discover);
                case MDSCHEMA_MEASUREGROUP_DIMENSIONS -> handleDiscoverMdSchemaMeasureGroupDimensions(discover);
                case MDSCHEMA_LEVELS -> handleDiscoverMdSchemaLevels(discover);
                case DBSCHEMA_SCHEMATA -> handleDiscoverDbSchemaSchemata(discover);
                case DBSCHEMA_PROVIDER_TYPES -> handleDiscoverDbSchemaProviderTypes(discover);
                case DBSCHEMA_COLUMNS -> handleDiscoverDbSchemaColumns(discover);
                case DISCOVER_XML_METADATA -> handleDiscoverXmlMetaData(discover);
                case DISCOVER_DATASOURCES -> handleDiscoverDataSources(discover);
                case MDSCHEMA_HIERARCHIES -> handleDiscoverMdSchemaHierarchies(discover);
                case MDSCHEMA_FUNCTIONS -> handleDiscoverMdSchemaFunctions(discover);
                case MDSCHEMA_DIMENSIONS -> handleDiscoverMdSchemaDimensions(discover);
                case MDSCHEMA_CUBES -> handleDiscoverMdSchemaCubes(discover);
                case MDSCHEMA_ACTIONS -> handleDiscoverMdSchemaActions(discover);
                case DBSCHEMA_TABLES -> handleDiscoverDbSchemaTables(discover);
                case DISCOVER_LITERALS -> handleDiscoverLiterals(discover);
                case DISCOVER_KEYWORDS -> handleDiscoverKeywords(discover);
                case DISCOVER_ENUMERATORS -> handleDiscoverEnumerators(discover);
                case DISCOVER_SCHEMA_ROWSETS -> handleDiscoverSchemaRowsets(discover);
                case DISCOVER_PROPERTIES -> handleDiscoverProperties(discover);
                case DBSCHEMA_CATALOGS -> handleDbSchemaCatalogs(discover);
                default -> throw new IllegalArgumentException("Unexpected value: " + discover.getRequestType());
            };

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ExecuteResponse execute(Execute parameters, Holder<Session> session, BeginSession beginSession,
			EndSession endSession) {
		if (parameters == null) {
			return null;
		}
		ExecuteResponse executeResponse = null;
		try {
			if (parameters.getCommand() != null) {
				if (parameters.getCommand().getStatement() != null) {
					executeResponse = handleStatement(parameters);
				}
				if (parameters.getCommand().getAlter() != null) {
					executeResponse = handleAlter(parameters);
				}
				if (parameters.getCommand().getClearCache() != null) {
					executeResponse = handleClearCache(parameters);
				}
				if (parameters.getCommand().getCancel() != null) {
					executeResponse = handleCancel(parameters);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return executeResponse;
	}

	private ExecuteResponse handleCancel(Execute requestWs) {
		CancelRequest requestApi = Convert.fromCancel(requestWs);
		CancelResponse responseApi = xmlaService.execute().cancel(requestApi);
		return Convert.toCancel(responseApi);
	}

	private ExecuteResponse handleClearCache(Execute requestWs) {
		ClearCacheRequest requestApi = Convert.fromClearCache(requestWs);
		ClearCacheResponse responseApi = xmlaService.execute().clearCache(requestApi);
		return Convert.toClearCache(responseApi);
	}

	private ExecuteResponse handleAlter(Execute requestWs) {
		AlterRequest requestApi = Convert.fromAlter(requestWs);
		AlterResponse responseApi = xmlaService.execute().alter(requestApi);
		return Convert.toAlter(responseApi);
	}

	private ExecuteResponse handleStatement(Execute requestWs) {
		StatementRequest requestApi = Convert.fromStatement(requestWs);
		StatementResponse responseApi = xmlaService.execute().statement(requestApi);
		return Convert.toStatement(responseApi);
	}

	private DiscoverResponse handleDbSchemaCatalogs(Discover requestWs) throws JAXBException, IOException {
		DbSchemaCatalogsRequest requestApi = Convert.fromDiscoverDbSchemaCatalogs(requestWs);
		List<DbSchemaCatalogsResponseRow> responseApi = xmlaService.discover().dbSchemaCatalogs(requestApi);
		return Convert.toDiscoverDbSchemaCatalogs(responseApi);
	}

	private DiscoverResponse handleDiscoverProperties(Discover requestWs) throws JAXBException, IOException {

		DiscoverPropertiesRequest requestApi = Convert.fromDiscoverProperties(requestWs);
		List<DiscoverPropertiesResponseRow> responseApi = xmlaService.discover().discoverProperties(requestApi);
		return Convert.toDiscoverProperties(responseApi);
	}

	private DiscoverResponse handleDiscoverSchemaRowsets(Discover requestWs) throws JAXBException, IOException {

		DiscoverSchemaRowsetsRequest requestApi = Convert.fromDiscoverSchemaRowsets(requestWs);
		List<DiscoverSchemaRowsetsResponseRow> responseApi = xmlaService.discover().discoverSchemaRowsets(requestApi);
		return Convert.toDiscoverSchemaRowsets(responseApi);
	}

	private DiscoverResponse handleDiscoverEnumerators(Discover requestWs) throws JAXBException, IOException {

		DiscoverEnumeratorsRequest requestApi = Convert.fromDiscoverEnumerators(requestWs);
		List<DiscoverEnumeratorsResponseRow> responseApi = xmlaService.discover().discoverEnumerators(requestApi);
		return Convert.toDiscoverEnumerators(responseApi);
	}

	private DiscoverResponse handleDiscoverKeywords(Discover requestWs) throws JAXBException, IOException {

		DiscoverKeywordsRequest requestApi = Convert.fromDiscoverKeywords(requestWs);
		List<DiscoverKeywordsResponseRow> responseApi = xmlaService.discover().discoverKeywords(requestApi);
		return Convert.toDiscoverKeywords(responseApi);
	}

	private DiscoverResponse handleDiscoverLiterals(Discover requestWs) throws JAXBException, IOException {

		DiscoverLiteralsRequest requestApi = Convert.fromDiscoverLiterals(requestWs);
		List<DiscoverLiteralsResponseRow> responseApi = xmlaService.discover().discoverLiterals(requestApi);
		return Convert.toDiscoverLiterals(responseApi);
	}

	private DiscoverResponse handleDiscoverDbSchemaTables(Discover requestWs) throws JAXBException, IOException {

		DbSchemaTablesRequest requestApi = Convert.fromDiscoverDbSchemaTables(requestWs);
		List<DbSchemaTablesResponseRow> responseApi = xmlaService.discover().dbSchemaTables(requestApi);
		return Convert.toDiscoverDbSchemaTables(responseApi);
	}

	private DiscoverResponse handleDiscoverMdSchemaActions(Discover requestWs) throws JAXBException, IOException {

		MdSchemaActionsRequest requestApi = Convert.fromDiscoverMdSchemaActions(requestWs);
		List<MdSchemaActionsResponseRow> responseApi = xmlaService.discover().mdSchemaActions(requestApi);
		return Convert.toDiscoverMdSchemaActions(responseApi);
	}

	private DiscoverResponse handleDiscoverMdSchemaCubes(Discover requestWs) throws JAXBException, IOException {

		MdSchemaCubesRequest requestApi = Convert.fromDiscoverMdSchemaCubes(requestWs);
		List<MdSchemaCubesResponseRow> responseApi = xmlaService.discover().mdSchemaCubes(requestApi);
		return Convert.toDiscoverMdSchemaCubes(responseApi);
	}

	private DiscoverResponse handleDiscoverMdSchemaDimensions(Discover requestWs) throws JAXBException, IOException {

		MdSchemaDimensionsRequest requestApi = Convert.fromDiscoverMdSchemaDimensions(requestWs);
		List<MdSchemaDimensionsResponseRow> responseApi = xmlaService.discover().mdSchemaDimensions(requestApi);
		return Convert.toDiscoverMdSchemaDimensions(responseApi);
	}

	private DiscoverResponse handleDiscoverMdSchemaFunctions(Discover requestWs) throws JAXBException, IOException {

		MdSchemaFunctionsRequest requestApi = Convert.fromDiscoverMdSchemaFunctions(requestWs);
		List<MdSchemaFunctionsResponseRow> responseApi = xmlaService.discover().mdSchemaFunctions(requestApi);
		return Convert.toDiscoverMdSchemaFunctions(responseApi);
	}

	private DiscoverResponse handleDiscoverMdSchemaHierarchies(Discover requestWs) throws JAXBException, IOException {

		MdSchemaHierarchiesRequest requestApi = Convert.fromDiscoverMdSchemaHierarchies(requestWs);
		List<MdSchemaHierarchiesResponseRow> responseApi = xmlaService.discover().mdSchemaHierarchies(requestApi);
		return Convert.toDiscoverMdSchemaHierarchies(responseApi);
	}

	private DiscoverResponse handleDiscoverDataSources(Discover requestWs) throws JAXBException, IOException {

		DiscoverDataSourcesRequest requestApi = Convert.fromDiscoverDataSources(requestWs);
		List<DiscoverDataSourcesResponseRow> responseApi = xmlaService.discover().dataSources(requestApi);
		return Convert.toDiscoverDataSources(responseApi);
	}

	private DiscoverResponse handleDiscoverXmlMetaData(Discover requestWs) throws JAXBException, IOException {

		DiscoverXmlMetaDataRequest requestApi = Convert.fromDiscoverXmlMetaData(requestWs);
		List<DiscoverXmlMetaDataResponseRow> responseApi = xmlaService.discover().xmlMetaData(requestApi);
		return Convert.toDiscoverXmlMetaData(responseApi);
	}

	private DiscoverResponse handleDiscoverDbSchemaColumns(Discover requestWs) throws JAXBException, IOException {

		DbSchemaColumnsRequest requestApi = Convert.fromDiscoverDbSchemaColumns(requestWs);
		List<DbSchemaColumnsResponseRow> responseApi = xmlaService.discover().dbSchemaColumns(requestApi);
		return Convert.toDiscoverDbSchemaColumns(responseApi);
	}

	private DiscoverResponse handleDiscoverDbSchemaProviderTypes(Discover requestWs) throws JAXBException, IOException {

		DbSchemaProviderTypesRequest requestApi = Convert.fromDiscoverDbSchemaProviderTypes(requestWs);
		List<DbSchemaProviderTypesResponseRow> responseApi = xmlaService.discover().dbSchemaProviderTypes(requestApi);
		return Convert.toDiscoverDbSchemaProviderTypes(responseApi);
	}

	private DiscoverResponse handleDiscoverDbSchemaSchemata(Discover requestWs) throws JAXBException, IOException {

		DbSchemaSchemataRequest requestApi = Convert.fromDiscoverDbSchemaSchemata(requestWs);
		List<DbSchemaSchemataResponseRow> responseApi = xmlaService.discover().dbSchemaSchemata(requestApi);
		return Convert.toDiscoverDbSchemaSchemata(responseApi);
	}

	private DiscoverResponse handleDiscoverMdSchemaLevels(Discover requestWs) throws JAXBException, IOException {

		MdSchemaLevelsRequest requestApi = Convert.fromDiscoverMdSchemaLevels(requestWs);
		List<MdSchemaLevelsResponseRow> responseApi = xmlaService.discover().mdSchemaLevels(requestApi);
		return Convert.toDiscoverMdSchemaLevels(responseApi);
	}

	private DiscoverResponse handleDiscoverMdSchemaMeasureGroupDimensions(Discover requestWs)
			throws JAXBException, IOException {

		MdSchemaMeasureGroupDimensionsRequest requestApi = Convert
				.fromDiscoverMdSchemaMeasureGroupDimensions(requestWs);
		List<MdSchemaMeasureGroupDimensionsResponseRow> responseApi = xmlaService.discover()
				.mdSchemaMeasureGroupDimensions(requestApi);
		return Convert.toDiscoverMdSchemaMeasureGroupDimensions(responseApi);
	}

	private DiscoverResponse handleDiscoverMdSchemaMeasures(Discover requestWs) throws JAXBException, IOException {

		MdSchemaMeasuresRequest requestApi = Convert.fromDiscoverMdSchemaMeasures(requestWs);
		List<MdSchemaMeasuresResponseRow> responseApi = xmlaService.discover().mdSchemaMeasures(requestApi);
		return Convert.toDiscoverMdSchemaMeasures(responseApi);
	}

	private DiscoverResponse handleDiscoverMdSchemaMembers(Discover requestWs) throws JAXBException, IOException {

		MdSchemaMembersRequest requestApi = Convert.fromDiscoverMdSchemaMembers(requestWs);
		List<MdSchemaMembersResponseRow> responseApi = xmlaService.discover().mdSchemaMembers(requestApi);
		return Convert.toDiscoverMdSchemaMembers(responseApi);
	}

	private DiscoverResponse handleDiscoverMdSchemaProperties(Discover requestWs) throws JAXBException, IOException {

		MdSchemaPropertiesRequest requestApi = Convert.fromDiscoverMdSchemaProperties(requestWs);
		List<MdSchemaPropertiesResponseRow> responseApi = xmlaService.discover().mdSchemaProperties(requestApi);
		return Convert.toDiscoverMdSchemaProperties(responseApi);
	}

	private DiscoverResponse handleDiscoverMdSchemaSets(Discover requestWs) throws JAXBException, IOException {

		MdSchemaSetsRequest requestApi = Convert.fromDiscoverMdSchemaSets(requestWs);
		List<MdSchemaSetsResponseRow> responseApi = xmlaService.discover().mdSchemaSets(requestApi);
		return Convert.toDiscoverMdSchemaSets(responseApi);
	}

	private DiscoverResponse handleDiscoverMdSchemaKpis(Discover requestWs) throws JAXBException, IOException {

		MdSchemaKpisRequest requestApi = Convert.fromDiscoverMdSchemaKpis(requestWs);
		List<MdSchemaKpisResponseRow> responseApi = xmlaService.discover().mdSchemaKpis(requestApi);
		return Convert.toDiscoverMdSchemaKpis(responseApi);
	}

	private DiscoverResponse handleDiscoverMdSchemaMeasureGroups(Discover requestWs) throws JAXBException, IOException {

		MdSchemaMeasureGroupsRequest requestApi = Convert.fromDiscoverMdSchemaMeasureGroups(requestWs);
		List<MdSchemaMeasureGroupsResponseRow> responseApi = xmlaService.discover().mdSchemaMeasureGroups(requestApi);
		return Convert.toDiscoverMdSchemaMeasureGroups(responseApi);
	}

	private DiscoverResponse handleDiscoverDbSchemaSourceTables(Discover requestWs) throws JAXBException, IOException {

		DbSchemaSourceTablesRequest requestApi = Convert.fromDiscoverDbSchemaSourceTables(requestWs);
		List<DbSchemaSourceTablesResponseRow> responseApi = xmlaService.discover().dbSchemaSourceTables(requestApi);
		return Convert.toDiscoverDbSchemaSourceTables(responseApi);
	}

	private DiscoverResponse handleDiscoverDbSchemaTablesInfo(Discover requestWs) throws JAXBException, IOException {

		DbSchemaTablesInfoRequest requestApi = Convert.fromDiscoverDbSchemaTablesInfo(requestWs);
		List<DbSchemaTablesInfoResponseRow> responseApi = xmlaService.discover().dbSchemaTablesInfo(requestApi);
		return Convert.toDiscoverDbSchemaTablesInfo(responseApi);
	}

}
