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

import org.eclipse.daanse.olap.rolap.dbmapper.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Cause;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.JdbcValidator;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.VerificationResult;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Verifyer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.jdbc.SchemaCheckerUtils.checkSchema;

public class DatabaseVerifyer implements Verifyer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Verifyer.class);

    @Override
    public List<VerificationResult> verify(Schema schema, DataSource dataSource) {
        List<VerificationResult> results = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            JdbcValidator jdbcValidator = new JdbcValidatorImpl(connection);
            results.addAll(checkSchema(jdbcValidator, schema));
        } catch (SQLException e) {
            results.add(new VerificationResultR("Database access error", e.getMessage(), Level.ERROR, Cause.DATABASE));
        }

        return results;
    }

}
