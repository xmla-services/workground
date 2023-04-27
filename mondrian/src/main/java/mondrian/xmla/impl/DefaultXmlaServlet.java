/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2001-2005 Julian Hyde
// Copyright (C) 2005-2017 Hitachi Vantara and others
// Copyright (C) 2019 Topsoft
// All Rights Reserved.
*/
package mondrian.xmla.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.olap4j.impl.Olap4jUtil;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import mondrian.server.Session;
import mondrian.util.XmlParserFactoryProducer;
import mondrian.xmla.Enumeration;
import mondrian.xmla.SaxWriter;
import mondrian.xmla.XmlaConstants;
import mondrian.xmla.XmlaException;
import mondrian.xmla.XmlaRequest;
import mondrian.xmla.XmlaRequestCallback;
import mondrian.xmla.XmlaResponse;
import mondrian.xmla.XmlaServlet;
import mondrian.xmla.XmlaUtil;

/**
 * Default implementation of XML/A servlet.
 *
 * @author Gang Chen
 */
public abstract class DefaultXmlaServlet extends XmlaServlet {

    protected static final String NL = System.getProperty("line.separator");

    /**
     * Servlet config parameter that determines whether the xmla servlet
     * requires authenticated sessions.
     */
    private static final String REQUIRE_AUTHENTICATED_SESSIONS =
        "requireAuthenticatedSessions";
    public static final String INVALID_SOAP_MESSAGE_MORE_THAN_ONE_HEADER_ELEMENTS = "Invalid SOAP message: More than " +
        "one Header elements";
    public static final String INVALID_SOAP_MESSAGE_DOES_NOT_HAVE_ONE_BODY_ELEMENT = "Invalid SOAP message: Does not " +
        "have one Body element";
    public static final String INVALID_SOAP_MESSAGE_ENVELOPE_ELEMENT_NOT_IN_SOAP_NAMESPACE = "Invalid SOAP message: " +
        "Envelope element not in SOAP namespace";
    public static final String INVALID_SOAP_MESSAGE_TOP_ELEMENT_NOT_ENVELOPE = "Invalid SOAP message: Top element not" +
        " Envelope";

    private DocumentBuilderFactory domFactory = null;

    private boolean requireAuthenticatedSessions = false;

    /**
     * Session properties, keyed by session ID. Currently just username and
     * password.
     */
    private final Map<String, SessionInfo> sessionInfos =
        new HashMap<>();

