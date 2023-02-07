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

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.daanse.xmla.api.common.enums.CoordinateTypeEnum.CUBE;
import static org.eclipse.daanse.xmla.api.common.enums.InvocationEnum.NORMAL_OPERATION;
import static org.eclipse.daanse.xmla.api.common.properties.Content.SchemaData;
import static org.eclipse.daanse.xmla.api.common.properties.Format.Tabular;
import static org.eclipse.daanse.xmla.api.discover.mdschema.hierarchies.MdSchemaHierarchiesRestrictions.RESTRICTIONS_HIERARCHY_ORIGIN;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.eclipse.daanse.xmla.api.XmlaService;
import org.eclipse.daanse.xmla.api.common.enums.*;
import org.eclipse.daanse.xmla.api.discover.DiscoverService;
import org.eclipse.daanse.xmla.api.discover.dbschema.tables.DbSchemaTablesRequest;
import org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesRequest;
import org.eclipse.daanse.xmla.api.discover.discover.enumerators.DiscoverEnumeratorsRequest;
import org.eclipse.daanse.xmla.api.discover.discover.keywords.DiscoverKeywordsRequest;
import org.eclipse.daanse.xmla.api.discover.discover.literals.DiscoverLiteralsRequest;
import org.eclipse.daanse.xmla.api.discover.discover.properties.DiscoverPropertiesRequest;
import org.eclipse.daanse.xmla.api.discover.discover.schemarowsets.DiscoverSchemaRowsetsRequest;
import org.eclipse.daanse.xmla.api.discover.discover.xmlmetadata.DiscoverXmlMetaDataRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.actions.MdSchemaActionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.cubes.MdSchemaCubesRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.functions.MdSchemaFunctionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.hierarchies.MdSchemaHierarchiesRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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

@ExtendWith(ConfigurationExtension.class)
@WithFactoryConfiguration(factoryPid = Constants.PID_MS_SOAP, name = "test-ms-config", location = "?", properties = {
        @Property(key = "xmlaService.target", value = "(" + Constants.XMLASERVICE_FILTER_KEY + "="
                + Constants.XMLASERVICE_FILTER_VALUE + ")"),
        @Property(key = "osgi.soap.endpoint.contextpath", value = Constants.WS_PATH) })
@WithFactoryConfiguration(factoryPid = "org.eclipse.daanse.ws.handler.SOAPLoggingHandler", name = "test-ms-config", location = "?", properties = {
        @Property(key = "osgi.soap.endpoint.selector", value = "(service.pid=*)") })
@RequireServiceComponentRuntime
public class DiscoverRequestTest {
    private Logger logger = LoggerFactory.getLogger(DiscoverRequestTest.class);

    @InjectBundleContext
    BundleContext bc;

    @BeforeEach
    void beforaEach() throws InterruptedException {
        XmlaService xmlaService = mock(XmlaService.class);
        DiscoverService discoverService = mock(DiscoverService.class);

        when(xmlaService.discover()).thenReturn(discoverService);


        bc.registerService(XmlaService.class, xmlaService, FrameworkUtil
                .asDictionary(Map.of(Constants.XMLASERVICE_FILTER_KEY, Constants.XMLASERVICE_FILTER_VALUE)));
        TimeUnit.SECONDS.sleep(2);
    }

    @Test
    void test_DISCOVER_PROPERTIES_LocaleIdentifier(@InjectService XmlaService xmlaService) throws Exception {
        ArgumentCaptor<DiscoverPropertiesRequest> captor = ArgumentCaptor.forClass(DiscoverPropertiesRequest.class);

        final String sRequest = """
                <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
                  <RequestType>DISCOVER_PROPERTIES</RequestType>
                  <Restrictions>
                    <RestrictionList>
                    </RestrictionList>
                  </Restrictions>
                  <Properties>
                    <PropertyList>
                      <LocaleIdentifier>1033</LocaleIdentifier>
                    </PropertyList>
                  </Properties>
                </Discover>
                """;

        SOAPUtil.callSoapWebService(Constants.soapEndpointUrl, Optional.of(Constants.SOAP_ACTION_DISCOVER),
                SOAPUtil.envelop(sRequest));

        DiscoverService discoverService = xmlaService.discover();
        verify(discoverService, (times(1))).discoverProperties(captor.capture());

        DiscoverPropertiesRequest request = captor.getValue();
        assertThat(request).isNotNull()
                .satisfies(d -> {
                    assertThat(d.properties()).isNotNull()
                            .satisfies(p -> {
                                assertThat(p.localeIdentifier()).isNotNull()
                                        .isPresent()
                                        .contains(1033);
                                assertThat(p.dataSourceInfo()).isNotNull()
                                        .isNotPresent();
                                assertThat(p.content()).isNotNull()
                                        .isNotPresent();
                                assertThat(p.format()).isNotNull()
                                        .isNotPresent();

                            });
                    assertThat(d.restrictions()).isNotNull()
                            .satisfies(r -> {
                                assertThat(r.propertyName()).isNotNull()
                                        .isNotPresent();
                            });
                });
    };

