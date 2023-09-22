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

import static mondrian.rolap.util.JoinUtil.left;
import static mondrian.rolap.util.JoinUtil.right;

import java.util.Objects;

import mondrian.rolap.RolapRuntimeException;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingInlineTable;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingJoin;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Relation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.RelationOrJoin;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Table;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.View;

public class RelationUtil {

    private RelationUtil() {
        // constructor
    }

    public static Relation find(RelationOrJoin relationOrJoin, String tableName) {
        if (relationOrJoin instanceof MappingInlineTable inlineTable) {
            return tableName.equals(inlineTable.alias()) ? (Relation) relationOrJoin : null;
        }
        if (relationOrJoin instanceof Table table) {
            if (tableName.equals(table.name())) {
                return (Relation) relationOrJoin;
            } else {
                    return null; //old version of code had wrong condition with equals
            }
        }
        if (relationOrJoin instanceof View view) {
            if (tableName.equals(view.alias())) {
                return (Relation) relationOrJoin;
            } else {
                return null;
            }
        }
        if (relationOrJoin instanceof MappingJoin join) {
            RelationOrJoin relation = find(left(join), tableName);
            if (relation == null) {
                relation = find(right(join), tableName);
            }
            return (Relation) relation;

        }

        throw new RolapRuntimeException("Rlation: find error");
    }

    public static String getAlias(Relation relation) {
        if (relation instanceof Table table) {
            return (relation.alias() != null) ? relation.alias() : table.name();
        }
        else {
            return relation.alias();
        }
    }

    public static boolean equals(Relation relation, Object o) {
        if (relation instanceof View view) {
            if (o instanceof View that) {
                if (!Objects.equals(relation.alias(), that.alias())) {
                    return false;
                }
                if (view.sqls() == null || that.sqls() == null || view.sqls().size() != that.sqls().size()) {
                    return false;
                }
                for (int i = 0; i < view.sqls().size(); i++) {
                    if (!Objects.equals(view.sqls().get(i).dialect(), that.sqls().get(i).dialect())
                        || !Objects.equals(view.sqls().get(i).content(), that.sqls().get(i).content()))
                    {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        }
        if (relation instanceof Table table) {
            if (o instanceof Table that) {
                return table.name().equals(that.name()) &&
                    Objects.equals(relation.alias(), that.alias()) &&
                    Objects.equals(table.schema(), that.schema());
            } else {
                return false;
            }
        }
        if (relation instanceof MappingInlineTable) {
            if (o instanceof MappingInlineTable that) {
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
        if (relation instanceof MappingInlineTable) {
            return toString(relation).hashCode();
        }
        return System.identityHashCode(relation);
    }

    private static Object toString(Relation relation) {
        if (relation instanceof Table table) {
            return (table.schema() == null) ?
                table.name() :
                new StringBuilder(table.schema()).append(".").append(table.name()).toString();
        }
        if (relation instanceof MappingJoin join) {
            return new StringBuilder("(").append(left(join)).append(") join (").append(right(join)).append(") on ")
                .append((join).leftAlias()).append(".").append((join).leftKey()).append(" = ")
                .append((join).rightAlias()).append(".").append((join).rightKey()).toString();
        }
        if (relation instanceof MappingInlineTable) {
            return "<inline data>";
        }
        return relation.toString();
    }

}
