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

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import org.eclipse.daanse.xmla.api.engine300_300.XEvent;
import org.eclipse.daanse.xmla.api.execute.ExecuteParameter;
import org.eclipse.daanse.xmla.api.xmla.AndOrType;
import org.eclipse.daanse.xmla.api.xmla.AndOrTypeEnum;
import org.eclipse.daanse.xmla.api.xmla.Annotation;
import org.eclipse.daanse.xmla.api.xmla.BoolBinop;
import org.eclipse.daanse.xmla.api.xmla.Cancel;
import org.eclipse.daanse.xmla.api.xmla.ClearCache;
import org.eclipse.daanse.xmla.api.xmla.Event;
import org.eclipse.daanse.xmla.api.xmla.EventColumnID;
import org.eclipse.daanse.xmla.api.xmla.EventSession;
import org.eclipse.daanse.xmla.api.xmla.EventType;
import org.eclipse.daanse.xmla.api.xmla.MajorObject;
import org.eclipse.daanse.xmla.api.xmla.NotType;
import org.eclipse.daanse.xmla.api.xmla.ObjectReference;
import org.eclipse.daanse.xmla.api.xmla.PartitionModes;
import org.eclipse.daanse.xmla.api.xmla.RetentionModes;
import org.eclipse.daanse.xmla.api.xmla.Trace;
import org.eclipse.daanse.xmla.api.xmla.TraceFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xmlunit.assertj3.XmlAssert;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.eclipse.daanse.xmla.client.soapmessage.Constants.DESCRIPTION;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.ID;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.NAME;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.NAME_LC;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.VALUE2;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SoapUtilTest {

    private MessageFactory mf;
    private SOAPMessage message;
    private SOAPElement soapElement;

    @BeforeEach
    void beforeEach() throws SOAPException {
        mf = MessageFactory.newInstance();
        message = mf.createMessage();
        soapElement = message.getSOAPBody();
    }

    @Test
    void addChildElementCancelTest() throws Exception {

        SoapUtil.addChildElementCancel(soapElement, createCancel());

        XmlAssert xmlAssert = XMLUtil.createAssert(message);
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Cancel")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Cancel/ConnectionID")
            .isEqualTo("1");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Cancel/SessionID")
            .isEqualTo("SessionID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Cancel/SPID")
            .isEqualTo("10");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Cancel/CancelAssociated")
            .isEqualTo(true);
    }

    @Test
    void addChildElementExecuteParameterTest() throws Exception {
        SoapUtil.addChildElementExecuteParameter(soapElement, createExecuteParameter());

        XmlAssert xmlAssert = XMLUtil.createAssert(message);
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Parameter")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Parameter/Name")
            .isEqualTo("Name");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Parameter/Value")
            .isEqualTo("Value");
    }

    @Test
    void addChildElementTraceTest() throws Exception {
        Trace trace = mock(Trace.class);
        when(trace.name()).thenReturn(NAME);
        when(trace.id()).thenReturn(ID);
        when(trace.createdTimestamp()).thenReturn(Instant.parse("2024-01-10T10:45:00Z"));
        when(trace.lastSchemaUpdate()).thenReturn(Instant.parse("2024-01-10T10:45:00Z"));
        when(trace.description()).thenReturn(DESCRIPTION);
        List<Annotation> l = createAnnotationList();
        when(trace.annotations()).thenReturn(l);
        when(trace.logFileName()).thenReturn("LogFileName");
        when(trace.logFileAppend()).thenReturn(true);
        when(trace.logFileSize()).thenReturn(10L);
        when(trace.audit()).thenReturn(true);
        when(trace.logFileRollover()).thenReturn(true);
        when(trace.autoRestart()).thenReturn(true);
        when(trace.stopTime()).thenReturn(Instant.parse("2024-01-10T10:45:00Z"));
        TraceFilter traceFilter = createTraceFilter();
        when(trace.filter()).thenReturn(traceFilter);
        EventType eventType = createEventType();
        when(trace.eventType()).thenReturn(eventType);

        SoapUtil.addChildElementTrace(soapElement, trace);

        XmlAssert xmlAssert = XMLUtil.createAssert(message);
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Trace")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Trace/NAME")
            .isEqualTo(NAME);
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Trace/ID")
            .isEqualTo(ID);
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Trace/CreatedTimestamp")
            .isEqualTo("2024-01-10T10:45:00Z");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Trace/LastSchemaUpdate")
            .isEqualTo("2024-01-10T10:45:00Z");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Trace/Description")
            .isEqualTo(DESCRIPTION);
        checkAnnotationList(xmlAssert, "/SOAP:Envelope/SOAP:Body/Trace");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Trace/LogFileName")
            .isEqualTo("LogFileName");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Trace/LogFileAppend")
            .isEqualTo("true");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Trace/LogFileSize")
            .isEqualTo("10");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Trace/Audit")
            .isEqualTo("true");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Trace/LogFileRollover")
            .isEqualTo("true");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Trace/AutoRestart")
            .isEqualTo("true");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Trace/StopTime")
            .isEqualTo("2024-01-10T10:45:00Z");
        checkTraceFilter(xmlAssert, "/SOAP:Envelope/SOAP:Body/Trace");
        checkEventType(xmlAssert, "/SOAP:Envelope/SOAP:Body/Trace");
    }

    @Test
    void addChildElementClearCacheTest() throws Exception {
        SoapUtil.addChildElementClearCache(soapElement, createClearCache());
        XmlAssert xmlAssert = XMLUtil.createAssert(message);

        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/ClearCache")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/ServerID")
            .isEqualTo("serverID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/DatabaseID")
            .isEqualTo("databaseID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/RoleID")
            .isEqualTo("roleID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/TraceID")
            .isEqualTo("traceID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/AssemblyID")
            .isEqualTo("assemblyID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/DimensionID")
            .isEqualTo("dimensionID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/DimensionPermissionID")
            .isEqualTo("dimensionPermissionID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/DataSourceID")
            .isEqualTo("dataSourceID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/DataSourcePermissionID")
            .isEqualTo("dataSourcePermissionID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/DatabasePermissionID")
            .isEqualTo("databasePermissionID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/DataSourceViewID")
            .isEqualTo("dataSourceViewID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/CubeID")
            .isEqualTo("cubeID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/MiningStructureID")
            .isEqualTo("miningStructureID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/MeasureGroupID")
            .isEqualTo("measureGroupID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/PerspectiveID")
            .isEqualTo("perspectiveID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/CubePermissionID")
            .isEqualTo("cubePermissionID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/MdxScriptID")
            .isEqualTo("mdxScriptID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/PartitionID")
            .isEqualTo("partitionID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/AggregationDesignID")
            .isEqualTo("aggregationDesignID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/MiningModelID")
            .isEqualTo("miningModelID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/MiningModelPermissionID")
            .isEqualTo("miningModelPermissionID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/MiningStructurePermissionID")
            .isEqualTo("miningStructurePermissionID");
    }

    @Test
    void addChildElementMajorObjectTest() throws Exception {
        SoapUtil.addChildElementMajorObject(soapElement, createMajorObject());
        XmlAssert xmlAssert = XMLUtil.createAssert(message);
        //TODO

    }

    private MajorObject createMajorObject() {
        MajorObject majorObject = mock(MajorObject.class);
        //TODO
        return majorObject;
    }

    private ObjectReference createObjectReference() {
        ObjectReference objectReference = mock(ObjectReference.class);
        when(objectReference.serverID()).thenReturn("serverID");
        when(objectReference.databaseID()).thenReturn("databaseID");
        when(objectReference.roleID()).thenReturn("roleID");
        when(objectReference.traceID()).thenReturn("traceID");
        when(objectReference.assemblyID()).thenReturn("assemblyID");
        when(objectReference.dimensionID()).thenReturn("dimensionID");
        when(objectReference.dimensionPermissionID()).thenReturn("dimensionPermissionID");
        when(objectReference.dataSourceID()).thenReturn("dataSourceID");
        when(objectReference.dataSourcePermissionID()).thenReturn("dataSourcePermissionID");
        when(objectReference.databasePermissionID()).thenReturn("databasePermissionID");
        when(objectReference.dataSourceViewID()).thenReturn("dataSourceViewID");
        when(objectReference.cubeID()).thenReturn("cubeID");
        when(objectReference.miningStructureID()).thenReturn("miningStructureID");
        when(objectReference.measureGroupID()).thenReturn("measureGroupID");
        when(objectReference.perspectiveID()).thenReturn("perspectiveID");
        when(objectReference.cubePermissionID()).thenReturn("cubePermissionID");
        when(objectReference.mdxScriptID()).thenReturn("mdxScriptID");
        when(objectReference.partitionID()).thenReturn("partitionID");
        when(objectReference.aggregationDesignID()).thenReturn("aggregationDesignID");
        when(objectReference.miningModelID()).thenReturn("miningModelID");
        when(objectReference.miningModelPermissionID()).thenReturn("miningModelPermissionID");
        when(objectReference.miningStructurePermissionID()).thenReturn("miningStructurePermissionID");

        return objectReference;
    }

    private ClearCache createClearCache() {
        ClearCache clearCache = mock(ClearCache.class);
        ObjectReference objectReference = createObjectReference();
        when(clearCache.object()).thenReturn(objectReference);
        return clearCache;
    }

    private void checkEventType(XmlAssert xmlAssert, String path) {
        xmlAssert.nodesByXPath(path + "/EventType")
            .exist();
        String eventPath = new StringBuilder(path).append("/EventType/Events").toString();
        xmlAssert.nodesByXPath(eventPath)
            .exist();
        String xEventPath = new StringBuilder(path).append("/EventType/XEvent").toString();
        xmlAssert.nodesByXPath(xEventPath)
            .exist();
        checkEvent(xmlAssert, eventPath);
        checkEventSession(xmlAssert, xEventPath);

    }

    private void checkEvent(XmlAssert xmlAssert, String path) {
        xmlAssert.nodesByXPath(path + "/Event")
            .exist();
        xmlAssert.valueByXPath(path + "/Event/EventID")
            .isEqualTo("EventID");
        checkEventEventColumnID(xmlAssert, path + "/Event");
    }

    private void checkEventEventColumnID(XmlAssert xmlAssert, String path) {
        xmlAssert.nodesByXPath(path + "/Columns")
            .exist();
        xmlAssert.valueByXPath(path + "/Columns/ColumnID")
            .isEqualTo("columnID1");

    }

    private void checkEventSession(XmlAssert xmlAssert, String path) {
        xmlAssert.nodesByXPath(path + "/event_session")
            .exist();
        xmlAssert.valueByXPath(path + "/event_session/templateCategory")
            .isEqualTo("templateCategory");
        xmlAssert.valueByXPath(path + "/event_session/templateName")
            .isEqualTo("templateName");
        xmlAssert.valueByXPath(path + "/event_session/templateDescription")
            .isEqualTo("templateDescription");
        checkObjectList(xmlAssert, path + "/event_session/event");
        checkObjectList(xmlAssert, path + "/event_session/target");
        xmlAssert.valueByXPath(path + "/event_session/name")
            .isEqualTo("name");
        xmlAssert.valueByXPath(path + "/event_session/maxMemory")
            .isEqualTo("1");
        xmlAssert.valueByXPath(path + "/event_session/eventRetentionMode")
            .isEqualTo("allowSingleEventLoss");
        xmlAssert.valueByXPath(path + "/event_session/dispatchLatency")
            .isEqualTo("10");
        xmlAssert.valueByXPath(path + "/event_session/maxEventSize")
            .isEqualTo("11");
        xmlAssert.valueByXPath(path + "/event_session/memoryPartitionMode")
            .isEqualTo("allowSingleEventLoss");
        xmlAssert.valueByXPath(path + "/event_session/trackCausality")
            .isEqualTo(true);
    }

    private void checkObjectList(XmlAssert xmlAssert, String path) {
        xmlAssert.valueByXPath(path)
            .isEqualTo("Object1");
    }

    private void checkTraceFilter(XmlAssert xmlAssert, String path) {
        xmlAssert.nodesByXPath(path + "/Filter")
            .exist();
        checkNotType(xmlAssert, path + "/Filter/Not");
        checkAndOrType(xmlAssert, path + "/Filter/Or");
        checkAndOrType(xmlAssert, path + "/Filter/And");
        checkBoolBinop(xmlAssert, path + "/Filter/Equal");
        checkBoolBinop(xmlAssert, path + "/Filter/NotEqual");
        checkBoolBinop(xmlAssert, path + "/Filter/Less");
        checkBoolBinop(xmlAssert, path + "/Filter/LessOrEqual");
        checkBoolBinop(xmlAssert, path + "/Filter/Greater");
        checkBoolBinop(xmlAssert, path + "/Filter/GreaterOrEqual");
        checkBoolBinop(xmlAssert, path + "/Filter/Like");
        checkBoolBinop(xmlAssert, path + "/Filter/NotLike");
    }

    private void checkNotType(XmlAssert xmlAssert, String path) {
        xmlAssert.nodesByXPath(path)
            .exist();
        checkAndOrType(xmlAssert, path + "/Or");
        checkAndOrType(xmlAssert, path + "/And");
        checkBoolBinop(xmlAssert, path + "/Equal");
        checkBoolBinop(xmlAssert, path + "/NotEqual");
        checkBoolBinop(xmlAssert, path + "/Less");
        checkBoolBinop(xmlAssert, path + "/LessOrEqual");
        checkBoolBinop(xmlAssert, path + "/Greater");
        checkBoolBinop(xmlAssert, path + "/GreaterOrEqual");
        checkBoolBinop(xmlAssert, path + "/Like");
        checkBoolBinop(xmlAssert, path + "/NotLike");
    }

    private void checkAndOrType(XmlAssert xmlAssert, String path) {
        xmlAssert.nodesByXPath(path)
            .exist();
        xmlAssert.nodesByXPath(path + "/And")
            .exist();
    }

    private void checkBoolBinop(XmlAssert xmlAssert, String path) {
        xmlAssert.nodesByXPath(path)
            .exist();
        xmlAssert.valueByXPath(path + "/ColumnID")
            .isEqualTo("columnID");
        xmlAssert.valueByXPath(path + "/Value")
            .isEqualTo("value");
    }

    private void checkAnnotationList(XmlAssert xmlAssert, String path) {
        xmlAssert.nodesByXPath(path + "/Annotations")
            .exist();
        xmlAssert.valueByXPath(path + "/Annotations/Annotation/Name")
            .isEqualTo(NAME);
        xmlAssert.valueByXPath(path + "/Annotations/Annotation/Visibility")
            .isEqualTo("Visibility");
        xmlAssert.valueByXPath(path + "/Annotations/Annotation/Value")
            .isEqualTo(VALUE2);
    }

    private List<Annotation> createAnnotationList() {
        Annotation annotation1 = mock(Annotation.class);
        when(annotation1.name()).thenReturn(NAME);
        when(annotation1.value()).thenReturn(Optional.of(VALUE2));
        when(annotation1.visibility()).thenReturn(Optional.of("Visibility"));
        return List.of(annotation1);
    }

    private Cancel createCancel() {
        Cancel cancel = mock(Cancel.class);
        when(cancel.connectionID()).thenReturn(BigInteger.ONE);
        when(cancel.sessionID()).thenReturn("SessionID");
        when(cancel.spid()).thenReturn(BigInteger.TEN);
        when(cancel.cancelAssociated()).thenReturn(true);
        return cancel;
    }

    private EventType createEventType() {
        EventType eventType = mock(EventType.class);
        List<Event> l = createEventList();
        when(eventType.events()).thenReturn(l);
        XEvent xEvent = createXEvent();
        when(eventType.xEvent()).thenReturn(xEvent);
        return eventType;
    }

    private XEvent createXEvent() {
        XEvent xEvent = mock(XEvent.class);
        EventSession eventSession = createEventSession();
        when(xEvent.eventSession()).thenReturn(eventSession);
        return xEvent;
    }

    private EventSession createEventSession() {
        EventSession eventSession = mock(EventSession.class);
        when(eventSession.templateCategory()).thenReturn("templateCategory");
        when(eventSession.templateName()).thenReturn("templateName");
        when(eventSession.templateDescription()).thenReturn("templateDescription");
        when(eventSession.event()).thenReturn(createListObject());
        when(eventSession.target()).thenReturn(createListObject());
        when(eventSession.name()).thenReturn("name");
        when(eventSession.maxMemory()).thenReturn(BigInteger.ONE);
        when(eventSession.eventRetentionMode()).thenReturn(RetentionModes.ALLOW_SINGLE_EVENT_LOSS);
        when(eventSession.dispatchLatency()).thenReturn(10l);
        when(eventSession.maxEventSize()).thenReturn(11l);
        when(eventSession.memoryPartitionMode()).thenReturn(PartitionModes.NONE);
        when(eventSession.trackCausality()).thenReturn(true);
        return eventSession;
    }

    private List<Object> createListObject() {
        return List.of("Object1", "Object2");
    }

    private List<Event> createEventList() {
        Event event = createEvent();
        return List.of(event);
    }

    private Event createEvent() {
        Event event = mock(Event.class);
        when(event.eventID()).thenReturn("EventID");
        EventColumnID ecid = createEventColumnID();
        when(event.columns()).thenReturn(ecid);
        return event;
    }

    private EventColumnID createEventColumnID() {
        EventColumnID eventColumnID = mock(EventColumnID.class);
        when(eventColumnID.columnID()).thenReturn(List.of("columnID1", "columnID2"));
        return eventColumnID;
    }

    TraceFilter createTraceFilter() {
        TraceFilter traceFilter = mock(TraceFilter.class);
        NotType not = createNotType();
        AndOrType or = createAndOrType();
        AndOrType and = or;
        BoolBinop isEqual = createBoolBinop();
        BoolBinop notEqual = isEqual;
        BoolBinop less = isEqual;
        BoolBinop lessOrEqual = isEqual;
        BoolBinop greater = isEqual;
        BoolBinop greaterOrEqual = isEqual;
        BoolBinop like = isEqual;
        BoolBinop notLike = isEqual;
        when(traceFilter.not()).thenReturn(not);
        when(traceFilter.or()).thenReturn(or);
        when(traceFilter.and()).thenReturn(and);
        when(traceFilter.isEqual()).thenReturn(isEqual);
        when(traceFilter.notEqual()).thenReturn(notEqual);
        when(traceFilter.less()).thenReturn(less);
        when(traceFilter.lessOrEqual()).thenReturn(lessOrEqual);
        when(traceFilter.greater()).thenReturn(greater);
        when(traceFilter.greaterOrEqual()).thenReturn(greaterOrEqual);
        when(traceFilter.like()).thenReturn(like);
        when(traceFilter.notLike()).thenReturn(notLike);
        return traceFilter;
    }

    private BoolBinop createBoolBinop() {
        BoolBinop boolBinop = mock(BoolBinop.class);
        when(boolBinop.columnID()).thenReturn("columnID");
        when(boolBinop.value()).thenReturn("value");
        return boolBinop;
    }

    private NotType createNotType() {
        NotType notType = mock(NotType.class);
        AndOrType or = createAndOrType();
        AndOrType and = or;
        BoolBinop isEqual = createBoolBinop();
        BoolBinop notEqual = isEqual;
        BoolBinop less = isEqual;
        BoolBinop lessOrEqual = isEqual;
        BoolBinop greater = isEqual;
        BoolBinop greaterOrEqual = isEqual;
        BoolBinop like = isEqual;
        BoolBinop notLike = isEqual;
        when(notType.or()).thenReturn(or);
        when(notType.and()).thenReturn(and);
        when(notType.isEqual()).thenReturn(isEqual);
        when(notType.notEqual()).thenReturn(notEqual);
        when(notType.less()).thenReturn(less);
        when(notType.lessOrEqual()).thenReturn(lessOrEqual);
        when(notType.greater()).thenReturn(greater);
        when(notType.greaterOrEqual()).thenReturn(greaterOrEqual);
        when(notType.like()).thenReturn(like);
        when(notType.notLike()).thenReturn(notLike);
        return notType;
    }

    private AndOrType createAndOrType() {
        AndOrType andOrType = mock(AndOrType.class);
        List l = List.of(AndOrTypeEnum.And);
        when(andOrType.notOrOrOrAnd()).thenReturn(l);
        return andOrType;
    }

    private ExecuteParameter createExecuteParameter() {
        ExecuteParameter executeParameter = mock(ExecuteParameter.class);
        when(executeParameter.value()).thenReturn(VALUE2);
        when(executeParameter.name()).thenReturn(NAME_LC);
        return executeParameter;
    }

}
