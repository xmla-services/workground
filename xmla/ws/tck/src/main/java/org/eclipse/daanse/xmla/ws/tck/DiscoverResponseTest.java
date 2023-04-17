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
package org.eclipse.daanse.xmla.ws.tck;

import static org.eclipse.daanse.xmla.api.common.enums.AuthenticationModeEnum.UNAUTHENTICATED;
import static org.eclipse.daanse.xmla.api.common.enums.ProviderTypeEnum.MDP;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.daanse.xmla.api.XmlaService;
import org.eclipse.daanse.xmla.api.common.enums.ActionTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.ClientCacheRefreshPolicyEnum;
import org.eclipse.daanse.xmla.api.common.enums.ColumnFlagsEnum;
import org.eclipse.daanse.xmla.api.common.enums.ColumnOlapTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.CoordinateTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.CubeSourceEnum;
import org.eclipse.daanse.xmla.api.common.enums.CubeTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.CustomRollupSettingEnum;
import org.eclipse.daanse.xmla.api.common.enums.DimensionCardinalityEnum;
import org.eclipse.daanse.xmla.api.common.enums.DimensionTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.DimensionUniqueSettingEnum;
import org.eclipse.daanse.xmla.api.common.enums.DirectQueryPushableEnum;
import org.eclipse.daanse.xmla.api.common.enums.GroupingBehaviorEnum;
import org.eclipse.daanse.xmla.api.common.enums.HierarchyOriginEnum;
import org.eclipse.daanse.xmla.api.common.enums.InstanceSelectionEnum;
import org.eclipse.daanse.xmla.api.common.enums.InterfaceNameEnum;
import org.eclipse.daanse.xmla.api.common.enums.InvocationEnum;
import org.eclipse.daanse.xmla.api.common.enums.LevelDbTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.LevelOriginEnum;
import org.eclipse.daanse.xmla.api.common.enums.LevelTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.LevelUniqueSettingsEnum;
import org.eclipse.daanse.xmla.api.common.enums.LiteralNameEnumValueEnum;
import org.eclipse.daanse.xmla.api.common.enums.MeasureAggregatorEnum;
import org.eclipse.daanse.xmla.api.common.enums.MemberTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.OriginEnum;
import org.eclipse.daanse.xmla.api.common.enums.PreferredQueryPatternsEnum;
import org.eclipse.daanse.xmla.api.common.enums.PropertyCardinalityEnum;
import org.eclipse.daanse.xmla.api.common.enums.PropertyContentTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.PropertyOriginEnum;
import org.eclipse.daanse.xmla.api.common.enums.PropertyTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.ScopeEnum;
import org.eclipse.daanse.xmla.api.common.enums.SearchableEnum;
import org.eclipse.daanse.xmla.api.common.enums.SetEvaluationContextEnum;
import org.eclipse.daanse.xmla.api.common.enums.StructureEnum;
import org.eclipse.daanse.xmla.api.common.enums.StructureTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.TableTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.TypeEnum;
import org.eclipse.daanse.xmla.api.discover.DiscoverService;
import org.eclipse.daanse.xmla.api.discover.mdschema.functions.ParameterInfo;
import org.eclipse.daanse.xmla.api.discover.mdschema.measuregroupdimensions.MeasureGroupDimension;
import org.eclipse.daanse.xmla.model.record.discover.MeasureGroupDimensionR;
import org.eclipse.daanse.xmla.model.record.discover.ParameterInfoR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.catalogs.DbSchemaCatalogsResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.columns.DbSchemaColumnsResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.providertypes.DbSchemaProviderTypesResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.schemata.DbSchemaSchemataResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.sourcetables.DbSchemaSourceTablesResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.tables.DbSchemaTablesResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.tablesinfo.DbSchemaTablesInfoResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.discover.datasources.DiscoverDataSourcesResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.discover.enumerators.DiscoverEnumeratorsResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.discover.keywords.DiscoverKeywordsResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.discover.literals.DiscoverLiteralsResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.discover.properties.DiscoverPropertiesResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.discover.schemarowsets.DiscoverSchemaRowsetsResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.discover.xmlmetadata.DiscoverXmlMetaDataResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.actions.MdSchemaActionsResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.cubes.MdSchemaCubesResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.demensions.MdSchemaDimensionsResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.functions.MdSchemaFunctionsResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.hierarchies.MdSchemaHierarchiesResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.kpis.MdSchemaKpisResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.levels.MdSchemaLevelsResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.measuregroupdimensions.MdSchemaMeasureGroupDimensionsResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.measuregroups.MdSchemaMeasureGroupsResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.measures.MdSchemaMeasuresResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.members.MdSchemaMembersResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.properties.MdSchemaPropertiesResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.sets.MdSchemaSetsResponseRowR;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.RequireServiceComponentRuntime;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlunit.assertj3.XmlAssert;

import jakarta.xml.soap.SOAPMessage;

@ExtendWith(ConfigurationExtension.class)
@WithFactoryConfiguration(factoryPid = Constants.PID_MS_SOAP, name = "test-ms-config", location = "?", properties = {
    @Property(key = "xmlaService.target", value = "(" + Constants.XMLASERVICE_FILTER_KEY + "="
        + Constants.XMLASERVICE_FILTER_VALUE + ")"),
    @Property(key = "osgi.soap.endpoint.contextpath", value = Constants.WS_PATH)})
@WithFactoryConfiguration(factoryPid = "org.eclipse.daanse.ws.handler.SOAPLoggingHandler", name = "test-ms-config",
    location = "?", properties = {
    @Property(key = "osgi.soap.endpoint.selector", value = "(service.pid=*)")})
@RequireServiceComponentRuntime
class DiscoverResponseTest {

    private Logger logger = LoggerFactory.getLogger(DiscoverResponseTest.class);

    @InjectBundleContext
    BundleContext bc;

    @BeforeEach
    void beforaEach() throws InterruptedException {
        XmlaService xmlaService = mock(XmlaService.class);
        DiscoverService discoverService = mock(DiscoverService.class);

        when(xmlaService.discover()).thenReturn(discoverService);

        bc.registerService(XmlaService.class, xmlaService, FrameworkUtil
            .asDictionary(Map.of(Constants.XMLASERVICE_FILTER_KEY, Constants.XMLASERVICE_FILTER_VALUE)));
    }

    public static final String REQUEST = """
        <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
          <RequestType>%s</RequestType>
            <Restrictions>
              <RestrictionList>
    		      <TABLE_TYPE>ALIAS</TABLE_TYPE>
    		      <COORDINATE_TYPE>1</COORDINATE_TYPE>
    		      <INVOCATION>1</INVOCATION>
              </RestrictionList>
          </Restrictions>
          <Properties>
            <PropertyList>
            </PropertyList>
          </Properties>
        </Discover>
        """;

