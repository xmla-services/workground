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
import mondrian.server.Session;
import org.eclipse.daanse.olap.api.CacheControl;
import org.eclipse.daanse.olap.api.Command;
import org.eclipse.daanse.olap.api.Connection;
import org.eclipse.daanse.olap.api.Context;
import org.eclipse.daanse.olap.api.Statement;
import org.eclipse.daanse.olap.api.element.Cube;
import org.eclipse.daanse.olap.api.element.Schema;
import org.eclipse.daanse.olap.api.query.component.CalculatedFormula;
import org.eclipse.daanse.olap.api.query.component.DmvQuery;
import org.eclipse.daanse.olap.api.query.component.DrillThrough;
import org.eclipse.daanse.olap.api.query.component.Refresh;
import org.eclipse.daanse.olap.api.query.component.TransactionCommand;
import org.eclipse.daanse.olap.api.query.component.Update;
import org.eclipse.daanse.olap.api.result.CellSet;
import org.eclipse.daanse.olap.xmla.bridge.ContextGroupXmlaServiceConfig;
import org.eclipse.daanse.olap.xmla.bridge.ContextListSupplyer;
import org.eclipse.daanse.xmla.api.common.properties.Content;
import org.eclipse.daanse.xmla.api.common.properties.Format;
import org.eclipse.daanse.xmla.api.execute.ExecuteService;
import org.eclipse.daanse.xmla.api.execute.alter.AlterRequest;
import org.eclipse.daanse.xmla.api.execute.alter.AlterResponse;
import org.eclipse.daanse.xmla.api.execute.cancel.CancelRequest;
import org.eclipse.daanse.xmla.api.execute.cancel.CancelResponse;
import org.eclipse.daanse.xmla.api.execute.clearcache.ClearCacheRequest;
import org.eclipse.daanse.xmla.api.execute.clearcache.ClearCacheResponse;
import org.eclipse.daanse.xmla.api.execute.statement.StatementRequest;
import org.eclipse.daanse.xmla.api.execute.statement.StatementResponse;
import org.eclipse.daanse.xmla.model.record.execute.statement.StatementResponseR;
import org.olap4j.Scenario;

import java.util.List;
import java.util.Optional;

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
                return executeDrillThroughQuery(context, statementRequest);
            } else if (query instanceof CalculatedFormula calculatedFormula) {
                return executeCalculatedFormula(context, statementRequest);
            } else if (query instanceof DmvQuery dmvQuery) {
                return executeDmvQuery(context, statementRequest);
            } else if (query instanceof Refresh refresh) {
                return executeRefresh(context, refresh);
            } else if (query instanceof Update update) {
                return executeUpdate(context, statementRequest);
            } else if (query instanceof TransactionCommand transactionCommand) {
                return executeTransactionCommand(context, transactionCommand);
            } else {
                return executeQuery(context, statementRequest);
            }
        }
        return null;
	}

    private StatementResponse executeQuery(Context context, StatementRequest statementRequest) {
        Statement statement = context.getConnection().createStatement();
        String mdx = statementRequest.command().statement();
        if ((mdx != null) && (mdx.length() == 0)) {
            CellSet cellSet = statement.executeQuery(statementRequest.command().statement());
            Optional<Content> content = statementRequest.properties().content();
            boolean omitDefaultSlicerInfo = false;
            if (!content.isPresent() || !Content.DATA_INCLUDE_DEFAULT_SLICER.equals(content.get())) {
                    omitDefaultSlicerInfo = true;
            }
            boolean json = false; //TODO? I think we don't need that at all
            Optional<Format> format = statementRequest.properties().format();
            if (!format.isPresent()
                || Format.NATIVE.equals(format.get())
                || Format.MULTIDIMENSIONAL.equals(format.get())) {
                return Convertor.toStatementResponseMddataset(cellSet, omitDefaultSlicerInfo, json);
            }
            return Convertor.toStatementResponseRowSet(cellSet);
        }
        return null;
    }

    private StatementResponse executeTransactionCommand(Context context, TransactionCommand transactionCommand) {
        String sessionId = context.getSessionId();
        Session session = Session.get(sessionId);
        if(transactionCommand.getCommand() == Command.BEGIN) {
            Scenario scenario = context.createScenario();
            session.setScenario(scenario);
        }
        else if(transactionCommand.getCommand() == Command.ROLLBACK) {
            session.setScenario(null);
        }
        else if(transactionCommand.getCommand() == Command.COMMIT) {
            session.setScenario(null);
        }
        return new StatementResponseR(null, null);
    }

    private StatementResponse executeUpdate(Context context, StatementRequest statementRequest) {
        return null;
        //TODO
    }

    private StatementResponse executeRefresh(Context context, Refresh refresh) {
        Connection connection = context.getConnection();
        Schema schema = connection.getSchema();
        Cube cube = schema.lookupCube(refresh.getCubeName(), true);
        flushCache(cube, connection);
        return new StatementResponseR(null, null);
    }

    private void flushCache(Cube cube, Connection connection) {
        final CacheControl cacheControl = connection.getCacheControl(null);
        cacheControl.flush(cacheControl.createMeasuresRegion(cube));
        //TODO


    }

    private StatementResponse executeDmvQuery(Context context, StatementRequest statementRequest) {
        return null;
        //TODO
    }

    private StatementResponse executeCalculatedFormula(Context context, StatementRequest statementRequest) {
        return null;
        //TODO
    }

    private StatementResponse executeDrillThroughQuery(Context context, StatementRequest statementRequest) {
	    return null;
	    //TODO
    }

}
