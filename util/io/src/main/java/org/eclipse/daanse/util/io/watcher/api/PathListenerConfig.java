
package org.eclipse.daanse.util.io.watcher.api;

import org.osgi.service.component.annotations.ComponentPropertyType;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition
@ComponentPropertyType
public @interface PathListenerConfig {
	public static final String PREFIX_ = "pathListener.";

	@AttributeDefinition(required = true)
	String path() default "";

	@AttributeDefinition
	String pattern() default ".*";

	@AttributeDefinition()
	EventKind[] kinds() default { EventKind.ENTRY_CREATE, EventKind.ENTRY_DELETE, EventKind.ENTRY_MODIFY };

	@AttributeDefinition()
	boolean initialFiles() default true;

	@AttributeDefinition()
	boolean recursive() default false;

}
