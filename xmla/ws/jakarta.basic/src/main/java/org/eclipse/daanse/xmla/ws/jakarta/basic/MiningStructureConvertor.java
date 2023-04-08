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

import static org.eclipse.daanse.xmla.ws.jakarta.basic.AnnotationConvertor.convertAnnotationList;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.BindingConvertor.convertBinding;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.CommandConvertor.convertErrorConfiguration;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.ConvertorUtil.convertToInstant;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.CubeConvertor.convertMeasureGroupBinding;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.CubeConvertor.convertTranslationList;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.DataItemConvertor.convertDataItem;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.DataItemConvertor.convertDataItemList;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.MiningModelConvertor.convertMiningModelingFlagList;

import java.util.List;
import java.util.Optional;

import org.eclipse.daanse.xmla.api.xmla.MiningModel;
import org.eclipse.daanse.xmla.api.xmla.MiningStructure;
import org.eclipse.daanse.xmla.api.xmla.MiningStructureColumn;
import org.eclipse.daanse.xmla.api.xmla.MiningStructurePermission;
import org.eclipse.daanse.xmla.api.xmla.ReadDefinitionEnum;
import org.eclipse.daanse.xmla.api.xmla.ReadWritePermissionEnum;
import org.eclipse.daanse.xmla.model.record.xmla.MiningStructurePermissionR;
import org.eclipse.daanse.xmla.model.record.xmla.MiningStructureR;
import org.eclipse.daanse.xmla.model.record.xmla.ScalarMiningStructureColumnR;
import org.eclipse.daanse.xmla.model.record.xmla.TableMiningStructureColumnR;

public class MiningStructureConvertor {

	private MiningStructureConvertor() {
	}

    public static MiningStructure convertMiningStructure(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.MiningStructure miningStructure) {
        if (miningStructure != null) {
            return new MiningStructureR(miningStructure.getName(),
                Optional.ofNullable(miningStructure.getID()),
                Optional.ofNullable(convertToInstant(miningStructure.getCreatedTimestamp())),
                Optional.ofNullable(convertToInstant(miningStructure.getLastSchemaUpdate())),
                Optional.ofNullable(miningStructure.getDescription()),
                Optional.ofNullable(convertAnnotationList(miningStructure.getAnnotations() == null ? null :
                    miningStructure.getAnnotations().getAnnotation())),
                Optional.ofNullable(convertBinding(miningStructure.getSource())),
                Optional.ofNullable(convertToInstant(miningStructure.getLastProcessed())),
                Optional.ofNullable(convertTranslationList(miningStructure.getTranslations() == null ? null :
                    miningStructure.getTranslations().getTranslation())),
                Optional.ofNullable(miningStructure.getLanguage()),
                Optional.ofNullable(miningStructure.getCollation()),
                Optional.ofNullable(convertErrorConfiguration(miningStructure.getErrorConfiguration())),
                Optional.ofNullable(miningStructure.getCacheMode()),
                Optional.ofNullable(miningStructure.getHoldoutMaxPercent()),
                Optional.ofNullable(miningStructure.getHoldoutMaxCases()),
                Optional.ofNullable(miningStructure.getHoldoutSeed()),
                Optional.ofNullable(miningStructure.getHoldoutActualSize()),
                convertMiningStructureColumnList(miningStructure.getColumns() == null ? null :
                    miningStructure.getColumns().getColumn()),
                Optional.ofNullable(miningStructure.getState()),
                Optional.ofNullable(convertMiningStructurePermissionList(miningStructure.getMiningStructurePermissions() == null ? null :
                    miningStructure.getMiningStructurePermissions().getMiningStructurePermission())),
                Optional.ofNullable(convertMiningModelList(miningStructure.getMiningModels() == null ? null :
                    miningStructure.getMiningModels().getMiningModel())));
        }
        return null;
    }

