/*
 * Copyright (c) 2023 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   SmartCity Jena - initial
 *   Stefan Bischof (bipolis.org) - initial
 */
package org.eclipse.daanse.xmla.model.record.xmla;

import org.eclipse.daanse.xmla.api.xmla.DataItem;
import org.eclipse.daanse.xmla.api.xmla.MeasureGroupBinding;
import org.eclipse.daanse.xmla.api.xmla.MiningStructureColumn;
import org.eclipse.daanse.xmla.api.xmla.TableMiningStructureColumn;
import org.eclipse.daanse.xmla.api.xmla.Translation;

import java.util.List;

public record TableMiningStructureColumnR(List<DataItem> foreignKeyColumns,
                                          MeasureGroupBinding sourceMeasureGroup,
                                          List<MiningStructureColumn> columns,
                                          List<Translation> translations
) implements TableMiningStructureColumn {

}
