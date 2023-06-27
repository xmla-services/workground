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
import org.eclipse.daanse.xmla.api.common.enums.LiteralNameEnumValueEnum;
import org.eclipse.daanse.xmla.api.common.enums.ObjectExpansionEnum;
import org.eclipse.daanse.xmla.api.common.enums.ProviderTypeEnum;
import org.eclipse.daanse.xmla.api.common.properties.PropertyListElementDefinition;
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

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.eclipse.daanse.xmla.client.soapmessage.Responses.DATA_SOURCES;
import static org.eclipse.daanse.xmla.client.soapmessage.Responses.ENUMERARORS;
import static org.eclipse.daanse.xmla.client.soapmessage.Responses.KEYWORDS;
import static org.eclipse.daanse.xmla.client.soapmessage.Responses.LITERALS;
import static org.eclipse.daanse.xmla.client.soapmessage.Responses.PROPERTIES;
import static org.eclipse.daanse.xmla.client.soapmessage.Responses.SCHEMAROWSETS;
import static org.eclipse.daanse.xmla.client.soapmessage.Responses.XML_META_DATA;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

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

    private Provider<SOAPMessage> registerService(String xml) throws InterruptedException {
        Provider<SOAPMessage> provider = spy(new MockProvider(xml));
        bc.registerService(Provider.class, provider, Dictionaries.dictionaryOf("osgi.soap.endpoint.contextpath",
            "/xmla", "osgi.soap.endpoint.implementor", "true"));
        TimeUnit.SECONDS.sleep(2);
        return provider;
    }

}