    @Override
	public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        this.domFactory = getDocumentBuilderFactory();
        this.requireAuthenticatedSessions =
            Boolean.parseBoolean(
                servletConfig.getInitParameter(REQUIRE_AUTHENTICATED_SESSIONS));
    }

    protected static DocumentBuilderFactory getDocumentBuilderFactory() {
        DocumentBuilderFactory factory;
        try {
            factory = XmlParserFactoryProducer.createSecureDocBuilderFactory();
        } catch (ParserConfigurationException ex) {
            throw new XmlaException(
                SERVER_FAULT_FC,
                SIE_REQUEST_STATE_CODE,
                SIE_REQUEST_STATE_FAULT_FS,
                ex);
        }
        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(true);
        factory.setNamespaceAware(true);
        return factory;
    }

    @Override
	protected void unmarshallSoapMessage(
        HttpServletRequest request,
        Element[] requestSoapParts)
        throws XmlaException
    {
        try {
            InputStream inputStream;
            try {
                inputStream = request.getInputStream();
            } catch (IllegalStateException ex) {
                throw new XmlaException(
                    SERVER_FAULT_FC,
                    USM_REQUEST_STATE_CODE,
                    USM_REQUEST_STATE_FAULT_FS,
                    ex);
            } catch (IOException ex) {
                // This is either Client or Server
                throw new XmlaException(
                    SERVER_FAULT_FC,
                    USM_REQUEST_INPUT_CODE,
                    USM_REQUEST_INPUT_FAULT_FS,
                    ex);
            }

            DocumentBuilder domBuilder;
            try {
                domBuilder = domFactory.newDocumentBuilder();
            } catch (ParserConfigurationException ex) {
                throw new XmlaException(
                    SERVER_FAULT_FC,
                    USM_DOM_FACTORY_CODE,
                    USM_DOM_FACTORY_FAULT_FS,
                    ex);
            }

            Document soapDoc;
            try {
                soapDoc = domBuilder.parse(new InputSource(inputStream));
            } catch (IOException ex) {
                // This is either Client or Server
                throw new XmlaException(
                    SERVER_FAULT_FC,
                    USM_DOM_PARSE_IO_CODE,
                    USM_DOM_PARSE_IO_FAULT_FS,
                    ex);
            } catch (SAXException ex) {
                // Assume client passed bad xml
                throw new XmlaException(
                    CLIENT_FAULT_FC,
                    USM_DOM_PARSE_CODE,
                    USM_DOM_PARSE_FAULT_FS,
                    ex);
            }

            /* Check SOAP message */
            Element envElem = soapDoc.getDocumentElement();

            if (LOGGER.isDebugEnabled()) {
                logXmlaRequest(envElem);
            }

            if ("Envelope".equals(envElem.getLocalName())) {
                if (!(NS_SOAP_ENV_1_1.equals(envElem.getNamespaceURI()))) {
                    throw new XmlaException(
                        CLIENT_FAULT_FC,
                        USM_DOM_PARSE_CODE,
                        USM_DOM_PARSE_FAULT_FS,
                        new SAXException(
                            INVALID_SOAP_MESSAGE_ENVELOPE_ELEMENT_NOT_IN_SOAP_NAMESPACE));
                }
            } else {
                throw new XmlaException(
                    CLIENT_FAULT_FC,
                    USM_DOM_PARSE_CODE,
                    USM_DOM_PARSE_FAULT_FS,
                    new SAXException(
                        INVALID_SOAP_MESSAGE_TOP_ELEMENT_NOT_ENVELOPE));
            }

            Element[] childs =
                XmlaUtil.filterChildElements(
                    envElem, NS_SOAP_ENV_1_1, "Header");
            if (childs.length > 1) {
                throw new XmlaException(
                    CLIENT_FAULT_FC,
                    USM_DOM_PARSE_CODE,
                    USM_DOM_PARSE_FAULT_FS,
                    new SAXException(
                        INVALID_SOAP_MESSAGE_MORE_THAN_ONE_HEADER_ELEMENTS));
            }
            requestSoapParts[0] = childs.length == 1 ? childs[0] : null;

            childs =
                XmlaUtil.filterChildElements(envElem, NS_SOAP_ENV_1_1, "Body");
            if (childs.length != 1) {
                throw new XmlaException(
                    CLIENT_FAULT_FC,
                    USM_DOM_PARSE_CODE,
                    USM_DOM_PARSE_FAULT_FS,
                    new SAXException(
                        INVALID_SOAP_MESSAGE_DOES_NOT_HAVE_ONE_BODY_ELEMENT));
            }
            requestSoapParts[1] = childs[0];
        } catch (XmlaException xex) {
            throw xex;
        } catch (Exception ex) {
            throw new XmlaException(
                SERVER_FAULT_FC,
                USM_UNKNOWN_CODE,
                USM_UNKNOWN_FAULT_FS,
                ex);
        }
    }

    protected void logXmlaRequest(Element envElem) {
        final StringWriter writer = new StringWriter();
        writer.write("XML/A request content");
        writer.write(NL);
        XmlaUtil.element2Text(envElem, writer);
        LOGGER.debug(writer.toString());
    }

    /**
     * {@inheritDoc}
     *
     * <p>See if there is a "mustUnderstand" header element.
     * If there is a BeginSession element, then generate a session id and
     * add to context Map.</p>
     *
     * <p>Excel 2000 and Excel XP generate both a BeginSession, Session and
     * EndSession mustUnderstand=1
     * in the "urn:schemas-microsoft-com:xml-analysis" namespace
     * Header elements and a NamespaceCompatibility mustUnderstand=0
     * in the "http://schemas.microsoft.com/analysisservices/2003/xmla"
     * namespace. Here we handle only the session Header elements.
     *
     * <p>We also handle the Security element.</p>
     */
    @Override
	protected void handleSoapHeader(
        HttpServletResponse response,
        Element[] requestSoapParts,
        byte[][] responseSoapParts,
        Map<String, Object> context) throws XmlaException
    {
        try {
            Element hdrElem = requestSoapParts[0];

            String encoding = response.getCharacterEncoding();

            byte[] bytes = null;

            boolean authenticatedSession = false;
            boolean beginSession = false;
            String sessionIdStr = null;
            if (hdrElem != null && hdrElem.hasChildNodes()) {

                NodeList nlst = hdrElem.getChildNodes();
                int nlen = nlst.getLength();

                for (int i = 0; i < nlen; i++) {
                    Node n = nlst.item(i);
                    if (!(n instanceof Element e)) {
                        continue;
                    }
                    String localName = e.getLocalName();

                    if (localName.equals(XMLA_SECURITY)
                            && NS_SOAP_SECEXT.equals(e.getNamespaceURI())) {
                        // Example:
                        //
                        // <Security xmlns="http://schemas.xmlsoap.org/ws/2002/04/secext">
                        //   <UsernameToken>
                        //     <Username>MICHELE</Username>
                        //     <Password Type="PasswordText">ROSSI</Password>
                        //   </UsernameToken>
                        // </Security>
                        // <BeginSession mustUnderstand="1"
                        //   xmlns="urn:schemas-microsoft-com:xml-analysis" />
                        NodeList childNodes = e.getChildNodes();
                        Element userNameToken = (Element) childNodes.item(1);
                        NodeList userNamePassword = userNameToken.getChildNodes();
                        Element username = (Element) userNamePassword.item(1);
                        Element password = (Element) userNamePassword.item(3);
                        String userNameStr =
                                username.getChildNodes().item(0).getNodeValue();
                        context.put(CONTEXT_XMLA_USERNAME, userNameStr);
                        String passwordStr = "";

                        if (password.getChildNodes().item(0) != null) {
                            passwordStr =
                                    password.getChildNodes().item(0).getNodeValue();
                        }

                        context.put(CONTEXT_XMLA_PASSWORD, passwordStr);

                        if ("".equals(passwordStr) || null == passwordStr) {
                            LOGGER.warn(
                                    "Security header for user [{}] provided without password", userNameStr);
                        }
                        authenticatedSession = true;
                        continue;
                    }

                    // Make sure Element has mustUnderstand=1 attribute.
                    Attr attr = e.getAttributeNode(SOAP_MUST_UNDERSTAND_ATTR);
                    if(attr != null) {
                        boolean mustUnderstandValue =
                                        attr.getValue() != null
                                        && attr.getValue().equals("1");

                        if (!mustUnderstandValue) {
                            continue;
                        }
                    }

                    // Is it an XMLA element
                    if (!NS_XMLA.equals(e.getNamespaceURI())) {
                        continue;
                    }
                    // So, an XMLA mustUnderstand-er
                    // Do we know what to do with it
                    // We understand:
                    //    BeginSession
                    //    Session
                    //    EndSession

                    if (localName.equals(XMLA_BEGIN_SESSION)) {
                        sessionIdStr = generateSessionId(context);

                        context.put(CONTEXT_XMLA_SESSION_ID, sessionIdStr);
                        context.put(
                                CONTEXT_XMLA_SESSION_STATE,
                                CONTEXT_XMLA_SESSION_STATE_BEGIN);

                        Session.create(sessionIdStr);

                    } else if (localName.equals(XMLA_SESSION)) {
                        sessionIdStr = getSessionIdFromRequest(e);

                        Session.get(sessionIdStr);
                        Session.checkIn(sessionIdStr);

                        SessionInfo sessionInfo = null;
                        if (authenticatedSession) {
                            sessionInfo = getSessionInfo(sessionIdStr);
                        }

                        if (sessionInfo != null) {
                            context.put(CONTEXT_XMLA_USERNAME, sessionInfo.user);
                            context.put(
                                    CONTEXT_XMLA_PASSWORD,
                                    sessionInfo.password);
                        }

                        context.put(CONTEXT_XMLA_SESSION_ID, sessionIdStr);
                        context.put(
                                CONTEXT_XMLA_SESSION_STATE,
                                CONTEXT_XMLA_SESSION_STATE_WITHIN);


                    } else if (localName.equals(XMLA_END_SESSION)) {
                        sessionIdStr = getSessionIdFromRequest(e);
                        context.put(CONTEXT_XMLA_SESSION_ID, sessionIdStr);
                        context.put(
                                CONTEXT_XMLA_SESSION_STATE,
                                CONTEXT_XMLA_SESSION_STATE_END);
                        // Session is closed in XmlaServlet doPost
                    } else {
                        // error
                        String msg =
                            new StringBuilder("Invalid XML/A message: Unknown ")
                                .append("\"mustUnderstand\" XMLA Header element \"")
                                .append(localName)
                                .append("\"").toString();
                        throw new XmlaException(
                            MUST_UNDERSTAND_FAULT_FC,
                            HSH_MUST_UNDERSTAND_CODE,
                            HSH_MUST_UNDERSTAND_FAULT_FS,
                            new RuntimeException(msg));
                    }

                    if (sessionIdStr != null) {
                        StringBuilder buf = new StringBuilder(100);
                        buf.append("<Session ");
                        buf.append(XMLA_SESSION_ID);
                        buf.append("=\"");
                        buf.append(sessionIdStr);
                        buf.append("\" ");
                        buf.append("xmlns=\"");
                        buf.append(NS_XMLA);
                        buf.append("\" />");
                        bytes = buf.toString().getBytes(encoding);
                    }

                    if (authenticatedSession) {
                        String username =
                                (String) context.get(CONTEXT_XMLA_USERNAME);
                        String password =
                                (String) context.get(CONTEXT_XMLA_PASSWORD);
                        String sessionId =
                                (String) context.get(CONTEXT_XMLA_SESSION_ID);

                        LOGGER.debug(
                                "New authenticated session; storing credentials [{}/********] for session id [{}]",
                            username, sessionId);

                        saveSessionInfo(
                                username,
                                password,
                                sessionId);
                    } else {
                        if (beginSession && requireAuthenticatedSessions) {
                            throw new XmlaException(
                                    XmlaConstants.CLIENT_FAULT_FC,
                                    XmlaConstants.CHH_AUTHORIZATION_CODE,
                                    XmlaConstants.CHH_AUTHORIZATION_FAULT_FS,
                                    new Exception("Session Credentials NOT PROVIDED"));
                        }
                    }
                }
            }

            if(sessionIdStr == null) {
                // Request lifetime session
                // Session is closed in XmlaServlet doPost
                String lifetimeSessionId = generateSessionId(context);
                context.put(CONTEXT_XMLA_SESSION_ID, lifetimeSessionId);
                Session.create(lifetimeSessionId);
                context.put(
                        CONTEXT_XMLA_SESSION_STATE,
                        CONTEXT_XMLA_SESSION_STATE_END);
            }

            responseSoapParts[0] = bytes;
        } catch (XmlaException xex) {
            throw xex;
        } catch (Exception ex) {
            throw new XmlaException(
                SERVER_FAULT_FC,
                HSH_UNKNOWN_CODE,
                HSH_UNKNOWN_FAULT_FS,
                ex);
        }
    }


    protected String generateSessionId(Map<String, Object> context) {
        for (XmlaRequestCallback callback : getCallbacks()) {
            final String sessionId = callback.generateSessionId(context);
            if (sessionId != null) {
                return sessionId;
            }
        }


        // Generate a pseudo-random new session ID.
        return Long.toString(
            17L * System.nanoTime()
            + 3L * System.currentTimeMillis(), 35);
    }


    private static String getSessionIdFromRequest(
        Element e)
        throws Exception
    {
        // extract the SessionId attrs value and put into context
        Attr attr = e.getAttributeNode(XMLA_SESSION_ID);
        if (attr == null) {
            throw new SAXException(
                new StringBuilder("Invalid XML/A message: ")
                    .append(XMLA_SESSION)
                    .append(" Header element with no ")
                    .append(XMLA_SESSION_ID)
                    .append(" attribute").toString());
        }

        String sessionId = attr.getValue();
        if (sessionId == null) {
            throw new SAXException(
                new StringBuilder("Invalid XML/A message: ")
                    .append(XMLA_SESSION)
                    .append(" Header element with ")
                    .append(XMLA_SESSION_ID)
                    .append(" attribute but no attribute value").toString());
        }
        return sessionId;
    }

    @Override
	protected void handleSoapBody(
        HttpServletResponse response,
        Element[] requestSoapParts,
        byte[][] responseSoapParts,
        Map<String, Object> context)
        throws XmlaException
    {
        try {
            String encoding = response.getCharacterEncoding();
            Element hdrElem = requestSoapParts[0]; // not used
            Element bodyElem = requestSoapParts[1];
            Element[] dreqs =
                XmlaUtil.filterChildElements(bodyElem, NS_XMLA, "Discover");
            Element[] ereqs =
                XmlaUtil.filterChildElements(bodyElem, NS_XMLA, "Execute");
            if (dreqs.length + ereqs.length != 1) {
                throw new XmlaException(
                    CLIENT_FAULT_FC,
                    HSB_BAD_SOAP_BODY_CODE,
                    HSB_BAD_SOAP_BODY_FAULT_FS,
                    new RuntimeException(
                        new StringBuilder("Invalid XML/A message: Body has ")
                            .append(dreqs.length).append(" Discover Requests and ")
                            .append(ereqs.length).append(" Execute Requests").toString()));
            }

            Element xmlaReqElem = (dreqs.length == 0 ? ereqs[0] : dreqs[0]);

            ByteArrayOutputStream osBuf = new ByteArrayOutputStream();

            // use context variable 'role_name' as this request's XML/A role
            String roleName = (String) context.get(CONTEXT_ROLE_NAME);

            String username = (String) context.get(CONTEXT_XMLA_USERNAME);
            String password = (String) context.get(CONTEXT_XMLA_PASSWORD);
            String sessionId = (String) context.get(CONTEXT_XMLA_SESSION_ID);
            XmlaRequest xmlaReq =
                new DefaultXmlaRequest(
                    xmlaReqElem, roleName, username, password, sessionId);

            // "ResponseMimeType" may be in the context if the "Accept" HTTP
            // header was specified. But override if the SOAP request has the
            // "ResponseMimeType" property.
            Enumeration.ResponseMimeType responseMimeType =
                Enumeration.ResponseMimeType.SOAP;
            final String responseMimeTypeName =
                xmlaReq.getProperties().get("ResponseMimeType");
            if (responseMimeTypeName != null) {
                responseMimeType =
                    Enumeration.ResponseMimeType.MAP.get(
                        responseMimeTypeName);
                if (responseMimeType != null) {
                    context.put(CONTEXT_MIME_TYPE, responseMimeType);
                }
            }

            XmlaResponse xmlaRes =
                new DefaultXmlaResponse(osBuf, encoding, responseMimeType);

            try {
                getXmlaHandler().process(xmlaReq, xmlaRes);
            } catch (XmlaException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new XmlaException(
                    SERVER_FAULT_FC,
                    HSB_PROCESS_CODE,
                    HSB_PROCESS_FAULT_FS,
                    ex);
            }

            responseSoapParts[1] = osBuf.toByteArray();
        } catch (XmlaException xex) {
            throw xex;
        } catch (Exception ex) {
            throw new XmlaException(
                SERVER_FAULT_FC,
                HSB_UNKNOWN_CODE,
                HSB_UNKNOWN_FAULT_FS,
                ex);
        }
    }

    @Override
	protected void marshallSoapMessage(
        HttpServletResponse response,
        byte[][] responseSoapParts,
        Enumeration.ResponseMimeType responseMimeType)
    {
        try {
            // If CharacterEncoding was set in web.xml, use this value
            String encoding =
                (charEncoding != null)
                    ? charEncoding
                    : response.getCharacterEncoding();

            // Since we just reset response, encoding and content-type were
            // reset too
            if (charEncoding != null) {
                response.setCharacterEncoding(charEncoding);
            }
            switch (responseMimeType) {
            case JSON:
                response.setContentType("application/json");
                break;
            case SOAP:
            default:
                response.setContentType("text/xml");
                break;
            }

             // The setCharacterEncoding, setContentType, or setLocale method
             // must be called BEFORE getWriter or getOutputStream and before
             // committing the response for the character encoding to be used.
             //
             // @see javax.servlet.ServletResponse
            OutputStream outputStream = response.getOutputStream();


            byte[] soapHeader = responseSoapParts[0];
            byte[] soapBody = responseSoapParts[1];

            Object[] byteChunks = null;

            try {
                switch (responseMimeType) {
                case JSON:
                    byteChunks = new Object[] {
                        soapBody,
                    };
                    break;

                case SOAP:
                default:
                    String s0 =
                        new StringBuilder("<?xml version=\"1.0\" encoding=\"").append(encoding)
                            .append("\"?>\n<" + SOAP_PREFIX).append(":Envelope xmlns:")
                            .append(SOAP_PREFIX).append("=\"").append(NS_SOAP_ENV_1_1).append("\" ")
                            .append(SOAP_PREFIX).append(":encodingStyle=\"")
                            .append(NS_SOAP_ENC_1_1).append("\" >").append("\n<" + SOAP_PREFIX)
                            .append(":Header>\n").toString();
                    String s2 =
                        new StringBuilder("</").append(SOAP_PREFIX).append(":Header>\n<").append(SOAP_PREFIX)
                            .append(":Body>\n").toString();
                    String s4 =
                        new StringBuilder("\n</").append(SOAP_PREFIX).append(":Body>\n</").append(SOAP_PREFIX)
                            .append(":Envelope>\n").toString();

                    byteChunks = new Object[] {
                        s0.getBytes(encoding),
                        soapHeader,
                        s2.getBytes(encoding),
                        soapBody,
                        s4.getBytes(encoding),
                    };
                    break;
                }
            } catch (UnsupportedEncodingException uee) {
                LOGGER.warn(
                    "This should be handled at begin of processing request",
                    uee);
            }

            if (LOGGER.isDebugEnabled()) {
                StringBuilder buf = new StringBuilder(100);
                buf.append("XML/A response content").append(NL);
                try {
                    for (Object byteChunk : byteChunks) {
                        byte[] chunk = (byte[]) byteChunk;
                        if (chunk != null && chunk.length > 0) {
                            buf.append(new String(chunk, encoding));
                        }
                    }
                } catch (UnsupportedEncodingException uee) {
                    LOGGER.warn(
                        "This should be handled at begin of processing request",
                        uee);
                }
                LOGGER.debug(buf.toString());
            }

            if (LOGGER.isDebugEnabled()) {
                StringBuilder buf = new StringBuilder();
                buf.append("XML/A response content").append(NL);
            }
            try {
                int bufferSize = 4096;
                ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
                WritableByteChannel wch = Channels.newChannel(outputStream);
                ReadableByteChannel rch;
                for (Object byteChunk : byteChunks) {
                    if (byteChunk == null || ((byte[]) byteChunk).length == 0) {
                        continue;
                    }
                    rch = Channels.newChannel(
                        new ByteArrayInputStream((byte[]) byteChunk));

                    int readSize;
                    do {
                        buffer.clear();
                        readSize = rch.read(buffer);
                        buffer.flip();

                        int writeSize = 0;
                        while ((writeSize += wch.write(buffer)) < readSize) {
                        }
                    } while (readSize == bufferSize);
                    rch.close();
                }
                outputStream.flush();
            } catch (IOException ioe) {
                LOGGER.warn(
                    "Exception when transferring bytes over sockets",
                    ioe);
            }
        } catch (XmlaException xex) {
            throw xex;
        } catch (Exception ex) {
            throw new XmlaException(
                SERVER_FAULT_FC,
                MSM_UNKNOWN_CODE,
                MSM_UNKNOWN_FAULT_FS,
                ex);
        }
    }

    /**
     * This produces a SOAP 1.1 version Fault element - not a 1.2 version.
     *
     */
    @Override
	protected void handleFault(
        HttpServletResponse response,
        byte[][] responseSoapParts,
        Phase phase,
        Throwable t)
    {
        // Regardless of whats been put into the response so far, clear
        // it out.
        // If we just reset we will lose headers
        // For example these
        // Access-Control-Allow-Origin
        // Access-Control-Allow-Credentials
        //response.reset();

        // NOTE: if you can think of better/other status codes to use
        // for the various phases, please make changes.
        // I think that XMLA faults always returns OK.
        switch (phase) {
        case VALIDATE_HTTP_HEAD:
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            break;
        case INITIAL_PARSE:
        case CALLBACK_PRE_ACTION:
        case PROCESS_HEADER:
        case PROCESS_BODY:
        case CALLBACK_POST_ACTION:
        case SEND_RESPONSE:
            response.setStatus(HttpServletResponse.SC_OK);
            break;
        }

        String code;
        String faultCode;
        String faultString;
        String detail;
        if (t instanceof XmlaException xex) {
            code = xex.getCode();
            faultString = new StringBuilder(xex.getFaultString()).append(" ").append(xex.getDetail()).toString();
            faultCode = XmlaException.formatFaultCode(xex);
            detail = XmlaException.formatDetail(xex.getDetail());

        } else {
            // some unexpected Throwable
            t = XmlaException.getRootCause(t);
            code = UNKNOWN_ERROR_CODE;
            faultString = new StringBuilder(UNKNOWN_ERROR_FAULT_FS).append(" ").append(t.getMessage()).toString();
            faultCode = XmlaException.formatFaultCode(
                SERVER_FAULT_FC, code);
            detail = XmlaException.formatDetail(t.getMessage());
        }

        String encoding = response.getCharacterEncoding();

        ByteArrayOutputStream osBuf = new ByteArrayOutputStream();
        try {
            SaxWriter writer = new DefaultSaxWriter(osBuf, encoding);
            writer.startDocument();
            writer.startElement(new StringBuilder(SOAP_PREFIX).append(":Fault").toString());

            // The faultcode element is intended for use by software to provide
            // an algorithmic mechanism for identifying the fault. The faultcode
            // MUST be present in a SOAP Fault element and the faultcode value
            // MUST be a qualified name
            writer.startElement("faultcode");
            writer.characters(faultCode);
            writer.endElement();

            // The faultstring element is intended to provide a human readable
            // explanation of the fault and is not intended for algorithmic
            // processing.
            writer.startElement("faultstring");
            writer.characters(faultString);
            writer.endElement();

            // The faultactor element is intended to provide information about
            // who caused the fault to happen within the message path
            writer.startElement("faultactor");
            writer.characters(FAULT_ACTOR);
            writer.endElement();

            // The detail element is intended for carrying application specific
            // error information related to the Body element. It MUST be present
            // if the contents of the Body element could not be successfully
            // processed. It MUST NOT be used to carry information about error
            // information belonging to header entries. Detailed error
            // information belonging to header entries MUST be carried within
            // header entries.

            //code must be unsigned type
            //HSH_UNKNOWN_CODE
            switch (code) {
                case  ("00HSBE02"): // The connection either timed out or was lost.
                case ("00HSHU01"): // The connection either timed out or was lost.
                case ("0xc10c000a"): // Session with id  does not exist
                    code = "3238789130";
                    break;
                default:
                    code = "3238658121";
                    break;
            }
            writer.startElement("detail");
            writer.startElement("Error",
                    "ErrorCode", code,
                    "Description", detail);
            writer.endElement(); // error
            writer.endElement(); // detail

//            if (phase != Phase.PROCESS_HEADER) {
//                boolean  canConvertUnsignedInt;
//                try {
//                    Integer.parseUnsignedInt(code);
//                    canConvertUnsignedInt = true;
//                }
//                catch(Exception ex) {
//                    canConvertUnsignedInt = false;
//                }
//                if(canConvertUnsignedInt) {
//                    //code must be unsigned type
//                    //code = "3238658121";
//                    writer.startElement("detail");
//                    writer.startElement("Error",
//                            "ErrorCode", code,
//                            "Description", detail);
//                    writer.endElement(); // error
//                    writer.endElement(); // detail
//                }
//                else {
//                    writer.startElement("detail");
//                    writer.startElement(
//                            FAULT_NS_PREFIX + ":error",
//                            "xmlns:" + FAULT_NS_PREFIX, MONDRIAN_NAMESPACE);
//                    writer.startElement("code");
//                    writer.characters(code);
//                    writer.endElement(); // code
//                    writer.startElement("desc");
//                    writer.characters(detail);
//                    writer.endElement(); // desc
//                    writer.endElement(); // error
//                    writer.endElement(); // detail
//                }
//            }

            writer.endElement();   // </Fault>
            writer.endDocument();
        } catch (UnsupportedEncodingException uee) {
            LOGGER.warn(
                "This should be handled at begin of processing request",
                uee);
        } catch (Exception e) {
            LOGGER.error(
                "Unexcepted runimt exception when handing SOAP fault :(");
        }

        responseSoapParts[1] = osBuf.toByteArray();
    }

    private SessionInfo getSessionInfo(String sessionId) {
        if (sessionId == null) {
            return null;
        }

        SessionInfo sessionInfo = null;

        synchronized (sessionInfos) {
            sessionInfo = sessionInfos.get(sessionId);
        }

        if (sessionInfo == null) {
            LOGGER.error(
                "No login credentials for found for session [{}]", sessionId);
        } else {
            LOGGER.debug(
                "Found credentials for session id [{}], username=[{}] in servlet cache", sessionId, sessionInfo.user);
        }
        return sessionInfo;
    }

    private SessionInfo saveSessionInfo(
        String username,
        String password,
        String sessionId)
    {
        synchronized (sessionInfos) {
            SessionInfo sessionInfo = sessionInfos.get(sessionId);
            if (sessionInfo != null
                && Olap4jUtil.equal(sessionInfo.user, username))
            {
                // Overwrite the password, but only if it is non-empty.
                // (Sometimes Simba sends the credentials object again
                // but without a password.)
                if (password != null && password.length() > 0) {
                    sessionInfo =
                        new SessionInfo(username, password);
                    sessionInfos.put(sessionId, sessionInfo);
                }
            } else {
                // A credentials object was stored against the provided session
                // ID but the username didn't match, so create a new holder.
                sessionInfo = new SessionInfo(username, password);
                sessionInfos.put(sessionId, sessionInfo);
            }
            return sessionInfo;
        }
    }
    /**
     * Holds authentication credentials of a XMLA session.
     */
    private static class SessionInfo {
        final String user;
        final String password;

        public SessionInfo(String user, String password)
        {
            this.user = user;
            this.password = password;
        }
    }
}
