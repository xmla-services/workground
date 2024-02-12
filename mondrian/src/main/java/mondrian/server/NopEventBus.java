/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2011-2021 Hitachi Vantara
// All Rights Reserved.
*/
package mondrian.server;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.lang.management.MemoryUsage;

import org.eclipse.daanse.olap.api.monitor.EventBus;
import org.eclipse.daanse.olap.api.monitor.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mondrian.server.monitor.ConnectionInfo;
import mondrian.server.monitor.ExecutionInfo;
import mondrian.server.monitor.ServerInfo;
import mondrian.server.monitor.SqlStatementInfo;
import mondrian.server.monitor.StatementInfo;


public class NopEventBus implements EventBus {
  private static final Logger LOGGER = LoggerFactory.getLogger( NopEventBus.class );

  protected static final MemoryInfo MEMORY_INFO = getMemoryInfo();



  public NopEventBus() {
	  
  }



	@Override
	public void accept(Event event) {
		System.out.println(event);
	}





  /**
   * Workspace to collect statistics about the execution of a Mondrian server.
   */
  private static class MutableServerInfo {
    private final MutableSqlStatementInfo aggSql = new MutableSqlStatementInfo( null, -1, null, null );
    private final MutableExecutionInfo aggExec = new MutableExecutionInfo( null, -1, null );
    private final MutableStatementInfo aggStmt = new MutableStatementInfo( null, -1, null );
    private final MutableConnectionInfo aggConn = new MutableConnectionInfo( null );
    private final String stack;

    public MutableServerInfo( String stack ) {
      this.stack = stack;
    }

    public ServerInfo fix() {
      MemoryInfo.Usage memoryUsage = MEMORY_INFO.get();
      return new ServerInfo( stack, aggConn.startCount, aggConn.endCount, aggStmt.startCount, aggStmt.endCount,
          aggSql.startCount, aggSql.executeCount, aggSql.endCount, aggSql.rowFetchCount, aggSql.executeNanos,
          aggSql.cellRequestCount, aggExec.cellCacheHitCount, aggExec.cellCacheRequestCount,
          aggExec.cellCacheMissCount, aggExec.cellCachePendingCount, aggExec.startCount, aggExec.endCount, memoryUsage
              .getUsed(), memoryUsage.getCommitted(), memoryUsage.getMax(), ( aggExec.cellCacheSegmentCreateCount
                  - aggExec.cellCacheSegmentDeleteCount ), aggExec.cellCacheSegmentCreateCount,
          aggExec.cellCacheSegmentCreateViaExternalCount, aggExec.cellCacheSegmentDeleteViaExternalCount,
          aggExec.cellCacheSegmentCreateViaRollupCount, aggExec.cellCacheSegmentCreateViaSqlCount,
          aggExec.cellCacheSegmentCellCount, aggExec.cellCacheSegmentCoordinateSum );
    }
  }

  /**
   * Workspace to collect statistics about the execution of a Mondrian MDX statement. Parent context is the server.
   */
  private static class MutableConnectionInfo {
    private final MutableExecutionInfo aggExec = new MutableExecutionInfo( null, -1, null );
    private final MutableStatementInfo aggStmt = new MutableStatementInfo( null, -1, null );
    private int startCount;
    private int endCount;
    private final String stack;

    public MutableConnectionInfo( String stack ) {
      this.stack = stack;
    }

    public ConnectionInfo fix() {
      return new ConnectionInfo( stack, aggExec.cellCacheHitCount, aggExec.cellCacheRequestCount,
          aggExec.cellCacheMissCount, aggExec.cellCachePendingCount, aggStmt.startCount, aggStmt.endCount,
          aggExec.startCount, aggExec.endCount );
    }
  }

  /**
   * Workspace to collect statistics about the execution of a Mondrian MDX statement. Parent context is the connection.
   */
  private static class MutableStatementInfo {
    private final MutableConnectionInfo conn;
    private final long statementId;
    private final MutableExecutionInfo aggExec = new MutableExecutionInfo( null, -1, null );
    private final MutableSqlStatementInfo aggSql = new MutableSqlStatementInfo( null, -1, null, null );
    private int startCount;
    private int endCount;
    private final String stack;

    public MutableStatementInfo( MutableConnectionInfo conn, long statementId, String stack ) {
      this.statementId = statementId;
      this.conn = conn;
      this.stack = stack;
    }

