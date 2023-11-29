/*
* Copyright (c) 2022 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.olap.xmla.bridge.execute;

import mondrian.olap.QueryImpl;
import org.eclipse.daanse.olap.api.Context;
import org.eclipse.daanse.olap.api.query.component.CalculatedFormula;
import org.eclipse.daanse.olap.api.query.component.DmvQuery;
import org.eclipse.daanse.olap.api.query.component.DrillThrough;
import org.eclipse.daanse.olap.api.query.component.Refresh;
import org.eclipse.daanse.olap.api.query.component.TransactionCommand;
import org.eclipse.daanse.olap.api.query.component.Update;
import org.eclipse.daanse.olap.xmla.bridge.ContextGroupXmlaServiceConfig;
import org.eclipse.daanse.olap.xmla.bridge.ContextListSupplyer;
import org.eclipse.daanse.xmla.api.execute.ExecuteService;
import org.eclipse.daanse.xmla.api.execute.alter.AlterRequest;
import org.eclipse.daanse.xmla.api.execute.alter.AlterResponse;
import org.eclipse.daanse.xmla.api.execute.cancel.CancelRequest;
import org.eclipse.daanse.xmla.api.execute.cancel.CancelResponse;
import org.eclipse.daanse.xmla.api.execute.clearcache.ClearCacheRequest;
import org.eclipse.daanse.xmla.api.execute.clearcache.ClearCacheResponse;
import org.eclipse.daanse.xmla.api.execute.statement.StatementRequest;
import org.eclipse.daanse.xmla.api.execute.statement.StatementResponse;

import java.util.List;

public class OlapExecuteService implements ExecuteService {

	private ContextListSupplyer contextsListSupplyer;
    private ContextGroupXmlaServiceConfig config;

	public OlapExecuteService(ContextListSupplyer contextsListSupplyer, ContextGroupXmlaServiceConfig config) {
		this.contextsListSupplyer=contextsListSupplyer;
        this.config = config;
	}

	@Override
	public AlterResponse alter(AlterRequest statementRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CancelResponse cancel(CancelRequest capture) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClearCacheResponse clearCache(ClearCacheRequest clearCacheRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatementResponse statement(StatementRequest statementRequest) {
        List<Context> contexts = contextsListSupplyer.get();
        String statement = statementRequest.command().statement();
        for (Context context : contexts) {
            QueryImpl query = context.getConnection().parseQuery(statement);
            if (query instanceof DrillThrough) {
                return executeDrillThroughQuery(statementRequest);
            } else if (query instanceof CalculatedFormula calculatedFormula) {
                return executeCalculatedFormula(statementRequest);
            } else if (query instanceof DmvQuery dmvQuery) {
                return executeDmvQuery(statementRequest);
            } else if (query instanceof Refresh refresh) {
                return executeRefresh(statementRequest);
            } else if (query instanceof Update update) {
                return executeUpdate(statementRequest);
            } else if (query instanceof TransactionCommand) {
                return executeTransactionCommand(statementRequest);
            } else {
                return executeQuery(statementRequest);
            }
        }
        return null;
	}

    private StatementResponse executeQuery(StatementRequest statementRequest) {
        return null;
        //TODO
    }

    private StatementResponse executeTransactionCommand(StatementRequest statementRequest) {
        return null;
        //TODO

    }

    private StatementResponse executeUpdate(StatementRequest statementRequest) {
        return null;
        //TODO
    }

    private StatementResponse executeRefresh(StatementRequest statementRequest) {
        return null;
        //TODO
    }

    private StatementResponse executeDmvQuery(StatementRequest statementRequest) {
        return null;
        //TODO
    }

    private StatementResponse executeCalculatedFormula(StatementRequest statementRequest) {
        return null;
        //TODO
    }

    private StatementResponse executeDrillThroughQuery(StatementRequest statementRequest) {
	    return null;
	    //TODO
    }

}
