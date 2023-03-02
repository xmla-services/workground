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
import org.eclipse.daanse.xmla.api.xmla.MiningModel;
import org.eclipse.daanse.xmla.api.xmla.MiningStructure;
import org.eclipse.daanse.xmla.api.xmla.MiningStructureColumn;
import org.eclipse.daanse.xmla.api.xmla.MiningStructurePermission;
import org.eclipse.daanse.xmla.api.xmla.Translation;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

public record MiningStructureR(String name,
                               String id,
                               Instant createdTimestamp,
                               Instant lastSchemaUpdate,
                               String description,
                               List<Annotation> annotations,
                               BindingR source,
                               Instant lastProcessed,
                               MiningStructureR.Translations translations,
                               BigInteger language,
                               String collation,
                               ErrorConfigurationR errorConfiguration,
                               String cacheMode,
                               Integer holdoutMaxPercent,
                               Integer holdoutMaxCases,
                               Integer holdoutSeed,
                               Integer holdoutActualSize,
                               MiningStructureR.Columns columns,
                               String state,
                               MiningStructureR.MiningStructurePermissions miningStructurePermissions,
                               MiningStructureR.MiningModels miningModels) implements MiningStructure {

    public record Columns(List<MiningStructureColumn> column) implements MiningStructure.Columns {

    }

    record MiningModels(List<MiningModel> miningModel) implements MiningStructure.MiningModels {

    }

    record MiningStructurePermissions(
        List<MiningStructurePermission> miningStructurePermission) implements MiningStructure.MiningStructurePermissions {

    }

    record Translations(List<Translation> translation) implements MiningStructure.Translations {

    }

}
