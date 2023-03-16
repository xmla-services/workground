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
package org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic;

import org.eclipse.daanse.olap.rolap.dbmapper.api.Action;
import org.eclipse.daanse.olap.rolap.dbmapper.api.AggColumnName;
import org.eclipse.daanse.olap.rolap.dbmapper.api.AggExclude;
import org.eclipse.daanse.olap.rolap.dbmapper.api.AggForeignKey;
import org.eclipse.daanse.olap.rolap.dbmapper.api.AggLevel;
import org.eclipse.daanse.olap.rolap.dbmapper.api.AggLevelProperty;
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
import org.eclipse.daanse.olap.rolap.dbmapper.api.DrillThroughAction;
import org.eclipse.daanse.olap.rolap.dbmapper.api.DrillThroughAttribute;
import org.eclipse.daanse.olap.rolap.dbmapper.api.DrillThroughElement;
import org.eclipse.daanse.olap.rolap.dbmapper.api.DrillThroughMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.api.ElementFormatter;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Expression;
import org.eclipse.daanse.olap.rolap.dbmapper.api.ExpressionView;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Formula;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Hierarchy;
import org.eclipse.daanse.olap.rolap.dbmapper.api.HierarchyGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Hint;
import org.eclipse.daanse.olap.rolap.dbmapper.api.InlineTable;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Join;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Measure;
import org.eclipse.daanse.olap.rolap.dbmapper.api.MemberGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.api.MemberReaderParameter;
import org.eclipse.daanse.olap.rolap.dbmapper.api.NamedSet;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Parameter;
import org.eclipse.daanse.olap.rolap.dbmapper.api.PrivateDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Property;
import org.eclipse.daanse.olap.rolap.dbmapper.api.RelationOrJoin;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Role;
import org.eclipse.daanse.olap.rolap.dbmapper.api.RoleUsage;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Row;
import org.eclipse.daanse.olap.rolap.dbmapper.api.SQL;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.api.SchemaGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Table;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Union;
import org.eclipse.daanse.olap.rolap.dbmapper.api.UserDefinedFunction;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Value;
import org.eclipse.daanse.olap.rolap.dbmapper.api.View;
import org.eclipse.daanse.olap.rolap.dbmapper.api.VirtualCube;
import org.eclipse.daanse.olap.rolap.dbmapper.api.VirtualCubeMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.api.WritebackAttribute;
import org.eclipse.daanse.olap.rolap.dbmapper.api.WritebackColumn;
import org.eclipse.daanse.olap.rolap.dbmapper.api.WritebackMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.api.WritebackTable;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.VerificationResult;

import java.util.ArrayList;
import java.util.List;

import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.NOT_SET;

public abstract class AbstractSchemaWalker {

    protected List<VerificationResult> results = new ArrayList<>();

    public List<VerificationResult> checkSchema(Schema schema) {

        checkAnnotationList(schema.annotations());
        checkParameterList(schema.parameter());
        checkCubeDimensionList(schema.dimension(), null);
        checkCubeList(schema.cube());
        checkVirtualCubeList(schema.virtualCube());
        checkNamedSetList(schema.namedSet());
        checkRoleList(schema.roles());
        checkUserDefinedFunctionList(schema.userDefinedFunctions());

        return results;
    }

    protected void checkCube(Cube cube) {
        checkAnnotationList(cube.annotations());
        checkCubeDimensionList(cube.dimensionUsageOrDimension(), cube);
        checkMeasureList(cube.measure(), cube);
        checkCalculatedMemberList(cube.calculatedMember());
        checkNamedSetList(cube.namedSet());
        checkDrillThroughActionList(cube.drillThroughAction());
        checkWritebackTableList(cube.writebackTable());
        if (cube.action() != null) {
            cube.action().forEach(this::checkAction);
        }
    }

    protected void checkAction(Action action) {
        checkAnnotationList(action.annotations());
    }

    protected void checkDrillThroughAction(DrillThroughAction drillThroughAction) {
        checkAnnotationList(drillThroughAction.annotations());
        checkDrillThroughElementList(drillThroughAction.drillThroughElement());
    }

