package org.eclipse.daanse.olap.api;

import mondrian.server.Locus;

public interface Execution {

	void cancelSqlStatements();

	Statement getMondrianStatement();

	void setOutOfMemory(String string);

	long getId();

	void checkCancelOrTimeout();

	boolean isCancelOrTimeout();

	long getElapsedMillis();

	void tracePhase(int hitCount, int missCount, int pendingCount);

	void setCellCacheHitCount(int hitCount);

	void setCellCacheMissCount(int missCount);

	void setCellCachePendingCount(int pendingCount);

	void setExpCacheCounts(int expResultCacheHitCount, int expResultCacheMissCount);

	void registerStatement(Locus locus, java.sql.Statement stmt);

	void end();

	void start();

	void cancel();

	QueryTiming getQueryTiming();

	int getExpCacheHitCount();

	int getExpCacheMissCount();

}
