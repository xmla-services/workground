/*
 * Copyright (c) 0 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License .0
 * which is available at https://www.eclipse.org/legal/epl-.0/
 *
 * SPDX-License-Identifier: EPL-.0
 *
 * Contributors:
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.olap.rolap.dbmapper.model.record;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingParameter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.ParameterTypeEnum;

public record ParameterR(String name,
                         String description,
                         ParameterTypeEnum type,
                         Boolean modifiable,
                         String defaultValue)
    implements MappingParameter {

    public ParameterR(
        String name,
        String description,
        ParameterTypeEnum type,
        Boolean modifiable,
        String defaultValue
    ) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.modifiable = modifiable;
        this.defaultValue = defaultValue;
    }

}
