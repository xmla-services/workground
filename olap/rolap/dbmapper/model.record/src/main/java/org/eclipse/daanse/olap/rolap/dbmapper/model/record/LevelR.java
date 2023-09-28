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
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingClosure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingElementFormatter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingExpressionView;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingLevel;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingProperty;
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
                     )
        implements MappingLevel {

}