    @Test
    void test_DISCOVER_PROPERTIES_Restricted(@InjectService XmlaService xmlaService) throws Exception {
        ArgumentCaptor<DiscoverPropertiesRequest> captor = ArgumentCaptor.forClass(DiscoverPropertiesRequest.class);

        final String sRequest = """
                <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
                  <RequestType>DISCOVER_PROPERTIES</RequestType>
                  <Restrictions>
                    <RestrictionList>
                      <PropertyName>ProviderVersion</PropertyName>
                    </RestrictionList>
                  </Restrictions>
                  <Properties>
                    <PropertyList>
                      <LocaleIdentifier>1033</LocaleIdentifier>
                      <DataSourceInfo>FoodMart</DataSourceInfo>
                      <Content>SchemaData</Content>
                      <Format>Tabular</Format>
                    </PropertyList>
                  </Properties>
                </Discover>
                """;

        SOAPUtil.callSoapWebService(Constants.soapEndpointUrl, Optional.of(Constants.SOAP_ACTION_DISCOVER),
                SOAPUtil.envelop(sRequest));

        DiscoverService discoverService = xmlaService.discover();
        verify(discoverService, (times(1))).discoverProperties(captor.capture());

        DiscoverPropertiesRequest request = captor.getValue();
        assertThat(request).isNotNull()
                .satisfies(d -> {
                    assertThat(d.properties()).isNotNull()
                            .satisfies(p -> {
                                assertThat(p.localeIdentifier()).isNotNull()
                                        .isPresent()
                                        .contains(1033);
                                assertThat(p.dataSourceInfo()).isNotNull()
                                        .isPresent()
                                        .contains("FoodMart");
                                assertThat(p.content()).isNotNull()
                                        .isPresent()
                                        .contains(SchemaData);
                                assertThat(p.format()).isNotNull()
                                        .isPresent()
                                        .contains(Tabular);
                            });
                    assertThat(d.restrictions()).isNotNull()
                            .satisfies(r -> {
                                assertThat(r.propertyName()).isNotNull()
                                        .isPresent()
                                        .contains("ProviderVersion");
                            });
                });
    };

    @Test
    void test_DISCOVER_SCHEMA_ROWSETS(@InjectService XmlaService xmlaService) throws Exception {
        ArgumentCaptor<DiscoverSchemaRowsetsRequest> captor = ArgumentCaptor
                .forClass(DiscoverSchemaRowsetsRequest.class);
        final String sRequest = """
                <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
                  <RequestType>DISCOVER_SCHEMA_ROWSETS</RequestType>
                  <Restrictions>
                    <RestrictionList>
                      <SchemaName>FoodMart</SchemaName>
                    </RestrictionList>
                  </Restrictions>
                  <Properties>
                    <PropertyList>
                      <LocaleIdentifier>1033</LocaleIdentifier>
                      <DataSourceInfo>FoodMart</DataSourceInfo>
                      <Catalog>FoodMart</Catalog>
                      <Format>Tabular</Format>
                    </PropertyList>
                  </Properties>
                </Discover>
                """;
        SOAPUtil.callSoapWebService(Constants.soapEndpointUrl, Optional.of(Constants.SOAP_ACTION_DISCOVER),
                SOAPUtil.envelop(sRequest));

        DiscoverService discoverService = xmlaService.discover();
        verify(discoverService, (times(1))).discoverSchemaRowsets(captor.capture());

        DiscoverSchemaRowsetsRequest request = captor.getValue();
        assertThat(request).isNotNull()
                .satisfies(d -> {
                    // getRestrictions
                    assertThat(d.restrictions()).isNotNull()
                            .satisfies(r -> {
                                assertThat(r.schemaName()).isNotNull()
                                        .isPresent()
                                        .contains("FoodMart");
                            });
                    // getProperties
                    assertThat(d.properties()).isNotNull()
                            .satisfies(p -> {
                                assertThat(p.localeIdentifier()).isNotNull()
                                        .isPresent()
                                        .contains(1033);
                                assertThat(p.dataSourceInfo()).isNotNull()
                                        .isPresent()
                                        .contains("FoodMart");
                                assertThat(p.format()).isNotNull()
                                        .isPresent()
                                        .contains(Tabular);
                                assertThat(p.catalog()).isNotNull()
                                        .isPresent()
                                        .contains("FoodMart");
                            });

                });
    }

    @Test
    void test_DISCOVER_ENUMERATORS(@InjectService XmlaService xmlaService) throws Exception {
        ArgumentCaptor<DiscoverEnumeratorsRequest> captor = ArgumentCaptor.forClass(DiscoverEnumeratorsRequest.class);
        final String sRequest = """
                    <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <RequestType>DISCOVER_ENUMERATORS</RequestType>
                    <Restrictions>
                      <RestrictionList>
                        <EnumName>FoodMart</EnumName>
                      </RestrictionList>
                    </Restrictions>
                    <Properties>
                      <PropertyList>
                    <DataSourceInfo>FoodMart</DataSourceInfo>
                    <Content>SchemaData</Content>
                      </PropertyList>
                    </Properties>
                    </Discover>
                """;

        SOAPUtil.callSoapWebService(Constants.soapEndpointUrl, Optional.of(Constants.SOAP_ACTION_DISCOVER),
                SOAPUtil.envelop(sRequest));

        DiscoverService discoverService = xmlaService.discover();
        verify(discoverService, (times(1))).discoverEnumerators(captor.capture());

        DiscoverEnumeratorsRequest request = captor.getValue();
        assertThat(request).isNotNull()
                .satisfies(d -> {
                    // getRestrictions
                    assertThat(d.restrictions()).isNotNull()
                            .satisfies(r -> {
                                assertThat(r.enumName()).isNotNull()
                                        .contains("FoodMart");
                            });
                    // getProperties
                    assertThat(d.properties()).isNotNull()
                            .satisfies(p -> {
                                assertThat(p.localeIdentifier()).isNotNull()
                                        .isNotPresent();
                                assertThat(p.dataSourceInfo()).isNotNull()
                                        .isPresent()
                                        .contains("FoodMart");
                                assertThat(p.format()).isNotNull()
                                        .isNotPresent();
                                assertThat(p.content()).isNotNull()
                                        .isPresent()
                                        .contains(SchemaData);
                            });
                });
    }

