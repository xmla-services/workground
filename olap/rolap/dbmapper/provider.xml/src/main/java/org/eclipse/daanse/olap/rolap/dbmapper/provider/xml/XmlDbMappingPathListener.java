package org.eclipse.daanse.olap.rolap.dbmapper.provider.xml;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent.Kind;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.daanse.util.io.watcher.api.EventKind;
import org.eclipse.daanse.util.io.watcher.api.PathListener;
import org.eclipse.daanse.util.io.watcher.api.PathListenerConfig;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component()
@Designate(ocd = XmlDbMappingPathListener.Config.class, factory = true)
@PathListenerConfig(path = "", kinds = EventKind.ENTRY_MODIFY,pattern = ".*.xml")
public class XmlDbMappingPathListener implements PathListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(XmlDbMappingPathListener.class);
	static final String PID = "org.eclipse.daanse.olap.rolap.dbmapper.provider.xml.XmlDbMappingFileWatcher";

	Map<Path, Configuration> map = Collections.synchronizedMap(new HashMap<>());

	@Reference
	ConfigurationAdmin ca;

	@ObjectClassDefinition
	@PathListenerConfig

	@interface Config  {
		
	}

	protected void removeSchema(Path path) {
		try {

			Configuration c = map.remove(path);
			if (c != null) {
				c.delete();
			}
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
			ht.put("sample.name", sUrl);
			ht.put("sample.type", "xml");
			c.update(ht);

			map.put(path, c);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handleBasePath(Path basePath) {
		//
	}

	@Override
	public void handleInitialPaths(List<Path> path) {
		path.forEach(this::addPath);

	}

	@Override
	public void handlePathEvent(Path path, Kind<Path> kind) {
		if (StandardWatchEventKinds.ENTRY_MODIFY.equals(kind)) {
			addPath(path);
		}
	}

	private void addPath(Path path) {

		if (Files.isDirectory(path)) {
			return;
		}
		if (!path.endsWith(".xml")) {
			return;
		}

		addSchema(path);
	}

}
