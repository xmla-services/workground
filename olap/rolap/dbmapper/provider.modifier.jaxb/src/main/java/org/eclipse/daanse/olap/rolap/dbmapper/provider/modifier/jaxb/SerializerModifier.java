package org.eclipse.daanse.olap.rolap.dbmapper.provider.modifier.jaxb;

import java.io.ByteArrayOutputStream;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.SchemaImpl;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class SerializerModifier extends JaxbDbMappingSchemaModifier {

	public SerializerModifier(MappingSchema mappingSchema) {
		super(mappingSchema);
	}

	public String getXML() throws JAXBException {
		MappingSchema schema = get();

		if (schema instanceof SchemaImpl impl) {

			JAXBContext jaxbContext = JAXBContext.newInstance(SchemaImpl.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			ByteArrayOutputStream boas = new ByteArrayOutputStream();
			jaxbMarshaller.marshal(impl, boas);
			return boas.toString();

		}
		return null;

	}

}
