package org.eclipse.daanse.olap.filecatalog;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent.Kind;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.daanse.util.io.watcher.api.PathListener;
import org.eclipse.daanse.util.io.watcher.api.PathListenerConfig;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.annotations.RequireConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.RequireServiceComponentRuntime;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@Designate(factory = true, ocd = FileContextRepositoryConfigurator.Config.class)
@Component()
@RequireConfigurationAdmin
@RequireServiceComponentRuntime
@PathListenerConfig(initialFiles = true, recursive = false)
public class FileContextRepositoryConfigurator implements PathListener {

	public static final String PID_FileContextConfigurator = FileContextConfigurator.class.getName();

	@Reference
	ConfigurationAdmin configurationAdmin;

	private Path basePath;
	private Map<Path, Configuration> catalogFolderConfigs = new ConcurrentHashMap<>();

	@ObjectClassDefinition
	@interface Config {
		@AttributeDefinition
		String pathListener_path();

	}

	@Override
	public void handleBasePath(Path basePath) {
		this.basePath = basePath;

	}

	@Override
	public void handleInitialPaths(List<Path> paths) {
		paths.forEach(this::addPath);
	}

	@Override
	public void handlePathEvent(Path path, Kind<Path> kind) {

		if (StandardWatchEventKinds.ENTRY_MODIFY.equals(kind)) {
			removePath(path);
			addPath(path);
		} else if (StandardWatchEventKinds.ENTRY_CREATE.equals(kind)) {
			addPath(path);
		} else if (StandardWatchEventKinds.ENTRY_DELETE.equals(kind)) {
			removePath(path);
		}
	}

	private void removePath(Path path) {
		if (!Files.isDirectory(path)) {
			return;
		}

		try {
			Configuration c = catalogFolderConfigs.remove(path);
			c.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addPath(Path path) {
		if (!Files.isDirectory(path)) {
			return;
		}

		try {
			Configuration c = configurationAdmin.getFactoryConfiguration(PID_FileContextConfigurator,
					UUID.randomUUID().toString(), "?");
			Dictionary<String, Object> props = new Hashtable<>();
			props.put("pathListener.path", path.toString());
			props.put("name", basePath.resolve(path).toString());
			c.update(props);
			
			catalogFolderConfigs.put(path, c);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}