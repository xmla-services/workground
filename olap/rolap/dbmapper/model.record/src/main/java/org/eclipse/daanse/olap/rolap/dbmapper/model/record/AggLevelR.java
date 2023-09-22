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
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.olap.rolap.dbmapper.model.record;

import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggLevel;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggLevelProperty;

public record AggLevelR(String column,
                        String name,
                        String ordinalColumn,
                        String nameColumn,
                        String captionColumn,
                        Boolean collapsed,
                        List<MappingAggLevelProperty> properties)
        implements MappingAggLevel {

}
