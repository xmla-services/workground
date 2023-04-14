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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Column;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Expression;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.ExpressionView;

import mondrian.rolap.sql.SqlQuery;

public class ExpressionUtil {

    static public String getExpression(Expression expression, final SqlQuery query) {
        if (expression instanceof Column) {
            return query.getDialect().quoteIdentifier(expression.table(), expression.name());
        }
        if (expression instanceof ExpressionView) {
            SqlQuery.CodeSet codeSet = new SqlQuery.CodeSet();
            ((ExpressionView) expression).sqls().forEach(e -> codeSet.put(e.dialect(), e.content()));
            return codeSet.chooseQuery(query.getDialect());
        }
        throw new RuntimeException("getExpression error. Expression is not ExpressionView or Column");
    }

    static public int hashCode(Expression expression) {
        if (expression instanceof Column) {
            return expression.name().hashCode() ^ (expression.table()==null ? 0 : expression.table().hashCode());
        }
        if (expression instanceof ExpressionView) {
            int h = 17;
            for (int i = 0; i < ((ExpressionView) expression).sqls().size(); i++) {
                h = 37 * h + SQLUtil.hashCode(((ExpressionView) expression).sqls().get(i));
            }
            return h;
        }
        return expression.hashCode();
    }

    public static boolean equals(Expression expression, Object obj) {
        if (expression instanceof Column) {
            if (!(obj instanceof Column that)) {
                return false;
            }
            return expression.name().equals(that.name()) &&
                Objects.equals(expression.table(), that.table());
        }
        if (expression instanceof ExpressionView) {
            if (!(obj instanceof ExpressionView that)) {
                return false;
            }
            if (((ExpressionView) expression).sqls().size() != that.sqls().size()) {
                return false;
            }
            for (int i = 0; i < ((ExpressionView) expression).sqls().size(); i++) {
                if (! ((ExpressionView) expression).sqls().get(i).equals(that.sqls().get(i))) {
                    return false;
                }
            }
            return true;
        }
        return expression.equals(obj);
    }

    public static String genericExpression(Expression expression) {
        if (expression instanceof Column) {
            return ((Column) expression).genericExpression();
        }
        if (expression instanceof ExpressionView) {
            for (int i = 0; i < ((ExpressionView) expression).sqls().size(); i++) {
                if (((ExpressionView) expression).sqls().get(i).dialect().equals("generic")) {
                    return ((ExpressionView) expression).sqls().get(i).content();
                }
            }
            return ((ExpressionView) expression).sqls().get(0).content();
        }
        throw new RuntimeException("genericExpression error");
    }

    public static String toString(Expression expression) {
        if (expression instanceof ExpressionView) {
            return ((ExpressionView) expression).sqls().get(0).content();
        }
        return expression.toString();
    }

    public String getExpression(ExpressionView expression, SqlQuery query) {
        return SQLUtil.toCodeSet(expression.sqls()).chooseQuery(query.getDialect());
    }

    public static String getTableAlias(Expression expression) {
        if (expression instanceof Column) {
            return expression.table();
        }
        if (expression instanceof ExpressionView) {
            return null;
        }
        throw new RuntimeException("Expression getTableAlias error");
    }
}
