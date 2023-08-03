package org.eclipse.daanse.grabber;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;


@ObjectClassDefinition
public interface GrabberServiceConfig {

    /**
     * @return Clear Target Database before fill flag
     */
    @AttributeDefinition(description = "clearTargetDatabase")
    default boolean clearTargetDatabase() {
        return true;
    }

    @AttributeDefinition(name = "Cron-job expression")
    default String schedulerExpression() { return "*/30 * * * * ?"; }

}
