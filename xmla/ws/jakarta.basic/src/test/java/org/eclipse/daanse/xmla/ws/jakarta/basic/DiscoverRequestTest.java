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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

import org.eclipse.daanse.xmla.model.jaxb.xmla.Discover;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aQute.bnd.annotation.service.ServiceCapability;

@ServiceCapability(XmlaService.class)
@ExtendWith(ConfigurationExtension.class)
@WithFactoryConfiguration(factoryPid = Constants.PID_MS_SOAP, name = "test-ms-config", location = "?", properties = {
        @Property(key = "xmlaService.target", value = "(" + Constants.XMLASERVICE_FILTER_KEY + "="
                + Constants.XMLASERVICE_FILTER_VALUE + ")"),
        @Property(key = "osgi.soap.endpoint.contextpath", value = Constants.WS_PATH) })
public class DiscoverRequestTest {
    private Logger logger = LoggerFactory.getLogger(DiscoverRequestTest.class);

    @InjectBundleContext
    BundleContext bc;

    @InjectService
    ServiceComponentRuntime componentRuntime;

    private ArgumentCaptor<Discover> dicoverCaptor;

    @BeforeEach
    void beforaEach() {
        XmlaService xmlaService = mock(XmlaService.class);
        bc.registerService(XmlaService.class, xmlaService, FrameworkUtil
                .asDictionary(Map.of(Constants.XMLASERVICE_FILTER_KEY, Constants.XMLASERVICE_FILTER_VALUE)));

        dicoverCaptor = ArgumentCaptor.forClass(Discover.class);
    }

    @Test
    void test_DISCOVER_PROPERTIES_LocaleIdentifier(@InjectService XmlaService xmlaService) throws Exception {

        final String request = """
                <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
                  <RequestType>DISCOVER_PROPERTIES</RequestType>
                  <Restrictions/>
                  <Properties>
                    <PropertyList>
                      <LocaleIdentifier>1033</LocaleIdentifier>
                    </PropertyList>
                  </Properties>
                </Discover>
                """;

        SOAPUtil.callSoapWebService(Constants.soapEndpointUrl, Optional.of(Constants.SOAP_ACTION_DISCOVER),
                SOAPUtil.envelop(request));

        verify(xmlaService, (times(1))).discover(dicoverCaptor.capture(), Mockito.any(), Mockito.any(), Mockito.any());

        assertThat(dicoverCaptor.getValue()).isNotNull()
                .satisfies(d -> {
                    // getRequestType
                    assertThat(d.getRequestType()).isNotNull()
                            .isEqualTo("DISCOVER_PROPERTIES");
                    // getRestrictions
                    assertThat(d.getRestrictions()).isNotNull()
                            .satisfies(r -> {
                                assertThat(r.getRestrictionList()).isNull();
                            });
                    // getProperties
                    assertThat(d.getProperties()).isNotNull()
                            .satisfies(r -> {
                                assertThat(r.getPropertyList()).isNotNull()
                                        .satisfies(pl -> {
                                            assertThat(pl.getLocaleIdentifier()).isEqualTo(new BigInteger("1033"));
                                        });
                            });
                });
    }

    @Test
    void test_MDSCHEMA_CUBES_Content_Data(@InjectService XmlaService xmlaService) throws Exception {

        final String request = """
                <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
                  <RequestType>MDSCHEMA_CUBES</RequestType>
                  <Restrictions></Restrictions>
                  <Properties>
                    <PropertyList>
                      <Content>Data</Content>
                    </PropertyList>
                  </Properties>
                </Discover>
                """;
        SOAPUtil.callSoapWebService(Constants.soapEndpointUrl, Optional.of(Constants.SOAP_ACTION_DISCOVER),
                SOAPUtil.envelop(request));

        verify(xmlaService, (times(1))).discover(dicoverCaptor.capture(), Mockito.any(), Mockito.any(), Mockito.any());

        assertThat(dicoverCaptor.getValue()).isNotNull()
                .satisfies(d -> {
                    // getRequestType
                    assertThat(d.getRequestType()).isNotNull()
                            .isEqualTo("MDSCHEMA_CUBES");
                    // getRestrictions
                    assertThat(d.getRestrictions()).isNotNull()
                            .satisfies(r -> {
                                assertThat(r.getRestrictionList()).isNull();
                            });
                    // getProperties
                    assertThat(d.getProperties()).isNotNull()
                            .satisfies(r -> {
                                assertThat(r.getPropertyList()).isNotNull()
                                        .satisfies(pl -> {
                                            assertThat(pl.getContent()).isEqualTo("Data");

                                        });

                            });
                });

    }

