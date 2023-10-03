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
package org.eclipse.daanse.olap.rolap.dbmapper.model.record;

import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAnnotation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCalculatedMember;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCalculatedMemberProperty;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCellFormatter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingFormula;

public record CalculatedMemberR(String name,
                                String description,
                                List<MappingAnnotation> annotations,
                                String caption,
                                Boolean visible,
                                String formatString,
                                String dimension,
                                String displayFolder,
                                String formula,
                                List<MappingCalculatedMemberProperty> calculatedMemberProperties,
                                String hierarchy,
                                String parent,
                                MappingCellFormatter cellFormatter,
                                MappingFormula formulaElement)
    implements MappingCalculatedMember {

    public CalculatedMemberR(
        String name,
        String description,
        List<MappingAnnotation> annotations,
        String caption,
        Boolean visible,
        String formatString,
        String dimension,
        String displayFolder,
        String formula,
        List<MappingCalculatedMemberProperty> calculatedMemberProperties,
        String hierarchy,
        String parent,
        MappingCellFormatter cellFormatter,
        MappingFormula formulaElement
    ) {
        this.name = name;
        this.description = description;
        this.annotations = annotations;
        this.caption = caption;
        this.visible = visible == null ? Boolean.FALSE : visible;
        this.formatString = formatString;
        this.dimension = dimension;
        this.displayFolder = displayFolder;
        this.formula = formula;
        this.calculatedMemberProperties = calculatedMemberProperties == null ? List.of() : calculatedMemberProperties;
        this.hierarchy = hierarchy;
        this.parent = parent;
        this.cellFormatter = cellFormatter;
        this.formulaElement = formulaElement;

    }

}
