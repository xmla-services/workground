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
package org.eclipse.daanse.olap.rolap.dbmapper.schemacreator.basic;

import org.eclipse.daanse.db.jdbc.metadata.api.JdbcMetaDataService;
import org.eclipse.daanse.db.jdbc.metadata.api.JdbcMetaDataServiceFactory;
import org.eclipse.daanse.db.jdbc.metadata.impl.ForeignKey;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Annotation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Cube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.CubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Hierarchy;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Join;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Level;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Measure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.NamedSet;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Parameter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.PrivateDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Relation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.RelationOrJoin;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Role;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Table;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.UserDefinedFunction;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.VirtualCube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.DimensionTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.LevelTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.TypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.CubeR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.HierarchyR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.JoinR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.LevelR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.PrivateDimensionR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.SchemaR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.TableR;
import org.eclipse.daanse.olap.rolap.dbmapper.schemacreator.api.SchemaCreatorService;
import org.eclipse.daanse.olap.rolap.dbmapper.schemacreator.api.SchemaInitData;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SchemaCreatorServiceImpl implements SchemaCreatorService {

    private final JdbcMetaDataServiceFactory jmdsf;
    private final DataSource dataSource;

    public SchemaCreatorServiceImpl(DataSource dataSource, JdbcMetaDataServiceFactory jmdsf) {
        this.dataSource = dataSource;
        this.jmdsf = jmdsf;
    }

    @Override
    public Schema createSchema(SchemaInitData sid) {
        Schema schema = null;
        try (Connection connection = dataSource.getConnection()) {
            JdbcMetaDataService jmds = jmdsf.create(connection);
            String schemaName = connection.getSchema();
            String description = schemaName;
            String measuresCaption = null;
            String defaultRole = null;
            List<Annotation> annotations = List.of();
            List<Parameter> parameters = List.of();
            List<PrivateDimension> sharedDimensions = getSharedDimensions(schemaName, sid.getFactTables(), jmds);
            List<Cube> cubes = getCubes(sid.getFactTables(), jmds);
            List<VirtualCube> virtualCubes = List.of();
            List<NamedSet> namedSets = List.of();
            List<Role> roles = List.of();
            List<UserDefinedFunction> userDefinedFunctions = List.of();

            schema = new SchemaR(schemaName,
                description,
                measuresCaption,
                defaultRole,
                annotations,
                parameters,
                sharedDimensions,
                cubes,
                virtualCubes,
                namedSets,
                roles,
                userDefinedFunctions);

        } catch (SQLException e) {
            throw new SchemaCreatorException("createSchema error", e);
        }
        return schema;
    }

    private List<PrivateDimension> getSharedDimensions(
        String schemaName,
        List<String> factTables,
        JdbcMetaDataService jmds
    ) {

        if (factTables != null) {
            List<ForeignKey> list = factTables.stream()
                .flatMap(ft -> jmds.getForeignKeys(schemaName, ft).stream())
                .toList();
            if (!list.isEmpty()) {
                //get origen foreign key by Pk
                Map<String, ForeignKey> foreignKeyMap = list.stream().collect(
                    Collectors.toMap(this::getKey, fk -> fk,
                        (oldValue, newValue) -> oldValue
                    )
                );
                return foreignKeyMap.values()
                    .stream()
                    .map(fk -> getSharedDimensions(schemaName, fk, jmds))
                    .toList();
            }

        }
        return List.of();
    }

    private String getKey(ForeignKey fk) {
        return new StringBuilder()
            .append(fk.getPkTableName())
            .append(fk.getPkColumnName())
            .toString();
    }

    private PrivateDimension getSharedDimensions(String schemaName, ForeignKey fk, JdbcMetaDataService jmds) {
        List<Hierarchy> hierarchies = getHierarchies(schemaName, fk, jmds);
        String description = new StringBuilder("Dimension for ").append(fk.getFkColumnName()).toString();
        return new PrivateDimensionR(getDimensionName(fk), DimensionTypeEnum.STANDARD_DIMENSION,
            getDimensionCaption(fk),
            description,
            fk.getPkColumnName(),
            true,
            List.of(),
            hierarchies,
            true,
            List.of(),
            null);
    }

    private String getDimensionCaption(ForeignKey fk) {
        return new StringBuilder().append(fk.getPkTableName()).toString();
    }

    private List<Hierarchy> getHierarchies(String schemaName, ForeignKey fk, JdbcMetaDataService jmds) {
        List<RelationOrJoin> relationList = getHierarchyRelation(schemaName, fk, jmds);
        List<Hierarchy> result = new ArrayList<>();
        for (RelationOrJoin relation : relationList) {
            Hierarchy hierarchy = new HierarchyR(getHierarchyName(fk),
                getHierarchyCaption(fk),
                getHierarchyDescription(fk),
                List.of(),
                null,
                null,
                null,
                null,
                getHierarchyLevels(schemaName, relation, fk.getPkTableName(), fk.getPkColumnName(), jmds),
                List.of(),
                true,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                true,
                null,
                relation,
                null);
            result.add(hierarchy);
        }
        return result;
    }

    private List<RelationOrJoin> getHierarchyRelation(String schemaName, ForeignKey fk, JdbcMetaDataService jmds) {
        List<ForeignKey> listFK = jmds.getForeignKeys(schemaName, fk.getPkTableName());
        if (listFK != null && !listFK.isEmpty()) {
            List<RelationOrJoin> result = new ArrayList<>();
            for (ForeignKey foreignKey : listFK) {
                List<RelationOrJoin> rightRelations = getHierarchyRelation(schemaName, foreignKey, jmds);
                for (RelationOrJoin relationOrJoin : rightRelations) {
                    List<RelationOrJoin> relations = List.of(
                        new TableR(fk.getPkTableName()),
                        relationOrJoin);
                    result.add(new JoinR(relations,
                        null,
                        listFK.get(0).getFkColumnName(),
                        null,
                        foreignKey.getPkColumnName()));
                }
            }
            return result;
        }
        return List.of(new TableR(fk.getPkTableName()));
    }

    private List<Level> getHierarchyLevels(
        String schemaName,
        RelationOrJoin relation,
        String tableName,
        String columnName,
        JdbcMetaDataService jmds
    ) {
        List<Level> result = new ArrayList<>();
        if (relation instanceof Join join) {
            if (join.relations() != null && join.relations().size() > 1) {
                if (join.relations().get(1) instanceof Table t) {
                    List<ForeignKey> listFK = jmds.getForeignKeys(schemaName, tableName);
                    ForeignKey key =
                        listFK.stream().filter(k -> t.name().equals(k.getPkTableName())).findFirst().orElse(null);
                    if (key != null) {
                        result.addAll(getHierarchyLevels(schemaName, t, key.getPkTableName(), key.getPkColumnName(),
                            jmds));
                    }
                } else if (join.relations().get(1) instanceof Join j) {
                    Table t = getFistTable(j);
                    if (t != null) {
                        List<ForeignKey> listFK = jmds.getForeignKeys(schemaName, tableName);
                        ForeignKey key =
                            listFK.stream().filter(k -> t.name().equals(k.getPkTableName())).findFirst().orElse(null);
                        if (key != null) {
                            result.addAll(getHierarchyLevels(schemaName, j, key.getPkTableName(),
                                key.getPkColumnName(), jmds));
                        }
                    }
                }
                if (join.relations().get(0) instanceof Table t) {
                    result.addAll(getHierarchyLevels(schemaName, t, tableName, columnName, jmds));
                }
            }
        }
        if (relation instanceof Table table) {
            Level l = new LevelR(getLevelName(tableName),
                tableName,
                columnName,
                getColumnNameByPostfix(schemaName, table.name(), columnName, "name", jmds),
                getLevelOrdinalName(table.name(), columnName),
                getLevelParentColumn(table.name(), columnName),
                getLevelNullParentValue(table.name(), columnName),
                getLevelColumnType(schemaName, table.name(), columnName, jmds),
                getLevelApproxRowCount(table.name(), columnName),
                true,
                getLevelType(table.name(), columnName),
                null,
                null,
                getLevelCaption(table.name(), columnName),
                getLevelDescription(table.name()),
                getLevelCaptionColumn(table.name(), columnName),
                List.of(),
                null,
                null,
                null,
                null,
                null,
                null,
                List.of(),
                true,
                null,
                null);
            result.add(l);
        }
        return result;
    }

    private String getLevelName(String tableName) {
        return capitalize(tableName);
    }

    private Table getFistTable(Join j) {
        if (j.relations().get(0) instanceof Table t) {
            return t;
        }
        if (j.relations().get(1) instanceof Table t) {
            return t;
        }
        if (j.relations().get(0) instanceof Join join) {
            return getFistTable(join);
        }
        return null;
    }

    private String getLevelCaptionColumn(String table, String columnName) {
        //TODO
        return null;
    }

    private String getLevelDescription(String table) {
        return capitalize(table);
    }

    private String getLevelCaption(String table, String columnName) {
        return null;
    }

    private LevelTypeEnum getLevelType(String table, String columnName) {
        return null;
    }

    private String getLevelApproxRowCount(String table, String columnName) {
        return null;
    }

    private TypeEnum getLevelColumnType(String schemaName, String table, String columnName, JdbcMetaDataService jmds) {
        try {
            Optional<String> type = jmds.getColumnDataTypeName(schemaName, table, columnName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private String getLevelNullParentValue(String table, String columnName) {
        return null;
    }

    private String getLevelParentColumn(String table, String columnName) {
        return null;
    }

    private String getLevelOrdinalName(String table, String columnName) {
        return null;
    }

    private String getColumnNameByPostfix(
        String schemaName, String tableName, String columnName, String postfix, JdbcMetaDataService jmds
    ) {
        try {
            if (jmds.doesColumnExist(schemaName, tableName, postfix)) {
                return postfix;
            }
            String cName = new StringBuilder(columnName).append("_").append(postfix).toString();
            if (jmds.doesColumnExist(schemaName, tableName, cName)) {
                return cName;
            }
        } catch (SQLException e) {
            throw new SchemaCreatorException("ColumnExist error", e);
        }
        return null;
    }

    private String getHierarchyDescription(ForeignKey fk) {
        return new StringBuilder("Description for hierarchy")
            .append(fk.getPkTableName())
            .append("_")
            .append(fk.getPkColumnName()).toString();
    }

    private String getHierarchyCaption(ForeignKey fk) {
        return new StringBuilder("Caption for hierarchy")
            .append(fk.getPkTableName())
            .append("_")
            .append(fk.getPkColumnName()).toString();
    }

    private String getHierarchyName(ForeignKey fk) {
        return new StringBuilder(fk.getPkTableName())
            .append("_")
            .append(fk.getPkColumnName()).toString();
    }

    private String getDimensionName(ForeignKey fk) {
        return new StringBuilder("Dimension ").append(capitalize(fk.getPkTableName())).toString();
    }

    private List<Cube> getCubes(List<String> tables, JdbcMetaDataService jmds) {
        if (tables != null) {
            return tables.stream().map(t -> getCube(t, jmds)).toList();
        }
        return List.of();
    }

    private Cube getCube(String tableName, JdbcMetaDataService jmds) {
        String name = tableName;
        String caption = tableName;
        String description = tableName;
        String defaultMeasure = null;
        List<CubeDimension> dimensionUsageOrDimensions = getCubeDimensions(tableName, jmds);
        List<Measure> measures = getMeasures(tableName, jmds);
        Relation fact = getRelation(tableName, jmds);
        return new CubeR(name,
            caption,
            description,
            defaultMeasure,
            List.of(),
            dimensionUsageOrDimensions,
            measures,
            List.of(),
            List.of(),
            List.of(),
            List.of(),
            true,
            true,
            true,
            fact,
            List.of());
    }

    private Relation getRelation(String tableName, JdbcMetaDataService jmds) {
        //TODO
        return null;
    }

    private List<Measure> getMeasures(String tableName, JdbcMetaDataService jmds) {
        //TODO
        return List.of();
    }

    private List<CubeDimension> getCubeDimensions(String tableName, JdbcMetaDataService jmds) {
        //TODO
        return List.of();
    }

    private String capitalize(String str) {
        if (str != null && !str.isBlank()) {
            return new StringBuilder(str.substring(0, 1).toUpperCase())
                .append(str.substring(1)).toString();
        }
        return str;
    }
}
