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
package org.eclipse.daanse.olap.rolap.dbmapper.verifyer.jdbc;

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
import org.eclipse.daanse.olap.rolap.dbmapper.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Table;
import org.eclipse.daanse.olap.rolap.dbmapper.api.UserDefinedFunction;
import org.eclipse.daanse.olap.rolap.dbmapper.api.View;
import org.eclipse.daanse.olap.rolap.dbmapper.api.VirtualCube;
import org.eclipse.daanse.olap.rolap.dbmapper.api.enums.DimensionTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.api.enums.LevelTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Cause;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.JdbcValidator;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.VerificationResult;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Verifyer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import static java.util.stream.Collectors.toList;

public class SchemaCheckerUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Verifyer.class);
    private static boolean isSchemaRequired = true;
    static String[] DEF_LEVEL = {
        "column", "nameColumn", "parentColumn", "ordinalColumn", "captionColumn"
    };

    public static Collection<? extends VerificationResult> checkSchema(JdbcValidator jdbcValidator, Schema schema) {
        List<VerificationResult> results = new ArrayList<>();

        if (isEmpty(schema.name())) {
            results.add(new VerificationResultR("Schema name must be set",
                "Schema name must be set",
                Level.ERROR,
                Cause.SCHEMA));
        }

        results.addAll(schema.cube().stream()
            .flatMap(c -> checkCube(jdbcValidator, c).stream())
            .collect(toList()));

        results.addAll(checkNamedSetList(schema.namedSet()));

        if (schema.virtualCube() != null) {
            results.addAll(schema.virtualCube().stream()
                .flatMap(virtualCube -> checkVirtualCube(virtualCube).stream())
                .collect(toList()));
        }

        if (schema.userDefinedFunctions() != null) {
            results.addAll(schema.userDefinedFunctions().stream()
                .flatMap(udf -> checkUserDefinedFunction(udf).stream())
                .collect(toList()));
        }
        return results;

    }

    private static List<VerificationResult> checkCube(JdbcValidator jdbcValidator, Cube cube) {
        List<VerificationResult> results = new ArrayList<>();

        if (isEmpty(cube.name())) {
            results.add(new VerificationResultR("Cube name must be set",
                "Cube name must be set",
                Level.ERROR,
                Cause.SCHEMA));
        }

        if (cube.fact() == null
            || ((cube.fact() instanceof Table)
            && isEmpty(((Table) cube.fact()).name()))
            || ((cube.fact() instanceof View)
            && isEmpty(((View) cube.fact()).alias()))) {
            results.add(new VerificationResultR("Fact name must be set",
                "Fact name must be set",
                Level.ERROR,
                Cause.SCHEMA));
        }
        if (cube.dimensionUsageOrDimension() == null || cube.dimensionUsageOrDimension().isEmpty()) {
            results.add(new VerificationResultR("Cube must contain dimensions",
                "Cube must contain dimensions",
                Level.ERROR,
                Cause.SCHEMA));

            results.addAll(cube.dimensionUsageOrDimension().stream()
                .flatMap(cd -> checkCubeDimension(jdbcValidator, cd, cube).stream())
                .collect(toList()));
        }
        if (cube.measure() == null || cube.measure().isEmpty()) {
            results.add(new VerificationResultR("Cube must contain measures",
                "Cube must contain measures",
                Level.ERROR,
                Cause.SCHEMA));
        } else {
            results.addAll(cube.measure().stream()
                .flatMap(m -> checkMeasure(jdbcValidator, m, cube).stream())
                .collect(toList()));
        }
        // database validity check, if database connection is successful
        if (jdbcValidator.isInitialized()) {
            if (cube.fact()
                instanceof Table) {
                final Table table =
                    (Table) cube.fact();
                String schemaName = table.schema();
                String factTable = table.name();
                if (!jdbcValidator.isTableExists(schemaName, factTable)) {
                    results.add(new VerificationResultR("Cube must contain measures",
                        "Cube must contain measures",
                        Level.ERROR,
                        Cause.SCHEMA));

                    String message =
                        String.format("Fact table {0} does not exist in database {1}",
                            factTable,
                            ((schemaName == null || schemaName.equals(""))
                                ? "."
                                : "schema " + schemaName));
                    results.add(new VerificationResultR(message,
                        message,
                        Level.ERROR,
                        Cause.DATABASE));
                }
            }
        }

        if (cube.namedSet() != null) {
            results.addAll(cube.namedSet().stream()
                .flatMap(ns -> checkNamedSet(ns).stream())
                .collect(toList()));
        }

        if (cube.calculatedMember() != null) {
            results.addAll(cube.calculatedMember().stream()
                .flatMap(cm -> checkCalculatedMember(cm).stream())
                .collect(toList()));
        }

        return results;
    }

    private static List<VerificationResult> checkVirtualCube(VirtualCube virtCube) {
        List<VerificationResult> results = new ArrayList<>();
        if (isEmpty(virtCube.name())) {
            results.add(new VerificationResultR("VirtualCube name must be set",
                "VirtualCube name must be set",
                Level.ERROR,
                Cause.SCHEMA));

        }
        if (virtCube.virtualCubeDimension() == null) {
            results.add(new VerificationResultR("VirtualCube must contain dimensions",
                "VirtualCube must contain dimensions",
                Level.ERROR,
                Cause.SCHEMA));
        }
        if (virtCube.virtualCubeMeasure() == null) {
            results.add(new VerificationResultR("VirtualCube must contain measures",
                "VirtualCube must contain measures",
                Level.ERROR,
                Cause.SCHEMA));
        }

        results.addAll(checkNamedSetList(virtCube.namedSet()));

        if (virtCube.calculatedMember() != null) {
            results.addAll(virtCube.calculatedMember().stream()
                .flatMap(cm -> checkCalculatedMember(cm).stream())
                .collect(toList()));
        }

        return results;
    }

    private static Collection<? extends VerificationResult> checkMeasure(
        JdbcValidator jdbcValidator,
        Measure measure,
        Cube cube
    ) {
        List<VerificationResult> results = new ArrayList<>();
        if (isEmpty(measure.name())) {
            results.add(new VerificationResultR("Measure name must be set",
                "Measure name must be set",
                Level.ERROR,
                Cause.SCHEMA));

        }
        if (isEmpty(measure.aggregator())) {
            results.add(new VerificationResultR("Measure Aggregator must be set",
                "Measure Aggregator must be set",
                Level.ERROR,
                Cause.SCHEMA));
        }
        if (measure.measureExpression() != null) {
            // Measure expressions are OK
        } else if (isEmpty(measure.column())) {
            results.add(new VerificationResultR("Measure Column must be set",
                "Measure Column must be set",
                Level.ERROR,
                Cause.SCHEMA));

        } else if (cube != null && cube.fact() != null) {
            // Database validity check, if database connection is
            // successful
            if (cube.fact() instanceof Table) {
                final Table factTable =
                    (Table) cube.fact();
                if (jdbcValidator.isInitialized()) {
                    String column = measure.column();
                    if (jdbcValidator.isColExists(
                        factTable.schema(),
                        factTable.name(),
                        column)) {
                        // Check for aggregator type only if column
                        // exists in table.

                        // Check if aggregator selected is valid on
                        // the data type of the column selected.
                        int colType =
                            jdbcValidator.getColumnDataType(
                                factTable.schema(),
                                factTable.name(),
                                measure.column());
                        // Coltype of 2, 4,5, 7, 8, -5 is numeric types
                        // whereas 1, 12 are char varchar string
                        // and 91 is date type.
                        // Types are enumerated in java.sql.Types.
                        int agIndex = -1;
                        if ("sum".equals(
                            measure.aggregator())
                            || "avg".equals(
                            measure.aggregator())) {
                            // aggregator = sum or avg, column should
                            // be numeric
                            agIndex = 0;
                        }
                        if (!(agIndex == -1
                            || (colType >= 2 && colType <= 8)
                            || colType == -5 || colType == -6)) {
                            String msg = String.format("Aggregator %s is not valid for the data type of the column %s",
                                measure.aggregator(),
                                measure.column());
                            results.add(new VerificationResultR(msg,
                                msg,
                                Level.ERROR,
                                Cause.SCHEMA));
                        }
                    }
                }
            }
        }
        if (measure.cellFormatter() != null) {
            results.addAll(checkElementFormatter(measure.cellFormatter()));
        }
        return results;
    }

    private static Collection<? extends VerificationResult> checkCubeDimension(
        JdbcValidator jdbcValidator,
        CubeDimension cubeDimension,
        Cube cube
    ) {
        List<VerificationResult> results = new ArrayList<>();

        if (isEmpty(cubeDimension.name())) {
            results.add(new VerificationResultR("Cube Dimension name must be set",
                "Cube Dimension name must be set",
                Level.ERROR,
                Cause.SCHEMA));

        }
        if (cubeDimension instanceof DimensionUsage) {
            if (isEmpty(
                ((DimensionUsage) cubeDimension).source())) {
                results.add(new VerificationResultR("Cube Dimension Source must be set",
                    "Cube Dimension Source must be set",
                    Level.ERROR,
                    Cause.SCHEMA));
            }
        }
        if (cubeDimension instanceof PrivateDimension) {
            if (!isEmpty(
                ((PrivateDimension) cubeDimension).foreignKey())) {
                // database validity check, if database connection is
                // successful
                if (jdbcValidator.isInitialized()) {
                    // TODO: Need to add validation for Views
                    if (cube.fact() instanceof Table) {
                        final Table factTable =
                            (Table) cube.fact();
                        String foreignKey =
                            ((PrivateDimension) cubeDimension)
                                .foreignKey();
                        if (!jdbcValidator.isColExists(
                            factTable.schema(),
                            factTable.name(),
                            foreignKey)) {
                            String msg = String.format("Cube Dimension foreignKey %s does not exist in fact table",
                                foreignKey);
                            results.add(new VerificationResultR(msg,
                                msg,
                                Level.ERROR,
                                Cause.SCHEMA));
                        }
                    }
                }
            }
            if (((PrivateDimension) cubeDimension).hierarchy() != null) {
                results.addAll(((PrivateDimension) cubeDimension).hierarchy().stream()
                    .flatMap(h -> checkHierarchy(jdbcValidator, h, (PrivateDimension) cubeDimension, cube).stream())
                    .collect(toList()));
            }
        }

        return results;
    }

    private static Collection<? extends VerificationResult> checkHierarchy(
        JdbcValidator jdbcValidator,
        Hierarchy hierarchy,
        PrivateDimension cubeDimension,
        Cube cube
    ) {
        List<VerificationResult> results = new ArrayList<>();
        if (hierarchy.relation() instanceof Join) {
            if (isEmpty(hierarchy.primaryKeyTable())) {
                if (isEmpty(hierarchy.primaryKey())) {
                    results.add(new VerificationResultR("Hierarchy: PrimaryKeyTable and PrimaryKey must be set for " +
                        "Join",
                        "Hierarchy: PrimaryKeyTable and PrimaryKey must be set for Join",
                        Level.ERROR,
                        Cause.SCHEMA));

                } else {
                    results.add(new VerificationResultR("Hierarchy: PrimaryKeyTable must be set for Join",
                        "Hierarchy: PrimaryKeyTable must be set for Join",
                        Level.ERROR,
                        Cause.SCHEMA));
                }
            }
            if (isEmpty(hierarchy.primaryKey())) {
                results.add(new VerificationResultR("Hierarchy: PrimaryKey must be set for Join",
                    "Hierarchy: PrimaryKey must be set for Join",
                    Level.ERROR,
                    Cause.SCHEMA));
            }
        }

        List<? extends org.eclipse.daanse.olap.rolap.dbmapper.api.Level> levels = hierarchy.level();
        if (levels == null || levels.size() == 0) {
            results.add(new VerificationResultR("Hierarchy: At least one Level must be set for Hierarchy",
                "Hierarchy: At least one Level must be set for Hierarchy",
                Level.ERROR,
                Cause.SCHEMA));
        } else {
            results.addAll(levels.stream()
                .flatMap(l -> checkLevel(jdbcValidator, l, hierarchy, cubeDimension, cube).stream())
                .collect(toList()));
        }

        // Validates that value in primaryKey exists in Table.
        String schema = null;
        String pkTable = null;
        if (hierarchy.relation() instanceof Join) {
            String[] schemaAndTable =
                SchemaExplorer.getTableNameForAlias(
                    hierarchy.relation(),
                    hierarchy.primaryKeyTable());
            schema = schemaAndTable[0];
            pkTable = schemaAndTable[1];
        } else if (hierarchy.relation() instanceof Table) {
            final Table table =
                (Table) hierarchy.relation();
            pkTable = table.name();
            schema = table.schema();
        }

        if (pkTable != null
            && !jdbcValidator.isColExists(
            schema, pkTable, hierarchy.primaryKey())) {
            String msg = String.format("Column %s defined in field %s does not exist in table %s",
                isEmpty(hierarchy.primaryKey().trim())
                    ? "' '"
                    : hierarchy.primaryKey(),
                "primaryKey",
                pkTable);
            results.add(new VerificationResultR(msg,
                msg,
                Level.ERROR,
                Cause.DATABASE));
        }

        // Validates against primaryKeyTable name on field when using
        // Table.
        if (hierarchy.relation() instanceof Table) {
            if (!isEmpty(hierarchy.primaryKeyTable())) {
                results.add(new VerificationResultR("Hierarchy: Table field must be empty",
                    "Hierarchy: Table field must be empty",
                    Level.ERROR,
                    Cause.SCHEMA));
            }
            results.addAll(checkTable(jdbcValidator, (Table)hierarchy.relation(), isSchemaRequired));
        }

        // Validates that the value at primaryKeyTable corresponds to
        // tables in joins.
        String primaryKeyTable = hierarchy.primaryKeyTable();
        if (!isEmpty(primaryKeyTable)
            && (hierarchy.relation() instanceof Join)) {
            TreeSet<String> joinTables = new TreeSet<String>();
            SchemaExplorer.getTableNamesForJoin(
                hierarchy.relation(), joinTables);
            if (!joinTables.contains(primaryKeyTable)) {
                results.add(new VerificationResultR("Hierarchy: Table value does not correspond to any join",
                    "Hierarchy: Table value does not correspond to any join",
                    Level.ERROR,
                    Cause.DATABASE));
            }
            results.addAll(checkJoin((Join)hierarchy.relation()));
        }

        if (!isEmpty(primaryKeyTable)
            && (hierarchy.relation() instanceof Table)) {
            Table theTable =
                (Table) hierarchy.relation();
            String compareTo =
                (theTable.alias() != null
                    && theTable.alias().trim().length() > 0)
                    ? theTable.alias()
                    : theTable.name();
            if (!primaryKeyTable.equals(compareTo)) {
                results.add(new VerificationResultR("Hierarchy: Table value does not correspond to Hierarchy Relation",
                    "Hierarchy: Table value does not correspond to Hierarchy Relation",
                    Level.ERROR,
                    Cause.DATABASE));
            }
            checkTable(jdbcValidator, theTable, isSchemaRequired);
        }
        return results;
    }

    private static Collection<? extends VerificationResult> checkLevel(
        JdbcValidator jdbcValidator,
        org.eclipse.daanse.olap.rolap.dbmapper.api.Level level,
        Hierarchy hierarchy,
        PrivateDimension parentDimension,
        Cube cube
    ) {

        List<VerificationResult> results = new ArrayList<>();
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
                if ((parentDimension.type() == null
                    || parentDimension.type().equals(DimensionTypeEnum.STANDARD_DIMENSION))
                    && level.levelType() != null
                    && (!level.levelType().equals(LevelTypeEnum.REGULAR))) {
                    // If dimension type is 'standard' then leveltype
                    // should be 'regular'
                    String msg = String.format("Level: levelType %s can only be used with a TimeDimension",
                        level.levelType().getValue());
                    results.add(new VerificationResultR(msg,
                        msg,
                        Level.ERROR,
                        Cause.SCHEMA));

                } else if (parentDimension.type() != null
                    && (parentDimension.type().equals(DimensionTypeEnum.TIME_DIMENSION))
                    && level.levelType() != null
                    && (level.levelType().equals(
                    LevelTypeEnum.REGULAR))) {
                    // If dimension type is 'time' then leveltype value
                    // could be 'timeyears', 'timedays' etc'
                    String msg = String.format("Level: levelType %s can only be used with a StandardDimension",
                        level.levelType().getValue());
                    results.add(new VerificationResultR(msg,
                        msg,
                        Level.ERROR,
                        Cause.SCHEMA));
                }
            }
        }
        // verify level's name is set
        if (isEmpty(level.name())) {
            results.add(new VerificationResultR("Level name must be set",
                "Level name must be set",
                Level.ERROR,
                Cause.SCHEMA));
        }

        // check level's column is in fact table
        String column = level.column();
        if (isEmpty(column)) {
            if (level.property() == null
                || level.property().size() == 0) {
                results.add(new VerificationResultR("Level: Column must be set",
                    "Level: Column must be set",
                    Level.ERROR,
                    Cause.SCHEMA));
            } else {
                results.addAll(level.property().stream()
                    .flatMap(p -> checkProperty(jdbcValidator, p, level, hierarchy, cube).stream())
                    .collect(toList()));
            }
        } else {
            // Enforces validation for all column types against invalid
            // value.
            String theMessage = null;
            try {
                for (int i = 0; i < DEF_LEVEL.length; i++) {
                    Field theField =
                        level.getClass().getDeclaredField(DEF_LEVEL[i]);
                    column = (String) theField.get(level);
                    results.addAll(checkColumn(
                        column,
                        DEF_LEVEL[i],
                        level,
                        jdbcValidator,
                        cube,
                        hierarchy));
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
        return results;
    }

    private static Collection<? extends VerificationResult> checkProperty(
        JdbcValidator jdbcValidator,
        Property property,
        org.eclipse.daanse.olap.rolap.dbmapper.api.Level level,
        Hierarchy hierarchy,
        Cube cube
    ) {
        List<VerificationResult> results = new ArrayList<>();

        // Check 'column' exists in 'table' if [level table] is
        // specified otherwise :: case of join.

        // It should exist in [hierarchy relation table] if it is
        // specified otherwise :: case of table.

        // It should exist in [fact table] :: case of degenerate
        // dimension where dimension columns exist in fact table and
        // there is no separate table.

        // check property's column is in table
        String column = property.column();
        if (isEmpty(column)) {
            results.add(new VerificationResultR("Column must be set",
                "Column must be set",
                Level.ERROR,
                Cause.SCHEMA));
        }
        // Database validity check, if database connection is successful
        if (jdbcValidator.isInitialized()) {
            String table = null;
            if (level != null) {
                // specified table for level's column'
                table = level.table();
            }
            if (isEmpty(table)) {
                if (hierarchy != null) {
                    if (hierarchy.relation() == null
                        && cube != null) {
                        // Case of degenerate dimension within cube,
                        // hierarchy table not specified
                        final Table factTable =
                            (Table) cube.fact();
                        if (!jdbcValidator.isColExists(
                            factTable.schema(),
                            factTable.name(),
                            column)) {
                            String msg = String.format("Degenerate dimension validation check - Column %s does not " +
                                    "exist in fact table",
                                column);
                            results.add(new VerificationResultR(msg,
                                msg,
                                Level.ERROR,
                                Cause.DATABASE));
                        }
                    } else if (hierarchy.relation()
                        instanceof Table) {
                        final Table parentTable =
                            (Table)
                                hierarchy.relation();
                        if (!jdbcValidator.isColExists(
                            parentTable.schema(),
                            parentTable.name(),
                            column)) {
                            String msg = String.format("Column {0} does not exist in Dimension table",
                                parentTable.name());
                            results.add(new VerificationResultR(msg,
                                msg,
                                Level.ERROR,
                                Cause.DATABASE));
                        }
                        checkTable(jdbcValidator, parentTable, isSchemaRequired);
                    }
                }
            } else {
                if (!jdbcValidator.isColExists(null, table, column)) {
                    String msg = String.format("Column %s does not exist in Level table %s",
                        column,
                        table);
                    results.add(new VerificationResultR(msg,
                        msg,
                        Level.ERROR,
                        Cause.DATABASE));
                }
            }
        }
        if (property.propertyFormatter() != null) {
            results.addAll(checkElementFormatter(property.propertyFormatter()));
        }

        return results;
    }

    private static Collection<? extends VerificationResult> checkJoin(Join join) {
        List<VerificationResult> results = new ArrayList<>();
        if (isEmpty(join.leftKey())) {
            results.add(new VerificationResultR("Join: Left key must be set",
                "Join: Left key must be set",
                Level.ERROR,
                Cause.SCHEMA));
        }
        if (isEmpty(join.rightKey())) {
            results.add(new VerificationResultR("Join: Right key must be set",
                "Join: Right key must be set",
                Level.ERROR,
                Cause.SCHEMA));
        }
        return results;
    }

    private static Collection<? extends VerificationResult> checkTable(
        JdbcValidator jdbcValidator,
        Table table,
        boolean isSchemaRequired
    ) {
        List<VerificationResult> results = new ArrayList<>();
        String tableName = table.name();
        if (!jdbcValidator.isTableExists(null, tableName)) {
            String msg = String.format("Table %s does not exist in database",
                tableName);
            results.add(new VerificationResultR(msg,
                msg,
                Level.ERROR,
                Cause.DATABASE));
        }

        String theSchema = table.schema();
        if (!isEmpty(theSchema)
            && !jdbcValidator.isSchemaExists(theSchema)) {
            String msg = String.format("Schema %s does not exist",
                theSchema);
            results.add(new VerificationResultR(msg,
                msg,
                Level.ERROR,
                Cause.DATABASE));
        }
        if (isEmpty(theSchema) && isSchemaRequired) {
            results.add(new VerificationResultR("Schema must be set",
                "Schema must be set",
                Level.ERROR,
                Cause.DATABASE));
        }
        return results;
    }

    private static Collection<? extends VerificationResult> checkNamedSetList(List<? extends NamedSet> collection) {
        if (collection != null) {
            return collection.stream()
                .flatMap(ns -> checkNamedSet(ns).stream())
                .collect(toList());
        }
        return List.of();
    }

    private static Collection<? extends VerificationResult> checkNamedSet(NamedSet namedSet) {
        List<VerificationResult> results = new ArrayList<>();
        if (isEmpty(namedSet.name())) {
            results.add(new VerificationResultR("NamedSet name must be set",
                "NamedSet name must be set",
                Level.ERROR,
                Cause.SCHEMA));
        }
        if (isEmpty(namedSet.formula())
            && namedSet.formulaElement() == null) {
            results.add(new VerificationResultR("NamedSet Formula must be set",
                "NamedSet Formula must be set",
                Level.ERROR,
                Cause.SCHEMA));
        }

        if (namedSet.formulaElement() != null) {
            results.addAll(checkFormula(namedSet.formulaElement()));
        }

        return results;
    }

    private static Collection<? extends VerificationResult> checkFormula(Formula formula) {
        List<VerificationResult> results = new ArrayList<>();
        if (isEmpty(formula.cdata())) {
            results.add(new VerificationResultR("Formula must be set",
                "Formula must be set",
                Level.ERROR,
                Cause.SCHEMA));
        }

        return results;
    }

    private static Collection<? extends VerificationResult> checkUserDefinedFunction(UserDefinedFunction udf) {
        List<VerificationResult> results = new ArrayList<>();
        if (isEmpty(udf.name())) {
            results.add(new VerificationResultR("UserDefinedFunction name must be set",
                "UserDefinedFunction name must be set",
                Level.ERROR,
                Cause.SCHEMA));
        }
        if (isEmpty(udf.className())
            && udf.script() == null) {
            results.add(new VerificationResultR("UserDefinedFunction Either a Class Name or a Script are required",
                "UserDefinedFunction Either a Class Name or a Script are required",
                Level.ERROR,
                Cause.SCHEMA));
        }

        return results;
    }

    private static Collection<? extends VerificationResult> checkElementFormatter(ElementFormatter elementFormatter) {
        List<VerificationResult> results = new ArrayList<>();
        if (isEmpty(elementFormatter.className())
            && elementFormatter.script() == null) {
            results.add(new VerificationResultR("Formatter: either a Class Name or a Script are required",
                "Formatter: either a Class Name or a Script are required",
                Level.ERROR,
                Cause.SCHEMA));
        }
        return results;
    }

    private static Collection<? extends VerificationResult> checkCalculatedMember(CalculatedMember calculatedMember) {
        List<VerificationResult> results = new ArrayList<>();
        if (isEmpty(calculatedMember.name())) {
            results.add(new VerificationResultR("CalculatedMember name must be set",
                "CalculatedMember name must be set",
                Level.ERROR,
                Cause.SCHEMA));
        }
        if (isEmpty(calculatedMember.dimension())) {
            results.add(new VerificationResultR("CalculatedMember Dimension must be set",
                "CalculatedMember Dimension must be set",
                Level.ERROR,
                Cause.SCHEMA));
        }
        if (isEmpty(calculatedMember.formula())
            && calculatedMember.formulaElement() == null) {
            results.add(new VerificationResultR("CalculatedMember Formula must be set",
                "CalculatedMember Formula must be set",
                Level.ERROR,
                Cause.SCHEMA));
        }
        if (calculatedMember.formulaElement() != null) {
            results.addAll(checkFormula(calculatedMember.formulaElement()));
        }

        if (calculatedMember.cellFormatter() != null) {
            results.addAll(checkElementFormatter(calculatedMember.cellFormatter()));
        }

        return results;
    }

    /**
     * Validates a column, and returns an error message if it is invalid.
     *
     * @param column          Column
     * @param fieldName       Field name
     * @param level           Level
     * @param jdbcValidator   JDBC validator
     * @param cube            Cube
     * @param parentHierarchy Hierarchy
     * @return Error message if invalid, null if valid
     */
    private static Collection<? extends VerificationResult> checkColumn(
        String column,
        String fieldName,
        org.eclipse.daanse.olap.rolap.dbmapper.api.Level level,
        JdbcValidator jdbcValidator,
        Cube cube,
        Hierarchy parentHierarchy
    ) {
        List<VerificationResult> results = new ArrayList<>();
        if (!isEmpty(column)) {
            // database validity check, if database connection is successful
            if (jdbcValidator.isInitialized()) {
                // specified table for level's column
                String table = level.table();
                // If table has been changed in join then sets the table value
                // to null to cause "tableMustBeSet" validation fail.
                if (!isEmpty(table)
                    && parentHierarchy != null
                    && parentHierarchy.relation() instanceof Join) {
                    TreeSet<String> joinTables = new TreeSet<String>();
                    SchemaExplorer.getTableNamesForJoin(
                        parentHierarchy.relation(), joinTables);
                    if (!joinTables.contains(table)) {

                        results.add(new VerificationResultR("Table value does not correspond to any join",
                            "Table value does not correspond to any join",
                            Level.ERROR,
                            Cause.SCHEMA));
                    }
                }

                if (!isEmpty(table)
                    && parentHierarchy != null
                    && parentHierarchy.relation() instanceof Table) {
                    final Table parentTable =
                        (Table) parentHierarchy.relation();
                    Table theTable = parentTable;
                    String compareTo =
                        (theTable.alias() != null
                            && theTable.alias().trim().length() > 0)
                            ? theTable.alias()
                            : theTable.name();
                    if (!table.equals(compareTo)) {
                        results.add(new VerificationResultR("Table value does not correspond to Hierarchy Relation",
                            "Table value does not correspond to Hierarchy Relation",
                            Level.ERROR,
                            Cause.SCHEMA));
                    }
                    checkTable(jdbcValidator, parentTable, isSchemaRequired);
                }

                if (!isEmpty(table)
                    && parentHierarchy != null
                    && parentHierarchy.relation() instanceof View) {
                    results.add(new VerificationResultR("Table for column cannot be set in View",
                        "Table for column cannot be set in View",
                        Level.ERROR,
                        Cause.SCHEMA));
                }

                if (isEmpty(table)) {
                    if (parentHierarchy != null) {
                        if (parentHierarchy.relation() == null
                            && cube != null) {
                            // case of degenerate dimension within cube,
                            // hierarchy table not specified
                            if (!jdbcValidator.isColExists(
                                ((Table) cube.fact()).schema(),
                                ((Table) cube.fact()).name(),
                                column)) {
                                String msg = String.format("Degenerate dimension validation check - Column %s does " +
                                        "not exist in fact table",
                                    column);
                                results.add(new VerificationResultR(msg,
                                    msg,
                                    Level.ERROR,
                                    Cause.SCHEMA));
                            }
                        } else if (parentHierarchy.relation()
                            instanceof Table) {
                            final Table parentTable =
                                (Table) parentHierarchy.relation();
                            if (!jdbcValidator.isColExists(
                                parentTable.schema(),
                                parentTable.name(),
                                column)) {
                                String msg = String.format("Column %s defined in field %s does not exist in table %s",
                                    isEmpty(column.trim())
                                        ? "' '"
                                        : column,
                                    fieldName,
                                    parentTable.name());
                                results.add(new VerificationResultR(msg,
                                    msg,
                                    Level.ERROR,
                                    Cause.DATABASE));
                                results.addAll(checkTable(jdbcValidator, (Table)parentHierarchy.relation(), isSchemaRequired));
                            }
                        } else if (parentHierarchy.relation()
                            instanceof Join) {
                            // relation is join, table should be specified
                            results.add(new VerificationResultR("Table must be set",
                                "Table must be set",
                                Level.ERROR,
                                Cause.DATABASE));

                            results.addAll(checkJoin((Join)parentHierarchy.relation()));
                        }
                    }
                } else {
                    String schema = null;
                    // if using Joins then gets the table name for isColExists
                    // validation.
                    if (parentHierarchy != null
                        && parentHierarchy.relation()
                        instanceof Join) {
                        String[] schemaAndTable =
                            SchemaExplorer.getTableNameForAlias(
                                parentHierarchy.relation(),
                                table);
                        schema = schemaAndTable[0];
                        table = schemaAndTable[1];
                        results.addAll(checkJoin((Join)parentHierarchy.relation()));
                    }
                    if (!jdbcValidator.isColExists(schema, table, column)) {
                        String msg = String.format("Column %s defined in field %s does not exist in table {2}",
                            isEmpty(column.trim())
                                ? "' '"
                                : column,
                            fieldName,
                            table);
                        results.add(new VerificationResultR(msg,
                            msg,
                            Level.ERROR,
                            Cause.SCHEMA));
                    }
                }
            }
        }
        return results;
    }

    private static boolean isEmpty(String v) {
        return (v == null) || v.equals("");
    }

}
