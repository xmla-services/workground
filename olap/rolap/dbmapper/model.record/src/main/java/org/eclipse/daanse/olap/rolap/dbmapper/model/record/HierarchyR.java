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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Hierarchy;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.RelationOrJoin;

public record HierarchyR(String name,
                         String caption,
                         String description,
                         List<AnnotationR> annotations,
                         TableR table,
                         ViewR view,
                         JoinR join,
                         InlineTableR inlineTable,
                         List<LevelR> level,
                         List<MemberReaderParameterR> memberReaderParameter,
                         boolean hasAll,
                         String allMemberName,
                         String allMemberCaption,
                         String allLevelName,
                         String primaryKey,
                         String primaryKeyTable,
                         String defaultMember,
                         String memberReaderClass,
                         String uniqueKeyLevelName,
                         boolean visible,
                         String displayFolder,
                         RelationOrJoin relation,
                         String origin
                         )
        implements Hierarchy {

}
