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
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAnnotation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCalculatedMember;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCalculatedMemberProperty;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCellFormatter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCubeUsage;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingDrillThroughAction;
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
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchemaGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingUnion;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingUserDefinedFunction;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingVirtualCube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingVirtualCubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingVirtualCubeMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingWritebackTable;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.DimensionTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.ParameterTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.ActionR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.AnnotationR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.CalculatedMemberR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.CubeR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.HierarchyR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.MemberReaderParameterR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.ParameterR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.PrivateDimensionR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.RoleR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.SchemaR;
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
}
