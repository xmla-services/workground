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

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Base is the base class for all of the elements.
 * All elements can be enabled or not, have a tag, and
 * can be validated.
 */
@XmlType(name = "Base")
@XmlSeeAlso({CaseMatcher.class, RegexMapper.class, Ref.class, AggRule.class})
public abstract class Base {

    public static final String NAME = "Base";
    /**
     * Is this element enabled - if true, then Mondrian can consider
     * using it otherwise it ignored.
     */
    @XmlAttribute(name = "enabled")
    Boolean enabled = true;

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    protected abstract String getTag();

    public abstract void validate(
        final AggRules rules,
        final mondrian.recorder.MessageRecorder msgRecorder
    );

    protected String getName() {
        return NAME;
    }
}

