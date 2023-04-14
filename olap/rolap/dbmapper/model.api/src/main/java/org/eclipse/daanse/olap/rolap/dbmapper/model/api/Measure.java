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
package org.eclipse.daanse.olap.rolap.dbmapper.model.api;

import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.MeasureDataTypeEnum;

public interface Measure {

    List<Annotation> annotations();

    ExpressionView measureExpression();

    List<CalculatedMemberProperty> calculatedMemberProperties();

    String name();

    String column();

    MeasureDataTypeEnum datatype();

    String formatString();

    String aggregator();

    String formatter();

    String caption();

    String description();

    boolean visible();

    String displayFolder();

    ElementFormatter cellFormatter();

    String backColor();
}
