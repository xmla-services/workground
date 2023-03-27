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
package org.eclipse.daanse.olap.rolap.dbmapper.model.api;

import java.util.List;

public sealed interface AggTable permits AggName, AggPattern {
    AggColumnName aggFactCount();

    List<? extends AggColumnName> aggIgnoreColumn();

    List<? extends AggForeignKey> aggForeignKey();

    List<? extends AggMeasure> aggMeasure();

    List<? extends AggLevel> aggLevel();

    boolean ignorecase();

    List<? extends AggMeasureFactCount> measuresFactCount();
}
