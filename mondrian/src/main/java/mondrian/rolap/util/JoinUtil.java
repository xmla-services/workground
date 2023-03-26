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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Join;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Relation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.RelationOrJoin;

import mondrian.olap.Util;

public class JoinUtil {

    public static RelationOrJoin left(Join join) {
        if (join.relation() != null && join.relation().size() > 0) {
            return join.relation().get(0);
        }
        throw new RuntimeException("Join left error");
    }

    public static RelationOrJoin right(Join join) {
        if (join.relation() != null && join.relation().size() > 1) {
            return join.relation().get(1);
        }
        throw new RuntimeException("Join left error");
    }

    public static void changeLeftRight(Join join, RelationOrJoin left, RelationOrJoin right) {
        join.relation().clear();
        join.relation().add(left);
        join.relation().add(right);
    }

    /**
     * Returns the alias of the left join key, defaulting to left's
     * alias if left is a table.
     */
    public static String getLeftAlias(Join join) {
        if (join.leftAlias() != null) {
            return join.leftAlias();
        }
        RelationOrJoin left = left(join);
        if (left instanceof Relation) {
            return RelationUtil.getAlias((Relation) left);
        }
        throw Util.newInternal(
            new StringBuilder("alias is required because ").append(left).append(" is not a table").toString());
    }

    /**
     * Returns the alias of the right join key, defaulting to right's
     * alias if right is a table.
     */
    public static String getRightAlias(Join join) {
        if (join.rightAlias() != null) {
            return join.rightAlias();
        }
        RelationOrJoin right = right(join);
        if (right instanceof Relation) {
            return RelationUtil.getAlias(((Relation) right));
        }
        if (right instanceof Join) {
            return getLeftAlias(((Join) right));
        }
        throw Util.newInternal(
            new StringBuilder("alias is required because ").append(right).append(" is not a table").toString());
    }

}
