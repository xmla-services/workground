/*********************************************************************
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 **********************************************************************/

package org.eclipse.daanse.core.api.olap;

public interface IQueryTiming {
    void init( boolean enabled );

    void done();

    /**
     * Marks the start of a Query component's execution.
     *
     * @param name
     *          Name of the component
     */
     void markStart( String name );

    /**
     * Marks the end of a Query component's execution.
     *
     * @param name
     *          Name of the component
     */
    void markEnd( String name );

    /**
     * Marks the duration of a Query component's execution.
     *
     * markFull is synchronized because it may be called from either Actor's spawn thread or RolapResultShepherd thread
     *
     * @param name
     *          Name of the component
     * @param duration
     *          Duration of the execution
     */
    void markFull( String name, long duration );
}
