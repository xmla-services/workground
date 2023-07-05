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
import org.eclipse.daanse.xmla.api.execute.ExecuteParameter;
import org.eclipse.daanse.xmla.api.xmla.Cancel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xmlunit.assertj3.XmlAssert;

import java.math.BigInteger;

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
        Cancel cancel = mock(Cancel.class);
        when(cancel.connectionID()).thenReturn(BigInteger.ONE);
        when(cancel.sessionID()).thenReturn("SessionID");
        when(cancel.spid()).thenReturn(BigInteger.TEN);
        when(cancel.cancelAssociated()).thenReturn(true);

        SoapUtil.addChildElementCancel(soapElement, cancel);

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
    void addChildElementExecuteParameter() throws Exception {
        ExecuteParameter executeParameter = mock(ExecuteParameter.class);
        when(executeParameter.value()).thenReturn("Value");
        when(executeParameter.name()).thenReturn("Name");

        SoapUtil.addChildElementExecuteParameter(soapElement, executeParameter);

        XmlAssert xmlAssert = XMLUtil.createAssert(message);
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Parameter")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Parameter/Name")
            .isEqualTo("Name");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Parameter/Value")
            .isEqualTo("Value");
    }
}
