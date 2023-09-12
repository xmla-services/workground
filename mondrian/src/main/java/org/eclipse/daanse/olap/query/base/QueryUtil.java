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
package org.eclipse.daanse.olap.query.base;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.daanse.mdx.model.api.expression.CallExpression;
import org.eclipse.daanse.mdx.model.api.expression.CompoundId;
import org.eclipse.daanse.mdx.model.api.expression.Expression;
import org.eclipse.daanse.mdx.model.api.expression.KeyObjectIdentifier;
import org.eclipse.daanse.mdx.model.api.expression.Literal;
import org.eclipse.daanse.mdx.model.api.expression.NameObjectIdentifier;
import org.eclipse.daanse.mdx.model.api.expression.NullLiteral;
import org.eclipse.daanse.mdx.model.api.expression.NumericLiteral;
import org.eclipse.daanse.mdx.model.api.expression.ObjectIdentifier;
import org.eclipse.daanse.mdx.model.api.expression.StringLiteral;
import org.eclipse.daanse.mdx.model.api.expression.SymbolLiteral;
import org.eclipse.daanse.mdx.model.api.select.Axis;
import org.eclipse.daanse.mdx.model.api.select.CreateMemberBodyClause;
import org.eclipse.daanse.mdx.model.api.select.CreateSetBodyClause;
import org.eclipse.daanse.mdx.model.api.select.SelectCellPropertyListClause;
import org.eclipse.daanse.mdx.model.api.select.SelectDimensionPropertyListClause;
import org.eclipse.daanse.mdx.model.api.select.SelectQueryAsteriskClause;
import org.eclipse.daanse.mdx.model.api.select.SelectQueryAxesClause;
import org.eclipse.daanse.mdx.model.api.select.SelectQueryAxisClause;
import org.eclipse.daanse.mdx.model.api.select.SelectQueryClause;
import org.eclipse.daanse.mdx.model.api.select.SelectQueryEmptyClause;
import org.eclipse.daanse.mdx.model.api.select.SelectSlicerAxisClause;
import org.eclipse.daanse.mdx.model.api.select.SelectSubcubeClause;
import org.eclipse.daanse.mdx.model.api.select.SelectSubcubeClauseName;
import org.eclipse.daanse.mdx.model.api.select.SelectSubcubeClauseStatement;
import org.eclipse.daanse.mdx.model.api.select.SelectWithClause;
import org.eclipse.daanse.olap.api.query.component.CellProperty;
import org.eclipse.daanse.olap.api.query.component.Formula;
import org.eclipse.daanse.olap.api.query.component.Id;
import org.eclipse.daanse.olap.api.query.component.Subcube;

import mondrian.mdx.UnresolvedFunCallImpl;
import mondrian.olap.AxisOrdinal;
import mondrian.olap.CellPropertyImpl;
import mondrian.olap.NumericLiteralImpl;
import mondrian.olap.Exp;
import mondrian.olap.FormulaImpl;
import mondrian.olap.IdImpl;
import mondrian.olap.NullLiteralImpl;
import mondrian.olap.QueryAxisImpl;
import mondrian.olap.StringLiteralImpl;
import mondrian.olap.SubcubeImpl;
import mondrian.olap.SymbolLiteralImpl;
import mondrian.olap.Syntax;
import mondrian.olap.api.NameSegment;
import mondrian.olap.api.Quoting;
import mondrian.olap.api.Segment;
import mondrian.olap.api.SubtotalVisibility;

public class QueryUtil {

    private QueryUtil() {
        // constructor
    }

    static List<String> getColumns(List<? extends ObjectIdentifier> objectIdentifiers) {
        List<String> columns = new ArrayList<>();
        if (objectIdentifiers != null) {
            objectIdentifiers.forEach(oi -> {
                if (oi instanceof KeyObjectIdentifier keyObjectIdentifier){
                    List<? extends NameObjectIdentifier> l = keyObjectIdentifier.nameObjectIdentifiers();
                    if (l != null) {
                        l.forEach(QueryUtil::getName);
                    }
                }
                if (oi instanceof NameObjectIdentifier nameObjectIdentifier){
                    columns.add(getName(nameObjectIdentifier));
                }
            });
        }
        return columns;
    }

    static List<CellProperty> getParameterList(Optional<SelectCellPropertyListClause> selectCellPropertyListClauseOptional) {
        if (selectCellPropertyListClauseOptional.isPresent()) {
            SelectCellPropertyListClause selectCellPropertyListClause = selectCellPropertyListClauseOptional.get();
            if (selectCellPropertyListClause.properties() != null) {
                List<Segment> list = selectCellPropertyListClause.properties()
                    .stream().map(p -> ((Segment) new IdImpl.NameSegmentImpl(p))).toList();
                return List.of(new CellPropertyImpl(list));
            }
        }
        return List.of();
    }

