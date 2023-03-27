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

import java.util.Objects;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.*;

import static mondrian.rolap.util.JoinUtil.left;
import static mondrian.rolap.util.JoinUtil.right;

public class RelationUtil {

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

    public static String getAlias(Relation relation) {
        if (relation instanceof Table) {
            return (relation.alias() != null) ? relation.alias() : ((Table) relation).name();
        }
        else {
            return relation.alias();
        }
    }

    public static boolean equals(Relation relation, Object o) {
        if (relation instanceof View) {
            if (o instanceof View) {
                View that = (View) o;
                if (!Objects.equals(relation.alias(), that.alias())) {
                    return false;
                }
                if (((View) relation).sqls() == null || that.sqls() == null || ((View) relation).sqls().size() != that.sqls().size()) {
                    return false;
                }
                for (int i = 0; i < ((View) relation).sqls().size(); i++) {
                    if (!Objects.equals(((View) relation).sqls().get(i).dialect(), that.sqls().get(i).dialect())
                        || !Objects.equals(((View) relation).sqls().get(i).content(), that.sqls().get(i).content()))
                    {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        }
        if (relation instanceof Table) {
            if (o instanceof Table) {
                Table that = (Table) o;
                return ((Table) relation).name().equals(that.name()) &&
                    Objects.equals(relation.alias(), that.alias()) &&
                    Objects.equals(((Table) relation).schema(), that.schema());
            } else {
                return false;
            }
        }
        if (relation instanceof InlineTable) {
            if (o instanceof InlineTable) {
                InlineTable that = (InlineTable) o;
                return relation.alias().equals(that.alias());
            } else {
                return false;
            }

        }
        return relation == o;
    }

    public static int hashCode(Relation relation) {
        if (relation instanceof Table) {
            return toString(relation).hashCode();
        }
        if (relation instanceof InlineTable) {
            return toString(relation).hashCode();
        }
        return System.identityHashCode(relation);
    }

    private static Object toString(Relation relation) {
        if (relation instanceof Table) {
            return (((Table) relation).schema() == null) ?
                ((Table) relation).name() :
                new StringBuilder(((Table) relation).schema()).append(".").append(((Table) relation).name()).toString();
        }
        if (relation instanceof Join) {
            return new StringBuilder("(").append(left((Join) relation)).append(") join (").append(right((Join) relation)).append(") on ")
                .append(((Join) relation).leftAlias()).append(".").append(((Join) relation).leftKey()).append(" = ")
                .append(((Join) relation).rightAlias()).append(".").append(((Join) relation).rightKey()).toString();
        }
        if (relation instanceof InlineTable) {
            return "<inline data>";
        }
        return relation.toString();
    }

}
