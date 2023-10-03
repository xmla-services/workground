/*
 * Copyright (c) 0 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License .0
 * which is available at https://www.eclipse.org/legal/epl-.0/
 *
 * SPDX-License-Identifier: EPL-.0
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
                         Boolean visible,
                         List<MappingLevel> levels,
                         List<MappingMemberReaderParameter> memberReaderParameters,
                         Boolean hasAll,
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

    public HierarchyR(
        String name,
        String description,
        List<MappingAnnotation> annotations,
        String caption,
        Boolean visible,
        List<MappingLevel> levels,
        List<MappingMemberReaderParameter> memberReaderParameters,
        Boolean hasAll,
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
    ) {
        this.name = name;
        this.description = description;
        this.annotations = annotations;
        this.caption = caption;
        this.visible = visible == null ? Boolean.TRUE : visible;
        this.levels = levels == null ? List.of() : levels;
        this.memberReaderParameters = memberReaderParameters == null ? List.of() : memberReaderParameters;
        this.hasAll = hasAll == null ? Boolean.FALSE : hasAll;//TODO: docs sais none default -null
        this.allMemberName = allMemberName;
        this.allMemberCaption = allMemberCaption;
        this.allLevelName = allLevelName;
        this.primaryKey = primaryKey;
        this.primaryKeyTable = primaryKeyTable;
        this.defaultMember = defaultMember;
        this.memberReaderClass = memberReaderClass;
        this.uniqueKeyLevelName = uniqueKeyLevelName;
        this.displayFolder = displayFolder;
        this.relation = relation;
        this.origin = origin;

    }

}
