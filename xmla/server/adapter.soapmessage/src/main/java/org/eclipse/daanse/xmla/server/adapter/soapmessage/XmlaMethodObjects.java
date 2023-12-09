package org.eclipse.daanse.xmla.server.adapter.soapmessage;

import javax.xml.namespace.QName;

import jakarta.xml.soap.SOAPElement;

public enum XmlaMethodObjects {
    DISCOVER(new QName("urn:schemas-microsoft-com:xml-analysis", "Discover"));

    private QName qName;

    private XmlaMethodObjects(QName qName) {

        this.qName = qName;
    }

    boolean matches(SOAPElement element) {
        return qName.equals(element.getElementQName());

    }

}
