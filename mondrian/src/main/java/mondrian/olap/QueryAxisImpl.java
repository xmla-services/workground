/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 1998-2005 Julian Hyde
// Copyright (C) 2005-2017 Hitachi Vantara and others
// All Rights Reserved.
*/

package mondrian.olap;

import java.io.PrintWriter;

import org.eclipse.daanse.olap.api.SubtotalVisibility;
import org.eclipse.daanse.olap.api.Syntax;
import org.eclipse.daanse.olap.api.Validator;
import org.eclipse.daanse.olap.api.element.Level;
import org.eclipse.daanse.olap.api.query.component.AxisOrdinal;
import org.eclipse.daanse.olap.api.query.component.DimensionExpression;
import org.eclipse.daanse.olap.api.query.component.Expression;
import org.eclipse.daanse.olap.api.query.component.FunctionCall;
import org.eclipse.daanse.olap.api.query.component.Id;
import org.eclipse.daanse.olap.api.query.component.LevelExpression;
import org.eclipse.daanse.olap.api.query.component.QueryAxis;
import org.eclipse.daanse.olap.api.query.component.visit.QueryComponentVisitor;
import org.eclipse.daanse.olap.api.type.Type;
import org.eclipse.daanse.olap.calc.api.Calc;
import org.eclipse.daanse.olap.calc.api.ResultStyle;
import org.eclipse.daanse.olap.calc.api.compiler.ExpressionCompiler;

import mondrian.mdx.HierarchyExpressionImpl;
import mondrian.mdx.LevelExpressionImpl;
import mondrian.mdx.UnresolvedFunCallImpl;
import mondrian.olap.type.DimensionType;
import mondrian.olap.type.HierarchyType;
import mondrian.olap.type.MemberType;
import mondrian.olap.type.TupleType;
import mondrian.olap.type.TypeUtil;

import static mondrian.resource.MondrianResource.message;
import static mondrian.resource.MondrianResource.MdxAxisIsNotSet;

/**
 * An axis in an MDX query. For example, the typical MDX query has two axes,
 * which appear as the "ON COLUMNS" and "ON ROWS" clauses.
 *
 * @author jhyde, 20 January, 1999
 */
public class QueryAxisImpl extends AbstractQueryPart implements QueryAxis {

    private boolean nonEmpty;
    private boolean ordered;
    private Expression exp;
    private final AxisOrdinal axisOrdinal;

    /**
     * Whether to show subtotals on this axis.
     * The "(show\hide)Subtotals" operation changes its valud.
     */
    private SubtotalVisibility subtotalVisibility;
    private final Id[] dimensionProperties;

    /**
     * Creates an axis.
     *
     * @param nonEmpty Whether to filter out members of this axis whose cells
     *    are all empty
     * @param set Expression to populate the axis
     * @param axisOrdinal Which axis (ROWS, COLUMNS, etc.)
     * @param subtotalVisibility Whether to show subtotals
     * @param dimensionProperties List of dimension properties
     */
    public QueryAxisImpl(
        boolean nonEmpty,
        Expression set,
        AxisOrdinal axisOrdinal,
        SubtotalVisibility subtotalVisibility,
        Id[] dimensionProperties)
    {
        if (dimensionProperties == null || axisOrdinal == null) {
            throw new IllegalArgumentException("QueryAxis: dimensionProperties and axisOrdinal should not be null");
        }
        this.nonEmpty = nonEmpty
            || (SystemWideProperties.instance().EnableNonEmptyOnAllAxis
            && !axisOrdinal.isFilter());
        this.exp = set;
        this.axisOrdinal = axisOrdinal;
        this.subtotalVisibility = subtotalVisibility;
        this.dimensionProperties = dimensionProperties;
        this.ordered = false;
    }

    /**
     * Creates an axis with no dimension properties.
     *
     * @see QueryAxis(boolean,Exp,AxisOrdinal,mondrian.olap.QueryAxisImpl.SubtotalVisibility,Id[])
     */
    public QueryAxisImpl(
        boolean nonEmpty,
        Expression set,
        AxisOrdinal axisOrdinal,
        SubtotalVisibility subtotalVisibility)
    {
        this(nonEmpty, set, axisOrdinal, subtotalVisibility, new Id[0]);
    }

    public QueryAxisImpl(QueryAxis queryAxis) {
        this(
            queryAxis.isNonEmpty(), queryAxis.getSet().cloneExp(), queryAxis.getAxisOrdinal(),
            queryAxis.getSubtotalVisibility(), queryAxis.getDimensionProperties().clone());
    }

    static QueryAxis[] cloneArray(QueryAxis[] a) {
        QueryAxisImpl[] a2 = new QueryAxisImpl[a.length];
        for (int i = 0; i < a.length; i++) {
            a2[i] = new QueryAxisImpl(a[i]);
        }
        return a2;
    }

    public Object accept(QueryComponentVisitor visitor) {
        final Object o = visitor.visitQueryAxis(this);

        if (visitor.visitChildren()) {
            // visit the expression which forms the axis
            exp.accept(visitor);
        }
        return o;
    }