    static QueryAxisImpl getQueryAxis(Optional<SelectSlicerAxisClause> selectSlicerAxisClauseOptional) {
        if (selectSlicerAxisClauseOptional.isPresent()) {
            SelectSlicerAxisClause selectSlicerAxisClause = selectSlicerAxisClauseOptional.get();
            Expression expression = selectSlicerAxisClause.expression();
            Exp exp = getExp(expression);
            boolean nonEmpty = false;
            Id[] dimensionProperties = new Id[0];

            new QueryAxisImpl(
                nonEmpty,
                exp,
                AxisOrdinal.StandardAxisOrdinal.SLICER,
                SubtotalVisibility.Undefined,
                dimensionProperties);
        }
        return null;
    }

    static List<QueryAxisImpl> getQueryAxisList(SelectQueryClause selectQueryClause) {
        if (selectQueryClause instanceof SelectQueryAsteriskClause) {
            return selectQueryAsteriskClauseToQueryAxisList();
        }
        if (selectQueryClause instanceof SelectQueryAxesClause selectQueryAxesClause) {
            return selectQueryAxesClauseToQueryAxisList(selectQueryAxesClause);
        }
        if (selectQueryClause instanceof SelectQueryEmptyClause) {
            return selectQueryEmptyClauseToQueryAxisList();
        }
        return List.of();
    }

    static List<QueryAxisImpl> selectQueryEmptyClauseToQueryAxisList() {
        return List.of();
    }

    static List<QueryAxisImpl> selectQueryAxesClauseToQueryAxisList(SelectQueryAxesClause selectQueryAxesClause) {
        if (selectQueryAxesClause.selectQueryAxisClauses() != null) {
            return selectQueryAxesClause.selectQueryAxisClauses().stream()
                .map(QueryUtil::selectQueryAxisClauseToQueryAxisList).toList();
        }
        return List.of();
    }

    static QueryAxisImpl selectQueryAxisClauseToQueryAxisList(SelectQueryAxisClause selectQueryAxisClause) {
        Exp exp = getExp(selectQueryAxisClause.expression());
        AxisOrdinal axisOrdinal = getAxisOrdinal(selectQueryAxisClause.axis());
        List<IdImpl> dimensionProperties =
            getDimensionProperties(selectQueryAxisClause.selectDimensionPropertyListClause());

        return new QueryAxisImpl(
            selectQueryAxisClause.nonEmpty(),
            exp,
            axisOrdinal,
            SubtotalVisibility.Undefined,
            dimensionProperties.stream().toArray(IdImpl[]::new)
        );
    }

    static List<IdImpl> getDimensionProperties(SelectDimensionPropertyListClause selectDimensionPropertyListClause) {
        if (selectDimensionPropertyListClause != null && selectDimensionPropertyListClause.properties() != null) {
            return selectDimensionPropertyListClause.properties().stream()
                .map(QueryUtil::getExpByCompoundId).toList();
        }
        return List.of();
    }

    static AxisOrdinal getAxisOrdinal(Axis axis) {
        return AxisOrdinal.StandardAxisOrdinal.forLogicalOrdinal(axis.ordinal());
    }

    static List<QueryAxisImpl> selectQueryAsteriskClauseToQueryAxisList() {
        return List.of();
    }

    static Subcube getSubcube(SelectSubcubeClause selectSubcubeClause) {
        if (selectSubcubeClause instanceof SelectSubcubeClauseName selectSubcubeClauseName) {
            return getSubcubeBySelectSubcubeClauseName(selectSubcubeClauseName);
        }
        if (selectSubcubeClause instanceof SelectSubcubeClauseStatement selectSubcubeClauseStatement) {
            return getSubcubeBySelectSubcubeClauseStatement(selectSubcubeClauseStatement);
        }
        return null;
    }

    static Subcube getSubcubeBySelectSubcubeClauseStatement(SelectSubcubeClauseStatement selectSubcubeClauseStatement) {
        Subcube subcube = getSubcube(selectSubcubeClauseStatement.selectSubcubeClause());
        List<QueryAxisImpl> axes = getQueryAxisList(selectSubcubeClauseStatement.selectQueryClause());
        String cubeName = null;
        QueryAxisImpl slicerAxis = getQueryAxis(selectSubcubeClauseStatement.selectSlicerAxisClause());
        return new SubcubeImpl(cubeName, subcube, axes.stream().toArray(QueryAxisImpl[]::new), slicerAxis);
    }

    static Subcube getSubcubeBySelectSubcubeClauseName(SelectSubcubeClauseName selectSubcubeClauseName) {
        NameObjectIdentifier nameObjectIdentifier = selectSubcubeClauseName.cubeName();
        String cubeName = getName(nameObjectIdentifier);
        return new SubcubeImpl(cubeName, null, null, null);
    }

