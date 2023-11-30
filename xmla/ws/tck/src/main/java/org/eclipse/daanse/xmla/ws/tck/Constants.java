package org.eclipse.daanse.xmla.ws.tck;

import javax.xml.namespace.QName;

public class Constants {

    private Constants() {
        // constructor
    }

    public static final String XMLASERVICE_FILTER_KEY = "type";
    public static final String XMLASERVICE_FILTER_VALUE = "mock";
    @SuppressWarnings("java:S1075")
    public static final String WS_PATH = "/xmla";

    public static final String SERVER_PORT_WHITEBOARD = "8090";
    public static final String URN_SCHEMAS_MICROSOFT_COM_XML_ANALYSIS = "urn:schemas-microsoft-com:xml-analysis";

    public static final QName QNAME_Discover = new QName(URN_SCHEMAS_MICROSOFT_COM_XML_ANALYSIS, "Discover");
    public static final QName QNAME_RequestType = new QName(URN_SCHEMAS_MICROSOFT_COM_XML_ANALYSIS, "RequestType");
    public static final QName QNAME_Restrictions = new QName(URN_SCHEMAS_MICROSOFT_COM_XML_ANALYSIS, "Restrictions");
    public static final QName QNAME_RestrictionList = new QName(URN_SCHEMAS_MICROSOFT_COM_XML_ANALYSIS,
            "RestrictionList");
    public static final QName QNAME_Properties = new QName(URN_SCHEMAS_MICROSOFT_COM_XML_ANALYSIS, "Properties");
    public static final QName QNAME_PropertyList = new QName(URN_SCHEMAS_MICROSOFT_COM_XML_ANALYSIS, "PropertyList");
    public static final QName QNAME_DataSourceInfo = new QName(URN_SCHEMAS_MICROSOFT_COM_XML_ANALYSIS,
            "DataSourceInfo");
    public static final QName QNAME_Catalog = new QName(URN_SCHEMAS_MICROSOFT_COM_XML_ANALYSIS, "Catalog");
    public static final QName QNAME_Format = new QName(URN_SCHEMAS_MICROSOFT_COM_XML_ANALYSIS, "Format");
    public static final QName QNAME_Content = new QName(URN_SCHEMAS_MICROSOFT_COM_XML_ANALYSIS, "Content");

    public static final String SOAP_ENDPOINT_URL = "http://localhost:" + SERVER_PORT_WHITEBOARD + WS_PATH;
    public static final String SOAP_ACTION_DISCOVER = URN_SCHEMAS_MICROSOFT_COM_XML_ANALYSIS + ":Discover";
    public static final String SOAP_ACTION_EXECUTE = URN_SCHEMAS_MICROSOFT_COM_XML_ANALYSIS + ":Execute";
    public static final String PID_MS_SOAP = "org.eclipse.daanse.xmla.ws.jakarta.basic.MsXmlAnalysisSoap";
}
