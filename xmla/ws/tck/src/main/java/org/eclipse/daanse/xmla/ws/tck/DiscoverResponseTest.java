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

import jakarta.xml.soap.SOAPMessage;
import org.eclipse.daanse.xmla.api.XmlaService;
import org.eclipse.daanse.xmla.api.common.enums.LiteralNameEnumValueEnum;
import org.eclipse.daanse.xmla.api.discover.DiscoverService;
import org.eclipse.daanse.xmla.model.record.discover.discover.datasources.DiscoverDataSourcesResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.discover.enumerators.DiscoverEnumeratorsResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.discover.keywords.DiscoverKeywordsResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.discover.literals.DiscoverLiteralsResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.discover.properties.DiscoverPropertiesResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.discover.schemarowsets.DiscoverSchemaRowsetsResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.discover.xmlmetadata.DiscoverXmlMetaDataResponseRowR;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.eclipse.daanse.xmla.api.common.enums.AuthenticationModeEnum.Unauthenticated;
import static org.eclipse.daanse.xmla.api.common.enums.ProviderTypeEnum.MDP;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
                  </RestrictionList>
              </Restrictions>
              <Properties>
                <PropertyList>
                </PropertyList>
              </Properties>
            </Discover>
            """;

    @Test
    void test_DISCOVER_DATA_SOURCES_XXX(@InjectService XmlaService xmlaService) throws Exception {
        DiscoverDataSourcesResponseRowR row = new DiscoverDataSourcesResponseRowR("dataSourceName",
            Optional.of("dataSourceDescription"),Optional.of("url"),
            Optional.of("dataSourceInfo"), "providerName",
            Optional.of(MDP), Optional.of(Unauthenticated));

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.dataSources(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST, "DISCOVER_DATASOURCES")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);
        checkRowValues(xmlAssert, Map.of(
            "DataSourceName", "dataSourceName",
            "DataSourceDescription","dataSourceDescription",
            "URL", "url",
            "DataSourceInfo", "dataSourceInfo",
            "ProviderName","providerName",
            "ProviderType", "MDP",
            "AuthenticationMode", "Unauthenticated"
        ));
    }

    @Test
    void test_DISCOVER_ENUMERATORS_XXX(@InjectService XmlaService xmlaService) throws Exception {

        DiscoverEnumeratorsResponseRowR row = new DiscoverEnumeratorsResponseRowR("enumName",
            Optional.of("enumDescription"), "enumType", "elementName",
            Optional.of("elementDescription"), Optional.of("elementValue"));

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.discoverEnumerators(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST, "DISCOVER_ENUMERATORS")));

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
    void test_DISCOVER_KEYWORDS_XXX(@InjectService XmlaService xmlaService) throws Exception {

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
    void test_DISCOVER_LITERALS_XXX(@InjectService XmlaService xmlaService) throws Exception {

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
            "LiteralValue","literalValue",
            "LiteralInvalidChars","literalInvalidChars",
            "LiteralInvalidStartingChars","literalInvalidStartingChars",
            "LiteralMaxLength", "10",
            "LiteralNameValue", "1"
        ));
    }

    @Test
    void test_DISCOVER_PROPERTIES_XXX(@InjectService XmlaService xmlaService) throws Exception {

        DiscoverPropertiesResponseRowR row = new DiscoverPropertiesResponseRowR("DbpropMsmdSubqueries",
            Optional.of("An enumeration value that determines the behavior of subqueries."), Optional.of("Integer"),
            "ReadWrite", Optional.of(false), Optional.of("1"));

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.discoverProperties(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST, "DISCOVER_PROPERTIES")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/return/rowset:root")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/return/rowset:root/rowset:row")
            .exist();
        xmlAssert.valueByXPath("count(/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/return/rowset:root/rowset:row)").isEqualTo(1);

        xmlAssert.valueByXPath( "/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/return/rowset:root/rowset:row/rowset:PropertyName")
            .isEqualTo( "DbpropMsmdSubqueries" );
        xmlAssert.valueByXPath( "/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/return/rowset:root/rowset:row/rowset:PropertyDescription")
            .isEqualTo( "An enumeration value that determines the behavior of subqueries." );
        xmlAssert.valueByXPath( "/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/return/rowset:root/rowset:row/rowset:PropertyAccessType")
            .isEqualTo( "ReadWrite" );
        xmlAssert.valueByXPath( "/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/return/rowset:root/rowset:row/rowset:IsRequired")
            .isEqualTo( "false" );
        xmlAssert.valueByXPath( "/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/return/rowset:root/rowset:row/rowset:Value")
            .isEqualTo( "1" );
        System.out.println(1);
    }

    //TODO test failed
    @Test
    @Disabled
    void test_DISCOVER_SCHEMA_ROW_SETS_XXX(@InjectService XmlaService xmlaService) throws Exception {

        DiscoverSchemaRowsetsResponseRowR row = new DiscoverSchemaRowsetsResponseRowR("schemaName",
            Optional.of("schemaGuid"), Optional.of("restrictions"), Optional.of("description"), Optional.of(10l));

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.discoverSchemaRowsets(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST, "DISCOVER_SCHEMA_ROWSETS")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);
        checkRowValues(xmlAssert, Map.of(
            "SchemaName", "schemaName",
            "SchemaGuid", "schemaGuid",
            "Restrictions", "restrictions",
            "Description","description",
            "RestrictionsMask", "10"
        ));
    }

    @Test
    void test_DISCOVER_XML_METADATA_XXX(@InjectService XmlaService xmlaService) throws Exception {

        DiscoverXmlMetaDataResponseRowR row = new DiscoverXmlMetaDataResponseRowR("metaData");

        DiscoverService discoverService = xmlaService.discover();
        when(discoverService.xmlMetaData(Mockito.any())).thenReturn(List.of(row));

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_DISCOVER), SOAPUtil.envelop(String.format(REQUEST, "DISCOVER_XML_METADATA")));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);
        checkRowValues(xmlAssert, Map.of(
            "MetaData", "metaData"
        ));
    }

    private void checkRow(XmlAssert xmlAssert) {
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/return/rowset:root")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/return/rowset:root/rowset:row")
            .exist();
        xmlAssert.valueByXPath("count(/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/return/rowset:root/rowset:row)").isEqualTo(1);
    }

    private void checkRowValues(XmlAssert xmlAssert, Map<String, String> map){
        for (Map.Entry<String, String> entry : map.entrySet()) {
            xmlAssert.valueByXPath( new StringBuilder("/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/return/rowset:root/rowset:row/rowset:").append(entry.getKey()).toString())
                .isEqualTo( entry.getValue());
        }
    }
}