    @Test
    void test_DBSCHEMA_CATALOGS(@InjectService XmlaService xmlaService) throws Exception {

        final String request = """
                <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
                  <RequestType>DBSCHEMA_CATALOGS</RequestType>
                  <Restrictions>
                    <RestrictionList/>
                  </Restrictions>
                  <Properties>
                    <PropertyList>
                      <DataSourceInfo>${datasource}</DataSourceInfo>
                      <Catalog>${catalog}</Catalog>
                      <Format>Tabular</Format>
                    </PropertyList>
                  </Properties>
                </Discover>
                """;
        SOAPUtil.callSoapWebService(Constants.soapEndpointUrl, Optional.of(Constants.SOAP_ACTION_DISCOVER),
                SOAPUtil.envelop(request));

        verify(xmlaService, (times(1))).discover(dicoverCaptor.capture(), Mockito.any(), Mockito.any(), Mockito.any());

        assertThat(dicoverCaptor.getValue()).isNotNull()
                .satisfies(d -> {
                    // getRequestType
                    assertThat(d.getRequestType()).isNotNull()
                            .isEqualTo("DBSCHEMA_CATALOGS");
                    // getRestrictions
                    assertThat(d.getRestrictions()).isNotNull()
                            .satisfies(r -> {
                                assertThat(r.getRestrictionList()).isNotNull()
                                        .isEmpty();
                            });
                    // getProperties
                    assertThat(d.getProperties()).isNotNull()
                            .satisfies(r -> {
                                assertThat(r.getPropertyList()).isNotNull()
                                        .satisfies(pl -> {
                                            assertThat(pl.getDataSourceInfo()).isEqualTo("${datasource}");
                                            assertThat(pl.getCatalog()).isEqualTo("${catalog}");
                                            assertThat(pl.getFormat()).isEqualTo("Tabular");
                                        });
                            });

                });

    }

    @Test
    void test_DBSCHEMA_SCHEMATA(@InjectService XmlaService xmlaService) throws Exception {

        final String request = """
                <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
                  <RequestType>DBSCHEMA_SCHEMATA</RequestType>
                  <Restrictions>
                    <RestrictionList/>
                  </Restrictions>
                  <Properties>
                    <PropertyList>
                      <DataSourceInfo>${datasource}</DataSourceInfo>
                      <Catalog>${catalog}</Catalog>
                      <Format>Tabular</Format>
                    </PropertyList>
                  </Properties>
                </Discover>
                """;
        SOAPUtil.callSoapWebService(Constants.soapEndpointUrl, Optional.of(Constants.SOAP_ACTION_DISCOVER),
                SOAPUtil.envelop(request));

        verify(xmlaService, (times(1))).discover(dicoverCaptor.capture(), Mockito.any(), Mockito.any(), Mockito.any());

        assertThat(dicoverCaptor.getValue()).isNotNull()
                .satisfies(d -> {
                    // getRequestType
                    assertThat(d.getRequestType()).isNotNull()
                            .isEqualTo("DBSCHEMA_SCHEMATA");
                    // getRestrictions
                    assertThat(d.getRestrictions()).isNotNull()
                            .satisfies(r -> {
                                assertThat(r.getRestrictionList()).isNotNull()
                                        .isEmpty();
                            });
                    // getProperties
                    assertThat(d.getProperties()).isNotNull()
                            .satisfies(r -> {
                                assertThat(r.getPropertyList()).isNotNull()
                                        .satisfies(pl -> {
                                            assertThat(pl.getDataSourceInfo()).isEqualTo("${datasource}");
                                            assertThat(pl.getCatalog()).isEqualTo("${catalog}");
                                            assertThat(pl.getFormat()).isEqualTo("Tabular");
                                        });
                            });

                });

    }