    @Override
    public Calc compile(ExpressionCompiler compiler, ResultStyle resultStyle) {
        Expression expInner = this.exp;
        if (axisOrdinal.isFilter()) {
            expInner = normalizeSlicerExpression(expInner);
            expInner = expInner.accept(compiler.getValidator());
        }
        switch (resultStyle) {
        case LIST:
            return compiler.compileList(expInner, false);
        case MUTABLE_LIST:
            return compiler.compileList(expInner, true);
        case ITERABLE:
            return compiler.compileIter(expInner);
        default:
            throw Util.unexpected(resultStyle);
        }
    }

    private static Expression normalizeSlicerExpression(Expression exp) {
        Expression slicer = exp;
        if (slicer instanceof LevelExpression
            || slicer instanceof HierarchyExpressionImpl
            || slicer instanceof DimensionExpression)
        {
            slicer = new UnresolvedFunCallImpl(
                "DefaultMember", Syntax.Property, new Expression[] {
                    slicer});
        }

        if (slicer instanceof FunctionCall funCall
            && funCall.getSyntax() == Syntax.Parentheses)
        {
            slicer =
                new UnresolvedFunCallImpl(
                    "{}", Syntax.Braces, new Expression[] {slicer});
        } else {
            slicer =
                new UnresolvedFunCallImpl(
                    "{}", Syntax.Braces, new Expression[] {
                        new UnresolvedFunCallImpl(
                            "()", Syntax.Parentheses, new Expression[] {
                                slicer})});
        }

        return slicer;
    }

    @Override
    public String getAxisName() {
        return axisOrdinal.name();
    }

    /**
     * Returns the ordinal of this axis, for example
     * {@link org.eclipse.daanse.olap.api.query.component.AxisOrdinal.StandardAxisOrdinal#ROWS}.
     */
    @Override
    public AxisOrdinal getAxisOrdinal() {
        return axisOrdinal;
    }

    /**
     * Returns whether the axis has the <code>NON EMPTY</code> property set.
     */
    @Override
    public boolean isNonEmpty() {
        return nonEmpty;
    }

    /**
     * Sets whether the axis has the <code>NON EMPTY</code> property set.
     * See {@link #isNonEmpty()}.
     */
    public void setNonEmpty(boolean nonEmpty) {
        this.nonEmpty = nonEmpty;
    }

     /**
     * Returns whether the axis has the <code>ORDER</code> property set.
     */
    @Override
    public boolean isOrdered() {
        return ordered;
    }

    /**
     * Sets whether the axis has the <code>ORDER</code> property set.
     */
    @Override
    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }

    /**
     * Returns the expression which is used to compute the value of this axis.
     */
    @Override
    public Expression getSet() {
        return exp;
    }



    /**
     * Sets the expression which is used to compute the value of this axis.
     * See {@link #getSet()}.
     */
    @Override
    public void setSet(Expression set) {
        this.exp = set;
    }

    @Override
    public void resolve(Validator validator) {
        exp = validator.validate(exp, false);
        final Type type = exp.getType();
        if (!TypeUtil.isSet(type)) {
            // If expression is a member or a tuple, implicitly convert it
            // into a set. Dimensions and hierarchies can be converted to
            // members, thence to sets.
            if (type instanceof MemberType
                || type instanceof TupleType
                || type instanceof DimensionType
                || type instanceof HierarchyType)
            {
                exp =
                    new UnresolvedFunCallImpl(
                        "{}",
                        Syntax.Braces,
                        new Expression[] {exp});
                exp = validator.validate(exp, false);
            } else {
                throw new MondrianException(message(MdxAxisIsNotSet,
                    axisOrdinal.name()));
            }
        }
    }

    @Override
	public Object[] getChildren() {
        return new Object[] {exp};
    }

    @Override
	public void unparse(PrintWriter pw) {
        if (nonEmpty) {
            pw.print("NON EMPTY ");
        }
        if (exp != null) {
            exp.unparse(pw);
        }
        if (dimensionProperties.length > 0) {
            pw.print(" DIMENSION PROPERTIES ");
            for (int i = 0; i < dimensionProperties.length; i++) {
                Id dimensionProperty = dimensionProperties[i];
                if (i > 0) {
                    pw.print(", ");
                }
                dimensionProperty.unparse(pw);
            }
        }
        if (!axisOrdinal.isFilter()) {
            pw.print(" ON " + axisOrdinal.name());
        }
    }

    @Override
    public void addLevel(Level level) {
        Util.assertTrue(level != null, "addLevel needs level");
        exp = new UnresolvedFunCallImpl(
            "Crossjoin", Syntax.Function, new Expression[] {
                exp,
                new UnresolvedFunCallImpl(
                    "Members", Syntax.Property, new Expression[] {
                        new LevelExpressionImpl(level)})});
    }

    public SubtotalVisibility getSubtotalVisibility() {
        return subtotalVisibility;
    }

    @Override
    public void validate(Validator validator) {
        if (axisOrdinal.isFilter() && exp != null) {
            exp = validator.validate(exp, false);
        }
    }

    public Id[] getDimensionProperties() {
        return dimensionProperties;
    }


}
