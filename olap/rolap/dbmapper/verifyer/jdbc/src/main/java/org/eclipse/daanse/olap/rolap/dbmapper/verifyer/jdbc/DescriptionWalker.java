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

import org.eclipse.daanse.olap.rolap.dbmapper.api.Action;
import org.eclipse.daanse.olap.rolap.dbmapper.api.CalculatedMember;
import org.eclipse.daanse.olap.rolap.dbmapper.api.CalculatedMemberProperty;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Cube;
import org.eclipse.daanse.olap.rolap.dbmapper.api.CubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.api.DrillThroughAction;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Hierarchy;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Measure;
import org.eclipse.daanse.olap.rolap.dbmapper.api.NamedSet;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Parameter;
import org.eclipse.daanse.olap.rolap.dbmapper.api.PrivateDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Property;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.api.SharedDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.api.VirtualCube;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Cause;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.DescriptionVerifierConfig;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.VerificationResult;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class DescriptionWalker extends AbstractSchemaWalker{
    private DescriptionVerifierConfig config;

    private Consumer<Cube> cubeConsumer = this::checkCubeDescription;
    private Consumer<VirtualCube> virtualCubeConsumer = this::checkVirtualCube;
    private Consumer<CubeDimension> dimensionConsumer = this::checkDimension;
    private Consumer<CalculatedMemberProperty> calculatedMemberPropertyConsumer = this::checkCalculatedMemberProperty;
    private Consumer<CalculatedMember> calculatedMemberConsumer = this::checkCalculatedMember;
    private Consumer<Measure> measureConsumer = this::checkMeasure;
    private Consumer<Hierarchy> hierarchyConsumer = this::checkHierarchy;
    private Consumer<org.eclipse.daanse.olap.rolap.dbmapper.api.Level> levelConsumer = this::checkLevel;
    private Consumer<Property> propertyConsumer = this::checkProperty;
    private Consumer<NamedSet> namedSetConsumer = this::checkNamedSet;
    private Consumer<Parameter> parameterConsumer = this::checkParameter;
    private Consumer<DrillThroughAction> drillThroughActionConsumer = this::checkDrillThroughAction;
    private Consumer<Action> actionConsumer = this::checkAction;
    private Consumer<SharedDimension> sharedDimensionConsumer = this::checkSharedDimension;

    public DescriptionWalker(DescriptionVerifierConfig config) {
        this.config = config;
    }

    public Collection<? extends VerificationResult> checkSchema(Schema schema) {
        Level lavel = config.schema();
        if (lavel  != null && (schema.description() == null || schema.description().isEmpty())) {
            results.add(new VerificationResultR("Schema",
                "Schema must contain description",
                lavel,
                Cause.SCHEMA));
        }

        checkList(schema.cube(), cubeConsumer);

        checkList(schema.virtualCube(), virtualCubeConsumer);

        checkList(schema.dimension(), dimensionConsumer);

        checkList(schema.namedSet(), namedSetConsumer);

        checkList(schema.parameter(), parameterConsumer);

        return results;
    }

    private void checkDimension(CubeDimension dimension) {
        Level lavel = config.privateDimension();
        if (lavel  != null && (dimension.description() == null || dimension.description().isEmpty())) {
            results.add(new VerificationResultR("Dimension",
                "Dimension must contain description",
                lavel,
                Cause.SCHEMA));
        }
        if (dimension instanceof PrivateDimension) {
            checkList(((PrivateDimension) dimension).hierarchy(), hierarchyConsumer);
        }
    }

    private void checkVirtualCube(VirtualCube virtualCube) {
        Level lavel = config.virtualCube();
        if (lavel  != null && (virtualCube.description() == null || virtualCube.description().isEmpty())) {
            results.add(new VerificationResultR("Virtual Cube",
                "Virtual Cube must contain description",
                lavel,
                Cause.SCHEMA));
        }

        checkList(virtualCube.virtualCubeDimension(), dimensionConsumer);

        checkList(virtualCube.calculatedMember(), calculatedMemberConsumer);

        checkList(virtualCube.namedSet(), namedSetConsumer);
    }

    private void checkCubeDescription(Cube cube) {
        Level lavel = config.cube();
        if (lavel  != null && (cube.description() == null || cube.description().isEmpty())) {
            results.add(new VerificationResultR("Cube",
                "Cube must contain description",
                lavel,
                Cause.SCHEMA));
        }

        checkList(cube.calculatedMember(), calculatedMemberConsumer);

        checkList(cube.dimensionUsageOrDimension(), dimensionConsumer);

        checkList(cube.measure(), measureConsumer);

        if (cube.action() != null && cube.action().iterator() != null) {
            cube.action().iterator().forEachRemaining(actionConsumer);
        }

        checkList(cube.namedSet(), namedSetConsumer);

        checkList(cube.drillThroughAction(), drillThroughActionConsumer);
    }

    private void checkMeasure(Measure measure) {
        Level lavel = config.measure();
        if (lavel  != null && (measure.description() == null || measure.description().isEmpty())) {
            results.add(new VerificationResultR("Measure",
                "Measure must contain description",
                lavel,
                Cause.SCHEMA));
        }
        checkList(measure.calculatedMemberProperty(), calculatedMemberPropertyConsumer);
    }

    private void checkCalculatedMemberProperty(CalculatedMemberProperty calculatedMemberProperty) {
        Level lavel = config.calculatedMemberProperty();
        if (lavel  != null && (calculatedMemberProperty.description() == null || calculatedMemberProperty.description().isEmpty())) {
            results.add(new VerificationResultR("CalculatedMemberProperty",
                "CalculatedMemberProperty must contain description",
                lavel,
                Cause.SCHEMA));
        }
    }

    private void checkCalculatedMember(CalculatedMember calculatedMember) {
        Level lavel = config.calculatedMember();
        if (lavel  != null && (calculatedMember.description() == null || calculatedMember.description().isEmpty())) {
            results.add(new VerificationResultR("CalculatedMember",
                "CalculatedMember must contain description",
                lavel,
                Cause.SCHEMA));
        }

        checkList(calculatedMember.calculatedMemberProperty(), calculatedMemberPropertyConsumer);
    }

    private void checkHierarchy(Hierarchy hierarchy) {
        Level lavel = config.hierarchy();
        if (lavel  != null && (hierarchy.description() == null || hierarchy.description().isEmpty())) {
            results.add(new VerificationResultR("Hierarchy",
                "Hierarchy must contain description",
                lavel,
                Cause.SCHEMA));
        }
    }

    private void checkLevel(final org.eclipse.daanse.olap.rolap.dbmapper.api.Level l) {
        Level lavel = config.level();
        if (lavel  != null && (l.description() == null || l.description().isEmpty())) {
            results.add(new VerificationResultR("Level",
                "Level must contain description",
                lavel,
                Cause.SCHEMA));
        }
        checkList(l.property(), propertyConsumer);
    }

    private void checkAction(final Action action) {
        Level lavel = config.action();
        if (lavel  != null && (action.description() == null || action.description().isEmpty())) {
            results.add(new VerificationResultR("Action",
                "Action must contain description",
                lavel,
                Cause.SCHEMA));
        }
    }

    private void checkSharedDimension(final SharedDimension sharedDimension) {
        Level lavel = config.sharedDimension();
        if (lavel  != null && (sharedDimension.description() == null || sharedDimension.description().isEmpty())) {
            results.add(new VerificationResultR("SharedDimension",
                "SharedDimension must contain description",
                lavel,
                Cause.SCHEMA));
        }
    }

    private void checkProperty(final Property property) {
        Level lavel = config.property();
        if (lavel  != null && (property.description() == null || property.description().isEmpty())) {
            results.add(new VerificationResultR("Property",
                "Property must contain description",
                lavel,
                Cause.SCHEMA));
        }
    }
    private void checkNamedSet(final NamedSet namedSet) {
        Level lavel = config.namedSet();
        if (lavel  != null && (namedSet.description() == null || namedSet.description().isEmpty())) {
            results.add(new VerificationResultR("NamedSet",
                "NamedSet must contain description",
                lavel,
                Cause.SCHEMA));
        }
    }
    private void checkParameter(final Parameter parameter) {
        Level lavel = config.parameter();
        if (lavel  != null && (parameter.description() == null || parameter.description().isEmpty())) {
            results.add(new VerificationResultR("Parameter",
                "Parameter must contain description",
                lavel,
                Cause.SCHEMA));
        }
    }
    private void checkDrillThroughAction(final DrillThroughAction drillThroughAction) {
        Level lavel = config.drillThroughAction();
        if (lavel  != null && (drillThroughAction.description() == null || drillThroughAction.description().isEmpty())) {
            results.add(new VerificationResultR("DrillThroughAction",
                "DrillThroughAction must contain description",
                lavel,
                Cause.SCHEMA));
        }
    }

    private <T> void checkList(List<? extends T> list, Consumer fun) {
        if (list != null) {
            list.stream().forEach(it -> fun.accept(it));
        }
    }
}
