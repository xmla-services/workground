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
package org.eclipse.daanse.olap.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.daanse.olap.api.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mondrian.olap.MondrianException;
import mondrian.resource.MondrianResource;
import mondrian.rolap.RolapConnection;
import mondrian.rolap.RolapResultShepherd;
import mondrian.rolap.agg.AggregationManager;
import mondrian.server.MonitorImpl;
import mondrian.server.Statement;
import mondrian.server.monitor.ConnectionEndEvent;
import mondrian.server.monitor.ConnectionStartEvent;
import mondrian.server.monitor.Monitor;
import mondrian.server.monitor.StatementEndEvent;
import mondrian.server.monitor.StatementStartEvent;

public abstract class AbstractBasicContext implements Context {

	public static final String SERVER_ALREADY_SHUTDOWN = "Server already shutdown.";
	/**
	 * Id of server. Unique within JVM's lifetime. Not the same as the ID of the
	 * server within a lockbox.
	 */
	private final int id = ID_GENERATOR.incrementAndGet();

	protected RolapResultShepherd shepherd;

	@SuppressWarnings("unchecked")
	private final List<RolapConnection> connections = Collections.synchronizedList(new ArrayList<>());

	@SuppressWarnings("unchecked")
	private final List<Statement> statements =Collections.synchronizedList(new ArrayList<>());

	private final MonitorImpl monitor = new MonitorImpl();

	protected AggregationManager aggMgr;

