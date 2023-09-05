/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package mondrian.mdx;

import mondrian.olap.FormulaImpl;
import mondrian.olap.IdImpl;
import mondrian.olap.LiteralImpl;
import mondrian.olap.QueryImpl;
import mondrian.olap.QueryAxisImpl;
import mondrian.olap.interfaces.DimensionExpr;
import mondrian.olap.interfaces.Formula;
import mondrian.olap.interfaces.Id;
import mondrian.olap.interfaces.LevelExpr;
import mondrian.olap.interfaces.Literal;
import mondrian.olap.interfaces.NamedSetExpr;
import mondrian.olap.interfaces.ParameterExpr;

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
     * @see QueryImpl#accept(MdxVisitor)
     */
    Object visit(QueryImpl query);

    /**
     * Visits a QueryAxis.
     *
     * @see QueryAxisImpl#accept(MdxVisitor)
     */
    Object visit(QueryAxisImpl queryAxis);

    /**
     * Visits a Formula.
     *
     * @see FormulaImpl#accept(MdxVisitor)
     */
    Object visit(Formula formula);

    /**
     * Visits an UnresolvedFunCall.
     *
     * @see UnresolvedFunCallImpl#accept(MdxVisitor)
     */
    Object visit(UnresolvedFunCallImpl call);

    /**
     * Visits a ResolvedFunCall.
     *
     * @see ResolvedFunCallImpl#accept(MdxVisitor)
     */
    Object visit(ResolvedFunCallImpl call);

    /**
     * Visits an Id.
     *
     * @see IdImpl#accept(MdxVisitor)
     */
    Object visit(Id id);

    /**
     * Visits a Parameter.
     *
     * @see ParameterExpr)
     */
    Object visit(ParameterExpr parameterExpr);

    /**
     * Visits a DimensionExpr.
     *
     * @see DimensionExpr(MdxVisitor)
     */
    Object visit(DimensionExpr dimensionExpr);

    /**
     * Visits a HierarchyExpr.
     *
     * @see HierarchyExprImpl#accept(MdxVisitor)
     */
    Object visit(HierarchyExprImpl hierarchyExpr);

    /**
     * Visits a LevelExpr.
     *
     * @see LevelExpr(MdxVisitor)
     */
    Object visit(LevelExpr levelExpr);

    /**
     * Visits a MemberExpr.
     *
     * @see MemberExprImpl#accept(MdxVisitor)
     */
    Object visit(MemberExprImpl memberExpr);

    /**
     * Visits a NamedSetExpr.
     *
     * @see NamedSetExpr)
     */
    Object visit(NamedSetExpr namedSetExpr);

    /**
     * Visits a Literal.
     *
     * @see LiteralImpl#accept(MdxVisitor)
     */
    Object visit(Literal literal);
}
