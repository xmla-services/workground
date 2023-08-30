package org.eclipse.daanse.query;

import mondrian.olap.Exp;
import mondrian.olap.Formula;
import mondrian.olap.Id;
import mondrian.olap.Parameter;
import mondrian.olap.Query;
import mondrian.olap.QueryAxis;
import mondrian.olap.QueryPart;
import mondrian.olap.Subcube;
import mondrian.server.Statement;
import org.eclipse.daanse.mdx.model.api.DrillthroughStatement;
import org.eclipse.daanse.mdx.model.api.MdxStatement;
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
import org.eclipse.daanse.mdx.model.api.select.CreateMemberBodyClause;
import org.eclipse.daanse.mdx.model.api.select.CreateSetBodyClause;
import org.eclipse.daanse.mdx.model.api.select.SelectSlicerAxisClause;
import org.eclipse.daanse.mdx.model.api.select.SelectSubcubeClause;
import org.eclipse.daanse.mdx.model.api.select.SelectSubcubeClauseName;
import org.eclipse.daanse.mdx.model.api.select.SelectSubcubeClauseStatement;
import org.eclipse.daanse.mdx.model.api.select.SelectWithClause;
import org.eclipse.daanse.olap.api.model.Cube;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QueryProviderImpl implements QueryProvider {

    public Query createQuery(MdxStatement mdxStatement) {

        if (mdxStatement instanceof SelectStatement selectStatement) {
            return selectStatementToQuery(selectStatement);
        }
        if (mdxStatement instanceof DrillthroughStatement drillthroughStatement) {
            return drillthroughStatementToQuery(drillthroughStatement);
        }
        return null;
    }

    private Query drillthroughStatementToQuery(DrillthroughStatement drillthroughStatement) {
        return null;
    }

    private Query selectStatementToQuery(SelectStatement selectStatement) {
        Statement statement = null;
        Cube mdxCube = null;
        QueryPart[] cellProps = null;
        Parameter[] parameters = null;
        boolean strictValidation = false;
        Subcube subcube = getSubcube(selectStatement.selectSubcubeClause());
        List<Formula> formulaList = getFormulaList(selectStatement.selectWithClauses());
        List<QueryAxis> axesList = getQueryAxisList(selectStatement.selectSlicerAxisClause());
        QueryAxis slicerAxis = getQueryAxis(selectStatement.selectSlicerAxisClause());

        return new Query(
            statement,
            mdxCube,
            formulaList.toArray(Formula[]::new),
            subcube,
            axesList.toArray(QueryAxis[]::new),
            slicerAxis,
            cellProps,
            parameters,
            strictValidation
        );
    }

    private QueryAxis getQueryAxis(Optional<SelectSlicerAxisClause> selectSlicerAxisClause) {
        //TODO
        return null;
    }

    private List<QueryAxis> getQueryAxisList(Optional<SelectSlicerAxisClause> selectSlicerAxisClause) {
        //TODO
        return null;
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
        String cubeName = null;
        Subcube subcube = null;
        QueryAxis[] axes = null;
        QueryAxis slicerAxis = null;

        //TODO
        return new Subcube(cubeName, subcube, axes, slicerAxis);
    }

    private Subcube getSubcubeBySelectSubcubeClauseName(SelectSubcubeClauseName selectSubcubeClauseName) {
        String cubeName = null;
        Subcube subcube = null;
        QueryAxis[] axes = null;
        QueryAxis slicerAxis = null;

        NameObjectIdentifier nameObjectIdentifier = selectSubcubeClauseName.cubeName();

        //TODO
        return new Subcube(cubeName, subcube, axes, slicerAxis);
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
            return getExpByKeyObjectIdentifier(keyObjectIdentifier);
        }
        if (objectIdentifier instanceof NameObjectIdentifier nameObjectIdentifier) {
            return getExpByNameObjectIdentifier(nameObjectIdentifier);
        }
        return null;
    }

    private Exp getExpByNameObjectIdentifier(NameObjectIdentifier nameObjectIdentifier) {
        StringBuilder sb = new StringBuilder();
        switch (nameObjectIdentifier.quoting()) {
            case QUOTED:
                return mondrian.olap.Literal.createString(quoted(nameObjectIdentifier.name()));
            case UNQUOTED:
                return mondrian.olap.Literal.createString(nameObjectIdentifier.name());
            case KEY:
                return mondrian.olap.Literal.createString(key(nameObjectIdentifier.name()));

        }
        if (Id.Quoting.QUOTED.equals(nameObjectIdentifier.quoting())) {
            sb.append("[");
        }
        return mondrian.olap.Literal.createString(nameObjectIdentifier.name());
    }

    private Exp getExpByKeyObjectIdentifier(KeyObjectIdentifier keyObjectIdentifier) {
        //TODO
        return null;
    }

    private Exp getExpByCompoundId(CompoundId compoundId) {
        return null;
    }

    private Exp getExpByLiteral(Literal literal) {
        if (literal instanceof NumericLiteral numericLiteral) {
            return mondrian.olap.Literal.create(numericLiteral.value());
        }
        if (literal instanceof StringLiteral stringLiteral) {
            return mondrian.olap.Literal.createString(stringLiteral.value());
        }
        if (literal instanceof NullLiteral nullLiteral) {
            return mondrian.olap.Literal.nullValue;
        }
        if (literal instanceof SymbolLiteral symbolLiteral) {
            return mondrian.olap.Literal.createSymbol(symbolLiteral.value());
        }
        return null;
    }

    private Exp getExpByCallExpression(CallExpression callExpression) {
        return null;
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

    private String quoted(String name) {
        StringBuilder sb = new StringBuilder();
        return sb.append("[").append(name).append("]").toString();
    }

    private String key(String name) {
        StringBuilder sb = new StringBuilder();
        return sb.append("&").append(name).append("]").toString();
    }

}
