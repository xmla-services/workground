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
package org.eclipse.daanse.grabber;

import org.eclipse.daanse.db.jdbc.util.impl.Column;
import org.eclipse.daanse.db.jdbc.util.impl.Type;
import org.eclipse.daanse.xmla.api.common.enums.LevelDbTypeEnum;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.hierarchies.MdSchemaHierarchiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.levels.MdSchemaLevelsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.properties.MdSchemaPropertiesResponseRow;

import java.util.List;
import java.util.Optional;

public class GrabberUtils {

    private GrabberUtils() {
        // constructor
    }

    static String getDimensionNameByUniqueName(List<MdSchemaDimensionsResponseRow> dimensions, String dimensionUniqueName) {
        Optional<MdSchemaDimensionsResponseRow> optional =
            dimensions.stream().filter(it -> (it.dimensionUniqueName().isPresent() &&
                it.dimensionUniqueName().get().equals(dimensionUniqueName))).findFirst();
        if (optional.isPresent()) {
            Optional<String> optionalDimensionName = optional.get().dimensionName();
            if (optionalDimensionName.isPresent()) {
                return optionalDimensionName.get();
            }
        }
        return null;
    }

    static String getHierarchyNameByUniqueName(List<MdSchemaHierarchiesResponseRow> hierarchies, String hierarchyUniqueName) {
        Optional<MdSchemaHierarchiesResponseRow> optional =
            hierarchies.stream().filter(it -> (it.hierarchyName().isPresent() &&
                it.hierarchyUniqueName().get().equals(hierarchyUniqueName))).findFirst();
        if (optional.isPresent() && optional.get().hierarchyName().isPresent()) {
            Optional<String> optionalHierarchyName = optional.get().hierarchyName();
            if (optionalHierarchyName.isPresent()) {
                return optionalHierarchyName.get();
            }
        }
        return null;
    }

    static List<Column> getPropertyColumns(List<MdSchemaPropertiesResponseRow> properties, MdSchemaLevelsResponseRow row) {
        Optional<String> optionalLevelUniqueName = row.levelUniqueName();
        if (optionalLevelUniqueName.isPresent()) {
            String levelUniqueName = optionalLevelUniqueName.get();
            List<MdSchemaPropertiesResponseRow> props = properties.stream()
                .filter(it -> (
                    it.levelUniqueName().isPresent() &&
                        it.propertyName().isPresent() &&
                        it.levelUniqueName().get().equals(levelUniqueName))
                ).toList();
            return props.stream()
                .map(it ->
                    new Column(it.propertyName().get(), getType(it.dataType()))
                ).toList();

        }
        return List.of();
    }

    private static Type getType(Optional<LevelDbTypeEnum> dataType) {
        if (dataType.isPresent()) {
            switch (dataType.get()) {
                case DBTYPE_WSTR:
                    return Type.STRING;
                //TODO
                default:
                    return Type.STRING;
            }
        }
        return Type.STRING;
    }

}
