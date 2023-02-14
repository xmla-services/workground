package org.eclipse.daanse.xmla.demo.server;

import java.util.List;

import org.eclipse.daanse.xmla.api.discover.DiscoverService;
import org.eclipse.daanse.xmla.api.discover.dbschema.catalogs.DbSchemaCatalogsRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.catalogs.DbSchemaCatalogsResponse;
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

public class DemoDiscoverService implements DiscoverService {

    @Override
    public DbSchemaCatalogsResponse dbSchemaCatalogs(DbSchemaCatalogsRequest dbSchemaCatalogsRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DbSchemaTablesResponseRow> dbSchemaTables(DbSchemaTablesRequest dbSchemaTablesRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DiscoverEnumeratorsResponseRow> discoverEnumerators(
            DiscoverEnumeratorsRequest discoverEnumeratorsRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DiscoverKeywordsResponseRow> discoverKeywords(DiscoverKeywordsRequest discoverKeywordsRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DiscoverLiteralsResponseRow> discoverLiterals(DiscoverLiteralsRequest discoverLiteralsRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DiscoverPropertiesResponseRow> discoverProperties(DiscoverPropertiesRequest discoverPropertiesRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DiscoverSchemaRowsetsResponseRow> discoverSchemaRowsets(
            DiscoverSchemaRowsetsRequest discoverSchemaRowsetsRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MdSchemaActionsResponseRow> mdSchemaActions(MdSchemaActionsRequest mdSchemaActionsRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MdSchemaCubesResponseRow> mdSchemaCubes(MdSchemaCubesRequest mdSchemaCubesRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MdSchemaDimensionsResponseRow> mdSchemaDimensions(MdSchemaDimensionsRequest mdSchemaDimensionsRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MdSchemaFunctionsResponseRow> mdSchemaFunctions(MdSchemaFunctionsRequest mdSchemaFunctionsRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MdSchemaHierarchiesResponseRow> mdSchemaHierarchies(MdSchemaHierarchiesRequest requestApi) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DiscoverDataSourcesResponseRow> dataSources(DiscoverDataSourcesRequest requestApi) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DiscoverXmlMetaDataResponseRow> xmlMetaData(DiscoverXmlMetaDataRequest requestApi) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DbSchemaColumnsResponseRow> dbSchemaColumns(DbSchemaColumnsRequest requestApi) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DbSchemaProviderTypesResponseRow> dbSchemaProviderTypes(DbSchemaProviderTypesRequest requestApi) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DbSchemaSchemataResponseRow> dbSchemaSchemata(DbSchemaSchemataRequest requestApi) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MdSchemaLevelsResponseRow> mdSchemaLevels(MdSchemaLevelsRequest requestApi) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MdSchemaMeasureGroupDimensionsResponseRow> mdSchemaMeasureGroupDimensions(
            MdSchemaMeasureGroupDimensionsRequest requestApi) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MdSchemaMeasuresResponseRow> mdSchemaMeasures(MdSchemaMeasuresRequest requestApi) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MdSchemaMembersResponseRow> mdSchemaMembers(MdSchemaMembersRequest requestApi) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MdSchemaPropertiesResponseRow> mdSchemaProperties(MdSchemaPropertiesRequest requestApi) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MdSchemaSetsResponseRow> mdSchemaSets(MdSchemaSetsRequest requestApi) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MdSchemaKpisResponseRow> mdSchemaKpis(MdSchemaKpisRequest requestApi) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MdSchemaMeasureGroupsResponseRow> mdSchemaMeasureGroups(MdSchemaMeasureGroupsRequest requestApi) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DbSchemaSourceTablesResponseRow> dbSchemaSourceTables(DbSchemaSourceTablesRequest requestApi) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DbSchemaTablesInfoResponseRow> dbSchemaTablesInfo(DbSchemaTablesInfoRequest requestApi) {
        // TODO Auto-generated method stub
        return null;
    }

}
