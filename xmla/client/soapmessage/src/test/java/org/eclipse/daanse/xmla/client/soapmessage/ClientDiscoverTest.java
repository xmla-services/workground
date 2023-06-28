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
package org.eclipse.daanse.xmla.client.soapmessage;

import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.Provider;
import org.eclipse.daanse.ws.api.whiteboard.annotations.RequireSoapWhiteboard;
import org.eclipse.daanse.xmla.api.common.enums.AuthenticationModeEnum;
import org.eclipse.daanse.xmla.api.common.enums.ClientCacheRefreshPolicyEnum;
import org.eclipse.daanse.xmla.api.common.enums.ColumnFlagsEnum;
import org.eclipse.daanse.xmla.api.common.enums.ColumnOlapTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.LevelDbTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.LiteralNameEnumValueEnum;
import org.eclipse.daanse.xmla.api.common.enums.ObjectExpansionEnum;
import org.eclipse.daanse.xmla.api.common.enums.ProviderTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.SearchableEnum;
import org.eclipse.daanse.xmla.api.common.enums.TableTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.TypeEnum;
import org.eclipse.daanse.xmla.api.common.properties.PropertyListElementDefinition;
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
import org.eclipse.daanse.xmla.model.record.discover.PropertiesR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.catalogs.DbSchemaCatalogsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.catalogs.DbSchemaCatalogsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.columns.DbSchemaColumnsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.columns.DbSchemaColumnsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.providertypes.DbSchemaProviderTypesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.providertypes.DbSchemaProviderTypesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.schemata.DbSchemaSchemataRequestR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.schemata.DbSchemaSchemataRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.sourcetables.DbSchemaSourceTablesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.sourcetables.DbSchemaSourceTablesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.tables.DbSchemaTablesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.tables.DbSchemaTablesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.tablesinfo.DbSchemaTablesInfoRequestR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.tablesinfo.DbSchemaTablesInfoRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.discover.datasources.DiscoverDataSourcesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.discover.datasources.DiscoverDataSourcesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.discover.enumerators.DiscoverEnumeratorsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.discover.enumerators.DiscoverEnumeratorsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.discover.keywords.DiscoverKeywordsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.discover.keywords.DiscoverKeywordsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.discover.literals.DiscoverLiteralsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.discover.literals.DiscoverLiteralsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.discover.properties.DiscoverPropertiesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.discover.properties.DiscoverPropertiesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.discover.schemarowsets.DiscoverSchemaRowsetsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.discover.schemarowsets.DiscoverSchemaRowsetsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.discover.xmlmetadata.DiscoverXmlMetaDataRequestR;
import org.eclipse.daanse.xmla.model.record.discover.discover.xmlmetadata.DiscoverXmlMetaDataRestrictionsR;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.RequireServiceComponentRuntime;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.common.dictionary.Dictionaries;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.xmlunit.assertj3.XmlAssert;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.daanse.xmla.client.soapmessage.Responses.CATALOGS;
import static org.eclipse.daanse.xmla.client.soapmessage.Responses.DATA_SOURCES;
import static org.eclipse.daanse.xmla.client.soapmessage.Responses.ENUMERARORS;
import static org.eclipse.daanse.xmla.client.soapmessage.Responses.KEYWORDS;
import static org.eclipse.daanse.xmla.client.soapmessage.Responses.LITERALS;
import static org.eclipse.daanse.xmla.client.soapmessage.Responses.PROPERTIES;
import static org.eclipse.daanse.xmla.client.soapmessage.Responses.PROVIDER_TYPES;
import static org.eclipse.daanse.xmla.client.soapmessage.Responses.SCHEMAROWSETS;
import static org.eclipse.daanse.xmla.client.soapmessage.Responses.SCHEMATA;
import static org.eclipse.daanse.xmla.client.soapmessage.Responses.SOURCE_TABLES;
import static org.eclipse.daanse.xmla.client.soapmessage.Responses.TABLES;
import static org.eclipse.daanse.xmla.client.soapmessage.Responses.TABLES_INFO;
import static org.eclipse.daanse.xmla.client.soapmessage.Responses.XML_META_DATA;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RequireSoapWhiteboard
@ExtendWith(ConfigurationExtension.class)
@WithFactoryConfiguration(factoryPid = "org.eclipse.daanse.ws.handler.SOAPLoggingHandler", name = "test-ms-config", location = "?", properties = {
        @Property(key = "osgi.soap.endpoint.selector", value = "(service.pid=*)") })
@RequireServiceComponentRuntime
class ClientDiscoverTest {
    XmlaServiceClientImpl client = new XmlaServiceClientImpl("http://localhost:8090/xmla");
    // Register a Provider using whiteboardpattern and xmlassert to check xml
    @InjectBundleContext
    BundleContext bc;

    private ArgumentCaptor<SOAPMessage> requestMessageCaptor;

    @BeforeEach
    void beforeEach() throws InterruptedException {
        requestMessageCaptor = ArgumentCaptor.forClass(SOAPMessage.class);
    }