    @Test
    void test_DISCOVER_KEYWORDS(@InjectService XmlaService xmlaService) throws Exception {
        ArgumentCaptor<DiscoverKeywordsRequest> captor = ArgumentCaptor.forClass(DiscoverKeywordsRequest.class);
        final String sRequest = """
                    <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <RequestType>DISCOVER_KEYWORDS</RequestType>
                    <Restrictions>
                      <RestrictionList>
                    <Keyword>Keyword1</Keyword>
                      </RestrictionList>
                    </Restrictions>
                    <Properties>
                      <PropertyList>
                        <DataSourceInfo>FoodMart</DataSourceInfo>
                        <Content>SchemaData</Content>
                      </PropertyList>
                    </Properties>
                    </Discover>
                """;

        SOAPUtil.callSoapWebService(Constants.soapEndpointUrl, Optional.of(Constants.SOAP_ACTION_DISCOVER),
                SOAPUtil.envelop(sRequest));

        DiscoverService discoverService = xmlaService.discover();
        verify(discoverService, (times(1))).discoverKeywords(captor.capture());

        DiscoverKeywordsRequest request = captor.getValue();
        assertThat(request).isNotNull()
                .satisfies(d -> {
                    // getRestrictions
                    assertThat(d.restrictions()).isNotNull()
                            .satisfies(r -> {
                                assertThat(r.keyword()).isNotNull()
                                        .contains("Keyword1");
                            });
                    // getProperties
                    assertThat(d.properties()).isNotNull()
                            .satisfies(p -> {
                                assertThat(p.localeIdentifier()).isNotNull()
                                        .isNotPresent();
                                assertThat(p.dataSourceInfo()).isNotNull()
                                        .contains("FoodMart");
                                assertThat(p.format()).isNotNull()
                                        .isNotPresent();
                                assertThat(p.content()).isNotNull()
                                        .isPresent()
                                        .contains(SchemaData);
                            });
                });
    }

    @Test
    void test_DISCOVER_LITERALS(@InjectService XmlaService xmlaService) throws Exception {
        ArgumentCaptor<DiscoverLiteralsRequest> captor = ArgumentCaptor.forClass(DiscoverLiteralsRequest.class);
        final String sRequest = """
                    <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <RequestType>DISCOVER_LITERALS</RequestType>
                    <Restrictions>
                      <RestrictionList>
                        <LiteralName>literalName</LiteralName>
                      </RestrictionList>
                    </Restrictions>
                    <Properties>
                      <PropertyList>
                        <DataSourceInfo>FoodMart</DataSourceInfo>
                        <Content>SchemaData</Content>
                      </PropertyList>
                    </Properties>
                    </Discover>
                """;

        SOAPUtil.callSoapWebService(Constants.soapEndpointUrl, Optional.of(Constants.SOAP_ACTION_DISCOVER),
                SOAPUtil.envelop(sRequest));

        DiscoverService discoverService = xmlaService.discover();
        verify(discoverService, (times(1))).discoverLiterals(captor.capture());

        DiscoverLiteralsRequest request = captor.getValue();
        assertThat(request).isNotNull()
                .satisfies(d -> {
                    // getRestrictions
                    assertThat(d.restrictions()).isNotNull()
                            .satisfies(r -> {
                                assertThat(r.literalName()).isNotNull()
                                        .isPresent()
                                        .contains("literalName");
                            });
                    // getProperties
                    assertThat(d.properties()).isNotNull()
                            .satisfies(p -> {
                                assertThat(p.localeIdentifier()).isNotNull()
                                        .isNotPresent();
                                assertThat(p.dataSourceInfo()).isNotNull()
                                        .contains("FoodMart");
                                assertThat(p.format()).isNotNull()
                                        .isNotPresent();
                                assertThat(p.content()).isNotNull()
                                        .isPresent()
                                        .contains(SchemaData);
                            });
                });
    }

