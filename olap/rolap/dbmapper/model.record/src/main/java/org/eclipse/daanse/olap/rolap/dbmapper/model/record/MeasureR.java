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
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCalculatedMemberProperty;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingElementFormatter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingExpressionView;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.MeasureDataTypeEnum;

public record MeasureR(String name,
		String description,
		List<MappingAnnotation> annotations,
		String caption,
		boolean visible,
                       String column,
                       MeasureDataTypeEnum datatype,
                       String formatString,
                       String aggregator,
                       String formatter,
                       String displayFolder,
                       MappingExpressionView measureExpression,
                       List<MappingCalculatedMemberProperty> calculatedMemberProperties,
                       MappingElementFormatter cellFormatter,
                       String backColor
                       )
        implements MappingMeasure {

}
