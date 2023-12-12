
package org.eclipse.daanse.util.io.watcher.api;

import org.osgi.service.component.annotations.ComponentPropertyType;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition
@ComponentPropertyType
public @interface PathListenerConfig {
	public static final String PREFIX_="pathListener.";

	@AttributeDefinition(required = true)
	String path() default "";

	@AttributeDefinition
	String pattern() default ".*";

	@AttributeDefinition(defaultValue = { "ENTRY_CREATE", "ENTRY_DELETE", "ENTRY_MODIFY" })
	EventKind[] kinds() default { EventKind.ENTRY_CREATE, EventKind.ENTRY_DELETE, EventKind.ENTRY_MODIFY };

	@AttributeDefinition(defaultValue = "true")

	boolean initialFiles() default true;

	@AttributeDefinition(defaultValue = "true")

	boolean recursive() default false;

}