    @Test
    void testDataSources() throws Exception {
        Provider<SOAPMessage> provider = registerService(DATA_SOURCES);
        PropertiesR properties = new PropertiesR();
        properties.addProperty(PropertyListElementDefinition.DATA_SOURCE_INFO, "FoodMart");
        properties.addProperty(PropertyListElementDefinition.CONTENT, "SchemaData");
        DiscoverDataSourcesRestrictionsR restrictions = new DiscoverDataSourcesRestrictionsR(
            "dataSourceName",
            Optional.of("dataSourceDescription"),
            Optional.of("url"), Optional.of("dataSourceInfo"),
            "providerName",
            Optional.of(ProviderTypeEnum.DMP),
            Optional.of(AuthenticationModeEnum.AUTHENTICATED)
        );

        DiscoverDataSourcesRequest dataSourcesRequest = new DiscoverDataSourcesRequestR(properties, restrictions);

        List<DiscoverDataSourcesResponseRow> rows = client.discover()
                .dataSources(dataSourcesRequest);

        assertThat(rows).isNotNull().hasSize(1);
        assertThat(rows.get(0)).isNotNull();
        DiscoverDataSourcesResponseRow r = rows.get(0);
        assertThat(r.dataSourceName()).isEqualTo("FoodMart");
        assertThat(r.dataSourceDescription()).isPresent().contains("Foodmart 2000 on local machine");
        assertThat(r.url()).isPresent().contains("http://localhost/xmla/msxisapi.dll");
        assertThat(r.dataSourceInfo()).isPresent().contains("Foodmart 2000");
        assertThat(r.providerType()).isPresent().contains(ProviderTypeEnum.TDP);
        assertThat(r.authenticationMode()).isPresent().contains(AuthenticationModeEnum.UNAUTHENTICATED);

        verify(provider, (times(1))).invoke(requestMessageCaptor.capture());

        SOAPMessage request = requestMessageCaptor.getValue();

        request.writeTo(System.out);
        XmlAssert xmlAssert = XMLUtil.createAssert(request);
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType").isEqualTo("DISCOVER_DATASOURCES");
        // Restrictions
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/DataSourceName")
            .isEqualTo("dataSourceName");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/DataSourceDescription")
            .isEqualTo("dataSourceDescription");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/URL")
            .isEqualTo("url");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/DataSourceInfo")
            .isEqualTo("dataSourceInfo");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/ProviderName")
            .isEqualTo("providerName");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/ProviderType")
            .isEqualTo("DMP");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/AuthenticationMode")
            .isEqualTo("Authenticated");
        // Properties
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/DataSourceInfo")
            .isEqualTo("FoodMart");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/Content")
            .isEqualTo("SchemaData");
    }


    @Test
    void testDiscoverEnumerators() throws Exception {
        Provider<SOAPMessage> provider = registerService(ENUMERARORS);
        PropertiesR properties = new PropertiesR();
        properties.addProperty(PropertyListElementDefinition.DATA_SOURCE_INFO, "FoodMart");
        properties.addProperty(PropertyListElementDefinition.CONTENT, "SchemaData");
        DiscoverEnumeratorsRestrictionsR restrictions = new DiscoverEnumeratorsRestrictionsR(Optional.of("FoodMart"));

        DiscoverEnumeratorsRequest discoverEnumeratorsRequest = new DiscoverEnumeratorsRequestR(properties, restrictions);

        List<DiscoverEnumeratorsResponseRow> rows = client.discover()
            .discoverEnumerators(discoverEnumeratorsRequest);

        assertThat(rows).isNotNull().hasSize(1);
        assertThat(rows.get(0)).isNotNull();
        DiscoverEnumeratorsResponseRow r = rows.get(0);
        assertThat(r.enumName()).isEqualTo("EnumName");
        assertThat(r.enumDescription()).isPresent().contains("EnumDescription");
        assertThat(r.enumType()).isEqualTo("EnumType");
        assertThat(r.elementName()).isEqualTo("ElementName");
        assertThat(r.elementDescription()).isPresent().contains("ElementDescription");
        assertThat(r.elementValue()).isPresent().contains("ElementValue");

        verify(provider, (times(1))).invoke(requestMessageCaptor.capture());

        SOAPMessage request = requestMessageCaptor.getValue();

        request.writeTo(System.out);
        XmlAssert xmlAssert = XMLUtil.createAssert(request);
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType").isEqualTo("DISCOVER_ENUMERATORS");
        // Restrictions
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/EnumName")
            .isEqualTo("FoodMart");
        // Properties
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/DataSourceInfo")
            .isEqualTo("FoodMart");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/Content")
            .isEqualTo("SchemaData");
    }

