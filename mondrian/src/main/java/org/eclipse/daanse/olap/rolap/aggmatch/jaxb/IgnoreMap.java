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
import mondrian.rolap.aggmatcher.Recognizer;

import java.util.List;

/**
 * This is the template used to specify columns to be ignored.
 * There are NO template names. One simply uses a regular
 * expression.
 */
@XmlType(name = "IgnoreMap")
@XmlAccessorType(XmlAccessType.FIELD)
public class IgnoreMap extends RegexMapper {

    private static final List<String> TEMPLATE_NAMES = List.of();

    protected List<String> getTemplateNames() {
        return TEMPLATE_NAMES;
    }

    public Recognizer.Matcher getMatcher() {
        return getMatcher(new String[]{});
    }

    @Override
    protected String getName() {
        return "IgnoreMap";
    }
}