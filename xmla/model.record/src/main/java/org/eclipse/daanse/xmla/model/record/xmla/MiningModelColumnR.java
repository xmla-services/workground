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
import org.eclipse.daanse.xmla.api.xmla.MiningModelColumn;
import org.eclipse.daanse.xmla.api.xmla.MiningModelingFlag;
import org.eclipse.daanse.xmla.api.xmla.Translation;

import java.util.List;

public record MiningModelColumnR(String name,
                                 String id,
                                 String description,
                                 String sourceColumnID,
                                 String usage,
                                 String filter,
                                 MiningModelColumnR.Translations translations,
                                 MiningModelColumnR.Columns columns,
                                 MiningModelColumnR.ModelingFlags modelingFlags,
                                 MiningModelColumnR.Annotations annotations) implements MiningModelColumn {

    public record Annotations(List<Annotation> annotation) implements MiningModelColumn.Annotations {

    }

    public record Columns(List<MiningModelColumn> column) implements MiningModelColumn.Columns {

    }

    public record ModelingFlags(List<MiningModelingFlag> modelingFlag) implements MiningModelColumn.ModelingFlags {

    }

    public record Translations(List<Translation> translation) implements MiningModelColumn.Translations {

    }

}
