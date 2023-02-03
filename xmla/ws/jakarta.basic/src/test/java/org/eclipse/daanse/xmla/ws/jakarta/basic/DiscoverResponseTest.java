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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.eclipse.daanse.xmla.api.XmlaService;
import org.eclipse.daanse.xmla.api.discover.discoverproperties.DiscoverPropertiesResponseRow;
import org.eclipse.daanse.xmla.model.record.discover.discoverproperties.DiscoverPropertiesResponseRowR;
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
        @Property(key = "osgi.soap.endpoint.contextpath", value = Constants.WS_PATH) })
@WithFactoryConfiguration(factoryPid = "org.eclipse.daanse.ws.handler.SOAPLoggingHandler", name = "test-ms-config", location = "?", properties = {
        @Property(key = "osgi.soap.endpoint.selector", value = "(service.pid=*)") })
@RequireServiceComponentRuntime
public class DiscoverResponseTest {
    private Logger logger = LoggerFactory.getLogger(DiscoverResponseTest.class);

    @InjectBundleContext
    BundleContext bc;

    @BeforeEach
    void beforaEach() throws InterruptedException {
        XmlaService xmlaService = mock(XmlaService.class);
        bc.registerService(XmlaService.class, xmlaService, FrameworkUtil
                .asDictionary(Map.of(Constants.XMLASERVICE_FILTER_KEY, Constants.XMLASERVICE_FILTER_VALUE)));
        TimeUnit.SECONDS.sleep(2);
    }

    public static final String REQUEST_DISCOVER_DISCOVER_PROPERTIES = """
            <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
              <RequestType>DISCOVER_PROPERTIES</RequestType>
                <Restrictions>
                  <RestrictionList>
                  </RestrictionList>
              </Restrictions>
              <Properties>
                <PropertyList>
                </PropertyList>
              </Properties>
            </Discover>
            """;

    @Test
    void test_DISCOVER_PROPERTIES_XXX(@InjectService XmlaService xmlaService) throws Exception {

        DiscoverPropertiesResponseRowR row = new DiscoverPropertiesResponseRowR("DbpropMsmdSubqueries",
                Optional.of("An enumeration value that determines the behavior of subqueries."), Optional.of("Integer"),
                "ReadWrite", Optional.of(false), Optional.of("1"));

        when(xmlaService.discoverProperties(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
                Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(REQUEST_DISCOVER_DISCOVER_PROPERTIES));

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/return/rowset:root")
                .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/return/rowset:root/rowset:row")
                .exist();

        System.out.println(1);

    };

    DiscoverPropertiesResponseRowR rowCatalog = new DiscoverPropertiesResponseRowR("Catalog", Optional.of("Catalog"),
            Optional.of("string"), "ReadWrite", Optional.of(false), Optional.of("AdventureWorks_SSAS"));

    DiscoverPropertiesResponseRowR rowTimeout = new DiscoverPropertiesResponseRowR("Timeout", Optional.of("Timeout"),
            Optional.of("int"), "ReadWrite", Optional.of(false), Optional.empty());

    DiscoverPropertiesResponseRowR rowContent = new DiscoverPropertiesResponseRowR("Content", Optional.of("Content"),
            Optional.of("string"), "Write", Optional.of(false), Optional.of("SchemaData"));

    DiscoverPropertiesResponseRowR rowFormat = new DiscoverPropertiesResponseRowR("Format", Optional.of("Format"),
            Optional.of("string"), "Write", Optional.of(false), Optional.of("Native"));

    DiscoverPropertiesResponseRowR rowAxisFormat = new DiscoverPropertiesResponseRowR("AxisFormat",
            Optional.of("AxisFormat"), Optional.of("string"), "Write", Optional.of(false), Optional.of("TupleFormat"));

    DiscoverPropertiesResponseRowR rowBeginRange = new DiscoverPropertiesResponseRowR("BeginRange",
            Optional.of("BeginRange"), Optional.of("int"), "Write", Optional.of(false), Optional.of("-1"));

    DiscoverPropertiesResponseRowR rowEndRange = new DiscoverPropertiesResponseRowR("EndRange", Optional.of("EndRange"),
            Optional.of("int"), "Write", Optional.of(false), Optional.of("-1"));

    DiscoverPropertiesResponseRowR rowShowHiddenCubes = new DiscoverPropertiesResponseRowR("ShowHiddenCubes",
            Optional.of("ShowHiddenCubes"), Optional.of("boolean"), "ReadWrite", Optional.of(false),
            Optional.of("false"));

    DiscoverPropertiesResponseRowR rowMaximumRows = new DiscoverPropertiesResponseRowR("MaximumRows",
            Optional.of("MaximumRows"), Optional.of("int"), "Write", Optional.of(false), Optional.empty());

    DiscoverPropertiesResponseRowR rowVisualMode = new DiscoverPropertiesResponseRowR("VisualMode",
            Optional.of("VisualMode"), Optional.of("int"), "Write", Optional.of(false), Optional.of("0"));

    DiscoverPropertiesResponseRowR rowDbpropMsmdCachePolicy = new DiscoverPropertiesResponseRowR(
            "DbpropMsmdCachePolicy", Optional.of("DbpropMsmdCachePolicy"), Optional.of("int"), "ReadWrite",
            Optional.of(false), Optional.empty());

    DiscoverPropertiesResponseRowR rowDbpropMsmdCacheRatio = new DiscoverPropertiesResponseRowR("DbpropMsmdCacheRatio",
            Optional.of("DbpropMsmdCacheRatio"), Optional.of("double"), "ReadWrite", Optional.of(false),
            Optional.empty());

    DiscoverPropertiesResponseRowR rowDbpropMsmdCacheMode = new DiscoverPropertiesResponseRowR("DbpropMsmdCacheMode",
            Optional.of("DbpropMsmdCacheMode"), Optional.of("int"), "ReadWrite", Optional.of(false), Optional.empty());

    List<DiscoverPropertiesResponseRow> resultSpec = List.of(rowCatalog, rowTimeout, rowContent, rowFormat,
            rowAxisFormat, rowBeginRange, rowEndRange, rowShowHiddenCubes, rowMaximumRows, rowVisualMode,
            rowDbpropMsmdCachePolicy, rowDbpropMsmdCacheRatio, rowDbpropMsmdCacheMode);

}
