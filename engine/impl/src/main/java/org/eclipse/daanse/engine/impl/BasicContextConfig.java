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

    @AttributeDefinition(name = "%aggregateRuleTag.name", description = "%aggregateRuleTag.description")
    default String aggregateRuleTag() {
        return "default";
    }

    @AttributeDefinition(name = "%generateAggregateSql.name", description = "%generateAggregateSql.description")
    default Boolean generateAggregateSql() { return false; }
}
