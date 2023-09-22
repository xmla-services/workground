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

import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.NOT_SET;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAction;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggColumnName;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggExclude;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggForeignKey;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggLevel;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggLevelProperty;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggMeasureFactCount;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggName;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggPattern;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggTable;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAnnotation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCalculatedMember;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCalculatedMemberProperty;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingClosure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingColumn;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingColumnDef;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCubeGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCubeUsage;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingDimensionGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingDrillThroughAction;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingDrillThroughAttribute;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingDrillThroughElement;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingDrillThroughMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingElementFormatter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingExpression;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingExpressionView;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingFormula;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingHierarchy;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingHierarchyGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingHint;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingInlineTable;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingJoin;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Measure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MemberGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MemberReaderParameter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.NamedSet;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Parameter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.PrivateDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Property;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.RelationOrJoin;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Role;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.RoleUsage;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Row;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.SQL;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.SchemaGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.SharedDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Table;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Union;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.UserDefinedFunction;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Value;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.View;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.VirtualCube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.VirtualCubeMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.WritebackAttribute;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.WritebackColumn;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.WritebackMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.WritebackTable;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.VerificationResult;

public abstract class AbstractSchemaWalker {

    protected List<VerificationResult> results = new ArrayList<>();

    public List<VerificationResult> checkSchema(Schema schema) {

        if (schema != null) {
            checkAnnotationList(schema.annotations());
            checkParameterList(schema.parameters());
            checkCubeDimensionList(schema.dimensions(), null);
            checkCubeList(schema.cubes());
            checkVirtualCubeList(schema.virtualCubes());
            checkNamedSetList(schema.namedSets());
            checkRoleList(schema.roles());
            checkUserDefinedFunctionList(schema.userDefinedFunctions());
        }

        return results;
    }

    protected void checkCube(MappingCube cube) {
        if (cube != null) {
            checkAnnotationList(cube.annotations());
            checkCubeDimensionList(cube.dimensionUsageOrDimensions(), cube);
            checkMeasureList(cube.measures(), cube);
            checkCalculatedMemberList(cube.calculatedMembers());
            checkNamedSetList(cube.namedSets());
            checkDrillThroughActionList(cube.drillThroughActions());
            checkWritebackTableList(cube.writebackTables());
            if (cube.actions() != null) {
                cube.actions().forEach(this::checkAction);
            }
        }
    }

    protected void checkAction(MappingAction action) {
        if (action != null) {
            checkAnnotationList(action.annotations());
        }
    }

    protected void checkDrillThroughAction(MappingDrillThroughAction drillThroughAction) {
        if (drillThroughAction != null) {
            checkAnnotationList(drillThroughAction.annotations());
            checkDrillThroughElementList(drillThroughAction.drillThroughElements());
        }
    }

    protected void checkDrillThroughElement(MappingDrillThroughElement drillThroughElement) {
        if (drillThroughElement != null) {
            if (drillThroughElement instanceof MappingDrillThroughMeasure drillThroughMeasure) {
                checkDrillThroughMeasure(drillThroughMeasure);
            }
            if (drillThroughElement instanceof MappingDrillThroughAttribute drillThroughAttribute) {
                checkDrillThroughAttribute(drillThroughAttribute);
            }
        }
    }

    protected void checkDrillThroughAttribute(MappingDrillThroughAttribute drillThroughElement) {
        //empty
    }

    protected void checkDrillThroughMeasure(MappingDrillThroughMeasure drillThroughElement) {
        //empty
    }

    @SuppressWarnings("java:S1172")
    protected void checkMeasure(Measure measure, MappingCube cube) {
        if (measure != null) {
            checkAnnotationList(measure.annotations());
            checkCalculatedMemberPropertyList(measure.calculatedMemberProperties());
            checkExpressionView(measure.measureExpression());
            checkElementFormatter(measure.cellFormatter());
        }
    }

    protected void checkCalculatedMemberProperty(MappingCalculatedMemberProperty calculatedMemberProperty) {
        //empty
    }

