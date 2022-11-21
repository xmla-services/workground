/*********************************************************************
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 **********************************************************************/

package org.eclipse.daanse.core.api.olap;

import org.olap4j.mdx.IdentifierSegment;

/**
 * Naming context within which elements are defined.
 *
 * <p>Elements' names are hierarchical, so elements are resolved one
 * name segment at a time. It is possible for an element to be defined
 * in a different namespace than its parent: for example, stored member
 * [Dim].[Hier].[X].[Y] might have a child [Dim].[Hier].[X].[Y].[Z] which
 * is a calculated member defined using a WITH MEMBER clause.</p>
 */
public interface Namespace {
    /**
     * Looks up a child element, using a match type for inexact matching.
     *
     * <p>If {@code matchType} is {@link MatchType#EXACT}, effect is
     * identical to calling
     * {@link #lookupChild(OlapElement, org.olap4j.mdx.IdentifierSegment)}.</p>
     *
     * <p>Match type is ignored except when searching for members.</p>
     *
     * @param parent Parent element
     * @param segment Name segment
     * @param matchType Match type
     * @return Olap element, or null
     */
    OlapElement lookupChild(
        OlapElement parent,
        IdentifierSegment segment,
        MatchType matchType);

    /**
     * Looks up a child element.
     *
     * @param parent Parent element
     * @param segment Name segment
     * @return Olap element, or null
     */
    OlapElement lookupChild(
        OlapElement parent,
        IdentifierSegment segment);
}
