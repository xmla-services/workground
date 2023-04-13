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
package org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.jdbc;

import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Cause.DATABASE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.ERROR;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.AGGREGATOR_IS_NOT_VALID_FOR_THE_DATA_TYPE_OF_THE_COLUMN;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.COLUMN_0_DOES_NOT_EXIST_IN_DIMENSION_TABLE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.COLUMN_DEFINED_IN_FIELD_DOES_NOT_EXIST_IN_TABLE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.COLUMN_S_DEFINED_IN_FIELD_DOES_NOT_EXIST_IN_TABLE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.COLUMN_S_DEFINED_IN_FIELD_S_DOES_NOT_EXIST_IN_TABLE_S2;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.COLUMN_S_DOES_NOT_EXIST_IN_LEVEL_TABLE_S;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.COULD_NOCH_CHECK_EXISTANCE_OF_TABLE_0;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.COULD_NOT_CHECK_EXISTANCE_OF_FACT_TABLE_0_DOES_NOT_EXIST_IN_DATABASE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.COULD_NOT_CHECK_EXISTANCE_OF_SCHEMA_S;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.COULD_NOT_EVALUEATE_DOES_COLUMN_EXIST_SCHEMA_TABLE_COLUMN;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_DEFINED_IN_FIELD_IN_TABLE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_S_DEFINED_IN_FIELD_S_IN;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_S_DEFINED_IN_FIELD_S_IN_TABLE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_S_DEFINED_IN_FIELD_S_IN_TABLE_24;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.COULD_NOT_QUERY_COLUMN_DATA_TYPE_ON_SCHEMA_TABLE_COLUMN;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.CUBE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.CUBE_DIMENSION;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.CUBE_DIMENSION_FOREIGN_KEY_S_DOES_NOT_EXIST_IN_FACT_TABLE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.CUBE_MUST_CONTAIN_MEASURES;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.DATABASE_DOES_NOW_ANSWER_WITH_DATA_TYPE_FOR_AGGREGATOR_COLUMN;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.DEGENERATE_DIMENSION_VALIDATION_CHECK_COLUMN_S_DOES_NOT_EXIST_IN_FACT_TABLE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.DEGENERATE_DIMENSION_VALIDATION_CHECK_COLUMN_S_DOES_NOT_EXIST_IN_FACT_TABLE1;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.FACT_TABLE_0_DOES_NOT_EXIST_IN_DATABASE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.FOREIGN_KEY;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.HIERARCHY;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.HIERARCHY_PRIMARY_KEY;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.LEVEL;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.MEASURE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.PARENT_TABLE_NAME;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.PRIMARY_KEY;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.PROPERTY;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.RELATON;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.SCHEMA_SPACE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.SCHEMA_S_DOES_NOT_EXIST;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.TABLE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.TABLE_S_DOES_NOT_EXIST_IN_DATABASE;

import java.sql.SQLException;
import java.util.Optional;

import org.eclipse.daanse.db.jdbc.metadata.api.JdbcMetaDataService;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Cube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.CubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Hierarchy;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Join;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Measure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.PrivateDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Property;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Table;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Cause;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.AbstractSchemaWalker;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaExplorer;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.VerificationResultR;

public class JDBCSchemaWalker extends AbstractSchemaWalker {

    private JdbcMetaDataService jmds;
    private DatabaseVerifierConfig config;

    public JDBCSchemaWalker(DatabaseVerifierConfig config, JdbcMetaDataService jmds) {
        this.config = config;
        this.jmds = jmds;
    }

    @Override
    protected void checkCube(Cube cube) {
        super.checkCube(cube);

        if (cube.fact() instanceof Table table) {
            String schemaName = table.schema();
            String factTable = table.name();
            try {
                if (!jmds.doesTableExist(schemaName, factTable)) {
                    String msg = String.format(CUBE_MUST_CONTAIN_MEASURES, orNotSet(cube.name()));
                    results.add(new VerificationResultR(CUBE, msg,
                        ERROR, DATABASE));

                    String message = String.format(FACT_TABLE_0_DOES_NOT_EXIST_IN_DATABASE, factTable,
                        ((schemaName == null || schemaName.equals("")) ? "." : SCHEMA_SPACE + schemaName));
                    results.add(new VerificationResultR(CUBE, message, ERROR, DATABASE));
                }
            } catch (SQLException e) {
                String message = String.format(COULD_NOT_CHECK_EXISTANCE_OF_FACT_TABLE_0_DOES_NOT_EXIST_IN_DATABASE, factTable,
                    ((schemaName == null || schemaName.equals("")) ? "." : SCHEMA_SPACE + schemaName));
                results.add(new VerificationResultR(CUBE, message, ERROR, DATABASE));
            }
        }

    }

