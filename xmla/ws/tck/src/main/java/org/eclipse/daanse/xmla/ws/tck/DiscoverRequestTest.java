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
import static org.eclipse.daanse.xmla.api.common.properties.Content.SchemaData;
import static org.eclipse.daanse.xmla.api.common.properties.Format.Tabular;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.eclipse.daanse.xmla.api.XmlaService;
import org.eclipse.daanse.xmla.api.common.enums.ActionTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.CubeSourceEnum;
import org.eclipse.daanse.xmla.api.discover.DiscoverService;
import org.eclipse.daanse.xmla.api.discover.dbschema.tables.DbSchemaTablesRequest;
import org.eclipse.daanse.xmla.api.discover.discover.enumerators.DiscoverEnumeratorsRequest;
import org.eclipse.daanse.xmla.api.discover.discover.keywords.DiscoverKeywordsRequest;
import org.eclipse.daanse.xmla.api.discover.discover.literals.DiscoverLiteralsRequest;
import org.eclipse.daanse.xmla.api.discover.discover.properties.DiscoverPropertiesRequest;
import org.eclipse.daanse.xmla.api.discover.discover.schemarowsets.DiscoverSchemaRowsetsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.actions.MdSchemaActionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.cubes.MdSchemaCubesRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.functions.MdSchemaFunctionsRequest;
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
                                        .isEqualTo(1);
                                assertThat(r.invocation()).isNotNull()
                                        .isEqualTo(1);
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
                                assertThat(p.localeIdentifier()).isNull();
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
                                assertThat(p.localeIdentifier()).isNull();
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
                                assertThat(p.localeIdentifier()).isNull();
                                assertThat(p.dataSourceInfo()).isNotNull()
                                        .contains("FoodMart");
                                assertThat(p.catalog()).isNull();
                                assertThat(p.format()).isNull();
                                assertThat(p.content()).isNotNull()
                                        .isPresent()
                                        .contains(SchemaData);
                            });
                });
    }

    // TODO: All Discover-Requests with tests
}
