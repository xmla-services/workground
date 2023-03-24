package org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.mandantory;

import org.eclipse.daanse.olap.rolap.dbmapper.api.Action;
import org.eclipse.daanse.olap.rolap.dbmapper.api.AggColumnName;
import org.eclipse.daanse.olap.rolap.dbmapper.api.AggForeignKey;
import org.eclipse.daanse.olap.rolap.dbmapper.api.AggLevel;
import org.eclipse.daanse.olap.rolap.dbmapper.api.AggMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.api.AggMeasureFactCount;
import org.eclipse.daanse.olap.rolap.dbmapper.api.AggName;
import org.eclipse.daanse.olap.rolap.dbmapper.api.AggPattern;
import org.eclipse.daanse.olap.rolap.dbmapper.api.AggTable;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Annotation;
import org.eclipse.daanse.olap.rolap.dbmapper.api.CalculatedMember;
import org.eclipse.daanse.olap.rolap.dbmapper.api.CalculatedMemberProperty;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Closure;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Column;
import org.eclipse.daanse.olap.rolap.dbmapper.api.ColumnDef;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Cube;
import org.eclipse.daanse.olap.rolap.dbmapper.api.CubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.api.CubeGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.api.CubeUsage;
import org.eclipse.daanse.olap.rolap.dbmapper.api.DimensionGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.api.DimensionUsage;
import org.eclipse.daanse.olap.rolap.dbmapper.api.DrillThroughAttribute;
import org.eclipse.daanse.olap.rolap.dbmapper.api.DrillThroughMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.api.ElementFormatter;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Formula;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Hierarchy;
import org.eclipse.daanse.olap.rolap.dbmapper.api.HierarchyGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Hint;
import org.eclipse.daanse.olap.rolap.dbmapper.api.InlineTable;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Join;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Measure;
import org.eclipse.daanse.olap.rolap.dbmapper.api.MemberGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.api.NamedSet;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Parameter;
import org.eclipse.daanse.olap.rolap.dbmapper.api.PrivateDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Property;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Role;
import org.eclipse.daanse.olap.rolap.dbmapper.api.RoleUsage;
import org.eclipse.daanse.olap.rolap.dbmapper.api.SQL;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.api.SchemaGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Table;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Union;
import org.eclipse.daanse.olap.rolap.dbmapper.api.UserDefinedFunction;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Value;
import org.eclipse.daanse.olap.rolap.dbmapper.api.View;
import org.eclipse.daanse.olap.rolap.dbmapper.api.VirtualCube;
import org.eclipse.daanse.olap.rolap.dbmapper.api.WritebackAttribute;
import org.eclipse.daanse.olap.rolap.dbmapper.api.WritebackMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.api.WritebackTable;
import org.eclipse.daanse.olap.rolap.dbmapper.api.enums.DimensionTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.api.enums.LevelTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Cause;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.VerificationResult;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.AbstractSchemaWalker;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaExplorer;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.VerificationResultR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.TreeSet;

import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.ERROR;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.WARNING;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.*;

public class MandantoriesSchemaWalker extends AbstractSchemaWalker {

    private final Logger LOGGER = LoggerFactory.getLogger(MandantoriesSchemaWalker.class);
    private final String[] DEF_LEVEL = {"column", "nameColumn", "parentColumn", "ordinalColumn", "captionColumn"};

    public MandantoriesSchemaWalker(MandantoriesVerifierConfig config) {
    }

    @Override
    public List<VerificationResult> checkSchema(Schema schema) {
        super.checkSchema(schema);
        if (schema != null ) {
            if (isEmpty(schema.name())) {
                results.add(new VerificationResultR(SCHEMA, SCHEMA_NAME_MUST_BE_SET, ERROR,
                    Cause.SCHEMA));
            }
        }
        else {
            results.add(new VerificationResultR(SCHEMA, SCHEMA_MUST_BE_NOT_NULL, ERROR,
                Cause.SCHEMA));
        }

        return results;
    }

