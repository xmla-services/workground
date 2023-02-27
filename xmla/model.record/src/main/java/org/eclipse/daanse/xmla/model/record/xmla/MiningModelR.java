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
                           MiningModelR.Annotations annotations,
                           String algorithm,
                           Instant lastProcessed,
                           MiningModelR.AlgorithmParameters algorithmParameters,
                           Boolean allowDrillThrough,
                           MiningModelR.Translations translations,
                           MiningModelR.Columns columns,
                           String state,
                           FoldingParametersR foldingParameters,
                           String filter,
                           MiningModelR.MiningModelPermissions miningModelPermissions,
                           String language,
                           String collation) implements MiningModel {

    public record AlgorithmParameters(
        List<AlgorithmParameter> algorithmParameter) implements MiningModel.AlgorithmParameters {

    }

    public record Annotations(List<Annotation> annotation) implements MiningModel.Annotations {

    }

    public record Columns(List<MiningModelColumn> column) implements MiningModel.Columns {

    }

    public record MiningModelPermissions(
        List<MiningModelPermission> miningModelPermission) implements MiningModel.MiningModelPermissions {

    }

    public record Translations(List<AttributeTranslation> translation) implements MiningModel.Translations {

    }

}
