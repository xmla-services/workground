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
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggName;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggPattern;
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
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingElementFormatter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingExpression;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingExpressionView;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingFormula;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingHierarchy;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingHierarchyGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingHint;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingInlineTable;
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
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingView;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingVirtualCube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingVirtualCubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingVirtualCubeMeasure;
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
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.AggColumnNameR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.AggExcludeR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.AggForeignKeyR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.AggLevelPropertyR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.AggLevelR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.AggMeasureFactCountR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.AggMeasureR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.AggNameR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.AggPatternR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.CellFormatterR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.ClosureR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.ColumnDefR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.CubeGrantR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.DimensionGrantR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.HierarchyGrantR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.HintR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.InlineTableR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.LevelR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.MemberGrantR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.PropertyR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.RoleUsageR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.RowR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.SQLR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.SchemaGrantR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.TableR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.UnionR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.ValueR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.ViewR;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.api.DatabaseMappingSchemaProvider;

import java.util.List;

// TODO: implement that all elemnts are copied that other could extend this class and override some methods to
//  manipulate the data
public abstract class AbstractDbMappingSchemaModifier implements DatabaseMappingSchemaProvider {

    protected MappingSchema mappingSchema;

    protected AbstractDbMappingSchemaModifier(MappingSchema mappingSchema) {
        super();
        this.mappingSchema = mappingSchema;
    }

    public MappingSchema get() {
        return modifyMappingSchema(mappingSchema);
    }

    /**
     * @param mappingSchemaOriginal
     * @return
     */
    protected MappingSchema modifyMappingSchema(MappingSchema mappingSchemaOriginal) {

        String name = schemaName(mappingSchemaOriginal);
        String description = schemaDescription(mappingSchemaOriginal);
        String measuresCaption = schemaMeasuresCaption(mappingSchemaOriginal);
        String defaultRole = schemaDefaultRole(mappingSchemaOriginal);

        List<MappingAnnotation> annotations = schemaAnnotations(mappingSchemaOriginal);
        List<MappingParameter> parameters = schemaParameters(mappingSchemaOriginal);
        List<MappingPrivateDimension> dimensions = schemaDimensions(mappingSchemaOriginal);
        List<MappingCube> cubes = schemaCubes(mappingSchemaOriginal);
        List<MappingVirtualCube> virtualCubes = schemaVirtualCubes(mappingSchemaOriginal);
        List<MappingNamedSet> namedSets = schemaNamedSets(mappingSchemaOriginal);
        List<MappingRole> roles = schemaRoles(mappingSchemaOriginal);
        List<MappingUserDefinedFunction> userDefinedFunctions = schemaUserDefinedFunctions(mappingSchemaOriginal);

        MappingSchema mappingSchemaNew = new_Schema(name, description, measuresCaption, defaultRole, annotations,
            parameters, dimensions, cubes, virtualCubes, namedSets, roles, userDefinedFunctions);

        return mappingSchemaNew;
    }

    protected abstract MappingSchema new_Schema(
        String name, String description, String measuresCaption,
        String defaultRole, List<MappingAnnotation> annotations, List<MappingParameter> parameters,
        List<MappingPrivateDimension> dimensions, List<MappingCube> cubes, List<MappingVirtualCube> virtualCubes,
        List<MappingNamedSet> namedSets, List<MappingRole> roles,
        List<MappingUserDefinedFunction> userDefinedFunctions
    );

    protected String schemaDefaultRole(MappingSchema mappingSchemaOriginal) {
        return mappingSchemaOriginal.defaultRole();
    }

    protected String schemaMeasuresCaption(MappingSchema mappingSchemaOriginal) {
        return mappingSchemaOriginal.measuresCaption();
    }

    protected String schemaDescription(MappingSchema mappingSchemaOriginal) {
        return mappingSchemaOriginal.description();
    }

    protected String schemaName(MappingSchema mappingSchemaOriginal) {
        return mappingSchemaOriginal.name();
    }

    protected List<MappingAnnotation> schemaAnnotations(MappingSchema mappingSchemaOriginal) {
        return annotations(mappingSchemaOriginal.annotations());
    }

    protected List<MappingParameter> schemaParameters(MappingSchema mappingSchema) {
        return schemaParameters(mappingSchema.parameters());
    }

    protected List<MappingParameter> schemaParameters(List<MappingParameter> parametersOriginal) {
        return parametersOriginal.stream().map(this::parameter).toList();
    }

    protected List<MappingAnnotation> annotations(List<MappingAnnotation> annotations) {
        return annotations.stream().map(this::annotation).toList();
    }

    protected MappingAnnotation annotation(MappingAnnotation annotation) {
        String name = annotation.name();
        String content = annotation.content();
        return new_Annotation(name, content);
    }

    protected abstract MappingAnnotation new_Annotation(String name, String content);

    protected MappingParameter parameter(MappingParameter parameterOriginal) {
        String name = parameterOriginal.name();
        String description = parameterOriginal.description();
        ParameterTypeEnum type = parameterOriginal.type();
        boolean modifiable = parameterOriginal.modifiable();
        String defaultValue = parameterOriginal.defaultValue();
        return new_parameter(name, description, type, modifiable, defaultValue);
    }

    protected abstract MappingParameter new_parameter(
        String name, String description, ParameterTypeEnum type,
        boolean modifiable, String defaultValue
    );

    protected List<MappingPrivateDimension> schemaDimensions(MappingSchema mappingSchemaOriginal) {
        return dimensions(mappingSchemaOriginal.dimensions());
    }

    protected List<MappingPrivateDimension> dimensions(List<MappingPrivateDimension> dimensions) {
        return dimensions.stream().map(this::dimension).toList();
    }

    protected MappingPrivateDimension dimension(MappingPrivateDimension dimension) {

        String name = dimensionName(dimension);
        DimensionTypeEnum type = dimensionType(dimension);
        String caption = dimensionCaption(dimension);
        String description = dimensionDescription(dimension);
        String foreignKey = dimensionForeignKey(dimension);
        boolean highCardinality = dimensionCardinality(dimension);
        List<MappingAnnotation> annotations = dimensionAnnotations(dimension);
        List<MappingHierarchy> hierarchies = dimensionHieraries(dimension);
        boolean visible = dimensionVisible(dimension);
        String usagePrefix = dimensionUsagePrefix(dimension);

        return new_PrivateDimension(name, type, caption, description, foreignKey, highCardinality, annotations,
            hierarchies, visible, usagePrefix);

    }

    protected abstract MappingPrivateDimension new_PrivateDimension(
        String name, DimensionTypeEnum type, String caption,
        String description, String foreignKey, boolean highCardinality, List<MappingAnnotation> annotations,
        List<MappingHierarchy> hierarchies, boolean visible, String usagePrefix
    );

    protected List<MappingHierarchy> dimensionHieraries(MappingPrivateDimension dimension) {
        return hierarchies(dimension.hierarchies());
    }

    protected String dimensionUsagePrefix(MappingPrivateDimension dimension) {
        return dimension.usagePrefix();
    }

    protected boolean dimensionVisible(MappingPrivateDimension dimension) {
        return dimension.visible();
    }

    protected boolean dimensionCardinality(MappingPrivateDimension dimension) {
        return dimension.highCardinality();
    }

    protected String dimensionForeignKey(MappingPrivateDimension dimension) {
        return dimension.foreignKey();
    }

    protected String dimensionDescription(MappingPrivateDimension dimension) {
        return dimension.description();
    }

    protected String dimensionCaption(MappingPrivateDimension dimension) {
        return dimension.caption();
    }

    protected DimensionTypeEnum dimensionType(MappingPrivateDimension dimension) {
        return dimension.type();
    }

    protected String dimensionName(MappingPrivateDimension dimension) {
        return dimension.name();
    }

    protected List<MappingAnnotation> dimensionAnnotations(MappingPrivateDimension mappingPrivateDimensionOriginal) {
        return annotations(mappingPrivateDimensionOriginal.annotations());
    }

    protected List<MappingHierarchy> hierarchies(List<MappingHierarchy> hierarchies) {
        return hierarchies.stream().map(this::hierarchy).toList();
    }

