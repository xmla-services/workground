package org.eclipse.daanse.db.jdbc.ecoregen;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.Collections;
import java.util.Map;

import javax.sql.DataSource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.util.converter.Converter;
import org.osgi.util.converter.Converters;

import biz.aQute.scheduler.api.CronJob;

@Designate(ocd = OberverConfig.class, factory = true)
@Component(immediate = true)
public class DatabaseSchemaToEcoreObervator implements CronJob {

	public static Converter CONVERTER = Converters.standardConverter();

	@Reference
	DataSource dataSource;

	private Resource resource;
	private String schema;

	@Activate
	public void activate(Map<String, Object> map) {
		OberverConfig config = CONVERTER.convert(map).to(OberverConfig.class);
		activate(config);
	}

	public void activate(OberverConfig config) {
		try {
			schema = config.database_schema();
			String url = config.ecore_url();

			Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());

			final URI uri = URI.createFileURI(url);

			ResourceSet resourceSet = new ResourceSetImpl();
			resource = resourceSet.createResource(uri);
			resource.load(Collections.emptyMap());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() throws Exception {

		try (Connection connection = dataSource.getConnection()) {

			connection.setSchema(schema);
			String catalog = connection.getCatalog();

			DatabaseMetaData metaData = connection.getMetaData();
			
			// Tables, Columns per Table, Foreign Key

		}
	}

}
