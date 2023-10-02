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
package org.eclipse.daanse.olap.rolap.dbmapper.provider.modifier;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAction;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggColumnName;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggExclude;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggForeignKey;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggLevel;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggLevelProperty;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggMeasureFactCount;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggTable;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAnnotation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCalculatedMember;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCalculatedMemberProperty;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCellFormatter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingClosure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingColumnDef;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCubeGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCubeUsage;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingDimensionGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingDrillThroughAction;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingDrillThroughElement;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingElementFormatter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingExpressionView;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingFormula;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingHierarchy;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingHierarchyGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingHint;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingJoin;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingLevel;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingMemberGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingMemberReaderParameter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingNamedSet;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingParameter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingPrivateDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingProperty;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingRelation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingRelationOrJoin;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingRole;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingRoleUsage;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingRow;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSQL;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchemaGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingScript;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingTable;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingUnion;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingUserDefinedFunction;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingValue;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingVirtualCube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingVirtualCubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingVirtualCubeMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingWritebackColumn;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingWritebackTable;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.AccessEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.DimensionTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.HideMemberIfEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.InternalTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.LevelTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.MemberGrantAccessEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.ParameterTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.PropertyTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.TypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.ActionR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.AggColumnNameR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.AggExcludeR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.AggForeignKeyR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.AggLevelPropertyR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.AggLevelR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.AggMeasureFactCountR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.AggMeasureR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.AggNameR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.AggPatternR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.AnnotationR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.CalculatedMemberPropertyR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.CalculatedMemberR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.CellFormatterR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.ClosureR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.ColumnDefR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.CubeDimensionR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.CubeGrantR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.CubeR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.DimensionGrantR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.DrillThroughActionR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.DrillThroughAttributeR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.DrillThroughMeasureR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.FormulaR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.HierarchyGrantR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.HierarchyR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.HintR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.InlineTableR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.JoinR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.LevelR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.MemberGrantR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.MemberReaderParameterR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.NamedSetR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.ParameterR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.PrivateDimensionR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.PropertyR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.RoleR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.RoleUsageR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.RowR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.SQLR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.SchemaGrantR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.SchemaR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.ScriptR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.TableR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.UnionR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.UserDefinedFunctionR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.ValueR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.ViewR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.VirtualCubeDimensionR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.VirtualCubeMeasureR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.VirtualCubeR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.WritebackAttributeR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.WritebackMeasureR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.WritebackTableR;

import java.util.List;

import static org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.CubeUsageRBuilder.CubeUsageR;

public class RDbMappingSchemaModifier extends AbstractDbMappingSchemaModifier {

	public RDbMappingSchemaModifier(MappingSchema mappingSchema) {
		super(mappingSchema);
	}

    @Override
	protected MappingSchema new_Schema(String name, String description, String measuresCaption, String defaultRole,
			List<MappingAnnotation> annotations, List<MappingParameter> parameters,
			List<MappingPrivateDimension> dimensions, List<MappingCube> cubes, List<MappingVirtualCube> virtualCubes,
			List<MappingNamedSet> namedSets, List<MappingRole> roles,
			List<MappingUserDefinedFunction> userDefinedFunctions) {
		MappingSchema mappingSchemaNew = new SchemaR(name, description, measuresCaption, defaultRole, annotations,
				parameters, dimensions, cubes, virtualCubes, namedSets, roles, userDefinedFunctions);
		return mappingSchemaNew;
	}

    @Override
	protected AnnotationR new_Annotation(String name, String content) {
		return new AnnotationR(name, content);
	}

    @Override
	protected ParameterR new_parameter(String name, String description, ParameterTypeEnum type, boolean modifiable,
			String defaultValue) {
		return new ParameterR(name, description, type, modifiable, defaultValue);
	}

