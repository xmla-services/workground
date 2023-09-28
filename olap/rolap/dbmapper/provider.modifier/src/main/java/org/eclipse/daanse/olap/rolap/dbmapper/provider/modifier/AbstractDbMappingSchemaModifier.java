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
    };

    protected boolean virtualCubeEnabled(MappingVirtualCube virtualCube) {
        return virtualCube.enabled();
    }

    protected List<MappingNamedSet> virtualCubeNamedSet(MappingVirtualCube virtualCube) {
        //TODO
        return virtualCube.namedSets();
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

    protected  MappingFormula calculatedMemberFormulaElement(MappingCalculatedMember calculatedMember) {
        return calculatedMember.formulaElement();
        //TODO
    }

    protected MappingCellFormatter calculatedMemberCellFormatter(MappingCalculatedMember calculatedMember) {
        // TODO
        return calculatedMember.cellFormatter();
    }

    protected  String calculatedMemberParent(MappingCalculatedMember calculatedMember) {
        return calculatedMember.parent();
    }

    protected  String calculatedMemberHierarchy(MappingCalculatedMember calculatedMember) {
        return calculatedMember.hierarchy();
    }

    protected  List<MappingCalculatedMemberProperty> calculatedMemberCalculatedMemberProperties(
        MappingCalculatedMember calculatedMember) {
        //TODO
        return calculatedMember.calculatedMemberProperties();
    }

    protected  String calculatedMemberFormula(MappingCalculatedMember calculatedMember) {
        return calculatedMember.formula();
    }

    protected  List<MappingAnnotation> calculatedMemberAnnotations(MappingCalculatedMember calculatedMember){
        return annotations(calculatedMember.annotations());
    }

    protected  String calculatedMemberDisplayFolder(MappingCalculatedMember calculatedMember) {
        return calculatedMember.displayFolder();
    }

    protected  boolean calculatedMemberVisible(MappingCalculatedMember calculatedMember) {
        return calculatedMember.visible();
    }

    protected  String calculatedMemberDimension(MappingCalculatedMember calculatedMember) {
        return calculatedMember.dimension();
    }

    protected  String calculatedMemberDescription(MappingCalculatedMember calculatedMember) {
        return calculatedMember.description();
    }

    protected  String calculatedMemberCaption(MappingCalculatedMember calculatedMember) {
        return calculatedMember.caption();
    }

    protected  String calculatedMemberFormatString(MappingCalculatedMember calculatedMember) {
        return calculatedMember.formatString();
    }

    protected  String calculatedMemberName(MappingCalculatedMember calculatedMember) {
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

    // TODO
    protected MappingNamedSet namedSets(MappingNamedSet mappingNamedSet) {
        return mappingNamedSet;
    }

    protected MappingUnion union(MappingUnion union) {
        return union;
    }

    protected MappingSchemaGrant grant(MappingSchemaGrant grant) {
        return grant;
    }

    protected MappingRelation cubeFact(MappingCube cube) {
        return cube.fact();
        // TODO
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

        // TODO:copy
        return level;
    }

    protected MappingRelationOrJoin hierarchyRelation(MappingHierarchy hierarchy) {
        // TOTO
        return hierarchy.relation();
    }

    protected List<MappingUserDefinedFunction> schemaUserDefinedFunctions(MappingSchema mappingSchemaOriginal) {
        return mappingSchemaOriginal.userDefinedFunctions();
    }

}
