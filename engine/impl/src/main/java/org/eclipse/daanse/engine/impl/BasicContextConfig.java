package org.eclipse.daanse.engine.impl;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "%ctx.ocd.name", description = "%ctx.ocd.description", localization = "OSGI-INF/l10n/ctx")
public interface BasicContextConfig {

    @AttributeDefinition(name = "%name.name", description = "%name.description", required = true)
    default String name() {
        return null;
    }

    @AttributeDefinition(name = "%description.name", description = "%description.description")
    default String description() {
        return null;
    }

}
