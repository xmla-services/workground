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

import org.eclipse.daanse.olap.api.Command;
import org.eclipse.daanse.olap.api.Statement;
import org.eclipse.daanse.olap.api.SubtotalVisibility;
import org.eclipse.daanse.olap.api.query.component.AxisOrdinal;
import org.eclipse.daanse.olap.api.query.component.CalculatedFormula;
import org.eclipse.daanse.olap.api.query.component.CellProperty;
import org.eclipse.daanse.olap.api.query.component.DmvQuery;
import org.eclipse.daanse.olap.api.query.component.DrillThrough;
import org.eclipse.daanse.olap.api.query.component.Explain;
import org.eclipse.daanse.olap.api.query.component.Expression;
import org.eclipse.daanse.olap.api.query.component.Formula;
import org.eclipse.daanse.olap.api.query.component.Id;
import org.eclipse.daanse.olap.api.query.component.Query;
import org.eclipse.daanse.olap.api.query.component.QueryAxis;
import org.eclipse.daanse.olap.api.query.component.QueryComponent;
import org.eclipse.daanse.olap.api.query.component.Refresh;
import org.eclipse.daanse.olap.api.query.component.Subcube;
import org.eclipse.daanse.olap.api.query.component.TransactionCommand;
import org.eclipse.daanse.olap.api.query.component.Update;
import org.eclipse.daanse.olap.api.query.component.UpdateClause;

import mondrian.parser.MdxParserValidator;


public class FactoryImpl
    implements MdxParserValidator.QueryComponentFactory
{

	@Override
	public Query makeQuery(Statement statement, Formula[] formulae, QueryAxis[] axes, Subcube subcube, Expression slicer,
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
        List<Expression> returnList)
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
        QueryComponent query)
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
            Expression whereExpression)
    {
        return new DmvQueryImpl(tableName, columns, whereExpression);
    }

    public TransactionCommand makeTransactionCommand(
            Command c)
    {
        return new TransactionCommandImpl(c);
    }


}