    static List<Formula> getFormulaList(List<? extends SelectWithClause> selectWithClauses) {
        List<Formula> formulaList = new ArrayList<>();
        if (selectWithClauses != null) {
            for (SelectWithClause swc : selectWithClauses) {
                if (swc instanceof CreateMemberBodyClause createMemberBodyClause) {
                    IdImpl id = getId(createMemberBodyClause.compoundId());
                    Exp exp = getExp(createMemberBodyClause.expression());
                    formulaList.add(new FormulaImpl(id, exp));
                }
                if (swc instanceof CreateSetBodyClause createSetBodyClause) {
                    IdImpl id = getId(createSetBodyClause.compoundId());
                    Exp exp = getExp(createSetBodyClause.expression());
                    formulaList.add(new FormulaImpl(id, exp));
                }
            }
        }
        return formulaList;
    }

    static Exp getExp(Expression expression) {
        if (expression instanceof CallExpression callExpression) {
            return getExpByCallExpression(callExpression);
        }
        if (expression instanceof Literal literal) {
            return getExpByLiteral(literal);
        }
        if (expression instanceof CompoundId compoundId) {
            return getExpByCompoundId(compoundId);
        }
        if (expression instanceof ObjectIdentifier objectIdentifier) {
            return getExpByObjectIdentifier(objectIdentifier);
        }
        return null;
    }

    static Exp getExpByObjectIdentifier(ObjectIdentifier objectIdentifier) {
        if (objectIdentifier instanceof KeyObjectIdentifier keyObjectIdentifier) {
            return new IdImpl(getExpByKeyObjectIdentifier(keyObjectIdentifier));
        }
        if (objectIdentifier instanceof NameObjectIdentifier nameObjectIdentifier) {
            return new IdImpl(getExpByNameObjectIdentifier(nameObjectIdentifier));
        }
        return null;
    }

    static Segment getExpByNameObjectIdentifier(NameObjectIdentifier nameObjectIdentifier) {
        switch (nameObjectIdentifier.quoting()) {
            case QUOTED:
                return new IdImpl.NameSegmentImpl(nameObjectIdentifier.name(), Quoting.QUOTED);
            case UNQUOTED:
                return new IdImpl.NameSegmentImpl(nameObjectIdentifier.name(), Quoting.UNQUOTED);
            case KEY:
                return new IdImpl.KeySegment(new IdImpl.NameSegmentImpl(nameObjectIdentifier.name()));
        }
        return null;
    }

    static List<Segment> getExpByKeyObjectIdentifier(KeyObjectIdentifier keyObjectIdentifier) {
        return keyObjectIdentifier.nameObjectIdentifiers()
            .stream().map(QueryUtil::getExpByNameObjectIdentifier).toList();
    }

    static IdImpl getExpByCompoundId(CompoundId compoundId) {
        List<Segment> list = new ArrayList<>();
        compoundId.objectIdentifiers().forEach(it ->
            {
                if (it instanceof KeyObjectIdentifier keyObjectIdentifier) {
                    list.addAll(getExpByKeyObjectIdentifier(keyObjectIdentifier));
                }
                if (it instanceof NameObjectIdentifier nameObjectIdentifier) {
                    list.add(getExpByNameObjectIdentifier(nameObjectIdentifier));
                }
            }
        );
        return new IdImpl(list);
    }

    static Exp getExpByLiteral(Literal literal) {
        if (literal instanceof NumericLiteral numericLiteral) {
            return NumericLiteralImpl.create(numericLiteral.value());
        }
        if (literal instanceof StringLiteral stringLiteral) {
            return StringLiteralImpl.create(stringLiteral.value());
        }
        if (literal instanceof NullLiteral) {
            return NullLiteralImpl.nullValue;
        }
        if (literal instanceof SymbolLiteral symbolLiteral) {
            return SymbolLiteralImpl.create(symbolLiteral.value());
        }
        return null;
    }

