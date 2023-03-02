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
import org.eclipse.daanse.xmla.api.xmla.Binding;
import org.eclipse.daanse.xmla.api.xmla.OutOfLineBinding;

import java.util.List;

public record OutOfLineBindingR(String databaseID,
                                String dimensionID,
                                String cubeID,
                                String measureGroupID,
                                String partitionID,
                                String miningModelID,
                                String miningStructureID,
                                String attributeID,
                                String cubeDimensionID,
                                String measureID,
                                String parentColumnID,
                                String columnID,
                                Binding source,
                                OutOfLineBinding.NameColumn nameColumn,
                                OutOfLineBinding.SkippedLevelsColumn skippedLevelsColumn,
                                OutOfLineBinding.CustomRollupColumn customRollupColumn,
                                OutOfLineBinding.CustomRollupPropertiesColumn customRollupPropertiesColumn,
                                OutOfLineBinding.ValueColumn valueColumn,
                                OutOfLineBinding.UnaryOperatorColumn unaryOperatorColumn,
                                OutOfLineBinding.KeyColumns keyColumns,
                                OutOfLineBinding.ForeignKeyColumns foreignKeyColumns,
                                OutOfLineBinding.Translations translations) implements OutOfLineBinding {

    public record CustomRollupColumn(Binding source) implements OutOfLineBinding.CustomRollupColumn {

    }

    public record CustomRollupPropertiesColumn(
        Binding source) implements OutOfLineBinding.CustomRollupPropertiesColumn {

    }

    public record ForeignKeyColumns(
        List<OutOfLineBinding.ForeignKeyColumns.ForeignKeyColumn> foreignKeyColumn) implements OutOfLineBinding.ForeignKeyColumns {

        public record ForeignKeyColumn(Binding source) implements OutOfLineBinding.ForeignKeyColumns.ForeignKeyColumn {

        }

    }

    public record KeyColumns(
        List<OutOfLineBinding.KeyColumns.KeyColumn> keyColumn) implements OutOfLineBinding.KeyColumns {

        public record KeyColumn(Binding source) implements OutOfLineBinding.KeyColumns.KeyColumn {

        }

    }

    public record NameColumn(Binding source) implements OutOfLineBinding.NameColumn {

    }

    public record SkippedLevelsColumn(Binding source) implements OutOfLineBinding.SkippedLevelsColumn {

    }

    public record Translations(
        List<OutOfLineBinding.Translations.Translation> translation) implements OutOfLineBinding.Translations {

        public record Translation(int language,
                                  Binding source
        ) implements OutOfLineBinding.Translations.Translation {

        }

    }

    public record UnaryOperatorColumn(Binding source) implements OutOfLineBinding.UnaryOperatorColumn {

    }

    public record ValueColumn(Binding source) implements OutOfLineBinding.ValueColumn {

    }

}
