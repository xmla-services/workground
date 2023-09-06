/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara.
* Copyright (c) 2021 Sergei Semenkov
* All rights reserved.
*/

package mondrian.parser;

import java.util.List;

import mondrian.olap.api.CalculatedFormula;
import mondrian.olap.api.Command;
import mondrian.olap.api.DmvQuery;
import mondrian.olap.api.DrillThrough;
import mondrian.olap.Exp;
import mondrian.olap.ExplainImpl;
import mondrian.olap.FunTable;
import mondrian.olap.api.QueryAxis;
import mondrian.olap.UpdateImpl;
import mondrian.olap.api.Explain;
import mondrian.olap.api.Formula;
import mondrian.olap.api.Query;
import mondrian.olap.api.QueryPart;
import mondrian.olap.api.Refresh;
import mondrian.olap.api.Subcube;
import mondrian.olap.api.TransactionCommand;
import mondrian.olap.api.Update;
import mondrian.olap.api.UpdateClause;
import mondrian.server.Statement;

/**
 * Parses and validates an MDX statement.
 *
 * <p>NOTE: API is subject to change. Current implementation is backwards
 * compatible with the old parser based on JavaCUP.
 *
 * @author jhyde
 */
public interface MdxParserValidator {

    /**
      * Parses a string to create a {@link mondrian.olap.QueryImpl}.
      * Called only by {@link mondrian.olap.ConnectionBase#parseQuery}.
      */
    QueryPart parseInternal(
        Statement statement,
        String queryString,
        boolean debug,
        FunTable funTable,
        boolean strictValidation);

    Exp parseExpression(
        Statement statement,
        String queryString,
        boolean debug,
        FunTable funTable);

    interface QueryPartFactory {

        /**
         * Creates a {@link mondrian.olap.QueryImpl} object.
         * Override this function to make your kind of query.
         */
        Query makeQuery(
            Statement statement,
            Formula[] formulae,
            QueryAxis[] axes,
            Subcube subcube,
            Exp slicer,
            QueryPart[] cellProps,
            boolean strictValidation);

        /**
         * Creates a {@link DrillThrough} object.
         */
        DrillThrough makeDrillThrough(
            Query query,
            int maxRowCount,
            int firstRowOrdinal,
            List<Exp> returnList);

        CalculatedFormula makeCalculatedFormula(
                String cubeName,
                Formula e);

        /**
         * Creates an {@link ExplainImpl} object.
         */
        Explain makeExplain(
            QueryPart query);

        Refresh makeRefresh(
                String cubeName);

        Update makeUpdate(
                String cubeName,
                List<UpdateClause> list);

        DmvQuery makeDmvQuery(
                String tableName,
                List<String> columns,
                Exp whereExpression);

        TransactionCommand makeTransactionCommand(
                Command c);
    }
}
