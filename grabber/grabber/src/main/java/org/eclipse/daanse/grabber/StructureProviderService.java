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
import org.eclipse.daanse.xmla.api.discover.mdschema.levels.MdSchemaLevelsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.levels.MdSchemaLevelsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.properties.MdSchemaPropertiesRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.properties.MdSchemaPropertiesResponseRow;
import org.eclipse.daanse.xmla.client.soapmessage.XmlaServiceClientImpl;
import org.eclipse.daanse.xmla.model.record.discover.PropertiesR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.levels.MdSchemaLevelsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.levels.MdSchemaLevelsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.properties.MdSchemaPropertiesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.properties.MdSchemaPropertiesRestrictionsR;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StructureProviderService {

    private static final Integer START_LEVEL_NUMBER = 1;

    public DBStructure grabStructure(String targetSchemaName, List<String> endPoints) {
        List<Table> tables = getDictionarys(targetSchemaName, endPoints);
        return new DBStructure(targetSchemaName, tables);
    }

    private List<Table> getDictionarys(String targetSchemaName, List<String> endPoints) {
        Map<String, Table> map = new HashMap<>();
        if (endPoints != null) {
            endPoints.forEach(ep ->  fillDictionaryMap(ep, targetSchemaName, map));
        }
        return new ArrayList<>(map.values());
    }

    private void fillDictionaryMap(String ep, String targetSchemaName, Map<String, Table> map) {
        List<MdSchemaLevelsResponseRow> levels = getMdSchemaLevels(ep);
        List<MdSchemaPropertiesResponseRow> properties = getMdSchemaProperties(ep);
        List<MdSchemaLevelsResponseRow> regularLevels = levels.stream()
            .filter(it -> it.levelType().isPresent()
                && it.levelType().get().equals(LevelTypeEnum.REGULAR))
            .toList();
        List<Table> tables = regularLevels.stream().map(it -> {
            List<Constraint> constraints = new ArrayList<>();
            List<Column> columns = new ArrayList<>();
            columns.add(new Column("key", Type.STRING));
            columns.add(new Column("caption", Type.STRING));
            Optional<Column> parentColumn = getParentColumn(regularLevels, it);
            parentColumn.ifPresent(c -> columns.add(c));
            List<Column> propertyColumns = getpropertyColumns(properties, it);
            columns.addAll(propertyColumns);

            return new Table(
                targetSchemaName,
                it.levelUniqueName().get(),
                constraints,
                columns
            );
        }).toList();
        tables.forEach(it -> {
            map.computeIfPresent(it.getTableName(), (key, value) -> unionTable(value, it));
            map.computeIfAbsent(it.getTableName(), k -> it);
        });

    }

    private List<Column> getpropertyColumns(List<MdSchemaPropertiesResponseRow> properties, MdSchemaLevelsResponseRow row) {
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

    private List<MdSchemaLevelsResponseRow> getMdSchemaLevels(String endPointUrl) {
        XmlaService client = new XmlaServiceClientImpl(endPointUrl);
        PropertiesR properties = new PropertiesR();
        MdSchemaLevelsRestrictionsR restrictions = new MdSchemaLevelsRestrictionsR(
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(CubeSourceEnum.CUBE),
            Optional.of(VisibilityEnum.VISIBLE)
        );
        MdSchemaLevelsRequest request = new MdSchemaLevelsRequestR(properties, restrictions);
        return client.discover().mdSchemaLevels(request);
    }

    private List<MdSchemaPropertiesResponseRow> getMdSchemaProperties(String endPointUrl) {
        XmlaService client = new XmlaServiceClientImpl(endPointUrl);
        PropertiesR properties = new PropertiesR();
        MdSchemaPropertiesRestrictionsR restrictions = new MdSchemaPropertiesRestrictionsR(
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(CubeSourceEnum.CUBE),
            Optional.of(VisibilityEnum.VISIBLE)
        );
        MdSchemaPropertiesRequest request = new MdSchemaPropertiesRequestR(properties, restrictions);
        return client.discover().mdSchemaProperties(request);
    }

}