    @Override
    protected void checkMeasure(Measure measure, Cube cube) {
        super.checkMeasure(measure, cube);
        if (cube != null && cube.fact() != null) {
            // Database validity check, if database connection is
            // successful
            if (cube.fact() instanceof Table factTable) {

                String column = measure.column();
                try {
                    if (jmds.doesColumnExist(factTable.schema(), factTable.name(), column)) {
                        // Check for aggregator type only if column
                        // exists in table.

                        // Check if aggregator selected is valid on
                        // the data type of the column selected.
                        Optional<Integer> oColType = Optional.empty();
                        try {
                            oColType = jmds.getColumnDataType(factTable.schema(), factTable.name(), measure.column());
                        } catch (SQLException e) {

                            String msg = String.format(COULD_NOT_QUERY_COLUMN_DATA_TYPE_ON_SCHEMA_TABLE_COLUMN,
                                measure.aggregator(), measure.column());
                            results.add(new VerificationResultR(MEASURE, msg, ERROR, Cause.SCHEMA));
                        }
                        // Coltype of 2, 4,5, 7, 8, -5 is numeric types
                        // whereas 1, 12 are char varchar string
                        // and 91 is date type.
                        // Types are enumerated in java.sql.Types.
                        int agIndex = -1;
                        if ("sum".equals(measure.aggregator()) || "avg".equals(measure.aggregator())) {
                            // aggregator = sum or avg, column should
                            // be numeric
                            agIndex = 0;
                        }
                        if (oColType.isPresent()) {
                            int colType = oColType.get();
                            if (!(agIndex == -1 || (colType >= 2 && colType <= 8) || colType == -5 || colType == -6)) {
                                String msg = String.format(AGGREGATOR_IS_NOT_VALID_FOR_THE_DATA_TYPE_OF_THE_COLUMN,
                                    measure.aggregator(), measure.column());
                                results.add(new VerificationResultR(MEASURE, msg, ERROR, Cause.SCHEMA));
                            }
                        } else {
                            String msg = String.format(DATABASE_DOES_NOW_ANSWER_WITH_DATA_TYPE_FOR_AGGREGATOR_COLUMN,
                                measure.aggregator(), measure.column());
                            results.add(new VerificationResultR(MEASURE, msg, ERROR, DATABASE));

                        }
                    }
                } catch (SQLException e) {

                    String msg = String.format(COULD_NOT_EVALUEATE_DOES_COLUMN_EXIST_SCHEMA_TABLE_COLUMN,
                        factTable.schema(), factTable.name(), column);
                    results.add(new VerificationResultR(MEASURE, msg, ERROR, DATABASE));
                }

            }
        }
    }

    @Override
    protected void checkCubeDimension(CubeDimension cubeDimension, Cube cube) {
        super.checkCubeDimension(cubeDimension, cube);

        if (cubeDimension instanceof PrivateDimension) {
            if (!isEmpty((cubeDimension).foreignKey())) {

                // TODO: Need to add validation for Views
                if (cube != null && cube.fact() instanceof Table factTable) {
                    String foreignKey = (cubeDimension).foreignKey();
                    try {
                        if (!jmds.doesColumnExist(factTable.schema(), factTable.name(), foreignKey)) {
                            String msg = String.format(CUBE_DIMENSION_FOREIGN_KEY_S_DOES_NOT_EXIST_IN_FACT_TABLE,
                                foreignKey);
                            results.add(new VerificationResultR(CUBE_DIMENSION, msg, ERROR, DATABASE));
                        }
                    } catch (SQLException e) {
                        String msg = String.format(COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_DEFINED_IN_FIELD_IN_TABLE,
                            JDBCSchemaWalker.isEmpty(foreignKey.trim()) ? "' '" : foreignKey, FOREIGN_KEY,
                            factTable.name());
                        results.add(new VerificationResultR(CUBE_DIMENSION, msg, ERROR, DATABASE));
                    }

                }
            }
        }

    }

