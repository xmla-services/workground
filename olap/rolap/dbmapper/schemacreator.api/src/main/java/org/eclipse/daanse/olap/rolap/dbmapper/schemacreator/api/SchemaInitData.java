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
package org.eclipse.daanse.olap.rolap.dbmapper.schemacreator.api;

import java.util.List;

public class SchemaInitData {

    private List<String> factTables;
    private List<DimensionData> dimensionData;

    public List<String> getFactTables() {
        return factTables;
    }

    public void setFactTables(List<String> factTables) {
        this.factTables = factTables;
    }

    public List<DimensionData> getDimensionData() {
        return dimensionData;
    }

    public void setDimensionData(List<DimensionData> dimensionData) {
        this.dimensionData = dimensionData;
    }
}
