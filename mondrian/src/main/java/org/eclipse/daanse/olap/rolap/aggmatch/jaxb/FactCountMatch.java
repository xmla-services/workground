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
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;
import mondrian.rolap.aggmatcher.Recognizer;

/**
 * This is used to identify the "fact_count" column in an aggregate
 * table. It allows one to match using regular exprssions.
 * The default is that the name of the fact count colum is simply
 * the string "fact_count".
 */
@XmlType(name = "FactCountMatch")
@XmlAccessorType(XmlAccessType.FIELD)
public class FactCountMatch extends NameMatcher {

    /**
     * The "base" name for a fact count column.
     */
    @XmlAttribute(name = "factCountName", required = true)
    String factCountName = "fact_count";

    @Override
    public void validate(
        final AggRules rules,
        final mondrian.recorder.MessageRecorder msgRecorder
    ) {
        msgRecorder.pushContextName(getName());
        try {
            super.validate(rules, msgRecorder);
        } finally {
            msgRecorder.popContextName();
        }
    }

    public Recognizer.Matcher getMatcher() {
        return super.getMatcher(factCountName);
    }

    @Override
    protected String getName() {
        return "FactCountMatch";
    }
}