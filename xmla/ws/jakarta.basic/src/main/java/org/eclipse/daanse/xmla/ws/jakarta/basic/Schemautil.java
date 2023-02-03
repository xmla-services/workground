package org.eclipse.daanse.xmla.ws.jakarta.basic;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.transform.Result;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.SchemaOutputResolver;
import jakarta.xml.bind.util.JAXBResult;

public class Schemautil {

    Map<Class<?>, Object> map = new ConcurrentHashMap<>();

    public static JAXBElement<?> getSchema(Class<?> clazz) throws JAXBException, IOException {
//        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
//        SchemaOutputResolver sor = new JaxBSchemaOutputResolver(jaxbContext);
//        jaxbContext.generateSchema(sor);
//        Result e = sor.createOutput("urn:schemas-microsoft-com:xml-analysis:rowset", "no.xsd");
//        
//        System.out.println(e);
        return null;

    }

    public static class JaxBSchemaOutputResolver extends SchemaOutputResolver {

        private JAXBContext jaxbContext;

        public JaxBSchemaOutputResolver(JAXBContext jaxbContext) {
            this.jaxbContext = jaxbContext;
        }

        public Result createOutput(String namespaceURI, String suggestedFileName) throws IOException {
            JAXBResult result = null;
            try {
                result = new JAXBResult(jaxbContext);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
            return result;
        }

    }
    

}
