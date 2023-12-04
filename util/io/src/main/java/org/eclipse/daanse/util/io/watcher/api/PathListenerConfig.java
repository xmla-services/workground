package org.eclipse.daanse.util.io.watcher.api;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition
public interface PathListenerConfig {
	

	public static final EventKind[] DEFAULT_KINDS = new EventKind[] { EventKind.ENTRY_CREATE, EventKind.ENTRY_DELETE,
			EventKind.ENTRY_MODIFY };

	@AttributeDefinition(required = true)
	default String[] pathListener_paths() {
		return null;
	}
	
	@AttributeDefinition(required = true,defaultValue = "true")
	default boolean pathListener_enabled() {
		return true;
	}

	@AttributeDefinition
	default String pathListener_pattern() {
		return null;
	}

	@AttributeDefinition(defaultValue = { "ENTRY_CREATE", "ENTRY_DELETE", "ENTRY_MODIFY" })
	default EventKind[] pathListener_kinds() {
		return DEFAULT_KINDS;
	}

	@AttributeDefinition(defaultValue = "true")

	default boolean pathListener_initialFiles() {
		return true;
	}

	@AttributeDefinition(defaultValue = "true")

	default boolean pathListener_recursive() {
		return false;
	}

}
