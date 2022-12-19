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

import org.eclipse.daanse.olap.rolap.dbmapper.api.*;

import static mondrian.rolap.util.JoinUtil.left;
import static mondrian.rolap.util.JoinUtil.right;

public class RlationUtil {

    public static Relation find(RelationOrJoin relationOrJoin, String tableName) {
        if (relationOrJoin instanceof InlineTable) {
            return tableName.equals(((InlineTable) relationOrJoin).alias()) ? (Relation) relationOrJoin : null;
        }
        if (relationOrJoin instanceof Table) {
            return tableName.equals(((Table) relationOrJoin).name()) ? (Relation) relationOrJoin :
                (((Table) relationOrJoin).alias() != null) && relationOrJoin.equals(((Table) relationOrJoin).alias()) ? (Relation) relationOrJoin :
                    null;
        }
        if (relationOrJoin instanceof View) {
            if (tableName.equals(((View) relationOrJoin).alias())) {
                return (Relation) relationOrJoin;
            } else {
                return null;
            }
        }
        if (relationOrJoin instanceof Join) {
            RelationOrJoin relation = find(left((Join) relationOrJoin), tableName);
            if (relation == null) {
                relation = find(right((Join) relationOrJoin), tableName);
            }
            return (Relation) relation;

        }

        throw new RuntimeException("Rlation: find error");
    }

}
