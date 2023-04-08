/*
 * Copyright (c) 2023 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   SmartCity Jena - initial
 *   Stefan Bischof (bipolis.org) - initial
 */
package org.eclipse.daanse.xmla.ws.jakarta.basic;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.transform.Result;

import org.eclipse.daanse.xmla.ws.jakarta.model.xsd.Schema;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.SchemaOutputResolver;
import jakarta.xml.bind.util.JAXBResult;

public class SchemaUtil {

	private SchemaUtil() {
	}

	static Map<Class<?>[], Schema> map = new ConcurrentHashMap<>();

	//
	public static final Schema generateSchema(String ns, Class<?>... classes) throws JAXBException, IOException {

		Schema schema = map.get(classes);
		if (schema != null) {
			return schema;
		}
		JAXBContext applicationContext = JAXBContext.newInstance(classes);
		JAXBContext schemaContext = JAXBContext.newInstance(Schema.class);
		JaxBSchemaOutputResolver resolver = new JaxBSchemaOutputResolver(schemaContext);
		applicationContext.generateSchema(resolver);
		Marshaller marshaller = schemaContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		schema = (Schema) resolver.schemas.get(ns).getResult();
		marshaller.marshal(schema, System.out);
		map.put(classes, schema);
		return schema;
	}

	public static final class JaxBSchemaOutputResolver extends SchemaOutputResolver {

		private JAXBContext context;
		private Map<String, JAXBResult> schemas = new HashMap<>();

		public JaxBSchemaOutputResolver(JAXBContext context) {
			this.context = context;
		}

		public Result createOutput(String namespaceURI, String suggestedFileName) throws IOException {
			System.out.println("Generate schema for " + namespaceURI + " with suggested name " + suggestedFileName);
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