	private boolean shutdown = false;

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBasicContext.class);

	private static final AtomicInteger ID_GENERATOR = new AtomicInteger();

	public static final List<String> KEYWORD_LIST = Collections.unmodifiableList(Arrays.asList("$AdjustedProbability",
			"$Distance", "$Probability", "$ProbabilityStDev", "$ProbabilityStdDeV", "$ProbabilityVariance", "$StDev",
			"$StdDeV", "$Support", "$Variance", "AddCalculatedMembers", "Action", "After", "Aggregate", "All", "Alter",
			"Ancestor", "And", "Append", "As", "ASC", "Axis", "Automatic", "Back_Color", "BASC", "BDESC", "Before",
			"Before_And_After", "Before_And_Self", "Before_Self_After", "BottomCount", "BottomPercent", "BottomSum",
			"Break", "Boolean", "Cache", "Calculated", "Call", "Case", "Catalog_Name", "Cell", "Cell_Ordinal", "Cells",
			"Chapters", "Children", "Children_Cardinality", "ClosingPeriod", "Cluster", "ClusterDistance",
			"ClusterProbability", "Clusters", "CoalesceEmpty", "Column_Values", "Columns", "Content", "Contingent",
			"Continuous", "Correlation", "Cousin", "Covariance", "CovarianceN", "Create", "CreatePropertySet",
			"CrossJoin", "Cube", "Cube_Name", "CurrentMember", "CurrentCube", "Custom", "Cyclical", "DefaultMember",
			"Default_Member", "DESC", "Descendents", "Description", "Dimension", "Dimension_Unique_Name", "Dimensions",
			"Discrete", "Discretized", "DrillDownLevel", "DrillDownLevelBottom", "DrillDownLevelTop", "DrillDownMember",
			"DrillDownMemberBottom", "DrillDownMemberTop", "DrillTrough", "DrillUpLevel", "DrillUpMember", "Drop",
			"Else", "Empty", "End", "Equal_Areas", "Exclude_Null", "ExcludeEmpty", "Exclusive", "Expression", "Filter",
			"FirstChild", "FirstRowset", "FirstSibling", "Flattened", "Font_Flags", "Font_Name", "Font_size",
			"Fore_Color", "Format_String", "Formatted_Value", "Formula", "From", "Generate", "Global", "Head",
			"Hierarchize", "Hierarchy", "Hierary_Unique_name", "IIF", "IsEmpty", "Include_Null", "Include_Statistics",
			"Inclusive", "Input_Only", "IsDescendant", "Item", "Lag", "LastChild", "LastPeriods", "LastSibling", "Lead",
			"Level", "Level_Number", "Level_Unique_Name", "Levels", "LinRegIntercept", "LinRegR2", "LinRegPoint",
			"LinRegSlope", "LinRegVariance", "Long", "MaxRows", "Median", "Member", "Member_Caption", "Member_Guid",
			"Member_Name", "Member_Ordinal", "Member_Type", "Member_Unique_Name", "Members", "Microsoft_Clustering",
			"Microsoft_Decision_Trees", "Mining", "Model", "Model_Existence_Only", "Models", "Move", "MTD", "Name",
			"Nest", "NextMember", "Non", "NonEmpty", "Normal", "Not", "Ntext", "Nvarchar", "OLAP", "On",
			"OpeningPeriod", "OpenQuery", "Or", "Ordered", "Ordinal", "Pages", "ParallelPeriod", "Parent",
			"Parent_Level", "Parent_Unique_Name", "PeriodsToDate", "PMML", "Predict", "Predict_Only",
			"PredictAdjustedProbability", "PredictHistogram", "Prediction", "PredictionScore", "PredictProbability",
			"PredictProbabilityStDev", "PredictProbabilityVariance", "PredictStDev", "PredictSupport",
			"PredictVariance", "PrevMember", "Probability", "Probability_StDev", "Probability_StdDev",
			"Probability_Variance", "Properties", "Property", "QTD", "RangeMax", "RangeMid", "RangeMin", "Rank",
			"Recursive", "Refresh", "Related", "Rename", "Rollup", "Rows", "Schema_Name", "Sections", "Select", "Self",
			"Self_And_After", "Sequence_Time", "Server", "Session", "Set", "SetToArray", "SetToStr", "Shape", "Skip",
			"Solve_Order", "Sort", "StdDev", "Stdev", "StripCalculatedMembers", "StrToSet", "StrToTuple", "SubSet",
			"Support", "Tail", "Text", "Thresholds", "ToggleDrillState", "TopCount", "TopPercent", "TopSum",
			"TupleToStr", "Under", "Uniform", "UniqueName", "Use", "Value", "Var", "Variance", "VarP", "VarianceP",
			"VisualTotals", "When", "Where", "With", "WTD", "Xor"));

	@Override
	protected void finalize() throws Throwable {
		try {
			super.finalize();
			shutdown(true);
		} catch (Throwable t) {
			LOGGER.info(MondrianResource.instance().FinalizerErrorMondrianServerImpl.baseMessage, t);
		}
	}

	protected int getId() {
		return id;
	}

	@Override
	public RolapResultShepherd getResultShepherd() {
		if (shutdown) {
			throw new MondrianException(SERVER_ALREADY_SHUTDOWN);
		}
		return this.shepherd;
	}

	// @Override
	public List<String> getKeywords() {
		return KEYWORD_LIST;
	}

	@Override
	public AggregationManager getAggregationManager() {
		if (shutdown) {
			throw new MondrianException(SERVER_ALREADY_SHUTDOWN);
		}
		return aggMgr;
	}

	protected void shutdown() {
		this.shutdown(false);
	}

	private void shutdown(boolean silent) {

		if (shutdown) {
			if (silent) {
				return;
			}
			throw new MondrianException("Server already shutdown.");
		}
		this.shutdown = true;
		aggMgr.shutdown();
		monitor.shutdown();

		shepherd.shutdown();
	}

	@Override
	synchronized public void addConnection(RolapConnection connection) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addConnection , id={}, statements={}, connections=", id, statements.size(),
					connections.size());
		}
		if (shutdown) {
			throw new MondrianException("Server already shutdown.");
		}
		connections.add(connection);
		monitor.sendEvent(new ConnectionStartEvent(System.currentTimeMillis(), connection.getContext().getName(),
				connection.getId()));
	}

	@Override
	synchronized public void removeConnection(RolapConnection connection) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("removeConnection , id={}, statements={}, connections={}", id, statements.size(),
					connections.size());
		}
		if (shutdown) {
			throw new MondrianException("Server already shutdown.");
		}
		connections.remove(connection.getId());
		monitor.sendEvent(new ConnectionEndEvent(System.currentTimeMillis(), getName(), connection.getId()));
	}

	@Override
	public synchronized void addStatement(Statement statement) {
		if (shutdown) {
			throw new MondrianException("Server already shutdown.");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addStatement , id={}, statements={}, connections={}", id, statements.size(),
					connections.size());
		}
		statements.add( statement);
		final RolapConnection connection = statement.getMondrianConnection();
		monitor.sendEvent(new StatementStartEvent(System.currentTimeMillis(), connection.getContext().getName(),
				connection.getId(), statement.getId()));
	}

	@Override
	public synchronized void removeStatement(Statement statement) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("removeStatement , id={}, statements={}, connections={}", id, statements.size(),
					connections.size());
		}
		if (shutdown) {
			throw new MondrianException("Server already shutdown.");
		}
		statements.remove(statement.getId());
		final RolapConnection connection = statement.getMondrianConnection();
		monitor.sendEvent(new StatementEndEvent(System.currentTimeMillis(), connection.getContext().getName(),
				connection.getId(), statement.getId()));
	}

	@Override
	public Monitor getMonitor() {
		if (shutdown) {
			throw new MondrianException("Server already shutdown.");
		}
		return monitor;
	}



	@Override
	public List<Statement> getStatements(org.eclipse.daanse.olap.api.Connection connection) {
		return statements.stream().filter(stmnt -> stmnt.getMondrianConnection().equals(connection))
				.toList();
	}

}