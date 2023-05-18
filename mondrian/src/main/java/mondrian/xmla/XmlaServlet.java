/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2003-2005 Julian Hyde
// Copyright (C) 2005-2017 Hitachi Vantara
// All Rights Reserved.
*/

package mondrian.xmla;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base XML/A servlet.
 *
 * @author Gang Chen
 * @since December, 2005
 */
public abstract class XmlaServlet
    extends HttpServlet
    implements XmlaConstants
{

    protected static final Logger LOGGER = LoggerFactory.getLogger(XmlaServlet.class);

    public static final String PARAM_DATASOURCES_CONFIG = "DataSourcesConfig";
    public static final String PARAM_OPTIONAL_DATASOURCE_CONFIG =
        "OptionalDataSourceConfig";
    public static final String PARAM_CHAR_ENCODING = "CharacterEncoding";
    public static final String PARAM_CALLBACKS = "Callbacks";
    private static final String ERRORS_WHEN_HANDLING_XML_A_MESSAGE = "Errors when handling XML/A message";

    protected static XmlaHandler xmlaHandler = null;
    protected static String charEncoding = null;
    private final List<XmlaRequestCallback> callbackList =
        new ArrayList<>();

    private static XmlaHandler.ConnectionFactory connectionFactory;

    public enum Phase {
        VALIDATE_HTTP_HEAD,
        INITIAL_PARSE,
        CALLBACK_PRE_ACTION,
        PROCESS_HEADER,
        PROCESS_BODY,
        CALLBACK_POST_ACTION,
        SEND_RESPONSE,
        SEND_ERROR
    }

    /**
     * Returns true if paramName's value is not null and 'true'.
     */
    public static boolean getBooleanInitParameter(
        ServletConfig servletConfig,
        String paramName)
    {
        String paramValue = servletConfig.getInitParameter(paramName);
        return paramValue != null && Boolean.valueOf(paramValue);
    }

    public static boolean getParameter(
        HttpServletRequest req,
        String paramName)
    {
        String paramValue = req.getParameter(paramName);
        return paramValue != null && Boolean.valueOf(paramValue);
    }

    protected XmlaServlet() {
        LOGGER.info("Application working directory: \"{}\"",
            new java.io.File(".").getAbsolutePath());
    }


    /**
     * Initializes servlet and XML/A handler.
     *
     */
    @Override
	public void init(ServletConfig servletConfig)
        throws ServletException
    {
        super.init(servletConfig);

        // init: charEncoding
        initCharEncodingHandler(servletConfig);

        // init: callbacks
        initCallbacks(servletConfig);

        connectionFactory = createConnectionFactory(servletConfig);
    }

    protected abstract XmlaHandler.ConnectionFactory createConnectionFactory(
        ServletConfig servletConfig)
        throws ServletException;

    /**
     * Gets (creating if needed) the XmlaHandler.
     *
     * @return XMLA handler
     */
    protected XmlaHandler getXmlaHandler() {
        if (xmlaHandler == null) {
            xmlaHandler =
                new XmlaHandler(
                    connectionFactory,
                    "cxmla");
        }
        return this.xmlaHandler;
    }

    /**
     * Registers a callback.
     */
    protected final void addCallback(XmlaRequestCallback callback) {
        callbackList.add(callback);
    }

    /**
     * Returns the list of callbacks. The list is immutable.
     *
     * @return list of callbacks
     */
    protected final List<XmlaRequestCallback> getCallbacks() {
        return Collections.unmodifiableList(callbackList);
    }

    /**
     * Main entry for HTTP post method
     *
     */
    @Override
	protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response)
    {
        // Request Soap Header and Body
        // header [0] and body [1]
        Element[] requestSoapParts = new Element[2];

        // Response Soap Header and Body
        // An array allows response parts to be passed into callback
        // and possible modifications returned.
        // response header in [0] and response body in [1]
        byte[][] responseSoapParts = new byte[2][];

        Phase phase = Phase.VALIDATE_HTTP_HEAD;
        Enumeration.ResponseMimeType mimeType =
            Enumeration.ResponseMimeType.SOAP;

        try {

            encodeRequestResponse(request, response);

            response.setContentType(mimeType.getMimeType());

            Map<String, Object> context = new HashMap<>();

            if (validateHttpHeaderCallbacks(request, response, context, responseSoapParts, phase, mimeType)) {
                return;
            }


            phase = Phase.INITIAL_PARSE;

            unmarshallingSoapMessage(request,  response,  context, responseSoapParts, phase, mimeType, requestSoapParts);

            phase = Phase.PROCESS_HEADER;
            handlingXmlaMessageHeader(response, requestSoapParts, responseSoapParts, context, phase, mimeType);

            phase = Phase.CALLBACK_PRE_ACTION;

            callbacksPreAction(request, response, requestSoapParts, responseSoapParts, context, phase, mimeType);


            phase = Phase.PROCESS_BODY;
            handlingXmlaMessageBody(response, requestSoapParts, responseSoapParts, context, phase, mimeType);

            mimeType =
                (Enumeration.ResponseMimeType) context.get(CONTEXT_MIME_TYPE);

            phase = Phase.CALLBACK_POST_ACTION;
            invokingCallbacksPostAction(request, response, responseSoapParts, context, phase, mimeType);


            phase = Phase.SEND_RESPONSE;

            invokingSendResponseAction(response, responseSoapParts, phase, mimeType);

            String sessionId = (String)context.get(CONTEXT_XMLA_SESSION_ID);
            if(sessionId != null) {
                if(((String)context.get(CONTEXT_XMLA_SESSION_STATE)).equals(CONTEXT_XMLA_SESSION_STATE_END)) {
                    mondrian.server.Session.close(sessionId);
                }
                else {
                    mondrian.server.Session.checkIn(sessionId);
                }
            }
        } catch (XmlaServletException t) {
            LOGGER.error("Unknown Error when handling XML/A message");
        } catch (Exception t) {
            LOGGER.error("Unknown Error when handling XML/A message", t);
            handleFault(response, responseSoapParts, phase, t);
            marshallSoapMessage(response, responseSoapParts, mimeType);
        }
    }

    private void invokingSendResponseAction(HttpServletResponse response,
                                            byte[][] responseSoapParts,
                                            Phase phase, Enumeration.ResponseMimeType mimeType) {
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            marshallSoapMessage(response, responseSoapParts, mimeType);
        } catch (XmlaException xex) {
            LOGGER.error(ERRORS_WHEN_HANDLING_XML_A_MESSAGE, xex);
            handleFault(response, responseSoapParts, phase, xex);
            marshallSoapMessage(response, responseSoapParts, mimeType);
        }

    }

    private void invokingCallbacksPostAction(HttpServletRequest request, HttpServletResponse response,
                                             byte[][] responseSoapParts, Map<String, Object> context,
                                             Phase phase, Enumeration.ResponseMimeType mimeType) {
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Invoking callbacks postAction");
            }

            for (XmlaRequestCallback callback : getCallbacks()) {
                callback.postAction(
                    request, response,
                    responseSoapParts, context);
            }
        } catch (XmlaException xex) {
            LOGGER.error("Errors when invoking callbacks postaction", xex);
            handleFault(response, responseSoapParts, phase, xex);
            marshallSoapMessage(response, responseSoapParts, mimeType);
            throw new XmlaServletException();
        } catch (Exception ex) {
            LOGGER.error("Errors when invoking callbacks postaction", ex);
            handleFault(
                response,
                responseSoapParts,
                phase,
                new XmlaException(
                    SERVER_FAULT_FC,
                    CPOSTA_CODE,
                    CPOSTA_FAULT_FS,
                    ex));
            marshallSoapMessage(response, responseSoapParts, mimeType);
            throw new XmlaServletException();
        }
    }

    private void handlingXmlaMessageBody(HttpServletResponse response,
                                         Element[] requestSoapParts, byte[][] responseSoapParts,
                                         Map<String, Object> context, Phase phase, Enumeration.ResponseMimeType mimeType){
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Handling XML/A message body");
            }

            // process XML/A request
            handleSoapBody(
                response,
                requestSoapParts,
                responseSoapParts,
                context);
        } catch (XmlaException xex) {
            LOGGER.error(ERRORS_WHEN_HANDLING_XML_A_MESSAGE, xex);
            handleFault(response, responseSoapParts, phase, xex);
            marshallSoapMessage(response, responseSoapParts, mimeType);
            throw new XmlaServletException();
        }
    }

    private void callbacksPreAction(HttpServletRequest request, HttpServletResponse response,
                                    Element[] requestSoapParts, byte[][] responseSoapParts,
                                    Map<String, Object> context, Phase phase, Enumeration.ResponseMimeType mimeType) {
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Invoking callbacks preAction");
            }

            for (XmlaRequestCallback callback : getCallbacks()) {
                callback.preAction(request, requestSoapParts, context);
            }
        } catch (XmlaException xex) {
            LOGGER.error("Errors when invoking callbacks preaction", xex);
            handleFault(response, responseSoapParts, phase, xex);
            marshallSoapMessage(response, responseSoapParts, mimeType);
            throw new XmlaServletException();
        } catch (Exception ex) {
            LOGGER.error("Errors when invoking callbacks preaction", ex);
            handleFault(
                response, responseSoapParts,
                phase,
                new XmlaException(
                    SERVER_FAULT_FC,
                    CPREA_CODE,
                    CPREA_FAULT_FS,
                    ex));
            marshallSoapMessage(response, responseSoapParts, mimeType);
            throw new XmlaServletException();
        }
    }

    private void handlingXmlaMessageHeader(HttpServletResponse response, Element[] requestSoapParts,
                                           byte[][] responseSoapParts, Map<String, Object> context,
                                           Phase phase, Enumeration.ResponseMimeType mimeType) {
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Handling XML/A message header");
            }

            // process application specified SOAP header here
            handleSoapHeader(
                response,
                requestSoapParts,
                responseSoapParts,
                context);
        } catch (XmlaException xex) {
            LOGGER.error(ERRORS_WHEN_HANDLING_XML_A_MESSAGE, xex);
            handleFault(response, responseSoapParts, phase, xex);
            marshallSoapMessage(response, responseSoapParts, mimeType);
            throw new XmlaServletException();
        }
    }

    private void unmarshallingSoapMessage(
        HttpServletRequest request, HttpServletResponse response,
        Map<String, Object> context, byte[][] responseSoapParts,
        Phase phase, Enumeration.ResponseMimeType mimeType, Element[] requestSoapParts) {
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Unmarshalling SOAP message");
            }

            // check request's content type
            String contentType = request.getContentType();
            if (contentType == null
                || !contentType.contains("text/xml"))
            {
                throw new IllegalArgumentException(
                    new StringBuilder("Only accepts content type 'text/xml', not '")
                        .append(contentType).append("'").toString());
            }

            // are they asking for a JSON response?
            String accept = request.getHeader("Accept");
            if (accept != null) {
                mimeType = XmlaUtil.chooseResponseMimeType(accept);
                if (mimeType == null) {
                    throw new IllegalArgumentException(
                        new StringBuilder("Accept header '").append(accept).append("' is not a supported")
                            .append(" response content type. Allowed values:")
                            .append(" text/xml, application/xml, application/json.").toString());
                }
                if (mimeType != Enumeration.ResponseMimeType.SOAP) {
                    response.setContentType(mimeType.getMimeType());
                }
            }
            context.put(CONTEXT_MIME_TYPE, mimeType);

            unmarshallSoapMessage(request, requestSoapParts);
        } catch (XmlaException xex) {
            LOGGER.error("Unable to unmarshall SOAP message", xex);
            handleFault(response, responseSoapParts, phase, xex);
            marshallSoapMessage(response, responseSoapParts, mimeType);
            throw new XmlaServletException();
        }
    }

    private boolean validateHttpHeaderCallbacks(
        HttpServletRequest request, HttpServletResponse response, Map<String, Object> context, byte[][] responseSoapParts, Phase phase, Enumeration.ResponseMimeType mimeType) {
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Invoking validate http header callbacks");
            }
            for (XmlaRequestCallback callback : getCallbacks()) {
                if (!callback.processHttpHeader(
                    request,
                    response,
                    context))
                {
                    return true;
                }
            }
            return false;
        } catch (XmlaException xex) {
            LOGGER.error(
                "Errors when invoking callbacks validateHttpHeader", xex);
            handleFault(response, responseSoapParts, phase, xex);
            marshallSoapMessage(response, responseSoapParts, mimeType);
            throw new XmlaServletException();
        } catch (Exception ex) {
            LOGGER.error(
                "Errors when invoking callbacks validateHttpHeader", ex);
            handleFault(
                response, responseSoapParts,
                phase,
                new XmlaException(
                    SERVER_FAULT_FC,
                    CHH_CODE,
                    CHH_FAULT_FS,
                    ex));
            marshallSoapMessage(response, responseSoapParts, mimeType);
            throw new XmlaServletException();
        }
    }

    private static void encodeRequestResponse(HttpServletRequest request, HttpServletResponse response) {
        if (charEncoding != null) {
            try {
                request.setCharacterEncoding(charEncoding);
                response.setCharacterEncoding(charEncoding);
            } catch (UnsupportedEncodingException uee) {
                charEncoding = null;
                LOGGER.warn(
                    "Unsupported character encoding '{}': Use default character encoding from HTTP client for now",
                    charEncoding);
            }
        }
    }

    /**
     * Implement to provide application specified SOAP unmarshalling algorithm.
     */
    protected abstract void unmarshallSoapMessage(
        HttpServletRequest request,
        Element[] requestSoapParts)
        throws XmlaException;

    /**
     * Implement to handle application specified SOAP header.
     */
    protected abstract void handleSoapHeader(
        HttpServletResponse response,
        Element[] requestSoapParts,
        byte[][] responseSoapParts,
        Map<String, Object> context)
        throws XmlaException;

    /**
     * Implement to handle XML/A request.
     */
    protected abstract void handleSoapBody(
        HttpServletResponse response,
        Element[] requestSoapParts,
        byte[][] responseSoapParts,
        Map<String, Object> context)
        throws XmlaException;

    /**
     * Implement to provide application specified SOAP marshalling algorithm.
     */
    protected abstract void marshallSoapMessage(
        HttpServletResponse response,
        byte[][] responseSoapParts,
        Enumeration.ResponseMimeType responseMimeType);

    /**
     * Implement to application specified handler of SOAP fualt.
     */
    protected abstract void handleFault(
        HttpServletResponse response,
        byte[][] responseSoapParts,
        Phase phase,
        Throwable t);

    /**
     * Initialize character encoding
     */
    protected static void initCharEncodingHandler(ServletConfig servletConfig) {
        String paramValue = servletConfig.getInitParameter(PARAM_CHAR_ENCODING);
        if (paramValue != null) {
            charEncoding = paramValue;
        } else {
            charEncoding = null;
            LOGGER.warn("Use default character encoding from HTTP client");
        }
    }

    /**
     * Registers callbacks configured in web.xml.
     */
    protected void initCallbacks(ServletConfig servletConfig) {
        String callbacksValue = servletConfig.getInitParameter(PARAM_CALLBACKS);

        if (callbacksValue != null) {
            String[] classNames = callbacksValue.split(";");

            Integer count = 0;

            initCallbacks(classNames, servletConfig, count);
            LOGGER.debug("Registered {} callback{}", count, (count > 1 ? "s" : ""));
        }
    }

    private void initCallbacks(String[] classNames, ServletConfig servletConfig, Integer count) {
        for (String className1 : classNames) {
            String className = className1.trim();

            try {
                Class<?> cls = Class.forName(className);
                if (XmlaRequestCallback.class.isAssignableFrom(cls)) {
                    XmlaRequestCallback callback =
                        (XmlaRequestCallback) cls.newInstance();

                    try {
                        callback.init(servletConfig);
                    } catch (Exception e) {
                        LOGGER.warn(
                            new StringBuilder("Failed to initialize callback '")
                                .append(className).append("'").toString(),
                            e);
                        continue;
                    }

                    addCallback(callback);
                    count++;

                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug(
                            "Register callback '{}'", className);
                    }
                } else {
                    LOGGER.warn(
                        "'{}' is not an implementation of '{}'", className, XmlaRequestCallback.class);
                }
            } catch (ClassNotFoundException cnfe) {
                LOGGER.warn(
                    new StringBuilder("Callback class '").append(className).append("' not found").toString(),
                    cnfe);
            } catch (InstantiationException ie) {
                LOGGER.warn(
                    new StringBuilder("Can't instantiate class '").append(className).append("'").toString(),
                    ie);
            } catch (IllegalAccessException iae) {
                LOGGER.warn(
                    new StringBuilder("Can't instantiate class '").append(className).append("'").toString(),
                    iae);
            }
        }
    }
}
