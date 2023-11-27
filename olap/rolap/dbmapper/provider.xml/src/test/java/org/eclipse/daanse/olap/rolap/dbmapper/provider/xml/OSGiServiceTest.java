package org.eclipse.daanse.olap.rolap.dbmapper.provider.xml;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.daanse.olap.rolap.dbmapper.provider.api.DatabaseMappingSchemaProvider;
import org.junit.jupiter.api.io.TempDir;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.annotations.RequireConfigurationAdmin;
import org.osgi.service.component.annotations.RequireServiceComponentRuntime;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.dictionary.Dictionaries;
import org.osgi.test.common.service.ServiceAware;

@RequireServiceComponentRuntime
@RequireConfigurationAdmin
class OSGiServiceTest {

	@InjectService
	ConfigurationAdmin ca;

	@TempDir
	Path path;

	String XML = """
			<?xml version="1.0" encoding="UTF-8"?>
			<Schema name="X">

			<Cube name="Population" description = "Population cube">
			  <Table name="population"/>
			</Cube>

			</Schema>
			""";

	@org.junit.jupiter.api.Test
	void testDbMappingSchemaProvider(
			@InjectService(cardinality = 0) ServiceAware<DatabaseMappingSchemaProvider> provider) throws Exception {

		System.out.println(path);
		init();
		assertThat(provider.getServiceReferences()).isEmpty();
		Thread.sleep(100);

		Path f = Files.createTempFile(path, "schemaMapping", ".xml");
		Files.writeString(f, XML);

		Thread.sleep(1000000);

	}

	private void init() throws IOException {
		Configuration c = ca.createFactoryConfiguration(XmlDbMappingFileWatcher.PID, "?");
		c.update(Dictionaries.dictionaryOf("path", path.toAbsolutePath().toString()));
	}
}
