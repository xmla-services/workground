/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/
package mondrian.olap;

import java.util.List;

import mondrian.olap.api.CalculatedFormula;
import mondrian.olap.api.CellProperty;
import mondrian.olap.api.Command;
import mondrian.olap.api.DmvQuery;
import mondrian.olap.api.DrillThrough;
import mondrian.olap.api.Explain;
import mondrian.olap.api.Formula;
import mondrian.olap.api.Id;
import mondrian.olap.api.Query;
import mondrian.olap.api.QueryAxis;
import mondrian.olap.api.QueryPart;
import mondrian.olap.api.Refresh;
import mondrian.olap.api.Subcube;
import mondrian.olap.api.SubtotalVisibility;
import mondrian.olap.api.TransactionCommand;
import mondrian.olap.api.Update;
import mondrian.olap.api.UpdateClause;
import mondrian.parser.MdxParserValidator;
import mondrian.server.Statement;


public class FactoryImpl
    implements MdxParserValidator.QueryPartFactory
{

	@Override
	public Query makeQuery(Statement statement, Formula[] formulae, QueryAxis[] axes, Subcube subcube, Exp slicer,
			CellProperty[] cellProps, boolean strictValidation) {
		final QueryAxis slicerAxis =
            slicer == null
                ? null
                : new QueryAxisImpl(
                    false, slicer, AxisOrdinal.StandardAxisOrdinal.SLICER,
                    SubtotalVisibility.Undefined, new Id[0]);

		
		return new QueryImpl(statement,formulae,axes,subcube,slicerAxis,cellProps,strictValidation);
    }

    public DrillThrough makeDrillThrough(
        Query query,
        int maxRowCount,
        int firstRowOrdinal,
        List<Exp> returnList)
    {
        return new DrillThroughImpl(
            query, maxRowCount, firstRowOrdinal, returnList);
    }

    public CalculatedFormula makeCalculatedFormula(
        String cubeName,
        Formula e)
    {
        return new CalculatedFormulaImpl(cubeName, e);
    }

    /**
     * Creates an {@link Explain} object.
     */
    public Explain makeExplain(
        QueryPart query)
    {
        return new ExplainImpl(query);
    }

    public Refresh makeRefresh(
            String cubeName)
    {
        return new RefreshImpl(cubeName);
    }

    public Update makeUpdate(
            String cubeName,
            List<UpdateClause> list)
    {
        return new UpdateImpl(cubeName, list);
    }

    public DmvQuery makeDmvQuery(
            String tableName,
            List<String> columns,
            Exp whereExpression)
    {
        return new DmvQueryImpl(tableName, columns, whereExpression);
    }

    public TransactionCommand makeTransactionCommand(
            Command c)
    {
        return new TransactionCommandImpl(c);
    }


}