    @Test
    void testDiscoverKeywords() throws Exception {
        Provider<SOAPMessage> provider = registerService(KEYWORDS);
        PropertiesR properties = new PropertiesR();
        properties.addProperty(PropertyListElementDefinition.DATA_SOURCE_INFO, "FoodMart");
        properties.addProperty(PropertyListElementDefinition.CONTENT, "SchemaData");
        DiscoverKeywordsRestrictionsR restrictions = new DiscoverKeywordsRestrictionsR(Optional.of("Keyword"));

        DiscoverKeywordsRequest discoverKeywordsRequest = new DiscoverKeywordsRequestR(properties, restrictions);

        List<DiscoverKeywordsResponseRow> rows = client.discover()
            .discoverKeywords(discoverKeywordsRequest);

        assertThat(rows).isNotNull().hasSize(1);
        assertThat(rows.get(0)).isNotNull();
        DiscoverKeywordsResponseRow r = rows.get(0);
        assertThat(r.keyword()).isEqualTo("Keyword");

        verify(provider, (times(1))).invoke(requestMessageCaptor.capture());

        SOAPMessage request = requestMessageCaptor.getValue();

        request.writeTo(System.out);
        XmlAssert xmlAssert = XMLUtil.createAssert(request);
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType").isEqualTo("DISCOVER_KEYWORDS");
        // Restrictions
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/Keyword")
            .isEqualTo("Keyword");
        // Properties
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/DataSourceInfo")
            .isEqualTo("FoodMart");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/Content")
            .isEqualTo("SchemaData");
    }

    @Test
    void testDiscoverLiterals() throws Exception {
        Provider<SOAPMessage> provider = registerService(LITERALS);
        PropertiesR properties = new PropertiesR();
        properties.addProperty(PropertyListElementDefinition.DATA_SOURCE_INFO, "FoodMart");
        properties.addProperty(PropertyListElementDefinition.CONTENT, "SchemaData");
        DiscoverLiteralsRestrictionsR restrictions = new DiscoverLiteralsRestrictionsR(Optional.of("LiteralName"));

        DiscoverLiteralsRequest discoverLiteralsRequest = new DiscoverLiteralsRequestR(properties, restrictions);

        List<DiscoverLiteralsResponseRow> rows = client.discover()
            .discoverLiterals(discoverLiteralsRequest);

        assertThat(rows).isNotNull().hasSize(1);
        assertThat(rows.get(0)).isNotNull();
        DiscoverLiteralsResponseRow r = rows.get(0);
        assertThat(r.literalName()).isEqualTo("LiteralName");
        assertThat(r.literalValue()).isEqualTo("LiteralValue");
        assertThat(r.literalInvalidChars()).isEqualTo("LiteralInvalidChars");
        assertThat(r.literalInvalidStartingChars()).isEqualTo("LiteralInvalidStartingChars");
        assertThat(r.literalMaxLength()).isEqualTo(100);
        assertThat(r.literalNameEnumValue()).isEqualTo(LiteralNameEnumValueEnum.DBLITERAL_BINARY_LITERAL);

        verify(provider, (times(1))).invoke(requestMessageCaptor.capture());

        SOAPMessage request = requestMessageCaptor.getValue();

        request.writeTo(System.out);
        XmlAssert xmlAssert = XMLUtil.createAssert(request);
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType").isEqualTo("DISCOVER_LITERALS");
        // Restrictions
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/LiteralName")
            .isEqualTo("LiteralName");
        // Properties
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/DataSourceInfo")
            .isEqualTo("FoodMart");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/Content")
            .isEqualTo("SchemaData");
    }

    @Test
    void testDiscoverProperties() throws Exception {
        Provider<SOAPMessage> provider = registerService(PROPERTIES);
        PropertiesR properties = new PropertiesR();
        properties.addProperty(PropertyListElementDefinition.DATA_SOURCE_INFO, "FoodMart");
        properties.addProperty(PropertyListElementDefinition.CONTENT, "SchemaData");
        DiscoverPropertiesRestrictionsR restrictions = new DiscoverPropertiesRestrictionsR(Optional.of("PropertyName"));

        DiscoverPropertiesRequest discoverPropertiesRequest = new DiscoverPropertiesRequestR(properties, restrictions);

        List<DiscoverPropertiesResponseRow> rows = client.discover()
            .discoverProperties(discoverPropertiesRequest);

        assertThat(rows).isNotNull().hasSize(1);
        assertThat(rows.get(0)).isNotNull();
        DiscoverPropertiesResponseRow r = rows.get(0);

        assertThat(r.propertyName()).isEqualTo("PropertyName");
        assertThat(r.propertyDescription()).isPresent().contains("PropertyDescription");
        assertThat(r.propertyType()).isPresent().contains("PropertyType");
        assertThat(r.propertyAccessType()).isEqualTo("PropertyAccessType");
        assertThat(r.required()).isPresent().contains(true);
        assertThat(r.value()).isPresent().contains("Value");

        verify(provider, (times(1))).invoke(requestMessageCaptor.capture());

        SOAPMessage request = requestMessageCaptor.getValue();

        request.writeTo(System.out);
        XmlAssert xmlAssert = XMLUtil.createAssert(request);
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType").isEqualTo("DISCOVER_PROPERTIES");
        // Restrictions
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/PropertyName")
            .isEqualTo("PropertyName");
        // Properties
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/DataSourceInfo")
            .isEqualTo("FoodMart");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/Content")
            .isEqualTo("SchemaData");
    }

