package org.eclipse.daanse.xmla.ws.jakarta.model.test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import com.microsoft.schemas.xml_analysis.DiscoverResponse;
import com.microsoft.schemas.xml_analysis.Return;
import com.microsoft.schemas.xml_analysis.rowset.Root;
import com.microsoft.schemas.xml_analysis.rowset.Row;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.MarshalException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.PropertyException;

public class SerializeTest {
	static SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	static JAXBContext jaxbContext;

	static {
		try {
			jaxbContext = JAXBContext.newInstance(Root.class, Row.class, DiscoverResponse.class);
		} catch (JAXBException e) {
			throw new AssertionError(e);
		}
	}

	@Test
	public void testRowset() throws Exception {
		Root root = new Root();
		Row row = new Row();
		row.setPropertyName("DbpropMsmdSubqueries");
		row.setPropertyDescription("An enumeration value that determines the behavior of subqueries.");
		row.setPropertyType("Integer");
		row.setPropertyAccessType("ReadWrite");
		row.setIsRequired(false);
		row.setValue("1");
		root.getRow().add(row);

		Marshaller marshaller = createMarshaller("rowset.xsd");
		System.out.println("Will marshall and validate object");
		marshaller.marshal(root, System.out);
		assertThrows(MarshalException.class, () -> {
			Root invalidRoot = new Root();
			Row invalidRow = new Row();
			invalidRow.setPropertyName("DbpropMsmdSubqueries");
			invalidRoot.getRow().add(invalidRow);
			marshaller.marshal(invalidRoot, OutputStream.nullOutputStream());
		});
	}

	@Test
	public void testDiscoverResponse() throws Exception {
		DiscoverResponse response = new DiscoverResponse();
		Return value = new Return();
		response.setReturn(value);
		Root root = new Root();
		Row row = new Row();
		row.setPropertyName("DbpropMsmdSubqueries");
		row.setPropertyDescription("An enumeration value that determines the behavior of subqueries.");
		row.setPropertyType("Integer");
		row.setPropertyAccessType("ReadWrite");
		row.setIsRequired(false);
		row.setValue("1");
		root.getRow().add(row);
		value.setRoot(root);
		Marshaller marshaller = createMarshaller("discover.xsd");
		System.out.println("Will marshall and validate object");
		marshaller.marshal(response, System.out);
	}

	private Marshaller createMarshaller(String name) throws SAXException, JAXBException, PropertyException {
		Schema schema = getSchema(name);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setSchema(schema);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.toString());
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		try {
			marshaller.setProperty("org.glassfish.jaxb.namespacePrefixMapper", new NamespacePrefixMapper() {

				@Override
				public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
					if ("urn:schemas-microsoft-com:xml-analysis".equals(namespaceUri)) {
						return requirePrefix ? "analysis" : null;
					}
					if ("urn:schemas-microsoft-com:xml-analysis:rowset".equals(namespaceUri)) {
						return requirePrefix ? "rowset" : null;
					}
					return suggestion;
				}

			});
		} catch (PropertyException e) {
			// In case another JAXB implementation is used
		}
		return marshaller;
	}

	public static Schema getSchema(String name) throws SAXException {
		return schemaFactory.newSchema(SerializeTest.class.getResource("/META-INF/JAXB/xsd/" + name));
	}

}
