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
package org.eclipse.daanse.xmla.api.xmla;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

public interface MiningStructure {

     String name();
     String id();
     Instant createdTimestamp();
     Instant lastSchemaUpdate();
     String description();
     MiningStructure.Annotations annotations();
     Binding source();
     Instant lastProcessed();
     MiningStructure.Translations translations();
     BigInteger language();
     String collation();
     ErrorConfiguration errorConfiguration();
     String cacheMode();
     Integer holdoutMaxPercent();
     Integer holdoutMaxCases();
     Integer holdoutSeed();
     Integer holdoutActualSize();
     MiningStructure.Columns columns();
     String state();
     MiningStructure.MiningStructurePermissions miningStructurePermissions();
     MiningStructure.MiningModels miningModels();

    public interface Annotations {

         List<Annotation> annotation();
    }

    public interface Columns {

         List<MiningStructureColumn> column();

    }

    interface MiningModels {

         List<MiningModel> miningModel();

    }

    interface MiningStructurePermissions {

         List<MiningStructurePermission> miningStructurePermission();

    }

    interface Translations {

         List<Translation> translation();

    }

}