    @Test
    void test_DBSCHEMA_TABLES(@InjectService XmlaService xmlaService) throws Exception {
        ArgumentCaptor<DbSchemaTablesRequest> captor = ArgumentCaptor
                .forClass(DbSchemaTablesRequest.class);
        final String sRequest = """
                <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
                <RequestType>DBSCHEMA_TABLES</RequestType>
                <Restrictions>
                  <RestrictionList>
                    <TABLE_CATALOG>tableCatalog</TABLE_CATALOG>
                    <RESTRICTIONS_TABLE_SCHEMA>tableSchema</RESTRICTIONS_TABLE_SCHEMA>
                    <TABLE_NAME>tableName</TABLE_NAME>
                    <TABLE_TYPE>tableType</TABLE_TYPE>
                  </RestrictionList>
                </Restrictions>
                <Properties>
                  <PropertyList>
                    <DataSourceInfo>FoodMart</DataSourceInfo>
                    <Content>SchemaData</Content>
                  </PropertyList>
                </Properties>
                </Discover>""";

        SOAPUtil.callSoapWebService(Constants.soapEndpointUrl, Optional.of(Constants.SOAP_ACTION_DISCOVER),
                SOAPUtil.envelop(sRequest));

        DiscoverService discoverService = xmlaService.discover();
        verify(discoverService, (times(1))).dbSchemaTables(captor.capture());

        DbSchemaTablesRequest request = captor.getValue();
        assertThat(request).isNotNull()
                .satisfies(d -> {
                    // getRestrictions
                    assertThat(d.restrictions()).isNotNull()
                            .satisfies(r -> {
                                assertThat(r.tableCatalog()).isNotNull()
                                        .isPresent()
                                        .contains("tableCatalog");
                                assertThat(r.tableName()).isNotNull()
                                        .isPresent()
                                        .contains("tableName");
                                assertThat(r.tableType()).isNotNull()
                                        .isPresent()
                                        .contains("tableType");
                                assertThat(r.tableSchema()).isNotNull()
                                        .isPresent()
                                        .contains("tableSchema");
                            });
                    // getProperties
                    assertThat(d.properties()).isNotNull()
                            .satisfies(p -> {
                                assertThat(p.localeIdentifier()).isNotNull()
                                        .isNotPresent();
                                assertThat(p.dataSourceInfo()).isNotNull()
                                        .contains("FoodMart");
                                assertThat(p.format()).isNotNull()
                                        .isNotPresent();
                                assertThat(p.content()).isNotNull()
                                        .isPresent()
                                        .contains(SchemaData);
                            });
                });
    }

    @Test
    void test_MDSCHEMA_ACTIONS(@InjectService XmlaService xmlaService) throws Exception {

        ArgumentCaptor<MdSchemaActionsRequest> captor = ArgumentCaptor
                .forClass(MdSchemaActionsRequest.class);
        final String sRequest = """
                <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <RequestType>MDSCHEMA_ACTIONS</RequestType>
                    <Restrictions>
                      <RestrictionList>
                        <CATALOG_NAME>catalogName</CATALOG_NAME>
                        <SCHEMA_NAME>schemaName</SCHEMA_NAME>
                        <CUBE_NAME>cubeName</CUBE_NAME>
                        <ACTION_NAME>actionName</ACTION_NAME>
                        <ACTION_TYPE>0x01</ACTION_TYPE>
                        <COORDINATE>coordinate</COORDINATE>
                        <COORDINATE_TYPE>1</COORDINATE_TYPE>
                        <INVOCATION>1</INVOCATION>
                      </RestrictionList>
                    </Restrictions>
                    <Properties>
                      <PropertyList>
                        <DataSourceInfo>FoodMart</DataSourceInfo>
                        <Catalog>FoodMart</Catalog>
                        <Format>Tabular</Format>
                        <Content>SchemaData</Content>
                      </PropertyList>
                    </Properties>
                </Discover>""";

        SOAPUtil.callSoapWebService(Constants.soapEndpointUrl, Optional.of(Constants.SOAP_ACTION_DISCOVER),
                SOAPUtil.envelop(sRequest));

        DiscoverService discoverService = xmlaService.discover();
        verify(discoverService, (times(1))).mdSchemaActions(captor.capture());

        MdSchemaActionsRequest request = captor.getValue();
        assertThat(request).isNotNull()
                .satisfies(d -> {
                    // getRestrictions
                    assertThat(d.restrictions()).isNotNull()
                            .satisfies(r -> {
                                assertThat(r.catalogName()).isNotNull()
                                        .isPresent()
                                        .contains("catalogName");
                                assertThat(r.schemaName()).isNotNull()
                                        .isPresent()
                                        .contains("schemaName");
                                assertThat(r.cubeName()).isEqualTo("cubeName");
                                assertThat(r.actionName()).isNotNull()
                                        .isPresent()
                                        .contains("actionName");
                                assertThat(r.actionType()).isNotNull()
                                        .isPresent()
                                        .contains(ActionTypeEnum.URL);
                                assertThat(r.coordinate()).isNotNull()
                                        .isPresent()
                                        .contains("coordinate");
                                assertThat(r.coordinateType()).isNotNull()
                                        .isEqualTo(CUBE);
                                assertThat(r.invocation()).isNotNull()
                                        .isEqualTo(NORMAL_OPERATION);
                            });
                    // getProperties
                    assertThat(d.properties()).isNotNull()
                            .satisfies(p -> {
                                assertThat(p.localeIdentifier()).isNotNull()
                                        .isNotPresent();
                                assertThat(p.dataSourceInfo()).isNotNull()
                                        .contains("FoodMart");
                                assertThat(p.catalog()).isNotNull()
                                        .contains("FoodMart");
                                assertThat(p.format()).isNotNull()
                                        .contains(Tabular);
                                assertThat(p.content()).isNotNull()
                                        .isPresent()
                                        .contains(SchemaData);
                            });
                });
    }

