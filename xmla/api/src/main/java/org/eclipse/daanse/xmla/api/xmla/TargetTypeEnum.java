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

/**
 * The type of object to which this Action applies. Such objects are limited
 * to those in the enumeration that is specified in the XSD. The following
 * objects are allowed:
 * "Cube": A Cube object.
 * "Cells": A subcube. Subcubes are created by using MDX [MSDN-
 * CREATESUBCUBE].
 * "Set": A set. Sets are created by using MDX [MSDN-CREATESET].
 * "Hierarchy": A Hierarchy object.
 * "Level": A Level object.
 * "DimensionMembers": The members of a Dimension.
 * "HierarchyMembers": The members of a Hierarchy.
 * "LevelMembers": The members of a Level.
 * "AttributeMembers": The members of a DimensionAttribute.
 */
public enum TargetTypeEnum {

    /**
     * A Cube object.
     */
    Cube,

    /**
     * A subcube. Subcubes are created by using MDX [MSDN-
     * CREATESUBCUBE].
     */
    Cells,

    /**
     * A Hierarchy object.
     */
    Hierarchy,

    /**
     * A Level object.
     */
    Level,

    /**
     * The members of a Dimension.
     */
    DimensionMembers,

    /**
     * The members of a Hierarchy.
     */
    HierarchyMembers,

    /**
     * The members of a Level.
     */
    LevelMembers,

    /**
     * The members of a DimensionAttribute
     */
    AttributeMembers;

    public static TargetTypeEnum fromValue(String v) {
        if (v == null) {
            return null;
        }
        for (TargetTypeEnum e : TargetTypeEnum.values()) {
            if (e.name().equals(v)) {
                return e;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("AccessEnum Illegal argument ")
            .append(v).toString());
    }

}
