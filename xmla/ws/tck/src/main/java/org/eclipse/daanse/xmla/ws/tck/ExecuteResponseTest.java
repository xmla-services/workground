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
import org.eclipse.daanse.xmla.api.exception.Messages;
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
import org.eclipse.daanse.xmla.model.record.execute.alter.AlterResponseR;
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
import org.eclipse.daanse.xmla.model.record.xmla_empty.EmptyresultR;
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
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

    public static final String STATMENT_REQUEST = """
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

    public static final String ALTER_REQUEST = """
            <Execute xmlns="urn:schemas-microsoft-com:xml-analysis">
              <Command>
                <Alter>
                  <Object>
                    <DatabaseID>AdventureWorks_SSAS_Alter</DatabaseID>
                    <DimensionID>Dim Customer</DimensionID>
                  </Object>
                  <ObjectDefinition>
                    <Dimension>
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

        MessagesR messages = new MessagesR(List.of(getErrorType()));

        MddatasetR mdDataSet = new MddatasetR(olapInfo,
            axes,
            cellData,
            exception,
            messages);
        StatementResponseR row = new StatementResponseR(mdDataSet);

        ExecuteService executeService = xmlaService.execute();
        when(executeService.statement(Mockito.any())).thenReturn(row);

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_EXECUTE), SOAPUtil.envelop(STATMENT_REQUEST));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);
        checkRow(xmlAssert);
    }

    @Test
    void test_Alter(@InjectService XmlaService xmlaService) throws Exception {

        ExceptionR exception = new ExceptionR();
        Messages messages = new MessagesR(List.of(getErrorType()));
        EmptyresultR emptyresult = new EmptyresultR(exception, messages);
        AlterResponseR alterResponse = new AlterResponseR(emptyresult);
        ExecuteService executeService = xmlaService.execute();
        when(executeService.alter(Mockito.any())).thenReturn(alterResponse);

        SOAPMessage response = SOAPUtil.callSoapWebService(Constants.soapEndpointUrl,
            Optional.of(Constants.SOAP_ACTION_EXECUTE), SOAPUtil.envelop(ALTER_REQUEST));

        response.writeTo(System.out);

        XmlAssert xmlAssert = XMLUtil.createAssert(response);

    }

    private void checkAlert(XmlAssert xmlAssert) {
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse").exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root")
            .exist();
        checkException(xmlAssert);

        checkMessages(xmlAssert);
    }

    private void checkMessages(XmlAssert xmlAssert) {
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error")
            .exist().haveAttribute("Description", "description")
            .haveAttribute("HelpFile", "helpFile")
            .haveAttribute("Source", "source");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/Start")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/Start")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/Start/Line")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/Start/Line")
            .isEqualTo("1");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/Start/Column")
            .isEqualTo("2");

        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/End")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/End/Line")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/End/Line")
            .isEqualTo("3");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/End/Column")
            .isEqualTo("4");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/LineOffset")
            .isEqualTo("1");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/TextLength")
            .isEqualTo("2");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/SourceObject")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/SourceObject/engine200:WarningColumn")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/SourceObject/engine200:WarningColumn/Dimension")
            .isEqualTo("dimension");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/SourceObject/engine200:WarningColumn/Attribute")
            .isEqualTo("attribute");

        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/SourceObject/engine200:WarningMeasure")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/SourceObject/engine200:WarningMeasure/Cube")
            .isEqualTo("cube");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/SourceObject/engine200:WarningMeasure/MeasureGroup")
            .isEqualTo("measureGroup");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/SourceObject/engine200:WarningMeasure/MeasureName")
            .isEqualTo("measureName");


        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/DependsOnObject")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/DependsOnObject/engine200:WarningColumn")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/DependsOnObject/engine200:WarningColumn/Dimension")
            .isEqualTo("dimension");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/DependsOnObject/engine200:WarningColumn/Attribute")
            .isEqualTo("attribute");

        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/DependsOnObject/engine200:WarningMeasure")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/DependsOnObject/engine200:WarningMeasure/Cube")
            .isEqualTo("cube");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/DependsOnObject/engine200:WarningMeasure/MeasureGroup")
            .isEqualTo("measureGroup");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/DependsOnObject/engine200:WarningMeasure/MeasureName")
            .isEqualTo("measureName");

        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/RowNumber")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Location/RowNumber")
            .isEqualTo("3");

        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Messages/Error/Callstack")
            .isEqualTo("callstack");

    }

    private void checkException(XmlAssert xmlAssert) {
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Exception")
            .exist();
    }

    private Type getErrorType() {
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

        return new ErrorTypeR(location,
            "callstack",
            1l,
            "description",
            "source",
            "helpFile");
    }

    private void checkRow(XmlAssert xmlAssert) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            .withZone(ZoneId.systemDefault());
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse").exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root")
            .exist();

        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/OlapInfo")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/OlapInfo/CubeInfo")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/OlapInfo/CubeInfo/Cube")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/OlapInfo/CubeInfo/Cube/CubeName")
            .isEqualTo("cubeName");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/OlapInfo/CubeInfo/Cube/engine:LastDataUpdate")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/OlapInfo/CubeInfo/Cube/engine:LastDataUpdate")
            .isEqualTo(formatter.format(Instant.ofEpochMilli(2000l)));
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/OlapInfo/CubeInfo/Cube/engine:LastSchemaUpdate")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/OlapInfo/CubeInfo/Cube/engine:LastSchemaUpdate")
            .isEqualTo(formatter.format(Instant.ofEpochMilli(2000l)));


        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/OlapInfo/AxesInfo")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/OlapInfo/AxesInfo/AxisInfo")
            .exist().haveAttribute("name", "name");;
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/OlapInfo/AxesInfo/AxisInfo/HierarchyInfo")
            .exist().haveAttribute("name", "name");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/OlapInfo/AxesInfo/AxisInfo/HierarchyInfo/tagName")
            .exist().haveAttribute("name", "name").haveAttribute("type", "value");


        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/OlapInfo/CellInfo")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/OlapInfo/CellInfo/tagName")
            .exist().haveAttribute("name", "name").haveAttribute("type", "value");


        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Axes")
        .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Axes/Axis")
            .exist().haveAttribute("name", "name");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Axes/Axis/Members")
            .exist().haveAttribute("Hierarchy", "hierarchy");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Axes/Axis/Members/Member")
            .exist().haveAttribute("Hierarchy", "hierarchy");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/Axes/Axis/Members/Member/tagName")
            .exist().haveAttribute("name", "name").haveAttribute("type", "value");;


        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/CellData")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/CellData/Cell")
            .exist().haveAttribute("CellOrdinal", "1");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/CellData/Cell/Value")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/CellData/Cell/Value/Error")
            .exist().haveAttribute("Description", "description").haveAttribute("ErrorCode", "1");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/msxmla:ExecuteResponse/msxmla:return/mddataset:root/CellData/Cell/tagName")
            .exist().haveAttribute("name", "name").haveAttribute("type", "value");;

        checkException(xmlAssert);

        checkMessages(xmlAssert);
    }

}