    protected MappingHierarchy hierarchy(MappingHierarchy hierarchy) {

        String name = hierarchyName(hierarchy);
        String caption = hierarchyCaption(hierarchy);
        String description = hierarchyDescription(hierarchy);
        List<MappingAnnotation> annotations = hierarchyAnnotations(hierarchy);
        List<MappingLevel> levels = hierarchyLevels(hierarchy);
        List<MappingMemberReaderParameter> memberReaderParameters = hierarchyMemberReaderParameters(hierarchy);
        boolean hasAll = hierarchyHasAll(hierarchy);
        String allMemberName = hierarchyAllMemerName(hierarchy);
        String allMemberCaption = hierarchyAllMemberCaption(hierarchy);
        String allLevelName = hierarchyAllLevelName(hierarchy);
        String primaryKey = hierarchyPrimaryKey(hierarchy);
        String primaryKeyTable = hierarchyPrimaryKeyTable(hierarchy);
        String defaultMember = hierarchyDefaultMember(hierarchy);
        String memberReaderClass = hierarchyMemberReaderClass(hierarchy);
        String uniqueKeyLevelName = hierarchyUniqueKeyLevelName(hierarchy);
        boolean visible = hierarchyVisible(hierarchy);
        String displayFolder = hierarchyDisplayFolder(hierarchy);
        MappingRelationOrJoin relation = hierarchyRelation(hierarchy);
        String origin = hierarchyOrigin(hierarchy);

        return new_Hierarchy(name, caption, description, annotations, levels, memberReaderParameters, hasAll,
            allMemberName, allMemberCaption, allLevelName, primaryKey, primaryKeyTable, defaultMember,
            memberReaderClass, uniqueKeyLevelName, visible, displayFolder, relation, origin);

    }

    protected abstract MappingHierarchy new_Hierarchy(
        String name, String caption, String description,
        List<MappingAnnotation> annotations, List<MappingLevel> levels,
        List<MappingMemberReaderParameter> memberReaderParameters, boolean hasAll, String allMemberName,
        String allMemberCaption, String allLevelName, String primaryKey, String primaryKeyTable,
        String defaultMember, String memberReaderClass, String uniqueKeyLevelName, boolean visible,
        String displayFolder, MappingRelationOrJoin relation, String origin
    );

    protected String hierarchyOrigin(MappingHierarchy hierarchy) {
        return hierarchy.origin();
    }

    protected String hierarchyDisplayFolder(MappingHierarchy hierarchy) {
        return hierarchy.displayFolder();
    }

    protected boolean hierarchyVisible(MappingHierarchy hierarchy) {
        return hierarchy.visible();
    }

    protected String hierarchyUniqueKeyLevelName(MappingHierarchy hierarchy) {
        return hierarchy.uniqueKeyLevelName();
    }

    protected String hierarchyMemberReaderClass(MappingHierarchy hierarchy) {
        return hierarchy.memberReaderClass();
    }

    protected String hierarchyDefaultMember(MappingHierarchy hierarchy) {
        return hierarchy.defaultMember();
    }

    protected String hierarchyPrimaryKeyTable(MappingHierarchy hierarchy) {
        return hierarchy.primaryKeyTable();
    }

    protected String hierarchyPrimaryKey(MappingHierarchy hierarchy) {
        return hierarchy.primaryKey();
    }

    protected String hierarchyAllLevelName(MappingHierarchy hierarchy) {
        return hierarchy.allLevelName();
    }

    protected String hierarchyAllMemberCaption(MappingHierarchy hierarchy) {
        return hierarchy.allMemberCaption();
    }

    protected String hierarchyAllMemerName(MappingHierarchy hierarchy) {
        return hierarchy.allMemberName();
    }

    protected boolean hierarchyHasAll(MappingHierarchy hierarchy) {
        return hierarchy.hasAll();
    }

    protected List<MappingMemberReaderParameter> hierarchyMemberReaderParameters(MappingHierarchy hierarchy) {
        return memberReaderParameters(hierarchy.memberReaderParameters());
    }

    protected List<MappingMemberReaderParameter> memberReaderParameters(
        List<MappingMemberReaderParameter> mappingMemberReaderParameters
    ) {
        return mappingMemberReaderParameters.stream().map(this::memberReaderParameter).toList();
    }

    protected MappingMemberReaderParameter memberReaderParameter(
        MappingMemberReaderParameter mappingMemberReaderParameter
    ) {

        String name = mappingMemberReaderParameter.name();
        String value = mappingMemberReaderParameter.value();

        return new_MemberReaderParameter(name, value);
    }

    protected abstract MappingMemberReaderParameter new_MemberReaderParameter(String name, String value);

    protected List<MappingLevel> hierarchyLevels(MappingHierarchy hierarchy) {
        return levels(hierarchy.levels());
    }

    protected List<MappingLevel> levels(List<MappingLevel> levels) {
        return levels.stream().map(this::level).toList();
    }

    protected List<MappingAnnotation> hierarchyAnnotations(MappingHierarchy hierarchy) {
        return annotations(hierarchy.annotations());
    }

    protected String hierarchyDescription(MappingHierarchy hierarchy) {
        return hierarchy.description();
    }

    protected String hierarchyCaption(MappingHierarchy hierarchy) {
        return hierarchy.caption();
    }

    protected String hierarchyName(MappingHierarchy hierarchy) {
        return hierarchy.name();
    }

    protected List<MappingCube> schemaCubes(MappingSchema mappingSchemaOriginal) {

        return cubes(mappingSchemaOriginal.cubes());
    }

    protected List<MappingCube> cubes(List<MappingCube> cubes) {
        return cubes.stream().map(this::cube).toList();

    }

    protected MappingCube cube(MappingCube cube) {
        String name = cubeName(cube);
        String caption = cubeCaption(cube);
        String description = cubeDescription(cube);
        String defaultMeasure = cubeDefaultMeasures(cube);
        List<MappingAnnotation> annotations = cubeAnnotations(cube);
        List<MappingCubeDimension> dimensionUsageOrDimensions = cubeDimensionUsageOrDimensions(cube);
        List<MappingMeasure> measures = cubeMeasures(cube);
        List<MappingCalculatedMember> calculatedMembers = cubeCalculatedMembers(cube);
        List<MappingNamedSet> namedSets = cubeNamedSets(cube);
        List<MappingDrillThroughAction> drillThroughActions = cubeDrillThroughActions(cube);
        List<MappingWritebackTable> writebackTables = cubewritebackTables(cube);
        boolean enabled = cubeEnabled(cube);
        boolean cache = cubeCache(cube);
        boolean visible = cubeVisible(cube);
        MappingRelation fact = cubeFact(cube);
        List<MappingAction> actions = cubeActions(cube);

        return new_Cube(name, caption, description, defaultMeasure, annotations, dimensionUsageOrDimensions, measures,
            calculatedMembers, namedSets, drillThroughActions, writebackTables, enabled, cache, visible, fact,
            actions);

    }

    protected abstract MappingCube new_Cube(
        String name, String caption, String description, String defaultMeasure,
        List<MappingAnnotation> annotations, List<MappingCubeDimension> dimensionUsageOrDimensions,
        List<MappingMeasure> measures, List<MappingCalculatedMember> calculatedMembers,
        List<MappingNamedSet> namedSets, List<MappingDrillThroughAction> drillThroughActions,
        List<MappingWritebackTable> writebackTables, boolean enabled, boolean cache, boolean visible,
        MappingRelation fact, List<MappingAction> actions
    );

    protected List<MappingAction> cubeActions(MappingCube cube) {
        return actions(cube.actions());
    }

    protected List<MappingAction> actions(List<MappingAction> actions) {
        return actions.stream().map(this::action).toList();
    }

    protected MappingAction action(MappingAction action) {

        String name = actionName(action);
        String caption = actionCaption(action);
        String description = actionDescription(action);
        List<MappingAnnotation> annotations = actionAnnotations(action);
        return new_MappingAction(name, caption, description, annotations);
    }

    protected abstract MappingAction new_MappingAction(
        String name, String caption, String description,
        List<MappingAnnotation> annotations
    );

    protected String actionDescription(MappingAction action) {
        return action.description();
    }

    protected String actionCaption(MappingAction action) {
        return action.caption();
    }

    protected String actionName(MappingAction action) {
        return action.name();
    }

    protected List<MappingAnnotation> actionAnnotations(MappingAction action) {
        return annotations(action.annotations());
    }

    protected boolean cubeVisible(MappingCube cube) {
        return cube.visible();
    }

    protected boolean cubeCache(MappingCube cube) {
        return cube.cache();
    }

