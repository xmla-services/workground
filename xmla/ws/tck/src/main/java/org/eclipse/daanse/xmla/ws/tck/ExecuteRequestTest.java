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

import org.eclipse.daanse.xmla.api.XmlaService;
import org.eclipse.daanse.xmla.api.execute.ExecuteService;
import org.eclipse.daanse.xmla.api.execute.alter.AlterRequest;
import org.eclipse.daanse.xmla.api.execute.statement.StatementRequest;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.daanse.xmla.api.common.properties.AxisFormat.TupleFormat;
import static org.eclipse.daanse.xmla.api.common.properties.Format.Tabular;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(ConfigurationExtension.class)
@WithFactoryConfiguration(factoryPid = Constants.PID_MS_SOAP, name = "test-ms-config", location = "?", properties = {
    @Property(key = "xmlaService.target", value = "(" + Constants.XMLASERVICE_FILTER_KEY + "="
        + Constants.XMLASERVICE_FILTER_VALUE + ")"),
    @Property(key = "osgi.soap.endpoint.contextpath", value = Constants.WS_PATH)})
@WithFactoryConfiguration(factoryPid = "org.eclipse.daanse.ws.handler.SOAPLoggingHandler", name = "test-ms-config",
    location = "?", properties = {
    @Property(key = "osgi.soap.endpoint.selector", value = "(service.pid=*)")})
@RequireServiceComponentRuntime
public class ExecuteRequestTest {

    private Logger logger = LoggerFactory.getLogger(ExecuteRequestTest.class);

    @InjectBundleContext
    BundleContext bc;

    @BeforeEach
    void beforaEach() throws InterruptedException {
        XmlaService xmlaService = mock(XmlaService.class);
        ExecuteService executeService = mock(ExecuteService.class);

        when(xmlaService.execute()).thenReturn(executeService);

        bc.registerService(XmlaService.class, xmlaService, FrameworkUtil
            .asDictionary(Map.of(Constants.XMLASERVICE_FILTER_KEY, Constants.XMLASERVICE_FILTER_VALUE)));
    }

    @Test
    void test_Statement(@InjectService XmlaService xmlaService) throws Exception {
        ArgumentCaptor<StatementRequest> captor = ArgumentCaptor.forClass(StatementRequest.class);

        final String sRequest = """
            <Execute xmlns="urn:schemas-microsoft-com:xml-analysis">
              <Command>
                  <Statement>
                      select [Measures].[Sales Count] on 0, non empty [Store].[Store State].members on 1 from [Sales]
                  </Statement>
              </Command>
              <Properties>
                  <PropertyList>
                      <DataSourceInfo>FoodMart</DataSourceInfo>
                      <Catalog>FoodMart</Catalog>
                      <Format>Tabular</Format>
                      <AxisFormat>TupleFormat</AxisFormat>
                  </PropertyList>
              </Properties>
            </Execute>
              """;

        SOAPUtil.callSoapWebService(Constants.soapEndpointUrl, Optional.of(Constants.SOAP_ACTION_EXECUTE),
            SOAPUtil.envelop(sRequest));

        ExecuteService executeService = xmlaService.execute();
        verify(executeService, (times(1))).statement(captor.capture());

        StatementRequest request = captor.getValue();
        assertThat(request).isNotNull()
            .satisfies(d -> {
                assertThat(d.properties()).isNotNull()
                    .satisfies(p -> {
                        assertThat(p.dataSourceInfo()).isNotNull()
                            .isPresent().contains("FoodMart");
                        assertThat(p.catalog()).isNotNull()
                            .isPresent().contains("FoodMart");
                        assertThat(p.format()).isNotNull()
                            .isPresent().contains(Tabular);
                        assertThat(p.axisFormat()).isNotNull()
                            .isPresent().contains(TupleFormat);
                    });
                assertThat(d.command()).isNotNull()
                    .satisfies(r -> {
                        assertThat(r.statement().trim()).isNotNull()
                            .isEqualTo("select [Measures].[Sales Count] on 0, non empty [Store].[Store State].members" +
                                " on 1 from [Sales]");
                    });
            });
    }