    @Override
	protected PrivateDimensionR new_PrivateDimension(String name, DimensionTypeEnum type, String caption,
			String description, String foreignKey, boolean highCardinality, List<MappingAnnotation> annotations,
			List<MappingHierarchy> hierarchies, boolean visible, String usagePrefix) {
		return new PrivateDimensionR(name, type, caption, description, foreignKey, highCardinality, annotations,
				hierarchies, visible, usagePrefix);
	}

    @Override
	protected HierarchyR new_Hierarchy(String name, String caption, String description,
			List<MappingAnnotation> annotations, List<MappingLevel> levels,
			List<MappingMemberReaderParameter> memberReaderParameters, boolean hasAll, String allMemberName,
			String allMemberCaption, String allLevelName, String primaryKey, String primaryKeyTable,
			String defaultMember, String memberReaderClass, String uniqueKeyLevelName, boolean visible,
			String displayFolder, MappingRelationOrJoin relation, String origin) {
		return new HierarchyR(name, caption, description, annotations, levels, memberReaderParameters, hasAll,
				allMemberName, allMemberCaption, allLevelName, primaryKey, primaryKeyTable, defaultMember,
				memberReaderClass, uniqueKeyLevelName, visible, displayFolder, relation, origin);
	}

    @Override
	protected MemberReaderParameterR new_MemberReaderParameter(String name, String value) {
		return new MemberReaderParameterR(name, value);
	}

    @Override
	protected MappingCube new_Cube(String name, String caption, String description, String defaultMeasure,
			List<MappingAnnotation> annotations, List<MappingCubeDimension> dimensionUsageOrDimensions,
			List<MappingMeasure> measures, List<MappingCalculatedMember> calculatedMembers,
			List<MappingNamedSet> namedSets, List<MappingDrillThroughAction> drillThroughActions,
			List<MappingWritebackTable> writebackTables, boolean enabled, boolean cache, boolean visible,
			MappingRelation fact, List<MappingAction> actions) {
		return new CubeR(name, caption, description, defaultMeasure, annotations, dimensionUsageOrDimensions, measures,
				calculatedMembers, namedSets, drillThroughActions, writebackTables, enabled, cache, visible, fact,
				actions);
	}

    @Override
	protected MappingAction new_MappingAction(String name, String caption, String description,
			List<MappingAnnotation> annotations) {
		return new ActionR(name, caption, description, annotations);
	}

    @Override
	protected MappingRole new_MappingRole(List<MappingAnnotation> annotations, List<MappingSchemaGrant> schemaGrants,
			MappingUnion union, String name) {
		return new RoleR(annotations, schemaGrants, union, name);
	}

    @Override
    protected MappingFormula new_Formula(String cdata) {
        return new FormulaR(cdata);
    }

    @Override
    protected MappingCellFormatter new_CellFormatter(
        String className,
        MappingScript script
    ) {
        return new CellFormatterR(className, script);
    }

    @Override
    protected MappingScript new_Script(String language, String cdata) {
        return new ScriptR(language, cdata);
    }

    @Override
    protected MappingCalculatedMember new_CalculatedMember(
        String name,
        String formatString,
        String caption,
        String description,
        String dimension,
        boolean visible,
        String displayFolder,
        List<MappingAnnotation> annotations,
        String formula,
        List<MappingCalculatedMemberProperty> calculatedMemberProperties,
        String hierarchy,
        String parent,
        MappingCellFormatter cellFormatter,
        MappingFormula formulaElement
    ) {
        return new CalculatedMemberR(name,
            formatString,
            caption,
            description,
            dimension,
            visible,
            displayFolder,
            annotations,
            formula,
            calculatedMemberProperties,
            hierarchy,
            parent,
            cellFormatter,
            formulaElement);
    }

    @Override
    protected MappingVirtualCubeMeasure new_VirtualCubeMeasure(
        String name,
        String cubeName,
        boolean visible,
        List<MappingAnnotation> annotations
    ) {
        return new VirtualCubeMeasureR(
            name,
            cubeName,
            visible,
            annotations
        );
    }

