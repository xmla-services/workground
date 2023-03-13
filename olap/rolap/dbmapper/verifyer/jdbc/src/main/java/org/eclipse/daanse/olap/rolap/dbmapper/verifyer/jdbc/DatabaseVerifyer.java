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
package org.eclipse.daanse.olap.rolap.dbmapper.verifyer.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.eclipse.daanse.db.jdbc.metadata.api.JdbcMetaDataService;
import org.eclipse.daanse.db.jdbc.metadata.api.JdbcMetaDataServiceFactory;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Cause;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.VerificationResult;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Verifyer;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseVerifyer implements Verifyer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Verifyer.class);

    @Reference

    JdbcMetaDataServiceFactory jmdsf;

    @Override
    public List<VerificationResult> verify(Schema schema, DataSource dataSource) {
        List<VerificationResult> results = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            JdbcMetaDataService jmds = jmdsf.create(connection);

            JDBCSchemaWalker walker = new JDBCSchemaWalker(jmds);
            Collection<? extends VerificationResult> res = walker.checkSchema(schema);
            results.addAll(res);
        } catch (SQLException e) {
            results.add(new VerificationResultR("Database access error", e.getMessage(), Level.ERROR, Cause.DATABASE));
        }

        return results;
    }

}