    @Test
    void test_DISCOVER_DATA_SOURCES(@InjectService XmlaService xmlaService) throws Exception {
        DiscoverDataSourcesResponseRowR row = new DiscoverDataSourcesResponseRowR("dataSourceName",
            Optional.of("dataSourceDescription"), Optional.of("url"),
            Optional.of("dataSourceInfo"), "providerName",
            Optional.of(MDP), Optional.of(UNAUTHENTICATED));

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.dataSources(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST,
                "DISCOVER_DATASOURCES")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);
        checkRowValues(xmlAssert, Map.of(
            "DataSourceName", "dataSourceName",
            "DataSourceDescription", "dataSourceDescription",
            "URL", "url",
            "DataSourceInfo", "dataSourceInfo",
            "ProviderName", "providerName",
            "ProviderType", "MDP",
            "AuthenticationMode", "Unauthenticated"
        ));
    }

    @Test
    void test_DISCOVER_ENUMERATORS(@InjectService XmlaService xmlaService) throws Exception {

        DiscoverEnumeratorsResponseRowR row = new DiscoverEnumeratorsResponseRowR("enumName",
            Optional.of("enumDescription"), "enumType", "elementName",
            Optional.of("elementDescription"), Optional.of("elementValue"));

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.discoverEnumerators(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST,
                "DISCOVER_ENUMERATORS")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);
        checkRowValues(xmlAssert, Map.of(
            "EnumName", "enumName",
            "EnumDescription", "enumDescription",
            "EnumType", "enumType",
            "ElementName", "elementName",
            "ElementDescription", "elementDescription",
            "ElementValue", "elementValue"
        ));
    }

    @Test
    void test_DISCOVER_KEYWORDS(@InjectService XmlaService xmlaService) throws Exception {

        DiscoverKeywordsResponseRowR row = new DiscoverKeywordsResponseRowR("keyword");

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.discoverKeywords(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST, "DISCOVER_KEYWORDS")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);
        checkRowValues(xmlAssert, Map.of(
            "Keyword", "keyword"
        ));
    }

    @Test
    void test_DISCOVER_LITERALS(@InjectService XmlaService xmlaService) throws Exception {

        DiscoverLiteralsResponseRowR row = new DiscoverLiteralsResponseRowR("literalName",
            "literalValue", "literalInvalidChars", "literalInvalidStartingChars",
            10, LiteralNameEnumValueEnum.DBLITERAL_BINARY_LITERAL);

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.discoverLiterals(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST, "DISCOVER_LITERALS")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);
        checkRowValues(xmlAssert, Map.of(
            "LiteralName", "literalName",
            "LiteralValue", "literalValue",
            "LiteralInvalidChars", "literalInvalidChars",
            "LiteralInvalidStartingChars", "literalInvalidStartingChars",
            "LiteralMaxLength", "10",
            "LiteralNameValue", "1"
        ));
    }

    @Test
    void test_DISCOVER_PROPERTIES(@InjectService XmlaService xmlaService) throws Exception {

        DiscoverPropertiesResponseRowR row = new DiscoverPropertiesResponseRowR("DbpropMsmdSubqueries",
            Optional.of("An enumeration value that determines the behavior of subqueries."), Optional.of("Integer"),
            "ReadWrite", Optional.of(false), Optional.of("1"));

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.discoverProperties(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST, "DISCOVER_PROPERTIES"
            )));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/return/rowset:root")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/return/rowset:root/rowset:row")
            .exist();
        xmlAssert.valueByXPath("count(/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/return/rowset:root/rowset:row)"
        ).isEqualTo(1);

        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/return/rowset:root/rowset:row/rowset" +
            ":PropertyName")
            .isEqualTo("DbpropMsmdSubqueries");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/return/rowset:root/rowset:row/rowset" +
            ":PropertyDescription")
            .isEqualTo("An enumeration value that determines the behavior of subqueries.");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/return/rowset:root/rowset:row/rowset" +
            ":PropertyAccessType")
            .isEqualTo("ReadWrite");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/return/rowset:root/rowset:row/rowset" +
            ":IsRequired")
            .isEqualTo("false");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/return/rowset:root/rowset:row/rowset" +
            ":Value")
            .isEqualTo("1");
        System.out.println(1);
    }

    @Test
    void test_DISCOVER_SCHEMA_ROW_SETS(@InjectService XmlaService xmlaService) throws Exception {

        DiscoverSchemaRowsetsResponseRowR row = new DiscoverSchemaRowsetsResponseRowR("schemaName",
            Optional.of("schemaGuid"), Optional.of("restrictions"), Optional.of("description"), Optional.of(10l));

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.discoverSchemaRowsets(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST,
                "DISCOVER_SCHEMA_ROWSETS")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);
        checkRowValues(xmlAssert, Map.of(
            "SchemaName", "schemaName",
            "SchemaGuid", "schemaGuid",
            "Restrictions", "restrictions",
            "Description", "description",
            "RestrictionsMask", "10"
        ));
    }

    @Test
    void test_DISCOVER_XML_METADATA(@InjectService XmlaService xmlaService) throws Exception {

        DiscoverXmlMetaDataResponseRowR row = new DiscoverXmlMetaDataResponseRowR("metaData");

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.xmlMetaData(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST,
                "DISCOVER_XML_METADATA")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);
        checkRowValues(xmlAssert, Map.of(
            "MetaData", "metaData"
        ));
    }

    @Test
    void test_DBSCHEMA_CATALOGS(@InjectService XmlaService xmlaService) throws Exception {

        DbSchemaCatalogsResponseRowR row = new DbSchemaCatalogsResponseRowR(
            Optional.of("catalogName"),
            Optional.of("description"),
            Optional.of("roles"),
            Optional.of(LocalDateTime.of(2023, 2, 16, 10, 10)),
            Optional.of(1), Optional.of(TypeEnum.MULTIDIMENSIONAL), Optional.of(2), Optional.of("databaseId"),
            Optional.of(LocalDateTime.of(2023, 2, 16, 10, 10)),
            Optional.of(true), Optional.of(1.1d), Optional.of(1.2d),
            Optional.of(ClientCacheRefreshPolicyEnum.REFRESH_NEWER_DATA));

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.dbSchemaCatalogs(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST, "DBSCHEMA_CATALOGS")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);
        checkRowValues(xmlAssert, Map.ofEntries(
            Map.entry("CATALOG_NAME", "catalogName"),
            Map.entry("DESCRIPTION", "description"),
            Map.entry("ROLES", "roles"),
            Map.entry("DATE_MODIFIED", "2023-02-16T10:10"),
            Map.entry("TYPE", "0x00"),
            Map.entry("VERSION", "2"),
            Map.entry("DATABASE_ID", "databaseId"),
            Map.entry("DATE_QUERIED", "2023-02-16T10:10"),
            Map.entry("CURRENTLY_USED", "true"),
            Map.entry("POPULARITY", "1.1"),
            Map.entry("WEIGHTEDPOPULARITY", "1.2"),
            Map.entry("CLIENTCACHEREFRESHPOLICY", "0")));
    }

    @Test
    void test_DBSCHEMA_COLUMNS(@InjectService XmlaService xmlaService) throws Exception {

        DbSchemaColumnsResponseRowR row = new DbSchemaColumnsResponseRowR(
            Optional.of("tableCatalog"),
            Optional.of("tableSchema"),
            Optional.of("tableName"),
            Optional.of("columnName"),
            Optional.of(1),
            Optional.of(2),
            Optional.of(3),
            Optional.of(true),
            Optional.of("columnDefault"),
            Optional.of(ColumnFlagsEnum.DBCOLUMNFLAGS_ISBOOKMARK),
            Optional.of(false),
            Optional.of(4),
            Optional.of(5),
            Optional.of(6),
            Optional.of(7),
            Optional.of(8),
            Optional.of(9),
            Optional.of(10),
            Optional.of("characterSetCatalog"),
            Optional.of("characterSetSchema"),
            Optional.of("characterSetName"),
            Optional.of("collationCatalog"),
            Optional.of("collationSchema"),
            Optional.of("collationName"),
            Optional.of("domainCatalog"),
            Optional.of("domainSchema"),
            Optional.of("domainName"),
            Optional.of("description"),
            Optional.of(ColumnOlapTypeEnum.ATTRIBUTE)
        );

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.dbSchemaColumns(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST, "DBSCHEMA_COLUMNS")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);

        checkRowValues(xmlAssert, Map.ofEntries(
            Map.entry("TABLE_CATALOG", "tableCatalog"),
            Map.entry("TABLE_SCHEMA", "tableSchema"),
            Map.entry("TABLE_NAME", "tableName"),
            Map.entry("COLUMN_NAME", "columnName"),
            Map.entry("COLUMN_GUID", "1"),
            Map.entry("COLUMN_PROPID", "2"),
            Map.entry("ORDINAL_POSITION", "3"),
            Map.entry("COLUMN_HAS_DEFAULT", "true"),
            Map.entry("COLUMN_DEFAULT", "columnDefault"),
            Map.entry("COLUMN_FLAG", "0x1"),
            Map.entry("IS_NULLABLE", "false"),
            Map.entry("DATA_TYPE", "4"),
            Map.entry("TYPE_GUID", "5"),
            Map.entry("CHARACTER_MAXIMUM_LENGTH", "6"),
            Map.entry("CHARACTER_OCTET_LENGTH", "7"),
            Map.entry("NUMERIC_PRECISION", "8"),
            Map.entry("NUMERIC_SCALE", "9"),
            Map.entry("DATETIME_PRECISION", "10"),
            Map.entry("CHARACTER_SET_CATALOG", "characterSetCatalog"),
            Map.entry("CHARACTER_SET_SCHEMA", "characterSetSchema"),
            Map.entry("CHARACTER_SET_NAME", "characterSetName"),
            Map.entry("COLLATION_CATALOG", "collationCatalog"),
            Map.entry("COLLATION_SCHEMA", "collationSchema"),
            Map.entry("COLLATION_NAME", "collationName"),
            Map.entry("DOMAIN_CATALOG", "domainCatalog"),
            Map.entry("DOMAIN_SCHEMA", "domainSchema"),
            Map.entry("DOMAIN_NAME", "domainName"),
            Map.entry("DESCRIPTION", "description"),
            Map.entry("COLUMN_OLAP_TYPE", "ATTRIBUTE")));
    }

    @Test
    void test_DBSCHEMA_PROVIDER_TYPES(@InjectService XmlaService xmlaService) throws Exception {

        DbSchemaProviderTypesResponseRowR row = new DbSchemaProviderTypesResponseRowR(
            Optional.of("typeName"),
            Optional.of(LevelDbTypeEnum.DBTYPE_EMPTY),
            Optional.of(1),
            Optional.of("literalPrefix"),
            Optional.of("literalSuffix"),
            Optional.of("createParams"),
            Optional.of(true),
            Optional.of(false),
            Optional.of(SearchableEnum.DB_UNSEARCHABLE),
            Optional.of(true),
            Optional.of(false),
            Optional.of(true),
            Optional.of("localTypeName"),
            Optional.of(2),
            Optional.of(3),
            Optional.of(4),
            Optional.of("typeLib"),
            Optional.of("version"),
            Optional.of(false),
            Optional.of(true),
            Optional.of(false));

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.dbSchemaProviderTypes(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST,
                "DBSCHEMA_PROVIDER_TYPES")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);
        checkRowValues(xmlAssert, Map.ofEntries(
            Map.entry("TYPE_NAME", "typeName"),
            Map.entry("DATA_TYPE", "0"),
            Map.entry("COLUMN_SIZE", "1"),
            Map.entry("LITERAL_PREFIX", "literalPrefix"),
            Map.entry("LITERAL_SUFFIX", "literalSuffix"),
            Map.entry("CREATE_PARAMS", "createParams"),
            Map.entry("IS_NULLABLE", "true"),
            Map.entry("CASE_SENSITIVE", "false"),
            Map.entry("SEARCHABLE", "0x01"),
            Map.entry("UNSIGNED_ATTRIBUTE", "true"),
            Map.entry("FIXED_PREC_SCALE", "false"),
            Map.entry("AUTO_UNIQUE_VALUE", "true"),
            Map.entry("LOCAL_TYPE_NAME", "localTypeName"),
            Map.entry("MINIMUM_SCALE", "2"),
            Map.entry("MAXIMUM_SCALE", "3"),
            Map.entry("GUID", "4"),
            Map.entry("TYPE_LIB", "typeLib"),
            Map.entry("VERSION", "version"),
            Map.entry("IS_LONG", "false"),
            Map.entry("BEST_MATCH", "true"),
            Map.entry("IS_FIXEDLENGTH", "false")));
    }

    @Test
    void test_DBSCHEMA_SCHEMATA(@InjectService XmlaService xmlaService) throws Exception {

        DbSchemaSchemataResponseRowR row = new DbSchemaSchemataResponseRowR(
            "catalogName",
            "schemaName",
            "schemaOwner");

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.dbSchemaSchemata(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST, "DBSCHEMA_SCHEMATA")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);

        checkRowValues(xmlAssert, Map.ofEntries(
            Map.entry("CATALOG_NAME", "catalogName"),
            Map.entry("SCHEMA_NAME", "schemaName"),
            Map.entry("SCHEMA_OWNER", "schemaOwner")));
    }

    @Test
    void test_DBSCHEMA_SOURCE_TABLES(@InjectService XmlaService xmlaService) throws Exception {

        DbSchemaSourceTablesResponseRowR row = new DbSchemaSourceTablesResponseRowR(
            Optional.of("catalogName"),
            Optional.of("schemaName"),
            "tableName",
            TableTypeEnum.ALIAS);

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.dbSchemaSourceTables(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST,
                "DBSCHEMA_SOURCE_TABLES")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);

        checkRowValues(xmlAssert, Map.ofEntries(
            Map.entry("TABLE_CATALOG", "catalogName"),
            Map.entry("TABLE_SCHEMA", "schemaName"),
            Map.entry("TABLE_NAME", "tableName"),
            Map.entry("TABLE_TYPE", "ALIAS")));
    }

    @Test
    void test_DBSCHEMA_TABLES(@InjectService XmlaService xmlaService) throws Exception {

        DbSchemaTablesResponseRowR row = new DbSchemaTablesResponseRowR(
            Optional.of("tableCatalog"),
            Optional.of("tableSchema"),
            Optional.of("tableName"),
            Optional.of("tableType"),
            Optional.of("tableGuid"),
            Optional.of("description"),
            Optional.of(1),
            Optional.of(LocalDateTime.of(2023, 2, 16, 10, 10)),
            Optional.of(LocalDateTime.of(2023, 2, 16, 10, 10)));

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.dbSchemaTables(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST, "DBSCHEMA_TABLES")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);

        checkRowValues(xmlAssert, Map.ofEntries(
            Map.entry("TABLE_CATALOG", "tableCatalog"),
            Map.entry("TABLE_SCHEMA", "tableSchema"),
            Map.entry("TABLE_NAME", "tableName"),
            Map.entry("TABLE_TYPE", "tableType"),
            Map.entry("TABLE_GUID", "tableGuid"),
            Map.entry("DESCRIPTION", "description"),
            Map.entry("TABLE_PROP_ID", "1"),
            Map.entry("DATE_CREATED", "2023-02-16T10:10"),
            Map.entry("DATE_MODIFIED", "2023-02-16T10:10")));
    }

    @Test
    void test_DBSCHEMA_TABLES_INFO(@InjectService XmlaService xmlaService) throws Exception {

        DbSchemaTablesInfoResponseRowR row = new DbSchemaTablesInfoResponseRowR(
            Optional.of("catalogName"),
            Optional.of("schemaName"),
            "tableName",
            "tableType",
            Optional.of(1),
            Optional.of(true),
            Optional.of(2),
            Optional.of(3),
            Optional.of(4),
            Optional.of(5),
            Optional.of(6l),
            Optional.of(7l),
            Optional.of("description"),
            Optional.of(8));

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.dbSchemaTablesInfo(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST,
                "DBSCHEMA_TABLES_INFO")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);

        checkRowValues(xmlAssert, Map.ofEntries(
            Map.entry("TABLE_CATALOG", "catalogName"),
            Map.entry("TABLE_SCHEMA", "schemaName"),
            Map.entry("TABLE_NAME", "tableName"),
            Map.entry("TABLE_TYPE", "tableType"),
            Map.entry("TABLE_GUID", "1"),
            Map.entry("BOOKMARKS", "true"),
            Map.entry("BOOKMARK_TYPE", "2"),
            Map.entry("BOOKMARK_DATA_TYPE", "3"),
            Map.entry("BOOKMARK_MAXIMUM_LENGTH", "4"),
            Map.entry("BOOKMARK_INFORMATION", "5"),
            Map.entry("TABLE_VERSION", "6"),
            Map.entry("CARDINALITY", "7"),
            Map.entry("DESCRIPTION", "description"),
            Map.entry("TABLE_PROP_ID", "8")));
    }

    @Test
    void test_MDSCHEMA_ACTIONS(@InjectService XmlaService xmlaService) throws Exception {

        MdSchemaActionsResponseRowR row = new MdSchemaActionsResponseRowR(
            Optional.of("catalogName"),
            Optional.of("schemaName"),
            "cubeName",
            Optional.of("actionName"),
            Optional.of(ActionTypeEnum.URL),
            "coordinate",
            CoordinateTypeEnum.CUBE,
            Optional.of("actionCaption"),
            Optional.of("description"),
            Optional.of("content"),
            Optional.of("application"),
            Optional.of(InvocationEnum.NORMAL_OPERATION));

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.mdSchemaActions(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST, "MDSCHEMA_ACTIONS")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);

        checkRowValues(xmlAssert, Map.ofEntries(
            Map.entry("CATALOG_NAME", "catalogName"),
            Map.entry("SCHEMA_NAME", "schemaName"),
            Map.entry("CUBE_NAME", "cubeName"),
            Map.entry("ACTION_NAME", "actionName"),
            Map.entry("ACTION_TYPE", "0x01"),
            Map.entry("COORDINATE", "coordinate"),
            Map.entry("COORDINATE_TYPE", "1"),
            Map.entry("ACTION_CAPTION", "actionCaption"),
            Map.entry("DESCRIPTION", "description"),
            Map.entry("CONTENT", "content"),
            Map.entry("APPLICATION", "application"),
            Map.entry("INVOCATION", "1")));
    }

    @Test
    void test_MDSCHEMA_CUBES(@InjectService XmlaService xmlaService) throws Exception {

        MdSchemaCubesResponseRowR row = new MdSchemaCubesResponseRowR(
            "catalogName",
            Optional.of("schemaName"),
            Optional.of("cubeName"),
            Optional.of(CubeTypeEnum.CUBE),
            Optional.of(1),
            Optional.of(LocalDateTime.of(2023, 2, 16, 10, 10)),
            Optional.of(LocalDateTime.of(2023, 2, 16, 10, 10)),
            Optional.of("schemaUpdatedBy"),
            Optional.of(LocalDateTime.of(2023, 2, 16, 10, 10)),
            Optional.of("dataUpdateDBy"),
            Optional.of("description"),
            Optional.of(true),
            Optional.of(false),
            Optional.of(true),
            Optional.of(false),
            Optional.of("cubeCaption"),
            Optional.of("baseCubeName"),
            Optional.of(CubeSourceEnum.CUBE),
            Optional.of(PreferredQueryPatternsEnum.CROSS_JOIN));

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.mdSchemaCubes(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST, "MDSCHEMA_CUBES")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);

        checkRowValues(xmlAssert, Map.ofEntries(
            Map.entry("CATALOG_NAME", "catalogName"),
            Map.entry("SCHEMA_NAME", "schemaName"),
            Map.entry("CUBE_NAME", "cubeName"),
            Map.entry("CUBE_TYPE", "CUBE"),
            Map.entry("CUBE_GUID", "1"),
            Map.entry("CREATED_ON", "2023-02-16T10:10"),
            Map.entry("LAST_SCHEMA_UPDATE", "2023-02-16T10:10"),
            Map.entry("SCHEMA_UPDATED_BY", "schemaUpdatedBy"),
            Map.entry("LAST_DATA_UPDATE", "2023-02-16T10:10"),
            Map.entry("DATA_UPDATED_BY", "dataUpdateDBy"),
            Map.entry("DESCRIPTION", "description"),
            Map.entry("IS_DRILLTHROUGH_ENABLED", "true"),
            Map.entry("IS_LINKABLE", "false"),
            Map.entry("IS_WRITE_ENABLED", "true"),
            Map.entry("IS_SQL_ENABLED", "false"),
            Map.entry("CUBE_CAPTION", "cubeCaption"),
            Map.entry("BASE_CUBE_NAME", "baseCubeName"),
            Map.entry("CUBE_SOURCE", "0x01"),
            Map.entry("PREFERRED_QUERY_PATTERNS", "0x00")
        ));
    }

    @Test
    void test_MDSCHEMA_DIMENSIONS(@InjectService XmlaService xmlaService) throws Exception {

        MdSchemaDimensionsResponseRowR row = new MdSchemaDimensionsResponseRowR(
            Optional.of("catalogName"),
            Optional.of("schemaName"),
            Optional.of("cubeName"),
            Optional.of("dimensionName"),
            Optional.of("dimensionUniqueName"),
            Optional.of(1),
            Optional.of("dimensionCaption"),
            Optional.of(2),
            Optional.of(DimensionTypeEnum.UNKNOWN),
            Optional.of(3),
            Optional.of("defaultHierarchy"),
            Optional.of("description"),
            Optional.of(true),
            Optional.of(false),
            Optional.of(DimensionUniqueSettingEnum.MEMBER_KEY),
            Optional.of("dimensionMasterName"),
            Optional.of(true));

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.mdSchemaDimensions(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST, "MDSCHEMA_DIMENSIONS"
            )));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);

        checkRowValues(xmlAssert, Map.ofEntries(
            Map.entry("CATALOG_NAME", "catalogName"),
            Map.entry("SCHEMA_NAME", "schemaName"),
            Map.entry("CUBE_NAME", "cubeName"),
            Map.entry("DIMENSION_NAME", "dimensionName"),
            Map.entry("DIMENSION_UNIQUE_NAME", "dimensionUniqueName"),
            Map.entry("DIMENSION_GUID", "1"),
            Map.entry("DIMENSION_CAPTION", "dimensionCaption"),
            Map.entry("DIMENSION_ORDINAL", "2"),
            Map.entry("DIMENSION_TYPE", "0"),
            Map.entry("DIMENSION_CARDINALITY", "3"),
            Map.entry("DEFAULT_HIERARCHY", "defaultHierarchy"),
            Map.entry("DESCRIPTION", "description"),
            Map.entry("IS_VIRTUAL", "true"),
            Map.entry("IS_READWRITE", "false"),
            Map.entry("DIMENSION_UNIQUE_SETTINGS", "0x00000001"),
            Map.entry("DIMENSION_MASTER_NAME", "dimensionMasterName"),
            Map.entry("DIMENSION_IS_VISIBLE", "true")
        ));
    }

    @Test
    void test_MDSCHEMA_FUNCTIONS(@InjectService XmlaService xmlaService) throws Exception {

        List<ParameterInfo> parameterInfoList = List.of(new ParameterInfoR(
            "name",
            "description",
            true,
            false,
            1));
        MdSchemaFunctionsResponseRowR row = new MdSchemaFunctionsResponseRowR(
            Optional.of("functionalName"),
            Optional.of("description"),
            "parameterList",
            Optional.of(1),
            Optional.of(OriginEnum.MSOLAP),
            Optional.of(InterfaceNameEnum.FILTER),
            Optional.of("libraryName"),
            Optional.of("dllName"),
            Optional.of("helpFile"),
            Optional.of("helpContext"),
            Optional.of("object"),
            Optional.of("caption"),
            Optional.of(parameterInfoList),
            Optional.of(DirectQueryPushableEnum.MEASURE));

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.mdSchemaFunctions(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST,
                "MDSCHEMA_FUNCTIONS")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);

        checkRowValues(xmlAssert, Map.ofEntries(
            Map.entry("FUNCTION_NAME", "functionalName"),
            Map.entry("DESCRIPTION", "description"),
            Map.entry("PARAMETER_LIST", "parameterList"),
            Map.entry("RETURN_TYPE", "1"),
            Map.entry("ORIGIN", "0x1"),
            Map.entry("INTERFACE_NAME", "FILTER"),
            Map.entry("LIBRARY_NAME", "libraryName"),
            Map.entry("DLL_NAME", "dllName"),
            Map.entry("HELP_FILE", "helpFile"),
            Map.entry("HELP_CONTEXT", "helpContext"),
            Map.entry("OBJECT", "object"),
            Map.entry("CAPTION", "caption"),
            Map.entry("PARAMETERINFO", "namedescriptiontruefalse1"),
            Map.entry("DIRECTQUERY_PUSHABLE", "0x1")
        ));
    }

    @Test
    void test_MDSCHEMA_HIERARCHIES(@InjectService XmlaService xmlaService) throws Exception {

        List<ParameterInfo> parameterInfoList = List.of();
        MdSchemaHierarchiesResponseRowR row = new MdSchemaHierarchiesResponseRowR(
            Optional.of("catalogName"),
            Optional.of("schemaName"),
            Optional.of("cubeName"),
            Optional.of("dimensionUniqueName"),
            Optional.of("hierarchyName"),
            Optional.of("hierarchyUniqueName"),
            Optional.of(1),
            Optional.of("hierarchyCaption"),
            Optional.of(DimensionTypeEnum.UNKNOWN),
            Optional.of(2),
            Optional.of("defaultMember"),
            Optional.of("allMember"),
            Optional.of("description"),
            Optional.of(StructureEnum.HIERARCHY_FULLY_BALANCED),
            Optional.of(true),
            Optional.of(false),
            Optional.of(DimensionUniqueSettingEnum.MEMBER_KEY),
            Optional.of("dimensionMasterUniqueName"),
            Optional.of(true),
            Optional.of(3),
            Optional.of(false),
            Optional.of(true),
            Optional.of(HierarchyOriginEnum.USER_DEFINED),
            Optional.of("hierarchyDisplayFolder"),
            Optional.of(InstanceSelectionEnum.DROPDOWN),
            Optional.of(GroupingBehaviorEnum.ENCOURAGED),
            Optional.of(StructureTypeEnum.NATURAL));

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.mdSchemaHierarchies(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST,
                "MDSCHEMA_HIERARCHIES")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);

        checkRowValues(xmlAssert, Map.ofEntries(
            Map.entry("CATALOG_NAME", "catalogName"),
            Map.entry("SCHEMA_NAME", "schemaName"),
            Map.entry("CUBE_NAME", "cubeName"),
            Map.entry("DIMENSION_UNIQUE_NAME", "dimensionUniqueName"),
            Map.entry("HIERARCHY_NAME", "hierarchyName"),
            Map.entry("HIERARCHY_UNIQUE_NAME", "hierarchyUniqueName"),
            Map.entry("HIERARCHY_GUID", "1"),
            Map.entry("HIERARCHY_CAPTION", "hierarchyCaption"),
            Map.entry("DIMENSION_TYPE", "0"),
            Map.entry("HIERARCHY_CARDINALITY", "2"),
            Map.entry("DEFAULT_MEMBER", "defaultMember"),
            Map.entry("ALL_MEMBER", "allMember"),
            Map.entry("DESCRIPTION", "description"),
            Map.entry("STRUCTURE", "0"),
            Map.entry("IS_VIRTUAL", "true"),
            Map.entry("IS_READWRITE", "false"),
            Map.entry("DIMENSION_UNIQUE_SETTINGS", "0x00000001"),
            Map.entry("DIMENSION_MASTER_UNIQUE_NAME", "dimensionMasterUniqueName"),
            Map.entry("DIMENSION_IS_VISIBLE", "true"),
            Map.entry("HIERARCHY_ORDINAL", "3"),
            Map.entry("DIMENSION_IS_SHARED", "false"),
            Map.entry("HIERARCHY_IS_VISIBLE", "true"),
            Map.entry("HIERARCHY_ORIGIN", "0x0001"),
            Map.entry("HIERARCHY_DISPLAY_FOLDER", "hierarchyDisplayFolder"),
            Map.entry("INSTANCE_SELECTION", "1"),
            Map.entry("GROUPING_BEHAVIOR", "1"),
            Map.entry("STRUCTURE_TYPE", "Natural")
        ));


    }

    @Test
    void test_MDSCHEMA_KPIS(@InjectService XmlaService xmlaService) throws Exception {

        List<ParameterInfo> parameterInfoList = List.of();
        MdSchemaKpisResponseRowR row = new MdSchemaKpisResponseRowR(
            Optional.of("catalogName"),
            Optional.of("schemaName"),
            Optional.of("cubeName"),
            Optional.of("measureGroupName"),
            Optional.of("kpiName"),
            Optional.of("kpiCaption"),
            Optional.of("kpiDescription"),
            Optional.of("kpiDisplayFolder"),
            Optional.of("kpiValue"),
            Optional.of("kpiGoal"),
            Optional.of("kpiStatus"),
            Optional.of("kpiTrend"),
            Optional.of("kpiStatusGraphic"),
            Optional.of("kpiTrendGraphic"),
            Optional.of("kpiWight"),
            Optional.of("kpiCurrentTimeMember"),
            Optional.of("kpiParentKpiName"),
            Optional.of("annotation"),
            Optional.of(ScopeEnum.GLOBAL));

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.mdSchemaKpis(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST,
                "MDSCHEMA_KPIS")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);

        checkRowValues(xmlAssert, Map.ofEntries(
            Map.entry("CATALOG_NAME", "catalogName"),
            Map.entry("SCHEMA_NAME", "schemaName"),
            Map.entry("CUBE_NAME", "cubeName"),
            Map.entry("MEASUREGROUP_NAME", "measureGroupName"),
            Map.entry("KPI_NAME", "kpiName"),
            Map.entry("KPI_CAPTION", "kpiCaption"),
            Map.entry("KPI_DESCRIPTION", "kpiDescription"),
            Map.entry("KPI_DISPLAY_FOLDER", "kpiDisplayFolder"),
            Map.entry("KPI_VALUE", "kpiValue"),
            Map.entry("KPI_GOAL", "kpiGoal"),
            Map.entry("KPI_STATUS", "kpiStatus"),
            Map.entry("KPI_TREND", "kpiTrend"),
            Map.entry("KPI_STATUS_GRAPHIC", "kpiStatusGraphic"),
            Map.entry("KPI_TREND_GRAPHIC", "kpiTrendGraphic"),
            Map.entry("KPI_WEIGHT", "kpiWight"),
            Map.entry("KPI_CURRENT_TIME_MEMBER", "kpiCurrentTimeMember"),
            Map.entry("KPI_PARENT_KPI_NAME", "kpiParentKpiName"),
            Map.entry("ANNOTATIONS", "annotation"),
            Map.entry("SCOPE", "1")
        ));
    }

    @Test
    void test_MDSCHEMA_LEVELS(@InjectService XmlaService xmlaService) throws Exception {

        List<ParameterInfo> parameterInfoList = List.of();
        MdSchemaLevelsResponseRowR row = new MdSchemaLevelsResponseRowR(
            Optional.of("catalogName"),
            Optional.of("schemaName"),
            Optional.of("cubeName"),
            Optional.of("dimensionUniqueName"),
            Optional.of("hierarchyUniqueName"),
            Optional.of("levelName"),
            Optional.of("levelUniqueName"),
            Optional.of(1),
            Optional.of("levelCaption"),
            Optional.of(2),
            Optional.of(3),
            Optional.of(LevelTypeEnum.ALL),
            Optional.of("description"),
            Optional.of(CustomRollupSettingEnum.CUSTOM_ROLLUP_EXPRESSION_EXIST),
            Optional.of(LevelUniqueSettingsEnum.KEY_COLUMNS),
            Optional.of(true),
            Optional.of("levelOrderingProperty"),
            Optional.of(LevelDbTypeEnum.DBTYPE_EMPTY),
            Optional.of("levelMasterUniqueName"),
            Optional.of("levelNameSqlColumnName"),
            Optional.of("levelKeySqlColumnName"),
            Optional.of("levelUniqueNameSqlColumnName"),
            Optional.of("levelAttributeHierarchyName"),
            Optional.of(4),
            Optional.of(LevelOriginEnum.USER_DEFINED));

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.mdSchemaLevels(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST,
                "MDSCHEMA_LEVELS")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);

        checkRowValues(xmlAssert, Map.ofEntries(
            Map.entry("CATALOG_NAME", "catalogName"),
            Map.entry("SCHEMA_NAME", "schemaName"),
            Map.entry("CUBE_NAME", "cubeName"),
            Map.entry("DIMENSION_UNIQUE_NAME", "dimensionUniqueName"),
            Map.entry("HIERARCHY_UNIQUE_NAME", "hierarchyUniqueName"),
            Map.entry("LEVEL_NAME", "levelName"),
            Map.entry("LEVEL_UNIQUE_NAME", "levelUniqueName"),
            Map.entry("LEVEL_GUID", "1"),
            Map.entry("LEVEL_CAPTION", "levelCaption"),
            Map.entry("LEVEL_NUMBER", "2"),
            Map.entry("LEVEL_CARDINALITY", "3"),
            Map.entry("LEVEL_TYPE", "0x0001"),
            Map.entry("DESCRIPTION", "description"),
            Map.entry("CUSTOM_ROLLUP_SETTINGS", "0x01"),
            Map.entry("LEVEL_UNIQUE_SETTINGS", "0x00000001"),
            Map.entry("LEVEL_IS_VISIBLE", "true"),
            Map.entry("LEVEL_ORDERING_PROPERTY", "levelOrderingProperty"),
            Map.entry("LEVEL_DBTYPE", "0"),
            Map.entry("LEVEL_MASTER_UNIQUE_NAME", "levelMasterUniqueName"),
            Map.entry("LEVEL_NAME_SQL_COLUMN_NAME", "levelNameSqlColumnName"),
            Map.entry("LEVEL_KEY_SQL_COLUMN_NAME", "levelKeySqlColumnName"),
            Map.entry("LEVEL_UNIQUE_NAME_SQL_COLUMN_NAME", "levelUniqueNameSqlColumnName"),
            Map.entry("LEVEL_ATTRIBUTE_HIERARCHY_NAME", "levelAttributeHierarchyName"),
            Map.entry("LEVEL_KEY_CARDINALITY", "4"),
            Map.entry("LEVEL_ORIGIN", "0x0001")
        ));
    }

    @Test
    void test_MDSCHEMA_MEASUREGROUP_DIMENSIONS(@InjectService XmlaService xmlaService) throws Exception {

        List<MeasureGroupDimension> list = List.of(new MeasureGroupDimensionR("measureGroupDimension"));
        MdSchemaMeasureGroupDimensionsResponseRowR row = new MdSchemaMeasureGroupDimensionsResponseRowR(
            Optional.of("catalogName"),
            Optional.of("schemaName"),
            Optional.of("cubeName"),
            Optional.of("measureGroupName"),
            Optional.of("measureGroupCardinality"),
            Optional.of("dimensionUniqueName"),
            Optional.of(DimensionCardinalityEnum.ONE),
            Optional.of(true),
            Optional.of(false),
            Optional.of(list),
            Optional.of("dimensionGranularity"));

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.mdSchemaMeasureGroupDimensions(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST,
                "MDSCHEMA_MEASUREGROUP_DIMENSIONS")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);

        checkRowValues(xmlAssert, Map.ofEntries(
            Map.entry("CATALOG_NAME", "catalogName"),
            Map.entry("SCHEMA_NAME", "schemaName"),
            Map.entry("CUBE_NAME", "cubeName"),
            Map.entry("MEASUREGROUP_NAME", "measureGroupName"),
            Map.entry("MEASUREGROUP_CARDINALITY", "measureGroupCardinality"),
            Map.entry("DIMENSION_UNIQUE_NAME", "dimensionUniqueName"),
            Map.entry("DIMENSION_CARDINALITY", "ONE"),
            Map.entry("DIMENSION_IS_VISIBLE", "true"),
            Map.entry("DIMENSION_IS_FACT_DIMENSION", "false"),
            Map.entry("DIMENSION_PATH", "measureGroupDimension"),
            Map.entry("DIMENSION_GRANULARITY", "dimensionGranularity")
        ));
    }

    @Test
    void test_MDSCHEMA_MEASUREGROUPS(@InjectService XmlaService xmlaService) throws Exception {

        List<MeasureGroupDimension> list = List.of();
        MdSchemaMeasureGroupsResponseRowR row = new MdSchemaMeasureGroupsResponseRowR(
            Optional.of("catalogName"),
            Optional.of("schemaName"),
            Optional.of("cubeName"),
            Optional.of("measureGroupName"),
            Optional.of("description"),
            Optional.of(true),
            Optional.of("measureGroupCaption")
        );

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.mdSchemaMeasureGroups(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST,
                "MDSCHEMA_MEASUREGROUPS")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);

        checkRowValues(xmlAssert, Map.ofEntries(
            Map.entry("CATALOG_NAME", "catalogName"),
            Map.entry("SCHEMA_NAME", "schemaName"),
            Map.entry("CUBE_NAME", "cubeName"),
            Map.entry("MEASUREGROUP_NAME", "measureGroupName"),
            Map.entry("DESCRIPTION", "description"),
            Map.entry("IS_WRITE_ENABLED", "true"),
            Map.entry("MEASUREGROUP_CAPTION", "measureGroupCaption")
        ));
    }

    @Test
    void test_MDSCHEMA_MEASURES(@InjectService XmlaService xmlaService) throws Exception {

        MdSchemaMeasuresResponseRowR row = new MdSchemaMeasuresResponseRowR(
            Optional.of("catalogName"),
            Optional.of("schemaName"),
            Optional.of("cubeName"),
            Optional.of("measureName"),
            Optional.of("measureUniqueName"),
            Optional.of("measureCaption"),
            Optional.of(1),
            Optional.of(MeasureAggregatorEnum.MDMEASURE_AGGR_UNKNOWN),
            Optional.of(LevelDbTypeEnum.DBTYPE_EMPTY),
            Optional.of(2),
            Optional.of(3),
            Optional.of("measureUnits"),
            Optional.of("description"),
            Optional.of("expression"),
            Optional.of(true),
            Optional.of("levelsList"),
            Optional.of("measureNameSqlColumnName"),
            Optional.of("measureUnqualifiedCaption"),
            Optional.of("measureGroupName"),
            Optional.of("defaultFormatString")
        );

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.mdSchemaMeasures(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST,
                "MDSCHEMA_MEASURES")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);

        checkRowValues(xmlAssert, Map.ofEntries(
            Map.entry("CATALOG_NAME", "catalogName"),
            Map.entry("SCHEMA_NAME", "schemaName"),
            Map.entry("CUBE_NAME", "cubeName"),
            Map.entry("MEASURE_NAME", "measureName"),
            Map.entry("MEASURE_UNIQUE_NAME", "measureUniqueName"),
            Map.entry("MEASURE_CAPTION", "measureCaption"),
            Map.entry("MEASURE_GUID","1"),
            Map.entry("MEASURE_AGGREGATOR", "0"),
            Map.entry("DATA_TYPE", "0"),
            Map.entry("NUMERIC_PRECISION", "2"),
            Map.entry("NUMERIC_SCALE", "3"),
            Map.entry("MEASURE_UNITS", "measureUnits"),
            Map.entry("DESCRIPTION", "description"),
            Map.entry("EXPRESSION", "expression"),
            Map.entry("MEASURE_IS_VISIBLE", "true"),
            Map.entry("LEVELS_LIST", "levelsList"),
            Map.entry("MEASURE_NAME_SQL_COLUMN_NAME", "measureNameSqlColumnName"),
            Map.entry("MEASURE_UNQUALIFIED_CAPTION", "measureUnqualifiedCaption"),
            Map.entry("MEASUREGROUP_NAME", "measureGroupName"),
            Map.entry("DEFAULT_FORMAT_STRING", "defaultFormatString")
        ));
    }

    @Test
    void test_MDSCHEMA_MEMBERS(@InjectService XmlaService xmlaService) throws Exception {

        MdSchemaMembersResponseRowR row = new MdSchemaMembersResponseRowR(
            Optional.of("catalogName"),
            Optional.of("schemaName"),
            Optional.of("cubeName"),
            Optional.of("dimensionUniqueName"),
            Optional.of("hierarchyUniqueName"),
            Optional.of("levelUniqueName"),
            Optional.of(1),
            Optional.of(2),
            Optional.of("memberName"),
            Optional.of("memberUniqueName"),
            Optional.of(MemberTypeEnum.UNKNOWN),
            Optional.of(3),
            Optional.of("measureCaption"),
            Optional.of(4),
            Optional.of(5),
            Optional.of("parentUniqueName"),
            Optional.of(6),
            Optional.of(7),
            Optional.of(8),
            Optional.of(9),
            Optional.of(true),
            Optional.of(false),
            Optional.of(ScopeEnum.GLOBAL)
        );

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.mdSchemaMembers(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST,
                "MDSCHEMA_MEMBERS")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);

        checkRowValues(xmlAssert, Map.ofEntries(
            Map.entry("CATALOG_NAME", "catalogName"),
            Map.entry("SCHEMA_NAME", "schemaName"),
            Map.entry("CUBE_NAME", "cubeName"),
            Map.entry("DIMENSION_UNIQUE_NAME", "dimensionUniqueName"),
            Map.entry("HIERARCHY_UNIQUE_NAME", "hierarchyUniqueName"),
            Map.entry("LEVEL_UNIQUE_NAME", "levelUniqueName"),
            Map.entry("LEVEL_NUMBER", "1"),
            Map.entry("MEMBER_ORDINAL", "2"),
            Map.entry("MEMBER_NAME", "memberName"),
            Map.entry("MEMBER_UNIQUE_NAME", "memberUniqueName"),
            Map.entry("MEMBER_TYPE", "0"),
            Map.entry("MEMBER_GUID", "3"),
            Map.entry("MEMBER_CAPTION", "measureCaption"),
            Map.entry("CHILDREN_CARDINALITY", "4"),
            Map.entry("PARENT_LEVEL", "5"),
            Map.entry("PARENT_UNIQUE_NAME", "parentUniqueName"),
            Map.entry("PARENT_COUNT", "6"),
            Map.entry("DESCRIPTION", "7"),
            Map.entry("EXPRESSION", "8"),
            Map.entry("MEMBER_KEY", "9"),
            Map.entry("IS_PLACEHOLDERMEMBER", "true"),
            Map.entry("IS_DATAMEMBER", "false"),
            Map.entry("SCOPE", "1")
        ));
    }

    @Test
    void test_MDSCHEMA_PROPERTIES(@InjectService XmlaService xmlaService) throws Exception {

        MdSchemaPropertiesResponseRowR row = new MdSchemaPropertiesResponseRowR(
            Optional.of("catalogName"),
            Optional.of("schemaName"),
            Optional.of("cubeName"),
            Optional.of("dimensionUniqueName"),
            Optional.of("hierarchyUniqueName"),
            Optional.of("levelUniqueName"),
            Optional.of("memberUniqueName"),
            Optional.of(PropertyTypeEnum.PROPERTY_MEMBER),
            Optional.of("propertyName"),
            Optional.of("propertyCaption"),
            Optional.of(LevelDbTypeEnum.DBTYPE_EMPTY),
            Optional.of(1),
            Optional.of(2),
            Optional.of(3),
            Optional.of(4),
            Optional.of("description"),
            Optional.of(PropertyContentTypeEnum.REGULAR),
            Optional.of("sqlColumnName"),
            Optional.of(5),
            Optional.of(PropertyOriginEnum.USER_DEFINED),
            Optional.of("propertyAttributeHierarchyName"),
            Optional.of(PropertyCardinalityEnum.ONE),
            Optional.of("mimeType"),
            Optional.of(true)
        );

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.mdSchemaProperties(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST,
                "MDSCHEMA_PROPERTIES")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);

        checkRowValues(xmlAssert, Map.ofEntries(
            Map.entry("CATALOG_NAME", "catalogName"),
            Map.entry("SCHEMA_NAME", "schemaName"),
            Map.entry("CUBE_NAME", "cubeName"),
            Map.entry("DIMENSION_UNIQUE_NAME", "dimensionUniqueName"),
            Map.entry("HIERARCHY_UNIQUE_NAME", "hierarchyUniqueName"),
            Map.entry("LEVEL_UNIQUE_NAME", "levelUniqueName"),
            Map.entry("MEMBER_UNIQUE_NAME", "memberUniqueName"),
            Map.entry("PROPERTY_TYPE", "1"),
            Map.entry("PROPERTY_NAME", "propertyName"),
            Map.entry("PROPERTY_CAPTION", "propertyCaption"),
            Map.entry("DATA_TYPE", "0"),
            Map.entry("CHARACTER_MAXIMUM_LENGTH", "1"),
            Map.entry("CHARACTER_OCTET_LENGTH", "2"),
            Map.entry("NUMERIC_PRECISION", "3"),
            Map.entry("NUMERIC_SCALE", "4"),
            Map.entry("DESCRIPTION", "description"),
            Map.entry("PROPERTY_CONTENT_TYPE", "0x00"),
            Map.entry("SQL_COLUMN_NAME", "sqlColumnName"),
            Map.entry("LANGUAGE", "5"),
            Map.entry("PROPERTY_ORIGIN", "1"),
            Map.entry("PROPERTY_ATTRIBUTE_HIERARCHY_NAME", "propertyAttributeHierarchyName"),
            Map.entry("PROPERTY_CARDINALITY", "ONE"),
            Map.entry("MIME_TYPE", "mimeType"),
            Map.entry("PROPERTY_IS_VISIBLE", "true")
        ));
    }

    @Test
    void test_MDSCHEMA_SETS(@InjectService XmlaService xmlaService) throws Exception {

        MdSchemaSetsResponseRowR row = new MdSchemaSetsResponseRowR(
            Optional.of("catalogName"),
            Optional.of("schemaName"),
            Optional.of("cubeName"),
            Optional.of("setName"),
            Optional.of(ScopeEnum.GLOBAL),
            Optional.of("description"),
            Optional.of("expression"),
            Optional.of("dimension"),
            Optional.of("setCaption"),
            Optional.of("setDisplayFolder"),
            Optional.of(SetEvaluationContextEnum.STATIC)
        );

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.mdSchemaSets(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST,
                "MDSCHEMA_SETS")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);

        checkRowValues(xmlAssert, Map.ofEntries(
            Map.entry("CATALOG_NAME", "catalogName"),
            Map.entry("SCHEMA_NAME", "schemaName"),
            Map.entry("CUBE_NAME", "cubeName"),
            Map.entry("SET_NAME", "setName"),
            Map.entry("SCOPE",  "1"),
            Map.entry("DESCRIPTION", "description"),
            Map.entry("EXPRESSION", "expression"),
            Map.entry("DIMENSIONS", "dimension"),
            Map.entry("SET_CAPTION", "setCaption"),
            Map.entry("SET_DISPLAY_FOLDER", "setDisplayFolder"),
            Map.entry("SET_EVALUATION_CONTEXT", "1")
        ));
    }

    private void checkRow(XmlAssert xmlAssert) {
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/return/rowset:root")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/return/rowset:root/rowset:row")
            .exist();
        xmlAssert.valueByXPath("count(/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/return/rowset:root/rowset:row)"
        ).isEqualTo(1);
    }

    private void checkRowValues(XmlAssert xmlAssert, Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            xmlAssert.valueByXPath(new StringBuilder("/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/return/rowset" +
                ":root/rowset:row/rowset:").append(entry.getKey()).toString())
                .isEqualTo(entry.getValue());
        }
    }
}
