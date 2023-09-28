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
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingColumnDef;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCubeGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCubeUsage;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingDimensionGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingDrillThroughAction;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingElementFormatter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingFormula;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingHierarchy;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingLevel;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingMemberReaderParameter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingNamedSet;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingParameter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingPrivateDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingRelation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingRelationOrJoin;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingRole;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingRoleUsage;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingRow;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSQL;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchemaGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingScript;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingUnion;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingUserDefinedFunction;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingValue;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingVirtualCube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingVirtualCubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingVirtualCubeMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingWritebackTable;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.AccessEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.DimensionTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.ParameterTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.TypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.ActionR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.AggForeignKeyR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.AggLevelR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.AggMeasureR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.AggNameR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.AnnotationR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.CalculatedMemberPropertyR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.CalculatedMemberR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.CellFormatterR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.ColumnDefR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.CubeR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.DimensionGrantR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.FormulaR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.HierarchyR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.InlineTableR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.MemberReaderParameterR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.NamedSetR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.ParameterR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.PrivateDimensionR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.RoleR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.RoleUsageR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.RowR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.SQLR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.SchemaGrantR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.SchemaR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.ScriptR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.UnionR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.ValueR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.VirtualCubeDimensionR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.VirtualCubeMeasureR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.VirtualCubeR;

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

}