    static Exp getExpByCallExpression(CallExpression callExpression) {
        Exp x;
        Exp y;
        List<Exp> list;
        switch (callExpression.type()) {
            case TERM_INFIX:
                x = getExp(callExpression.expressions().get(0));
                y = getExp(callExpression.expressions().get(1));
                return new UnresolvedFunCallImpl(callExpression.name(), Syntax.Infix, new Exp[]{x, y});
            case TERM_PREFIX:
                x = getExp(callExpression.expressions().get(0));
                return new UnresolvedFunCallImpl(callExpression.name(), Syntax.Prefix, new Exp[]{x});
            case FUNCTION:
                list = getExpList(callExpression.expressions());
                return new UnresolvedFunCallImpl(callExpression.name(), Syntax.Function, list.stream().toArray(Exp[]::new));
            case PROPERTY:
                x = getExp(callExpression.expressions().get(0));
                return new UnresolvedFunCallImpl(callExpression.name(), Syntax.Property, new Exp[]{x});
            case PROPERTY_QUOTED:
                x = getExp(callExpression.expressions().get(0));
                return new UnresolvedFunCallImpl(callExpression.name(), Syntax.QuotedProperty, new Exp[]{x});
            case PROPERTY_AMPERS_AND_QUOTED:
                x = getExp(callExpression.expressions().get(0));
                return new UnresolvedFunCallImpl(callExpression.name(), Syntax.AmpersandQuotedProperty, new Exp[]{x});
            case METHOD:
                list = getExpList(callExpression.expressions());
                return new UnresolvedFunCallImpl(callExpression.name(), Syntax.Method, list.stream().toArray(Exp[]::new));
            case BRACES:
                list = getExpList(callExpression.expressions());
                return new UnresolvedFunCallImpl(callExpression.name(), Syntax.Braces, list.stream().toArray(Exp[]::new));
            case PARENTHESES:
                list = getExpList(callExpression.expressions());
                return new UnresolvedFunCallImpl(callExpression.name(), Syntax.Parentheses, list.stream().toArray(Exp[]::new));
            case INTERNAL:
                list = getExpList(callExpression.expressions());
                return new UnresolvedFunCallImpl(callExpression.name(), Syntax.Internal, list.stream().toArray(Exp[]::new));
            case EMPTY:
                return new UnresolvedFunCallImpl(callExpression.name(), Syntax.Empty, new Exp[]{});
            case TERM_POSTFIX:
                x = getExp(callExpression.expressions().get(0));
                return new UnresolvedFunCallImpl(callExpression.name(), Syntax.Postfix, new Exp[]{x});
            case TERM_CASE:
                list = getExpList(callExpression.expressions());
                return new UnresolvedFunCallImpl(callExpression.name(), Syntax.Case, list.stream().toArray(Exp[]::new));
            case CAST:
                list = getExpList(callExpression.expressions());
                return new UnresolvedFunCallImpl(callExpression.name(), Syntax.Cast, list.stream().toArray(Exp[]::new));
        }
        return null;
    }

    static List<Exp> getExpList(List<? extends Expression> expressions) {
        if (expressions != null) {
            return expressions.stream().map(QueryUtil::getExp).toList();
        }
        return List.of();
    }

    static IdImpl getId(CompoundId compoundId) {
        List<Segment> segmentList = getIdSegmentList(compoundId.objectIdentifiers());
        return new IdImpl(segmentList);
    }

    static List<Segment> getIdSegmentList(List<? extends ObjectIdentifier> objectIdentifiers) {
        List<Segment> segmentList = new ArrayList<>();
        if (objectIdentifiers != null) {
            for (ObjectIdentifier oi : objectIdentifiers) {
                segmentList.add(getIdSegment(oi));
            }
        }
        return segmentList;
    }

    static Segment getIdSegment(ObjectIdentifier oi) {
        if (oi instanceof KeyObjectIdentifier keyObjectIdentifier) {
            return new IdImpl.KeySegment(getNameSegmentList(keyObjectIdentifier.nameObjectIdentifiers()));
        }
        if (oi instanceof NameObjectIdentifier nameObjectIdentifier) {
            return new IdImpl.NameSegmentImpl(nameObjectIdentifier.name(), Quoting.UNQUOTED);
        }
        return null;
    }

    static List<NameSegment> getNameSegmentList(List<? extends NameObjectIdentifier> nameObjectIdentifiers) {
        List<NameSegment> res = new ArrayList<>();
        if (nameObjectIdentifiers != null) {
            for (NameObjectIdentifier noi : nameObjectIdentifiers) {
                res.add(new IdImpl.NameSegmentImpl(noi.name(), getQuoting(noi.quoting())));
            }
        }
        return res;
    }

    static Quoting getQuoting(ObjectIdentifier.Quoting quoting) {
        switch (quoting) {
            case QUOTED:
                return Quoting.QUOTED;
            case UNQUOTED:
                return Quoting.UNQUOTED;
            case KEY:
                return Quoting.KEY;
            default:
                return Quoting.UNQUOTED;
        }
    }

    static String getName(NameObjectIdentifier nameObjectIdentifier) {
        switch (nameObjectIdentifier.quoting()) {
            case QUOTED:
                return quoted(nameObjectIdentifier.name());
            case UNQUOTED:
                return nameObjectIdentifier.name();
            case KEY:
                return key(nameObjectIdentifier.name());
            default:
                return nameObjectIdentifier.name();
        }
    }

    static String quoted(String name) {
        StringBuilder sb = new StringBuilder();
        return sb.append("[").append(name).append("]").toString();
    }

    static String key(String name) {
        StringBuilder sb = new StringBuilder();
        return sb.append("&").append(name).append("]").toString();
    }


}
