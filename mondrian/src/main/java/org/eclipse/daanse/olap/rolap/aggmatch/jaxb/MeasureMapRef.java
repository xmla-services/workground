/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.olap.rolap.aggmatch.jaxb;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "MeasureMapRef")
@XmlAccessorType(XmlAccessType.FIELD)
public class MeasureMapRef extends Ref {

    public void validate(
        final AggRules rules,
        final mondrian.recorder.MessageRecorder msgRecorder
    ) {
        msgRecorder.pushContextName(getName());
        try {
            if (!rules.hasMeasureMap(getRefId())) {
                String msg = "No MeasureMap has id equal to refid \"" +
                    getRefId() +
                    "\"";
                msgRecorder.reportError(msg);
            }
        } finally {
            msgRecorder.popContextName();
        }
    }

    @Override
    protected String getName() {
        return "MeasureMapRef";
    }
}