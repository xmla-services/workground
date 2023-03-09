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

import org.eclipse.daanse.xmla.api.xmla.Annotation;
import org.eclipse.daanse.xmla.api.xmla.Binding;
import org.eclipse.daanse.xmla.api.xmla.DataItem;
import org.eclipse.daanse.xmla.api.xmla.MiningModelingFlag;
import org.eclipse.daanse.xmla.api.xmla.MiningStructureColumn;
import org.eclipse.daanse.xmla.api.xmla.ScalarMiningStructureColumn;
import org.eclipse.daanse.xmla.api.xmla.Translation;

import java.math.BigInteger;
import java.util.List;

public record ScalarMiningStructureColumnR(
    String name,
    String id,
    String description,
    String type,
    List<Annotation> annotations,
    Boolean isKey,
    Binding source,
    String distribution,
    List<MiningModelingFlag> modelingFlags,
    String content,
    List<String> classifiedColumns,
    String discretizationMethod,
    BigInteger discretizationBucketCount,
    List<DataItem> keyColumns,
    DataItem nameColumn,
    List<Translation> translations) implements ScalarMiningStructureColumn {
}
