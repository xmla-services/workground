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
package org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.adapter;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.DimensionTypeEnum;

public class DimensionTypeAdaptor  extends Adaptor<DimensionTypeEnum> {

    protected DimensionTypeAdaptor() {
        super(DimensionTypeEnum.class);
    }

    @Override
    public Enum<DimensionTypeEnum> unmarshal(String v) {
        return DimensionTypeEnum.fromValue(v);
    }

}
