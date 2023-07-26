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
package org.eclipse.daanse.xmla.ws.jakarta.provider.soapmessage;

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import org.eclipse.daanse.xmla.api.discover.dbschema.catalogs.DbSchemaCatalogsResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.columns.DbSchemaColumnsResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.providertypes.DbSchemaProviderTypesResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.schemata.DbSchemaSchemataResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.sourcetables.DbSchemaSourceTablesResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.tables.DbSchemaTablesResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.tablesinfo.DbSchemaTablesInfoResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.enumerators.DiscoverEnumeratorsResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.keywords.DiscoverKeywordsResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.literals.DiscoverLiteralsResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.properties.DiscoverPropertiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.schemarowsets.DiscoverSchemaRowsetsResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.xmlmetadata.DiscoverXmlMetaDataResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.actions.MdSchemaActionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.cubes.MdSchemaCubesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.functions.MdSchemaFunctionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.functions.ParameterInfo;
import org.eclipse.daanse.xmla.api.discover.mdschema.hierarchies.MdSchemaHierarchiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.kpis.MdSchemaKpisResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.levels.MdSchemaLevelsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.measuregroupdimensions.MdSchemaMeasureGroupDimensionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.measuregroupdimensions.MeasureGroupDimension;
import org.eclipse.daanse.xmla.api.discover.mdschema.measuregroups.MdSchemaMeasureGroupsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.measures.MdSchemaMeasuresResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.members.MdSchemaMembersResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.properties.MdSchemaPropertiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.sets.MdSchemaSetsResponseRow;
import org.eclipse.daanse.xmla.api.engine200.WarningColumn;
import org.eclipse.daanse.xmla.api.engine200.WarningLocationObject;
import org.eclipse.daanse.xmla.api.engine200.WarningMeasure;
import org.eclipse.daanse.xmla.api.exception.ErrorType;
import org.eclipse.daanse.xmla.api.exception.Exception;
import org.eclipse.daanse.xmla.api.exception.MessageLocation;
import org.eclipse.daanse.xmla.api.exception.Messages;
import org.eclipse.daanse.xmla.api.exception.StartEnd;
import org.eclipse.daanse.xmla.api.exception.WarningType;
import org.eclipse.daanse.xmla.api.execute.statement.StatementResponse;
import org.eclipse.daanse.xmla.api.mddataset.Axes;
import org.eclipse.daanse.xmla.api.mddataset.AxesInfo;
import org.eclipse.daanse.xmla.api.mddataset.Axis;
import org.eclipse.daanse.xmla.api.mddataset.AxisInfo;
import org.eclipse.daanse.xmla.api.mddataset.CellData;
import org.eclipse.daanse.xmla.api.mddataset.CellInfo;
import org.eclipse.daanse.xmla.api.mddataset.CellInfoItem;
import org.eclipse.daanse.xmla.api.mddataset.CellSetType;
import org.eclipse.daanse.xmla.api.mddataset.CellType;
import org.eclipse.daanse.xmla.api.mddataset.CellTypeError;
import org.eclipse.daanse.xmla.api.mddataset.CubeInfo;
import org.eclipse.daanse.xmla.api.mddataset.HierarchyInfo;
import org.eclipse.daanse.xmla.api.mddataset.Mddataset;
import org.eclipse.daanse.xmla.api.mddataset.MemberType;
import org.eclipse.daanse.xmla.api.mddataset.MembersType;
import org.eclipse.daanse.xmla.api.mddataset.NormTupleSet;
import org.eclipse.daanse.xmla.api.mddataset.OlapInfo;
import org.eclipse.daanse.xmla.api.mddataset.OlapInfoCube;
import org.eclipse.daanse.xmla.api.mddataset.SetListType;
import org.eclipse.daanse.xmla.api.mddataset.TupleType;
import org.eclipse.daanse.xmla.api.mddataset.TuplesType;
import org.eclipse.daanse.xmla.api.mddataset.Type;
import org.eclipse.daanse.xmla.api.mddataset.Union;
import org.eclipse.daanse.xmla.api.mddataset.Value;
import org.eclipse.daanse.xmla.api.msxmla.MemberRef;
import org.eclipse.daanse.xmla.api.msxmla.MembersLookup;
import org.eclipse.daanse.xmla.api.msxmla.NormTuple;
import org.eclipse.daanse.xmla.api.msxmla.NormTuplesType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.daanse.xmla.ws.jakarta.provider.soapmessage.Constants.ROW;

