package org.eclipse.daanse.query;

import mondrian.mdx.UnresolvedFunCall;
import mondrian.olap.AxisOrdinal;
import mondrian.olap.CellProperty;
import mondrian.olap.DmvQuery;
import mondrian.olap.DrillThrough;
import mondrian.olap.Exp;
import mondrian.olap.Explain;
import mondrian.olap.Formula;
import mondrian.olap.Id;
import mondrian.olap.Query;
import mondrian.olap.QueryAxis;
import mondrian.olap.QueryPart;
import mondrian.olap.Refresh;
import mondrian.olap.Subcube;
import mondrian.olap.Syntax;
import mondrian.server.Statement;
import org.eclipse.daanse.mdx.model.api.DMVStatement;
import org.eclipse.daanse.mdx.model.api.DrillthroughStatement;
import org.eclipse.daanse.mdx.model.api.ExplainStatement;
import org.eclipse.daanse.mdx.model.api.MdxStatement;
import org.eclipse.daanse.mdx.model.api.RefreshStatement;
import org.eclipse.daanse.mdx.model.api.SelectStatement;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QueryProviderImpl implements QueryProvider {

    public QueryPart createQuery(MdxStatement mdxStatement) {

        if (mdxStatement instanceof SelectStatement selectStatement) {
            return selectStatementToQuery(selectStatement);
        }
        if (mdxStatement instanceof DrillthroughStatement drillthroughStatement) {
            return drillthroughStatementToQuery(drillthroughStatement);
        }
        if (mdxStatement instanceof ExplainStatement explainStatement) {
            return explainStatementToQuery(explainStatement);
        }
        if (mdxStatement instanceof DMVStatement dmvStatement) {
            return dmvStatementToQuery(dmvStatement);
        }
        if (mdxStatement instanceof RefreshStatement refreshStatement) {
            return refreshStatementToQuery(refreshStatement);
        }
        return null;
    }

    private Refresh refreshStatementToQuery(RefreshStatement refreshStatement) {
        getName(refreshStatement.cubeName());
        return new Refresh(getName(refreshStatement.cubeName()));
    }

    private DmvQuery dmvStatementToQuery(DMVStatement dmvStatement) {
        String tableName = getName(dmvStatement.table());
        List<String> columns = new ArrayList<>();
        if (dmvStatement.columns() != null) {
            dmvStatement.columns().forEach(c -> columns.addAll(getColumns(c.objectIdentifiers())));
        }
        Exp whereExpression = null;
        return new DmvQuery(tableName,
            columns,
            whereExpression);
    }

    private List<String> getColumns(List<? extends ObjectIdentifier> objectIdentifiers) {
        List<String> columns = new ArrayList<>();
        if (objectIdentifiers != null) {
            objectIdentifiers.forEach(oi -> {
                if (oi instanceof KeyObjectIdentifier keyObjectIdentifier){
                    List<? extends NameObjectIdentifier> l = keyObjectIdentifier.nameObjectIdentifiers();
                    if (l != null) {
                        l.forEach(this::getName);
                    }
                }
                if (oi instanceof NameObjectIdentifier nameObjectIdentifier){
                    columns.add(getName(nameObjectIdentifier));
                }
            });
        }
        return columns;
    }

    private Explain explainStatementToQuery(ExplainStatement explainStatement) {
        QueryPart queryPart = createQuery(explainStatement.mdxStatement());
        return new Explain(queryPart);
    }

    private DrillThrough drillthroughStatementToQuery(DrillthroughStatement drillthroughStatement) {
        Query query = selectStatementToQuery(drillthroughStatement.selectStatement());
        List<Exp> returnList = List.of();
        return new DrillThrough(query,
            drillthroughStatement.maxRows().orElse(0),
            drillthroughStatement.firstRowSet().orElse(0),
            returnList);
    }

    private Query selectStatementToQuery(SelectStatement selectStatement) {
        Statement statement = null;
        boolean strictValidation = false;
        Subcube subcube = getSubcube(selectStatement.selectSubcubeClause());
        List<Formula> formulaList = getFormulaList(selectStatement.selectWithClauses());
        List<QueryAxis> axesList = getQueryAxisList(selectStatement.selectQueryClause());
        QueryAxis slicerAxis = getQueryAxis(selectStatement.selectSlicerAxisClause());
        List<QueryPart> cellProps = getParameterList(selectStatement.selectCellPropertyListClause());

        return new Query(
            statement,
            formulaList.toArray(Formula[]::new),
            axesList.toArray(QueryAxis[]::new),
            subcube,
            slicerAxis,
            cellProps.toArray(QueryPart[]::new),
            strictValidation);
    }

    private List<QueryPart> getParameterList(Optional<SelectCellPropertyListClause> selectCellPropertyListClauseOptional) {
        if (selectCellPropertyListClauseOptional.isPresent()) {
            SelectCellPropertyListClause selectCellPropertyListClause = selectCellPropertyListClauseOptional.get();
            if (selectCellPropertyListClause.properties() != null) {
                List<Id.Segment> list = selectCellPropertyListClause.properties()
                    .stream().map(p -> ((Id.Segment) new Id.NameSegment(p))).toList();
                return List.of(new CellProperty(list));
            }
        }
        return List.of();
    }

    private QueryAxis getQueryAxis(Optional<SelectSlicerAxisClause> selectSlicerAxisClauseOptional) {
        if (selectSlicerAxisClauseOptional.isPresent()) {
            SelectSlicerAxisClause selectSlicerAxisClause = selectSlicerAxisClauseOptional.get();
            Expression expression = selectSlicerAxisClause.expression();
            Exp exp = getExp(expression);
            boolean nonEmpty = false;
            Id[] dimensionProperties = new Id[0];

            new QueryAxis(
                nonEmpty,
                exp,
                AxisOrdinal.StandardAxisOrdinal.SLICER,
                QueryAxis.SubtotalVisibility.Undefined,
                dimensionProperties);
        }
        return null;
    }

    private List<QueryAxis> getQueryAxisList(SelectQueryClause selectQueryClause) {
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

    private List<QueryAxis> selectQueryEmptyClauseToQueryAxisList() {
        return List.of();
    }

    private List<QueryAxis> selectQueryAxesClauseToQueryAxisList(SelectQueryAxesClause selectQueryAxesClause) {
        if (selectQueryAxesClause.selectQueryAxisClauses() != null) {
            return selectQueryAxesClause.selectQueryAxisClauses().stream()
                .map(this::selectQueryAxisClauseToQueryAxisList).toList();
        }
        return List.of();
    }

    private QueryAxis selectQueryAxisClauseToQueryAxisList(SelectQueryAxisClause selectQueryAxisClause) {
        Exp exp = getExp(selectQueryAxisClause.expression());
        AxisOrdinal axisOrdinal = getAxisOrdinal(selectQueryAxisClause.axis());
        List<Id> dimensionProperties =
            getDimensionProperties(selectQueryAxisClause.selectDimensionPropertyListClause());

        return new QueryAxis(
            selectQueryAxisClause.nonEmpty(),
            exp,
            axisOrdinal,
            QueryAxis.SubtotalVisibility.Undefined,
            dimensionProperties.stream().toArray(Id[]::new)
        );
    }

    private List<Id> getDimensionProperties(SelectDimensionPropertyListClause selectDimensionPropertyListClause) {
        if (selectDimensionPropertyListClause.properties() != null) {
            return selectDimensionPropertyListClause.properties().stream()
                .map(this::getExpByCompoundId).toList();
        }
        return List.of();
    }

    private AxisOrdinal getAxisOrdinal(Axis axis) {
        return AxisOrdinal.StandardAxisOrdinal.forLogicalOrdinal(axis.ordinal());
    }

    private List<QueryAxis> selectQueryAsteriskClauseToQueryAxisList() {
        return List.of();
    }

    private Subcube getSubcube(SelectSubcubeClause selectSubcubeClause) {
        if (selectSubcubeClause instanceof SelectSubcubeClauseName selectSubcubeClauseName) {
            return getSubcubeBySelectSubcubeClauseName(selectSubcubeClauseName);
        }
        if (selectSubcubeClause instanceof SelectSubcubeClauseStatement selectSubcubeClauseStatement) {
            return getSubcubeBySelectSubcubeClauseStatement(selectSubcubeClauseStatement);
        }
        return null;
    }

    private Subcube getSubcubeBySelectSubcubeClauseStatement(SelectSubcubeClauseStatement selectSubcubeClauseStatement) {
        Subcube subcube = getSubcube(selectSubcubeClauseStatement.selectSubcubeClause());
        List<QueryAxis> axes = getQueryAxisList(selectSubcubeClauseStatement.selectQueryClause());
        String cubeName = null;
        QueryAxis slicerAxis = getQueryAxis(selectSubcubeClauseStatement.selectSlicerAxisClause());
        return new Subcube(cubeName, subcube, axes.stream().toArray(QueryAxis[]::new), slicerAxis);
    }

    private Subcube getSubcubeBySelectSubcubeClauseName(SelectSubcubeClauseName selectSubcubeClauseName) {
        NameObjectIdentifier nameObjectIdentifier = selectSubcubeClauseName.cubeName();
        String cubeName = getName(nameObjectIdentifier);
        return new Subcube(cubeName, null, null, null);
    }

    private List<Formula> getFormulaList(List<? extends SelectWithClause> selectWithClauses) {
        List<Formula> formulaList = new ArrayList<>();
        if (selectWithClauses != null) {
            for (SelectWithClause swc : selectWithClauses) {
                if (swc instanceof CreateMemberBodyClause createMemberBodyClause) {
                    Id id = getId(createMemberBodyClause.compoundId());
                    Exp exp = getExp(createMemberBodyClause.expression());
                    formulaList.add(new Formula(id, exp));
                }
                if (swc instanceof CreateSetBodyClause createSetBodyClause) {
                    Id id = getId(createSetBodyClause.compoundId());
                    Exp exp = getExp(createSetBodyClause.expression());
                    formulaList.add(new Formula(id, exp));
                }
            }
        }
        return formulaList;
    }

    private Exp getExp(Expression expression) {
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

    private Exp getExpByObjectIdentifier(ObjectIdentifier objectIdentifier) {
        if (objectIdentifier instanceof KeyObjectIdentifier keyObjectIdentifier) {
            return new Id(getExpByKeyObjectIdentifier(keyObjectIdentifier));
        }
        if (objectIdentifier instanceof NameObjectIdentifier nameObjectIdentifier) {
            return new Id(getExpByNameObjectIdentifier(nameObjectIdentifier));
        }
        return null;
    }

    private Id.Segment getExpByNameObjectIdentifier(NameObjectIdentifier nameObjectIdentifier) {
        switch (nameObjectIdentifier.quoting()) {
            case QUOTED:
                return new Id.NameSegment(nameObjectIdentifier.name(), Id.Quoting.QUOTED);
            case UNQUOTED:
                return new Id.NameSegment(nameObjectIdentifier.name(), Id.Quoting.UNQUOTED);
            case KEY:
                return new Id.KeySegment(new Id.NameSegment(nameObjectIdentifier.name()));
        }
        return null;
    }

    private List<Id.Segment> getExpByKeyObjectIdentifier(KeyObjectIdentifier keyObjectIdentifier) {
        return keyObjectIdentifier.nameObjectIdentifiers()
            .stream().map(this::getExpByNameObjectIdentifier).toList();
    }

    private Id getExpByCompoundId(CompoundId compoundId) {
        List<Id.Segment> list = new ArrayList<>();
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
        return new Id(list);
    }

    private Exp getExpByLiteral(Literal literal) {
        if (literal instanceof NumericLiteral numericLiteral) {
            return mondrian.olap.Literal.create(numericLiteral.value());
        }
        if (literal instanceof StringLiteral stringLiteral) {
            return mondrian.olap.Literal.createString(stringLiteral.value());
        }
        if (literal instanceof NullLiteral) {
            return mondrian.olap.Literal.nullValue;
        }
        if (literal instanceof SymbolLiteral symbolLiteral) {
            return mondrian.olap.Literal.createSymbol(symbolLiteral.value());
        }
        return null;
    }

    private Exp getExpByCallExpression(CallExpression callExpression) {
        Exp x;
        Exp y;
        List<Exp> list;
        switch (callExpression.type()) {
            case TERM_INFIX:
                x = getExp(callExpression.expressions().get(0));
                y = getExp(callExpression.expressions().get(1));
                return new UnresolvedFunCall(callExpression.name(), Syntax.Infix, new Exp[]{x, y});
            case TERM_PREFIX:
                x = getExp(callExpression.expressions().get(0));
                return new UnresolvedFunCall(callExpression.name(), Syntax.Prefix, new Exp[]{x});
            case FUNCTION:
                list = getExpList(callExpression.expressions());
                return new UnresolvedFunCall(callExpression.name(), Syntax.Function, list.stream().toArray(Exp[]::new));
            case PROPERTY:
                x = getExp(callExpression.expressions().get(0));
                return new UnresolvedFunCall(callExpression.name(), Syntax.Property, new Exp[]{x});
            case PROPERTY_QUOTED:
                x = getExp(callExpression.expressions().get(0));
                return new UnresolvedFunCall(callExpression.name(), Syntax.QuotedProperty, new Exp[]{x});
            case PROPERTY_AMPERS_AND_QUOTED:
                x = getExp(callExpression.expressions().get(0));
                return new UnresolvedFunCall(callExpression.name(), Syntax.AmpersandQuotedProperty, new Exp[]{x});
            case METHOD:
                list = getExpList(callExpression.expressions());
                return new UnresolvedFunCall(callExpression.name(), Syntax.Method, list.stream().toArray(Exp[]::new));
            case BRACES:
                list = getExpList(callExpression.expressions());
                return new UnresolvedFunCall(callExpression.name(), Syntax.Braces, list.stream().toArray(Exp[]::new));
            case PARENTHESES:
                list = getExpList(callExpression.expressions());
                return new UnresolvedFunCall(callExpression.name(), Syntax.Parentheses, list.stream().toArray(Exp[]::new));
            case INTERNAL:
                list = getExpList(callExpression.expressions());
                return new UnresolvedFunCall(callExpression.name(), Syntax.Internal, list.stream().toArray(Exp[]::new));
            case EMPTY:
                return new UnresolvedFunCall(callExpression.name(), Syntax.Empty, new Exp[]{});
            case TERM_POSTFIX:
                x = getExp(callExpression.expressions().get(0));
                return new UnresolvedFunCall(callExpression.name(), Syntax.Postfix, new Exp[]{x});
            case TERM_CASE:
                list = getExpList(callExpression.expressions());
                return new UnresolvedFunCall(callExpression.name(), Syntax.Case, list.stream().toArray(Exp[]::new));
            case CAST:
                list = getExpList(callExpression.expressions());
                return new UnresolvedFunCall(callExpression.name(), Syntax.Cast, list.stream().toArray(Exp[]::new));
        }
        return null;
    }

    private List<Exp> getExpList(List<? extends Expression> expressions) {
        if (expressions != null) {
            return expressions.stream().map(this::getExp).toList();
        }
        return List.of();
    }

    private Id getId(CompoundId compoundId) {
        List<Id.Segment> segmentList = getIdSegmentList(compoundId.objectIdentifiers());
        return new Id(segmentList);
    }

    private List<Id.Segment> getIdSegmentList(List<? extends ObjectIdentifier> objectIdentifiers) {
        List<Id.Segment> segmentList = new ArrayList<>();
        if (objectIdentifiers != null) {
            for (ObjectIdentifier oi : objectIdentifiers) {
                segmentList.add(getIdSegment(oi));
            }
        }
        return segmentList;
    }

    private Id.Segment getIdSegment(ObjectIdentifier oi) {
        if (oi instanceof KeyObjectIdentifier keyObjectIdentifier) {
            return new Id.KeySegment(getNameSegmentList(keyObjectIdentifier.nameObjectIdentifiers()));
        }
        if (oi instanceof NameObjectIdentifier nameObjectIdentifier) {
            return new Id.NameSegment(nameObjectIdentifier.name(), Id.Quoting.UNQUOTED);
        }
        return null;
    }

    private List<Id.NameSegment> getNameSegmentList(List<? extends NameObjectIdentifier> nameObjectIdentifiers) {
        List<Id.NameSegment> res = new ArrayList<>();
        if (nameObjectIdentifiers != null) {
            for (NameObjectIdentifier noi : nameObjectIdentifiers) {
                res.add(new Id.NameSegment(noi.name(), getQuoting(noi.quoting())));
            }
        }
        return res;
    }

    private Id.Quoting getQuoting(ObjectIdentifier.Quoting quoting) {
        switch (quoting) {
            case QUOTED:
                return Id.Quoting.QUOTED;
            case UNQUOTED:
                return Id.Quoting.UNQUOTED;
            case KEY:
                return Id.Quoting.KEY;
            default:
                return Id.Quoting.UNQUOTED;
        }
    }

    private String getName(NameObjectIdentifier nameObjectIdentifier) {
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

    private String quoted(String name) {
        StringBuilder sb = new StringBuilder();
        return sb.append("[").append(name).append("]").toString();
    }

    private String key(String name) {
        StringBuilder sb = new StringBuilder();
        return sb.append("&").append(name).append("]").toString();
    }

}
