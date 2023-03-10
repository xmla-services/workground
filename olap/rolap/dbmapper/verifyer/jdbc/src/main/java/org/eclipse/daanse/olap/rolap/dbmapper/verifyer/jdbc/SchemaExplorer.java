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

import org.eclipse.daanse.olap.rolap.dbmapper.api.Join;
import org.eclipse.daanse.olap.rolap.dbmapper.api.RelationOrJoin;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Table;

import java.util.TreeSet;

public class SchemaExplorer {

    public static String[] getTableNameForAlias(RelationOrJoin relation, String table) {
        String theTableName = table;
        String schemaName = null;

        // EC: Loops join tree and finds the table name for an alias.
        if (relation instanceof Join) {
            RelationOrJoin theRelOrJoin_L =
                left(((Join) relation));
            RelationOrJoin theRelOrJoin_R =
                right(((Join) relation));
            for (int i = 0; i < 2; i++) {
                // Searches first using the Left Join and then the Right.
                RelationOrJoin theCurrentRelOrJoin =
                    (i == 0)
                        ? theRelOrJoin_L
                        : theRelOrJoin_R;
                if (theCurrentRelOrJoin instanceof Table) {
                    Table theTable =
                        ((Table) theCurrentRelOrJoin);
                    if (theTable.alias() != null && theTable.alias()
                        .equals(table)) {
                        // If the alias was found get its table name and return
                        // it.
                        theTableName = theTable.name();
                        schemaName = theTable.schema();
                    }
                } else {
                    // otherwise continue down the join tree.
                    String[] result = getTableNameForAlias(
                        theCurrentRelOrJoin, table);
                    schemaName = result[0];
                    theTableName = result[1];
                }
            }
        }
        return new String[]{schemaName, theTableName};
    }

    public static void getTableNamesForJoin(RelationOrJoin relation, TreeSet<String> joinTables) {
        // EC: Loops join tree and collects table names.
        if (relation instanceof Join) {
            RelationOrJoin theRelOrJoin_L =
                left((Join) relation);
            RelationOrJoin theRelOrJoin_R =
                right((Join) relation);
            for (int i = 0; i < 2; i++) {
                // Searches first using the Left Join and then the Right.
                RelationOrJoin theCurrentRelOrJoin =
                    (i == 0)
                        ? theRelOrJoin_L
                        : theRelOrJoin_R;
                if (theCurrentRelOrJoin instanceof Table) {
                    Table theTable =
                        ((Table) theCurrentRelOrJoin);
                    String theTableName = (theTable.alias() != null
                        && theTable.alias().trim().length()
                        > 0)
                        ? theTable.alias()
                        : theTable.name();
                    joinTables.add(theTableName);
                } else {
                    // Calls recursively collecting all table names down the
                    // join tree.
                    getTableNamesForJoin(theCurrentRelOrJoin, joinTables);
                }
            }
        }

    }

    private static RelationOrJoin left(Join join) {
        if (join.relation() != null && join.relation().size() > 0) {
            return join.relation().get(0);
        }
        throw new RuntimeException("Join left error");
    }

    private static RelationOrJoin right(Join join) {
        if (join.relation() != null && join.relation().size() > 1) {
            return join.relation().get(1);
        }
        throw new RuntimeException("Join left error");
    }
}
