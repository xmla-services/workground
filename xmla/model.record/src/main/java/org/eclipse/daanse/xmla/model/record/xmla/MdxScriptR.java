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
import org.eclipse.daanse.xmla.api.xmla.CalculationProperty;
import org.eclipse.daanse.xmla.api.xmla.Command;
import org.eclipse.daanse.xmla.api.xmla.MdxScript;

import java.time.Instant;
import java.util.List;

public record MdxScriptR(String name,
                         String id,
                         Instant createdTimestamp,
                         Instant lastSchemaUpdate,
                         String description,
                         MdxScriptR.Annotations annotations,
                         MdxScriptR.Commands commands,
                         Boolean defaultScript,
                         MdxScriptR.CalculationProperties calculationProperties) implements MdxScript {

    record Annotations(List<Annotation> annotation) implements MdxScript.Annotations {

    }

    record CalculationProperties(
        List<CalculationProperty> calculationProperty) implements MdxScript.CalculationProperties {

    }

    record Commands(List<Command> command) implements MdxScript.Commands {

    }

}
