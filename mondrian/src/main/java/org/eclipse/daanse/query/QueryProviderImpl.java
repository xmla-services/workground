package org.eclipse.daanse.query;

import mondrian.mdx.UnresolvedFunCallImpl;
import mondrian.olap.AxisOrdinal;
import mondrian.olap.CellPropertyImpl;
import mondrian.olap.DmvQueryImpl;
import mondrian.olap.DrillThroughImpl;
import mondrian.olap.QueryImpl;
import mondrian.olap.api.DmvQuery;
import mondrian.olap.api.DrillThrough;
import mondrian.olap.Exp;
import mondrian.olap.ExplainImpl;
import mondrian.olap.FormulaImpl;
import mondrian.olap.IdImpl;
import mondrian.olap.api.Explain;
import mondrian.olap.api.Formula;
import mondrian.olap.api.Id;
import mondrian.olap.api.Query;
import mondrian.olap.QueryAxisImpl;
import mondrian.olap.api.QueryPart;
import mondrian.olap.RefreshImpl;
import mondrian.olap.SubcubeImpl;
import mondrian.olap.Syntax;
import mondrian.olap.api.Refresh;
import mondrian.olap.api.Subcube;
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
        return new RefreshImpl(getName(refreshStatement.cubeName()));
    }

    private DmvQuery dmvStatementToQuery(DMVStatement dmvStatement) {
        String tableName = getName(dmvStatement.table());
        List<String> columns = new ArrayList<>();
        if (dmvStatement.columns() != null) {
            dmvStatement.columns().forEach(c -> columns.addAll(getColumns(c.objectIdentifiers())));
        }
        Exp whereExpression = null;
        return new DmvQueryImpl(tableName,
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
        return new ExplainImpl(queryPart);
    }

    private DrillThrough drillthroughStatementToQuery(DrillthroughStatement drillthroughStatement) {
        Query query = selectStatementToQuery(drillthroughStatement.selectStatement());
        List<Exp> returnList = List.of();
        return new DrillThroughImpl(query,
            drillthroughStatement.maxRows().orElse(0),
            drillthroughStatement.firstRowSet().orElse(0),
            returnList);
    }

    private Query selectStatementToQuery(SelectStatement selectStatement) {
        Statement statement = null;
        boolean strictValidation = false;
        Subcube subcube = getSubcube(selectStatement.selectSubcubeClause());
        List<Formula> formulaList = getFormulaList(selectStatement.selectWithClauses());
        List<QueryAxisImpl> axesList = getQueryAxisList(selectStatement.selectQueryClause());
        QueryAxisImpl slicerAxis = getQueryAxis(selectStatement.selectSlicerAxisClause());
        List<QueryPart> cellProps = getParameterList(selectStatement.selectCellPropertyListClause());

        return new QueryImpl(
            statement,
            formulaList.toArray(Formula[]::new),
            axesList.toArray(QueryAxisImpl[]::new),
            subcube,
            slicerAxis,
            cellProps.toArray(QueryPart[]::new),
            strictValidation);
    }

    private List<QueryPart> getParameterList(Optional<SelectCellPropertyListClause> selectCellPropertyListClauseOptional) {
        if (selectCellPropertyListClauseOptional.isPresent()) {
            SelectCellPropertyListClause selectCellPropertyListClause = selectCellPropertyListClauseOptional.get();
            if (selectCellPropertyListClause.properties() != null) {
                List<IdImpl.Segment> list = selectCellPropertyListClause.properties()
                    .stream().map(p -> ((IdImpl.Segment) new IdImpl.NameSegment(p))).toList();
                return List.of(new CellPropertyImpl(list));
            }
        }
        return List.of();
    }

    private QueryAxisImpl getQueryAxis(Optional<SelectSlicerAxisClause> selectSlicerAxisClauseOptional) {
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
                QueryAxisImpl.SubtotalVisibility.Undefined,
                dimensionProperties);
        }
        return null;
    }

    private List<QueryAxisImpl> getQueryAxisList(SelectQueryClause selectQueryClause) {
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

    private List<QueryAxisImpl> selectQueryEmptyClauseToQueryAxisList() {
        return List.of();
    }

    private List<QueryAxisImpl> selectQueryAxesClauseToQueryAxisList(SelectQueryAxesClause selectQueryAxesClause) {
        if (selectQueryAxesClause.selectQueryAxisClauses() != null) {
            return selectQueryAxesClause.selectQueryAxisClauses().stream()
                .map(this::selectQueryAxisClauseToQueryAxisList).toList();
        }
        return List.of();
    }

    private QueryAxisImpl selectQueryAxisClauseToQueryAxisList(SelectQueryAxisClause selectQueryAxisClause) {
        Exp exp = getExp(selectQueryAxisClause.expression());
        AxisOrdinal axisOrdinal = getAxisOrdinal(selectQueryAxisClause.axis());
        List<IdImpl> dimensionProperties =
            getDimensionProperties(selectQueryAxisClause.selectDimensionPropertyListClause());

        return new QueryAxisImpl(
            selectQueryAxisClause.nonEmpty(),
            exp,
            axisOrdinal,
            QueryAxisImpl.SubtotalVisibility.Undefined,
            dimensionProperties.stream().toArray(IdImpl[]::new)
        );
    }

    private List<IdImpl> getDimensionProperties(SelectDimensionPropertyListClause selectDimensionPropertyListClause) {
        if (selectDimensionPropertyListClause.properties() != null) {
            return selectDimensionPropertyListClause.properties().stream()
                .map(this::getExpByCompoundId).toList();
        }
        return List.of();
    }

    private AxisOrdinal getAxisOrdinal(Axis axis) {
        return AxisOrdinal.StandardAxisOrdinal.forLogicalOrdinal(axis.ordinal());
    }

    private List<QueryAxisImpl> selectQueryAsteriskClauseToQueryAxisList() {
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
        List<QueryAxisImpl> axes = getQueryAxisList(selectSubcubeClauseStatement.selectQueryClause());
        String cubeName = null;
        QueryAxisImpl slicerAxis = getQueryAxis(selectSubcubeClauseStatement.selectSlicerAxisClause());
        return new SubcubeImpl(cubeName, subcube, axes.stream().toArray(QueryAxisImpl[]::new), slicerAxis);
    }

    private Subcube getSubcubeBySelectSubcubeClauseName(SelectSubcubeClauseName selectSubcubeClauseName) {
        NameObjectIdentifier nameObjectIdentifier = selectSubcubeClauseName.cubeName();
        String cubeName = getName(nameObjectIdentifier);
        return new SubcubeImpl(cubeName, null, null, null);
    }

    private List<Formula> getFormulaList(List<? extends SelectWithClause> selectWithClauses) {
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
            return new IdImpl(getExpByKeyObjectIdentifier(keyObjectIdentifier));
        }
        if (objectIdentifier instanceof NameObjectIdentifier nameObjectIdentifier) {
            return new IdImpl(getExpByNameObjectIdentifier(nameObjectIdentifier));
        }
        return null;
    }

    private IdImpl.Segment getExpByNameObjectIdentifier(NameObjectIdentifier nameObjectIdentifier) {
        switch (nameObjectIdentifier.quoting()) {
            case QUOTED:
                return new IdImpl.NameSegment(nameObjectIdentifier.name(), IdImpl.Quoting.QUOTED);
            case UNQUOTED:
                return new IdImpl.NameSegment(nameObjectIdentifier.name(), IdImpl.Quoting.UNQUOTED);
            case KEY:
                return new IdImpl.KeySegment(new IdImpl.NameSegment(nameObjectIdentifier.name()));
        }
        return null;
    }

    private List<IdImpl.Segment> getExpByKeyObjectIdentifier(KeyObjectIdentifier keyObjectIdentifier) {
        return keyObjectIdentifier.nameObjectIdentifiers()
            .stream().map(this::getExpByNameObjectIdentifier).toList();
    }

    private IdImpl getExpByCompoundId(CompoundId compoundId) {
        List<IdImpl.Segment> list = new ArrayList<>();
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

    private Exp getExpByLiteral(Literal literal) {
        if (literal instanceof NumericLiteral numericLiteral) {
            return mondrian.olap.LiteralImpl.create(numericLiteral.value());
        }
        if (literal instanceof StringLiteral stringLiteral) {
            return mondrian.olap.LiteralImpl.createString(stringLiteral.value());
        }
        if (literal instanceof NullLiteral) {
            return mondrian.olap.LiteralImpl.nullValue;
        }
        if (literal instanceof SymbolLiteral symbolLiteral) {
            return mondrian.olap.LiteralImpl.createSymbol(symbolLiteral.value());
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

    private List<Exp> getExpList(List<? extends Expression> expressions) {
        if (expressions != null) {
            return expressions.stream().map(this::getExp).toList();
        }
        return List.of();
    }

    private IdImpl getId(CompoundId compoundId) {
        List<IdImpl.Segment> segmentList = getIdSegmentList(compoundId.objectIdentifiers());
        return new IdImpl(segmentList);
    }

    private List<IdImpl.Segment> getIdSegmentList(List<? extends ObjectIdentifier> objectIdentifiers) {
        List<IdImpl.Segment> segmentList = new ArrayList<>();
        if (objectIdentifiers != null) {
            for (ObjectIdentifier oi : objectIdentifiers) {
                segmentList.add(getIdSegment(oi));
            }
        }
        return segmentList;
    }

    private IdImpl.Segment getIdSegment(ObjectIdentifier oi) {
        if (oi instanceof KeyObjectIdentifier keyObjectIdentifier) {
            return new IdImpl.KeySegment(getNameSegmentList(keyObjectIdentifier.nameObjectIdentifiers()));
        }
        if (oi instanceof NameObjectIdentifier nameObjectIdentifier) {
            return new IdImpl.NameSegment(nameObjectIdentifier.name(), IdImpl.Quoting.UNQUOTED);
        }
        return null;
    }

    private List<IdImpl.NameSegment> getNameSegmentList(List<? extends NameObjectIdentifier> nameObjectIdentifiers) {
        List<IdImpl.NameSegment> res = new ArrayList<>();
        if (nameObjectIdentifiers != null) {
            for (NameObjectIdentifier noi : nameObjectIdentifiers) {
                res.add(new IdImpl.NameSegment(noi.name(), getQuoting(noi.quoting())));
            }
        }
        return res;
    }

    private IdImpl.Quoting getQuoting(ObjectIdentifier.Quoting quoting) {
        switch (quoting) {
            case QUOTED:
                return IdImpl.Quoting.QUOTED;
            case UNQUOTED:
                return IdImpl.Quoting.UNQUOTED;
            case KEY:
                return IdImpl.Quoting.KEY;
            default:
                return IdImpl.Quoting.UNQUOTED;
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
