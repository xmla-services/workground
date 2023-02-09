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
package org.eclipse.daanse.mdx.unparser.simple;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.daanse.mdx.model.Axis;
import org.eclipse.daanse.mdx.model.CallExpression;
import org.eclipse.daanse.mdx.model.CompoundId;
import org.eclipse.daanse.mdx.model.DMVStatement;
import org.eclipse.daanse.mdx.model.DrillthroughStatement;
import org.eclipse.daanse.mdx.model.ExplainStatement;
import org.eclipse.daanse.mdx.model.Expression;
import org.eclipse.daanse.mdx.model.KeyObjectIdentifier;
import org.eclipse.daanse.mdx.model.Literal;
import org.eclipse.daanse.mdx.model.MdxRefreshStatement;
import org.eclipse.daanse.mdx.model.MdxStatement;
import org.eclipse.daanse.mdx.model.NameObjectIdentifier;
import org.eclipse.daanse.mdx.model.NullLiteral;
import org.eclipse.daanse.mdx.model.NumericLiteral;
import org.eclipse.daanse.mdx.model.ObjectIdentifier;
import org.eclipse.daanse.mdx.model.SelectStatement;
import org.eclipse.daanse.mdx.model.StringLiteral;
import org.eclipse.daanse.mdx.model.SymbolLiteral;
import org.eclipse.daanse.mdx.model.select.CreateCellCalculationBodyClause;
import org.eclipse.daanse.mdx.model.select.CreateMemberBodyClause;
import org.eclipse.daanse.mdx.model.select.CreateSetBodyClause;
import org.eclipse.daanse.mdx.model.select.MeasureBodyClause;
import org.eclipse.daanse.mdx.model.select.SelectCellPropertyListClause;
import org.eclipse.daanse.mdx.model.select.SelectQueryAsteriskClause;
import org.eclipse.daanse.mdx.model.select.SelectQueryAxesClause;
import org.eclipse.daanse.mdx.model.select.SelectQueryAxisClause;
import org.eclipse.daanse.mdx.model.select.SelectQueryClause;
import org.eclipse.daanse.mdx.model.select.SelectQueryEmptyClause;
import org.eclipse.daanse.mdx.model.select.SelectSlicerAxisClause;
import org.eclipse.daanse.mdx.model.select.SelectSubcubeClause;
import org.eclipse.daanse.mdx.model.select.SelectSubcubeClauseName;
import org.eclipse.daanse.mdx.model.select.SelectSubcubeClauseStatement;
import org.eclipse.daanse.mdx.model.select.SelectWithClause;
import org.eclipse.daanse.mdx.unparser.api.UnParser;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.util.converter.Converters;

@Component
@Designate(ocd = SimpleUnparser.Config.class)
public class SimpleUnparser implements UnParser {

    @ObjectClassDefinition()
    public interface Config {

        @AttributeDefinition(defaultValue = "false")
        default boolean commentUnusedElements() {
            return true;
        }
    }

    int indent = 0;

    public Config config = null;

    @Activate
    public void activate(Map<String, Object> configMap) {
        modifies(configMap);

    }

    @Deactivate
    public void deActivate(Map<String, Object> configMap) {
        this.config = null;

    }

    @Modified
    public void modifies(Map<String, Object> configMap) {
        this.config = Converters.standardConverter()
                .convert(configMap)
                .to(Config.class);

    }

    public StringBuilder unparseSelectStatement(SelectStatement selectStatement) {
        StringBuilder sb = new StringBuilder();
        if (!selectStatement.selectWithClauses()
                .isEmpty()) {
            sb = sb.append("WITH ");
            sb = sb.append(unparseSelectWithClauses(selectStatement.selectWithClauses()));
            sb = sb.append(" ");
        }

        sb = sb.append("SELECT ");
        sb = sb.append(unparseSelectQueryClause(selectStatement.selectQueryClause()));
        sb = sb.append(" FROM ");
        sb = sb.append(unparseSelectSubcubeClause(selectStatement.selectSubcubeClause()));
        sb = sb.append(unparseSelectSlicerAxisClause(selectStatement.selectSlicerAxisClause()));
        sb = sb.append(unparseSelectCellPropertyListClause(selectStatement.selectCellPropertyListClause()));

        return sb;

    }

    public StringBuilder unparseSelectCellPropertyListClause(SelectCellPropertyListClause clause) {
        return null;
    }

    public StringBuilder unparseSelectSlicerAxisClause(SelectSlicerAxisClause clause) {
        return null;
    }

