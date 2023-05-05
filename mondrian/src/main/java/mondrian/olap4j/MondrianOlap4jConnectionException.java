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
package mondrian.olap4j;

public class MondrianOlap4jConnectionException extends RuntimeException{

    public MondrianOlap4jConnectionException(String msg) {
        super(msg);
    }

    public MondrianOlap4jConnectionException(Exception e) {
        super(e);
    }
}
