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

import mondrian.olap.MondrianProperties;
import mondrian.olap.QueryImpl;
import mondrian.server.Session;
import mondrian.xmla.XmlaException;
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
import org.eclipse.daanse.olap.api.query.component.Formula;
import org.eclipse.daanse.olap.api.query.component.Refresh;
import org.eclipse.daanse.olap.api.query.component.TransactionCommand;
import org.eclipse.daanse.olap.api.query.component.Update;
import org.eclipse.daanse.olap.api.query.component.UpdateClause;
import org.eclipse.daanse.olap.api.result.Cell;
import org.eclipse.daanse.olap.api.result.CellSet;
import org.eclipse.daanse.olap.api.result.CellSetAxis;
import org.eclipse.daanse.olap.api.result.Property;
import org.eclipse.daanse.olap.xmla.bridge.ContextGroupXmlaServiceConfig;
import org.eclipse.daanse.olap.xmla.bridge.ContextListSupplyer;
import org.eclipse.daanse.olap.xmla.bridge.discover.DBSchemaDiscoverService;
import org.eclipse.daanse.xmla.api.common.enums.ColumnOlapTypeEnum;
import org.eclipse.daanse.xmla.api.common.properties.Content;
import org.eclipse.daanse.xmla.api.common.properties.Format;
import org.eclipse.daanse.xmla.api.common.properties.OperationNames;
import org.eclipse.daanse.xmla.api.discover.Properties;
import org.eclipse.daanse.xmla.api.discover.dbschema.columns.DbSchemaColumnsRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.columns.DbSchemaColumnsResponseRow;
import org.eclipse.daanse.xmla.api.execute.ExecuteService;
import org.eclipse.daanse.xmla.api.execute.alter.AlterRequest;
import org.eclipse.daanse.xmla.api.execute.alter.AlterResponse;
import org.eclipse.daanse.xmla.api.execute.cancel.CancelRequest;
import org.eclipse.daanse.xmla.api.execute.cancel.CancelResponse;
import org.eclipse.daanse.xmla.api.execute.clearcache.ClearCacheRequest;
import org.eclipse.daanse.xmla.api.execute.clearcache.ClearCacheResponse;
import org.eclipse.daanse.xmla.api.execute.statement.StatementRequest;
import org.eclipse.daanse.xmla.api.execute.statement.StatementResponse;
import org.eclipse.daanse.xmla.model.record.discover.PropertiesR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.columns.DbSchemaColumnsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.columns.DbSchemaColumnsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.execute.alter.AlterResponseR;
import org.eclipse.daanse.xmla.model.record.execute.cancel.CancelResponseR;
import org.eclipse.daanse.xmla.model.record.execute.clearcache.ClearCacheResponseR;
import org.eclipse.daanse.xmla.model.record.execute.statement.StatementResponseR;
import org.eclipse.daanse.xmla.model.record.mddataset.MddatasetR;
import org.eclipse.daanse.xmla.model.record.mddataset.RowSetR;
import org.eclipse.daanse.xmla.model.record.xmla_empty.EmptyresultR;
import org.olap4j.AllocationPolicy;
import org.olap4j.Scenario;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static mondrian.xmla.XmlaConstants.CLIENT_FAULT_FC;
import static mondrian.xmla.XmlaConstants.HSB_DRILL_THROUGH_SQL_CODE;
import static mondrian.xmla.XmlaConstants.HSB_DRILL_THROUGH_SQL_FAULT_FS;
import static mondrian.xmla.XmlaConstants.SERVER_FAULT_FC;
import static mondrian.xmla.XmlaConstants.USM_DOM_PARSE_FAULT_FS;

public class OlapExecuteService implements ExecuteService {
    public static final String SESSION_ID = "sessionId";
    public static final String CODE3238658121 = "3238658121";
	private ContextListSupplyer contextsListSupplyer;
    private ContextGroupXmlaServiceConfig config;
    private DBSchemaDiscoverService dbSchemaService = new DBSchemaDiscoverService(contextsListSupplyer);

	public OlapExecuteService(ContextListSupplyer contextsListSupplyer, ContextGroupXmlaServiceConfig config) {
		this.contextsListSupplyer=contextsListSupplyer;
        this.config = config;
	}

	@Override
	public AlterResponse alter(AlterRequest statementRequest) {
	    //TODO
        return new AlterResponseR(new EmptyresultR(null, null));
	}

	@Override
	public CancelResponse cancel(CancelRequest cancel) {
        List<Context> contexts = contextsListSupplyer.get();
        for (Context context : contexts) {
            try {
                final Connection connection = context.getConnection();
            /*
            final mondrian.rolap.RolapConnection rolapConnection1 =
                ((mondrian.olap4j.MondrianOlap4jConnection) connection).getMondrianConnection();
            for(XmlaRequest xmlaRequest: currentRequests){
                if(xmlaRequest.getSessionId().equals(rolapConnection1.getConnectInfo().get(SESSION_ID))){
                    ((mondrian.xmla.impl.DefaultXmlaRequest)xmlaRequest).setProperty(CANCELED, "true");
                }
            }
            */
                mondrian.olap.MondrianServer mondrianServer =
                    mondrian.olap.MondrianServer.forConnection(connection);
                String sessionId = cancel.command().sessionID();
                for (mondrian.server.Statement statement : mondrianServer.getStatements(sessionId)) {
                    if (statement.getMondrianConnection().getConnectInfo().get(SESSION_ID).equals(sessionId)) {
                        statement.cancel();
                    }
                }
            /*
            for(XmlaRequest xmlaRequest: currentRequests){
                if(xmlaRequest.getSessionId().equals(sessionId)){
                    ((mondrian.xmla.impl.DefaultXmlaRequest)xmlaRequest).setProperty(CANCELED, "true");
                }
            }
            */
            } catch (org.olap4j.OlapException oe) {
                throw new XmlaException(
                    CLIENT_FAULT_FC,
                    CODE3238658121,
                    USM_DOM_PARSE_FAULT_FS,
                    oe);
            } catch (java.sql.SQLException oe) {
                throw new XmlaException(
                    CLIENT_FAULT_FC,
                    CODE3238658121,
                    USM_DOM_PARSE_FAULT_FS,
                    oe);
            }
        }
		return new CancelResponseR(new EmptyresultR(null, null));
	}