    @Override
    protected void checkHierarchy(Hierarchy hierarchy, PrivateDimension cubeDimension, Cube cube) {
        super.checkHierarchy(hierarchy, cubeDimension, cube);

        // Validates that value in primaryKey exists in Table.
        String schema = null;
        String pkTable = null;
        if (hierarchy.relation() instanceof Join) {
            String[] schemaAndTable = SchemaExplorer.getTableNameForAlias(hierarchy.relation(),
                hierarchy.primaryKeyTable());
            schema = schemaAndTable[0];
            pkTable = schemaAndTable[1];
        } else if (hierarchy.relation() instanceof Table table) {
            pkTable = table.name();
            schema = table.schema();
        }

        try {
            if (pkTable != null && !jmds.doesColumnExist(schema, pkTable, hierarchy.primaryKey())) {
                String msg = String.format(COLUMN_S_DEFINED_IN_FIELD_DOES_NOT_EXIST_IN_TABLE,
                    isEmpty(hierarchy.primaryKey()
                        .trim()) ? "' '" : hierarchy.primaryKey(),
                    PRIMARY_KEY, pkTable);
                results.add(new VerificationResultR(HIERARCHY, msg, ERROR, DATABASE));
            }
        } catch (SQLException e) {
            String msg = String.format(COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_DEFINED_IN_FIELD_IN_TABLE,
                isEmpty(hierarchy.primaryKey().trim()) ? "' '" : hierarchy.primaryKey(), HIERARCHY_PRIMARY_KEY,
                pkTable);
            results.add(new VerificationResultR(HIERARCHY, msg, ERROR, DATABASE));
        }
    }

    @Override
    protected void checkProperty(
        Property property, org.eclipse.daanse.olap.rolap.dbmapper.model.api.Level level,
        Hierarchy hierarchy, Cube cube
    ) {
        super.checkProperty(property, level, hierarchy, cube);
        // Check 'column' exists in 'table' if [level table] is
        // specified otherwise :: case of join.

        // It should exist in [hierarchy relation table] if it is
        // specified otherwise :: case of table.

        // It should exist in [fact table] :: case of degenerate
        // dimension where dimension columns exist in fact table and
        // there is no separate table.

        // check property's column is in table
        String column = property.column();
        String table = null;
        if (level != null) {
            // specified table for level's column'
            table = level.table();
        }
        if (isEmpty(table)) {
            if (hierarchy != null) {
                if (hierarchy.relation() == null && cube != null) {
                    // Case of degenerate dimension within cube,
                    // hierarchy table not specified
                    final Table factTable = (Table) cube.fact();
                    try {
                        if (!jmds.doesColumnExist(factTable.schema(), factTable.name(), column)) {
                            String msg = String.format(
                                DEGENERATE_DIMENSION_VALIDATION_CHECK_COLUMN_S_DOES_NOT_EXIST_IN_FACT_TABLE,
                                column);
                            results.add(new VerificationResultR(PROPERTY, msg, ERROR, DATABASE));
                        }
                    } catch (SQLException e) {
                        String msg = String.format(COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_DEFINED_IN_FIELD_IN_TABLE,
                            isEmpty(column.trim()) ? "' '" : column, RELATON, factTable.name());
                        results.add(new VerificationResultR(PROPERTY, msg, ERROR, DATABASE));
                    }
                } else if (hierarchy.relation() instanceof Table parentTable) {
                    try {
                        if (!jmds.doesColumnExist(parentTable.schema(), parentTable.name(), column)) {
                            String msg = String.format(COLUMN_0_DOES_NOT_EXIST_IN_DIMENSION_TABLE,
                                parentTable.name());
                            results.add(new VerificationResultR(PROPERTY, msg, ERROR, DATABASE));
                        }
                    } catch (SQLException e) {
                        String msg = String.format(COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_S_DEFINED_IN_FIELD_S_IN +
                                "table {2}",
                            isEmpty(column.trim()) ? "' '" : column, PARENT_TABLE_NAME, parentTable.name());
                        results.add(new VerificationResultR(PROPERTY, msg, ERROR, DATABASE));
                    }
                    checkTable(parentTable);
                }
            }
        } else {
            try {
                if (!jmds.doesColumnExist(null, table, column)) {
                    String msg = String.format(COLUMN_S_DOES_NOT_EXIST_IN_LEVEL_TABLE_S, column, table);
                    results.add(new VerificationResultR(PROPERTY, msg, ERROR, DATABASE));
                }
            } catch (SQLException e) {
                String msg = String.format(COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_DEFINED_IN_FIELD_IN_TABLE,
                    isEmpty(column.trim()) ? "' '" : column, "-", table);
                results.add(new VerificationResultR(PROPERTY, msg, ERROR, DATABASE));
            }
        }

    }