    @Test
    void testSchemaRowSets() throws Exception {
        Provider<SOAPMessage> provider = registerService(SCHEMAROWSETS);
        PropertiesR properties = new PropertiesR();
        properties.addProperty(PropertyListElementDefinition.DATA_SOURCE_INFO, "FoodMart");
        properties.addProperty(PropertyListElementDefinition.CONTENT, "SchemaData");
        DiscoverSchemaRowsetsRestrictionsR restrictions = new DiscoverSchemaRowsetsRestrictionsR(Optional.of("SchemaName"));

        DiscoverSchemaRowsetsRequest discoverSchemaRowsetsRequest = new DiscoverSchemaRowsetsRequestR(properties, restrictions);

        List<DiscoverSchemaRowsetsResponseRow> rows = client.discover()
            .discoverSchemaRowsets(discoverSchemaRowsetsRequest);

        assertThat(rows).isNotNull().hasSize(1);
        assertThat(rows.get(0)).isNotNull();
        DiscoverSchemaRowsetsResponseRow r = rows.get(0);

        assertThat(r.schemaName()).isEqualTo("SchemaName");
        assertThat(r.schemaGuid()).isPresent().contains("SchemaGuid");
        assertThat(r.restrictions()).isPresent().contains("Restrictions");
        assertThat(r.description()).isPresent().contains("Description");
        assertThat(r.restrictionsMask()).isPresent().contains(100l);

        verify(provider, (times(1))).invoke(requestMessageCaptor.capture());

        SOAPMessage request = requestMessageCaptor.getValue();

        request.writeTo(System.out);
        XmlAssert xmlAssert = XMLUtil.createAssert(request);
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType").isEqualTo("DISCOVER_SCHEMA_ROWSETS");
        // Restrictions
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/SchemaName")
            .isEqualTo("SchemaName");
        // Properties
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/DataSourceInfo")
            .isEqualTo("FoodMart");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/Content")
            .isEqualTo("SchemaData");
    }