	@Override
	public ClearCacheResponse clearCache(ClearCacheRequest clearCacheRequest) {
		// TODO Auto-generated method stub
		return new ClearCacheResponseR(new EmptyresultR(null, null));
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
                return executeCalculatedFormula(context, calculatedFormula);
            } else if (query instanceof DmvQuery dmvQuery) {
                return executeDmvQuery(context, dmvQuery, statementRequest);
            } else if (query instanceof Refresh refresh) {
                return executeRefresh(context, refresh);
            } else if (query instanceof Update update) {
                return executeUpdate(context, update);
            } else if (query instanceof TransactionCommand transactionCommand) {
                return executeTransactionCommand(context, statementRequest, transactionCommand);
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

    private StatementResponse executeTransactionCommand(Context context, StatementRequest statementRequest,
                                                        TransactionCommand transactionCommand) {
        String sessionId = statementRequest.sessionId();
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

    private StatementResponse executeUpdate(Context context, Update update) {
        Scenario scenario = context.createScenario();
        Connection connection = context.getConnection();
        final Schema schema = connection.getSchema();
        for(UpdateClause updateClause: update.getUpdateClauses()) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new mondrian.mdx.QueryPrintWriter(sw);
            updateClause.getTupleExp().unparse(pw);
            String tupleString = sw.toString();

            Statement pstmt = connection.createStatement();
            CellSet cellSet = pstmt.executeQuery(
                new StringBuilder("SELECT ")
                    .append(tupleString)
                    .append(" ON 0 FROM ")
                    .append(update.getCubeName())
                    .append(" CELL PROPERTIES CELL_ORDINAL").toString()
            );
            CellSetAxis axis = cellSet.getAxes().get(0);
            if(axis.getPositionCount() == 0) {
                //Empty tuple exception
            }
            if (axis.getPositionCount() == 1) {
                //More than one tuple exception
            }
            Cell writeBackCell = cellSet.getCell(Arrays.asList(0));

            sw = new StringWriter();
            pw = new mondrian.mdx.QueryPrintWriter(sw);
            updateClause.getValueExp().unparse(pw);
            String valueString = sw.toString();

            pstmt = connection.createStatement();
            cellSet = pstmt.executeQuery(
                new StringBuilder("WITH MEMBER [Measures].[m1] AS ")
                    .append(valueString)
                    .append(" SELECT [Measures].[m1] ON 0 FROM ")
                    .append(update.getCubeName())
                    .append(" CELL PROPERTIES VALUE").toString()
            );
            Cell cell = cellSet.getCell(Arrays.asList(0));

            writeBackCell.setValue(scenario, cell.getValue(), AllocationPolicy.EQUAL_ALLOCATION);
        }
        return new StatementResponseR(null, null);
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

    private StatementResponse executeDmvQuery(Context context, DmvQuery dmvQuery, StatementRequest statementRequest) {
        Properties properties = statementRequest.properties();
        String tableName = dmvQuery.getTableName().toUpperCase();
        RowSetR rowSet = null;
        switch (tableName) {
            case OperationNames.DBSCHEMA_CATALOGS:
                DbSchemaColumnsRestrictionsR r = new DbSchemaColumnsRestrictionsR(
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty()
                );
                DbSchemaColumnsRequest request = new DbSchemaColumnsRequestR((PropertiesR) statementRequest.properties(), r);
                rowSet = Convertor.DbSchemaColumnsResponseRowToRowSet(dbSchemaService.dbSchemaColumns(request));
            //TODO
        }

        return new StatementResponseR(null, rowSet);
    }

    private StatementResponse executeCalculatedFormula(Context context, CalculatedFormula calculatedFormula) {
        Formula formula = calculatedFormula.getFormula();
        Connection connection = context.getConnection();
        final Schema schema = connection.getSchema();
        final Cube cube = schema.lookupCube(calculatedFormula.getCubeName(), true);
        if(formula.isMember()){
            cube.createCalculatedMember(formula);
        }
        else {
            cube.createNamedSet(formula);
        }
        return new StatementResponseR(null, null);
    }

    private StatementResponse executeDrillThroughQuery(Context context, StatementRequest statementRequest) {
        Optional<String> tabFields = statementRequest.properties().tableFields();
        Optional<Boolean> advanced = statementRequest.properties().advancedFlag();
        final boolean enableRowCount = MondrianProperties.instance().EnableTotalCount.booleanValue();
        final int[] rowCountSlot = enableRowCount ? new int[]{0} : null;
        Connection connection = null;
        Statement statement;
        ResultSet resultSet = null;
        try {
            connection = context.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                statementRequest.command().statement(),
                advanced,
                tabFields,
                rowCountSlot);
            int rowCount = enableRowCount ? rowCountSlot[0] : -1;
            return Convertor.toStatementResponseRowSet(resultSet, rowCount);
        } catch (Exception e) {
            // NOTE: One important error is "cannot drill through on the cell"
            throw new XmlaException(
                SERVER_FAULT_FC,
                HSB_DRILL_THROUGH_SQL_CODE,
                HSB_DRILL_THROUGH_SQL_FAULT_FS,
                e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

}
