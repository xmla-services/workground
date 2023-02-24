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

import java.util.List;

public interface OutOfLineBinding {


     String databaseID();

     String dimensionID();

     String cubeID();

     String measureGroupID();

     String partitionID();

     String miningModelID();

     String miningStructureID();

     String attributeID();

     String cubeDimensionID();

     String measureID();

     String parentColumnID();

     String columnID();

     Binding source();

     OutOfLineBinding.NameColumn nameColumn();

     OutOfLineBinding.SkippedLevelsColumn skippedLevelsColumn();

     OutOfLineBinding.CustomRollupColumn customRollupColumn();

     OutOfLineBinding.CustomRollupPropertiesColumn customRollupPropertiesColumn();

     OutOfLineBinding.ValueColumn valueColumn();

     OutOfLineBinding.UnaryOperatorColumn unaryOperatorColumn();

     OutOfLineBinding.KeyColumns keyColumns();

     OutOfLineBinding.ForeignKeyColumns foreignKeyColumns();

     OutOfLineBinding.Translations translations();


    public interface CustomRollupColumn {

         Binding source();

    }

    public interface CustomRollupPropertiesColumn {

         Binding source();


    }

    public interface ForeignKeyColumns {

         List<ForeignKeyColumn> foreignKeyColumn();


        public interface ForeignKeyColumn {

             Binding source();


        }

    }

    public interface KeyColumns {

         List<OutOfLineBinding.KeyColumns.KeyColumn> keyColumn();


        public interface KeyColumn {

             Binding source();

        }

    }

    public interface NameColumn {

         Binding source();

    }


    public interface SkippedLevelsColumn {

         Binding source();


    }

    public interface Translations {

         List<OutOfLineBinding.Translations.Translation> translation();

        public interface Translation {

             int language();
             Binding source();


        }

    }

    public interface UnaryOperatorColumn {

         Binding source();
    }

    public interface ValueColumn {

         Binding source();

    }

}
