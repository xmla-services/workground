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
import org.eclipse.daanse.db.jdbc.util.impl.Constraint;
import org.eclipse.daanse.db.jdbc.util.impl.DBStructure;
import org.eclipse.daanse.db.jdbc.util.impl.Table;
import org.eclipse.daanse.db.jdbc.util.impl.Type;
import org.eclipse.daanse.xmla.api.XmlaService;
import org.eclipse.daanse.xmla.api.common.enums.CubeSourceEnum;
import org.eclipse.daanse.xmla.api.common.enums.LevelDbTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.LevelTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.VisibilityEnum;
import org.eclipse.daanse.xmla.api.discover.mdschema.cubes.MdSchemaCubesRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.cubes.MdSchemaCubesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.hierarchies.MdSchemaHierarchiesRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.hierarchies.MdSchemaHierarchiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.levels.MdSchemaLevelsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.levels.MdSchemaLevelsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.properties.MdSchemaPropertiesRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.properties.MdSchemaPropertiesResponseRow;
import org.eclipse.daanse.xmla.client.soapmessage.XmlaServiceClientImpl;
import org.eclipse.daanse.xmla.model.record.discover.PropertiesR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.cubes.MdSchemaCubesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.cubes.MdSchemaCubesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.demensions.MdSchemaDimensionsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.demensions.MdSchemaDimensionsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.hierarchies.MdSchemaHierarchiesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.hierarchies.MdSchemaHierarchiesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.levels.MdSchemaLevelsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.levels.MdSchemaLevelsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.properties.MdSchemaPropertiesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.properties.MdSchemaPropertiesRestrictionsR;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.eclipse.daanse.grabber.XmlaServiceClientHelper.getMdSchemaCubes;
import static org.eclipse.daanse.grabber.XmlaServiceClientHelper.getMdSchemaDimensions;
import static org.eclipse.daanse.grabber.XmlaServiceClientHelper.getMdSchemaHierarchies;
import static org.eclipse.daanse.grabber.XmlaServiceClientHelper.getMdSchemaLevels;
import static org.eclipse.daanse.grabber.XmlaServiceClientHelper.getMdSchemaProperties;

public class StructureProviderServiceImpl {

    private static final Integer START_LEVEL_NUMBER = 1;

    public DBStructure grabStructure(String targetSchemaName, List<String> endPoints) {
        Map<String, Table> factTablesMap = getFactTablesMap(targetSchemaName, endPoints);
        List<Table> tables = getDictionaries(targetSchemaName, endPoints);
        return new DBStructure(targetSchemaName, tables);
    }

    private Map<String, Table> getFactTablesMap(String targetSchemaName, List<String> endPoints) {
        Map<String, Table> map = new HashMap<>();
        if (endPoints != null) {
            endPoints.forEach(ep ->  fillFactMap(ep, targetSchemaName, map));
        }
        return map;
    }

    private List<Table> getDictionaries(String targetSchemaName, List<String> endPoints) {
        Map<String, Table> map = new HashMap<>();
        if (endPoints != null) {
            endPoints.forEach(ep ->  fillDictionaryMap(ep, targetSchemaName, map));
        }
        return new ArrayList<>(map.values());
    }
    private void fillFactMap(String ep, String targetSchemaName, Map<String, Table> map) {
        List<MdSchemaCubesResponseRow> cubes = getMdSchemaCubes(ep);
        List<MdSchemaDimensionsResponseRow> dimensions = getMdSchemaDimensions(ep);
        List<MdSchemaHierarchiesResponseRow> hierarchies = getMdSchemaHierarchies(ep);
        if (cubes != null) {
            List<Table> tables = cubes.stream().map(it ->
                new Table(targetSchemaName, getFactTableName(it.cubeName().get()), getFactConstraints(it.cubeName().get(), dimensions, hierarchies), getFactColumns(it.cubeName().get(), dimensions, hierarchies))).toList();
            tables.forEach(it -> {
                map.computeIfPresent(it.getTableName(), (key, value) -> unionTable(value, it));
                map.computeIfAbsent(it.getTableName(), k -> it);
            });
        }
    }

    private List<Constraint> getFactConstraints(String cubeName, List<MdSchemaDimensionsResponseRow> dimensions, List<MdSchemaHierarchiesResponseRow> hierarchies) {
        List<Constraint> constraints = new ArrayList<>();
        //TODO
        return constraints;
    }

    private String getFactTableName(String cubeName) {
        return cubeName;
    }

    private List<Column> getFactColumns(String cubeName, List<MdSchemaDimensionsResponseRow> dimensions, List<MdSchemaHierarchiesResponseRow> hierarchies) {
        List<Column> columns = new ArrayList<>();
        dimensions.forEach(it -> {
            if (it.cubeName().isPresent() && cubeName.equals(it.cubeName().get()) &&
                it.dimensionName().isPresent() && it.dimensionUniqueName().isPresent()) {
                List<MdSchemaHierarchiesResponseRow> dimensionHierarchies =
                    hierarchies.stream()
                        .filter(h -> h.hierarchyName().isPresent() &&
                            h.dimensionUniqueName().isPresent() &&
                            h.dimensionUniqueName().get().equals(it.dimensionUniqueName().get())
                        ).toList();
                columns.addAll(dimensionHierarchies.stream().map(dh ->
                    new Column(
                        getFactTableColumnName(it.dimensionName().get(),
                            dh.hierarchyName().get()), Type.LONG))
                    .toList());
            }
        });
        return columns;
    }

