package org.eclipse.daanse.xmla.ws.jakarta.basic;

import org.eclipse.daanse.xmla.api.xmla.Account;
import org.eclipse.daanse.xmla.api.xmla.Assembly;
import org.eclipse.daanse.xmla.api.xmla.Cube;
import org.eclipse.daanse.xmla.api.xmla.DataSource;
import org.eclipse.daanse.xmla.api.xmla.DataSourceView;
import org.eclipse.daanse.xmla.api.xmla.Database;
import org.eclipse.daanse.xmla.api.xmla.DatabasePermission;
import org.eclipse.daanse.xmla.api.xmla.Dimension;
import org.eclipse.daanse.xmla.api.xmla.MiningStructure;
import org.eclipse.daanse.xmla.api.xmla.ReadDefinitionEnum;
import org.eclipse.daanse.xmla.api.xmla.ReadWritePermissionEnum;
import org.eclipse.daanse.xmla.api.xmla.Role;
import org.eclipse.daanse.xmla.model.record.xmla.AccountR;
import org.eclipse.daanse.xmla.model.record.xmla.DatabasePermissionR;
import org.eclipse.daanse.xmla.model.record.xmla.DatabaseR;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.eclipse.daanse.xmla.ws.jakarta.basic.AnnotationConvertor.convertAnnotationList;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.CommandConvertor.convertImpersonationInfo;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.ConvertorUtil.convertToInstant;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.CubeConvertor.convertTranslationList;

public class DatabaseConvertor {
    public static Database convertDatabase(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Database database) {
        if (database != null) {
            return new DatabaseR(database.getName(),
                database.getID(),
                convertToInstant(database.getCreatedTimestamp()),
                convertToInstant(database.getLastSchemaUpdate()),
                database.getDescription(),
                convertAnnotationList(database.getAnnotations() == null ?
                    null : database.getAnnotations().getAnnotation()),
                convertToInstant(database.getLastUpdate()),
                database.getState(),
                database.getReadWriteMode(),
                database.getDbStorageLocation(),
                database.getAggregationPrefix(),
                database.getProcessingPriority(),
                database.getEstimatedSize(),
                convertToInstant(database.getLastProcessed()),
                database.getLanguage(),
                database.getCollation(),
                database.isVisible(),
                database.getMasterDataSourceID(),
                convertImpersonationInfo(database.getDataSourceImpersonationInfo()),
                convertAccountList(database.getAccounts() == null ? null : database.getAccounts().getAccount()),
                convertDataSourceList(database.getDataSources() == null ? null : database.getDataSources().getDataSource()),
                convertDataSourceViewList(database.getDataSources() == null ? null : database.getDataSourceViews().getDataSourceView()),
                convertDimensionList(database.getDimensions() == null ? null : database.getDimensions().getDimension()),
                convertCubeList(database.getCubes() == null ? null : database.getCubes().getCube()),
                convertMiningStructureList(database.getMiningStructures() == null ? null : database.getMiningStructures().getMiningStructure()),
                convertRoleList(database.getRoles() == null ? null : database.getRoles().getRole()),
                convertAssemblyList(database.getAssemblies() == null ? null : database.getAssemblies().getAssembly()),
                convertDatabasePermissionList(database.getDatabasePermissions() == null ? null : database.getDatabasePermissions().getDatabasePermission()),
                convertTranslationList(database.getTranslations() == null ? null : database.getTranslations().getTranslation()),
                database.getStorageEngineUsed(),
                database.getImagePath(),
                database.getImageUrl(),
                database.getImageUniqueID(),
                database.getImageVersion(),
                database.getToken(),
                database.getCompatibilityLevel(),
                database.getDirectQueryMode());
        }
        return null;
    }

    private static List<DatabasePermission> convertDatabasePermissionList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.DatabasePermission> list) {
        if (list != null) {
            return list.stream().map(DatabaseConvertor::convertDatabasePermission).collect(Collectors.toList());
        }
        return null;
    }

    private static DatabasePermission convertDatabasePermission(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.DatabasePermission databasePermission) {
        if (databasePermission != null) {
            return new DatabasePermissionR(Optional.ofNullable(databasePermission.isAdminister()),
                databasePermission.getName(),
                Optional.ofNullable(databasePermission.getID()),
                Optional.ofNullable(convertToInstant(databasePermission.getCreatedTimestamp())),
                Optional.ofNullable(convertToInstant(databasePermission.getLastSchemaUpdate())),
                Optional.ofNullable(databasePermission.getDescription()),
                Optional.ofNullable(convertAnnotationList(databasePermission.getAnnotations() == null ? null : databasePermission.getAnnotations().getAnnotation())),
                databasePermission.getRoleID(),
                Optional.ofNullable(databasePermission.isProcess()),
                Optional.ofNullable(ReadDefinitionEnum.fromValue(databasePermission.getReadDefinition())),
                Optional.ofNullable(ReadWritePermissionEnum.fromValue(databasePermission.getRead())),
                Optional.ofNullable(ReadWritePermissionEnum.fromValue(databasePermission.getWrite())));
        }
        return null;
    }

    public static List<Assembly> convertAssemblyList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Assembly> list) {
        if (list != null) {
            return list.stream().map(MajorObjectConvertor::convertAssembly).collect(Collectors.toList());
        }
        return null;
    }

    private static List<Role> convertRoleList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Role> list) {
        if (list != null) {
            return list.stream().map(RoleConvertor::convertRole).collect(Collectors.toList());
        }
        return null;
    }

    private static List<MiningStructure> convertMiningStructureList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.MiningStructure> list) {
        if (list != null) {
            return list.stream().map(MiningStructureConvertor::convertMiningStructure).collect(Collectors.toList());
        }
        return null;
    }

    private static List<Cube> convertCubeList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Cube> list) {
        if (list != null) {
            return list.stream().map(CubeConvertor::convertCube).collect(Collectors.toList());
        }
        return null;
    }

    private static List<Dimension> convertDimensionList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Dimension> list) {
        if (list != null) {
            return list.stream().map(DimensionConvertor::convertDimension).collect(Collectors.toList());
        }
        return null;
    }

    private static List<DataSourceView> convertDataSourceViewList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.DataSourceView> list) {
        if (list != null) {
            return list.stream().map(CommandConvertor::convertDataSourceView).collect(Collectors.toList());
        }
        return null;
    }

    private static List<DataSource> convertDataSourceList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.DataSource> list) {
        if (list != null) {
            return list.stream().map(CommandConvertor::convertDataSource).collect(Collectors.toList());
        }
        return null;
    }

    private static List<Account> convertAccountList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Account> list) {
        if (list != null) {
            return list.stream().map(DatabaseConvertor::convertAccount).collect(Collectors.toList());
        }
        return null;
    }

    private static Account convertAccount(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Account account) {
        if (account != null) {
            return new AccountR(account.getAccountType(),
                account.getAggregationFunction(),
                account.getAliases() == null ? null : account.getAliases().getAlias(),
                convertAnnotationList(account.getAnnotations() == null ? null : account.getAnnotations().getAnnotation()));
        }
        return null;
    }

    public static List<Database> convertDatabaseList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Database> list) {
        if (list != null) {
            return list.stream().map(DatabaseConvertor::convertDatabase).collect(Collectors.toList());
        }
        return null;
    }
}