    protected void checkDrillThroughElement(DrillThroughElement drillThroughElement) {
        if (drillThroughElement instanceof DrillThroughMeasure) {
            checkDrillThroughMeasure((DrillThroughMeasure) drillThroughElement);
        }
        if (drillThroughElement instanceof DrillThroughAttribute) {
            checkDrillThroughAttribute((DrillThroughAttribute) drillThroughElement);
        }
    }

    protected void checkDrillThroughAttribute(DrillThroughAttribute drillThroughElement) {
        //empty
    }

    protected void checkDrillThroughMeasure(DrillThroughMeasure drillThroughElement) {
        //empty
    }

    protected void checkMeasure(Measure measure, Cube cube) {
        checkAnnotationList(measure.annotations());
        checkCalculatedMemberPropertyList(measure.calculatedMemberProperty());
        checkExpressionView(measure.measureExpression());
        checkElementFormatter(measure.cellFormatter());
    }

    protected void checkCalculatedMemberProperty(CalculatedMemberProperty calculatedMemberProperty) {
        //empty
    }

    protected void checkExpressionView(ExpressionView measureExpression) {
        checkSQLList(measureExpression.sql());
    }

    protected void checkSQL(SQL sql) {
        //empty
    }

    protected void checkElementFormatter(ElementFormatter elementFormatter) {
        //empty
    }

    protected void checkCubeDimension(CubeDimension cubeDimension, Cube cube) {
        checkAnnotationList(cubeDimension.annotations());
        if (cubeDimension instanceof PrivateDimension) {
            if (((PrivateDimension) cubeDimension).hierarchy() != null) {
                ((PrivateDimension) cubeDimension).hierarchy()
                    .forEach(h -> checkHierarchy(h, (PrivateDimension) cubeDimension, cube));
            }
        }
    }

    protected void checkHierarchy(Hierarchy hierarchy, PrivateDimension cubeDimension, Cube cube) {
        checkAnnotationList(hierarchy.annotations());
        checkMemberReaderParameterList(hierarchy.memberReaderParameter());

        //Level
        if (hierarchy.level() != null) {
            hierarchy.level().forEach(l -> checkLevel(l, hierarchy, cubeDimension, cube));
        }

    }

    protected void checkMemberReaderParameter(MemberReaderParameter memberReaderParameter) {
        //empty
    }

    protected void checkJoin(Join join) {
        checkRelationOrJoinList(join.relation());
    }

    protected void checkRelationOrJoin(RelationOrJoin relationOrJoin) {

        if (relationOrJoin instanceof InlineTable) {
            checkInlineTable((InlineTable) relationOrJoin);
        }
        if (relationOrJoin instanceof Join) {
            checkJoin((Join) relationOrJoin);
        }
        if (relationOrJoin instanceof Table) {
            checkTable((Table) relationOrJoin);
        }
        if (relationOrJoin instanceof View) {
            checkView((View) relationOrJoin);
        }
    }

    protected void checkView(View relationOrJoin) {
        checkSQLList(relationOrJoin.sqls());
    }

    protected void checkInlineTable(InlineTable relationOrJoin) {
        checkColumnDefList(relationOrJoin.columnDefs());
        checkRowList(relationOrJoin.rows());
    }

    protected void checkRow(Row row) {
        checkValueList(row.values());
    }

    protected void checkValue(Value value) {
        //empty
    }

    protected void checkColumnDef(ColumnDef columnDef) {
        //empty
    }

    protected void checkTable(Table table) {
        checkSQL(table.sql());

        checkAggExcludeList(table.aggExclude());

        checkAggTableList(table.aggTable());

        checkHintList(table.hint());
    }

    protected void checkHint(Hint hint) {
        //empty
    }

    protected void checkAggTable(AggTable aggTable) {
        checkAggColumnName(aggTable.aggFactCount());
        checkAggColumnNameList(aggTable.aggIgnoreColumn());
        checkAggForeignKeyList(aggTable.aggForeignKey());
        checkAggMeasureList(aggTable.aggMeasure());
        checkAggLevelList(aggTable.aggLevel());
        checkAggMeasureFactCountList(aggTable.measuresFactCount());
        if (aggTable instanceof AggName){
            checkAggName((AggName) aggTable);
        }
        if (aggTable instanceof AggPattern) {
            checkAggPattern((AggPattern) aggTable);
        }
    }

    protected void checkAggPattern(AggPattern aggTable) {
        checkAggExcludeList(aggTable.aggExclude());
    }

