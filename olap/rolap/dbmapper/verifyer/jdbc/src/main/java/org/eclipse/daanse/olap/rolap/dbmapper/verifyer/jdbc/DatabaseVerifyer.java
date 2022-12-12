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

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.daanse.olap.rolap.dbmapper.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Table;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.VerificationResult;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Verifyer;

public class DatabaseVerifyer implements Verifyer {

    @Override
    public List<VerificationResult> verify(Schema schema, Optional<DatabaseMetaData> oDatabaseMetaData) {
        if (oDatabaseMetaData.isEmpty()) {
            return List.of(NoDatabaseVerificationResult.INSTANCE);
        }

        DatabaseMetaData dbMetaData = oDatabaseMetaData.get();

        List<VerificationResult> results = new ArrayList<>();

        return results;
    }

    boolean checkTable(DatabaseMetaData dbMetaData, Table table) {
//        dbmMetaData.getTables(null, null, null, null);
        return false;
    }

}
