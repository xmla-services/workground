package org.eclipse.daanse.olap.api;

import java.time.Duration;

public interface QueryTiming {

	/**
	 * Marks the start of a Query component's execution.
	 *
	 * @param name Name of the component
	 */
	void markStart(String name);

	/**
	 * Marks the end of a Query component's execution.
	 *
	 * @param name Name of the component
	 */
	void markEnd(String name);

	/**
	 * Marks the duration of a Query component's execution.
	 *
	 * markFull is synchronized because it may be called from either Actor's spawn
	 * thread or RolapResultShepherd thread
	 *
	 * @param name     Name of the component
	 * @param duration Duration of the execution
	 */

	void markFull(String string, Duration duration);

}