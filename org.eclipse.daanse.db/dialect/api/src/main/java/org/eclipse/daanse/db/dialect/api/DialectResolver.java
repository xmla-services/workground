/*********************************************************************
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
*   Sergei Semenkov - initial
**********************************************************************/
package org.eclipse.daanse.db.dialect.api;

import javax.sql.DataSource;

/**
 * A {@link DialectResolver} gives access to Dialects that are compatible with a
 * {@link DataSource}
 * 
 * @author stbischof
 *
 */
public interface DialectResolver {

    /**
     * Returns a dialect that could operate on the given DataSource. The given
     * dialect MUST be compatible. Must return value >=0 on
     * {@link org.eclipse.daanse.db.dialect.api.Dialect#isCompatible(DataSource)}.
     * 
     * The implementation COULD choose others than the best Dialect.
     * 
     * @param dataSource
     * @returns a Dialect
     */
    Dialect resolve(DataSource dataSource);

}
