package org.eclipse.daanse.engine.impl;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "%ctx.ocd.name", description = "%ctx.ocd.description", localization = "OSGI-INF/l10n/ctx")

public interface BasicContextConfig {
    @AttributeDefinition(name = "%name", description = "%description")
    default String name() {
        return null;
    }

}