    public StringBuilder unparseSelectSubcubeClause(SelectSubcubeClause clause) {

        if (clause instanceof SelectSubcubeClauseName sscn) {
            return unparseSelectSubcubeClauseName(sscn);
        }
        if (clause instanceof SelectSubcubeClauseStatement sscs) {
            return unparseSelectSubcubeClauseStatement(sscs);
        }

        return null;
    }

    public StringBuilder unparseSelectSubcubeClauseStatement(SelectSubcubeClauseStatement clause) {
        // TODO Auto-generated method stub
        return null;
    }

    public StringBuilder unparseSelectSubcubeClauseName(SelectSubcubeClauseName clause) {

        return unparseNameObjectIdentifier(clause.cubeName());
    }

    private StringBuilder unparseNameObjectIdentifier(NameObjectIdentifier nameObjectIdentifier) {

        StringBuilder sb = new StringBuilder();
        switch (nameObjectIdentifier.quoting()) {
        case KEY ->

            sb.append("&")
                    .append(nameObjectIdentifier.name());

        case QUOTED ->

            sb.append("[")
                    .append(nameObjectIdentifier.name()
                            .replace("]", "]]"))
                    .append("]");

        case UNQUOTED ->

            sb.append(nameObjectIdentifier.name());

        }

        return sb;
    }

    public StringBuilder unparseSelectQueryClause(SelectQueryClause clause) {

        if (clause instanceof SelectQueryAsteriskClause sqasc) {
            return unparseSelectQueryAsteriskClause(sqasc);
        }
        if (clause instanceof SelectQueryAxesClause sqaxc) {
            return unparseSelectQueryAxesClause(sqaxc);

        }
        if (clause instanceof SelectQueryEmptyClause sqec) {
            return unparseSelectQueryEmptyClause(sqec);

        }

        return null;
    }

    private StringBuilder unparseSelectQueryEmptyClause(SelectQueryEmptyClause clause) {
        return new StringBuilder();/* empty */
    }

    private StringBuilder unparseSelectQueryAxesClause(SelectQueryAxesClause clause) {

        clause.selectQueryAxisClauses()
                .stream()
                .map(s -> unparseSelectQueryAxisClause(s))
                .map(Object::toString)
                .collect(Collectors.joining(","));
        return null;
    }

    private Object unparseSelectQueryAxisClause(SelectQueryAxisClause clause) {
        StringBuilder sb = new StringBuilder();

        if (clause.nonEmpty()) {

            sb.append("NON EMPTY ");
        }
        sb.append(unparseExpression(clause.expression()));
        sb.append(" ON ");
        sb.append(unparseAxis(clause.axis()));

        return sb;
    }

    private StringBuilder unparseExpression(Expression expression) {

        if (expression instanceof CallExpression ce) {
            return unparseCallExpression(ce);
        }
        if (expression instanceof Literal l) {
            return unparseLiteral(l);
        }
        if (expression instanceof CompoundId cId) {
            return unparseCompoundId(cId);
        }
        if (expression instanceof ObjectIdentifier oi) {
            return unparseObjectIdentifier(oi);
        }

        return null;
    }

    private StringBuilder unparseObjectIdentifier(ObjectIdentifier objectIdentifier) {

        if (objectIdentifier instanceof KeyObjectIdentifier koi) {

            return unparseKeyObjectIdentifier(koi);
        }
        if (objectIdentifier instanceof NameObjectIdentifier noi) {

            return unparseNameObjectIdentifier(noi);

        }
        return null;
    }

    private StringBuilder unparseKeyObjectIdentifier(KeyObjectIdentifier koi) {
        StringBuilder sb = new StringBuilder();

        koi.nameObjectIdentifiers()
                .stream()
                .map(k -> unparseNameObjectIdentifier(k))
                .map(Object::toString)
                .collect(Collectors.joining("."));

        return sb;
    }

    private StringBuilder unparseCompoundId(CompoundId compoundId) {
        String s = compoundId.objectIdentifiers()
                .stream()
                .map(o -> unparseObjectIdentifier(o))
                .collect(Collectors.joining("."));
        return new StringBuilder(s);
    }

    private StringBuilder unparseLiteral(Literal literal) {

        if (literal instanceof NullLiteral nullLiteral) {
            return unparseNullLiteral(nullLiteral);
        }
        if (literal instanceof NumericLiteral numericLiteral) {
            return unparseNumericLiteral(numericLiteral);

        }
        if (literal instanceof StringLiteral stringLiteral) {
            return unparseStringLiteral(stringLiteral);

        }
        if (literal instanceof SymbolLiteral symbolLiteral) {
            return unparseSymbolLiteral(symbolLiteral);

        }

        return null;
    }

    private StringBuilder unparseSymbolLiteral(SymbolLiteral symbolLiteral) {
        return new StringBuilder(symbolLiteral.value());
    }

