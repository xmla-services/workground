/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package mondrian.mdx;

import org.eclipse.daanse.olap.api.query.component.DimensionExpression;
import org.eclipse.daanse.olap.api.query.component.Formula;
import org.eclipse.daanse.olap.api.query.component.HierarchyExpression;
import org.eclipse.daanse.olap.api.query.component.Id;
import org.eclipse.daanse.olap.api.query.component.LevelExpression;
import org.eclipse.daanse.olap.api.query.component.Literal;
import org.eclipse.daanse.olap.api.query.component.MemberExpression;
import org.eclipse.daanse.olap.api.query.component.NamedSetExpression;
import org.eclipse.daanse.olap.api.query.component.ParameterExpression;
import org.eclipse.daanse.olap.api.query.component.Query;
import org.eclipse.daanse.olap.api.query.component.QueryAxis;
import org.eclipse.daanse.olap.api.query.component.ResolvedFunCall;
import org.eclipse.daanse.olap.api.query.component.UnresolvedFunCall;
import org.eclipse.daanse.olap.api.query.component.visit.QueryComponentVisitor;

import mondrian.olap.Expression;

/**
 * Default implementation of the visitor interface, {@link QueryComponentVisitor}.
 *
 * <p>The method implementations just ask the child nodes to
 * {@link Expression#accept(QueryComponentVisitor)} this visitor.
 *
 * @author jhyde
 * @since Jul 21, 2006
 */
public class MdxVisitorImpl implements QueryComponentVisitor {
    private boolean shouldVisitChildren = true;

    @Override
	public boolean visitChildren() {
        boolean returnValue = shouldVisitChildren;
        turnOnVisitChildren();
        return returnValue;
    }

    public void turnOnVisitChildren() {
        shouldVisitChildren = true;
    }

    public void turnOffVisitChildren() {
        shouldVisitChildren = false;
    }

    @Override
	public Object visitQuery(Query query) {
        return null;
    }

    @Override
	public Object visitQueryAxis(QueryAxis queryAxis) {
        return null;
    }

    @Override
	public Object visitFormula(Formula formula) {
        return null;
    }

    @Override
	public Object visitUnresolvedFunCall(UnresolvedFunCall call) {
        return null;
    }

    @Override
	public Object visitResolvedFunCall(ResolvedFunCall call) {
        return null;
    }

    @Override
	public Object visitId(Id id) {
        return null;
    }

    @Override
	public Object visitParameterExpression(ParameterExpression parameterExpr) {
        return null;
    }

    @Override
	public Object visitDimensionExpression(DimensionExpression dimensionExpr) {
        // do nothing
        return null;
    }

    @Override
	public Object visitHierarchyExpression(HierarchyExpression hierarchyExpr) {
        // do nothing
        return null;
    }

    @Override
	public Object visitLevelExpression(LevelExpression levelExpr) {
        // do nothing
        return null;
    }

    @Override
	public Object visitMemberExpression(MemberExpression memberExpr) {
        // do nothing
        return null;
    }

    @Override
	public Object visitNamedSetExpression(NamedSetExpression namedSetExpr) {
        // do nothing
        return null;
    }

    @Override
	public Object visitLiteral(Literal<?> literal) {
        // do nothing
        return null;
    }

    /**
     * Visits an array of expressions. Returns the same array if none of the
     * expressions are changed, otherwise a new array.
     *
     * @param args Array of expressions
     * @return Array of visited expressions; same as {@code args} iff none of
     * the expressions are changed.
     */
    protected Expression[] visitArray(Expression[] args) {
        Expression[] newArgs = args;
        for (int i = 0; i < args.length; i++) {
            Expression arg = args[i];
            Expression newArg = (Expression) arg.accept(this);
            if (newArg != arg) {
                if (newArgs == args) {
                    newArgs = args.clone();
                }
                newArgs[i] = newArg;
            }
        }
        return newArgs;
    }
}
