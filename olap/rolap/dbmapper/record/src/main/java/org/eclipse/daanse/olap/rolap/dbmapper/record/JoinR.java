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

import org.eclipse.daanse.olap.rolap.dbmapper.api.Join;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Relation;
import org.eclipse.daanse.olap.rolap.dbmapper.api.RelationOrJoin;

public class JoinR implements Join {

    private List<RelationOrJoin> relation;
    private String leftAlias;
    private String leftKey;
    private String rightAlias;
    private String rightKey;

    public JoinR(List<RelationOrJoin> relation,
                    String leftAlias,
                    String leftKey,
                    String rightAlias,
                    String rightKey)
    {
        this.relation = relation;
        this.leftAlias = leftAlias;
        this.leftKey = leftKey;
        this.rightAlias = rightAlias;
        this.rightKey = rightKey;
    }

    @Override
    public List<RelationOrJoin> relation() {
        return relation;
    }

    @Override
    public String leftAlias() {
        return leftAlias;
    }

    @Override
    public String leftKey() {
        return leftKey;
    }

    @Override
    public String rightAlias() {
        return rightAlias;
    }

    @Override
    public String rightKey() {
        return rightKey;
    }

    @Override
    public void setLeftAlias(String rightAlias) {
        this.rightAlias = rightAlias;
    }

    @Override
    public void setLeftKey(String rightKey) {
        this.rightKey = rightKey;
    }

    @Override
    public void setRightAlias(String leftAlias) {
        this.leftAlias = leftAlias;
    }

    @Override
    public void setRightKey(String leftKey) {
        this.leftKey = leftKey;
    }

}
