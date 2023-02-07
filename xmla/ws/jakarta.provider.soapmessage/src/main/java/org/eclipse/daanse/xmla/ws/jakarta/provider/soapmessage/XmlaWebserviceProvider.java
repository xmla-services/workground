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
package org.eclipse.daanse.xmla.ws.jakarta.provider.soapmessage;

import java.io.IOException;

import org.eclipse.daanse.ws.api.whiteboard.annotations.RequireSoapWhiteboard;
import org.eclipse.daanse.ws.api.whiteboard.prototypes.SOAPWhiteboardEndpoint;
import org.eclipse.daanse.xmla.api.XmlaService;
import org.eclipse.daanse.xmla.ws.tck.SOAPUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPConstants;
import jakarta.xml.soap.SOAPEnvelope;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPFactory;
import jakarta.xml.soap.SOAPFault;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.soap.SOAPPart;
import jakarta.xml.ws.Provider;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.ServiceMode;
import jakarta.xml.ws.WebServiceProvider;
import jakarta.xml.ws.soap.SOAPFaultException;

@WebServiceProvider()
@ServiceMode(value = Service.Mode.MESSAGE)
@RequireSoapWhiteboard
@Component(service = Provider.class, name = "org.eclipse.daanse.msxmlanalysisservice")
@Designate(factory = true, ocd = XmlaWebserviceProvider.Config.class)
@SOAPWhiteboardEndpoint(contextpath = "xmla")
public class XmlaWebserviceProvider implements Provider<SOAPMessage> {

    @ObjectClassDefinition()
    @interface Config {

        @AttributeDefinition(name = "XMLA-Service Filter", required = true)
        String xmlaService_target()

        default "(&(must.be.configured=*)(!(must.not.configured=*)))";

        @AttributeDefinition(required = true)
        String osgi_soap_endpoint_contextpath();
    }

    private XmlaApiAdapter wsAdapter;

    @Reference
    private XmlaService xmlaService;

    @Activate
    public void activate() {
        wsAdapter = new XmlaApiAdapter(xmlaService);
    }

    private static final String MY_TNS = "my:tns";

    @Override
    public SOAPMessage invoke(SOAPMessage request) {
        System.out.println("===== The provider got a request =====");
        try {
            request.writeTo(System.out);
            System.out.println();

            System.out.println(SOAPUtil.string(request));

            wsAdapter.handleRequest(request);

            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = messageFactory.createMessage();
            SOAPPart soapPart = soapMessage.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();
            envelope.addNamespaceDeclaration("tns", "myTargetNamespace");
            SOAPBody body = envelope.getBody();

            body.addChildElement("provider", "tns", MY_TNS);
            body.addChildElement("say")
                    .setTextContent("Hello Test");
            return soapMessage;

        } catch (SOAPException | IOException e) {
            throw new SOAPFaultException(getFault(e));
        }
    }

    private SOAPFault getFault(Exception ex) {
        try {
            SOAPFault fault = SOAPFactory.newInstance()
                    .createFault();
            fault.setFaultCode(SOAPConstants.SOAP_SENDER_FAULT);
            fault.setFaultString(ex.toString());
            return fault;
        } catch (SOAPException e) {
            throw new IllegalStateException(e);
        }
    }

}
