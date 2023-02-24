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
package org.eclipse.daanse.xmla.api.xmla;


import org.eclipse.daanse.xmla.api.engine.ImpersonationInfo;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

public interface Database {

    String name();

    String id();

    Instant createdTimestamp();

    Instant lastSchemaUpdate();

    String description();

    Database.Annotations annotations();

    Instant lastUpdate();

    String state();

    String readWriteMode();

    String dbStorageLocation();

    String aggregationPrefix();

    BigInteger processingPriority();

    Long estimatedSize();

    Instant lastProcessed();

    BigInteger language();

    String collation();

    Boolean visible();

    String masterDataSourceID();

    ImpersonationInfo dataSourceImpersonationInfo();

    Database.Accounts accounts();

    Database.DataSources dataSources();

    Database.DataSourceViews dataSourceViews();

    Database.Dimensions dimensions();

    Database.Cubes cubes();

    Database.MiningStructures miningStructures();

    Database.Roles roles();

    Database.Assemblies assemblies();

    Database.DatabasePermissions databasePermissions();

    Database.Translations translations();

    String storageEngineUsed();

    String imagePath();

    String imageUrl();

    String imageUniqueID();

    String imageVersion();

    String token();

    BigInteger compatibilityLevel();

    String directQueryMode();

    public interface Accounts {

        List<Account> account();

    }

    interface Annotations {

        List<Annotation> annotation();

    }

    interface Assemblies {

        List<Assembly> assembly();

    }

    interface Cubes {

        List<Cube> cube();

    }

    interface DatabasePermissions {

        List<DatabasePermission> databasePermission();

    }

    interface DataSources {

        List<DataSource> dataSource();

    }

    interface DataSourceViews {

        List<DataSourceView> dataSourceView();

    }

    interface Dimensions {

        List<Dimension> dimension();

    }

    interface MiningStructures {

        List<MiningStructure> miningStructure();

    }

    interface Roles {

        List<Role> role();

    }

    interface Translations {

        List<Translation> translation();
    }

}
