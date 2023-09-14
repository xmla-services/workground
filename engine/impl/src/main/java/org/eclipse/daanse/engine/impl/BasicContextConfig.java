/*
* Copyright (c) 2022 Contributors to the Eclipse Foundation.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*   SmartCity Jena - initial
*   Stefan Bischof (bipolis.org) - initial
*/
package org.eclipse.daanse.engine.impl;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "%ctx.ocd.name", description = "%ctx.ocd.description", localization = "OSGI-INF/l10n/ctx")
public interface BasicContextConfig {

    @AttributeDefinition(name = "%name.name", description = "%name.description", required = true)
    default String name() {
        return null;
    }

    @AttributeDefinition(name = "%description.name", description = "%description.description", type = AttributeType.STRING)
    default String description() {
        return null;
    }

    @AttributeDefinition(name = "%aggregateRuleTag.name", description = "%aggregateRuleTag.description", type = AttributeType.STRING)
    default String aggregateRuleTag() {
        return "default";
    }

    @AttributeDefinition(name = "%generateAggregateSql.name", description = "%generateAggregateSql.description", type = AttributeType.BOOLEAN)
    default Boolean generateAggregateSql() { return false; }

    @AttributeDefinition(name = "%disableCaching.name", description = "%disableCaching.description", type = AttributeType.BOOLEAN)
    default Boolean disableCaching() { return false; }

    @AttributeDefinition(name = "%disableLocalSegmentCache.name", description = "%disableLocalSegmentCache.description", type = AttributeType.BOOLEAN)
    default Boolean disableLocalSegmentCache() { return false; }

    @AttributeDefinition(name = "%enableTriggers.name", description = "%enableTriggers.description", type = AttributeType.BOOLEAN)
    default Boolean enableTriggers() { return true; }

    @AttributeDefinition(name = "%generateFormattedSql.name", description = "%generateFormattedSql.description", type = AttributeType.BOOLEAN)
    default Boolean generateFormattedSql() { return false; }

    @AttributeDefinition(name = "%enableNonEmptyOnAllAxis.name", description = "%enableNonEmptyOnAllAxis.description", type = AttributeType.BOOLEAN)
    default Boolean enableNonEmptyOnAllAxis() { return false; }

    @AttributeDefinition(name = "%expandNonNative.name", description = "%expandNonNative.description", type = AttributeType.BOOLEAN)
    default Boolean expandNonNative() { return false; }

    @AttributeDefinition(name = "%compareSiblingsByOrderKey.name", description = "%compareSiblingsByOrderKey.description", type = AttributeType.BOOLEAN)
    default Boolean compareSiblingsByOrderKey() { return false; }

    @AttributeDefinition(name = "%enableExpCache.name", description = "%enableExpCache.description", type = AttributeType.BOOLEAN)
    default Boolean enableExpCache() { return true; }

    @AttributeDefinition(name = "%testExpDependencies.name", description = "%testExpDependencies.description", type = AttributeType.INTEGER)
    default Integer testExpDependencies() { return 0; }

    @AttributeDefinition(name = "%testSeed.name", description = "%testSeed.description", type = AttributeType.INTEGER)
    default Integer testSeed() { return 1234; }

    @AttributeDefinition(name = "%localePropFile.name", description = "%localePropFile.description", type = AttributeType.INTEGER)
    default String localePropFile() { return null; }

}
