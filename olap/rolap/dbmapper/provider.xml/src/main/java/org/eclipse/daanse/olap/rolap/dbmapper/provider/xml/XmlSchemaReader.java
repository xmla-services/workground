package org.eclipse.daanse.olap.rolap.dbmapper.provider.xml;

import java.io.InputStream;

import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.SchemaImpl;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class XmlSchemaReader {

    public SchemaImpl read(InputStream inputStream) throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(SchemaImpl.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (SchemaImpl) jaxbUnmarshaller.unmarshal(inputStream);

    }
}