    @Test
    void test_MDSCHEMA_CUBES(@InjectService XmlaService xmlaService) throws Exception {

        ArgumentCaptor<MdSchemaCubesRequest> captor = ArgumentCaptor
                .forClass(MdSchemaCubesRequest.class);
        final String sRequest = """
                    <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
                        <RequestType>MDSCHEMA_CUBES</RequestType>
                        <Restrictions>
                          <RestrictionList>
                            <SCHEMA_NAME>schemaName</SCHEMA_NAME>
                            <CUBE_NAME>cubeName</CUBE_NAME>
                            <BASE_CUBE_NAME>baseCubeName</BASE_CUBE_NAME>
                            <CUBE_SOURCE>0x01</CUBE_SOURCE>
                          </RestrictionList>
                        </Restrictions>
                        <Properties>
                          <PropertyList>
                            <DataSourceInfo>FoodMart</DataSourceInfo>
                            <Catalog>FoodMart</Catalog>
                            <Format>Tabular</Format>
                            <Content>SchemaData</Content>
                          </PropertyList>
                        </Properties>
                    </Discover>
                """;

        SOAPUtil.callSoapWebService(Constants.soapEndpointUrl, Optional.of(Constants.SOAP_ACTION_DISCOVER),
                SOAPUtil.envelop(sRequest));

        DiscoverService discoverService = xmlaService.discover();
        verify(discoverService, (times(1))).mdSchemaCubes(captor.capture());

        MdSchemaCubesRequest request = captor.getValue();
        assertThat(request).isNotNull()
                .satisfies(d -> {
                    // getRestrictions
                    assertThat(d.restrictions()).isNotNull()
                            .satisfies(r -> {
                                assertThat(r.schemaName()).isNotNull()
                                        .isPresent()
                                        .contains("schemaName");
                                assertThat(r.cubeName()).isNotNull()
                                        .isPresent()
                                        .contains("cubeName");
                                ;
                                assertThat(r.baseCubeName()).isNotNull()
                                        .isPresent()
                                        .contains("baseCubeName");
                                ;
                                assertThat(r.cubeSource()).isNotNull()
                                        .isPresent()
                                        .contains(CubeSourceEnum.CUBE);
                            });
                    // getProperties
                    assertThat(d.properties()).isNotNull()
                            .satisfies(p -> {
                                assertThat(p.localeIdentifier()).isNotNull()
                                .isNotPresent();
                                assertThat(p.dataSourceInfo()).isNotNull()
                                        .contains("FoodMart");
                                assertThat(p.catalog()).isNotNull()
                                        .contains("FoodMart");
                                assertThat(p.format()).isNotNull()
                                        .contains(Tabular);
                                assertThat(p.content()).isNotNull()
                                        .isPresent()
                                        .contains(SchemaData);
                            });
                });
    }

    @Test
    void test_MDSCHEMA_DIMENSIONS(@InjectService XmlaService xmlaService) throws Exception {

        ArgumentCaptor<MdSchemaDimensionsRequest> captor = ArgumentCaptor
                .forClass(MdSchemaDimensionsRequest.class);
        final String sRequest = """
                    <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <RequestType>MDSCHEMA_DIMENSIONS</RequestType>
                    <Restrictions>
                      <RestrictionList>
                        <CATALOG_NAME>catalogName</CATALOG_NAME>
                        <SCHEMA_NAME>schemaName</SCHEMA_NAME>
                        <CUBE_NAME>cubeName</CUBE_NAME>
                        <DIMENSION_NAME>dimensionName</DIMENSION_NAME>
                        <DIMENSION_UNIQUE_NAME>dimensionUniqueName</DIMENSION_UNIQUE_NAME>
                      </RestrictionList>
                    </Restrictions>
                    <Properties>
                      <PropertyList>
                        <DataSourceInfo>FoodMart</DataSourceInfo>
                        <Catalog>FoodMart</Catalog>
                        <Format>Tabular</Format>
                        <Content>SchemaData</Content>
                      </PropertyList>
                    </Properties>
                    </Discover>
                """;

        SOAPUtil.callSoapWebService(Constants.soapEndpointUrl, Optional.of(Constants.SOAP_ACTION_DISCOVER),
                SOAPUtil.envelop(sRequest));

        DiscoverService discoverService = xmlaService.discover();
        verify(discoverService, (times(1))).mdSchemaDimensions(captor.capture());

        MdSchemaDimensionsRequest request = captor.getValue();
        assertThat(request).isNotNull()
                .satisfies(d -> {
                    // getRestrictions
                    assertThat(d.restrictions()).isNotNull()
                            .satisfies(r -> {
                                assertThat(r.catalogName()).isNotNull()
                                        .isPresent()
                                        .contains("catalogName");
                                assertThat(r.schemaName()).isNotNull()
                                        .isPresent()
                                        .contains("schemaName");
                                assertThat(r.cubeName()).isNotNull()
                                        .isPresent()
                                        .contains("cubeName");
                                assertThat(r.dimensionName()).isNotNull()
                                        .isPresent()
                                        .contains("dimensionName");
                                assertThat(r.dimensionUniqueName()).isNotNull()
                                        .isPresent()
                                        .contains("dimensionUniqueName");
                            });
                    // getProperties
                    assertThat(d.properties()).isNotNull()
                            .satisfies(p -> {
                                assertThat(p.localeIdentifier()).isNotNull()
                                .isNotPresent();
                                assertThat(p.dataSourceInfo()).isNotNull()
                                        .contains("FoodMart");
                                assertThat(p.catalog()).isNotNull()
                                        .contains("FoodMart");
                                assertThat(p.format()).isNotNull()
                                        .contains(Tabular);
                                assertThat(p.content()).isNotNull()
                                        .isPresent()
                                        .contains(SchemaData);
                            });
                });
    }