    @Override
    protected void checkTable(Table table) {
        super.checkTable(table);
        String tableName = table.name();
        try {
            if (!jmds.doesTableExist(null, tableName)) {
                String msg = String.format(TABLE_S_DOES_NOT_EXIST_IN_DATABASE, tableName);
                results.add(new VerificationResultR(TABLE, msg, ERROR, DATABASE));
            }
        } catch (SQLException e) {
            String message = String.format(COULD_NOCH_CHECK_EXISTANCE_OF_TABLE_0, tableName);
            results.add(new VerificationResultR(TABLE, message, ERROR, DATABASE));
        }

        String theSchema = table.schema();
        try {
            if (!isEmpty(theSchema) && !jmds.doesSchemaExist(theSchema)) {
                String msg = String.format(SCHEMA_S_DOES_NOT_EXIST, theSchema);
                results.add(new VerificationResultR(TABLE, msg, ERROR, DATABASE));
            }
        } catch (SQLException e) {
            String msg = String.format(COULD_NOT_CHECK_EXISTANCE_OF_SCHEMA_S, theSchema);

            results.add(new VerificationResultR(TABLE, msg, ERROR, DATABASE));

        }
    }

    /**
     * Validates a column, and returns an error message if it is invalid.
     *
     * @param column          Column
     * @param fieldName       Field name
     * @param level           Level
     * @param cube            Cube
     * @param parentHierarchy Hierarchy
     */
    @Override
    protected void checkColumn(
        String column,
        String fieldName,
        org.eclipse.daanse.olap.rolap.dbmapper.model.api.Level level,
        Cube cube,
        Hierarchy parentHierarchy
    ) {
        super.checkColumn(column, fieldName, level, cube, parentHierarchy);
        if (!isEmpty(column)) {

            String table = level.table();
            if (isEmpty(table)) {
                if (parentHierarchy != null) {
                    if (parentHierarchy.relation() == null && cube != null) {
                        // case of degenerate dimension within cube,
                        // hierarchy table not specified
                        try {
                            if (!jmds.doesColumnExist(((Table) cube.fact()).schema(), ((Table) cube.fact()).name(),
                                column)) {
                                String msg = String.format(DEGENERATE_DIMENSION_VALIDATION_CHECK_COLUMN_S_DOES_NOT_EXIST_IN_FACT_TABLE1, column);
                                results.add(new VerificationResultR(LEVEL, msg, ERROR, DATABASE));
                            }
                        } catch (SQLException e) {
                            String msg = String.format(COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_S_DEFINED_IN_FIELD_S_IN_TABLE_24,
                                isEmpty(column.trim()) ? "' '" : column, fieldName, table);
                            results.add(new VerificationResultR(LEVEL, msg, ERROR, DATABASE));
                        }
                    } else if (parentHierarchy.relation() instanceof Table parentTable) {
                        try {
                            if (!jmds.doesColumnExist(parentTable.schema(), parentTable.name(), column)) {
                                String msg = String.format(COLUMN_DEFINED_IN_FIELD_DOES_NOT_EXIST_IN_TABLE,
                                    isEmpty(column.trim()) ? "' '" : column, fieldName,
                                    parentTable.name());
                                results.add(new VerificationResultR(LEVEL, msg, ERROR, DATABASE));
                                checkTable((Table) parentHierarchy.relation());
                            }
                        } catch (SQLException e) {
                            String msg = String.format(COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_DEFINED_IN_FIELD_IN_TABLE,
                                isEmpty(column.trim()) ? "' '" : column, fieldName, table);
                            results.add(new VerificationResultR(LEVEL, msg, ERROR, DATABASE));
                        }
                    }
                }
            } else {
                String schema = null;
                // if using Joins then gets the table name for doesColumnExist
                // validation.
                if (parentHierarchy != null && parentHierarchy.relation() instanceof Join join) {
                    String[] schemaAndTable = SchemaExplorer.getTableNameForAlias(parentHierarchy.relation(), table);
                    schema = schemaAndTable[0];
                    table = schemaAndTable[1];
                    checkJoin(join);
                }
                try {
                    if (!jmds.doesColumnExist(schema, table, column)) {
                        String msg = String.format(COLUMN_S_DEFINED_IN_FIELD_S_DOES_NOT_EXIST_IN_TABLE_S2,
                            isEmpty(column.trim()) ? "' '" : column, fieldName, table);
                        results.add(new VerificationResultR(LEVEL, msg, ERROR, DATABASE));
                    }
                } catch (SQLException e) {
                    String msg = String.format(COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_S_DEFINED_IN_FIELD_S_IN_TABLE,
                        isEmpty(column.trim()) ? "' '" : column, fieldName, table);
                    results.add(new VerificationResultR(LEVEL, msg, ERROR, DATABASE));

                }
            }
        }
    }

    @Override
    protected boolean isSchemaRequired() {
        return config.isSchemaRequired();
    }
}
