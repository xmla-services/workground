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
package org.eclipse.daanse.olap.rolap.dbmapper.dbcreator.basic;

import org.eclipse.daanse.db.jdbc.util.api.DatabaseCreatorService;
import org.eclipse.daanse.db.jdbc.util.impl.DBStructure;
import org.eclipse.daanse.olap.rolap.dbmapper.dbcreator.api.DbCreatorService;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchema;
import org.eclipse.daanse.olap.rolap.dbmapper.utils.Utils;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DbCreatorServiceImpl implements DbCreatorService {

    private DataSource dataSource;
    private DatabaseCreatorService databaseCreatorService;

    public DbCreatorServiceImpl(DataSource dataSource, DatabaseCreatorService databaseCreatorService) {
        this.dataSource = dataSource;
        this.databaseCreatorService = databaseCreatorService;
    }

    @Override
    public DBStructure createSchema(MappingSchema schema) throws SQLException {
        DBStructure dbStructure = Utils.getDBStructure(schema);
        databaseCreatorService.createDatabaseSchema(dataSource, dbStructure);
        return dbStructure;
    }
}
