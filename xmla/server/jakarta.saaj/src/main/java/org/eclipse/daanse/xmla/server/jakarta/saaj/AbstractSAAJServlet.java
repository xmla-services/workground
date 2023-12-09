
package org.eclipse.daanse.xmla.server.jakarta.saaj;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.StringTokenizer;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.MimeHeader;
import jakarta.xml.soap.MimeHeaders;
import jakarta.xml.soap.SOAPConnection;
import jakarta.xml.soap.SOAPConnectionFactory;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;

public abstract class AbstractSAAJServlet extends HttpServlet {

	protected SOAPConnection soapConnection = null;

	protected MessageFactory messageFactory = null;

	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		try {
			messageFactory = MessageFactory.newInstance();
			SOAPConnectionFactory connFactory = SOAPConnectionFactory.newInstance();
			soapConnection = connFactory.createConnection();
		} catch (SOAPException ex) {
			throw new ServletException("Unable to create message factory" + ex.getMessage());
		}
	}

	public void setMessageFactory(MessageFactory msgFactory) {
		this.messageFactory = msgFactory;
	}

	protected static MimeHeaders getRequestHeaders(HttpServletRequest req) {
		Enumeration enumeration = req.getHeaderNames();
		MimeHeaders headers = new MimeHeaders();

		while (enumeration.hasMoreElements()) {
			String headerName = (String) enumeration.nextElement();
			String headerValue = req.getHeader(headerName);

			StringTokenizer values = new StringTokenizer(headerValue, ",");

			while (values.hasMoreTokens()) {
				headers.addHeader(headerName, values.nextToken().trim());
			}
		}

		return headers;
	}

	protected static void putHeaders(MimeHeaders headers, HttpServletResponse res) {
		Iterator it = headers.getAllHeaders();

		while (it.hasNext()) {
			MimeHeader header = (MimeHeader) it.next();

			String[] values = headers.getHeader(header.getName());

			if (values.length == 1) {
				res.setHeader(header.getName(), header.getValue());
			} else {
				StringBuffer concat = new StringBuffer();
				int i = 0;

				while (i < values.length) {
					if (i != 0) {
						concat.append(',');
					}
					concat.append(values[i++]);
				}
				res.setHeader(header.getName(), concat.toString());
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			MimeHeaders headers = getRequestHeaders(request);

			InputStream requestInptStream = request.getInputStream();

			SOAPMessage requestMessage = messageFactory.createMessage(headers, requestInptStream);

			SOAPMessage responseMessage = null;

			responseMessage = onMessage(requestMessage);

			if (responseMessage != null) {

				// Need to saveChanges 'cos we're going to use the
				// MimeHeaders to set HTTP response information. These
				// MimeHeaders are generated as part of the save.

				if (responseMessage.saveRequired()) {
					responseMessage.saveChanges();
				}

				response.setStatus(HttpServletResponse.SC_OK);

				putHeaders(responseMessage.getMimeHeaders(), response);

				OutputStream responseOutputStream = response.getOutputStream();

				responseMessage.writeTo(responseOutputStream);

				responseOutputStream.flush();

			} else {
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			}
		} catch (Exception ex) {
			throw new ServletException("SAAJ POST failed " + ex.getMessage());
		}
	}

	public abstract SOAPMessage onMessage(SOAPMessage requestMessage);

}
