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
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingLevel;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingMemberReaderParameter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingRelationOrJoin;

public record HierarchyR(String name,
		String description,
		List<MappingAnnotation> annotations,
                         String caption,
                         boolean visible,
                         List<MappingLevel> levels,
                         List<MappingMemberReaderParameter> memberReaderParameters,
                         boolean hasAll,
                         String allMemberName,
                         String allMemberCaption,
                         String allLevelName,
                         String primaryKey,
                         String primaryKeyTable,
                         String defaultMember,
                         String memberReaderClass,
                         String uniqueKeyLevelName,
                         String displayFolder,
                         MappingRelationOrJoin relation,
                         String origin
                         )
        implements MappingHierarchy {

}
