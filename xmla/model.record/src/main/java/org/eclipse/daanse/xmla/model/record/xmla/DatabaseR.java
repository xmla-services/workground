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
package org.eclipse.daanse.xmla.model.record.xmla;

import org.eclipse.daanse.xmla.api.engine.ImpersonationInfo;
import org.eclipse.daanse.xmla.api.xmla.Annotation;
import org.eclipse.daanse.xmla.api.xmla.Assembly;
import org.eclipse.daanse.xmla.api.xmla.Cube;
import org.eclipse.daanse.xmla.api.xmla.DataSource;
import org.eclipse.daanse.xmla.api.xmla.DataSourceView;
import org.eclipse.daanse.xmla.api.xmla.Database;
import org.eclipse.daanse.xmla.api.xmla.DatabasePermission;
import org.eclipse.daanse.xmla.api.xmla.Dimension;
import org.eclipse.daanse.xmla.api.xmla.MiningStructure;
import org.eclipse.daanse.xmla.api.xmla.Role;
import org.eclipse.daanse.xmla.api.xmla.Translation;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

public record DatabaseR(String name,
                        String id,
                        Instant createdTimestamp,
                        Instant lastSchemaUpdate,
                        String description,
                        List<Annotation> annotations,
                        Instant lastUpdate,
                        String state,
                        String readWriteMode,
                        String dbStorageLocation,
                        String aggregationPrefix,
                        BigInteger processingPriority,
                        Long estimatedSize,
                        Instant lastProcessed,
                        BigInteger language,
                        String collation,
                        Boolean visible,
                        String masterDataSourceID,
                        ImpersonationInfo dataSourceImpersonationInfo,
                        DatabaseR.Accounts accounts,
                        DatabaseR.DataSources dataSources,
                        DatabaseR.DataSourceViews dataSourceViews,
                        DatabaseR.Dimensions dimensions,
                        DatabaseR.Cubes cubes,
                        DatabaseR.MiningStructures miningStructures,
                        DatabaseR.Roles roles,
                        DatabaseR.Assemblies assemblies,
                        DatabaseR.DatabasePermissions databasePermissions,
                        DatabaseR.Translations translations,
                        String storageEngineUsed,
                        String imagePath,
                        String imageUrl,
                        String imageUniqueID,
                        String imageVersion,
                        String token,
                        BigInteger compatibilityLevel,
                        String directQueryMode) implements Database {


    record AssembliesR(List<Assembly> assembly) implements Database.Assemblies {

    }

    record CubesR(List<Cube> cube) implements Database.Cubes {

    }

    record DatabasePermissionsR(List<DatabasePermission> databasePermission) implements Database.DatabasePermissions {

    }

    record DataSourcesR(List<DataSource> dataSource) implements Database.DataSources {

    }

    record DataSourceViewsR(List<DataSourceView> dataSourceView) implements Database.DataSourceViews {

    }

    record DimensionsR(List<Dimension> dimension) implements Database.Dimensions {

    }

    record MiningStructuresR(List<MiningStructure> miningStructure) implements Database.MiningStructures {

    }

    record RolesR(List<Role> role) implements Database.Roles {

    }

    record TranslationsR(List<Translation> translation) implements Database.Translations {

    }
}
