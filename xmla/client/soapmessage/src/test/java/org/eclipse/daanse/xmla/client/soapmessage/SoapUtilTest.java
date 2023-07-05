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
import org.eclipse.daanse.xmla.api.xmla.Annotation;
import org.eclipse.daanse.xmla.api.xmla.Cancel;
import org.eclipse.daanse.xmla.api.xmla.Event;
import org.eclipse.daanse.xmla.api.xmla.EventColumnID;
import org.eclipse.daanse.xmla.api.xmla.EventSession;
import org.eclipse.daanse.xmla.api.xmla.EventType;
import org.eclipse.daanse.xmla.api.xmla.PartitionModes;
import org.eclipse.daanse.xmla.api.xmla.RetentionModes;
import org.eclipse.daanse.xmla.api.xmla.Trace;
import org.eclipse.daanse.xmla.api.xmla.TraceFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.xmlunit.assertj3.XmlAssert;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Arrays;
import java.util.LinkedList;
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
        //when(trace.annotations()).thenReturn(createAnnotationList());
        when(trace.logFileName()).thenReturn("LogFileName");
        when(trace.logFileAppend()).thenReturn(true);
        when(trace.logFileSize()).thenReturn(10L);
        when(trace.audit()).thenReturn(true);
        when(trace.logFileRollover()).thenReturn(true);
        when(trace.autoRestart()).thenReturn(true);
        when(trace.stopTime()).thenReturn(Instant.parse("2024-01-10T10:45:00Z"));
        when(trace.filter()).thenReturn(createTraceFilter());
        //when(trace.eventType()).thenReturn(createEventType());

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
        //checkAnnotationList(xmlAssert, "/SOAP:Envelope/SOAP:Body/Trace");
    }

    private void checkAnnotationList(XmlAssert xmlAssert, String path) {
        xmlAssert.nodesByXPath(path + "/Annotations")
            .exist();
        xmlAssert.valueByXPath(path + "/Annotations/Name")
            .isEqualTo(NAME);
        xmlAssert.valueByXPath(path + "/Annotations/Visibility")
            .isEqualTo("Visibility");
        xmlAssert.valueByXPath(path + "Annotations/Value")
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
        when(eventType.events()).thenReturn(createEventList());
        when(eventType.xEvent()).thenReturn(createXEvent());
        return eventType;
    }

    private XEvent createXEvent() {
        XEvent xEvent = mock(XEvent.class);
        when(xEvent.eventSession()).thenReturn(createEventSession());
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
        when(event.columns()).thenReturn(createEventColumnID());
        return event;
    }

    private EventColumnID createEventColumnID() {
        EventColumnID eventColumnID = mock(EventColumnID.class);
        when(eventColumnID.columnID()).thenReturn(List.of("columnID1", "columnID2"));
        return eventColumnID;
    }

    TraceFilter createTraceFilter() {
        TraceFilter traceFilter = mock(TraceFilter.class);
        return traceFilter;
    }

    private ExecuteParameter createExecuteParameter() {
        ExecuteParameter executeParameter = mock(ExecuteParameter.class);
        when(executeParameter.value()).thenReturn(VALUE2);
        when(executeParameter.name()).thenReturn(NAME_LC);
        return executeParameter;
    }

    private static  <N> Answer<List<N>> setupDummyListAnswer(N... values) {
        final List<N> someList = new LinkedList<>(Arrays.asList(values));

        Answer<List<N>> answer = new Answer<>() {
            @Override
            public List<N> answer(InvocationOnMock invocation) throws Throwable {
                return someList;
            }
        };
        return answer;
    }

}
