package org.eclipse.daanse.util.io.watcher.api;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition
public @interface PathListenerConfig {

	public static final String PREFIX_ = "org.eclipse.daanse.util.io.watcher.";

	@AttributeDefinition(required = true)
	String[] paths();

	@AttributeDefinition

	String pattern() default "";

	@AttributeDefinition

	EventKind[] kind() default { EventKind.ENTRY_CREATE, EventKind.ENTRY_DELETE, EventKind.ENTRY_MODIFY };

	@AttributeDefinition

	boolean listInitialFiles() default true;

	@AttributeDefinition

	boolean recursive() default false;

}