    @Test
    void test_MDSCHEMA_FUNCTIONS(@InjectService XmlaService xmlaService) throws Exception {

        ArgumentCaptor<MdSchemaFunctionsRequest> captor = ArgumentCaptor
                .forClass(MdSchemaFunctionsRequest.class);
        final String sRequest = """
                    <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <RequestType>MDSCHEMA_FUNCTIONS</RequestType>
                    <Restrictions>
                      <RestrictionList>
                        <FUNCTION_NAME>Item</FUNCTION_NAME>
                      </RestrictionList>
                    </Restrictions>
                    <Properties>
                      <PropertyList>
                        <DataSourceInfo>FoodMart</DataSourceInfo>
                        <Content>SchemaData</Content>
                      </PropertyList>
                    </Properties>
                    </Discover>
                """;

        SOAPUtil.callSoapWebService(Constants.soapEndpointUrl, Optional.of(Constants.SOAP_ACTION_DISCOVER),
                SOAPUtil.envelop(sRequest));

        DiscoverService discoverService = xmlaService.discover();
        verify(discoverService, (times(1))).mdSchemaFunctions(captor.capture());

        MdSchemaFunctionsRequest request = captor.getValue();
        assertThat(request).isNotNull()

                .satisfies(d -> {
                    // getRestrictions
                    assertThat(d.restrictions()).isNotNull()
                            .satisfies(r -> {
                                assertThat(r.origin()).isNotNull()
                                        .isNotPresent();
                            });
                    // getProperties
                    assertThat(d.properties()).isNotNull()
                            .satisfies(p -> {
                                assertThat(p.localeIdentifier()).isNotNull()
                                .isNotPresent();
                                assertThat(p.dataSourceInfo()).isNotNull()
                                        .contains("FoodMart");
                                assertThat(p.catalog()).isNotNull()
                                .isNotPresent();
                                assertThat(p.format()).isNotNull()
                                .isNotPresent();
                                assertThat(p.content()).isNotNull()
                                        .isPresent()
                                        .contains(SchemaData);
                            });
                });
    }

    @Test
    void test_MDSCHEMA_HIERARCHIES(@InjectService XmlaService xmlaService) throws Exception {

        ArgumentCaptor<MdSchemaHierarchiesRequest> captor = ArgumentCaptor
            .forClass(MdSchemaHierarchiesRequest.class);
        final String sRequest = """
                <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
                <RequestType>MDSCHEMA_HIERARCHIES</RequestType>
                <Restrictions>
                  <RestrictionList>
                    <CATALOG_NAME>catalogName</CATALOG_NAME>
                    <SCHEMA_NAME>schemaName</SCHEMA_NAME>
                    <CUBE_NAME>cubeName</CUBE_NAME>
                    <DIMENSION_UNIQUE_NAME>dimensionUniqueName</DIMENSION_UNIQUE_NAME>
                    <HIERARCHY_NAME>hierarchyName</HIERARCHY_NAME>
                    <HIERARCHY_UNIQUE_NAME>hierarchyUniqueName</HIERARCHY_UNIQUE_NAME>
                    <HIERARCHY_ORIGIN>1</HIERARCHY_ORIGIN>
                    <CUBE_SOURCE>0x01</CUBE_SOURCE>
                    <HIERARCHY_VISIBILITY>0x01</HIERARCHY_VISIBILITY>
                  </RestrictionList>
                </Restrictions>
                <Properties>
                  <PropertyList>
                    <DataSourceInfo>FoodMart</DataSourceInfo>
                    <Content>SchemaData</Content>
                  </PropertyList>
                </Properties>
                </Discover>
            """;

        SOAPUtil.callSoapWebService(Constants.soapEndpointUrl, Optional.of(Constants.SOAP_ACTION_DISCOVER),
            SOAPUtil.envelop(sRequest));

        DiscoverService discoverService = xmlaService.discover();
        verify(discoverService, (times(1))).mdSchemaHierarchies(captor.capture());

        MdSchemaHierarchiesRequest request = captor.getValue();
        assertThat(request).isNotNull()

            .satisfies(d -> {
                // getRestrictions
                assertThat(d.restrictions()).isNotNull()
                    .satisfies(r -> {
                        assertThat(r.catalogName()).isNotNull()
                            .isPresent().contains("catalogName");
                        assertThat(r.schemaName()).isNotNull()
                            .isPresent().contains("schemaName");
                        assertThat(r.cubeName()).isNotNull()
                            .isPresent().contains("cubeName");
                        assertThat(r.dimensionUniqueName()).isNotNull()
                            .isPresent().contains("dimensionUniqueName");
                        assertThat(r.hierarchyName()).isNotNull()
                            .isPresent().contains("hierarchyName");
                        assertThat(r.hierarchyUniqueName()).isNotNull()
                            .isPresent().contains("hierarchyUniqueName");
                        assertThat(r.hierarchyOrigin()).isNotNull()
                            .isPresent().contains(1);
                        assertThat(r.cubeSource()).isNotNull()
                            .isPresent().contains(CubeSourceEnum.CUBE);
                        assertThat(r.hierarchyVisibility()).isNotNull()
                            .isPresent().contains(VisibilityEnum.VISIBLE);
                    });
                // getProperties
                assertThat(d.properties()).isNotNull()
                    .satisfies(p -> {
                        assertThat(p.localeIdentifier()).isNotNull()
                            .isNotPresent();
                        assertThat(p.dataSourceInfo()).isNotNull()
                            .contains("FoodMart");
                        assertThat(p.catalog()).isNotNull()
                            .isNotPresent();
                        assertThat(p.format()).isNotNull()
                            .isNotPresent();
                        assertThat(p.content()).isNotNull()
                            .isPresent()
                            .contains(SchemaData);
                    });
            });
    }

