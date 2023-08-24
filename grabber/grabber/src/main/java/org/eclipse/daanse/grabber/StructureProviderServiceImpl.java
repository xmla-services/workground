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
import org.eclipse.daanse.grabber.api.StructureProviderService;
import org.eclipse.daanse.xmla.api.common.enums.LevelTypeEnum;
import org.eclipse.daanse.xmla.api.discover.mdschema.cubes.MdSchemaCubesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.hierarchies.MdSchemaHierarchiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.levels.MdSchemaLevelsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.measures.MdSchemaMeasuresResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.properties.MdSchemaPropertiesResponseRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.eclipse.daanse.grabber.GrabberUtils.getDimensionNameByUniqueName;
import static org.eclipse.daanse.grabber.GrabberUtils.getFactColumns;
import static org.eclipse.daanse.grabber.GrabberUtils.getFactTableName;
import static org.eclipse.daanse.grabber.GrabberUtils.getHierarchyNameByUniqueName;
import static org.eclipse.daanse.grabber.GrabberUtils.getPropertyColumns;
import static org.eclipse.daanse.grabber.XmlaServiceClientHelper.getMdSchemaCubes;
import static org.eclipse.daanse.grabber.XmlaServiceClientHelper.getMdSchemaDimensions;
import static org.eclipse.daanse.grabber.XmlaServiceClientHelper.getMdSchemaHierarchies;
import static org.eclipse.daanse.grabber.XmlaServiceClientHelper.getMdSchemaLevels;
import static org.eclipse.daanse.grabber.XmlaServiceClientHelper.getMdSchemaMeasures;
import static org.eclipse.daanse.grabber.XmlaServiceClientHelper.getMdSchemaProperties;

public class StructureProviderServiceImpl implements StructureProviderService {

    private static final Integer START_LEVEL_NUMBER = 1;

    @Override
    public DBStructure grabStructure(String targetSchemaName, List<String> endPoints) {
        Map<String, Table> factTablesMap = getFactTablesMap(targetSchemaName, endPoints);
        List<Table> tables = getDictionaries(targetSchemaName, endPoints);
        tables.addAll(factTablesMap.values());
        //add source table;
        tables.add(new Table(targetSchemaName, "sources", List.of(), getSourcesColumns()));
        return new DBStructure(targetSchemaName, tables);
    }

    private List<Column> getSourcesColumns() {
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("source_id", Type.LONG));
        columns.add(new Column("source", Type.STRING));
        return columns;
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
        List<MdSchemaMeasuresResponseRow> measures = getMdSchemaMeasures(ep);
        if (cubes != null) {
            List<Table> tables = cubes.stream().map(it ->
                new Table(targetSchemaName,
                    getFactTableName(it.cubeName().get()),
                    getFactConstraints(it.cubeName().get(), dimensions, hierarchies),
                    getFactColumns(it.cubeName().get(), dimensions, measures))).toList();

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
            parentColumn.ifPresent(columns::add);
            List<Column> propertyColumns = getPropertyColumns(properties, it);
            columns.addAll(propertyColumns);
            String dimensionName = getDimensionNameByUniqueName(dimensions, it.dimensionUniqueName().get());
            String hierarchyName = getHierarchyNameByUniqueName(hierarchies, it.hierarchyUniqueName().get());

            return new Table(
                targetSchemaName,
                getDictionaryTableName(it.cubeName().get(), dimensionName, hierarchyName, it.levelName().get()),
                constraints,
                columns
            );
        }).toList();
        tables.forEach(it -> {
            map.computeIfPresent(it.getTableName(), (key, value) -> unionTable(value, it));
            map.computeIfAbsent(it.getTableName(), k -> it);
        });

    }

    private String getDictionaryTableName(String cubeName, String dimensionName, String hierarchyName, String levelName) {
        return new StringBuilder(cubeName).append("_").append(dimensionName).append("_")
            .append(hierarchyName).append("_").append(levelName).toString();
    }


    private Table unionTable(Table oldTable, Table newTable) {
        //TODO
        return newTable;
    }

    private Optional<Column> getParentColumn(List<MdSchemaLevelsResponseRow> regularLevels, MdSchemaLevelsResponseRow row) {
        Optional<Integer> optionalLevelNumber = row.levelNumber();
        Optional<String> optionalHierarchyUniqueName = row.hierarchyUniqueName();
        if (optionalLevelNumber.isPresent() && optionalHierarchyUniqueName.isPresent()
            && optionalLevelNumber.get() > START_LEVEL_NUMBER) {
            Integer levelNumber = optionalLevelNumber.get();
            String hierarchyUniqueName = optionalHierarchyUniqueName.get();
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
