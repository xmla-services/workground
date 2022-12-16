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
package org.eclipse.daanse.olap.rolap.dbmapper.record;

import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.api.ExpressionView;

public record ExpressionViewR(List<SQLR> sql,
                              String gnericExpression,
                              String table,
                              String name,
                              String tableAlias) implements ExpressionView {

    @Override
    public String genericExpression() {
        for (int i = 0; i < sql.size(); i++) {
            if (sql.get(i).dialect().equals("generic")) {
                return sql.get(i).content();
            }
        }
        return sql.get(0).content();
    }
}