    @Override
    protected MappingVirtualCubeDimension new_VirtualCubeDimension(
        String name,
        String cubeName,
        List<MappingAnnotation> annotations,
        String foreignKey,
        boolean highCardinality,
        String caption,
        boolean visible,
        String description
    ) {
        return new VirtualCubeDimensionR(
            name,
            cubeName,
            annotations,
            foreignKey,
            highCardinality,
            caption,
            visible,
            description);
    }

    @Override
    protected MappingCubeUsage new_CubeUsage(String cubeName, boolean ignoreUnrelatedDimensions) {
        return CubeUsageR(cubeName, ignoreUnrelatedDimensions);
    }

    @Override
    protected MappingVirtualCube new_VirtualCube(
        String name,
        String caption,
        String description,
        String defaultMeasure,
        boolean enabled,
        List<MappingAnnotation> annotations,
        List<MappingCubeUsage> cubeUsages,
        List<MappingVirtualCubeDimension> virtualCubeDimensions,
        List<MappingVirtualCubeMeasure> virtualCubeMeasures,
        List<MappingCalculatedMember> calculatedMembers,
        List<MappingNamedSet> namedSets,
        boolean visible
    ) {
        return new VirtualCubeR(
            name,
            caption,
            description,
            defaultMeasure,
            enabled,
            annotations,
            cubeUsages,
            virtualCubeDimensions,
            virtualCubeMeasures,
            calculatedMembers,
            namedSets,
            visible);
    }

    @Override
    protected MappingCalculatedMemberProperty new_CalculatedMemberProperty(
        String name,
        String caption,
        String description,
        String expression,
        String value
    ) {
        return new CalculatedMemberPropertyR(
            name,
            caption,
            description,
            expression,
            value
        );
    }

    @Override
    protected MappingNamedSet new_NamedSet(String name,
                                           String caption,
                                           String description,
                                           String formula,
                                           List<MappingAnnotation> annotations,
                                           String displayFolder,
                                           MappingFormula formulaElement) {
        return new NamedSetR(
            name,
            caption,
            description,
            formula,
            annotations,
            displayFolder,
            formulaElement
        );
    }

    @Override
    protected MappingUnion new_Union(List<MappingRoleUsage> roleUsages) {
        return new UnionR(roleUsages);
    }

    @Override
    protected MappingRoleUsage new_RoleUsage(String roleName) {
        return new RoleUsageR(roleName);
    }

    @Override
    protected MappingSchemaGrant new_SchemaGrant(List<MappingCubeGrant> schemaGrantCubeGrants, AccessEnum access) {
        return new SchemaGrantR(schemaGrantCubeGrants, access);
    }

    @Override
    protected MappingDimensionGrant new_DimensionGrant(AccessEnum access, String dimension) {
        return new DimensionGrantR(access, dimension);
    }

    @Override
    protected MappingRelation new_InlineTable(
        List<MappingColumnDef> columnDefs,
        List<MappingRow> rows, String alias
    ) {
        return new InlineTableR(columnDefs, rows, alias);
    }

    @Override
    protected MappingSQL new_SQL(String content,
                                 String dialect) {
        return new SQLR(content, dialect);
    }

    @Override
    protected MappingAggTable new_AggName(String name,
                                          MappingAggColumnName aggFactCount,
                                          List<MappingAggMeasure> aggMeasures,
                                          List<MappingAggColumnName> aggIgnoreColumns,
                                          List<MappingAggForeignKey> aggForeignKeys,
                                          List<MappingAggLevel> aggLevels,
                                          boolean ignorecase,
                                          List<MappingAggMeasureFactCount> measuresFactCounts,
                                          String approxRowCount) {
        return new AggNameR(
            name,
            aggFactCount,
            aggMeasures,
            aggIgnoreColumns,
            aggForeignKeys,
            aggLevels,
            ignorecase,
            measuresFactCounts,
            approxRowCount
        );
    }

    @Override
    protected MappingAggLevel new_AggLevel(String column,
                                           String name,
                                           String ordinalColumn,
                                           String nameColumn,
                                           String captionColumn,
                                           Boolean collapsed,
                                           List<MappingAggLevelProperty> properties) {
        return new AggLevelR(
            column,
            name,
            ordinalColumn,
            nameColumn,
            captionColumn,
            collapsed,
            properties
        );
    }