    private String getFactTableColumnName(String dimensionName, String hierarchyName) {
        return new StringBuilder(dimensionName).append("_").append(hierarchyName).toString();
    }

    private void fillDictionaryMap(String ep, String targetSchemaName, Map<String, Table> map) {
        List<MdSchemaLevelsResponseRow> levels = getMdSchemaLevels(ep);
        List<MdSchemaDimensionsResponseRow> dimensions = getMdSchemaDimensions(ep);
        List<MdSchemaHierarchiesResponseRow> hierarchies = getMdSchemaHierarchies(ep);
        List<MdSchemaPropertiesResponseRow> properties = getMdSchemaProperties(ep);
        List<MdSchemaLevelsResponseRow> regularLevels = levels.stream()
            .filter(it -> it.levelType().isPresent()
                && it.levelType().get().equals(LevelTypeEnum.REGULAR))
            .toList();
        List<Table> tables = regularLevels.stream().map(it -> {
            List<Constraint> constraints = new ArrayList<>();
            List<Column> columns = new ArrayList<>();
            columns.add(new Column("key", Type.LONG));
            columns.add(new Column("caption", Type.STRING));
            Optional<Column> parentColumn = getParentColumn(regularLevels, it);
            parentColumn.ifPresent(c -> columns.add(c));
            List<Column> propertyColumns = getPropertyColumns(properties, it);
            columns.addAll(propertyColumns);
            String dimensionName = getDimensionName(dimensions, it.dimensionUniqueName().get());
            String hierarchyName = getHierarchyName(hierarchies, it.hierarchyUniqueName().get());

            return new Table(
                targetSchemaName,
                getDictionaryTableName(dimensionName, hierarchyName, it.levelName().get()),
                constraints,
                columns
            );
        }).toList();
        tables.forEach(it -> {
            map.computeIfPresent(it.getTableName(), (key, value) -> unionTable(value, it));
            map.computeIfAbsent(it.getTableName(), k -> it);
        });

    }

    private String getDictionaryTableName(String dimensionName, String hierarchyName, String levelName) {
        return new StringBuilder(dimensionName).append("_")
            .append(hierarchyName).append("_").append(levelName).toString();
    }

    private String getHierarchyName(List<MdSchemaHierarchiesResponseRow> hierarchies, String hierarchyniqueName) {
        Optional<MdSchemaHierarchiesResponseRow> optional =
            hierarchies.stream().filter(it -> (it.hierarchyName().isPresent() &&
                it.hierarchyIsVisible().get().equals(hierarchyniqueName))).findFirst();
        if (optional.isPresent() && optional.get().hierarchyName().isPresent()) {
            return optional.get().hierarchyName().get();
        }
        return "";
    }

    private String getDimensionName(List<MdSchemaDimensionsResponseRow> dimensions, String dimensionUniqueName) {
        Optional<MdSchemaDimensionsResponseRow> optional =
            dimensions.stream().filter(it -> (it.dimensionUniqueName().isPresent() &&
            it.dimensionUniqueName().get().equals(dimensionUniqueName))).findFirst();
        if (optional.isPresent() && optional.get().dimensionName().isPresent()) {
            return optional.get().dimensionName().get();
        }
        return "";
    }

    private List<Column> getPropertyColumns(List<MdSchemaPropertiesResponseRow> properties, MdSchemaLevelsResponseRow row) {
        if (row.levelUniqueName().isPresent()) {
            String levelUniqueName = row.levelUniqueName().get();
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

    private Type getType(Optional<LevelDbTypeEnum> dataType) {
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

    private Table unionTable(Table oldTable, Table newTable) {
        //TODO
        return newTable;
    }

    private Optional<Column> getParentColumn(List<MdSchemaLevelsResponseRow> regularLevels, MdSchemaLevelsResponseRow row) {
        if (row.levelNumber().isPresent() && row.hierarchyUniqueName().isPresent() && row.levelNumber().get() > START_LEVEL_NUMBER) {
            Integer levelNumber = row.levelNumber().get();
            String hierarchyUniqueName = row.hierarchyUniqueName().get();
            Optional<MdSchemaLevelsResponseRow> rowOptional = regularLevels.stream()
                .filter(it ->
                    it.levelNumber().isPresent() &&
                        it.hierarchyUniqueName().isPresent() &&
                        it.levelNumber().get() == (levelNumber - 1) &&
                        it.hierarchyUniqueName().get().equals(hierarchyUniqueName)).findFirst();
            if (rowOptional.isPresent()) {
                return Optional.of(new Column("parent", Type.STRING));
            }
        }
        return Optional.empty();
    }
}