    protected void checkExpressionView(MappingExpressionView measureExpression) {
        if (measureExpression != null) {
            checkSQLList(measureExpression.sqls());
        }
    }

    protected void checkSQL(SQL sql) {
        //empty
    }

    protected void checkElementFormatter(MappingElementFormatter elementFormatter) {
        //empty
    }

    protected void checkCubeDimension(MappingCubeDimension cubeDimension, MappingCube cube) {
        if (cubeDimension != null) {
            checkAnnotationList(cubeDimension.annotations());
            if (cubeDimension instanceof PrivateDimension privateDimension && privateDimension.hierarchies() != null) {
                privateDimension.hierarchies()
                    .forEach(h -> checkHierarchy(h, (PrivateDimension) cubeDimension, cube));
            }
        }
    }

    protected void checkHierarchy(MappingHierarchy hierarchy, PrivateDimension cubeDimension, MappingCube cube) {
        if (hierarchy != null) {
            checkAnnotationList(hierarchy.annotations());
            checkMemberReaderParameterList(hierarchy.memberReaderParameters());

            //Level
            if (hierarchy.levels() != null) {
                hierarchy.levels().forEach(l -> checkLevel(l, hierarchy, cubeDimension, cube));
            }
        }
    }

    protected void checkMemberReaderParameter(MemberReaderParameter memberReaderParameter) {
        //empty
    }

    protected void checkJoin(MappingJoin join) {
        if (join != null) {
            checkRelationOrJoinList(join.relations());
        }
    }

    protected void checkRelationOrJoin(RelationOrJoin relationOrJoin) {
        if (relationOrJoin != null) {
            if (relationOrJoin instanceof MappingInlineTable inlineTable) {
                checkInlineTable(inlineTable);
            }
            if (relationOrJoin instanceof MappingJoin join) {
                checkJoin(join);
            }
            if (relationOrJoin instanceof Table table) {
                checkTable(table);
            }
            if (relationOrJoin instanceof View view) {
                checkView(view);
            }
        }
    }

    protected void checkView(View relationOrJoin) {
        if (relationOrJoin != null) {
            checkSQLList(relationOrJoin.sqls());
        }
    }

    protected void checkInlineTable(MappingInlineTable relationOrJoin) {
        if (relationOrJoin != null) {
            checkColumnDefList(relationOrJoin.columnDefs());
            checkRowList(relationOrJoin.rows());
        }
    }

    protected void checkRow(Row row) {
        if (row != null) {
            checkValueList(row.values());
        }
    }

    protected void checkValue(Value value) {
        //empty
    }

    protected void checkColumnDef(MappingColumnDef columnDef) {
        //empty
    }

    protected void checkTable(Table table) {
        if (table != null) {
            checkSQL(table.sql());

            checkAggExcludeList(table.aggExcludes());

            checkAggTableList(table.aggTables());

            checkHintList(table.hints());
        }
    }

    protected void checkHint(MappingHint hint) {
        //empty
    }

    protected void checkAggTable(MappingAggTable aggTable) {
        if (aggTable != null) {
            checkAggColumnName(aggTable.aggFactCount());
            checkAggColumnNameList(aggTable.aggIgnoreColumns());
            checkAggForeignKeyList(aggTable.aggForeignKeys());
            checkAggMeasureList(aggTable.aggMeasures());
            checkAggLevelList(aggTable.aggLevels());
            checkAggMeasureFactCountList(aggTable.measuresFactCounts());
            if (aggTable instanceof MappingAggName aggName) {
                checkAggName(aggName);
            }
            if (aggTable instanceof MappingAggPattern aggPattern) {
                checkAggPattern(aggPattern);
            }
        }
    }

    protected void checkAggPattern(MappingAggPattern aggTable) {
        if (aggTable != null) {
            checkAggExcludeList(aggTable.aggExcludes());
        }
    }

    protected void checkAggName(MappingAggName aggTable) {
        //empty
    }

    protected void checkAggMeasureFactCount(MappingAggMeasureFactCount aggMeasureFactCount) {
        //empty
    }

