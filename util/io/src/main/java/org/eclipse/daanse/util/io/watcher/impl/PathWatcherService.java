
package org.eclipse.daanse.util.io.watcher.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.daanse.util.io.watcher.api.PathListener;
import org.eclipse.daanse.util.io.watcher.api.PathListenerConfig;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.util.converter.Converter;
import org.osgi.util.converter.Converters;

@Component(immediate = true, scope = ServiceScope.SINGLETON)
public class PathWatcherService {

	public static final Converter CONVERTER = Converters.standardConverter();
	private final Map<PathListener, FileWatcherRunable> listeners = Collections.synchronizedMap(new HashMap<>());
	private final ExecutorService executorService = Executors.newCachedThreadPool();

	@Activate
	void activate() throws IOException {

	}	
	@Deactivate
	void deactivate() throws IOException {

	}

	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	@AttributeDefinition
	public void bindPathListener(PathListener handler, Map<String, Object> map) throws IOException {
		PathListenerConfig config = CONVERTER.convert(map).to(PathListenerConfig.class);

		FileWatcherRunable fwrRunable = new FileWatcherRunable(handler, config);
		executorService.execute(fwrRunable);
		listeners.put(handler, fwrRunable);
	}

	public void unbindPathListener(PathListener handler) {
		FileWatcherRunable runable = listeners.remove(handler);
		if (runable != null) {
			runable.shutdown();
		}
	}

}
