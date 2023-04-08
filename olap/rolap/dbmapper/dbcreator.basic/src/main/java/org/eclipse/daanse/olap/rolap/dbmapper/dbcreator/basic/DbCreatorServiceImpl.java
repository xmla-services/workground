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
package org.eclipse.daanse.olap.rolap.dbmapper.dbcreator.basic;

import org.eclipse.daanse.db.jdbc.util.api.DatabaseCreatorService;
import org.eclipse.daanse.db.jdbc.util.impl.Column;
import org.eclipse.daanse.db.jdbc.util.impl.Constraint;
import org.eclipse.daanse.db.jdbc.util.impl.DBStructure;
import org.eclipse.daanse.db.jdbc.util.impl.Type;
import org.eclipse.daanse.olap.rolap.dbmapper.dbcreator.api.DbCreatorService;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.ColumnDef;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Cube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.CubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Hierarchy;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.InlineTable;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Join;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Level;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Measure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.PrivateDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Property;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.RelationOrJoin;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DbCreatorServiceImpl implements DbCreatorService {

    private DataSource dataSource;
    private DatabaseCreatorService databaseCreatorService;

    public DbCreatorServiceImpl(DataSource dataSource, DatabaseCreatorService databaseCreatorService) {
        this.dataSource = dataSource;
        this.databaseCreatorService = databaseCreatorService;
    }

    @Override
    public DBStructure createSchema(Schema schema) throws SQLException {
        DBStructure dbStructure = getDBStructure(schema);
        databaseCreatorService.createDatabaseSchema(dataSource, dbStructure);
        return dbStructure;
    }

    private DBStructure getDBStructure(Schema schema) {

        String schemaName = schema.name();
        Map<String, Table> tables = new HashMap<>();
        if (schema.dimension() != null) {
            schema.dimension().forEach(d -> processingDimension(d, tables, null, schemaName));
        }
        if (schema.cube() != null) {
            schema.cube().forEach(c -> processingCube(c, tables, schemaName));
        }
        List<org.eclipse.daanse.db.jdbc.util.impl.Table> tList = tables.values().stream().map(t -> new org.eclipse.daanse.db.jdbc.util.impl.Table(
            t.getSchema(),
            t.getName(),
            t.getConstraint().values().stream().toList(),
            t.getColumns().values().stream().toList())).toList();
        return new DBStructure(schemaName, tList);
    }

    private void processingCube(Cube cube, Map<String, Table> tables, String schemaName) {
        if (cube != null) {
            String tableName = null;
            if (cube.fact() != null) {
                tableName = processingRelation(cube.fact(), tables, schemaName);
            }
            if (cube.dimensionUsageOrDimension() != null) {
                String tName = tableName;
                cube.dimensionUsageOrDimension().forEach(d -> processingDimension(d, tables, tName, schemaName));
            }
            if (cube.measure() != null) {
                String tName = tableName;
                cube.measure().forEach(m -> processingMeasure(m, tables, tName, schemaName));
            }
        }
    }

    private void processingMeasure(Measure m, Map<String, Table> tables, String tableName, String schemaName) {

        if (m.column() != null) {
            String columnName = m.column();
            if (tableName != null) {
                Table t = getTableOrCreateNew(tables, tableName, schemaName);
                getColumnOrCreateNew(
                    t.getColumns(), columnName, Type.fromName(m.datatype() != null ? m.datatype().name() : null));
            }
        }
    }

    private void processingDimension(CubeDimension d, Map<String, Table> tables, String tableName, String schemaName) {
        if (d instanceof PrivateDimension) {
            PrivateDimension privateDimension = (PrivateDimension) d;
            if (privateDimension.hierarchy() != null) {
                privateDimension.hierarchy().forEach(h -> processingHierarchy(h, tables, schemaName));
            }
        }
        if (tableName != null) {
                Table table = getTableOrCreateNew(tables, tableName, schemaName);
                if (d.foreignKey() != null) {
                    getColumnOrCreateNew(table.getColumns(), d.foreignKey(), Type.INTEGER);
                    getConstraintOrCreateNew(table.getConstraint(), d.foreignKey(),
                        true, List.of(d.foreignKey()));
                }
        }
    }

    private void processingHierarchy(Hierarchy h, Map<String, Table> tables, String schemaName) {
        if (h.relation() != null) {
            String tName = processingRelation(h.relation(), tables, schemaName);
            if (h.level() != null) {
                h.level().forEach(l -> processingLevel(l, tables, tName, schemaName));
            }
            if (h.primaryKey() != null && (tName != null || h.primaryKeyTable() != null)) {
                    String t = h.primaryKeyTable() != null ? h.primaryKeyTable() : tName;
                    Table table = getTableOrCreateNew(tables, t, schemaName);
                    getColumnOrCreateNew(table.getColumns(), h.primaryKey(), Type.INTEGER);
                    getConstraintOrCreateNew(table.getConstraint(), h.primaryKey(), true, List.of(h.primaryKey()));
            }
        }
    }

    private String processingRelation(RelationOrJoin relation, Map<String, Table> tables, String schemaName) {
        if (relation instanceof org.eclipse.daanse.olap.rolap.dbmapper.model.api.Table) {
            return processingTable((org.eclipse.daanse.olap.rolap.dbmapper.model.api.Table) relation, tables, schemaName);
        }
        if (relation instanceof Join) {
            return processingJoin((Join) relation, tables, schemaName);
        }
        if (relation instanceof InlineTable) {
            return processingInlineTable((InlineTable) relation, tables, schemaName);
        }
        return null;
    }

    private String processingInlineTable(InlineTable table, Map<String, Table> tables, String schemaName) {
        if (table.alias() != null) {
            Table t = getTableOrCreateNew(tables, table.alias(), schemaName);
            if (table.columnDefs() != null) {
                table.columnDefs().forEach(c -> processingColumnDef(c, t.getColumns()));
            }
            return table.alias();
        }
        return null;
    }

    private void processingColumnDef(ColumnDef c, Map<String, Column> columns) {
        if (!columns.containsKey(c.name())) {
            columns.put(c.name(), new Column(c.name(), Type.fromName(c.type() != null ? c.type().name() : null)));
        }
    }

    private String processingTable(
        org.eclipse.daanse.olap.rolap.dbmapper.model.api.Table table,
        Map<String, Table> tables,
        String schemaName
    ) {
        if (table.name() != null) {
            getTableOrCreateNew(tables, table.name(), schemaName);
            return table.name();
        }
        return null;
    }

    private String processingJoin(Join relation, Map<String, Table> tables, String schemaName) {
        if (relation.relation() != null) {
            relation.relation().forEach(r -> processingRelation(r, tables, schemaName));
        }
        return null;
    }

    private void processingLevel(Level level, Map<String, Table> tables, String tableName, String schema) {
        String tName = level.table() != null ? level.table() : tableName;
        if (tName != null) {
            Table t = getTableOrCreateNew(tables, tName, schema);
            Type type = Type.fromName(level.type() != null ? level.type().name() : null);
            if (level.column() != null) {
                getColumnOrCreateNew(t.getColumns(), level.column(), type);
            }
            if (level.parentColumn() != null) {
                getColumnOrCreateNew(t.getColumns(), level.parentColumn(), type);
            }
            if (level.nameColumn() != null) {
                getColumnOrCreateNew(t.getColumns(), level.nameColumn(), Type.STRING);
            }
            if (level.captionColumn() != null) {
                getColumnOrCreateNew(t.getColumns(), level.captionColumn(), Type.STRING);
            }
            if (level.ordinalColumn() != null) {
                getColumnOrCreateNew(t.getColumns(), level.ordinalColumn(), Type.INTEGER);
            }
        }
        if (level.property() != null) {
            level.property().forEach(p -> processingProperty(p, tables, tName, schema));
        }

    }

    private void processingProperty(Property property, Map<String, Table> tables, String tableName, String schema) {
        if (property.column() != null) {
            String columnName = property.column();
            if (tableName != null) {
                Table t = getTableOrCreateNew(tables, tableName, schema);
                getColumnOrCreateNew(t.getColumns(), columnName, Type.fromName(property.type() != null ? property.type().name() : null));
            }
        }
    }

    private Table getTableOrCreateNew(Map<String, Table> tables, String tableName, String schema) {
        Table table;
        if (!tables.containsKey(tableName)) {
            table = new Table(tableName, schema);
            tables.put(tableName, table);
        } else {
            table = tables.get(tableName);
        }
        return table;
    }

    private Column getColumnOrCreateNew(Map<String, Column> columnsMap, String columnName, Type type) {
        Column column;
        if (!columnsMap.containsKey(columnName)) {
            column = new Column(columnName, type);
            columnsMap.put(columnName, column);
        } else {
            column = columnsMap.get(columnName);
        }
        return column;
    }

    private void getConstraintOrCreateNew(Map<String, Constraint> constraintMap, String primaryKey, boolean unique, List<String> columnNames) {
        Constraint constrain;
        if (!constraintMap.containsKey(primaryKey)) {
            constrain = new Constraint(primaryKey, unique, columnNames);
            constraintMap.put(primaryKey, constrain);
        }

    }

}