    @Override
    protected MappingAggMeasure new_AggMeasure(String column,
                                               String name,
                                               String rollupType) {
        return new AggMeasureR(column, name, rollupType);
    }

    @Override
    protected MappingAggForeignKey new_AggForeignKey(String factColumn,
                                                     String aggColumn) {
        return new AggForeignKeyR(factColumn, aggColumn);
    }

    @Override
    protected MappingRow new_Row(List<MappingValue> values) {
        return new RowR(values);
    }

    @Override
    protected MappingValue new_Value(String column,
                                     String content) {
        return new ValueR(column, content);
    }

    @Override
    protected MappingColumnDef new_ColumnDef(String name,
                                             TypeEnum type) {
        return new ColumnDefR(name, type);
    }

    @Override
    protected MappingElementFormatter new_ElementFormatter(String className, MappingScript script) {
        return new CellFormatterR(className, script);
    }

    @Override
    protected MappingMemberGrant new_MemberGrant(String member, MemberGrantAccessEnum access) {
        return new MemberGrantR(member, access);
    }

    @Override
    protected MappingHierarchyGrant new_HierarchyGrant(String hierarchy,
                                                       AccessEnum access,
                                                       String topLevel,
                                                       String bottomLevel,
                                                       String rollupPolicy,
                                                       List<MappingMemberGrant> memberGrants) {
        return new HierarchyGrantR(
            hierarchy,
            access,
            topLevel,
            bottomLevel,
            rollupPolicy,
            memberGrants);
    }

    @Override
    protected MappingCubeGrant new_CubeGrant(
        String cube,
        String access,
        List<MappingDimensionGrant> dimensionGrants,
        List<MappingHierarchyGrant> hierarchyGrants
    ) {
        return new CubeGrantR(
            cube,
            access,
            dimensionGrants,
            hierarchyGrants
        );
    }

    @Override
    protected MappingHint new_Hint(String content,
                                   String type) {
        return new HintR(content, type);
    }

    @Override
    protected MappingAggMeasureFactCount new_AggMeasureFactCount(String factColumn, String column) {
        return new AggMeasureFactCountR(factColumn, column);
    }

    @Override
    protected MappingAggLevelProperty new_AggLevelProperty(String name, String column) {
        return new AggLevelPropertyR(name, column);
    }

    @Override
    protected MappingAggColumnName new_AggColumnName(String column) {
        return new AggColumnNameR(column);
    }

    @Override
    protected MappingAggTable new_AggPattern(
        String pattern,
        MappingAggColumnName aggFactCount,
        List<MappingAggColumnName> aggIgnoreColumns,
        List<MappingAggForeignKey> aggForeignKeys,
        List<MappingAggMeasure> aggMeasures,
        List<MappingAggLevel> aggLevels,
        List<MappingAggExclude> aggExcludes,
        boolean ignorecase,
        List<MappingAggMeasureFactCount> measuresFactCounts
    ){
        return new AggPatternR(
            pattern,
            aggFactCount,
            aggIgnoreColumns,
            aggForeignKeys,
            aggMeasures,
            aggLevels,
            aggExcludes,
            ignorecase,
            measuresFactCounts);
    }

    protected MappingAggExclude new_AggExclude(
        String pattern,
        String name,
        boolean ignorecase
    ) {
        return new AggExcludeR(
            pattern,
            name,
            ignorecase
        );
    }

    @Override
    protected MappingTable new_Table(String schema, String name, String alias,
                                     List<MappingHint> hints, MappingSQL sql,
                                     List<MappingAggExclude> aggExcludes, List<MappingAggTable> aggTables) {
        return new TableR(schema, name, alias, hints, sql, aggExcludes, aggTables);
    }

    @Override
    protected MappingRelation new_View(String alias,
                                       List<MappingSQL> sqls) {
        return new ViewR(alias, sqls);
    }