    protected void checkAggName(AggName aggTable) {
        //empty
    }

    protected void checkAggMeasureFactCount(AggMeasureFactCount aggMeasureFactCount) {
        //empty
    }

    protected void checkAggLevel(AggLevel aggLevel) {
        checkAggLevelPropertyList(aggLevel.properties());
    }

    protected void checkAggLevelProperty(AggLevelProperty aggLevelProperty) {
        //empty
    }

    protected void checkAggMeasure(AggMeasure aggMeasure) {
        //empty
    }

    protected void checkAggForeignKey(AggForeignKey aggForeignKey) {
        //empty
    }

    protected void checkAggColumnName(AggColumnName aggFactCount) {
        if (aggFactCount instanceof AggMeasureFactCount) {
            checkAggMeasureFactCount((AggMeasureFactCount) aggFactCount);
        }
    }

    protected void checkAggExclude(AggExclude aggExclude) {
        //empty
    }

    protected void checkLevel(
        org.eclipse.daanse.olap.rolap.dbmapper.api.Level level, Hierarchy hierarchy,
        PrivateDimension parentDimension, Cube cube
    ) {

        checkAnnotationList(level.annotations());

        checkExpression(level.keyExpression());

        checkExpression(level.nameExpression());

        checkExpression(level.captionExpression());

        checkExpression(level.ordinalExpression());

        checkExpression(level.parentExpression());

        checkClosure(level.closure());

        checkPropertyList(level.property(), level, hierarchy,
            cube);

        checkElementFormatter(level.memberFormatter());
    }

    protected void checkClosure(Closure closure) {
        checkTable(closure.table());
    }

    protected void checkExpression(Expression keyExpression) {
        if (keyExpression instanceof Column) {
            checkColumn((Column) keyExpression);
        }
        if (keyExpression instanceof ExpressionView) {
            checkExpressionView((ExpressionView) keyExpression);
        }
    }

    protected void checkColumn(Column keyExpression) {
        //empty
    }

    protected void checkProperty(
        Property property, org.eclipse.daanse.olap.rolap.dbmapper.api.Level level,
        Hierarchy hierarchy, Cube cube
    ) {
        //ElementFormatter
        if (property.propertyFormatter() != null) {
            checkElementFormatter(property.propertyFormatter());
        }

    }

    protected void checkVirtualCube(VirtualCube virtCube) {

        checkAnnotationList(virtCube.annotations());

        checkCubeUsageList(virtCube.cubeUsages());

        checkCubeDimensionList(virtCube.virtualCubeDimension(), null);

        checkVirtualCubeMeasureList(virtCube.virtualCubeMeasure());

        checkNamedSetList(virtCube.namedSet());

        //CalculatedMember
        if (virtCube.calculatedMember() != null) {
            virtCube.calculatedMember()
                .forEach(cm -> checkCalculatedMember(cm));
        }

    }

    protected void checkCubeUsage(CubeUsage cubeUsage) {
        //empty
    }

    protected void checkVirtualCubeMeasure(VirtualCubeMeasure virtualCubeMeasure) {
        checkAnnotationList(virtualCubeMeasure.annotations());
    }

    protected void checkCalculatedMember(CalculatedMember calculatedMember) {
        checkAnnotationList(calculatedMember.annotations());
        checkCalculatedMemberPropertyList(calculatedMember.calculatedMemberProperty());

        if (calculatedMember.formulaElement() != null) {
            checkFormula(calculatedMember.formulaElement());
        }

        if (calculatedMember.cellFormatter() != null) {
            checkElementFormatter(calculatedMember.cellFormatter());
        }
    }