    @Test
    void test_DISCOVER_DATASOURCES(@InjectService XmlaService xmlaService) throws Exception {

        ArgumentCaptor<DiscoverDataSourcesRequest> captor = ArgumentCaptor
            .forClass(DiscoverDataSourcesRequest.class);
        final String sRequest = """
                    <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <RequestType>DISCOVER_DATASOURCES</RequestType>
                    <Restrictions>
                      <RestrictionList>
                        <DataSourceName>dataSourceName</DataSourceName>
                        <DataSourceDescription>dataSourceDescription</DataSourceDescription>
                        <URL>url</URL>
                        <DataSourceInfo>dataSourceInfo</DataSourceInfo>
                        <ProviderName>providerName</ProviderName>
                        <ProviderType>DMP</ProviderType>
                        <AuthenticationMode>Authenticated</AuthenticationMode>
                      </RestrictionList>
                    </Restrictions>
                    <Properties>
                      <PropertyList>
                        <DataSourceInfo>FoodMart</DataSourceInfo>
                        <Content>SchemaData</Content>
                      </PropertyList>
                    </Properties>
                    </Discover>
                """;

        SOAPUtil.callSoapWebService(Constants.soapEndpointUrl, Optional.of(Constants.SOAP_ACTION_DISCOVER),
            SOAPUtil.envelop(sRequest));

        DiscoverService discoverService = xmlaService.discover();
        verify(discoverService, (times(1))).dataSources(captor.capture());

        DiscoverDataSourcesRequest request = captor.getValue();
        assertThat(request).isNotNull()

            .satisfies(d -> {
                // getRestrictions
                assertThat(d.restrictions()).isNotNull()
                    .satisfies(r -> {

                        assertThat(r.dataSourceName()).isNotNull()
                            .isEqualTo("dataSourceName");
                        assertThat(r.dataSourceDescription()).isNotNull()
                            .isPresent().contains("dataSourceDescription");
                        assertThat(r.url()).isNotNull()
                            .isPresent().contains("url");
                        assertThat(r.dataSourceInfo()).isNotNull()
                            .isPresent().contains("dataSourceInfo");
                        assertThat(r.providerName()).isNotNull()
                            .isEqualTo("providerName");
                        assertThat(r.providerType()).isNotNull()
                            .isPresent().contains(ProviderTypeEnum.DMP);
                        assertThat(r.authenticationMode()).isNotNull()
                            .isPresent().contains(AuthenticationModeEnum.Authenticated);
                    });
                // getProperties
                assertThat(d.properties()).isNotNull()
                    .satisfies(p -> {
                        assertThat(p.localeIdentifier()).isNotNull()
                            .isNotPresent();
                        assertThat(p.dataSourceInfo()).isNotNull()
                            .contains("FoodMart");
                        assertThat(p.catalog()).isNotNull()
                            .isNotPresent();
                        assertThat(p.format()).isNotNull()
                            .isNotPresent();
                        assertThat(p.content()).isNotNull()
                            .isPresent()
                            .contains(SchemaData);
                    });
            });
    }