    public StatementInfo fix() {
      return new StatementInfo( stack, statementId, aggExec.startCount, aggExec.endCount, aggExec.phaseCount,
          aggExec.cellCacheRequestCount, aggExec.cellCacheHitCount, aggExec.cellCacheMissCount,
          aggExec.cellCachePendingCount, aggSql.startCount, aggSql.executeCount, aggSql.endCount, aggSql.rowFetchCount,
          aggSql.executeNanos, aggSql.cellRequestCount );
    }
  }

  /**
   * <p>
   * Workspace to collect statistics about the execution of a Mondrian MDX statement. A statement execution occurs
   * within the context of a statement.
   * </p>
   *
   * <p>
   * Most statements are executed only once. It is possible (if you use the {@link org.olap4j.PreparedOlapStatement} API
   * for instance) to execute a statement more than once. There can be at most one execution at a time for a given
   * statement. Thus a statement's executeStartCount and executeEndCount should never differ by more than 1.
   * </p>
   */
  private static class MutableExecutionInfo {
    private final MutableStatementInfo stmt;
    private final long executionId;
    private final MutableSqlStatementInfo aggSql = new MutableSqlStatementInfo( null, -1, null, null );
    private int startCount;
    private int phaseCount;
    private int endCount;
    private int cellCacheRequestCount;
    private int cellCacheHitCount;
    private int cellCacheMissCount;
    private int cellCachePendingCount;
    private int cellCacheHitCountDelta;
    private int cellCacheMissCountDelta;
    private int cellCachePendingCountDelta;
    private int cellCacheSegmentCreateCount;
    private int cellCacheSegmentCreateViaRollupCount;
    private int cellCacheSegmentCreateViaSqlCount;
    private int cellCacheSegmentCreateViaExternalCount;
    private int cellCacheSegmentDeleteViaExternalCount;
    private int cellCacheSegmentDeleteCount;
    private int cellCacheSegmentCoordinateSum;
    private int cellCacheSegmentCellCount;
    private final String stack;
    private int expCacheHitCount;
    private int expCacheMissCount;

    public MutableExecutionInfo( MutableStatementInfo stmt, long executionId, String stack ) {
      this.stmt = stmt;
      this.executionId = executionId;
      this.stack = stack;
    }

    public ExecutionInfo fix() {
      return new ExecutionInfo( stack, executionId, phaseCount, cellCacheRequestCount, cellCacheHitCount,
          cellCacheMissCount, cellCachePendingCount, aggSql.startCount, aggSql.executeCount, aggSql.endCount,
          aggSql.rowFetchCount, aggSql.executeNanos, aggSql.cellRequestCount, expCacheHitCount, expCacheMissCount );
    }
  }

  /**
   * Workspace to collect statistics about the execution of a SQL statement. A SQL statement execution occurs within the
   * context of a Mondrian MDX statement.
   */
  private static class MutableSqlStatementInfo {
    private final MutableStatementInfo stmt; // parent context
    private final long sqlStatementId;
    private int startCount;
    private int executeCount;
    private int endCount;
    private int cellRequestCount;
    private long executeNanos;
    private long rowFetchCount;
    private final String stack;
    private final String sql;

    public MutableSqlStatementInfo( MutableStatementInfo stmt, long sqlStatementId, String sql, String stack ) {
      this.sqlStatementId = sqlStatementId;
      this.stmt = stmt;
      this.sql = sql;
      this.stack = stack;
    }

    public SqlStatementInfo fix() {
      return new SqlStatementInfo( stack, sqlStatementId, sql );
    }
  }



  /**
   * Information about memory usage.
   *
   */
  public interface MemoryInfo {
      Usage get();

      public interface Usage {
          long getUsed();
          long getCommitted();
          long getMax();
      }
  }

	public static MemoryInfo getMemoryInfo() {
		return new MemoryInfo() {
			protected static final MemoryPoolMXBean TENURED_POOL = findTenuredGenPool();

			@Override
			public MemoryInfo.Usage get() {
				final MemoryUsage memoryUsage = TENURED_POOL.getUsage();
				return new Usage() {
					@Override
					public long getUsed() {
						return memoryUsage.getUsed();
					}

					@Override
					public long getCommitted() {
						return memoryUsage.getCommitted();
					}

					@Override
					public long getMax() {
						return memoryUsage.getMax();
					}
				};
			}
		};
	}

	private static MemoryPoolMXBean findTenuredGenPool() {
		for (MemoryPoolMXBean pool : ManagementFactory.getMemoryPoolMXBeans()) {
			if (pool.getType() == MemoryType.HEAP) {
				return pool;
			}
		}
		throw new AssertionError("Could not find tenured space");
	}



}