    protected void checkAggLevel(MappingAggLevel aggLevel) {
        if (aggLevel != null) {
            checkAggLevelPropertyList(aggLevel.properties());
        }
    }

    protected void checkAggLevelProperty(MappingAggLevelProperty aggLevelProperty) {
        //empty
    }

    protected void checkAggMeasure(MappingAggMeasure aggMeasure) {
        //empty
    }

    protected void checkAggForeignKey(MappingAggForeignKey aggForeignKey) {
        //empty
    }

    protected void checkAggColumnName(MappingAggColumnName aggFactCount) {
        if (aggFactCount != null && aggFactCount instanceof MappingAggMeasureFactCount aggMeasureFactCount) {
            checkAggMeasureFactCount(aggMeasureFactCount);
        }
    }

    protected void checkAggExclude(MappingAggExclude aggExclude) {
        //empty
    }

    @SuppressWarnings("java:S1172")
    protected void checkLevel(
        org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingLevel level, MappingHierarchy hierarchy,
        PrivateDimension parentDimension, MappingCube cube
    ) {
        if (level != null) {
            checkAnnotationList(level.annotations());

            checkExpression(level.keyExpression());

            checkExpression(level.nameExpression());

            checkExpression(level.captionExpression());

            checkExpression(level.ordinalExpression());

            checkExpression(level.parentExpression());

            checkClosure(level.closure());

            checkPropertyList(level.properties(), level, hierarchy,
                cube);

            checkElementFormatter(level.memberFormatter());
        }
    }

    protected void checkClosure(MappingClosure closure) {
        if (closure != null) {
            checkTable(closure.table());
        }
    }

    protected void checkExpression(MappingExpression keyExpression) {
        if (keyExpression != null) {
            if (keyExpression instanceof MappingColumn column) {
                checkColumn(column);
            }
            if (keyExpression instanceof MappingExpressionView expressionView) {
                checkExpressionView(expressionView);
            }
        }
    }

    protected void checkColumn(MappingColumn keyExpression) {
        //empty
    }

    @SuppressWarnings("java:S1172")
    protected void checkProperty(
        Property property, org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingLevel level,
        MappingHierarchy hierarchy, MappingCube cube
    ) {
        if (property != null) {
            //ElementFormatter
            checkElementFormatter(property.propertyFormatter());
        }
    }

    protected void checkVirtualCube(VirtualCube virtCube) {
        if (virtCube != null) {
            checkAnnotationList(virtCube.annotations());

            checkCubeUsageList(virtCube.cubeUsages());

            checkCubeDimensionList(virtCube.virtualCubeDimensions(), null);

            checkVirtualCubeMeasureList(virtCube.virtualCubeMeasures());

            checkNamedSetList(virtCube.namedSets());

            //CalculatedMember
            if (virtCube.calculatedMembers() != null) {
                virtCube.calculatedMembers()
                    .forEach(this::checkCalculatedMember);
            }
        }
    }

    protected void checkCubeUsage(MappingCubeUsage cubeUsage) {
        //empty
    }

    protected void checkVirtualCubeMeasure(VirtualCubeMeasure virtualCubeMeasure) {
        if (virtualCubeMeasure != null) {
            checkAnnotationList(virtualCubeMeasure.annotations());
        }
    }

    protected void checkCalculatedMember(MappingCalculatedMember calculatedMember) {
        if (calculatedMember != null) {
            checkAnnotationList(calculatedMember.annotations());
            checkCalculatedMemberPropertyList(calculatedMember.calculatedMemberProperties());

            if (calculatedMember.formulaElement() != null) {
                checkFormula(calculatedMember.formulaElement());
            }

            if (calculatedMember.cellFormatter() != null) {
                checkElementFormatter(calculatedMember.cellFormatter());
            }
        }
    }

    protected void checkFormula(MappingFormula formula) {
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
        String column, String fieldName, org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingLevel level,
        MappingCube cube, MappingHierarchy parentHierarchy
    ) {
        //empty
    }

