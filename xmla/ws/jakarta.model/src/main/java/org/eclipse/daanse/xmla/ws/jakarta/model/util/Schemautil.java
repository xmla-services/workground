package org.eclipse.daanse.xmla.ws.jakarta.model.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.SchemaOutputResolver;
import jakarta.xml.bind.util.JAXBResult;

public class Schemautil {

    Map<Class<?>, Object> map = new ConcurrentHashMap<>();

    public static void getSchema(Class<?> clazz) throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        SchemaOutputResolver sor = new MySchemaOutputResolver(jaxbContext);
        jaxbContext.generateSchema(sor);

    }

    static class MySchemaOutputResolver extends SchemaOutputResolver {

        private JAXBContext jaxbContext;

        public MySchemaOutputResolver(JAXBContext jaxbContext) {
            this.jaxbContext = jaxbContext;
        }

        @Override
        public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
            File file = new File(suggestedFileName);
            JAXBResult result = null;
            try {
                result = new JAXBResult(jaxbContext);
            } catch (JAXBException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
//            result.setSystemId();
            return result;
        }

    }

}
