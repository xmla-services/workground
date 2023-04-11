package org.eclipse.daanse.olap.rolap.dbmapper.provider.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.api.DbMappingSchemaProvider;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.xml.XmlDbMappingSchemaProvider.Config;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import jakarta.xml.bind.JAXBException;

@Component(service = DbMappingSchemaProvider.class)
@Designate(ocd = Config.class, factory = true)
public class XmlDbMappingSchemaProvider implements DbMappingSchemaProvider {

	@ObjectClassDefinition
	@interface Config {

		String url();

	}

	private XmlSchemaReader reader = new XmlSchemaReader();
	private Schema schema;

	@Activate
	public void activate(Config config) throws JAXBException, IOException {
		URL url = new URL(config.url());
		try (InputStream in = url.openStream()) {
			schema = reader.read(in);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Schema get() {
		return schema;
	}

}
