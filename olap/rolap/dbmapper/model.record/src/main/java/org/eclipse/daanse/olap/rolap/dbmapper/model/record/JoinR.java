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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingJoinQuery;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingJoinedQueryElement;

public class JoinR implements MappingJoinQuery {

    private MappingJoinedQueryElement left;
    private MappingJoinedQueryElement right;

    public JoinR(MappingJoinedQueryElement left,
                 MappingJoinedQueryElement right)
    {
        this.left = left;
        this.right = right;
    }

    @Override
    public MappingJoinedQueryElement left() {
        return left;
    }

    @Override
    public MappingJoinedQueryElement right() {
        return right;
    }

    public void setLeft(MappingJoinedQueryElement left) {
        this.left = left;
    }

    public void setRight(MappingJoinedQueryElement right) {
        this.right = right;
    }
}