    @Test
    @SuppressWarnings("java:S5961")
    void testXmlMetaData() throws Exception {
        Provider<SOAPMessage> provider = registerService(XML_META_DATA);
        PropertiesR properties = new PropertiesR();
        properties.addProperty(PropertyListElementDefinition.DATA_SOURCE_INFO, "FoodMart");
        properties.addProperty(PropertyListElementDefinition.CONTENT, "SchemaData");
        DiscoverXmlMetaDataRestrictionsR restrictions = new DiscoverXmlMetaDataRestrictionsR(
            Optional.of("DatabaseId"),
            Optional.of("DimensionId"),
            Optional.of("CubeId"),
            Optional.of("MeasureGroupId"),
            Optional.of("PartitionId"),
            Optional.of("PerspectiveId"),
            Optional.of("DimensionPermissionId"),
            Optional.of("RoleId"),
            Optional.of("DatabasePermissionId"),
            Optional.of("MiningModelId"),
            Optional.of("MiningModelPermissionId"),
            Optional.of("DataSourceId"),
            Optional.of("MiningStructureId"),
            Optional.of("AggregationDesignId"),
            Optional.of("TraceId"),
            Optional.of("MiningStructurePermissionId"),
            Optional.of("CubePermissionId"),
            Optional.of("AssemblyId"),
            Optional.of("MdxScriptId"),
            Optional.of("DataSourceViewId"),
            Optional.of("DataSourcePermissionId"),
            Optional.of(ObjectExpansionEnum.EXPAND_OBJECT)
        );

        DiscoverXmlMetaDataRequest discoverXmlMetaDataRequest = new DiscoverXmlMetaDataRequestR(properties, restrictions);

        List<DiscoverXmlMetaDataResponseRow> rows = client.discover()
            .xmlMetaData(discoverXmlMetaDataRequest);

        assertThat(rows).isNotNull().hasSize(1);
        assertThat(rows.get(0)).isNotNull();
        DiscoverXmlMetaDataResponseRow r = rows.get(0);

        assertThat(r.metaData()).isEqualTo("MetaData");

        verify(provider, (times(1))).invoke(requestMessageCaptor.capture());

        SOAPMessage request = requestMessageCaptor.getValue();

        request.writeTo(System.out);
        XmlAssert xmlAssert = XMLUtil.createAssert(request);
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType").isEqualTo("DISCOVER_XML_METADATA");
        // Restrictions
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/DatabaseID")
            .isEqualTo("DatabaseId");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/DimensionID").isEqualTo("DimensionId");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/CubeID").isEqualTo("CubeId");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/MeasureGroupID").isEqualTo("MeasureGroupId");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/PartitionID").isEqualTo("PartitionId");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/PerspectiveID").isEqualTo("PerspectiveId");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/DimensionPermissionID").isEqualTo("DimensionPermissionId");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/RoleID").isEqualTo("RoleId");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/DatabasePermissionID").isEqualTo("DatabasePermissionId");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/MiningModelID").isEqualTo("MiningModelId");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/MiningModelPermissionID").isEqualTo("MiningModelPermissionId");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/DataSourceID").isEqualTo("DataSourceId");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/MiningStructureID").isEqualTo("MiningStructureId");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/AggregationDesignID").isEqualTo("AggregationDesignId");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/TraceID").isEqualTo("TraceId");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/MiningStructurePermissionID").isEqualTo("MiningStructurePermissionId");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/CubePermissionID").isEqualTo("CubePermissionId");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/AssemblyID").isEqualTo("AssemblyId");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/MdxScriptID").isEqualTo("MdxScriptId");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/DataSourceViewID").isEqualTo("DataSourceViewId");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/DataSourcePermissionID").isEqualTo("DataSourcePermissionId");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/ObjectExpansion").isEqualTo("ExpandObject");

        // Properties
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/DataSourceInfo")
            .isEqualTo("FoodMart");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/Content")
            .isEqualTo("SchemaData");
    }

    @Test
    void testCatalogs() throws Exception {
        Provider<SOAPMessage> provider = registerService(CATALOGS);
        PropertiesR properties = new PropertiesR();
        properties.addProperty(PropertyListElementDefinition.DATA_SOURCE_INFO, "FoodMart");
        properties.addProperty(PropertyListElementDefinition.CONTENT, "SchemaData");
        DbSchemaCatalogsRestrictionsR restrictions = new DbSchemaCatalogsRestrictionsR(Optional.of("CatalogName"));

        DbSchemaCatalogsRequest dbSchemaCatalogsRequest = new DbSchemaCatalogsRequestR(properties, restrictions);

        List<DbSchemaCatalogsResponseRow> rows = client.discover()
            .dbSchemaCatalogs(dbSchemaCatalogsRequest);

        assertThat(rows).isNotNull().hasSize(1);
        assertThat(rows.get(0)).isNotNull();
        DbSchemaCatalogsResponseRow r = rows.get(0);

        assertThat(r.catalogName()).isPresent().contains("CatalogName");
        assertThat(r.description()).isPresent().contains("Description");
        assertThat(r.roles()).isPresent().contains("Roles");
        assertThat(r.dateModified()).isPresent().contains(LocalDateTime.of(2023, Month.JANUARY, 10, 10, 45));
        assertThat(r.compatibilityLevel()).isPresent().contains(10);
        assertThat(r.type()).isPresent().contains(TypeEnum.MULTIDIMENSIONAL);
        assertThat(r.version()).isPresent().contains(10);
        assertThat(r.databaseId()).isPresent().contains("DatabaseId");
        assertThat(r.dateQueried()).isPresent().contains(LocalDateTime.of(2024, Month.JANUARY, 10, 10, 45));
        assertThat(r.currentlyUsed()).isPresent().contains(true);
        assertThat(r.popularity()).isPresent().contains(2.25);
        assertThat(r.weightedPopularity()).isPresent().contains(2.26);
        assertThat(r.clientCacheRefreshPolicy()).isPresent().contains(ClientCacheRefreshPolicyEnum.REFRESH_NEWER_DATA);

        verify(provider, (times(1))).invoke(requestMessageCaptor.capture());

        SOAPMessage request = requestMessageCaptor.getValue();

        request.writeTo(System.out);
        XmlAssert xmlAssert = XMLUtil.createAssert(request);
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType").isEqualTo("DBSCHEMA_CATALOGS");
        // Restrictions
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/CATALOG_NAME")
            .isEqualTo("CatalogName");
        // Properties
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/DataSourceInfo")
            .isEqualTo("FoodMart");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/Content")
            .isEqualTo("SchemaData");
    }

    @Test
    @SuppressWarnings("java:S5961")
    void testColumns() throws Exception {
        Provider<SOAPMessage> provider = registerService(Responses.COLUMNS);
        PropertiesR properties = new PropertiesR();
        properties.addProperty(PropertyListElementDefinition.DATA_SOURCE_INFO, "FoodMart");
        properties.addProperty(PropertyListElementDefinition.CONTENT, "SchemaData");
        DbSchemaColumnsRestrictionsR restrictions = new DbSchemaColumnsRestrictionsR(
            Optional.of("TableCatalog"),
            Optional.of("TableSchema"),
            Optional.of("TableName"),
            Optional.of("ColumnName"),
            Optional.of(ColumnOlapTypeEnum.ATTRIBUTE)
        );

        DbSchemaColumnsRequest dbSchemaColumnsRequest = new DbSchemaColumnsRequestR(properties, restrictions);

        List<DbSchemaColumnsResponseRow> rows = client.discover()
            .dbSchemaColumns(dbSchemaColumnsRequest);

        assertThat(rows).isNotNull().hasSize(1);
        assertThat(rows.get(0)).isNotNull();
        DbSchemaColumnsResponseRow r = rows.get(0);

        assertThat(r.tableCatalog()).isPresent().contains("TableCatalog");
        assertThat(r.tableSchema()).isPresent().contains("TableSchema");
        assertThat(r.tableName()).isPresent().contains("TableName");
        assertThat(r.columnName()).isPresent().contains("ColumnName");
        assertThat(r.columnGuid()).isPresent().contains(10);
        assertThat(r.columnPropId()).isPresent().contains(11);
        assertThat(r.ordinalPosition()).isPresent().contains(12);
        assertThat(r.columnHasDefault()).isPresent().contains(true);
        assertThat(r.columnDefault()).isPresent().contains("ColumnDefault");
        assertThat(r.columnFlags()).isPresent().contains(ColumnFlagsEnum.DBCOLUMNFLAGS_ISBOOKMARK);
        assertThat(r.dataType()).isPresent().contains(10);
        assertThat(r.typeGuid()).isPresent().contains(11);
        assertThat(r.characterMaximum()).isPresent().contains(12);
        assertThat(r.characterOctetLength()).isPresent().contains(14);
        assertThat(r.numericPrecision()).isPresent().contains(15);
        assertThat(r.numericScale()).isPresent().contains(16);
        assertThat(r.dateTimePrecision()).isPresent().contains(17);
        assertThat(r.characterSetCatalog()).isPresent().contains("CharacterSetCatalog");
        assertThat(r.characterSetSchema()).isPresent().contains("CharacterSetSchema");
        assertThat(r.characterSetName()).isPresent().contains("CharacterSetName");
        assertThat(r.collationCatalog()).isPresent().contains("CollationCatalog");
        assertThat(r.collationSchema()).isPresent().contains("CollationSchema");
        assertThat(r.collationName()).isPresent().contains("CollationName");
        assertThat(r.domainCatalog()).isPresent().contains("DomainCatalog");
        assertThat(r.domainSchema()).isPresent().contains("DomainSchema");
        assertThat(r.domainName()).isPresent().contains("DomainName");
        assertThat(r.description()).isPresent().contains("Description");
        assertThat(r.columnOlapType()).isPresent().contains(ColumnOlapTypeEnum.ATTRIBUTE);

        verify(provider, (times(1))).invoke(requestMessageCaptor.capture());

        SOAPMessage request = requestMessageCaptor.getValue();

        request.writeTo(System.out);
        XmlAssert xmlAssert = XMLUtil.createAssert(request);
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType").isEqualTo("DBSCHEMA_COLUMNS");
        // Restrictions
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/TABLE_CATALOG")
            .isEqualTo("TableCatalog");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/TABLE_SCHEMA")
            .isEqualTo("TableSchema");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/TABLE_NAME")
            .isEqualTo("TableName");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/COLUMN_NAME")
            .isEqualTo("ColumnName");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/COLUMN_OLAP_TYPE")
            .isEqualTo("ATTRIBUTE");

        // Properties
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/DataSourceInfo")
            .isEqualTo("FoodMart");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/Content")
            .isEqualTo("SchemaData");
    }

    @Test
    @SuppressWarnings("java:S5961")
    void testProviderTypes() throws Exception {
        Provider<SOAPMessage> provider = registerService(PROVIDER_TYPES);
        PropertiesR properties = new PropertiesR();
        properties.addProperty(PropertyListElementDefinition.DATA_SOURCE_INFO, "FoodMart");
        properties.addProperty(PropertyListElementDefinition.CONTENT, "SchemaData");
        DbSchemaProviderTypesRestrictionsR restrictions = new DbSchemaProviderTypesRestrictionsR(
            Optional.of(LevelDbTypeEnum.DBTYPE_EMPTY),
            Optional.of(true)
        );

        DbSchemaProviderTypesRequest dbSchemaProviderTypesRequest = new DbSchemaProviderTypesRequestR(properties, restrictions);

        List<DbSchemaProviderTypesResponseRow> rows = client.discover()
            .dbSchemaProviderTypes(dbSchemaProviderTypesRequest);

        assertThat(rows).isNotNull().hasSize(1);
        assertThat(rows.get(0)).isNotNull();
        DbSchemaProviderTypesResponseRow r = rows.get(0);

        assertThat(r.typeName()).isPresent().contains("TypeName");
        assertThat(r.dataType()).isPresent().contains(LevelDbTypeEnum.DBTYPE_EMPTY);
        assertThat(r.columnSize()).isPresent().contains(10);
        assertThat(r.literalPrefix()).isPresent().contains("LiteralPrefix");
        assertThat(r.literalSuffix()).isPresent().contains("LiteralSuffix");
        assertThat(r.createParams()).isPresent().contains("CreateParams");
        assertThat(r.isNullable()).isPresent().contains(true);
        assertThat(r.caseSensitive()).isPresent().contains(true);
        assertThat(r.searchable()).isPresent().contains(SearchableEnum.DB_UNSEARCHABLE);
        assertThat(r.unsignedAttribute()).isPresent().contains(true);
        assertThat(r.fixedPrecScale()).isPresent().contains(true);
        assertThat(r.autoUniqueValue()).isPresent().contains(true);
        assertThat(r.localTypeName()).isPresent().contains("LocalTypeName");
        assertThat(r.minimumScale()).isPresent().contains(10);
        assertThat(r.maximumScale()).isPresent().contains(11);
        assertThat(r.guid()).isPresent().contains(12);
        assertThat(r.typeLib()).isPresent().contains("TypeLib");
        assertThat(r.version()).isPresent().contains("Version");
        assertThat(r.isLong()).isPresent().contains(true);
        assertThat(r.bestMatch()).isPresent().contains(true);
        assertThat(r.isFixedLength()).isPresent().contains(true);

        verify(provider, (times(1))).invoke(requestMessageCaptor.capture());

        SOAPMessage request = requestMessageCaptor.getValue();

        request.writeTo(System.out);
        XmlAssert xmlAssert = XMLUtil.createAssert(request);
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType").isEqualTo("DBSCHEMA_PROVIDER_TYPES");
        // Restrictions
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/DATA_TYPE")
            .isEqualTo("0");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/BEST_MATCH")
            .isEqualTo("true");

        // Properties
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/DataSourceInfo")
            .isEqualTo("FoodMart");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/Content")
            .isEqualTo("SchemaData");
    }

    @Test
    void testSchemata() throws Exception {
        Provider<SOAPMessage> provider = registerService(SCHEMATA);
        PropertiesR properties = new PropertiesR();
        properties.addProperty(PropertyListElementDefinition.DATA_SOURCE_INFO, "FoodMart");
        properties.addProperty(PropertyListElementDefinition.CONTENT, "SchemaData");
        DbSchemaSchemataRestrictionsR restrictions = new DbSchemaSchemataRestrictionsR(
            "CatalogName",
            "SchemaName",
            "SchemaOwner"
        );

        DbSchemaSchemataRequest dbSchemaSchemataRequest = new DbSchemaSchemataRequestR(properties, restrictions);

        List<DbSchemaSchemataResponseRow> rows = client.discover()
            .dbSchemaSchemata(dbSchemaSchemataRequest);

        assertThat(rows).isNotNull().hasSize(1);
        assertThat(rows.get(0)).isNotNull();
        DbSchemaSchemataResponseRow r = rows.get(0);

        assertThat(r.catalogName()).isEqualTo("CatalogName");
        assertThat(r.schemaName()).isEqualTo("SchemaName");
        assertThat(r.schemaOwner()).isEqualTo("SchemaOwner");

        verify(provider, (times(1))).invoke(requestMessageCaptor.capture());

        SOAPMessage request = requestMessageCaptor.getValue();

        request.writeTo(System.out);
        XmlAssert xmlAssert = XMLUtil.createAssert(request);
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType").isEqualTo("DBSCHEMA_SCHEMATA");
        // Restrictions
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/CATALOG_NAME")
            .isEqualTo("CatalogName");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/SCHEMA_NAME")
            .isEqualTo("SchemaName");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/SCHEMA_OWNER")
            .isEqualTo("SchemaOwner");

        // Properties
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/DataSourceInfo")
            .isEqualTo("FoodMart");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/Content")
            .isEqualTo("SchemaData");
    }

    @Test
    void testSourceTables() throws Exception {
        Provider<SOAPMessage> provider = registerService(SOURCE_TABLES);
        PropertiesR properties = new PropertiesR();
        properties.addProperty(PropertyListElementDefinition.DATA_SOURCE_INFO, "FoodMart");
        properties.addProperty(PropertyListElementDefinition.CONTENT, "SchemaData");
        DbSchemaSourceTablesRestrictionsR restrictions = new DbSchemaSourceTablesRestrictionsR(
            Optional.of("CatalogName"),
            Optional.of("SchemaName"),
            "TableName",
            TableTypeEnum.TABLE
        );

        DbSchemaSourceTablesRequest dbSchemaSourceTablesRequest = new DbSchemaSourceTablesRequestR(properties, restrictions);

        List<DbSchemaSourceTablesResponseRow> rows = client.discover()
            .dbSchemaSourceTables(dbSchemaSourceTablesRequest);

        assertThat(rows).isNotNull().hasSize(1);
        assertThat(rows.get(0)).isNotNull();
        DbSchemaSourceTablesResponseRow r = rows.get(0);

        assertThat(r.catalogName()).isPresent().contains("CatalogName");
        assertThat(r.schemaName()).isPresent().contains("SchemaName");
        assertThat(r.tableName()).isEqualTo("TableName");
        assertThat(r.tableType()).isEqualTo(TableTypeEnum.TABLE);

        verify(provider, (times(1))).invoke(requestMessageCaptor.capture());

        SOAPMessage request = requestMessageCaptor.getValue();

        request.writeTo(System.out);
        XmlAssert xmlAssert = XMLUtil.createAssert(request);
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType").isEqualTo("DBSCHEMA_SOURCE_TABLES");
        // Restrictions
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/TABLE_CATALOG")
            .isEqualTo("CatalogName");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/TABLE_SCHEMA")
            .isEqualTo("SchemaName");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/TABLE_NAME")
            .isEqualTo("TableName");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/TABLE_TYPE")
            .isEqualTo("TABLE");

        // Properties
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/DataSourceInfo")
            .isEqualTo("FoodMart");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/Content")
            .isEqualTo("SchemaData");
    }

    @Test
    void testTables() throws Exception {
        Provider<SOAPMessage> provider = registerService(TABLES);
        PropertiesR properties = new PropertiesR();
        properties.addProperty(PropertyListElementDefinition.DATA_SOURCE_INFO, "FoodMart");
        properties.addProperty(PropertyListElementDefinition.CONTENT, "SchemaData");
        DbSchemaTablesRestrictionsR restrictions = new DbSchemaTablesRestrictionsR(
            Optional.of("TableCatalog"),
            Optional.of("TableSchema"),
            Optional.of("TableName"),
            Optional.of("TableType")
        );

        DbSchemaTablesRequest dbSchemaTablesRequest = new DbSchemaTablesRequestR(properties, restrictions);

        List<DbSchemaTablesResponseRow> rows = client.discover()
            .dbSchemaTables(dbSchemaTablesRequest);

        assertThat(rows).isNotNull().hasSize(1);
        assertThat(rows.get(0)).isNotNull();
        DbSchemaTablesResponseRow r = rows.get(0);

        assertThat(r.tableCatalog()).isPresent().contains("TableCatalog");
        assertThat(r.tableSchema()).isPresent().contains("TableSchema");
        assertThat(r.tableName()).isPresent().contains("TableName");
        assertThat(r.tableType()).isPresent().contains("TableType");
        assertThat(r.tableGuid()).isPresent().contains("TableGuid");
        assertThat(r.description()).isPresent().contains("Description");
        assertThat(r.tablePropId()).isPresent().contains(10);
        assertThat(r.dateCreated()).isPresent().contains(LocalDateTime.of(2023, Month.JANUARY, 10, 10, 45));
        assertThat(r.dateModified()).isPresent().contains(LocalDateTime.of(2024, Month.JANUARY, 10, 10, 45));

        verify(provider, (times(1))).invoke(requestMessageCaptor.capture());

        SOAPMessage request = requestMessageCaptor.getValue();

        request.writeTo(System.out);
        XmlAssert xmlAssert = XMLUtil.createAssert(request);
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType").isEqualTo("DBSCHEMA_TABLES");
        // Restrictions
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/TABLE_CATALOG")
            .isEqualTo("TableCatalog");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/TABLE_SCHEMA")
            .isEqualTo("TableSchema");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/TABLE_NAME")
            .isEqualTo("TableName");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/TABLE_TYPE")
            .isEqualTo("TableType");

        // Properties
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/DataSourceInfo")
            .isEqualTo("FoodMart");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/Content")
            .isEqualTo("SchemaData");
    }

    @Test
    @SuppressWarnings("java:S5961")
    void testTablesInfo() throws Exception {
        Provider<SOAPMessage> provider = registerService(TABLES_INFO);
        PropertiesR properties = new PropertiesR();
        properties.addProperty(PropertyListElementDefinition.DATA_SOURCE_INFO, "FoodMart");
        properties.addProperty(PropertyListElementDefinition.CONTENT, "SchemaData");
        DbSchemaTablesInfoRestrictionsR restrictions = new DbSchemaTablesInfoRestrictionsR(
            Optional.of("CatalogName"),
            Optional.of("TableSchema"),
            "TableName",
            TableTypeEnum.TABLE
        );

        DbSchemaTablesInfoRequest dbSchemaTablesInfoRequest = new DbSchemaTablesInfoRequestR(properties, restrictions);

        List<DbSchemaTablesInfoResponseRow> rows = client.discover()
            .dbSchemaTablesInfo(dbSchemaTablesInfoRequest);

        assertThat(rows).isNotNull().hasSize(1);
        assertThat(rows.get(0)).isNotNull();
        DbSchemaTablesInfoResponseRow r = rows.get(0);

        assertThat(r.catalogName()).isPresent().contains("CatalogName");
        assertThat(r.schemaName()).isPresent().contains("SchemaName");
        assertThat(r.tableName()).isEqualTo("TableName");
        assertThat(r.tableType()).isEqualTo("TableType");
        assertThat(r.tableGuid()).isPresent().contains(10);
        assertThat(r.bookmarks()).isPresent().contains(true);
        assertThat(r.bookmarkType()).isPresent().contains(10);
        assertThat(r.bookmarkDataType()).isPresent().contains(11);
        assertThat(r.bookmarkMaximumLength()).isPresent().contains(12);
        assertThat(r.bookmarkInformation()).isPresent().contains(14);
        assertThat(r.tableVersion()).isPresent().contains(15l);
        assertThat(r.cardinality()).isPresent().contains(16l);
        assertThat(r.description()).isPresent().contains("Description");
        assertThat(r.tablePropId()).isPresent().contains(17);

        verify(provider, (times(1))).invoke(requestMessageCaptor.capture());

        SOAPMessage request = requestMessageCaptor.getValue();

        request.writeTo(System.out);
        XmlAssert xmlAssert = XMLUtil.createAssert(request);
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/RequestType").isEqualTo("DBSCHEMA_TABLES_INFO");
        // Restrictions
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/TABLE_CATALOG")
            .isEqualTo("CatalogName");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/TABLE_SCHEMA")
            .isEqualTo("TableSchema");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/TABLE_NAME")
            .isEqualTo("TableName");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Restrictions/RestrictionList/TABLE_TYPE")
            .isEqualTo("TABLE");

        // Properties
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/DataSourceInfo")
            .isEqualTo("FoodMart");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Discover/Properties/PropertyList/Content")
            .isEqualTo("SchemaData");
    }

    private Provider<SOAPMessage> registerService(String xml) throws InterruptedException {
        Provider<SOAPMessage> provider = spy(new MockProvider(xml));
        bc.registerService(Provider.class, provider, Dictionaries.dictionaryOf("osgi.soap.endpoint.contextpath",
            "/xmla", "osgi.soap.endpoint.implementor", "true"));
        TimeUnit.SECONDS.sleep(2);
        return provider;
    }

}