    @Override
    protected void checkCube(Cube cube) {
        super.checkCube(cube);
        if (cube != null ) {
            if (isEmpty(cube.name())) {
                results.add(new VerificationResultR(CUBE, CUBE_NAME_MUST_SET, Level.ERROR,
                    Cause.SCHEMA));
            }

            if (cube.fact() == null
                || ((cube.fact() instanceof Table) && isEmpty(((Table) cube.fact()).name()))
                || ((cube.fact() instanceof View) && isEmpty(((View) cube.fact()).alias()))) {
                String msg = String.format(FACT_NAME_MUST_BE_SET, orNotSet(cube.name()));
                results.add(new VerificationResultR(CUBE, msg, ERROR,
                    Cause.SCHEMA));
            }

            //CubeDimension
            if (cube.dimensionUsageOrDimension() == null || cube.dimensionUsageOrDimension()
                .isEmpty()) {
                String msg = String.format(CUBE_WITH_NAME_MUST_CONTAIN_, orNotSet(cube.name()), DIMENSIONS);
                results.add(new VerificationResultR(DIMENSIONS, msg,
                    ERROR, Cause.SCHEMA));
            }

            //Measure
            if (cube.measure() == null || cube.measure()
                .isEmpty()) {
                String msg = String.format(CUBE_WITH_NAME_MUST_CONTAIN_, orNotSet(cube.name()), MEASURE);
                results.add(new VerificationResultR(MEASURE, msg, ERROR,
                    Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkMeasure(Measure measure, Cube cube) {
        super.checkMeasure(measure, cube);
        if (measure != null ) {
            if (isEmpty(measure.name())) {
                String msg = String.format(MEASURE_NAME_MUST_BE_SET, orNotSet(cube.name()));
                results.add(new VerificationResultR(MEASURE, msg, ERROR, Cause.SCHEMA));

            }
            if (isEmpty(measure.aggregator())) {
                String msg = String.format(MEASURE_AGGREGATOR_MUST_BE_SET, orNotSet(cube.name()));
                results.add(new VerificationResultR(MEASURE, msg, ERROR, Cause.SCHEMA));
            }

            //ExpressionView
            if (measure.measureExpression() != null) {
                // Measure expressions are OK
            } else if (isEmpty(measure.column())) {
                String msg = String.format(MEASURE_COLUMN_MUST_BE_SET, orNotSet(cube.name()));
                results.add(new VerificationResultR(MEASURE, msg, ERROR,
                    Cause.SCHEMA));

            }
        }
    }

    @Override
    protected void checkElementFormatter(ElementFormatter elementFormatter) {
        super.checkElementFormatter(elementFormatter);
        if (elementFormatter != null) {
            if (isEmpty(elementFormatter.className()) && elementFormatter.script() == null) {
                results.add(new VerificationResultR(ELEMENT_FORMATTER,
                    FORMATTER_EITHER_A_CLASS_NAME_OR_A_SCRIPT_ARE_REQUIRED, ERROR, Cause.SCHEMA));
            }
        }

    }

    @Override
    protected void checkCubeDimension(CubeDimension cubeDimension, Cube cube) {
        super.checkCubeDimension(cubeDimension, cube);
        if (cube != null ) {
            if (isEmpty(cubeDimension.name())) {
                String msg = String.format(CUBE_DIMENSION_NAME_MUST_BE_SET, cube != null ? orNotSet(cube.name()) : NOT_SET);
                results.add(new VerificationResultR(CUBE_DIMENSION, msg, ERROR, Cause.SCHEMA));

            }

            if (cubeDimension instanceof DimensionUsage) {
                if (isEmpty(((DimensionUsage) cubeDimension).source())) {
                    String msg = String.format(SOURCE_MUST_BE_SET, orNotSet(cubeDimension.name()));
                    results.add(new VerificationResultR(CUBE_DIMENSION, msg, ERROR, Cause.SCHEMA));
                }
            }
        }
    }

    @Override
    protected void checkHierarchy(Hierarchy hierarchy, PrivateDimension cubeDimension, Cube cube) {
        super.checkHierarchy(hierarchy, cubeDimension, cube);

        if (hierarchy != null ) {
            if (hierarchy.relation() instanceof Join) {
                if (isEmpty(hierarchy.primaryKeyTable())) {
                    if (isEmpty(hierarchy.primaryKey())) {
                        String msg = String.format(PRIMARY_KEY_TABLE_AND_PRIMARY_KEY_MUST_BE_SET_FOR_JOIN_,
                            orNotSet(cubeDimension.name()));
                        results.add(new VerificationResultR(
                            HIERARCHY, msg, ERROR, Cause.SCHEMA));

                    } else {
                        String msg = String.format(PRIMARY_KEY_TABLE_MUST_BE_SET_FOR_JOIN, orNotSet(cubeDimension.name()));
                        results.add(new VerificationResultR(HIERARCHY, msg, ERROR, Cause.SCHEMA));
                    }
                }
                if (isEmpty(hierarchy.primaryKey())) {
                    String msg = String.format(PRIMARY_KEY_MUST_BE_SET_FOR_JOIN, orNotSet(cubeDimension.name()));
                    results.add(new VerificationResultR(HIERARCHY, msg, ERROR, Cause.SCHEMA));
                }
            }

            //Level
            List<? extends org.eclipse.daanse.olap.rolap.dbmapper.api.Level> levels = hierarchy.level();
            if (levels == null || levels.size() == 0) {
                String msg = String.format(LEVEL_MUST_BE_SET_FOR_HIERARCHY, orNotSet(cubeDimension.name()));
                results.add(new VerificationResultR(HIERARCHY,
                    msg, ERROR, Cause.SCHEMA));
            }

            // Validates against primaryKeyTable name on field when using
            // Table.
            if (hierarchy.relation() instanceof Table) {
                if (!isEmpty(hierarchy.primaryKeyTable())) {
                    String msg = String.format(HIERARCHY_TABLE_FIELD_MUST_BE_EMPTY, orNotSet(cubeDimension.name()));
                    results.add(new VerificationResultR(HIERARCHY, msg, ERROR, Cause.SCHEMA));
                }
                checkTable((Table) hierarchy.relation());
            }

            // Validates that the value at primaryKeyTable corresponds to
            // tables in joins.
            String primaryKeyTable = hierarchy.primaryKeyTable();
            if (!isEmpty(primaryKeyTable) && (hierarchy.relation() instanceof Join)) {
                TreeSet<String> joinTables = new TreeSet<>();
                SchemaExplorer.getTableNamesForJoin(hierarchy.relation(), joinTables);
                if (!joinTables.contains(primaryKeyTable)) {
                    String msg = String.format(HIERARCHY_TABLE_VALUE_DOES_NOT_CORRESPOND_TO_ANY_JOIN,
                        orNotSet(cubeDimension.name()));
                    results.add(new VerificationResultR(HIERARCHY, msg, ERROR, Cause.DATABASE));
                }
                checkJoin((Join) hierarchy.relation());
            }

            if (!isEmpty(primaryKeyTable) && (hierarchy.relation() instanceof Table)) {
                Table theTable = (Table) hierarchy.relation();
                String compareTo = (theTable.alias() != null && theTable.alias()
                    .trim()
                    .length() > 0) ? theTable.alias() : theTable.name();
                if (!primaryKeyTable.equals(compareTo)) {
                    String msg = String.format(HIERARCHY_TABLE_VALUE_DOES_NOT_CORRESPOND_TO_HIERARCHY_RELATION,
                        orNotSet(cubeDimension.name()));
                    results.add(new VerificationResultR(HIERARCHY,
                        msg, ERROR,
                        Cause.DATABASE));
                }
                checkTable(theTable);
            }
        }

    }

    @Override
    protected void checkJoin(Join join) {
        super.checkJoin(join);
        if (join != null ) {
            if (isEmpty(join.leftKey())) {
                results.add(new VerificationResultR(JOIN, JOIN_LEFT_KEY_MUST_BE_SET, ERROR,
                    Cause.SCHEMA));
            }
            if (isEmpty(join.rightKey())) {
                results.add(new VerificationResultR(JOIN, JOIN_RIGHT_KEY_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (join.relation() == null || join.relation().size() < 2) {
                results.add(new VerificationResultR(JOIN, JOIN_RELATION_MUST_BE_SET_LEFT_AND_RIGHT,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkTable(Table table) {
        super.checkTable(table);
        if (table != null ) {
            if (isEmpty(table.name())) {
                results.add(
                    new VerificationResultR(TABLE, TABLE_NAME_MUST_BE_SET, ERROR, Cause.DATABASE));
            }

            String theSchema = table.schema();
            if (isEmpty(theSchema) && isSchemaRequired()) {
                results.add(
                    new VerificationResultR(TABLE, SCHEMA_MUST_BE_SET, ERROR, Cause.DATABASE));
            }
        }
    }

    @Override
    protected void checkLevel(
        org.eclipse.daanse.olap.rolap.dbmapper.api.Level level, Hierarchy hierarchy,
        PrivateDimension parentDimension, Cube cube
    ) {
        super.checkLevel(level, hierarchy, parentDimension, cube);
        // Check 'column' exists in 'table' if table is specified
        // otherwise :: case of join.

        // It should exist in relation table if it is specified
        // otherwise :: case of table.

        // It should exist in fact table :: case of degenerate dimension
        // where dimension columns exist in fact table and there is no
        // separate table.

        if (level != null ) {
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
                        String msg = String.format(LEVEL_LEVEL_TYPE_S_CAN_ONLY_BE_USED_WITH_A_TIME_DIMENSION,
                            level.levelType()
                                .getValue());
                        results.add(new VerificationResultR(LEVEL, msg, ERROR, Cause.SCHEMA));

                    } else if (parentDimension.type() != null && (parentDimension.type()
                        .equals(DimensionTypeEnum.TIME_DIMENSION)) && level.levelType() != null && (level.levelType()
                        .equals(LevelTypeEnum.REGULAR))) {
                        // If dimension type is 'time' then leveltype value
                        // could be 'timeyears', 'timedays' etc'
                        String msg = String.format(LEVEL_TYPE_S_CAN_ONLY_BE_USED_WITH_A_STANDARD_DIMENSION,
                            level.levelType()
                                .getValue());
                        results.add(new VerificationResultR(LEVEL, msg, ERROR, Cause.SCHEMA));
                    }
                }
            }
            if (level.type() != null) {
                results.add(new VerificationResultR(LEVEL, LEVEL_TYPE_MUST_BE_SET, WARNING, Cause.SCHEMA));
            }
            // verify level's name is set
            if (isEmpty(level.name())) {
                String msg = String.format(LEVEL_NAME_MUST_BE_SET, orNotSet(hierarchy.name()));
                results.add(new VerificationResultR(LEVEL, msg, ERROR, Cause.SCHEMA));
            }

            // check level's column is in fact table
            String column = level.column();
            if (isEmpty(column)) {
                if (level.property() == null || level.property()
                    .size() == 0) {
                    String msg = String.format(LEVEL_COLUMN_MUST_BE_SET, orNotSet(hierarchy.name()));
                    results.add(new VerificationResultR(LEVEL, msg, ERROR, Cause.SCHEMA));
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
                        theField.setAccessible(true);
                        column = (String) theField.get(level);
                        checkColumn(column, element, level, cube, hierarchy);
                        if (theMessage != null) {
                            break;
                        }
                    }
                } catch (Exception ex) {
                    LOGGER.error("Validation", ex);
                }
            }
            if (level.memberFormatter() != null) {
                checkElementFormatter(level.memberFormatter());
            }
        }
    }

    @Override
    protected void checkProperty(
        Property property, org.eclipse.daanse.olap.rolap.dbmapper.api.Level level,
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
        if (property != null ) {
            String column = property.column();
            if (isEmpty(column)) {
                results.add(new VerificationResultR(PROPERTY, PROPERTY_COLUMN_MUST_BE_SET, ERROR, Cause.SCHEMA));
            }

            if (property.type() == null) {
                results.add(new VerificationResultR(PROPERTY, PROPERTY_TYPE_MUST_BE_SET, WARNING, Cause.SCHEMA));
            }
        }

    }

    @Override
    protected void checkVirtualCube(VirtualCube virtCube) {
        super.checkVirtualCube(virtCube);
        if (virtCube != null ) {
            if (isEmpty(virtCube.name())) {
                results.add(new VerificationResultR(VIRTUAL_CUBE, VIRTUAL_CUBE_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));

            }
            if (virtCube.virtualCubeDimension() == null || virtCube.virtualCubeDimension().isEmpty()) {
                String msg = String.format(VIRTUAL_CUBE_MUST_CONTAIN_DIMENSIONS, orNotSet(virtCube.name()));
                results.add(new VerificationResultR(VIRTUAL_CUBE,
                    msg, ERROR, Cause.SCHEMA));
            }
            if (virtCube.virtualCubeMeasure() == null || virtCube.virtualCubeMeasure().isEmpty()) {
                String msg = String.format(VIRTUAL_CUBE_MUST_CONTAIN_MEASURES, orNotSet(virtCube.name()));
                results.add(new VerificationResultR(VIRTUAL_CUBE, msg, ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkCalculatedMember(CalculatedMember calculatedMember) {
        super.checkCalculatedMember(calculatedMember);
        if (calculatedMember != null ) {
            if (isEmpty(calculatedMember.name())) {
                results.add(new VerificationResultR(CALCULATED_MEMBER,
                    CALCULATED_MEMBER_NAME_MUST_BE_SET, ERROR, Cause.SCHEMA));
            }
            if (isEmpty(calculatedMember.dimension())) {
                String msg = String.format(DIMENSION_MUST_BE_SET_FOR_CALCULATED_MEMBER, orNotSet(calculatedMember.name()));
                results.add(new VerificationResultR(CALCULATED_MEMBER, msg, ERROR, Cause.SCHEMA));
            }
            if (isEmpty(calculatedMember.formula()) && calculatedMember.formulaElement() == null) {
                String msg = String.format(FORMULA_MUST_BE_SET_FOR_CALCULATED_MEMBER, orNotSet(calculatedMember.name()));
                results.add(new VerificationResultR(CALCULATED_MEMBER, msg, ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkFormula(Formula formula) {
        super.checkFormula(formula);
        if (formula != null ) {
            if (isEmpty(formula.cdata())) {
                results.add(
                    new VerificationResultR(FORMULA, FORMULA_MUST_BE_SET, ERROR, Cause.SCHEMA));
            }
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
        String column, String fieldName, org.eclipse.daanse.olap.rolap.dbmapper.api.Level level,
        Cube cube, Hierarchy parentHierarchy
    ) {
        super.checkColumn(column, fieldName, level, cube, parentHierarchy);
        if (!isEmpty(column)) {

            // specified table for level's column
            String table = level.table();
            // If table has been changed in join then sets the table value
            // to null to cause "tableMustBeSet" validation fail.
            if (!isEmpty(table) && parentHierarchy != null
                && parentHierarchy.relation() instanceof Join) {
                TreeSet<String> joinTables = new TreeSet<>();
                SchemaExplorer.getTableNamesForJoin(parentHierarchy.relation(), joinTables);
                if (!joinTables.contains(table)) {

                    results.add(new VerificationResultR(LEVEL,
                        TABLE_VALUE_DOES_NOT_CORRESPOND_TO_ANY_JOIN, ERROR, Cause.SCHEMA));
                }
            }

            if (!isEmpty(table) && parentHierarchy != null
                && parentHierarchy.relation() instanceof Table) {
                final Table parentTable = (Table) parentHierarchy.relation();
                Table theTable = parentTable;
                String compareTo = (theTable.alias() != null && theTable.alias()
                    .trim()
                    .length() > 0) ? theTable.alias() : theTable.name();
                if (!table.equals(compareTo)) {
                    results.add(new VerificationResultR(LEVEL,
                        TABLE_VALUE_DOES_NOT_CORRESPOND_TO_HIERARCHY_RELATION, ERROR, Cause.SCHEMA));
                }
                checkTable(parentTable);
            }

            if (!isEmpty(table) && parentHierarchy != null
                && parentHierarchy.relation() instanceof View) {
                results.add(new VerificationResultR(LEVEL,
                    TABLE_FOR_COLUMN_CANNOT_BE_SET_IN_VIEW, ERROR, Cause.SCHEMA));
            }

            if (isEmpty(table)) {
                if (parentHierarchy != null) {
                    if (parentHierarchy.relation() instanceof Join) {
                        // relation is join, table should be specified
                        results.add(new VerificationResultR(LEVEL, TABLE_MUST_BE_SET, ERROR,
                            Cause.DATABASE));

                        checkJoin((Join) parentHierarchy.relation());
                    }
                }
            } else {
                // if using Joins then gets the table name for doesColumnExist
                // validation.
                if (parentHierarchy != null && parentHierarchy.relation() instanceof Join) {
                    checkJoin((Join) parentHierarchy.relation());
                }
            }
        }
    }

    @Override
    protected void checkNamedSet(NamedSet namedSet) {
        super.checkNamedSet(namedSet);
        if (namedSet != null ) {
            if (isEmpty(namedSet.name())) {
                results.add(new VerificationResultR(NAMED_SET, NAMED_SET_NAME_MUST_BE_SET, ERROR,
                    Cause.SCHEMA));
            }
            if (isEmpty(namedSet.formula()) && namedSet.formulaElement() == null) {
                results.add(new VerificationResultR(NAMED_SET, NAMED_SET_FORMULA_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkUserDefinedFunction(UserDefinedFunction udf) {
        super.checkUserDefinedFunction(udf);
        if (udf != null ) {
            if (isEmpty(udf.name())) {
                results.add(new VerificationResultR(USER_DEFINED_FUNCTION,
                    USER_DEFINED_FUNCTION_NAME_MUST_BE_SET, ERROR, Cause.SCHEMA));
            }
            if (isEmpty(udf.className()) && udf.script() == null) {
                String msg = String.format(EITHER_A_CLASS_NAME_OR_A_SCRIPT_ARE_REQUIRED, orNotSet(udf.name()));
                results.add(new VerificationResultR(USER_DEFINED_FUNCTION, msg, ERROR, Cause.SCHEMA));
            }
        }
    }
    // were add from mondrian.xml
    @Override
    protected void checkCubeUsage(CubeUsage cubeUsage) {
        super.checkCubeUsage(cubeUsage);
        if (cubeUsage != null ) {
            if (cubeUsage.cubeName() == null) {
                results.add(new VerificationResultR(CUBE_USAGE, CUBE_USAGE_CUBE_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkClosure(Closure closure) {
        super.checkClosure(closure);
        if (closure != null) {
            if (isEmpty(closure.parentColumn())) {
                results.add(new VerificationResultR(CLOSURE, CLOSURE_PARENT_COLUMN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (isEmpty(closure.childColumn())) {
                results.add(new VerificationResultR(CLOSURE, CLOSURE_CHILD_COLUMN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (closure.table() == null) {
                results.add(new VerificationResultR(CLOSURE, CLOSURE_TABLE_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkCalculatedMemberProperty(CalculatedMemberProperty calculatedMemberProperty) {
        super.checkCalculatedMemberProperty(calculatedMemberProperty);
        if (calculatedMemberProperty != null ) {
            if (isEmpty(calculatedMemberProperty.name())) {
                results.add(new VerificationResultR(CALCULATED_MEMBER_PROPERTY, CALCULATED_MEMBER_PROPERTY_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkView(View view) {
        super.checkView(view);
        if (view != null ) {
            if (isEmpty(view.alias())) {
                results.add(new VerificationResultR(VIEW, VIEW_ALIAS_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkSQL(SQL sql) {
        super.checkSQL(sql);
        if (sql != null) {
            if (isEmpty(sql.dialect())) {
                results.add(new VerificationResultR(SQL, SQL_DIALECT_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkHint(Hint hint) {
        super.checkHint(hint);
        if (hint != null ) {
            if (isEmpty(hint.type())) {
                results.add(new VerificationResultR(HINT, HINT_TYPE_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkInlineTable(InlineTable inlineTable) {
        super.checkInlineTable(inlineTable);
        if (inlineTable != null ) {
            if (inlineTable.columnDefs() == null) {
                results.add(new VerificationResultR(INLINE_TABLE, INLINE_TABLE_COLUMN_DEFS_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (inlineTable.rows() == null) {
                results.add(new VerificationResultR(INLINE_TABLE, INLINE_TABLE_ROWS_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkColumnDef(ColumnDef columnDef) {
        super.checkColumnDef(columnDef);
        if (columnDef != null ) {
            if (isEmpty(columnDef.name())) {
                results.add(new VerificationResultR(COLUMN_DEF, COLUMN_DEF_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (columnDef.type() == null) {
                results.add(new VerificationResultR(COLUMN_DEF, COLUMN_DEF_TYPE_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkValue(Value value) {
        super.checkValue(value);
        if (value != null ) {
            if (isEmpty(value.column())) {
                results.add(new VerificationResultR(VALUE, VALUE_COLUMN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkAggTable(AggTable aggTable) {
        super.checkAggTable(aggTable);
        if (aggTable != null ) {
            if (aggTable.aggFactCount() == null) {
                results.add(new VerificationResultR(AGG_TABLE, AGG_TABLE_AGG_FACT_COUNT_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkAggName(AggName aggName) {
        super.checkAggName(aggName);
        if (aggName != null ) {
            if (isEmpty(aggName.name())) {
                results.add(new VerificationResultR(AGG_NAME, AGG_NAME_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkAggPattern(AggPattern aggPattern) {
        super.checkAggPattern(aggPattern);
        if (aggPattern != null ) {
            if (isEmpty(aggPattern.pattern())) {
                results.add(new VerificationResultR(AGG_PATTERN, AGG_PATTERN_PATTERN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkAggColumnName(AggColumnName aggColumnName) {
        super.checkAggColumnName(aggColumnName);
        if (aggColumnName != null ) {
            if (isEmpty(aggColumnName.column())) {
                results.add(new VerificationResultR(AGG_COLUMN_NAME, AGG_COLUMN_NAME_COLUMN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkAggMeasureFactCount(AggMeasureFactCount aggMeasureFactCount) {
        super.checkAggMeasureFactCount(aggMeasureFactCount);
        if (aggMeasureFactCount != null ) {
            if (isEmpty(aggMeasureFactCount.factColumn())) {
                results.add(new VerificationResultR(AGG_MEASURE_FACT_COUNT, AGG_MEASURE_FACT_COUNT_FACT_COLUMN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkAggForeignKey(AggForeignKey aggForeignKey) {
        super.checkAggForeignKey(aggForeignKey);
        if (aggForeignKey != null ) {
            if (isEmpty(aggForeignKey.factColumn())) {
                results.add(new VerificationResultR(AGG_FOREIGN_KEY, AGG_FOREIGN_KEY_FACT_COLUMN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (isEmpty(aggForeignKey.aggColumn())) {
                results.add(new VerificationResultR(AGG_FOREIGN_KEY, AGG_FOREIGN_KEY_AGG_COLUMN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkAggLevel(AggLevel aggLevel) {
        super.checkAggLevel(aggLevel);
        if (aggLevel != null ) {
            if (aggLevel.name() == null) {
                results.add(new VerificationResultR(AGG_LEVEL, AGG_LEVEL_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (aggLevel.column() == null) {
                results.add(new VerificationResultR(AGG_LEVEL, AGG_LEVEL_COLUMN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkAggMeasure(AggMeasure aggMeasure) {
        super.checkAggMeasure(aggMeasure);
        if (aggMeasure != null ) {
            if (aggMeasure.column() == null) {
                results.add(new VerificationResultR(AGG_MEASURE, AGG_MEASURE_COLUMN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (aggMeasure.name() == null) {
                results.add(new VerificationResultR(AGG_MEASURE, AGG_MEASURE_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkColumn(Column column) {
        super.checkColumn(column);
        if (column != null ) {
            if (column.name() == null) {
                results.add(new VerificationResultR(COLUMN, COLUMN_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkRole(Role role) {
        super.checkRole(role);
        if (role != null ) {
            if (role.name() == null) {
                results.add(new VerificationResultR(ROLE, ROLE_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkSchemaGrant(SchemaGrant schemaGrant) {
        super.checkSchemaGrant(schemaGrant);
        if (schemaGrant != null ) {
            if (schemaGrant.access() == null) {
                results.add(new VerificationResultR(SCHEMA_GRANT, SCHEMA_GRANT_ACCESS_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkCubeGrant(CubeGrant cubeGrant) {
        super.checkCubeGrant(cubeGrant);
        if (cubeGrant != null ) {
            if (isEmpty(cubeGrant.cube())) {
                results.add(new VerificationResultR(CUBE_GRANT, CUBE_GRANT_CUBE_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkDimensionGrant(DimensionGrant dimensionGrant) {
        super.checkDimensionGrant(dimensionGrant);
        if (dimensionGrant != null ) {
            if (isEmpty(dimensionGrant.dimension())) {
                results.add(new VerificationResultR(DIMENSION_GRANT, DIMENSION_GRANT_DIMENSION_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkHierarchyGrant(HierarchyGrant hierarchyGrant) {
        super.checkHierarchyGrant(hierarchyGrant);
        if (hierarchyGrant != null ) {
            if (isEmpty(hierarchyGrant.hierarchy())) {
                results.add(new VerificationResultR(HIERARCHY_GRANT, HIERARCHY_GRANT_HIERARCHY_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkMemberGrant(MemberGrant memberGrant) {
        super.checkMemberGrant(memberGrant);
        if (memberGrant != null ) {
            if (isEmpty(memberGrant.member())) {
                results.add(new VerificationResultR(MEMBER_GRANT, MEMBER_GRANT_MEMBER_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (memberGrant.access() == null) {
                results.add(new VerificationResultR(MEMBER_GRANT, MEMBER_GRANT_ACCESS_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkUnion(Union union) {
        super.checkUnion(union);
        if (union != null) {
            if (union.roleUsage() == null) {
                results.add(new VerificationResultR(UNION, UNION_ROLE_USAGE_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkRoleUsage(RoleUsage roleUsage) {
        super.checkRoleUsage(roleUsage);
        if (roleUsage != null) {
            if (isEmpty(roleUsage.roleName())) {
                results.add(new VerificationResultR(ROLE_USAGE, ROLE_USAGE_ROLE_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkParameter(Parameter parameter) {
        super.checkParameter(parameter);
        if(parameter != null) {
            if (isEmpty(parameter.name())) {
                results.add(new VerificationResultR(PARAMETER, PARAMETER_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (parameter.type() == null) {
                results.add(new VerificationResultR(PARAMETER, PARAMETER_TYPE_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkAnnotation(Annotation annotation) {
        if (annotation != null) {
            super.checkAnnotation(annotation);
            if (isEmpty(annotation.name())) {
                results.add(new VerificationResultR(ANNOTATION, ANNOTATION_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkDrillThroughAttribute(DrillThroughAttribute drillThroughAttribute) {

        super.checkDrillThroughAttribute(drillThroughAttribute);
        if (drillThroughAttribute != null) {
            if (isEmpty(drillThroughAttribute.dimension())) {
                results.add(new VerificationResultR(DRILL_THROUGH_ATTRIBUTE, DRILL_THROUGH_ATTRIBUTE_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkDrillThroughMeasure(DrillThroughMeasure drillThroughMeasure) {
        super.checkDrillThroughMeasure(drillThroughMeasure);
        if (drillThroughMeasure != null ) {
            if (isEmpty(drillThroughMeasure.name())) {
                results.add(new VerificationResultR(DRILL_THROUGH_MEASURE, DRILL_THROUGH_MEASURE_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkAction(Action action) {
        super.checkAction(action);
        if (action != null ) {
            if (isEmpty(action.name())) {
                results.add(new VerificationResultR(ACTION, ACTION_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkWritebackAttribute(WritebackAttribute writebackAttribute) {
        super.checkWritebackAttribute(writebackAttribute);
        if (writebackAttribute != null ) {
            if (isEmpty(writebackAttribute.dimension())) {
                results.add(new VerificationResultR(WRITEBACK_ATTRIBUTE, WRITEBACK_ATTRIBUTE_DIMENSION_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (isEmpty(writebackAttribute.column())) {
                results.add(new VerificationResultR(WRITEBACK_ATTRIBUTE, WRITEBACK_ATTRIBUTE_COLUMN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkWritebackMeasure(WritebackMeasure writebackMeasure) {
        super.checkWritebackMeasure(writebackMeasure);
        if (writebackMeasure != null ) {
            if (isEmpty(writebackMeasure.name())) {
                results.add(new VerificationResultR(WRITEBACK_MEASURE, WRITEBACK_MEASURE_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (isEmpty(writebackMeasure.column())) {
                results.add(new VerificationResultR(WRITEBACK_MEASURE, WRITEBACK_MEASURE_COLUMN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkWritebackTable(WritebackTable writebackTable) {
        super.checkWritebackTable(writebackTable);
        if (writebackTable != null ) {
            if (isEmpty(writebackTable.name())) {
                results.add(new VerificationResultR(WRITEBACK_TABLE, WRITEBACK_TABLE_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }
}
