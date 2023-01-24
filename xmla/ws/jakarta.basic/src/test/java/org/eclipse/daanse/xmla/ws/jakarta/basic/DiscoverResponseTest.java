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

import java.util.Map;
import java.util.Optional;

import org.eclipse.daanse.xmla.model.jaxb.xmla.DiscoverResponse;
import org.eclipse.daanse.xmla.model.jaxb.xmla_rowset.Row;
import org.eclipse.daanse.xmla.model.jaxb.xmla_rowset.Rowset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.xmlunit.assertj3.XmlAssert;

import aQute.bnd.annotation.service.ServiceCapability;
import jakarta.xml.soap.SOAPMessage;

@ServiceCapability(XmlaService.class)
@ExtendWith(ConfigurationExtension.class)
@WithFactoryConfiguration(factoryPid = Constants.PID_MS_SOAP, name = "test-ms-config", location = "?", properties = {
        @Property(key = "xmlaService.target", value = "(" + Constants.XMLASERVICE_FILTER_KEY + "="
                + Constants.XMLASERVICE_FILTER_VALUE + ")"),
        @Property(key = "osgi.soap.endpoint.contextpath", value = Constants.WS_PATH) })
public class DiscoverResponseTest {
    private Logger logger = LoggerFactory.getLogger(DiscoverResponseTest.class);

    public static final String REQUEST_DISCOVER_DUMMY = """
            <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
               <RequestType>DUMMY</RequestType>
            </Discover>
            """;

    @InjectBundleContext
    BundleContext bc;

    @InjectService
    ServiceComponentRuntime componentRuntime;

    @BeforeEach
    void beforaEach() {
        XmlaService xmlaService = mock(XmlaService.class);
        bc.registerService(XmlaService.class, xmlaService, FrameworkUtil
                .asDictionary(Map.of(Constants.XMLASERVICE_FILTER_KEY, Constants.XMLASERVICE_FILTER_VALUE)));

    }

    @Test
    void testResponse(@InjectService XmlaService xmlaService) throws Exception {

        DiscoverResponse discoverResponse = new DiscoverResponse();

        org.eclipse.daanse.xmla.model.jaxb.xmla.DiscoverResponse.Return r = new DiscoverResponse.Return();
        Rowset rs = new Rowset();
        Row row = new Row();
        rs.getRow()
                .add(row);

        r.setRoot(rs);
        discoverResponse.setReturn(r);

        when(xmlaService.discover(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(discoverResponse);

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
                Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(REQUEST_DISCOVER_DUMMY));

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/msxmla:return/rowset:root")
                .exist();

    }

}
