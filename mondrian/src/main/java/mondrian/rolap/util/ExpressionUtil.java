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

import mondrian.rolap.RolapRuntimeException;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Column;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Expression;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.ExpressionView;

import mondrian.rolap.sql.SqlQuery;

public class ExpressionUtil {

    public static String getExpression(Expression expression, final SqlQuery query) {
        if (expression instanceof Column) {
            return query.getDialect().quoteIdentifier(expression.table(), expression.name());
        }
        if (expression instanceof ExpressionView expressionView) {
            SqlQuery.CodeSet codeSet = new SqlQuery.CodeSet();
            expressionView.sqls().forEach(e -> codeSet.put(e.dialect(), e.content()));
            return codeSet.chooseQuery(query.getDialect());
        }
        throw new RolapRuntimeException("getExpression error. Expression is not ExpressionView or Column");
    }

    public static int hashCode(Expression expression) {
        if (expression instanceof Column) {
            return expression.name().hashCode() ^ (expression.table()==null ? 0 : expression.table().hashCode());
        }
        if (expression instanceof ExpressionView expressionView) {
            int h = 17;
            for (int i = 0; i < expressionView.sqls().size(); i++) {
                h = 37 * h + SQLUtil.hashCode(expressionView.sqls().get(i));
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
        if (expression instanceof ExpressionView expressionView) {
            if (!(obj instanceof ExpressionView that)) {
                return false;
            }
            if (expressionView.sqls().size() != that.sqls().size()) {
                return false;
            }
            for (int i = 0; i < expressionView.sqls().size(); i++) {
                if (! expressionView.sqls().get(i).equals(that.sqls().get(i))) {
                    return false;
                }
            }
            return true;
        }
        return expression.equals(obj);
    }

    public static String genericExpression(Expression expression) {
        if (expression instanceof Column column) {
            return column.genericExpression();
        }
        if (expression instanceof ExpressionView expressionView) {
            for (int i = 0; i < expressionView.sqls().size(); i++) {
                if (expressionView.sqls().get(i).dialect().equals("generic")) {
                    return expressionView.sqls().get(i).content();
                }
            }
            return ((ExpressionView) expression).sqls().get(0).content();
        }
        throw new RolapRuntimeException("genericExpression error");
    }

    public static String toString(Expression expression) {
        if (expression instanceof ExpressionView expressionView) {
            return expressionView.sqls().get(0).content();
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
        throw new RolapRuntimeException("Expression getTableAlias error");
    }
}
