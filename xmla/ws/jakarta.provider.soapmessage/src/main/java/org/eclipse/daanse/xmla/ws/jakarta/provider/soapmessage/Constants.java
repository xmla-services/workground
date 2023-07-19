package org.eclipse.daanse.xmla.ws.jakarta.provider.soapmessage;

import javax.xml.namespace.QName;

public class Constants {

    private Constants() {
        // constructor
    }

    private static final String URN_SCHEMAS_MICROSOFT_COM_XML_ANALYSIS = "urn:schemas-microsoft-com:xml-analysis";

    public static final QName QNAME_MSXMLA_DISCOVER = new QName(URN_SCHEMAS_MICROSOFT_COM_XML_ANALYSIS,
        "Discover");
    public static final QName QNAME_MSXMLA_REQUESTTYPE = new QName(URN_SCHEMAS_MICROSOFT_COM_XML_ANALYSIS,
        "RequestType");
    public static final QName QNAME_MSXMLA_RESTRICTIONS = new QName(URN_SCHEMAS_MICROSOFT_COM_XML_ANALYSIS,
        "Restrictions");
    public static final QName QNAME_MSXMLA_RESTRICTIONLIST = new QName(URN_SCHEMAS_MICROSOFT_COM_XML_ANALYSIS,
        "RestrictionList");
    public static final QName QNAME_MSXMLA_PROPERTIES = new QName(URN_SCHEMAS_MICROSOFT_COM_XML_ANALYSIS,
        "Properties");
    public static final QName QNAME_MSXMLA_PROPERTYLIST = new QName(URN_SCHEMAS_MICROSOFT_COM_XML_ANALYSIS,
        "PropertyList");
    public static final QName QNAME_MSXMLA_EXECUTE = new QName(URN_SCHEMAS_MICROSOFT_COM_XML_ANALYSIS,
        "Execute");

    public static final QName QNAME_MSXMLA_COMMAND = new QName(null,
        "Command");
}
