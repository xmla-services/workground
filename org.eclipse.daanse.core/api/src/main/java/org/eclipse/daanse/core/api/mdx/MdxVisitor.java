/*
 * This software is subject to the terms of the Eclipse Public License v1.0
 * Agreement, available at the following URL:
 * http://www.eclipse.org/legal/epl-v10.html.
 * You must accept the terms of that agreement to use this software.
 *
 * Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
 */

package org.eclipse.daanse.core.api.mdx;

import org.eclipse.daanse.core.api.olap.*;

/**
 * Interface for a visitor to an MDX parse tree.
 *
 * @author jhyde
 * @since Jul 21, 2006
 */
public interface MdxVisitor {
    /**
     * @return Indicates whether the visitee should call accept on it's children
     */
    boolean shouldVisitChildren();

    /**
     * Visits a Query.
     *
     * @see IQuery#accept(MdxVisitor)
     */
    Object visit(IQuery query);

    /**
     * Visits a QueryAxis.
     *
     * @see IQueryAxis#accept(MdxVisitor)
     */
    Object visit(IQueryAxis queryAxis);

    /**
     * Visits a Formula.
     *
     * @see IFormula#accept(MdxVisitor)
     */
    Object visit(IFormula formula);

    /**
     * Visits an UnresolvedFunCall.
     *
     * @see IUnresolvedFunCall#accept(MdxVisitor)
     */
    Object visit(IUnresolvedFunCall call);

    /**
     * Visits a ResolvedFunCall.
     *
     * @see IResolvedFunCall#accept(MdxVisitor)
     */
    Object visit(IResolvedFunCall call);

    /**
     * Visits an Id.
     *
     * @see IId#accept(MdxVisitor)
     */
    Object visit(IId id);

    /**
     * Visits a Parameter.
     *
     * @see IParameterExpr#accept(MdxVisitor)
     */
    Object visit(IParameterExpr parameterExpr);

    /**
     * Visits a DimensionExpr.
     *
     * @see IDimensionExpr#accept(MdxVisitor)
     */
    Object visit(IDimensionExpr dimensionExpr);

    /**
     * Visits a HierarchyExpr.
     *
     * @see IHierarchyExpr#accept(MdxVisitor)
     */
    Object visit(IHierarchyExpr hierarchyExpr);

    /**
     * Visits a LevelExpr.
     *
     * @see ILevelExpr#accept(MdxVisitor)
     */
    Object visit(ILevelExpr levelExpr);

    /**
     * Visits a MemberExpr.
     *
     * @see IMemberExpr#accept(MdxVisitor)
     */
    Object visit(IMemberExpr memberExpr);

    /**
     * Visits a NamedSetExpr.
     *
     * @see INamedSetExpr#accept(MdxVisitor)
     */
    Object visit(INamedSetExpr namedSetExpr);

    /**
     * Visits a Literal.
     *
     * @see ILiteral#accept(MdxVisitor)
     */
    Object visit(ILiteral literal);
}

// End MdxVisitor.java
