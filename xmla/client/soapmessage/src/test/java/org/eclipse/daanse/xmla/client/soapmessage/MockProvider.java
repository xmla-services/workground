package org.eclipse.daanse.xmla.client.soapmessage;

import java.io.IOException;

import org.eclipse.daanse.ws.api.whiteboard.annotations.RequireSoapWhiteboard;

import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.Provider;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.ServiceMode;
import jakarta.xml.ws.WebServiceProvider;

@WebServiceProvider()
@ServiceMode(value = Service.Mode.MESSAGE)
@RequireSoapWhiteboard
public class MockProvider implements Provider<SOAPMessage> {

    @Override
    public SOAPMessage invoke(SOAPMessage request) {
        try {
            request.writeTo(System.out);
        } catch (SOAPException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

}
