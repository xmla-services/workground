/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package mondrian.olap;

import mondrian.olap.api.DrillThrough;
import mondrian.olap.api.Explain;
import mondrian.olap.api.Query;
import mondrian.olap.api.QueryPart;

import java.io.PrintWriter;

/**
 * Explain statement.
 *
 * @author jhyde
 */
public class ExplainImpl extends AbstractQueryPart implements Explain {
    private final QueryPart query;

    /**
     * Creates an Explain statement.
     *
     * @param query Query (SELECT or DRILLTHROUGH)
     */
    public ExplainImpl(
        QueryPart query)
    {
        this.query = query;
        assert this.query != null;
        assert this.query instanceof Query
            || this.query instanceof DrillThrough;
    }

    @Override
    public void unparse(PrintWriter pw) {
        pw.print("EXPLAIN PLAN FOR ");
        query.unparse(pw);
    }

    @Override
    public Object[] getChildren() {
        return new Object[] {query};
    }

    public QueryPart getQuery() {
        return query;
    }
}
