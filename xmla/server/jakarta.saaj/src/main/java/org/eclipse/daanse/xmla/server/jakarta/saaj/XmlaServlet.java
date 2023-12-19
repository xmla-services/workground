
package org.eclipse.daanse.xmla.server.jakarta.saaj;

import java.util.Optional;

import org.eclipse.daanse.xmla.api.RequestMetaData;
import org.eclipse.daanse.xmla.api.XmlaService;
import org.eclipse.daanse.xmla.model.record.RequestMetaDataR;
import org.eclipse.daanse.xmla.server.adapter.soapmessage.XmlaApiAdapter;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.servlet.whiteboard.propertytypes.HttpWhiteboardServletPattern;

import jakarta.servlet.Servlet;
import jakarta.xml.soap.MimeHeaders;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPHeader;
import jakarta.xml.soap.SOAPMessage;

@HttpWhiteboardServletPattern("/xmla")
@Component(service = Servlet.class, scope = ServiceScope.PROTOTYPE)
public class XmlaServlet extends AbstractSAAJServlet {

	private XmlaApiAdapter wsAdapter;

	@Reference
	private XmlaService xmlaService;

	@Activate
	public void activate() {
		wsAdapter = new XmlaApiAdapter(xmlaService);
	}

	@Override
	public SOAPMessage onMessage(SOAPMessage message) {
		System.out.println("On message call");
		try {

			message.writeTo(System.out);

			SOAPHeader header = message.getSOAPHeader();
			SOAPBody body = message.getSOAPBody();

			MimeHeaders m = message.getMimeHeaders();
			String[] s = m.getHeader("User-agent");

			Optional<String> oUserAgent = Optional.empty();
			if (s != null && s.length > 0) {
				oUserAgent = Optional.of(s[0]);
			}

			RequestMetaData metaData = new RequestMetaDataR(oUserAgent);

			return wsAdapter.handleRequest(message, metaData);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