    private static List<MiningModel> convertMiningModelList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.MiningModel> list) {
        if (list != null) {
            return list.stream().map(MiningModelConvertor::convertMiningModel).toList();
        }
        return List.of();
    }

    private static List<MiningStructurePermission> convertMiningStructurePermissionList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.MiningStructurePermission> list) {
        if (list != null) {
            return list.stream().map(MiningStructureConvertor::convertMiningStructurePermission).toList();
        }
        return List.of();
    }

    private static MiningStructurePermission convertMiningStructurePermission(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.MiningStructurePermission miningStructurePermission) {
        if (miningStructurePermission != null) {
            return new MiningStructurePermissionR(Optional.ofNullable(miningStructurePermission.isAllowDrillThrough()),
                miningStructurePermission.getName(),
                Optional.ofNullable(miningStructurePermission.getID()),
                Optional.ofNullable(convertToInstant(miningStructurePermission.getCreatedTimestamp())),
                Optional.ofNullable(convertToInstant(miningStructurePermission.getLastSchemaUpdate())),
                Optional.ofNullable(miningStructurePermission.getDescription()),
                Optional.ofNullable(convertAnnotationList(miningStructurePermission.getAnnotations() == null ? null : miningStructurePermission.getAnnotations().getAnnotation())),
                miningStructurePermission.getRoleID(),
                Optional.ofNullable(miningStructurePermission.isProcess()),
                Optional.ofNullable(ReadDefinitionEnum.fromValue(miningStructurePermission.getReadDefinition())),
                Optional.ofNullable(ReadWritePermissionEnum.fromValue(miningStructurePermission.getRead())),
                Optional.ofNullable(ReadWritePermissionEnum.fromValue(miningStructurePermission.getWrite())));
        }
        return null;
    }

    private static List<MiningStructureColumn> convertMiningStructureColumnList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.MiningStructureColumn> list) {
        if (list != null) {
            return list.stream().map(MiningStructureConvertor::convertMiningStructureColumn).toList();
        }
        return List.of();
    }

    private static MiningStructureColumn convertMiningStructureColumn(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.MiningStructureColumn miningStructureColumn) {
        if (miningStructureColumn != null) {
            if (miningStructureColumn instanceof org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.ScalarMiningStructureColumn scalarMiningStructureColumn) {
                return new ScalarMiningStructureColumnR(scalarMiningStructureColumn.getName(),
                    Optional.ofNullable(scalarMiningStructureColumn.getID()),
                    Optional.ofNullable(scalarMiningStructureColumn.getDescription()),
                    Optional.ofNullable(scalarMiningStructureColumn.getType()),
                    Optional.ofNullable(convertAnnotationList(scalarMiningStructureColumn.getAnnotations() == null ? null : scalarMiningStructureColumn.getAnnotations().getAnnotation())),
                    Optional.ofNullable(scalarMiningStructureColumn.isIsKey()),
                    Optional.ofNullable(convertBinding(scalarMiningStructureColumn.getSource())),
                    Optional.ofNullable(scalarMiningStructureColumn.getDistribution()),
                    Optional.ofNullable(convertMiningModelingFlagList(scalarMiningStructureColumn.getModelingFlags() == null ? null : scalarMiningStructureColumn.getModelingFlags().getModelingFlag())),
                    scalarMiningStructureColumn.getContent(),
                    Optional.ofNullable(scalarMiningStructureColumn.getClassifiedColumns() == null ? null : scalarMiningStructureColumn.getClassifiedColumns().getClassifiedColumnID()),
                    Optional.ofNullable(scalarMiningStructureColumn.getDiscretizationMethod()),
                    Optional.ofNullable(scalarMiningStructureColumn.getDiscretizationBucketCount()),
                    Optional.ofNullable(convertDataItemList(scalarMiningStructureColumn.getKeyColumns() == null ? null : scalarMiningStructureColumn.getKeyColumns().getKeyColumn())),
                    Optional.ofNullable(convertDataItem(scalarMiningStructureColumn.getNameColumn())),
                    Optional.ofNullable(convertTranslationList(scalarMiningStructureColumn.getTranslations() == null ? null : scalarMiningStructureColumn.getTranslations().getTranslation())));
            }
            if (miningStructureColumn instanceof org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.TableMiningStructureColumn tableMiningStructureColumn) {
                return new TableMiningStructureColumnR(
                    Optional.ofNullable(convertDataItemList(tableMiningStructureColumn.getForeignKeyColumns() == null ? null : tableMiningStructureColumn.getForeignKeyColumns().getForeignKeyColumn())),
                    Optional.ofNullable(convertMeasureGroupBinding(tableMiningStructureColumn.getSourceMeasureGroup())),
                    Optional.ofNullable(convertMiningStructureColumnList(tableMiningStructureColumn.getColumns() == null ? null : tableMiningStructureColumn.getColumns().getColumn())),
                    Optional.ofNullable(convertTranslationList(tableMiningStructureColumn.getTranslations() == null ? null : tableMiningStructureColumn.getTranslations().getTranslation())));
            }
        }
        return null;
    }

}