    @Test
    void test_DISCOVER_XML_METADATA(@InjectService XmlaService xmlaService) throws Exception {

        ArgumentCaptor<DiscoverXmlMetaDataRequest> captor = ArgumentCaptor
            .forClass(DiscoverXmlMetaDataRequest.class);
        final String sRequest = """
                    <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <RequestType>DISCOVER_XML_METADATA</RequestType>
                    <Restrictions>
                      <RestrictionList>

    public static final String RESTRICTIONS_MEASURE_GROUP_ID = "MeasureGroupID";
    public static final String RESTRICTIONS_PARTITION_ID = "PartitionID";
    public static final String RESTRICTIONS_PERSPECTIVE_ID = "PerspectiveID";
    public static final String RESTRICTIONS_PERMISSION_ID = "DimensionPermissionID";


                        <DatabaseID>databaseId</DatabaseID>
                        <DimensionID>dimensionId</DimensionID>
                        <CubeID>cubeId</CubeID>
                        <MeasureGroupID>measureGroupId</MeasureGroupID>
                        <PartitionID>partitionId</PartitionID>
                        <PerspectiveID>perspectiveId</PerspectiveID>
                        <DimensionPermissionID>dimensionPermissionId</DimensionPermissionID>
                        <RoleID>roleId</RoleID>
                        <DatabasePermissionID>databasePermissionId</DatabasePermissionID>
                        <MiningModelID>miningModelId</MiningModelID>
                        <MiningModelPermissionID>miningModelPermissionId</MiningModelPermissionID>
                        <DataSourceID>dataSourceId</DataSourceID>
                        <MiningStructureID>miningStructureId</MiningStructureID>
                        <AggregationDesignID>aggregationDesignId</AggregationDesignID>
                        <TraceID>traceId</TraceID>
                        <MiningStructurePermissionID>miningStructurePermissionId</MiningStructurePermissionID>
                        <CubePermissionID>cubePermissionId</CubePermissionID>
                        <AssemblyID>assemblyId</AssemblyID>
                        <MdxScriptID>mdxScriptId</MdxScriptID>
                        <DataSourceViewID>dataSourceViewId</DataSourceViewID>
                        <DataSourcePermissionID>dataSourcePermissionId</DataSourcePermissionID>
                        <ObjectExpansion>ReferenceOnly</ObjectExpansion>
                      </RestrictionList>
                    </Restrictions>
                    <Properties>
                      <PropertyList>
                        <DataSourceInfo>FoodMart</DataSourceInfo>
                        <Content>SchemaData</Content>
                      </PropertyList>
                    </Properties>
                    </Discover>
                """;

        SOAPUtil.callSoapWebService(Constants.soapEndpointUrl, Optional.of(Constants.SOAP_ACTION_DISCOVER),
            SOAPUtil.envelop(sRequest));

        DiscoverService discoverService = xmlaService.discover();
        verify(discoverService, (times(1))).xmlMetaData(captor.capture());

        DiscoverXmlMetaDataRequest request = captor.getValue();
        assertThat(request).isNotNull()

            .satisfies(d -> {
                // getRestrictions
                assertThat(d.restrictions()).isNotNull()
                    .satisfies(r -> {
                        assertThat(r.databaseId()).isNotNull()
                            .isPresent().contains("databaseId");
                        assertThat(r.dimensionId()).isNotNull()
                            .isPresent().contains("dimensionId");
                        assertThat(r.cubeId()).isNotNull()
                            .isPresent().contains("cubeId");
                        assertThat(r.measureGroupId()).isNotNull()
                            .isPresent().contains("measureGroupId");
                        assertThat(r.partitionId()).isNotNull()
                            .isPresent().contains("partitionId");
                        assertThat(r.perspectiveId()).isNotNull()
                            .isPresent().contains("perspectiveId");
                        assertThat(r.dimensionPermissionId()).isNotNull()
                            .isPresent().contains("dimensionPermissionId");
                        assertThat(r.roleId()).isNotNull()
                            .isPresent().contains("roleId");
                        assertThat(r.dataSourcePermissionId()).isNotNull()
                            .isPresent().contains("dataSourcePermissionId");
                        assertThat(r.miningModelId()).isNotNull()
                            .isPresent().contains("miningModelId");
                        assertThat(r.miningModelPermissionId()).isNotNull()
                            .isPresent().contains("miningModelPermissionId");
                        assertThat(r.dataSourceId()).isNotNull()
                            .isPresent().contains("dataSourceId");
                        assertThat(r.miningStructureId()).isNotNull()
                            .isPresent().contains("miningStructureId");
                        assertThat(r.aggregationDesignId()).isNotNull()
                            .isPresent().contains("aggregationDesignId");
                        assertThat(r.traceId()).isNotNull()
                            .isPresent().contains("traceId");
                        assertThat(r.miningStructurePermissionId()).isNotNull()
                            .isPresent().contains("miningStructurePermissionId");
                        assertThat(r.cubePermissionId()).isNotNull()
                            .isPresent().contains("cubePermissionId");
                        assertThat(r.assemblyId()).isNotNull()
                            .isPresent().contains("assemblyId");
                        assertThat(r.mdxScriptId()).isNotNull()
                            .isPresent().contains("mdxScriptId");
                        assertThat(r.dataSourceViewId()).isNotNull()
                            .isPresent().contains("dataSourceViewId");
                        assertThat(r.dataSourcePermissionId()).isNotNull()
                            .isPresent().contains("dataSourcePermissionId");
                        assertThat(r.objectExpansion()).isNotNull()
                            .isPresent().contains(ObjectExpansionEnum.ReferenceOnly);
                    });
                // getProperties
                assertThat(d.properties()).isNotNull()
                    .satisfies(p -> {
                        assertThat(p.localeIdentifier()).isNotNull()
                            .isNotPresent();
                        assertThat(p.dataSourceInfo()).isNotNull()
                            .contains("FoodMart");
                        assertThat(p.catalog()).isNotNull()
                            .isNotPresent();
                        assertThat(p.format()).isNotNull()
                            .isNotPresent();
                        assertThat(p.content()).isNotNull()
                            .isPresent()
                            .contains(SchemaData);
                    });
            });
    }
    // TODO: All Discover-Requests with tests
}
