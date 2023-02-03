package org.eclipse.daanse.xmla.ws.jakarta.model.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.transform.Result;

import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.DiscoverResponse;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.Return;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla_rowset.DiscoverPropertiesResponseRowXml;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla_rowset.Rowset;
import org.junit.jupiter.api.Test;
import org.w3._2001.xmlschema.Schema;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.SchemaOutputResolver;
import jakarta.xml.bind.util.JAXBResult;

public class TestSchemaGen {

	@Test
	public void testSchemaGen() throws Exception {
		DiscoverResponse response = new DiscoverResponse();
		Return ret = new Return();
		response.setReturn(ret);
		Rowset rowset = new Rowset();
		DiscoverPropertiesResponseRowXml props = new DiscoverPropertiesResponseRowXml();
		props.setPropertyName("My Name");
		rowset.setRow(List.of(props));
		ret.setValue(rowset);
		System.out.println("========[ GEN SCHEMA ]=============");
		Schema generateSchema = generateSchema(DiscoverPropertiesResponseRowXml.class);
		response.setSchema(generateSchema);
		System.out.println("=========[ DiscoverResponse ]============");
		JAXBContext context = JAXBContext.newInstance(DiscoverResponse.class, Return.class, Rowset.class,
				DiscoverPropertiesResponseRowXml.class, Schema.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(response, System.out);
	}
	
	public static final Schema generateSchema(Class<?> ...classes) throws JAXBException, IOException {
		JAXBContext applicationContext = JAXBContext.newInstance(classes);
		JAXBContext schemaContext = JAXBContext.newInstance(Schema.class);
		JaxBSchemaOutputResolver resolver = new JaxBSchemaOutputResolver(schemaContext);
		applicationContext.generateSchema(resolver);
		Marshaller marshaller = schemaContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		Schema result = (Schema) resolver.schemas.get("urn:schemas-microsoft-com:xml-analysis:rowset").getResult();
		marshaller.marshal(result, System.out);
		return result;
	}

	public static final class JaxBSchemaOutputResolver extends SchemaOutputResolver {
		
		private JAXBContext context;
		private Map<String, JAXBResult> schemas= new HashMap<>();

		public JaxBSchemaOutputResolver(JAXBContext context) {
			this.context = context;
		}

		public Result createOutput(String namespaceURI, String suggestedFileName) throws IOException {
			System.out.println("Generate schema for "+namespaceURI+" with suggested name "+suggestedFileName);
			try {
				JAXBResult result = new JAXBResult(context);
				result.setSystemId(namespaceURI);
				schemas.put(namespaceURI, result);
				return result;
			} catch (JAXBException e) {
				throw new IOException(e);
			}
		}

	}
}
