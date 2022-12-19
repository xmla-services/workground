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
package mondrian.rolap.util;

import mondrian.rolap.sql.SqlQuery;
import org.eclipse.daanse.olap.rolap.dbmapper.api.SQL;

import java.util.List;

public class SQLUtil {
    /**
     * Converts an array of SQL to a
     * {@link mondrian.rolap.sql.SqlQuery.CodeSet} object.
     */
    public static SqlQuery.CodeSet toCodeSet(List<? extends SQL> sqls) {
        SqlQuery.CodeSet codeSet = new SqlQuery.CodeSet();
        for (SQL sql : sqls) {
            codeSet.put(sql.dialect(), sql.content());
        }
        return codeSet;
    }
}
