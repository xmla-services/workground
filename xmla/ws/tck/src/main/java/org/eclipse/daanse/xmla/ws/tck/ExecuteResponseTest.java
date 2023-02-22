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
import org.eclipse.daanse.xmla.api.exception.Type;
import org.eclipse.daanse.xmla.api.execute.ExecuteService;
import org.eclipse.daanse.xmla.api.mddataset.Axis;
import org.eclipse.daanse.xmla.api.mddataset.AxisInfo;
import org.eclipse.daanse.xmla.api.mddataset.CellInfoItem;
import org.eclipse.daanse.xmla.api.mddataset.CellType;
import org.eclipse.daanse.xmla.api.mddataset.CellTypeError;
import org.eclipse.daanse.xmla.api.mddataset.HierarchyInfo;
import org.eclipse.daanse.xmla.api.mddataset.MemberType;
import org.eclipse.daanse.xmla.api.mddataset.OlapInfoCube;
import org.eclipse.daanse.xmla.model.record.engine200.WarningColumnR;
import org.eclipse.daanse.xmla.model.record.engine200.WarningLocationObjectR;
import org.eclipse.daanse.xmla.model.record.engine200.WarningMeasureR;
import org.eclipse.daanse.xmla.model.record.exception.ErrorTypeR;
import org.eclipse.daanse.xmla.model.record.exception.ExceptionR;
import org.eclipse.daanse.xmla.model.record.exception.MessageLocationR;
import org.eclipse.daanse.xmla.model.record.exception.MessagesR;
import org.eclipse.daanse.xmla.model.record.exception.StartEndR;
import org.eclipse.daanse.xmla.model.record.execute.statement.StatementResponseR;
import org.eclipse.daanse.xmla.model.record.mddataset.AxesInfoR;
import org.eclipse.daanse.xmla.model.record.mddataset.AxesR;
import org.eclipse.daanse.xmla.model.record.mddataset.AxisInfoR;
import org.eclipse.daanse.xmla.model.record.mddataset.AxisR;
import org.eclipse.daanse.xmla.model.record.mddataset.CellDataR;
import org.eclipse.daanse.xmla.model.record.mddataset.CellInfoItemR;
import org.eclipse.daanse.xmla.model.record.mddataset.CellInfoR;
import org.eclipse.daanse.xmla.model.record.mddataset.CellSetTypeR;
import org.eclipse.daanse.xmla.model.record.mddataset.CellTypeErrorR;
import org.eclipse.daanse.xmla.model.record.mddataset.CellTypeR;
import org.eclipse.daanse.xmla.model.record.mddataset.CubeInfoR;
import org.eclipse.daanse.xmla.model.record.mddataset.HierarchyInfoR;
import org.eclipse.daanse.xmla.model.record.mddataset.MddatasetR;
import org.eclipse.daanse.xmla.model.record.mddataset.MemberTypeR;
import org.eclipse.daanse.xmla.model.record.mddataset.MembersTypeR;
import org.eclipse.daanse.xmla.model.record.mddataset.OlapInfoCubeR;
import org.eclipse.daanse.xmla.model.record.mddataset.OlapInfoR;
import org.eclipse.daanse.xmla.model.record.mddataset.ValueR;
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

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.mock;
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
public class ExecuteResponseTest {

    private Logger logger = LoggerFactory.getLogger(ExecuteResponseTest.class);

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

    public static final String REQUEST = """
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

    @Test
    void test_Statement(@InjectService XmlaService xmlaService) throws Exception {
        CellInfoItem any = new CellInfoItemR("tagName",
            "name", Optional.of("value"));

        OlapInfoCube cube = new OlapInfoCubeR("cubeName",
            Instant.ofEpochMilli(2000l),
            Instant.ofEpochMilli(2000l));

        CubeInfoR cubeInfo = new CubeInfoR(List.of(cube));

        HierarchyInfo hierarchyInfo = new HierarchyInfoR(List.of(any), "name");

        AxisInfo axisInfo = new AxisInfoR(List.of(hierarchyInfo), "name");

        AxesInfoR axesInfo = new AxesInfoR(List.of(axisInfo));

        CellInfoR cellInfo = new CellInfoR(List.of(any));

        OlapInfoR olapInfo = new OlapInfoR(cubeInfo,
            axesInfo,
            cellInfo);

        MemberType member = new MemberTypeR(List.of(any), "hierarchy");

        org.eclipse.daanse.xmla.api.mddataset.Type setType = new MembersTypeR(List.of(member), "hierarchy");

        Axis axis = new AxisR(List.of(setType), "name");

        AxesR axes = new AxesR(List.of(axis));

        CellTypeError error = new CellTypeErrorR(1l,
            "description");

        ValueR value = new ValueR(List.of(error));

        CellType cell = new CellTypeR(value,
            List.of(any),
        1);

        CellSetTypeR cellSet = new CellSetTypeR(List.of(new byte[]{1, 2}));

        CellDataR cellData = new CellDataR(List.of(cell), cellSet);

        ExceptionR exception = new ExceptionR();

        StartEndR start = new StartEndR(1, 2);

        StartEndR end = new StartEndR(3, 4);

        WarningColumnR warningColumn = new WarningColumnR("dimension", "attribute");

        WarningMeasureR warningMeasure = new WarningMeasureR("cube",
            "measureGroup",
            "measureName");

        WarningLocationObjectR sourceObject = new WarningLocationObjectR(warningColumn,
            warningMeasure);

        WarningLocationObjectR dependsOnObject = new WarningLocationObjectR(warningColumn,
            warningMeasure);

        MessageLocationR location = new MessageLocationR(start,
            end,
            1,
            2,
            sourceObject,
            dependsOnObject,
            3);

        Type warningOrError = new ErrorTypeR(location,
            "callstack",
            1l,
            "description",
            "source",
            "helpFile");

        MessagesR messages = new MessagesR(List.of(warningOrError));

        MddatasetR mdDataSet = new MddatasetR(olapInfo,
            axes,
            cellData,
            exception,
            messages);
        StatementResponseR row = new StatementResponseR(mdDataSet);

        ExecuteService executeService = xmlaService.execute();
        when(executeService.statement(Mockito.any())).thenReturn(row);

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_EXECUTE), SOAPUtil.envelop(REQUEST));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);
        /*checkRowValues(xmlAssert, Map.of(
            "DataSourceName", "dataSourceName",
            "DataSourceDescription", "dataSourceDescription",
            "URL", "url",
            "DataSourceInfo", "dataSourceInfo",
            "ProviderName", "providerName",
            "ProviderType", "MDP",
            "AuthenticationMode", "Unauthenticated"
        ));*/
    }

    private void checkRow(XmlAssert xmlAssert) {
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse").exist();
        //xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/return/rowset:root")
        //    .exist();
        //xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/return/rowset:root/rowset:row")
        //    .exist();
        //xmlAssert.valueByXPath("count(/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/return/rowset:root/rowset:row)"
        //).isEqualTo(1);
    }

    private void checkRowValues(XmlAssert xmlAssert, Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            xmlAssert.valueByXPath(new StringBuilder("/SOAP:Envelope/SOAP:Body/msxmla:DiscoverResponse/return/rowset" +
                ":root/rowset:row/rowset:").append(entry.getKey()).toString())
                .isEqualTo(entry.getValue());
        }
    }
}