    protected void checkNamedSet(NamedSet namedSet) {
        if (namedSet != null) {
            checkAnnotationList(namedSet.annotations());

            if (namedSet.formulaElement() != null) {
                checkFormula(namedSet.formulaElement());
            }
        }
    }

    /**
     * @return @deprecated
     */
    @Deprecated(since="new version", forRemoval=true)
    protected void checkUserDefinedFunction(UserDefinedFunction udf) {
        //empty
    }

    protected void checkParameter(Parameter parameter) {
        //empty
    }

    protected void checkAnnotation(MappingAnnotation annotation) {
        //empty
    }

    protected void checkRole(Role role) {
        if (role != null) {
            checkAnnotationList(role.annotations());
            checkSchemaGrantList(role.schemaGrants());
            checkUnion(role.union());
        }
    }

    protected void checkUnion(Union union){
        if (union != null) {
            checkRoleUsageList(union.roleUsages());
        }
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
        if (schemaGrant != null) {
            checkCubeGrantList(schemaGrant.cubeGrants());
        }
    }

    protected void checkCubeGrant(MappingCubeGrant cubeGrant) {
        if (cubeGrant != null) {
            checkDimensionGrantList(cubeGrant.dimensionGrants());
            checkHierarchyGrantList(cubeGrant.hierarchyGrants());
        }
    }

    protected void checkHierarchyGrant(MappingHierarchyGrant hierarchyGrant) {
        if (hierarchyGrant != null) {
            checkMemberGrantList(hierarchyGrant.memberGrants());
        }
    }

    protected void checkMemberGrant(MemberGrant memberGrant) {
        //empty
    }

    protected void checkDimensionGrant(MappingDimensionGrant dimensionGrant) {
        //empty
    }

    protected static boolean isEmpty(String v) {
        return (v == null) || v.equals("");
    }

    protected void checkNamedSetList(List<? extends NamedSet> namedSet) {
        if (namedSet != null) {
            namedSet.forEach(this::checkNamedSet);

        }
    }

    protected void checkWritebackTable(WritebackTable writebackTable) {
        if (writebackTable != null && writebackTable.columns() != null) {
            writebackTable.columns().forEach(this::checkWritebackColumn);
        }
    }

    protected void checkWritebackColumn(WritebackColumn writebackColumn) {
        if (writebackColumn != null) {
            if (writebackColumn instanceof WritebackAttribute writebackAttribute) {
                checkWritebackAttribute(writebackAttribute);
            }
            if (writebackColumn instanceof WritebackMeasure writebackMeasure) {
                checkWritebackMeasure(writebackMeasure);
            }
        }
    }

    protected void checkWritebackMeasure(WritebackMeasure writebackColumn) {
        //empty
    }

    protected void checkWritebackAttribute(WritebackAttribute writebackColumn) {
        //empty
    }

    protected void checkSharedDimension(final SharedDimension sharedDimension) {
        //empty
    }

    protected boolean isSchemaRequired() {
        return true;
    }

    protected String orNotSet(String value) {
        return value == null ? NOT_SET : value;
    }

    private void checkHintList(List<? extends MappingHint> list) {
        if (list != null) {
            list.forEach(this::checkHint);
        }
    }

    private void checkCalculatedMemberPropertyList(List<? extends MappingCalculatedMemberProperty> list) {
        if (list != null) {
            list.forEach(this::checkCalculatedMemberProperty);
        }
    }

    private void checkAggTableList(List<? extends MappingAggTable> list) {
        if (list != null) {
            list.forEach(this::checkAggTable);
        }
    }

