/*
 * Copyright (c) 2023 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   SmartCity Jena - initial
 *   Stefan Bischof (bipolis.org) - initial
 */
package org.eclipse.daanse.xmla.ws.jakarta.basic;

import org.eclipse.daanse.xmla.api.xmla.Annotation;
import org.eclipse.daanse.xmla.api.xmla.MiningModel;
import org.eclipse.daanse.xmla.api.xmla.MiningStructure;
import org.eclipse.daanse.xmla.api.xmla.MiningStructureColumn;
import org.eclipse.daanse.xmla.api.xmla.MiningStructurePermission;
import org.eclipse.daanse.xmla.model.record.xmla.MiningStructureColumnR;
import org.eclipse.daanse.xmla.model.record.xmla.MiningStructurePermissionR;
import org.eclipse.daanse.xmla.model.record.xmla.MiningStructureR;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static org.eclipse.daanse.xmla.ws.jakarta.basic.AnnotationConvertor.convertAnnotationList;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.BindingConvertor.convertBinding;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.CommandConvertor.convertErrorConfiguration;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.ConvertorUtil.convertToInstant;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.CubeConvertor.convertTranslationList;

public class MiningStructureConvertor {

    public static MiningStructure convertMiningStructure(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.MiningStructure miningStructure) {
        if (miningStructure != null) {
            return new MiningStructureR(miningStructure.getName(),
                miningStructure.getID(),
                convertToInstant(miningStructure.getCreatedTimestamp()),
                convertToInstant(miningStructure.getLastSchemaUpdate()),
                miningStructure.getDescription(),
                convertAnnotationList(miningStructure.getAnnotations() == null ? null :
                    miningStructure.getAnnotations().getAnnotation()),
                convertBinding(miningStructure.getSource()),
                convertToInstant(miningStructure.getLastProcessed()),
                convertTranslationList(miningStructure.getTranslations() == null ? null :
                    miningStructure.getTranslations().getTranslation()),
                miningStructure.getLanguage(),
                miningStructure.getCollation(),
                convertErrorConfiguration(miningStructure.getErrorConfiguration()),
                miningStructure.getCacheMode(),
                miningStructure.getHoldoutMaxPercent(),
                miningStructure.getHoldoutMaxCases(),
                miningStructure.getHoldoutSeed(),
                miningStructure.getHoldoutActualSize(),
                convertMiningStructureColumnList(miningStructure.getColumns() == null ? null :
                    miningStructure.getColumns().getColumn()),
                miningStructure.getState(),
                convertMiningStructurePermissionList(miningStructure.getMiningStructurePermissions() == null ? null :
                    miningStructure.getMiningStructurePermissions().getMiningStructurePermission()),
                convertMiningModelList(miningStructure.getMiningModels() == null ? null :
                    miningStructure.getMiningModels().getMiningModel()));
        }
        return null;
    }

    private static List<MiningModel> convertMiningModelList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.MiningModel> list) {
        if (list != null) {
            return list.stream().map(MiningModelConvertor::convertMiningModel).collect(Collectors.toList());
        }
        return null;
    }

    private static List<MiningStructurePermission> convertMiningStructurePermissionList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.MiningStructurePermission> list) {
        if (list != null) {
            return list.stream().map(MiningStructureConvertor::convertMiningStructurePermission).collect(Collectors.toList());
        }
        return null;
    }

    private static MiningStructurePermission convertMiningStructurePermission(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.MiningStructurePermission miningStructurePermission) {
        if (miningStructurePermission != null) {
            return new MiningStructurePermissionR(miningStructurePermission.isAllowDrillThrough(),
                miningStructurePermission.getWrite(),
                miningStructurePermission.getName(),
                miningStructurePermission.getID(),
                convertToInstant(miningStructurePermission.getCreatedTimestamp()),
                convertToInstant(miningStructurePermission.getLastSchemaUpdate()),
                miningStructurePermission.getDescription(),
                convertAnnotationList(miningStructurePermission.getAnnotations() == null ? null : miningStructurePermission.getAnnotations().getAnnotation()),
                miningStructurePermission.getRoleID(),
                miningStructurePermission.isProcess(),
                miningStructurePermission.getReadDefinition(),
                miningStructurePermission.getRead());
        }
        return null;
    }

    private static List<MiningStructureColumn> convertMiningStructureColumnList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.MiningStructureColumn> list) {
        if (list != null) {
            return list.stream().map(MiningStructureConvertor::convertMiningStructureColumn).collect(Collectors.toList());
        }
        return null;
    }

    private static MiningStructureColumn convertMiningStructureColumn(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.MiningStructureColumn miningStructureColumn) {
        if (miningStructureColumn != null) {
            return new MiningStructureColumnR();
        }
        return null;
    }

}
