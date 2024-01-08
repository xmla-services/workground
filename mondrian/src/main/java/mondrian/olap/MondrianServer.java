/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..
* Copyright (C) 2021 Sergei Semenkov
* All rights reserved.
*/

package mondrian.olap;

import java.util.List;
import java.util.Map;

import mondrian.rolap.RolapConnection;
import mondrian.rolap.RolapResultShepherd;
import mondrian.rolap.agg.AggregationManager;
import mondrian.server.Statement;
import mondrian.server.monitor.Monitor;

/**
 * Interface by which to control an instance of Mondrian.
 *
 * <p>Typically, there is only one instance of Mondrian per JVM. However, you
 * access a MondrianServer via the {@link #forConnection} method for future
 * expansion.
 *
 * @author jhyde
 * @since Jun 25, 2006
 */
public abstract class MondrianServer {
  




    /**
     * Returns an integer uniquely identifying this server within its JVM.
     *
     * @return Server's unique identifier
     */
    public abstract int getId();

  
    /**
     * Returns a list of MDX keywords.
     * @return list of MDX keywords
     */
    public abstract List<String> getKeywords();

    public abstract RolapResultShepherd getResultShepherd();



    /**
     * Called when the server must terminate all background tasks
     * and cleanup all potential memory leaks.
     */
    public abstract void shutdown();

    /**
     * Called just after a connection has been created.
     *
     * @param connection Connection
     */
    public abstract void addConnection(RolapConnection connection);

    /**
     * Called when a connection is closed.
     *
     * @param connection Connection
     */
    public abstract void removeConnection(RolapConnection connection);

    /**
     * Retrieves a connection.
     *
     * @param connectionId Connection id, per
     *   {@link mondrian.rolap.RolapConnection#getId()}
     *
     * @return Connection, or null if connection is not registered
     */
    public abstract RolapConnection getConnection(int connectionId);

    /**
     * Called just after a statement has been created.
     *
     * @param statement Statement
     */
    public abstract void addStatement(Statement statement);

    /**
     * Called when a statement is closed.
     *
     * @param statement Statement
     */
    public abstract void removeStatement(Statement statement);

    public abstract Monitor getMonitor();

    public abstract AggregationManager getAggregationManager();


	public Map<Long, Statement> getStatementMap() {
		// TODO Auto-generated method stub
		return null;
	}


}