    protected boolean cubeEnabled(MappingCube cube) {
        return cube.enabled();
    }

    protected List<MappingNamedSet> cubeNamedSets(MappingCube cube) {
        return namedSets(cube.namedSets());
    }

    protected List<MappingAnnotation> cubeAnnotations(MappingCube cube) {
        return annotations(cube.annotations());
    }

    protected String cubeDefaultMeasures(MappingCube cube) {
        return cube.defaultMeasure();
    }

    protected String cubeDescription(MappingCube cube) {
        return cube.description();
    }

    protected String cubeCaption(MappingCube cube) {
        return cube.caption();
    }

    protected String cubeName(MappingCube cube) {
        return cube.name();
    }

    protected List<MappingVirtualCube> schemaVirtualCubes(MappingSchema mappingSchemaOriginal) {

        return virtualCubes(mappingSchemaOriginal.virtualCubes());
    }

    protected List<MappingVirtualCube> virtualCubes(List<MappingVirtualCube> virtualCubes) {
        return virtualCubes.stream().map(this::virtualCube).toList();

    }

    protected List<MappingNamedSet> schemaNamedSets(MappingSchema mappingSchemaOriginal) {

        return namedSets(mappingSchemaOriginal.namedSets());
    }

    protected List<MappingNamedSet> namedSets(List<MappingNamedSet> mappingNamedSets) {
        return mappingNamedSets.stream().map(this::namedSets).toList();

    }

    protected List<MappingRole> schemaRoles(MappingSchema mappingSchemaOriginal) {
        return roles(mappingSchemaOriginal.roles());
    }

    protected List<MappingRole> roles(List<MappingRole> roles) {
        return roles.stream().map(this::role).toList();
    }

    protected MappingRole role(MappingRole role) {
        List<MappingAnnotation> annotations = roleAnnotations(role);
        List<MappingSchemaGrant> schemaGrants = roleSchemaGrants(role);
        MappingUnion union = roleUnion(role);
        String name = roleName(role);

        return new_MappingRole(annotations, schemaGrants, union, name);
    }

    protected abstract MappingRole new_MappingRole(
        List<MappingAnnotation> annotations,
        List<MappingSchemaGrant> schemaGrants, MappingUnion union, String name
    );

    protected String roleName(MappingRole role) {
        return role.name();
    }

    protected MappingUnion roleUnion(MappingRole role) {
        return union(role.union());
    }

    protected List<MappingSchemaGrant> roleSchemaGrants(MappingRole role) {
        return schemaGrants(role.schemaGrants());
    }

    protected List<MappingSchemaGrant> schemaGrants(List<MappingSchemaGrant> grants) {
        return grants.stream().map(this::grant).toList();
    }

    protected List<MappingAnnotation> roleAnnotations(MappingRole role) {
        return annotations(role.annotations());
    }