    protected void checkFormula(Formula formula) {
        //empty
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
    protected void checkColumn(
        String column, String fieldName, org.eclipse.daanse.olap.rolap.dbmapper.api.Level level,
        Cube cube, Hierarchy parentHierarchy
    ) {
        //empty
    }

    protected void checkNamedSet(NamedSet namedSet) {
        checkAnnotationList(namedSet.annotations());

        if (namedSet.formulaElement() != null) {
            checkFormula(namedSet.formulaElement());
        }
    }

    protected void checkUserDefinedFunction(UserDefinedFunction udf) {
        //empty
    }

    protected void checkParameter(Parameter parameter) {
        //empty
    }

    protected void checkAnnotation(Annotation annotation) {
        //empty
    }

    protected void checkRole(Role role) {
        checkAnnotationList(role.annotations());
        checkSchemaGrantList(role.schemaGrant());
        checkUnion(role.union());
    }

    protected void checkUnion(Union union){
        checkRoleUsageList(union.roleUsage());
    }

    private void checkRoleUsageList(List<? extends RoleUsage> list) {
        if (list != null) {
            list.forEach(this::checkRoleUsage);
        }
    }

    protected void checkRoleUsage(RoleUsage roleUsage) {
        //empty
    }

    protected void checkSchemaGrant(SchemaGrant schemaGrant) {
        checkCubeGrantList(schemaGrant.cubeGrant());
    }

    protected void checkCubeGrant(CubeGrant cubeGrant) {
        checkDimensionGrantList(cubeGrant.dimensionGrant());
        checkHierarchyGrantList(cubeGrant.hierarchyGrant());
    }

    protected void checkHierarchyGrant(HierarchyGrant hierarchyGrant) {
        checkMemberGrantList(hierarchyGrant.memberGrant());
    }

    protected void checkMemberGrant(MemberGrant memberGrant) {
        //empty
    }

    protected void checkDimensionGrant(DimensionGrant dimensionGrant) {
        //empty
    }

    protected static boolean isEmpty(String v) {
        return (v == null) || v.equals("");
    }

    protected void checkNamedSetList(List<? extends NamedSet> namedSet) {
        if (namedSet != null) {
            namedSet.forEach(ns -> checkNamedSet(ns));

        }
    }

    protected void checkWritebackTable(WritebackTable writebackTable) {
        if (writebackTable.columns() != null) {
            writebackTable.columns().forEach(this::checkWritebackColumn);
        }
    }

    protected void checkWritebackColumn(WritebackColumn writebackColumn) {
        if (writebackColumn instanceof WritebackAttribute) {
            checkWritebackAttribute((WritebackAttribute) writebackColumn);
        }
        if (writebackColumn instanceof WritebackMeasure) {
            checkWritebackMeasure((WritebackMeasure) writebackColumn);
        }
    }

    protected void checkWritebackMeasure(WritebackMeasure writebackColumn) {
        //empty
    }

    protected void checkWritebackAttribute(WritebackAttribute writebackColumn) {
        //empty
    }

    protected boolean isSchemaRequired() {
        return true;
    }

    protected String orNotSet(String value) {
        return value == null ? NOT_SET : value;
    }

    private void checkHintList(List<? extends Hint> list) {
        if (list != null) {
            list.forEach(this::checkHint);
        }
    }

    private void checkCalculatedMemberPropertyList(List<? extends CalculatedMemberProperty> list) {
        if (list != null) {
            list.forEach(this::checkCalculatedMemberProperty);
        }
    }

    private void checkAggTableList(List<? extends AggTable> list) {
        if (list != null) {
            list.forEach(this::checkAggTable);
        }
    }

    private void checkDrillThroughElementList(List<? extends DrillThroughElement> list) {
        if (list != null) {
            list.forEach(this::checkDrillThroughElement);
        }
    }

    private void checkSQLList(List<? extends SQL> list) {
        if (list != null) {
            list.forEach(this::checkSQL);
        }
    }

    private void checkMemberReaderParameterList(List<? extends MemberReaderParameter> list) {
        if (list != null) {
            list.forEach(this::checkMemberReaderParameter);
        }
    }

    private void checkRelationOrJoinList(List<RelationOrJoin> list) {
        if (list != null) {
            list.forEach(this::checkRelationOrJoin);
        }
    }

    private void checkRowList(List<? extends Row> list) {
        if (list != null) {
            list.forEach(this::checkRow);
        }
    }

    private void checkValueList(List<? extends Value> list) {
        if (list != null) {
            list.forEach(this::checkValue);
        }
    }

    private void checkColumnDefList(List<? extends ColumnDef> list) {
        if (list != null) {
            list.forEach(this::checkColumnDef);
        }
    }

    private void checkAggMeasureFactCountList(List<? extends AggMeasureFactCount> list) {
        if (list != null) {
            list.forEach(this::checkAggMeasureFactCount);
        }
    }

    private void checkAggLevelList(List<? extends AggLevel> list) {
        if (list != null) {
            list.forEach(this::checkAggLevel);
        }
    }

    private void checkAggLevelPropertyList(List<AggLevelProperty> list) {
        if (list != null) {
            list.forEach(this::checkAggLevelProperty);
        }
    }

    private void checkAggMeasureList(List<? extends AggMeasure> list) {
        if (list != null) {
            list.forEach(this::checkAggMeasure);
        }
    }

    private void checkAggForeignKeyList(List<? extends AggForeignKey> list) {
        if (list != null) {
            list.forEach(this::checkAggForeignKey);
        }
    }

    private void checkAggColumnNameList(List<? extends AggColumnName> list) {
        if (list != null) {
            list.forEach(this::checkAggColumnName);
        }
    }

    private void checkAggExcludeList(List<? extends AggExclude> list) {
        if (list != null) {
            list.forEach(this::checkAggExclude);
        }
    }

    private void checkPropertyList(
        List<? extends Property> list,
        org.eclipse.daanse.olap.rolap.dbmapper.api.Level level,
        Hierarchy hierarchy, Cube cube
    ) {
        if (list != null) {
            list.forEach(it -> checkProperty(it, level,
                hierarchy, cube));
        }
    }

    private void checkCubeUsageList(List<? extends CubeUsage> list) {
        if (list != null) {
            list.forEach(this::checkCubeUsage);
        }
    }

    private void checkVirtualCubeMeasureList(List<? extends VirtualCubeMeasure> list) {
        if (list != null) {
            list.forEach(this::checkVirtualCubeMeasure);
        }
    }

    private void checkCubeGrantList(List<? extends CubeGrant> list) {
        if (list != null) {
            list.forEach(this::checkCubeGrant);
        }
    }

    private void checkHierarchyGrantList(List<? extends HierarchyGrant> list) {
        if (list != null) {
            list.forEach(this::checkHierarchyGrant);
        }
    }

    private void checkMemberGrantList(List<? extends MemberGrant> list) {
        if (list != null) {
            list.forEach(this::checkMemberGrant);
        }
    }

    private void checkDimensionGrantList(List<? extends DimensionGrant> list) {
        if (list != null) {
            list.forEach(this::checkDimensionGrant);
        }
    }

    private void checkSchemaGrantList(List<? extends SchemaGrant> list) {
        if (list != null) {
            list.forEach(this::checkSchemaGrant);
        }
    }

    private void checkCubeDimensionList(List<? extends CubeDimension> list, Cube cube) {
        if (list != null) {
            list.forEach(it -> checkCubeDimension(it, cube));
        }
    }

    private void checkVirtualCubeList(List<? extends VirtualCube> list) {
        if (list != null) {
            list.forEach(this::checkVirtualCube);
        }
    }

    private void checkUserDefinedFunctionList(List<? extends UserDefinedFunction> list) {
        if (list != null) {
            list.forEach(this::checkUserDefinedFunction);
        }
    }

    private void checkCubeList(List<? extends Cube> list) {
        if (list != null) {
            list.forEach(this::checkCube);
        }
    }

    private void checkAnnotationList(List<? extends Annotation> list) {
        if (list != null) {
            list.forEach(this::checkAnnotation);
        }
    }

    private void checkParameterList(List<? extends Parameter> list) {
        if (list != null) {
            list.forEach(this::checkParameter);
        }
    }

    private void checkCalculatedMemberList(List<? extends CalculatedMember> list) {
        if (list != null) {
            list.forEach(cm -> checkCalculatedMember(cm));
        }
    }

    private void checkMeasureList(List<? extends Measure> list, Cube cube) {
        if (list != null) {
            list.forEach(m -> checkMeasure(m, cube));
        }
    }

    private void checkRoleList(List<? extends Role> list) {
        if (list != null) {
            list.forEach(this::checkRole);
        }
    }

    private void checkWritebackTableList(List<? extends WritebackTable> list) {
        if (list != null) {
            list.forEach(this::checkWritebackTable);
        }
    }

    private void checkDrillThroughActionList(List<? extends DrillThroughAction> list) {
        if (list != null) {
            list.forEach(this::checkDrillThroughAction);
        }
    }
}
