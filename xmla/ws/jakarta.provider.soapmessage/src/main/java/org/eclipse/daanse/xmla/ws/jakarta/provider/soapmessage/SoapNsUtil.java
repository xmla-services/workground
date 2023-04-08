package org.eclipse.daanse.xmla.ws.jakarta.provider.soapmessage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPEnvelope;

public class SoapNsUtil {

    private static final String HTTP_SCHEMAS_XMLSOAP_ORG_SOAP_ENVELOPE = "http://schemas.xmlsoap.org/soap/envelope";

    public static Map<String, String> nsMap(SOAPElement soapElement) {
        boolean isEnvelop = SOAPEnvelope.class.isInstance(soapElement);
        Map<String, String> nsMap = new HashMap<>();
        Iterator<String> nsPrefixIterator = soapElement.getNamespacePrefixes();
        while (nsPrefixIterator.hasNext()) {
            String prefix = nsPrefixIterator.next();
            String nsUri = soapElement.getNamespaceURI(prefix);

            if (isEnvelop) {
                // filter SOAP-ENV ns
                if (!nsUri.startsWith(HTTP_SCHEMAS_XMLSOAP_ORG_SOAP_ENVELOPE)) {
                    nsMap.put(prefix, nsUri);
                }
            }
        }

        return nsMap;
    }

}