    ////////////////// TODO
    protected MappingVirtualCube virtualCube(MappingVirtualCube virtualCube) {
        List<MappingAnnotation> annotations = virtualCubeAnnotations(virtualCube);
        List<MappingCubeUsage> cubeUsages = virtualCubeCubeUsage(virtualCube);
        List<MappingVirtualCubeDimension> virtualCubeDimensions = virtualCubeVirtualCubeDimension(virtualCube);
        List<MappingVirtualCubeMeasure> virtualCubeMeasures = virtualCubeVirtualCubeMeasure(virtualCube);
        List<MappingCalculatedMember> calculatedMembers = virtualCubeCalculatedMember(virtualCube);
        List<MappingNamedSet> namedSets = virtualCubeNamedSet(virtualCube);
        boolean enabled = virtualCubeEnabled(virtualCube);
        String name = virtualCubeName(virtualCube);
        String defaultMeasure = virtualCubeDefaultMeasure(virtualCube);
        String caption = virtualCubeCaption(virtualCube);
        String description = virtualCubeDescription(virtualCube);
        boolean visible = virtualCubeVisible(virtualCube);

        return new_VirtualCube(
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

    protected boolean virtualCubeVisible(MappingVirtualCube virtualCube) {
        return virtualCube.visible();
    }

    protected String virtualCubeDescription(MappingVirtualCube virtualCube) {
        return virtualCube.description();
    }

    protected String virtualCubeCaption(MappingVirtualCube virtualCube) {
        return virtualCube.caption();
    }

    protected String virtualCubeDefaultMeasure(MappingVirtualCube virtualCube) {
        return virtualCube.defaultMeasure();
    }

    protected String virtualCubeName(MappingVirtualCube virtualCube) {
        return virtualCube.name();
    }

    ;

    protected boolean virtualCubeEnabled(MappingVirtualCube virtualCube) {
        return virtualCube.enabled();
    }

    protected List<MappingNamedSet> virtualCubeNamedSet(MappingVirtualCube virtualCube) {
        return namedSets(virtualCube.namedSets());
    }

    protected List<MappingCalculatedMember> virtualCubeCalculatedMember(MappingVirtualCube virtualCube) {
        return calculatedMembers(virtualCube.calculatedMembers());
    }

    private List<MappingCalculatedMember> calculatedMembers(List<MappingCalculatedMember> calculatedMembers) {
        return calculatedMembers.stream().map(this::calculatedMember).toList();
    }

    private MappingCalculatedMember calculatedMember(MappingCalculatedMember calculatedMember) {
        String name = calculatedMemberName(calculatedMember);
        String formatString = calculatedMemberFormatString(calculatedMember);
        String caption = calculatedMemberCaption(calculatedMember);
        String description = calculatedMemberDescription(calculatedMember);
        String dimension = calculatedMemberDimension(calculatedMember);
        boolean visible = calculatedMemberVisible(calculatedMember);
        String displayFolder = calculatedMemberDisplayFolder(calculatedMember);
        List<MappingAnnotation> annotations = calculatedMemberAnnotations(calculatedMember);
        String formula = calculatedMemberFormula(calculatedMember);
        List<MappingCalculatedMemberProperty> calculatedMemberProperties =
            calculatedMemberCalculatedMemberProperties(calculatedMember);
        String hierarchy = calculatedMemberHierarchy(calculatedMember);
        String parent = calculatedMemberParent(calculatedMember);
        MappingCellFormatter cellFormatter = calculatedMemberCellFormatter(calculatedMember);
        MappingFormula formulaElement = calculatedMemberFormulaElement(calculatedMember);

        return new_CalculatedMember(
            name,
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
            formulaElement
        );
    }

    protected MappingFormula calculatedMemberFormulaElement(MappingCalculatedMember calculatedMember) {
        return formula(calculatedMember.formulaElement());
    }

    protected abstract MappingFormula new_Formula(String cdata);

    protected String formulaCdata(MappingFormula formula) {
        return formula.cdata();
    }

    protected MappingCellFormatter calculatedMemberCellFormatter(MappingCalculatedMember calculatedMember) {
        return cellFormatter(calculatedMember.cellFormatter());
    }

    protected MappingCellFormatter cellFormatter(MappingCellFormatter cellFormatter) {
        String className = cellFormatterClassName(cellFormatter);
        MappingScript script = cellFormatterScript(cellFormatter);

        return new_CellFormatter(className, script);
    }

    protected abstract MappingCellFormatter new_CellFormatter(String className, MappingScript script);

    protected MappingScript cellFormatterScript(MappingCellFormatter cellFormatter) {
        return script(cellFormatter.script());
    }

    protected MappingScript script(MappingScript script) {
        String language = scriptLanguage(script);
        String cdata = scriptCdata(script);
        return new_Script(language, cdata);
    }

    protected abstract MappingScript new_Script(String language, String cdata);

    protected String scriptCdata(MappingScript script) {
        return script.cdata();
    }

    protected String scriptLanguage(MappingScript script) {
        return script.language();
    }

    protected String cellFormatterClassName(MappingCellFormatter cellFormatter) {
        return cellFormatter.className();
    }

    protected String calculatedMemberParent(MappingCalculatedMember calculatedMember) {
        return calculatedMember.parent();
    }

    protected String calculatedMemberHierarchy(MappingCalculatedMember calculatedMember) {
        return calculatedMember.hierarchy();
    }

    protected List<MappingCalculatedMemberProperty> calculatedMemberCalculatedMemberProperties(
        MappingCalculatedMember calculatedMember
    ) {
        return calculatedMemberProperties(calculatedMember.calculatedMemberProperties());
    }

    protected List<MappingCalculatedMemberProperty> calculatedMemberProperties(List<MappingCalculatedMemberProperty> calculatedMemberProperties) {
        return calculatedMemberProperties.stream().map(this::calculatedMemberProperty).toList();
    }

    private MappingCalculatedMemberProperty calculatedMemberProperty(MappingCalculatedMemberProperty calculatedMemberProperty) {
        String name = calculatedMemberPropertyName(calculatedMemberProperty);
        String caption = calculatedMemberPropertyCaption(calculatedMemberProperty);
        String description = calculatedMemberPropertyDescription(calculatedMemberProperty);
        String expression = calculatedMemberPropertyExpression(calculatedMemberProperty);
        String value = calculatedMemberPropertyValue(calculatedMemberProperty);
        return new_CalculatedMemberProperty(
            name,
            caption,
            description,
            expression,
            value
        );
    }

    protected String calculatedMemberPropertyValue(MappingCalculatedMemberProperty calculatedMemberProperty) {
        return calculatedMemberProperty.value();
    }

    protected String calculatedMemberPropertyExpression(MappingCalculatedMemberProperty calculatedMemberProperty) {
        return calculatedMemberProperty.expression();
    }

    protected String calculatedMemberPropertyDescription(MappingCalculatedMemberProperty calculatedMemberProperty) {
        return calculatedMemberProperty.description();
    }

    protected String calculatedMemberPropertyCaption(MappingCalculatedMemberProperty calculatedMemberProperty) {
        return calculatedMemberProperty.caption();
    }

    protected String calculatedMemberPropertyName(MappingCalculatedMemberProperty calculatedMemberProperty) {
        return calculatedMemberProperty.name();
    }

    protected abstract MappingCalculatedMemberProperty new_CalculatedMemberProperty(
        String name,
        String caption,
        String description,
        String expression,
        String value
    );

    protected String calculatedMemberFormula(MappingCalculatedMember calculatedMember) {
        return calculatedMember.formula();
    }

    protected List<MappingAnnotation> calculatedMemberAnnotations(MappingCalculatedMember calculatedMember) {
        return annotations(calculatedMember.annotations());
    }

    protected String calculatedMemberDisplayFolder(MappingCalculatedMember calculatedMember) {
        return calculatedMember.displayFolder();
    }

    protected boolean calculatedMemberVisible(MappingCalculatedMember calculatedMember) {
        return calculatedMember.visible();
    }

    protected String calculatedMemberDimension(MappingCalculatedMember calculatedMember) {
        return calculatedMember.dimension();
    }

    protected String calculatedMemberDescription(MappingCalculatedMember calculatedMember) {
        return calculatedMember.description();
    }

    protected String calculatedMemberCaption(MappingCalculatedMember calculatedMember) {
        return calculatedMember.caption();
    }

    protected String calculatedMemberFormatString(MappingCalculatedMember calculatedMember) {
        return calculatedMember.formatString();
    }

    protected String calculatedMemberName(MappingCalculatedMember calculatedMember) {
        return calculatedMember.name();
    }

    protected abstract MappingCalculatedMember new_CalculatedMember(
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
    );

    protected List<MappingVirtualCubeMeasure> virtualCubeVirtualCubeMeasure(MappingVirtualCube virtualCube) {
        return virtualCubeMeasures(virtualCube.virtualCubeMeasures());
    }

    protected List<MappingVirtualCubeMeasure> virtualCubeMeasures(List<MappingVirtualCubeMeasure> virtualCubeMeasures) {
        return virtualCubeMeasures.stream().map(this::virtualCubeMeasure).toList();
    }

    private MappingVirtualCubeMeasure virtualCubeMeasure(MappingVirtualCubeMeasure virtualCubeMeasure) {
        String name = virtualCubeMeasureName(virtualCubeMeasure);
        String cubeName = virtualCubeMeasureCubeName(virtualCubeMeasure);
        boolean visible = virtualCubeMeasureVisible(virtualCubeMeasure);
        List<MappingAnnotation> annotations = virtualCubeMeasureAnnotation(virtualCubeMeasure);

        return new_VirtualCubeMeasure(name, cubeName, visible, annotations);
    }

    protected List<MappingAnnotation> virtualCubeMeasureAnnotation(MappingVirtualCubeMeasure virtualCubeMeasure) {
        return annotations(virtualCubeMeasure.annotations());
    }

    protected boolean virtualCubeMeasureVisible(MappingVirtualCubeMeasure virtualCubeMeasure) {
        return virtualCubeMeasure.visible();
    }

    protected String virtualCubeMeasureCubeName(MappingVirtualCubeMeasure virtualCubeMeasure) {
        return virtualCubeMeasure.cubeName();
    }

    protected String virtualCubeMeasureName(MappingVirtualCubeMeasure virtualCubeMeasure) {
        return virtualCubeMeasure.name();
    }

    ;

    protected abstract MappingVirtualCubeMeasure new_VirtualCubeMeasure(
        String name,
        String cubeName,
        boolean visible,
        List<MappingAnnotation> annotations
    );

    protected List<MappingVirtualCubeDimension> virtualCubeVirtualCubeDimension(MappingVirtualCube virtualCube) {
        return virtualCubeDimensions(virtualCube.virtualCubeDimensions());
    }

    protected List<MappingVirtualCubeDimension> virtualCubeDimensions(List<MappingVirtualCubeDimension> virtualCubeDimensions) {
        return virtualCubeDimensions.stream().map(this::cubeDimension).toList();
    }

    private MappingVirtualCubeDimension cubeDimension(MappingVirtualCubeDimension mappingVirtualCubeDimension) {
        List<MappingAnnotation> annotations = virtualCubeDimensionAnnotations(mappingVirtualCubeDimension);
        String name = virtualCubeDimensionName(mappingVirtualCubeDimension);
        String foreignKey = virtualCubeDimensionForeignKey(mappingVirtualCubeDimension);
        boolean highCardinality = virtualCubeDimensionHighCardinality(mappingVirtualCubeDimension);
        String caption = virtualCubeDimensionCaption(mappingVirtualCubeDimension);
        boolean visible = virtualCubeDimensionVisible(mappingVirtualCubeDimension);
        String description = virtualCubeDimensionDescription(mappingVirtualCubeDimension);
        String cubeName = virtualCubeDimensionCubeName(mappingVirtualCubeDimension);

        return new_VirtualCubeDimension(
            name,
            cubeName,
            annotations,
            foreignKey,
            highCardinality,
            caption,
            visible,
            description);
    }

    protected String virtualCubeDimensionCubeName(MappingVirtualCubeDimension mappingVirtualCubeDimension) {
        return mappingVirtualCubeDimension.cubeName();
    }

    protected String virtualCubeDimensionDescription(MappingVirtualCubeDimension mappingVirtualCubeDimension) {
        return mappingVirtualCubeDimension.description();
    }

    protected boolean virtualCubeDimensionVisible(MappingVirtualCubeDimension mappingVirtualCubeDimension) {
        return mappingVirtualCubeDimension.visible();
    }

    protected String virtualCubeDimensionCaption(MappingVirtualCubeDimension mappingVirtualCubeDimension) {
        return mappingVirtualCubeDimension.caption();
    }

    protected boolean virtualCubeDimensionHighCardinality(MappingVirtualCubeDimension mappingVirtualCubeDimension) {
        return mappingVirtualCubeDimension.highCardinality();
    }

    protected String virtualCubeDimensionForeignKey(MappingVirtualCubeDimension mappingVirtualCubeDimension) {
        return mappingVirtualCubeDimension.foreignKey();
    }

    protected String virtualCubeDimensionName(MappingVirtualCubeDimension mappingVirtualCubeDimension) {
        return mappingVirtualCubeDimension.name();
    }

    protected List<MappingAnnotation> virtualCubeDimensionAnnotations(MappingVirtualCubeDimension mappingVirtualCubeDimension) {
        return annotations(mappingVirtualCubeDimension.annotations());
    }

    protected abstract MappingVirtualCubeDimension new_VirtualCubeDimension(
        String name,
        String cubeName,
        List<MappingAnnotation> annotations,
        String foreignKey,
        boolean highCardinality,
        String caption,
        boolean visible,
        String description
    );

    protected List<MappingCubeUsage> virtualCubeCubeUsage(MappingVirtualCube virtualCube) {
        return cubeUsages(virtualCube.cubeUsages());
    }

    protected List<MappingCubeUsage> cubeUsages(List<MappingCubeUsage> cubeUsages) {
        return cubeUsages.stream().map(this::cubeUsage).toList();
    }

    private MappingCubeUsage cubeUsage(MappingCubeUsage mappingCubeUsage) {
        String cubeName = cubeUsageCubeName(mappingCubeUsage);
        boolean ignoreUnrelatedDimensions = cubeUsageIgnoreUnrelatedDimensions(mappingCubeUsage);
        return new_CubeUsage(cubeName, ignoreUnrelatedDimensions);
    }

    protected abstract MappingCubeUsage new_CubeUsage(String cubeName, boolean ignoreUnrelatedDimensions);

    protected String cubeUsageCubeName(MappingCubeUsage mappingCubeUsage) {
        return mappingCubeUsage.cubeName();
    }

    protected boolean cubeUsageIgnoreUnrelatedDimensions(MappingCubeUsage mappingCubeUsage) {
        return mappingCubeUsage.ignoreUnrelatedDimensions();
    }

    protected List<MappingAnnotation> virtualCubeAnnotations(MappingVirtualCube virtualCube) {
        return annotations(virtualCube.annotations());
    }

    protected abstract MappingVirtualCube new_VirtualCube(
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
    );

    protected MappingNamedSet namedSets(MappingNamedSet namedSet) {
        List<MappingAnnotation> annotations = namedSetAnnotations(namedSet);
        String formula = namedSetFormula(namedSet);
        String name = namedSetName(namedSet);
        String caption = namedSetCaption(namedSet);
        String description = namedSetDescription(namedSet);
        String displayFolder = namedSetDisplayFolder(namedSet);
        MappingFormula formulaElement = namedSetFormulaElement(namedSet);

        return new_NamedSet(
            name,
            caption,
            description,
            formula,
            annotations,
            displayFolder,
            formulaElement
        );

    }

    protected List<MappingAnnotation> namedSetAnnotations(MappingNamedSet namedSet) {
        return annotations(namedSet.annotations());
    }

    protected MappingFormula namedSetFormulaElement(MappingNamedSet namedSet) {
        return formula(namedSet.formulaElement());
    }

    protected MappingFormula formula(MappingFormula formula) {
        String cdata = formulaCdata(formula);
        return new_Formula(cdata);
    }

    protected String namedSetDisplayFolder(MappingNamedSet namedSet) {
        return namedSet.displayFolder();
    }

    protected String namedSetDescription(MappingNamedSet namedSet) {
        return namedSet.description();
    }

    protected String namedSetCaption(MappingNamedSet namedSet) {
        return namedSet.caption();
    }

    protected String namedSetName(MappingNamedSet namedSet) {
        return namedSet.name();
    }

    protected String namedSetFormula(MappingNamedSet namedSet) {
        return namedSet.formula();
    }

    protected abstract MappingNamedSet new_NamedSet(
        String name,
        String caption,
        String description,
        String formula,
        List<MappingAnnotation> annotations,
        String displayFolder,
        MappingFormula formulaElement
    );

    protected MappingUnion union(MappingUnion union) {
        List<MappingRoleUsage> roleUsages = unionRoleUsages(union);

        return new_Union(roleUsages);
    }

    protected abstract MappingUnion new_Union(List<MappingRoleUsage> roleUsages);

    protected List<MappingRoleUsage> unionRoleUsages(MappingUnion union) {
        return roleUsages(union.roleUsages());
    }

    protected List<MappingRoleUsage> roleUsages(List<MappingRoleUsage> roleUsages) {
        return roleUsages.stream().map(this::roleUsage).toList();
    }

    private MappingRoleUsage roleUsage(MappingRoleUsage roleUsage) {
        String roleName = roleUsageRoleName(roleUsage);
        return new_RoleUsage(roleName);
    }

    protected String roleUsageRoleName(MappingRoleUsage roleUsage) {
        return roleUsage.roleName();
    }

    protected abstract MappingRoleUsage new_RoleUsage(String roleName);

    protected MappingSchemaGrant grant(MappingSchemaGrant schemaGrant) {
        List<MappingCubeGrant> schemaGrantCubeGrants = schemaGrantCubeGrants(schemaGrant);
        AccessEnum access = schemaGrantAccess(schemaGrant);
        return new_SchemaGrant(schemaGrantCubeGrants, access);
    }

    protected List<MappingCubeGrant> schemaGrantCubeGrants(MappingSchemaGrant schemaGrant) {
           return cubeGrants(schemaGrant.cubeGrants());
    }

    protected List<MappingCubeGrant> cubeGrants(List<MappingCubeGrant> cubeGrants) {
        return cubeGrants.stream().map(this::cubeGrant).toList();
    }

    private MappingCubeGrant cubeGrant(MappingCubeGrant cubeGrant) {
        List<MappingDimensionGrant> dimensionGrants = cubeGrantDimensionGrants(cubeGrant);
        List<MappingHierarchyGrant> hierarchyGrants = cubeGrantHierarchyGrants(cubeGrant);
        String cube = cubeGrantCube(cubeGrant);
        String access = cubeGrantAccess(cubeGrant);

        return new_CubeGrant(
            cube,
            access,
            dimensionGrants,
            hierarchyGrants
        );
    }

    protected String cubeGrantAccess(MappingCubeGrant cubeGrant) {
        return cubeGrant.access();
    }

    protected String cubeGrantCube(MappingCubeGrant cubeGrant) {
        return cubeGrant.cube();
    }

    protected List<MappingHierarchyGrant> cubeGrantHierarchyGrants(MappingCubeGrant cubeGrant) {
        return hierarchyGrants(cubeGrant.hierarchyGrants());
    }

    protected List<MappingHierarchyGrant> hierarchyGrants(List<MappingHierarchyGrant> hierarchyGrants) {
        return hierarchyGrants.stream().map(this::hierarchyGrant).toList();
    }

    private MappingHierarchyGrant hierarchyGrant(MappingHierarchyGrant hierarchyGrant) {
        List<MappingMemberGrant> memberGrants = hierarchyGrantMemberGrants(hierarchyGrant);
        String hierarchy = hierarchyGrantHierarchy(hierarchyGrant);
        AccessEnum access = hierarchyGrantAccess(hierarchyGrant);
        String topLevel = hierarchyGrantTopLevel(hierarchyGrant);
        String bottomLevel = hierarchyGrantBottomLevel(hierarchyGrant);
        String rollupPolicy = hierarchyGrantRollupPolicy(hierarchyGrant);

        return new_HierarchyGrant(hierarchy,
            access,
            topLevel,
            bottomLevel,
            rollupPolicy,
            memberGrants);
    }

    protected String hierarchyGrantRollupPolicy(MappingHierarchyGrant hierarchyGrant) {
        return hierarchyGrant.rollupPolicy();
    }

    protected String hierarchyGrantBottomLevel(MappingHierarchyGrant hierarchyGrant) {
        return hierarchyGrant.bottomLevel();
    }

    protected String hierarchyGrantTopLevel(MappingHierarchyGrant hierarchyGrant) {
        return hierarchyGrant.topLevel();
    }

    protected AccessEnum hierarchyGrantAccess(MappingHierarchyGrant hierarchyGrant) {
        return hierarchyGrant.access();
    }

    protected String hierarchyGrantHierarchy(MappingHierarchyGrant hierarchyGrant) {
        return hierarchyGrant.hierarchy();
    }

    protected List<MappingMemberGrant> hierarchyGrantMemberGrants(MappingHierarchyGrant hierarchyGrant) {
        return memberGrants(hierarchyGrant.memberGrants());
    }

    protected List<MappingMemberGrant> memberGrants(List<MappingMemberGrant> memberGrants) {
        return memberGrants.stream().map(this::memberGrant).toList();
    }

    private MappingMemberGrant memberGrant(MappingMemberGrant memberGrant) {
        String member = memberGrantMember(memberGrant);
        MemberGrantAccessEnum access = memberGrantAccess(memberGrant);
        return new_MemberGrant(member, access);
    }

    protected MemberGrantAccessEnum memberGrantAccess(MappingMemberGrant memberGrant) {
        return memberGrant.access();
    }

    protected String memberGrantMember(MappingMemberGrant memberGrant) {
        return memberGrant.member();
    }

    protected MappingMemberGrant new_MemberGrant(String member, MemberGrantAccessEnum access) {
        return new MemberGrantR(member, access);
    }

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

    protected List<MappingDimensionGrant> cubeGrantDimensionGrants(MappingCubeGrant cubeGrant) {
        return dimensionGrants(cubeGrant.dimensionGrants());
    }

    protected List<MappingDimensionGrant> dimensionGrants(List<MappingDimensionGrant> dimensionGrants) {
        return dimensionGrants.stream().map(this::dimensionGrant).toList();
    }

    private MappingDimensionGrant dimensionGrant(MappingDimensionGrant dimensionGrant) {
        AccessEnum access = dimensionGrantAccess(dimensionGrant);
        String dimension = dimensionGrantDimension(dimensionGrant);
        return new_DimensionGrant(access, dimension);
    }

    protected String dimensionGrantDimension(MappingDimensionGrant dimensionGrant) {
        return dimensionGrant.dimension();
    }

    protected AccessEnum dimensionGrantAccess(MappingDimensionGrant dimensionGrant) {
        return dimensionGrant.access();
    }

    protected abstract MappingDimensionGrant new_DimensionGrant(AccessEnum access, String dimension);

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

    protected AccessEnum schemaGrantAccess(MappingSchemaGrant schemaGrant){
        return schemaGrant.access();
    };

    protected abstract MappingSchemaGrant new_SchemaGrant(
        List<MappingCubeGrant> schemaGrantCubeGrants,
        AccessEnum access
    );

    protected MappingRelation cubeFact(MappingCube cube) {
        return relation(cube.fact());
    }

    protected MappingRelation relation(MappingRelation relation) {
        String alias = relationAlias(relation);
        if (relation instanceof MappingInlineTable inlineTable) {
            List<MappingColumnDef> columnDefs = inlineTableColumnDefs(inlineTable);
            List<MappingRow> rows = inlineTableRows(inlineTable);
            return new_InlineTable(columnDefs, rows, alias);
        }
        if (relation instanceof MappingTable table) {
            MappingSQL sql = tableSql(table);
            List<MappingAggExclude> aggExcludes = tableAggExcludes(table);
            List<MappingAggTable> aggTables = tableAggTables(table);
            List<MappingHint> hints = tableHints(table);
            String name = tableName(table);
            String schema = tableSchema(table);
            return new_Table(schema, name, alias, hints, sql, aggExcludes, aggTables);
        }
        if (relation instanceof MappingView view) {
            List<MappingSQL> sqls = viewSqls(view);
            return new_View(alias, sqls);
        }
        return null;
    }

    protected List<MappingSQL> viewSqls(MappingView view) {
        return sqls(view.sqls());
    }

    protected List<MappingSQL> sqls(List<MappingSQL> sqls){
        return sqls.stream().map(this::sql).toList();
    }

    protected String tableSchema(MappingTable table) {
        return table.schema();
    }

    protected String tableName(MappingTable table) {
        return table.name();
    }

    protected List<MappingHint> tableHints(MappingTable table) {
        return hints(table.hints());
    }

    protected List<MappingHint> hints(List<MappingHint> hints) {
        return hints.stream().map(this::hint).toList();
    }

    private MappingHint hint(MappingHint hint) {
        String content = hintContent(hint);
        String type = hintType(hint);
        return new_Hint(content, type);
    }

    protected String hintType(MappingHint hint) {
        return hint.type();
    }

    protected String hintContent(MappingHint hint) {
        return hint.content();
    }

    protected MappingHint new_Hint(String content,
                                   String type) {
        return new HintR(content, type);
    };

    protected List<MappingAggTable> tableAggTables(MappingTable table) {
        return aggTables(table.aggTables());
    }

    protected List<MappingAggTable> aggTables(List<MappingAggTable> aggTables) {
        return aggTables.stream().map(this::aggTable).toList();
    }

    private MappingAggTable aggTable(MappingAggTable aggTable) {
        MappingAggColumnName aggFactCount = aggTableAggFactCount(aggTable);
        List<MappingAggColumnName> aggIgnoreColumns = aggTableAggIgnoreColumns(aggTable);
        List<MappingAggForeignKey> aggForeignKeys = aggTableAggForeignKeys(aggTable);
        List<MappingAggMeasure> aggMeasures = aggTableAggMeasures(aggTable);
        List<MappingAggLevel> aggLevels = aggTableAggLevels(aggTable);
        boolean ignorecase = aggTableIgnorecase(aggTable);
        List<MappingAggMeasureFactCount> measuresFactCounts = aggTableMeasuresFactCounts(aggTable);
        if (aggTable instanceof MappingAggName aggName) {
            String name = aggNameName(aggName);
            String approxRowCount = aggNameApproxRowCount(aggName);
            return new_AggName(
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
        if (aggTable instanceof MappingAggPattern aggPattern) {
            List<MappingAggExclude> aggExcludes = aggPatternAggExcludes(aggPattern);
            String pattern = aggPatternPattern(aggPattern);
            return new_AggPattern(pattern,
                aggFactCount,
                aggIgnoreColumns,
                aggForeignKeys,
                aggMeasures,
                aggLevels,
                aggExcludes,
                ignorecase,
                measuresFactCounts);
        }
        return null;
    }

    protected String aggNameApproxRowCount(MappingAggName aggName) {
        return aggName.approxRowCount();
    }

    protected String aggNameName(MappingAggName aggName) {
        return aggName.name();
    };

    protected List<MappingAggMeasureFactCount> aggTableMeasuresFactCounts(MappingAggTable aggTable) {
        return measuresFactCounts(aggTable.measuresFactCounts());
    }

    protected List<MappingAggMeasureFactCount> measuresFactCounts(List<MappingAggMeasureFactCount> aggMeasuresFactCounts) {
        return aggMeasuresFactCounts.stream().map(this::aggMeasuresFactCount).toList();
    }

    private MappingAggMeasureFactCount aggMeasuresFactCount(MappingAggMeasureFactCount mappingAggMeasureFactCount) {
        String factColumn = mappingAggMeasureFactCountFactColumn(mappingAggMeasureFactCount);
        String column = mappingAggMeasureFactCount.column();
        return new_AggMeasureFactCount(factColumn, column);
    }

    protected MappingAggMeasureFactCount new_AggMeasureFactCount(String factColumn, String column) {
        return new AggMeasureFactCountR(factColumn, column);
    }

    protected String mappingAggMeasureFactCountFactColumn(MappingAggMeasureFactCount mappingAggMeasureFactCount) {
        return mappingAggMeasureFactCount.factColumn();
    }

    protected boolean aggTableIgnorecase(MappingAggTable aggTable) {
        return aggTable.ignorecase();
    }

    protected List<MappingAggLevel> aggTableAggLevels(MappingAggTable aggTable) {
        return aggLevels(aggTable.aggLevels());
    }

    protected List<MappingAggLevel> aggLevels(List<MappingAggLevel> aggLevels) {
        return aggLevels.stream().map(this::aggLevel).toList();
    }

    private MappingAggLevel aggLevel(MappingAggLevel aggLevel) {
        String column = aggLevelColumn(aggLevel);
        String name = aggLevelName(aggLevel);
        String ordinalColumn = aggLevelOrdinalColumn(aggLevel);
        String captionColumn = aggLevelCaptionColumn(aggLevel);
        String nameColumn = aggLevelNameColumn(aggLevel);
        Boolean collapsed = aggLevelCollapsed(aggLevel);
        List<MappingAggLevelProperty> properties = aggLevelProperties(aggLevel);
        return new_AggLevel(
            column,
            name,
            ordinalColumn,
            nameColumn,
            captionColumn,
            collapsed,
            properties
        );
    }

    protected List<MappingAggLevelProperty> aggLevelProperties(MappingAggLevel aggLevel) {
        return aggLevelProperties(aggLevel.properties());
    }

    protected List<MappingAggLevelProperty> aggLevelProperties(List<MappingAggLevelProperty> properties) {
        return properties.stream().map(this::aggLevelProperty).toList();
    }

    private MappingAggLevelProperty aggLevelProperty(MappingAggLevelProperty aggLevelProperty) {
        String name = aggLevelPropertyName(aggLevelProperty);
        String column = aggLevelPropertyColumn(aggLevelProperty);
        return new_AggLevelProperty(name, column);
    }

    protected String aggLevelPropertyColumn(MappingAggLevelProperty aggLevelProperty) {
        return aggLevelProperty.column();
    }

    protected String aggLevelPropertyName(MappingAggLevelProperty aggLevelProperty) {
        return aggLevelProperty.name();
    }

    protected MappingAggLevelProperty new_AggLevelProperty(String name, String column) {
        return new AggLevelPropertyR(name, column);
    }

    protected Boolean aggLevelCollapsed(MappingAggLevel aggLevel) {
        return aggLevel.collapsed();
    }

    protected String aggLevelNameColumn(MappingAggLevel aggLevel) {
        return aggLevel.nameColumn();
    }

    protected String aggLevelCaptionColumn(MappingAggLevel aggLevel) {
        return aggLevel.captionColumn();
    }

    protected String aggLevelOrdinalColumn(MappingAggLevel aggLevel) {
        return aggLevel.column();
    }

    protected String aggLevelName(MappingAggLevel aggLevel) {
        return aggLevel.name();
    };

    protected String aggLevelColumn(MappingAggLevel aggLevel) {
        return aggLevel.column();
    }

    protected abstract MappingAggLevel new_AggLevel(String column,
                                           String name,
                                           String ordinalColumn,
                                           String nameColumn,
                                           String captionColumn,
                                           Boolean collapsed,
                                           List<MappingAggLevelProperty> properties);

    protected List<MappingAggMeasure> aggTableAggMeasures(MappingAggTable aggTable){
        return aggMeasures(aggTable.aggMeasures());
    }

    protected List<MappingAggMeasure> aggMeasures(List<MappingAggMeasure> aggMeasures) {
        return aggMeasures.stream().map(this::aggMeasure).toList();
    }

    private MappingAggMeasure aggMeasure(MappingAggMeasure aggMeasure) {
        String column = aggMeasureColumn(aggMeasure);
        String name = aggMeasureName(aggMeasure);
        String rollupType = aggMeasureRollupType(aggMeasure);
        return new_AggMeasure(column, name, rollupType);
    }

    protected abstract MappingAggMeasure new_AggMeasure(String column,
                                               String name,
                                               String rollupType);

    protected String aggMeasureRollupType(MappingAggMeasure aggMeasure) {
        return aggMeasure.rollupType();
    }

    protected String aggMeasureName(MappingAggMeasure aggMeasure) {
        return aggMeasure.name();
    };

    protected String aggMeasureColumn(MappingAggMeasure aggMeasure) {
        return aggMeasure.column();
    }

    protected List<MappingAggForeignKey> aggTableAggForeignKeys(MappingAggTable aggTable) {
        return aggForeignKeys(aggTable.aggForeignKeys());
    }

    protected List<MappingAggForeignKey> aggForeignKeys(List<MappingAggForeignKey> aggForeignKeys) {
        return aggForeignKeys.stream().map(this::aggForeignKey).toList();
    }

    private MappingAggForeignKey aggForeignKey(MappingAggForeignKey aggForeignKey) {
        String factColumn = aggForeignKeyFactColumn(aggForeignKey);
        String aggColumn = aggForeignKeyAggColumn(aggForeignKey);
        return new_AggForeignKey(factColumn, aggColumn);
    }

    protected String aggForeignKeyAggColumn(MappingAggForeignKey aggForeignKey) {
        return aggForeignKey.aggColumn();
    }

    protected String aggForeignKeyFactColumn(MappingAggForeignKey aggForeignKey) {
        return aggForeignKey.factColumn();
    }

    protected abstract MappingAggForeignKey new_AggForeignKey(String factColumn, String aggColumn);

    protected List<MappingAggExclude> aggPatternAggExcludes(MappingAggPattern aggPattern) {
        return aggExcludes(aggPattern.aggExcludes());
    }

    protected String aggPatternPattern(MappingAggPattern aggPattern) {
        return aggPattern.pattern();
    }

    protected List<MappingAggColumnName> aggTableAggIgnoreColumns(MappingAggTable aggTable) {
        return aggColumnNames(aggTable.aggIgnoreColumns());
    }

    protected List<MappingAggColumnName> aggColumnNames(List<MappingAggColumnName> aggColumnNames) {
        return aggColumnNames.stream().map(this::aggColumnName).toList();
    }

    private MappingAggColumnName aggColumnName(MappingAggColumnName aggColumnName) {
        String column = aggColumnNameColumn(aggColumnName);
        return new_AggColumnName(column);
    }

    protected String aggColumnNameColumn(MappingAggColumnName aggColumnName) {
        return aggColumnName.column();
    }

    protected MappingAggColumnName new_AggColumnName(String column) {
        return new AggColumnNameR(column);
    }

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

    protected abstract MappingAggTable new_AggName(String name,
                                          MappingAggColumnName aggFactCount,
                                          List<MappingAggMeasure> aggMeasures,
                                          List<MappingAggColumnName> aggIgnoreColumns,
                                          List<MappingAggForeignKey> aggForeignKeys,
                                          List<MappingAggLevel> aggLevels,
                                          boolean ignorecase,
                                          List<MappingAggMeasureFactCount> measuresFactCounts,
                                          String approxRowCount);

    protected MappingAggColumnName aggTableAggFactCount(MappingAggTable aggTable) {
     //TODO
     return  aggTable.aggFactCount();
    }

    protected List<MappingAggExclude> tableAggExcludes(MappingTable table) {
        return aggExcludes(table.aggExcludes());
    }

    protected List<MappingAggExclude> aggExcludes(List<MappingAggExclude> aggExcludes) {
        return aggExcludes.stream().map(this::aggExclude).toList();
    }

    private MappingAggExclude aggExclude(MappingAggExclude aggExclude) {
        String pattern = aggExcludePattern(aggExclude);
        String name = aggExcludeName(aggExclude);
        boolean ignorecase = aggExcludeIgnorecase(aggExclude);
        return new_AggExclude(
            pattern,
            name,
            ignorecase
        );
    }

    protected boolean aggExcludeIgnorecase(MappingAggExclude aggExclude) {
        return aggExclude.ignorecase();
    }

    protected String aggExcludeName(MappingAggExclude aggExclude) {
        return aggExclude.name();
    };

    protected String aggExcludePattern(MappingAggExclude aggExclude) {
        return aggExclude.pattern();
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

    protected MappingSQL tableSql(MappingTable table) {
        return sql(table.sql());
    }

    protected MappingSQL sql(MappingSQL sql) {
        String content = sqlContent(sql);
        String dialect = sqlDialect(sql);
        return new_SQL(content, dialect);
    }

    protected String sqlDialect(MappingSQL sql) {
        return sql.dialect();
    }

    protected String sqlContent(MappingSQL sql) {
        return sql.content();
    }

    protected abstract MappingSQL new_SQL(String content, String dialect);

    protected List<MappingRow> inlineTableRows(MappingInlineTable inlineTable) {
        return rows(inlineTable.rows());
    }

    protected List<MappingRow> rows(List<MappingRow> rows) {
        return rows.stream().map(this::row).toList();
    }

    private MappingRow row(MappingRow row) {
        List<MappingValue> values = rowValues(row);
        return new_Row(values);
    }

    protected List<MappingValue> rowValues(MappingRow row) {
        return values(row.values());
    }

    protected List<MappingValue> values(List<MappingValue> values) {
        return values.stream().map(this::value).toList();
    }

    private MappingValue value(MappingValue value) {
        String content = valueContent(value);
        String column = valueColumn(value);
        return new_Value(column, content);
    }

    protected abstract MappingValue new_Value(String column, String content);

    protected String valueColumn(MappingValue value) {
        return value.column();
    }

    protected String valueContent(MappingValue value) {
        return value.content();
    };

    protected abstract MappingRow new_Row(List<MappingValue> values);

    protected MappingRelation new_Table(String schema, String name, String alias,
                                        List<MappingHint> hints, MappingSQL sql,
                                        List<MappingAggExclude> aggExcludes, List<MappingAggTable> aggTables) {
        return new TableR(schema, name, alias, hints, sql, aggExcludes, aggTables);
    }

    protected List<MappingColumnDef> inlineTableColumnDefs(MappingInlineTable inlineTable) {
        return columnDefs(inlineTable.columnDefs());
    }

    protected List<MappingColumnDef> columnDefs(List<MappingColumnDef> columnDefs) {
        return columnDefs.stream().map(this::columnDef).toList();
    }

    private MappingColumnDef columnDef(MappingColumnDef columnDef) {
        String name = columnDefName(columnDef);
        TypeEnum type = columnDefType(columnDef);
        return new_ColumnDef(name, type);
    }

    protected abstract MappingColumnDef new_ColumnDef(String name, TypeEnum type);

    protected TypeEnum columnDefType(MappingColumnDef columnDef) {
        return columnDef.type();
    }

    protected String columnDefName(MappingColumnDef columnDef) {
        return columnDef.name();
    }

    protected MappingRelation new_View(String alias,
                                       List<MappingSQL> sqls) {
        return new ViewR(alias, sqls);
    }

    protected abstract MappingRelation new_InlineTable(
        List<MappingColumnDef> columnDefs,
        List<MappingRow> rows, String alias);

    protected String relationAlias(MappingRelation relation) {
        return relation.alias();
    }

    protected List<MappingWritebackTable> cubewritebackTables(MappingCube cube) {
        return cube.writebackTables();
    }

    protected List<MappingDrillThroughAction> cubeDrillThroughActions(MappingCube cube) {
        return cube.drillThroughActions();
    }

    protected List<MappingCalculatedMember> cubeCalculatedMembers(MappingCube cube) {
        return cube.calculatedMembers();
    }

    protected List<MappingMeasure> cubeMeasures(MappingCube cube) {
        return cube.measures();
    }

    protected List<MappingCubeDimension> cubeDimensionUsageOrDimensions(MappingCube cube) {
        return cube.dimensionUsageOrDimensions();
    }

    protected MappingLevel level(MappingLevel level) {
        List<MappingAnnotation> annotations = levelAnnotations(level);
        MappingExpressionView keyExpression = levelKeyExpression(level);
        MappingExpressionView nameExpression = levelNameExpression(level);
        MappingExpressionView captionExpression = levelCaptionExpression(level);
        MappingExpressionView ordinalExpression = levelOrdinalExpression(level);
        MappingExpressionView parentExpression = levelParentExpression(level);
        MappingClosure closure = levelClosure(level);
        List<MappingProperty> properties = levelProperties(level);
        String approxRowCount = levelApproxRowCount(level);
        String name = levelName(level);
        String table = levelTable(level);
        String column = levelColumn(level);
        String nameColumn = levelNameColumn(level);
        String ordinalColumn = levelOrdinalColumn(level);
        String parentColumn = levelParentColumn(level);
        String nullParentValue = levelNullParentValue(level);
        TypeEnum type = levelType(level);
        boolean uniqueMembers = levelUniqueMembers(level);
        LevelTypeEnum levelType = levelLevelType(level);
        HideMemberIfEnum hideMemberIf = levelHideMemberIf(level);
        String formatter = levelFormatter(level);
        String caption = levelCaption(level);
        String description = levelDescription(level);
        String captionColumn = levelCaptionColumn(level);
        boolean visible = levelVisible(level);
        InternalTypeEnum internalType = levelInternalType(level);;
        MappingElementFormatter memberFormatter = levelMemberFormatter(level);
        return new_Level(
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
            memberFormatter);
    }

    protected MappingElementFormatter levelMemberFormatter(MappingLevel level){
        return memberFormatter(level.memberFormatter());
    }

    protected MappingElementFormatter memberFormatter(MappingElementFormatter formatter) {
        String className = formatterClassName(formatter);
        MappingScript script = formatterScript(formatter);
        return new_ElementFormatter(className, script);
    }

    protected MappingScript formatterScript(MappingElementFormatter formatter) {
        return script(formatter.script());
    }

    protected String formatterClassName(MappingElementFormatter formatter) {
        return formatter.className();
    }

    protected abstract MappingElementFormatter new_ElementFormatter(String className, MappingScript script);

    protected InternalTypeEnum levelInternalType(MappingLevel level){
        return level.internalType();
    }

    protected boolean levelVisible(MappingLevel level){
        return level.visible();
    }

    protected String levelCaptionColumn(MappingLevel level){
        return level.column();
    }

    protected String levelDescription(MappingLevel level){
        return level.description();
    }

    protected String levelCaption(MappingLevel level){
        return level.caption();
    }

    protected String levelFormatter(MappingLevel level){
        return level.formatter();
    }

    protected HideMemberIfEnum levelHideMemberIf(MappingLevel level){
        return level.hideMemberIf();
    }

    protected LevelTypeEnum levelLevelType(MappingLevel level){
        return level.levelType();
    }

    protected boolean levelUniqueMembers(MappingLevel level){
        return level.uniqueMembers();
    }

    protected TypeEnum levelType(MappingLevel level){
        return level.type();
    }

    protected String levelNullParentValue(MappingLevel level){
        return level.nullParentValue();
    }

    protected String levelParentColumn(MappingLevel level){
        return level.parentColumn();
    }

    protected String levelOrdinalColumn(MappingLevel level){
        return level.ordinalColumn();
    }

    protected String levelNameColumn(MappingLevel level){
        return level.nameColumn();
    }

    protected String levelColumn(MappingLevel level){
        return level.column();
    }

    protected String levelTable(MappingLevel level){
        return level.table();
    }

    protected String levelName(MappingLevel level){
        return level.name();
    }

    protected String levelApproxRowCount(MappingLevel level){
        return level.approxRowCount();
    }

    protected List<MappingProperty> levelProperties(MappingLevel level){
        return properties(level.properties());
    }

    protected List<MappingProperty> properties(List<MappingProperty> properties) {
        return properties.stream().map(this::property).toList();
    }

    private MappingProperty property(MappingProperty property) {
        String name = propertyName(property);
        String column = propertyColumn(property);
        PropertyTypeEnum type = propertyType(property);
        String formatter = propertyFormatter(property);
        String caption = propertyCaption(property);
        String description = propertyDescription(property);
        boolean dependsOnLevelValue = propertyDependsOnLevelValue(property);
        MappingElementFormatter propertyFormatter = propertyPropertyFormatter(property);

        return new_Property(
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

    protected MappingElementFormatter propertyPropertyFormatter(MappingProperty property) {
        return elementFormatter(property.propertyFormatter());
    }

    protected MappingElementFormatter elementFormatter(MappingElementFormatter elementFormatter) {
        String className = elementFormatterClassName(elementFormatter);
        MappingScript script = elementFormatterScript(elementFormatter);
        return new_ElementFormatter(className, script);
    }

    protected MappingScript elementFormatterScript(MappingElementFormatter elementFormatter) {
        return script(elementFormatter.script());
    }

    protected String elementFormatterClassName(MappingElementFormatter elementFormatter) {
        return elementFormatter.className();
    }

    protected boolean propertyDependsOnLevelValue(MappingProperty property) {
        return property.dependsOnLevelValue();
    }

    protected String propertyDescription(MappingProperty property) {
        return property.description();
    };

    protected String propertyCaption(MappingProperty property) {
        return property.caption();
    }

    protected String propertyFormatter(MappingProperty property) {
        return property.formatter();
    }

    protected PropertyTypeEnum propertyType(MappingProperty property) {
        return property.type();
    }

    protected String propertyColumn(MappingProperty property) {
        return property.column();
    }

    protected String propertyName(MappingProperty property) {
        return property.name();
    }

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
    };

    protected MappingClosure levelClosure(MappingLevel level){
        return closure(level.closure());
    }

    protected MappingClosure closure(MappingClosure closure) {
        //TODO
        return closure;
    }

    protected MappingExpressionView levelParentExpression(MappingLevel level){
        return expressionView(level.parentExpression());
    }

    protected MappingExpressionView expressionView(MappingExpression expression) {
        //TODO
        return (MappingExpressionView)expression;
    };

    protected MappingExpressionView levelOrdinalExpression(MappingLevel level){
        return expressionView(level.ordinalExpression());
    }

    protected MappingExpressionView levelCaptionExpression(MappingLevel level){
        return expressionView(level.captionExpression());
    }

    protected MappingExpressionView levelNameExpression(MappingLevel level){
        return expressionView(level.nameExpression());
    }

    protected MappingExpressionView levelKeyExpression(MappingLevel level){
        return expressionView(level.keyExpression());
    }

    protected List<MappingAnnotation> levelAnnotations(MappingLevel level) {
        return annotations(level.annotations());
    }

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

    protected MappingRelationOrJoin hierarchyRelation(MappingHierarchy hierarchy) {
        // TOTO
        return hierarchy.relation();
    }

    protected List<MappingUserDefinedFunction> schemaUserDefinedFunctions(MappingSchema mappingSchemaOriginal) {
        return mappingSchemaOriginal.userDefinedFunctions();
    }

}
