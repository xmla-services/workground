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

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

import org.eclipse.daanse.db.jdbc.metadata.api.JdbcMetaDataService;
import org.eclipse.daanse.olap.rolap.dbmapper.api.CalculatedMember;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Cube;
import org.eclipse.daanse.olap.rolap.dbmapper.api.CubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.api.DimensionUsage;
import org.eclipse.daanse.olap.rolap.dbmapper.api.ElementFormatter;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Formula;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Hierarchy;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Join;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Measure;
import org.eclipse.daanse.olap.rolap.dbmapper.api.NamedSet;
import org.eclipse.daanse.olap.rolap.dbmapper.api.PrivateDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Property;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Table;
import org.eclipse.daanse.olap.rolap.dbmapper.api.UserDefinedFunction;
import org.eclipse.daanse.olap.rolap.dbmapper.api.View;
import org.eclipse.daanse.olap.rolap.dbmapper.api.VirtualCube;
import org.eclipse.daanse.olap.rolap.dbmapper.api.enums.DimensionTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.api.enums.LevelTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Cause;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Verifyer;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.AbstractSchemaWalker;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaExplorer;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.VerificationResultR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBCSchemaWalker extends AbstractSchemaWalker {

    private JdbcMetaDataService jmds;
    private DatabaseVerifierConfig config;

    public JDBCSchemaWalker(DatabaseVerifierConfig config, JdbcMetaDataService jmds) {
        this.config = config;
        this.jmds = jmds;
    }

    private final Logger LOGGER = LoggerFactory.getLogger(Verifyer.class);
    private boolean isSchemaRequired = true;
    String[] DEF_LEVEL = { "column", "nameColumn", "parentColumn", "ordinalColumn", "captionColumn" };

    @Override
    protected void checkCube(Cube cube) {
        super.checkCube(cube);

        if (cube.fact() == null
                || ((cube.fact() instanceof Table) && JDBCSchemaWalker.isEmpty(((Table) cube.fact()).name()))
                || ((cube.fact() instanceof View) && JDBCSchemaWalker.isEmpty(((View) cube.fact()).alias()))) {
            results.add(new VerificationResultR("Fact name must be set", "Fact name must be set", Level.ERROR,
                    Cause.SCHEMA));
        }
        if (cube.dimensionUsageOrDimension() == null || cube.dimensionUsageOrDimension()
                .isEmpty()) {
            results.add(new VerificationResultR("Cube must contain dimensions", "Cube must contain dimensions",
                    Level.ERROR, Cause.SCHEMA));
        } else {
            cube.dimensionUsageOrDimension()
                    .forEach(cd -> checkCubeDimension(cd, cube));
        }
        if (cube.measure() == null || cube.measure()
                .isEmpty()) {
            results.add(new VerificationResultR("Cube must contain measures", "Cube must contain measures", Level.ERROR,
                    Cause.SCHEMA));
        } else {
            cube.measure()
                    .forEach(m -> checkMeasure(m, cube));
        }

        if (cube.fact() instanceof Table) {
            final Table table = (Table) cube.fact();
            String schemaName = table.schema();
            String factTable = table.name();
            try {
                if (!jmds.doesTableExist(schemaName, factTable)) {
                    results.add(new VerificationResultR("Cube must contain measures", "Cube must contain measures",
                            Level.ERROR, Cause.SCHEMA));

                    String message = String.format("Fact table {0} does not exist in database {1}", factTable,
                            ((schemaName == null || schemaName.equals("")) ? "." : "schema " + schemaName));
                    results.add(new VerificationResultR(message, message, Level.ERROR, Cause.DATABASE));
                }
            } catch (SQLException e) {
                String message = String.format("could noch check existance of Fact table {0} does not exist in database {1}", factTable,
                        ((schemaName == null || schemaName.equals("")) ? "." : "schema " + schemaName));
                results.add(new VerificationResultR(message, message, Level.ERROR, Cause.DATABASE));
            }
        }

        if (cube.namedSet() != null) {
            cube.namedSet()
                    .forEach(ns -> checkNamedSet(ns));
        }

        if (cube.calculatedMember() != null) {
            cube.calculatedMember()
                    .forEach(cm -> checkCalculatedMember(cm));
        }

    }

    protected void checkVirtualCube(VirtualCube virtCube) {

        if (JDBCSchemaWalker.isEmpty(virtCube.name())) {
            results.add(new VerificationResultR("VirtualCube name must be set", "VirtualCube name must be set",
                    Level.ERROR, Cause.SCHEMA));

        }
        if (virtCube.virtualCubeDimension() == null) {
            results.add(new VerificationResultR("VirtualCube must contain dimensions",
                    "VirtualCube must contain dimensions", Level.ERROR, Cause.SCHEMA));
        }
        if (virtCube.virtualCubeMeasure() == null) {
            results.add(new VerificationResultR("VirtualCube must contain measures",
                    "VirtualCube must contain measures", Level.ERROR, Cause.SCHEMA));
        }

        checkNamedSetList(virtCube.namedSet());

        if (virtCube.calculatedMember() != null) {
            virtCube.calculatedMember()
                    .forEach(cm -> checkCalculatedMember(cm));
        }

    }

    private void checkMeasure(Measure measure, Cube cube) {

        if (JDBCSchemaWalker.isEmpty(measure.name())) {
            results.add(new VerificationResultR("Measure name must be set", "Measure name must be set", Level.ERROR,
                    Cause.SCHEMA));

        }
        if (JDBCSchemaWalker.isEmpty(measure.aggregator())) {
            results.add(new VerificationResultR("Measure Aggregator must be set", "Measure Aggregator must be set",
                    Level.ERROR, Cause.SCHEMA));
        }
        if (measure.measureExpression() != null) {
            // Measure expressions are OK
        } else if (JDBCSchemaWalker.isEmpty(measure.column())) {
            results.add(new VerificationResultR("Measure Column must be set", "Measure Column must be set", Level.ERROR,
                    Cause.SCHEMA));

        } else if (cube != null && cube.fact() != null) {
            // Database validity check, if database connection is
            // successful
            if (cube.fact() instanceof Table) {
                final Table factTable = (Table) cube.fact();

                String column = measure.column();
                try {
                    if (jmds.doesColumnExist(factTable.schema(), factTable.name(), column)) {
                        // Check for aggregator type only if column
                        // exists in table.

                        // Check if aggregator selected is valid on
                        // the data type of the column selected.
                        Optional<Integer> oColType=Optional.empty();
                        try {
                            oColType = jmds.getColumnDataType(factTable.schema(), factTable.name(), measure.column());
                        } catch (SQLException e) {
                            
                            String msg = String.format("Could not Query ColumnDataType on Schema: %s, Table: %s, Column: %s",
                                    measure.aggregator(), measure.column());
                            results.add(new VerificationResultR(msg, msg, Level.ERROR, Cause.SCHEMA));
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
                        if(oColType.isPresent()) {
                           int colType=oColType.get(); 
                        if (!(agIndex == -1 || (colType >= 2 && colType <= 8) || colType == -5 || colType == -6)) {
                            String msg = String.format("Aggregator %s is not valid for the data type of the column %s",
                                    measure.aggregator(), measure.column());
                            results.add(new VerificationResultR(msg, msg, Level.ERROR, Cause.SCHEMA));
                        }
                        }else {
                            String msg = String.format("Database does now answer with DataType for aggregator: %s Column: %s",
                                    measure.aggregator(), measure.column());
                            results.add(new VerificationResultR(msg, msg, Level.ERROR, Cause.DATABASE));
                            
                        }
                    }
                } catch (SQLException e) {

                    String msg = String.format("Could not evalueate doesColumnExist Schema: %s Table: %s Column: %s",
                            factTable.schema(), factTable.name(), column);
                    results.add(new VerificationResultR(msg, msg, Level.ERROR, Cause.DATABASE));
                }

            }
        }
        if (measure.cellFormatter() != null) {
            checkElementFormatter(measure.cellFormatter());
        }
    }

    private void checkCubeDimension(CubeDimension cubeDimension, Cube cube) {

        if (JDBCSchemaWalker.isEmpty(cubeDimension.name())) {
            results.add(new VerificationResultR("Cube Dimension name must be set", "Cube Dimension name must be set",
                    Level.ERROR, Cause.SCHEMA));

        }
        if (cubeDimension instanceof DimensionUsage) {
            if (JDBCSchemaWalker.isEmpty(((DimensionUsage) cubeDimension).source())) {
                results.add(new VerificationResultR("Cube Dimension Source must be set",
                        "Cube Dimension Source must be set", Level.ERROR, Cause.SCHEMA));
            }
        }
        if (cubeDimension instanceof PrivateDimension) {
            if (!JDBCSchemaWalker.isEmpty(((PrivateDimension) cubeDimension).foreignKey())) {

                // TODO: Need to add validation for Views
                if (cube.fact() instanceof Table) {
                    final Table factTable = (Table) cube.fact();
                    String foreignKey = ((PrivateDimension) cubeDimension).foreignKey();
                    try {
                        if (!jmds.doesColumnExist(factTable.schema(), factTable.name(), foreignKey)) {
                            String msg = String.format("Cube Dimension foreignKey %s does not exist in fact table",
                                    foreignKey);
                            results.add(new VerificationResultR(msg, msg, Level.ERROR, Cause.SCHEMA));
                        }
                    } catch (SQLException e) {
                        String msg = String.format("Could not lookup existance of Column %s defined in field %s in table {2}",
                                JDBCSchemaWalker.isEmpty(foreignKey.trim()) ? "' '" : foreignKey, "foreignKey", factTable.name());
                        results.add(new VerificationResultR(msg, msg, Level.ERROR, Cause.DATABASE));
                    }

                }
            }
            if (((PrivateDimension) cubeDimension).hierarchy() != null) {
                ((PrivateDimension) cubeDimension).hierarchy()
                        .forEach(h -> checkHierarchy(h, (PrivateDimension) cubeDimension, cube));
            }
        }

    }

    private void checkHierarchy(Hierarchy hierarchy, PrivateDimension cubeDimension, Cube cube) {

        if (hierarchy.relation() instanceof Join) {
            if (JDBCSchemaWalker.isEmpty(hierarchy.primaryKeyTable())) {
                if (JDBCSchemaWalker.isEmpty(hierarchy.primaryKey())) {
                    results.add(new VerificationResultR(
                            "Hierarchy: PrimaryKeyTable and PrimaryKey must be set for " + "Join",
                            "Hierarchy: PrimaryKeyTable and PrimaryKey must be set for Join", Level.ERROR,
                            Cause.SCHEMA));

                } else {
                    results.add(new VerificationResultR("Hierarchy: PrimaryKeyTable must be set for Join",
                            "Hierarchy: PrimaryKeyTable must be set for Join", Level.ERROR, Cause.SCHEMA));
                }
            }
            if (JDBCSchemaWalker.isEmpty(hierarchy.primaryKey())) {
                results.add(new VerificationResultR("Hierarchy: PrimaryKey must be set for Join",
                        "Hierarchy: PrimaryKey must be set for Join", Level.ERROR, Cause.SCHEMA));
            }
        }

        List<? extends org.eclipse.daanse.olap.rolap.dbmapper.api.Level> levels = hierarchy.level();
        if (levels == null || levels.size() == 0) {
            results.add(new VerificationResultR("Hierarchy: At least one Level must be set for Hierarchy",
                    "Hierarchy: At least one Level must be set for Hierarchy", Level.ERROR, Cause.SCHEMA));
        } else {
            levels.forEach(l -> checkLevel(l, hierarchy, cubeDimension, cube));
        }

        // Validates that value in primaryKey exists in Table.
        String schema = null;
        String pkTable = null;
        if (hierarchy.relation() instanceof Join) {
            String[] schemaAndTable = SchemaExplorer.getTableNameForAlias(hierarchy.relation(),
                    hierarchy.primaryKeyTable());
            schema = schemaAndTable[0];
            pkTable = schemaAndTable[1];
        } else if (hierarchy.relation() instanceof Table) {
            final Table table = (Table) hierarchy.relation();
            pkTable = table.name();
            schema = table.schema();
        }

        try {
            if (pkTable != null && !jmds.doesColumnExist(schema, pkTable, hierarchy.primaryKey())) {
                String msg = String.format("Column %s defined in field %s does not exist in table %s",
                        JDBCSchemaWalker.isEmpty(hierarchy.primaryKey()
                                .trim()) ? "' '" : hierarchy.primaryKey(),
                        "primaryKey", pkTable);
                results.add(new VerificationResultR(msg, msg, Level.ERROR, Cause.DATABASE));
            }
        } catch (SQLException e) {
            String msg = String.format("Could not lookup existance of Column %s defined in field %s in table {2}",
                    JDBCSchemaWalker.isEmpty(hierarchy.primaryKey().trim()) ? "' '" : hierarchy.primaryKey(), "hierarchy.primaryKey", pkTable);
            results.add(new VerificationResultR(msg, msg, Level.ERROR, Cause.DATABASE));
        }

        // Validates against primaryKeyTable name on field when using
        // Table.
        if (hierarchy.relation() instanceof Table) {
            if (!JDBCSchemaWalker.isEmpty(hierarchy.primaryKeyTable())) {
                results.add(new VerificationResultR("Hierarchy: Table field must be empty",
                        "Hierarchy: Table field must be empty", Level.ERROR, Cause.SCHEMA));
            }
            checkTable((Table) hierarchy.relation(), isSchemaRequired);
        }

        // Validates that the value at primaryKeyTable corresponds to
        // tables in joins.
        String primaryKeyTable = hierarchy.primaryKeyTable();
        if (!JDBCSchemaWalker.isEmpty(primaryKeyTable) && (hierarchy.relation() instanceof Join)) {
            TreeSet<String> joinTables = new TreeSet<>();
            SchemaExplorer.getTableNamesForJoin(hierarchy.relation(), joinTables);
            if (!joinTables.contains(primaryKeyTable)) {
                results.add(new VerificationResultR("Hierarchy: Table value does not correspond to any join",
                        "Hierarchy: Table value does not correspond to any join", Level.ERROR, Cause.DATABASE));
            }
            checkJoin((Join) hierarchy.relation());
        }

        if (!JDBCSchemaWalker.isEmpty(primaryKeyTable) && (hierarchy.relation() instanceof Table)) {
            Table theTable = (Table) hierarchy.relation();
            String compareTo = (theTable.alias() != null && theTable.alias()
                    .trim()
                    .length() > 0) ? theTable.alias() : theTable.name();
            if (!primaryKeyTable.equals(compareTo)) {
                results.add(new VerificationResultR("Hierarchy: Table value does not correspond to Hierarchy Relation",
                        "Hierarchy: Table value does not correspond to Hierarchy Relation", Level.ERROR,
                        Cause.DATABASE));
            }
            checkTable(theTable, isSchemaRequired);
        }
    }

    private void checkLevel(org.eclipse.daanse.olap.rolap.dbmapper.api.Level level, Hierarchy hierarchy,
            PrivateDimension parentDimension, Cube cube) {

        // Check 'column' exists in 'table' if table is specified
        // otherwise :: case of join.

        // It should exist in relation table if it is specified
        // otherwise :: case of table.

        // It should exist in fact table :: case of degenerate dimension
        // where dimension columns exist in fact table and there is no
        // separate table.

        if (level.levelType() != null) {
            // Empty leveltype is treated as default value of "Regular""
            // which is ok with standard/time dimension.
            if (parentDimension != null) {
                if ((parentDimension.type() == null || parentDimension.type()
                        .equals(DimensionTypeEnum.STANDARD_DIMENSION)) && level.levelType() != null
                        && (!level.levelType()
                                .equals(LevelTypeEnum.REGULAR))) {
                    // If dimension type is 'standard' then leveltype
                    // should be 'regular'
                    String msg = String.format("Level: levelType %s can only be used with a TimeDimension",
                            level.levelType()
                                    .getValue());
                    results.add(new VerificationResultR(msg, msg, Level.ERROR, Cause.SCHEMA));

                } else if (parentDimension.type() != null && (parentDimension.type()
                        .equals(DimensionTypeEnum.TIME_DIMENSION)) && level.levelType() != null && (level.levelType()
                                .equals(LevelTypeEnum.REGULAR))) {
                    // If dimension type is 'time' then leveltype value
                    // could be 'timeyears', 'timedays' etc'
                    String msg = String.format("Level: levelType %s can only be used with a StandardDimension",
                            level.levelType()
                                    .getValue());
                    results.add(new VerificationResultR(msg, msg, Level.ERROR, Cause.SCHEMA));
                }
            }
        }
        // verify level's name is set
        if (JDBCSchemaWalker.isEmpty(level.name())) {
            results.add(new VerificationResultR("Level name must be set", "Level name must be set", Level.ERROR,
                    Cause.SCHEMA));
        }

        // check level's column is in fact table
        String column = level.column();
        if (JDBCSchemaWalker.isEmpty(column)) {
            if (level.property() == null || level.property()
                    .size() == 0) {
                results.add(new VerificationResultR("Level: Column must be set", "Level: Column must be set",
                        Level.ERROR, Cause.SCHEMA));
            } else {
                level.property()
                        .forEach(p -> checkProperty(p, level, hierarchy, cube));
            }
        } else {
            // Enforces validation for all column types against invalid
            // value.
            String theMessage = null;
            try {
                for (String element : DEF_LEVEL) {
                    Field theField = level.getClass()
                            .getDeclaredField(element);
                    column = (String) theField.get(level);
                    checkColumn(column, element, level, cube, hierarchy);
                    if (theMessage != null) {
                        break;
                    }
                }
            } catch (Exception ex) {
                LOGGER.error("ValidationUtils", ex);
            }
        }
        if (level.memberFormatter() != null) {
            checkElementFormatter(level.memberFormatter());
        }
    }

    private void checkProperty(Property property, org.eclipse.daanse.olap.rolap.dbmapper.api.Level level,
            Hierarchy hierarchy, Cube cube) {

        // Check 'column' exists in 'table' if [level table] is
        // specified otherwise :: case of join.

        // It should exist in [hierarchy relation table] if it is
        // specified otherwise :: case of table.

        // It should exist in [fact table] :: case of degenerate
        // dimension where dimension columns exist in fact table and
        // there is no separate table.

        // check property's column is in table
        String column = property.column();
        if (JDBCSchemaWalker.isEmpty(column)) {
            results.add(new VerificationResultR("Column must be set", "Column must be set", Level.ERROR, Cause.SCHEMA));
        }

        String table = null;
        if (level != null) {
            // specified table for level's column'
            table = level.table();
        }
        if (JDBCSchemaWalker.isEmpty(table)) {
            if (hierarchy != null) {
                if (hierarchy.relation() == null && cube != null) {
                    // Case of degenerate dimension within cube,
                    // hierarchy table not specified
                    final Table factTable = (Table) cube.fact();
                    try {
                        if (!jmds.doesColumnExist(factTable.schema(), factTable.name(), column)) {
                            String msg = String.format(
                                    "Degenerate dimension validation check - Column %s does not " + "exist in fact table",
                                    column);
                            results.add(new VerificationResultR(msg, msg, Level.ERROR, Cause.DATABASE));
                        }
                    } catch (SQLException e) {
                        String msg = String.format("Could not lookup existance of Column %s defined in field %s in table {2}",
                                JDBCSchemaWalker.isEmpty(column.trim()) ? "' '" : column, "relaton", factTable.name());
                        results.add(new VerificationResultR(msg, msg, Level.ERROR, Cause.DATABASE));
                    }
                } else if (hierarchy.relation() instanceof Table) {
                    final Table parentTable = (Table) hierarchy.relation();
                    try {
                        if (!jmds.doesColumnExist(parentTable.schema(), parentTable.name(), column)) {
                            String msg = String.format("Column {0} does not exist in Dimension table", parentTable.name());
                            results.add(new VerificationResultR(msg, msg, Level.ERROR, Cause.DATABASE));
                        }
                    } catch (SQLException e) {
                        String msg = String.format("Could not lookup existance of Column %s defined in field %s in table {2}",
                                JDBCSchemaWalker.isEmpty(column.trim()) ? "' '" : column, "parentTable.name", parentTable.name());
                        results.add(new VerificationResultR(msg, msg, Level.ERROR, Cause.DATABASE));
                    }
                    checkTable(parentTable, isSchemaRequired);
                }
            }
        } else {
            try {
                if (!jmds.doesColumnExist(null, table, column)) {
                    String msg = String.format("Column %s does not exist in Level table %s", column, table);
                    results.add(new VerificationResultR(msg, msg, Level.ERROR, Cause.DATABASE));
                }
            } catch (SQLException e) {
                String msg = String.format("Could not lookup existance of Column %s defined in field %s in table {2}",
                        JDBCSchemaWalker.isEmpty(column.trim()) ? "' '" : column, "-", table);
                results.add(new VerificationResultR(msg, msg, Level.ERROR, Cause.DATABASE));
            }
        }

        if (property.propertyFormatter() != null) {
            checkElementFormatter(property.propertyFormatter());
        }

    }

    private void checkJoin(Join join) {

        if (JDBCSchemaWalker.isEmpty(join.leftKey())) {
            results.add(new VerificationResultR("Join: Left key must be set", "Join: Left key must be set", Level.ERROR,
                    Cause.SCHEMA));
        }
        if (JDBCSchemaWalker.isEmpty(join.rightKey())) {
            results.add(new VerificationResultR("Join: Right key must be set", "Join: Right key must be set",
                    Level.ERROR, Cause.SCHEMA));
        }
    }

    private void checkTable(Table table, boolean isSchemaRequired) {

        String tableName = table.name();
        try {
            if (!jmds.doesTableExist(null, tableName)) {
                String msg = String.format("Table %s does not exist in database", tableName);
                results.add(new VerificationResultR(msg, msg, Level.ERROR, Cause.DATABASE));
            }
        } catch (SQLException e) {
            String message = String.format("could noch check existance of Table {0}", tableName);
            results.add(new VerificationResultR(message, message, Level.ERROR, Cause.DATABASE));
        }

        String theSchema = table.schema();
        try {
            if (!JDBCSchemaWalker.isEmpty(theSchema) && !jmds.doesSchemaExist(theSchema)) {
                String msg = String.format("Schema %s does not exist", theSchema);
                results.add(new VerificationResultR(msg, msg, Level.ERROR, Cause.DATABASE));
            }
        } catch (SQLException e) {
            String msg = String.format("could not check existance of Schema %s", theSchema);

            results.add(new VerificationResultR(msg, msg, Level.ERROR, Cause.DATABASE));

        }
        if (JDBCSchemaWalker.isEmpty(theSchema) && isSchemaRequired) {
            results.add(
                    new VerificationResultR("Schema must be set", "Schema must be set", Level.ERROR, Cause.DATABASE));
        }
    }

    protected void checkNamedSet(NamedSet namedSet) {
        super.checkNamedSet(namedSet);
        if (JDBCSchemaWalker.isEmpty(namedSet.name())) {
            results.add(new VerificationResultR("NamedSet name must be set", "NamedSet name must be set", Level.ERROR,
                    Cause.SCHEMA));
        }
        if (JDBCSchemaWalker.isEmpty(namedSet.formula()) && namedSet.formulaElement() == null) {
            results.add(new VerificationResultR("NamedSet Formula must be set", "NamedSet Formula must be set",
                    Level.ERROR, Cause.SCHEMA));
        }

        if (namedSet.formulaElement() != null) {
            checkFormula(namedSet.formulaElement());
        }

    }

    private void checkFormula(Formula formula) {

        if (JDBCSchemaWalker.isEmpty(formula.cdata())) {
            results.add(
                    new VerificationResultR("Formula must be set", "Formula must be set", Level.ERROR, Cause.SCHEMA));
        }

    }

    protected void checkUserDefinedFunction(UserDefinedFunction udf) {
        super.checkUserDefinedFunction(udf);
        if (JDBCSchemaWalker.isEmpty(udf.name())) {
            results.add(new VerificationResultR("UserDefinedFunction name must be set",
                    "UserDefinedFunction name must be set", Level.ERROR, Cause.SCHEMA));
        }
        if (JDBCSchemaWalker.isEmpty(udf.className()) && udf.script() == null) {
            results.add(new VerificationResultR("UserDefinedFunction Either a Class Name or a Script are required",
                    "UserDefinedFunction Either a Class Name or a Script are required", Level.ERROR, Cause.SCHEMA));
        }

    }

    private void checkElementFormatter(ElementFormatter elementFormatter) {

        if (JDBCSchemaWalker.isEmpty(elementFormatter.className()) && elementFormatter.script() == null) {
            results.add(new VerificationResultR("Formatter: either a Class Name or a Script are required",
                    "Formatter: either a Class Name or a Script are required", Level.ERROR, Cause.SCHEMA));
        }

    }

    private void checkCalculatedMember(CalculatedMember calculatedMember) {

        if (JDBCSchemaWalker.isEmpty(calculatedMember.name())) {
            results.add(new VerificationResultR("CalculatedMember name must be set",
                    "CalculatedMember name must be set", Level.ERROR, Cause.SCHEMA));
        }
        if (JDBCSchemaWalker.isEmpty(calculatedMember.dimension())) {
            results.add(new VerificationResultR("CalculatedMember Dimension must be set",
                    "CalculatedMember Dimension must be set", Level.ERROR, Cause.SCHEMA));
        }
        if (JDBCSchemaWalker.isEmpty(calculatedMember.formula()) && calculatedMember.formulaElement() == null) {
            results.add(new VerificationResultR("CalculatedMember Formula must be set",
                    "CalculatedMember Formula must be set", Level.ERROR, Cause.SCHEMA));
        }
        if (calculatedMember.formulaElement() != null) {
            checkFormula(calculatedMember.formulaElement());
        }

        if (calculatedMember.cellFormatter() != null) {
            checkElementFormatter(calculatedMember.cellFormatter());
        }

    }

    /**
     * Validates a column, and returns an error message if it is invalid.
     *
     * @param column          Column
     * @param fieldName       Field name
     * @param level           Level
     * @param jmds            JDBC validator
     * @param cube            Cube
     * @param parentHierarchy Hierarchy
     * @return Error message if invalid, null if valid
     */
    private void checkColumn(String column, String fieldName, org.eclipse.daanse.olap.rolap.dbmapper.api.Level level,
            Cube cube, Hierarchy parentHierarchy) {

        if (!JDBCSchemaWalker.isEmpty(column)) {

            // specified table for level's column
            String table = level.table();
            // If table has been changed in join then sets the table value
            // to null to cause "tableMustBeSet" validation fail.
            if (!JDBCSchemaWalker.isEmpty(table) && parentHierarchy != null
                    && parentHierarchy.relation() instanceof Join) {
                TreeSet<String> joinTables = new TreeSet<>();
                SchemaExplorer.getTableNamesForJoin(parentHierarchy.relation(), joinTables);
                if (!joinTables.contains(table)) {

                    results.add(new VerificationResultR("Table value does not correspond to any join",
                            "Table value does not correspond to any join", Level.ERROR, Cause.SCHEMA));
                }
            }

            if (!JDBCSchemaWalker.isEmpty(table) && parentHierarchy != null
                    && parentHierarchy.relation() instanceof Table) {
                final Table parentTable = (Table) parentHierarchy.relation();
                Table theTable = parentTable;
                String compareTo = (theTable.alias() != null && theTable.alias()
                        .trim()
                        .length() > 0) ? theTable.alias() : theTable.name();
                if (!table.equals(compareTo)) {
                    results.add(new VerificationResultR("Table value does not correspond to Hierarchy Relation",
                            "Table value does not correspond to Hierarchy Relation", Level.ERROR, Cause.SCHEMA));
                }
                checkTable(parentTable, isSchemaRequired);
            }

            if (!JDBCSchemaWalker.isEmpty(table) && parentHierarchy != null
                    && parentHierarchy.relation() instanceof View) {
                results.add(new VerificationResultR("Table for column cannot be set in View",
                        "Table for column cannot be set in View", Level.ERROR, Cause.SCHEMA));
            }

            if (JDBCSchemaWalker.isEmpty(table)) {
                if (parentHierarchy != null) {
                    if (parentHierarchy.relation() == null && cube != null) {
                        // case of degenerate dimension within cube,
                        // hierarchy table not specified
                        try {
                            if (!jmds.doesColumnExist(((Table) cube.fact()).schema(), ((Table) cube.fact()).name(),
                                    column)) {
                                String msg = String.format("Degenerate dimension validation check - Column %s does "
                                        + "not exist in fact table", column);
                                results.add(new VerificationResultR(msg, msg, Level.ERROR, Cause.SCHEMA));
                            }
                        } catch (SQLException e) {
                            String msg = String.format("Could not lookup existance of Column %s defined in field %s in table {2}",
                                    JDBCSchemaWalker.isEmpty(column.trim()) ? "' '" : column, fieldName, table);
                            results.add(new VerificationResultR(msg, msg, Level.ERROR, Cause.DATABASE));
                        }
                    } else if (parentHierarchy.relation() instanceof Table) {
                        final Table parentTable = (Table) parentHierarchy.relation();
                        try {
                            if (!jmds.doesColumnExist(parentTable.schema(), parentTable.name(), column)) {
                                String msg = String.format("Column %s defined in field %s does not exist in table %s",
                                        JDBCSchemaWalker.isEmpty(column.trim()) ? "' '" : column, fieldName,
                                        parentTable.name());
                                results.add(new VerificationResultR(msg, msg, Level.ERROR, Cause.DATABASE));
                                checkTable((Table) parentHierarchy.relation(), isSchemaRequired);
                            }
                        } catch (SQLException e) {
                            String msg = String.format("Could not lookup existance of Column %s defined in field %s in table {2}",
                                    JDBCSchemaWalker.isEmpty(column.trim()) ? "' '" : column, fieldName, table);
                            results.add(new VerificationResultR(msg, msg, Level.ERROR, Cause.DATABASE));
                        }
                    } else if (parentHierarchy.relation() instanceof Join) {
                        // relation is join, table should be specified
                        results.add(new VerificationResultR("Table must be set", "Table must be set", Level.ERROR,
                                Cause.DATABASE));

                        checkJoin((Join) parentHierarchy.relation());
                    }
                }
            } else {
                String schema = null;
                // if using Joins then gets the table name for doesColumnExist
                // validation.
                if (parentHierarchy != null && parentHierarchy.relation() instanceof Join) {
                    String[] schemaAndTable = SchemaExplorer.getTableNameForAlias(parentHierarchy.relation(), table);
                    schema = schemaAndTable[0];
                    table = schemaAndTable[1];
                    checkJoin((Join) parentHierarchy.relation());
                }
                try {
                    if (!jmds.doesColumnExist(schema, table, column)) {
                        String msg = String.format("Column %s defined in field %s does not exist in table {2}",
                                JDBCSchemaWalker.isEmpty(column.trim()) ? "' '" : column, fieldName, table);
                        results.add(new VerificationResultR(msg, msg, Level.ERROR, Cause.SCHEMA));
                    }
                } catch (SQLException e) {
                    String msg = String.format("Could not lookup existance of Column %s defined in field %s in table {2}",
                            JDBCSchemaWalker.isEmpty(column.trim()) ? "' '" : column, fieldName, table);
                    results.add(new VerificationResultR(msg, msg, Level.ERROR, Cause.DATABASE));

                }
            }

        }
    }

}
