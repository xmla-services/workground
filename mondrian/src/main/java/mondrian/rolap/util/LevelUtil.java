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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Expression;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Level;
import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.ColumnImpl;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.ColumnR;

import java.util.Objects;

public class LevelUtil {

    public static Expression getKeyExp(Level level) {
        if (level.keyExpression() != null) {
            return level.keyExpression();
        } else if (level.column() != null) {
            return new ColumnR(level.table(), level.column());
        } else {
            return null;
        }
    }

    public static Expression getNameExp(Level level) {
        if (level.nameExpression() != null) {
            return level.nameExpression();
        } else if (level.nameColumn() != null && !Objects.equals(level.nameColumn(), level.column())) {
            return new ColumnImpl(level.table(), level.nameColumn());
        } else {
            return null;
        }
    }

    public static Expression getCaptionExp(Level level) {
        if (level.captionExpression() != null) {
            return level.captionExpression();
        } else if (level.captionColumn() != null) {
            return new ColumnImpl(level.table(), level.captionColumn());
        } else {
            return null;
        }
    }

    public static Expression getOrdinalExp(Level level) {
        if (level.ordinalExpression() != null) {
            return level.ordinalExpression();
        } else if (level.ordinalColumn() != null) {
            return new ColumnImpl(level.table(), level.ordinalColumn());
        } else {
            return null;
        }
    }

    public static Expression getParentExp(Level level) {
        if (level.parentExpression() != null) {
            return level.parentExpression();
        } else if (level.parentColumn() != null) {
            return new ColumnImpl(level.table(), level.parentColumn());
        } else {
            return null;
        }
    }

    public static Expression getPropertyExp(Level level, int i) {
        return new ColumnImpl(level.table(), level.property().get(i).column());
    }
}
