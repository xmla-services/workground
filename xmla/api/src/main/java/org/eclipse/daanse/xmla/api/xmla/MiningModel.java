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

import java.time.Instant;
import java.util.List;

public interface MiningModel {

     String name();
     String id();
     Instant createdTimestamp();
     Instant lastSchemaUpdate();
     String description();
     MiningModel.Annotations annotations();
     String algorithm();
     Instant lastProcessed();
     MiningModel.AlgorithmParameters algorithmParameters();
     Boolean allowDrillThrough();
     MiningModel.Translations translations();
     MiningModel.Columns columns();
     String state();
     FoldingParameters foldingParameters();
     String filter();
     MiningModel.MiningModelPermissions miningModelPermissions();
     String language();
     String collation();

    public interface AlgorithmParameters {

         List<AlgorithmParameter> algorithmParameter();

    }

    public interface Annotations {

         List<Annotation> annotation();

    }

    public interface Columns {

         List<MiningModelColumn> column();

    }

    public interface MiningModelPermissions {

         List<MiningModelPermission> miningModelPermission();

    }

    public interface Translations {

         List<AttributeTranslation> translation();

    }

}