    @Test
    void test_Alter(@InjectService XmlaService xmlaService) throws Exception {
        ArgumentCaptor<AlterRequest> captor = ArgumentCaptor.forClass(AlterRequest.class);
        //<Alter xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">
        final String sRequest = """
            <Execute xmlns="urn:schemas-microsoft-com:xml-analysis">
              <Command>
                <Alter>
                  <Object>
                    <DatabaseID>AdventureWorks_SSAS_Alter</DatabaseID>
                    <DimensionID>Dim Customer</DimensionID>
                  </Object>
                  <ObjectDefinition>
                    <Dimension xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                                                               xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                                                               xmlns:ddl2="http://schemas.microsoft.com/analysisservices/2003/engine/2"
                                                               xmlns:ddl2_2="http://schemas.microsoft.com/analysisservices/2003/engine/2/2"
                                                               xmlns:ddl100_100="http://schemas.microsoft.com/analysisservices/2008/engine/100/100">
                        <ID>Dim Customer</ID>
                        <Name>Customer</Name>
                        <ErrorConfiguration>
                            <KeyNotFound>ReportAndStop</KeyNotFound>
                            <KeyDuplicate>ReportAndStop</KeyDuplicate>
                            <NullKeyNotAllowed>ReportAndStop</NullKeyNotAllowed>
                        </ErrorConfiguration>
                        <Language>1033</Language>
                        <Collation>Latin1_General_CI_AS</Collation>
                        <UnknownMemberName>Unknown</UnknownMemberName>
                        <Attributes>
                            <Attribute>
                                <ID>Customer Key</ID>
                                <Name>Customer Key</Name>
                                <Usage>Key</Usage>
                                <EstimatedCount>18484</EstimatedCount>
                                <KeyColumns>
                                    <KeyColumn>
                                        <DataType>Integer</DataType>
                                        <Source xsi:type="ColumnBinding">
        									<TableID>dbo_DimCustomer</TableID>
        									<ColumnID>CustomerKey</ColumnID>
        								</Source>
                                    </KeyColumn>
                                </KeyColumns>
                                <OrderBy>Key</OrderBy>
                            </Attribute>
                        </Attributes>
                    </Dimension>
                  </ObjectDefinition>
                </Alter>
              </Command>
              <Properties>
                  <PropertyList>
                      <DataSourceInfo>FoodMart</DataSourceInfo>
                      <Catalog>FoodMart</Catalog>
                      <Format>Tabular</Format>
                      <AxisFormat>TupleFormat</AxisFormat>
                  </PropertyList>
              </Properties>
            </Execute>
              """;

        SOAPUtil.callSoapWebService(Constants.soapEndpointUrl, Optional.of(Constants.SOAP_ACTION_EXECUTE),
            SOAPUtil.envelop(sRequest));

        ExecuteService executeService = xmlaService.execute();
        verify(executeService, (times(1))).alter(captor.capture());

        AlterRequest request = captor.getValue();
        assertThat(request).isNotNull()
            .satisfies(d -> {
                assertThat(d.properties()).isNotNull()
                    .satisfies(p -> {
                        assertThat(p.dataSourceInfo()).isNotNull()
                            .isPresent().contains("FoodMart");
                        assertThat(p.catalog()).isNotNull()
                            .isPresent().contains("FoodMart");
                        assertThat(p.format()).isNotNull()
                            .isPresent().contains(Tabular);
                        assertThat(p.axisFormat()).isNotNull()
                            .isPresent().contains(TupleFormat);
                    });
                assertThat(d.command()).isNotNull()
                    .satisfies(r -> {
                        assertThat(r.object()).isNotNull();
                        assertThat(r.object().databaseID())
                        .isNotNull().isEqualTo("AdventureWorks_SSAS_Alter");
                        assertThat(r.object().dimensionID())
                        .isNotNull().isEqualTo("Dim Customer");
                        assertThat(r.objectDefinition()).isNotNull().satisfies(od -> {
                        	assertThat(od.dimension()).isNotNull().satisfies(dimension -> {
                        		assertThat(dimension.id()).isNotNull().isEqualTo("Dim Customer");
                        		assertThat(dimension.name()).isNotNull().isEqualTo("Customer");
                        	});
                        });
                    });
            });
    }

}
