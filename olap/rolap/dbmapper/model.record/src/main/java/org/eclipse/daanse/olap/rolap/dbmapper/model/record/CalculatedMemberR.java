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
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingFormula;

public record CalculatedMemberR(String name,
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
                                CellFormatterR cellFormatter,
                                MappingFormula formulaElement)
        implements MappingCalculatedMember {

}
