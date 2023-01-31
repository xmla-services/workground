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

import org.eclipse.daanse.xmla.api.XmlaService;
import org.eclipse.daanse.xmla.api.discover.DiscoverPropertiesRequest;
import org.eclipse.daanse.xmla.api.discover.schemarowsets.DiscoverSchemaRowsetsRequest;
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

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.daanse.xmla.api.common.properties.Content.SchemaData;
import static org.eclipse.daanse.xmla.api.common.properties.Format.Tabular;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(ConfigurationExtension.class)
@WithFactoryConfiguration(factoryPid = Constants.PID_MS_SOAP, name = "test-ms-config", location = "?", properties = {
        @Property(key = "xmlaService.target", value = "(" + Constants.XMLASERVICE_FILTER_KEY + "="
                + Constants.XMLASERVICE_FILTER_VALUE + ")"),
        @Property(key = "osgi.soap.endpoint.contextpath", value = Constants.WS_PATH) })
@RequireServiceComponentRuntime
public class DiscoverRequestTest {
    private Logger logger = LoggerFactory.getLogger(DiscoverRequestTest.class);

    @InjectBundleContext
    BundleContext bc;

    @BeforeEach
    void beforaEach() throws InterruptedException {
        XmlaService xmlaService = mock(XmlaService.class);
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

        verify(xmlaService, (times(1))).discoverProperties(captor.capture());

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

        verify(xmlaService, (times(1))).discoverProperties(captor.capture());

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
        ArgumentCaptor<DiscoverSchemaRowsetsRequest> captor = ArgumentCaptor.forClass(DiscoverSchemaRowsetsRequest.class);
        final String sRequest = """
            <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
              <RequestType>DISCOVER_SCHEMA_ROWSETS</RequestType>
              <Restrictions />
              <SchemaName>FoodMart</SchemaName>
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

        verify(xmlaService, (times(1))).discoverSchemaRowsets(captor.capture());

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
    // TODO: All Discover-Requests with tests
}
