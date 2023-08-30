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
import org.eclipse.daanse.mdx.model.api.SelectStatement;
import org.eclipse.daanse.mdx.model.api.expression.CompoundId;
import org.eclipse.daanse.mdx.model.api.expression.Expression;
import org.eclipse.daanse.mdx.model.api.expression.KeyObjectIdentifier;
import org.eclipse.daanse.mdx.model.api.expression.NameObjectIdentifier;
import org.eclipse.daanse.mdx.model.api.expression.ObjectIdentifier;
import org.eclipse.daanse.mdx.model.api.select.CreateMemberBodyClause;
import org.eclipse.daanse.mdx.model.api.select.CreateSetBodyClause;
import org.eclipse.daanse.mdx.model.api.select.SelectSubcubeClause;
import org.eclipse.daanse.mdx.model.api.select.SelectWithClause;
import org.eclipse.daanse.olap.api.model.Cube;

import java.util.ArrayList;
import java.util.List;

public class QueryProviderImpl implements QueryProvider{

    public Query createQuery(SelectStatement selectStatement) {

        Statement statement = null;
        Cube mdxCube = null;
        QueryAxis[] axes = null;
        QueryAxis slicerAxis = null;
        QueryPart[] cellProps = null;
        Parameter[] parameters = null;
        boolean strictValidation = false;
        Subcube subcube = getSubcube(selectStatement.selectSubcubeClause());
        List<Formula> formulaList = getFormulaList(selectStatement.selectWithClauses());


        return new Query(
            statement,
            mdxCube,
            formulaList.toArray(Formula[]::new),
            subcube,
            axes,
            slicerAxis,
            cellProps,
            parameters,
            strictValidation
        );
    }

    private Subcube getSubcube(SelectSubcubeClause selectSubcubeClause) {
        String cubeName = null;
        Subcube subcube = null;
        QueryAxis[] axes = null;
        QueryAxis slicerAxis = null;
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
                    //TODO
                }
            }
        }
        return formulaList;
    }

    private Exp getExp(Expression expression) {
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
}
