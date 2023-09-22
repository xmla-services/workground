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
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingHierarchy;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingInlineTable;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingJoin;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingLevel;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MemberReaderParameter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.RelationOrJoin;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Table;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.View;

public record HierarchyR(String name,
                         String caption,
                         String description,
                         List<MappingAnnotation> annotations,
                         Table table,
                         View view,
                         MappingJoin join,
                         MappingInlineTable inlineTable,
                         List<MappingLevel> levels,
                         List<MemberReaderParameter> memberReaderParameters,
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
        implements MappingHierarchy {

}
