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

import mondrian.rolap.RolapRuntimeException;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingJoin;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Relation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.RelationOrJoin;

import mondrian.olap.Util;

public class JoinUtil {

    private JoinUtil() {
        // constructor
    }

    public static RelationOrJoin left(MappingJoin join) {
        if (join.relations() != null && !join.relations().isEmpty()) {
            return join.relations().get(0);
        }
        throw new RolapRuntimeException("Join left error");
    }

    public static RelationOrJoin right(MappingJoin join) {
        if (join.relations() != null && join.relations().size() > 1) {
            return join.relations().get(1);
        }
        throw new RolapRuntimeException("Join left error");
    }

    public static void changeLeftRight(MappingJoin join, RelationOrJoin left, RelationOrJoin right) {
        join.relations().clear();
        join.relations().add(left);
        join.relations().add(right);
    }

    /**
     * Returns the alias of the left join key, defaulting to left's
     * alias if left is a table.
     */
    public static String getLeftAlias(MappingJoin join) {
        if (join.leftAlias() != null) {
            return join.leftAlias();
        }
        RelationOrJoin left = left(join);
        if (left instanceof Relation relation) {
            return RelationUtil.getAlias(relation);
        }
        throw Util.newInternal(
            new StringBuilder("alias is required because ").append(left).append(" is not a table").toString());
    }

    /**
     * Returns the alias of the right join key, defaulting to right's
     * alias if right is a table.
     */
    public static String getRightAlias(MappingJoin join) {
        if (join.rightAlias() != null) {
            return join.rightAlias();
        }
        RelationOrJoin right = right(join);
        if (right instanceof Relation relation) {
            return RelationUtil.getAlias(relation);
        }
        if (right instanceof MappingJoin j) {
            return getLeftAlias(j);
        }
        throw Util.newInternal(
            new StringBuilder("alias is required because ").append(right).append(" is not a table").toString());
    }

}
