/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package mondrian.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A collection of counters. Used internally for logging and
 * consistency-checking purposes. Should not be relied upon by applications.
 */
public abstract class Counters {
    /** Number of times {@code SqlStatement.execute} has completed
     * successfully. */
    public static final AtomicLong SQL_STATEMENT_EXECUTE_COUNT =
        new AtomicLong();

    /** Number of times {@code SqlStatement.close} has been called. */
    public static final AtomicLong SQL_STATEMENT_CLOSE_COUNT = new AtomicLong();

    /** Ids of all {@code SqlStatement} instances that are executing. */
    public static final Set<Long> SQL_STATEMENT_EXECUTING_IDS =
        Collections.synchronizedSet(new HashSet<Long>());
}
