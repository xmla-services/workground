
package org.eclipse.daanse.xmla.server.jakarta.saaj;

import java.util.Iterator;

import org.eclipse.daanse.xmla.api.XmlaService;
import org.eclipse.daanse.xmla.server.adapter.soapmessage.XmlaApiAdapter;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.servlet.whiteboard.propertytypes.HttpWhiteboardServletPattern;

import jakarta.servlet.Servlet;
import jakarta.xml.soap.Node;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPHeader;
import jakarta.xml.soap.SOAPHeaderElement;
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

			Iterator<SOAPHeaderElement> headerElements = header
					.examineMustUnderstandHeaderElements("http://foo.bar/receiver");

			while (headerElements.hasNext()) {
				SOAPHeaderElement headerElement = headerElements.next();
				String actor = headerElement.getActor();
				boolean mustUnderStand = headerElement.getMustUnderstand();
				Iterator<Node> headerChildElements = headerElement.getChildElements();

				System.out.println("");
				System.out.println("actor: " + actor + " - mustUnderStand: " + mustUnderStand);
				while (headerChildElements.hasNext()) {

					org.w3c.dom.Node child = headerElement.getFirstChild();
					String childValue = child.getNodeValue();
					System.out.println("childValue is " + childValue);
				}
			}

			return wsAdapter.handleRequest(message);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
