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

import org.eclipse.daanse.xmla.api.xmla.AlgorithmParameter;
import org.eclipse.daanse.xmla.api.xmla.Annotation;
import org.eclipse.daanse.xmla.api.xmla.AttributeTranslation;
import org.eclipse.daanse.xmla.api.xmla.FoldingParameters;
import org.eclipse.daanse.xmla.api.xmla.MiningModel;
import org.eclipse.daanse.xmla.api.xmla.MiningModelColumn;
import org.eclipse.daanse.xmla.api.xmla.MiningModelPermission;

import java.time.Instant;
import java.util.List;

public record MiningModelR(String name,
                           String id,
                           Instant createdTimestamp,
                           Instant lastSchemaUpdate,
                           String description,
                           List<Annotation> annotations,
                           String algorithm,
                           Instant lastProcessed,
                           List<AlgorithmParameter> algorithmParameters,
                           Boolean allowDrillThrough,
                           List<AttributeTranslation> translations,
                           List<MiningModelColumn> columns,
                           String state,
                           FoldingParameters foldingParameters,
                           String filter,
                           List<MiningModelPermission> miningModelPermissions,
                           String language,
                           String collation) implements MiningModel {

}