    @Test
    void test_DISCOVER_DATASOURCES(@InjectService XmlaService xmlaService) throws Exception {

        final String request = """
                <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
                  <RequestType>DISCOVER_DATASOURCES</RequestType>
                  <Restrictions>
                    <RestrictionList/>
                  </Restrictions>
                  <Properties>
                    <PropertyList>
                      <Format>Tabular</Format>
                    </PropertyList>
                  </Properties>
                </Discover>""";
        SOAPUtil.callSoapWebService(Constants.soapEndpointUrl, Optional.of(Constants.SOAP_ACTION_DISCOVER),
                SOAPUtil.envelop(request));

        verify(xmlaService, (times(1))).discover(dicoverCaptor.capture(), Mockito.any(), Mockito.any(), Mockito.any());

        assertThat(dicoverCaptor.getValue()).isNotNull()
                .satisfies(d -> {
                    // getRequestType
                    assertThat(d.getRequestType()).isNotNull()
                            .isEqualTo("DISCOVER_DATASOURCES");
                    // getRestrictions
                    assertThat(d.getRestrictions()).isNotNull()
                            .satisfies(r -> {
                                assertThat(r.getRestrictionList()).isNotNull()
                                        .isEmpty();
                            });
                    // getProperties
                    assertThat(d.getProperties()).isNotNull()
                            .satisfies(r -> {
                                assertThat(r.getPropertyList()).isNotNull()
                                        .satisfies(pl -> {
                                            assertThat(pl.getFormat()).isEqualTo("Tabular");
                                        });
                            });

                });

    }

    @Test
    void test_MDSCHEMA_CUBES_mdschemaCubesRestrictions(@InjectService XmlaService xmlaService) throws Exception {

        final String request = """
                <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
                  <RequestType>MDSCHEMA_CUBES</RequestType>
                  <Restrictions>
                    <RestrictionList>
                      <CATALOG_NAME>${catalog}</CATALOG_NAME>
                      <CUBE_NAME>Sales</CUBE_NAME>
                    </RestrictionList>
                  </Restrictions>
                  <Properties>
                    <PropertyList>
                      <DataSourceInfo>${datasource}</DataSourceInfo>
                      <Catalog>${catalog}</Catalog>
                      <Format>Tabular</Format>
                    </PropertyList>
                  </Properties>
                </Discover>
                """;
        SOAPUtil.callSoapWebService(Constants.soapEndpointUrl, Optional.of(Constants.SOAP_ACTION_DISCOVER),
                SOAPUtil.envelop(request));

        verify(xmlaService, (times(1))).discover(dicoverCaptor.capture(), Mockito.any(), Mockito.any(), Mockito.any());

        Discover discover = dicoverCaptor.getValue();
        assertThat(discover).isNotNull()
                .satisfies(d -> {
                    // getRequestType
                    assertThat(d.getRequestType()).isNotNull()
                            .isEqualTo("MDSCHEMA_CUBES");
                    // getRestrictions
                    assertThat(d.getRestrictions()).isNotNull()
                            .satisfies(r -> {
                                assertThat(r.getRestrictionMap()).isNotNull()
                                        .hasSize(2)
                                        .containsEntry("CATALOG_NAME", "${catalog}")
                                        .containsEntry("CUBE_NAME", "Sales");
                            });
                    // getProperties
                    assertThat(d.getProperties()).isNotNull()
                            .satisfies(r -> {
                                assertThat(r.getPropertyList()).isNotNull()
                                        .satisfies(pl -> {
                                            assertThat(pl.getDataSourceInfo()).isEqualTo("${datasource}");
                                            assertThat(pl.getCatalog()).isEqualTo("${catalog}");
                                            assertThat(pl.getFormat()).isEqualTo("Tabular");
                                        });

                            });

                });

    }

