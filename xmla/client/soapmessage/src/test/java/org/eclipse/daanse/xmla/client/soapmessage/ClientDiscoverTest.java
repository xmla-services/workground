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
import org.eclipse.daanse.xmla.api.common.enums.ProviderTypeEnum;
import org.eclipse.daanse.xmla.api.common.properties.PropertyListElementDefinition;
import org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesRequest;
import org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesResponseRow;
import org.eclipse.daanse.xmla.model.record.discover.PropertiesR;
import org.eclipse.daanse.xmla.model.record.discover.discover.datasources.DiscoverDataSourcesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.discover.datasources.DiscoverDataSourcesRestrictionsR;
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

    public Provider<SOAPMessage> provider = spy(new MockProvider());

    private ArgumentCaptor<SOAPMessage> requestMessageCaptor;

    @BeforeEach
    void beforeEach() throws InterruptedException {
        requestMessageCaptor = ArgumentCaptor.forClass(SOAPMessage.class);

        bc.registerService(Provider.class, provider, Dictionaries.dictionaryOf("osgi.soap.endpoint.contextpath",
                "/xmla", "osgi.soap.endpoint.implementor", "true"));
        TimeUnit.SECONDS.sleep(2);

    }

    @Test
    void testDataSources() throws Exception {
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

}
