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
package org.eclipse.daanse.olap.rolap.dbmapper.model.record;

import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.ExpressionView;

public record ExpressionViewR(List<SQLR> sql,
                              String gnericExpression,
                              String table,
                              String name) implements ExpressionView {

    public boolean equals(Object obj) {
        if (!(obj instanceof ExpressionView)) {
            return false;
        }
        ExpressionView that = (ExpressionView) obj;
        if (sql().size() != that.sql().size()) {
            return false;
        }
        for (int i = 0; i < sql().size(); i++) {
            if (! sql().get(i).equals(that.sql().get(i))) {
                return false;
            }
        }
        return true;
    }
}
