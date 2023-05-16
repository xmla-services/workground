/*
 * Copyright (c) 2023 Contributors to the Eclipse Foundation.
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

import org.olap4j.OlapException;

public class Olap4jRuntimeException extends RuntimeException{

    public Olap4jRuntimeException(String msg) {
        super(msg);
    }

    public Olap4jRuntimeException(Exception e) {
        super(e);
    }
}
