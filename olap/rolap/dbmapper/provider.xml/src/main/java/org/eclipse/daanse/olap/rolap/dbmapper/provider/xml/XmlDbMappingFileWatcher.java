package org.eclipse.daanse.olap.rolap.dbmapper.provider.xml;

import java.io.Closeable;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.xml.bind.JAXBException;

@Component()
@Designate(ocd = XmlDbMappingFileWatcher.Config.class, factory = true)
public class XmlDbMappingFileWatcher {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(XmlDbMappingFileWatcher.class);
	static final String PID = "org.eclipse.daanse.olap.rolap.dbmapper.provider.xml.XmlDbMappingFileWatcher";

	private Path basePath;

	Map<Path, Configuration> map = Collections.synchronizedMap(new HashMap<>());

	@Reference
	ConfigurationAdmin ca;


	private WatchRunner watchRunner;

	@ObjectClassDefinition
	@interface Config {

		String path();

	}

	@Activate
	public void activate(Config config) throws IOException, JAXBException, InterruptedException {

		basePath = Paths.get(config.path());

		watchRunner = new WatchRunner();
		Executors.newCachedThreadPool().execute(watchRunner);

	}

	@Deactivate
	public void deactivate(Config config) throws IOException, JAXBException {
		watchRunner.close();
	}

	protected void removeSchema(Path path) {
		try {

			Configuration c = map.get(path);
			c.delete();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	protected void addSchema(Path path) {
		String uuid = UUID.randomUUID().toString();

		try {
			Configuration c = ca.getFactoryConfiguration(XmlDbMappingSchemaProvider.PID, uuid, "?");

			URL url = path.toUri().toURL();
			String sUrl = url.toString();
			Dictionary<String, Object> ht = new Hashtable<>();
			ht.put("url", sUrl);
			ht.put("sample.name", url);
			ht.put("sample.type", "xml");
			c.update(ht);

			map.put(path, c);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class WatchRunner implements Runnable, Closeable {

		private WatchService watchService;
		private WatchKey watchKey;

		@Override
		public void run() {
			boolean valid = true;
			try {

				watchService = FileSystems.getDefault().newWatchService();
				basePath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
						StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

				watchKey = watchService.take();
				Files.list(basePath).forEach(XmlDbMappingFileWatcher.this::addSchema);

				do {
					for (WatchEvent<?> event : watchKey.pollEvents()) {

						String eventType = event.kind().name();
						String fileName = event.context().toString();

						Path eventPath = basePath.resolve(fileName).toAbsolutePath();
						System.out.println("File " + fileName + ", EventKind : " + eventType);
						if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
							addSchema(eventPath);
						} else if (StandardWatchEventKinds.ENTRY_DELETE.equals(event.kind())) {
							removeSchema(eventPath);
						} else if (StandardWatchEventKinds.ENTRY_MODIFY.equals(event.kind())) {
							removeSchema(eventPath);
							addSchema(eventPath);
						}

					}
					valid = watchKey.reset();

				} while (valid);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void close() throws IOException {
			watchService.close();

			map.entrySet().forEach(e -> removeSchema(e.getKey()));

		}

	}

}
