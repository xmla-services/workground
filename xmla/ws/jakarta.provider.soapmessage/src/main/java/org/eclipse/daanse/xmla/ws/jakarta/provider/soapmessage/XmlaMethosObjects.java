package org.eclipse.daanse.xmla.ws.jakarta.provider.soapmessage;

import javax.xml.namespace.QName;

import jakarta.xml.soap.SOAPElement;

public enum XmlaMethosObjects {
    Discover(new QName("urn:schemas-microsoft-com:xml-analysis", "Discover"));

    private QName qName;

    private XmlaMethosObjects(QName qName) {

        this.qName = qName;
    }

    boolean matches(SOAPElement element) {
        return qName.equals(element.getElementQName());

    }

}
