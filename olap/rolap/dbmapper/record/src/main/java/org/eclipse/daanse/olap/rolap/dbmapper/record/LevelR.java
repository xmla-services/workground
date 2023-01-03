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
package org.eclipse.daanse.olap.rolap.dbmapper.record;

import org.eclipse.daanse.olap.rolap.dbmapper.api.ElementFormatter;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Level;
import org.eclipse.daanse.olap.rolap.dbmapper.api.enums.HideMemberIfEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.api.enums.InternalTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.api.enums.LevelTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.api.enums.TypeEnum;

import java.util.List;

public record LevelR(String name,
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
                     List<AnnotationR> annotations,
                     ExpressionViewR keyExpression,
                     ExpressionViewR nameExpression,
                     ExpressionViewR captionExpression,
                     ExpressionViewR ordinalExpression,
                     ExpressionViewR parentExpression,
                     ClosureR closure,
                     List<PropertyR> property,
                     boolean visible,
                     InternalTypeEnum internalType,
                     ElementFormatter memberFormatter
                     )
        implements Level {

}
