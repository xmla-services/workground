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
package mondrian.rolap;

import mondrian.rolap.sql.SqlQuery;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Column;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Expression;
import org.eclipse.daanse.olap.rolap.dbmapper.api.ExpressionView;

public class ExpressionUtil {

    static public String getExpression(Expression expression, final SqlQuery query) {
        if (expression instanceof Column) {
            return query.getDialect().quoteIdentifier(expression.table(), expression.name());
        }
        if (expression instanceof ExpressionView) {
            SqlQuery.CodeSet codeSet = new SqlQuery.CodeSet();
            ((ExpressionView) expression).sql().forEach(e -> codeSet.put(e.dialect(), e.content()));
            return codeSet.chooseQuery(query.getDialect());
        }
        throw new RuntimeException("getExpression error. Expression is not ExpressionView or Column");
    }
}
