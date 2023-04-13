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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Annotation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.ElementFormatter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.ExpressionView;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Level;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Property;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.HideMemberIfEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.InternalTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.LevelTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.TypeEnum;

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
                     List<Annotation> annotations,
                     ExpressionView keyExpression,
                     ExpressionView nameExpression,
                     ExpressionView captionExpression,
                     ExpressionView ordinalExpression,
                     ExpressionView parentExpression,
                     ClosureR closure,
                     List<Property> property,
                     boolean visible,
                     InternalTypeEnum internalType,
                     ElementFormatter memberFormatter
                     )
        implements Level {

}