    @Test
    void test_MDSCHEMA_DIMENSIONS_Restrictions(@InjectService XmlaService xmlaService) throws Exception {

        final String request = """
                             <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
                  <RequestType>MDSCHEMA_DIMENSIONS</RequestType>
                  <Restrictions>
                    <RestrictionList>
                      <CATALOG_NAME>${catalog}</CATALOG_NAME>
                      <CUBE_NAME>Sales</CUBE_NAME>
                      <DIMENSION_NAME>Time</DIMENSION_NAME>
                    </RestrictionList>
                  </Restrictions>
                  <Properties>
                    <PropertyList>
                      <DataSourceInfo>${datasource}</DataSourceInfo>
                      <Catalog>${catalog}</Catalog>
                      <Format>Tabular</Format>
                    </PropertyList>
                  </Properties>
                </Discover>
                """;
        SOAPUtil.callSoapWebService(Constants.soapEndpointUrl, Optional.of(Constants.SOAP_ACTION_DISCOVER),
                SOAPUtil.envelop(request));

        verify(xmlaService, (times(1))).discover(dicoverCaptor.capture(), Mockito.any(), Mockito.any(), Mockito.any());

        Discover discover = dicoverCaptor.getValue();
        assertThat(discover).isNotNull()
                .satisfies(d -> {
                    // getRequestType
                    assertThat(d.getRequestType()).isNotNull()
                            .isEqualTo("MDSCHEMA_DIMENSIONS");
                    // getRestrictions
                    assertThat(d.getRestrictions()).isNotNull()
                            .satisfies(r -> {

                                assertThat(r.getRestrictionMap()).isNotNull()
                                        .hasSize(3)
                                        .containsEntry("CATALOG_NAME", "${catalog}")
                                        .containsEntry("CUBE_NAME", "Sales")
                                        .containsEntry("DIMENSION_NAME", "Time");

                            });
                    // getProperties
                    assertThat(d.getProperties()).isNotNull()
                            .satisfies(r -> {
                                assertThat(r.getPropertyList()).isNotNull()
                                        .satisfies(pl -> {
                                            assertThat(pl.getDataSourceInfo()).isEqualTo("${datasource}");
                                            assertThat(pl.getCatalog()).isEqualTo("${catalog}");
                                            assertThat(pl.getFormat()).isEqualTo("Tabular");
                                        });

                            });

                });

    }

    @Test
    void test_MDSCHEMA_HIERARCHIES_Restrictions(@InjectService XmlaService xmlaService) throws Exception {

        final String request = """
                <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
                  <RequestType>MDSCHEMA_HIERARCHIES</RequestType>
                  <Restrictions>
                    <RestrictionList>
                      <CATALOG_NAME>${catalog}</CATALOG_NAME>
                      <CUBE_NAME>Sales</CUBE_NAME>
                      <DIMENSION_UNIQUE_NAME>[Time]</DIMENSION_UNIQUE_NAME>
                    </RestrictionList>
                  </Restrictions>
                  <Properties>
                    <PropertyList>
                      <DataSourceInfo>${datasource}</DataSourceInfo>
                      <Catalog>${catalog}</Catalog>
                      <Format>Tabular</Format>
                    </PropertyList>
                  </Properties>
                </Discover>
                """;
        SOAPUtil.callSoapWebService(Constants.soapEndpointUrl, Optional.of(Constants.SOAP_ACTION_DISCOVER),
                SOAPUtil.envelop(request));

        verify(xmlaService, (times(1))).discover(dicoverCaptor.capture(), Mockito.any(), Mockito.any(), Mockito.any());

        Discover discover = dicoverCaptor.getValue();
        assertThat(discover).isNotNull()
                .satisfies(d -> {
                    // getRequestType
                    assertThat(d.getRequestType()).isNotNull()
                            .isEqualTo("MDSCHEMA_HIERARCHIES");
                    // getRestrictions
                    assertThat(d.getRestrictions()).isNotNull()
                            .satisfies(r -> {
                                assertThat(r.getRestrictionMap()).isNotNull()
                                        .hasSize(3)
                                        .containsEntry("CATALOG_NAME", "${catalog}")
                                        .containsEntry("CUBE_NAME", "Sales")
                                        .containsEntry("DIMENSION_UNIQUE_NAME", "[Time]");
                            });
                    // getProperties
                    assertThat(d.getProperties()).isNotNull()
                            .satisfies(r -> {
                                assertThat(r.getPropertyList()).isNotNull()
                                        .satisfies(pl -> {
                                            assertThat(pl.getDataSourceInfo()).isEqualTo("${datasource}");
                                            assertThat(pl.getCatalog()).isEqualTo("${catalog}");
                                            assertThat(pl.getFormat()).isEqualTo("Tabular");
                                        });

                            });

                });

    }

}