public class SoapUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SoapUtil.class);

    public static SOAPBody toDiscoverProperties(List<DiscoverPropertiesResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addDiscoverPropertiesResponseRow(root, r)
        );
        return body;
    }

    public static SOAPBody toMdSchemaFunctions(List<MdSchemaFunctionsResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addMdSchemaFunctionsResponseRow(root, r)
        );
        return body;
    }

    public static SOAPBody toMdSchemaDimensions(List<MdSchemaDimensionsResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addMdSchemaDimensionsResponseRow(root, r)
        );
        return body;
    }
    public static SOAPBody toMdSchemaCubes(List<MdSchemaCubesResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addMdSchemaCubesResponseRow(root, r)
        );
        return body;
    }
    public static SOAPBody toMdSchemaMeasureGroups(List<MdSchemaMeasureGroupsResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addMdSchemaMeasureGroupsResponseRow(root, r)
        );
        return body;
    }

    public static SOAPBody toMdSchemaKpis(List<MdSchemaKpisResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addMdSchemaKpisResponseRow(root, r)
        );
        return body;
    }

    public static SOAPBody toMdSchemaSets(List<MdSchemaSetsResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addMdSchemaSetsResponseRow(root, r)
        );
        return body;
    }
    public static SOAPBody toMdSchemaProperties(List<MdSchemaPropertiesResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addMdSchemaPropertiesResponseRow(root, r)
        );
        return body;
    }

    public static SOAPBody toMdSchemaMembers(List<MdSchemaMembersResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addMdSchemaMembersResponseRow(root, r)
        );
        return body;
    }

    public static SOAPBody toMdSchemaMeasures(List<MdSchemaMeasuresResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addMdSchemaMeasuresResponseRow(root, r)
        );
        return body;
    }

    public static SOAPBody toMdSchemaMeasureGroupDimensions(List<MdSchemaMeasureGroupDimensionsResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addMdSchemaMeasureGroupDimensionsResponseRow(root, r)
        );
        return body;
    }

    public static SOAPBody toMdSchemaLevels(List<MdSchemaLevelsResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addMdSchemaLevelsResponseRow(root, r)
        );
        return body;
    }

    public static SOAPBody toMdSchemaHierarchies(List<MdSchemaHierarchiesResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addMdSchemaHierarchiesResponseRow(root, r)
        );
        return body;
    }

    public static SOAPBody toDbSchemaTablesInfo(List<DbSchemaTablesInfoResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addDbSchemaTablesInfoResponseRow(root, r)
        );
        return body;
    }

    public static SOAPBody toDbSchemaSourceTables(List<DbSchemaSourceTablesResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addDbSchemaSourceTablesResponseRow(root, r)
        );
        return body;
    }

    public static SOAPBody toDbSchemaSchemata(List<DbSchemaSchemataResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addDbSchemaSchemataResponseRow(root, r)
        );
        return body;
    }

    public static SOAPBody toDbSchemaProviderTypes(List<DbSchemaProviderTypesResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addDbSchemaProviderTypesResponseRow(root, r)
        );
        return body;
    }

    public static SOAPBody toDbSchemaColumns(List<DbSchemaColumnsResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addDbSchemaColumnsResponseRow(root, r)
        );
        return body;
    }

    public static SOAPBody toDiscoverXmlMetaData(List<DiscoverXmlMetaDataResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addDiscoverXmlMetaDataResponseRow(root, r)
        );
        return body;
    }

    public static SOAPBody toDiscoverDataSources(List<DiscoverDataSourcesResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addDiscoverDataSourcesResponseRow(root, r)
        );
        return body;
    }

    public static SOAPBody toDbSchemaCatalogs(List<DbSchemaCatalogsResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addDbSchemaCatalogsResponseRow(root, r)
        );
        return body;
    }

    public static SOAPBody toDiscoverSchemaRowsets(List<DiscoverSchemaRowsetsResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addDiscoverSchemaRowsetsResponseRow(root, r)
        );
        return body;
    }

    public static SOAPBody toDiscoverEnumerators(List<DiscoverEnumeratorsResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addDiscoverEnumeratorsResponseRow(root, r)
        );
        return body;
    }

    public static SOAPBody toDiscoverKeywords(List<DiscoverKeywordsResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addDiscoverKeywordsResponseRow(root, r)
        );
        return body;
    }

    public static SOAPBody toDiscoverLiterals(List<DiscoverLiteralsResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addDiscoverLiteralsResponseRow(root, r)
        );
        return body;
    }

    public static SOAPBody toDbSchemaTables(List<DbSchemaTablesResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addDbSchemaTablesResponseRow(root, r)
        );
        return body;
    }

    public static SOAPBody toMdSchemaActions(List<MdSchemaActionsResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addMdSchemaActionsResponseRow(root, r)
        );
        return body;
    }

    private static void addMdSchemaActionsResponseRow(SOAPElement root, MdSchemaActionsResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.actionName().ifPresent(v -> addChildElement(row, "ACTION_NAME", v));
        r.actionType().ifPresent(v -> addChildElement(row, "ACTION_TYPE", String.valueOf(v.getValue())));
        addChildElement(row, "COORDINATE", r.coordinate());
        addChildElement(row, "COORDINATE_TYPE", String.valueOf(r.coordinateType().getValue()));
        r.actionCaption().ifPresent(v -> addChildElement(row, "ACTION_CAPTION", v));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.content().ifPresent(v -> addChildElement(row, "CONTENT", v));
        r.application().ifPresent(v -> addChildElement(row, "APPLICATION", v));
        r.invocation().ifPresent(v -> addChildElement(row, "INVOCATION", String.valueOf(v.getValue())));
    }

    private static void addDbSchemaTablesResponseRow(SOAPElement root, DbSchemaTablesResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.tableCatalog().ifPresent(v -> addChildElement(row, "TABLE_CATALOG", v));
        r.tableSchema().ifPresent(v -> addChildElement(row, "TABLE_SCHEMA", v));
        r.tableName().ifPresent(v -> addChildElement(row, "TABLE_NAME", v));
        r.tableType().ifPresent(v -> addChildElement(row, "TABLE_TYPE", v));
        r.tableGuid().ifPresent(v -> addChildElement(row, "TABLE_GUID", v));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.tablePropId().ifPresent(v -> addChildElement(row, "TABLE_PROP_ID", String.valueOf(v)));
        r.dateCreated().ifPresent(v -> addChildElement(row, "DATE_CREATED", String.valueOf(v)));
        r.dateModified().ifPresent(v -> addChildElement(row, "DATE_MODIFIED", String.valueOf(v)));
    }

    private static void addDiscoverLiteralsResponseRow(SOAPElement root, DiscoverLiteralsResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        addChildElement(row, "LiteralName", r.literalName());
        addChildElement(row, "LiteralValue", r.literalValue());
        addChildElement(row, "LiteralInvalidChars", r.literalInvalidChars());
        addChildElement(row, "LiteralInvalidStartingChars", r.literalInvalidStartingChars());
        addChildElement(row, "LiteralMaxLength", String.valueOf(r.literalMaxLength()));
        addChildElement(row, "LiteralNameValue", String.valueOf(r.literalNameEnumValue().getValue()));
    }

    private static void addDiscoverKeywordsResponseRow(SOAPElement root, DiscoverKeywordsResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        addChildElement(row, "Keyword", r.keyword());
    }

    private static void addDiscoverEnumeratorsResponseRow(SOAPElement root, DiscoverEnumeratorsResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        addChildElement(row, "EnumName", r.elementName());
        r.elementDescription().ifPresent(v -> addChildElement(row, "EnumDescription", v));
        addChildElement(row, "EnumType", r.enumType());
        addChildElement(row, "ElementName", r.elementName());
        r.elementDescription().ifPresent(v -> addChildElement(row, "ElementDescription", v));
        r.elementValue().ifPresent(v -> addChildElement(row, "ElementValue", v));
    }

    private static void addDiscoverSchemaRowsetsResponseRow(SOAPElement root, DiscoverSchemaRowsetsResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        addChildElement(row, "SchemaName", r.schemaName());
        r.schemaGuid().ifPresent(v -> addChildElement(row, "SchemaGuid", v));
        r.restrictions().ifPresent(v -> addChildElement(row, "Restrictions", v));
        r.restrictions().ifPresent(v -> addChildElement(row, "Restrictions", v));
        r.description().ifPresent(v -> addChildElement(row, "Description", v));
        r.restrictionsMask().ifPresent(v -> addChildElement(row, "Description", String.valueOf(v)));
    }

    private static void addDbSchemaCatalogsResponseRow(SOAPElement root, DbSchemaCatalogsResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.catalogName().ifPresent(v -> addChildElement(row, "CATALOG_NAME", v));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.roles().ifPresent(v -> addChildElement(row, "ROLES", v));
        r.dateModified().ifPresent(v -> addChildElement(row, "DATE_MODIFIED", String.valueOf(v)));
        r.compatibilityLevel().ifPresent(v -> addChildElement(row, "COMPATIBILITY_LEVEL", String.valueOf(v)));
        r.type().ifPresent(v -> addChildElement(row, "TYPE", String.valueOf(v.getValue())));
        r.version().ifPresent(v -> addChildElement(row, "VERSION", String.valueOf(v)));
        r.databaseId().ifPresent(v -> addChildElement(row, "DATABASE_ID", v));
        r.dateQueried().ifPresent(v -> addChildElement(row, "DATE_QUERIED", String.valueOf(v)));
        r.currentlyUsed().ifPresent(v -> addChildElement(row, "CURRENTLY_USED", String.valueOf(v)));
        r.popularity().ifPresent(v -> addChildElement(row, "POPULARITY", String.valueOf(v)));
        r.weightedPopularity().ifPresent(v -> addChildElement(row, "WEIGHTEDPOPULARITY", String.valueOf(v)));
        r.clientCacheRefreshPolicy().ifPresent(v -> addChildElement(row, "CLIENTCACHEREFRESHPOLICY",
            String.valueOf(v.getValue())));
    }

    private static void addDiscoverDataSourcesResponseRow(SOAPElement root, DiscoverDataSourcesResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        addChildElement(row, "DataSourceName", r.dataSourceName());
        r.dataSourceDescription().ifPresent(v -> addChildElement(row, "DataSourceDescription", v));
        r.url().ifPresent(v -> addChildElement(row, "URL", v));
        r.dataSourceInfo().ifPresent(v -> addChildElement(row, "DataSourceInfo", v));
        addChildElement(row, "ProviderName", r.providerName());
        r.providerType().ifPresent(v -> addChildElement(row, "ProviderType", v.name()));
        r.authenticationMode().ifPresent(v -> addChildElement(row, "AuthenticationMode", v.name()));
    }

    private static void addDiscoverXmlMetaDataResponseRow(SOAPElement root, DiscoverXmlMetaDataResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        addChildElement(row, "MetaData", r.metaData());
    }

    private static void addDbSchemaColumnsResponseRow(SOAPElement root, DbSchemaColumnsResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.tableCatalog().ifPresent(v -> addChildElement(row, "TABLE_CATALOG", v));
        r.tableSchema().ifPresent(v -> addChildElement(row, "TABLE_SCHEMA", v));
        r.tableName().ifPresent(v -> addChildElement(row, "TABLE_NAME", v));
        r.columnName().ifPresent(v -> addChildElement(row, "COLUMN_NAME", v));
        r.columnGuid().ifPresent(v -> addChildElement(row, "COLUMN_GUID", String.valueOf(v)));
        r.columnPropId().ifPresent(v -> addChildElement(row, "COLUMN_PROPID", String.valueOf(v)));
        r.ordinalPosition().ifPresent(v -> addChildElement(row, "ORDINAL_POSITION", String.valueOf(v)));
        r.columnHasDefault().ifPresent(v -> addChildElement(row, "COLUMN_HAS_DEFAULT", String.valueOf(v)));
        r.columnDefault().ifPresent(v -> addChildElement(row, "COLUMN_DEFAULT", v));
        r.columnFlags().ifPresent(v -> addChildElement(row, "COLUMN_FLAG", String.valueOf(v.getValue())));
        r.isNullable().ifPresent(v -> addChildElement(row, "IS_NULLABLE", String.valueOf(v)));
        r.dataType().ifPresent(v -> addChildElement(row, "DATA_TYPE", String.valueOf(v)));
        r.typeGuid().ifPresent(v -> addChildElement(row, "TYPE_GUID", String.valueOf(v)));
        r.characterMaximum().ifPresent(v -> addChildElement(row, "CHARACTER_MAXIMUM_LENGTH", String.valueOf(v)));
        r.characterOctetLength().ifPresent(v -> addChildElement(row, "CHARACTER_OCTET_LENGTH", String.valueOf(v)));
        r.numericPrecision().ifPresent(v -> addChildElement(row, "NUMERIC_PRECISION", String.valueOf(v)));
        r.dateTimePrecision().ifPresent(v -> addChildElement(row, "DATETIME_PRECISION", String.valueOf(v)));
        r.characterSetCatalog().ifPresent(v -> addChildElement(row, "CHARACTER_SET_CATALOG", v));
        r.characterSetSchema().ifPresent(v -> addChildElement(row, "CHARACTER_SET_SCHEMA", v));
        r.characterSetName().ifPresent(v -> addChildElement(row, "CHARACTER_SET_NAME", v));
        r.collationCatalog().ifPresent(v -> addChildElement(row, "COLLATION_CATALOG", v));
        r.collationSchema().ifPresent(v -> addChildElement(row, "COLLATION_SCHEMA", v));
        r.collationName().ifPresent(v -> addChildElement(row, "COLLATION_NAME", v));
        r.domainCatalog().ifPresent(v -> addChildElement(row, "DOMAIN_CATALOG", v));
        r.domainSchema().ifPresent(v -> addChildElement(row, "DOMAIN_SCHEMA", v));
        r.domainName().ifPresent(v -> addChildElement(row, "DOMAIN_NAME", v));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.columnOlapType().ifPresent(v -> addChildElement(row, "COLUMN_OLAP_TYPE", v.name()));
    }

    private static void addDbSchemaProviderTypesResponseRow(SOAPElement root, DbSchemaProviderTypesResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.typeName().ifPresent(v -> addChildElement(row, "TYPE_NAME", v));
        r.dataType().ifPresent(v -> addChildElement(row, "DATA_TYPE", String.valueOf(v.getValue())));
        r.columnSize().ifPresent(v -> addChildElement(row, "COLUMN_SIZE", String.valueOf(v)));
        r.literalPrefix().ifPresent(v -> addChildElement(row, "LITERAL_PREFIX", v));
        r.literalSuffix().ifPresent(v -> addChildElement(row, "LITERAL_SUFFIX", v));
        r.createParams().ifPresent(v -> addChildElement(row, "CREATE_PARAMS", v));
        r.isNullable().ifPresent(v -> addChildElement(row, "IS_NULLABLE", String.valueOf(v)));
        r.caseSensitive().ifPresent(v -> addChildElement(row, "CASE_SENSITIVE", String.valueOf(v)));
        r.searchable().ifPresent(v -> addChildElement(row, "SEARCHABLE", String.valueOf(v.getValue())));
        r.unsignedAttribute().ifPresent(v -> addChildElement(row, "UNSIGNED_ATTRIBUTE", String.valueOf(v)));
        r.fixedPrecScale().ifPresent(v -> addChildElement(row, "FIXED_PREC_SCALE", String.valueOf(v)));
        r.autoUniqueValue().ifPresent(v -> addChildElement(row, "AUTO_UNIQUE_VALUE", String.valueOf(v)));
        r.localTypeName().ifPresent(v -> addChildElement(row, "LOCAL_TYPE_NAME", v));
        r.minimumScale().ifPresent(v -> addChildElement(row, "MINIMUM_SCALE", String.valueOf(v)));
        r.maximumScale().ifPresent(v -> addChildElement(row, "MAXIMUM_SCALE", String.valueOf(v)));
        r.guid().ifPresent(v -> addChildElement(row, "GUID", String.valueOf(v)));
        r.typeLib().ifPresent(v -> addChildElement(row, "TYPE_LIB", v));
        r.version().ifPresent(v -> addChildElement(row, "VERSION", v));
        r.isLong().ifPresent(v -> addChildElement(row, "IS_LONG", String.valueOf(v)));
        r.bestMatch().ifPresent(v -> addChildElement(row, "BEST_MATCH", String.valueOf(v)));
        r.isFixedLength().ifPresent(v -> addChildElement(row, "IS_FIXEDLENGTH", String.valueOf(v)));
    }

    private static void addDbSchemaSchemataResponseRow(SOAPElement root, DbSchemaSchemataResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        addChildElement(row, "CATALOG_NAME", r.catalogName());
        addChildElement(row, "SCHEMA_NAME", r.schemaName());
        addChildElement(row, "SCHEMA_OWNER", r.schemaOwner());
    }

    private static void addDbSchemaSourceTablesResponseRow(SOAPElement root, DbSchemaSourceTablesResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.catalogName().ifPresent(v -> addChildElement(row, "TABLE_CATALOG", v));
        r.schemaName().ifPresent(v -> addChildElement(row, "TABLE_SCHEMA", v));
        addChildElement(row, "TABLE_NAME", r.tableName());
        addChildElement(row, "TABLE_TYPE", r.tableType().getValue());
    }

    private static void addDbSchemaTablesInfoResponseRow(SOAPElement root, DbSchemaTablesInfoResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.catalogName().ifPresent(v -> addChildElement(row, "TABLE_CATALOG", v));
        r.schemaName().ifPresent(v -> addChildElement(row, "TABLE_SCHEMA", v));
        addChildElement(row, "TABLE_NAME", r.tableName());
        addChildElement(row, "TABLE_TYPE", r.tableType());
        r.tableGuid().ifPresent(v -> addChildElement(row, "TABLE_TYPE", String.valueOf(v)));
        r.bookmarks().ifPresent(v -> addChildElement(row, "BOOKMARKS", String.valueOf(v)));
        r.bookmarkType().ifPresent(v -> addChildElement(row, "BOOKMARK_TYPE", String.valueOf(v)));
        r.bookmarkDataType().ifPresent(v -> addChildElement(row, "BOOKMARK_DATA_TYPE", String.valueOf(v)));
        r.bookmarkMaximumLength().ifPresent(v -> addChildElement(row, "BOOKMARK_MAXIMUM_LENGTH", String.valueOf(v)));
        r.bookmarkInformation().ifPresent(v -> addChildElement(row, "BOOKMARK_INFORMATION", String.valueOf(v)));
        r.tableVersion().ifPresent(v -> addChildElement(row, "TABLE_VERSION", String.valueOf(v)));
        r.cardinality().ifPresent(v -> addChildElement(row, "CARDINALITY", String.valueOf(v)));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.tablePropId().ifPresent(v -> addChildElement(row, "TABLE_PROP_ID", String.valueOf(v)));
    }

    private static void addMdSchemaHierarchiesResponseRow(SOAPElement root, MdSchemaHierarchiesResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.catalogName().ifPresent(v -> addChildElement(row, "CATALOG_NAME", v));
        r.schemaName().ifPresent(v -> addChildElement(row, "SCHEMA_NAME", v));
        r.cubeName().ifPresent(v -> addChildElement(row, "CUBE_NAME", v));
        r.dimensionUniqueName().ifPresent(v -> addChildElement(row, "DIMENSION_UNIQUE_NAME", v));
        r.hierarchyName().ifPresent(v -> addChildElement(row, "HIERARCHY_NAME", v));
        r.hierarchyUniqueName().ifPresent(v -> addChildElement(row, "HIERARCHY_UNIQUE_NAME", v));
        r.hierarchyGuid().ifPresent(v -> addChildElement(row, "HIERARCHY_GUID", String.valueOf(v)));
        r.hierarchyCaption().ifPresent(v -> addChildElement(row, "HIERARCHY_CAPTION", v));
        r.dimensionType().ifPresent(v -> addChildElement(row, "DIMENSION_TYPE", String.valueOf(v.getValue())));
        r.hierarchyCardinality().ifPresent(v -> addChildElement(row, "HIERARCHY_CARDINALITY", String.valueOf(v)));
        r.defaultMember().ifPresent(v -> addChildElement(row, "DEFAULT_MEMBER", v));
        r.allMember().ifPresent(v -> addChildElement(row, "ALL_MEMBER", v));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.structure().ifPresent(v -> addChildElement(row, "STRUCTURE", String.valueOf(v.getValue())));
        r.isVirtual().ifPresent(v -> addChildElement(row, "IS_VIRTUAL", String.valueOf(v)));
        r.isReadWrite().ifPresent(v -> addChildElement(row, "IS_READWRITE", String.valueOf(v)));
        r.dimensionUniqueSettings().ifPresent(v -> addChildElement(row, "DIMENSION_UNIQUE_SETTINGS",
            String.valueOf(v.getValue())));
        r.dimensionMasterUniqueName().ifPresent(v -> addChildElement(row, "DIMENSION_MASTER_UNIQUE_NAME", v));
        r.dimensionIsVisible().ifPresent(v -> addChildElement(row, "DIMENSION_IS_VISIBLE", String.valueOf(v)));
        r.hierarchyOrdinal().ifPresent(v -> addChildElement(row, "HIERARCHY_ORDINAL", String.valueOf(v)));
        r.dimensionIsShared().ifPresent(v -> addChildElement(row, "DIMENSION_IS_SHARED", String.valueOf(v)));
        r.hierarchyIsVisible().ifPresent(v -> addChildElement(row, "HIERARCHY_IS_VISIBLE", String.valueOf(v)));
        r.hierarchyOrigin().ifPresent(v -> addChildElement(row, "HIERARCHY_ORIGIN", String.valueOf(v)));
        r.hierarchyDisplayFolder().ifPresent(v -> addChildElement(row, "HIERARCHY_DISPLAY_FOLDER", v));
        r.instanceSelection().ifPresent(v -> addChildElement(row, "INSTANCE_SELECTION", String.valueOf(v.getValue())));
        r.groupingBehavior().ifPresent(v -> addChildElement(row, "GROUPING_BEHAVIOR", String.valueOf(v.getValue())));
        r.structureType().ifPresent(v -> addChildElement(row, "STRUCTURE_TYPE", String.valueOf(v.getValue())));
    }

    private static void addMdSchemaLevelsResponseRow(SOAPElement root, MdSchemaLevelsResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.catalogName().ifPresent(v -> addChildElement(row, "CATALOG_NAME", v));
        r.schemaName().ifPresent(v -> addChildElement(row, "SCHEMA_NAME", v));
        r.cubeName().ifPresent(v -> addChildElement(row, "CUBE_NAME", v));
        r.dimensionUniqueName().ifPresent(v -> addChildElement(row, "DIMENSION_UNIQUE_NAME", v));
        r.hierarchyUniqueName().ifPresent(v -> addChildElement(row, "HIERARCHY_UNIQUE_NAME", v));
        r.levelName().ifPresent(v -> addChildElement(row, "LEVEL_NAME", v));
        r.levelUniqueName().ifPresent(v -> addChildElement(row, "LEVEL_UNIQUE_NAME", v));
        r.levelGuid().ifPresent(v -> addChildElement(row, "LEVEL_GUID", String.valueOf(v)));
        r.levelCaption().ifPresent(v -> addChildElement(row, "LEVEL_CAPTION", v));
        r.levelNumber().ifPresent(v -> addChildElement(row, "LEVEL_NUMBER", String.valueOf(v)));
        r.levelCardinality().ifPresent(v -> addChildElement(row, "LEVEL_CARDINALITY", String.valueOf(v)));
        r.levelType().ifPresent(v -> addChildElement(row, "LEVEL_TYPE", String.valueOf(v.getValue())));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.customRollupSetting().ifPresent(v -> addChildElement(row, "CUSTOM_ROLLUP_SETTINGS",
            String.valueOf(v.getValue())));
        r.levelUniqueSettings().ifPresent(v -> addChildElement(row, "LEVEL_UNIQUE_SETTINGS",
            String.valueOf(v.getValue())));
        r.levelIsVisible().ifPresent(v -> addChildElement(row, "LEVEL_IS_VISIBLE", String.valueOf(v)));
        r.levelOrderingProperty().ifPresent(v -> addChildElement(row, "LEVEL_ORDERING_PROPERTY", v));
        r.levelDbType().ifPresent(v -> addChildElement(row, "LEVEL_DBTYPE", String.valueOf(v.getValue())));
        r.levelMasterUniqueName().ifPresent(v -> addChildElement(row, "LEVEL_MASTER_UNIQUE_NAME", v));
        r.levelNameSqlColumnName().ifPresent(v -> addChildElement(row, "LEVEL_NAME_SQL_COLUMN_NAME", v));
        r.levelKeySqlColumnName().ifPresent(v -> addChildElement(row, "LEVEL_KEY_SQL_COLUMN_NAME", v));
        r.levelUniqueNameSqlColumnName().ifPresent(v -> addChildElement(row, "LEVEL_UNIQUE_NAME_SQL_COLUMN_NAME", v));
        r.levelAttributeHierarchyName().ifPresent(v -> addChildElement(row, "LEVEL_ATTRIBUTE_HIERARCHY_NAME", v));
        r.levelKeyCardinality().ifPresent(v -> addChildElement(row, "LEVEL_KEY_CARDINALITY", String.valueOf(v)));
        r.levelOrigin().ifPresent(v -> addChildElement(row, "LEVEL_ORIGIN", String.valueOf(v.getValue())));
    }

    private static void addMdSchemaMeasureGroupDimensionsResponseRow(
        SOAPElement root,
        MdSchemaMeasureGroupDimensionsResponseRow r
    ) {
        SOAPElement row = addChildElement(root, ROW);
        r.catalogName().ifPresent(v -> addChildElement(row, "CATALOG_NAME", v));
        r.schemaName().ifPresent(v -> addChildElement(row, "SCHEMA_NAME", v));
        r.cubeName().ifPresent(v -> addChildElement(row, "CUBE_NAME", v));

        r.measureGroupName().ifPresent(v -> addChildElement(row, "MEASUREGROUP_NAME", v));
        r.measureGroupCardinality().ifPresent(v -> addChildElement(row, "MEASUREGROUP_CARDINALITY", v));
        r.dimensionUniqueName().ifPresent(v -> addChildElement(row, "DIMENSION_UNIQUE_NAME", v));
        r.dimensionCardinality().ifPresent(v -> addChildElement(row, "DIMENSION_CARDINALITY", v.name()));
        r.dimensionIsVisible().ifPresent(v -> addChildElement(row, "DIMENSION_IS_VISIBLE", String.valueOf(v)));
        r.dimensionIsFactDimension().ifPresent(v -> addChildElement(row, "DIMENSION_IS_FACT_DIMENSION",
            String.valueOf(v)));
        r.dimensionPath().ifPresent(v -> addMeasureGroupDimensionXmlList(row, v));
        r.dimensionGranularity().ifPresent(v -> addChildElement(row, "DIMENSION_GRANULARITY", v));
    }

    private static void addMeasureGroupDimensionXmlList(SOAPElement el, List<MeasureGroupDimension> list) {
        if (list != null) {
            SOAPElement e = addChildElement(el, "DIMENSION_PATH");
            list.forEach(it -> addMeasureGroupDimensionXml(e, it));
        }
    }

    private static void addMeasureGroupDimensionXml(SOAPElement el, MeasureGroupDimension it) {
        addChildElement(el, "MeasureGroupDimension", it.measureGroupDimension());
    }

    private static void addMdSchemaMeasuresResponseRow(SOAPElement root, MdSchemaMeasuresResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.catalogName().ifPresent(v -> addChildElement(row, "CATALOG_NAME", v));
        r.schemaName().ifPresent(v -> addChildElement(row, "SCHEMA_NAME", v));
        r.cubeName().ifPresent(v -> addChildElement(row, "CUBE_NAME", v));
        r.measureName().ifPresent(v -> addChildElement(row, "MEASURE_NAME", v));
        r.measureUniqueName().ifPresent(v -> addChildElement(row, "MEASURE_UNIQUE_NAME", v));
        r.measureCaption().ifPresent(v -> addChildElement(row, "MEASURE_CAPTION", v));
        r.measureGuid().ifPresent(v -> addChildElement(row, "MEASURE_GUID", String.valueOf(v)));
        r.measureAggregator().ifPresent(v -> addChildElement(row, "MEASURE_AGGREGATOR", String.valueOf(v.getValue())));
        r.dataType().ifPresent(v -> addChildElement(row, "DATA_TYPE", String.valueOf(v.getValue())));
        r.numericPrecision().ifPresent(v -> addChildElement(row, "NUMERIC_PRECISION", String.valueOf(v)));
        r.numericScale().ifPresent(v -> addChildElement(row, "NUMERIC_SCALE", String.valueOf(v)));
        r.measureUnits().ifPresent(v -> addChildElement(row, "MEASURE_UNITS", v));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.expression().ifPresent(v -> addChildElement(row, "EXPRESSION", v));
        r.measureIsVisible().ifPresent(v -> addChildElement(row, "MEASURE_IS_VISIBLE", String.valueOf(v)));
        r.levelsList().ifPresent(v -> addChildElement(row, "LEVELS_LIST", v));
        r.measureNameSqlColumnName().ifPresent(v -> addChildElement(row, "MEASURE_NAME_SQL_COLUMN_NAME", v));
        r.measureUnqualifiedCaption().ifPresent(v -> addChildElement(row, "MEASURE_UNQUALIFIED_CAPTION", v));
        r.measureGroupName().ifPresent(v -> addChildElement(row, "MEASUREGROUP_NAME", v));
        r.defaultFormatString().ifPresent(v -> addChildElement(row, "DEFAULT_FORMAT_STRING", v));
    }

    private static void addMdSchemaMembersResponseRow(SOAPElement root, MdSchemaMembersResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.catalogName().ifPresent(v -> addChildElement(row, "CATALOG_NAME", v));
        r.schemaName().ifPresent(v -> addChildElement(row, "SCHEMA_NAME", v));
        r.cubeName().ifPresent(v -> addChildElement(row, "CUBE_NAME", v));
        r.dimensionUniqueName().ifPresent(v -> addChildElement(row, "DIMENSION_UNIQUE_NAME", v));
        r.dimensionUniqueName().ifPresent(v -> addChildElement(row, "DIMENSION_UNIQUE_NAME", v));
        r.hierarchyUniqueName().ifPresent(v -> addChildElement(row, "HIERARCHY_UNIQUE_NAME", v));
        r.levelUniqueName().ifPresent(v -> addChildElement(row, "LEVEL_UNIQUE_NAME", v));
        r.levelNumber().ifPresent(v -> addChildElement(row, "LEVEL_NUMBER", String.valueOf(v)));
        r.memberOrdinal().ifPresent(v -> addChildElement(row, "MEMBER_ORDINAL", String.valueOf(v)));
        r.memberName().ifPresent(v -> addChildElement(row, "MEMBER_NAME", v));
        r.memberUniqueName().ifPresent(v -> addChildElement(row, "MEMBER_UNIQUE_NAME", v));
        r.memberType().ifPresent(v -> addChildElement(row, "MEMBER_TYPE", String.valueOf(v.getValue())));
        r.memberGuid().ifPresent(v -> addChildElement(row, "MEMBER_GUID", String.valueOf(v)));
        r.memberCaption().ifPresent(v -> addChildElement(row, "MEMBER_CAPTION", v));
        r.childrenCardinality().ifPresent(v -> addChildElement(row, "CHILDREN_CARDINALITY", String.valueOf(v)));
        r.parentLevel().ifPresent(v -> addChildElement(row, "PARENT_LEVEL", String.valueOf(v)));
        r.parentUniqueName().ifPresent(v -> addChildElement(row, "PARENT_UNIQUE_NAME", v));
        r.parentCount().ifPresent(v -> addChildElement(row, "PARENT_COUNT", String.valueOf(v)));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.expression().ifPresent(v -> addChildElement(row, "EXPRESSION", v));
        r.memberKey().ifPresent(v -> addChildElement(row, "MEMBER_KEY", v));
        r.isPlaceHolderMember().ifPresent(v -> addChildElement(row, "IS_PLACEHOLDERMEMBER", String.valueOf(v)));
        r.isDataMember().ifPresent(v -> addChildElement(row, "IS_DATAMEMBER", String.valueOf(v)));
        r.scope().ifPresent(v -> addChildElement(row, "SCOPE", String.valueOf(v.getValue())));
    }

    private static void addMdSchemaPropertiesResponseRow(SOAPElement root, MdSchemaPropertiesResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.catalogName().ifPresent(v -> addChildElement(row, "CATALOG_NAME", v));
        r.schemaName().ifPresent(v -> addChildElement(row, "SCHEMA_NAME", v));
        r.cubeName().ifPresent(v -> addChildElement(row, "CUBE_NAME", v));

        r.dimensionUniqueName().ifPresent(v -> addChildElement(row, "DIMENSION_UNIQUE_NAME", v));
        r.hierarchyUniqueName().ifPresent(v -> addChildElement(row, "HIERARCHY_UNIQUE_NAME", v));
        r.levelUniqueName().ifPresent(v -> addChildElement(row, "LEVEL_UNIQUE_NAME", v));
        r.memberUniqueName().ifPresent(v -> addChildElement(row, "MEMBER_UNIQUE_NAME", v));
        r.propertyType().ifPresent(v -> addChildElement(row, "PROPERTY_TYPE", String.valueOf(v.getValue())));
        r.propertyName().ifPresent(v -> addChildElement(row, "PROPERTY_NAME", v));
        r.propertyCaption().ifPresent(v -> addChildElement(row, "PROPERTY_CAPTION", v));
        r.dataType().ifPresent(v -> addChildElement(row, "DATA_TYPE", String.valueOf(v.getValue())));
        r.characterMaximumLength().ifPresent(v -> addChildElement(row, "CHARACTER_MAXIMUM_LENGTH", String.valueOf(v)));
        r.characterOctetLength().ifPresent(v -> addChildElement(row, "CHARACTER_OCTET_LENGTH", String.valueOf(v)));
        r.numericPrecision().ifPresent(v -> addChildElement(row, "NUMERIC_PRECISION", String.valueOf(v)));
        r.numericScale().ifPresent(v -> addChildElement(row, "NUMERIC_SCALE", String.valueOf(v)));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.propertyContentType().ifPresent(v -> addChildElement(row, "PROPERTY_CONTENT_TYPE",
            String.valueOf(v.getValue())));
        r.sqlColumnName().ifPresent(v -> addChildElement(row, "SQL_COLUMN_NAME", v));
        r.language().ifPresent(v -> addChildElement(row, "LANGUAGE", String.valueOf(v)));
        r.propertyOrigin().ifPresent(v -> addChildElement(row, "PROPERTY_ORIGIN", String.valueOf(v.getValue())));
        r.propertyAttributeHierarchyName().ifPresent(v -> addChildElement(row, "PROPERTY_ATTRIBUTE_HIERARCHY_NAME", v));
        r.propertyCardinality().ifPresent(v -> addChildElement(row, "PROPERTY_CARDINALITY", v.name()));
        r.propertyIsVisible().ifPresent(v -> addChildElement(row, "PROPERTY_IS_VISIBLE", String.valueOf(v)));
    }

    private static void addMdSchemaSetsResponseRow(SOAPElement root, MdSchemaSetsResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.catalogName().ifPresent(v -> addChildElement(row, "CATALOG_NAME", v));
        r.schemaName().ifPresent(v -> addChildElement(row, "SCHEMA_NAME", v));
        r.cubeName().ifPresent(v -> addChildElement(row, "CUBE_NAME", v));

        r.setName().ifPresent(v -> addChildElement(row, "SET_NAME", v));
        r.scope().ifPresent(v -> addChildElement(row, "SCOPE", String.valueOf(v.getValue())));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.expression().ifPresent(v -> addChildElement(row, "EXPRESSION", v));
        r.dimension().ifPresent(v -> addChildElement(row, "DIMENSIONS", v));
        r.setCaption().ifPresent(v -> addChildElement(row, "SET_CAPTION", v));
        r.setDisplayFolder().ifPresent(v -> addChildElement(row, "SET_DISPLAY_FOLDER", v));
        r.setEvaluationContext().ifPresent(v -> addChildElement(row, "SET_EVALUATION_CONTEXT",
            String.valueOf(v.getValue())));
    }

    private static void addMdSchemaKpisResponseRow(SOAPElement root, MdSchemaKpisResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.catalogName().ifPresent(v -> addChildElement(row, "CATALOG_NAME", v));
        r.schemaName().ifPresent(v -> addChildElement(row, "SCHEMA_NAME", v));
        r.cubeName().ifPresent(v -> addChildElement(row, "CUBE_NAME", v));

        r.measureGroupName().ifPresent(v -> addChildElement(row, "MEASUREGROUP_NAME", v));
        r.kpiName().ifPresent(v -> addChildElement(row, "KPI_NAME", v));
        r.kpiCaption().ifPresent(v -> addChildElement(row, "KPI_CAPTION", v));
        r.kpiDescription().ifPresent(v -> addChildElement(row, "KPI_DESCRIPTION", v));
        r.kpiDisplayFolder().ifPresent(v -> addChildElement(row, "KPI_DISPLAY_FOLDER", v));
        r.kpiGoal().ifPresent(v -> addChildElement(row, "KPI_GOAL", v));
        r.kpiStatus().ifPresent(v -> addChildElement(row, "KPI_STATUS", v));
        r.kpiTrend().ifPresent(v -> addChildElement(row, "KPI_TREND", v));
        r.kpiStatusGraphic().ifPresent(v -> addChildElement(row, "KPI_STATUS_GRAPHIC", v));
        r.kpiTrendGraphic().ifPresent(v -> addChildElement(row, "KPI_TREND_GRAPHIC", v));
        r.kpiWight().ifPresent(v -> addChildElement(row, "KPI_WEIGHT", v));
        r.kpiCurrentTimeMember().ifPresent(v -> addChildElement(row, "KPI_CURRENT_TIME_MEMBER", v));
        r.kpiParentKpiName().ifPresent(v -> addChildElement(row, "KPI_PARENT_KPI_NAME", v));
        r.annotation().ifPresent(v -> addChildElement(row, "ANNOTATIONS", v));
        r.scope().ifPresent(v -> addChildElement(row, "ANNOTATIONS", String.valueOf(v.getValue())));
    }

    private static void addMdSchemaMeasureGroupsResponseRow(SOAPElement root, MdSchemaMeasureGroupsResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.catalogName().ifPresent(v -> addChildElement(row, "CATALOG_NAME", v));
        r.schemaName().ifPresent(v -> addChildElement(row, "SCHEMA_NAME", v));
        r.cubeName().ifPresent(v -> addChildElement(row, "CUBE_NAME", v));

        r.measureGroupName().ifPresent(v -> addChildElement(row, "MEASUREGROUP_NAME", v));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.isWriteEnabled().ifPresent(v -> addChildElement(row, "IS_WRITE_ENABLED", String.valueOf(v)));
        r.measureGroupCaption().ifPresent(v -> addChildElement(row, "MEASUREGROUP_CAPTION", v));
    }

    private static void addMdSchemaCubesResponseRow(SOAPElement root, MdSchemaCubesResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        addChildElement(row, "CATALOG_NAME", r.catalogName());
        r.schemaName().ifPresent(v -> addChildElement(row, "SCHEMA_NAME", v));
        r.cubeName().ifPresent(v -> addChildElement(row, "CUBE_NAME", v));

        r.cubeType().ifPresent(v -> addChildElement(row, "CUBE_TYPE", v.name()));
        r.cubeGuid().ifPresent(v -> addChildElement(row, "CUBE_GUID", String.valueOf(v)));
        r.createdOn().ifPresent(v -> addChildElement(row, "CREATED_ON", String.valueOf(v)));
        r.lastSchemaUpdate().ifPresent(v -> addChildElement(row, "LAST_SCHEMA_UPDATE", String.valueOf(v)));
        r.schemaUpdatedBy().ifPresent(v -> addChildElement(row, "SCHEMA_UPDATED_BY", v));
        r.lastDataUpdate().ifPresent(v -> addChildElement(row, "LAST_DATA_UPDATE", String.valueOf(v)));
        r.dataUpdateDBy().ifPresent(v -> addChildElement(row, "DATA_UPDATED_BY", v));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.isDrillThroughEnabled().ifPresent(v -> addChildElement(row, "IS_DRILLTHROUGH_ENABLED", String.valueOf(v)));
        r.isLinkable().ifPresent(v -> addChildElement(row, "IS_LINKABLE", String.valueOf(v)));
        r.isWriteEnabled().ifPresent(v -> addChildElement(row, "IS_WRITE_ENABLED", String.valueOf(v)));
        r.isSqlEnabled().ifPresent(v -> addChildElement(row, "IS_SQL_ENABLED", String.valueOf(v)));
        r.cubeCaption().ifPresent(v -> addChildElement(row, "CUBE_CAPTION", v));
        r.baseCubeName().ifPresent(v -> addChildElement(row, "BASE_CUBE_NAME", v));
        r.cubeSource().ifPresent(v -> addChildElement(row, "CUBE_SOURCE", String.valueOf(v.getValue())));
        r.preferredQueryPatterns().ifPresent(v -> addChildElement(row, "PREFERRED_QUERY_PATTERNS",
            String.valueOf(v.getValue())));
    }

    private static void addMdSchemaDimensionsResponseRow(SOAPElement root, MdSchemaDimensionsResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.catalogName().ifPresent(v -> addChildElement(row, "CATALOG_NAME", v));
        r.schemaName().ifPresent(v -> addChildElement(row, "SCHEMA_NAME", v));
        r.cubeName().ifPresent(v -> addChildElement(row, "CUBE_NAME", v));

        r.dimensionName().ifPresent(v -> addChildElement(row, "DIMENSION_NAME", v));
        r.dimensionUniqueName().ifPresent(v -> addChildElement(row, "DIMENSION_UNIQUE_NAME", v));
        r.dimensionGuid().ifPresent(v -> addChildElement(row, "DIMENSION_GUID", String.valueOf(v)));
        r.dimensionUniqueName().ifPresent(v -> addChildElement(row, "DIMENSION_UNIQUE_NAME", v));
        r.dimensionCaption().ifPresent(v -> addChildElement(row, "DIMENSION_CAPTION", v));
        r.dimensionOptional().ifPresent(v -> addChildElement(row, "DIMENSION_ORDINAL", String.valueOf(v)));
        r.dimensionType().ifPresent(v -> addChildElement(row, "DIMENSION_TYPE", String.valueOf(v.getValue())));
        r.dimensionCardinality().ifPresent(v -> addChildElement(row, "DIMENSION_CARDINALITY", String.valueOf(v)));
        r.defaultHierarchy().ifPresent(v -> addChildElement(row, "DEFAULT_HIERARCHY", v));
        r.defaultHierarchy().ifPresent(v -> addChildElement(row, "DEFAULT_HIERARCHY", v));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.isVirtual().ifPresent(v -> addChildElement(row, "IS_VIRTUAL", String.valueOf(v)));
        r.isReadWrite().ifPresent(v -> addChildElement(row, "IS_READWRITE", String.valueOf(v)));
        r.dimensionUniqueSetting().ifPresent(v -> addChildElement(row, "DIMENSION_UNIQUE_SETTINGS",
            String.valueOf(v.getValue())));
        r.dimensionMasterName().ifPresent(v -> addChildElement(row, "DIMENSION_MASTER_NAME", v));
        r.dimensionIsVisible().ifPresent(v -> addChildElement(row, "DIMENSION_IS_VISIBLE", String.valueOf(v)));
    }

    private static void addMdSchemaFunctionsResponseRow(SOAPElement root, MdSchemaFunctionsResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.functionalName().ifPresent(v -> addChildElement(row, "FUNCTION_NAME", v));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        addChildElement(row, "PARAMETER_LIST", r.parameterList());
        r.returnType().ifPresent(v -> addChildElement(row, "RETURN_TYPE", String.valueOf(v)));
        r.origin().ifPresent(v -> addChildElement(row, "ORIGIN", String.valueOf(v.getValue())));
        r.interfaceName().ifPresent(v -> addChildElement(row, "INTERFACE_NAME", v.name()));
        r.libraryName().ifPresent(v -> addChildElement(row, "LIBRARY_NAME", v));
        r.dllName().ifPresent(v -> addChildElement(row, "DLL_NAME", v));
        r.helpFile().ifPresent(v -> addChildElement(row, "HELP_FILE", v));
        r.helpContext().ifPresent(v -> addChildElement(row, "HELP_CONTEXT", v));
        r.object().ifPresent(v -> addChildElement(row, "OBJECT", v));
        r.caption().ifPresent(v -> addChildElement(row, "CAPTION", v));
        r.parameterInfo().ifPresent(v -> addParameterInfoXmlList(row, v));
        r.directQueryPushable().ifPresent(v -> addChildElement(row, "DIRECTQUERY_PUSHABLE",
            String.valueOf(v.getValue())));
    }

    private static void addParameterInfoXmlList(SOAPElement root, List<ParameterInfo> list) {
        if (list != null) {
            list.forEach(it -> addParameterInfoXml(root, it));
        }
    }

    private static void addParameterInfoXml(SOAPElement root, ParameterInfo it) {
        SOAPElement el = addChildElement(root, "PARAMETERINFO");
        addChildElement(el, "NAME", it.name());
        addChildElement(el, "DESCRIPTION", it.description());
        addChildElement(el, "OPTIONAL", String.valueOf(it.optional()));
        addChildElement(el, "REPEATABLE", String.valueOf(it.repeatable()));
        addChildElement(el, "REPEATGROUP", String.valueOf(it.repeatGroup()));
    }

    private static void addDiscoverPropertiesResponseRow(SOAPElement root, DiscoverPropertiesResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        addChildElement(row, "PropertyName", r.propertyName());
        r.propertyDescription().ifPresent(v -> addChildElement(row, "PropertyDescription", v));
        r.propertyDescription().ifPresent(v -> addChildElement(row, "PropertyType", v));
        addChildElement(row, "PropertyAccessType", r.propertyAccessType());
        r.required().ifPresent(v -> addChildElement(row, "IsRequired", String.valueOf(v)));
        r.value().ifPresent(v -> addChildElement(row, "Value", v));
    }

    public static SOAPBody toStatementResponse(StatementResponse statementResponse) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        addMdDataSet(root, statementResponse.mdDataSet());
        return body;
    }

    private static void addMdDataSet(SOAPElement e, Mddataset it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "mddataset");
            addOlapInfo(el, it.olapInfo());
            addAxes(el, it.axes());
            addCellData(el, it.cellData());
            addException(el, it.exception());
            addMessages(el, it.messages());
        }
    }

    private static void addMessages(SOAPElement e, Messages it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Messages");
            addExceptionTypeList(el, it.warningOrError());

        }
    }

    private static void addExceptionTypeList(
        SOAPElement e,
        List<org.eclipse.daanse.xmla.api.exception.Type> list) {
        if (list != null) {
            list.forEach(it -> addExceptionType(e, it));
        }
    }

    private static void addExceptionType(SOAPElement e, org.eclipse.daanse.xmla.api.exception.Type it) {
        if (it != null) {
            if (it instanceof WarningType warningType) {
                addWarningType(e, warningType);
            }
            if (it instanceof ErrorType errorType) {
                addErrorType(e, errorType);
            }
        }
    }

    private static void addErrorType(SOAPElement e, ErrorType it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Error");

            addChildElement(el, "Callstack", it.callstack());
            addChildElement(el, "ErrorCode", String.valueOf(it.errorCode()));
            addMessageLocation(el, it.location());
            addChildElement(el, "Description", it.description());
            addChildElement(el, "Source", it.source());
            addChildElement(el, "HelpFile", it.helpFile());
        }
    }

    private static void addWarningType(SOAPElement e, WarningType it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Warning");
            addChildElement(el, "WarningCode", String.valueOf(it.warningCode()));
            addMessageLocation(el, it.location());
            addChildElement(el, "Description", it.description());
            addChildElement(el, "Source", it.source());
            addChildElement(el, "HelpFile", it.helpFile());
        }
    }

    private static void addMessageLocation(SOAPElement e, MessageLocation it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Location");
            addStartEnd(el, "Start", it.start());
            addStartEnd(el, "End", it.end());
            addChildElement(el, "LineOffset", String.valueOf(it.lineOffset()));
            addChildElement(el, "TextLength", String.valueOf(it.textLength()));
            addWarningLocationObject(el, "SourceObject", it.sourceObject());
            addWarningLocationObject(el, "DependsOnObject", it.dependsOnObject());
            addChildElement(el, "RowNumber", String.valueOf(it.rowNumber()));
        }

    }

    private static void addWarningLocationObject(SOAPElement e, String tagName, WarningLocationObject it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, tagName);
            addWarningColumn(el, it.warningColumn());
            addWarningMeasure(el, it.warningMeasure());
        }
    }

    private static void addWarningMeasure(SOAPElement e, WarningMeasure it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "WarningMeasure");
            addChildElement(el, "Cube", it.cube());
            addChildElement(el, "MeasureGroup", it.measureGroup());
            addChildElement(el, "MeasureName", it.measureName());
        }
    }

    private static void addWarningColumn(SOAPElement e, WarningColumn it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "WarningColumn");
            addChildElement(el, "Dimension", it.dimension());
            addChildElement(el, "Attribute", it.attribute());
        }
    }

    private static void addStartEnd(SOAPElement e, String tagName, StartEnd it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, tagName);
            addChildElement(el, "Line", String.valueOf(it.line()));
            addChildElement(el, "Column", String.valueOf(it.column()));
        }
    }


    private static void addException(SOAPElement e, Exception it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Exception");
        }
    }

    private static void addCellData(SOAPElement e, CellData it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "CellData");
            addCellTypeList(el, it.cell());
            addCellSetType(el, it.cellSet());
        }
    }

    private static void addCellSetType(SOAPElement e, CellSetType it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "CellSet");
            addDataList(el, it.data());
        }
    }

    private static void addDataList(SOAPElement el, List<byte[]> list) {
        if (list != null) {
            list.forEach(it -> addData(el, it));
        }
    }

    private static void addData(SOAPElement e, byte[] it) {
        if (it != null) {
            addChildElement(e, "Data", new String(it, UTF_8));
        }
    }

    private static void addCellTypeList(SOAPElement el, List<CellType> list) {
        if (list != null) {
            list.forEach(it -> addCellType(el, it));
        }
    }

    private static void addCellType(SOAPElement e, CellType it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Cell");
            addCellTypeValue(el, it.value());
            addCellInfoItemList(el, it.any());
            addChildElement(el, "CellOrdinal", String.valueOf(it.cellOrdinal()));
        }
    }

    private static void addCellTypeValue(SOAPElement e, Value it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Value");
            addCellTypeErrorList(el, it.error());
        }
    }

    private static void addCellTypeErrorList(SOAPElement el, List<CellTypeError> list) {
        if (list != null) {
            list.forEach(it -> addCellTypeError(el, it));
        }
    }

    private static void addCellTypeError(SOAPElement e, CellTypeError it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Error");
            addChildElement(el, "ErrorCode", String.valueOf(it.errorCode()));
            addChildElement(el, "Description", it.description());
        }
    }

    private static void addAxes(SOAPElement e, Axes it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Axes");
            addAxisList(el, it.axis());
        }
    }

    private static void addAxisList(SOAPElement e, List<Axis> list) {
        if (list != null) {
            list.forEach(it -> addAxis(e, it));
        }
    }

    private static void addAxis(SOAPElement e, Axis it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Axis");
            addTypeList(el, it.setType());
            addChildElement(el, "name", it.name());
        }
    }

    private static void addTypeList(SOAPElement e, List<Type> list) {
        if (list != null) {
            list.forEach(it -> addType(e, it));
        }
    }

    private static void addType(SOAPElement e, Type it) {
        if (it != null) {
            if (it instanceof MembersType membersType) {
                addMembersType(e, membersType);
            }
            if (it instanceof TuplesType tuplesType) {
                addTuplesType(e, tuplesType);
            }
            if (it instanceof SetListType setListType) {
                addSetListType(e, setListType);
            }
            if (it instanceof NormTupleSet normTupleSet) {
                addNormTupleSet(e, normTupleSet);
            }
            if (it instanceof Union union) {
                addUnion(e, union);
            }
        }
    }

    private static void addUnion(SOAPElement e, Union it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Union");
            addTypeList(el, it.setType());
        }
    }

    private static void addNormTupleSet(SOAPElement e, NormTupleSet it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "NormTupleSet");
            addNormTuplesType(el, it.normTuples());
            addTupleTypeList(el, it.membersLookup());
        }
    }

    private static void addTupleTypeList(SOAPElement e, MembersLookup list) {
        if (list != null) {
            SOAPElement el = addChildElement(e, "MembersLookup");
            if (list.members() != null) {
                list.members().forEach(it -> addTupleType(el, "Members", it));
            }
        }
    }

    private static void addNormTuplesType(SOAPElement e, NormTuplesType it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "NormTuples");
            addNormTupleList(el, it.normTuple());
        }
    }

    private static void addNormTupleList(SOAPElement el, List<NormTuple> list) {
        if (list  != null) {
            list.forEach(it -> addNormTuple(el, it));
        }
    }

    private static void addNormTuple(SOAPElement e, NormTuple it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "NormTuple");
            addMemberRefList(e, it.memberRef());
        }
    }

    private static void addMemberRefList(SOAPElement e, List<MemberRef> list) {
        if (list  != null) {
            list.forEach(it -> addMemberRef(e, it));
        }
    }

    private static void addMemberRef(SOAPElement e, MemberRef it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "MemberRef");
            addChildElement(el, "MemberOrdinal", String.valueOf(it.memberOrdinal()));
            addChildElement(el, "MemberDispInfo", String.valueOf(it.memberDispInfo()));
        }
    }

    private static void addSetListType(SOAPElement e, SetListType it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "CrossProduct");
            addTypeList(el, it.setType());
            addChildElement(el, "Size", String.valueOf(it.size()));
        }
    }

    private static void addTuplesType(SOAPElement e, TuplesType it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Tuples");
            addTuplesTypeList(el, it.tuple());
        }

    }

    private static void addTuplesTypeList(SOAPElement e, List<TupleType> list) {
        if (list != null) {
            list.forEach(it -> addTupleType(e, "Tuple", it));
        }
    }

    private static void addTupleType(SOAPElement e, String tagName, TupleType it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, tagName);
            addMemberTypeList(el, it.member());
        }

    }

    private static void addMemberTypeList(SOAPElement e, List<MemberType> list) {
        if (list != null) {
            list.forEach(it -> addMemberType(e, it));
        }
    }

    private static void addMemberType(SOAPElement e, MemberType it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Member");
            addCellInfoItemList(el, it.any());
            addChildElement(el, "Hierarchy", it.hierarchy());
        }
    }

    private static void addMembersType(SOAPElement e, MembersType it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Members");
            addMemberTypeList(el, it.member());
            addChildElement(el, "Hierarchy", it.hierarchy());
        }
    }

    private static void addOlapInfo(SOAPElement e, OlapInfo it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "OlapInfo");
            addCubeInfo(el, it.cubeInfo());
            addAxesInfo(el, it.axesInfo());
            addCellInfo(el, it.cellInfo());
        }
    }

    private static void addCellInfo(SOAPElement e, CellInfo it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "CellInfo");
            addCellInfoItemList(el, it.any());
        }
    }

    private static void addCellInfoItemList(SOAPElement e, List<CellInfoItem> list) {
        if (list != null) {
            list.forEach(it -> addCellInfoItem(e, it));
        }
    }

    private static void addCellInfoItem(SOAPElement e, CellInfoItem it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, it.tagName());
            el.setAttribute("name", it.name());
            it.type().ifPresent(v -> el.setAttribute("type", v));
        }
    }

    private static void addAxesInfo(SOAPElement e, AxesInfo it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "AxesInfo");
            addAxisInfoList(el, it.axisInfo());
        }
    }

    private static void addAxisInfoList(SOAPElement el, List<AxisInfo> list) {
        if (list != null) {
            list.forEach(it -> addAxisInfo(el, it));
        }
    }

    private static void addAxisInfo(SOAPElement e, AxisInfo it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "AxisInfo");
            addChildElement(el, "name", it.name());
            addHierarchyInfoList(el, it.hierarchyInfo());
        }
    }

    private static void addHierarchyInfoList(SOAPElement el, List<HierarchyInfo> list) {
        if (list != null) {
            list.forEach(it -> addHierarchyInfo(el, it));
        }
    }

    private static void addHierarchyInfo(SOAPElement e, HierarchyInfo it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "HierarchyInfo");
            addCellInfoItemList(el, it.any());
            addChildElement(el, "name", it.name());
        }
    }

    private static void addCubeInfo(SOAPElement e, CubeInfo it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "CubeInfo");
            addOlapInfoCubeList(el, it.cube());
        }
    }

    private static void addOlapInfoCubeList(SOAPElement e, List<OlapInfoCube> list) {
        if (list != null) {
            list.forEach(it -> addOlapInfoCube(e, it));
        }
    }

    private static void addOlapInfoCube(SOAPElement e, OlapInfoCube it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Cube");
            addChildElement(el, "CubeName", it.cubeName());
            addChildElement(el, "LastDataUpdate", String.valueOf(it.lastDataUpdate()));
            addChildElement(el, "LastSchemaUpdate", String.valueOf(it.lastSchemaUpdate()));
        }
    }

    private static SOAPBody createSOAPBody() {
        try {
            SOAPMessage message = MessageFactory.newInstance().createMessage();
            return message.getSOAPBody();
        } catch (SOAPException e) {
            throw new RuntimeException("create SOAPBody error");
        }
    }

    private static SOAPElement addRoot(SOAPElement body) {
        SOAPElement response = addChildElement(body, "DiscoverResponse");
        SOAPElement ret = addChildElement(response, "return");
        return addChildElement(ret, "root");
    }

    private static void addChildElement(SOAPElement element, String childElementName, String value) {
        try {
            if (value != null) {
                element.addChildElement(childElementName).setTextContent(value);
            }
        } catch (SOAPException e) {
            LOGGER.error("addChildElement {} error", childElementName);
            throw new RuntimeException("addChildElement error", e);
        }
    }

    private static SOAPElement addChildElement(SOAPElement element, String childElementName) {
        try {
            return element.addChildElement(childElementName);
        } catch (SOAPException e) {
            LOGGER.error("addChildElement {} error", childElementName);
            throw new RuntimeException("addChildElement error", e);
        }
    }
}
