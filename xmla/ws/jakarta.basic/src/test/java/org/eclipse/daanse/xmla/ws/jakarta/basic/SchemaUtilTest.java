package org.eclipse.daanse.xmla.ws.jakarta.basic;

import java.util.List;

import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.DiscoverResponse;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Return;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_rowset.discover.DiscoverPropertiesResponseRowXml;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_rowset.Rowset;
import org.eclipse.daanse.xmla.ws.jakarta.model.xsd.Schema;
import org.junit.jupiter.api.Test;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

public class SchemaUtilTest {
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
        Schema generateSchema = SchemaUtil.generateSchema("urn:schemas-microsoft-com:xml-analysis:rowset",
                DiscoverPropertiesResponseRowXml.class);
        rowset.setSchema(generateSchema);
        System.out.println("=========[ DiscoverResponse ]============");
        JAXBContext context = JAXBContext.newInstance(DiscoverResponse.class, Return.class, Rowset.class,
                DiscoverPropertiesResponseRowXml.class, Schema.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(response, System.out);
    }

}