    private void checkDrillThroughElementList(List<? extends MappingDrillThroughElement> list) {
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

    private void checkColumnDefList(List<? extends MappingColumnDef> list) {
        if (list != null) {
            list.forEach(this::checkColumnDef);
        }
    }

    private void checkAggMeasureFactCountList(List<? extends MappingAggMeasureFactCount> list) {
        if (list != null) {
            list.forEach(this::checkAggMeasureFactCount);
        }
    }

    private void checkAggLevelList(List<? extends MappingAggLevel> list) {
        if (list != null) {
            list.forEach(this::checkAggLevel);
        }
    }

    private void checkAggLevelPropertyList(List<MappingAggLevelProperty> list) {
        if (list != null) {
            list.forEach(this::checkAggLevelProperty);
        }
    }

    private void checkAggMeasureList(List<? extends MappingAggMeasure> list) {
        if (list != null) {
            list.forEach(this::checkAggMeasure);
        }
    }

    private void checkAggForeignKeyList(List<? extends MappingAggForeignKey> list) {
        if (list != null) {
            list.forEach(this::checkAggForeignKey);
        }
    }

    private void checkAggColumnNameList(List<? extends MappingAggColumnName> list) {
        if (list != null) {
            list.forEach(this::checkAggColumnName);
        }
    }

    private void checkAggExcludeList(List<? extends MappingAggExclude> list) {
        if (list != null) {
            list.forEach(this::checkAggExclude);
        }
    }

    private void checkPropertyList(
        List<? extends Property> list,
        org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingLevel level,
        MappingHierarchy hierarchy, MappingCube cube
    ) {
        if (list != null) {
            list.forEach(it -> checkProperty(it, level,
                hierarchy, cube));
        }
    }

    private void checkCubeUsageList(List<? extends MappingCubeUsage> list) {
        if (list != null) {
            list.forEach(this::checkCubeUsage);
        }
    }

    private void checkVirtualCubeMeasureList(List<? extends VirtualCubeMeasure> list) {
        if (list != null) {
            list.forEach(this::checkVirtualCubeMeasure);
        }
    }

    private void checkCubeGrantList(List<? extends MappingCubeGrant> list) {
        if (list != null) {
            list.forEach(this::checkCubeGrant);
        }
    }

    private void checkHierarchyGrantList(List<? extends MappingHierarchyGrant> list) {
        if (list != null) {
            list.forEach(this::checkHierarchyGrant);
        }
    }

    private void checkMemberGrantList(List<? extends MemberGrant> list) {
        if (list != null) {
            list.forEach(this::checkMemberGrant);
        }
    }

    private void checkDimensionGrantList(List<? extends MappingDimensionGrant> list) {
        if (list != null) {
            list.forEach(this::checkDimensionGrant);
        }
    }

    private void checkSchemaGrantList(List<? extends SchemaGrant> list) {
        if (list != null) {
            list.forEach(this::checkSchemaGrant);
        }
    }

    private void checkCubeDimensionList(List<? extends MappingCubeDimension> list, MappingCube cube) {
        if (list != null) {
            list.forEach(it -> checkCubeDimension(it, cube));
        }
    }

    private void checkVirtualCubeList(List<? extends VirtualCube> list) {
        if (list != null) {
            list.forEach(this::checkVirtualCube);
        }
    }

    /**
     * @return @deprecated
     */
    @Deprecated(since="new version", forRemoval=true)
    private void checkUserDefinedFunctionList(List<? extends UserDefinedFunction> list) {
        if (list != null) {
            list.forEach(this::checkUserDefinedFunction);
        }
    }

    private void checkCubeList(List<? extends MappingCube> list) {
        if (list != null) {
            list.forEach(this::checkCube);
        }
    }

    private void checkAnnotationList(List<? extends MappingAnnotation> list) {
        if (list != null) {
            list.forEach(this::checkAnnotation);
        }
    }

    private void checkParameterList(List<? extends Parameter> list) {
        if (list != null) {
            list.forEach(this::checkParameter);
        }
    }

    private void checkCalculatedMemberList(List<? extends MappingCalculatedMember> list) {
        if (list != null) {
            list.forEach(this::checkCalculatedMember);
        }
    }

    private void checkMeasureList(List<? extends Measure> list, MappingCube cube) {
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

    private void checkDrillThroughActionList(List<? extends MappingDrillThroughAction> list) {
        if (list != null) {
            list.forEach(this::checkDrillThroughAction);
        }
    }
}
