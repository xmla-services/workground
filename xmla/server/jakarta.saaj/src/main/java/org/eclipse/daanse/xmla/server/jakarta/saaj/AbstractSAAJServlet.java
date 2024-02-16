
package org.eclipse.daanse.xmla.server.jakarta.saaj;

import static org.eclipse.daanse.xmla.server.jakarta.saaj.SoapServletHelper.setMimeHeadersToResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.MimeHeaders;
import jakarta.xml.soap.SOAPConnection;
import jakarta.xml.soap.SOAPConnectionFactory;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;

public abstract class AbstractSAAJServlet extends HttpServlet {

    private static final String EXCEPTION_MSG_MESSAGE_FACTORY = "Unable to get/create MessageFactory.";

    private static final String EXCEPTION_MSG_MESSAGE_SOAP_CONNECTION = "Unable to create SOAPConnection.";
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSAAJServlet.class);
    private static final String EXCEPTION_MSG_POST = "SAAJ post failed.";

    protected transient SOAPConnection soapConnection = null;
    protected transient MessageFactory messageFactory = null;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        try {
            messageFactory = getMessageFactory();
        } catch (SOAPException ex) {
            LOGGER.error(EXCEPTION_MSG_MESSAGE_FACTORY, ex);
            throw new ServletException(EXCEPTION_MSG_MESSAGE_FACTORY);
        }
        try {

            soapConnection = createSOAPConnection();
        } catch (SOAPException ex) {
            LOGGER.error(EXCEPTION_MSG_MESSAGE_SOAP_CONNECTION, ex);
            throw new ServletException(EXCEPTION_MSG_MESSAGE_SOAP_CONNECTION);
        }

    }

    protected MessageFactory getMessageFactory() throws SOAPException {
        return MessageFactory.newInstance();
    }

    protected SOAPConnection createSOAPConnection() throws SOAPException {
        return SOAPConnectionFactory.newInstance().createConnection();
    }

    @Override
    public void doPost(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
            throws ServletException, IOException {
        try {
            MimeHeaders headers = SoapServletHelper.getMimeHeadersFromRequest(servletRequest);

            InputStream requestInptStream = servletRequest.getInputStream();

            SOAPMessage requestMessage = messageFactory.createMessage(headers, requestInptStream);

            SOAPMessage responseMessage = onMessage(requestMessage);

            if (responseMessage != null) {

                // Need to saveChanges 'cos we're going to use the
                // MimeHeaders to set HTTP response information. These
                // MimeHeaders are generated as part of the save.

                if (responseMessage.saveRequired()) {
                    responseMessage.saveChanges();
                }

                servletResponse.setStatus(HttpServletResponse.SC_OK);

                setMimeHeadersToResponse(servletResponse, responseMessage.getMimeHeaders());

                OutputStream responseOutputStream = servletResponse.getOutputStream();

                responseMessage.writeTo(responseOutputStream);

                responseOutputStream.flush();

            } else {
                servletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } catch (Exception ex) {
            LOGGER.error(EXCEPTION_MSG_POST, ex);
            throw new ServletException(EXCEPTION_MSG_POST);
        }
    }

    protected abstract SOAPMessage onMessage(SOAPMessage requestMessage);

}