    @Override
    protected MappingProperty new_Property(
        String name,
        String column,
        PropertyTypeEnum type,
        String formatter,
        String caption,
        String description,
        boolean dependsOnLevelValue,
        MappingElementFormatter propertyFormatter
    ) {
        return new PropertyR(
            name,
            column,
            type,
            formatter,
            caption,
            description,
            dependsOnLevelValue,
            propertyFormatter
        );
    }

    protected MappingClosure new_Closure(MappingTable table,
                                         String parentColumn,
                                         String childColumn) {
        return new ClosureR(table, parentColumn, childColumn);
    }

    @Override
    protected MappingLevel new_Level(
        String name,
        String table,
        String column,
        String nameColumn,
        String ordinalColumn,
        String parentColumn,
        String nullParentValue,
        TypeEnum type,
        String approxRowCount,
        boolean uniqueMembers,
        LevelTypeEnum levelType,
        HideMemberIfEnum hideMemberIf,
        String formatter,
        String caption,
        String description,
        String captionColumn,
        List<MappingAnnotation> annotations,
        MappingExpressionView keyExpression,
        MappingExpressionView nameExpression,
        MappingExpressionView captionExpression,
        MappingExpressionView ordinalExpression,
        MappingExpressionView parentExpression,
        MappingClosure closure,
        List<MappingProperty> properties,
        boolean visible,
        InternalTypeEnum internalType,
        MappingElementFormatter memberFormatter
    ) {
        return new LevelR(
            name,
            table,
            column,
            nameColumn,
            ordinalColumn,
            parentColumn,
            nullParentValue,
            type,
            approxRowCount,
            uniqueMembers,
            levelType,
            hideMemberIf,
            formatter,
            caption,
            description,
            captionColumn,
            annotations,
            keyExpression,
            nameExpression,
            captionExpression,
            ordinalExpression,
            parentExpression,
            closure,
            properties,
            visible,
            internalType,
            memberFormatter
        );
    }

    @Override
    protected MappingJoin new_Join(List<MappingRelationOrJoin> relations,
                                   String leftAlias,
                                   String leftKey,
                                   String rightAlias,
                                   String rightKey) {
        return new JoinR(relations,
            leftAlias,
            leftKey,
            rightAlias,
            rightKey);
    }

    @Override
    protected MappingUserDefinedFunction new_UserDefinedFunction(
        String name,
        String className,
        MappingScript script) {
        return new UserDefinedFunctionR(name, className, script);
    }

    @Override
    protected MappingCubeDimension new_CubeDimension(
        List<MappingAnnotation> annotations,
        String name,
        String foreignKey,
        boolean highCardinality,
        String caption,
        boolean visible,
        String description
    ) {
        return new CubeDimensionR(
            annotations,
            name,
            foreignKey,
            highCardinality,
            caption,
            visible,
            description);
    }

    @Override
    protected MappingDrillThroughAction new_DrillThroughAction(
        String name,
        String caption,
        String description,
        Boolean defaultt,
        List<MappingAnnotation> annotations,
        List<MappingDrillThroughElement> drillThroughElements
    ) {
        return new DrillThroughActionR(name,
            caption,
            description,
            defaultt,
            annotations,
            drillThroughElements);
    }

    @Override
    protected MappingDrillThroughElement new_DrillThroughMeasure(String name) {
        return new DrillThroughMeasureR(name);
    }

    @Override
    protected MappingDrillThroughElement new_MappingDrillThroughAttribute(
        String dimension,
        String level,
        String hierarchy) {
        return new DrillThroughAttributeR(dimension,
            level,
            hierarchy);
    }

    @Override
    protected MappingWritebackTable new_WritebackTable(
        String schema,
        String name,
        List<MappingWritebackColumn> columns
    ) {
        return new WritebackTableR(schema, name, columns);
    }

    @Override
    protected MappingWritebackColumn new_WritebackAttribute(String dimension, String column) {
        return new WritebackAttributeR(dimension, column);
    }

    @Override
    protected MappingWritebackColumn new_WritebackMeasure(String name, String column) {
        return new WritebackMeasureR(name, column);
    }
}