    private StringBuilder unparseStringLiteral(StringLiteral stringLiteral) {
        return new StringBuilder(stringLiteral.value());

    }

    private StringBuilder unparseNumericLiteral(NumericLiteral numericLiteral) {
        return new StringBuilder(numericLiteral.value()
                .toString());

    }

    private StringBuilder unparseNullLiteral(NullLiteral nullLiteral) {
        return new StringBuilder("NULL");

    }

    private StringBuilder unparseCallExpression(CallExpression callExpression) {
        StringBuilder sb = new StringBuilder();
        String name = callExpression.name();
        List<Expression> expressions = callExpression.expressions();
        String expressionText = unparseExpressions(expressions);
        switch (callExpression.type()) {
        case Braces -> sb.append("{")
                .append(expressionText)
                .append("}");
        case Cast -> sb.append("CAST(")
                .append(expressionText)
                .append(")");
        case Empty -> sb.append("");
        case Function -> sb.append(name)
                .append("(")
                .append(expressionText)
                .append(")");
        case Internal -> sb.append("$")
                .append(expressionText);
        case Method -> sb.append(".")
                .append(name)
                .append("(")
                .append(expressionText)
                .append(")");
        case Parentheses -> sb.append("(")
                .append(expressionText)
                .append(")");
        case Property -> sb.append(".")
                .append(name);
        case PropertyAmpersAndQuoted -> sb.append(".[&")
                .append(name)
                .append("]");
        case PropertyQuoted -> sb.append(".&")
                .append(name)
                .append("");
        case Term_Case -> sb.append("TODO");
        case Term_Infix -> sb.append("TODO");
        case Term_Postfix -> sb.append("TODO");
        case Term_Prefix -> sb.append("TODO");

        }

        return sb;
    }

    private String unparseExpressions(List<Expression> expressions) {
        return expressions.stream()
                .map(e -> unparseExpression(e))
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }

    private StringBuilder unparseSelectQueryAsteriskClause(SelectQueryAsteriskClause clause) {
        return new StringBuilder("*");
    }

    public StringBuilder unparseSelectWithClauses(List<SelectWithClause> clause) {
        return null;

    }

    public StringBuilder unparseSelectWithClause(SelectWithClause clause) {
        if (clause instanceof CreateCellCalculationBodyClause c) {
            return unparseCreateCellCalculationBodyClause(c);
        } else if (clause instanceof CreateMemberBodyClause c) {
            return unparseCreateMemberBodyClause(c);
        } else if (clause instanceof CreateSetBodyClause c) {
            return unparseCreateSetBodyClause(c);
        } else if (clause instanceof MeasureBodyClause c) {
            return unparseMeasureBodyClause(c);
        }
        return new StringBuilder();

    }

    public StringBuilder unparseCreateCellCalculationBodyClause(CreateCellCalculationBodyClause clause) {
        return null;

    }

    public StringBuilder unparseCreateMemberBodyClause(CreateMemberBodyClause clause) {
        return null;

    }

    public StringBuilder unparseCreateSetBodyClause(CreateSetBodyClause clause) {
        return null;

    }

    public StringBuilder unparseMeasureBodyClause(MeasureBodyClause clause) {
        return null;

    }

    public StringBuilder unparseDrillthroughStatement(DrillthroughStatement selectStatement) {
        return null;

    }

    public StringBuilder unparseExplainStatement(ExplainStatement selectStatement) {
        return null;

    }

    public StringBuilder unparseDMVStatement(DMVStatement selectStatement) {
        return null;

    }

    public StringBuilder unparseMdxRefreshStatement(MdxRefreshStatement selectStatement) {
        return null;

    }

    @Override
    public StringBuilder unparseMdxStatement(MdxStatement mdxStatement) {
        if (mdxStatement instanceof SelectStatement) {
            return unparseSelectStatement((SelectStatement) mdxStatement);
        } else if (mdxStatement instanceof DrillthroughStatement) {
            return unparseDrillthroughStatement((DrillthroughStatement) mdxStatement);
        } else if (mdxStatement instanceof ExplainStatement) {
            return unparseExplainStatement((ExplainStatement) mdxStatement);
        } else if (mdxStatement instanceof DMVStatement) {
            return unparseDMVStatement((DMVStatement) mdxStatement);
        } else if (mdxStatement instanceof SelectStatement) {
            return unparseSelectStatement((SelectStatement) mdxStatement);
        }

        return new StringBuilder();
    }

    public StringBuilder unparseAxis(Axis axis) {
        StringBuilder sb = new StringBuilder().append(axis.named() ? axis.name()
                .toUpperCase() : axis.ordinal());
        return sb;

    }

}
